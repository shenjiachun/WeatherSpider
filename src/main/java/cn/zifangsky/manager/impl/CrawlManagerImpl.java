package cn.zifangsky.manager.impl;

import javax.annotation.Resource;

import cn.zifangsky.manager.ProxyIpManager;
import cn.zifangsky.model.ProxyIp;
import cn.zifangsky.spider.stock.StockListSpider;
import cn.zifangsky.spider.stock.StockPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zifangsky.manager.CrawlManager;
import cn.zifangsky.spider.CustomPipeline;
import cn.zifangsky.spider.ProxyIPPipeline;
import cn.zifangsky.spider.ProxyIPSpider;
import cn.zifangsky.spider.ProxyIPSpider2;
import cn.zifangsky.spider.WeatherSpider;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

@Service("crawlManager")
public class CrawlManagerImpl implements CrawlManager {

    @Resource(name = "customPipeline")
    private CustomPipeline customPipeline;

    @Resource(name = "proxyIPPipeline")
    private ProxyIPPipeline proxyIPPipeline;

    @Resource(name = "stockPipeline")
    StockPipeline stockPipeline;

    @Override
    public void weatherCrawl(String stationCode) {
        OOSpider.create(new WeatherSpider()).addPipeline(customPipeline)
                .addUrl("http://www.weather.com.cn/weather/" + stationCode + ".shtml")
                .thread(1)
                .run();
    }

    @Override
    public void proxyIPCrawl() {
        OOSpider.create(new ProxyIPSpider())
                .addUrl("http://www.xicidaili.com/nn/1").addPipeline(proxyIPPipeline)
                .thread(3)
                .run();
    }

    @Override
    public void proxyIPCrawl2() {
        OOSpider.create(new ProxyIPSpider2())
                .addUrl("http://www.kuaidaili.com/free/inha/1/").addPipeline(proxyIPPipeline)
                .thread(2)
                .run();
    }


    @Autowired
    ProxyIpManager proxyIpManager;

    @Override
    public void stockList() {
//        OOSpider.create(new StockListSpider())
//                .addUrl("https://basic.10jqka.com.cn/mobile/300540/pubn.html").addPipeline(stockPipeline)
//                .addUrl("http://www.bestopview.com/stocklist.html").addPipeline(stockPipeline)
//
//                .thread(2).run();


        Spider spider =  OOSpider.create(new StockListSpider())
//                .addUrl("https://basic.10jqka.com.cn/mobile/600071/pubn.html").addPipeline(stockPipeline)
                .addUrl("http://www.bestopview.com/stocklist.html")
                .addPipeline(stockPipeline)
                .thread(6);


//        HttpClientDownloader downloader = new HttpClientDownloader(){
//            @Override
//            protected void onError(Request request) {
//
//                ProxyIp proxyIp = proxyIpManager.selectRandomIP();
//
//                setProxyProvider(SimpleProxyProvider.from(new Proxy(proxyIp.getIp(), proxyIp.getPort())));
//            }
//        };
//        spider.setDownloader(downloader);

        spider.run();


    }




}
