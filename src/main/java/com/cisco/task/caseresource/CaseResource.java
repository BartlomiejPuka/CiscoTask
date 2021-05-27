package com.cisco.task.caseresource;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class CaseResource {

    private final CaseService caseService;

    /**
     * Query for cases with specified status.
     * @param status
     * @return
     */
    @GetMapping("/cases/status/{status}")
    List<CaseDto> getCasesWithStatus(@PathVariable Case.Status status) {
        return caseService.getCaseByStatus(status);
    }

    /**
     * Query for cases with a userId.
     * @param userId
     * @return
     */
    @GetMapping("/cases/user/{userId}")
    List<CaseDto> getCasesByUserId(@PathVariable Integer userId) {
        return caseService.getCaseByUserId(userId);
    }

    /**
     * Query for cases with a userId and specified status.
     * @param userId
     * @param status
     * @return
     */
    @GetMapping("/cases/user/{userId}/status/{status}")
    List<CaseDto> getCasesByUserIdAndStatus(@PathVariable Integer userId, @PathVariable Case.Status status) {
        return caseService.getCaseByUserIdAndStatus(userId, status);
    }

    /**
     * Query for case with given id.
     * @param caseId
     * @return
     */
    @GetMapping("/case/{caseId}")
    CaseDto getCase(@PathVariable Integer caseId) {
        return caseService.getCaseById(caseId);
    }

    /**
     * Creates new case.
     * @param startCaseDto
     * @return
     */
    @PostMapping(value = "/case/create", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Integer createCase(@RequestBody StartCaseDto startCaseDto) {
        return caseService.createCase(startCaseDto);
    }


    /**
     * Adds note to existing case.
     * @param caseId
     * @param noteDto
     */
    @PostMapping(value = "/case/{caseId}/addNote", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public CaseDto addNote(@PathVariable Integer caseId, @RequestBody NoteDto noteDto) {
        return caseService.addNote(noteDto, caseId);
    }


    @PutMapping(value = "/case/{caseId}/close", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public CaseDto addNote(@PathVariable Integer caseId) {
        return caseService.closeCase(caseId);
    }

}
