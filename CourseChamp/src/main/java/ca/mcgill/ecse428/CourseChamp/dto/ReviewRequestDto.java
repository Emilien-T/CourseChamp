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

    @NotBlank(message = "Text cannot be blank.")
    @Schema(example = "This class is the best", description = "Review on the course", required = true)
    private String text;

    // associations
    private String studentEmail;
    @NotBlank(message = "Course Code cannot be blank.")
    private String courseCode;
    @NotBlank(message = "Semester cannot be blank.")
    private String semester;


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

    public void setCourseCode(String courseCode){
        this.courseCode = courseCode;
    }

    public String getCourseCode(){
        return this.courseCode;
    }

    public void setStudentEmail(String studentEmail){
        this.studentEmail = studentEmail;
    }

    public String getStudentEmail(){
        return this.studentEmail;
    }

    public void setSemester(String semester){
        this.semester = semester;
    }

    public String getSemester(){
        return this.semester;
    }


    public Review toModel() {
        Review review = new Review();
        review.setRating(rating);
        review.setText(text);
        return review;
    }
}


