package ca.mcgill.ecse428.CourseChamp.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/



// line 11 "model.ump"
// line 78 "model.ump"
public class Student extends Account
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Major { Software, Computer, Electrical }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Student Attributes
  private Major major;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Student(String aEmail, String aPassword, Major aMajor)
  {
    super(aEmail, aPassword);
    major = aMajor;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMajor(Major aMajor)
  {
    boolean wasSet = false;
    major = aMajor;
    wasSet = true;
    return wasSet;
  }

  public Major getMajor()
  {
    return major;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "major" + "=" + (getMajor() != null ? !getMajor().equals(this)  ? getMajor().toString().replaceAll("  ","    ") : "this" : "null");
  }
}