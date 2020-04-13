package com.transform.util;

import com.alibaba.fastjson.JSON;
import com.transform.bean.ResultBean;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpGetUtil {
    public static ResultBean get(String url) {
        return getWithParams(url, new HashMap<>());
    }

    //有参方式
    public static ResultBean getWithParams(String url, Map<String, Object> params) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        try {
            // 创建Get请求
            url = joinParam(url, params);
            HttpGet httpGet = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(2000000) //服务器响应超时时间
                    .setConnectTimeout(2000000) //连接服务器超时时间
                    .build();
            httpGet.setConfig(requestConfig);
            httpGet.setHeader("Accept", "application/json");
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
//            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
//                System.out.println("响应内容长度为:" + responseEntity.getContentLength());

                ResultBean resultBean = JSON.parseObject(EntityUtils.toString(responseEntity), ResultBean.class);
                return resultBean;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private static String joinParam(String url, Map<String, Object> params) {
        if (params == null || params.size() == 0) {
            return url;
        }

        StringBuilder urlBuilder = new StringBuilder(url);
        urlBuilder.append("?");

        int counter = 0;
        for (Map.Entry<String,Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key == null) {
                continue;
            }

            if (counter == 0) {
                urlBuilder.append(key).append("=").append(value);
            } else {
                urlBuilder.append("&").append(key).append("=").append(value);
            }
            counter++;
        }

        return urlBuilder.toString();
    }
}
