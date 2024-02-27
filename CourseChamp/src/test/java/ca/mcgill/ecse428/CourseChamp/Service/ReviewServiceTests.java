package ca.mcgill.ecse428.CourseChamp.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import ca.mcgill.ecse428.CourseChamp.exception.CourseChampException;
import ca.mcgill.ecse428.CourseChamp.model.Review;
import ca.mcgill.ecse428.CourseChamp.model.Student;
import ca.mcgill.ecse428.CourseChamp.repository.ReviewRepository;
import ca.mcgill.ecse428.CourseChamp.service.ReviewService;
import ca.mcgill.ecse428.CourseChamp.model.Course;
import ca.mcgill.ecse428.CourseChamp.model.CourseOffering;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.springframework.boot.test.context.SpringBootTest;



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
    public void testFindReviewsByCourseCodeSuccess() {
        ArrayList<Review> mockReviews = new ArrayList<>();
        int reviewId = 1;
        CourseOffering courseOffering = new CourseOffering();
        Course course = new Course();
        String courseCode = "ECSE428";
        course.setCourseCode(courseCode);
        courseOffering.setCourse(course);
        String reviewText = "This is a review";
        int rating = 5;

        Review review = new Review();
        review.setId(reviewId);
        review.setCourseOffering(courseOffering);
        review.setText(reviewText);
        review.setRating(rating);

        Student student = new Student();
        student.setEmail("evan@mail.com");
        review.setStudent(student);

        mockReviews.add(review); // Add a mock Review instance

        when(reviewRepository.findAll()).thenReturn(mockReviews);

        List<Review> reviews = reviewService.findReviewsByCourseCode(courseCode);

        assertNotNull(reviews, "The returned review list should not be null.");
        assertFalse(reviews.isEmpty(), "The review list should not be empty.");
        assertEquals(mockReviews.size(), reviews.size(),
                "The size of returned review list should match the mock list.");
    }

    @Test
    public void testFindReviewsByCourseCodeNotFound() {
        String courseCode = "ECSE428";
        when(reviewRepository.findAll()).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(
                CourseChampException.class,
                () -> reviewService.findReviewsByCourseCode(courseCode));

        assertEquals(HttpStatus.NOT_FOUND, ((CourseChampException) exception).getStatus(),
                "HttpStatus should be NOT_FOUND.");
        assertEquals("No reviews found for this course.", exception.getMessage(),
                "Exception message should match the expected text.");
    }

    @Test
    public void testFindReviewsByCourseCodeNullCourseCode() {
        String courseCode = null;

        assertThrows(CourseChampException.class, () -> {
            reviewService.findReviewsByCourseCode(courseCode);
        }, "Expected findReviewsByCourseCode to throw IllegalArgumentException, but it didn't");

    }

    @Test
    public void testFindReviewsByCourseCodeEmptyCourseCode() {
        String courseCode = "";

        assertThrows(CourseChampException.class, () -> {
            reviewService.findReviewsByCourseCode(courseCode);
        }, "Expected findReviewsByCourseCode to throw IllegalArgumentException, but it didn't");
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

        Student student = new Student();
        student.setEmail("evan@mail.com");
        review.setStudent(student);

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

    @Test
    public void testCreateReviewNullText() {
        assertThrows(CourseChampException.class, () -> {
            Review review = new Review();
            CourseOffering courseOffering = new CourseOffering();
            review.setCourseOffering(courseOffering);
            reviewService.createReview(review);
        });
    }

    @Test
    public void testCreateReviewNullRating() {
        assertThrows(CourseChampException.class, () -> {
            Review review = new Review();
            CourseOffering courseOffering = new CourseOffering();
            review.setCourseOffering(courseOffering);
            review.setText("This is a review");
            reviewService.createReview(review);
        });
    }



    @Test
    public void testGetReview() {
        int reviewId = 1;
        CourseOffering courseOffering = new CourseOffering(); 
        
        Course course = new Course();
        courseOffering.setCourse(course);
        String reviewText = "This is a review";
        int rating = 5;

        Review review = new Review();
        review.setId(reviewId);
        review.setCourseOffering(courseOffering);
        review.setText(reviewText);
        review.setRating(rating);


        when(reviewRepository.findReviewById(reviewId)).thenReturn(review);

        Review foundReview = reviewService.getReviewById(reviewId);

        assertNotNull(foundReview);
        assertEquals(reviewId, foundReview.getId());
        assertEquals(courseOffering, foundReview.getCourseOffering());
        assertEquals(reviewText, foundReview.getText());
        assertEquals(rating, foundReview.getRating());

    }

    @Test
    public void testGetReviewNotFound() {
        int reviewId = 1;


        when(reviewRepository.findById(reviewId)).thenReturn(java.util.Optional.empty());


        assertThrows(CourseChampException.class, () -> {
            reviewService.getReviewById(reviewId);
        });
    }



    @Test
    public void testFindReviewsByCourseCodeNull() {
        Exception exception = assertThrows(CourseChampException.class, () -> {
            reviewService.findReviewsByCourseCode(null);
        });

        String expectedMessage = "Course code cannot be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
