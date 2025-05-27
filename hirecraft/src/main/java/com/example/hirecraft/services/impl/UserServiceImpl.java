package com.example.hirecraft.services.impl;

import com.example.hirecraft.dtos.requests.ProfilePatchRequest;
import com.example.hirecraft.dtos.requests.UserUpdateRequest;
import com.example.hirecraft.dtos.response.UserDetailResponse;
import com.example.hirecraft.dtos.response.UserListResponse;
import com.example.hirecraft.exceptions.UserNotFoundException;
import com.example.hirecraft.models.User;
import com.example.hirecraft.repository.UserRepository;
import com.example.hirecraft.services.CloudinaryService;
import com.example.hirecraft.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;
    @Override
    public List<UserListResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserListResponse(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getStatus().name()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public UserDetailResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID " + id));
        return mapToDetail(user);
    }

    @Override
    @Transactional
    public UserDetailResponse updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID " + id));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        // optionally update email or other fields
        user.setUpdatedAt(LocalDateTime.now());

        User updated = userRepository.save(user);
        return mapToDetail(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Cannot delete, user not found with ID " + id);
        }
        userRepository.deleteById(id);
    }
    @Override
    public UserDetailResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + email));
        return mapToDetail(user);
    }

    @Override
    public UserDetailResponse updateUserProfile(String email, ProfilePatchRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + email));

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        return mapToDetail(user);
    }

    @Override
    @Transactional
    public String updateProfilePicture(String email, MultipartFile file) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + email));

        String url = cloudinaryService.uploadProfileImage(file);
        user.setProfilePictureUrl(url);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return url;
    }


    private UserDetailResponse mapToDetail(User user) {
        return new UserDetailResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getStatus().name(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getProfilePictureUrl()
        );
    }


}
