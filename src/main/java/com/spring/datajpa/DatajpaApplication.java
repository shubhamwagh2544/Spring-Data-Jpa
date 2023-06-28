package com.spring.datajpa;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SpringBootApplication
public class DatajpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatajpaApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
		return args -> {
			generateRandomStudents(studentRepository);

			sorting(studentRepository);

			paging(studentRepository);
		};
	}

	private static void paging(StudentRepository studentRepository) {
		PageRequest pageRequest = PageRequest.of(
				0,
				5,
				Sort.by("firstName").ascending()
		);

		Page<Student> page = studentRepository.findAll(pageRequest);
		System.out.println(page);
	}

	private static void sorting(StudentRepository studentRepository) {
		Sort sort1 =
				Sort.by(Sort.Direction.ASC, "firstName")
				.and(Sort.by(Sort.Direction.DESC, "age"));

		Sort sort2 =
				Sort.by("firstName").ascending()
				.and(Sort.by("age").descending());

		studentRepository.findAll(sort1)
				.forEach(student ->
						System.out.println(student.getFirstName())
				);
	}

	private static void generateRandomStudents(StudentRepository studentRepository) {
		Faker faker = new Faker();
		for (int i = 1; i <= 20; i++) {
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = String.format("%s.%s@gmail.com", firstName, lastName);
			int age = faker.number().numberBetween(10, 100);
			Student student = new Student(
					firstName,
					lastName,
					email,
					age
			);
			studentRepository.save(student);
		}
	}

}