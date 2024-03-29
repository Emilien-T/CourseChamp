package ca.mcgill.ecse428.CourseChamp.StepDefinitions;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse428.CourseChamp.model.CourseOffering;
import ca.mcgill.ecse428.CourseChamp.model.Review;
import ca.mcgill.ecse428.CourseChamp.model.Admin;
import ca.mcgill.ecse428.CourseChamp.model.Course;
import ca.mcgill.ecse428.CourseChamp.model.Student;
import ca.mcgill.ecse428.CourseChamp.repository.AccountRepository;
import ca.mcgill.ecse428.CourseChamp.repository.AdminRepository;
import ca.mcgill.ecse428.CourseChamp.repository.CourseOfferingRepository;
import ca.mcgill.ecse428.CourseChamp.repository.CourseRepository;
import ca.mcgill.ecse428.CourseChamp.repository.ReviewRepository;
import ca.mcgill.ecse428.CourseChamp.repository.StudentRepository;
import ca.mcgill.ecse428.CourseChamp.repository.VoteRepository;
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


  @Autowired
  ReviewRepository reviewRepository;

  @Autowired
  VoteRepository voteRepository;

  @After
  public void tearDown(){
    voteRepository.deleteAll();
    reviewRepository.deleteAll();
    adminRepository.deleteAll();
    studentRepository.deleteAll();
    accountRepository.deleteAll();
    studentRepository.deleteAll();
    courseOfferingRepository.deleteAll();
    courseRepository.deleteAll();
    
  }

  //Background StepDefs
  //=-=-=-=-=-=-=-=-=-=-=-=- GIVEN -=-=-=-=-=-=-=-=-=-=-=-=//
  @Given("the following admins exists in the system:")
  public void the_admin_exists_in_the_system(io.cucumber.datatable.DataTable dataTable) {
      List<Map<String, String>> rows = dataTable.asMaps();
      for(var row : rows){
        String email = row.get("email");
        String username = row.get("username");
        String password = row.get("password");
        Admin admin = new Admin(email, username, password);
        adminRepository.save(admin);
      }
  }

  //=-=-=-=-=-=-=-=-=-=-=-=- GIVEN -=-=-=-=-=-=-=-=-=-=-=-=//
  @Given("the following courses exist in the system:")
  public void the_following_courses_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for(var row : rows){
      String department = row.get("department");
      int courseNumber = Integer.parseInt(row.get("courseNumber"));
      String name = row.get("name");
      String description = row.get("description");
      String prerequisite = row.get("prerequisite");
      Course course = new Course(department, courseNumber, name, description, description);
      if(prerequisite != null && !prerequisite.isEmpty()){
        Course prerequeCourse = courseRepository.findCourseByCourseCode(prerequisite);
        if(prerequeCourse != null){
          course.addPrerequesite(prerequeCourse);
        }
      }
      courseRepository.save(course);
    }
  }


  //=-=-=-=-=-=-=-=-=-=-=-=- GIVEN -=-=-=-=-=-=-=-=-=-=-=-=//
  @Given("the following students exist in the system:")
  public void the_following_students_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
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
      studentRepository.save(student);
    }
  }

  // =-=-=-=-=-=-=-=-=-=-=-=- GIVEN -=-=-=-=-=-=-=-=-=-=-=-=//
  @Given("the following course offerings exist in the system:")
  public void the_following_course_offerings_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
      List<Map<String, String>> rows = dataTable.asMaps();
      for (var row : rows) {
          Course course = courseRepository.findCourseByCourseCode(row.get("courseCode"));
          CourseOffering courseOffering = new CourseOffering(row.get("semester"), course);
          courseOfferingRepository.delete(courseOffering);
          courseOfferingRepository.save(courseOffering);
      }
  }
}
