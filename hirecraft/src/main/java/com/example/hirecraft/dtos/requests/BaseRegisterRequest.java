package com.example.hirecraft.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public abstract class BaseRegisterRequest {
    @NotBlank(message = "First name must not be empty")
    @Size(max = 20, message = "First name cannot exceed 20 characters")
    private String firstName;

    @NotBlank(message = "Last name must not be empty")
    @Size(max = 20, message = "Last name cannot exceed 20 characters")
    private String lastName;

    @Email(message = "Must be a valid email address")
    @NotBlank(message = "Email must not be empty")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;

    @NotBlank(message = "Password must not be empty")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must contain at least one digit, one lowercase, one uppercase letter, one special character and no whitespace"
    )
    private String password;

    @NotBlank(message = "Phone number must not be empty")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number must be valid")
    private String phoneNo;

    @NotBlank(message = "City must not be empty")
    private String city;

    @NotBlank(message = "State must not be empty")
    private String state;

    @NotBlank(message = "Country must not be empty")
    @Size(min = 2, max = 2, message = "Country must be a 2-letter ISO code")
    private String country;
}