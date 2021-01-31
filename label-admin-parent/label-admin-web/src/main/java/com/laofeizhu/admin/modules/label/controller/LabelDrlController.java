package com.laofeizhu.admin.modules.label.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laofeizhu.admin.common.api.vo.Result;
import com.laofeizhu.admin.common.system.base.controller.JeecgController;
import com.laofeizhu.admin.common.system.query.QueryGenerator;
import com.laofeizhu.admin.modules.label.entity.LabelDrl;
import com.laofeizhu.admin.modules.label.entity.LabelDrlHistory;
import com.laofeizhu.admin.modules.label.service.ILabelDrlHistoryService;
import com.laofeizhu.admin.modules.label.service.ILabelDrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Description: 标签drl文件表
 * @Author: jeecg-boot
 * @Date: 2021-01-31
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "标签drl文件表")
@RestController
@RequestMapping("/label/labelDrl")
public class LabelDrlController extends JeecgController<LabelDrl, ILabelDrlService> {

    @Autowired
    private ILabelDrlService labelDrlService;

    @Autowired
    private ILabelDrlHistoryService labelDrlHistoryService;

    /**
     * 分页列表查询
     *
     * @param labelDrl
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "标签drl文件表-分页列表查询", notes = "标签drl文件表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(LabelDrl labelDrl,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<LabelDrl> queryWrapper = QueryGenerator.initQueryWrapper(labelDrl, req.getParameterMap());
        Page<LabelDrl> page = new Page<LabelDrl>(pageNo, pageSize);
        IPage<LabelDrl> pageList = labelDrlService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param labelDrl
     * @return
     */
    @ApiOperation(value = "标签drl文件表-添加", notes = "标签drl文件表-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody LabelDrl labelDrl) {
        labelDrlService.save(labelDrl);
        LabelDrlHistory history = new LabelDrlHistory();
        history.setName(labelDrl.getTitle());
        history.setContent(labelDrl.getContent());
        history.setLabelVersion(labelDrl.getLabelVersion());
        labelDrlHistoryService.save(history);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param labelDrl
     * @return
     */
    @ApiOperation(value = "标签drl文件表-编辑", notes = "标签drl文件表-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody LabelDrl labelDrl) {
        labelDrlService.updateById(labelDrl);
        LabelDrlHistory history = new LabelDrlHistory();
        history.setName(labelDrl.getTitle());
        history.setContent(labelDrl.getContent());
        history.setLabelVersion(labelDrl.getLabelVersion());
        labelDrlHistoryService.save(history);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "标签drl文件表-通过id删除", notes = "标签drl文件表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        labelDrlService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "标签drl文件表-批量删除", notes = "标签drl文件表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.labelDrlService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "标签drl文件表-通过id查询", notes = "标签drl文件表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        LabelDrl labelDrl = labelDrlService.getById(id);
        return Result.ok(labelDrl);
    }

}
