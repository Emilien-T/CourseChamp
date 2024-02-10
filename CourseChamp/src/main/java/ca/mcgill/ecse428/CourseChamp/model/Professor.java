package ca.mcgill.ecse428.CourseChamp.model;

import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Professor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Professor Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  private String name;

  //Professor Associations
  @ManyToMany(mappedBy = "courseOfferings")
  private List<CourseOffering> courseOfferings;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Professor(int aId, String aName)
  {
    id = aId;
    name = aName;
    courseOfferings = new ArrayList<CourseOffering>();
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

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template association_GetMany */
  public CourseOffering getCourseOffering(int index)
  {
    CourseOffering aCourseOffering = courseOfferings.get(index);
    return aCourseOffering;
  }

  public List<CourseOffering> getCourseOfferings()
  {
    List<CourseOffering> newCourseOfferings = Collections.unmodifiableList(courseOfferings);
    return newCourseOfferings;
  }

  public int numberOfCourseOfferings()
  {
    int number = courseOfferings.size();
    return number;
  }

  public boolean hasCourseOfferings()
  {
    boolean has = courseOfferings.size() > 0;
    return has;
  }

  public int indexOfCourseOffering(CourseOffering aCourseOffering)
  {
    int index = courseOfferings.indexOf(aCourseOffering);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCourseOfferings()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addCourseOffering(CourseOffering aCourseOffering)
  {
    boolean wasAdded = false;
    if (courseOfferings.contains(aCourseOffering)) { return false; }
    courseOfferings.add(aCourseOffering);
    if (aCourseOffering.indexOfProfessor(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aCourseOffering.addProfessor(this);
      if (!wasAdded)
      {
        courseOfferings.remove(aCourseOffering);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeCourseOffering(CourseOffering aCourseOffering)
  {
    boolean wasRemoved = false;
    if (!courseOfferings.contains(aCourseOffering))
    {
      return wasRemoved;
    }

    int oldIndex = courseOfferings.indexOf(aCourseOffering);
    courseOfferings.remove(oldIndex);
    if (aCourseOffering.indexOfProfessor(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aCourseOffering.removeProfessor(this);
      if (!wasRemoved)
      {
        courseOfferings.add(oldIndex,aCourseOffering);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCourseOfferingAt(CourseOffering aCourseOffering, int index)
  {  
    boolean wasAdded = false;
    if(addCourseOffering(aCourseOffering))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCourseOfferings()) { index = numberOfCourseOfferings() - 1; }
      courseOfferings.remove(aCourseOffering);
      courseOfferings.add(index, aCourseOffering);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCourseOfferingAt(CourseOffering aCourseOffering, int index)
  {
    boolean wasAdded = false;
    if(courseOfferings.contains(aCourseOffering))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCourseOfferings()) { index = numberOfCourseOfferings() - 1; }
      courseOfferings.remove(aCourseOffering);
      courseOfferings.add(index, aCourseOffering);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCourseOfferingAt(aCourseOffering, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<CourseOffering> copyOfCourseOfferings = new ArrayList<CourseOffering>(courseOfferings);
    courseOfferings.clear();
    for(CourseOffering aCourseOffering : copyOfCourseOfferings)
    {
      if (aCourseOffering.numberOfProfessors() <= CourseOffering.minimumNumberOfProfessors())
      {
        aCourseOffering.delete();
      }
      else
      {
        aCourseOffering.removeProfessor(this);
      }
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "name" + ":" + getName()+ "]";
  }
}