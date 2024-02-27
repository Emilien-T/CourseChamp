package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import ca.mcgill.ecse428.CourseChamp.DummyRepo;
import ca.mcgill.ecse428.CourseChamp.controller.ReviewController;
import ca.mcgill.ecse428.CourseChamp.dto.ReviewRequestDto;
import ca.mcgill.ecse428.CourseChamp.dto.ReviewResponseDto;
import ca.mcgill.ecse428.CourseChamp.exception.CourseChampException;
import ca.mcgill.ecse428.CourseChamp.model.Admin;
import ca.mcgill.ecse428.CourseChamp.model.Course;
import ca.mcgill.ecse428.CourseChamp.model.CourseOffering;
import ca.mcgill.ecse428.CourseChamp.model.Student;
import ca.mcgill.ecse428.CourseChamp.repository.CourseOfferingRepository;
import ca.mcgill.ecse428.CourseChamp.repository.CourseRepository;
import ca.mcgill.ecse428.CourseChamp.repository.StudentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateReviewStepDefinition {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseOfferingRepository courseOfferingRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate client;

    private ReviewRequestDto request;
    private ResponseEntity<ReviewResponseDto> response;
    private ResponseEntity<String> error;
    // =-=-=-=-=-=-=-=-=-=-=-=- GIVEN -=-=-=-=-=-=-=-=-=-=-=-=//
    @Given("the following courses exist in the system:")
    public void the_following_courses_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        for (var row : rows) {
            Course course = new Course(row.get("department"),Integer.valueOf(row.get("courseNumber")), row.get("name"), row.get("description"), "");
            courseRepository.delete(course);
            courseRepository.save(course);
        }
    }
    @Given("the following course offerings exist in the system:")
    public void the_following_course_offerings_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        for (var row : rows) {
            Course course = courseRepository.findCourseByCourseCode(row.get("courseCode"));
            CourseOffering courseOffering = new CourseOffering(row.get("semester"), course);
            courseOfferingRepository.delete(courseOffering);
            courseOfferingRepository.save(courseOffering);
        }
    }

    


    @When("the user {string} attempts to leave a review with the rating {string}, with the content {string}, for the course offering {string} for the course {string}")
    public void the_user_attempts_to_leave_a_review_with_the_rating_with_the_content_for_the_course_offering_for_the_course(String string, String string2, String string3, String string4, String string5) {
        // Write code here that turns the phrase above into concrete actions
        request = new ReviewRequestDto();
        request.setStudentEmail(string);
        request.setRating(Integer.valueOf(string2));
        request.setText(string3);
        request.setSemester(string4);

        request.setCourseCode(string5);
        response = client.postForEntity("/review/create", request, ReviewResponseDto.class);
    }

    @When("the user {string} unsuccessfully attempts to leave a review with the rating {string}, with the content {string}, for the course offering {string} for the course {string}")
    public void the_user_unsuccessfully_attempts_to_leave_a_review_with_the_rating_with_the_content_for_the_course_offering_for_the_course(String string, String string2, String string3, String string4, String string5) {
        // Write code here that turns the phrase above into concrete actions
        request = new ReviewRequestDto();
        request.setStudentEmail(string);
        request.setRating(Integer.valueOf(string2));
        request.setText(string3);
        request.setSemester(string4);

        request.setCourseCode(string5);
        error = client.postForEntity("/review/create", request, String.class);
    }
    @Then("the system shall contain a review with a unique ID, username {string}, rating {string} , content {string} and course {string}")
    public void the_system_shall_contain_a_review_with_a_unique_id_username_rating_content_and_course(String string, String string2, String string3, String string4) {
        // Write code here that turns the phrase above into concrete actions
        //assertEquals(request.getRating(), response.getBody().getRating());
        assertEquals(request.getText(), response.getBody().getText());
    }

    @Then("a {string} error message is issued")
    public void a_message_is_issued(String string) {
        // Write code here that turns the phrase above into concrete actions
        //assertEquals(request.getRating(), response.getBody().getRating());
        assertEquals(string.trim(), error.getBody().trim());
    }


}
