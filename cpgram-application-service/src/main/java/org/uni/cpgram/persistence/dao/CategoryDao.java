package org.uni.cpgram.persistence.dao;

import org.uni.cpgram.model.Category;

import java.util.List;

public interface CategoryDao {

    public List<Category> searchCategories(Category category);

    public List<Category> searchCategoriesByText(String searchText);


}
