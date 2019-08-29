package com.zdpang.template.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zdpang.template.model.GoodsInfo;
import com.zdpang.template.util.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SpiderService {
    @Autowired
    private GoodsInfoService goodsInfoService;
    private static String HTTPS_PROTOCOL = "https:";
    
    public void spiderData(String url, Map<String, String> params) {
        String html = HttpClientUtils.sendGet(url, null, params);
        if(!StringUtils.isBlank(html)) {
            List<GoodsInfo> goodsInfoList =parseHtml(html);
            goodsInfoService.saveBatch(goodsInfoList);
        }
    }
    /**
     * 解析html
     * @param html
     */
    private List<GoodsInfo> parseHtml(String html) {
        //商品集合
        List<GoodsInfo> goods = Lists.newArrayList();
        log.info(html);
        /**
         * 获取dom并解析
         */
        Document document = Jsoup.parse(html);
         Elements elements = document.
                select("ul[class=gl-warp clearfix]").select("li[class=gl-item]");
        int index = 0;
        for(Element element : elements) {
            String goodsId = element.attr("data-sku");
            String goodsName = element.select("div[class=p-name p-name-type-2]").select("em").text();
            String goodsPrice = element.select("div[class=p-price]").select("strong").select("i").text();
            String imgUrl = HTTPS_PROTOCOL + element.select("div[class=p-img]").select("a").select("img").attr("src");
            GoodsInfo goodsInfo = new GoodsInfo(goodsId, goodsName, imgUrl, goodsPrice);
            goods.add(goodsInfo);
            String jsonStr = JSON.toJSONString(goodsInfo);
            log.info("成功爬取【" + goodsName + "】的基本信息 ");
            log.info(jsonStr);
            if(index ++ == 9) {
                break;
            }
        }
        return goods;
    }
}