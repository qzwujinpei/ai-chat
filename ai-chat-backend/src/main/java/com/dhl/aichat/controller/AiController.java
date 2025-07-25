package com.dhl.aichat.controller;

import com.dhl.aichat.entity.ChatMessage;
import com.dhl.aichat.result.PageResult;
import com.dhl.aichat.result.Result;
import com.dhl.aichat.service.IAiService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
@Slf4j
public class AiController {
    private final IAiService iAiService;

    @PostMapping(value = "/chat", produces = "text/html;charset=utf-8")
    public Flux<String> chat(@RequestParam String prompt,
                             @RequestParam String chatId,
                             @RequestParam(value = "files", required = false) List<MultipartFile> files,
                             @RequestParam(required = false) boolean deep)
    {
        log.info("用户输入的prompt为: {}", prompt);
        log.info("用户输入的chatId为: {}", chatId);
        log.info("用户输入的files为: {}", files);
        log.info("是否开启深度思考: {}", deep);
        return iAiService.chat(prompt, chatId, files, deep);
    }
    @GetMapping("/create/chat")
    public Result createChat(@RequestParam @NotBlank String conversationId) {
        iAiService.createChat(conversationId);
        return Result.success();
    }

    // 获取ai历史对话
    @GetMapping("/chat/history")
    public Result<PageResult> getChatHistory() {
        PageResult pageResult = iAiService.getChatHistory();
        return Result.success(pageResult);
    }

    // 获取ai对话记忆
    @GetMapping("/chat/memory")
    public Result<List<ChatMessage>> getChatMemory(@RequestParam @NotBlank String conversationId) {
        List<ChatMessage> list = iAiService.getChatMemory(conversationId);
        return Result.success(list);
    }
    // 新增对话
    @PostMapping("/chat/add")
    @Transactional
    public Result addChat(@RequestBody ChatMessage chatMessage) {
        iAiService.addChat(chatMessage);
        return Result.success();
    }
    @PutMapping("/chat/{conversationId}/title")
    public Result updateChatTitle(@PathVariable String conversationId, @RequestParam String newTitle) {
        iAiService.updateChatTitle(conversationId, newTitle);
        return Result.success();
    }
    // 删除会话记录
    @DeleteMapping("/chat/{conversationId}")
    @Transactional
    public Result deleteChatHistory(@PathVariable String conversationId) {
        iAiService.deleteChatHistory(conversationId);
        return Result.success();
    }
}
