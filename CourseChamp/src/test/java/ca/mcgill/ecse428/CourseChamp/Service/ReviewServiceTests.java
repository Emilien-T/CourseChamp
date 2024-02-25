package ca.mcgill.ecse428.CourseChamp.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse428.CourseChamp.exception.CourseChampException;
import ca.mcgill.ecse428.CourseChamp.model.Course;
import ca.mcgill.ecse428.CourseChamp.model.CourseId;
import ca.mcgill.ecse428.CourseChamp.model.CourseOffering;
import ca.mcgill.ecse428.CourseChamp.model.Review;
import ca.mcgill.ecse428.CourseChamp.repository.ReviewRepository;
import ca.mcgill.ecse428.CourseChamp.service.ReviewService;
import io.cucumber.java.After;
import io.cucumber.java.Before;

@SpringBootTest
public class ReviewServiceTests {


    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    public void setMockOutput() {
        reviewRepository.deleteAll();;
    }

    @AfterEach
    public void clearDatabase() {
        reviewRepository.deleteAll();
    }


    @Test
    public void testCreateReview() {
        int reviewId = 1;
        CourseOffering courseOffering = new CourseOffering();        
        String reviewText = "This is a review";
        int rating = 5;

        Review review = new Review();
        review.setId(reviewId);
        review.setCourseOffering(courseOffering);
        review.setText(reviewText);
        review.setRating(rating);

        when(reviewRepository.save(review)).thenReturn(review);

        Review createdReview = reviewService.createReview(review);

        assertNotNull(createdReview);
        assertEquals(reviewId, createdReview.getId());
        assertEquals(courseOffering, createdReview.getCourseOffering());
        assertEquals(reviewText, createdReview.getText());
        assertEquals(rating, createdReview.getRating());
    }


    @Test
    public void testCreateReviewNull() {
        assertThrows(CourseChampException.class, () -> {
            reviewService.createReview(null);
        });
    }

    @Test
    public void testCreateReviewNullCourseOffering() {
        assertThrows(CourseChampException.class, () -> {
            Review review = new Review();
            reviewService.createReview(review);
        });
    }

    
}
