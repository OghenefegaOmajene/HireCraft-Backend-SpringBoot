//package com.example.hirecraft.services;
//
//import com.example.hirecraft.dtos.requests.LoginRequest;
//import com.example.hirecraft.dtos.requests.SignUpRequestWrapper;
//import com.example.hirecraft.dtos.response.LoginResponse;
//
//public interface AuthService {
//    LoginResponse register(SignUpRequestWrapper request);
//    LoginResponse login(LoginRequest request);
//}


package com.example.hirecraft.services;

import com.example.hirecraft.dtos.requests.*;
import com.example.hirecraft.dtos.response.LoginResponse;
import com.example.hirecraft.dtos.response.UserInfoResponse;
import com.example.hirecraft.enums.UserRole;
import com.example.hirecraft.models.User;
import com.example.hirecraft.models.ClientProfile;
import com.example.hirecraft.models.ServiceProviderProfile;
import com.example.hirecraft.repository.UserRepository;
import com.example.hirecraft.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String token = jwtTokenProvider.generateToken(authentication);
        long expiresIn = jwtTokenProvider.getValidityInMilliseconds();
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return LoginResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(expiresIn)
                .user(UserInfoResponse.fromEntity(user))
                .build();
    }

    public LoginResponse register(SignUpRequestWrapper wrapper) {
        BaseRegisterRequest baseRequest = wrapper.getRequest();
        String roleStr = wrapper.getRole().toUpperCase();

        UserRole userRole = UserRole.valueOf(roleStr);

        if (userRepository.existsByEmail(baseRequest.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }

        // Build user
        User user = User.builder()
                .email(baseRequest.getEmail())
                .password(passwordEncoder.encode(baseRequest.getPassword()))
                .firstName(baseRequest.getFirstName())
                .lastName(baseRequest.getLastName())
                .role(role)
                .build();

        // Attach role-specific profile
        switch (userRole) {
            case CLIENT -> {
                ClientRegisterRequest clientReq = (ClientRegisterRequest) baseRequest;
                ClientProfile clientProfile = new ClientProfile();
                clientProfile.setUser(user);
                user.setClientProfile(clientProfile);
            }
            case PROVIDER -> {
                ProviderRegisterRequest providerReq = (ProviderRegisterRequest) baseRequest;
                ServiceProviderProfile providerProfile = ServiceProviderProfile.builder()
                        .bio(providerReq.getBio())
                        .profession(providerReq.getProfession())
                        .user(user)
                        .build();
                user.setProviderProfile(providerProfile);
            }
            default -> throw new RuntimeException("Unsupported role");
        }

        userRepository.save(user);

        // Auto-login after registration
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(baseRequest.getEmail(), baseRequest.getPassword())
        );

        String token = jwtTokenProvider.generateToken(auth);
        long expiresIn = jwtTokenProvider.getValidityInMilliseconds();

        return LoginResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(expiresIn)
                .user(UserInfoResponse.fromEntity(user))
                .build();
    }
}
