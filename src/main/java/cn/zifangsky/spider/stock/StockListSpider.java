package cn.zifangsky.spider.stock;

import cn.zifangsky.spider.UserAgentUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author shenjiachun
 * @create 2019/4/17
 * @description
 */
public class StockListSpider implements PageProcessor {
    @Override
    public void process(Page page) {
//        page.getHtml().xpath().all();
    }

    @Override
    public Site getSite() {

        Site site = Site.me().setTimeOut(6000).setRetryTimes(3)
                .setSleepTime(1000).setCharset("UTF-8").addHeader("Accept-Encoding", "/")
                .setUserAgent(UserAgentUtils.radomUserAgent());

        return site;
    }
}
