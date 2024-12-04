package org.uni.cpgram.model.mapper;


import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.uni.cpgram.model.Category;
import org.uni.cpgram.model.Grievance;
import org.uni.cpgram.persistence.dto.CategoryDTO;
import org.uni.cpgram.persistence.dto.GrievanceDTO;

@Mapper(componentModel = "spring")
@Component
public interface GrievanceModelMapper {

    GrievanceDTO toDTO(Grievance grievance);
    Grievance toModel(GrievanceDTO grievanceDTO);



}
