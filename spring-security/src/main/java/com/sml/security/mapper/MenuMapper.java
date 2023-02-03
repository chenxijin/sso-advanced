package com.sml.security.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sml.security.mapper.entity.Menu;
import com.sml.security.mapper.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    @Select({
            "SELECT m.perms FROM sys_user_role ur " +
                    "LEFT JOIN sys_role r ON ur.role_id = r.id " +
                    "LEFT JOIN sys_role_menu rm ON ur.role_id = rm.role_id " +
                    "LEFT JOIN sys_menu m ON m.id = rm.menu_id " +
                    "WHERE user_id = #{userid} AND r.status = 0 AND m.status = 0 "
    })
    Set<String> selectPerms(Long userid);
}

