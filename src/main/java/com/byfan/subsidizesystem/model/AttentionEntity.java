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
 * @Description 关注信息表实体类
 * @Author: byfan
 * @Date: 2022/04/06 23:30
 */
@ApiModel(value = "关注信息表实体类", description = "关注信息表Entity")
@EntityListeners(AuditingEntityListener.class)
@Entity
@Data
@Table(name = "attention")
public class AttentionEntity implements Serializable {

	/**
	 * id
	 */
	@ApiModelProperty(value = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	@Column(name = "user_id")
	private Integer userId;

	/**
	 * 被关注对象的id
	 */
	@ApiModelProperty(value = "被关注对象的id")
	@Column(name = "followed_user_id")
	private Integer followedUserId;

	/**
	 * 认证的身份 1 贫困生  2 贫困学校
	 */
	@ApiModelProperty(value = "认证的身份 1 贫困生  2 贫困学校")
	@Column(name = "approve_identity")
	private Integer approveIdentity;

	/**
	 * 状态  0 删除  1 正常
	 */
	@ApiModelProperty(value = "状态  0 删除  1 正常")
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