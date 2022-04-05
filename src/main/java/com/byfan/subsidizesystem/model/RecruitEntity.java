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
 * @Description 招聘表实体类
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@ApiModel(value = "招聘表实体类", description = "招聘表Entity")
@EntityListeners(AuditingEntityListener.class)
@Entity
@Data
@Table(name = "recruit")
public class RecruitEntity implements Serializable {

	/**
	 * id
	 */
	@ApiModelProperty(value = "id")
	@Id
	@Column(name = "id")
	private Integer id;

	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	@Column(name = "user_id")
	private Integer userId;

	/**
	 * 招聘级别 1 系统 2 学校
	 */
	@ApiModelProperty(value = "招聘级别 1 系统 2 学校")
	@Column(name = "level")
	private Integer level;

	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题")
	@Column(name = "title")
	private String title;

	/**
	 * 岗位
	 */
	@ApiModelProperty(value = "岗位")
	@Column(name = "position")
	private String position;

	/**
	 * 薪资
	 */
	@ApiModelProperty(value = "薪资")
	@Column(name = "salary")
	private String salary;

	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容")
	@Column(name = "content")
	private String content;

	/**
	 * 联系人
	 */
	@ApiModelProperty(value = "联系人")
	@Column(name = "contacts")
	private String contacts;

	/**
	 * 联系电话
	 */
	@ApiModelProperty(value = "联系电话")
	@Column(name = "telephone")
	private String telephone;

	/**
	 * 邮箱
	 */
	@ApiModelProperty(value = "邮箱")
	@Column(name = "email")
	private String email;

	/**
	 * 招聘状态
	 */
	@ApiModelProperty(value = "招聘状态")
	@Column(name = "status")
	private Integer status;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@CreatedDate
	@Column(name = "create_time")
	private Date createTime;

}