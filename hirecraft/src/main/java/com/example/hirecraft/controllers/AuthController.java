package com.example.hirecraft.controllers;

import com.example.hirecraft.dtos.requests.*;
import com.example.hirecraft.enums.UserRole;
import com.example.hirecraft.models.*;
import com.example.hirecraft.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final ClientProfileRepository clientProfileRepository;
    private final ServiceProviderProfileRepository providerProfileRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,
                          ClientProfileRepository clientProfileRepository,
                          ServiceProviderProfileRepository providerProfileRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.clientProfileRepository = clientProfileRepository;
        this.providerProfileRepository = providerProfileRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register/client")
    public ResponseEntity<?> registerClient(
            @Validated @RequestBody ClientRegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        User user = createBaseUser(request);
        Role clientRole = roleRepository.findByName(UserRole.ROLE_CLIENT.name())
                .orElseThrow(() -> new RuntimeException("Error: Role not found"));
        user.getRoles().add(clientRole);
        User savedUser = userRepository.save(user);

        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setCompanyName(request.getCompanyName());
        clientProfile.setUser(savedUser);
        clientProfileRepository.save(clientProfile);

        return ResponseEntity.ok("Client registered successfully");
    }

    @PostMapping("/register/provider")
    public ResponseEntity<?> registerProvider(
            @Validated @RequestBody ProviderRegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        User user = createBaseUser(request);
        Role providerRole = roleRepository.findByName(UserRole.ROLE_SERVICE_PROVIDER.name())
                .orElseThrow(() -> new RuntimeException("Error: Role not found"));
        user.getRoles().add(providerRole);
        User savedUser = userRepository.save(user);

        ServiceProviderProfile providerProfile = new ServiceProviderProfile();
        providerProfile.setProfession(request.getProfession());
        providerProfile.setBio(request.getBio());
        providerProfile.setSkills(request.getSkills());
        providerProfile.setUser(savedUser);
        providerProfileRepository.save(providerProfile);

        return ResponseEntity.ok("Service provider registered successfully");
    }

    private User createBaseUser(BaseRegisterRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .phoneNo(request.getPhoneNo())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .build();
    }
}