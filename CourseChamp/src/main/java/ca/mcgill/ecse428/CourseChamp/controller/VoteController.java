package ca.mcgill.ecse428.CourseChamp.controller;

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
import ca.mcgill.ecse428.CourseChamp.dto.VoteRequestDto;
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

@CrossOrigin(origins = "*")
@RestController
public class VoteController {
  @Autowired
  VoteService voteService;
  @Autowired
  StudentRepository studentRepository;
  @Autowired
  ReviewRepository reviewRepository;
  @Autowired
  VoteRepository voteRepository;

  @PostMapping("/upvote/{email}")
  public ResponseEntity<ReviewResponseDto> upvoteReview(@PathVariable String email, @Valid @RequestBody VoteRequestDto request){
    return createVote(email, request);
  }

  @PostMapping("/downvote/{email}")
  public ResponseEntity<ReviewResponseDto> downvoteReview(@PathVariable String email, @Valid @RequestBody VoteRequestDto request){
    return createVote(email, request);
  }

  @DeleteMapping("/deletevote")
  public void deleteReview(@RequestParam String studentEmail, @RequestParam int reviewId){
    voteService.deleteVote(studentEmail, reviewId);
  }

  public ResponseEntity<ReviewResponseDto> createVote(String email, VoteRequestDto request){
    Vote vote = request.toModel();
    vote.setStudent(studentRepository.findStudentByEmail(email));
    vote.setReview(reviewRepository.findReviewById(request.getReviewId()));
    

    if(vote.getStudent() == null){
      throw new CourseChampException(HttpStatus.BAD_REQUEST, "Student email: " + email);
    }

    if(vote.getReview() == null){
      throw new CourseChampException(HttpStatus.BAD_REQUEST, "Review not found");
    }

    Review review = voteService.createVote(vote);
    Iterable<Vote> votes = voteRepository.findAll();
    int upvotes = 0;
    int downvotes = 0;
    for(Vote v : votes){
      if(v.getReview().getId() == request.getReviewId()){
       if(v.getType()){
        upvotes++;
       }else{
        downvotes++;
       }
      }
    }

    ReviewResponseDto response = new ReviewResponseDto(review);
    response.setUpvotes(upvotes);
    response.setDownvotes(downvotes);
    return new ResponseEntity<ReviewResponseDto>(response, HttpStatus.CREATED);
  }
}
