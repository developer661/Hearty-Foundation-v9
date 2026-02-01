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
public class Volunteer {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String location;
    private String interest;
    private String message;
    private OffsetDateTime createdAt;
}
