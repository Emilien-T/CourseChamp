package ca.mcgill.ecse428.CourseChamp.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse428.CourseChamp.model.Student;

/**
 * DAO class in the spring framework that acts as a link between the database and java application
 * for CRUD operations of the Student class in the context of the Course Champ system
 */
public interface StudentRepository extends CrudRepository<Student, String>{

  /**
	 * Find a student by email
	 * @param email
	 * @return Student found
	 */
    public Student findStudentByEmail(String email);    
}