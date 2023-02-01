package com.sml.satoken.mapper.entity;


import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sml.platform.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

	private static final long serialVersionUID = -991183859355000223L;

	/**
	 * 账户
	 */
	@JsonProperty("acc")
	private String account;

	/**
	 * 密码
	 */

	@JsonProperty(value = "pwd", access = JsonProperty.Access.WRITE_ONLY)
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

}

