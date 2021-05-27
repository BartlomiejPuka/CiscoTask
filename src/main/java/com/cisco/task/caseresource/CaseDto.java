package com.cisco.task.caseresource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaseDto {
    private String title;
    private String description;
    private Integer severity;
    private Case.Status status;
}
