package org.uni.cpgram.persistence.dto;
import lombok.Data;

import java.util.List;
import java.util.Vector;

@Data
public class OnboardDemoDTO {

    private String id;
    private String description;
    private String orgcode;
    private String  parent;
    private Integer stage;
    private String descriptionhindi;
    private Integer monitoringcode;
    private String mappingcode;
    private Integer fieldcode;
    private String destination;
    private String isactive;
    private List<Double> embedding;
    private String fieldDetails;
    private List<OnboardDemoDTO> categories;

    // Nested subCategories (can be null or empty)
    private List<OnboardDemoDTO> subCategories;
    private Integer code;

}

