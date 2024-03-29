package ca.mcgill.ecse428.CourseChamp.model;

import jakarta.persistence.*;

/**
 * Abstract class that is part of the domain model of the CourseChamp System
 * This abstract class contains information common to all the account types for all types of users
 * 
 * A major part of this class is auto-generated by umple
 * This class is also JPA anotated for ORM
 */
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
  private String username;
  private String password;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Account(){}
  public Account(String aEmail, String aUsername, String aPassword)
  {
    email = aEmail;
    username = aUsername;
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

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    username = aUsername;
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

  public String getUsername()
  {
    return username;
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
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "]";
  }
}