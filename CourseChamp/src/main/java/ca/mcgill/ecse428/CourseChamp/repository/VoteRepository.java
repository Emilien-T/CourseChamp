package ca.mcgill.ecse428.CourseChamp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ca.mcgill.ecse428.CourseChamp.model.Review;
import ca.mcgill.ecse428.CourseChamp.model.Student;
import ca.mcgill.ecse428.CourseChamp.model.Vote;

public interface VoteRepository extends CrudRepository<Vote, Integer>  {
  
  public Vote findVoteById(int id);
  @Query("SELECT v FROM Vote v WHERE v.review = :review and v.student = :student")
  Vote findVoteByReviewAndStudentNamedParams(
    @Param("review") Review review,
    @Param("student") Student student);
}
