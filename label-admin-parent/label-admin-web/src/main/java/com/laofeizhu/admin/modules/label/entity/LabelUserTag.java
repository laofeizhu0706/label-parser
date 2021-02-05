package com.laofeizhu.admin.modules.label.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.laofeizhu.admin.common.system.base.entity.JeecgEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 用户标签
 * @Author: laofeizhu
 * @Date:   2021-02-01
 * @Version: V1.0
 */
@Data
@TableName("label_user_tag")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="label_user_tag对象", description="用户标签")
public class LabelUserTag extends JeecgEntity {
    
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private String id;
	/**标签名称*/
	@Excel(name = "标签名称", width = 15)
    @ApiModelProperty(value = "标签名称")
	private String name;
	/**父id*/
	@Excel(name = "父id", width = 15)
    @ApiModelProperty(value = "父id")
	private String parentId;
	/**
	 * 是否叶子节点: 1:是  0:不是
	 */
	@TableField(value="is_leaf")
	@Excel(name = "是否为叶子节点", width = 15)
	@ApiModelProperty(value = "是否为叶子节点")
	private Boolean leaf;
	/**createTime*/
	@Excel(name = "createTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createTime")
	private Date createTime;
	/**updateTime*/
	@Excel(name = "updateTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "updateTime")
	private Date updateTime;
	/**createBy*/
	@Excel(name = "createBy", width = 15)
    @ApiModelProperty(value = "createBy")
	private String createBy;
	/**updateBy*/
	@Excel(name = "updateBy", width = 15)
    @ApiModelProperty(value = "updateBy")
	private String updateBy;
}
