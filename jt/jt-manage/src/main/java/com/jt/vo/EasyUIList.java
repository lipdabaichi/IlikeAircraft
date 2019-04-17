package com.jt.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@Accessors(chain = true)
public class EasyUIList {
    private Integer total ;//记录总数
    private List<?> rows;//保存商品的信息
    public EasyUIList(){}

    public EasyUIList(Integer total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }
}
