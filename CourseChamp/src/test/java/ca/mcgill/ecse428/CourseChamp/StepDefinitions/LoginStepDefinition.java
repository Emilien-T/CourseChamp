package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse428.CourseChamp.controller.AdminController;
import ca.mcgill.ecse428.CourseChamp.controller.LoginController;
import ca.mcgill.ecse428.CourseChamp.controller.StudentController;
import ca.mcgill.ecse428.CourseChamp.dto.AdminResponseDto;
import ca.mcgill.ecse428.CourseChamp.dto.LoginDto;
import ca.mcgill.ecse428.CourseChamp.dto.StudentResponseDto;
import ca.mcgill.ecse428.CourseChamp.exception.CourseChampException;
import ca.mcgill.ecse428.CourseChamp.model.Admin;
import ca.mcgill.ecse428.CourseChamp.model.Student;
import ca.mcgill.ecse428.CourseChamp.repository.AdminRepository;
import ca.mcgill.ecse428.CourseChamp.repository.StudentRepository;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginStepDefinition {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    StudentController studentController;
    @Autowired
    AdminController adminController;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    LoginController loginController;
    private LoginDto request;
    private ResponseEntity<StudentResponseDto> studentResponse;
    private ResponseEntity<AdminResponseDto> adminResponse;
    private CourseChampException exception;
    
    //=-=-=-=-=-=-=-=-=-=-=-=- GIVEN -=-=-=-=-=-=-=-=-=-=-=-=//
    @Given("the following admins exist in the system:")
    public void the_following_admins_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        for (var row : rows) {
            Admin admin = new Admin(row.get("email"),row.get("username"), row.get("password"));
            adminRepository.delete(admin);
            adminRepository.save(admin);
        }
    }
    //=-=-=-=-=-=-=-=-=-=-=-=- GIVEN -=-=-=-=-=-=-=-=-=-=-=-=//
    //=-=-=-=-=-=-=-=-=-=-=-=- GIVEN -=-=-=-=-=-=-=-=-=-=-=-=//
    @Given("the following students exist in the system:")
    public void the_following_students_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        for (var row : rows) {
            Student.Major major = Student.Major.Software;
            String majorString = row.get("major");
            if(majorString.equals("Computer")){
                major = Student.Major.Computer;
            }
            if(majorString.equals("Electrical")){
                major = Student.Major.Electrical;
            }
            Student student = new Student(row.get("email"),row.get("username"), row.get("password"), major);
            studentRepository.delete(student);
            studentRepository.save(student);
        }
    }

    //=-=-=-=-=-=-=-=-=-=-=-=- WHEN -=-=-=-=-=-=-=-=-=-=-=-=//
    @When("an admin attempts to log in with email {string} and password {string}")
    public void an_admin_attempts_to_log_in_with_email_and_password(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        request = new LoginDto();
        request.setEmail(string);
        request.setPassword(string2);

        adminResponse = (ResponseEntity<AdminResponseDto>)loginController.LoginUser("Admin", request);
    }


    @When("a student attempts to log in with email {string} and password {string}")
    public void a_student_attempts_to_log_in_with_email_and_password(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        request = new LoginDto();
        request.setEmail(string);
        request.setPassword(string2);

        studentResponse = (ResponseEntity<StudentResponseDto>)loginController.LoginUser("student", request);
    }


    @When("an admin unsuccessfully attempts to log in with email {string} and password {string}")
    public void an_admin_unsuccessfully_attempts_to_log_in_with_email_and_password(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        // requestResponse = AccountController.LoginUser(string, string2);
        request = new LoginDto();
        request.setEmail(string);
        request.setPassword(string2);

        exception = assertThrows(CourseChampException.class, () -> loginController.LoginUser("Admin", request));
    }

    @When("a student unsuccessfully attempts to log in with email {string} and password {string}")
    public void a_student_unsuccessfully_attempts_to_log_in_with_email_and_password(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        // requestResponse = AccountController.LoginUser(string, string2);
        request = new LoginDto();
        request.setEmail(string);
        request.setPassword(string2);

        exception = assertThrows(CourseChampException.class, () -> loginController.LoginUser("student", request));
    }

    @When("a student attempts to log in with username {string} and password {string}")
    public void a_student_attempts_to_log_in_with_username_and_password(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        request = new LoginDto();
        request.setEmail(string);
        request.setPassword(string2);

        studentResponse = (ResponseEntity<StudentResponseDto>)loginController.LoginUser("student", request);
    }

    @When("an admin attempts to log in with username {string} and password {string}")
    public void an_admin_attempts_to_log_in_with_username_and_password(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        request = new LoginDto();
        request.setEmail(string);
        request.setPassword(string2);

        adminResponse = (ResponseEntity<AdminResponseDto>)loginController.LoginUser("Admin", request);
    }

    @When("a user unsuccessfully attempts to log in with email {string} and password {string}")
    public void a_user_unsuccessfully_attempts_to_log_in_with_email_and_password(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        request = new LoginDto();
        request.setEmail(string);
        request.setPassword(string2);

        exception = assertThrows(CourseChampException.class, () ->
        loginController.LoginUser("student", request));
    }

    @Then("the admin shall successfully login into the system with the account with the email {string}")
    public void the_admin_shall_successfully_login_into_the_system_with_the_account_with_the_email(String string) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(string, adminResponse.getBody().getEmail());
        assertEquals(request.getPassword(), adminResponse.getBody().getPassword());
    }

    @Then("the student shall successfully login into the system with the account with the email {string}")
    public void the_student_shall_successfully_login_into_the_system_with_the_account_with_the_email(String string) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(string, studentResponse.getBody().getEmail());
        assertEquals(request.getPassword(), studentResponse.getBody().getPassword());
    }


    @Then("the message {string} is issued by the system")
    public void the_message_is_issued_by_the_system(String string) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(exception.getMessage(), string);
    }
}
