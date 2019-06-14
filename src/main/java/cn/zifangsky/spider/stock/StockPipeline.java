package cn.zifangsky.spider.stock;

import cn.zifangsky.mapper.StockCodeInfoMapper;
import cn.zifangsky.mapper.StockInfoMapper;
import cn.zifangsky.mapper.StockTopHoldersMapper;
import cn.zifangsky.mapper.StockYearReportMapper;
import cn.zifangsky.model.StockCodeInfo;
import cn.zifangsky.model.StockInfo;
import cn.zifangsky.model.StockTopHolders;
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

    @Autowired
    StockTopHoldersMapper stockTopHoldersMapper;

    @Autowired
    StockCodeInfoMapper stockCodeInfoMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {

        List<StockInfo> list = resultItems.get("result");

        List<StockYearReport> reportList = resultItems.get("report");

        List<StockTopHolders> stockTopHolders = resultItems.get("stockTopHolders");

        List<StockCodeInfo> stockCodeInfos = resultItems.get("stockCodeInfo");

        if (list != null && list.size() > 0) {

            log.info("保存stockInfo:{}", list.size());
            try {


                stockInfoMapper.batchInsert(list);
            } catch (Exception e) {

                log.error("异常",e);
                list.stream().forEach(
                        stockInfo -> {
                            log.info("错误信息:{}",stockInfo);
                        }
                );
            }

        }

        if (!CollectionUtils.isEmpty(reportList)) {
            log.info("保存StockYearReport:{}", list.size());
            stockYearReportMapper.batchInsert(reportList);
        }

        if (stockTopHolders != null && stockTopHolders.size() > 0) {

            log.info("保存stockTopHolders:{}", stockTopHolders.size());
            stockTopHoldersMapper.batchInsert(stockTopHolders);
        }

        if (!CollectionUtils.isEmpty(stockCodeInfos)) {

            stockCodeInfoMapper.deleteStockCodeInfo();

            log.info("保存stockCodeInfos:{}", stockCodeInfos.size());

            stockCodeInfoMapper.batchInsert(stockCodeInfos);
        }


    }
}
