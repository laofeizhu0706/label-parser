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
 * @Description: 标签drl文件表
 * @Author: jeecg-boot
 * @Date:   2021-01-31
 * @Version: V1.0
 */
@Data
@TableName("label_drl")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="label_drl对象", description="标签drl文件表")
public class LabelDrl extends JeecgEntity {
    
	/**labelVersion*/
	@Excel(name = "labelVersion", width = 15)
    @ApiModelProperty(value = "labelVersion")
	private String labelVersion;
	/**content*/
	@Excel(name = "content", width = 15)
    @ApiModelProperty(value = "content")
	private String content;
	/**title*/
	@Excel(name = "title", width = 15)
    @ApiModelProperty(value = "title")
	private String title;
	/**enable*/
	@Excel(name = "enable", width = 15)
    @ApiModelProperty(value = "enable")
	private Boolean enable;
}
