package org.uni.cpgram.persistence.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
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
    private final JdbcTemplate jdbcTemplate;

    ObjectMapper objectMapper = new ObjectMapper();

    public OnboardDaoImpl(@Qualifier("primaryJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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
    public List<OnboardDemo> findByParent( String searchCriteria,Long value) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM m_demo_cpgram_categories WHERE 1=1");

        // Dynamically add conditions
        List<Object> params = new ArrayList<>();
        if (searchCriteria != null) {
            if (searchCriteria.equals("parent")) {
                if (value!=null) {
                    sqlBuilder.append(" AND parent = ?");
                    params.add(value);
                }else {
                    sqlBuilder.append(" AND parent is null");
                }
            }

        }

        String sql = sqlBuilder.toString();

        return jdbcTemplate.query(
                sql,
                new OnboardDemoRowMapper(), // Use the custom RowMapper
                params.toArray()
        );
    }








    public List<OnboardDemo> findByDescription(String searchCriteria, String value) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM m_demo_cpgram_categories WHERE 1=1");

        // Dynamically add conditions
        List<Object> params = new ArrayList<>();
        if (searchCriteria != null) {

            if (searchCriteria.equals("description")) {
                sqlBuilder.append(" AND description = ?");
                params.add(value);
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
                "mappingcode, fieldcode, destination, isactive, embedding, code,field_details) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?::vector, ?, ?::jsonb)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                OnboardDemo demo = demoList.get(i);
                ps.setLong(1, demo.getId());
                ps.setString(2, demo.getDescription());
                ps.setString(3, demo.getOrgcode());
                ps.setLong(4, (demo.getParent() != null) ? demo.getParent() : 0L);
                ps.setObject(5, demo.getStage()); // Handles numeric values
                ps.setString(6, demo.getDescriptionhindi());
                ps.setObject(7, demo.getMonitoringcode()); // Handles numeric values
                ps.setString(8, demo.getMappingcode());
                ps.setObject(9, demo.getFieldcode()); // Handles numeric values
                ps.setString(10, demo.getDestination());
                ps.setString(11, demo.getIsactive());
                ps.setString(12, formatVector(demo.getEmbedding()));
                ps.setInt(13, demo.getCode());// Convert List<Double> to vector string

                try {
                    ps.setString(14, objectMapper.writeValueAsString(demo.getFieldDetails()));
                } catch (JsonProcessingException e) {
                    throw new SQLException("Error converting fieldDetails or categories to JSON", e);
                }

            }

            @Override
            public int getBatchSize() {
                return demoList.size();
            }
        });
        return demoList;
    }

    @Override
    public long getNextUserId() {
        // Fetch the latest ID using a SQL query
        String sql = "SELECT COALESCE(MAX(id), 0) FROM public.m_demo_cpgram_categories";

        Long latestId = jdbcTemplate.queryForObject(sql, Long.class);

        // Increment the ID to set the next value
        long nextId = latestId != null ? latestId +1: 1;

        return nextId;
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