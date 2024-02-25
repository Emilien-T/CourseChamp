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
import ca.mcgill.ecse428.CourseChamp.model.Course;
import ca.mcgill.ecse428.CourseChamp.repository.CourseRepository;
import ca.mcgill.ecse428.CourseChamp.service.CourseService;

@SpringBootTest
public class CourseServiceTests {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    // =-=-=-=-=-=- Create Course Service Tests -=-=-=-=-=-=//
    // New course added by admin
    @Test
    public void testAdminAddsNewCourse() {
        final String department = "ECSE";
        final int courseNumber = 223;
        final String name = "Software Engineering Principles";
        final String courseCode = department + courseNumber;

        Course course = new Course(department, courseNumber, name, "", "");

        // Mock the repository to return null indicating that the course does not exist
        when(courseRepository.findCourseByCourseCode(courseCode)).thenReturn(null);
        // Mock the repository to return the course after it is saved
        when(courseRepository.save(course)).thenReturn(course);

        // Call the createCourse method
        Course createdCourse = courseService.createCourse(course);

        // Assertions
        assertNotNull(createdCourse);
        assertEquals(courseCode, createdCourse.getCourseCode());
    }

    // Admin attempts to add a duplicate course
    @Test
    public void testAttemptsToAddDuplicateCourse() {
        final String department = "ECSE";
        final int courseNumber = 223;
        final String name = "Software Engineering Principles";

        Course existingCourse = new Course(department, courseNumber, name, "", "");

        when(courseRepository.findCourseByCourseCode(department + courseNumber)).thenReturn(existingCourse);

        CourseChampException e = assertThrows(CourseChampException.class, () -> {
            courseService.createCourse(existingCourse);
        });
        assertEquals(HttpStatus.CONFLICT, e.getStatus());
        assertEquals("A course with this code already exists", e.getMessage());
    }

    // Admin adds new course with various incomplete details
    @Test
    public void testCreateIncompleteDetailsCourse() {
        // THIS SHOULD BE AN INTEGRATION TEST (TESTING THE CONTROLLER!)
        // final String department = "";
        // final int courseNumber = 223;
        // final String name = "";

        // final Course invalidCourse = new Course(department, courseNumber, name, "",
        // "");

        // // when(CourseRepository.findCourseBycourseCode(department +
        // // course)).thenReturn(null);

        // CourseChampException e = assertThrows(CourseChampException.class,
        // () -> courseService.createCourse(invalidCourse));
        // assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        // assertEquals(e.getMessage(), "Incomplete Details");
    }

    // Admin adds a new course with an invalid course number
    @Test
    public void testInvalidCourseNumberTest() {
        // THIS SHOULD BE AN INTEGRATION TEST (TESTING THE CONTROLLER!)
        // final String department = "ECSE";
        // final int invalidCourseNumber = 3333;
        // final String name = "Software Engineering Principles";

        // final Course invalidCourse = new Course(department, invalidCourseNumber,
        // name, "", "");

        // when(courseRepository.findCourseByCourseCode(department +
        // invalidCourseNumber)).thenReturn(null);

        // CourseChampException e = assertThrows(CourseChampException.class, () ->
        // courseService.createCourse(invalidCourse));
        // assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        // assertEquals(e.getMessage(), "Course number must be a 3-digit integer");
    }

    // Admin adds a new course with an invalid department
    @Test
    public void testInvalidDepartmentTest() {
        // THIS SHOULD BE AN INTEGRATION TEST (TESTING THE CONTROLLER!)
        // final String invalidDepartment = "ECE";
        // final int courseNumber = 223;
        // final String name = "Software Engineering Principles";

        // Course invalidCourse = new Course(invalidDepartment, courseNumber, name, "",
        // "");

        // CourseChampException e = assertThrows(CourseChampException.class,
        // () -> courseService.createCourse(invalidCourse));
        // assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        // assertEquals("Department must be a four-letter alphabetic string",
        // e.getMessage());
    }

}