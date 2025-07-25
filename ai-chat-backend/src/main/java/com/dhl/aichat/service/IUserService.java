package com.dhl.aichat.service;

import com.dhl.aichat.DTO.RegisterUserDTO;
import com.dhl.aichat.DTO.UserLoginDTO;

public interface IUserService {
    void register(RegisterUserDTO registerUserDTO);

    String login(UserLoginDTO userLoginDTO);
}
