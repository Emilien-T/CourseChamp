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
import ca.mcgill.ecse428.CourseChamp.model.Student;
import ca.mcgill.ecse428.CourseChamp.repository.AdminRepository;
import ca.mcgill.ecse428.CourseChamp.repository.StudentRepository;
import ca.mcgill.ecse428.CourseChamp.service.AdminService;

@SpringBootTest
public class AdminServiceTests {

    @Mock
    private AdminRepository adminRepository;
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private AdminService adminService;

    // =-=-=-=-=-=- Create Accout Service Tests -=-=-=-=-=-=//
    // New user with unique info
    @Test
    public void testCreateValidAdmin() {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Admin john = new Admin(email, username, password);

        when(adminRepository.save(john)).thenReturn(john);

        Admin output = adminService.createAdminAccount(john);

        assertNotNull(output);
        assertEquals(john, output);
    }

    // User registers with an existent email
    @Test
    public void testCreateDuplicateEmailAdmin() {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Admin john = new Admin(email, username, password);

        when(adminRepository.findAdminByEmail(email)).thenReturn(john);

        final String password1 = "JaneDoe2002";
        final String username1 = "Jane Doe";
        final Admin jane = new Admin(email, username1, password1);

        CourseChampException e = assertThrows(CourseChampException.class, () -> adminService.createAdminAccount(jane));
        assertEquals(e.getStatus(), HttpStatus.CONFLICT);
        assertEquals(e.getMessage(), "Another account with this email already exists");
    }

    @Test
    public void testCreateDuplicateEmailAdminStudent() {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Student.Major major = Student.Major.Software;
        final Student john = new Student(email, username, password, major);

        when(studentRepository.findStudentByUsername(username)).thenReturn(john);
        when(studentRepository.findStudentByEmail(email)).thenReturn(john);

        final String password1 = "JaneDoe2002";
        final String username1 = "Jane Doe";
        final Admin jane = new Admin(email, username1, password1);

        CourseChampException e = assertThrows(CourseChampException.class, () -> adminService.createAdminAccount(jane));
        assertEquals(e.getStatus(), HttpStatus.CONFLICT);
        assertEquals(e.getMessage(), "Another account with this email already exists");
    }

    // User registers with an existent username
    @Test
    public void testCreateDuplicateUsernameAdmin() {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Admin john = new Admin(email, username, password);

        when(adminRepository.findAdminByUsername(username)).thenReturn(john);
        when(adminRepository.findAdminByEmail(email)).thenReturn(john);

        final String email1 = "jane.doe@gmail.ca";
        final String password1 = "JaneDoe2002";
        final Admin jane = new Admin(email1, username, password1);

        CourseChampException e = assertThrows(CourseChampException.class, () -> adminService.createAdminAccount(jane));
        assertEquals(e.getStatus(), HttpStatus.CONFLICT);
        assertEquals(e.getMessage(), "Another account with this username already exists");
    }

    @Test
    public void testCreateDuplicateUsernameAdminStudent() {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Student.Major major = Student.Major.Software;
        final Student john = new Student(email, username, password, major);

        when(studentRepository.findStudentByUsername(username)).thenReturn(john);
        when(studentRepository.findStudentByEmail(email)).thenReturn(john);

        final String email1 = "jane.doe@gmail.ca";
        final String password1 = "JaneDoe2002";
        final Admin jane = new Admin(email1, username, password1);

        CourseChampException e = assertThrows(CourseChampException.class, () -> adminService.createAdminAccount(jane));
        assertEquals(e.getStatus(), HttpStatus.CONFLICT);
        assertEquals(e.getMessage(), "Another account with this username already exists");
    }


    // =-=-=-=-=-=- Login Accout Service Tests -=-=-=-=-=-=//
    // User successfully login using email
    @Test
    public void testLoginWithEmail() {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Admin john = new Admin(email, username, password);

        when(adminRepository.findAdminByUsername(username)).thenReturn(john);
        when(adminRepository.findAdminByEmail(email)).thenReturn(john);

        Admin output = adminService.loginIntoAdmin(email, password);

        assertNotNull(output);
        assertEquals(john, output);
    }

    // User successfully login using username
    @Test
    public void testLoginWithUsername() {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Admin john = new Admin(email, username, password);

        when(adminRepository.findAdminByEmail(username)).thenReturn(null);
        when(adminRepository.findAdminByUsername(username)).thenReturn(john);

        Admin output = adminService.loginIntoAdmin(username, password);

        assertNotNull(output);
        assertEquals(john, output);
    }

    // User login with a non-existent email
    @Test
    public void testLoginWithNonExistentEmail() {
        final String email = "john.doe@mcgill.ca";
        final String password = "JohnDoe2002";

        when(adminRepository.findAdminByEmail(email)).thenReturn(null);

        CourseChampException e = assertThrows(CourseChampException.class,
                () -> adminService.loginIntoAdmin(email, password));
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
        assertEquals(e.getMessage(), "Admin account not found");
    }

    @Test
    public void testLoginWithNonExistentUsername() {
        final String username = "blahhhhh";
        final String password = "JohnDoe2002";

        when(adminRepository.findAdminByEmail(username)).thenReturn(null);

        CourseChampException e = assertThrows(CourseChampException.class,
                () -> adminService.loginIntoAdmin(username, password));
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
        assertEquals(e.getMessage(), "Admin account not found");
    }

    // User login with a wrong password
    @Test
    public void testLoginWithWrongPassword() {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Admin john = new Admin(email, username, password);
        final String password1 = "password1234";

        when(adminRepository.findAdminByEmail(email)).thenReturn(john);
        when(adminRepository.findAdminByUsername(email)).thenReturn(john);

        CourseChampException e = assertThrows(CourseChampException.class,
                () -> adminService.loginIntoAdmin(email, password1));
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
        assertEquals(e.getMessage(), "Please enter the correct password");
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//

    // =-=-=-=-=-=-= Get Accout Service Tests =-=-=-=-=-=-=//

    // Get Admin with email
    @Test
    public void testGetAdminByValidEmail() {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Admin john = new Admin(email, username, password);

        when(adminRepository.findAdminByEmail(email)).thenReturn(john);

        Admin output = adminService.getAdminByEmail(email);

        assertNotNull(output);
        assertEquals(output, john);

    }

    // Get Admin with a non-existant email
    @Test
    public void testGetAdminByInvalidEmail() {
        final String email = "jane.doe@mcgill.ca";

        when(adminRepository.findAdminByEmail(email)).thenReturn(null);

        CourseChampException e = assertThrows(CourseChampException.class, () -> adminService.getAdminByEmail(email));
        assertEquals(e.getMessage(), "Admin account not found");
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
}