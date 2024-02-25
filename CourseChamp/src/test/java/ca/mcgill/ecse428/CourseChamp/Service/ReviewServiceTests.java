package ca.mcgill.ecse428.CourseChamp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ca.mcgill.ecse428.CourseChamp.exception.CourseChampException;
import ca.mcgill.ecse428.CourseChamp.model.Review;
import ca.mcgill.ecse428.CourseChamp.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class ReviewServiceTest {

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

        assertNotNull(reviews);
        assertFalse(reviews.isEmpty());
        verify(reviewRepository).findReviewsByCourseCode(courseCode);
    }

    @Test
    public void testFindReviewsByCourseCodeNotFound() {
        String courseCode = "ECSE428";
        when(reviewRepository.findReviewsByCourseCode(courseCode)).thenReturn(new ArrayList<>());

        CourseChampException exception = assertThrows(
            CourseChampException.class,
            () -> reviewService.findReviewsByCourseCode(courseCode)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No reviews found for this course.", exception.getMessage());
        verify(reviewRepository).findReviewsByCourseCode(courseCode);
    }
}
