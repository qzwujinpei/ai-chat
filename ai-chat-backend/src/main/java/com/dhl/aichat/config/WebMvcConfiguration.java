package com.dhl.aichat.config;

import com.dhl.aichat.interceptor.MyHandlerIntercepter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private MyHandlerIntercepter myHandlerIntercepter;

    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(myHandlerIntercepter)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/api/*","/user/register","/ai/chat", "/captcha");
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // 允许的路径
//                .allowedOrigins("http://localhost:8080") // 允许的源
//                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的 HTTP 方法
//                .allowedHeaders("*"); // 允许的请求头
//    }
}
