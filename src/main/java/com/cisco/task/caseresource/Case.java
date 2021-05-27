package com.cisco.task.caseresource;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cases")
@Setter
@Getter
public class Case {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer caseId;
    private String title;
    private String description;
    private Integer severity;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToOne(
            optional = true,
            cascade = CascadeType.ALL
    )
    private User user;
    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Note> notes;

    public enum Status{
        OPEN,
        CLOSED
    }

    public void addNote(Note note){
        note.setCaseId(this.caseId);
        this.notes.add(note);
    }
}
