package ca.mcgill.ecse428.CourseChamp.service;

import ca.mcgill.ecse428.CourseChamp.dto.ReviewResponseDto;
import ca.mcgill.ecse428.CourseChamp.exception.CourseChampException;
import ca.mcgill.ecse428.CourseChamp.model.Review;
import ca.mcgill.ecse428.CourseChamp.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    /**

     * Service method to fetch reviews in the database for a specific course code
     * 
     * @param courseCode - The code of the course for which reviews are fetched
     * @return List of ReviewResponseDto containing review data
     * @throws CourseChampException - If no reviews are found for the course
     */
    @Transactional
    public List<Review> findReviewsByCourseCode(String courseCode) {
        if (courseCode == null || courseCode.isEmpty()) {
            throw new CourseChampException(HttpStatus.BAD_REQUEST, "Course code cannot be null or empty");
        }
        
        Iterable<Review> reviews = reviewRepository.findAll();
        ArrayList<Review> reviewsByCourseCode = new ArrayList<>();  
        for(Review r : reviews){
            if(r.getCourseOffering() != null && r.getCourseOffering().getCourse() != null && r.getCourseOffering().getCourse().getCourseCode().equals(courseCode)){
                reviewsByCourseCode.add(r);
            }
        }
        
        if (reviewsByCourseCode.isEmpty()) {
            throw new CourseChampException(HttpStatus.NOT_FOUND, "No reviews found for this course.");
        }
    
        return reviewsByCourseCode;
    }
    

     /**
     * Service method to fetch an existing review with a specific ID from
     * the database
     * 
     * @param reviewId - review id of the review
     * @throws CourseChampException - If review does not exist
     */
    @Transactional
    public Review getReviewById(int reviewId) {
        Review review = reviewRepository.findReviewById(reviewId);
        if (review == null) {
            throw new CourseChampException(HttpStatus.NOT_FOUND, "Review not found.");
        }
        return review;
    }


     /**
     * Service method to store a created review in the database
     * 
     * @param review - instance of the review to be persisted
     * @throws CourseChampException - If a review with the same Id already exists
     */
    @Transactional
    public Review createReview(Review review) {
        if(review == null){
            throw new CourseChampException(HttpStatus.CONFLICT, "Please enter a review"); 
        }
        if(review.getCourseOffering() == null){
            throw new CourseChampException(HttpStatus.CONFLICT, "Please enter a course offering"); 
        }
        if(review.getStudent() == null){
            throw new CourseChampException(HttpStatus.CONFLICT, "Please enter a student"); 
        }
            return reviewRepository.save(review);
    }

    public Review verifyReview(int id, int rating, String text) {
        // Find the review by id
        Review review = reviewRepository.findReviewById(id);
    
        // If the review doesn't exist or the rating and text don't match, throw an exception
        if (review == null || review.getRating() != rating || !review.getText().equals(text)) {
            throw new CourseChampException(null, "Review not found or rating and text don't match");
        }
    
        // If everything checks out, return the review
        return review;
    }

}
