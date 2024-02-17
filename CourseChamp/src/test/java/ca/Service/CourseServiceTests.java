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
import ca.mcgill.ecse428.CourseChamp.model.CourseId;
import ca.mcgill.ecse428.CourseChamp.model.CourseOffering;
import ca.mcgill.ecse428.CourseChamp.repository.CourseRepository;
import ca.mcgill.ecse428.CourseChamp.service.CourseService;

@SpringBootTest
public class CourseServiceTests {

    @Mock
    private CourseRepository CourseRepository;

    @InjectMocks
    private CourseService CounterService;

    //=-=-=-=-=-=- Create Course Service Tests -=-=-=-=-=-=//
    //New course added by admin
    @Test
    public void testAdminAddsNewCourse()
    {
        final String department = "ECSE";
        final String courseCode = "223";
        final String name = "Software Engineering Principles";

        when(courseRepository.save(223)).thenReturn(223);

        Course output = CourseService.createCourse(223);

        assertNotNull(output);
        assertEquals(223, output);
    }

    //Admin attempts to add a duplicate course
    @Test
    public void testAttemptsToAddDuplicateCourse()
    {
        final String department = "ECSE";
        final String courseCode = "223";
        final Course 223 = new Course(department, courseCode, name);

        when(CourseRepository.findCourseBycourseCode(223)).thenReturn(223);

        final String department = "ECSE";
        final String courseCode = "223";
        final String name = "Software Engineering Principles";

        final Course 223 = new Course(department, courseCode, name);

        CourseChampException e = assertThrows(CourseChampException.class, () -> CourseService.createCourse(223));
        assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(e.getMessage(), "Course already exists");
    }


    //Admin adds new course with various incomplete details
    @Test
    public void testCreateIncompleteDetailsCourse()
    {
        final String department = "";
        final String courseCode = "";
        final String name = "";

        final Course 223 = new Course(department, courseCode, name);

        when(CourseRepository.findCourseBycourseCode(223)).thenReturn(223);

        CourseChampException e = assertThrows(CourseChampException.class, () -> CourseService.createCourse(223));
        assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(e.getMessage(), "Incomplete Details");
    }
    //Admin adds a new course with an invalid course number
    @Test
    public void testInvalidCourseNumberTest()
    {
        final String department = "ECSE";
        final String courseCode = "ABC";
        final String name = "Software Engineering Principles";

       // final Course 223 = new Course(department, courseCode, name);

        when(CourseRepository.findCourseBycourseCode(ABC)).thenReturn(null);

        CourseChampException e = assertThrows(CourseChampException.class, () -> CourseService.createCourse(ABC));
        assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(e.getMessage(), "Course number should be a 3-digit number");
    }
    //User registers with an empty password
    @Test
    public void testCreateEmptyPasswordAccount()
    {
        final String department = "EC";
        final String courseCode = "223";
        final String name = "Software Engineering Principles";

        final Course 223 = new Course(department, courseCode, name);

        when(CourseRepository.findCourseByDepartment(EC)).thenReturn(null);

        CourseChampException e = assertThrows(CourseChampException.class, () -> CourseService.createCourse(223));
        assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(e.getMessage(), "Department should be a 4 character alphabetical string");
    }    

}