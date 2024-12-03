package org.uni.cpgram.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
    private Long code;
    private String description;
    private String orgcode;
    private Long parent;
    private Long stage;
    private String descriptionhindi;
    private Long monitoringcode;
    private String mappingcode;
    private Long fieldcode;
    private String destination;
    private String isactive;
}
