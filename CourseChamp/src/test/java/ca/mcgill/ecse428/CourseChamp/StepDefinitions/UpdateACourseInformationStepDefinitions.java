package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse428.CourseChamp.dto.CourseResponseDto;
import ca.mcgill.ecse428.CourseChamp.repository.CourseRepository;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UpdateACourseInformationStepDefinitions {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TestRestTemplate client;

    private ResponseEntity<CourseResponseDto> response;
    private ResponseEntity<String> responseError;


    @When("the admin attempts to update the course {string}, with name {string}, description {string}, and syllabus {string}")
    public void the_admin_attempts_to_update_the_course_with_name_description_and_syllabus(String courseCode, String name, String description, String syllabus) {
        // Create a course request DTO
        CourseRequestDto requestDto = new CourseRequestDto();
        requestDto.setName(name);
        requestDto.setDescription(description);
        requestDto.setSyllabus(syllabus);
        
        // Make a request to update the course
        ResponseEntity<CourseResponseDto> response = client.exchange("/course/" + courseCode, HttpMethod.PUT, new HttpEntity<>(requestDto), CourseResponseDto.class);
    }
    @Then("the system should confirm the successful update")
    public void the_system_should_confirm_the_successful_update() {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Then("the course with course code {string} should have the name {string}, description {string}, and syllabus {string}")
    public void the_course_with_course_code_should_have_the_name_description_and_syllabus(String courseCode, String expectedName, String expectedDescription, String expectedSyllabus) {
        // Retrieve the course details from your database or service
        Course course = courseRepository.findCourseByCourseCode(courseCode);

        // Compare the retrieved course details with the expected values
        assertEquals(expectedName, course.getName());
        assertEquals(expectedDescription, course.getDescription());
        assertEquals(expectedSyllabus, course.getSyllabus());
    }

    @Then("the system should display the error message for unsuccessful update of a course's information {string}")
    public void the_system_should_display_the_error_message_for_unsuccessful_update_of_a_course_s_information(String string) {
        assertTrue(response.getBody().contains(string)); 
    }

    
}
