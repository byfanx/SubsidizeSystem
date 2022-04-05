package com.byfan.subsidizesystem.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description 用户表实体类
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@ApiModel(value = "用户表实体类", description = "用户表Entity")
@EntityListeners(AuditingEntityListener.class)
@Entity
@Data
@Table(name = "user")
public class UserEntity implements Serializable {

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 登录名
	 */
	@ApiModelProperty(value = "登录名")
	@Column(name = "user_name")
	private String userName;

	/**
	 * 展示名
	 */
	@ApiModelProperty(value = "展示名")
	@Column(name = "display_name")
	private String displayName;

	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	@Column(name = "password")
	private String password;

	/**
	 * 用户角色
	 */
	@ApiModelProperty(value = "用户角色")
	@Column(name = "role")
	private Integer role;

	/**
	 * 性别  1 男  2 女
	 */
	@ApiModelProperty(value = "性别  1 男  2 女")
	@Column(name = "gender")
	private Integer gender;

	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号")
	@Column(name = "telephone")
	private String telephone;

	/**
	 * 邮箱
	 */
	@ApiModelProperty(value = "邮箱")
	@Column(name = "email")
	private String email;

	/**
	 * 地址
	 */
	@ApiModelProperty(value = "地址")
	@Column(name = "address")
	private String address;

	/**
	 * 密保问题
	 */
	@ApiModelProperty(value = "密保问题")
	@Column(name = "security_question")
	private String securityQuestion;

	/**
	 * 密保问题答案
	 */
	@ApiModelProperty(value = "密保问题答案")
	@Column(name = "security_question_answer")
	private String securityQuestionAnswer;

	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态")
	@Column(name = "status")
	private Integer status;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@CreatedDate
	@Column(name = "create_time")
	private Date createTime;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	@LastModifiedDate
	@Column(name = "update_time")
	private Date updateTime;

}