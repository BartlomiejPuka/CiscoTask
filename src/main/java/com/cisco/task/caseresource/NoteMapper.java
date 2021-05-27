package com.cisco.task.caseresource;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NoteMapper {

    public Note toEntity(NoteDto noteDto){
        Note note = new Note();
        note.setDetails(noteDto.getDetails());
        return note;
    }

}
