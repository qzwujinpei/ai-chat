package com.dhl.aichat.service.Impl;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.dhl.aichat.DTO.RegisterUserDTO;
import com.dhl.aichat.DTO.UserLoginDTO;
import com.dhl.aichat.constant.CacheConstants;
import com.dhl.aichat.constant.JwtClaimsConstant;
import com.dhl.aichat.core.RedisCache;
import com.dhl.aichat.entity.User;
import com.dhl.aichat.exception.BizException;
import com.dhl.aichat.properties.JwtProperties;
import com.dhl.aichat.service.IUserService;
import com.dhl.aichat.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.dhl.aichat.exception.BizExceptionEnume.*;

@Slf4j
@Service
public class UserService implements IUserService {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private RedisCache redisCache;

    // 封装校验验证码的方法
    private void verifyCaptcha(String uuid, String my_captcha) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        log.info("验证码key：{}", verifyKey);
        String captcha = redisCache.getCacheObject(verifyKey);
        log.info("验证码：{}", captcha);
        if (Objects.isNull(captcha) || !captcha.equalsIgnoreCase(my_captcha)) {
            throw new BizException(CAPTCHA_ERROR);
        }
    }

    @Override
    public void register(RegisterUserDTO registerUserDTO) {
        String username = registerUserDTO.getUsername();
        String password = registerUserDTO.getPassword();
        String uuid = registerUserDTO.getUuid();
        String my_captcha = registerUserDTO.getCaptcha();
        // 调用封装的验证码校验方法
        verifyCaptcha(uuid, my_captcha);
        User one = Db.lambdaQuery(User.class).eq(User::getUsername, username).one();
        if (one != null) {
            throw new BizException(USER_EXIST);
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        User user = User.builder()
                .username(username)
                .password(password)
                .build();
        Db.save(user);
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {
        String password = userLoginDTO.getPassword();
        String my_captcha = userLoginDTO.getCaptcha();
        String uuid = userLoginDTO.getUuid();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        User user = Db.lambdaQuery(User.class)
                .eq(User::getUsername, userLoginDTO.getUsername())
                .eq(User::getPassword, password)
                .one();
        if (Objects.isNull(user)) {
            throw new BizException(USER_NOT_EXIST);
        }
        // 调用封装的验证码校验方法
        verifyCaptcha(uuid, my_captcha);
        return generateJWT(user);
    }


    private String generateJWT(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        return JwtUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtlMillis(), claims);
    }
}
