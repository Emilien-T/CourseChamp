package ca.mcgill.ecse428.CourseChamp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse428.CourseChamp.model.Review;
import ca.mcgill.ecse428.CourseChamp.model.Vote;
import ca.mcgill.ecse428.CourseChamp.repository.ReviewRepository;
import ca.mcgill.ecse428.CourseChamp.repository.StudentRepository;
import ca.mcgill.ecse428.CourseChamp.repository.VoteRepository;

@Service
public class VoteService {
  @Autowired
  VoteRepository voteRepository;
  @Autowired
  StudentRepository studentRepository;
  @Autowired
  ReviewRepository reviewRepository;

  public Review createVote(Vote vote){
    Iterable<Vote> votes = voteRepository.findAll();
    for(Vote v : votes ){
      if (v.getStudent().getEmail().equals(vote.getStudent().getEmail()) && v.getReview().getId() == vote.getReview().getId()){
        voteRepository.delete(v);
        break;
      }
    }
    vote = voteRepository.save(vote);
    return vote.getReview();
  }

  public void deleteVote(String studentEmail, int reviewId){
    Iterable<Vote> votes = voteRepository.findAll();
    for(Vote v : votes ){
      if (v.getStudent().getEmail().equals(studentEmail) && v.getReview().getId() == reviewId){
        voteRepository.delete(v);
        break;
      }
    }
  }
}
