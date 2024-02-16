package ca.mcgill.ecse428.CourseChamp.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse428.CourseChamp.model.Account;

/**
 * DAO class in the spring framework that acts as a link between the database and java application
 * for CRUD operations of the Account abstract class in the context of the Course Champ system
 */
public interface AccountRepository extends CrudRepository<Account, String>{

  /**
	 * Find an account by email
	 * @param email
	 * @return Account found
	 */
    public Account findAccountByEmail(String email);
	public Account findAccountByUsername(String username);
}