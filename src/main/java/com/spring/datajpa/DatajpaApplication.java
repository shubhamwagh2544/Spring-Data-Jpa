package com.spring.datajpa;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DatajpaApplication {

	private static final Faker faker = new Faker();

	public static void main(String[] args) {
		SpringApplication.run(DatajpaApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository,
										StudentIdCardRepository studentIdCardRepository) {
		return args -> {
			Student student = generateStudent();
			StudentIdCard studentIdCard = new StudentIdCard(
					"123456789",
					student
			);
			studentIdCardRepository.save(studentIdCard);

			studentIdCardRepository.findById(1L)							//bidirectional
					.ifPresent(System.out::println);

			studentRepository.findById(1L)									//bidirectional
					.ifPresent(System.out::println);

			studentRepository.deleteById(1L);

		};
	}

	private static void generateRandomStudents(StudentRepository studentRepository) {

		for (int i = 1; i <= 20; i++) {
			Student student = generateStudent();
			studentRepository.save(student);
		}
	}

	private static Student generateStudent() {
		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();
		String email = String.format("%s.%s@gmail.com", firstName, lastName);
		int age = faker.number().numberBetween(10, 100);

		return new Student(
				firstName,
				lastName,
				email,
				age
		);
	}

}