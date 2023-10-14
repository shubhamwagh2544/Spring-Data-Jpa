package com.spring.demo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    private int id;
    private String name;

    @ManyToOne
    @JoinColumn(                                //owing entity
            name = "student_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(                   //foreign key managed here
                    name = "student_book_foreign_key"
            )
    )
    private Student student;

    public Book(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
