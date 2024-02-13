package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse428.CourseChamp.DummyRepo;
import ca.mcgill.ecse428.CourseChamp.controller.AccountController;
import ca.mcgill.ecse428.CourseChamp.model.Account;
import ca.mcgill.ecse428.CourseChamp.repository.AccountRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginStepDefinition {
    @Autowired
    AccountRepository accountRepository;

    private String requestResponse;
    
    //=-=-=-=-=-=-=-=-=-=-=-=- GIVEN -=-=-=-=-=-=-=-=-=-=-=-=//
    @Given("an account in the system has the email {string}, username {string} and password {string}")
    public void NoAccountWithEmailAndUsernameInSystem(String string, String string2, String string3) {
        Account account = new Account(string,string2, string3);
        accountRepository.save(account);
    }
    //=-=-=-=-=-=-=-=-=-=-=-=- GIVEN -=-=-=-=-=-=-=-=-=-=-=-=//


    //=-=-=-=-=-=-=-=-=-=-=-=- WHEN -=-=-=-=-=-=-=-=-=-=-=-=//
    @When("a user attempts to log in with email {string} and password {string}")
    public void LoginUserStepDefinition(String string, String string2) {
        requestResponse = AccountController.LoginUser(string, string2);
    }
    //=-=-=-=-=-=-=-=-=-=-=-=- WHEN -=-=-=-=-=-=-=-=-=-=-=-=//


    //=-=-=-=-=-=-=-=-=-=-=-=- THEN -=-=-=-=-=-=-=-=-=-=-=-=//
    @Then("the user shall successfully login into the system with the account with the username {string}")
    public void CheckIfUserIsLoggedIn(String string) {
        assertEquals(string, requestResponse);
    }
    //=-=-=-=-=-=-=-=-=-=-=-=- THEN -=-=-=-=-=-=-=-=-=-=-=-=//
}
