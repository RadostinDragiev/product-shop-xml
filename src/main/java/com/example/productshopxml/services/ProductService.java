package com.example.productshopxml.services;

import com.example.productshopxml.models.dtos.ProductExportDto;
import com.example.productshopxml.models.dtos.ProductRegistrationDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void addProduct(ProductRegistrationDto productRegistrationDto);

    List<ProductExportDto> findProductsInRange(BigDecimal min, BigDecimal max);
}
