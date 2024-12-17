package org.uni.cpgram.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OnboardDemo {

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
    private Integer code;
}

