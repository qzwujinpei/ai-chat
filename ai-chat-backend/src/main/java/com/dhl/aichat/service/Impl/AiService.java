package com.dhl.aichat.service.Impl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.dhl.aichat.context.BaseContext;
import com.dhl.aichat.entity.ChatHistory;
import com.dhl.aichat.entity.ChatMessage;
import com.dhl.aichat.exception.BizException;
import com.dhl.aichat.result.PageResult;
import com.dhl.aichat.service.IAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.model.Media;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.dhl.aichat.constant.PageConstant.DEFAULT_PAGE_CURRENT;
import static com.dhl.aichat.constant.PageConstant.DEFAULT_PAGE_SIZE;
import static com.dhl.aichat.exception.BizExceptionEnume.FILE_UPLOAD_AND_DEEP_THINKING_CANNOT_BE_OPENED_AT_THE_SAME_TIME;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

/**
 * AiService 类实现了 IAiService 接口，提供了与 AI 聊天相关的服务方法。
 * 该类使用 Spring 的 @Service 注解标记为服务层组件，并通过 Lombok 的 @RequiredArgsConstructor 注解自动生成构造函数。
 */
@Service
@RequiredArgsConstructor
public class AiService implements IAiService {
    /**
     * 注入的聊天客户端，用于与 AI 模型进行交互。
     */
    private final ChatClient deepseekChatClient;
    private final ChatClient qwenChatClient;
    private final ChatClient imgChatClient ;

    /**
     * 创建一个新的聊天记录。
     *
     * @param conversationId 聊天会话的 ID
     */
    @Override
    public void createChat(String conversationId) {
        // 获取当前用户的 ID
        Long userId = BaseContext.getCurrentId();
        // 构建聊天历史对象
        ChatHistory chatHistory = ChatHistory.builder()
                .userId(userId)
                .conversationId(conversationId)
                .lastActiveTime(LocalDateTime.now())
                .build();
        // 保存聊天历史到数据库
        Db.save(chatHistory);
    }

    /**
     * 获取用户的聊天历史记录。
     *
     * @return 包含聊天历史记录的分页结果对象
     */
    @Override
    public PageResult getChatHistory() {
        // 获取当前用户的 ID
        Long userId = BaseContext.getCurrentId();
        // 创建分页对象，使用默认的当前页和每页大小
        IPage<ChatHistory> page = new Page<>(DEFAULT_PAGE_CURRENT, DEFAULT_PAGE_SIZE);
        // 查询用户的聊天历史记录，并按创建时间降序排序
        Db.lambdaQuery(ChatHistory.class)
                .eq(ChatHistory::getUserId, userId)
                .orderByDesc(ChatHistory::getLastActiveTime) // 按照 lastActiveTime 字段降序排序
                .page(page);
        // 返回分页结果对象
        return new PageResult(page.getTotal(), page.getRecords());
    }

    /**
     * 获取指定聊天会话的聊天记忆（消息记录）。
     *
     * @param conversationId 聊天会话的 ID
     * @return 聊天消息记录列表
     */
    @Override
    public List<ChatMessage> getChatMemory(String conversationId) {
        // 获取当前用户的 ID
        Long userId = BaseContext.getCurrentId();
        // 查询用户在指定会话中的聊天消息记录，并按发送时间升序排序
        return Db.lambdaQuery(ChatMessage.class)
                .eq(ChatMessage::getUserId, userId)
                .eq(ChatMessage::getConversationId, conversationId)
                .orderByAsc(ChatMessage::getSentTime)
                .list();
    }

    /**
     * 添加一条新的聊天消息。
     *
     * @param chatMessage 要添加的聊天消息对象
     */
    @Override
    public void addChat(ChatMessage chatMessage) {
        // 获取当前用户的 ID
        Long userId = BaseContext.getCurrentId();
        // 设置聊天消息的用户 ID
        chatMessage.setUserId(userId);
        // 设置聊天消息的发送时间为当前时间
        chatMessage.setSentTime(LocalDateTime.now());
        // 设置聊天消息使用的模型
        chatMessage.setModel("deepseek:R1-7b");
        // 保存聊天消息到数据库
        Db.save(chatMessage);
        String conversationId = chatMessage.getConversationId();
        // 查询用户在指定会话中的聊天消息记录，并按发送时间升序排序
        Db.lambdaUpdate(ChatHistory.class).eq(ChatHistory::getUserId, userId)
                .eq(ChatHistory::getConversationId, conversationId)
                .set(ChatHistory::getLastActiveTime, LocalDateTime.now())
                .update();
    }

