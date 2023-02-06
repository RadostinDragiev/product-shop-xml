package com.example.productshopxml.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {
    private String name;

    private BigDecimal price;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    private User buyer;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.DETACH)
    private User seller;

    @ManyToMany(targetEntity = Category.class, cascade = CascadeType.ALL)
    private Set<Category> categories;
}
