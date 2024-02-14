package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import ca.mcgill.ecse428.CourseChamp.DummyRepo;
import ca.mcgill.ecse428.CourseChamp.controller.ReviewController;
import static org.junit.jupiter.api.Assertions.assertEquals;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreateReviewStepDefinition {

    // =-=-=-=-=-=-=-=-=-=-=-=- GIVEN -=-=-=-=-=-=-=-=-=-=-=-=//
    @Given("a user logged in the system has the username {string}")
    public void LoggedInSystemWithUsernameInSystem(String username) {
        assertEquals(username, DummyRepo.GetFromSystem("username"));
    }
    // =-=-=-=-=-=-=-=-=-=-=-=- GIVEN -=-=-=-=-=-=-=-=-=-=-=-=//

    // =-=-=-=-=-=-=-=-=-=-=-=- WHEN -=-=-=-=-=-=-=-=-=-=-=-=//
    @When("a user attempts to leave a review with the rating {string}, with the content {string}, for a course number {string} in the department {string}")
    public void LeaveReviewStepDefinition(String rating, String content, String number, String department) {
        ReviewController.CreateReview(rating, content, number, department);
    }
    // =-=-=-=-=-=-=-=-=-=-=-=- WHEN -=-=-=-=-=-=-=-=-=-=-=-=//

    // =-=-=-=-=-=-=-=-=-=-=-=- THEN -=-=-=-=-=-=-=-=-=-=-=-=//
    @Then("the system shall contain a review with a unique ID, username {string}, rating {string}, content {string} and course {string}")
    public void CheckIfSystemContainsReview(String username, String rating, String content, String course) {
        assertEquals(username, DummyRepo.GetFromSystem("username"));
        assertEquals(rating, DummyRepo.GetFromSystem("rating"));
        assertEquals(content, DummyRepo.GetFromSystem("content"));
        assertEquals(content, DummyRepo.GetFromSystem("course"));
    }

    // @Then("a {string} message is issued")
    // public void CheckForErrorMessage(String message) {
    //     assertEquals(message, DummyRepo.GetFromSystem("errorMessage"));
    // }
    // =-=-=-=-=-=-=-=-=-=-=-=- THEN -=-=-=-=-=-=-=-=-=-=-=-=//

}
