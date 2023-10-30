package com.sics.sxt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sics.sxt.pojo.dto.RiskRulesMapping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Auth:GuangYun
 * */
@Mapper
@Repository
public interface RiskRulesMappingMapper extends BaseMapper<RiskRulesMapping>{


    @Select("SELECT DISTINCT\n" +
            " p.planno,\n" +
            " p.riskamntcalcode,\n" +
            " p.ruleid \n" +
            "FROM\n" +
            " group_cession_upload_mid t,\n" +
            " riskrulesmapping p \n" +
            "WHERE\n" +
            " replace(t.PLANNO, 'del', '') = p.planno ")
    List<RiskRulesMapping> selectCurrentRisk();
    @Update("update group_cession_upload_mid a set a.SUM_AT_RISK = ROUND(${ruleId}) where replace(a.PLANNO, 'del', '') = #{planNo} \n" +
            "AND a.TRANS_CODE not in ('ZT','LC_ZT','CT')")
    int updateSumAtRisk(RiskRulesMapping riskRulesMapping);

}
