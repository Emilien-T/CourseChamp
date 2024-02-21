package ca.mcgill.ecse428.CourseChamp.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse428.CourseChamp.model.Admin;

/**
 * DAO class in the spring framework that acts as a link between the database and java application
 * for CRUD operations of the Admin class in the context of the Course Champ system
 */
public interface AdminRepository extends CrudRepository<Admin, String>{

  /**
	 * Find owner based on email
	 * @param email
	 * @return Admin found
	 */
    public Admin findAdminByEmail(String email); 
	public Admin findAdminByUsername(String username);   
}