package ca.mcgill.ecse428.CourseChamp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ca.mcgill.ecse428.CourseChamp.dto.StudentRequestDto;
import ca.mcgill.ecse428.CourseChamp.dto.StudentResponseDto;
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
    @GetMapping(value = { "/student", "/student/" })
    public ResponseEntity<StudentResponseDto> getStudentByEmail(@RequestParam String email) {
        return new ResponseEntity<StudentResponseDto>(new StudentResponseDto(studentService.getStudentByEmail(email)),
                HttpStatus.OK);
    }

    /**
     * Creates a new Student
     * 
     * @param StudentRequest - Pass in a student dto using a JSON request
     * @return the dto response of the new Student
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Hourly wage must be positive.", content = {
                    @Content(mediaType = "String") }),
            @ApiResponse(responseCode = "409", description = "Student account with this email already exists.", content = {
                    @Content(mediaType = "String") })
    })
    @PostMapping("/student/create")
    public ResponseEntity<StudentResponseDto> createStudent(@Valid @RequestBody StudentRequestDto StudentRequest) {
        Student Student = StudentRequest.toModel(); // 1. You pass in a request, validates the constraints, creates an
                                                    // Student if they pass
        Student = studentService.createStudentAccount(Student); // 2. You use the service class to check if it exists
                                                                // and save it
        StudentResponseDto responseBody = new StudentResponseDto(Student);
        return new ResponseEntity<StudentResponseDto>(responseBody, HttpStatus.CREATED); // 3. You mask the model by
                                                                                         // returning a Response
    }

    @DeleteMapping("/student/delete/{email}")
    public void deleteStudent(@PathVariable String email) {
        studentService.deleteStudentAccount(email);
    }
}
