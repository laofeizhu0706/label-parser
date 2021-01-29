package com.laofeizhu.data;

import java.util.List;

/**
 * @Classname ICommonContent
 * @Description TODO
 * @Date 2021/1/16 15:07
 * @Created by laofeizhu
 */
public interface ICommonContent {

    /**
     *
     * @param userLabel
     * @return
     */
    List<String> get(String userLabel);

    /**
     *
     * @param userLabel
     * @return
     */
    boolean containsIn(String userLabel);
}
