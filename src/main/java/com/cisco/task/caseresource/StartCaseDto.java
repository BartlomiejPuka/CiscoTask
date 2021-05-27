package com.cisco.task.caseresource;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StartCaseDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private Integer severity;
    @NotBlank
    private Case.Status status;
    @NotBlank
    private String userFirstName;
    @NotBlank
    private String userLastName;
    @NotBlank
    @Email
    private String userEmail;
}
