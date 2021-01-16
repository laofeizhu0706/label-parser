package com.laofeizhu;

import cn.hutool.core.io.FileUtil;
import com.google.common.collect.Lists;
import com.laofeizhu.model.BaseProductVo;
import com.laofeizhu.service.IRecommendService;
import com.laofeizhu.service.impl.DefaultRecommendService;

import java.io.File;
import java.util.List;

/**
 * @Classname Main
 * @Description TODO
 * @Date 2021/1/16 16:40
 * @Created by laofeizhu
 */
public class Main {
    public static void main(String[] args) {
        List<BaseProductVo> productVos = Lists.newArrayList(new BaseProductVo("1", Lists.newArrayList("product_label_2")),
                new BaseProductVo("2", Lists.newArrayList("product_label_1")),new BaseProductVo("3", Lists.newArrayList("product_label_3")));
        IRecommendService service = DefaultRecommendService.build("C:\\Users\\laofeizhu\\Desktop\\java_project\\label.drl",productVos);
        List<String> list = service.listMatchingLabel(Lists.newArrayList("user_label_1"));
        List<BaseProductVo> list1 = service.listMatchingProduct(Lists.newArrayList("user_label_1"));
//        String content = FileUtil.readUtf8String("label.txt");
        System.out.println(list1);
    }
}
