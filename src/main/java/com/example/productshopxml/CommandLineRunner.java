package com.example.productshopxml;

import com.example.productshopxml.models.dtos.*;
import com.example.productshopxml.services.CategoryService;
import com.example.productshopxml.services.ProductService;
import com.example.productshopxml.services.UserService;
import com.example.productshopxml.utils.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.math.BigDecimal;

@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {
    private final XmlParser xmlParser;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public CommandLineRunner(XmlParser xmlParser, CategoryService categoryService, ProductService productService, UserService userService) {
        this.xmlParser = xmlParser;
        this.categoryService = categoryService;
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        //seedUsers();
        //seedCategories();
        //seedProducts();

        //productsInRange();

        usersSoldProducts();

    }

    // Query 2 – Successfully Sold Products
    private void usersSoldProducts() throws JAXBException {
        this.xmlParser.marshalFile(this.userService.getUsersSoldProducts(), "src/main/resources/files/output/users-sold-products.xml");
    }

    // Query 1 – Products in Range
    private void productsInRange() throws JAXBException {
        ProductsExportDto productsExportDto = new ProductsExportDto(this.productService.findProductsInRange(new BigDecimal("500"), new BigDecimal("1000")));
        this.xmlParser.marshalFile(productsExportDto, "src/main/resources/files/output/products-in-range.xml");
    }

    private void seedUsers() throws JAXBException, FileNotFoundException {
        UserRootRegisterDTo userRootRegisterDTo = (UserRootRegisterDTo) this.xmlParser.unmarshalFile(UserRootRegisterDTo.class, "src/main/resources/files/data/users.xml");
        userRootRegisterDTo.getUsers().forEach(userService::registerUser);
    }

    private void seedProducts() throws JAXBException, FileNotFoundException {
        ProductRootRegisterDto productRootRegisterDto = (ProductRootRegisterDto) xmlParser.unmarshalFile(ProductRootRegisterDto.class, "src/main/resources/files/data/products.xml");
        productRootRegisterDto.getProducts().forEach(productService::addProduct);
    }

    private void seedCategories() throws JAXBException, FileNotFoundException {
        CategoryRootRegisterDto categoryRootRegisterDto = (CategoryRootRegisterDto) xmlParser.unmarshalFile(CategoryRootRegisterDto.class, "src/main/resources/files/data/categories.xml");
        categoryRootRegisterDto.getCategories().forEach(categoryService::addCategory);
    }
}
