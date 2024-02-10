package ca.mcgill.ecse428.CourseChamp.model;

import jakarta.persistence.Entity;

@Entity
public class Admin extends Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Admin(String aEmail, String aPassword)
  {
    super(aEmail, aPassword);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}