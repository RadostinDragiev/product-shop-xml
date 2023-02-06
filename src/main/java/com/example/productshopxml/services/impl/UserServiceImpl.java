package com.example.productshopxml.services.impl;

import com.example.productshopxml.models.dtos.*;
import com.example.productshopxml.models.entities.Product;
import com.example.productshopxml.models.entities.User;
import com.example.productshopxml.models.repositories.UserRepository;
import com.example.productshopxml.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
        User user = this.modelMapper.map(userRegisterDto, User.class);
        if (this.userRepository.findUserByFirstNameAndLastNameAndAge(user.getFirstName(), user.getLastName(), user.getAge()) == null) {
            this.userRepository.saveAndFlush(user);
        }
    }

    @Override
    public User getRandomUser() {
        Random random = new Random();
        int usersCount = (int) this.userRepository.count();
        int randomId = random.nextInt(usersCount) + 1;
        if (this.userRepository.findById((long) randomId).isPresent()) {
            return this.userRepository.findById((long) randomId).get();
        }

        return new User();
    }

    @Override
    public UsersSoldExportDto getUsersSoldProducts() {
        List<User> allByProductsSoldNotNull = this.userRepository.findAllByProductsSoldNotNull();
        List<UserSoldExportDto> userSoldExportDtos = new ArrayList<>();
        allByProductsSoldNotNull.forEach(user -> {
            List<ProductSoldExportDto> productSoldExportDtos = new ArrayList<>();
            for (Product product : user.getProductsSold()) {
                String firstName = product.getBuyer().getFirstName();
                String lastName = product.getBuyer().getLastName();

                ProductSoldExportDto productSoldExportDto = new ProductSoldExportDto(product.getName(), product.getPrice(), firstName, lastName);
                productSoldExportDtos.add(productSoldExportDto);
            }

            UserSoldExportDto userSoldExportDto = this.modelMapper.map(user, UserSoldExportDto.class);
            userSoldExportDto.setProductSoldExportDto(new ProductsSoldExportDto(productSoldExportDtos));
            userSoldExportDtos.add(userSoldExportDto);
        });
        return new UsersSoldExportDto(userSoldExportDtos);
    }

}