    /**
     * 更新指定聊天会话的标题。
     *
     * @param conversationId 聊天会话的 ID
     * @param newTitle       新的聊天标题
     */
    @Override
    public void updateChatTitle(String conversationId, String newTitle) {
        // 获取当前用户的 ID
        Long userId = BaseContext.getCurrentId();
        // 更新用户指定会话的聊天标题
        Db.lambdaUpdate(ChatHistory.class)
                .eq(ChatHistory::getUserId, userId)
                .eq(ChatHistory::getConversationId, conversationId)
                .set(ChatHistory::getMessageTitle, newTitle)
                .update();
    }

    /**
     * 处理聊天请求，根据是否有文件进行不同类型的聊天。
     *
     * @param prompt     用户输入的聊天提示
     * @param chatId     聊天会话的 ID
     * @param files      用户上传的文件列表
     * @param deep       是否使用深度模式
     * @return 包含聊天回复的响应式流
     */
    @Override
    public Flux<String> chat(String prompt, String chatId, List<MultipartFile> files, Boolean deep) {
        if(files!=null && deep){
            throw new BizException(FILE_UPLOAD_AND_DEEP_THINKING_CANNOT_BE_OPENED_AT_THE_SAME_TIME);
        }
        if (files != null && !files.isEmpty()) {
            // 如果有文件，则使用多模态聊天
            return multiModalChat(prompt, chatId, files);
        } else {
            // 如果没有文件，则使用文本聊天
            return textChat(prompt, chatId,deep);
        }
    }

    @Override
    public void deleteChatHistory(String conversationId) {
        Long userId = BaseContext.getCurrentId();
        Db.lambdaUpdate(ChatHistory.class)
               .eq(ChatHistory::getUserId, userId)
               .eq(ChatHistory::getConversationId, conversationId)
               .remove();

        Db.lambdaUpdate(ChatMessage.class)
              .eq(ChatMessage::getUserId, userId)
              .eq(ChatMessage::getConversationId, conversationId)
              .remove();
    }

    /**
     * 处理多媒体聊天请求，支持用户上传文件进行聊天。
     *
     * @param prompt  用户输入的聊天提示
     * @param chatId  聊天会话的 ID
     * @param files   用户上传的文件列表
     * @return 包含聊天回复的响应式流
     */
    private Flux<String> multiModalChat(String prompt, String chatId, List<MultipartFile> files) {
        // 1.解析多媒体文件，将 MultipartFile 转换为 Media 对象
        List<Media> medias = files.stream()
                .map(file -> new Media(
                                // 获取文件的 MIME 类型
                                MimeType.valueOf(Objects.requireNonNull(file.getContentType())),
                                // 获取文件资源
                                file.getResource()
                        )
                )
                .toList();
        // 2.向 AI 模型发送请求，并获取响应流
        return imgChatClient.prompt()
                .user(p -> p.text(prompt).media(medias.toArray(Media[]::new)))
                .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();
    }

    /**
     * 处理纯文本聊天请求。
     *
     * @param prompt 用户输入的聊天提示
     * @param chatId 聊天会话的 ID
     * @param deep 是否使用深度模式
     * @return 包含聊天回复的响应式流
     */
    private Flux<String> textChat(String prompt, String chatId, Boolean deep) {
        // 如果使用深度模式，则向深度模型发送请求
        if(deep){
            return deepseekChatClient.prompt()
                   .user(prompt)
                   .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                   .stream()
                   .content();
        }
        // 否则，向普通模型发送请求
        return qwenChatClient.prompt()
                .user(prompt)
                .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();
    }
}
