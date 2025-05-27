package com.example.hirecraft.models;

import com.example.hirecraft.enums.PermissionName;
import com.example.hirecraft.enums.RoleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    Role name r.g ADMIN, USER
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleName name;


//    private String description;

    @ElementCollection(targetClass = PermissionName.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id")
    )
    @Column(name = "permission")
    @Builder.Default

    private Set<PermissionName> permissions = new HashSet<>();
}
