package com.sics.sxt.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sics.sxt.pojo.dto.GroupCessionUploadMid;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


/**
 * @Auth:GuangYun
 * */
@Mapper
@Repository
public interface GroupCessionUploadMidMapper extends BaseMapper<GroupCessionUploadMid>{

    @Select("SELECT * FROM ${tableName}")
    Page<GroupCessionUploadMid> selectByTable(Page<GroupCessionUploadMid> page ,@Param("tableName") String tableName);

    @DS("HA_DATA_PROD1")
    @Select("{CALL DELETEBATCHCLAIMBYID_G()}")
    void deleteClaim();

    @DS("HA_DATA_PROD1")
    @Select("{CALL DELETEBATCHCESSIONBYID_G()}")
    void deleteCession();

    @DS("HA_DATA_PROD1")
    @Update("create table TEMPLATE_GCLAIM_PLACEMENT_ACI as\n" +
            "SELECT a.Contno Policy_Number\n" +
            "     , a.rgtno\n" +
            "     , a.cvgid||a.insurednum CEDANT_CES_NBER\n" +
            "     , (\n" +
            "         CASE a.REINSURECOM\n" +
            "         WHEN 'CLRE01' THEN '70001'\n" +
            "         WHEN 'QHRE01' THEN '70007'\n" +
            "         WHEN 'HURE01' THEN '70000'\n" +
            "         WHEN 'MURE01' THEN '70005'\n" +
            "         WHEN 'TPRE01' THEN '70006'\n" +
            "         WHEN 'SWRE01' THEN '70003'\n" +
            "         WHEN 'GENE01' THEN '70004'\n" +
            "         WHEN 'SCRE01' THEN '70002'\n" +
            "         WHEN 'SCRE02' THEN '70009'\n" +
            "         WHEN 'RCRE01' THEN 'RCRE01'\n" +
            "         ELSE ''\n" +
            "           END\n" +
            "      ) AS rasnum\n" +
            "     , a.REINSURECOM\n" +
            "     , a.COMCLMPAY RetroACI\n" +
            " FROM sics_data.group_claim_rein A\n" +
            "       where  a.Contno||a.cvgid||a.insurednum||a.RGTNO in(select tmp.pol_no||substr(tmp.cedant_ces_nber,5)||tmp.BUSSNO from sicslife_ceded1.dx_grp_cession_del tmp)\n" +
            "union\n" +
            "SELECT a.Contno Policy_Number\n" +
            "     , a.rgtno\n" +
            "     , a.cvgid||a.insurednum CEDANT_CES_NBER\n" +
            "     , (\n" +
            "         CASE a.REINSURECOM\n" +
            "         WHEN 'CLRE01' THEN '70001'\n" +
            "         WHEN 'QHRE01' THEN '70007'\n" +
            "         WHEN 'HURE01' THEN '70000'\n" +
            "         WHEN 'MURE01' THEN '70005'\n" +
            "         WHEN 'TPRE01' THEN '70006'\n" +
            "         WHEN 'SWRE01' THEN '70003'\n" +
            "         WHEN 'GENE01' THEN '70004'\n" +
            "         WHEN 'SCRE01' THEN '70002'\n" +
            "         WHEN 'SCRE02' THEN '70009'\n" +
            "         WHEN 'RCRE01' THEN 'RCRE01'\n" +
            "         ELSE ''\n" +
            "           END\n" +
            "      ) AS rasnum\n" +
            "     , a.REINSURECOM\n" +
            "     , a.COMCLMPAY RetroACI\n" +
            "FROM sics_data.his_grp_claim_rein A  \n" +
            "         where   a.Contno||a.cvgid||a.insurednum||a.RGTNO in(select tmp.pol_no||substr(tmp.cedant_ces_nber,5)||tmp.BUSSNO from sicslife_ceded1.dx_grp_cession_del tmp)")
    void createTempClaim();

    @DS("HA_DATA_PROD1")
    @Select("{CALL Gimport_PLACEMENT_CLAIM()}")
    void tempToSicsByPlacementClaim();
    @DS("HA_DATA_PROD1")
    @Select("{CALL Gimport_RETRO_CLAIM()}")
    void tempToSicsByRetroClaim();

