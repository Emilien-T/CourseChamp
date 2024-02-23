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
    private CourseService courseService;

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
    @GetMapping(value = { "/review/{reviewId", "/review/{reviewId}/" })
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
        Review review = reviewService.createReview(reviewRequest.toModel());
        ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);
        return new ResponseEntity<>(reviewResponseDto, HttpStatus.CREATED);
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
}
