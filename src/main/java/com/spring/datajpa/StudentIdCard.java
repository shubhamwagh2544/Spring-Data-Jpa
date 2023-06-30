package com.spring.datajpa;

import jakarta.persistence.*;

@Entity(name = "StudentIdCard")
@Table(
        name = "student_id_card",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "student_id_card_unique",
                        columnNames = "card_number"
                )
        }
)
public class StudentIdCard {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_id_card_seq"
    )
    @SequenceGenerator(
            name = "student_id_card_seq",
            sequenceName = "student_id_card_seq",
            allocationSize = 1
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "card_number",
            nullable = false,
            length = 15
    )
    private String card_number;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER             //eager by default for one-to-one relationships
    )
    @JoinColumn(
            name = "student_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "student_id_foreign_key"
            )
    )
    private Student student;

    public StudentIdCard(String card_number, Student student) {
        this.card_number = card_number;
        this.student = student;
    }

    public StudentIdCard() {
    }

    public Long getId() {
        return id;
    }

    public String getCard_number() {
        return card_number;
    }

    @Override
    public String toString() {
        return "StudentIdCard{" +
                "id=" + id +
                ", card_number='" + card_number + '\'' +
                ", student=" + student +
                '}';
    }
}
