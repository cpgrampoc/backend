package org.uni.cpgram.web.service;

import org.uni.cpgram.persistence.dto.GrievanceDTO;
import org.uni.cpgram.web.response.GrievanceResponse;

import java.util.Optional;

public interface GrievanceService {
    GrievanceResponse createGrievance(GrievanceDTO grievanceDTO);
    Optional<GrievanceDTO> getGrievanceById(String id);
}
