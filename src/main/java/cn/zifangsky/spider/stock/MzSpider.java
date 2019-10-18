package cn.zifangsky.spider.stock;

import cn.zifangsky.config.SpringContext;
import cn.zifangsky.model.StockCodeInfo;
import cn.zifangsky.model.StockTopHolders;
import cn.zifangsky.spider.UserAgentUtils;
import cn.zifangsky.third.api.TushareApi;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.utils.HttpConstant;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author shenjiachun
 * @create 2019/4/17
 * @description
 */
@Slf4j
@Component
public class MzSpider implements PageProcessor {


    DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Override
    public void process(Page page) {

        List<String> titles = page.getHtml().xpath("//ul[@class='subcatList clear']/li/div/h3/a/text()").all();
    }

    @Override
    public Site getSite() {

        Site site = Site.me().setTimeOut(6000).setRetryTimes(10)
                .setSleepTime(1000)
                .setCharset("UTF-8").addHeader("Accept-Encoding", "/")
                .setUserAgent(UserAgentUtils.radomUserAgent());

        return site;
    }

}
