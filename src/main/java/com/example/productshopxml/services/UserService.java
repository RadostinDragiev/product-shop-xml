package com.example.productshopxml.services;

import com.example.productshopxml.models.dtos.UserRegisterDto;
import com.example.productshopxml.models.dtos.UsersSoldExportDto;
import com.example.productshopxml.models.entities.User;

public interface UserService {
    void registerUser(UserRegisterDto userRegisterDto);

    User getRandomUser();

    UsersSoldExportDto getUsersSoldProducts();
}
