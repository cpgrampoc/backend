package org.uni.cpgram.model.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.uni.cpgram.model.Category;
import org.uni.cpgram.persistence.dto.CategoryDTO;


@Mapper(componentModel = "spring")
public interface CategoryModelMapper {
    CategoryDTO toDTO(Category category);
    Category toModel(CategoryDTO categoryDTO);
}
