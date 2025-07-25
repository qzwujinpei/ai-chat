package com.dhl.aichat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id; // 用户ID
    private String username; // 用户名
    private String password; // 密码哈希值
    private String email; // 邮箱
    private String phone; // 手机号
    private LocalDateTime createdTime; // 创建时间
    private LocalDateTime updatedTime; // 更新时间
    private Boolean isActive; // 是否激活
    private LocalDateTime lastLogin; // 最后登录时间
}
