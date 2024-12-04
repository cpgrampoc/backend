package org.uni.cpgram.web.response;


import lombok.Builder;
import lombok.Data;
import org.uni.cpgram.persistence.dto.GrievanceDTO;

@Data
@Builder
public class GrievanceResponse {

    private GrievanceDTO grievanceDTO;
}
