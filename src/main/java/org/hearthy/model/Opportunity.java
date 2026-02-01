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
public class Opportunity {
    private UUID id;
    private String title;
    private String description;
    private String category;
    private String institutionName;
    private String location;
    private String urgency;
    private String status;
    private OffsetDateTime createdAt;
}
