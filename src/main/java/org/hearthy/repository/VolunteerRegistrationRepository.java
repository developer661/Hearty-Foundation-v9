package org.hearthy.repository;

import lombok.RequiredArgsConstructor;
import org.hearthy.config.SupabaseClient;
import org.hearthy.model.VolunteerRegistration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class VolunteerRegistrationRepository {
    private final SupabaseClient supabaseClient;

    private final RowMapper<VolunteerRegistration> rowMapper = new RowMapper<VolunteerRegistration>() {
        @Override
        public VolunteerRegistration mapRow(ResultSet rs, int rowNum) throws SQLException {
            return VolunteerRegistration.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .fullName(rs.getString("full_name"))
                    .email(rs.getString("email"))
                    .phone(rs.getString("phone"))
                    .dateOfBirth(rs.getDate("date_of_birth").toLocalDate())
                    .profession(rs.getString("profession"))
                    .experience(rs.getString("experience"))
                    .motivation(rs.getString("motivation"))
                    .status(rs.getString("status"))
                    .createdAt(rs.getObject("created_at", java.time.OffsetDateTime.class))
                    .build();
        }
    };

    public List<VolunteerRegistration> findAll() {
        String sql = "SELECT * FROM volunteer_registrations ORDER BY created_at DESC";
        return supabaseClient.getJdbcTemplate().query(sql, rowMapper);
    }

    public VolunteerRegistration save(VolunteerRegistration registration) {
        if (registration.getId() == null) {
            registration.setId(UUID.randomUUID());
            String sql = "INSERT INTO volunteer_registrations (id, full_name, email, phone, date_of_birth, profession, experience, motivation, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            supabaseClient.getJdbcTemplate().update(sql,
                    registration.getId(),
                    registration.getFullName(),
                    registration.getEmail(),
                    registration.getPhone(),
                    Date.valueOf(registration.getDateOfBirth()),
                    registration.getProfession(),
                    registration.getExperience(),
                    registration.getMotivation(),
                    registration.getStatus()
            );
        }
        return registration;
    }
}
