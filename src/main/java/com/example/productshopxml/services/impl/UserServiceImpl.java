package com.example.productshopxml.services.impl;

import com.example.productshopxml.models.dtos.UserRegisterDto;
import com.example.productshopxml.models.entities.User;
import com.example.productshopxml.models.repositories.UserRepository;
import com.example.productshopxml.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
