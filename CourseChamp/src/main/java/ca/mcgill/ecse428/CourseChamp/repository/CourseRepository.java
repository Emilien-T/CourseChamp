package ca.mcgill.ecse428.CourseChamp.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse428.CourseChamp.model.Course;

public interface CourseRepository extends CrudRepository<Course, String> {
    
   /**
     * Retrieves a course from the database based on its unique course code.
     *
     * @param courseCode The unique course code of the course.
     * @return The Course entity corresponding to the provided course code, or null if not found.
     */
    Course findCourseByCourseCode(String courseCode);

    /**
     * Retrieves a course from the database based on its department and course number.
     *
     * @param department  The department code of the course.
     * @param courseNumber The course number.
     * @return The Course entity corresponding to the provided department and course number, or null if not found.
     */
    Course findCourseByDepartmentAndCourseNumber(String department, int courseNumber);
}