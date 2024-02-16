package ca.mcgill.ecse428.CourseChamp.dto;

import ca.mcgill.ecse428.CourseChamp.model.Student;
import ca.mcgill.ecse428.CourseChamp.model.Student.Major;
import io.swagger.v3.oas.annotations.media.Schema;

public class StudentResponseDto {

    @Schema(example= "student@email.com", description = "Email linked to the account of the student")
    private String email;
    @Schema(example= "Password1!", description = "Password linked to the account of the student")
    private String password;
    @Schema(example= "student", description = "Username of the student")
    private String username;
    @Schema(example= "Software", description = "Major of the student")
    private Major major;

    public StudentResponseDto(Student student) {
        this.email = student.getEmail();
        this.password = student.getPassword();
        this.username = student.getUsername();
        this.major = student.getMajor();
    }

    StudentResponseDto() {}

    public String getEmail()
    { return email; }

    public String getPassword()
    { return password; }

    public String getName()
    { return username; }

    public Major getMajor()
    { return major; }
}
