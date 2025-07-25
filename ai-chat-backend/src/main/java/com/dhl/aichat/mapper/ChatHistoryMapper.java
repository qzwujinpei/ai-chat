package com.dhl.aichat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dhl.aichat.entity.ChatHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatHistoryMapper extends BaseMapper<ChatHistory> {
}
