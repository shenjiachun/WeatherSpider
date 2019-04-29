package cn.zifangsky.mapper;

import cn.zifangsky.model.StockInfo;

import java.util.List;

public interface StockInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockInfo record);

    int batchInsert(List<StockInfo> list);

    int insertSelective(StockInfo record);

    StockInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockInfo record);

    int updateByPrimaryKey(StockInfo record);
}