package org.uni.cpgram.persistence.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.uni.cpgram.model.OnboardingDept;
import org.uni.cpgram.persistence.builder.OnboardingDeptQueryBuilder;
import org.uni.cpgram.persistence.dao.OnboardingDeptDao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class OnboardingDeptDaoImpl implements OnboardingDeptDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String create(OnboardingDept onboardingDept) {
        if (onboardingDept.getUuid() == null) {
            onboardingDept.setUuid(UUID.randomUUID().toString());
        }
        String sql = OnboardingDeptQueryBuilder.getCreateQuery();
        jdbcTemplate.update(sql, onboardingDept.getUuid(), onboardingDept.getDeptCode(), onboardingDept.getDeptName(), onboardingDept.getFieldName(), onboardingDept.getLevel(), onboardingDept.getParentLevel(), onboardingDept.getIsMandatory(), onboardingDept.getOwnerName());
        return onboardingDept.getUuid();
    }

    @Override
    public Boolean update(OnboardingDept onboardingDept) {
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

        return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<>(OnboardingDept.class));
    }

}