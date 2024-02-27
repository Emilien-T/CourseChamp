package ca.mcgill.ecse428.CourseChamp.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse428.CourseChamp.model.Vote;

public interface VoteRepository extends CrudRepository<Vote, Integer>  {
  
  public Vote findVoteById(int id);
  
}
