package com.jt.vo;

import lombok.Data;
import lombok.experimental.Accessors;

//因为 vo对象最终转化为Json串  调用get方法  无需序列化
//如果当前对象需要远程传输  则要序列化
@Data
@Accessors(chain = true)
public class EasyUITree {
    private Long id;//节点的id号
    private String text; //节点名称
    private String state;//节点状态

    public EasyUITree() {
    }

    public EasyUITree(Long id, String text, String state) {
        this.id = id;
        this.text = text;
        this.state = state;
    }
}
