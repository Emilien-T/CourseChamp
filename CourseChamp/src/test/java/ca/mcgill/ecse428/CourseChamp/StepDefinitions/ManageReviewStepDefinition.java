package ca.mcgill.ecse428.CourseChamp.StepDefinitions;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse428.CourseChamp.dto.ReviewRequestDto;
import ca.mcgill.ecse428.CourseChamp.dto.ReviewResponseDto;
import ca.mcgill.ecse428.CourseChamp.exception.CourseChampException;
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

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class ManageReviewStepDefinition {


    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    VoteRepository voteRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseOfferingRepository courseOfferingRepository;


    @Autowired
    private TestRestTemplate client;

    private ResponseEntity<ReviewResponseDto> response;
    private ResponseEntity<String> responseError;

    private Map<Integer, Integer> fakeToRealIdMap = new HashMap<>();

    private CourseChampException exception;


    @Given("the following reviews currently exist in the system:")
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

    @When("the student {string} attempts to change the comment of the review {string} to {string}")
    public void the_student_attempts_to_change_the_comment_of_the_review_to(String string, String string2, String string3) {
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();
        int oldFakeId = fakeToRealIdMap.get(Integer.parseInt(string2));
        Review review = reviewRepository.findReviewById(oldFakeId);
        reviewRequestDto.setText(string3);
        reviewRequestDto.setStudentEmail(string);
        reviewRequestDto.setRating(review.getRating());
        reviewRequestDto.setCourseCode(review.getCourseOffering().getCourse().getCourseCode());
        reviewRequestDto.setSemester(review.getCourseOffering().getSemester());


        HttpEntity<ReviewRequestDto> requestEntity = new HttpEntity<>(reviewRequestDto);
        
        response = client.exchange("/review/" + oldFakeId, HttpMethod.PUT, requestEntity, ReviewResponseDto.class);
        fakeToRealIdMap.put(oldFakeId, response.getBody().getId());
    }

    @Then("the review {string} shall have a new comment {string}")
    public void the_review_shall_have_a_new_comment(String string, String string2) {
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(string2, response.getBody().getText());
        assertEquals(fakeToRealIdMap.get(Integer.parseInt(string)), response.getBody().getId());
    }

    @When("the student {string} unsuccessfully attempts to change the comment of the review {string} to {string}")
    public void the_student_unsuccessfully_attempts_to_change_the_comment_of_the_review_to(String string, String string2, String string3) {
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();
        int oldFakeId = fakeToRealIdMap.get(Integer.parseInt(string2));
        Review review = reviewRepository.findReviewById(oldFakeId);
        reviewRequestDto.setText(string3);
        reviewRequestDto.setStudentEmail(string);
        reviewRequestDto.setCourseCode(review.getCourseOffering().getCourse().getCourseCode());
        reviewRequestDto.setSemester(review.getCourseOffering().getSemester());
        reviewRequestDto.setRating(review.getRating());

        HttpEntity<ReviewRequestDto> requestEntity = new HttpEntity<>(reviewRequestDto);
        responseError = client.exchange("/review/" + oldFakeId, HttpMethod.PUT, requestEntity, String.class);
    }

    @Then("the system shall display the {string} to the student after")
    public void the_system_shall_display_the_to_the_student_after(String string) {
        assertNotNull(responseError);
        assertNotNull(responseError.getBody());
        assertEquals(string, responseError.getBody());
    }


    @When("the student {string} attempts to change the rating of the review {string} to {string}")
    public void the_student_attempts_to_change_the_rating_of_the_review_to(String string, String string2, String string3) {
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();
        int oldFakeId = fakeToRealIdMap.get(Integer.parseInt(string2));
        Review review = reviewRepository.findReviewById(oldFakeId);
        reviewRequestDto.setRating(Integer.parseInt(string3));
        reviewRequestDto.setStudentEmail(string);
        reviewRequestDto.setCourseCode(review.getCourseOffering().getCourse().getCourseCode());
        reviewRequestDto.setSemester(review.getCourseOffering().getSemester());
        reviewRequestDto.setText(review.getText());

        HttpEntity<ReviewRequestDto> requestEntity = new HttpEntity<>(reviewRequestDto);
        response = client.exchange("/review/" + oldFakeId, HttpMethod.PUT, requestEntity, ReviewResponseDto.class);
        fakeToRealIdMap.put(oldFakeId, response.getBody().getId());
    }


    @Then("the review {string} shall have a new rating {string}")
    public void the_review_shall_have_a_new_rating(String string, String string2) {
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(Integer.parseInt(string2), (Integer) response.getBody().getRating());
        assertEquals(fakeToRealIdMap.get(Integer.parseInt(string)), response.getBody().getId());
    }


    @When("the student {string} unsuccessfully attempts to change the rating of the review {string} to {string}")
    public void the_student_unsuccessfully_attempts_to_change_the_rating_of_the_review_to(String string, String string2, String string3) {
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();
        int oldFakeId = fakeToRealIdMap.get(Integer.parseInt(string2));
        Review review = reviewRepository.findReviewById(oldFakeId);
        reviewRequestDto.setRating(Integer.parseInt(string3));
        reviewRequestDto.setStudentEmail(string);
        reviewRequestDto.setCourseCode(review.getCourseOffering().getCourse().getCourseCode());
        reviewRequestDto.setSemester(review.getCourseOffering().getSemester());
        reviewRequestDto.setText(review.getText());

        HttpEntity<ReviewRequestDto> requestEntity = new HttpEntity<>(reviewRequestDto);
        responseError = client.exchange("/review/" + oldFakeId, HttpMethod.PUT, requestEntity, String.class);
    }


    @When("the student {string} attempts to change the semester of the review {string} to {string}")
    public void the_student_attempts_to_change_the_semester_of_the_review_to(String string, String string2, String string3) {
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();
        int oldFakeId = fakeToRealIdMap.get(Integer.parseInt(string2));
        Review review = reviewRepository.findReviewById(oldFakeId);
        reviewRequestDto.setSemester(string3);
        reviewRequestDto.setStudentEmail(string);
        reviewRequestDto.setCourseCode(review.getCourseOffering().getCourse().getCourseCode());
        reviewRequestDto.setRating(review.getRating());
        reviewRequestDto.setText(review.getText());

        HttpEntity<ReviewRequestDto> requestEntity = new HttpEntity<>(reviewRequestDto);
        
        response = client.exchange("/review/" + oldFakeId, HttpMethod.PUT, requestEntity, ReviewResponseDto.class);
        fakeToRealIdMap.put(oldFakeId, response.getBody().getId());
    }

    @Then("the review {string} shall have a new semester {string}")
    public void the_review_shall_have_a_new_semester(String string, String string2) {
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(string2, response.getBody().getSemester());
        assertEquals(fakeToRealIdMap.get(Integer.parseInt(string)), response.getBody().getId());
    }


    @When("the student {string} unsuccessfully attempts to change the semester of the review {string} to {string}")
    public void the_student_unsuccessfully_attempts_to_change_the_semester_of_the_review_to(String string, String string2, String string3) {
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();
        int oldFakeId = fakeToRealIdMap.get(Integer.parseInt(string2));
        Review review = reviewRepository.findReviewById(oldFakeId);
        reviewRequestDto.setSemester(string3);
        reviewRequestDto.setStudentEmail(string);
        reviewRequestDto.setCourseCode(review.getCourseOffering().getCourse().getCourseCode());
        reviewRequestDto.setRating(review.getRating());
        reviewRequestDto.setText(review.getText());


        HttpEntity<ReviewRequestDto> requestEntity = new HttpEntity<>(reviewRequestDto);
        responseError = client.exchange("/review/" + oldFakeId, HttpMethod.PUT, requestEntity, String.class);
    }


    @When("the student {string} attempts to delete the review {string}")
    public void the_student_attempts_to_delete_the_review(String string, String string2) {
        int oldFakeId = fakeToRealIdMap.get(Integer.parseInt(string2));
        HttpEntity<String> requestEntity = new HttpEntity<>(null);

        client.exchange("/review/" + oldFakeId, HttpMethod.DELETE, requestEntity, String.class);
    }

    @Then("the review {string} shall no longer exist in the system")
    public void the_review_shall_no_longer_exist_in_the_system(String string) {
        Optional<Review> review = reviewRepository.findById(fakeToRealIdMap.get(Integer.parseInt(string)));
        assertTrue(review.isEmpty());
    }



    @When("the student {string} unsuccessfully attempts to delete the review {string}")
    public void the_student_unsuccessfully_attempts_to_delete_the_review(String string, String string2) {
        int oldFakeId = Integer.parseInt(string2);
        HttpEntity<String> requestEntity = new HttpEntity<>(null);

        responseError = client.exchange("/review/" + oldFakeId, HttpMethod.DELETE, requestEntity, String.class);
    }








}
