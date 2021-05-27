package com.cisco.task.caseresource;

import com.cisco.task.caseresource.validators.StartCaseStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StartCaseDto {
    @NotBlank(message = "Case title should not be blank.")
    private String title;
    @NotBlank(message = "Case description should not be blank.")
    private String description;
    private Integer severity;
    @StartCaseStatus
    private Case.Status status;
    @NotBlank(message = "User first name should not be blank.")
    private String userFirstName;
    @NotBlank(message = "User last name should not be blank.")
    private String userLastName;
    @NotBlank(message = "User email should not be blank.")
    @Email
    private String userEmail;
}
