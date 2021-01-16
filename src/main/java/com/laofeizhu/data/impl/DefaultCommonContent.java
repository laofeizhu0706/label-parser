package com.laofeizhu.data.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.laofeizhu.data.ICommonContent;
import com.laofeizhu.model.BaseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Classname Content
 * @Description TODO
 * @Date 2021/1/15 21:47
 * @Created by laofeizhu
 */
public class DefaultCommonContent extends BaseModel implements ICommonContent {
    private static final long serialVersionUID = 5298933440865056702L;

    private  Map<String, Set<String>> map = new HashMap<>();

    public void put(String key, String value) {
        if (map.containsKey(key)) {
            map.get(key).add(value);
        } else {
            map.put(key, Sets.newHashSet(value));
        }
    }

    @Override
    public boolean containsIn(String key) {
        return map.containsKey(key);
    }

    @Override
    public List<String> get(String key) {
        return Lists.newArrayList(map.get(key));
    }
}
