package com.example.hirecraft.dtos.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@Data
@NoArgsConstructor
public class SignUpRequestWrapper {

    @NotBlank
    private String role; // "CLIENT" or "PROVIDER"

    @Valid
    private BaseRegisterRequest request;
}

