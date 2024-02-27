package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse428.CourseChamp.model.CourseOffering;
import ca.mcgill.ecse428.CourseChamp.repository.AccountRepository;
import ca.mcgill.ecse428.CourseChamp.repository.AdminRepository;
import ca.mcgill.ecse428.CourseChamp.repository.CourseOfferingRepository;
import ca.mcgill.ecse428.CourseChamp.repository.CourseRepository;
import ca.mcgill.ecse428.CourseChamp.repository.StudentRepository;
import io.cucumber.java.After;

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
    adminRepository.deleteAll();
    studentRepository.deleteAll();
    accountRepository.deleteAll();
    studentRepository.deleteAll();
    courseOfferingRepository.deleteAll();
  }
}
