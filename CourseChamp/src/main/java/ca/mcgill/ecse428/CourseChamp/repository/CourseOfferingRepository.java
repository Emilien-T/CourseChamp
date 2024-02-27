package ca.mcgill.ecse428.CourseChamp.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse428.CourseChamp.model.CourseOffering;

public interface CourseOfferingRepository extends CrudRepository<CourseOffering, String> {
    
   /**
     * Retrieves a course from the database based on its unique course code.
     *
     * @param courseCode The unique course code of the course.
     * @return The Course entity corresponding to the provided course code, or null if not found.
     */
    CourseOffering findCourseOfferingById(String courseCode);
}
