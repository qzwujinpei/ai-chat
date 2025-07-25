package com.dhl.aichat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    private Long id;
    private String conversationId;
    private String content;
    private String role;
    private Long userId;
    private LocalDateTime sentTime;
    private String model;
    private Integer tokens;
}
