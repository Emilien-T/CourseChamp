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
import ca.mcgill.ecse428.CourseChamp.model.Admin;
import ca.mcgill.ecse428.CourseChamp.repository.AdminRepository;
import ca.mcgill.ecse428.CourseChamp.service.AdminService;

@SpringBootTest
public class AdminServiceTests {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    //=-=-=-=-=-=- Create Accout Service Tests -=-=-=-=-=-=//
    //New user with unique info
    @Test
    public void testCreateValidAdmin()
    {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Admin john = new Admin(email, username, password);

        when(adminRepository.save(john)).thenReturn(john);

        Admin output = adminService.createAdminAccount(john);

        assertNotNull(output);
        assertEquals(john, output);
    }

    //User registers with an existent email
    @Test
    public void testCreateDuplicateEmailAdmin()
    {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Admin john = new Admin(email, username, password);

        when(adminRepository.findAdminByEmail(email)).thenReturn(john);

        final String password1 = "JaneDoe2002";
        final String username1 = "Jane Doe";
        final Admin jane = new Admin(email, username1, password1);

        CourseChampException e = assertThrows(CourseChampException.class, () -> adminService.createAdminAccount(jane));
        assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(e.getMessage(), "Email already used");
    }

    //User registers with an existent username
    @Test
    public void testCreateDuplicateUsernameAdmin()
    {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Admin john = new Admin(email, username, password);

        when(adminRepository.findAdminByUsername(username)).thenReturn(john);

        final String email1 = "jane.doe@gmail.ca";
        final String password1 = "JaneDoe2002";
        final Admin jane = new Admin(email1, username, password1);

        CourseChampException e = assertThrows(CourseChampException.class, () -> adminService.createAdminAccount(jane));
        assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(e.getMessage(), "Username already used");
    }

    //User registers with an empty email
    @Test
    public void testCreateEmptyEmailAdmin()
    {
        final String email = "";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Admin john = new Admin(email, username, password);

        when(adminRepository.save(john)).thenReturn(john);

        CourseChampException e = assertThrows(CourseChampException.class, () -> adminService.createAdminAccount(john));
        assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(e.getMessage(), "All fields must be filled");
    }
    //User registers with an empty username
    @Test
    public void testCreateEmptyUsernameAdmin()
    {
        final String email = "john.doe@mcgill.ca";
        final String username = "";
        final String password = "JohnDoe2002";
        final Admin john = new Admin(email, username, password);

        when(adminRepository.save(john)).thenReturn(john);

        CourseChampException e = assertThrows(CourseChampException.class, () -> adminService.createAdminAccount(john));
        assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(e.getMessage(), "All fields must be filled");
    }
    //User registers with an empty password
    @Test
    public void testCreateEmptyPasswordAdmin()
    {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "";
        final Admin john = new Admin(email, username, password);

        when(adminRepository.save(john)).thenReturn(john);

        CourseChampException e = assertThrows(CourseChampException.class, () -> adminService.createAdminAccount(john));
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
        final Admin john = new Admin(email, username, password);

        when(adminRepository.findAdminByUsername(email)).thenReturn(john);

        Admin output = adminService.loginIntoAdmin(email,password);

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
        final Admin john = new Admin(email, username, password);

        when(adminRepository.findAdminByEmail(username)).thenReturn(null);
        when(adminRepository.findAdminByUsername(username)).thenReturn(john);

        Admin output = adminService.loginIntoAdmin(username,password);

        assertNotNull(output);
        assertEquals(john, output);
    }

    //User login with a non-existent email
    @Test
    public void testLoginWithNonExistantEmail()
    {
        final String email = "john.doe@mcgill.ca";
        final String password = "JohnDoe2002";

        when(adminRepository.findAdminByUsername(email)).thenReturn(null);

        CourseChampException e = assertThrows(CourseChampException.class, () -> adminService.loginIntoAdmin(email,password));
        assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(e.getMessage(), "No admin with this email exists");
    }

    //User login with a wrong password
    @Test
    public void testLoginWithWrongPassword()
    {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Admin john = new Admin(email, username, password);
        final String password1 = "password1234";

        when(adminRepository.findAdminByUsername(email)).thenReturn(john);

        CourseChampException e = assertThrows(CourseChampException.class, () -> adminService.loginIntoAdmin(email,password1));
        assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(e.getMessage(), "Given password is wrong");
    }
    
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//

    //=-=-=-=-=-=-= Get Accout Service Tests =-=-=-=-=-=-=//
    
    //Get Admin with email
    @Test
    public void testGetEmployeeByValidEmail()
    {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Admin john = new Admin(email, username, password);

        when(adminRepository.findAdminByEmail(email)).thenReturn(john);

        Admin output = adminService.getAdminByEmail(email);

        assertNotNull(output);
        assertEquals(output, john);

    }

    //Get Admin with a non-existant email
    @Test
    public void testGetEmployeeByInvalidEmail()
    {
        final String email = "jane.doe@mcgill.ca";

        when(adminRepository.findAdminByEmail(email)).thenReturn(null);

        CourseChampException e = assertThrows(CourseChampException.class, () -> adminService.getAdminByEmail(email));
        assertEquals(e.getMessage(), "Employee not found.");
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
    }
    
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
}