package org.rootle.rentalroom.controller.landlord;
import org.rootle.rentalroom.dto.request.LandlordRegistrationDTO;
import org.rootle.rentalroom.dto.response.ResponseLandlordDto;
import org.rootle.rentalroom.enum_common.LandlordType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.rootle.rentalroom.entity.LandlordEntity;
import org.rootle.rentalroom.service.LandlordService;

@RestController
@RequestMapping("/v1/api/landlord")
public class LandlordController {

    @Autowired
    LandlordService landlordService;


    @PostMapping("/register-info")
    public ResponseEntity<Object> register(@RequestBody LandlordRegistrationDTO dto) {
        try {

            if (!LandlordType.isValid(dto.getLandlordType())) {
                return ResponseEntity.badRequest().body("Invalid value for land lord type. Please choose from: INDIVIDUAL, BUSINESS.");
            }
            LandlordType type = LandlordType.valueOf(dto.getLandlordType().toUpperCase());
            if (type == LandlordType.BUSINESS && (dto.getBusinessName() == null || dto.getBusinessName().isBlank())) {
                return ResponseEntity.badRequest().body("Business name is required for a BUSINESS landlord type.");
            }
            LandlordEntity landlordEntity = landlordService.register(dto, type);
            ResponseLandlordDto responseLandlordDto = new ResponseLandlordDto();
            responseLandlordDto.setFullName(landlordEntity.getUser().getFullName());
            responseLandlordDto.setAddress(landlordEntity.getUser().getAddress());
            responseLandlordDto.setPhoneNumber(landlordEntity.getUser().getPhoneNumber());
            responseLandlordDto.setEmail(landlordEntity.getUser().getEmail());
            responseLandlordDto.setCreatedAt(landlordEntity.getUser().getCreatedAt().toString());
            responseLandlordDto.setBusinessName(landlordEntity.getBusinessName());
            responseLandlordDto.setLandlordType(LandlordEntity.getTypeName(landlordEntity.getLandlordType()));
            return ResponseEntity.ok(responseLandlordDto);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}