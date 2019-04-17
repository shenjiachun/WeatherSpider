package cn.zifangsky.spider.stock;

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
public class StockPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {

    }
}
