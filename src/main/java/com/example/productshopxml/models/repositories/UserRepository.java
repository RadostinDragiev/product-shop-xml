package com.example.productshopxml.models.repositories;

import com.example.productshopxml.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByFirstNameAndLastNameAndAge(String firstName, String lastName, int age);

    @Query("SELECT u FROM User AS u JOIN Product AS p ON p.seller = u.id AND p.buyer IS NOT NULL WHERE u.productsSold.size > 0 ORDER BY u.lastName, u.firstName")
    List<User> findAllByProductsSoldNotNull();

    @Query("SELECT DISTINCT u FROM User AS u JOIN Product AS p ON u.id = p.seller.id ORDER BY u.productsSold.size DESC")
    List<User> findAllByProductsSoldNotNullOrderByProductsSoldDescLastNameAsc();
}
