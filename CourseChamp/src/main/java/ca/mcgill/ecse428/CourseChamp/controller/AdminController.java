package ca.mcgill.ecse428.CourseChamp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ca.mcgill.ecse428.CourseChamp.dto.AdminRequestDto;
import ca.mcgill.ecse428.CourseChamp.dto.AdminResponseDto;
import ca.mcgill.ecse428.CourseChamp.dto.StudentRequestDto;
import ca.mcgill.ecse428.CourseChamp.dto.StudentResponseDto;
import ca.mcgill.ecse428.CourseChamp.model.Admin;
import ca.mcgill.ecse428.CourseChamp.model.Student;
import ca.mcgill.ecse428.CourseChamp.service.AdminService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin(origins = "*")
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * Returns the Admin based on their Email
     * 
     * @param email - Pass in the email argument by using /admin={?email}
     * @return the Admin with Email, Password, Name
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Admin not found.", content = {
                    @Content(mediaType = "String") })
    })
    @GetMapping(value = { "/admin/{email}", "/admin/{email}/" })
    public ResponseEntity<AdminResponseDto> getAdminByEmail(@PathVariable String email) {
        return new ResponseEntity<AdminResponseDto>(new AdminResponseDto(adminService.getAdminByEmail(email)),
                HttpStatus.OK);
    }

    /**
     * Creates a new Admin
     * 
     * @param AdminRequest - Pass in a admin dto using a JSON request
     * @return the dto response of the new Admin
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "409", description = "Admin account with this email already exists.", content = {
                    @Content(mediaType = "String") })
    })
    @PostMapping("/admin/create")
    public ResponseEntity<AdminResponseDto> createAdmin(@Valid @RequestBody AdminRequestDto AdminRequest) {
        // 1. You pass in a request, validates the constraints, creates an Admin if they pass
        Admin Admin = adminService.createAdminAccount(AdminRequest.toModel()); // 2. You use the service class to check if it exists and save
                                                                                  // it
        AdminResponseDto responseBody = new AdminResponseDto(Admin);
        return new ResponseEntity<AdminResponseDto>(responseBody, HttpStatus.CREATED); // 3. You mask the model by
                                                                                       // returning a Response
    }

    /**
     * Updates an Admin
     * 
     * @param StudentRequest - Pass in a admin dto using a JSON request
     * @return the dto response of the updtated admin
     */// returning a Response
    @PutMapping("/admin/update")
    public ResponseEntity<AdminResponseDto> updateAdmin(@Valid @RequestBody AdminRequestDto AdminRequest) {
        AdminResponseDto responseBody = new AdminResponseDto(new Admin());
        return new ResponseEntity<AdminResponseDto>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping("/admin/delete/{email}")
    public void deleteAdmin(@PathVariable String email) {
        adminService.deleteAdminAccount(email);
    }
}
