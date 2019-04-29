package cn.zifangsky.mapper;

import cn.zifangsky.model.StockYearReport;

import java.util.List;

public interface StockYearReportMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockYearReport record);

    int batchInsert(List<StockYearReport> list);

    int insertSelective(StockYearReport record);

    StockYearReport selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockYearReport record);

    int updateByPrimaryKey(StockYearReport record);
}