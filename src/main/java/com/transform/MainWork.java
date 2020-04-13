package com.transform;

import com.transform.bean.ResultItemBean;
import com.transform.bean.ResultBean;
import com.transform.bean.UrlBean;
import com.transform.util.DownLoadUtil;
import com.transform.util.HttpGetUtil;
import lombok.SneakyThrows;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainWork {
    private static ExecutorService executor = Executors.newFixedThreadPool(32);

    private static int steps = 2000;
    private static int totalCount = 0;
    private static String startPath = "http://xxx:18888";
    private static String localPath = "D:\\download";

    public static void main(String[] args) {
        work("");
        executor.shutdown();
        System.out.println("执行结束");
    }

    public static void work(String url) {
        System.out.println("开始进行处理:" + startPath + url);
        UrlBean urlBean = new UrlBean();
        urlBean.setHost(startPath + url);
        urlBean.setLimit(steps);
        urlBean.setLastFileName("");
        ResultBean resultBean = null;

        List<String> imgList = new LinkedList<>();
        List<String> uriList = new LinkedList<>();
        while (true){
            resultBean = HttpGetUtil.get(urlBean.getUrl());
            urlBean.setLastFileName(resultBean.getLastFileName());
            List<ResultItemBean> list = resultBean.getEntries();
            if(CollectionUtils.isEmpty(list)){
                break;
            }
            list.forEach(s -> {
                if(s.isPath()){
                    uriList.add(s.getFullPath());
                }else {
                    imgList.add(s.getFullPath());
                }
            });
        }
        totalCount += imgList.size();
        System.out.println("获取文件数量:" + imgList.size());
        System.out.println("获取文件总数量:" + totalCount);
        if(! CollectionUtils.isEmpty(imgList)){
            /**
             * 排重并下载
             */
            imgList.forEach(s ->{
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                DownLoadUtil.download(startPath + s, localPath + s);
                            } catch (Exception e) {
                                System.out.println("文件拉取发生异常:" + s);
                            }
                        }
                    });
            });
        }
        /**
         * 递归处理
         */
        if(! CollectionUtils.isEmpty(uriList)){
            uriList.forEach(MainWork::work);
        }
    }
}
