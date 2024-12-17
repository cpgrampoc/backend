package org.uni.cpgram.persistence.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.uni.cpgram.model.OnboardDemo;
import org.uni.cpgram.persistence.dao.OnboardDao;
import org.uni.cpgram.persistence.mapper.OnboardDemoRowMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class OnboardDaoImpl implements OnboardDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Check if a description exists in the database.
     */
    @Override
    public boolean existsByDescription(String description) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(*) FROM m_demo_cpgram_categories WHERE 1=1");

        // Dynamically add conditions
        List<Object> params = new ArrayList<>();
        if (description != null) {
            sqlBuilder.append(" AND description = ?");
            params.add(description);
        }

        String sql = sqlBuilder.toString();
        int count = jdbcTemplate.queryForObject(sql, Integer.class, params.toArray());
        return count > 0;
    }

    /**
     * Find a record by description.
     */


    /**
     * Retrieve a list of records by parent.
     */
    @Override
    public List<OnboardDemo> findByParent( String searchCriteria,String Value) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM m_demo_cpgram_categories WHERE 1=1");

        // Dynamically add conditions
        List<Object> params = new ArrayList<>();
        if (searchCriteria != null) {
            if (searchCriteria.equals("parent")) {
                sqlBuilder.append(" AND parent = ?");
                params.add(Value);
            }
            if (searchCriteria.equals("description")) {
                sqlBuilder.append(" AND description = ?");
                params.add(Value);
            }

        }

        String sql = sqlBuilder.toString();

        return jdbcTemplate.query(
                sql,
                new OnboardDemoRowMapper(), // Use the custom RowMapper
                params.toArray()
        );
    }


    @Override
    public List<OnboardDemo> saveAll(List<OnboardDemo> demoList) {
        String sql = "INSERT INTO m_demo_cpgram_categories " +
                "(id, description, orgcode, parent, stage, descriptionhindi, monitoringcode, " +
                "mappingcode, fieldcode, destination, isactive, embedding, field_details, code) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?::vector, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                OnboardDemo demo = demoList.get(i);
                ps.setString(1, demo.getId());
                ps.setString(2, demo.getDescription());
                ps.setString(3, demo.getOrgcode());
                ps.setString(4, demo.getParent());
                ps.setObject(5, demo.getStage()); // Handles numeric values
                ps.setString(6, demo.getDescriptionhindi());
                ps.setObject(7, demo.getMonitoringcode()); // Handles numeric values
                ps.setString(8, demo.getMappingcode());
                ps.setObject(9, demo.getFieldcode()); // Handles numeric values
                ps.setString(10, demo.getDestination());
                ps.setString(11, demo.getIsactive());
                ps.setString(12, formatVector(demo.getEmbedding()));
                ps.setString(13, demo.getFieldDetails());
                ps.setInt(14, demo.getCode());// Convert List<Double> to vector string
            }

            @Override
            public int getBatchSize() {
                return demoList.size();
            }
        });
        return demoList;
    }

    // Helper method: Convert List<Double> to vector string format for PostgreSQL
    private String formatVector(List<Double> vector) {
        if (vector == null || vector.isEmpty()) return null;
        return "[" + vector.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ")) + "]";
    }

    // Helper method: Parse vector string from PostgreSQL to List<Double>
    private List<Double> parseVector(String vectorString) {
        if (vectorString == null || vectorString.isEmpty()) return null;

        return List.of(vectorString.replace("[", "")
                        .replace("]", "")
                        .split(","))
                .stream()
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }
}