package com.example.hirecraft.repository;

import com.example.hirecraft.models.ClientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientProfileRepository extends JpaRepository<ClientProfile, Long> {
    // Custom query methods can be added here
    ClientProfile findByUserId(Long userId);
}