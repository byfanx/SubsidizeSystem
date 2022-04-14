package com.byfan.subsidizesystem.model;

import com.byfan.subsidizesystem.bean.StudentBean;
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
 * @Description 学生表实体类
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@ApiModel(value = "学生表实体类", description = "学生表Entity")
@EntityListeners(AuditingEntityListener.class)
@Entity
@Data
@Table(name = "students")
public class StudentsEntity extends StudentBean implements Serializable {

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
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名")
	@Column(name = "name")
	private String name;

	/**
	 * 性别
	 */
	@ApiModelProperty(value = "性别  1 男  2 女")
	@Column(name = "gender")
	private Integer gender;

	/**
	 * 电话
	 */
	@ApiModelProperty(value = "电话")
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
	 * 认证说明
	 */
	@ApiModelProperty(value = "认证说明")
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
	@ApiModelProperty(value = "审核人id")
	@Column(name = "auditor_id")
	private Integer auditorId;

	/**
	 * 认证状态
	 */
	@ApiModelProperty(value = "认证状态 0 待审核  1 通过  2 拒绝")
	@Column(name = "authorize_status")
	private Integer authorizeStatus;

	/**
	 * 状态
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

//	private UserEntity userEntity;
//
//	@OneToOne
//	@JoinColumn(name = "user_id", referencedColumnName = "id",insertable = false,updatable = false)
//	public UserEntity getUserEntity() {
//		return userEntity;
//	}
//
//	public void setUserEntity(UserEntity userEntity){
//		this.userEntity = userEntity;
//	}
}