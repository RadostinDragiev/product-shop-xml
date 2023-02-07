package com.example.productshopxml.models.repositories;

import com.example.productshopxml.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryByName(String name);

    @Query(value = "SELECT c.name, COUNT(p.id) AS 'Count', AVG(p.price), SUM(p.price) FROM category AS c LEFT JOIN products_categories pc on c.id = pc.categories_id LEFT JOIN products p on p.id = pc.products_id GROUP BY c.id, c.name ORDER BY Count DESC;", nativeQuery = true)
    List<String> findAllCategoryByProductCount();
}
