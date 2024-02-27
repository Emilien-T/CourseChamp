package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse428.CourseChamp.model.CourseOffering;
import ca.mcgill.ecse428.CourseChamp.model.Student;
import ca.mcgill.ecse428.CourseChamp.repository.AccountRepository;
import ca.mcgill.ecse428.CourseChamp.repository.AdminRepository;
import ca.mcgill.ecse428.CourseChamp.repository.CourseOfferingRepository;
import ca.mcgill.ecse428.CourseChamp.repository.CourseRepository;
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
    adminRepository.deleteAll();
    studentRepository.deleteAll();
    accountRepository.deleteAll();
    studentRepository.deleteAll();
    courseOfferingRepository.deleteAll();
    courseRepository.deleteAll();
    
  }

  @Given("the following students exist in the system:")
    public void the_follow_students_exist_in_the_system(io.cucumber.datatable.DataTable dataTable){
         List<Map<String, String>> rows = dataTable.asMaps();
        for (var row : rows) {
            Student.Major major = Student.Major.Software;
            String majorString = row.get("major");
            if(majorString.equals("Computer")){
                major = Student.Major.Computer;
            }
            if(majorString.equals("Electrical")){
                major = Student.Major.Electrical;
            }
            Student student = new Student(row.get("email"),row.get("username"), row.get("password"), major);
            studentRepository.delete(student);
            studentRepository.save(student);
        }
    }
}
