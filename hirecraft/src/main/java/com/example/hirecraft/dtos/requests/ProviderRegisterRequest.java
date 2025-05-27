package com.example.hirecraft.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProviderRegisterRequest extends BaseRegisterRequest {
    @NotBlank(message = "Profession must not be empty")
    @Size(max = 50, message = "Profession cannot exceed 50 characters")
    private String profession;

    @Size(max = 500, message = "Bio cannot exceed 500 characters")
    private String bio;

    @NotNull(message = "Skills must not be null")
    private Set<@NotBlank(message = "Skill cannot be blank") String> skills;
}