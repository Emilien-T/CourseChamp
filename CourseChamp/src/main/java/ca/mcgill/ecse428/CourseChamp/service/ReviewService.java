package ca.mcgill.ecse428.CourseChamp.service;

import ca.mcgill.ecse428.CourseChamp.dto.ReviewResponseDto;
import ca.mcgill.ecse428.CourseChamp.exception.CourseChampException;
import ca.mcgill.ecse428.CourseChamp.model.Review;
import ca.mcgill.ecse428.CourseChamp.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        List<Review> reviews = reviewRepository.findReviewsByCourseCode(courseCode);
        if (reviews.isEmpty()) {
            throw new CourseChampException(HttpStatus.NOT_FOUND, "No reviews found for this course.");
        }
        return reviews;
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
        Review review = reviewRepository.findReviewByReviewId(reviewId);
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
        if (reviewRepository.findReviewByReviewId(review.getReviewById()) == null) {
            return reviewRepository.save(review);
        } else {
            throw new CourseChampException(HttpStatus.CONFLICT, "A review with this Id already exists");
        }
    }

}

