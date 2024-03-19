package ca.mcgill.ecse428.CourseChamp.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse428.CourseChamp.exception.CourseChampException;
import ca.mcgill.ecse428.CourseChamp.model.Admin;
import ca.mcgill.ecse428.CourseChamp.model.Review;
import ca.mcgill.ecse428.CourseChamp.model.Student;
import ca.mcgill.ecse428.CourseChamp.repository.AdminRepository;
import ca.mcgill.ecse428.CourseChamp.repository.ReviewRepository;
import ca.mcgill.ecse428.CourseChamp.repository.StudentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AdminRepository adminRepository;
     @Autowired
    ReviewRepository reviewRepository;

    /**
     * Service method to fetch all existing students in the database
     * 
     * @throws CourseChampException - if no students exist in the system
     */
    @Transactional
    public Iterable<Student> getAllStudents() {
        ArrayList<Student> arrayList = (ArrayList<Student>) studentRepository.findAll();
        if (arrayList.isEmpty())
            throw new CourseChampException(HttpStatus.NOT_FOUND, "There are no students in the system");
        return studentRepository.findAll();
    }

    /**
     * Service method to fetch an existing student with a specific email from the
     * database
     * 
     * @param email email of the student
     * @throws CourseChampException - If student does not exist
     */
    @Transactional
    public Student getStudentByEmail(String email) {
        Student student = studentRepository.findStudentByEmail(email);
        if (student == null) {
            throw new CourseChampException(HttpStatus.NOT_FOUND, "Student account not found");
        }
        return student;
    }

    /**
     * Service method to store a created student in the database
     * 
     * @param student instance of the student to be persisted
     * @throws CourseChampException - If an student already exists
     */
    @Transactional
    public Student createStudentAccount(Student student) {
        if ((studentRepository.findStudentByEmail(student.getEmail()) == null)
                && (adminRepository.findAdminByEmail(student.getEmail()) == null)
                && (adminRepository.findAdminByUsername(student.getUsername()) == null)
                && (studentRepository.findStudentByUsername(student.getUsername()) == null))
            return studentRepository.save(student);
        else if ((adminRepository.findAdminByEmail(student.getEmail()) != null)
                || (studentRepository.findStudentByEmail(student.getEmail()) != null))
            throw new CourseChampException(HttpStatus.CONFLICT, "Another account with this email already exists");
        else
            throw new CourseChampException(HttpStatus.CONFLICT, "Another account with this username already exists");

    }

    @Transactional
    public Student updateStudentAccount(Student student){
        Student s = getStudentByEmail(student.getEmail());
        Student s2 = studentRepository.findStudentByUsername(student.getUsername());
        if(s2 != null && !student.getEmail().equals(s2.getEmail())){
            throw new CourseChampException(HttpStatus.CONFLICT, "Another account with this username already exists");
        }
        if (adminRepository.findAdminByUsername(student.getUsername()) != null){
            throw new CourseChampException(HttpStatus.CONFLICT, "Another account with this username already exists");
        }

        // Regular expression to match at least one lowercase letter
        String regex = ".*[a-z].*";
        
        // Check if the string matches the regex
        boolean containsLowercase = Pattern.matches(regex, student.getPassword());
        if(!containsLowercase){
            throw new CourseChampException(HttpStatus.BAD_REQUEST, "Password must contain at least one uppercase letter, one lowercase letter, and one special character [!@#$%^+=]");
        }
        
        s.setPassword(student.getPassword());
        s.setUsername(student.getUsername());
        s.setMajor(student.getMajor());
        return studentRepository.save(s);
    }

    @Transactional
    public List<Review> getReviewsOfStudent(String email){
        if (email == null || email.isEmpty()) {
            throw new CourseChampException(HttpStatus.BAD_REQUEST, "email cannot be null or empty");
        }
        
        Iterable<Review> reviews = reviewRepository.findAll();
        ArrayList<Review> reviewsOfStudent = new ArrayList<Review>();
        for (Review r : reviews) {
            if (r.getStudent() != null && r.getStudent() != null && r.getStudent().getEmail().equals(email)) {
                reviewsOfStudent.add(r);
            }
        }
        if (reviewsOfStudent.isEmpty()) {
            throw new CourseChampException(HttpStatus.NOT_FOUND, "No reviews found for this student.");
        }

        return reviewsOfStudent;
    }

    /**
     * Service method to verify that email and password match
     * 
     * @param email    - email of the student
     * @param password - password of the student
     * @throws CourseChampException - The login fails
     */
    @Transactional
    public Student loginIntoStudent(String email, String password) {
        // Attempt to find the student by username
        Student student = studentRepository.findStudentByUsername(email);
        if (student != null && student.getPassword().equals(password))
            return student;
        
        // next find student by email
        student = getStudentByEmail(email);
        if (student != null && student.getPassword().equals(password))
            return student;
        else
            throw new CourseChampException(HttpStatus.NOT_FOUND, "Please enter the correct password");
    }

    @Transactional
    public void deleteStudentAccount(String email){
        studentRepository.delete(getStudentByEmail(email));
    }
}
