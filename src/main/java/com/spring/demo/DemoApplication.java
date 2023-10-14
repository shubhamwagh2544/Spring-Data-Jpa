package com.spring.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository,
										LaptopRepository laptopRepository,
										BookRepository bookRepository,
										CourseRepository courseRepository) {
		return args -> {
			Student student = new Student(1, "Shubham Wagh");
			Laptop laptop = new Laptop(1, "Macbook Pro");

			//one to one
			laptop.setStudent(student);					//owning side will have to take responsibility
			laptopRepository.save(laptop);

			laptopRepository.findById(1).ifPresentOrElse(
					System.out::println,
					() -> System.out.println("Not Found")
			);

			//one to many
			Book book1 = new Book(1, "Harry Potter: Vol 1");
			Book book2 = new Book(2, "Harry Potter: Vol 2");
			Book book3 = new Book(3, "Harry Potter: Vol 3");

			book1.setStudent(student);					//owning side will have to take responsibility
			book2.setStudent(student);
			book3.setStudent(student);

			bookRepository.save(book1);
			bookRepository.save(book2);
			bookRepository.save(book3);

			studentRepository.findById(1).ifPresentOrElse(
					student1 -> {
						List<Book> books = student1.getBooks();
						books.forEach(System.out::println);
					},
					() -> System.out.println("Not Found")
			);

			//many to many
			Course course2 = new Course(2, "course 2");
			Course course3 = new Course(3, "course 3");

			Student student2 = new Student(2, "Student 2");
			Student student3 = new Student(3, "Student 3");

			List<Course> courses = new ArrayList<>();
			courses.add(course2);
			courses.add(course3);

			student2.setCourses(courses);					//owning side
			student3.setCourses(courses);					//owning side

			studentRepository.save(student2);
			studentRepository.save(student3);

			studentRepository.findById(2).ifPresentOrElse(
					student1 -> {
						List<Course> courseList = student1.getCourses();
						courseList.forEach(System.out::println);
					},
					() -> System.out.println("Not Found")
			);

			courseRepository.findById(2).ifPresentOrElse(
					course -> {
						List<Student> studentList = course.getStudents();
						studentList.forEach(System.out::println);
					},
					() -> System.out.println("Not Found")
			);
		};
	}

}
