package cn.zifangsky.spider.stock;

import cn.zifangsky.model.StockInfo;
import cn.zifangsky.spider.UserAgentUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * @author shenjiachun
 * @create 2019/4/17
 * @description
 */
public class StockListSpider implements PageProcessor {

    DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM/dd");

    @Override
    public void process(Page page) {

        if (page.getUrl().regex("http://www.bestopview.com").match()) {

            List<String> stringList = page.getHtml().xpath("/html/body/div/ul/li/a/@href").regex("\\s*(\\d[\\.\\d]*\\d)").all();

            stringList.stream().forEach(
                    item -> {

                        String url = "https://basic.10jqka.com.cn/mobile/" + item + "/pubn.html";

                        page.addTargetRequest(url);

                    }
            );
        }
//https://data.hexin.cn/financial/cb/?origin=yjgg
        if (page.getUrl().regex("basic.10jqka.com").match()) {

            if (page.getHtml().xpath("//div[@class='remind-item-content expand']/span/text()").match()) {

                String type = page.getHtml().xpath("//div[@class='remind-item-content expand']/span/text()").toString();

                String text = page.getHtml().xpath("//div[@class='remind-item-content-detail globalBg']/text()").toString();

                String year = page.getHtml().xpath("//div[@class='remind-date textAss']/text()").toString();

                String month = page.getHtml().xpath("//div[@class='remind-date textMain']/text()").toString();

                String code = page.getUrl().replace("basic.10jqka.com.cn/mobile", "").regex("\\s*(\\d[\\.\\d]*\\d)").toString();

                DateTime dateTime = DateTime.parse(year + "-" + month, format);

                StockInfo stockInfo = StockInfo.builder().code(code).zqDate(dateTime.toDate())
                        .ztText(text).ztType(type).build();

                page.putField("result", stockInfo);


            }


        }


    }

    @Override
    public Site getSite() {

        Site site = Site.me().setTimeOut(6000).setRetryTimes(3)
                .setSleepTime(1000).setCharset("UTF-8").addHeader("Accept-Encoding", "/")
                .setUserAgent(UserAgentUtils.radomUserAgent());

        return site;
    }
}
