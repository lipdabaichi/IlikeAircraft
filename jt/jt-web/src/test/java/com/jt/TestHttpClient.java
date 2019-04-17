package com.jt;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;

public class TestHttpClient {
    /**
     * 实现步骤:
     * 1.创建httpClient对象 发起用户请求
     * 2.指定url请求地址
     * 3.指定请求的方式 get和post
     * 规则:一般查询操作,使用get请求
     * 如果涉及数据数据入库/更新  并且数据很大时采用post提交
     * 涉密操作采用post
     * 4.发起请求获取返回值数据 JSON串
     * 5.判断请求是否正确  检查状态码 200
     * 6.从返回值对象中获取数据(json/html).之后转成转化对象.
     */
    //实现get请求
    @Test
    public void testGet() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "https://item.jd.com/100002285140.html";
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            System.out.println("请求正确返回!!!");
            String result = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(result);
        }


    }
}
