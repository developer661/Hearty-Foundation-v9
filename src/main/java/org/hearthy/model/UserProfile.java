package org.hearthy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    private UUID id;
    private UUID userId;
    private String fullName;
    private String avatarUrl;
    private String bio;
    private String phone;
    private String address;
    private String city;
    private String country;
    private String verificationStatus;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
