package com.cisco.task.exception;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class ApiErrorResponse {
    private List<String> errors;
    private String message;

}
