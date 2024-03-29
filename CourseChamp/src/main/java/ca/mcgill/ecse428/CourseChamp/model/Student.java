package ca.mcgill.ecse428.CourseChamp.model;

import jakarta.persistence.Entity;

/**
 * Class that is part of the domain model of the CourseChamp System
 * This class contains information that is unique to student accounts
 * 
 * A major part of this class is auto-generated by umple
 * This class is also JPA anotated for ORM
 */
@Entity
public class Student extends Account {

  // ------------------------
  // ENUMERATIONS
  // ------------------------

  public enum Major {
    Software, Computer, Electrical
  }

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // Student Attributes
  private Major major;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------
  public Student() {
  }

  public Student(String aEmail, String aUsername, String aPassword, Major aMajor) {
    super(aEmail, aUsername, aPassword);
    major = aMajor;
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setMajor(Major aMajor) {
    boolean wasSet = false;
    major = aMajor;
    wasSet = true;
    return wasSet;
  }

  public Major getMajor() {
    return major;
  }

  public void delete() {
    super.delete();
  }

  public String toString() {
    return super.toString() + "[" + "]" + System.getProperties().getProperty("line.separator") +
        "  " + "major" + "="
        + (getMajor() != null ? !getMajor().equals(this) ? getMajor().toString().replaceAll("  ", "    ") : "this"
            : "null");
  }
}