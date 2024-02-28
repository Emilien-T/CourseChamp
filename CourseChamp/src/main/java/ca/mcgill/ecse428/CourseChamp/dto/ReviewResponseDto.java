package ca.mcgill.ecse428.CourseChamp.dto;

import ca.mcgill.ecse428.CourseChamp.model.Review;


public class ReviewResponseDto {

    private int id;
    private String courseCode;
    private String semester;
    private int rating;
    private String text;
    private int upvotes;
    private int downvotes;

    public ReviewResponseDto() {
    }

    public ReviewResponseDto(int id, String courseCode, String semester, int rating, String text, int upvotes, int downvotes) {
        this.id = id;
        this.courseCode = courseCode;
        this.semester = semester;
        this.rating = rating;
        this.text = text;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
    }

    // Constructor to directly map from the Review model
    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.courseCode = review.getCourseOffering().getCourse().getCourseCode();
        this.semester = review.getCourseOffering().getSemester();
        this.rating = review.getRating();
        this.text = review.getText();
        this.upvotes = review.getUpvotes();
        this.downvotes = review.getDownvotes();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUpvotes(int upvotes){
        this.upvotes = upvotes;
    }

    public void setDownvotes(int downvotes){
        this.downvotes = downvotes;
    }

    public int getUpvotes(){
        return this.upvotes;
    }

    public int getDownvotes(){
        return this.downvotes;
    }

    public String getSemester(){
        return this.semester;
    }

    public String getCourseCode(){
        return this.courseCode;
    }

    public void setSemester(String semester){
        this.semester = semester;
    }

    public void setCourseCode(String courseCode){
        this.courseCode = courseCode;
    }
    // toString, hashCode, equals methods as needed
}
