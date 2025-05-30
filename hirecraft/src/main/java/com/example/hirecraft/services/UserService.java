package com.example.hirecraft.services;

import com.example.hirecraft.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByEmail(String email);
    Page<User> getAllUsers(Pageable pageable);
    User updateUser(Long id, User userDetails);
    void deleteUser(Long id);
    boolean existsByEmail(String email);
//    if (user.getCompletedJobs() >= 50 && user.getCvUrl() != null) {
//        user.setVerified(true);
//        userRepository.save(user);
//    }

}