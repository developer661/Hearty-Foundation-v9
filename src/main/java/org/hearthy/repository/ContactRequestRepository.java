package org.hearthy.repository;

import lombok.RequiredArgsConstructor;
import org.hearthy.config.SupabaseClient;
import org.hearthy.model.ContactRequest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ContactRequestRepository {
    private final SupabaseClient supabaseClient;

    private final RowMapper<ContactRequest> rowMapper = new RowMapper<ContactRequest>() {
        @Override
        public ContactRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
            return ContactRequest.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .fullName(rs.getString("full_name"))
                    .email(rs.getString("email"))
                    .phone(rs.getString("phone"))
                    .message(rs.getString("message"))
                    .status(rs.getString("status"))
                    .createdAt(rs.getObject("created_at", java.time.OffsetDateTime.class))
                    .build();
        }
    };

    public ContactRequest save(ContactRequest contactRequest) {
        if (contactRequest.getId() == null) {
            contactRequest.setId(UUID.randomUUID());
            String sql = "INSERT INTO contact_requests (id, full_name, email, phone, message, status) VALUES (?, ?, ?, ?, ?, ?)";
            supabaseClient.getJdbcTemplate().update(sql,
                    contactRequest.getId(),
                    contactRequest.getFullName(),
                    contactRequest.getEmail(),
                    contactRequest.getPhone(),
                    contactRequest.getMessage(),
                    contactRequest.getStatus()
            );
        }
        return contactRequest;
    }
}
