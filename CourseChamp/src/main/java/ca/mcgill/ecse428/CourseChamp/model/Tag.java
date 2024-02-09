/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import java.util.*;

// line 30 "model.ump"
// line 95 "model.ump"
public class Tag
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum TagType { Bird_Course, Course_from_Hell }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tag Attributes
  private TagType tag;

  //Tag Associations
  private List<Review> reviews;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tag(TagType aTag)
  {
    tag = aTag;
    reviews = new ArrayList<Review>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTag(TagType aTag)
  {
    boolean wasSet = false;
    tag = aTag;
    wasSet = true;
    return wasSet;
  }

  public TagType getTag()
  {
    return tag;
  }
  /* Code from template association_GetMany */
  public Review getReview(int index)
  {
    Review aReview = reviews.get(index);
    return aReview;
  }

  public List<Review> getReviews()
  {
    List<Review> newReviews = Collections.unmodifiableList(reviews);
    return newReviews;
  }

  public int numberOfReviews()
  {
    int number = reviews.size();
    return number;
  }

  public boolean hasReviews()
  {
    boolean has = reviews.size() > 0;
    return has;
  }

  public int indexOfReview(Review aReview)
  {
    int index = reviews.indexOf(aReview);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReviews()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addReview(Review aReview)
  {
    boolean wasAdded = false;
    if (reviews.contains(aReview)) { return false; }
    reviews.add(aReview);
    if (aReview.indexOfTag(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aReview.addTag(this);
      if (!wasAdded)
      {
        reviews.remove(aReview);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeReview(Review aReview)
  {
    boolean wasRemoved = false;
    if (!reviews.contains(aReview))
    {
      return wasRemoved;
    }

    int oldIndex = reviews.indexOf(aReview);
    reviews.remove(oldIndex);
    if (aReview.indexOfTag(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aReview.removeTag(this);
      if (!wasRemoved)
      {
        reviews.add(oldIndex,aReview);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addReviewAt(Review aReview, int index)
  {  
    boolean wasAdded = false;
    if(addReview(aReview))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveReviewAt(Review aReview, int index)
  {
    boolean wasAdded = false;
    if(reviews.contains(aReview))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addReviewAt(aReview, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<Review> copyOfReviews = new ArrayList<Review>(reviews);
    reviews.clear();
    for(Review aReview : copyOfReviews)
    {
      aReview.removeTag(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "tag" + "=" + (getTag() != null ? !getTag().equals(this)  ? getTag().toString().replaceAll("  ","    ") : "this" : "null");
  }
}