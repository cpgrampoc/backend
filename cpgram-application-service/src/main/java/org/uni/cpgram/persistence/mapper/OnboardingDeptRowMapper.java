package org.uni.cpgram.persistence.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import org.springframework.jdbc.core.RowMapper;
import org.uni.cpgram.model.OnboardingDept;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class OnboardingDeptRowMapper implements RowMapper<OnboardingDept> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public OnboardingDept mapRow(ResultSet rs, int rowNum) throws SQLException {
        OnboardingDept onboardingDept = new OnboardingDept();
        onboardingDept.setUuid(rs.getString("uuid"));
        onboardingDept.setDeptCode(rs.getString("dept_code"));
        onboardingDept.setDeptName(rs.getString("dept_name"));
        onboardingDept.setFieldName(rs.getString("field_name"));
        onboardingDept.setLevel(rs.getInt("level"));
        onboardingDept.setParentLevel(rs.getInt("parent_level"));
        onboardingDept.setIsMandatory(rs.getBoolean("is_mandatory"));
        onboardingDept.setOwnerName(rs.getString("owner_name"));

        PGobject fieldDetailsObject = (PGobject) rs.getObject("field_details");
        if (fieldDetailsObject != null) {
            String fieldDetailsJson = fieldDetailsObject.getValue();
            List<Map<String, Object>> fieldDetails = null;
            try {
                fieldDetails = objectMapper.readValue(fieldDetailsJson, new TypeReference<List<Map<String, Object>>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            onboardingDept.setFieldDetails(fieldDetails);
        }

        PGobject categoriesObject = (PGobject) rs.getObject("categories");
        if (categoriesObject != null) {
            String categoriesJson = categoriesObject.getValue();
            List<Map<String, Object>> categories = null;
            try {
                categories = objectMapper.readValue(categoriesJson, new TypeReference<List<Map<String, Object>>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            onboardingDept.setCategories(categories);
        }

        return onboardingDept;
    }
}
