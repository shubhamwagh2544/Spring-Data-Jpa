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

			List<Student> queryStudents =
					studentRepository.findStudentsByFirstNameEqualsAndAgeGreaterThanEqual("Maria", 25);

			queryStudents.forEach(System.out::println);
		};
	}

}