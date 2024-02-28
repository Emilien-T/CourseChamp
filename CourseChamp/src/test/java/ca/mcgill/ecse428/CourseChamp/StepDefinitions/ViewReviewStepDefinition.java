package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse428.CourseChamp.dto.ReviewResponseDto;

import ca.mcgill.ecse428.CourseChamp.dto.StudentResponseDto;
import ca.mcgill.ecse428.CourseChamp.model.CourseOffering;
import ca.mcgill.ecse428.CourseChamp.model.Review;
import ca.mcgill.ecse428.CourseChamp.model.Student;
import ca.mcgill.ecse428.CourseChamp.model.Vote;
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

    @Autowired
    private TestRestTemplate client;
    private ResponseEntity<ReviewResponseDto> response;
    private ResponseEntity<String> stringResponse;
    private ResponseEntity<String> error;
    private ResponseEntity<List> responseList;
    
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

    @Given("the following votes exist in the system:")
  public void the_following_votes_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for(var row : rows){
      Student student = studentRepository.findStudentByEmail(row.get("email"));
      Review review = reviewRepository.findReviewById(fakeToRealIdMap.get(Integer.parseInt(row.get("reviewId"))));
      Vote vote = new Vote();
      vote.setType((row.get("type")).equals("upvote"));
      vote.setStudent(student);
      vote.setReview(review);
      voteRepository.save(vote);
    }
  }

    @Given("the user {string} has not upvoted the review with id {string}")
    public void the_user_has_not_upvoted_the_review_with_id(String string, String string2) {
        Student student = studentRepository.findById(string).get();
        assertNotNull(student);
        Review review = reviewRepository.findById(fakeToRealIdMap.get(Integer.parseInt(string2))).get();
        assertNotNull(review);
        
        Vote vote = voteRepository.findVoteByReviewAndStudentNamedParams(review,student);
        assertNull(vote);     
    }

    @When("the user {string} selects the option to upvote a review with the id {string}")
    public void the_user_selects_the_option_to_upvote_a_review_with_the_id(String string, String string2) {

        response = client.postForEntity("/upvote/?email=" + string + "&id=" + fakeToRealIdMap.get(Integer.parseInt(string2)), null, ReviewResponseDto.class);
    }
    
    @Then("the review should display as {string}, {string}, {string}, {string}, {string}")
    public void the_review_should_display_as(String string, String string2, String string3, String string4, String string5) {
        assertEquals(string, response.getBody().getCourseCode());
        assertEquals(Integer.parseInt(string2), response.getBody().getRating());
        assertEquals(string3, response.getBody().getText());
        assertEquals(Integer.parseInt(string4), response.getBody().getUpvotes());
        assertEquals(Integer.parseInt(string5), response.getBody().getDownvotes());
    }
    
    @When("the user attempts to view reviews for the course {string}")
    public void the_user_attempts_to_view_reviews_for_the_course(String string) {
        responseList = client.getForEntity("/getreviews/" + string, List.class);
    }

    @Then("the user should display the following reviews {string} with the semesters {string}, ratings {string}, upvotes {string}, and downvotes {string}")
    public void the_user_should_display_the_following_reviews_with_the_ratings_upvotes_and_downvotes(String string, String string2, String string3, String string4, String string5) {
        // Write code here that turns the phrase above into concrete actions
        String[] semesters = string.split(",");
        String[] reviewContents = string2.split(",");
        String[] ratings = string3.split(",");
        String[] upvotes = string4.split(",");
        String[] downvotes = string5.split(",");

        List<Map<String, Object>> responseBody = responseList.getBody();
        for(Map<String, Object> map : responseBody){
            for(int i = 0; i < reviewContents.length; i++){
                if(reviewContents[i].equals(map.get("text"))){
                    assertEquals(Integer.parseInt(ratings[i]), map.get("rating"));
                    assertEquals(Integer.parseInt(upvotes[i]), map.get("upvotes"));
                    assertEquals(semesters[i], map.get("semester"));
                    assertNotEquals(null, map.get("courseCode"));
                    assertEquals(Integer.parseInt(downvotes[i]), map.get("downvotes"));
                    break;
                }
            }
        }
    }

    @When("the user {string} unsuccessfully attempts to view reviews for the course {string}")
    public void the_user_unsuccessfully_attempts_to_view_reviews_for_the_course(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        error = client.getForEntity("/getreviews/" + string, String.class);
    }

    @Then("the system displays the error message {string} to the user")
    public void the_system_displays_the_error_message_to_the_user(String string) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(string.trim(), error.getBody().trim());
    }
    @Given("the user {string} has upvoted the review with id {string}")
    public void the_user_has_upvoted_the_review_with_id(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        response = client.postForEntity("/upvote/?email=" + string + "&id=" + fakeToRealIdMap.get(Integer.parseInt(string2)), null, ReviewResponseDto.class);
    }
    @When("the user {string} selects the option to remove the upvote from the review with the id {string}")
    public void the_user_selects_the_option_to_remove_the_upvote_from_the_review_with_the_id(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        response = client.postForEntity("/upvote/?email=" + string + "&id=" + fakeToRealIdMap.get(Integer.parseInt(string2)), null, ReviewResponseDto.class);
    }
}
