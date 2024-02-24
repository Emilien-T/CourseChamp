package ca.mcgill.ecse428.CourseChamp.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse428.CourseChamp.exception.CourseChampException;
import ca.mcgill.ecse428.CourseChamp.model.Admin;
import ca.mcgill.ecse428.CourseChamp.repository.AdminRepository;
import ca.mcgill.ecse428.CourseChamp.repository.StudentRepository;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    StudentRepository studentRepository;

    /**
     * Service method to fetch all existing admins in the database
     * 
     * @return all the admins from persistence layer
     * @throws CourseChampException - if no owners exist in the system
     */
    @Transactional
    public Iterable<Admin> getAllAdmins() {
        Iterable<Admin> admins = adminRepository.findAll();
        Iterator<Admin> iterator = admins.iterator();
        if (!iterator.hasNext())
            throw new CourseChampException(HttpStatus.NOT_FOUND, "There are no admins in the system");
        return adminRepository.findAll();
    }

    /**
     * Service method to fetch an existing admin with a specific email from the
     * database
     * 
     * @param email admin's email linked to their account
     * @return the admin corresponding to the provided email
     * @throws CourseChampException - If the admin does not exist
     */
    @Transactional
    public Admin getAdminByEmail(String email) {
        Admin admin = adminRepository.findAdminByEmail(email);
        if (admin == null) {
            throw new CourseChampException(HttpStatus.NOT_FOUND, "Admin not found.");
        }
        return admin;
    }

    /**
     * Service method that updates the admin's information in the database
     * 
     * @param owner updated instance of the admin
     * @return the updated instance
     * @throws CourseChampException - If admin does not exist
     */
    @Transactional
    public Admin updateAdminAccount(Admin admin) {
        Admin a = getAdminByEmail(admin.getEmail());
        a.setPassword(admin.getPassword());
        a.setUsername(admin.getUsername());
        return adminRepository.save(a);

    }

    /**
     * Service method to store a created admin in the database
     * 
     * @param admin instance to be persisted
     * @return the persisted instance if successful
     * @throws CourseChampException - If an admin already exists
     */
    @Transactional
    public Admin createAdminAccount(Admin admin) {
        // Register the admin account into database
        if ((adminRepository.findAdminByEmail(admin.getEmail()) == null)
                && (studentRepository.findStudentByEmail(admin.getEmail()) == null)
                && (adminRepository.findAdminByUsername(admin.getUsername()) == null)
                && (studentRepository.findStudentByUsername(admin.getUsername()) == null))
            return adminRepository.save(admin);
        else if ((adminRepository.findAdminByEmail(admin.getEmail()) != null)
                || (studentRepository.findStudentByEmail(admin.getEmail()) != null))
            throw new CourseChampException(HttpStatus.CONFLICT, "Another account with this email already exists");
        else
            throw new CourseChampException(HttpStatus.CONFLICT, "Another account with this username already exists");
    }

    /**
     * Service method to verify that email and password match
     * 
     * @param email    - email of the admin
     * @param password - password of the admin
     * @throws CourseChampException - The login fails
     */
    @Transactional
    public Admin loginIntoAdmin(String email, String password) {
        // Attempt to find the admin by username
        Admin admin = adminRepository.findAdminByUsername(email);
        if (admin != null && admin.getPassword().equals(password))
            return admin;
        
        // next find admin by email
        admin = getAdminByEmail(email);
        if (admin != null && admin.getPassword().equals(password))
            return admin;
        
        throw new CourseChampException(HttpStatus.NOT_FOUND, "Please enter the correct password");
        
    }
}
