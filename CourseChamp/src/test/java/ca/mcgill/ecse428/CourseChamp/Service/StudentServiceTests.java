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
import ca.mcgill.ecse428.CourseChamp.model.Student;
import ca.mcgill.ecse428.CourseChamp.model.Student.Major;
import ca.mcgill.ecse428.CourseChamp.repository.AdminRepository;
import ca.mcgill.ecse428.CourseChamp.repository.StudentRepository;
import ca.mcgill.ecse428.CourseChamp.service.StudentService;

@SpringBootTest
public class StudentServiceTests {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private StudentService studentService;

    // =-=-=-=-=-=- Create Accout Service Tests -=-=-=-=-=-=//
    // New user with unique info
    @Test
    public void testCreateValidStudent() {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Major major = Major.Software;
        final Student john = new Student(email, username, password, major);

        when(studentRepository.save(john)).thenReturn(john);

        Student output = studentService.createStudentAccount(john);

        assertNotNull(output);
        assertEquals(john, output);
    }

    // User registers with an existent email
    @Test
    public void testCreateDuplicateEmailStudent() {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Major major = Major.Software;
        final Student john = new Student(email, username, password, major);

        when(studentRepository.findStudentByEmail(email)).thenReturn(john);

        final String password1 = "JaneDoe2002";
        final String username1 = "Jane Doe";
        final Major major1 = Major.Computer;
        final Student jane = new Student(email, username1, password1, major1);

        CourseChampException e = assertThrows(CourseChampException.class,
                () -> studentService.createStudentAccount(jane));
        assertEquals(e.getStatus(), HttpStatus.CONFLICT);
        assertEquals(e.getMessage(), "Another account with this email already exists");
    }

    // User registers with an existent username
    @Test
    public void testCreateDuplicateUsernameStudent() {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Major major = Major.Software;
        final Student john = new Student(email, username, password, major);

        when(studentRepository.findStudentByUsername(username)).thenReturn(john);
        when(studentRepository.findStudentByEmail(email)).thenReturn(john);

        final String email1 = "jane.doe@gmail.ca";
        final String password1 = "JaneDoe2002";
        final Major major1 = Major.Computer;
        final Student jane = new Student(email1, username, password1, major1);

        CourseChampException e = assertThrows(CourseChampException.class,
                () -> studentService.createStudentAccount(jane));
        assertEquals(e.getStatus(), HttpStatus.CONFLICT);
        assertEquals(e.getMessage(), "Another account with this username already exists");
    }

    // User registers with an empty email
    @Test
    public void testCreateEmptyEmailStudent() {
        // THIS SHOULD BE AN INTEGRATION TEST (TESTING THE CONTROLLER!)
        // final String email = "";
        // final String username = "John Doe";
        // final String password = "JohnDoe2002";
        // final Major major = Major.Software;
        // final Student john = new Student(email, username, password, major);

        // when(studentRepository.save(john)).thenReturn(john);

        // CourseChampException e = assertThrows(CourseChampException.class,
        // () -> studentService.createStudentAccount(john));
        // assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        // assertEquals(e.getMessage(), "All fields must be filled");
    }

    // User registers with an empty username
    @Test
    public void testCreateEmptyUsernameStudent() {
        // THIS SHOULD BE AN INTEGRATION TEST (TESTING THE CONTROLLER!)
        // final String email = "john.doe@mcgill.ca";
        // final String username = "";
        // final String password = "JohnDoe2002";
        // final Major major = Major.Software;
        // final Student john = new Student(email, username, password, major);

        // when(studentRepository.save(john)).thenReturn(john);

        // CourseChampException e = assertThrows(CourseChampException.class,
        // () -> studentService.createStudentAccount(john));
        // assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        // assertEquals(e.getMessage(), "All fields must be filled");
    }

    // User registers with an empty password
    @Test
    public void testCreateEmptyPasswordStudent() {
        // THIS SHOULD BE AN INTEGRATION TEST (TESTING THE CONTROLLER!)
        // final String email = "john.doe@mcgill.ca";
        // final String username = "John Doe";
        // final String password = "";
        // final Major major = Major.Software;
        // final Student john = new Student(email, username, password, major);

        // when(studentRepository.save(john)).thenReturn(john);

        // CourseChampException e = assertThrows(CourseChampException.class,
        // () -> studentService.createStudentAccount(john));
        // assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        // assertEquals(e.getMessage(), "All fields must be filled");
    }

    // User registers with an empty major
    @Test
    public void testCreateEmptyMajorStudent() {
        // THIS SHOULD BE AN INTEGRATION TEST (TESTING THE CONTROLLER!)
        // final String email = "john.doe@mcgill.ca";
        // final String username = "John Doe";
        // final String password = "";
        // final Major major = null;
        // final Student john = new Student(email, username, password, major);

        // when(studentRepository.save(john)).thenReturn(john);

        // CourseChampException e = assertThrows(CourseChampException.class,
        // () -> studentService.createStudentAccount(john));
        // assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        // assertEquals(e.getMessage(), "All fields must be filled");
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//

    // =-=-=-=-=-=- Login Accout Service Tests -=-=-=-=-=-=//
    // User successfully login using email
    @Test
    public void testLoginWithEmail() {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Major major = Major.Software;
        final Student john = new Student(email, username, password, major);

        when(studentRepository.findStudentByEmail(email)).thenReturn(john);

        Student output = studentService.loginIntoStudent(email, password);

        assertNotNull(output);
        assertEquals(john, output);
    }

    // User successfully login using username
    @Test
    public void testLoginWithUsername() {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Major major = Major.Software;
        final Student john = new Student(email, username, password, major);

        when(studentRepository.findStudentByEmail(email)).thenReturn(null);
        when(studentRepository.findStudentByUsername(username)).thenReturn(john);

        Student output = studentService.loginIntoStudent(username, password);

        assertNotNull(output);
        assertEquals(john, output);
    }

    // User login with a non-existent email
    @Test
    public void testLoginWithNonExistantEmail() {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";

        when(studentRepository.findStudentByEmail(email)).thenReturn(null);
        when(studentRepository.findStudentByUsername(username)).thenReturn(null);

        CourseChampException e = assertThrows(CourseChampException.class,
                () -> studentService.loginIntoStudent(email, password));
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
        assertEquals(e.getMessage(), "Student not found.");
    }

    // User login with a wrong password
    @Test
    public void testLoginWithWrongPassword() {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Major major = Major.Software;
        final Student john = new Student(email, username, password, major);
        final String password1 = "password1234";

        when(studentRepository.findStudentByEmail(email)).thenReturn(john);

        CourseChampException e = assertThrows(CourseChampException.class,
                () -> studentService.loginIntoStudent(email, password1));
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
        assertEquals(e.getMessage(), "Please enter the correct password");
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//

    // =-=-=-=-=-=-= Get Accout Service Tests =-=-=-=-=-=-=//

    // Get Student with email
    @Test
    public void testGetStudentByValidEmail() {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Major major = Major.Software;
        final Student john = new Student(email, username, password, major);

        when(studentRepository.findStudentByEmail(email)).thenReturn(john);

        Student output = studentService.getStudentByEmail(email);

        assertNotNull(output);
        assertEquals(output, john);

    }

    // Get Student with a non-existant email
    @Test
    public void testGetStudentByInvalidEmail() {
        final String email = "jane.doe@mcgill.ca";

        when(studentRepository.findStudentByEmail(email)).thenReturn(null);

        CourseChampException e = assertThrows(CourseChampException.class,
                () -> studentService.getStudentByEmail(email));
        assertEquals(e.getMessage(), "Student not found.");
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
}