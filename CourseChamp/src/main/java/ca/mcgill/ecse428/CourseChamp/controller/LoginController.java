package ca.mcgill.ecse428.CourseChamp.controller;
import ca.mcgill.ecse428.CourseChamp.dto.AdminResponseDto;
import ca.mcgill.ecse428.CourseChamp.dto.StudentResponseDto;
import ca.mcgill.ecse428.CourseChamp.service.AdminService;
import ca.mcgill.ecse428.CourseChamp.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class LoginController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;


    /**
     * Controller Method that allows the verfication of the email and password of a Student or Admin
     * 
     * @param user - Takes the value "Admin" to log in as an admin. Else, logs in as a student
     * @param email -il of the student/admin
     * @param password - Password of the student/admin
     * @return the Admin/Student that you are now logged in as
     */
    @GetMapping("/login/{user}")
    public ResponseEntity<?> LoginUser(@PathVariable String user, @PathVariable String email, @RequestParam String password){
        if (user.equals("Admin"))
            return new ResponseEntity<AdminResponseDto>(new AdminResponseDto(adminService.loginIntoAdmin(email, password)), HttpStatus.OK);
        else
            return new ResponseEntity<StudentResponseDto>(new StudentResponseDto(studentService.loginIntoStudent(email, password)), HttpStatus.OK);


    }




}