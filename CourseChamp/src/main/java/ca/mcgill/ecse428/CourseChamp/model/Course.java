package ca.mcgill.ecse428.CourseChamp.model;

import java.util.*;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Course
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Course Attributes
  @Id
  private String courseCode;
  private String name;
  private String description;
  private String syllabus;

  //Course Associations
  @ManyToMany
  @JoinTable(
    name = "Course_CourseOfferings",
    joinColumns = { @JoinColumn(name = "courseCode")},
    inverseJoinColumns = {@JoinColumn(name = "id")}
  )
  private List<CourseOffering> courseOfferings;
  @ManyToMany
  @JoinTable(name = "Prerequesite_Prerequirement",
    joinColumns = @JoinColumn(name = "Prerequesite"),
    inverseJoinColumns = @JoinColumn(name = "Prerequirement")
  )
  private List<Course> Prerequesite;
  @ManyToMany
  @JoinTable(name = "Corequesite_Corequirement",
    joinColumns = @JoinColumn(name = "Corequesite"),
    inverseJoinColumns = @JoinColumn(name = "Corequeriment")
  )
  private List<Course> Corequesite;
  @ManyToMany(mappedBy = "Prerequesite")
  private List<Course> Prerequirement;
  @ManyToMany(mappedBy = "Corequesite")
  private List<Course> Corequirement;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Course(String aCourseCode, String aName, String aDescription, String aSyllabus)
  {
    courseCode = aCourseCode;
    name = aName;
    description = aDescription;
    syllabus = aSyllabus;
    courseOfferings = new ArrayList<CourseOffering>();
    Prerequesite = new ArrayList<Course>();
    Corequesite = new ArrayList<Course>();
    Prerequirement = new ArrayList<Course>();
    Corequirement = new ArrayList<Course>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCourseCode(String aCourseCode)
  {
    boolean wasSet = false;
    courseCode = aCourseCode;
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

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setSyllabus(String aSyllabus)
  {
    boolean wasSet = false;
    syllabus = aSyllabus;
    wasSet = true;
    return wasSet;
  }

  public String getCourseCode()
  {
    return courseCode;
  }

  public String getName()
  {
    return name;
  }

  public String getDescription()
  {
    return description;
  }

  public String getSyllabus()
  {
    return syllabus;
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
  /* Code from template association_GetMany */
  public Course getPrerequesite(int index)
  {
    Course aPrerequesite = Prerequesite.get(index);
    return aPrerequesite;
  }

  public List<Course> getPrerequesite()
  {
    List<Course> newPrerequesite = Collections.unmodifiableList(Prerequesite);
    return newPrerequesite;
  }

  public int numberOfPrerequesite()
  {
    int number = Prerequesite.size();
    return number;
  }

  public boolean hasPrerequesite()
  {
    boolean has = Prerequesite.size() > 0;
    return has;
  }

  public int indexOfPrerequesite(Course aPrerequesite)
  {
    int index = Prerequesite.indexOf(aPrerequesite);
    return index;
  }
  /* Code from template association_GetMany */
  public Course getCorequesite(int index)
  {
    Course aCorequesite = Corequesite.get(index);
    return aCorequesite;
  }

  public List<Course> getCorequesite()
  {
    List<Course> newCorequesite = Collections.unmodifiableList(Corequesite);
    return newCorequesite;
  }

  public int numberOfCorequesite()
  {
    int number = Corequesite.size();
    return number;
  }

  public boolean hasCorequesite()
  {
    boolean has = Corequesite.size() > 0;
    return has;
  }

  public int indexOfCorequesite(Course aCorequesite)
  {
    int index = Corequesite.indexOf(aCorequesite);
    return index;
  }
  /* Code from template association_GetMany */
  public Course getPrerequirement(int index)
  {
    Course aPrerequirement = Prerequirement.get(index);
    return aPrerequirement;
  }

  public List<Course> getPrerequirement()
  {
    List<Course> newPrerequirement = Collections.unmodifiableList(Prerequirement);
    return newPrerequirement;
  }

  public int numberOfPrerequirement()
  {
    int number = Prerequirement.size();
    return number;
  }

  public boolean hasPrerequirement()
  {
    boolean has = Prerequirement.size() > 0;
    return has;
  }

  public int indexOfPrerequirement(Course aPrerequirement)
  {
    int index = Prerequirement.indexOf(aPrerequirement);
    return index;
  }
  /* Code from template association_GetMany */
  public Course getCorequirement(int index)
  {
    Course aCorequirement = Corequirement.get(index);
    return aCorequirement;
  }

  public List<Course> getCorequirement()
  {
    List<Course> newCorequirement = Collections.unmodifiableList(Corequirement);
    return newCorequirement;
  }

  public int numberOfCorequirement()
  {
    int number = Corequirement.size();
    return number;
  }

  public boolean hasCorequirement()
  {
    boolean has = Corequirement.size() > 0;
    return has;
  }

  public int indexOfCorequirement(Course aCorequirement)
  {
    int index = Corequirement.indexOf(aCorequirement);
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
    if (aCourseOffering.indexOfCourse(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aCourseOffering.addCourse(this);
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
    if (aCourseOffering.indexOfCourse(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aCourseOffering.removeCourse(this);
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPrerequesite()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addPrerequesite(Course aPrerequesite)
  {
    boolean wasAdded = false;
    if (Prerequesite.contains(aPrerequesite)) { return false; }
    Prerequesite.add(aPrerequesite);
    if (aPrerequesite.indexOfPrerequirement(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aPrerequesite.addPrerequirement(this);
      if (!wasAdded)
      {
        Prerequesite.remove(aPrerequesite);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removePrerequesite(Course aPrerequesite)
  {
    boolean wasRemoved = false;
    if (!Prerequesite.contains(aPrerequesite))
    {
      return wasRemoved;
    }

    int oldIndex = Prerequesite.indexOf(aPrerequesite);
    Prerequesite.remove(oldIndex);
    if (aPrerequesite.indexOfPrerequirement(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aPrerequesite.removePrerequirement(this);
      if (!wasRemoved)
      {
        Prerequesite.add(oldIndex,aPrerequesite);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPrerequesiteAt(Course aPrerequesite, int index)
  {  
    boolean wasAdded = false;
    if(addPrerequesite(aPrerequesite))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPrerequesite()) { index = numberOfPrerequesite() - 1; }
      Prerequesite.remove(aPrerequesite);
      Prerequesite.add(index, aPrerequesite);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePrerequesiteAt(Course aPrerequesite, int index)
  {
    boolean wasAdded = false;
    if(Prerequesite.contains(aPrerequesite))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPrerequesite()) { index = numberOfPrerequesite() - 1; }
      Prerequesite.remove(aPrerequesite);
      Prerequesite.add(index, aPrerequesite);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPrerequesiteAt(aPrerequesite, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCorequesite()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addCorequesite(Course aCorequesite)
  {
    boolean wasAdded = false;
    if (Corequesite.contains(aCorequesite)) { return false; }
    Corequesite.add(aCorequesite);
    if (aCorequesite.indexOfCorequirement(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aCorequesite.addCorequirement(this);
      if (!wasAdded)
      {
        Corequesite.remove(aCorequesite);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeCorequesite(Course aCorequesite)
  {
    boolean wasRemoved = false;
    if (!Corequesite.contains(aCorequesite))
    {
      return wasRemoved;
    }

    int oldIndex = Corequesite.indexOf(aCorequesite);
    Corequesite.remove(oldIndex);
    if (aCorequesite.indexOfCorequirement(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aCorequesite.removeCorequirement(this);
      if (!wasRemoved)
      {
        Corequesite.add(oldIndex,aCorequesite);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCorequesiteAt(Course aCorequesite, int index)
  {  
    boolean wasAdded = false;
    if(addCorequesite(aCorequesite))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCorequesite()) { index = numberOfCorequesite() - 1; }
      Corequesite.remove(aCorequesite);
      Corequesite.add(index, aCorequesite);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCorequesiteAt(Course aCorequesite, int index)
  {
    boolean wasAdded = false;
    if(Corequesite.contains(aCorequesite))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCorequesite()) { index = numberOfCorequesite() - 1; }
      Corequesite.remove(aCorequesite);
      Corequesite.add(index, aCorequesite);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCorequesiteAt(aCorequesite, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPrerequirement()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addPrerequirement(Course aPrerequirement)
  {
    boolean wasAdded = false;
    if (Prerequirement.contains(aPrerequirement)) { return false; }
    Prerequirement.add(aPrerequirement);
    if (aPrerequirement.indexOfPrerequesite(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aPrerequirement.addPrerequesite(this);
      if (!wasAdded)
      {
        Prerequirement.remove(aPrerequirement);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removePrerequirement(Course aPrerequirement)
  {
    boolean wasRemoved = false;
    if (!Prerequirement.contains(aPrerequirement))
    {
      return wasRemoved;
    }

    int oldIndex = Prerequirement.indexOf(aPrerequirement);
    Prerequirement.remove(oldIndex);
    if (aPrerequirement.indexOfPrerequesite(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aPrerequirement.removePrerequesite(this);
      if (!wasRemoved)
      {
        Prerequirement.add(oldIndex,aPrerequirement);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPrerequirementAt(Course aPrerequirement, int index)
  {  
    boolean wasAdded = false;
    if(addPrerequirement(aPrerequirement))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPrerequirement()) { index = numberOfPrerequirement() - 1; }
      Prerequirement.remove(aPrerequirement);
      Prerequirement.add(index, aPrerequirement);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePrerequirementAt(Course aPrerequirement, int index)
  {
    boolean wasAdded = false;
    if(Prerequirement.contains(aPrerequirement))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPrerequirement()) { index = numberOfPrerequirement() - 1; }
      Prerequirement.remove(aPrerequirement);
      Prerequirement.add(index, aPrerequirement);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPrerequirementAt(aPrerequirement, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCorequirement()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addCorequirement(Course aCorequirement)
  {
    boolean wasAdded = false;
    if (Corequirement.contains(aCorequirement)) { return false; }
    Corequirement.add(aCorequirement);
    if (aCorequirement.indexOfCorequesite(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aCorequirement.addCorequesite(this);
      if (!wasAdded)
      {
        Corequirement.remove(aCorequirement);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeCorequirement(Course aCorequirement)
  {
    boolean wasRemoved = false;
    if (!Corequirement.contains(aCorequirement))
    {
      return wasRemoved;
    }

    int oldIndex = Corequirement.indexOf(aCorequirement);
    Corequirement.remove(oldIndex);
    if (aCorequirement.indexOfCorequesite(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aCorequirement.removeCorequesite(this);
      if (!wasRemoved)
      {
        Corequirement.add(oldIndex,aCorequirement);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCorequirementAt(Course aCorequirement, int index)
  {  
    boolean wasAdded = false;
    if(addCorequirement(aCorequirement))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCorequirement()) { index = numberOfCorequirement() - 1; }
      Corequirement.remove(aCorequirement);
      Corequirement.add(index, aCorequirement);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCorequirementAt(Course aCorequirement, int index)
  {
    boolean wasAdded = false;
    if(Corequirement.contains(aCorequirement))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCorequirement()) { index = numberOfCorequirement() - 1; }
      Corequirement.remove(aCorequirement);
      Corequirement.add(index, aCorequirement);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCorequirementAt(aCorequirement, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<CourseOffering> copyOfCourseOfferings = new ArrayList<CourseOffering>(courseOfferings);
    courseOfferings.clear();
    for(CourseOffering aCourseOffering : copyOfCourseOfferings)
    {
      aCourseOffering.removeCourse(this);
    }
    ArrayList<Course> copyOfPrerequesite = new ArrayList<Course>(Prerequesite);
    Prerequesite.clear();
    for(Course aPrerequesite : copyOfPrerequesite)
    {
      aPrerequesite.removePrerequirement(this);
    }
    ArrayList<Course> copyOfCorequesite = new ArrayList<Course>(Corequesite);
    Corequesite.clear();
    for(Course aCorequesite : copyOfCorequesite)
    {
      aCorequesite.removeCorequirement(this);
    }
    ArrayList<Course> copyOfPrerequirement = new ArrayList<Course>(Prerequirement);
    Prerequirement.clear();
    for(Course aPrerequirement : copyOfPrerequirement)
    {
      aPrerequirement.removePrerequesite(this);
    }
    ArrayList<Course> copyOfCorequirement = new ArrayList<Course>(Corequirement);
    Corequirement.clear();
    for(Course aCorequirement : copyOfCorequirement)
    {
      aCorequirement.removeCorequesite(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "courseCode" + ":" + getCourseCode()+ "," +
            "name" + ":" + getName()+ "," +
            "description" + ":" + getDescription()+ "," +
            "syllabus" + ":" + getSyllabus()+ "]";
  }
}