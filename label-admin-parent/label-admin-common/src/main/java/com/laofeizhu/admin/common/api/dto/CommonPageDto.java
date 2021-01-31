package com.laofeizhu.admin.common.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;

/**
 * @author 老肥猪
 * @since 2019/9/19
 */
@Data
@ApiModel("公共的分页查询")
public class CommonPageDto {
    @ApiModelProperty(value = "当前页",notes = "默认第一页")
    private Integer pageNo=1;
    @ApiModelProperty(value = "当前页大小",notes = "默认一页十条，一页不能超过100条")
    @Max(value = 100,message = "每页的最大条数不能超过一百条")
    private Integer pageSize=10;
}
