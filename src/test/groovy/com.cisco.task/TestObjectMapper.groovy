package com.cisco.task

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class TestObjectMapper {

    private final ObjectMapper objectMapper;

    TestObjectMapper() {
        this.objectMapper = new ObjectMapper()
    }

    String asJson(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }

    public <T> T asClass(String content, Class<T> clazz){
       return objectMapper.readValue(content, clazz)
    }

    public <T> T asTypeRef(String content, TypeReference<T> typeReference){
        return objectMapper.readValue(content, typeReference)
    }

}
