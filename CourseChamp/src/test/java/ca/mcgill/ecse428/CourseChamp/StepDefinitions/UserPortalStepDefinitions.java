package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import ca.mcgill.ecse428.CourseChamp.DummyRepo;
import ca.mcgill.ecse428.CourseChamp.controller.AdminController;
import ca.mcgill.ecse428.CourseChamp.controller.LoginController;
import ca.mcgill.ecse428.CourseChamp.controller.ReviewController;
import ca.mcgill.ecse428.CourseChamp.controller.StudentController;
import ca.mcgill.ecse428.CourseChamp.dto.AdminRequestDto;
import ca.mcgill.ecse428.CourseChamp.dto.AdminResponseDto;
import ca.mcgill.ecse428.CourseChamp.dto.LoginDto;
import ca.mcgill.ecse428.CourseChamp.dto.ReviewRequestDto;
import ca.mcgill.ecse428.CourseChamp.dto.ReviewResponseDto;
import ca.mcgill.ecse428.CourseChamp.dto.StudentRequestDto;
import ca.mcgill.ecse428.CourseChamp.dto.StudentResponseDto;
import ca.mcgill.ecse428.CourseChamp.exception.CourseChampException;
import ca.mcgill.ecse428.CourseChamp.model.Admin;
import ca.mcgill.ecse428.CourseChamp.model.Course;
import ca.mcgill.ecse428.CourseChamp.model.CourseOffering;
import ca.mcgill.ecse428.CourseChamp.model.Review;
import ca.mcgill.ecse428.CourseChamp.model.Student;
import ca.mcgill.ecse428.CourseChamp.model.Student.Major;
import ca.mcgill.ecse428.CourseChamp.repository.AdminRepository;
import ca.mcgill.ecse428.CourseChamp.repository.CourseOfferingRepository;
import ca.mcgill.ecse428.CourseChamp.repository.CourseRepository;
import ca.mcgill.ecse428.CourseChamp.repository.ReviewRepository;
import ca.mcgill.ecse428.CourseChamp.repository.StudentRepository;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserPortalStepDefinitions {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseOfferingRepository courseOfferingRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    StudentController studentController;

    @Autowired
    AdminController adminController;

    @Autowired
    private TestRestTemplate client;

    private StudentRequestDto studentRequest;
    private ResponseEntity<StudentResponseDto> studentResponse;

    private AdminRequestDto adminRequest;
    private ResponseEntity<AdminResponseDto> adminResponse;

    private ResponseEntity<List> responseList;

    private ResponseEntity<String> errorMessage;

    
    private ResponseEntity<ReviewResponseDto> reviewResponse;
    private Map<Integer, Integer> fakeToRealIdMap = new HashMap<>();
    @Given("the following reviews are in the system:")
    public void the_following_reviews_are_in_the_system(io.cucumber.datatable.DataTable dataTable) {

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

    @When("the student {string} attempts to change their username to {string}")
    public void the_student_attempts_to_change_their_username(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        ResponseEntity<StudentResponseDto> student = (ResponseEntity<StudentResponseDto>)studentController.getStudentByEmail(string);
        studentRequest = new StudentRequestDto();
        studentRequest.setPassword(student.getBody().getPassword());
        studentRequest.setMajor(student.getBody().getMajor());
        studentRequest.setEmail(string);
        studentRequest.setUsername(string2);
        HttpEntity<StudentRequestDto> requestEntity = new HttpEntity<>(studentRequest);
        studentResponse = client.exchange("/student/update", HttpMethod.PUT, requestEntity, StudentResponseDto.class);
    }

    @When("the student {string} unsuccessfully attempts to change their username to {string}")
    public void the_student_unsuccessfully_attempts_to_change_their_username(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        ResponseEntity<StudentResponseDto> student = (ResponseEntity<StudentResponseDto>)studentController.getStudentByEmail(string);
        studentRequest = new StudentRequestDto();
        studentRequest.setPassword(student.getBody().getPassword());
        studentRequest.setMajor(student.getBody().getMajor());
        studentRequest.setEmail(string);
        studentRequest.setUsername(string2);
        HttpEntity<StudentRequestDto> requestEntity = new HttpEntity<>(studentRequest);
        errorMessage = client.exchange("/student/update", HttpMethod.PUT, requestEntity, String.class);
    }

    @When("the student {string} attempts to change their password to {string}")
    public void the_student_attempts_to_change_their_password(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        ResponseEntity<StudentResponseDto> student = (ResponseEntity<StudentResponseDto>)studentController.getStudentByEmail(string);
        studentRequest = new StudentRequestDto();
        studentRequest.setPassword(string2);
        studentRequest.setEmail(string);
        studentRequest.setMajor(student.getBody().getMajor());
        studentRequest.setUsername(student.getBody().getUsername());

        HttpEntity<StudentRequestDto> requestEntity = new HttpEntity<>(studentRequest);
        studentResponse = client.exchange("/student/update", HttpMethod.PUT, requestEntity, StudentResponseDto.class);
    }

    @When("the student {string} unsuccessfully attempts to change their password to {string}")
    public void the_student_unsuccessfully_attempts_to_change_their_password(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        ResponseEntity<StudentResponseDto> student = (ResponseEntity<StudentResponseDto>)studentController.getStudentByEmail(string);
        studentRequest = new StudentRequestDto();
        studentRequest.setPassword(string2);
        studentRequest.setEmail(string);
        studentRequest.setUsername(student.getBody().getUsername());
        studentRequest.setMajor(student.getBody().getMajor());

        HttpEntity<StudentRequestDto> requestEntity = new HttpEntity<>(studentRequest);
        errorMessage = client.exchange("/student/update", HttpMethod.PUT, requestEntity, String.class);
    }

    @When("the student {string} attempts to change their major to {string}")
    public void the_student_unsuccessfully_attempts_to_change_their_major(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        ResponseEntity<StudentResponseDto> student = (ResponseEntity<StudentResponseDto>)studentController.getStudentByEmail(string);
        studentRequest = new StudentRequestDto();
        studentRequest.setPassword(student.getBody().getPassword());
        studentRequest.setUsername(student.getBody().getUsername());

        if(string2.equals("Electrical")){
            studentRequest.setMajor(Major.Electrical);
        }
        else if(string2.equals("Computer")){
            studentRequest.setMajor(Major.Computer);
        }
        else if(string2.equals("Software")){
            studentRequest.setMajor(Major.Software);
        }

        studentRequest.setEmail(string);
        
        
        HttpEntity<StudentRequestDto> requestEntity = new HttpEntity<>(studentRequest);
        studentResponse = client.exchange("/student/update", HttpMethod.PUT, requestEntity, StudentResponseDto.class);
    }

    @When("the admin {string} attempts to change their username to {string}")
    public void the_admin_attempts_to_change_their_username(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        ResponseEntity<AdminResponseDto> admin = (ResponseEntity<AdminResponseDto>)adminController.getAdminByEmail(string);
        adminRequest = new AdminRequestDto();
        adminRequest.setPassword(admin.getBody().getPassword());
        adminRequest.setEmail(string);
        adminRequest.setUsername(string2);
        HttpEntity<AdminRequestDto> requestEntity = new HttpEntity<>(adminRequest);
        adminResponse = client.exchange("/admin/update", HttpMethod.PUT, requestEntity, AdminResponseDto.class);
    }

    @When("the admin {string} unsuccessfully attempts to change their username to {string}")
    public void the_admin_unsuccessfully_attempts_to_change_their_username(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        ResponseEntity<AdminResponseDto> admin = (ResponseEntity<AdminResponseDto>)adminController.getAdminByEmail(string);
        adminRequest = new AdminRequestDto();
        adminRequest.setPassword(admin.getBody().getPassword());
        adminRequest.setEmail(string);
        adminRequest.setUsername(string2);
        HttpEntity<AdminRequestDto> requestEntity = new HttpEntity<>(adminRequest);
        errorMessage = client.exchange("/admin/update", HttpMethod.PUT, requestEntity, String.class);
    }

    @When("the admin {string} attempts to change their password to {string}")
    public void the_admin_attempts_to_change_their_password(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        ResponseEntity<AdminResponseDto> admin = (ResponseEntity<AdminResponseDto>)adminController.getAdminByEmail(string);
        adminRequest = new AdminRequestDto();
        adminRequest.setPassword(string2);
        adminRequest.setEmail(string);
        adminRequest.setUsername(admin.getBody().getUsername());

        HttpEntity<AdminRequestDto> requestEntity = new HttpEntity<>(adminRequest);
        adminResponse = client.exchange("/admin/update", HttpMethod.PUT, requestEntity, AdminResponseDto.class);
    }

    @When("the admin {string} unsuccessfully attempts to change their password to {string}")
    public void the_admin_unsuccessfully_attempts_to_change_their_password(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        ResponseEntity<AdminResponseDto> admin = (ResponseEntity<AdminResponseDto>)adminController.getAdminByEmail(string);
        adminRequest = new AdminRequestDto();
        adminRequest.setPassword(string2);
        adminRequest.setEmail(string);
        adminRequest.setUsername(admin.getBody().getUsername());

        HttpEntity<AdminRequestDto> requestEntity = new HttpEntity<>(adminRequest);
        errorMessage = client.exchange("/admin/update", HttpMethod.PUT, requestEntity, String.class);
    }

    @When("the student {string} attempts to view their reviews")
    public void the_student_attemps_to_view_their_reviews(String string){
        String username = studentRepository.findStudentByEmail(string).getUsername();
        responseList = client.getForEntity("/getreviewsStudent/" + string, List.class);
    }

    @When("the student {string} unsuccessfully attempts to view their reviews")
    public void the_student_unsuccessfully_attemps_to_view_their_reviews(String string){
        String username = studentRepository.findStudentByEmail(string).getUsername();
        errorMessage = client.getForEntity("/getreviewsStudent/" + string, String.class);
    }

    @Then("the student shall have the new username {string}")
    public void the_student_shall_have_the_new_username(String string){
        assertEquals(string.trim(), studentResponse.getBody().getUsername());
    }

    @Then("the student shall have the new password {string}")
    public void the_student_shall_have_the_new_password(String string){
        assertEquals(string.trim(), studentResponse.getBody().getPassword());
    }

    @Then("the student shall have the new major {string}")
    public void the_student_shall_have_the_new_major(String string){
        Major maj = studentResponse.getBody().getMajor();

        String majString = null;
        if(maj == Major.Electrical){
            majString = "Electrical";
        }
        else if(maj == Major.Computer){
            majString = "Computer";
        }
        else if(maj == Major.Software){
            majString = "Software";
        }
        assertEquals(string.trim(), majString);
    }

    @Then("the admin shall have the new username {string}")
    public void the_admin_shall_have_the_new_username(String string){
        assertEquals(string.trim(), adminResponse.getBody().getUsername().trim());
    }

    @Then("the admin shall have the new password {string}")
    public void the_admin_shall_have_the_new_password(String string){
        assertEquals(string.trim(), adminResponse.getBody().getPassword().trim());
    }

    @Then("the system shall display the error message {string}")
    public void the_system_shall_display_the_error_message(String string){
        assertEquals(string.trim(), errorMessage.getBody().trim());
    }

    @Then("the system shall display the following reviews to the student:")
    public void the_system_shall_display_the_following_reviews_to_the_student(io.cucumber.datatable.DataTable dataTable){
        List<Map<String, String>> rows = (List<Map<String, String>>)dataTable.asMaps();

        List<Map<String, Object>> responseBody = responseList.getBody();
        for(Map<String, Object> map : responseBody){
            for(int i = 0; i < rows.size(); i++){
                if(rows.get(i).get("reviewId").equals(map.get("reviewId"))){
                    assertEquals(rows.get(i).get("courseCode"), map.get("courseCode"));
                    assertEquals(rows.get(i).get("semester"), map.get("semester"));
                    assertEquals(rows.get(i).get("student"), map.get("student"));
                    assertEquals(Integer.parseInt(rows.get(i).get("rating")), map.get("rating"));
                    assertEquals(rows.get(i).get("comment"), map.get("comment"));
                    break;
                }
            }
        }
    }



    


}
