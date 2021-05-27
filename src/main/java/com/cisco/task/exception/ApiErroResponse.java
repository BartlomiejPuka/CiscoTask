package com.cisco.task.exception;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;


@Value
@Builder
@RequiredArgsConstructor
public class ApiErroResponse {
    private final List<String> errors;
    private final String message;
}
