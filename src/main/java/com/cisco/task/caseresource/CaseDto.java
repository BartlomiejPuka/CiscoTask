package com.cisco.task.caseresource;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CaseDto {
    private Integer id;
    private String title;
    private String description;
    private Integer severity;
    private Case.Status status;
    private List<NoteDto> notes;
    private String user;
}
