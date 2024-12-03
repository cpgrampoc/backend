package org.uni.cpgram.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uni.cpgram.persistence.dto.CategoryDTO;
import org.uni.cpgram.web.request.CategoryRequest;
import org.uni.cpgram.web.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/search")
    public ResponseEntity<List<CategoryDTO>> searchCategories(@RequestBody CategoryRequest categoryRequest) {
        try {
            List<CategoryDTO> categories = new ArrayList<>();
            if (categoryRequest.getSearchFields() != null) {
                categories = categoryService.searchCategories(categoryRequest.getSearchFields());
            } else if (!StringUtils.isEmpty(categoryRequest.getSearchText())) {
                categories = categoryService.searchCategoriesByText(categoryRequest.getSearchText());
            }
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
