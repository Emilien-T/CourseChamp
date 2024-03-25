package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.description;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse428.CourseChamp.controller.AdminController;
import ca.mcgill.ecse428.CourseChamp.controller.CourseController;
import ca.mcgill.ecse428.CourseChamp.dto.AdminResponseDto;
import ca.mcgill.ecse428.CourseChamp.dto.CourseRequestDto;
import ca.mcgill.ecse428.CourseChamp.dto.CourseResponseDto;
import ca.mcgill.ecse428.CourseChamp.dto.ReviewRequestDto;
import ca.mcgill.ecse428.CourseChamp.repository.CourseRepository;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UpdateACourseInformationStepDefinitions {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    CourseController courseController;
    @Autowired
    private TestRestTemplate client;

    private ResponseEntity<CourseResponseDto> response;
    private ResponseEntity<String> responseError;

    @When("the admin attempts to update the course {string}, with name {string}, description {string}, and syllabus {string}")
    public void the_admin_attempts_to_update_the_course_with_name_description_and_syllabus(String string,
            String string2, String string3, String string4) {
        CourseRequestDto courseRequest = new CourseRequestDto();
        courseRequest.setName(string2);
        courseRequest.setDescription(string3);
        courseRequest.setSyllabus(string4);

        HttpEntity<CourseRequestDto> requestEntity = new HttpEntity<>(courseRequest);
        response = client.exchange("/course/" + string, HttpMethod.PUT, requestEntity, CourseResponseDto.class);
    }

    @When("the admin unsuccessfully attempts to update the course {string}, with name {string}, description {string}, and syllabus {string}")
    public void the_admin_unsuccessfully_attempts_to_update_the_course_with_name_description_and_syllabus(String string,
            String string2, String string3, String string4) {
        CourseRequestDto courseRequest = new CourseRequestDto();
        courseRequest.setName(string2);
        courseRequest.setDescription(string3);
        courseRequest.setSyllabus(string4);

        HttpEntity<CourseRequestDto> requestEntity = new HttpEntity<>(courseRequest);
        responseError = client.exchange("/course/" + string, HttpMethod.PUT, requestEntity, String.class);
    }

    @Then("the system should confirm the successful update")
    public void the_system_should_confirm_the_successful_update() {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Then("the course with course code {string} should have the name {string}, description {string}, and syllabus {string}")
    public void the_course_with_course_code_should_have_the_name_description_and_syllabus(String string, String string2,
            String string3, String string4) {
        CourseResponseDto updatedCourse = response.getBody();
        assertNotNull(updatedCourse);
        assertEquals(string2, updatedCourse.getName());
        assertEquals(string3, updatedCourse.getDescription());
        assertEquals(string4, updatedCourse.getSyllabus());
    }

    @Then("the system should display the error message for unsuccessful update of a course's information {string}")
    public void the_system_should_display_the_error_message_for_unsuccessful_update_of_a_course_s_information(String string) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(string.trim(), responseError.getBody());
    }

    @SuppressWarnings("null")
    @Then("the course with course code {string} should keep the name {string}, description {string}, and syllabus {string}")
    public void the_course_with_course_code_should_keep_the_name_description_and_syllabus(String string, String string2,
            String string3, String string4) {
        ResponseEntity<CourseResponseDto> course = (ResponseEntity<CourseResponseDto>)courseController.getCourseByCode("/course/"+string);
        assertNotNull(course.getBody());
        assertEquals(string2, course.getBody().getName());
        assertEquals(string3, course.getBody().getDescription());
        assertEquals(string4, course.getBody().getSyllabus());
    }

}
