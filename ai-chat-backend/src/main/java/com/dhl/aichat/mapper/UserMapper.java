package com.dhl.aichat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dhl.aichat.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
