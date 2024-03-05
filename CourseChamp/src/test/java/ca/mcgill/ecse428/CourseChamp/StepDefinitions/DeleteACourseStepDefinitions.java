package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse428.CourseChamp.dto.CourseResponseDto;
import ca.mcgill.ecse428.CourseChamp.repository.CourseRepository;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeleteACourseStepDefinitions {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TestRestTemplate client;

    private ResponseEntity<CourseResponseDto> response;
    private ResponseEntity<String> responseError;

    @When("the admin attempts to delete a course with the course code {string}")
    public void the_admin_attempts_to_delete_a_course_with_the_course_code(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the system should confirm the successful deletion")
    public void the_system_should_confirm_the_successful_deletion() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the course with course code {string} should not exist in the course pool")
    public void the_course_with_course_code_should_not_exist_in_the_course_pool(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("there shouldn't be any course offering with the course code {string} in the system")
    public void there_shouldn_t_be_any_course_offering_with_the_course_code_in_the_system(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the system should display the error message for unsuccessful deletion {string}")
    public void the_system_should_display_the_error_message_for_unsuccessful_deletion(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    
    @Then("the course with course code {string} should still exist in the course pool")
    public void the_course_with_course_code_should_still_exist_in_the_course_pool(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("there should still exist all the course offerings associated with the course code {string} in the system")
    public void there_should_still_exist_all_the_course_offerings_associated_with_the_course_code_in_the_system(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
