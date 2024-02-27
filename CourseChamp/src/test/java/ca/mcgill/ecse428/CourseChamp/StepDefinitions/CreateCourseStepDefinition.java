package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import ca.mcgill.ecse428.CourseChamp.repository.AdminRepository;
import ca.mcgill.ecse428.CourseChamp.repository.CourseRepository;
import ca.mcgill.ecse428.CourseChamp.dto.CourseRequestDto;
import ca.mcgill.ecse428.CourseChamp.dto.CourseResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;

public class CreateCourseStepDefinition {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private TestRestTemplate client;

    private ResponseEntity<CourseResponseDto> response;

    @When("the admin adds a course with Department abbreviation {string} course number {string} course name {string}, and course description {string}")
    public void the_admin_adds_a_course_with_department_abbreviation_course_number_course_name_and_course_description(String string, String string2, String string3, String string4) {
        // Create a course request DTO
        CourseRequestDto requestDto = new CourseRequestDto();
        requestDto.setDepartment(string);
        requestDto.setCourseNumber(Integer.parseInt(string2));
        requestDto.setName(string3);
        requestDto.setDescription(string4);
        // Make a request to create the course
        // Not sure why this next line is failing
        // response =  client.postForEntity("/course/create", requestDto, CourseResponseDto.class);

        // Filler lines to debug
          // Make a request to create the course
        response = client.postForEntity("/course/create", requestDto, CourseResponseDto.class);
        
        // Print the response body
        System.out.println("Response body: " + response.getBody());
    }

    @Then("the system should confirm the successful addition")
    public void the_system_should_confirm_the_successful_addition() {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Then("a course with Department abbreviation {string} and course number {string} should exist in the course pool")
    public void a_course_with_department_abbreviation_and_course_number_should_exist_in_the_course_pool(String string, String string2) {
        // Verify that the new course appears in the course pool
        String courseCode = string + string2;
        assertTrue(courseRepository.existsById(courseCode));
        assertEquals(courseRepository.findCourseByCourseCode(courseCode).getCourseCode(), courseCode);
    }

    // @When("no course with Department abbreviation {string} and course number {string} exists in the course pool")
    // public void noCourseWithDepartmentAndCourseNumberExistsInTheCoursePool(String department, String courseNumber) {
    //     // Check if the course exists in the course pool
    //     assertNull(courseRepository.findCourseByCourseCode(department+courseNumber));
    // }

    // @Then("the system should confirm the successful addition")
    // public void theSystemShouldConfirmTheSuccessfulAddition() {
    //     // Verify that the successful addition message is displayed
    //     assertTrue(response.getBody().contains("Course acreated"));
    // }

    // @And("a course with Department abbreviation {string} and course number {string} exists in the course pool")
    // public void theCourseAppearsInTheCoursePool(String department, String courseNumber) {
    //     // Verify that the new course appears in the course pool
    //     assertEquals(HttpStatus.CREATED, response.getStatusCode());
    //     assertNotNull(response.getBody());
    //     courseCode = department + courseNumber;
    //     assertTrue(courseRepository.findCourseByCourseCode(courseCode)!= null);
    //     assertEquals(courseRepository.findCourseByCourseCode(courseCode).getCourseCode(), courseCode);
    // }

    // @Then("the system should display an error message {string}")
    // public void theSystemShouldDisplayAnErrorMessage(String errorMessage) {
    //     // Verify that an error message is displayed
    //     assertEquals(errorMessage, response.getBody());
    // }

    // @Then("the course should not be added to the course pool")
    // public void theCourseShouldNotBeAddedToTheCoursePool() {
    //     // Verify that the course is not added to the course pool
    //     assertNull(courseRepository.findCourseByCourseCode(courseCode));  
    // }

    // @Then("a course with Department abbreviation {string} and course number {string} should exist in the course pool")
    // public void a_course_with_department_abbreviation_and_course_number_should_exist_in_the_course_pool(String string, String string2) {
    //     // Write code here that turns the phrase above into concrete actions
    //     throw new io.cucumber.java.PendingException();
    // }
}
