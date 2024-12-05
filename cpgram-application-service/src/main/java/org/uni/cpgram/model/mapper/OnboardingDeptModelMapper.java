package org.uni.cpgram.model.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.uni.cpgram.model.OnboardingDept;
import org.uni.cpgram.persistence.dto.OnboardingDeptDTO;

@Mapper(componentModel = "spring")
@Component
public interface OnboardingDeptModelMapper {
    OnboardingDept toModel(OnboardingDeptDTO dto);
    OnboardingDeptDTO toDTO(OnboardingDept model);
}
