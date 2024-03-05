package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse428.CourseChamp.dto.CourseResponseDto;
import ca.mcgill.ecse428.CourseChamp.repository.CourseRepository;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UpdateACourseInformationStepDefinitions {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TestRestTemplate client;

    private ResponseEntity<CourseResponseDto> response;
    private ResponseEntity<String> responseError;

    @When("the admin attempts to update the course {string}, with name {string}, description {string}, and syllabus {string}")
    public void the_admin_attempts_to_update_the_course_with_name_description_and_syllabus(String string, String string2, String string3, String string4) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the system should confirm the successful update")
    public void the_system_should_confirm_the_successful_update() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the course with course code {string} should have the name {string}, description {string}, and syllabus {string}")
    public void the_course_with_course_code_should_have_the_name_description_and_syllabus(String string, String string2, String string3, String string4) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the system should display the error message for unsuccessful update of a course's information {string}")
    public void the_system_should_display_the_error_message_for_unsuccessful_update_of_a_course_s_information(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    
}
