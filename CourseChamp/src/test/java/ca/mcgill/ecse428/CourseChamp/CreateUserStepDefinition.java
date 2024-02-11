package ca.mcgill.ecse428.CourseChamp;

import ca.mcgill.ecse428.CourseChamp.controller.AccountController;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.mcgill.ecse428.CourseChamp.model.Account;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreateUserStepDefinition {

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
    Account account = new Account(string, "password");
    DummyRepo.SaveInSystem(account);
  }

  @Given("an account in the system has the username {string}")
  public void AnAccountWithUsernameExistInSystem(String string) {
    //Add the account to the system
    Account account = new Account(string, "password");
    DummyRepo.SaveInSystem(account);
  }
  //=-=-=-=-=-=-=-=-=-=-=-=- GIVEN -=-=-=-=-=-=-=-=-=-=-=-=//


  //=-=-=-=-=-=-=-=-=-=-=-=- WHEN -=-=-=-=-=-=-=-=-=-=-=-=//
  @When("a new user attempts to register with email {string}, username {string} and password {string}")
  public void RegisterUserStepDefinition(String string, String string2, String string3) {
    AccountController.CreateUser(string, string2, string3);
  }
  //=-=-=-=-=-=-=-=-=-=-=-=- WHEN -=-=-=-=-=-=-=-=-=-=-=-=//


  //=-=-=-=-=-=-=-=-=-=-=-=- THEN -=-=-=-=-=-=-=-=-=-=-=-=//
  @Then("a user shall exist with email {string}, username {string} and password {string}")
  public void CheckIfUserExists(String string, String string2, String string3) {
    assertEquals(string, DummyRepo.GetFromSystem("email"));
    assertEquals(string, DummyRepo.GetFromSystem("username"));
    assertEquals(string, DummyRepo.GetFromSystem("password"));
  }

  @Then("a {string} message is issued")
  public void CheckForErrorMessage(String string) {
    assertEquals(string, DummyRepo.GetFromSystem("errorMessage"));
  }
  //=-=-=-=-=-=-=-=-=-=-=-=- THEN -=-=-=-=-=-=-=-=-=-=-=-=//
}
