package org.rootle.rentalroom.service;

import org.rootle.rentalroom.dto.request.auth.ChangePasswordRequestDto;
import org.rootle.rentalroom.dto.request.auth.LoginRequestDto;
import org.rootle.rentalroom.dto.request.auth.RegisterRequestDto;
import org.rootle.rentalroom.dto.response.auth.LoginResponseDto;
import org.rootle.rentalroom.entity.RefreshTokenEntity;
import org.rootle.rentalroom.entity.UserEntity;
import org.rootle.rentalroom.repository.RefreshTokenRepository;
import org.rootle.rentalroom.repository.UserRepository;
import org.rootle.rentalroom.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AuthService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    public UserEntity register(RegisterRequestDto requestDto) {
        Optional<UserEntity> existingUser = userRepository.findFirstByPhoneNumber(requestDto.getPhoneNumber());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User with this phone number already exists");
        }

        UserEntity user = new UserEntity();
        user.setUserCode(UUID.randomUUID().toString().toLowerCase());
        user.setUsername(requestDto.getUsername());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword())); // Encrypt password
        user.setFullName(requestDto.getFullName());
        user.setAddress(requestDto.getAddress());
        user.setPhoneNumber(requestDto.getPhoneNumber());
        user.setEmail(requestDto.getEmail());
        return userRepository.save(user);
    }

    public void changePassword(ChangePasswordRequestDto changePasswordRequest) {
        // Find user by phone number
        UserEntity user = userRepository.findFirstByPhoneNumber(changePasswordRequest.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate current password
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        // Validate new password confirmation
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            throw new RuntimeException("New password and confirm password do not match");
        }

        // Validate new password is different from current password
        if (passwordEncoder.matches(changePasswordRequest.getNewPassword(), user.getPassword())) {
            throw new RuntimeException("New password cannot be the same as current password");
        }

        // Update password with encryption
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
    }


    public LoginResponseDto login(LoginRequestDto loginRequest) {
        UserEntity user = userRepository.findFirstByPhoneNumber(loginRequest.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid phone number"));

//        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
//            throw new BadCredentialsException("Invalid password");
//        }

        String accessToken = jwtUtil.generateAccessToken(user.getPhoneNumber());
        String refreshToken = jwtUtil.generateRefreshToken(user.getPhoneNumber());

        // Save refresh token to database
        saveRefreshToken(refreshToken, user.getPhoneNumber());

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtUtil.getAccessTokenExpiration() / 1000)
                .build();
    }

    public LoginResponseDto refreshToken(String refreshToken) {
        if (!jwtUtil.isRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token type");
        }

        String phoneNumber = jwtUtil.extractPhoneNumber(refreshToken);

        RefreshTokenEntity storedToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Refresh token not found"));

        if (storedToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(storedToken);
            throw new IllegalArgumentException("Refresh token expired");
        }

        if (!jwtUtil.validateToken(refreshToken, phoneNumber)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        // Generate new tokens
        String newAccessToken = jwtUtil.generateAccessToken(phoneNumber);
        String newRefreshToken = jwtUtil.generateRefreshToken(phoneNumber);

        // Update refresh token in database
        refreshTokenRepository.delete(storedToken);
        saveRefreshToken(newRefreshToken, phoneNumber);

        return LoginResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtUtil.getAccessTokenExpiration() / 1000)
                .build();
    }

    public void logout(String refreshToken) {
        refreshTokenRepository.findByToken(refreshToken)
                .ifPresent(refreshTokenRepository::delete);
    }

    private void saveRefreshToken(String token, String userCode) {
        Optional<RefreshTokenEntity> existingToken = refreshTokenRepository.findFirstByUserCode(userCode);

        if (existingToken.isPresent()) {
            RefreshTokenEntity tokenEntity = existingToken.get();
            tokenEntity.setToken(token);
            tokenEntity.setExpiryDate(LocalDateTime.now().plusDays(1));
            refreshTokenRepository.save(tokenEntity);
        } else {
            RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
                    .token(token)
                    .userCode(userCode)
                    .expiryDate(LocalDateTime.now().plusDays(1))
                    .build();
            refreshTokenRepository.save(refreshTokenEntity);
        }
    }
}