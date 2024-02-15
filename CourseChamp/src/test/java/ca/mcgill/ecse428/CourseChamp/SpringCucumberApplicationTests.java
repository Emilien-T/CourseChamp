package ca.mcgill.ecse428.CourseChamp;

import io.cucumber.spring.CucumberContextConfiguration;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringCucumberApplicationTests {
    @Test
    void contextLoads() {
    }

}
