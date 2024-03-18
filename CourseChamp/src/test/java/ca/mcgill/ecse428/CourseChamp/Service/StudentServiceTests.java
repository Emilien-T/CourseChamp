package ca.mcgill.ecse428.CourseChamp.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse428.CourseChamp.exception.CourseChampException;
import ca.mcgill.ecse428.CourseChamp.model.Admin;
import ca.mcgill.ecse428.CourseChamp.model.Course;
import ca.mcgill.ecse428.CourseChamp.model.CourseOffering;
import ca.mcgill.ecse428.CourseChamp.model.Review;
import ca.mcgill.ecse428.CourseChamp.model.Student;
import ca.mcgill.ecse428.CourseChamp.model.Student.Major;
import ca.mcgill.ecse428.CourseChamp.repository.AdminRepository;
import ca.mcgill.ecse428.CourseChamp.repository.ReviewRepository;
import ca.mcgill.ecse428.CourseChamp.repository.StudentRepository;
import ca.mcgill.ecse428.CourseChamp.service.StudentService;

@SpringBootTest
public class StudentServiceTests {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private AdminRepository adminRepository;
    @Mock
    private ReviewRepository reviewRepository;

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

    @Test
    public void testCreateDuplicateEmailStudentAdmin() {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Admin john = new Admin(email, username, password);

        when(adminRepository.findAdminByEmail(email)).thenReturn(john);

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

    @Test
    public void testCreateDuplicateUsernameStudentAdmin() {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Admin john = new Admin(email, username, password);

        when(adminRepository.findAdminByUsername(username)).thenReturn(john);

        final String email1 = "jane.doe@mcgill.ca";
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

        // CourseChampException e = assertThrows(CourseChampException.class, () -> studentService.createStudentAccount(john));
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
    public void testLoginWithNonExistentEmail() {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";

        when(studentRepository.findStudentByEmail(email)).thenReturn(null);
        when(studentRepository.findStudentByUsername(username)).thenReturn(null);

        CourseChampException e = assertThrows(CourseChampException.class,
                () -> studentService.loginIntoStudent(email, password));
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
        assertEquals(e.getMessage(), "Student account not found");
    }

    @Test
    public void testLoginWithNonExistentUsername() {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";

        when(studentRepository.findStudentByEmail(email)).thenReturn(null);
        when(studentRepository.findStudentByUsername(username)).thenReturn(null);

        CourseChampException e = assertThrows(CourseChampException.class,
                () -> studentService.loginIntoStudent(username, password));
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
        assertEquals(e.getMessage(), "Student account not found");
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
        assertEquals(e.getMessage(), "Student account not found");
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//

    // =-=-=-=-=-=-= Update Student Service Tests =-=-=-=-=-=//

    // Update all feilds successfully
    @Test
    public void testUpdateStudentSuccessfully() {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Major major = Major.Software;
        final Student john = new Student(email, username, password, major);

        when(studentRepository.findStudentByEmail(email)).thenReturn(john);

        final String newUsername = "Jordan Doe";
        final String newPassword = "JordanDoe2003";
        final Major newMajor = Major.Computer;
        final Student newJohn = new Student(email, newUsername, newPassword, newMajor);

        when(studentRepository.save(john)).thenReturn(newJohn);

        Student output = studentService.updateStudentAccount(newJohn);

        assertNotNull(output);
        assertEquals(newJohn, output);
    }

    // Try to update email (should be unsucessfull)
    @Test
    public void testUpdateStudentEmail() {
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Major major = Major.Software;
        final Student john = new Student(email, username, password, major);

        final String newEmail = "john.doe@mcgill.ca";
        final String newUsername = "";
        final String newPassword = "JordanDoe2003";
        final Major newMajor = Major.Computer;
        final Student newJohn = new Student(newEmail, newUsername, newPassword, newMajor);

        when(studentRepository.findStudentByEmail(newEmail)).thenReturn(null);

        CourseChampException e = assertThrows(CourseChampException.class,
                () -> studentService.updateStudentAccount(newJohn));
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
        assertEquals(e.getMessage(), "Student account not found");
    }
    

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= //

    // =-=-=-=-= Get Student Reviews Service Tests =-=-=-= //

    // Get reviews of the student
    // Try to update email (should be unsucessfull)
    @Test
    public void testGetReviewsSuccessfully() {
        //Creating student
        final String email = "john.doe@mcgill.ca";
        final String username = "John Doe";
        final String password = "JohnDoe2002";
        final Major major = Major.Software;
        final Student john = new Student(email, username, password, major);

        when(studentRepository.findStudentByEmail(email)).thenReturn(john);

        //Creating course offering
        CourseOffering courseOffering = new CourseOffering();
        Course course = new Course();
        String courseCode = "ECSE428";
        course.setCourseCode(courseCode);
        courseOffering.setCourse(course);
        
        //Creating Review
        ArrayList<Review> mockReviews = new ArrayList<>();
        int reviewId = 1;
        String reviewText = "This is a review";
        int rating = 5;

        Review review = new Review();
        review.setId(reviewId);
        review.setCourseOffering(courseOffering);
        review.setText(reviewText);
        review.setRating(rating);
        review.setStudent(john);

        mockReviews.add(review);

        when(reviewRepository.findAll()).thenReturn(mockReviews);

        List<Review> output = studentService.getReviewsOfStudent(email);

        assertNotNull(output, "The returned review list should not be null.");
        assertFalse(output.isEmpty(), "The review list should not be empty.");
        assertEquals(mockReviews.size(), output.size(),
                "The size of returned review list should match the mock list.");
    }

    // Student has no reviews
    @Test
    public void testGetReviewsUnsuccessfully() {
        final String email = "john.doe@mcgill.ca";

        ArrayList<Review> mockReviews = new ArrayList<>();

        when(reviewRepository.findAll()).thenReturn(mockReviews);

        Exception exception = assertThrows(
                CourseChampException.class,
                () -> studentService.getReviewsOfStudent(email));
        assertEquals(HttpStatus.NOT_FOUND, ((CourseChampException) exception).getStatus(),
                "HttpStatus should be NOT_FOUND.");
        assertEquals("No reviews found for this student.", exception.getMessage(),
                "Exception message should match the expected text.");
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= //

}