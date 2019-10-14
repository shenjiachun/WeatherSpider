package cn.zifangsky.manager.impl;

import cn.zifangsky.common.DateFormatUtil;
import cn.zifangsky.common.GlobalConstant;
import cn.zifangsky.manager.DealStockInfoService;
import cn.zifangsky.mapper.StockCodeInfoMapper;
import cn.zifangsky.mapper.StockInfoMapper;
import cn.zifangsky.mapper.ZqInfoMapper;
import cn.zifangsky.model.StockCodeInfo;
import cn.zifangsky.model.StockInfo;
import cn.zifangsky.model.ZqInfo;
import com.google.common.base.Splitter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shenjiachun
 * @create 2019/5/15
 * @description
 */
@Slf4j
@Service
public class DealStockInfoServiceImpl implements DealStockInfoService {

    @Autowired
    StockInfoMapper stockInfoMapper;

    @Autowired
    ExecutorService executorService;

    @Autowired
    StockCodeInfoMapper stockCodeInfoMapper;

    @Autowired
    ZqInfoMapper zqInfoMapper;

    @Override
    public void deal() {

        //查询数据
        List<StockInfo> list = stockInfoMapper.selectStockInfos();


//        list.stream().forEach(
//                stockInfo ->
//                        executorService.submit(
//                                () -> {
//                                    runData(stockInfo);
//                                }
//                        )
//
//        );


        list.stream().forEach(
                stockInfo -> {
                    runData(stockInfo);

                }

        );


    }


    public void runData(StockInfo stockInfo) {
        try {
            String ztType = stockInfo.getZtType();


            if (stockInfo.getZtText().indexOf("涨")>=0) {

                ZqInfo zqInfo = getZqInfo(stockInfo, GlobalConstant.ZQEnum.ZT);

                log.info("插入涨停数据:{}", zqInfo);
                zqInfoMapper.insertSelective(zqInfo);


            } else if (stockInfo.getZtText().indexOf("跌")>=0) {

                ZqInfo zqInfo = getZqInfo(stockInfo, GlobalConstant.ZQEnum.YD);
                log.info("插入跌停数据:{}", zqInfo);
                zqInfoMapper.insertSelective(zqInfo);

            } else {

            }

            stockInfo.setIsDeal(1);
            log.info("更新状态:{}", stockInfo);
            stockInfoMapper.updateByPrimaryKeySelective(stockInfo);
        } catch (Exception e) {
            log.error("股票{}.处理数据异常:", stockInfo, e);
        }
    }


    private ZqInfo getZqInfo(StockInfo stockInfo, GlobalConstant.ZQEnum zqEnum) {

        String ztText = stockInfo.getZtText();

        List<String> list = Splitter.on("原因或为：").trimResults().splitToList(ztText);

        String desc = list.get(1);

        StockCodeInfo stockCodeInfo = stockCodeInfoMapper.selectByCode(stockInfo.getCode());

        ZqInfo build = ZqInfo.builder().result(desc.replaceAll("\\(本信息由问财机器人通过网上已披露信息自动生成，不建议作为交易依据\\)", ""))
                .zqDate(stockInfo.getZqDate())
                .name(stockCodeInfo == null ? "" : stockCodeInfo.getName())
                .code(stockInfo.getCode()).ztType(zqEnum.getCode())
                .build();

        if (zqEnum == GlobalConstant.ZQEnum.ZT) {


            String dateToStr = DateFormatUtil.formatDateToStrYMD(stockInfo.getZqDate());

            String str = list.get(0);

            List<String> list1 = Splitter.on(":").trimResults().splitToList(str);

            String a1 = list1.get(0);

            String s2 = list1.get(1);

            Pattern p = Pattern.compile("\\d+");

            Matcher m = p.matcher(a1);
            while (m.find()) {
                String group = m.group();

                dateToStr = dateToStr + " " + group + ":";
            }

            m = p.matcher(s2);
            while (m.find()) {
                String group = m.group();

                dateToStr = dateToStr + group + ":00";

            }
            Date date = DateFormatUtil.parseStrToDate(dateToStr);

            build.setZqDate(date);

        }


        return build;
    }


}
