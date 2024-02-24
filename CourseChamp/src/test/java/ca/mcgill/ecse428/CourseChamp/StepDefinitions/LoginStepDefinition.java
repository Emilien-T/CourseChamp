package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import ca.mcgill.ecse428.CourseChamp.dto.StudentRequestDto;
import ca.mcgill.ecse428.CourseChamp.model.Account;
import ca.mcgill.ecse428.CourseChamp.model.Student;
import ca.mcgill.ecse428.CourseChamp.model.Student.Major;
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
    public void NoAccountWithEmailAndUsernameInSystem(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        for (var row : rows) {
            Student student = new Student(row.get("email"),row.get("username"), row.get("password"), Major.Software);
            accountRepository.save(student);
        }
    }
    //=-=-=-=-=-=-=-=-=-=-=-=- GIVEN -=-=-=-=-=-=-=-=-=-=-=-=//


    //=-=-=-=-=-=-=-=-=-=-=-=- WHEN -=-=-=-=-=-=-=-=-=-=-=-=//
    @When("a user attempts to log in with email {string} and password {string}")
    public void LoginUserStepDefinition(String string, String string2) {
        //TODO: Need to update to new controller classes

        // requestResponse = AccountController.LoginUser(string, string2);
        StudentRequestDto request = new StudentRequestDto();
        //Uncommment these 3 lines after AccountRequestDto is implemented
        // request.setEmail(string);
        // request.setName(string2);
        // request.setPassword(string3);

        // response =  client.postForEntity("/employee/create", request, AccountRequestDto.class);
    }
    //=-=-=-=-=-=-=-=-=-=-=-=- WHEN -=-=-=-=-=-=-=-=-=-=-=-=//


    //=-=-=-=-=-=-=-=-=-=-=-=- THEN -=-=-=-=-=-=-=-=-=-=-=-=//
    @Then("the user shall successfully login into the system with the account with the username {string}")
    public void CheckIfUserIsLoggedIn(String string) {
        assertEquals(string, requestResponse);
    }
    //=-=-=-=-=-=-=-=-=-=-=-=- THEN -=-=-=-=-=-=-=-=-=-=-=-=//
}
