package ca.mcgill.ecse428.CourseChamp.dto;

import ca.mcgill.ecse428.CourseChamp.model.Admin;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public class AdminRequestDto {

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Email must follow this format xxx@email.address")
    @Schema(example = "admin@email.com", description = "Email linked to the account of the admin", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^+=]).+$", message = "Password contains at least one uppercase, lowercase and special character [!@#$%^+=]")
    @Size(min = 5, max = 13, message = "Password must have 5-13 character")
    @NotBlank(message = "Password cannot be blank.")
    @Schema(example = "Password1!", description = "Password linked to the account of the admin", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Pattern(regexp = "^[a-zA-Z\\d\\s!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+$", message = "Name can only have letters, numbers, and special characters")
    @NotBlank(message = "Username cannot be blank.")
    @Schema(example = "admin", description = "Username of the admin", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String aEmail) {
        email = aEmail;
    }

    public void setUsername(String aUsername) {
        username = aUsername;
    }

    public void setPassword(String aPassword) {
        password = aPassword;
    }

    public Admin toModel() {
        return new Admin(email, username, password);
    }
}
