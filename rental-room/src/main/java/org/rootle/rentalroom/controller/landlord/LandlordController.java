package org.rootle.rentalroom.controller.landlord;
import org.rootle.rentalroom.base.ResponseData;
import org.rootle.rentalroom.constant.Constant;
import org.rootle.rentalroom.dto.request.LandlordRegistrationDTO;
import org.rootle.rentalroom.dto.response.ResponseLandlordDto;
import org.rootle.rentalroom.enum_common.LandlordType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.rootle.rentalroom.entity.LandlordEntity;
import org.rootle.rentalroom.service.LandlordService;

@RestController
@RequestMapping("/v1/api/landlord")
public class LandlordController {

    @Autowired
    LandlordService landlordService;


    @PostMapping("/register-info")
    public ResponseData<ResponseLandlordDto> register(@RequestBody LandlordRegistrationDTO dto) {
        try {
            if (!LandlordType.isValid(dto.getLandlordType())) {
                return ResponseData.execute(null, "Invalid value for landlord type. Please choose from: INDIVIDUAL, BUSINESS.", Constant.RESULT_ERROR);
            }
            LandlordType type = LandlordType.valueOf(dto.getLandlordType().toUpperCase());
            if (type == LandlordType.BUSINESS && (dto.getBusinessName() == null || dto.getBusinessName().isBlank())) {
                return ResponseData.execute(null, "Business name is required for a BUSINESS landlord type.", Constant.RESULT_ERROR);
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
            return ResponseData.execute(responseLandlordDto, "Registered successfully", Constant.RESULT_OK);
        } catch (Exception e) {
            throw new RuntimeException("Simulating a server error.");
        }
    }
}