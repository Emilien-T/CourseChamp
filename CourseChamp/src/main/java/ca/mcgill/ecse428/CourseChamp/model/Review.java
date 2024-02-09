package ca.mcgill.ecse428.CourseChamp.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import java.util.*;

// line 22 "model.ump"
// line 88 "model.ump"
public class Review
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Review Attributes
  private int rating;
  private String text;

  //Review Associations
  private Student student;
  private List<Tag> tags;
  private CourseOffering courseOffering;
  private List<Vote> votes;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Review(int aRating, String aText, Student aStudent, CourseOffering aCourseOffering)
  {
    rating = aRating;
    text = aText;
    boolean didAddStudent = setStudent(aStudent);
    if (!didAddStudent)
    {
      throw new RuntimeException("Unable to create review due to student. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    tags = new ArrayList<Tag>();
    boolean didAddCourseOffering = setCourseOffering(aCourseOffering);
    if (!didAddCourseOffering)
    {
      throw new RuntimeException("Unable to create review due to courseOffering. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    votes = new ArrayList<Vote>();
  }

  //------------------------
  // INTERFACE
  //------------------------

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
  /* Code from template association_GetMany */
  public Tag getTag(int index)
  {
    Tag aTag = tags.get(index);
    return aTag;
  }

  public List<Tag> getTags()
  {
    List<Tag> newTags = Collections.unmodifiableList(tags);
    return newTags;
  }

  public int numberOfTags()
  {
    int number = tags.size();
    return number;
  }

  public boolean hasTags()
  {
    boolean has = tags.size() > 0;
    return has;
  }

  public int indexOfTag(Tag aTag)
  {
    int index = tags.indexOf(aTag);
    return index;
  }
  /* Code from template association_GetOne */
  public CourseOffering getCourseOffering()
  {
    return courseOffering;
  }
  /* Code from template association_GetMany */
  public Vote getVote(int index)
  {
    Vote aVote = votes.get(index);
    return aVote;
  }

  public List<Vote> getVotes()
  {
    List<Vote> newVotes = Collections.unmodifiableList(votes);
    return newVotes;
  }

  public int numberOfVotes()
  {
    int number = votes.size();
    return number;
  }

  public boolean hasVotes()
  {
    boolean has = votes.size() > 0;
    return has;
  }

  public int indexOfVote(Vote aVote)
  {
    int index = votes.indexOf(aVote);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setStudent(Student aStudent)
  {
    boolean wasSet = false;
    if (aStudent == null)
    {
      return wasSet;
    }

    Student existingStudent = student;
    student = aStudent;
    if (existingStudent != null && !existingStudent.equals(aStudent))
    {
      existingStudent.removeReview(this);
    }
    student.addReview(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTags()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfTags()
  {
    return 3;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addTag(Tag aTag)
  {
    boolean wasAdded = false;
    if (tags.contains(aTag)) { return false; }
    if (numberOfTags() >= maximumNumberOfTags())
    {
      return wasAdded;
    }

    tags.add(aTag);
    if (aTag.indexOfReview(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aTag.addReview(this);
      if (!wasAdded)
      {
        tags.remove(aTag);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeTag(Tag aTag)
  {
    boolean wasRemoved = false;
    if (!tags.contains(aTag))
    {
      return wasRemoved;
    }

    int oldIndex = tags.indexOf(aTag);
    tags.remove(oldIndex);
    if (aTag.indexOfReview(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aTag.removeReview(this);
      if (!wasRemoved)
      {
        tags.add(oldIndex,aTag);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_SetOptionalNToMany */
  public boolean setTags(Tag... newTags)
  {
    boolean wasSet = false;
    ArrayList<Tag> verifiedTags = new ArrayList<Tag>();
    for (Tag aTag : newTags)
    {
      if (verifiedTags.contains(aTag))
      {
        continue;
      }
      verifiedTags.add(aTag);
    }

    if (verifiedTags.size() != newTags.length || verifiedTags.size() > maximumNumberOfTags())
    {
      return wasSet;
    }

    ArrayList<Tag> oldTags = new ArrayList<Tag>(tags);
    tags.clear();
    for (Tag aNewTag : verifiedTags)
    {
      tags.add(aNewTag);
      if (oldTags.contains(aNewTag))
      {
        oldTags.remove(aNewTag);
      }
      else
      {
        aNewTag.addReview(this);
      }
    }

    for (Tag anOldTag : oldTags)
    {
      anOldTag.removeReview(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTagAt(Tag aTag, int index)
  {  
    boolean wasAdded = false;
    if(addTag(aTag))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTags()) { index = numberOfTags() - 1; }
      tags.remove(aTag);
      tags.add(index, aTag);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTagAt(Tag aTag, int index)
  {
    boolean wasAdded = false;
    if(tags.contains(aTag))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTags()) { index = numberOfTags() - 1; }
      tags.remove(aTag);
      tags.add(index, aTag);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTagAt(aTag, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCourseOffering(CourseOffering aCourseOffering)
  {
    boolean wasSet = false;
    if (aCourseOffering == null)
    {
      return wasSet;
    }

    CourseOffering existingCourseOffering = courseOffering;
    courseOffering = aCourseOffering;
    if (existingCourseOffering != null && !existingCourseOffering.equals(aCourseOffering))
    {
      existingCourseOffering.removeReview(this);
    }
    courseOffering.addReview(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfVotes()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Vote addVote(boolean aType, Student aStudent)
  {
    return new Vote(aType, aStudent, this);
  }

  public boolean addVote(Vote aVote)
  {
    boolean wasAdded = false;
    if (votes.contains(aVote)) { return false; }
    Review existingReview = aVote.getReview();
    boolean isNewReview = existingReview != null && !this.equals(existingReview);
    if (isNewReview)
    {
      aVote.setReview(this);
    }
    else
    {
      votes.add(aVote);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeVote(Vote aVote)
  {
    boolean wasRemoved = false;
    //Unable to remove aVote, as it must always have a review
    if (!this.equals(aVote.getReview()))
    {
      votes.remove(aVote);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addVoteAt(Vote aVote, int index)
  {  
    boolean wasAdded = false;
    if(addVote(aVote))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfVotes()) { index = numberOfVotes() - 1; }
      votes.remove(aVote);
      votes.add(index, aVote);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveVoteAt(Vote aVote, int index)
  {
    boolean wasAdded = false;
    if(votes.contains(aVote))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfVotes()) { index = numberOfVotes() - 1; }
      votes.remove(aVote);
      votes.add(index, aVote);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addVoteAt(aVote, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Student placeholderStudent = student;
    this.student = null;
    if(placeholderStudent != null)
    {
      placeholderStudent.removeReview(this);
    }
    while (tags.size() > 0)
    {
      Tag aTag = tags.get(tags.size() - 1);
      aTag.delete();
      tags.remove(aTag);
    }
    
    CourseOffering placeholderCourseOffering = courseOffering;
    this.courseOffering = null;
    if(placeholderCourseOffering != null)
    {
      placeholderCourseOffering.removeReview(this);
    }
    while (votes.size() > 0)
    {
      Vote aVote = votes.get(votes.size() - 1);
      aVote.delete();
      votes.remove(aVote);
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "rating" + ":" + getRating()+ "," +
            "text" + ":" + getText()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "student = "+(getStudent()!=null?Integer.toHexString(System.identityHashCode(getStudent())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "courseOffering = "+(getCourseOffering()!=null?Integer.toHexString(System.identityHashCode(getCourseOffering())):"null");
  }
}