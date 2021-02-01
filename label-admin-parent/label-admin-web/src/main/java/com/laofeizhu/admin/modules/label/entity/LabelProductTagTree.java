package com.laofeizhu.admin.modules.label.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Classname LabelUserTagTree
 * @Description TODO
 * @Date 2021/2/1 19:54
 * @Created by laofeizhu
 */
@Data
public class LabelProductTagTree {
    /**
     * id
     */
    private String id;
    /**
     * 标签名称
     */
    private String name;
    /**
     * 父id
     */
    private String parentId;
    /**
     * 是否叶子节点: 1:是  0:不是
     */
    private Boolean leaf;

    private Date createTime;

    private List<LabelProductTagTree> children;

    public LabelProductTagTree(LabelProductTag labelProductTag) {
        this.id = labelProductTag.getId();
        this.name = labelProductTag.getName();
        this.parentId = labelProductTag.getParentId();
        this.leaf = labelProductTag.getLeaf();
        this.id = labelProductTag.getId();
        this.createTime = labelProductTag.getCreateTime();
        if (!labelProductTag.getLeaf()) {
            this.children = new ArrayList<>();
        }
    }

}
