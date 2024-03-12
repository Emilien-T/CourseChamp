package ca.mcgill.ecse428.CourseChamp.StepDefinitions;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse428.CourseChamp.dto.ReviewRequestDto;
import ca.mcgill.ecse428.CourseChamp.dto.ReviewResponseDto;
import ca.mcgill.ecse428.CourseChamp.model.Review;
import ca.mcgill.ecse428.CourseChamp.repository.ReviewRepository;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.*;

public class ManageReviewStepDefinition {


    @Autowired
    private ReviewRepository reviewRepository;


    @Autowired
    private TestRestTemplate client;

    private ResponseEntity<ReviewResponseDto> response;
    private ResponseEntity<String> responseError;



    @When("the student {string} attempts to change the comment of the review {string} to {string}")
    public void the_student_attempts_to_change_the_comment_of_the_review_to(String string, String string2, String string3) {
        
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();

        reviewRequestDto.setText(string3);
        reviewRequestDto.setStudentEmail(string);

        HttpEntity<ReviewRequestDto> requestEntity = new HttpEntity<>(reviewRequestDto);
        response = client.exchange("/review/" + string2, HttpMethod.PUT, requestEntity, ReviewResponseDto.class);
    }

    @Then("the review {string} shall have a new comment {string}")
    public void the_review_shall_have_a_new_comment(String string, String string2) {
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(string2, response.getBody().getText());
        assertEquals(Integer.parseInt(string), response.getBody().getId());
    }

    @When("the student {string} unsuccessfully attempts to change the comment of the review {string} to {string}")
    public void the_student_unsuccessfully_attempts_to_change_the_comment_of_the_review_to(String string, String string2, String string3) {
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();

        reviewRequestDto.setText(string3);
        reviewRequestDto.setStudentEmail(string);

        HttpEntity<ReviewRequestDto> requestEntity = new HttpEntity<>(reviewRequestDto);
        responseError = client.exchange("/review/" + string2, HttpMethod.PUT, requestEntity, String.class);
    }

    @Then("the system shall display the {string} to the student after")
    public void the_system_shall_display_the_to_the_student_after(String string) {
        assertNotNull(responseError);
        assertNotNull(responseError.getBody());
        assertEquals(string, responseError.getBody());
    }


    @When("the student {string} attempts to change the rating of the review {string} to {int}")
    public void the_student_attempts_to_change_the_rating_of_the_review_to(String string, String string2, Integer int1) {
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();

        reviewRequestDto.setRating(int1);
        reviewRequestDto.setStudentEmail(string);

        HttpEntity<ReviewRequestDto> requestEntity = new HttpEntity<>(reviewRequestDto);
        response = client.exchange("/review/" + string2, HttpMethod.PUT, requestEntity, ReviewResponseDto.class);
    }


    @Then("the review {string} shall have a new rating {int}")
    public void the_review_shall_have_a_new_rating(String string, Integer int1) {
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(int1, response.getBody().getRating());
        assertEquals(Integer.parseInt(string), response.getBody().getId());
    }


    @When("the student {string} unsuccessfully attempts to change the rating of the review {string} to {int}")
    public void the_student_unsuccessfully_attempts_to_change_the_rating_of_the_review_to(String string, String string2, Integer int1) {
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();

        reviewRequestDto.setRating(int1);
        reviewRequestDto.setStudentEmail(string);

        HttpEntity<ReviewRequestDto> requestEntity = new HttpEntity<>(reviewRequestDto);
        responseError = client.exchange("/review/" + string2, HttpMethod.PUT, requestEntity, String.class);
    }


    @When("the student {string} attempts to change the semester of the review {string} to {string}")
    public void the_student_attempts_to_change_the_semester_of_the_review_to(String string, String string2, String string3) {
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();

        reviewRequestDto.setSemester(string3);
        reviewRequestDto.setStudentEmail(string);

        HttpEntity<ReviewRequestDto> requestEntity = new HttpEntity<>(reviewRequestDto);
        response = client.exchange("/review/" + string2, HttpMethod.PUT, requestEntity, ReviewResponseDto.class);
    }

    @Then("the review {string} shall have a new semester {string}")
    public void the_review_shall_have_a_new_semester(String string, String string2) {
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(string2, response.getBody().getSemester());
        assertEquals(Integer.parseInt(string), response.getBody().getId());
    }


    @When("the student {string} unsuccessfully attempts to change the semester of the review {string} to {string}")
    public void the_student_unsuccessfully_attempts_to_change_the_semester_of_the_review_to(String string, String string2, String string3) {
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();

        reviewRequestDto.setSemester(string3);
        reviewRequestDto.setStudentEmail(string);

        HttpEntity<ReviewRequestDto> requestEntity = new HttpEntity<>(reviewRequestDto);
        responseError = client.exchange("/review/" + string2, HttpMethod.PUT, requestEntity, String.class);
    }


    @When("the student {string} attempts to delete the review {string}")
    public void the_student_attempts_to_delete_the_review(String string, String string2) {
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();

        reviewRequestDto.setStudentEmail(string);

        HttpEntity<ReviewRequestDto> requestEntity = new HttpEntity<>(reviewRequestDto);
        response = client.exchange("/review/" + string2, HttpMethod.DELETE, requestEntity, ReviewResponseDto.class);
    }

    @Then("the review {string} shall no longer exist in the system")
    public void the_review_shall_no_longer_exist_in_the_system(String string) {
        Review review = reviewRepository.findById(Integer.parseInt(string)).get();
        assertNull(review);
    }



    @When("the student {string} unsuccessfully attempts to delete the review {string}")
    public void the_student_unsuccessfully_attempts_to_delete_the_review(String string, String string2) {
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();

        reviewRequestDto.setStudentEmail(string);

        HttpEntity<ReviewRequestDto> requestEntity = new HttpEntity<>(reviewRequestDto);
        responseError = client.exchange("/review/" + string2, HttpMethod.DELETE, requestEntity, String.class);
    }








}
