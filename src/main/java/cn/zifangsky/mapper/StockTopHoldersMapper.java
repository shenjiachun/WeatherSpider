package cn.zifangsky.mapper;

import cn.zifangsky.model.StockTopHolders;

import java.util.List;

public interface StockTopHoldersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockTopHolders record);

    int insertSelective(StockTopHolders record);

    StockTopHolders selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockTopHolders record);

    int updateByPrimaryKey(StockTopHolders record);


    int batchInsert(List<StockTopHolders> list);
}