package ca.mcgill.ecse428.CourseChamp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse428.CourseChamp.exception.CourseChampException;
import ca.mcgill.ecse428.CourseChamp.model.Course;
import ca.mcgill.ecse428.CourseChamp.model.CourseOffering;
import ca.mcgill.ecse428.CourseChamp.repository.CourseOfferingRepository;
import ca.mcgill.ecse428.CourseChamp.repository.CourseRepository;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    /**
     * Service method to fetch all existing courses in the database
     * 
     * @throws CourseChampException - if no courses exist in the system
     */
    @Transactional
    public Iterable<Course> getAllCourses() {
        ArrayList<Course> courses = (ArrayList<Course>) courseRepository.findAll();
        if (courses.isEmpty()) {
            throw new CourseChampException(HttpStatus.NOT_FOUND, "There are no courses in the system");
        }
        return courses;
    }

    /**
     * Service method to fetch an existing course with a specific course code from
     * the database
     * 
     * @param courseCode - course code of the course
     * @throws CourseChampException - If course does not exist
     */
    @Transactional
    public Course getCourseByCode(String courseCode) {
        Course course = courseRepository.findCourseByCourseCode(courseCode);
        if (course == null) {
            throw new CourseChampException(HttpStatus.NOT_FOUND, "Course not found.");
        }
        return course;
    }

    /**
     * Service method to store a created course in the database
     * 
     * @param course - instance of the course to be persisted
     * @throws CourseChampException - If a course with the same code already exists
     */
    @Transactional
    public Course createCourse(Course course) {
        if (courseRepository.findCourseByCourseCode(course.getCourseCode()) == null) {
            Course course1 = courseRepository.save(course);
            courseOfferingRepository.save(new CourseOffering("W2023", course1));
            courseOfferingRepository.save(new CourseOffering("S2024", course1));
            courseOfferingRepository.save(new CourseOffering("F2023", course1));
            courseOfferingRepository.save(new CourseOffering("W2024", course1));
            return course1;
        } else {
            throw new CourseChampException(HttpStatus.CONFLICT, "A course with this code already exists");
        }
    }

    /**
     * Service method to update an existing course in the database.
     * 
     * @param courseId - The ID of the course to be updated
     * @param updatedCourse - The updated instance of the course
     * @return The updated course
     * @throws CourseChampException - If the course to be updated does not exist
     */
    @Transactional
    public Course updateCourse(String courseCode, Course updatedCourse) {
        if(updatedCourse.getSyllabus() == null || updatedCourse.getSyllabus().trim().isEmpty()){
            throw new CourseChampException(HttpStatus.BAD_REQUEST, "Syllabus cannot be blank.");
        }
       Course optionalCourse = courseRepository.findCourseByCourseCode(courseCode);

       Iterable<Course> courses = courseRepository.findAll();
       for(Course c : courses){
        if(c.getName().equals(updatedCourse.getName()) && !c.getCourseCode().equals(courseCode)){
            throw new CourseChampException(HttpStatus.BAD_REQUEST, "Another course already has this name.");
        }
       }
        if (optionalCourse != null) {
            
            // Update properties with values from updatedCourse

            // Uncomment if you would like to modify department and course number
            // course.setDepartment(updatedCourse.getDepartment());
            // course.setCourseCode(updatedCourse.getCourseCode());
            // course.setCourseNumber(updatedCourse.getCourseNumber());
            if (updatedCourse.getName()!= null) {
            optionalCourse.setName(updatedCourse.getName());
            }
            if (updatedCourse.getDescription()!= null) {
            optionalCourse.setDescription(updatedCourse.getDescription());
            }
            if (updatedCourse.getSyllabus()!= null) {
            optionalCourse.setSyllabus(updatedCourse.getSyllabus());
            }
            // Save and return the updated course
            return courseRepository.save(optionalCourse);
        } else {
            throw new CourseChampException(HttpStatus.NOT_FOUND, "Course not found");
        }
    }


    /**
     * Service method to verify if a course exists based on its department and
     * course number
     * 
     * @param department   - The department code of the course
     * @param courseNumber - The course number
     * @throws CourseChampException - If course does not exist
     */
    @Transactional
    public Course verifyCourse(String department, int courseNumber) {
        Course course = courseRepository.findCourseByDepartmentAndCourseNumber(department, courseNumber);
        if (course == null) {
            throw new CourseChampException(HttpStatus.NOT_FOUND, "Course not found.");
        }
        return course;
    }

    /**
     * Service method to delete a course from the database
     * @param courseCode of the course to be deleted from persistence layer
     */
    @Transactional
    public void deleteCourse(String courseCode) {
        Course course = courseRepository.findCourseByCourseCode(courseCode);
        if(course == null){
            throw new CourseChampException(HttpStatus.NOT_FOUND, "This course doesn't exist in the system.");  
        }else{
            if(!course.getPrerequirement().isEmpty()){
                throw new CourseChampException(HttpStatus.BAD_REQUEST, "This course cannot be removed as it is a prerequisite.");
            }
            courseRepository.deleteById(courseCode); // I am assuming here that composition cascading will also delete course offerings
        }
    }

    /**
     * Service method to fetch all existing courses in the database
     * 
     * @throws CourseChampException - if no courses exist in the system
     */
    @Transactional
    public Iterable<CourseOffering> getAllCourseOfferings(String courseCode) {
        ArrayList<CourseOffering> courseOfferings = new ArrayList<>();
        for (CourseOffering courseOffering : courseOfferingRepository.findAll()) {
            if(courseOffering.getCourse().getCourseCode().equals(courseCode)){
                courseOfferings.add(courseOffering);
            }
        }
        if (courseOfferings.isEmpty()) {
            throw new CourseChampException(HttpStatus.NOT_FOUND, "There are no course offerings for this course.");
        }
        return courseOfferings;
    }
}
