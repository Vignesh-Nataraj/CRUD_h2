package com.example.crud_h2.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @Temporal(TemporalType.DATE)
    private Date dob;
    private String nickname;

    public User(Date dob, String nickname, String name, Long id, String email) {
        this.dob = dob;
        this.nickname = nickname;
        this.name = name;
        this.id = id;
        this.email = email;
    }
}