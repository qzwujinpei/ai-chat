package com.dhl.aichat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dhl.aichat.entity.ChatMessage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMessageMapper extends BaseMapper<ChatMessage> {
}
