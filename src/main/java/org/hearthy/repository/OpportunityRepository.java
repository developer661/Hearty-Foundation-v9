package org.hearthy.repository;

import lombok.RequiredArgsConstructor;
import org.hearthy.config.SupabaseClient;
import org.hearthy.model.Opportunity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OpportunityRepository {
    private final SupabaseClient supabaseClient;

    private final RowMapper<Opportunity> rowMapper = new RowMapper<Opportunity>() {
        @Override
        public Opportunity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Opportunity.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .title(rs.getString("title"))
                    .description(rs.getString("description"))
                    .category(rs.getString("category"))
                    .institutionName(rs.getString("institution_name"))
                    .location(rs.getString("location"))
                    .urgency(rs.getString("urgency"))
                    .status(rs.getString("status"))
                    .createdAt(rs.getObject("created_at", java.time.OffsetDateTime.class))
                    .build();
        }
    };

    public List<Opportunity> findAll() {
        String sql = "SELECT * FROM opportunities WHERE status = 'active' ORDER BY created_at DESC";
        return supabaseClient.getJdbcTemplate().query(sql, rowMapper);
    }

    public List<Opportunity> findUrgent() {
        String sql = "SELECT * FROM opportunities WHERE status = 'active' AND urgency = 'urgent' ORDER BY created_at DESC LIMIT 10";
        return supabaseClient.getJdbcTemplate().query(sql, rowMapper);
    }

    public Optional<Opportunity> findById(UUID id) {
        String sql = "SELECT * FROM opportunities WHERE id = ?";
        List<Opportunity> results = supabaseClient.getJdbcTemplate().query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public Opportunity save(Opportunity opportunity) {
        if (opportunity.getId() == null) {
            opportunity.setId(UUID.randomUUID());
            String sql = "INSERT INTO opportunities (id, title, description, category, institution_name, location, urgency, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            supabaseClient.getJdbcTemplate().update(sql,
                    opportunity.getId(),
                    opportunity.getTitle(),
                    opportunity.getDescription(),
                    opportunity.getCategory(),
                    opportunity.getInstitutionName(),
                    opportunity.getLocation(),
                    opportunity.getUrgency(),
                    opportunity.getStatus()
            );
        }
        return opportunity;
    }
}
