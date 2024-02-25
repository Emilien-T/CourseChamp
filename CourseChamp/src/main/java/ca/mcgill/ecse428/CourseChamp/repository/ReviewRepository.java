package ca.mcgill.ecse428.CourseChamp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ca.mcgill.ecse428.CourseChamp.model.Review;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    
   /**
    * Retrieves a list of reviews from the database based on the course code.
    *
    * @param courseCode The course code associated with the reviews.
    * @return A list of Review entities corresponding to the provided course code. If no reviews are found, this method returns an empty list.
    */
    List<Review> findReviewsByCourseCode(String courseCode);

    /*

    PLEASE READ :


     * if the direct method signature List<Review> findReviewsByCourseCode(String courseCode); 
     * cannot be automatically implemented by Spring Data JPA 
     * because its not a straightforward relationship between Review, 
     * CourseOffering, and Course entities (i.e., if there isn't a straightforward mapping from Review to
     *  Course using course code directly)
     * Then
     * Please use the following instead
     * 
     * 
    @Query("SELECT r FROM Review r WHERE r.courseOffering.course.courseCode = :courseCode")
    List<Review> findReviewsByCourseCode(@Param("courseCode") String courseCode);



    Thanks
     */
}
