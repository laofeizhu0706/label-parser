package com.laofeizhu.label.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.laofeizhu.label.dao.LabelDrlMapper;
import com.laofeizhu.label.dto.LabelDrl;
import com.laofeizhu.label.dto.Result;
import com.laofeizhu.label.dto.SearchRequest;
import com.laofeizhu.label.service.LabelDrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author laofeizhu
 * @Date 2021/1/29
 */
@RestController
@Slf4j
public class TestController {


    @Autowired
    @Qualifier("labelDrlServiceImpl")
    private LabelDrlService labelDrlService;

    @PostMapping("/searchProductLabel")
    public Result searchProductLabel(@RequestBody SearchRequest request) {
        try {
            return Result.success(labelDrlService.searchProductLabel(request.getLabels()));
        } catch (Exception e) {
            log.error("searchProductLabel error", e);
            return Result.fail("searchProductLabel error");
        }
    }

    @PostMapping("/searchProduct")
    public Result searchProduct(@RequestBody SearchRequest request) {
        try {
            return Result.success(labelDrlService.searchProductLabel(request.getLabels()));
        } catch (Exception e) {
            log.error("searchProductLabel error", e);
            return Result.fail("searchProductLabel error");
        }
    }
}
