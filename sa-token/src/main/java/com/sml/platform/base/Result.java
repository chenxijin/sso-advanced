package com.sml.platform.base;

import com.sml.platform.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * 用于封装接口统一响应结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public final class Result<T> implements Serializable {


	private static final long serialVersionUID = 1L;

	/**
	 * 响应业务状态码
	 */
	private Integer code;

	/**
	 * 响应信息
	 */
	private String msg;

	/**
	 * 响应中的数据
	 */
	private T data;

	public static <T> Result<T> res(ResultEnum resultEnum) {
		return res(resultEnum, resultEnum.getDesc());
	}

	public static <T> Result<T> res(ResultEnum resultEnum, String message) {
		return new Result<>(resultEnum.getCode(), message, null);
	}

	public static <T> Result<T> res(ResultEnum resultEnum, T t) {
		return new Result<>(resultEnum.getCode(), resultEnum.getDesc(), t);
	}


	public static <T> Result<T> res(ResultEnum resultEnum, T t, String message) {
		return new Result<>(resultEnum.getCode(), message, t);
	}

	public static <T> Result<T> isSuccess(boolean success) {
		return success ? success() : error();
	}

	public static <T> Result<T> isSuccess(boolean success, String msg) {
		return success ? success() : error(msg);
	}

	public static <T> Result<T> success() {
		return res(ResultEnum.SUCCESS);
	}


	public static <T> Result<T> success(T t) {
		return res(ResultEnum.SUCCESS, t);
	}

	public static <T> Result<T> success(String message, T data) {
		return res(ResultEnum.SUCCESS, data, message);
	}

	public static <T> Result<T> error(String message) {
		log.debug("返回错误：code={}, message={}", ResultEnum.ERROR.getCode(), message);
		return res(ResultEnum.ERROR, null, message);
	}

	public static <T> Result<T> error() {
		log.debug("返回错误：code={}", ResultEnum.ERROR.getCode());
		return res(ResultEnum.ERROR);
	}
}