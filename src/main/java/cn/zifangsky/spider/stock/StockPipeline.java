package cn.zifangsky.spider.stock;

import cn.zifangsky.mapper.StockInfoMapper;
import cn.zifangsky.mapper.StockYearReportMapper;
import cn.zifangsky.model.StockInfo;
import cn.zifangsky.model.StockYearReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * @author shenjiachun
 * @create 2019/4/17
 * @description 公告
 */
@Component("stockPipeline")
@Slf4j
public class StockPipeline implements Pipeline {


    @Autowired
    StockInfoMapper stockInfoMapper;

    @Autowired
    StockYearReportMapper stockYearReportMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {

        List<StockInfo> list = resultItems.get("result");
        List<StockYearReport> reportList = resultItems.get("report");

        if (list!=null&&list.size()>0) {

            log.info("保存stockInfo:{}" ,list.size());

            stockInfoMapper.batchInsert(list);
        }

        if (!CollectionUtils.isEmpty(reportList)) {
            log.info("保存StockYearReport:{}" ,list.size());
            stockYearReportMapper.batchInsert(reportList);
        }



    }
}
