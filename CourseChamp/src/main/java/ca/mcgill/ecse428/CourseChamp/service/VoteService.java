package ca.mcgill.ecse428.CourseChamp.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import ca.mcgill.ecse428.CourseChamp.exception.CourseChampException;

// import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
// import ca.mcgill.ecse428.CourseChamp.model.Review;
// import ca.mcgill.ecse428.CourseChamp.model.Student;
// import ca.mcgill.ecse428.CourseChamp.model.Vote;
// import ca.mcgill.ecse428.CourseChamp.repository.ReviewRepository;
// import ca.mcgill.ecse428.CourseChamp.repository.StudentRepository;
// import ca.mcgill.ecse428.CourseChamp.repository.VoteRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse428.CourseChamp.dto.ReviewResponseDto;
import ca.mcgill.ecse428.CourseChamp.exception.CourseChampException;
import ca.mcgill.ecse428.CourseChamp.model.Review;
import ca.mcgill.ecse428.CourseChamp.model.Student;
import ca.mcgill.ecse428.CourseChamp.model.Vote;
import ca.mcgill.ecse428.CourseChamp.repository.ReviewRepository;
import ca.mcgill.ecse428.CourseChamp.repository.StudentRepository;
import ca.mcgill.ecse428.CourseChamp.repository.VoteRepository;
import ca.mcgill.ecse428.CourseChamp.service.VoteService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@Service
public class VoteService {
  @Autowired
  VoteRepository voteRepository;
  @Autowired
  StudentRepository studentRepository;
  @Autowired
  ReviewRepository reviewRepository;

  public Review createVote(String email, int id, boolean type){
    Student student = studentRepository.findStudentByEmail(email);
    Review review = reviewRepository.findReviewById(id);

    if(student == null){
      throw new CourseChampException(HttpStatus.BAD_REQUEST, "Student email: " + email);
    }

    if(review == null){
      throw new CourseChampException(HttpStatus.BAD_REQUEST, "Review not found");
    }

    Vote vote = voteRepository.findByReviewAndStudent(review, student);

    // Check if the student has already voted on the review
    if(vote != null){
      // If the student upvotes/downvotes again, update the review's upvotes and downvotes
      if (vote.getType() == type){
        if (vote.getType()){
          review.setUpvotes(review.getUpvotes() - 1);
        }else{
          review.setDownvotes(review.getDownvotes() + 1);
        }
        voteRepository.delete(vote);
      }
      // If students changes from an upvote to a downvote or vice versa, update the review's upvotes and downvotes
      else {
        if (type){
          review.setUpvotes(review.getUpvotes() + 1);
          review.setDownvotes(review.getDownvotes() - 1);
        }else{
          review.setDownvotes(review.getDownvotes() + 1);
          review.setUpvotes(review.getUpvotes() - 1);
        }
        voteRepository.save(vote);
      }
      review = reviewRepository.save(review); 
      
      return review;
    }
    // If the student has not voted on the review, create a new votet
    //  Update upvotes and downvotes for a review
    if (type){
      review.setUpvotes(review.getUpvotes() + 1);
    } else{
      review.setDownvotes(review.getDownvotes() + 1);
    }
    review = reviewRepository.save(review);
  
    Vote newVote = new Vote();
    newVote.setType(type);
    newVote.setStudent(student);
    newVote.setReview(review);
    
    
    newVote = voteRepository.save(newVote);
 
    
    return review;
  }
  
  // public Review createVote(Vote vote){
  //   Iterable<Vote> votes = voteRepository.findAll();
  //   for(Vote v : votes ){
  //     if (v.getStudent().getEmail().equals(vote.getStudent().getEmail()) && v.getReview().getId() == vote.getReview().getId()){
  //       voteRepository.delete(v);
  //       return vote.getReview();
  //     }
  //   }
  //   voteRepository.save(vote);
  //   return vote.getReview();
  // }

  public void deleteVote(String studentEmail, int reviewId){
   
    Student student = studentRepository.findStudentByEmail(studentEmail);
    Review review = reviewRepository.findReviewById(reviewId);
    Vote vote = voteRepository.findByReviewAndStudent(review, student);
    if (vote == null){
      throw new CourseChampException(HttpStatus.BAD_REQUEST, "Student email: " + studentEmail + " has not voted on review " + reviewId);
    }
    if (vote.getType()){ 
      review.setUpvotes(review.getUpvotes() - 1);
    } else{
      review.setDownvotes(review.getDownvotes() - 1);
    }
    review = reviewRepository.save(review);
    voteRepository.delete(vote);
    // createVote(studentEmail, reviewId, vote.getType());
    // Iterable<Vote> votes = voteRepository.findAll();
    // for(Vote v : votes ){
    //   if (v.getStudent().getEmail().equals(studentEmail) && v.getReview().getId() == reviewId){
    //     voteRepository.delete(v);
    //     break;
    //   }
    // }
  }
}
