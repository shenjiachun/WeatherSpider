package cn.zifangsky.spider.stock;

import cn.zifangsky.config.SpringContext;
import cn.zifangsky.model.StockCodeInfo;
import cn.zifangsky.model.StockInfo;
import cn.zifangsky.model.StockTopHolders;
import cn.zifangsky.model.StockYearReport;
import cn.zifangsky.spider.UserAgentUtils;
import cn.zifangsky.third.api.TushareApi;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Strings;
import org.joda.time.DateTime;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shenjiachun
 * @create 2019/4/17
 * @description
 */
@Slf4j
@Component
public class StockTopHoldersSpider implements PageProcessor {


    DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Override
    public void process(Page page) {

        if (page.getUrl().regex("http://www.baidu.com").match()) {


            TushareApi tushareApi = (TushareApi) SpringContext.getBean("tushareApi");
            List<StockCodeInfo> stocks = tushareApi.stocks();


            page.putField("stockCodeInfo", stocks);
//            Collections.reverse(stocks);


            stocks.stream().forEach(
                    item -> {

                        Request request = new Request("http://api.tushare.pro");

                        request.setMethod(HttpConstant.Method.POST);


                        JSONObject json = new JSONObject();
                        //接口名称
                        json.put("api_name", "top10_holders");

                        json.put("params", JSON.parse(String.format("{'ts_code':'%s'}", item.getTsCode())));

                        json.put("fields", "ts_code,ann_date,end_date,holder_name,hold_amount,hold_ratio");

                        json.put("token", "67b58c0deed2c8ffaf0c34429f776367112b6b22b3f695aada9148a5");

                        request.setRequestBody(HttpRequestBody.json(json.toJSONString(), "utf-8"));

                        page.addTargetRequest(request);
                    }
            );


        } else if (page.getUrl().regex("api.tushare.pro").match()) {

            Json json = page.getJson();
            JSONObject jsonObject = json.toObject(JSONObject.class);


            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("items");


   /*         0 = "ts_code"
            1 = "ann_date"
            2 = "end_date"
            3 = "holder_name"
            4 = "hold_amount"
            5 = "hold_ratio"*/


            Map<String, String> map = Maps.newHashMap();
            List<StockTopHolders> list = Lists.newArrayList();

            for (int i = 0; i < jsonArray.size(); i++) {

                JSONArray signalJsonArray = (JSONArray) jsonArray.get(i);
                String code = Splitter.on(".").splitToList(signalJsonArray.getString(0)).get(0);
                Date annDate = signalJsonArray.getDate(1);
                Date endDate = signalJsonArray.getDate(2);
                String holderName = signalJsonArray.getString(3);
                BigDecimal holdAmount = signalJsonArray.getBigDecimal(4);
                BigDecimal holdRatio = signalJsonArray.getBigDecimal(5);

                String key = code + annDate + endDate + holderName + holdAmount + holdRatio;

                StockTopHolders stockTopHolders = StockTopHolders.builder()
                        .code(code)
                        .annDate(annDate)
                        .endDate(endDate)
                        .holderName(holderName)
                        .holdAmount(holdAmount)
                        .holdRatio(holdRatio).build();

                if (!map.containsKey(key)) {
                    list.add(stockTopHolders);
                }

                map.put(key,key);

            }

            page.putField("stockTopHolders", list);

        }


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
