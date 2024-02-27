package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import ca.mcgill.ecse428.CourseChamp.DummyRepo;
import ca.mcgill.ecse428.CourseChamp.dto.StudentRequestDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

  private ResponseEntity<StudentRequestDto> response;

  //=-=-=-=-=-=-=-=-=-=-=-=- WHEN -=-=-=-=-=-=-=-=-=-=-=-=//
  @When("a new user attempts to register with email {string}, username {string} and password {string}")
  public void RegisterUserStepDefinition(String string, String string2, String string3) {
    StudentRequestDto request = new StudentRequestDto();
    //Uncommment these 3 lines after AccountRequestDto is implemented
    request.setEmail(string);
    request.setUsername(string2);
    request.setPassword(string3);

    response =  client.postForEntity("/student/create", request, StudentRequestDto.class);
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
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()); //This is only for create operations (will need to modify feature files)
    assertEquals(string, response.getBody());
    assertEquals(string, DummyRepo.GetFromSystem("errorMessage"));
  }
  //=-=-=-=-=-=-=-=-=-=-=-=- THEN -=-=-=-=-=-=-=-=-=-=-=-=//
}
