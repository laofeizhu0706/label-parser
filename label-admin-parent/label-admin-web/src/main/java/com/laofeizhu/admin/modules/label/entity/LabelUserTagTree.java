package com.laofeizhu.admin.modules.label.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.laofeizhu.admin.modules.system.model.SysPermissionTree;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname LabelUserTagTree
 * @Description TODO
 * @Date 2021/2/1 19:54
 * @Created by laofeizhu
 */
@Data
public class LabelUserTagTree {
    /**id*/
    private java.lang.String id;
    /**标签名称*/
    private java.lang.String name;
    /**父id*/
    private java.lang.String parentId;
    /**
     * 是否叶子节点: 1:是  0:不是
     */
    private Boolean leaf;

    private List<LabelUserTagTree> children;

    public LabelUserTagTree(LabelUserTag labelUserTag) {
        this.id= labelUserTag.getId();
        this.name= labelUserTag.getName();
        this.parentId= labelUserTag.getParentId();
        this.leaf= labelUserTag.getLeaf();
        this.id= labelUserTag.getId();
        if (!labelUserTag.getLeaf()) {
            this.children = new ArrayList<LabelUserTagTree>();
        }
    }

}
