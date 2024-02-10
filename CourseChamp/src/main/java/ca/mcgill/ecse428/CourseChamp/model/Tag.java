package ca.mcgill.ecse428.CourseChamp.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Tag {

  // ------------------------
  // ENUMERATIONS
  // ------------------------

  public enum TagType {
    Bird_Course, Course_from_Hell
  }

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // Tag Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Enumerated(EnumType.STRING)
  private TagType tag;

  // Tag Associations
  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Review review;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public Tag(int aId, TagType aTag, Review aReview) {
    id = aId;
    tag = aTag;
    if (!setReview(aReview)) {
      throw new RuntimeException(
          "Unable to create Tag due to aReview. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setId(int aId) {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setTag(TagType aTag) {
    boolean wasSet = false;
    tag = aTag;
    wasSet = true;
    return wasSet;
  }

  public int getId() {
    return id;
  }

  public TagType getTag() {
    return tag;
  }

  /* Code from template association_GetOne */
  public Review getReview() {
    return review;
  }

  /* Code from template association_SetUnidirectionalOne */
  public boolean setReview(Review aNewReview) {
    boolean wasSet = false;
    if (aNewReview != null) {
      review = aNewReview;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete() {
    review = null;
  }

  public String toString() {
    return super.toString() + "[" +
        "id" + ":" + getId() + "]" + System.getProperties().getProperty("line.separator") +
        "  " + "tag" + "="
        + (getTag() != null ? !getTag().equals(this) ? getTag().toString().replaceAll("  ", "    ") : "this" : "null")
        + System.getProperties().getProperty("line.separator") +
        "  " + "review = " + (getReview() != null ? Integer.toHexString(System.identityHashCode(getReview())) : "null");
  }
}