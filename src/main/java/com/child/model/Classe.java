package com.child.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomClass;   
    private String capacite;
    private String minAge;
    private String maxAge;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;


    }
