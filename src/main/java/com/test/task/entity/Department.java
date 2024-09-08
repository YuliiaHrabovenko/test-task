package com.test.task.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Table(name = "department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "head")
    private String departmentHead;

    @Builder.Default
    @ManyToMany(mappedBy = "departments", fetch = FetchType.EAGER)
    private List<Lector> lectors = new ArrayList<>();
}
