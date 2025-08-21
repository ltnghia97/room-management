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
@RequestMapping("/v1/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("add-new")
    public ResponseData<UserEntity> addNewUser(@RequestBody UserDto userDto) {
        try {
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
