package com.child.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;  // e.g., Toddler Group, Pre-K Group

    @NotNull
    private Integer ageMin;

    @NotNull
    private Integer ageMax;

    private Integer capacity;

    @OneToMany(mappedBy = "group")
    private Set<Child> children = new HashSet<>();

    @OneToOne(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Teacher teacher;
}
