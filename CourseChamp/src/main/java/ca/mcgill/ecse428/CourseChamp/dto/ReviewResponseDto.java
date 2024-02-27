package ca.mcgill.ecse428.CourseChamp.dto;

import ca.mcgill.ecse428.CourseChamp.model.Review;


public class ReviewResponseDto {

    private int id;
    private int rating;
    private String text;
    private int upvotes;
    private int downvotes;

    public ReviewResponseDto() {
    }

    public ReviewResponseDto(int id, int rating, String text, int upvotes, int downvotes) {
        this.id = id;
        this.rating = rating;
        this.text = text;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
    }

    // Constructor to directly map from the Review model
    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.rating = review.getRating();
        this.text = review.getText();
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

    // toString, hashCode, equals methods as needed
}
