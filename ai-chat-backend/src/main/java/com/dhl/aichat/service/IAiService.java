package com.dhl.aichat.service;


import com.dhl.aichat.entity.ChatMessage;
import com.dhl.aichat.result.PageResult;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.List;

public interface IAiService {
    void createChat(String conversationId);

    PageResult getChatHistory();

    List<ChatMessage> getChatMemory(String conversationId);

    void addChat(ChatMessage chatMessage);

    void updateChatTitle(String conversationId, String newTitle);

    Flux<String> chat(String prompt, String chatId, List<MultipartFile> files, Boolean deep);

    void deleteChatHistory(String conversationId);
}
