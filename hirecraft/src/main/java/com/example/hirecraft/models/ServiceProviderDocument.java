package com.example.hirecraft.models;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "provider_documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceProviderDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title; // e.g., "Plumbing License", "Electrical Certification"

    @NotBlank
    private String documentUrl; // Path to stored PDF/image

    @Enumerated(EnumType.STRING)
    private DocumentType documentType; // CV, LICENSE, CERTIFICATION, PORTFOLIO

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private ServiceProviderProfile serviceProviderProfile;

    private LocalDateTime uploadDate;

    private boolean verified = false; // Admin-verified status

    public enum DocumentType {
        CV,
        LICENSE,
        CERTIFICATION,
        PORTFOLIO_IMAGE,
        INSURANCE,
        OTHER
    }
}