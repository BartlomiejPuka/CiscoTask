package com.cisco.task.caseresource;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class NoteDto {
    @NotBlank
    private String details;
}
