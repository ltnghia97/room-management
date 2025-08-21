package org.rootle.rentalroom.service;

import org.rootle.rentalroom.dto.UserDto;
import org.rootle.rentalroom.entity.UserEntity;
import org.rootle.rentalroom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity save(UserDto userDto) {
        UserEntity data = userRepository.findFirstByPhoneNumber(userDto.getPhoneNumber())
                .orElseGet(() -> {
                    UserEntity newUser = new UserEntity();
                    newUser.setCreatedAt(new Date());
                    return newUser;
                });

        data.setUsername(userDto.getUsername());
        data.setPassword(userDto.getPassword());
        data.setFullName(userDto.getFullName());
        data.setAddress(userDto.getAddress());
        data.setPhoneNumber(userDto.getPhoneNumber());
        data.setEmail(userDto.getEmail());

        return userRepository.save(data);
    }
}
