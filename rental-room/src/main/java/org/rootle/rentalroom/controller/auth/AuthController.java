package org.rootle.rentalroom.controller.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.sql.exec.ExecutionException;
import org.rootle.rentalroom.base.ResponseData;
import org.rootle.rentalroom.constant.Constant;
import org.rootle.rentalroom.dto.request.auth.ChangePasswordRequestDto;
import org.rootle.rentalroom.dto.request.auth.LoginRequestDto;
import org.rootle.rentalroom.dto.request.auth.RefreshTokenRequestDto;
import org.rootle.rentalroom.dto.request.auth.RegisterRequestDto;
import org.rootle.rentalroom.dto.response.auth.LoginResponseDto;
import org.rootle.rentalroom.entity.UserEntity;
import org.rootle.rentalroom.service.AuthService;
import org.rootle.rentalroom.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseData<UserEntity> register(@RequestBody RegisterRequestDto registerRequest) {
        try {
            UserEntity user = authService.register(registerRequest);
            return ResponseData.execute(user, "User registered successfully", Constant.RESULT_OK);
        } catch (Exception e) {
            return ResponseData.execute(null, Constant.MESSAGE_ERROR, Constant.RESULT_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseData<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        try {
            LoginResponseDto response = authService.login(loginRequest);
            return ResponseData.execute(response, "Login successful", Constant.RESULT_OK);
        } catch (Exception e) {
            return ResponseData.execute(null, e.getMessage(), Constant.RESULT_ERROR);
        }
    }

    @PostMapping("/change-password")
    public ResponseData<String> changePassword(@RequestBody ChangePasswordRequestDto changePasswordRequest,
                                               HttpServletRequest request) {
        try {
            // Extract JWT token from Authorization header
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseData.execute(null, "Authorization token is required", Constant.RESULT_ERROR);
            }

            String token = authHeader.substring(7); // Remove "Bearer " prefix

            // Validate token
            if (!jwtUtil.isAccessToken(token)) {
                return ResponseData.execute(null, "Invalid token type", Constant.RESULT_ERROR);
            }

            // Extract username (phone number) from token
            String phoneNumber = jwtUtil.extractPhoneNumber(token);

            // Validate token
            if (!jwtUtil.validateToken(token, phoneNumber)) {
                return ResponseData.execute(null, "Invalid or expired token", Constant.RESULT_ERROR);
            }

            // Change password for the authenticated user
            authService.changePassword(changePasswordRequest);
            return ResponseData.execute("Password changed successfully", "Password changed successfully", Constant.RESULT_OK);
        } catch (Exception e) {
            return ResponseData.execute(null, e.getMessage(), Constant.RESULT_ERROR);
        }
    }


    @PostMapping("/refresh")
    public ResponseData<LoginResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto refreshRequest) {
        try {
            LoginResponseDto response = authService.refreshToken(refreshRequest.getRefreshToken());
            return ResponseData.execute(response, "Token refreshed successfully", Constant.RESULT_OK);
        } catch (Exception e) {
            return ResponseData.execute(null, e.getMessage(), Constant.RESULT_ERROR);
        }
    }

    @PostMapping("/logout")
    public ResponseData<String> logout(@RequestBody RefreshTokenRequestDto refreshRequest) {
        try {
            authService.logout(refreshRequest.getRefreshToken());
            return ResponseData.execute("Logged out successfully", "Logout successful", Constant.RESULT_OK);
        } catch (Exception e) {
            return ResponseData.execute(null, e.getMessage(), Constant.RESULT_ERROR);
        }
    }
}