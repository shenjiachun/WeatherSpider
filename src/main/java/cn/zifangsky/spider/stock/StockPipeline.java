package cn.zifangsky.spider.stock;

import cn.zifangsky.mapper.StockInfoMapper;
import cn.zifangsky.model.StockInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author shenjiachun
 * @create 2019/4/17
 * @description
 */
@Component("stockPipeline")
@Slf4j
public class StockPipeline implements Pipeline {


    @Autowired
    StockInfoMapper stockInfoMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {

        StockInfo stockInfo = resultItems.get("result");

        if (stockInfo!=null) {

            log.info("保存stockInfo:{}" ,stockInfo);

            stockInfoMapper.insertSelective(stockInfo);
        }

    }
}
