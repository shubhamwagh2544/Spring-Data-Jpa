package com.spring.demo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Laptop {

    @Id
    private int id;
    private String name;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(                                                //owing entity
            name = "student_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(                           //foreign key managed here
                    name = "student_laptop_foreign_key"
            )
    )
    private Student student;

    public Laptop(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Laptop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", student=" + student +
                '}';
    }
}
