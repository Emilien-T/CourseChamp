package ca.mcgill.ecse428.CourseChamp.controller;

import jakarta.validation.Valid;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse428.CourseChamp.dto.ReviewResponseDto;
import ca.mcgill.ecse428.CourseChamp.dto.StudentRequestDto;
import ca.mcgill.ecse428.CourseChamp.dto.StudentResponseDto;
import ca.mcgill.ecse428.CourseChamp.model.Admin;
import ca.mcgill.ecse428.CourseChamp.model.Review;
import ca.mcgill.ecse428.CourseChamp.model.Student;
import ca.mcgill.ecse428.CourseChamp.service.StudentService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin(origins = "*")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * Returns the Student based on their Email
     * 
     * @param email - Pass in the email argument by using /student={?email}
     * @return the Student with Email, Password, Name
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Student not found.", content = {
                    @Content(mediaType = "String") })
    })
    @GetMapping(value = { "/student/{email}", "/student/{email}/" })
    public ResponseEntity<StudentResponseDto> getStudentByEmail(@PathVariable String email) {
        return new ResponseEntity<StudentResponseDto>(new StudentResponseDto(studentService.getStudentByEmail(email)),
                HttpStatus.OK);
    }

    @GetMapping(value = { "/student/getreviews", "/student/getreviews/" })
    public Iterable<ReviewResponseDto> getReviewsOfStudentByEmail(@RequestParam String email) {
        return StreamSupport.stream(studentService.getReviewsOfStudent(email).spliterator(), false).map(r -> new ReviewResponseDto(r)).collect(Collectors.toList());
    }

    /**
     * Creates a new Student
     * 
     * @param studentRequest - Pass in a student dto using a JSON request
     * @return the dto response of the new Student
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "409", description = "Student account with this email already exists.", content = {
                    @Content(mediaType = "String") })
    })
    @PostMapping("/student/create")
    public ResponseEntity<StudentResponseDto> createStudent(@Valid @RequestBody StudentRequestDto studentRequest) {
        // 1. You pass in a request, validates the constraints, creates an Student if they pass
        // 2. You use the service class to check if it exists and save it
        Student Student = studentService.createStudentAccount(studentRequest.toModel()) ;

        StudentResponseDto responseBody = new StudentResponseDto(Student);
        return new ResponseEntity<StudentResponseDto>(responseBody, HttpStatus.CREATED); // 3. You mask the model by
        }
        
    /**
     * Updates a Student
     * 
     * @param studentRequest - Pass in a student dto using a JSON request
     * @return the dto response of the updtated Student
     */// returning a Response
    @PutMapping("/student/update")
    public ResponseEntity<StudentResponseDto> updateStudent(@Valid @RequestBody StudentRequestDto studentRequest) {
        Student s = studentRequest.toModel();
        StudentResponseDto responseBody = new StudentResponseDto(studentService.updateStudentAccount(s));
        return new ResponseEntity<StudentResponseDto>(responseBody, HttpStatus.OK);
    }
                                                                                

    @DeleteMapping("/student/delete/{email}")
    public void deleteStudent(@PathVariable String email) {
        studentService.deleteStudentAccount(email);
    }
}
