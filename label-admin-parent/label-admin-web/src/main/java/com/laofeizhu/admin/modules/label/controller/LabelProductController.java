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
import com.laofeizhu.admin.modules.label.entity.LabelProduct;
import com.laofeizhu.admin.modules.label.service.ILabelProductService;
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
 * @Description: 商品表
 * @Author: laofeizhu
 * @Date:   2021-02-01
 * @Version: V1.0
 */
@Slf4j
@Api(tags="商品表")
@RestController
@RequestMapping("/label/labelProduct")
public class LabelProductController extends JeecgController<LabelProduct, ILabelProductService> {
	@Autowired
	private ILabelProductService labelProductService;
	
	/**
	  * 分页列表查询
	 * @param labelProduct
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "商品表-分页列表查询")
	@ApiOperation(value="商品表-分页列表查询", notes="商品表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<LabelProduct>> queryPageList(LabelProduct labelProduct,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<LabelProduct>> result = new Result<IPage<LabelProduct>>();
		QueryWrapper<LabelProduct> queryWrapper = QueryGenerator.initQueryWrapper(labelProduct, req.getParameterMap());
		Page<LabelProduct> page = new Page<LabelProduct>(pageNo, pageSize);
		IPage<LabelProduct> pageList = labelProductService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param labelProduct
	 * @return
	 */
	@AutoLog(value = "商品表-添加")
	@ApiOperation(value="商品表-添加", notes="商品表-添加")
	@PostMapping(value = "/add")
	public Result<LabelProduct> add(@RequestBody LabelProduct labelProduct) {
		Result<LabelProduct> result = new Result<LabelProduct>();
		try {
			labelProductService.save(labelProduct);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param labelProduct
	 * @return
	 */
	@AutoLog(value = "商品表-编辑")
	@ApiOperation(value="商品表-编辑", notes="商品表-编辑")
	@PutMapping(value = "/edit")
	public Result<LabelProduct> edit(@RequestBody LabelProduct labelProduct) {
		Result<LabelProduct> result = new Result<LabelProduct>();
		LabelProduct labelProductEntity = labelProductService.getById(labelProduct.getId());
		if(labelProductEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = labelProductService.updateById(labelProduct);
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
	@AutoLog(value = "商品表-通过id删除")
	@ApiOperation(value="商品表-通过id删除", notes="商品表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<LabelProduct> delete(@RequestParam(name="id",required=true) String id) {
		Result<LabelProduct> result = new Result<LabelProduct>();
		LabelProduct labelProduct = labelProductService.getById(id);
		if(labelProduct==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = labelProductService.removeById(id);
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
	@AutoLog(value = "商品表-批量删除")
	@ApiOperation(value="商品表-批量删除", notes="商品表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<LabelProduct> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<LabelProduct> result = new Result<LabelProduct>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.labelProductService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品表-通过id查询")
	@ApiOperation(value="商品表-通过id查询", notes="商品表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<LabelProduct> queryById(@RequestParam(name="id",required=true) String id) {
		Result<LabelProduct> result = new Result<LabelProduct>();
		LabelProduct labelProduct = labelProductService.getById(id);
		if(labelProduct==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(labelProduct);
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
      QueryWrapper<LabelProduct> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              LabelProduct labelProduct = JSON.parseObject(deString, LabelProduct.class);
              queryWrapper = QueryGenerator.initQueryWrapper(labelProduct, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<LabelProduct> pageList = labelProductService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "商品表列表");
      mv.addObject(NormalExcelConstants.CLASS, LabelProduct.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("商品表列表数据", "导出人:Jeecg", "导出信息"));
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
              List<LabelProduct> listLabelProducts = ExcelImportUtil.importExcel(file.getInputStream(), LabelProduct.class, params);
              for (LabelProduct labelProductExcel : listLabelProducts) {
                  labelProductService.save(labelProductExcel);
              }
              return Result.ok("文件导入成功！数据行数:" + listLabelProducts.size());
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
