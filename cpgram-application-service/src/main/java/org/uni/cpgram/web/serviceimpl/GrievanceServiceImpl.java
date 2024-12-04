package org.uni.cpgram.web.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uni.cpgram.model.Grievance;
import org.uni.cpgram.model.mapper.GrievanceModelMapper;
import org.uni.cpgram.persistence.dto.GrievanceDTO;
import org.uni.cpgram.repository.GrievanceRepository;
import org.uni.cpgram.web.response.GrievanceResponse;
import org.uni.cpgram.web.service.GrievanceService;

import java.time.Year;
import java.util.Optional;
import java.util.UUID;

@Service
public class GrievanceServiceImpl implements GrievanceService {

    @Autowired
    private GrievanceModelMapper grievanceModelMapper;

    @Autowired
    private GrievanceRepository grievanceRepository;

    private String generateGrievanceNumber() {
        String prefix = "GRV";
        int currentYear = Year.now().getValue();
        String yearRange = currentYear + "-" + (currentYear + 1);

        // Get the latest claim to determine the next sequence number
        String latestClaim = grievanceRepository.findTopByOrderByGrievanceIdDesc();
        int nextSequence = (latestClaim != null) ? Integer.parseInt(latestClaim.split("/")[2]) + 1 : 1;

        // Format the sequence as a four-digit number with leading zeros
        String sequence = String.format("%04d", nextSequence);

        // Combine parts to form the full claim number
        return prefix + "/" + yearRange + "/" + sequence;
    }


    @Override
    public GrievanceResponse createGrievance(GrievanceDTO grievanceDTO) {
       Grievance grievance= grievanceModelMapper.toModel(grievanceDTO);
        grievance.setId(UUID.randomUUID().toString());
        grievance.setGrievanceId(generateGrievanceNumber());
        Grievance savedGrievance = grievanceRepository.save(grievance);
        return GrievanceResponse.builder().grievanceDTO(grievanceModelMapper.toDTO(savedGrievance)).build();

    }

    @Override
    public Optional<GrievanceDTO> getGrievanceById(String id) {
        return Optional.empty();
    }
}
