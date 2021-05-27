package com.cisco.task.caseresource

import com.cisco.task.BaseIT
import com.cisco.task.exception.ApiErrorResponse
import com.fasterxml.jackson.core.type.TypeReference
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Unroll

import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.OK

class CaseResourceSpec extends BaseIT {

    @Autowired
    CaseRepository caseRepository

    @Autowired
    UserRepository userRepository

    def "should be able to create case with valid input." () {
        given:
            def requestDto = getValidStartCaseDto()
        when:
            def response = performPost("/case/create", requestDto)
        then:
            CaseDto caseDto = objectMapper.asClass(response.getContentAsString(), CaseDto.class)
            verifyAll {
                response.status == OK.value()
                response.contentType == 'application/json'
                caseDto.description == requestDto.description
                caseDto.status == requestDto.status
                caseDto.notes == []
                caseDto.severity == requestDto.severity
                caseDto.title == requestDto.title
            }
    }

    @Unroll
    def "should not be able to create case with incorrect input (#field = #value)." () {
        given:
            def requestDto = getValidStartCaseDto()
            requestDto[field] = value
        when:
            def response = performPost("/case/create", requestDto)
        then:
            ApiErrorResponse apiErrorResponse = objectMapper.asClass(response.getContentAsString(), ApiErrorResponse.class)
            verifyAll {
                response.status == BAD_REQUEST.value()
                apiErrorResponse.message == "Constraints has been violated."
                apiErrorResponse.errors == errors
            }
        where:
            field          | value    || errors
            'title'        | ""       || ["Case title should not be blank."]
            'title'        | null     || ["Case title should not be blank."]
            'description'  | ""       || ["Case description should not be blank."]
            'description'  | null     || ["Case description should not be blank."]
            'userFirstName'| ""       || ["User first name should not be blank."]
            'userFirstName'| null     || ["User first name should not be blank."]
            'userLastName' | ""       || ["User last name should not be blank."]
            'userLastName' | null     || ["User last name should not be blank."]
            'userEmail'    | ""       || ["User email should not be blank."]
            'userEmail'    | null     || ["User email should not be blank."]
            'status'       | "CLOSED" || ["Status of new case should be set to OPEN."]
    }

    def "should not be able to close case that does not exists." () {
        given:
            Long caseId = -99
        when:
            def response = performPut("/case/${caseId}/close", null)
        then:
            ApiErrorResponse apiErrorResponse = objectMapper.asClass(response.getContentAsString(), ApiErrorResponse.class)
            verifyAll {
                response.status == BAD_REQUEST.value()
                response.contentType == 'application/json'
                apiErrorResponse.message == 'Constraints has been violated.'
                apiErrorResponse.errors == ["Case with id=${caseId} does not exists."]
            }
    }

    def "should not be able to see cases for user that does not exists." () {
        given:
            Long userId = -99
        when:
            def response = performGet("/cases/user/${userId}", null)
        then:
            ApiErrorResponse apiErrorResponse = objectMapper.asClass(response.getContentAsString(), ApiErrorResponse.class)
            verifyAll {
                response.status == BAD_REQUEST.value()
                response.contentType == 'application/json'
                apiErrorResponse.message == 'Constraints has been violated.'
                apiErrorResponse.errors == ["User with id=${userId} does not exists."]
            }
    }

    def "should not be able to add note to case that does not exists." () {
        given:
            Long caseId = -99
            def requestDto = getValidNoteDto()
        when:
            def response = performPost("/case/${caseId}/addNote", requestDto)
        then:
            ApiErrorResponse apiErrorResponse = objectMapper.asClass(response.getContentAsString(), ApiErrorResponse.class)
            verifyAll {
                response.status == BAD_REQUEST.value()
                response.contentType == 'application/json'
                apiErrorResponse.message == 'Constraints has been violated.'
                apiErrorResponse.errors == ["Case with id=${caseId} does not exists."]
            }
    }

    def "should not be able to add note to case that is closed" () {
        given:
            def requestNoteDto = getValidNoteDto()
            def requestDto = getValidStartCaseDto()
            def caseCreateResponse = performPost("/case/create", requestDto)
            def caseDto = objectMapper.asClass(caseCreateResponse.getContentAsString(), CaseDto.class)
            def caseId = caseDto.getId()
            performPut("/case/${caseId}/close", null)
        when:
            def response = performPost("/case/${caseId}/addNote", requestNoteDto)
        then:
            ApiErrorResponse apiErrorResponse = objectMapper.asClass(response.getContentAsString(), ApiErrorResponse.class)
            verifyAll {
                response.status == BAD_REQUEST.value()
                apiErrorResponse.message == "Constraints has been violated."
                apiErrorResponse.errors == ["Case with id=${caseId} is already closed."]
            }
    }

    def "should be able to close case that is open." () {
        given:
            def requestDto = getValidStartCaseDto()
            def caseDtoResponse = performPost("/case/create", requestDto)
            CaseDto caseDto = objectMapper.asClass(caseDtoResponse.getContentAsString(), CaseDto.class)
            def caseId = caseDto.getId()
        when:
            def response = performPut("/case/${caseId}/close", null)
        then:
            CaseDto closedCaseDto = objectMapper.asClass(response.getContentAsString(), CaseDto.class)
            verifyAll {
                response.status == OK.value()
                caseDto.status == Case.Status.OPEN
                closedCaseDto.status == Case.Status.CLOSED
            }
    }

