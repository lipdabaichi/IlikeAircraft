package com.jt.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HttpClientService {
    @Autowired
    private CloseableHttpClient httpClient;
    @Autowired
    private RequestConfig requestConfig;
    //该对象封装了请求的超时时间

    /**
     * 为了用户请求方便 封装doGet方法
     * 参数说明:
     * 1.String url 的地址
     * 2.map<String,String>集合封装 参数
     * 3.字符集编码
     *
     * GET URL 参数问题;
     * http://localhost:8092/addUser?id=1&name=tomcat
     */
    public String doGet(String url, Map<String, String> params, String charset) {
        //1.判断字符集编码是否为null
        if (StringUtils.isEmpty(charset)) {
            //如果为null,则是定默认的字符集
            charset = "UTF-8";
        }

        //2.判断用户是否传递参数
        //localhost:8092/addUser?id=value
        if (params != null) {
            //如果参数不为空,则需要url地址拼串  ?id=1&name=tomcat&
            url = url+"?" ;
            //需要获取参数变量map集合
            for (Map.Entry<String, String> entry:params.entrySet()      //对Map遍历的写法
                 ) {
                url += entry.getKey() + "=" + entry.getValue() + "&";
            }
            url = url.substring(0, url.length() - 1);  //把最后的& 扔掉
        }
        //3.封装请求对象httpGet
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);

        String result = null;
        //4.发起http请求
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) { //如果返回值状态信息是200表示请求正确
                result = EntityUtils.toString(response.getEntity(),charset);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        return result;
    }
    public String doGet(String url) {
        return doGet(url, null, null);
    }
    public String doGet(String url, Map<String, String> params) {
        return doGet(url, params,null);
    }

    //实现httpClient POST提交
    public String doPost(String url,Map<String,String> params,String charset){
        String result = null;

        //1.定义请求类型
        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);  	//定义超时时间

        //2.判断字符集是否为null
        if(StringUtils.isEmpty(charset)){

            charset = "UTF-8";
        }

        //3.判断用户是否传递参数
        if(params !=null){
            //3.2准备List集合信息
            List<NameValuePair> parameters =
                    new ArrayList<>();

            //3.3将数据封装到List集合中
            for (Map.Entry<String,String> entry : params.entrySet()) {

                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            //3.1模拟表单提交
            try {
                UrlEncodedFormEntity formEntity =
                        new UrlEncodedFormEntity(parameters,charset); //采用u8编码

                //3.4将实体对象封装到请求对象中
                post.setEntity(formEntity);
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            }
        }

        //4.发送请求
        try {
            CloseableHttpResponse response =
                    httpClient.execute(post);

            //4.1判断返回值状态
            if(response.getStatusLine().getStatusCode() == 200) {

                //4.2表示请求成功
                result = EntityUtils.toString(response.getEntity(),charset);
            }else{
                System.out.println("获取状态码信息:"+response.getStatusLine().getStatusCode());
                throw new RuntimeException();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }



    public String doPost(String url){

        return doPost(url, null, null);
    }

    public String doPost(String url,Map<String,String> params){

        return doPost(url, params, null);
    }

    public String doPost(String url,String charset){

        return doPost(url, null, charset);
    }

}
