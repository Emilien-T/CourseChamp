package ca.mcgill.ecse428.CourseChamp.dto;

import ca.mcgill.ecse428.CourseChamp.model.Review;
import io.swagger.v3.oas.annotations.media.Schema;


public class ReviewResponseDto {

    private int id;
    private int rating;
    private String text;

    public ReviewResponseDto() {
    }

    public ReviewResponseDto(int id, int rating, String text) {
        this.id = id;
        this.rating = rating;
        this.text = text;
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

    // toString, hashCode, equals methods as needed
}
