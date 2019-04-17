package com.jt.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FileVo {
    //文件上传后的vo对象
    private Integer error=0;
    private String url;
    private Integer width;
    private Integer height;

    public FileVo() {
    }

    public FileVo(Integer error, String url, Integer width, Integer height) {
        this.error = error;
        this.url = url;
        this.width = width;
        this.height = height;
    }

}
