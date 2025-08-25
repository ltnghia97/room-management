package org.rootle.rentalroom.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLandlordDto {
    String fullName;
    String address;
    String phoneNumber;
    String email;
    String createdAt;
    String businessName;
    String landlordType;
}
