package org.rootle.rentalroom.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDto {
    @NotBlank(message = "Phone number cannot be empty")
    private String phoneNumber;
    
    @NotBlank(message = "Current password cannot be empty")
    private String currentPassword;
    
    @NotBlank(message = "New password cannot be empty")
    private String newPassword;
    
    @NotBlank(message = "Confirm password cannot be empty")
    private String confirmPassword;
}