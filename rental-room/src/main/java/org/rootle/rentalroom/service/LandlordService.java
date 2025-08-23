package org.rootle.rentalroom.service;

import org.rootle.rentalroom.dto.request.LandlordRegistrationDTO;
import org.rootle.rentalroom.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.rootle.rentalroom.entity.LandlordEntity;
import org.rootle.rentalroom.repository.UserRepository;
import org.rootle.rentalroom.repository.LandlordRepository;
import org.rootle.rentalroom.enum_common.LandlordType;


import java.util.UUID;

@Service
public class LandlordService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    LandlordRepository landlordRepository;

    @Autowired
    UserRepository userRepository;


    @Transactional
    public LandlordEntity register(LandlordRegistrationDTO dto, LandlordType type) {
        UserEntity user = userRepository.findFirstByPhoneNumber(dto.getPhoneNumber()).map(existingUser -> {
            existingUser.setFullName(dto.getFullName());
            existingUser.setEmail(dto.getEmail());
            existingUser.setAddress(dto.getAddress());
            existingUser.setPhoneNumber(dto.getPhoneNumber());
            return existingUser;
        }).orElseGet(() -> {
            UserEntity newUser = new UserEntity();
            newUser.setUsername(dto.getUsername());
            newUser.setAddress(dto.getAddress());
            newUser.setUserCode(UUID.randomUUID().toString().toLowerCase());
            newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
            newUser.setFullName(dto.getFullName());
            newUser.setEmail(dto.getEmail());
            newUser.setPhoneNumber(dto.getPhoneNumber());
            newUser.setRole("SYSTEM");
            return newUser;
        });
        userRepository.save(user);
        LandlordEntity landlord = landlordRepository.findFirstLandlordByUser(user).map(existingLandlord -> {
            existingLandlord.setLandlordType(type);
            existingLandlord.setBusinessName(type == LandlordType.BUSINESS ? dto.getBusinessName() : null);
            return existingLandlord;
        }).orElseGet(() -> {
            LandlordEntity newLandlord = new LandlordEntity();
            newLandlord.setUser(user);
            newLandlord.setLandlordType(type);
            newLandlord.setBusinessName(type == LandlordType.BUSINESS ? dto.getBusinessName() : null);
            return newLandlord;
        });
        return landlordRepository.save(landlord);
    }
}
