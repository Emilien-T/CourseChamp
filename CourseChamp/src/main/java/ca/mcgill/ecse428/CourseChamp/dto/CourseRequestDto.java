package ca.mcgill.ecse428.CourseChamp.dto;

import ca.mcgill.ecse428.CourseChamp.model.Course;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import io.swagger.v3.oas.annotations.media.Schema;

public class CourseRequestDto {

    @NotBlank(message = "Department cannot be blank.")
    @Pattern(regexp = "^[A-Za-z]{4}$", message = "Department must be a four-letter alphabetic string.")
    @Schema(example = "COMP", description = "Department of the course", required = true)
    private String department;

    @PositiveOrZero(message = "Course number must be a positive 3-digit integer.")
    @Min(value = 100, message = "Course number must be a positive 3-digit integer.")
    @Max(value = 999, message = "Course number must be a positive 3-digit integer.")
    @Schema(example = "428", description = "Course number", required = true)
    private int courseNumber;

    @NotBlank(message = "Name cannot be blank.")
    @Schema(example = "Software Engineering", description = "Name of the course", required = true)
    private String name;

    @NotBlank(message = "Description cannot be blank.")
    @Schema(example = "Introduction to software engineering principles and practices.", description = "Description of the course", required = true)
    private String description;

    @Schema(example = "link-to-syllabus.pdf", description = "Link to the syllabus of the course")
    private String syllabus;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department.toUpperCase(); // Convert department to uppercase
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public Course toModel() {
        return new Course(department, courseNumber, name, description, syllabus);
    }
}
