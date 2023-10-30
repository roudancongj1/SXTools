package com.sics.sxt.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sics.sxt.pojo.dto.GroupCessionClaimMid;
import com.sics.sxt.pojo.dto.GroupCessionUploadMid;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


/**
 * @Auth:GuangYun
 * */
@Mapper
@Repository
public interface GroupCessionClaimMidMapper extends BaseMapper<GroupCessionClaimMid> {

    @Select("SELECT * FROM ${tableName}")
    Page<GroupCessionClaimMid> selectByTable(Page<GroupCessionUploadMid> page, @Param("tableName") String tableName);

}
