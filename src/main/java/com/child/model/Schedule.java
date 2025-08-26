package com.child.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    

    private String day; 

    private String activity;  

    private String startTime;  
    private String endTime;   
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Activity> activities;

    @ManyToOne
    @JoinColumn(name = "classe_id", nullable = false)
    private Classe classe;

 
}
