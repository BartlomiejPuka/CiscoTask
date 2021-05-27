package com.cisco.task

import com.cisco.task.TaskApplication
import com.cisco.task.TestObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import spock.lang.Specification

import static java.util.Optional.ofNullable
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

@SpringBootTest(classes = [TaskApplication])
@AutoConfigureMockMvc
abstract class BaseIT extends Specification {

    @Autowired
    protected MockMvc mockMvc

    @Autowired
    protected TestObjectMapper objectMapper

    def performGet(String path, Object content) {
        return performRequest(get(path), content)
    }

    def performPut(String path, Object content) {
        return performRequest(put(path), content)
    }

    def performPost(String path, Object content) {
        return performRequest(post(path), content)
    }

    def performRequest(MockHttpServletRequestBuilder requestBuilder, Object content = null) {
        MockHttpServletRequestBuilder builder = requestBuilder
            .contentType(MediaType.APPLICATION_JSON)
        ofNullable(content)
            .map(objectMapper.&asJson)
            .ifPresent(builder.&content)
        return mockMvc.perform(builder).andReturn().getResponse();
    }
}