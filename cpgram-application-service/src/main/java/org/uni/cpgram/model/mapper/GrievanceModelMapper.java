package org.uni.cpgram.model.mapper;


import digit.models.coremodels.AuditDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import org.uni.cpgram.model.DateMapper;
import org.uni.cpgram.model.Grievance;
import org.uni.cpgram.persistence.dto.GrievanceDTO;

@Mapper(componentModel = "spring", uses = {DateMapper.class})
@Component
public interface GrievanceModelMapper {


    @Mapping(target = "auditDetails", expression = "java(mapAuditDetails(grievance))")
    GrievanceDTO toDTO(Grievance grievance);


    @Mapping(target = "createdBy", source = "auditDetails.createdBy")
    @Mapping(target = "lastUpdatedBy", source = "auditDetails.lastModifiedBy")
    @Mapping(target = "createdOn", source = "auditDetails.createdTime")
    @Mapping(target = "lastUpdatedOn", source = "auditDetails.lastModifiedTime")
    Grievance toModel(GrievanceDTO grievanceDTO);

    default AuditDetails mapAuditDetails(Grievance grievance) {

        return AuditDetails.builder()
                .createdBy(grievance.getCreatedBy() != null ? grievance.getCreatedBy() : "Unknown") // Default value if null
                .createdTime(grievance.getCreatedOn() != null ? grievance.getCreatedOn().getTime() : null) // Handle null date
                .lastModifiedBy(grievance.getLastUpdatedBy() != null ? grievance.getLastUpdatedBy() : "Unknown")
                .lastModifiedTime(grievance.getLastUpdatedOn() != null ? grievance.getLastUpdatedOn().getTime() : null)
                .build();
    }



}
