package com.laofeizhu.admin.modules.label.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laofeizhu.admin.common.api.vo.Result;
import com.laofeizhu.admin.common.aspect.annotation.AutoLog;
import com.laofeizhu.admin.common.system.base.controller.JeecgController;
import com.laofeizhu.admin.common.system.query.QueryGenerator;
import com.laofeizhu.admin.common.util.oConvertUtils;
import com.laofeizhu.admin.modules.label.entity.LabelUserSubTag;
import com.laofeizhu.admin.modules.label.service.ILabelUserSubTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description: 用户子标签
 * @Author: laofeizhu
 * @Date: 2021-02-02
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "用户子标签")
@RestController
@RequestMapping("/label/labelUserSubTag")
public class LabelUserSubTagController extends JeecgController<LabelUserSubTag, ILabelUserSubTagService> {
    @Autowired
    private ILabelUserSubTagService labelUserSubTagService;

    /**
     * 分页列表查询
     *
     * @param labelUserSubTag
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "用户子标签-分页列表查询")
    @ApiOperation(value = "用户子标签-分页列表查询", notes = "用户子标签-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<LabelUserSubTag>> queryPageList(LabelUserSubTag labelUserSubTag,
                                                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                        HttpServletRequest req) {
        Result<IPage<LabelUserSubTag>> result = new Result<IPage<LabelUserSubTag>>();
        QueryWrapper<LabelUserSubTag> queryWrapper = QueryGenerator.initQueryWrapper(labelUserSubTag, req.getParameterMap());
        Page<LabelUserSubTag> page = new Page<LabelUserSubTag>(pageNo, pageSize);
        IPage<LabelUserSubTag> pageList = labelUserSubTagService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     *
     * @param labelUserSubTag
     * @return
     */
    @AutoLog(value = "用户子标签-添加")
    @ApiOperation(value = "用户子标签-添加", notes = "用户子标签-添加")
    @PostMapping(value = "/add")
    public Result<LabelUserSubTag> add(@RequestBody LabelUserSubTag labelUserSubTag) {
        Result<LabelUserSubTag> result = new Result<LabelUserSubTag>();
        try {
            labelUserSubTagService.save(labelUserSubTag);
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
     * @param labelUserSubTag
     * @return
     */
    @AutoLog(value = "用户子标签-编辑")
    @ApiOperation(value = "用户子标签-编辑", notes = "用户子标签-编辑")
    @PutMapping(value = "/edit")
    public Result<LabelUserSubTag> edit(@RequestBody LabelUserSubTag labelUserSubTag) {
        Result<LabelUserSubTag> result = new Result<LabelUserSubTag>();
        LabelUserSubTag labelUserSubTagEntity = labelUserSubTagService.getById(labelUserSubTag.getId());
        if (labelUserSubTagEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = labelUserSubTagService.updateById(labelUserSubTag);
            //TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }

        return result;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "用户子标签-通过id删除")
    @ApiOperation(value = "用户子标签-通过id删除", notes = "用户子标签-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<LabelUserSubTag> delete(@RequestParam(name = "id", required = true) String id) {
        Result<LabelUserSubTag> result = new Result<LabelUserSubTag>();
        LabelUserSubTag labelUserSubTag = labelUserSubTagService.getById(id);
        if (labelUserSubTag == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = labelUserSubTagService.removeById(id);
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
    @AutoLog(value = "用户子标签-批量删除")
    @ApiOperation(value = "用户子标签-批量删除", notes = "用户子标签-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<LabelUserSubTag> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<LabelUserSubTag> result = new Result<LabelUserSubTag>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.labelUserSubTagService.removeByIds(Arrays.asList(ids.split(",")));
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
    @AutoLog(value = "用户子标签-通过id查询")
    @ApiOperation(value = "用户子标签-通过id查询", notes = "用户子标签-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<LabelUserSubTag> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<LabelUserSubTag> result = new Result<LabelUserSubTag>();
        LabelUserSubTag labelUserSubTag = labelUserSubTagService.getById(id);
        if (labelUserSubTag == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(labelUserSubTag);
            result.setSuccess(true);
        }
        return result;
    }
}
