package org.uni.cpgram.model;






import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name ="t_cpgram_grievance")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Grievance {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "description")
    private String description;

    @Column(name = "raised_by")
    private String raisedBy;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "dept_name")
    private String deptName;

    @Column(name = "dept_code")
    private String deptCode;

    @Column(name = "gro_name")
    private String groName;

    @Column(name = "status")
    private String status;

    @Column(name = "field_1")
    private String field1;

    @Column(name = "field_2")
    private String field2;

    @Column(name = "field_3")
    private String field3;

    @Column(name = "field_4")
    private String field4;

    @Column(name = "field_5")
    private String field5;

    @Column(name = "grievance_id")
    private String grievanceId;

    @Column(name = "free_text_1")
    private String freeText1;

    @Column(name = "free_text_2")
    private String freeText2;

    @Column(name = "tracking_link")
    private String trackingLink;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Column(name = "last_updated_on")
    private Date lastUpdatedOn;


}
