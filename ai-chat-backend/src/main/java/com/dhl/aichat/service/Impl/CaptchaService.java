package com.dhl.aichat.service.Impl;

import com.dhl.aichat.VO.CodeVO;
import com.dhl.aichat.constant.CacheConstants;
import com.dhl.aichat.core.RedisCache;
import com.dhl.aichat.service.ICaptchaService;
import com.google.code.kaptcha.Producer;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.dhl.aichat.constant.CacheConstants.CAPTCHA_EXPIRATION;

@Service
public class CaptchaService implements ICaptchaService {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Autowired
    private RedisCache redisCache;

    @SneakyThrows
    @Override
    public CodeVO getCode() {
        String uuid = UUID.randomUUID().toString();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        String code = captchaProducer.createText(); // 纯文本验证码

        BufferedImage image = captchaProducer.createImage(code);
        redisCache.setCacheObject(verifyKey, code, CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 5. 将图片转换为Base64返回前端
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        ImageIO.write(image, "jpg", os);
        CodeVO codeVO = new CodeVO();
        codeVO.setUuid(uuid);
        // 使用 Java 标准库的 Base64 编码
        codeVO.setImg("data:image/jpeg;base64," + Base64.getEncoder().encodeToString(os.toByteArray()));

        return codeVO;

    }
}
