package ca.mcgill.ecse428.CourseChamp.dto;

import ca.mcgill.ecse428.CourseChamp.model.Vote;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

public class VoteRequestDto {
  @Email(message = "Email must follow this format xxx@email.address")
  @Schema(example= "student@email.com", description = "Email linked to the account of the admin")
  private String studentEmail;

  private int reviewId;
  private boolean type;

  public Vote toModel(){
    Vote vote = new Vote();
    vote.setType(type);
    return vote;
  }

  public void setStudentEmail(String studentEmail){
    this.studentEmail = studentEmail;
  }

  public String getStudentEmail(){
    return this.studentEmail;
  }

  public void setReviewId(int reviewId){
    this.reviewId = reviewId;
  }

  public int getReviewId(){
    return this.reviewId;
  }

  public void setType(boolean type){
    this.type = type;
  }

  public boolean getType(){
    return this.type;
  }
}
