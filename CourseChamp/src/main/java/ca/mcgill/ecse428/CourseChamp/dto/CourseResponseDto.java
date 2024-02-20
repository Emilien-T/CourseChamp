package ca.mcgill.ecse428.CourseChamp.dto;

import ca.mcgill.ecse428.CourseChamp.model.Course;
import io.swagger.v3.oas.annotations.media.Schema;

public class CourseResponseDto {

    @Schema(example = "COMP", description = "Department of the course")
    private String department;

    @Schema(example = "428", description = "Course number")
    private int courseNumber;

    @Schema(example = "COMP428", description = "Course code")
    private String courseCode;

    @Schema(example = "Software Engineering", description = "Name of the course")
    private String name;

    @Schema(example = "Introduction to software engineering principles and practices.", description = "Description of the course")
    private String description;

    @Schema(example = "link-to-syllabus.pdf", description = "Link to the syllabus of the course")
    private String syllabus;

    
    public CourseResponseDto(Course course) {
        this.department = course.getDepartment();
        this.courseNumber = course.getCourseNumber();
        this.courseCode = course.getCourseCode();
        this.name = course.getName();
        this.description = course.getDescription();
        this.syllabus = course.getSyllabus();
    }

    public CourseResponseDto() {}

    public String getDepartment() {
        return department;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSyllabus() {
        return syllabus;
    }
}
