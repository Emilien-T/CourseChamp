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

/**
 * Class that is part of the domain model of the CourseChamp System
 * This class contains information related to review tags
 * 
 * A major part of this class is auto-generated by umple
 * This class is also JPA anotated for ORM
 */
@Entity
public class Tag {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // Tag Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  // Tag Associations
  @ManyToOne
  private TagType tagType;
  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Review review;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------
  public Tag() {
  }

  public Tag(int aId, TagType aTagType, Review aReview) {
    id = aId;
    if (!setTagType(aTagType)) {
      throw new RuntimeException(
          "Unable to create Tag due to aTagType. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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

  public int getId() {
    return id;
  }

  /* Code from template association_GetOne */
  public TagType getTagType() {
    return tagType;
  }

  /* Code from template association_GetOne */
  public Review getReview() {
    return review;
  }

  /* Code from template association_SetUnidirectionalOne */
  public boolean setTagType(TagType aNewTagType) {
    boolean wasSet = false;
    if (aNewTagType != null) {
      tagType = aNewTagType;
      wasSet = true;
    }
    return wasSet;
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
    tagType = null;
    review = null;
  }

  public String toString() {
    return super.toString() + "[" +
        "id" + ":" + getId() + "]" + System.getProperties().getProperty("line.separator") +
        "  " + "tagType = "
        + (getTagType() != null ? Integer.toHexString(System.identityHashCode(getTagType())) : "null")
        + System.getProperties().getProperty("line.separator") +
        "  " + "review = " + (getReview() != null ? Integer.toHexString(System.identityHashCode(getReview())) : "null");
  }
}