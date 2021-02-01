package com.laofeizhu.admin.modules.label.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.laofeizhu.admin.common.exception.JeecgBootException;
import com.laofeizhu.admin.common.util.oConvertUtils;
import com.laofeizhu.admin.modules.label.entity.LabelProductTag;
import com.laofeizhu.admin.modules.label.mapper.LabelProductTagMapper;
import com.laofeizhu.admin.modules.label.service.ILabelProductTagService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description: 用户标签
 * @Author: laofeizhu
 * @Date:   2021-02-01
 * @Version: V1.0
 */
@Service
public class LabelProductTagServiceImpl extends ServiceImpl<LabelProductTagMapper, LabelProductTag> implements ILabelProductTagService {
    @Override
    public void addTag(LabelProductTag tag) {
        //----------------------------------------------------------------------
        String pid = tag.getParentId();
        if(oConvertUtils.isNotEmpty(pid)) {
            //设置父节点不为叶子节点
            LabelProductTag parentTag = this.getById(pid);
            parentTag.setLeaf(false);
            this.updateById(parentTag);
        }
        tag.setCreateTime(new Date());
        tag.setLeaf(true);
        this.save(tag);
    }

    @Override
    public void editTag(LabelProductTag tag) {
        LabelProductTag p = this.getById(tag.getId());
        if(p==null) {
            throw new JeecgBootException("未找到标签信息");
        }else {
            tag.setUpdateTime(new Date());
            //----------------------------------------------------------------------
            //Step2.判断标签下级是否有标签，无则设置为叶子节点
            int count = this.count(new QueryWrapper<LabelProductTag>().lambda().eq(LabelProductTag::getParentId, tag.getId()));
            if(count==0) {
                tag.setLeaf(true);
            }
            //----------------------------------------------------------------------
            this.updateById(tag);

            //如果当前标签的父标签变了，则需要修改新父标签和老父标签的，叶子节点状态
            String pid = tag.getParentId();
            if((oConvertUtils.isNotEmpty(pid) && !pid.equals(p.getParentId())) || oConvertUtils.isEmpty(pid)&&oConvertUtils.isNotEmpty(p.getParentId())) {
                //a.设置父节点不为叶子节点
                LabelProductTag parentTag = this.getById(pid);
                parentTag.setLeaf(false);
                this.updateById(parentTag);
                //b.判断老的标签下是否还有其他子标签，没有的话则设置为叶子节点
                int cc = this.count(new QueryWrapper<LabelProductTag>().lambda().eq(LabelProductTag::getParentId, p.getParentId()));
                if(cc==0) {
                    if(oConvertUtils.isNotEmpty(p.getParentId())) {
                        parentTag = this.getById(p.getParentId());
                        parentTag.setLeaf(true);
                        this.updateById(parentTag);
                    }
                }

            }
        }
    }

    @Override
    public void deleteTag(String id) {
        LabelProductTag sysPermission = this.getById(id);
        if(sysPermission==null) {
            throw new JeecgBootException("未找到标签信息");
        }
        String pid = sysPermission.getParentId();
        int count = this.count(new QueryWrapper<LabelProductTag>().lambda().eq(LabelProductTag::getParentId, pid));
        if(count==1) {
            //若父节点无其他子节点，则该父节点是叶子节点
            LabelProductTag parentTag = this.getById(pid);
            parentTag.setLeaf(true);
            this.updateById(parentTag);
        }
        // 该节点可能是子节点但也可能是其它节点的父节点,所以需要级联删除
        this.deleteByParentId(sysPermission.getId());
    }

    @Override
    public void deleteByParentId(String parentId) {
        LambdaQueryWrapper<LabelProductTag> query = new LambdaQueryWrapper<>();
        query.eq(LabelProductTag::getParentId, parentId);
        int countValue = this.count(query);
        if(countValue > 0) {
            this.remove(query);
        }
    }
}
