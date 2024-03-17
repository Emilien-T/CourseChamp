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
import ca.mcgill.ecse428.CourseChamp.repository.StudentRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AdminRepository adminRepository;

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
        Student a = getStudentByEmail(student.getEmail());
        a.setPassword(student.getPassword());
        a.setUsername(student.getUsername());
        return studentRepository.save(a);
    }

    @Transactional
    public List<Review> getReviewsOfStudent(String email){
        return null;
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
