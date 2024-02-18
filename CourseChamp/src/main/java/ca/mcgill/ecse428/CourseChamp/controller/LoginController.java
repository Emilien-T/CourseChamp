package ca.mcgill.ecse428.CourseChamp.controller;
import ca.mcgill.ecse428.CourseChamp.dto.AdminResponseDto;
import ca.mcgill.ecse428.CourseChamp.dto.StudentResponseDto;
import ca.mcgill.ecse428.CourseChamp.service.AdminService;
import ca.mcgill.ecse428.CourseChamp.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class LoginController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;


    @GetMapping("/login/{user}")
    public ResponseEntity<?> getUser(@PathVariable String user, @RequestParam String email){
        if (user.equals("Admin"))
            return new ResponseEntity<AdminResponseDto>(new AdminResponseDto(adminService.getAdminByEmail(email)), HttpStatus.OK);
        else
            return new ResponseEntity<StudentResponseDto>(new StudentResponseDto(studentService.getStudentByEmail(email)), HttpStatus.OK);


    }




}
