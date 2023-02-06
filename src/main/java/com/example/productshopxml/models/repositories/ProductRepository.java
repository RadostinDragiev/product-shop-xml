package com.example.productshopxml.models.repositories;

import com.example.productshopxml.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductsByName(String name);

    List<Product> findProductsByPriceBetweenAndBuyerIsNull(BigDecimal min, BigDecimal max);
}
