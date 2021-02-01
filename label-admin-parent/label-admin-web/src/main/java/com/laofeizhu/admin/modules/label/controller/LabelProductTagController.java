package com.laofeizhu.admin.modules.label.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.laofeizhu.admin.common.api.vo.Result;
import com.laofeizhu.admin.common.aspect.annotation.AutoLog;
import com.laofeizhu.admin.common.system.base.controller.JeecgController;
import com.laofeizhu.admin.common.util.oConvertUtils;
import com.laofeizhu.admin.modules.label.entity.LabelProductTag;
import com.laofeizhu.admin.modules.label.entity.LabelProductTagTree;
import com.laofeizhu.admin.modules.label.service.ILabelProductTagService;
import com.laofeizhu.admin.modules.system.model.TreeModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Description: 用户标签
 * @Author: laofeizhu
 * @Date: 2021-02-01
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "用户标签")
@RestController
@RequestMapping("/label/labelProductTag")
public class LabelProductTagController extends JeecgController<LabelProductTag, ILabelProductTagService> {
    @Autowired
    private ILabelProductTagService labelProductTagService;

    /**
     * 加载数据节点
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<LabelProductTagTree>> list() {
        Result<List<LabelProductTagTree>> result = new Result<>();
        try {
            LambdaQueryWrapper<LabelProductTag> query = new LambdaQueryWrapper<LabelProductTag>();
            List<LabelProductTag> list = labelProductTagService.list(query);
            List<LabelProductTagTree> treeList = new ArrayList<>();
            getTreeList(treeList, list, null);
            treeList.sort(Comparator.comparing(LabelProductTagTree::getName));
            result.setResult(treeList);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 获取全部的权限树
     *
     * @return
     */
    @RequestMapping(value = "/queryTreeList", method = RequestMethod.GET)
    public Result<Map<String, Object>> queryTreeList() {
        Result<Map<String, Object>> result = new Result<>();
        // 全部权限ids
        List<String> ids = new ArrayList<>();
        try {
            LambdaQueryWrapper<LabelProductTag> query = new LambdaQueryWrapper<>();
            List<LabelProductTag> list = labelProductTagService.list(query);
            for (LabelProductTag sysPer : list) {
                ids.add(sysPer.getId());
            }
            List<TreeModel> treeList = new ArrayList<>();
            getTreeModelList(treeList, list, null);
            treeList.sort(Comparator.comparing(TreeModel::getTitle));
            Map<String, Object> resMap = new HashMap<String, Object>();
            resMap.put("treeList", treeList); // 全部树节点数据
            resMap.put("ids", ids);// 全部树ids
            result.setResult(resMap);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 添加
     *
     * @param labelProductTag
     * @return
     */
    @AutoLog(value = "用户标签-添加")
    @ApiOperation(value = "用户标签-添加", notes = "用户标签-添加")
    @PostMapping(value = "/add")
    public Result<LabelProductTag> add(@RequestBody LabelProductTag labelProductTag) {
        Result<LabelProductTag> result = new Result<LabelProductTag>();
        try {
            labelProductTagService.addTag(labelProductTag);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 编辑
     *
     * @param labelProductTag
     * @return
     */
    @AutoLog(value = "用户标签-编辑")
    @ApiOperation(value = "用户标签-编辑", notes = "用户标签-编辑")
    @PutMapping(value = "/edit")
    public Result<LabelProductTag> edit(@RequestBody LabelProductTag labelProductTag) {
        Result<LabelProductTag> result = new Result<LabelProductTag>();
        LabelProductTag labelProductTagEntity = labelProductTagService.getById(labelProductTag.getId());
        if (labelProductTagEntity == null) {
            result.error500("未找到对应实体");
        } else {
            labelProductTagService.editTag(labelProductTag);
            result.success("修改成功!");
        }
        return result;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "用户标签-通过id删除")
    @ApiOperation(value = "用户标签-通过id删除", notes = "用户标签-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<LabelProductTag> delete(@RequestParam(name = "id", required = true) String id) {
        Result<LabelProductTag> result = new Result<LabelProductTag>();
        LabelProductTag labelProductTag = labelProductTagService.getById(id);
        if (labelProductTag == null) {
            result.error500("未找到对应实体");
        } else {
            labelProductTagService.deleteTag(id);
            result.success("删除成功!");
        }
        return result;
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "用户标签-批量删除")
    @ApiOperation(value = "用户标签-批量删除", notes = "用户标签-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<LabelProductTag> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<LabelProductTag> result = new Result<LabelProductTag>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            for (String id : ids.split(",")) {
                this.labelProductTagService.deleteTag(id);
            }
            result.success("删除成功!");
        }
        return result;
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "用户标签-通过id查询")
    @ApiOperation(value = "用户标签-通过id查询", notes = "用户标签-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<LabelProductTag> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<LabelProductTag> result = new Result<LabelProductTag>();
        LabelProductTag labelProductTag = labelProductTagService.getById(id);
        if (labelProductTag == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(labelProductTag);
            result.setSuccess(true);
        }
        return result;
    }


    private void getTreeModelList(List<TreeModel> treeList, List<LabelProductTag> metaList, TreeModel temp) {
        for (LabelProductTag permission : metaList) {
            String tempPid = permission.getParentId();
            TreeModel tree = new TreeModel(permission);
            if (temp == null && oConvertUtils.isEmpty(tempPid)) {
                treeList.add(tree);
                if (!tree.getIsLeaf()) {
                    getTreeModelList(treeList, metaList, tree);
                }
            } else if (temp != null && tempPid != null && tempPid.equals(temp.getKey())) {
                temp.getChildren().add(tree);
                if (!tree.getIsLeaf()) {
                    getTreeModelList(treeList, metaList, tree);
                }
            }

        }
    }

    private void getTreeList(List<LabelProductTagTree> treeList, List<LabelProductTag> metaList, LabelProductTagTree temp) {
        for (LabelProductTag labelProductTag : metaList) {
            String tempPid = labelProductTag.getParentId();
            LabelProductTagTree tree = new LabelProductTagTree(labelProductTag);
            if (temp == null && oConvertUtils.isEmpty(tempPid)) {
                treeList.add(tree);
                if (!tree.getLeaf()) {
                    getTreeList(treeList, metaList, tree);
                }
            } else if (temp != null && tempPid != null && tempPid.equals(temp.getId())) {
                temp.getChildren().add(tree);
                if (!tree.getLeaf()) {
                    getTreeList(treeList, metaList, tree);
                }
            }

        }
    }
}
