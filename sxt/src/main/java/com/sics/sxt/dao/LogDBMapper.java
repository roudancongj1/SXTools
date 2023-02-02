package com.sics.sxt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sics.sxt.pojo.po.LogDB;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LogDBMapper extends BaseMapper<LogDB> {

    @Select("adas")
    Object aa();
}
