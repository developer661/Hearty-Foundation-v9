package org.hearthy.config;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class SupabaseClient {
    private final String supabaseUrl;
    private final String supabaseAnonKey;
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public String getSupabaseUrl() {
        return supabaseUrl;
    }

    public String getSupabaseAnonKey() {
        return supabaseAnonKey;
    }
}
