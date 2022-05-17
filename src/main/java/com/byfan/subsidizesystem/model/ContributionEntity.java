package com.byfan.subsidizesystem.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description 捐款表实体类
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@ApiModel(value = "捐款表实体类", description = "捐款表Entity")
@EntityListeners(AuditingEntityListener.class)
@Entity
@Data
@Table(name = "contribution")
public class ContributionEntity implements Serializable {

	/**
	 * id
	 */
	@ApiModelProperty(value = "id（新增时不用传，编辑时需要传）")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 捐款人id
	 */
	@ApiModelProperty(value = "捐款人id", required = true)
	@Column(name = "user_id")
	private Integer userId;

	/**
	 * 捐款对象id
	 */
	@ApiModelProperty(value = "被捐款对象id", required = true)
	@Column(name = "payee")
	private Integer payee;

	/**
	 * 捐款对象身份
	 */
	@ApiModelProperty(value = "被捐款对象身份  3 平台 4 贫困生 5 贫困学校", required = true)
	@Column(name = "approve_identity")
	private Integer approveIdentity;

	/**
	 * 捐款金额
	 */
	@ApiModelProperty(value = "捐款金额", required = true)
	@Column(name = "payment_amount")
	private Double paymentAmount;

	/**
	 * 捐款备注
	 */
	@ApiModelProperty(value = "捐款备注")
	@Column(name = "remarks")
	private String remarks;

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
	 * 状态
	 */
	@ApiModelProperty(value = "状态 0 删除  1 正常（新增时不用传）")
	@Column(name = "status")
	private Integer status;

	/**
	 * 捐款时间
	 */
	@ApiModelProperty(value = "捐款时间（新增时不用传）")
	@CreatedDate
	@Column(name = "create_time")
	private Date createTime;

}