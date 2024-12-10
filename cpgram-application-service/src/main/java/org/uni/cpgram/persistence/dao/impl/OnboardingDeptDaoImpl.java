package org.uni.cpgram.persistence.dao.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.uni.cpgram.model.OnboardingDept;
import org.uni.cpgram.persistence.builder.OnboardingDeptQueryBuilder;
import org.uni.cpgram.persistence.dao.OnboardingDeptDao;
import org.uni.cpgram.persistence.mapper.OnboardingDeptRowMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class OnboardingDeptDaoImpl implements OnboardingDeptDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String create(OnboardingDept onboardingDept) {
        if (onboardingDept.getUuid() == null) {
            onboardingDept.setUuid(UUID.randomUUID().toString());
        }
        String sql = "INSERT INTO m_onboarding_dept (uuid, dept_code, dept_name, field_name, level, parent_level, is_mandatory, owner_name, field_details, categories) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?::jsonb, ?::jsonb)";
        jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, onboardingDept.getUuid());
                ps.setString(2, onboardingDept.getDeptCode());
                ps.setString(3, onboardingDept.getDeptName());
                ps.setString(4, onboardingDept.getFieldName());
                ps.setInt(5, onboardingDept.getLevel());
                ps.setInt(6, onboardingDept.getParentLevel());
                ps.setBoolean(7, onboardingDept.getIsMandatory());
                ps.setString(8, onboardingDept.getOwnerName());
                try {
                    ps.setString(9, objectMapper.writeValueAsString(onboardingDept.getFieldDetails()));
                    ps.setString(10, objectMapper.writeValueAsString(onboardingDept.getCategories()));
                } catch (JsonProcessingException e) {
                    throw new SQLException("Error converting fieldDetails or categories to JSON", e);
                }
            }
        });
        return onboardingDept.getUuid();
    }

    @Override
    public Boolean update(OnboardingDept onboardingDept) throws SQLException {
        StringBuilder sql = new StringBuilder(OnboardingDeptQueryBuilder.getUpdateQuery());
        List<Object> params = new ArrayList<>();

        if (onboardingDept.getDeptCode() != null) {
            sql.append("dept_code = ?, ");
            params.add(onboardingDept.getDeptCode());
        }
        if (onboardingDept.getDeptName() != null) {
            sql.append("dept_name = ?, ");
            params.add(onboardingDept.getDeptName());
        }
        if (onboardingDept.getFieldName() != null) {
            sql.append("field_name = ?, ");
            params.add(onboardingDept.getFieldName());
        }
        if (onboardingDept.getLevel() != null) {
            sql.append("level = ?, ");
            params.add(onboardingDept.getLevel());
        }
        if (onboardingDept.getParentLevel() != null) {
            sql.append("parent_level = ?, ");
            params.add(onboardingDept.getParentLevel());
        }
        if (onboardingDept.getIsMandatory() != null) {
            sql.append("is_mandatory = ?, ");
            params.add(onboardingDept.getIsMandatory());
        }
        if (onboardingDept.getOwnerName() != null) {
            sql.append("owner_name = ?, ");
            params.add(onboardingDept.getOwnerName());
        }
        if (onboardingDept.getFieldDetails() != null) {
            try {
                sql.append("field_details = ?::jsonb, ");
                params.add(objectMapper.writeValueAsString(onboardingDept.getFieldDetails()));
            } catch (JsonProcessingException e) {
                throw new SQLException("Error converting fieldDetails to JSON", e);
            }
        }
        if (onboardingDept.getCategories() != null) {
            try {
                sql.append("categories = ?::jsonb, ");
                params.add(objectMapper.writeValueAsString(onboardingDept.getCategories()));
            } catch (JsonProcessingException e) {
                throw new SQLException("Error converting categories to JSON", e);
            }
        }

        sql.setLength(sql.length() - 2);
        sql.append(" WHERE uuid = ?");
        params.add(onboardingDept.getUuid());

        int rowsAffected = jdbcTemplate.update(sql.toString(), params.toArray());
        return rowsAffected > 0;
    }


    @Override
    public List<OnboardingDept> search(OnboardingDept onboardingDept) {
        StringBuilder sql = new StringBuilder(OnboardingDeptQueryBuilder.getSearchQuery());
        List<Object> params = new ArrayList<>();

        if (onboardingDept.getUuid() != null) {
            sql.append("AND uuid = ? ");
            params.add(onboardingDept.getUuid());
        }
        if (onboardingDept.getDeptCode() != null) {
            sql.append("AND dept_code = ? ");
            params.add(onboardingDept.getDeptCode());
        }
        if (onboardingDept.getDeptName() != null) {
            sql.append("AND dept_name = ? ");
            params.add(onboardingDept.getDeptName());
        }
        if (onboardingDept.getFieldName() != null) {
            sql.append("AND field_name = ? ");
            params.add(onboardingDept.getFieldName());
        }
        if (onboardingDept.getLevel() != null) {
            sql.append("AND level = ? ");
            params.add(onboardingDept.getLevel());
        }
        if (onboardingDept.getParentLevel() != null) {
            sql.append("AND parent_level = ? ");
            params.add(onboardingDept.getParentLevel());
        }
        if (onboardingDept.getIsMandatory() != null) {
            sql.append("AND is_mandatory = ? ");
            params.add(onboardingDept.getIsMandatory());
        }
        if (onboardingDept.getOwnerName() != null) {
            sql.append("AND owner_name = ? ");
            params.add(onboardingDept.getOwnerName());
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), new OnboardingDeptRowMapper());
    }

}