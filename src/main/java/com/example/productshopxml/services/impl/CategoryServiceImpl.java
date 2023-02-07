package com.example.productshopxml.services.impl;

import com.example.productshopxml.models.dtos.CategoriesByProductExportDto;
import com.example.productshopxml.models.dtos.CategoryByProductExportDto;
import com.example.productshopxml.models.dtos.CategoryRegistrationDto;
import com.example.productshopxml.models.entities.Category;
import com.example.productshopxml.models.repositories.CategoryRepository;
import com.example.productshopxml.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        int randomId = new Random().nextInt((int) this.categoryRepository.count()) + 1;
        return this.categoryRepository.findById((long) randomId).get();
    }

    @Override
    public CategoriesByProductExportDto getCategoryWithCount() {
        List<CategoryByProductExportDto> category = new ArrayList<>();
        this.categoryRepository.findAllCategoryByProductCount().forEach(record -> {
            String[] splitRecord = record.split(",");
            category.add(new CategoryByProductExportDto(splitRecord[0], Integer.parseInt(splitRecord[1]), Double.parseDouble(splitRecord[2]), Double.parseDouble(splitRecord[3])));
        });
        return new CategoriesByProductExportDto(category);
    }

}
