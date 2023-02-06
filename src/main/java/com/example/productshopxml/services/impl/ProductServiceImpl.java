package com.example.productshopxml.services.impl;

import com.example.productshopxml.models.dtos.ProductExportDto;
import com.example.productshopxml.models.dtos.ProductRegistrationDto;
import com.example.productshopxml.models.entities.Category;
import com.example.productshopxml.models.entities.Product;
import com.example.productshopxml.models.repositories.ProductRepository;
import com.example.productshopxml.services.CategoryService;
import com.example.productshopxml.services.ProductService;
import com.example.productshopxml.services.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, UserService userService, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public void addProduct(ProductRegistrationDto productRegistrationDto) {
        Product product = this.modelMapper.map(productRegistrationDto, Product.class);
        if (this.productRepository.findProductsByName(product.getName()) == null) {
            product.setCategories(this.getCategories());
            product.setSeller(this.userService.getRandomUser());
            if (product.getSeller().getId() > 10) {
                product.setBuyer(this.userService.getRandomUser());
            }
            this.productRepository.saveAndFlush(product);
        }
    }

    @Override
    public List<ProductExportDto> findProductsInRange(BigDecimal min, BigDecimal max) {
        List<Product> products = this.productRepository.findProductsByPriceBetweenAndBuyerIsNull(min, max);
        this.modelMapper.createTypeMap(Product.class, ProductExportDto.class)
                .addMappings(new PropertyMap<Product, ProductExportDto>() {
                    @Override
                    protected void configure() {
                        using(ctx -> ((Product) ctx.getSource()).getSeller().getFirstName() + " " + ((Product) ctx.getSource()).getSeller().getLastName())
                                .map(source, destination.getSeller());
                    }
                });
        return Arrays.stream(this.modelMapper.map(products, ProductExportDto[].class)).collect(Collectors.toList());
    }


    private Set<Category> getCategories() {
        Set<Category> categories = new HashSet<>();
        int categoryCount = new Random().nextInt(3) + 1;
        for (int i = 0; i < categoryCount; i++) {
            categories.add(this.categoryService.getRandomCategory());
        }
        return categories;
    }
}
