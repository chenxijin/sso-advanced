package com.sml.platform.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.OrderBy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 0L;


	@OrderBy
	private Long id;

	/**
	 * 创建人
	 */
	@JsonInclude(Include.NON_NULL)
	@TableField(fill = FieldFill.INSERT)
	private Long createId;

	/**
	 * 创建时间
	 */
	@JsonInclude(Include.NON_NULL)
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 更新人
	 */
	@JsonIgnore
	@TableField(fill = FieldFill.INSERT_UPDATE, select = false)
	private Long updateId;

	/**
	 * 更新时间
	 */
	@JsonIgnore
	@TableField(fill = FieldFill.INSERT_UPDATE, select = false)
	private LocalDateTime updateTime;

	/**
	 * 逻辑删除
	 */
	@JsonIgnore
	private Boolean deleted;

}
