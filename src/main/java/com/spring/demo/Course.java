package com.spring.demo;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Course {
    @Id
    private int id;
    private String name;

    @ManyToMany(
            mappedBy = "courses",
            fetch = FetchType.EAGER
    )
    private List<Student> students = new ArrayList<>();

    public Course(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
