package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponseDTO;
import com.ecommerce.project.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryResponseDTO getAllCategories() {
        List<Category>mList=categoryRepository.findAll();
        if(mList.isEmpty()){
            throw new APIException("Category list is Empty");
        }
        List<CategoryDTO>categoryDTOS=mList.stream()
                .map(category -> modelMapper.map(category,CategoryDTO.class))
                .toList();
        CategoryResponseDTO categoryResponseDTO=new CategoryResponseDTO();
        categoryResponseDTO.setContent(categoryDTOS);
        return categoryResponseDTO;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category=modelMapper.map(categoryDTO,Category.class);
        Category categroyFromDB=categoryRepository.findByCategoryName(category.getCategoryName());
        if(categroyFromDB!=null){
            throw new APIException("Category with name "+category.getCategoryName()+" already exists!!!");
        }
        Category savedCategory=categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryID) {
        Category savedCategoryOptional=categoryRepository.findById(categoryID).orElseThrow(
                ()->new ResourceNotFoundException("Category","categoryId",categoryID));
        categoryRepository.delete(savedCategoryOptional);
        return modelMapper.map(savedCategoryOptional,CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryID) {
        Category savedCategory=categoryRepository.findById(categoryID)
                .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryID));
        Category category=modelMapper.map(categoryDTO,Category.class);
        category.setCategoryID(categoryID);
        savedCategory=categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDTO.class);
    }
}
