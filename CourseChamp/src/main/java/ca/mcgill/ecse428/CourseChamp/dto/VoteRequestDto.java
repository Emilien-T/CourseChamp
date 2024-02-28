package ca.mcgill.ecse428.CourseChamp.dto;

import ca.mcgill.ecse428.CourseChamp.model.Vote;
import io.swagger.v3.oas.annotations.media.Schema;

public class VoteRequestDto {
  
  @Schema(example= "student@email.com", description = "Email linked to the account of the admin")
  private String email;

  private int reviewId;
  private boolean type;

  public Vote toModel(){
    Vote vote = new Vote();
    vote.setType(type);
    return vote;
  }

  public void setEmail(String email){
    this.email = email;
  }

  public String getEmail(){
    return this.email;
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
