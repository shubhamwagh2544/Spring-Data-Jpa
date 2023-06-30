package com.spring.datajpa;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

			student.addBook(
					new Book(
							"Spring Security",
							LocalDateTime.now().minusDays(5)
					)
			);
			student.addBook(
					new Book(
							"Spring Boot",
							LocalDateTime.now().minusDays(5)
					)
			);
			student.addBook(
					new Book(
							"Spring Data Jpa",
							LocalDateTime.now().minusDays(5)
					)
			);

			student.setStudentIdCard(studentIdCard);

//			student.enrollToCourse(new Course("Computer Science", "IT"));
//			student.enrollToCourse(new Course("Political Science", "Arts"));

			student.addEnrollment(new Enrollment(
					new EnrollmentId(1L, 1L),
					student,
					new Course("Computer Science", "IT")
			));

			student.addEnrollment(new Enrollment(
					new EnrollmentId(1L, 2L),
					student,
					new Course("Political Science", "Arts")
			));

			studentRepository.save(student);

			studentRepository.findById(1L)									//bidirectional
					.ifPresent(System.out::println);
			//this won't print books as EAGER type is lazy in one to many and many to one

			//after assigning fetch type as eager
			studentRepository.findById(1L)									//bidirectional
					.ifPresent(
							student1 -> {
								List<Book> books = student1.getBooks();
								books.forEach(
										book -> {
											System.out.println(book.getBookName());
										}
								);
							}
					);


			studentIdCardRepository.findById(1L)							//bidirectional
					.ifPresent(System.out::println);


		};
	}

	private static void testStudentToStudentIdCardOneToOne(StudentRepository studentRepository, StudentIdCardRepository studentIdCardRepository, StudentIdCard studentIdCard) {
		studentIdCardRepository.save(studentIdCard);
		studentRepository.deleteById(1L);
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