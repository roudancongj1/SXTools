package com.sics.sxt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sics.sxt.pojo.dto.GTPlanMapping;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;



/**
 * @Auth:GuangYun
 * */
@Mapper
@Repository
public interface GTPlanMappingMapper extends BaseMapper<GTPlanMapping>{

    @Update("CREATE TABLE \"GTPLANMAPPING\" (\n" +
            "  \"RECEPTNO\" VARCHAR2(50 BYTE) NOT NULL,\n" +
            "  \"BP\" VARCHAR2(50 BYTE),\n" +
            "  \"IP\" VARCHAR2(50 BYTE),\n" +
            "  \"STATUS\" VARCHAR2(5 BYTE),\n" +
            "  \"OPERATOR\" VARCHAR2(50 BYTE),\n" +
            "  \"RICONTNO\" VARCHAR2(50 BYTE),\n" +
            "  \"SPECIALNAME\" VARCHAR2(50 BYTE),\n" +
            "  \"PREVALIDATE\" DATE,\n" +
            "  \"PREINVALIDATE\" DATE,\n" +
            "  \"MODIFYDATE\" DATE,\n" +
            "  \"LEFTVALUE\" VARCHAR2(2000 BYTE),\n" +
            "  \"RIGHTVALUE\" VARCHAR2(2000 BYTE),\n" +
            "  \"ALLVALUE\" VARCHAR2(4000 BYTE)\n" +
            ")\n" +
            "LOGGING\n" +
            "NOCOMPRESS\n" +
            "PCTFREE 10\n" +
            "INITRANS 1\n" +
            "STORAGE (\n" +
            "  INITIAL 65536 \n" +
            "  NEXT 1048576 \n" +
            "  MINEXTENTS 1\n" +
            "  MAXEXTENTS 2147483645\n" +
            "  BUFFER_POOL DEFAULT\n" +
            ")\n" +
            "PARALLEL 1\n" +
            "NOCACHE\n" +
            "DISABLE ROW MOVEMENT")
    int createMappingTable();

    @Insert("INSERT into GTPLANMAPPING (RECEPTNO,SPECIALNAME,PREVALIDATE,PREINVALIDATE,ALLVALUE,STATUS)\n" +
            "SELECT a.PLANNO,a.CALCODE,a.PREVALIDATE,a.PREINVALIDATE,a.CALVALUE||a.CALVALUE_TWO,'0' FROM SRULESMAPPING a")
    int insertSRulesMapping();


    @Insert("INSERT into GTPLANMAPPING (RECEPTNO,LEFTVALUE,RIGHTVALUE,PREVALIDATE,PREINVALIDATE,STATUS)\n" +
            "SELECT a.planno,NVL2(a.GRPCONTNO,'GROUP_SCHEMA_NUMBER||',NULL)||NVL2(a.CONTNO,'POLICY_NUMBER||',NULL)||NVL2(a.FINRISKCODE,'POLICY_TYPE||',NULL)||NVL2(a.cvgid,'INSURANCE_PRODUCT_CEDENT_CODE||',NULL),a.GRPCONTNO||a.CONTNO||a.FINRISKCODE||a.cvgid,a.PREVALIDATE,a.PREINVALIDATE,'1'\n" +
            " FROM (select p.* from planmapping_his p where p.planno not in(select t.planno from planmapping_his t where trim(t.grpcontno) is null and trim(t.CONTNO) is null and trim(t.finriskcode) is not null and trim(t.cvgid) is not null)) a")
    int insertHisNotTreatyMapping();

    @Insert("INSERT into GTPLANMAPPING (RECEPTNO,LEFTVALUE,RIGHTVALUE,PREVALIDATE,PREINVALIDATE,STATUS)\n" +
            "SELECT a.planno,NVL2(a.FINRISKCODE,'POLICY_TYPE||',NULL)||NVL2(a.cvgid,'INSURANCE_PRODUCT_CEDENT_CODE||',NULL),a.FINRISKCODE||a.cvgid,a.PREVALIDATE,a.PREINVALIDATE,'3'\n" +
            " FROM (select * from planmapping_his ) a")
    int insertHisTreatyMapping();

    @Insert("INSERT into GTPLANMAPPING (RECEPTNO,ALLVALUE,OPERATOR,PREVALIDATE,PREINVALIDATE,STATUS)\n" +
            "SELECT a.RIPRECEPTNO, LISTAGG('AND '||(CASE a.RECOMCODE\n" +
            "  WHEN 'GrpContNo' THEN 'GROUP_SCHEMA_NUMBER'\n" +
            "  WHEN 'ContNo' THEN 'POLICY_NUMBER'\n" +
            "  WHEN 'RiskCode' THEN 'RISKCODE'\n" +
            "  WHEN 'CvgID' THEN 'INSURANCE_PRODUCT_CEDENT_CODE'\n" +
            "  ELSE\n" +
            "    ''\n" +
            "END)||'='||''''||a.RELACODE||'''', ' ') WITHIN GROUP (ORDER BY a.RECOMCODE),a.OPERATOR,a.PREVALIDATE,a.PREINVALIDATE,'2'\n" +
            "FROM NEWPLANMAPPING a\n" +
            "GROUP BY a.RIPRECEPTNO,a.OPERATOR,a.PREVALIDATE,a.PREINVALIDATE\n")
    int insertNewTreatyMapping();
    @Insert("INSERT into GTPLANMAPPING (RECEPTNO,OPERATOR,PREVALIDATE,PREINVALIDATE,BP,IP,STATUS) SELECT a.RIPRECEPTNO,a.OPERATOR,a.PREINVALIDATE,a.PREVALIDATE,a.BP,a.IP,'4' FROM planchangmapping a")
    int insertPlanChangeMapping();
    int updatePlanNo(GTPlanMapping gtPlanMapping);



}
