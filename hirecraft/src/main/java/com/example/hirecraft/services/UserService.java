package com.example.hirecraft.services;

import com.example.hirecraft.dtos.requests.ProfilePatchRequest;
import com.example.hirecraft.dtos.requests.UserUpdateRequest;
import com.example.hirecraft.dtos.response.UserDetailResponse;
import com.example.hirecraft.dtos.response.UserListResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    List<UserListResponse> getAllUsers();
    UserDetailResponse getUserById(Long id);
    UserDetailResponse updateUser(Long id, UserUpdateRequest request);

    void deleteUser(Long id);
    UserDetailResponse getUserByEmail(String email);
    UserDetailResponse updateUserProfile(String email, ProfilePatchRequest request);
    String updateProfilePicture(String email, MultipartFile file);

}
