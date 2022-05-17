package com.byfan.subsidizesystem.model;

import com.byfan.subsidizesystem.bean.SchoolBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description 学校表实体类
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@ApiModel(value = "学校表实体类", description = "学校表Entity")
@EntityListeners(AuditingEntityListener.class)
@Entity
@Data
@Table(name = "school")
public class SchoolEntity extends SchoolBean implements Serializable {

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
	@ApiModelProperty(value = "用户id", required = true)
	@Column(name = "user_id")
	private Integer userId;

	/**
	 * 学校名称
	 */
	@ApiModelProperty(value = "学校名称", required = true)
	@Column(name = "name")
	private String name;

	/**
	 * 学校简介
	 */
	@ApiModelProperty(value = "学校简介", required = true)
	@Column(name = "description")
	private String description;

	/**
	 * 学校地址
	 */
	@ApiModelProperty(value = "学校地址", required = true)
	@Column(name = "address")
	private String address;

	/**
	 * 学校联系人
	 */
	@ApiModelProperty(value = "学校联系人", required = true)
	@Column(name = "contacts")
	private String contacts;

	/**
	 * 学校邮箱
	 */
	@ApiModelProperty(value = "学校邮箱", required = true)
	@Column(name = "email")
	private String email;

	/**
	 * 学校联系电话
	 */
	@ApiModelProperty(value = "学校联系电话", required = true)
	@Column(name = "telephone")
	private String telephone;

	/**
	 * 认证说明
	 */
	@ApiModelProperty(value = "认证说明", required = true)
	@Column(name = "authentication")
	private String authentication;

	/**
	 * 图片列表
	 */
	@ApiModelProperty(value = "图片集")
	@Column(name = "images")
	private String images;

	/**
	 * 审核人id
	 */
	@ApiModelProperty(value = "审核人id（新增时不用传）")
	@Column(name = "auditor_id")
	private Integer auditorId;

	/**
	 * 认证状态
	 */
	@ApiModelProperty(value = "认证状态 0 待审核  1 通过  2 拒绝（新增时不用传）")
	@Column(name = "authorize_status")
	private Integer authorizeStatus;

	/**
	 * 状态
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