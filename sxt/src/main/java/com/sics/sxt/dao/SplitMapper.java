package com.sics.sxt.dao;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sics.sxt.pojo.dto.LaCessionUploadSplit;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @Auth:GuangYun
 * */
@Mapper
@Repository
public interface SplitMapper extends BaseMapper<LaCessionUploadSplit> {



    @Select("select t.life1_id \n" +
            "from G_SPLIT_00NB_UPLOAD t ")
    List<String> findLife1IDList();

    void insertBatchDataByLifeId(@Param("tableName") String tableName, List<String> batchList);

    @Update("CREATE TABLE ${tableName} as \n" +
            "select * from group_cession_upload_mid where 1=2")
    void createUploadSplitTable(@Param("tableName") String tableName);

    @Select("select TABLE_NAME  from all_tables where \n" +
            "TABLE_NAME LIKE 'G_SPLIT_%'" +
            " and \n" +
            " OWNER=#{tableOwner,jdbcType=VARCHAR}")
    List<String> findAllSplitTablesAlreadyCreated(String tableOwner);



    @Insert("insert into ${tableName} select t.* " +
            "from group_cession_upload_mid t where t.trans_code in('NB','RR') " +
            "and (t.Life1_Id is not null  and t.benefit_covered is not null and t.policy_type is not null  and t.insurance_product_cedent_code is not null )")
    void insertNBSplitTable(@Param("tableName") String tableName);

    @Update("CREATE TABLE ${tableName} as \n" +
            " select * from group_cession_claim_mid where 1=2")
    void createClaimSplitTable(@Param("tableName") String tableName);


    void insertBatchDataByUploadObjID(@Param("tableName") String tableName,List<Integer> batchList);


    void insertBatchDataByClaimObjID(@Param("tableName") String tableName, List<Integer> batchList);

    @Insert("insert into GROUP_ALL_TRANSACTION  " +
            "select * from (\n" +
            "        select  t.life1_id LIFE_ID,t.benefit_covered bc,t.policy_number,t.policy_type bp,\n" +
            "        t.insurance_product_cedent_code ip,t.trans_code,t.bussno,t.tran_date,t.object_id\n" +
            "                from sics_data.group_cession_upload_mid t where t.trans_code not in('NB','RR')\n" +
            "    )\n" +
            "union all\n" +
            "select * from (\n" +
            "        select c.insuredno LIFE_ID,c.CLAIM_BENEFIT_COVER bc,c.contno policy_number,c.finriskcode bp,\n" +
            "        c.cvgid ip,'Claim' trans_code ,'' as bussno, c.endcasedate tran_date,c.object_id\n" +
            "                from sics_data.group_cession_claim_mid c\n" +
            "        )")
    void insertAllTransactions();

    @Insert("insert into GROUP_ALL_TRANSACTION  " +
            "select * from (\n" +
            "            select * from (\n" +
            "            select  t.life1_id LIFE_ID,t.benefit_covered bc,t.policy_number,t.policy_type bp,\n" +
            "            t.insurance_product_cedent_code ip,t.trans_code,t.bussno,t.tran_date,t.object_id\n" +
            "                    from sics_data.group_cession_upload_mid t where t.trans_code not in('NB','RR')\n" +
            "    order by t.life1_id,t.benefit_covered,t.policy_number,t.policy_type,t.insurance_product_cedent_code,t.tran_date,t.bussno,t.trans_code DESC\n" +
            "        )\n" +
            "    union all\n" +
            "    select * from (\n" +
            "            select c.insuredno LIFE_ID,c.CLAIM_BENEFIT_COVER bc,c.contno policy_number,c.finriskcode bp,\n" +
            "            c.cvgid ip,'Claim' trans_code ,'' as bussno, c.endcasedate tran_date,c.object_id\n" +
            "                    from sics_data.group_cession_claim_mid c\n" +
            "                    order by c.insuredno,c.CLAIM_BENEFIT_COVER,c.contno,c.finriskcode,c.cvgid,c.endcasedate\n" +
            "            )  \n" +
            ") t1  order by t1.LIFE_ID,t1.bc,t1.tran_date,t1.policy_number,t1.bp,t1.ip,t1.bussno,t1.trans_code DESC")
    void insertAllTransactionsOld();



    /**
     * split Rpo
     * */
    @Select("select min(a.partial_date_yea)||'-01-01' left,a.btyear01||'-12-31' right,TO_CHAR(max(a.lowsum)) num \n" +
            "from sicslife_ceded1.lifeId_partial_date a group by a.btyear01 order by a.btyear01")
    List<Map<String,String>> selectSplitRpo();
    @DS("HA_DATA_PROD1")
    void splitRpoInit();

    @DS("HA_DATA_PROD1")
    void splitRpoUpdate(Integer i);


}
