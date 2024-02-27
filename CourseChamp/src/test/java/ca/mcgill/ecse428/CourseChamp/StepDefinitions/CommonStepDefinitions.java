package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse428.CourseChamp.model.CourseOffering;
import ca.mcgill.ecse428.CourseChamp.repository.AccountRepository;
import ca.mcgill.ecse428.CourseChamp.repository.AdminRepository;
import ca.mcgill.ecse428.CourseChamp.repository.CourseOfferingRepository;
import ca.mcgill.ecse428.CourseChamp.repository.CourseRepository;
import ca.mcgill.ecse428.CourseChamp.repository.ReviewRepository;
import ca.mcgill.ecse428.CourseChamp.repository.StudentRepository;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;

public class CommonStepDefinitions {
  @Autowired
  AccountRepository accountRepository;

  @Autowired
  AdminRepository adminRepository;

  @Autowired
  CourseRepository courseRepository;

  @Autowired
  StudentRepository studentRepository;


  @Autowired
  CourseOfferingRepository courseOfferingRepository;
  @After
  public void tearDown(){
    reviewRepository.deleteAll();
    adminRepository.deleteAll();
    studentRepository.deleteAll();
    accountRepository.deleteAll();
    studentRepository.deleteAll();
    courseOfferingRepository.deleteAll();
  }
}
