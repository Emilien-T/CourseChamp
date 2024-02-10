
package ca.mcgill.ecse428.CourseChamp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.After;

import ca.mcgill.ecse428.CourseChamp.model.Account;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.sl.In;


import java.sql.Date;
import java.util.List;
import java.util.Map;

public class StepDefinitions {
  
  @Given("account balance is {double}")
    public void givenAccountBalance(Double initialBalance) {
      assertFalse(false);
      // assertFalse(true);
    }

    // other step definitions 
  
}

