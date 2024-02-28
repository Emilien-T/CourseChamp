package ca.mcgill.ecse428.CourseChamp.dto;

import ca.mcgill.ecse428.CourseChamp.model.Vote;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class VoteRequestDto {

  private int reviewId;
  private boolean type;

  public VoteRequestDto(){

  }
  public Vote toModel(){
    Vote vote = new Vote();
    vote.setType(type);
    return vote;
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
