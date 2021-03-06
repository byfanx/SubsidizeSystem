package com.byfan.subsidizesystem.model;

import com.byfan.subsidizesystem.bean.AttentionBean;
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
public class AttentionEntity extends AttentionBean implements Serializable {

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
	 * 被关注对象的id
	 */
	@ApiModelProperty(value = "被关注对象的id", required = true)
	@Column(name = "followed_user_id")
	private Integer followedUserId;

	/**
	 * 认证的身份 4 贫困生  5 贫困学校
	 */
	@ApiModelProperty(value = "认证的身份 4 学生  5 学校", required = true)
	@Column(name = "approve_identity")
	private Integer approveIdentity;

	/**
	 * 状态  0 删除  1 正常
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