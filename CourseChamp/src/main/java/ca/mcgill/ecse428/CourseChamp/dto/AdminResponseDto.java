package ca.mcgill.ecse428.CourseChamp.dto;

import ca.mcgill.ecse428.CourseChamp.model.Admin;
import io.swagger.v3.oas.annotations.media.Schema;

public class AdminResponseDto {

    @Schema(example= "admin@email.com", description = "Email linked to the account of the admin")
    private String email;
    @Schema(example= "Password1!", description = "Password linked to the account of the admin")
    private String password;
    @Schema(example= "admin", description = "Username of the admin")
    private String username;

    public AdminResponseDto(Admin admin) {
        this.email = admin.getEmail();
        this.password = admin.getPassword();
        this.username = admin.getUsername();
    }

    AdminResponseDto() {}

    public String getEmail()
    { return email; }

    public String getPassword()
    { return password; }

    public String getName()
    { return username; }
}
