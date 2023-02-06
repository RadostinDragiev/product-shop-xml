package com.example.productshopxml.models.repositories;

import com.example.productshopxml.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByFirstNameAndLastNameAndAge(String firstName, String lastName, int age);
}
