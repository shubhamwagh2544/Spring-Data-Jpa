package com.spring.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(
            """
            select s from Student s
            where s.email = ?1
            """
    )
    Optional<Student> findStudentByEmail(String email);

    @Query(
            """
            select s from Student s
            where s.firstName = ?1 and s.age >= ?2
            """
    )
    List<Student> findStudentsByFirstNameEqualsAndAgeGreaterThanEqual(String firstname,
                                                                               Integer age);
}