package com.sml.security.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sml.security.mapper.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}

