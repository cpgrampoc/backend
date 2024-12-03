package org.uni.cpgram.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpgramGrievanceModel {
    private String id;
    private String description;
    private String raisedBy;
    private String mobileNo;
    private String deptName;
    private String deptCode;
    private String groName;
    private String status;
    private String field1;
    private String field2;
    private String field3;
    private String field4;
    private String field5;
    private String grievanceId;
    private String freeText1;
    private String freeText2;
    private String trackingLink;

}
