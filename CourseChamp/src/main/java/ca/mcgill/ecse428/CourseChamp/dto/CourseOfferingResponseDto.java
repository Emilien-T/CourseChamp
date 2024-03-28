package ca.mcgill.ecse428.CourseChamp.dto;

import ca.mcgill.ecse428.CourseChamp.model.CourseOffering;
import io.swagger.v3.oas.annotations.media.Schema;

public class CourseOfferingResponseDto {

    @Schema(example = "F2024", description = "Semester of the course offering")
    private String semester;

    @Schema(example = "COMP428", description = "Course code of the offering")
    private String courseCode;

    
    public CourseOfferingResponseDto(CourseOffering courseOffering) {
        this.semester = courseOffering.getSemester();
        this.courseCode = courseOffering.getCourse().getCourseCode();
    }

    public CourseOfferingResponseDto() {}

    public String getSemester() {
        return semester;
    }

    public String getCourseCode() {
        return courseCode;
    }
}