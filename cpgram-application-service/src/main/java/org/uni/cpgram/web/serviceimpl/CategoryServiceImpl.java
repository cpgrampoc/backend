package org.uni.cpgram.web.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.uni.cpgram.model.Category;
import org.uni.cpgram.model.mapper.CategoryModelMapper;
import org.uni.cpgram.persistence.dao.CategoryDao;
import org.uni.cpgram.persistence.dto.CategoryDTO;
import org.uni.cpgram.web.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryRepository;

    @Autowired
    private CategoryModelMapper categoryMapper;

    public List<CategoryDTO> searchCategories(CategoryDTO categoryDTO) throws Exception {
        try {
            Category category = categoryMapper.toModel(categoryDTO);
            List<Category> categories = categoryRepository.searchCategories(category);
            return categories.stream().map(categoryMapper::toDTO).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while searching categories: ", e);
            throw new Exception("Failed to search categories", e);
        }
    }

    public List<CategoryDTO> searchCategoriesByText(String searchText) throws Exception {
        try {
            List<Category> categories = categoryRepository.searchCategoriesByText(searchText);
            return categories.stream().map(categoryMapper::toDTO).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while searching categories by text: ", e);
            throw new Exception("Failed to search categories by text", e);
        }
    }
}
