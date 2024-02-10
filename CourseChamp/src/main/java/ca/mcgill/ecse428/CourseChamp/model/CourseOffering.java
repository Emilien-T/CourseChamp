package ca.mcgill.ecse428.CourseChamp.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import java.util.*;

// line 37 "model.ump"
// line 100 "model.ump"
public class CourseOffering
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CourseOffering Attributes
  private int id;
  private String semester;

  //CourseOffering Associations
  private List<Professor> professors;
  private List<Course> courses;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CourseOffering(int aId, String aSemester, Professor... allProfessors)
  {
    id = aId;
    semester = aSemester;
    professors = new ArrayList<Professor>();
    boolean didAddProfessors = setProfessors(allProfessors);
    if (!didAddProfessors)
    {
      throw new RuntimeException("Unable to create CourseOffering, must have at least 1 professors. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    courses = new ArrayList<Course>();
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

  public boolean setSemester(String aSemester)
  {
    boolean wasSet = false;
    semester = aSemester;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public String getSemester()
  {
    return semester;
  }
  /* Code from template association_GetMany */
  public Professor getProfessor(int index)
  {
    Professor aProfessor = professors.get(index);
    return aProfessor;
  }

  public List<Professor> getProfessors()
  {
    List<Professor> newProfessors = Collections.unmodifiableList(professors);
    return newProfessors;
  }

  public int numberOfProfessors()
  {
    int number = professors.size();
    return number;
  }

  public boolean hasProfessors()
  {
    boolean has = professors.size() > 0;
    return has;
  }

  public int indexOfProfessor(Professor aProfessor)
  {
    int index = professors.indexOf(aProfessor);
    return index;
  }
  /* Code from template association_GetMany */
  public Course getCourse(int index)
  {
    Course aCourse = courses.get(index);
    return aCourse;
  }

  public List<Course> getCourses()
  {
    List<Course> newCourses = Collections.unmodifiableList(courses);
    return newCourses;
  }

  public int numberOfCourses()
  {
    int number = courses.size();
    return number;
  }

  public boolean hasCourses()
  {
    boolean has = courses.size() > 0;
    return has;
  }

  public int indexOfCourse(Course aCourse)
  {
    int index = courses.indexOf(aCourse);
    return index;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfProfessorsValid()
  {
    boolean isValid = numberOfProfessors() >= minimumNumberOfProfessors();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfProfessors()
  {
    return 1;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addProfessor(Professor aProfessor)
  {
    boolean wasAdded = false;
    if (professors.contains(aProfessor)) { return false; }
    professors.add(aProfessor);
    if (aProfessor.indexOfCourseOffering(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aProfessor.addCourseOffering(this);
      if (!wasAdded)
      {
        professors.remove(aProfessor);
      }
    }
    return wasAdded;
  }
  /* Code from template association_AddMStarToMany */
  public boolean removeProfessor(Professor aProfessor)
  {
    boolean wasRemoved = false;
    if (!professors.contains(aProfessor))
    {
      return wasRemoved;
    }

    if (numberOfProfessors() <= minimumNumberOfProfessors())
    {
      return wasRemoved;
    }

    int oldIndex = professors.indexOf(aProfessor);
    professors.remove(oldIndex);
    if (aProfessor.indexOfCourseOffering(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aProfessor.removeCourseOffering(this);
      if (!wasRemoved)
      {
        professors.add(oldIndex,aProfessor);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_SetMStarToMany */
  public boolean setProfessors(Professor... newProfessors)
  {
    boolean wasSet = false;
    ArrayList<Professor> verifiedProfessors = new ArrayList<Professor>();
    for (Professor aProfessor : newProfessors)
    {
      if (verifiedProfessors.contains(aProfessor))
      {
        continue;
      }
      verifiedProfessors.add(aProfessor);
    }

    if (verifiedProfessors.size() != newProfessors.length || verifiedProfessors.size() < minimumNumberOfProfessors())
    {
      return wasSet;
    }

    ArrayList<Professor> oldProfessors = new ArrayList<Professor>(professors);
    professors.clear();
    for (Professor aNewProfessor : verifiedProfessors)
    {
      professors.add(aNewProfessor);
      if (oldProfessors.contains(aNewProfessor))
      {
        oldProfessors.remove(aNewProfessor);
      }
      else
      {
        aNewProfessor.addCourseOffering(this);
      }
    }

    for (Professor anOldProfessor : oldProfessors)
    {
      anOldProfessor.removeCourseOffering(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addProfessorAt(Professor aProfessor, int index)
  {  
    boolean wasAdded = false;
    if(addProfessor(aProfessor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfProfessors()) { index = numberOfProfessors() - 1; }
      professors.remove(aProfessor);
      professors.add(index, aProfessor);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveProfessorAt(Professor aProfessor, int index)
  {
    boolean wasAdded = false;
    if(professors.contains(aProfessor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfProfessors()) { index = numberOfProfessors() - 1; }
      professors.remove(aProfessor);
      professors.add(index, aProfessor);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addProfessorAt(aProfessor, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCourses()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addCourse(Course aCourse)
  {
    boolean wasAdded = false;
    if (courses.contains(aCourse)) { return false; }
    courses.add(aCourse);
    if (aCourse.indexOfCourseOffering(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aCourse.addCourseOffering(this);
      if (!wasAdded)
      {
        courses.remove(aCourse);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeCourse(Course aCourse)
  {
    boolean wasRemoved = false;
    if (!courses.contains(aCourse))
    {
      return wasRemoved;
    }

    int oldIndex = courses.indexOf(aCourse);
    courses.remove(oldIndex);
    if (aCourse.indexOfCourseOffering(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aCourse.removeCourseOffering(this);
      if (!wasRemoved)
      {
        courses.add(oldIndex,aCourse);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCourseAt(Course aCourse, int index)
  {  
    boolean wasAdded = false;
    if(addCourse(aCourse))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCourses()) { index = numberOfCourses() - 1; }
      courses.remove(aCourse);
      courses.add(index, aCourse);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCourseAt(Course aCourse, int index)
  {
    boolean wasAdded = false;
    if(courses.contains(aCourse))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCourses()) { index = numberOfCourses() - 1; }
      courses.remove(aCourse);
      courses.add(index, aCourse);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCourseAt(aCourse, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<Professor> copyOfProfessors = new ArrayList<Professor>(professors);
    professors.clear();
    for(Professor aProfessor : copyOfProfessors)
    {
      aProfessor.removeCourseOffering(this);
    }
    ArrayList<Course> copyOfCourses = new ArrayList<Course>(courses);
    courses.clear();
    for(Course aCourse : copyOfCourses)
    {
      aCourse.removeCourseOffering(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "semester" + ":" + getSemester()+ "]";
  }
}