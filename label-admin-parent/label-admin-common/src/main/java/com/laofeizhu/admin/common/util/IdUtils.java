package com.laofeizhu.admin.common.util;

import com.baomidou.mybatisplus.core.toolkit.Sequence;

/**
 * @author 老肥猪
 * @since 2020/1/11
 */
public class IdUtils {
    private static Sequence worker = new Sequence();

    public static Long nextId(){
        return worker.nextId();
    }
}
