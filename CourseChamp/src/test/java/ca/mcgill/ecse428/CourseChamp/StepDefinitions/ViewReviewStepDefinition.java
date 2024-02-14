package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import ca.mcgill.ecse428.CourseChamp.DummyRepo;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ViewReviewStepDefinition {
    // =-=-=-=-=-=-=-=-=-=-=-=- GIVEN -=-=-=-=-=-=-=-=-=-=-=-=//
    @Given("the user is on the course details page for a specific course {string}")
    public void UserOnCourseDetailsPage(String coursePage) {
        assertEquals(DummyRepo.GetFromSystem("coursePage"), coursePage);
    }
    // =-=-=-=-=-=-=-=-=-=-=-=- GIVEN -=-=-=-=-=-=-=-=-=-=-=-=//

    // =-=-=-=-=-=-=-=-=-=-=-=- WHEN -=-=-=-=-=-=-=-=-=-=-=-=//
    @When("the user navigates to the reviews section")
    public void theUserNavigatesToTheReviewsSection() {
        DummyRepo.SaveInSystem("reviewsPage");
    }

    @When("the user selects the option to upvote a review with the following details: {string}")
    public void theUserSelectsOptionToUpvoteReview(String reviewDetails) {
        DummyRepo.SaveInSystem("upvote:" + reviewDetails);
    }

    @When("the user selects the option to upvote the same review again")
    public void theUserSelectsOptionToUpvoteSameReviewAgain() {
        DummyRepo.SaveInSystem("removeUpvote");
    }

    @When("the user selects the option to downvote a review with the following details: {string}")
    public void theUserSelectsOptionToDownvoteReview(String reviewDetails) {
        DummyRepo.SaveInSystem("downvote:" + reviewDetails);
    }

    @When("the user selects the option to remove downvote a review with the following details: {string}")
    public void theUserSelectsOptionToRemoveDownvoteFromReview(String reviewDetails) {
        DummyRepo.SaveInSystem("removeDownvote" + reviewDetails);
    }
    // =-=-=-=-=-=-=-=-=-=-=-=- WHEN -=-=-=-=-=-=-=-=-=-=-=-=//

    // =-=-=-=-=-=-=-=-=-=-=-=- THEN -=-=-=-=-=-=-=-=-=-=-=-=//
    @Then("the user should see a list of reviews for the course")
    public void theUserShouldSeeAListOfReviewsForTheCourse() {
        // Check DummyRepo to assert the presence of reviews
        String reviews = DummyRepo.GetFromSystem("reviews");
        assertTrue(!reviews.isEmpty()); // Adjust based on how reviews are stored or indicated
    }

    @Then("the system should increment the upvote count for the selected review")
    public void theSystemShouldIncrementTheUpvoteCount() {
        // Assert the upvote action was saved. In a real scenario, check the updated
        // review details.
        assertEquals("upvote", DummyRepo.GetFromSystem("lastAction"));
    }

    @Then("the system should decrement the upvote count for the selected review")
    public void theSystemShouldDecrementTheUpvoteCount() {
        // Assert the remove upvote action was saved. In a real scenario, check the
        // updated review details.
        assertEquals("removeUpvote", DummyRepo.GetFromSystem("lastAction"));
    }

    @Then("the system should increment the downvote count for the selected review")
    public void theSystemShouldIncrementTheDownvoteCount() {
        // Assert the downvote action was saved. In a real scenario, check the updated
        // review details.
        assertEquals("downvote", DummyRepo.GetFromSystem("lastAction"));
    }

    @Then("the system should decrement the downvote count for the selected review")
    public void theSystemShouldDecrementTheDownvoteCount() {
        // Assert the remove downvote action was saved. In a real scenario, check the
        // updated review details.
        assertEquals("removeDownvote", DummyRepo.GetFromSystem("lastAction"));
    }

    @Then("the user should see a message indicating that there are no reviews for this course")
    public void theUserShouldSeeAMessageForNoReviews() {
        // Assert that the message for no reviews is displayed. This could be simulated
        // by checking a flag in DummyRepo.
        String noReviewsMessage = DummyRepo.GetFromSystem("noReviewsMessage");
        assertTrue(noReviewsMessage.contains("no reviews"));
    }
    // =-=-=-=-=-=-=-=-=-=-=-=- THEN -=-=-=-=-=-=-=-=-=-=-=-=//

}
