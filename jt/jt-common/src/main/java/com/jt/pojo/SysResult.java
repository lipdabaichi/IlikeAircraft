package com.jt.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 该类表示页面统一返回方式.
 * status  200表示业务处理正确
 *          201表示业务处理失败
 *  msg    表示服务端返回提示信息
 *  data   表示服务端返回的数据
 */
@Data
@Accessors(chain = true)
public class SysResult {
    private Integer status;
    private String msg;
    private Object data;

    public SysResult() {
    }

    public SysResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
    //定义成功的静态方法
    public static SysResult ok(String msg ,Object data){
        return new SysResult(200, msg, data);
    }
    public static SysResult ok(Object data){
        return new SysResult(200, null, data);
    }
    public static SysResult ok(){
        return new SysResult(200, null, null);
    }
    //定义失败的静态方法
    public static SysResult fail(String msg ,Object data){
        return new SysResult(201, msg, data);
    }
    public static SysResult fail(){
        return new SysResult(201, null, null);
    }

}
