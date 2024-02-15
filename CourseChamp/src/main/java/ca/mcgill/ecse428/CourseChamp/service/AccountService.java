package ca.mcgill.ecse428.CourseChamp.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse428.CourseChamp.exception.CourseChampException;
import ca.mcgill.ecse428.CourseChamp.model.Account;
import ca.mcgill.ecse428.CourseChamp.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    /**
     * Service method to fetch an existing account with a specific email from the database
     * @param email email of the account
     * @throws CourseChampException - If account does not exist
     */
    @Transactional
    public Account getAccountByEmail(String email){
        //TODO
        return null;
    }

    /**
     * Service method to store a created account in the database
     * @param account instance of the account to be persisted
     * @throws PLMSException - If an account already exists
     */
    @Transactional
    public Account createAccountAccount(Account account) {
        //TODO
        return null;
    }

    /**
     * Service method to verify that email and password match
     * @param email - email of the account
     * @param password - password of the account
     * @throws CourseChampException - The login fails
     */
    @Transactional
    public Account loginIntoAccount(String email, String password) {
        //TODO
        return null;
    }
}
