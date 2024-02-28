package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import ca.mcgill.ecse428.CourseChamp.DummyRepo;
import ca.mcgill.ecse428.CourseChamp.dto.StudentRequestDto;
import ca.mcgill.ecse428.CourseChamp.dto.StudentResponseDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse428.CourseChamp.model.Account;
import ca.mcgill.ecse428.CourseChamp.model.Student;
import ca.mcgill.ecse428.CourseChamp.model.Student.Major;
import ca.mcgill.ecse428.CourseChamp.repository.AccountRepository;
import ca.mcgill.ecse428.CourseChamp.repository.StudentRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreateUserStepDefinition {
  
  @Autowired
  StudentRepository studentRepository;
  @Autowired
  private TestRestTemplate client;

  private ResponseEntity<StudentResponseDto> response;
  private ResponseEntity<String> error;

  //=-=-=-=-=-=-=-=-=-=-=-=- WHEN -=-=-=-=-=-=-=-=-=-=-=-=//
  @When("a new user successfully attempts to register with email {string}, username {string} and password {string}")
  public void SuccessfullyRegisterUserStepDefinition(String string, String string2, String string3) {
    StudentRequestDto request = new StudentRequestDto();
    //Uncommment these 3 lines after AccountRequestDto is implemented
    request.setEmail(string);
    request.setUsername(string2);
    request.setPassword(string3);

    response = client.postForEntity("/student/create", request, StudentResponseDto.class);
  }
  @When("a new user unsuccessfully attempts to register with email {string}, username {string} and password {string}")
  public void UnsuccessfullyRegisterUserStepDefinition(String string, String string2, String string3) {
    StudentRequestDto request = new StudentRequestDto();
    //Uncommment these 3 lines after AccountRequestDto is implemented
    request.setEmail(string);
    request.setUsername(string2);
    request.setPassword(string3);

    error =  client.postForEntity("/student/create", request, String.class);
  }
  //=-=-=-=-=-=-=-=-=-=-=-=- WHEN -=-=-=-=-=-=-=-=-=-=-=-=//


  //=-=-=-=-=-=-=-=-=-=-=-=- THEN -=-=-=-=-=-=-=-=-=-=-=-=//
  @Then("a user shall exist with email {string}, username {string} and password {string}")
  public void CheckIfUserExists(String string, String string2, String string3) {
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    Student student = studentRepository.findStudentByEmail(string);
    assertEquals(string, student.getEmail());
    assertEquals(string2, student.getUsername());
    assertEquals(string3, student.getPassword());
  }

  @Then("a {string} message is issued")
  public void CheckForErrorMessage(String string) {
    // assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()); //This is only for create operations (will need to modify feature files)
    // assertEquals(string, error.getBody());
    assertTrue(error.getBody().contains(string)); 
  }
  //=-=-=-=-=-=-=-=-=-=-=-=- THEN -=-=-=-=-=-=-=-=-=-=-=-=//
}
