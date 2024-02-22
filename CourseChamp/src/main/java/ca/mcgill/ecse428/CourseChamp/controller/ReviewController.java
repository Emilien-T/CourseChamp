package ca.mcgill.ecse428.CourseChamp.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ca.mcgill.ecse428.CourseChamp.dto.ReviewResponseDto;
import ca.mcgill.ecse428.CourseChamp.model.Review;
import ca.mcgill.ecse428.CourseChamp.service.ReviewService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/reviews") // Base path for reviews
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    /**
     * View reviews for a specific course.
     * 
     * @param courseCode - The code of the course to fetch reviews for.
     * @return A list of reviews for the specified course.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved reviews"),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content)
    })
    @GetMapping("/{courseCode}")
    public ResponseEntity<List<ReviewResponseDto>> viewReviews(@PathVariable String courseCode) {
        try {
            List<Review> reviews = reviewService.findReviewsByCourseCode(courseCode);
            List<ReviewResponseDto> responseDtos = reviews.stream()
            .map(review -> new ReviewResponseDto(review.getId(), review.getRating(), review.getText()))
            .collect(Collectors.toList());
            return new ResponseEntity<>(responseDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }



    public static void CreateReview(String rating, String content, String number, String department) {}

}