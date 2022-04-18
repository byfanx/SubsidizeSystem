package com.byfan.subsidizesystem.model;

import com.byfan.subsidizesystem.bean.NoticeBean;
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
 * @Description 公告表实体类
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@ApiModel(value = "公告表实体类", description = "公告表Entity")
@EntityListeners(AuditingEntityListener.class)
@Entity
@Data
@Table(name = "notice")
public class NoticeEntity extends NoticeBean implements Serializable {

	/**
	 * id
	 */
	@ApiModelProperty(value = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 发布人
	 */
	@ApiModelProperty(value = "发布人id")
	@Column(name = "user_id")
	private Integer userId;

	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题")
	@Column(name = "title")
	private String title;

	/**
	 * 发布内容
	 */
	@ApiModelProperty(value = "发布内容")
	@Column(name = "content")
	private String content;

	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态 0 删除  1 正常")
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