package com.example.hirecraft.services.impl;

import com.example.hirecraft.dtos.requests.*;
import com.example.hirecraft.dtos.response.LoginResponse;
import com.example.hirecraft.dtos.response.UserInfoResponse;
import com.example.hirecraft.enums.UserRole;
import com.example.hirecraft.models.*;
import com.example.hirecraft.repository.RoleRepository;
import com.example.hirecraft.repository.UserRepository;
import com.example.hirecraft.security.JwtService;
import com.example.hirecraft.services.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public LoginResponse register(SignUpRequestWrapper requestWrapper) {
        BaseRegisterRequest baseRequest;
        UserRole userRole;

        if ("CLIENT".equalsIgnoreCase(requestWrapper.getRole())) {
            baseRequest = requestWrapper.getClientDetails();
            userRole = UserRole.ROLE_CLIENT;
        } else if ("PROVIDER".equalsIgnoreCase(requestWrapper.getRole())) {
            baseRequest = requestWrapper.getProviderDetails();
            userRole = UserRole.ROLE_SERVICE_PROVIDER;
        } else {
            throw new IllegalArgumentException("Invalid role type");
        }

        // Create base user
        User user = User.builder()
                .firstName(baseRequest.getFirstName())
                .lastName(baseRequest.getLastName())
                .email(baseRequest.getEmail())
                .passwordHash(passwordEncoder.encode(baseRequest.getPassword()))
                .phoneNo(baseRequest.getPhoneNo())
                .city(baseRequest.getCity())
                .state(baseRequest.getState())
                .country(baseRequest.getCountry())
                .build();

        Role role = roleRepository.findByName(userRole.name())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Set.of(role));

        // Attach profile
        if (userRole == UserRole.ROLE_CLIENT) {
            ClientRegisterRequest clientRequest = (ClientRegisterRequest) baseRequest;
            ClientProfile clientProfile = ClientProfile.builder()
                    .companyName(clientRequest.getCompanyName())
                    .user(user)
                    .build();
            user.setClientProfile(clientProfile);
        } else {
            ProviderRegisterRequest providerRequest = (ProviderRegisterRequest) baseRequest;
            ServiceProviderProfile profile = ServiceProviderProfile.builder()
                    .profession(providerRequest.getProfession())
                    .bio(providerRequest.getBio())
                    .skills(providerRequest.getSkills())
                    .rating(0.0)
                    .user(user)
                    .build();
            user.setServiceProviderProfile(profile);
        }

        userRepository.save(user);

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(3600L)
                .tokenType("Bearer")
                .user(UserInfoResponse.fromUser(user))
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(3600L)
                .tokenType("Bearer")
                .user(UserInfoResponse.fromUser(user))
                .build();
    }
}
