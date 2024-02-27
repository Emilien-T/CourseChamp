package ca.mcgill.ecse428.CourseChamp.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse428.CourseChamp.dto.ReviewRequestDto;
import ca.mcgill.ecse428.CourseChamp.dto.ReviewResponseDto;
import ca.mcgill.ecse428.CourseChamp.exception.CourseChampException;
import ca.mcgill.ecse428.CourseChamp.model.CourseOffering;
import ca.mcgill.ecse428.CourseChamp.model.Review;
import ca.mcgill.ecse428.CourseChamp.model.Student;
import ca.mcgill.ecse428.CourseChamp.repository.CourseOfferingRepository;
import ca.mcgill.ecse428.CourseChamp.repository.StudentRepository;
import ca.mcgill.ecse428.CourseChamp.service.CourseService;
import ca.mcgill.ecse428.CourseChamp.service.ReviewService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
public class ReviewController {

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ReviewService reviewService;

    /**
     * Retrieves a review by its review id
     * 
     * @param reviewId - The unique id of the review
     * @return ResponseEntity containing ReviewResponseDto with review details if
     *         found, or 404 if not found
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review found"),
            @ApiResponse(responseCode = "404", description = "Review not found", content = @Content)
    })
    @GetMapping(value = { "/review/{reviewId}", "/review/{reviewId}/" })
    public ResponseEntity<ReviewResponseDto> getReviewById(@PathVariable int reviewId) {
        return new ResponseEntity<>(new ReviewResponseDto(reviewService.getReviewById(reviewId)),
                HttpStatus.OK);
    }

    /**
     * Creates a new review.
     * 
     * @param reviewRequest - Request body containing review details
     * @return ResponseEntity containing ReviewResponseDto with created review
     *         details, or 400 if invalid request
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Review created"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "String") })
    })
    @PostMapping("/review/create")
    public ResponseEntity<ReviewResponseDto> createReview(@Valid @RequestBody ReviewRequestDto reviewRequest) {
        Review review = new Review();
        review.setText(reviewRequest.getText());
        review.setRating(reviewRequest.getRating());
        Iterable<CourseOffering> courseOfferings = courseOfferingRepository.findAll();
        for (CourseOffering c : courseOfferings){
            if(c.getCourse().getCourseCode().equals(reviewRequest.getCourseCode()) && c.getSemester().equals(reviewRequest.getSemester())){
                review.setCourseOffering(c);
                break;
            }
        }

        if(review.getCourseOffering() == null){
            throw new CourseChampException(HttpStatus.BAD_REQUEST, "Semester not found");
        }

        Iterable<Student> students = studentRepository.findAll();
        for(Student s : students){
            if(s.getEmail().equals(reviewRequest.getStudentEmail())){
                review.setStudent(s);
                break;
            }
        }

        if(review.getStudent() == null){
            throw new CourseChampException(HttpStatus.BAD_REQUEST, "Student not found");
        }
        review = reviewService.createReview(review);

        if(review.getRating() == 0){
            throw new CourseChampException(HttpStatus.BAD_REQUEST, "BLAH");
        }
        return new ResponseEntity<ReviewResponseDto>(new ReviewResponseDto(review), HttpStatus.CREATED);
    }

    /**
     * Verifies if a review exists based on its department and course number.
     * 
     * @param id   - The id of the review
     * @param rating - The review rating
     * @param text - The description of the review 
     * @return ResponseEntity containing ReviewResponseDto with review details if
     *         found, or 404 if not found
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review found"),
            @ApiResponse(responseCode = "404", description = "Review not found", content = @Content)
    })
    @GetMapping("/review")
    public ResponseEntity<ReviewResponseDto> verifyReview(@RequestParam int id,
            @RequestParam int rating, @RequestParam String text) {
        Review review = reviewService.verifyReview(id, rating, text);
        ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);
        return new ResponseEntity<>(reviewResponseDto, HttpStatus.OK);
    }


   // public static void CreateReview(String rating, String content, String number, String department) {}
    //public static void ViewReview(String number, String department) {}
    
    

    /**
     * View reviews for a specific course.
     * 
     * @param courseCode - The code of the course to fetch reviews for.
     * @return A list of reviews for the specified course.
     */
    // @ApiResponses(value = {
    //         @ApiResponse(responseCode = "200", description = "Successfully retrieved reviews"),
    //         @ApiResponse(responseCode = "404", description = "Course not found", content = @Content)
    // })
    // @GetMapping("/{courseCode}")
    // public ResponseEntity<List<ReviewResponseDto>> viewReviews(@PathVariable String courseCode) {
    //     try {
    //         List<Review> reviews = reviewService.findReviewsByCourseCode(courseCode);
    //         List<ReviewResponseDto> responseDtos = reviews.stream()
    //         .map(review -> new ReviewResponseDto(review.getId(), review.getRating(), review.getText()))
    //         .collect(Collectors.toList());
    //         return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    //     }
    // }


}