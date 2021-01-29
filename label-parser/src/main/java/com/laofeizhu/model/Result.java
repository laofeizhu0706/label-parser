package com.laofeizhu.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

/**
 * @Classname Result
 * @Description TODO
 * @Date 2021/1/15 21:54
 * @Created by laofeizhu
 */
public class Result extends BaseModel {

    private static final long serialVersionUID = 712403533007017501L;

    private final Set<String> results = Sets.newHashSet();
    private final Set<BaseProductVo> productResults = Sets.newHashSet();

    public void add(String value) {
        results.add(value);
    }

    public void addProduct(BaseProductVo value) {
        productResults.add(value);
    }

    public void addAll(List<String> values) {
        results.addAll(values);
    }


    public void addAllProduct(List<BaseProductVo> values) {
        productResults.addAll(values);
    }

    public List<String> getResults() {
        return Lists.newArrayList(results);
    }

    public List<BaseProductVo> getProductResults() {
        return Lists.newArrayList(productResults);
    }
}
