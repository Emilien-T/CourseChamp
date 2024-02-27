package ca.mcgill.ecse428.CourseChamp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ca.mcgill.ecse428.CourseChamp.model.Review;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Review findReviewByReviewId(int reviewId);
    List<Review> findReviewsByCourseCode(String courseCode);
}

   /*
    * 
    * PLEASE READ :
    * 
    * 
    * if the direct method signature List<Review> findReviewsByCourseCode(String
    * courseCode);
    * cannot be automatically implemented by Spring Data JPA
    * because its not a straightforward relationship between Review,
    * CourseOffering, and Course entities (i.e., if there isn't a straightforward
    * mapping from Review to
    * Course using course code directly)
    * Then
    * Please use the following instead
    * 
    * 
    * @Query("SELECT r FROM Review r WHERE r.courseOffering.course.courseCode = :courseCode"
    * )
    * List<Review> findReviewsByCourseCode(@Param("courseCode") String courseCode);
    * 
    * 
    * 
    * Thanks
    */