    def "should not be able to close case that is already closed." () {
        given:
            def requestDto = getValidStartCaseDto()
            def caseDtoResponse = performPost("/case/create", requestDto)
            CaseDto caseDto = objectMapper.asClass(caseDtoResponse.getContentAsString(), CaseDto.class)
            def caseId = caseDto.getId()
            performPut("/case/${caseId}/close", null)
        when:
            def response = performPut("/case/${caseId}/close", null)
        then:
            ApiErrorResponse apiErrorResponse = objectMapper.asClass(response.getContentAsString(), ApiErrorResponse.class)
            verifyAll {
                response.status == BAD_REQUEST.value()
                apiErrorResponse.message == "Constraints has been violated."
                apiErrorResponse.errors == ["Case with id=${caseId} is already closed."]
            }
    }

    def "should get case by id" () {
        given:
            // should be moved to sql script.
            performPost("/case/create", getValidStartCaseDto())
            performPost("/case/create", getValidStartCaseDto())

            def requestDto = getValidStartCaseDto()
            def createCaseResponse = performPost("/case/create", requestDto)
            CaseDto caseDto = objectMapper.asClass(createCaseResponse.getContentAsString(), CaseDto.class)
            def caseId = caseDto.getId()
        when:
            def response = performGet("/case/${caseId}", null)
        then:
            CaseDto caseDtoResponse = objectMapper.asClass(response.getContentAsString(), CaseDto.class)
            verifyAll {
                response.status == OK.value()
                caseDtoResponse.id == caseId
                caseDtoResponse.status == caseDto.status
                caseDtoResponse.description == caseDto.description
                caseDtoResponse.title == caseDto.title
                caseDtoResponse.severity == caseDto.severity
                caseDtoResponse.notes == caseDto.notes
                caseDtoResponse.user == caseDto.user
            }
    }


    def "should get cases assigned to particular user" () {
        given:
            def firstRequestDto = getValidStartCaseDto()
            firstRequestDto['userFirstName'] = 'Keanu'
            firstRequestDto['userLastName'] = 'Reeves'
            firstRequestDto['userEmail'] = 'keanu.reeves@gmail.com'
            performPost("/case/create", firstRequestDto)
            def secondRequestDto = getValidStartCaseDto()
            secondRequestDto['userFirstName'] = 'Bill'
            secondRequestDto['userLastName'] = 'Gates'
            secondRequestDto['userEmail'] = 'bill.gates@gmail.com'
            def secondRequestResponse = performPost("/case/create", secondRequestDto)
            CaseDto caseDto = objectMapper.asClass(secondRequestResponse.getContentAsString(), CaseDto.class)
            Integer userId = userRepository.findByEmail(secondRequestDto['userEmail']).getId()
        when:
            def response = performGet("/cases/user/${userId}", null)
        then:
            TypeReference<List<CaseDto>> typeRef = new TypeReference<List<CaseDto>>() {}
            List<CaseDto> caseDtos = objectMapper.asTypeRef(response.getContentAsString(), typeRef)
            verifyAll {
                response.status == OK.value()
                caseDtos*.user.forEach{user -> user == caseDto.getUser()}
            }
    }

    def "should get only CLOSED cases" () {
        given:
            def firstRequestDto = getValidStartCaseDto()
            def firstCaseResponse = performPost("/case/create", firstRequestDto)
            CaseDto firstCase = objectMapper.asClass(firstCaseResponse.getContentAsString(), CaseDto.class)

            def secondRequestDto = getValidStartCaseDto()
            def secondCaseResponse =  performPost("/case/create", secondRequestDto)
            CaseDto secondCase = objectMapper.asClass(secondCaseResponse.getContentAsString(), CaseDto.class)

            performPut("/case/${secondCase.getId()}/close", null)
        when:
            def response = performGet("/cases/status/CLOSED", null)
        then:
            TypeReference<List<CaseDto>> typeRef = new TypeReference<List<CaseDto>>() {}
            List<CaseDto> caseDtos = objectMapper.asTypeRef(response.getContentAsString(), typeRef)
            verifyAll {
                response.status == OK.value()
                !caseDtos*.id.contains(firstCase.id)
                caseDtos*.status.forEach{status -> status == Case.Status.CLOSED }
            }
    }

    def "should get only OPEN cases" () {
        given:
            def firstRequestDto = getValidStartCaseDto()
            def firstCaseResponse = performPost("/case/create", firstRequestDto)
            CaseDto firstCase = objectMapper.asClass(firstCaseResponse.getContentAsString(), CaseDto.class)

            def secondRequestDto = getValidStartCaseDto()
            def secondCaseResponse =  performPost("/case/create", secondRequestDto)
            CaseDto secondCase = objectMapper.asClass(secondCaseResponse.getContentAsString(), CaseDto.class)

            performPut("/case/${secondCase.getId()}/close", null)
        when:
            def response = performGet("/cases/status/OPEN", null)
        then:
            TypeReference<List<CaseDto>> typeRef = new TypeReference<List<CaseDto>>() {}
            List<CaseDto> caseDtos = objectMapper.asTypeRef(response.getContentAsString(), typeRef)
            verifyAll {
                response.status == OK.value()
                !caseDtos*.id.contains(secondCase.id)
                caseDtos*.status.forEach{status -> status == Case.Status.OPEN }
            }
    }

    def getValidStartCaseDto() {
        StartCaseDto startCaseDto = new StartCaseDto()
        startCaseDto.setDescription("testDescription")
        startCaseDto.setTitle("testTitle")
        startCaseDto.setSeverity(1)
        startCaseDto.setStatus(Case.Status.OPEN)
        startCaseDto.setUserEmail("test@gmail.com")
        startCaseDto.setUserFirstName("testUserFirstName")
        startCaseDto.setUserLastName("testUserLastName")
        return startCaseDto
    }

    def getValidNoteDto() {
        NoteDto noteDto = new NoteDto();
        noteDto.setDetails("testDetails")
        return noteDto
    }
}
