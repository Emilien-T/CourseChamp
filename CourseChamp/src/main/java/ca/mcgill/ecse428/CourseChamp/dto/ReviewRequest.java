package ca.mcgill.ecse428.CourseChamp.dto;

import ca.mcgill.ecse428.CourseChamp.model.Review;
import io.swagger.v3.oas.annotations.media.Schema;


public class ReviewRequest {
   
    @NotBlank(message = "Rating cannot be blank.")
    @Pattern(regexp = "^[0-5]{1}$", message = "Rating must be a number form 1 to 5")
    @Schema(example = "1", description = "Rating of the course", required = true)
    private String rating;

    @NotBlank(message = "Text cannot be blank.")
    @Schema(example = "This class is the best", description = "Review on the course", required = true)
    private String text;


    public int getId() {
        return id;
    }

    public void setId(String id) {
        this.Id = id; 
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.Rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public Review toModel() {
        return new Review(id, rating, text);
    }
}



