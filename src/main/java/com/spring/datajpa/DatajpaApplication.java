package com.spring.datajpa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DatajpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatajpaApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
		return args -> {
			Student maria = new Student(
					"Maria",
					"Jones",
					"mariajones@gmail.com",
					25
			);
			Student maria2 = new Student(
					"Maria",
					"Jones",
					"mariajones2@gmail.com",
					25
			);
			Student jake = new Student(
					"Jake",
					"Jones",
					"jakejones@gmail.com",
					25
			);
			System.out.println("saving stundets to repository");
			studentRepository.saveAll(List.of(maria, maria2, jake));

			System.out.println("total number of students in repository");
			System.out.println(studentRepository.count());

			System.out.println("selecting all students");
			List<Student> studentList = studentRepository.findAll();
			studentList.forEach(System.out::println);

			System.out.println("deleting jake from repository");
			studentRepository.delete(jake);

			System.out.println("finding maria by id");
			studentRepository.findById(1L)
					.ifPresentOrElse(
							System.out::println,
							() -> System.out.println("Student with id 1 is not present")
					);

			studentRepository.findStudentByEmail("mariajones@gmail.com")
					.ifPresentOrElse(
							System.out::println,
							() -> System.out.println("Student with email mariajones@gmail not present")
					);

			studentRepository.selectStudentByEmailNative("mariajones@gmail.com")
					.ifPresentOrElse(
							System.out::println,
							() -> System.out.println("Student with email mariajones@gmail not present")
					);

			List<Student> jpqlQueryStudents =
					studentRepository.findStudentsByFirstNameEqualsAndAgeGreaterThanEqual("Maria", 25);

			jpqlQueryStudents.forEach(System.out::println);

			List<Student> nativeQueryStudents =
					studentRepository.selectAllStudentsWhereFirstnameAndAgeIsGreaterThanOrEqualsNative("Maria", 25);

			nativeQueryStudents.forEach(System.out::println);

			List<Student> jpqlNamedParameterStudents =
					studentRepository.selectStudentsWithFirstNameAndAgeJPQLUsingNamedParameter("Maria", 25);

			jpqlNamedParameterStudents.forEach(System.out::println);

			List<Student> nativeNamedParameterStudents =
					studentRepository.selectStudentsWithFirstNameAndAgeNativeUsingNamedParameter("Maria", 25);

			nativeNamedParameterStudents.forEach(System.out::println);

			System.out.println("Deleting student with id 2");
			int d = studentRepository.deleteStudentById(2L);
			System.out.println("DELETE : "+ d);

			System.out.println("Deleting student with email mariajones@gmail.com");
			int deleted =
					studentRepository.deleteStudentWithEmailNativeSQLWithNamedParameter("mariajones@gmail.com");
			System.out.println("DELETE : "+ deleted);
		};
	}

}