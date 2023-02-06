package com.example.productshopxml.services.impl;

import com.example.productshopxml.models.dtos.CategoryRegistrationDto;
import com.example.productshopxml.models.entities.Category;
import com.example.productshopxml.models.repositories.CategoryRepository;
import com.example.productshopxml.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addCategory(CategoryRegistrationDto categoryRegistrationDto) {
        Category category = this.modelMapper.map(categoryRegistrationDto, Category.class);
        if (this.categoryRepository.findCategoryByName(category.getName()) == null) {
            this.categoryRepository.saveAndFlush(category);
        }
    }

    @Override
    public Category getRandomCategory() {
        int randomId = new Random().nextInt((int)this.categoryRepository.count()) + 1;
        return this.categoryRepository.findById((long) randomId).get();
    }

}
