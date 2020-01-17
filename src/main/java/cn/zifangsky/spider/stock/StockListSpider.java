package cn.zifangsky.spider.stock;

import cn.zifangsky.config.SpringContext;
import cn.zifangsky.model.StockCodeInfo;
import cn.zifangsky.model.StockInfo;
import cn.zifangsky.model.StockYearReport;
import cn.zifangsky.spider.UserAgentUtils;
import cn.zifangsky.third.api.TushareApi;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shenjiachun
 * @create 2019/4/17
 * @description
 */
@Slf4j
@Component
public class StockListSpider implements PageProcessor {


    DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Override
    public void process(Page page) {

        if (page.getUrl().regex("http://www.bestopview.com").match()) {

            List<String> stringList = page.getHtml().xpath("/html/body/div/ul/li/a/@href").regex("\\s*(\\d[\\.\\d]*\\d)").all();
            log.info("代码:{}", stringList);

            TushareApi tushareApi = (TushareApi) SpringContext.getBean("tushareApi");
            List<StockCodeInfo> stocks = tushareApi.stocks();

            Collections.reverse(stocks);

            stocks.stream().forEach(
                    item -> {

                        log.info("股票信息:{}", item);

                        String url = "https://basic.10jqka.com.cn/mobile/" + item.getCode() + "/pubn.html";

                        page.addTargetRequest(url);
                    }
            );

        }
//https://data.hexin.cn/financial/cb/?origin=yjgg
        if (page.getUrl().regex("basic.10jqka.com").match()) {


            try {
//                CRFLexicalAnalyzer analyzer = new CRFLexicalAnalyzer();
//
//                analyzer.enableMultithreading(true);


                List<String> titles = page.getHtml().xpath("//div[@class='remind-item-title textMain']/text()").all();

                List<String> contents = page.getHtml().xpath("//div[@class='remind-item-content']/span[1]/text()").all();

                List<String> dates = page.getHtml().xpath("//div[contains(@class,'remind-item lineLightBd clearfix')]/@date").all();

                String code = page.getUrl().replace("basic.10jqka.com.cn/mobile", "").regex("\\s*(\\d[\\.\\d]*\\d)").toString();

                List<StockInfo> list = Lists.newArrayList();

                List<StockYearReport> stockYearReportList = Lists.newArrayList();

                for (int i = 0; i < titles.size(); i++) {

//                    analyzer.analyze("2019年一季报每股收益0.15元净利润5281.18万元同比去年增长27.25%");

                    DateTime dateTime = DateTime.parse(dates.get(i), format);
                    String trim = titles.get(i).replaceAll(" ", "").trim();
/*                    2019年一季报每股收益/nz 0.26/m 元/q 净利润/nz 1.93亿/m 元/q 同比去年增长/nz -86.47%/m
                    2018年年报每股收益/nz 0.58/m 元/q ，/w 净利润/nz 1.4亿/m 元/q ，/w 同比去年增长/nz 14.50%/m*/
//                    Sentence sentence = analyzer.analyze(contents.get(i));

//                    log.info("分词结果:{}", sentence);


//                    if (contents.get(i).indexOf("2019年一季报每股收益") >= 0
//                            || contents.get(i).indexOf("2018年年报每股收益") >= 0) {
//
//                        String filetext = contents.get(i).replaceAll("，", "");
//
//                        Pattern p = Pattern.compile("(?<=2019年一季报每股收益).*?(?=元净利润)");
//
//                        Pattern p1 = Pattern.compile("(?<=元净利润).*?(?=元)");
//
//                        Pattern p2 = Pattern.compile("(?<=同比去年增长).*?(?=%)");
//
//                        Matcher m = p.matcher(filetext);
//
//                        Matcher m1 = p1.matcher(filetext);
//
//                        Matcher m2 = p2.matcher(filetext);
//
//                        BigDecimal mgsy = BigDecimal.ZERO;
//                        BigDecimal jlr = BigDecimal.ZERO;
//                        BigDecimal zzl = BigDecimal.ZERO;
//
//
//                        if (m.find()) {
//                            String group = m.group();
//                            mgsy = new BigDecimal(group);
//                        }
//
//                        if (m1.find()) {
//                            String group = m1.group();
//                            if (group.indexOf("万") > 0) {
//                                jlr = new BigDecimal(group.replaceAll("万", "")).multiply(new BigDecimal(10000));
//                            } else if (group.indexOf("亿") > 0) {
//                                jlr = new BigDecimal(group.replaceAll("亿", "")).multiply(new BigDecimal(100000000));
//                            }
//                        }
//
//                        if (m2.find()) {
//                            String group = m2.group();
//                            if (!Strings.isNullOrEmpty(group)) {
//
//                                zzl = new BigDecimal(group);
//                            }
//                        }
//
//
//                        StockYearReport stockYearReport = StockYearReport.builder()
//                                .code(code).mgsy(mgsy).content(contents.get(i))
//                                .zzl(zzl)
//                                .jlr(jlr).reportDate(dateTime.toDate()).build();
//
//                        if (contents.get(i).indexOf("2019年一季报每股收益") >= 0) {
//                            stockYearReport.setType("1");
//                        } else if (contents.get(i).indexOf("2018年年报每股收益") >= 0) {
//                            stockYearReport.setType("6");
//                        } else if (contents.get(i).indexOf("2019年二季报每股收益") >= 0) {
//                            stockYearReport.setType("2");
//                        } else if (contents.get(i).indexOf("2019年三季报每股收益") >= 0) {
//                            stockYearReport.setType("3");
//                        } else if (contents.get(i).indexOf("2019年四季报每股收益") >= 0) {
//                            stockYearReport.setType("4");
//                        }
//
//                        stockYearReportList.add(stockYearReport);
//                        log.info("需要处理的stockYearReport信息：{}", stockYearReport);
//
//                    }

//                    List<Term> segment = NLPTokenizer.segment(contents.get(i).replaceAll("：", "")
//                            .replaceAll("；", "").replaceAll("，", ""));
//
//                    log.info("分词内容：{}", segment);
//
//                    String desc = "";
//                    if (segment.size() >= 3) {
//                        desc = segment.get(segment.size() - 3).word + segment.get(segment.size() - 2).word + segment.get(segment.size() - 1).word;
//                    } else {
//                        desc = contents.get(i);
//                    }

                    if (trim.indexOf("涨停")>=0 || trim.indexOf("异动")>=0) {
                        StockInfo stockInfo = StockInfo.builder().code(code).zqDate(dateTime.toDate()).ztdesc(contents.get(i))
                            .ztText(contents.get(i)).ztType(trim).build();

                        log.info("需要处理的信息：{}", stockInfo);

                        list.add(stockInfo);
                    }

                }
                page.putField("result", list);
                page.putField("report", stockYearReportList);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }

    @Override
    public Site getSite() {

        Site site = Site.me().setTimeOut(6000).setRetryTimes(10)
//                .setSleepTime(1000)
                .setCharset("UTF-8").addHeader("Accept-Encoding", "/")
                .setUserAgent(UserAgentUtils.radomUserAgent());

        return site;
    }


    public static void main(String[] args) {
        String filetext = "2019年一季报每股收益0.26元净利润1.93亿元同比去年增长-86.47%";
        Pattern p = Pattern.compile("(?<=2019年一季报每股收益).*?(?=元净利润)");
        Pattern p1 = Pattern.compile("(?<=元净利润).*?(?=元)");
        Pattern p2 = Pattern.compile("(?<=同比去年增长).*?(?=%)");
        Matcher m = p.matcher(filetext);
        Matcher m1 = p1.matcher(filetext);
        Matcher m2 = p2.matcher(filetext);

        if (m.find()) {
            String group = m.group();
        }

        if (m1.find()) {
            String group = m1.group();
        }

        if (m2.find()) {
            String group = m2.group();
        }

    }
}
