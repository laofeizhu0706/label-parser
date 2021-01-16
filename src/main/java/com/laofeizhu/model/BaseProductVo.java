package com.laofeizhu.model;

import java.util.List;
import java.util.Objects;

/**
 * @Classname BaseProductVo
 * @Description TODO
 * @Date 2021/1/16 12:14
 * @Created by laofeizhu
 */
public class BaseProductVo extends BaseModel {

    private static final long serialVersionUID = 2578931454276978960L;

    private String id;

    private List<String> labels;

    public BaseProductVo(String id, List<String> labels) {
        this.id = id;
        this.labels = labels;
    }

    public BaseProductVo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseProductVo that = (BaseProductVo) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
