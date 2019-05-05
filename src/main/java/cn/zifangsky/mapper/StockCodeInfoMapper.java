package cn.zifangsky.mapper;

import cn.zifangsky.model.StockCodeInfo;

import java.util.List;

public interface StockCodeInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockCodeInfo record);

    int insertSelective(StockCodeInfo record);

    StockCodeInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockCodeInfo record);

    int updateByPrimaryKey(StockCodeInfo record);

    int batchInsert(List<StockCodeInfo> list);


    void deleteStockCodeInfo();
}