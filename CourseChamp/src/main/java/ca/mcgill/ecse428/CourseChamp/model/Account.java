package ca.mcgill.ecse428.CourseChamp.model;

import jakarta.persistence.*;
import jakarta.persistence.Inheritance;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Account Attributes
  @Id
  private String email;
  private String password;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Account(String aEmail, String aPassword)
  {
    email = aEmail;
    password = aPassword;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "email" + ":" + getEmail()+ "," +
            "password" + ":" + getPassword()+ "]";
  }
}