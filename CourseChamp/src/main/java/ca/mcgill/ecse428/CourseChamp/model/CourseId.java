package ca.mcgill.ecse428.CourseChamp.model;

import java.io.Serializable; 

public class CourseId implements Serializable {
    private String department;

    private int courseCode;

    // default constructor

    public CourseId(String department, int courseCode) {
        this.department = department;
        this.courseCode = courseCode;
    }

    // equals() and hashCode()
}