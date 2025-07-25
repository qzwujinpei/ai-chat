package com.dhl.aichat.controller;

import com.dhl.aichat.VO.CodeVO;
import com.dhl.aichat.result.Result;
import com.dhl.aichat.service.ICaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/captcha")
@RequiredArgsConstructor
public class CaptchaController {
    private final ICaptchaService codeService;

    @GetMapping
    public Result<CodeVO> getCode() {
        CodeVO codeVO =  codeService.getCode();
        return Result.success(codeVO);
    }

}
