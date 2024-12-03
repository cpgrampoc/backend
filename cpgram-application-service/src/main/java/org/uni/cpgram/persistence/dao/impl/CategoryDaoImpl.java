package org.uni.cpgram.persistence.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.uni.cpgram.model.Category;
import org.uni.cpgram.persistence.builder.CategoryQueryBuilder;
import org.uni.cpgram.persistence.dao.CategoryDao;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Category> searchCategories(Category category) {
        StringBuilder sql = new StringBuilder("SELECT * FROM public.m_cpgram_categories WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (category.getCode() != null) {
            sql.append(" AND code = ?");
            params.add(category.getCode());
        }
        if (category.getDescription() != null) {
            sql.append(" AND description = ?");
            params.add(category.getDescription());
        }
        if (category.getOrgcode() != null) {
            sql.append(" AND orgcode = ?");
            params.add(category.getOrgcode());
        }
        if (category.getParent() != null) {
            sql.append(" AND parent = ?");
            params.add(category.getParent());
        }
        if (category.getStage() != null) {
            sql.append(" AND stage = ?");
            params.add(category.getStage());
        }
        if (category.getDescriptionhindi() != null) {
            sql.append(" AND descriptionhindi = ?");
            params.add(category.getDescriptionhindi());
        }
        if (category.getMonitoringcode() != null) {
            sql.append(" AND monitoringcode = ?");
            params.add(category.getMonitoringcode());
        }
        if (category.getMappingcode() != null) {
            sql.append(" AND mappingcode = ?");
            params.add(category.getMappingcode());
        }
        if (category.getFieldcode() != null) {
            sql.append(" AND fieldcode = ?");
            params.add(category.getFieldcode());
        }
        if (category.getDestination() != null) {
            sql.append(" AND destination = ?");
            params.add(category.getDestination());
        }
        if (category.getIsactive() != null) {
            sql.append(" AND isactive = ?");
            params.add(category.getIsactive());
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<>(Category.class));
    }

    public List<Category> searchCategoriesByText(String searchText) {
        String sql = CategoryQueryBuilder.buildSearchCategoriesByTextQuery();
        String searchPattern = "%" + searchText + "%";
        return jdbcTemplate.query(sql, new Object[]{
                searchPattern, searchPattern, searchPattern, searchPattern, searchPattern,
                searchPattern, searchPattern, searchPattern, searchPattern, searchPattern, searchPattern
        }, new BeanPropertyRowMapper<>(Category.class));
    }

}