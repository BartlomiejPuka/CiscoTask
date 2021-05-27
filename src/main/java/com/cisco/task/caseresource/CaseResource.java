package com.cisco.task.caseresource;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CaseResource {

    private final CaseService caseService;

    @GetMapping("/cases/status/{status}")
    List<CaseDto> getCasesWithStatus(@PathVariable Case.Status status) {
        // TODO: Query for cases with a specified status.
        return caseService.getCaseByStatus(status);
    }

    @GetMapping("/cases/user/{userId}")
    List<Case> getOpenCases(@PathVariable Integer userId) {
        // TODO: Query for cases with a userId;
        return List.of();
    }

    @GetMapping("/cases/user/{userId}/status/{status}")
    List<CaseDto> getOpenCases(@PathVariable Integer userId, @PathVariable Case.Status status) {
        return caseService.getCaseByUserIdAndStatus(userId, status);
    }

    @GetMapping("/case/{caseId}")
    CaseDto getCase(@PathVariable Integer caseId) {
        return caseService.getCaseById(caseId);
    }

    @PostMapping(value = "/case/create", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Integer createCase(@RequestBody CaseDto toCreate) {
        return caseService.createCase(toCreate);
    }
//
    @PostMapping(value = "/case/{caseId}/addNote", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void addNote(@PathVariable Integer caseId, @RequestBody NoteDto noteDto) {
        caseService.addNote(noteDto, caseId);
    }

}