    @DS("HA_DATA_PROD1")
    @Update("create table TEMPLATE_GROUP_PLACEMENT_ACI as \n" +
            "    SELECT \n" +
            "      a.Trans_Effective_Date , \n" +
            "      a.policy_number contno, \n" +
            "      a.trans_code,\n" +
            "      a.policy_type||a.CVGID ||a.life1_id as sub_key,\n" +
            "      a.bussno transaction_number,\n" +
            "      a.SUMATRISK,\n" +
            "      a.rasnum,a.rasnum_name,\n" +
            "      a.cur_ra_prem PREM,\n" +
            "      a.cur_ra_comm COMPAY,\n" +
            "      a.cur_ra_amount RA_AMOUNT\n" +
            "    from sics_data.his_grp_cession_upload_all A\n" +
            "    where a.POLICY_NUMBER||a.POLICY_TYPE||a.Cvgid||a.LIFE1_ID||a.bussno||a.trans_code\n" +
            "          in(select tmp.pol_no||tmp.cedant_ces_nber||tmp.bussno||tmp.trans_code from sicslife_ceded1.dx_grp_cession_del tmp)\n" +
            "  union\n" +
            "  select \n" +
            "  a.Trans_Effective_Date , \n" +
            "      a.policy_number contno, \n" +
            "      a.trans_code,\n" +
            "      a.policy_type||a.CVGID ||a.life1_id as sub_key,\n" +
            "      a.bussno transaction_number,\n" +
            "      a.SUMATRISK,\n" +
            "      a.rasnum,a.rasnum_name,\n" +
            "      a.cur_ra_prem PREM,\n" +
            "      a.cur_ra_comm COMPAY,\n" +
            "      a.cur_ra_amount RA_AMOUNT\n" +
            "  from sics_data.group_cession_upload_ALL A\n" +
            "    where a.POLICY_NUMBER||a.POLICY_TYPE||a.Cvgid||a.LIFE1_ID||a.bussno||a.trans_code\n" +
            "          in(select tmp.pol_no||tmp.cedant_ces_nber||tmp.bussno||tmp.trans_code from sicslife_ceded1.dx_grp_cession_del tmp)\n")
    void createTempCession();

    @DS("HA_DATA_PROD1")
    @Select("{CALL GIMPORT_PLACEMENT_ACILC_SC()}")
    void tempToSicsByPlacementCessionLc();

    @DS("HA_DATA_PROD1")
    @Select("{CALL GIMPORT_PLACEMENT_ACI_SC()}")
    void tempToSicsByPlacementCession();

    @DS("HA_DATA_PROD1")
    @Select("{CALL GIMPORT_RETRO_ACILC_SC()}")
    void tempToSicsByRetroCessionLc();

    @DS("HA_DATA_PROD1")
    @Select("{CALL GIMPORT_RETRO_ACI_SC()}")
    void tempToSicsByRetroCession();
    @DS("HA_DATA_PROD1")
    @Select("{CALL GIUPDATERETENTIONLC_SC()}")
    void tempToSicsByGiupDateRetentionCessionLc();

    @DS("HA_DATA_PROD1")
    @Select("{CALL GIUPDATERETENTION_SC()}")
    void tempToSicsByGiupDateRetentionCession();

    @Select("SELECT COUNT(1) FROM ${tableOwner}.${tableName} t where (nvl(t.h_prem,0)<>'0' or nvl(t.h_compay,0)<>'0') and t.h_rasnum_name<>'HASL01'")
    int queryOwnerTableCountNotHASL01Cession(@Param("tableOwner") String tableOwner,@Param("tableName") String tableName);

    @Select("SELECT COUNT(1) FROM ${tableOwner}.${tableName} t where t.h_reinsurecom<>'HASL01'")
    int queryOwnerTableCountNotHASL01Claim(@Param("tableOwner") String tableOwner,@Param("tableName") String tableName);

}
