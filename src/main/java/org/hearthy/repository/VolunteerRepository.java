package org.hearthy.repository;

import lombok.RequiredArgsConstructor;
import org.hearthy.config.SupabaseClient;
import org.hearthy.model.Volunteer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class VolunteerRepository {
    private final SupabaseClient supabaseClient;

    private final RowMapper<Volunteer> rowMapper = new RowMapper<Volunteer>() {
        @Override
        public Volunteer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Volunteer.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .name(rs.getString("name"))
                    .email(rs.getString("email"))
                    .phone(rs.getString("phone"))
                    .location(rs.getString("location"))
                    .interest(rs.getString("interest"))
                    .message(rs.getString("message"))
                    .createdAt(rs.getObject("created_at", java.time.OffsetDateTime.class))
                    .build();
        }
    };

    public List<Volunteer> findAll() {
        String sql = "SELECT * FROM volunteers ORDER BY created_at DESC";
        return supabaseClient.getJdbcTemplate().query(sql, rowMapper);
    }

    public Optional<Volunteer> findById(UUID id) {
        String sql = "SELECT * FROM volunteers WHERE id = ?";
        List<Volunteer> results = supabaseClient.getJdbcTemplate().query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public Volunteer save(Volunteer volunteer) {
        if (volunteer.getId() == null) {
            volunteer.setId(UUID.randomUUID());
            String sql = "INSERT INTO volunteers (id, name, email, phone, location, interest, message) VALUES (?, ?, ?, ?, ?, ?, ?)";
            supabaseClient.getJdbcTemplate().update(sql,
                    volunteer.getId(),
                    volunteer.getName(),
                    volunteer.getEmail(),
                    volunteer.getPhone(),
                    volunteer.getLocation(),
                    volunteer.getInterest(),
                    volunteer.getMessage()
            );
        }
        return volunteer;
    }
}
