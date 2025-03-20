package com.br.foodconnect.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerCredentialRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM customer_credential WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count > 0;
    }
}
