package ca.mcgill.ecse428.CourseChamp.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse428.CourseChamp.model.Review;
import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
    public Review findReviewById(int id);
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