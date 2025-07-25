package com.dhl.aichat.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatHistory {
    private Long id;
    @NotBlank(message = "会话ID不能为空")
    private String conversationId;      // 会话ID
    private Long userId;               // 用户ID
    private String messageTitle;       // 消息内容
    private LocalDateTime lastActiveTime;             // 创建时间
}
