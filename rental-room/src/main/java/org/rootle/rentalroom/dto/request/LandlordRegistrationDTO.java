package org.rootle.rentalroom.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rootle.rentalroom.dto.request.auth.RegisterUserDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LandlordRegistrationDTO {
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    @NotBlank(message = "Full name cannot be empty")
    private String fullName;

    @NotBlank(message = "Address cannot be empty")
    private String address;

    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "^\\+?[0-9\\s-]{10,15}$", message = "Wrong phone number format")
    private String phoneNumber;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Wrong email format")
    private String email;

    @NotNull(message = "Landlord type cannot be null")
    private String landlordType;

    private String businessName;
}