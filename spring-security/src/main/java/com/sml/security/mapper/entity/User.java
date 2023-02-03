package com.sml.security.mapper.entity;


import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sml.platform.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity implements UserDetails {

    private static final long serialVersionUID = 0L;

    /**
     * 账户
     */
    @NotBlank
    private String username;

    /**
     * 密码
     */

    private String password;

    /**
     * 姓名
     */

    @TableField(condition = SqlCondition.LIKE_RIGHT)
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */

    private String phone;

    /**
     * 是否为内置
     */
    private Boolean initial;

    /**
     * 是否启用 1 启用 0 禁用
     */
    private Boolean enable;


    @TableField(exist = false)
    private Set<String> permissions;

    //存储SpringSecurity所需要的权限信息的集合
    @TableField(exist = false)
    private transient List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Objects.isNull(authorities) ? permissions.stream().
                map(SimpleGrantedAuthority::new).collect(Collectors.toList()) : authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE;
    }
}

