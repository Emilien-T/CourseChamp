package ca.mcgill.ecse428.CourseChamp.dto;


import ca.mcgill.ecse428.CourseChamp.model.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public class ReviewRequestDto {
   
    @NotBlank(message = "Rating cannot be blank.")
    @Pattern(regexp = "^[0-5]{1}$", message = "Rating must be a number form 1 to 5")
    @Schema(example = "1", description = "Rating of the course", required = true)
    private int rating;
    private int id;

    @NotBlank(message = "Text cannot be blank.")
    @Schema(example = "This class is the best", description = "Review on the course", required = true)
    private String text;


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


    public Review toModel() {
        return new Review(id, rating, text, null, null);
    }
}
