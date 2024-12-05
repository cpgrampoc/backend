package org.uni.cpgram.web.response;


import lombok.Builder;
import lombok.Data;
import org.uni.cpgram.persistence.dto.GrievanceDTO;

import java.util.List;

@Data
@Builder
public class GrievanceResponse {

    private List<GrievanceDTO> grievanceDTO;
}
