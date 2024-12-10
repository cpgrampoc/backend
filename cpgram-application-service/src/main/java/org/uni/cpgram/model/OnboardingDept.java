package org.uni.cpgram.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OnboardingDept {
    private String uuid;
    private String deptCode;
    private String deptName;
    private String fieldName;
    private Integer level;
    private Integer parentLevel;
    private Boolean isMandatory;
    private String ownerName;
    private List<Map<String, Object>> categories;
    private List<Map<String, Object>> fieldDetails;
}
