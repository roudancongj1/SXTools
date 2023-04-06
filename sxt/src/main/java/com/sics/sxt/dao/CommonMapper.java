package com.sics.sxt.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface CommonMapper extends BaseMapper<Map<String,Object>>{

    //@DS("HA_DATA_UAT")
    @Select("SELECT * FROM ${tableName}")
    List<Map<String,Object>> selectByTable(@Param("tableName") String tableName);
}
