package cn.zifangsky.mapper;

import cn.zifangsky.model.ZqInfo;

public interface ZqInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ZqInfo record);

    int insertSelective(ZqInfo record);

    ZqInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ZqInfo record);

    int updateByPrimaryKey(ZqInfo record);
}