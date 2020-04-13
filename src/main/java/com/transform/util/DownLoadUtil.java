package com.transform.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

public class DownLoadUtil {
    /**
      * 下载文件到本地
      * @author sun
      * @date 2018年3月25日 上午11:01:05
      * @param urlString
      * @param filename
      * @throws Exception
      */
    public static void download(String urlString, String filename) throws Exception {
        /**
         * 排重--存在则返回
         */
        File file = new File(filename);
        if(file.exists()){
            return;
        }
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }

        URL url = new URL(urlString);// 构造URL
        URLConnection con = url.openConnection();// 打开连接
        InputStream is = con.getInputStream();// 输入流
        String code = con.getHeaderField("Content-Encoding");
        if ((null != code) && code.equals("gzip")) {
            GZIPInputStream gis = new GZIPInputStream(is);
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            OutputStream os = new FileOutputStream(filename);
            // 开始读取
            while ((len = gis.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            gis.close();
            os.close();
            is.close();
        } else {
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            OutputStream os = new FileOutputStream(filename);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();
        }
    }
//
//    public static void main(String[] args) {
//        try {
//            download("http://220.228.174.200:18888/sport/team/RB莱比锡.png", "D:\\download/sport/team/RB莱比锡.png");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
