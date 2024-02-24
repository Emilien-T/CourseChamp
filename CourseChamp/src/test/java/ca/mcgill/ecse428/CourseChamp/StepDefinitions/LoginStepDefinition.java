package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse428.CourseChamp.controller.LoginController;
import ca.mcgill.ecse428.CourseChamp.controller.StudentController;
import ca.mcgill.ecse428.CourseChamp.dto.LoginDto;
import ca.mcgill.ecse428.CourseChamp.dto.StudentRequestDto;
import ca.mcgill.ecse428.CourseChamp.dto.StudentResponseDto;
import ca.mcgill.ecse428.CourseChamp.exception.CourseChampException;
import ca.mcgill.ecse428.CourseChamp.model.Account;
import ca.mcgill.ecse428.CourseChamp.model.Student;
import ca.mcgill.ecse428.CourseChamp.model.Student.Major;
import ca.mcgill.ecse428.CourseChamp.repository.AccountRepository;
import ca.mcgill.ecse428.CourseChamp.repository.StudentRepository;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginStepDefinition {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StudentController studentController;

    @Autowired
    LoginController loginController;
    private LoginDto request;
    private ResponseEntity<StudentResponseDto> response;
    private CourseChampException exception;
    
    @BeforeEach()
    public void setup(){
        studentRepository.deleteAll();
    }

    @AfterEach()
    public void takedown(){
        studentRepository.deleteAll();
    }
    //=-=-=-=-=-=-=-=-=-=-=-=- GIVEN -=-=-=-=-=-=-=-=-=-=-=-=//
    @Given("the following accounts exist in the system:")
    public void the_following_accounts_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        for (var row : rows) {
                
            studentController.deleteStudent(row.get("email"));
            
            Student student = new Student(row.get("email"),row.get("username"), row.get("password"), Major.Software);
            StudentRequestDto studentRequestDto = new StudentRequestDto();
            studentRequestDto.setEmail(row.get("email"));
            studentRequestDto.setUsername(row.get("username"));
            studentRequestDto.setPassword(row.get("password"));
            studentRequestDto.setMajor(Major.Software);
            studentRepository.save(student);
        }
    }
    //=-=-=-=-=-=-=-=-=-=-=-=- GIVEN -=-=-=-=-=-=-=-=-=-=-=-=//


    //=-=-=-=-=-=-=-=-=-=-=-=- WHEN -=-=-=-=-=-=-=-=-=-=-=-=//
    @When("a user attempts to log in with email {string} and password {string}")
    public void LoginUserStepDefinition(String string, String string2) {
        //TODO: Need to update to new controller classes

        // requestResponse = AccountController.LoginUser(string, string2);
        request = new LoginDto();
        request.setEmail(string);
        request.setPassword(string2);

        response = (ResponseEntity<StudentResponseDto>)loginController.LoginUser("student", request);

        // response =  client.postForEntity("/employee/create", request, AccountRequestDto.class);
    }
    //=-=-=-=-=-=-=-=-=-=-=-=- WHEN -=-=-=-=-=-=-=-=-=-=-=-=//


    //=-=-=-=-=-=-=-=-=-=-=-=- THEN -=-=-=-=-=-=-=-=-=-=-=-=//
    @Then("the user shall successfully login into the system with the account with the email {string}")
    public void the_user_shall_successfully_login_into_the_system_with_the_account_with_the_email(String string) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(string, response.getBody().getEmail());
        assertEquals(request.getPassword(), response.getBody().getPassword());
    }
    //=-=-=-=-=-=-=-=-=-=-=-=- THEN -=-=-=-=-=-=-=-=-=-=-=-=//

    @When("a user attempts to log in with username {string} and password {string}")
    public void a_user_attempts_to_log_in_with_username_and_password(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        request = new LoginDto();
        request.setEmail(string);
        request.setPassword(string2);

        response = (ResponseEntity<StudentResponseDto>)loginController.LoginUser("student", request);
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


    @Then("the message {string} is issued by the system")
    public void the_message_is_issued_by_the_system(String string) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(exception.getMessage(), string);
    }
}
