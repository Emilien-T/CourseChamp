package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse428.CourseChamp.DummyRepo;
import ca.mcgill.ecse428.CourseChamp.model.Admin;
import ca.mcgill.ecse428.CourseChamp.model.Course;
import ca.mcgill.ecse428.CourseChamp.model.CourseOffering;
import ca.mcgill.ecse428.CourseChamp.model.Review;
import ca.mcgill.ecse428.CourseChamp.repository.CourseOfferingRepository;
import ca.mcgill.ecse428.CourseChamp.repository.CourseRepository;
import ca.mcgill.ecse428.CourseChamp.repository.ReviewRepository;
import ca.mcgill.ecse428.CourseChamp.repository.StudentRepository;
import ca.mcgill.ecse428.CourseChamp.repository.VoteRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ViewReviewStepDefinition {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseOfferingRepository courseOfferingRepository;

    private Map<Integer, Integer> fakeToRealIdMap = new HashMap<>();
    @Given("the following reviews exist in the system:")
    public void the_following_reviews_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {

        List<Map<String, String>> rows = dataTable.asMaps();
      for(var row : rows){
        Review review = new Review();
        review.setRating(Integer.parseInt(row.get("rating")));
        review.setText(row.get("comment"));
        review.setStudent(studentRepository.findStudentByEmail(row.get("email")));
        Iterable<CourseOffering> courseOfferings = courseOfferingRepository.findAll();
        for(CourseOffering c : courseOfferings ){
            if (c.getSemester().equals(row.get("semester")) && c.getCourse().getCourseCode().equals(row.get("courseCode"))){
                review.setCourseOffering(c);
                break;
            }
            
        }
        review = reviewRepository.save(review);
        fakeToRealIdMap.put(Integer.parseInt(row.get("reviewId")), review.getId());
      }
    }
    @When("the user attempts to view reviews for the course {string}")
    public void the_user_attempts_to_view_reviews_for_the_course(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the user should display the following reviews {string} with the ratings {string}, upvotes {string}, and downvotes {string}")
    public void the_user_should_display_the_following_reviews_with_the_ratings_upvotes_and_downvotes(String string, String string2, String string3, String string4) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("the user {string} unsuccessfully attempts to view reviews for the course {string}")
    public void the_user_unsuccessfully_attempts_to_view_reviews_for_the_course(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the system displays the error message {string} to the user")
    public void the_system_displays_the_error_message_to_the_user(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }



}
