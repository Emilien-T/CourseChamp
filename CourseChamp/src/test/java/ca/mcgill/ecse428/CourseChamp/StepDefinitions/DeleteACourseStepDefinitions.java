package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import ca.mcgill.ecse428.CourseChamp.model.Course;
import ca.mcgill.ecse428.CourseChamp.model.CourseOffering;
import ca.mcgill.ecse428.CourseChamp.repository.CourseOfferingRepository;
import ca.mcgill.ecse428.CourseChamp.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;

import org.apache.catalina.connector.Response;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeleteACourseStepDefinitions {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    @Autowired
    private TestRestTemplate client;

    private ResponseEntity<String> response;
    private ResponseEntity<String> responseError;

    @When("the admin attempts to delete a course with the course code {string}")
    public void the_admin_attempts_to_delete_a_course_with_the_course_code(String courseCode) {
        // Assuming you have a service endpoint set up to handle DELETE requests
        // Since TestRestTemplate.delete() does not return a response, you might need to adjust your approach
        client.delete("/course/delete/" + courseCode);
        // You may need to follow up with a get or another suitable request to verify deletion or capture an error response
        // This is a placeholder to illustrate the point and might need adjustment
        response = client.getForEntity("/course/" + courseCode, String.class);
    }

    @When("the admin unsuccessfully attempts to delete a course with the course code {string}")
    public void the_admin_unsuccessfully_attempts_to_delete_a_course_with_the_course_code(String courseCode) {
        // Assuming you have a service endpoint set up to handle DELETE requests
        // Since TestRestTemplate.delete() does not return a response, you might need to adjust your approach
        responseError = client.exchange("/course/delete/" + courseCode, HttpMethod.DELETE, new HttpEntity<>(null), String.class);
    }


    

    @Then("the system should confirm the successful deletion")
    public void the_system_should_confirm_the_successful_deletion() {
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("Course not found."));
    }

    @Then("the course with course code {string} should not exist in the course pool")
    public void the_course_with_course_code_should_not_exist_in_the_course_pool(String courseCode) {
        Course course = courseRepository.findCourseByCourseCode(courseCode);
        assertNull(course);
    }

    @Then("there shouldn't be any course offering with the course code {string} in the system")
    public void there_shouldn_t_be_any_course_offering_with_the_course_code_in_the_system(String courseCode) {
        Iterable<CourseOffering> courseOfferings = courseOfferingRepository.findAll();
        courseOfferings.forEach(courseOffering -> assertNotEquals(courseCode, courseOffering.getCourse().getCourseCode()));
    }

    @Then("the system should display the error message for unsuccessful deletion {string}")
    public void the_system_should_display_the_error_message_for_unsuccessful_deletion(String errorMessage) {
        assertEquals(HttpStatus.NOT_FOUND, responseError.getStatusCode());
        assertTrue(responseError.getBody().contains(errorMessage));
    }

    @Then("the system should display the error message for unsuccessful prerequisite deletion {string}")
    public void the_system_should_display_the_error_message_for_unsuccessful_prerequisite_deletion(String errorMessage) {
        assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());
        assertTrue(responseError.getBody().contains(errorMessage));
    }

    

    @Then("the course with course code {string} should still exist in the course pool")
    public void the_course_with_course_code_should_still_exist_in_the_course_pool(String courseCode) {
        Course course = courseRepository.findCourseByCourseCode(courseCode);
        assertNotNull(course);
    }

    @Then("there should still exist all the course offerings associated with the course code {string} in the system")
    public void there_should_still_exist_all_the_course_offerings_associated_with_the_course_code_in_the_system(String courseCode) {
        boolean found = false;
        Iterable<CourseOffering> courseOfferings = courseOfferingRepository.findAll();
        for (CourseOffering co : courseOfferings) {
            if (co.getCourse().getCourseCode().equals(courseCode)) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }
}
