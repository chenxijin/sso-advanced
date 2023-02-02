package com.sml.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultEnum {
	/**
	 * 请求成功
	 */
	SUCCESS(10000, "操作成功"),
	/**
	 * 请求错误
	 */
	ERROR(10001, "操作失败"),
	/**
	 * 参数错误
	 */
	PARAMETER_ERROR(10002, "参数错误"),
	/**
	 * 请先通过身份认证
	 */
	UNAUTHENTICATED(10003, "未登录"),
	/**
	 * 认证失败
	 */
	AUTH_FAIL(10004, "认证失败"),

	// token异常
	TOKEN_PAST(10005, "token异常！"),
	/**
	 * 令牌错误
	 */
	TOKEN_ERROR(10006, "令牌错误"),
	/**
	 * 请求头错误
	 */
	HEADER_ERROR(10007, "请求头错误"),
	/**
	 * 请求头错误
	 */
	NOT_ROLE(10008, "未拥有该角色"),
	/**
	 * 请求头错误
	 */
	REPEAT_SUBMIT(10009, "重复提交"),
	/**
	 * 无权限
	 */
	UNAUTHORIZED(10010, "没此权限，请联系管理员！"),

	/**
	 * Content-Type错误
	 */
	CONTENT_TYPE_ERROR(10011, "Content-Type错误"),
	;

	private Integer code;

	private String desc;
}
