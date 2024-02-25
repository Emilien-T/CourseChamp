package ca.mcgill.ecse428.CourseChamp.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ca.mcgill.ecse428.CourseChamp.exception.CourseChampException;
import ca.mcgill.ecse428.CourseChamp.model.Review;
import ca.mcgill.ecse428.CourseChamp.repository.ReviewRepository;
import ca.mcgill.ecse428.CourseChamp.service.ReviewService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class ReviewServiceTests {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindReviewsByCourseCodeSuccess() {
        String courseCode = "ECSE428";
        List<Review> mockReviews = new ArrayList<>();
        mockReviews.add(new Review()); // Add a mock Review instance

        when(reviewRepository.findReviewsByCourseCode(courseCode)).thenReturn(mockReviews);

        List<Review> reviews = reviewService.findReviewsByCourseCode(courseCode);

        assertNotNull(reviews, "The returned review list should not be null.");
        assertFalse(reviews.isEmpty(), "The review list should not be empty.");
        assertEquals(mockReviews.size(), reviews.size(), "The size of returned review list should match the mock list.");
        verify(reviewRepository).findReviewsByCourseCode(courseCode);
    }

    @Test
    public void testFindReviewsByCourseCodeNotFound() {
        String courseCode = "ECSE428";
        when(reviewRepository.findReviewsByCourseCode(courseCode)).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(
            CourseChampException.class,
            () -> reviewService.findReviewsByCourseCode(courseCode)
        );

        assertEquals(HttpStatus.CREATED, ((CourseChampException) exception).getStatus(), "HttpStatus should be NOT_FOUND.");
        assertEquals("No reviews found for this course.", exception.getMessage(), "Exception message should match the expected text.");
        verify(reviewRepository).findReviewsByCourseCode(courseCode);
    }
}
