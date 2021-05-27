package com.cisco.task.caseresource;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NoteMapper {

    public Note toEntity(NoteDto noteDto){
        Note note = new Note();
        note.setDetails(noteDto.getDetails());
        return note;
    }

    public NoteDto toDto(Note note) {
        NoteDto noteDto = new NoteDto();
        noteDto.setDetails(note.getDetails());
        return noteDto;
    }
}
