package ca.mcgill.ecse428.CourseChamp.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse428.CourseChamp.exception.CourseChampException;
import ca.mcgill.ecse428.CourseChamp.model.Course;
import ca.mcgill.ecse428.CourseChamp.repository.CourseOfferingRepository;
import ca.mcgill.ecse428.CourseChamp.repository.CourseRepository;
import ca.mcgill.ecse428.CourseChamp.service.CourseService;

@SpringBootTest
public class CourseServiceTests {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private CourseOfferingRepository courseOfferingRepository;

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


    // =-=-=-=-=-=- Update Course Service Tests -=-=-=-=-=-=//
    // Admin updates course
    @Test
    public void testAdminUpdatesCourse() {
        final String department = "ECSE";
        final int courseNumber = 223;
        final String name = "Software Engineering Principles";
        final String courseCode = department + courseNumber;

        Course course = new Course(department, courseNumber, name, "descriptive description", "syllabussy syllabus");

        // Mock the repository to return the course
        when(courseRepository.findCourseByCourseCode(courseCode)).thenReturn(course);
        // Mock the repository to return the course after it is saved
        when(courseRepository.save(course)).thenReturn(course);

        course.setDescription("This course is about software engineering principles");

        // Call the updateCourse method
        Course updatedCourse = courseService.updateCourse(courseCode, course);

        // Assertions
        assertNotNull(updatedCourse);
        assertEquals(courseCode, updatedCourse.getCourseCode());
    }


    // Admin attempts to update a course that does not exist
    @Test
    public void testAdminAttemptsToUpdateNonExistentCourse() {
        final String department = "ECSE";
        final int courseNumber = 226;
        final String name = "Fake Software Engineering Principles";
        final String courseCode = department + courseNumber;

        Course course = new Course(department, courseNumber, name, "descriptive description", "syllabussy syllabus");

        when(courseRepository.findCourseByCourseCode(courseCode)).thenReturn(null);

        CourseChampException e = assertThrows(CourseChampException.class, () -> {
            courseService.updateCourse(courseCode, course);
        });
        assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        assertEquals("Course not found", e.getMessage());
    }







     // Test for successfully deleting a course
    @Test
    public void testSuccessfulDeletion() {
        final String courseCode = "ECSE223";
        Course course = new Course("ECSE", 223, "Software Engineering Principles", "", "");
        when(courseRepository.findCourseByCourseCode(courseCode)).thenReturn(course);

        courseService.deleteCourse(courseCode);
        verify(courseRepository, times(1)).deleteById(courseCode);
    }

    // Test for attempting to delete a non-existing course
    @Test
    public void testDeleteNonExistingCourse() {
        final String courseCode = "ECSE223";
        when(courseRepository.findCourseByCourseCode(courseCode)).thenReturn(null);

        CourseChampException e = assertThrows(CourseChampException.class, () -> {
            courseService.deleteCourse(courseCode);
        });

        assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        assertEquals("This course doesn't exist in the system.", e.getMessage());
    }

    // Test for attempting to delete a course that is a prerequisite for others
    @Test
    public void testDeleteCourseWithPrerequisites() {
        final String courseCode = "ECSE223";
        Course course = new Course("ECSE", 223, "Software Engineering Principles", "", "");
        Course prerequisite = new Course("ECSE", 224, "Software Engineering Principles", "", "");
        course.addPrerequirement(prerequisite);
        when(courseRepository.findCourseByCourseCode(courseCode)).thenReturn(course);

        CourseChampException e = assertThrows(CourseChampException.class, () -> {
            courseService.deleteCourse(courseCode);
        });

        assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        assertEquals("This course cannot be removed as it is a prerequisite.", e.getMessage());
    }
}