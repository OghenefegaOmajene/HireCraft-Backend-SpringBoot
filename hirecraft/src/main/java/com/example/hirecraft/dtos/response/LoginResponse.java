package com.example.hirecraft.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
        private String accessToken;
        private String refreshToken;
        private Long expiresIn;
        private String tokenType;
        private UserInfoResponse user;
}
