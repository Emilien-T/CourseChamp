package ca.mcgill.ecse428.CourseChamp.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse428.CourseChamp.model.Course;

public interface CourseRepository extends CrudRepository<Course, String> {
    
    public Course findCourseByCourseCode(String courseCode);
    
}