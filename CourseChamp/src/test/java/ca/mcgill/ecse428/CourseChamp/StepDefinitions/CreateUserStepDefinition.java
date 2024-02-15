package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import ca.mcgill.ecse428.CourseChamp.DummyRepo;
import ca.mcgill.ecse428.CourseChamp.controller.AccountController;
import ca.mcgill.ecse428.CourseChamp.dto.AccountRequestDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse428.CourseChamp.model.Account;
import ca.mcgill.ecse428.CourseChamp.repository.AccountRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreateUserStepDefinition {
  
  @Autowired
  AccountRepository accountRepository;
  @Autowired
  private TestRestTemplate client;

  private ResponseEntity<AccountRequestDto> response;

  //=-=-=-=-=-=-=-=-=-=-=-=- GIVEN -=-=-=-=-=-=-=-=-=-=-=-=//
  @Given("no account in the system has the email {string} and username {string}")
  public void NoAccountWithEmailAndUsernameInSystem(String string, String string2) {
    //Check if an account already exists
    assertEquals(string, DummyRepo.GetFromSystem("email"));
    assertEquals(string, DummyRepo.GetFromSystem("username"));
  }

  @Given("an account in the system has the email {string}")
  public void AnAccountWithEmailExistInSystem(String string) {
    //Add the account to the system
    Account account = new Account(string,"username", "password");
    accountRepository.save(account);
  }

  @Given("an account in the system has the username {string}")
  public void AnAccountWithUsernameExistInSystem(String string) {
    //Add the account to the system
    Account account = new Account("email@gmail.com",string, "password");
    accountRepository.save(account);
  }
  //=-=-=-=-=-=-=-=-=-=-=-=- GIVEN -=-=-=-=-=-=-=-=-=-=-=-=//


  //=-=-=-=-=-=-=-=-=-=-=-=- WHEN -=-=-=-=-=-=-=-=-=-=-=-=//
  @When("a new user attempts to register with email {string}, username {string} and password {string}")
  public void RegisterUserStepDefinition(String string, String string2, String string3) {
    AccountRequestDto request = new AccountRequestDto();
    //Uncommment these 3 lines after AccountRequestDto is implemented
    // request.setEmail(string);
    // request.setName(string2);
    // request.setPassword(string3);

    response =  client.postForEntity("/employee/create", request, AccountRequestDto.class);
  }
  //=-=-=-=-=-=-=-=-=-=-=-=- WHEN -=-=-=-=-=-=-=-=-=-=-=-=//


  //=-=-=-=-=-=-=-=-=-=-=-=- THEN -=-=-=-=-=-=-=-=-=-=-=-=//
  @Then("a user shall exist with email {string}, username {string} and password {string}")
  public void CheckIfUserExists(String string, String string2, String string3) {
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    Account account = accountRepository.findAccountByEmail(string);
    assertEquals(string, account.getEmail());
    assertEquals(string2, account.getUsername());
    assertEquals(string3, account.getPassword());
  }

  @Then("a {string} message is issued")
  public void CheckForErrorMessage(String string) {
    //assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()); //This is only for create operations (will need to modify feature files)
    assertEquals(string, response.getBody());
    // assertEquals(string, DummyRepo.GetFromSystem("errorMessage"));
  }
  //=-=-=-=-=-=-=-=-=-=-=-=- THEN -=-=-=-=-=-=-=-=-=-=-=-=//
}
