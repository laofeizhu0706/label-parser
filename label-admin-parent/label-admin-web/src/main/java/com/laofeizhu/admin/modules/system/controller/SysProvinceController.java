package com.laofeizhu.admin.modules.system.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.laofeizhu.admin.common.api.vo.Result;
import com.laofeizhu.admin.common.system.query.QueryGenerator;
import com.laofeizhu.admin.common.aspect.annotation.AutoLog;
import com.laofeizhu.admin.common.util.oConvertUtils;
import com.laofeizhu.admin.modules.system.entity.SysProvince;
import com.laofeizhu.admin.modules.system.service.ISysProvinceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 省份信息
 * @Author: laofeizhu
 * @Date:   2019-10-19
 * @Version: V1.0
 */
@Slf4j
@Api(tags="省份信息")
@RestController
@RequestMapping("/system/sysProvince")
public class SysProvinceController {
	@Autowired
	private ISysProvinceService sysProvinceService;
	
	/**
	  * 分页列表查询
	 * @param sysProvince
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "省份信息-分页列表查询")
	@ApiOperation(value="省份信息-分页列表查询", notes="省份信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<SysProvince>> queryPageList(SysProvince sysProvince,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<SysProvince>> result = new Result<IPage<SysProvince>>();
		QueryWrapper<SysProvince> queryWrapper = QueryGenerator.initQueryWrapper(sysProvince, req.getParameterMap());
		Page<SysProvince> page = new Page<SysProvince>(pageNo, pageSize);
		IPage<SysProvince> pageList = sysProvinceService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param sysProvince
	 * @return
	 */
	@AutoLog(value = "省份信息-添加")
	@ApiOperation(value="省份信息-添加", notes="省份信息-添加")
	@PostMapping(value = "/add")
	public Result<SysProvince> add(@RequestBody SysProvince sysProvince) {
		Result<SysProvince> result = new Result<SysProvince>();
		try {
			sysProvinceService.save(sysProvince);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param sysProvince
	 * @return
	 */
	@AutoLog(value = "省份信息-编辑")
	@ApiOperation(value="省份信息-编辑", notes="省份信息-编辑")
	@PutMapping(value = "/edit")
	public Result<SysProvince> edit(@RequestBody SysProvince sysProvince) {
		Result<SysProvince> result = new Result<SysProvince>();
		SysProvince sysProvinceEntity = sysProvinceService.getById(sysProvince.getId());
		if(sysProvinceEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = sysProvinceService.updateById(sysProvince);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}
		
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "省份信息-通过id删除")
	@ApiOperation(value="省份信息-通过id删除", notes="省份信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<SysProvince> delete(@RequestParam(name="id",required=true) String id) {
		Result<SysProvince> result = new Result<SysProvince>();
		SysProvince sysProvince = sysProvinceService.getById(id);
		if(sysProvince==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = sysProvinceService.removeById(id);
			if(ok) {
				result.success("删除成功!");
			}
		}
		
		return result;
	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "省份信息-批量删除")
	@ApiOperation(value="省份信息-批量删除", notes="省份信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<SysProvince> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<SysProvince> result = new Result<SysProvince>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.sysProvinceService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "省份信息-通过id查询")
	@ApiOperation(value="省份信息-通过id查询", notes="省份信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<SysProvince> queryById(@RequestParam(name="id",required=true) String id) {
		Result<SysProvince> result = new Result<SysProvince>();
		SysProvince sysProvince = sysProvinceService.getById(id);
		if(sysProvince==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysProvince);
			result.setSuccess(true);
		}
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   * @param response
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
      // Step.1 组装查询条件
      QueryWrapper<SysProvince> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              SysProvince sysProvince = JSON.parseObject(deString, SysProvince.class);
              queryWrapper = QueryGenerator.initQueryWrapper(sysProvince, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<SysProvince> pageList = sysProvinceService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "省份信息列表");
      mv.addObject(NormalExcelConstants.CLASS, SysProvince.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("省份信息列表数据", "导出人:Jeecg", "导出信息"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
  }

  /**
      * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<SysProvince> listSysProvinces = ExcelImportUtil.importExcel(file.getInputStream(), SysProvince.class, params);
              for (SysProvince sysProvinceExcel : listSysProvinces) {
                  sysProvinceService.save(sysProvinceExcel);
              }
              return Result.ok("文件导入成功！数据行数:" + listSysProvinces.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
  }

}
