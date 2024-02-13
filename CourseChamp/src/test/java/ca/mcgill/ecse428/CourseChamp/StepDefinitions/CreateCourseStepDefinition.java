package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import ca.mcgill.ecse428.CourseChamp.controller.CourseController;
import ca.mcgill.ecse428.CourseChamp.model.Course;
import ca.mcgill.ecse428.CourseChamp.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;

public class CreateCourseStepDefinition {
    @Autowired
    private CourseRepository courseRepository;
    private ResponseEntity<String> response;
    private String courseCode;

    
    @Given("the admin is logged in and on the {string} page")
    public void the_admin_is_logged_in_and_on_the_page(String string) {
        // Initialize or navigate to the course management page
        // Yet to be defined
        // throw new io.cucumber.java.PendingException();
    }

    @When("no course with Department abbreviation {string} and course number {string} exists in the course pool")
    public void noCourseWithDepartmentAndCourseNumberExistsInTheCoursePool(String department, String courseNumber) {
        // Check if the course exists in the course pool
        assertNull(courseRepository.findCourseByCourseCode(department+courseNumber));
    }

    @When("the admin adds a course with Department abbreviation {string} course number {string} and course name {string}")
    public void theAdminAddsANewCourseWithDepartmentCourseNumberAndCourseDescription(String department, String courseNumber, String name) {
        // Add a new course with provided details
        response = CourseController.addCourse(courseNumber, name, "");
    }

    @Then("the system should confirm the successful addition")
    public void theSystemShouldConfirmTheSuccessfulAddition() {
        // Verify that the successful addition message is displayed
        assertNotNull(response);
        assertTrue(response.getBody().contains("Course added successfully"));
    }

    @And("a course with Department abbreviation {string} and course number {string} exists in the course pool")
    public void theCourseAppearsInTheCoursePool(String department, String courseNumber) {
        // Verify that the new course appears in the course pool
        courseCode = department + courseNumber;
        assertTrue(courseRepository.findCourseByCourseCode(courseCode)!= null);
        assertEquals(courseRepository.findCourseByCourseCode(courseCode).getCourseCode(), courseCode);
    }

    @Then("the system should display an error message {string}")
    public void theSystemShouldDisplayAnErrorMessage(String errorMessage) {
        // Verify that an error message is displayed
        assertNotNull(response);
        assertEquals(errorMessage, response.getBody());
    }

    @Then("the course should not be added to the course pool")
    public void theCourseShouldNotBeAddedToTheCoursePool() {
        // Verify that the course is not added to the course pool
        assertNull(courseRepository.findCourseByCourseCode(courseCode));  
    }


}
