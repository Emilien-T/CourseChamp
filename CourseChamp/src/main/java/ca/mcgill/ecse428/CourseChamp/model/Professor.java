/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import java.util.*;

// line 42 "model.ump"
// line 107 "model.ump"
public class Professor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Professor Associations
  private List<CourseOffering> courseOfferings;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Professor()
  {
    courseOfferings = new ArrayList<CourseOffering>();
  }

  //------------------------
  // INTERFACE
  //------------------------
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

}