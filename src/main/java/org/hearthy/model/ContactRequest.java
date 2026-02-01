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
public class ContactRequest {
    private UUID id;
    private String fullName;
    private String email;
    private String phone;
    private String message;
    private String status;
    private OffsetDateTime createdAt;
}
