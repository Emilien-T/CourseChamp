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
    public void LoggedInSystemWithUsernameInSystem(String string) {
        assertEquals(string, DummyRepo.GetFromSystem("username"));
    }
    // =-=-=-=-=-=-=-=-=-=-=-=- GIVEN -=-=-=-=-=-=-=-=-=-=-=-=//

    // =-=-=-=-=-=-=-=-=-=-=-=- WHEN -=-=-=-=-=-=-=-=-=-=-=-=//
    @When("a user attempts to leave a review with the rating {string}, with the content {string}, for a course number {string} in the department {string}")
    public void LeaveReviewStepDefinition(String string, String string2, String string3, String string4) {
        ReviewController.CreateReview(string, string2, string3, string4);
    }
    // =-=-=-=-=-=-=-=-=-=-=-=- WHEN -=-=-=-=-=-=-=-=-=-=-=-=//

    // =-=-=-=-=-=-=-=-=-=-=-=- THEN -=-=-=-=-=-=-=-=-=-=-=-=//
    @Then("the system shall contain a review with a unique ID, username {string}, rating {string}, content {string} and course {string}")
    public void CheckIfSystemContainsReview(String string, String string2, String string3, String string4) {
        assertEquals(string, DummyRepo.GetFromSystem("username"));
        assertEquals(string2, DummyRepo.GetFromSystem("rating"));
        assertEquals(string3, DummyRepo.GetFromSystem("content"));
        assertEquals(string4, DummyRepo.GetFromSystem("course"));
    }

    // @Then("a {string} message is issued")
    // public void CheckForErrorMessage(String message) {
    //     assertEquals(message, DummyRepo.GetFromSystem("errorMessage"));
    // }
    // =-=-=-=-=-=-=-=-=-=-=-=- THEN -=-=-=-=-=-=-=-=-=-=-=-=//

}
