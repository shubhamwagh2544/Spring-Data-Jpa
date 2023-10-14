package com.spring.demo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student {

    @Id
    private int id;
    private String name;

    @OneToOne(
            mappedBy = "student",       //not owing entity
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private Laptop laptop;

    @OneToMany(
            mappedBy = "student",       //not owing entity
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER         //default-lazy
    )
    private List<Book> books = new ArrayList<>();

    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "student_course_table",
            joinColumns = { @JoinColumn(name = "student_id", referencedColumnName = "id") },         //owning side
            inverseJoinColumns = { @JoinColumn(name = "course_id", referencedColumnName = "id") }  //inverse side
    )
    private List<Course> courses = new ArrayList<>();

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
