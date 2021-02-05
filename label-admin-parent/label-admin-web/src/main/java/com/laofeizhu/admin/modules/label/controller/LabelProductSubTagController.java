package com.laofeizhu.admin.modules.label.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laofeizhu.admin.common.api.vo.Result;
import com.laofeizhu.admin.common.aspect.annotation.AutoLog;
import com.laofeizhu.admin.common.system.base.controller.JeecgController;
import com.laofeizhu.admin.common.system.query.QueryGenerator;
import com.laofeizhu.admin.modules.label.entity.LabelProductSubTag;
import com.laofeizhu.admin.modules.label.service.ILabelProductSubTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Description: 商品子标签
 * @Author: laofeizhu
 * @Date: 2021-02-02
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "商品子标签")
@RestController
@RequestMapping("/label/labelProductSubTag")
public class LabelProductSubTagController extends JeecgController<LabelProductSubTag, ILabelProductSubTagService> {
    @Autowired
    private ILabelProductSubTagService labelProductSubTagService;

    /**
     * 分页列表查询
     *
     * @param labelProductSubTag
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "商品子标签-分页列表查询")
    @ApiOperation(value = "商品子标签-分页列表查询", notes = "商品子标签-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<LabelProductSubTag>> queryPageList(LabelProductSubTag labelProductSubTag,
                                                           @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                           HttpServletRequest req) {
        Result<IPage<LabelProductSubTag>> result = new Result<IPage<LabelProductSubTag>>();
        QueryWrapper<LabelProductSubTag> queryWrapper = QueryGenerator.initQueryWrapper(labelProductSubTag, req.getParameterMap());
        Page<LabelProductSubTag> page = new Page<LabelProductSubTag>(pageNo, pageSize);
        IPage<LabelProductSubTag> pageList = labelProductSubTagService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     *
     * @param labelProductSubTag
     * @return
     */
    @AutoLog(value = "商品子标签-添加")
    @ApiOperation(value = "商品子标签-添加", notes = "商品子标签-添加")
    @PostMapping(value = "/add")
    public Result<LabelProductSubTag> add(@RequestBody LabelProductSubTag labelProductSubTag) {
        Result<LabelProductSubTag> result = new Result<LabelProductSubTag>();
        try {
            labelProductSubTagService.addTag(labelProductSubTag);
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
     * @param labelProductSubTag
     * @return
     */
    @AutoLog(value = "商品子标签-编辑")
    @ApiOperation(value = "商品子标签-编辑", notes = "商品子标签-编辑")
    @PutMapping(value = "/edit")
    public Result<LabelProductSubTag> edit(@RequestBody LabelProductSubTag labelProductSubTag) {
        Result<LabelProductSubTag> result = new Result<LabelProductSubTag>();
        LabelProductSubTag labelProductSubTagEntity = labelProductSubTagService.getById(labelProductSubTag.getId());
        if (labelProductSubTagEntity == null) {
            result.error500("未找到对应实体");
        } else {
            labelProductSubTagService.updateTag(labelProductSubTag);
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
    @AutoLog(value = "商品子标签-通过id删除")
    @ApiOperation(value = "商品子标签-通过id删除", notes = "商品子标签-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<LabelProductSubTag> delete(@RequestParam(name = "id", required = true) String id) {
        Result<LabelProductSubTag> result = new Result<LabelProductSubTag>();
        LabelProductSubTag labelProductSubTag = labelProductSubTagService.getById(id);
        if (labelProductSubTag == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = labelProductSubTagService.removeById(id);
            if (ok) {
                result.success("删除成功!");
            }
        }

        return result;
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "商品子标签-批量删除")
    @ApiOperation(value = "商品子标签-批量删除", notes = "商品子标签-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<LabelProductSubTag> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<LabelProductSubTag> result = new Result<LabelProductSubTag>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.labelProductSubTagService.removeByIds(Arrays.asList(ids.split(",")));
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
    @AutoLog(value = "商品子标签-通过id查询")
    @ApiOperation(value = "商品子标签-通过id查询", notes = "商品子标签-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<LabelProductSubTag> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<LabelProductSubTag> result = new Result<LabelProductSubTag>();
        LabelProductSubTag labelProductSubTag = labelProductSubTagService.getById(id);
        if (labelProductSubTag == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(labelProductSubTag);
            result.setSuccess(true);
        }
        return result;
    }
}
