package org.uni.cpgram.persistence.dto;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Vector;

@Data
public class OnboardDemoDTO {

    private Long id;
    private String description;
    private String orgcode;
    private Long  parent;
    private Integer stage;
    private String descriptionhindi;
    private UserDTO monitoringcode;
    private String mappingcode;
    private Integer fieldcode;
    private String destination;
    private String isactive;
    private List<Double> embedding;
    private List<Map<String, Object>> fieldDetails;
    private List<OnboardDemoDTO> categories;

    // Nested subCategories (can be null or empty)
    private List<OnboardDemoDTO> subCategories;
    private Integer code;

}

