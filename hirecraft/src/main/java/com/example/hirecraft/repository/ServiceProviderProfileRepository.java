package com.example.hirecraft.repository;

import com.example.hirecraft.models.ServiceProviderProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceProviderProfileRepository extends JpaRepository<ServiceProviderProfile, Long> {
    // Custom query methods can be added here
    ServiceProviderProfile findByUserId(Long userId);
}