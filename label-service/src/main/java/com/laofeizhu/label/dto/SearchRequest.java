package com.laofeizhu.label.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author laofeizhu
 * @Date 2021/1/29
 */
@Data
public class SearchRequest {
    private List<String> labels;
}
