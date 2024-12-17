package org.uni.cpgram.model.mapper;


import org.mapstruct.Mapper;
import org.uni.cpgram.model.OnboardDemo;
import org.uni.cpgram.persistence.dto.OnboardDemoDTO;

@Mapper(componentModel = "spring")
public interface OnboardDemoModelMapper {

    OnboardDemoDTO toDTO(OnboardDemo OnboardDemo);
    OnboardDemo toModel(OnboardDemoDTO OnboardDemoDTO);
    
}
