package ca.mcgill.ecse428.CourseChamp.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Vote
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Vote Attributes
  private int id;
  private boolean type;

  //Vote Associations
  @ManyToOne
  private Student student;
  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Review review;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Vote(int aId, boolean aType, Student aStudent, Review aReview)
  {
    id = aId;
    type = aType;
    if (!setStudent(aStudent))
    {
      throw new RuntimeException("Unable to create Vote due to aStudent. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setReview(aReview))
    {
      throw new RuntimeException("Unable to create Vote due to aReview. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public boolean setType(boolean aType)
  {
    boolean wasSet = false;
    type = aType;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public boolean getType()
  {
    return type;
  }
  /* Code from template association_GetOne */
  public Student getStudent()
  {
    return student;
  }
  /* Code from template association_GetOne */
  public Review getReview()
  {
    return review;
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
  public boolean setReview(Review aNewReview)
  {
    boolean wasSet = false;
    if (aNewReview != null)
    {
      review = aNewReview;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    student = null;
    review = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "type" + ":" + getType()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "student = "+(getStudent()!=null?Integer.toHexString(System.identityHashCode(getStudent())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "review = "+(getReview()!=null?Integer.toHexString(System.identityHashCode(getReview())):"null");
  }
}