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
import com.laofeizhu.admin.modules.system.entity.SysCity;
import com.laofeizhu.admin.modules.system.service.ISysCityService;
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
 * @Description: 城市表信息
 * @Author: laofeizhu
 * @Date:   2019-10-19
 * @Version: V1.0
 */
@Slf4j
@Api(tags="城市表信息")
@RestController
@RequestMapping("/system/sysCity")
public class SysCityController {
	@Autowired
	private ISysCityService sysCityService;
	
	/**
	  * 分页列表查询
	 * @param sysCity
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "城市表信息-分页列表查询")
	@ApiOperation(value="城市表信息-分页列表查询", notes="城市表信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<SysCity>> queryPageList(SysCity sysCity,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<SysCity>> result = new Result<IPage<SysCity>>();
		QueryWrapper<SysCity> queryWrapper = QueryGenerator.initQueryWrapper(sysCity, req.getParameterMap());
		Page<SysCity> page = new Page<SysCity>(pageNo, pageSize);
		IPage<SysCity> pageList = sysCityService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param sysCity
	 * @return
	 */
	@AutoLog(value = "城市表信息-添加")
	@ApiOperation(value="城市表信息-添加", notes="城市表信息-添加")
	@PostMapping(value = "/add")
	public Result<SysCity> add(@RequestBody SysCity sysCity) {
		Result<SysCity> result = new Result<SysCity>();
		try {
			sysCityService.save(sysCity);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param sysCity
	 * @return
	 */
	@AutoLog(value = "城市表信息-编辑")
	@ApiOperation(value="城市表信息-编辑", notes="城市表信息-编辑")
	@PutMapping(value = "/edit")
	public Result<SysCity> edit(@RequestBody SysCity sysCity) {
		Result<SysCity> result = new Result<SysCity>();
		SysCity sysCityEntity = sysCityService.getById(sysCity.getId());
		if(sysCityEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = sysCityService.updateById(sysCity);
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
	@AutoLog(value = "城市表信息-通过id删除")
	@ApiOperation(value="城市表信息-通过id删除", notes="城市表信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<SysCity> delete(@RequestParam(name="id",required=true) String id) {
		Result<SysCity> result = new Result<SysCity>();
		SysCity sysCity = sysCityService.getById(id);
		if(sysCity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = sysCityService.removeById(id);
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
	@AutoLog(value = "城市表信息-批量删除")
	@ApiOperation(value="城市表信息-批量删除", notes="城市表信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<SysCity> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<SysCity> result = new Result<SysCity>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.sysCityService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "城市表信息-通过id查询")
	@ApiOperation(value="城市表信息-通过id查询", notes="城市表信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<SysCity> queryById(@RequestParam(name="id",required=true) String id) {
		Result<SysCity> result = new Result<SysCity>();
		SysCity sysCity = sysCityService.getById(id);
		if(sysCity==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysCity);
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
      QueryWrapper<SysCity> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              SysCity sysCity = JSON.parseObject(deString, SysCity.class);
              queryWrapper = QueryGenerator.initQueryWrapper(sysCity, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<SysCity> pageList = sysCityService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "城市表信息列表");
      mv.addObject(NormalExcelConstants.CLASS, SysCity.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("城市表信息列表数据", "导出人:Jeecg", "导出信息"));
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
              List<SysCity> listSysCitys = ExcelImportUtil.importExcel(file.getInputStream(), SysCity.class, params);
              for (SysCity sysCityExcel : listSysCitys) {
                  sysCityService.save(sysCityExcel);
              }
              return Result.ok("文件导入成功！数据行数:" + listSysCitys.size());
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
