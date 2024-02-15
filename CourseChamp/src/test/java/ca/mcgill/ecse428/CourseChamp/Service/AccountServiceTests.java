package ca.mcgill.ecse428.CourseChamp.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse428.CourseChamp.exception.CourseChampException;
import ca.mcgill.ecse428.CourseChamp.model.Account;
import ca.mcgill.ecse428.CourseChamp.repository.AccountRepository;
import ca.mcgill.ecse428.CourseChamp.service.AccountService;

@SpringBootTest
public class AccountServiceTests {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    //=-=-=-=-=-=- Create Accout Service Tests -=-=-=-=-=-=//
    //New user with unique info
    @Test
    public void testCreateValidAccount()
    {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Account john = new Account(email, username, password);

        when(accountRepository.save(john)).thenReturn(john);

        Account output = accountService.createAccount(john);

        assertNotNull(output);
        assertEquals(john, output);
    }

    //User registers with an existent email
    @Test
    public void testCreateDuplicateEmailAccount()
    {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Account john = new Account(email, username, password);

        when(accountRepository.findAccountByEmail(email)).thenReturn(john);

        final String password1 = "JaneDoe2002";
        final String username1 = "Jane Doe";
        final Account jane = new Account(email, username1, password1);

        CourseChampException e = assertThrows(CourseChampException.class, () -> accountService.createAccount(jane));
        assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(e.getMessage(), "Email already used");
    }

    //User registers with an existent username
    @Test
    public void testCreateDuplicateUsernameAccount()
    {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Account john = new Account(email, username, password);

        when(accountRepository.findAccountByUsername(email)).thenReturn(john);

        final String email1 = "jane.doe@gmail.ca";
        final String password1 = "JaneDoe2002";
        final Account jane = new Account(email1, username, password1);

        CourseChampException e = assertThrows(CourseChampException.class, () -> accountService.createAccount(jane));
        assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(e.getMessage(), "Username already used");
    }

    //User registers with an empty email
    @Test
    public void testCreateEmptyEmailAccount()
    {
        final String email = "";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Account john = new Account(email, username, password);

        when(accountRepository.save(john)).thenReturn(john);

        CourseChampException e = assertThrows(CourseChampException.class, () -> accountService.createAccount(john));
        assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(e.getMessage(), "All fields must be filled");
    }
    //User registers with an empty username
    @Test
    public void testCreateEmptyUsernameAccount()
    {
        final String email = "john.doe@mcgill.ca";
        final String username = "";
        final String password = "JohnDoe2002";
        final Account john = new Account(email, username, password);

        when(accountRepository.save(john)).thenReturn(john);

        CourseChampException e = assertThrows(CourseChampException.class, () -> accountService.createAccount(john));
        assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(e.getMessage(), "All fields must be filled");
    }
    //User registers with an empty password
    @Test
    public void testCreateEmptyPasswordAccount()
    {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "";
        final Account john = new Account(email, username, password);

        when(accountRepository.save(john)).thenReturn(john);

        CourseChampException e = assertThrows(CourseChampException.class, () -> accountService.createAccount(john));
        assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(e.getMessage(), "All fields must be filled");
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//

    //=-=-=-=-=-=- Login Accout Service Tests -=-=-=-=-=-=//
    //User successfully login using email
    @Test
    public void testLoginWithEmail()
    {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Account john = new Account(email, username, password);

        when(accountRepository.findAccountByUsername(email)).thenReturn(john);

        Account output = accountService.loginIntoAccount(email,password);

        assertNotNull(output);
        assertEquals(john, output);
    }

    //User successfully login using username
    @Test
    public void testLoginWithUsername()
    {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Account john = new Account(email, username, password);

        when(accountRepository.findAccountByUsername(username)).thenReturn(null);
        when(accountRepository.findAccountByUsername(username)).thenReturn(john);

        Account output = accountService.loginIntoAccount(username,password);

        assertNotNull(output);
        assertEquals(john, output);
    }

    //User login with a non-existent email
    @Test
    public void testLoginWithNonExistantEmail()
    {
        final String email = "john.doe@mcgill.ca";
        final String password = "JohnDoe2002";

        when(accountRepository.findAccountByUsername(email)).thenReturn(null);

        CourseChampException e = assertThrows(CourseChampException.class, () -> accountService.loginIntoAccount(email,password));
        assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(e.getMessage(), "No account with this email exists");
    }

    //User login with a wrong password
    @Test
    public void testLoginWithWrongPassword()
    {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Account john = new Account(email, username, password);
        final String password1 = "password1234";

        when(accountRepository.findAccountByUsername(email)).thenReturn(john);

        CourseChampException e = assertThrows(CourseChampException.class, () -> accountService.loginIntoAccount(email,password1));
        assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(e.getMessage(), "Given password is wrong");
    }
    
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//

    //=-=-=-=-=-=-= Get Accout Service Tests =-=-=-=-=-=-=//
    
    //Get Account with email
    @Test
    public void testGetEmployeeByValidEmail()
    {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Account john = new Account(email, username, password);

        when(accountRepository.findAccountByEmail(email)).thenReturn(john);

        Account output = accountService.getAccountByEmail(email);

        assertNotNull(output);
        assertEquals(output, john);

    }

    //Get Account with a non-existant email
    @Test
    public void testGetEmployeeByInvalidEmail()
    {
        final String email = "jane.doe@mcgill.ca";

        when(accountRepository.findAccountByEmail(email)).thenReturn(null);

        CourseChampException e = assertThrows(CourseChampException.class, () -> accountService.getAccountByEmail(email));
        assertEquals(e.getMessage(), "Employee not found.");
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
    }
    
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
}
