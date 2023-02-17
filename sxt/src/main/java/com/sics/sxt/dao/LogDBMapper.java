package com.sics.sxt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sics.sxt.pojo.po.LogDB;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface LogDBMapper extends BaseMapper<LogDB> {

    @Select("select * from  business")
    List<Map<String,Object>> aa();
}
