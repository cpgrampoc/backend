package org.uni.cpgram.web.serviceimpl;

import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uni.cpgram.model.Grievance;
import org.uni.cpgram.model.mapper.GrievanceModelMapper;
import org.uni.cpgram.persistence.dto.GrievanceDTO;
import org.uni.cpgram.repository.GrievanceRepository;
import org.uni.cpgram.utils.CpgramApplicationUtils;
import org.uni.cpgram.web.request.SearchCriteria;
import org.uni.cpgram.web.response.GrievanceResponse;
import org.uni.cpgram.web.service.GrievanceService;

import java.time.Year;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GrievanceServiceImpl implements GrievanceService {

    @Autowired
    private GrievanceModelMapper grievanceModelMapper;

    @Autowired
    private GrievanceRepository grievanceRepository;

    @Autowired
    private CpgramApplicationUtils cpgramApplicationUtils;

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
        AuditDetails auditDetails = cpgramApplicationUtils.buildCreateAuditDetails();
        grievanceDTO.setAuditDetails(auditDetails);
       Grievance grievance= grievanceModelMapper.toModel(grievanceDTO);
        grievance.setId(UUID.randomUUID().toString());
        grievance.setGrievanceId(generateGrievanceNumber());
        Grievance savedGrievance = grievanceRepository.save(grievance);
        return GrievanceResponse.builder().grievanceDTO(Collections.singletonList(grievanceModelMapper.toDTO(savedGrievance))).build();

    }

    @Override
    public GrievanceResponse getGrievanceById(SearchCriteria searchCriteria) {
        Set<String> grievanceIds = searchCriteria.getGrievanceId();
        List<Grievance> grievances;

        try {
            // If grievanceIds is null or empty, fetch all grievances from the database
            if (grievanceIds == null || grievanceIds.isEmpty()) {
                grievances = grievanceRepository.findAll(); // Fetch all grievances
            } else {
                // If grievanceIds are provided, fetch specific grievances by grievanceId
                grievances = grievanceRepository.findAllByGrievanceIdIn(grievanceIds);
            }

            // Convert List<Grievance> to List<GrievanceDTO>
            List<GrievanceDTO> grievanceDTOs = grievances.stream()
                    .map(grievanceModelMapper::toDTO)
                    .collect(Collectors.toList());

            // Return the response with the list of DTOs
            return GrievanceResponse.builder().grievanceDTO(grievanceDTOs).build();

        } catch (Exception e) {
            // Log the error for debugging
            log.error("Error occurred while fetching grievances: ", e);

            // Return an empty response or throw a custom exception as needed
            return GrievanceResponse.builder().grievanceDTO(Collections.emptyList()).build();
        }
    }


}
