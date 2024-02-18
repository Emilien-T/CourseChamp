package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import ca.mcgill.ecse428.CourseChamp.controller.CourseController;
import ca.mcgill.ecse428.CourseChamp.repository.CourseRepository;
import ca.mcgill.ecse428.CourseChamp.dto.CourseRequestDto;
import ca.mcgill.ecse428.CourseChamp.dto.CourseResponseDto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;



public class CreateCourseStepDefinition {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TestRestTemplate client;

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

    @When("the admin adds a course with Department abbreviation {string} course number {string} and course name {string}:")
    public void theAdminAddsANewCourseWithDepartmentCourseNumberAndCourseDescription(String department, String courseNumber, String name) {

        // Create a course request DTO
        CourseRequestDto requestDto = new CourseRequestDto();
        requestDto.setDepartment(department);
        requestDto.setCourseNumber(Integer.parseInt(courseNumber));
        requestDto.setName(name);
        // Make a request to create the course
        // Not sure why this next line is failing
        // response =  client.postForEntity("/course/create", requestDto, CourseResponseDto.class);

        // Filler lines to debug
          // Make a request to create the course
        response = client.postForEntity("/course/create", requestDto, String.class);
        
        // Print the response body
        System.out.println("Response body: " + response.getBody());
    
    }

    @Then("the system should confirm the successful addition")
    public void theSystemShouldConfirmTheSuccessfulAddition() {
        // Verify that the successful addition message is displayed
        assertTrue(response.getBody().contains("Course acreated"));
    }

    @And("a course with Department abbreviation {string} and course number {string} exists in the course pool")
    public void theCourseAppearsInTheCoursePool(String department, String courseNumber) {
        // Verify that the new course appears in the course pool
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        courseCode = department + courseNumber;
        assertTrue(courseRepository.findCourseByCourseCode(courseCode)!= null);
        assertEquals(courseRepository.findCourseByCourseCode(courseCode).getCourseCode(), courseCode);
    }

    @Then("the system should display an error message {string}")
    public void theSystemShouldDisplayAnErrorMessage(String errorMessage) {
        // Verify that an error message is displayed
        assertEquals(errorMessage, response.getBody());
    }

    @Then("the course should not be added to the course pool")
    public void theCourseShouldNotBeAddedToTheCoursePool() {
        // Verify that the course is not added to the course pool
        assertNull(courseRepository.findCourseByCourseCode(courseCode));  
    }


}
