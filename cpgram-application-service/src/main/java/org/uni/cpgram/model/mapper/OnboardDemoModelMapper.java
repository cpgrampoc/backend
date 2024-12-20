package org.uni.cpgram.model.mapper;


import digit.models.coremodels.AuditDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.uni.cpgram.model.Grievance;
import org.uni.cpgram.model.OnboardDemo;
import org.uni.cpgram.persistence.dto.OnboardDemoDTO;
import org.uni.cpgram.persistence.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface OnboardDemoModelMapper {


    @Mapping(target = "monitoringcode", expression = "java(mapMonitoringCode(onboardDemo))")
    OnboardDemoDTO toDTO(OnboardDemo onboardDemo);

    @Mapping(target = "monitoringcode", source = "monitoringcode.id")
    OnboardDemo toModel(OnboardDemoDTO onboardDemoDTO);




    default UserDTO mapMonitoringCode(OnboardDemo onboardDemo) {
        return   UserDTO.builder().id(onboardDemo.getId()).build();

    }
    
}
