package org.uni.cpgram.web.service;

import org.uni.cpgram.persistence.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    public List<CategoryDTO> searchCategories(CategoryDTO categoryDTO) throws Exception;

    public List<CategoryDTO> searchCategoriesByText(String searchText) throws Exception;

}
