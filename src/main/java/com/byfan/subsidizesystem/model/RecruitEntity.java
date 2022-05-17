package com.byfan.subsidizesystem.model;

import com.byfan.subsidizesystem.bean.RecruitBean;
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
public class RecruitEntity extends RecruitBean implements Serializable {

	/**
	 * id
	 */
	@ApiModelProperty(value = "id（新增时不用传，编辑时需要传）")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "发布单位id", required = true)
	@Column(name = "user_id")
	private Integer userId;

	/**
	 * 招聘级别 3 系统 5 学校
	 */
	@ApiModelProperty(value = "招聘级别 3 系统 5 学校", required = true)
	@Column(name = "level")
	private Integer level;

	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题", required = true)
	@Column(name = "title")
	private String title;

	/**
	 * 岗位
	 */
	@ApiModelProperty(value = "岗位", required = true)
	@Column(name = "position")
	private String position;

	/**
	 * 薪资
	 */
	@ApiModelProperty(value = "薪资", required = true)
	@Column(name = "salary")
	private String salary;

	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容", required = true)
	@Column(name = "content")
	private String content;

	/**
	 * 联系人
	 */
	@ApiModelProperty(value = "联系人", required = true)
	@Column(name = "contacts")
	private String contacts;

	/**
	 * 联系电话
	 */
	@ApiModelProperty(value = "联系电话", required = true)
	@Column(name = "telephone")
	private String telephone;

	/**
	 * 邮箱
	 */
	@ApiModelProperty(value = "邮箱", required = true)
	@Column(name = "email")
	private String email;

	/**
	 * 审核人id
	 */
	@ApiModelProperty(value = "审核人id（新增时不用传）")
	@Column(name = "auditor_id")
	private Integer auditorId;

	/**
	 * 审核状态
	 */
	@ApiModelProperty(value = "审核状态 0 待审核  1 通过  2 拒绝（新增时不用传）")
	@Column(name = "authorize_status")
	private Integer authorizeStatus;

	/**
	 * 招聘状态
	 */
	@ApiModelProperty(value = "状态  0 删除  1 正常（新增时不用传）")
	@Column(name = "status")
	private Integer status;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间（新增时不用传）")
	@CreatedDate
	@Column(name = "create_time")
	private Date createTime;

}