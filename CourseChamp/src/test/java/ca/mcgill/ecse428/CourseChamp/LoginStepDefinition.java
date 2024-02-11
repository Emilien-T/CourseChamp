package ca.mcgill.ecse428.CourseChamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.mcgill.ecse428.CourseChamp.controller.AccountController;
import ca.mcgill.ecse428.CourseChamp.model.Account;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginStepDefinition {
    //=-=-=-=-=-=-=-=-=-=-=-=- GIVEN -=-=-=-=-=-=-=-=-=-=-=-=//
    @Given("an account in the system has the email {string}, username {string} and password {string}")
    public void NoAccountWithEmailAndUsernameInSystem(String string, String string2, String string3) {
        Account account = new Account(string, string3);
        DummyRepo.SaveInSystem(account);
    }
    //=-=-=-=-=-=-=-=-=-=-=-=- GIVEN -=-=-=-=-=-=-=-=-=-=-=-=//


    //=-=-=-=-=-=-=-=-=-=-=-=- WHEN -=-=-=-=-=-=-=-=-=-=-=-=//
    @When("a user attempts to log in with email {string} and password {string}")
    public void LoginUserStepDefinition(String string, String string2) {
        AccountController.LoginUser(string, string2);
    }
    //=-=-=-=-=-=-=-=-=-=-=-=- WHEN -=-=-=-=-=-=-=-=-=-=-=-=//


    //=-=-=-=-=-=-=-=-=-=-=-=- THEN -=-=-=-=-=-=-=-=-=-=-=-=//
    @Then("the user shall successfully login into the system with the account with the username {string}")
    public void CheckIfUserIsLoggedIn(String string) {
        assertEquals(string, DummyRepo.GetFromSystem("username"));
    }
    //=-=-=-=-=-=-=-=-=-=-=-=- THEN -=-=-=-=-=-=-=-=-=-=-=-=//
}
