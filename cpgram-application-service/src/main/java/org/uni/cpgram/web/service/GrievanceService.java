package org.uni.cpgram.web.service;

import org.uni.cpgram.persistence.dto.GrievanceDTO;
import org.uni.cpgram.web.request.SearchCriteria;
import org.uni.cpgram.web.response.GrievanceResponse;

public interface GrievanceService {
    GrievanceResponse createGrievance(GrievanceDTO grievanceDTO);

    GrievanceResponse getGrievanceById(SearchCriteria searchCriteria);
}
