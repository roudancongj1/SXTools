package com.sics.sxt.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface CommonMapper extends BaseMapper<Map<String,Object>>{

    //@DS("HA_DATA_UAT")
    @Select("SELECT * FROM ${tableOwner}.${tableName}")
    List<Map<String,Object>> selectByTable(@Param("tableOwner") String tableOwner,@Param("tableName") String tableName);

    /**
     *  Strong matching
     * */
    @Select("select t.TABLE_NAME from all_tab_comments t where t.owner = #{tableOwner} and t.TABLE_NAME = #{tableName}")
    List<String> tableIsExist(@Param("tableOwner") String tableOwner,@Param("tableName") String tableName);

    @Update("drop table ${tableOwner}.${tableName}")
    void dropTable(@Param("tableOwner") String tableOwner,@Param("tableName") String tableName);

    @Update("truncate table ${tableOwner}.${tableName}")
    void truncateTable(@Param("tableOwner") String tableOwner,@Param("tableName") String tableName);
    @Select("SELECT COUNT(1) FROM ${tableOwner}.${tableName}")
    int selectTableCount(@Param("tableOwner") String tableOwner,@Param("tableName") String tableName);

    @Update("create table ${CTableOwner}.${CTableName} as select t.*,'' errmsg FROM ${PTableOwner}.${PTableName} t where 1=2")
    int copyErrTable(@Param("PTableOwner") String PTableOwner,@Param("PTableName") String PTableName,@Param("CTableOwner") String CTableOwner,@Param("CTableName") String CTableName);

    @Update("create table ${CTableOwner}.${CTableName} as select * FROM ${PTableOwner}.${PTableName}  where 1=2")
    int copyTable(@Param("PTableOwner") String PTableOwner,@Param("PTableName") String PTableName,@Param("CTableOwner") String CTableOwner,@Param("CTableName") String CTableName);


    @Update("create table wwwwww as select * FROM ${tableOwner}.${tableName}")
    int cr(@Param("tableOwner") String tableOwner,@Param("tableName") String tableName);
}
