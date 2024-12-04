package org.uni.cpgram.web.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.uni.cpgram.persistence.dto.GrievanceDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrievanceRequest {

    private GrievanceDTO grievanceDTO;

    //will change to requestInfo Class later
    private String requestInfo;


}
