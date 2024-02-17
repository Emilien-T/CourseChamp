package ca.mcgill.ecse428.CourseChamp.model;

import jakarta.persistence.Entity;

/**
 * Class that is part of the domain model of the CourseChamp System
 * This class contains information that is unique to admin accounts
 * 
 * A major part of this class is auto-generated by umple
 * This class is also JPA anotated for ORM
 */
@Entity
public class Admin extends Account {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // ------------------------
  // CONSTRUCTOR
  // ------------------------
  public Admin() {
  }

  public Admin(String aEmail, String aUsername, String aPassword) {
    super(aEmail, aUsername, aPassword);
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public void delete() {
    super.delete();
  }

}