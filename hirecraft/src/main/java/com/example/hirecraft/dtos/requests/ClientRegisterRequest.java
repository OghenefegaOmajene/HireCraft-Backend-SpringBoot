package com.example.hirecraft.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientRegisterRequest extends BaseRegisterRequest {
    @NotBlank(message = "Company name must not be empty")
    @Size(max = 100, message = "Company name cannot exceed 100 characters")
    private String companyName;
}