package com.zdpang.template.baiduai;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.util.Base64Util;
import com.zdpang.template.httpclient.HttpUtil;
import org.springframework.util.FileCopyUtils;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhangDaPang 285296372@qq.com
 * @date 2019/2/14 14:59
 */
public class BaiduAiService {
    private static String ANIMAL_URL = "https://aip.baidubce.com/rest/2.0/image-classify/v1/animal";
    private static String LOGIN_URL = "https://aip.baidubce.com/oauth/2.0/token";
    private static String REFRESH_URL = "https://openapi.baidu.com/oauth/2.0/token";
    private static String GRANT_TYPE_LOGIN = "client_credentials";
    private static String GRANT_TYPE_REFRESH = "refresh_token";
    private static String CLIENT_ID = "";
    private static String CLIENT_SECRET = "";
    private static volatile String TOKEN = "";
    private static volatile String REFRESH_TOKEN = "";
    public static void main(String[] args) throws Exception {
//        System.out.println(loginAndGetToken());
        System.out.println(refreshAndGetToken());
    }

    public static String animal() throws Exception {
        // 本地文件路径
        String filePath = "";
        FileInputStream fileInputStream = new FileInputStream(filePath);
        byte[] imgData = FileCopyUtils.copyToByteArray(fileInputStream);
        String imgStr = Base64Util.encode(imgData);

        Map<String, String> map = new HashMap<>();
        map.put("image", imgStr);
        map.put("access_token", TOKEN);
        map.put("top_num", "6");
        String result = HttpUtil.postMap(ANIMAL_URL, map);
        return result;
    }

    public static String refreshAndGetToken() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("grant_type", GRANT_TYPE_REFRESH);
        map.put("client_id", CLIENT_ID);
        map.put("client_secret", CLIENT_SECRET);
        map.put("refresh_token", REFRESH_TOKEN);
        String res = HttpUtil.postMap(REFRESH_URL, map);
        Map<String, String> resMap = JSON.parseObject(res, Map.class);
        return resMap.get("access_token");
    }

    public static String loginAndGetToken() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("grant_type", GRANT_TYPE_LOGIN);
        map.put("client_id", CLIENT_ID);
        map.put("client_secret", CLIENT_SECRET);
        String res = HttpUtil.postMap(LOGIN_URL, map);
        Map<String, String> resMap = JSON.parseObject(res, Map.class);
        return resMap.get("access_token");
    }
}
