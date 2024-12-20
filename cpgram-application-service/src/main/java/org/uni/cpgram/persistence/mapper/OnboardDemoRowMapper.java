package org.uni.cpgram.persistence.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import org.springframework.jdbc.core.RowMapper;
import org.uni.cpgram.model.OnboardDemo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OnboardDemoRowMapper implements RowMapper<OnboardDemo> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public OnboardDemo mapRow(ResultSet rs, int rowNum) throws SQLException {
        OnboardDemo demo = new OnboardDemo();

        demo.setId(rs.getLong("id"));
        demo.setDescription(rs.getString("description"));
        demo.setOrgcode(rs.getString("orgcode"));
        demo.setParent(rs.getLong("parent"));
        demo.setStage(rs.getInt("stage"));
        demo.setDescriptionhindi(rs.getString("descriptionhindi"));
        demo.setMonitoringcode(rs.getInt("monitoringcode"));
        demo.setMappingcode(rs.getString("mappingcode"));
        demo.setFieldcode(rs.getInt("fieldcode"));
        demo.setDestination(rs.getString("destination"));
        demo.setIsactive(rs.getString("isactive"));
        demo.setCode(rs.getInt("code"));

        PGobject fieldDetailsObject = (PGobject) rs.getObject("field_details");
        if (fieldDetailsObject != null) {
            String fieldDetailsJson = fieldDetailsObject.getValue();
            List<Map<String, Object>> fieldDetails = null;
            try {
                fieldDetails = objectMapper.readValue(fieldDetailsJson, new TypeReference<List<Map<String, Object>>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            demo.setFieldDetails(fieldDetails);
        }

        // Deserialize JSONB to List<Double>
        String embeddingStr = rs.getString("embedding");
        demo.setEmbedding(parseVector(embeddingStr)); // Use the helper method to parse

        return demo;
    }


    // Helper method: Convert vector string to List<Double>
    private List<Double> parseVector(String vectorString) {
        if (vectorString == null || vectorString.isEmpty()) return null;

        // Convert string format '[1.23, 2.34, 3.45]' to List<Double>
        return Arrays.stream(vectorString.replace("[", "")
                        .replace("]", "")
                        .split(","))
                .map(String::trim)
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }
}
