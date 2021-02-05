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
public class Result<T> extends BaseModel {

    private static final long serialVersionUID = 712403533007017501L;

    private final Set<T> results = Sets.newHashSet();

    public void add(T value) {
        results.add(value);
    }


    public void addAll(List<T> values) {
        results.addAll(values);
    }

    public List<T> getResults() {
        return Lists.newArrayList(results);
    }

}
