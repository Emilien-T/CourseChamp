package ca.mcgill.ecse428.CourseChamp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginDto {
  @NotBlank(message = "Email cannot be blank.")
  @Email(message = "Email must follow this format xxx@email.address")
  @Schema(example = "student@email.com", description = "Email linked to the account of the student", requiredMode = Schema.RequiredMode.REQUIRED)
  private String email;

  @NotBlank(message = "Password cannot be blank.")
  @Schema(example = "Password1!", description = "Password linked to the account of the student", requiredMode = Schema.RequiredMode.REQUIRED)
  private String password;

  public String getEmail() {
    return this.email;
  }

  public String getPassword() {
    return this.password;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}