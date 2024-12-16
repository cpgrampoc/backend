package org.uni.cpgram.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Table(name = "m_demo_cpgram_categories")
@Data
@NoArgsConstructor
public class OnboardDemo {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "description")
    private String description;

    @Column(name = "orgcode")
    private String orgcode;

    @Column(name = "parent")
    private String  parent;

    @Column(name = "stage")
    private Integer stage;

    @Column(name = "descriptionhindi")
    private String descriptionhindi;

    @Column(name = "monitoringcode")
    private Integer monitoringcode;

    @Column(name = "mappingcode")
    private String mappingcode;

    @Column(name = "fieldcode")
    private Integer fieldcode;

    @Column(name = "destination")
    private String destination;

    @Column(name = "isactive")
    private String isactive;

    @Column(name = "embedding")
    private List<Double> embedding;

    @Column(name = "field_details")
    private String fieldDetails;
}

