package org.rootle.rentalroom.service;

import org.rootle.rentalroom.dto.UserDto;
import org.rootle.rentalroom.entity.UserEntity;
import org.rootle.rentalroom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity save(UserDto data) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(data.getUsername());
        userEntity.setPasswordHash(data.getPassword());
        userEntity.setFullName(data.getFullName());
        userEntity.setAddress(data.getAddress());
        userEntity.setPhoneNumber(data.getPhoneNumber());
        userEntity.setEmail(data.getEmail());
        return userRepository.save(userEntity);
    }
}
