package com.example.hirecraft.services;

import com.example.hirecraft.dtos.requests.ForgotPasswordRequest;
import com.example.hirecraft.dtos.requests.LoginRequest;
import com.example.hirecraft.dtos.requests.RegisterRequest;
import com.example.hirecraft.dtos.requests.ResetPasswordRequest;
import com.example.hirecraft.dtos.response.ForgotPasswordResponse;
import com.example.hirecraft.dtos.response.LoginResponse;
import com.example.hirecraft.dtos.response.RegisterResponse;
import com.example.hirecraft.dtos.response.ResetPasswordResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
    /**
     * Initiate a password reset by sending a one-time code
     * to the user’s email (if it exists).
     */
    ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request);

    /**
     * Complete a password reset by validating the code
     * and setting the new password.
     */
    ResetPasswordResponse resetPassword(ResetPasswordRequest request);

}
