package ca.mcgill.ecse428.CourseChamp.dto;


public class VoteResponseDto {
  private int id;
  private String email;

  private int reviewId;
  private boolean type;
  public VoteResponseDto(){

  }

  public VoteResponseDto(int id, String email, int reviewId, boolean type){
    this.id = id;
    this.email = email;
    this.reviewId = reviewId;
    this.type = type;
  }

  public void setId(int id){
    this.id = id;
  }

  public void setEmail(String email){
    this.email = email;
  }

  public void setReviewId(int reviewId){
    this.reviewId = reviewId;
  }

  public void setType(boolean type){
    this.type = type;
  }

  public int getId(){
    return this.id;
  }

  public String getEmail(){
    return this.email;
  }

  public int getReviewId(){
    return this.reviewId;
  }

  public boolean getType(){
    return this.type;
  }
}
