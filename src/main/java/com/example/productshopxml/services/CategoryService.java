package com.example.productshopxml.services;

import com.example.productshopxml.models.dtos.CategoriesByProductExportDto;
import com.example.productshopxml.models.dtos.CategoryRegistrationDto;
import com.example.productshopxml.models.entities.Category;

public interface CategoryService {
    void addCategory(CategoryRegistrationDto categoryRegistrationDto);

    Category getRandomCategory();

    CategoriesByProductExportDto getCategoryWithCount();

}
