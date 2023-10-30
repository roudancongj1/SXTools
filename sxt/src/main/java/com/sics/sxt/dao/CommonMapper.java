package com.sics.sxt.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Auth:GuangYun
 * */
@Mapper
@Repository
public interface CommonMapper {

    //@DS("HA_DATA_UAT")
    @Select("SELECT * FROM ${tableOwner}.${tableName}")
    List<Map<String,Object>> selectByOwnerTable(@Param("tableOwner") String tableOwner,@Param("tableName") String tableName);

    @Select("SELECT * FROM ${tableName}")
    List<Map<String,Object>> selectByTable(@Param("tableName") String tableName);

    /**
     *  Strong matching
     * */
    @Select("select t.TABLE_NAME from all_tab_comments t where t.owner = #{tableOwner} and t.TABLE_NAME = #{tableName}")
    List<String> tableIsExist(@Param("tableOwner") String tableOwner,@Param("tableName") String tableName);

    @Update("drop table ${tableOwner}.${tableName}")
    void dropTable(@Param("tableOwner") String tableOwner,@Param("tableName") String tableName);

    @Update("truncate table ${tableOwner}.${tableName}")
    void truncateTable(@Param("tableOwner") String tableOwner,@Param("tableName") String tableName);

    //@DS("ORE_PR")
    @Select("SELECT COUNT(1) FROM ${tableOwner}.${tableName}")
    int selectOwnerTableCount(@Param("tableOwner") String tableOwner,@Param("tableName") String tableName);

    @Select("SELECT COUNT(1) FROM ${tableName}")
    int selectTableCount(@Param("tableName") String tableName);

    @Update("create table ${CTableOwner}.${CTableName} as select * FROM ${PTableOwner}.${PTableName}  where 1=2")
    int copyTable(@Param("PTableOwner") String PTableOwner,@Param("PTableName") String PTableName,@Param("CTableOwner") String CTableOwner,@Param("CTableName") String CTableName);

    int insertP(Map<String,Object> map);

    @DS("ROOT")
    @Select("{CALL get_business_data_return()}")
    List<String> selectcall();


}
