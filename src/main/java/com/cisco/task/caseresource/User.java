package com.cisco.task.caseresource;

import lombok.AccessLevel;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
}
