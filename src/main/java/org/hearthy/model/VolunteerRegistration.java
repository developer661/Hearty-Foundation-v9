package org.hearthy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerRegistration {
    private UUID id;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String profession;
    private String experience;
    private String motivation;
    private String status;
    private OffsetDateTime createdAt;
}
