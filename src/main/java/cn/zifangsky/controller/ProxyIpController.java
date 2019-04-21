package cn.zifangsky.controller;

import cn.zifangsky.manager.CrawlManager;
import cn.zifangsky.manager.ProxyIpManager;
import cn.zifangsky.manager.WeatherStationManager;
import cn.zifangsky.model.ProxyIp;
import cn.zifangsky.mq.producer.CheckIPSender;
import cn.zifangsky.mq.producer.WeatherUpdateSender;
import cn.zifangsky.task.ScheduledTasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.Format;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 代理IP相关的对外RESTful接口
 *
 * @author zifangsky
 * @date 2018/6/20
 * @since 1.0.0
 */
@RestController
@RequestMapping("/proxyIp")

public class ProxyIpController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyIpController.class);

    private final Format FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Value("${mq.topicName.checkIP}")
    private String checkIPTopicName;

    @Value("${mq.topicName.weather}")
    private String weatherTopicName;

    @Resource(name="proxyIpManager")
    private ProxyIpManager proxyIpManager;



    @Resource(name="crawlManager")
    private CrawlManager crawlManager;



    /**
     * 返回数据库中所有可用的代理IP
     * @author zifangsky
     * @date 2018/6/21 10:40
     * @since 1.0.0
     * @return java.util.List<cn.zifangsky.model.ProxyIp>
     */
    @RequestMapping(value = "/selectAll",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<ProxyIp> selectAll(){
        return proxyIpManager.selectAll();
    }

    /**
     * 从数据库中随机返回一个可用的代理IP
     * @author zifangsky
     * @date 2018/6/21 10:41
     * @since 1.0.0
     * @return cn.zifangsky.model.ProxyIp
     */
    @RequestMapping(value = "/selectRandomIP",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ProxyIp selectRandomIP(){
        return proxyIpManager.selectRandomIP();
    }


    /**
     * 代理IP定时获取任务1
     * @author zifangsky
     * @date 2018/6/21 13:53
     * @since 1.0.0
     */
    @RequestMapping(value = "/crawlProxyIpTask1",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void crawlProxyIpTask1(){
        Date current = new Date();
        LOGGER.debug(MessageFormat.format("开始执行代理IP定时获取任务1，Date：{0}",FORMAT.format(current)));

        crawlManager.proxyIPCrawl();
    }

    /**
     * 代理IP定时获取任务2
     * @author zifangsky
     * @date 2018/6/21 13:55
     * @since 1.0.0
     */
    @RequestMapping(value = "/crawlProxyIpTask2",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void crawlProxyIpTask2(){
        Date current = new Date();
        LOGGER.debug(MessageFormat.format("开始执行代理IP定时获取任务2，Date：{0}",FORMAT.format(current)));

        crawlManager.proxyIPCrawl2();
    }


    @RequestMapping(value = "/stockList",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void stockList(){
        Date current = new Date();
        LOGGER.debug(MessageFormat.format("开始执行stockList，Date：{0}",FORMAT.format(current)));

        crawlManager.stockList();
    }

}
