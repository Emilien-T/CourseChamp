package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;

import ca.mcgill.ecse428.CourseChamp.repository.AdminRepository;
import ca.mcgill.ecse428.CourseChamp.dto.CourseResponseDto;
import ca.mcgill.ecse428.CourseChamp.dto.CourseRequestDto;
import ca.mcgill.ecse428.CourseChamp.repository.CourseRepository;
import ca.mcgill.ecse428.CourseChamp.model.Course;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;



public class UpdateACourseInformationStepDefinitions {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TestRestTemplate client;

    private ResponseEntity<CourseResponseDto> response;
    private ResponseEntity<String> responseError;

    @Given("the following courses are in the system:")
  public void the_following_courses_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for(var row : rows){
      String department = row.get("department");
      int courseNumber = Integer.parseInt(row.get("courseNumber"));
      String name = row.get("name");
      String description = row.get("description");
      String syllabus = row.get("syllabus");
      Course course = new Course(department, courseNumber, name, description, syllabus);
      courseRepository.save(course);
    }
  }

    @When("the admin attempts to update the course {string}, with name {string}, description {string}, and syllabus {string}")
    public void the_admin_attempts_to_update_the_course_with_name_description_and_syllabus(String courseCode, String name, String description, String syllabus) {
        // Create a course request DTO
        CourseRequestDto requestDto = new CourseRequestDto();
        requestDto.setDepartment(courseCode.substring(0, 4));
        requestDto.setCourseNumber(Integer.parseInt(courseCode.substring(4)));
        requestDto.setName(name);
        requestDto.setDescription(description);
        requestDto.setSyllabus(syllabus);

        // response = client.exchange("/course/" + courseCode + "/update/", HttpMethod.PUT, new HttpEntity<>(requestDto), CourseResponseDto.class);
        
        // Make a request to update the course
        try{
            response = client.exchange("/course/" + courseCode + "/update/", HttpMethod.PUT, new HttpEntity<>(requestDto), CourseResponseDto.class);
        }
        catch(Exception e){
            responseError = client.exchange("/course/" + courseCode + "/update/", HttpMethod.PUT, new HttpEntity<>(requestDto), String.class);
        }
       
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
        assertTrue(responseError.getBody().contains(string)); 
    }

    
}