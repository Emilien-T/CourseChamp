package ca.mcgill.ecse428.CourseChamp.model;

import org.hibernate.annotations.IdGeneratorType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Review
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Review Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  private int rating;
  private String text;

  //Review Associations
  @ManyToOne
  private Student student;
  @ManyToOne
  private CourseOffering courseOffering;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Review(int aId, int aRating, String aText, Student aStudent, CourseOffering aCourseOffering)
  {
    id = aId;
    rating = aRating;
    text = aText;
    if (!setStudent(aStudent))
    {
      throw new RuntimeException("Unable to create Review due to aStudent. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setCourseOffering(aCourseOffering))
    {
      throw new RuntimeException("Unable to create Review due to aCourseOffering. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setRating(int aRating)
  {
    boolean wasSet = false;
    rating = aRating;
    wasSet = true;
    return wasSet;
  }

  public boolean setText(String aText)
  {
    boolean wasSet = false;
    text = aText;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public int getRating()
  {
    return rating;
  }

  public String getText()
  {
    return text;
  }
  /* Code from template association_GetOne */
  public Student getStudent()
  {
    return student;
  }
  /* Code from template association_GetOne */
  public CourseOffering getCourseOffering()
  {
    return courseOffering;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setStudent(Student aNewStudent)
  {
    boolean wasSet = false;
    if (aNewStudent != null)
    {
      student = aNewStudent;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCourseOffering(CourseOffering aNewCourseOffering)
  {
    boolean wasSet = false;
    if (aNewCourseOffering != null)
    {
      courseOffering = aNewCourseOffering;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    student = null;
    courseOffering = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "rating" + ":" + getRating()+ "," +
            "text" + ":" + getText()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "student = "+(getStudent()!=null?Integer.toHexString(System.identityHashCode(getStudent())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "courseOffering = "+(getCourseOffering()!=null?Integer.toHexString(System.identityHashCode(getCourseOffering())):"null");
  }
}