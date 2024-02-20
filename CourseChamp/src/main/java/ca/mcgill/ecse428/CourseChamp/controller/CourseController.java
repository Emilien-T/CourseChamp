package ca.mcgill.ecse428.CourseChamp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ca.mcgill.ecse428.CourseChamp.dto.CourseRequestDto;
import ca.mcgill.ecse428.CourseChamp.dto.CourseResponseDto;
import ca.mcgill.ecse428.CourseChamp.model.Course;
import ca.mcgill.ecse428.CourseChamp.service.CourseService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * Retrieves a course by its course code.
     * 
     * @param courseCode - The unique code of the course
     * @return ResponseEntity containing CourseResponseDto with course details if found, or 404 if not found
     */
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Course found"),
        @ApiResponse(responseCode = "404", description = "Course not found", content = @Content)
    })
    @GetMapping(value = {"/course/{courseCode}", "/course/{courseCode}/"})
    public ResponseEntity<CourseResponseDto> getCourseByCode(@PathVariable String courseCode) {
        return new ResponseEntity<CourseResponseDto>(new CourseResponseDto(courseService.getCourseByCode(courseCode)), HttpStatus.OK);
    }

    /**
     * Creates a new course.
     * 
     * @param courseRequest - Request body containing course details
     * @return ResponseEntity containing CourseResponseDto with created course details, or 400 if invalid request
     */
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Course created"),
        @ApiResponse(responseCode = "400", description = "Invalid request", content = {@Content(mediaType = "String")})
    })
    @PostMapping("/course/create")
    public ResponseEntity<CourseResponseDto> createCourse(@Valid @RequestBody CourseRequestDto courseRequest) {
        Course course = courseService.createCourse(courseRequest.toModel());
        CourseResponseDto courseResponseDto = new CourseResponseDto(course);
        return new ResponseEntity<CourseResponseDto>(courseResponseDto, HttpStatus.CREATED);
    }

    /**
     * Verifies if a course exists based on its department and course number.
     * 
     * @param department - The department code of the course
     * @param courseNumber - The course number
     * @return ResponseEntity containing CourseResponseDto with course details if found, or 404 if not found
     */
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Course found"),
        @ApiResponse(responseCode = "404", description = "Course not found", content = @Content)
    })
    @GetMapping("/course")
    public ResponseEntity<CourseResponseDto> verifyCourse(@RequestParam String department, @RequestParam int courseNumber) {
        Course course = courseService.verifyCourse(department, courseNumber);
        CourseResponseDto courseResponseDto = new CourseResponseDto(course);
        return new ResponseEntity<CourseResponseDto>(courseResponseDto, HttpStatus.OK);
    }
}
