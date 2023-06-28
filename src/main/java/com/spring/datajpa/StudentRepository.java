package com.spring.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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
            value = """
            select *
            from student
            where email = ?1
            """,
            nativeQuery = true
    )
    Optional<Student> selectStudentByEmailNative(String email);

    @Query(
            """
            select s from Student s
            where s.firstName = ?1 and s.age >= ?2
            """
    )
    List<Student> findStudentsByFirstNameEqualsAndAgeGreaterThanEqual(String firstname,
                                                                      Integer age);

    @Query(
            value =
            """
            select *
            from student
            where first_name = ?1 and age >= ?2
            """,
            nativeQuery = true
    )
    List<Student> selectAllStudentsWhereFirstnameAndAgeIsGreaterThanOrEqualsNative(String firstname,
                                                                                   Integer age);

    @Query(
            """
            select s from Student s
            where s.firstName = :firstName and s.age >= :age
            """
    )
    List<Student> selectStudentsWithFirstNameAndAgeJPQLUsingNamedParameter(@Param("firstName") String firstname,
                                                                           @Param("age") Integer age);

    @Query(
            value =
                    """
                    select *
                    from student
                    where first_name = :firstName and age >= :age
                    """,
            nativeQuery = true
    )
    List<Student> selectStudentsWithFirstNameAndAgeNativeUsingNamedParameter(@Param("firstName") String firstname,
                                                                       @Param("age") Integer age);

    @Transactional
    @Modifying
    @Query(
            """
            delete from Student s where s.id = ?1
            """
    )
    int deleteStudentById(Long id);

    @Transactional
    @Modifying
    @Query(
            value = """
            delete from
            student
            where email = :email
            """,
            nativeQuery = true
    )
    int deleteStudentWithEmailNativeSQLWithNamedParameter(@Param("email") String email);
}
