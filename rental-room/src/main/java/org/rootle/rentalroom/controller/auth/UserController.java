package org.rootle.rentalroom.controller.auth;

import org.rootle.rentalroom.base.ResponseData;
import org.rootle.rentalroom.constant.Constant;
import org.rootle.rentalroom.dto.UserDto;
import org.rootle.rentalroom.entity.UserEntity;
import org.rootle.rentalroom.service.UserService;
import org.rootle.rentalroom.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("add-new-user")
    public ResponseData<UserEntity> createUser(@RequestBody UserDto userDto) {
        try {

            if (ValidUtil.isNullOrBlank(userDto.getUsername())) {
                return ResponseData.execute(null, "Username cannot be empty", Constant.RESULT_ERROR);
            }

            if (ValidUtil.isNullOrBlank(userDto.getPassword())) {
                return ResponseData.execute(null, "Password cannot be empty", Constant.RESULT_ERROR);
            }

            if (ValidUtil.isNullOrBlank(userDto.getFullName())) {
                return ResponseData.execute(null, "Full name cannot be empty", Constant.RESULT_ERROR);
            }

            if (ValidUtil.isNullOrBlank(userDto.getAddress())) {
                return ResponseData.execute(null, "Address cannot be empty", Constant.RESULT_ERROR);
            }

            if (ValidUtil.isValidPhoneNumber(userDto.getPhoneNumber())) {
                return ResponseData.execute(null, "Phone cannot be empty or wrong format", Constant.RESULT_ERROR);
            }

            if (ValidUtil.isValidEmail(userDto.getEmail())) {
                return ResponseData.execute(null, "Email cannot be empty or wrong format", Constant.RESULT_ERROR);
            }

            UserEntity result = userService.save(userDto);
            if (result == null) {
                return ResponseData.execute(null, Constant.MESSAGE_OK, Constant.RESULT_ERROR);
            }

            return ResponseData.execute(result, Constant.MESSAGE_OK, Constant.RESULT_OK);
        } catch (Exception e) {
            return ResponseData.execute(null, e.getMessage(), Constant.RESULT_ERROR);
        }
    }
}
