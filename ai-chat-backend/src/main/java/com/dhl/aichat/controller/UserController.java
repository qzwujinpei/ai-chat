package com.dhl.aichat.controller;

import com.dhl.aichat.DTO.RegisterUserDTO;
import com.dhl.aichat.DTO.UserLoginDTO;
import com.dhl.aichat.result.Result;
import com.dhl.aichat.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    @PostMapping("/register")
    public Result register(@RequestBody @Valid RegisterUserDTO registerUserDTO) {
        userService.register(registerUserDTO);
        return Result.success();
    }
    @PostMapping("/login")
    public Result<String> login(@RequestBody @Valid UserLoginDTO UserLoginDTO) {
        String token =  userService.login(UserLoginDTO);
        return Result.success(token);
    }
}
