package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
   // private List<Category>categories=new ArrayList<>();
//    private Long nextID=1L;

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        //category.setCategoryID(nextID++);
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryID) {
        Category savedCategoryOptional=categoryRepository.findById(categoryID).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource Not Found"));
        categoryRepository.delete(savedCategoryOptional);
        return "item deleted successfully";
    }

    @Override
    public Category updateCategory(Category category, Long categoryID) {
        Optional<Category>savedCategoryOptional=categoryRepository.findById(categoryID);
        Category savedCategory=savedCategoryOptional
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource Not Found"));
        category.setCategoryID(categoryID);
        savedCategory=categoryRepository.save(category);
        return savedCategory;
    }
}
