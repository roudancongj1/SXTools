package com.sics.sxt.dao;


import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;



/**
 * @Auth:GuangYun
 * */
@Mapper
@Repository
public interface GMapper {


    /**
     * 重置
     * */
    @Update("UPDATE group_cession_upload_mid\n" +
            "SET planno = CASE WHEN planno LIKE '%del' THEN 'NOTSPEC' ELSE planno END,\n" +
            "    GRCode = 'NOTSPEC',\n" +
            "    PCode = 'NOTSPEC'")
    int changeNullNOTSPEC();

    /**
     * 重置
     * */
    @Update("update group_cession_upload_mid set planno = null,ORGPLANNO = null,SUM_AT_RISK = null")
    int changePlGrPcNull();


    @Update("update group_cession_upload_mid t set t.sum_at_risk = (t.ORIGINAL_SUM_ASSURED*t.INSUREDNUM) " +
            "where  t.insuredtype='1' ")
    int updateSARByInsuredtype();
    @Update("update group_cession_upload_mid t set t.sum_at_risk = (t.ORIGINAL_SUM_ASSURED*t.INSUREDNUM) " +
            "where  t.life1_full_name='无名单' ")
    int updateSARByNoName();

    @Update("update group_cession_upload_mid t set t.sum_at_risk = 0 " +
            "where  t.TRANS_CODE in('ZT','LC_ZT','CT')")
    int updateSARByTransCode();


    @Update("merge INTO (SELECT a.POLICY_NUMBER,\n" +
            "                   a.POLICY_TYPE,\n" +
            "                   a.INSURANCE_PRODUCT_CEDENT_CODE,\n" +
            "                   a.LIFE1_ID,\n" +
            "                   a.PLANNO,\n" +
            "                   a.PCODE\n" +
            "              FROM GROUP_CESSION_UPLOAD_MID a\n" +
            "             WHERE TRANS_CODE IN ('ZT', 'LC_ZT', 'CT', 'IC')) a\n" +
            "using (SELECT DISTINCT a.POLICY_NUMBER,\n" +
            "                       a.POLICY_TYPE,\n" +
            "                       a.INSURANCE_PRODUCT_CEDENT_CODE,\n" +
            "                       a.LIFE1_ID,\n" +
            "                       a.PLANNO\n" +
            "         FROM GROUP_CESSION_UPLOAD_MID a\n" +
            "        WHERE a.TRANS_CODE = 'NB') b\n" +
            "ON (a.POLICY_NUMBER = b.POLICY_NUMBER and a.POLICY_TYPE = b.POLICY_TYPE AND a.INSURANCE_PRODUCT_CEDENT_CODE = b.INSURANCE_PRODUCT_CEDENT_CODE AND a.LIFE1_ID = b.LIFE1_ID)\n" +
            "WHEN matched THEN\n" +
            "  UPDATE SET a.PLANNO = b.PLANNO,a.PCODE = '1'")
    int updatePlanNoByTransCodeInMid();

    @Update("merge into (SELECT a.POLICY_NUMBER,\n" +
            "                   a.POLICY_TYPE,\n" +
            "                   a.INSURANCE_PRODUCT_CEDENT_CODE,\n" +
            "                   a.LIFE1_ID,\n" +
            "                   a.PLANNO,\n" +
            "                   a.PCODE\n" +
            "              FROM GROUP_CESSION_UPLOAD_MID a\n" +
            "             where TRANS_CODE in ('ZT', 'LC_ZT', 'CT', 'IC')) a\n" +
            "using (SELECT DISTINCT a.POLICY_NUMBER,\n" +
            "                       a.POLICY_TYPE,\n" +
            "                       a.CVGID,\n" +
            "                       a.LIFE1_ID,\n" +
            "                       a.ripreceptno\n" +
            "         FROM HIS_GRP_CESSION_UPLOAD_ALL a\n" +
            "        where a.TRANS_CODE = 'NB' and a.RIPRECEPTNO IS NOT NULL) b\n" +
            "on (a.POLICY_NUMBER = b.POLICY_NUMBER AND a.POLICY_TYPE = b.POLICY_TYPE AND a.INSURANCE_PRODUCT_CEDENT_CODE = b.CVGID and a.LIFE1_ID = b.LIFE1_ID)\n" +
            "when matched then\n" +
            "  update set a.PLANNO = b.RIPRECEPTNO,a.PCODE = '2'")
    int updatePlanNoByTransCodeInAll();

    @Update("UPDATE GROUP_CESSION_UPLOAD_MID a SET a.ORGPLANNO = a.PLANNO")
    int copyOrgPlanNoByPlanNo();


    @Update("UPDATE\n" +
            "(SELECT\n" +
            " a.TRANS_EFFECTIVE_DATE,\n" +
            " a.LIFE1_DATE_OF_BIRTH,\n" +
            " a.SUM_AT_RISK,\n" +
            " ADD_MONTHS( TO_DATE( a.LIFE1_DATE_OF_BIRTH, 'yyyy/MM/dd' ), 216 ) zerodate \n" +
            "FROM\n" +
            "  GROUP_CESSION_UPLOAD_MID a " +
            "WHERE a.INSURANCE_PRODUCT_CEDENT_CODE = 'GCI401'\n" +
            ") b\n" +
            "  SET b.SUM_AT_RISK = '0'\n" +
            "  WHERE TO_DATE(b.TRANS_EFFECTIVE_DATE, 'yyyy/MM/dd')<b.ZERODATE")
    int updateSARByBirth();


    @Update("merge into \n" +
            "(SELECT * FROM group_cession_upload_MID where planno is null ) g\n" +
            "using (SELECT * FROM  GTPLANMAPPING WHERE STATUS = '4') p\n" +
            "on (g.policy_type=p.BP and g.insurance_product_cedent_code=p.IP and to_date(g.grp_eff_date,'yyyy/mm/dd')>=p.prevaliDate)\n" +
            "when matched then \n" +
            "update set g.planno=p.RECEPTNO")
    int changeReinsurancePlanNo();


    @Insert("INSERT INTO TRAN_LOG_G\n" +
            "  (SELECT\n" +
            "    a.OBJECT_ID,\n" +
            "    a.POLICY_NUMBER,\n" +
            "    a.POLICY_TYPE,\n" +
            "    a.INSURANCE_PRODUCT_CEDENT_CODE,\n" +
            "    a.LIFE1_ID,\n" +
            "    a.TRANS_CODE,\n" +
            "    '5' errcode,\n" +
            "    'MID/HIS表未找到对应NB交易TransCode' errmsg,\n" +
            "    SYSDATE time\n" +
            "  FROM\n" +
            "    GROUP_CESSION_UPLOAD_MID a \n" +
            "  WHERE\n" +
            "    TRANS_CODE IN ( 'ZT', 'LC_ZT', 'CT', 'IC' ) \n" +
            "    AND PCODE = 'NOTSPEC')")
    int addErrDataByNotNB();




    @Update("merge into \n" +
            "(select * from sics_data.group_cession_upload where MCOB is null) up\n" +
            "using (\n" +
            " SELECT DISTINCT B.HA_Product,B.MCOB \n" +
            "FROM sics_data.HAProdClassification_Mapping B ) c\n" +
            "on (up.policy_type=C.HA_Product)\n" +
            "when matched then \n" +
            "update set up.MCOB=c.MCOB")
    int associationMCOB();

    @Update("merge into \n" +
            "(select * from sics_data.group_cession_upload where long_short_term is null) up\n" +
            "using (\n" +
            " SELECT DISTINCT B.HA_Product,B.LongShortTerm_Ind \n" +
            "FROM sics_data.HAProdClassification_Mapping B ) c\n" +
            "on (up.policy_type=C.HA_Product)\n" +
            "when matched then \n" +
            "update set up.long_short_term=c.LongShortTerm_Ind")
    int associationLongShortTerm();

    @Update("merge into \n" +
            "(select * from sics_data.group_cession_upload where VAT is null) up\n" +
            "using (\n" +
            " SELECT DISTINCT B.HA_Product,B.VAT_Ind \n" +
            "FROM sics_data.HAProdClassification_Mapping B ) c\n" +
            "on (up.policy_type=C.HA_Product)\n" +
            "when matched then \n" +
            "update set up.VAT=c.VAT_Ind")
    int associationVAT();
    @Update("create table sics_data.group_cession_upload_ALL as \n" +
            "            SELECT \n" +
            "                  a.group_schema_number\n" +
            "                , a.policy_number\n" +
            "                , a.bussno\n" +
            "                , a.polno\n" +
            "                , a.dutycode\n" +
            "                , a.trans_code\n" +
            "                , to_number(\n" +
            "                   substr(replace(a.bussno,'_',''),12,length(replace(a.bussno,'_','')))\n" +
            "                   ) transaction_number\n" +
            "                , a.sequence_number\n" +
            "                , a.proposal_number\n" +
            "                , a.sales_channel\n" +
            "                , a.company_code\n" +
            "                , a.branch\n" +
            "                , a.mcob\n" +
            "                , a.long_short_term\n" +
            "                , a.vat\n" +
            "                , A.TRAN_DATE TRAN_DATE_orn\n" +
            "        ,(case when A.TRANS_CODE in ( 'NI','NB') \n" +
            "          then (case when A.TRAN_DATE>A.TRANS_EFFECTIVE_DATE then A.TRAN_DATE else  A.TRANS_EFFECTIVE_DATE end)\n" +
            "          else A.TRAN_DATE end) TRAN_DATE\n" +
            "                , a.trans_effective_date\n" +
            "                , a.agent_number\n" +
            "                , a.area_code\n" +
            "                , a.policy_type\n" +
            "                , a.cvgid\n" +
            "                , a.AMNTADDTYPE  as  benefit_covered\n" +
            "                , a.riskcode\n" +
            "                , a.cvrg_indicator\n" +
            "                , a.policy_inception_date\n" +
            "                , a.currentdate\n" +
            "                , a.benefit_issue_date\n" +
            "                , a.benefit_termination_date\n" +
            "                , a.mortality\n" +
            "                , a.cession_currency\n" +
            "                , a.original_sum_assured\n" +
            "                , a.additional_mortality_ratio\n" +
            "                , a.sum_at_risk\n" +
            "                , a.effe_amnt\n" +
            "                , a.cash_value\n" +
            "                , a.bonus_sa\n" +
            "                , a.payment_mode\n" +
            "                , a.premium_pay_term\n" +
            "                , a.premium_amount\n" +
            "                , a.plan\n" +
            "                , a.deductiblemode\n" +
            "                , a.deductibled\n" +
            "                , a.payment_ratio\n" +
            "                , a.policyholder_id\n" +
            "                , a.policyholder_full_name\n" +
            "                , a.policyholder_gender\n" +
            "                , a.policyholder_date_of_birth\n" +
            "                , a.policyholder_smoker_status\n" +
            "                , a.policyholder_occupation_code\n" +
            "                , a.policyholder_socialsecsta\n" +
            "                , a.policyholder_ssecmedinssta\n" +
            "                , a.policyholder_corpsubsta\n" +
            "                , a.life1_id\n" +
            "                , a.life1_full_name\n" +
            "                , a.life1_gender\n" +
            "                , a.life1_date_of_birth\n" +
            "                , a.life1_original_age\n" +
            "                , a.life1_smoker_status\n" +
            "                , a.life1_occupation_code\n" +
            "                , a.life1_socialsecsta\n" +
            "                , a.life1_ssecmedinssta\n" +
            "                , a.life1_corpsubsta\n" +
            "                , a.joint_life_type\n" +
            "                , a.joint_life_desc\n" +
            "                , a.life2_id\n" +
            "                , a.life2_full_name\n" +
            "                , a.life2_gender\n" +
            "                , a.life2_date_of_birth\n" +
            "                , a.life2_original_age\n" +
            "                , a.life2_smoker_status\n" +
            "                , a.life2_occupation_code\n" +
            "                , a.life2_socialsecsta\n" +
            "                , a.life2_ssecmedinssta\n" +
            "                , a.life2_corpsubsta\n" +
            "                , a.underwriting_solution\n" +
            "                , a.loading_reason\n" +
            "                , a.loading_type\n" +
            "                , a.addprem\n" +
            "                , a.loading_percent_1\n" +
            "                , a.loading_percent_2\n" +
            "                , a.loading_percent\n" +
            "                , a.loading_amount\n" +
            "                , a.loading_amount_unit\n" +
            "                , a.loading_duration\n" +
            "                , a.loading_duration_unit\n" +
            "                , a.conttype\n" +
            "                , a.pushdate\n" +
            "                , a.insuredtype\n" +
            "                , a.idtype\n" +
            "                , a.idno\n" +
            "                , a.trantype\n" +
            "                , a.policyyear\n" +
            "                , a.initprem\n" +
            "                , a.constrtype\n" +
            "                , a.insurednum\n" +
            "                , a.archarea\n" +
            "                , a.archcost\n" +
            "                , a.purpose\n" +
            "                , a.constrsa\n" +
            "                , a.waitingperiod\n" +
            "                , a.rnflag\n" +
            "                , a.policy_inception_date_real\n" +
            "                , a.quotno\n" +
            "                , a.busicategory\n" +
            "                , a.disability\n" +
            "                , a.notes\n" +
            "                , a.polrcdate\n" +
            "                , a.prodpolno\n" +
            "                , a.mprpolno\n" +
            "                , b.reinsurance_date\n" +
            "                , a.GRP_EFF_DATE\n" +
            "        ,b.CVGID as CVGID_re\n" +
            "        ,b.RIPRECEPTNO\n" +
            "    , (\n" +
            "                       CASE b.RASNUM\n" +
            "                       WHEN 'CLRE01' THEN '70001'\n" +
            "                       WHEN 'QHRE01' THEN '70007'\n" +
            "                       WHEN 'HURE01' THEN '70000'\n" +
            "                       WHEN 'MURE01' THEN '70005'\n" +
            "                       WHEN 'TPRE01' THEN '70006'\n" +
            "                       WHEN 'SWRE01' THEN '70003'\n" +
            "                       WHEN 'GENE01' THEN '70004'\n" +
            "                       WHEN 'SCRE01' THEN '70002'\n" +
            "                       WHEN 'SCRE02' THEN '70009'\n" +
            "                       WHEN 'RCRE01' THEN 'RCRE01'\n" +
            "                       ELSE ''\n" +
            "                   END\n" +
            "                   ) AS rasnum\n" +
            "        , b.RASNUM as RASNUM_NAME\n" +
            "        , b.BUSSTYPE \n" +
            "        , b.AMNTADDTYPE \n" +
            "        , b.SUMATRISK \n" +
            "        , b.RA_AMOUNT\n" +
            "        , b.CUR_RA_PREM \n" +
            "        , b.CUR_RA_COMM,b.cur_ra_amount\n" +
            "              FROM  \n" +
            "        (  select distinct c.*,gp.cvgid,gp.AMNTADDTYPE, gp.riskcode  from sics_data.group_cession_upload c\n" +
            "              LEFT JOIN sics_data.group_product gp\n" +
            "                ON c.dutycode = gp.dutycode where gp.cvgid is not null) a\n" +
            "              LEFT JOIN sics_data.group_cession_rein b\n" +
            "                ON (\n" +
            "                       a.group_schema_number = b.policyno\n" +
            "                   AND a.policy_number = b.contno\n" +
            "                   AND a.polno = b.polno\n" +
            "                   AND a.trans_code = b.busstype\n" +
            "                   AND a.dutycode = b.dutycode\n" +
            "           and a.cvgid=b.cvgid\n" +
            "                   AND a.policy_type = b.finriskcode\n" +
            "                   AND a.policyyear = b.years\n" +
            "                   )\n" +
            "               WHERE a.long_short_term = 'LT'\n" +
            "            union all\n" +
            "            SELECT                       \n" +
            "                  a.group_schema_number\n" +
            "                , a.policy_number\n" +
            "                , a.bussno\n" +
            "                , a.polno\n" +
            "                , a.dutycode\n" +
            "                , a.trans_code\n" +
            "                , to_number(\n" +
            "                   substr(replace(a.bussno,'_',''),12,length(replace(a.bussno,'_','')))\n" +
            "                   ) transaction_number\n" +
            "                , a.sequence_number\n" +
            "                , a.proposal_number\n" +
            "                , a.sales_channel\n" +
            "                , a.company_code\n" +
            "                , a.branch\n" +
            "                , a.mcob\n" +
            "                , a.long_short_term\n" +
            "                , a.vat\n" +
            "                , A.TRAN_DATE TRAN_DATE_orn\n" +
            "        ,(case when A.TRANS_CODE in ( 'NI','NB') \n" +
            "          then (case when A.TRAN_DATE>A.TRANS_EFFECTIVE_DATE then A.TRAN_DATE else  A.TRANS_EFFECTIVE_DATE end)\n" +
            "          else A.TRAN_DATE end) TRAN_DATE\n" +
            "                , a.trans_effective_date\n" +
            "                , a.agent_number\n" +
            "                , a.area_code\n" +
            "                , a.policy_type\n" +
            "                , a.cvgid\n" +
            "                , a.AMNTADDTYPE  as  benefit_covered\n" +
            "                , a.riskcode\n" +
            "                , a.cvrg_indicator\n" +
            "                , a.policy_inception_date\n" +
            "                , a.currentdate\n" +
            "                , a.benefit_issue_date\n" +
            "                , a.benefit_termination_date\n" +
            "                , a.mortality\n" +
            "                , a.cession_currency\n" +
            "                , a.original_sum_assured\n" +
            "                , a.additional_mortality_ratio\n" +
            "                , a.sum_at_risk\n" +
            "                , a.effe_amnt\n" +
            "                , a.cash_value\n" +
            "                , a.bonus_sa\n" +
            "                , a.payment_mode\n" +
            "                , a.premium_pay_term\n" +
            "                , a.premium_amount\n" +
            "                , a.plan\n" +
            "                , a.deductiblemode\n" +
            "                , a.deductibled\n" +
            "                , a.payment_ratio\n" +
            "                , a.policyholder_id\n" +
            "                , a.policyholder_full_name\n" +
            "                , a.policyholder_gender\n" +
            "                , a.policyholder_date_of_birth\n" +
            "                , a.policyholder_smoker_status\n" +
            "                , a.policyholder_occupation_code\n" +
            "                , a.policyholder_socialsecsta\n" +
            "                , a.policyholder_ssecmedinssta\n" +
            "                , a.policyholder_corpsubsta\n" +
            "                , a.life1_id\n" +
            "                , a.life1_full_name\n" +
            "                , a.life1_gender\n" +
            "                , a.life1_date_of_birth\n" +
            "                , a.life1_original_age\n" +
            "                , a.life1_smoker_status\n" +
            "                , a.life1_occupation_code\n" +
            "                , a.life1_socialsecsta\n" +
            "                , a.life1_ssecmedinssta\n" +
            "                , a.life1_corpsubsta\n" +
            "                , a.joint_life_type\n" +
            "                , a.joint_life_desc\n" +
            "                , a.life2_id\n" +
            "                , a.life2_full_name\n" +
            "                , a.life2_gender\n" +
            "                , a.life2_date_of_birth\n" +
            "                , a.life2_original_age\n" +
            "                , a.life2_smoker_status\n" +
            "                , a.life2_occupation_code\n" +
            "                , a.life2_socialsecsta\n" +
            "                , a.life2_ssecmedinssta\n" +
            "                , a.life2_corpsubsta\n" +
            "                , a.underwriting_solution\n" +
            "                , a.loading_reason\n" +
            "                , a.loading_type\n" +
            "                , a.addprem\n" +
            "                , a.loading_percent_1\n" +
            "                , a.loading_percent_2\n" +
            "                , a.loading_percent\n" +
            "                , a.loading_amount\n" +
            "                , a.loading_amount_unit\n" +
            "                , a.loading_duration\n" +
            "                , a.loading_duration_unit\n" +
            "                , a.conttype\n" +
            "                , a.pushdate\n" +
            "                , a.insuredtype\n" +
            "                , a.idtype\n" +
            "                , a.idno\n" +
            "                , a.trantype\n" +
            "                , a.policyyear\n" +
            "                , a.initprem\n" +
            "                , a.constrtype\n" +
            "                , a.insurednum\n" +
            "                , a.archarea\n" +
            "                , a.archcost\n" +
            "                , a.purpose\n" +
            "                , a.constrsa\n" +
            "                , a.waitingperiod\n" +
            "                , a.rnflag\n" +
            "                , a.policy_inception_date_real\n" +
            "                , a.quotno\n" +
            "                , a.busicategory\n" +
            "                , a.disability\n" +
            "                , a.notes\n" +
            "                , a.polrcdate\n" +
            "                , a.prodpolno\n" +
            "                , a.mprpolno\n" +
            "                , b.reinsurance_date\n" +
            "                , a.GRP_EFF_DATE\n" +
            "        ,b.CVGID as CVGID_re\n" +
            "        ,b.RIPRECEPTNO\n" +
            "    , (\n" +
            "                       CASE b.RASNUM\n" +
            "                       WHEN 'CLRE01' THEN '70001'\n" +
            "                       WHEN 'QHRE01' THEN '70007'\n" +
            "                       WHEN 'HURE01' THEN '70000'\n" +
            "                       WHEN 'MURE01' THEN '70005'\n" +
            "                       WHEN 'TPRE01' THEN '70006'\n" +
            "                       WHEN 'SWRE01' THEN '70003'\n" +
            "                       WHEN 'GENE01' THEN '70004'\n" +
            "                       WHEN 'SCRE01' THEN '70002'\n" +
            "                       WHEN 'SCRE02' THEN '70009'\n" +
            "                       WHEN 'RCRE01' THEN 'RCRE01'\n" +
            "                       ELSE ''\n" +
            "                   END\n" +
            "                   ) AS rasnum\n" +
            "        , b.RASNUM as RASNUM_NAME\n" +
            "        , b.BUSSTYPE \n" +
            "        , b.AMNTADDTYPE \n" +
            "        , b.SUMATRISK \n" +
            "        , b.RA_AMOUNT\n" +
            "        , b.CUR_RA_PREM \n" +
            "        , b.CUR_RA_COMM,b.cur_ra_amount\n" +
            "              FROM \n" +
            "        (  select distinct c.*,gp.cvgid,gp.AMNTADDTYPE, gp.riskcode  from sics_data.group_cession_upload c\n" +
            "              LEFT JOIN sics_data.group_product gp\n" +
            "                ON c.dutycode = gp.dutycode where gp.cvgid is not null) a\n" +
            "              LEFT JOIN SICS_DATA.group_cession_rein B\n" +
            "                ON (\n" +
            "                       A.Group_Schema_Number = B.Policyno\n" +
            "                   AND A.Policy_Number = B.Contno\n" +
            "                   AND A.bussno = B.Bussno\n" +
            "                   AND A.PolNo = B.Polno\n" +
            "                   AND A.DutyCode = B.Dutycode\n" +
            "           and a.cvgid=b.cvgid\n" +
            "                   AND A.Policy_Type = B.Finriskcode\n" +
            "                   AND a.trans_code = B.busstype \n" +
            "               )\n" +
            "             where    A.TRANS_CODE not in ( 'NI','RR') and a.long_short_term = 'ST' \n" +
            "            union all\n" +
            "            SELECT \n" +
            "                  a.group_schema_number\n" +
            "                , a.policy_number\n" +
            "                , a.bussno\n" +
            "                , a.polno\n" +
            "                , a.dutycode\n" +
            "                , a.trans_code\n" +
            "                , to_number(\n" +
            "                   substr(replace(a.bussno,'_',''),12,length(replace(a.bussno,'_','')))\n" +
            "                   ) transaction_number\n" +
            "                , a.sequence_number\n" +
            "                , a.proposal_number\n" +
            "                , a.sales_channel\n" +
            "                , a.company_code\n" +
            "                , a.branch\n" +
            "                , a.mcob\n" +
            "                , a.long_short_term\n" +
            "                , a.vat\n" +
            "                , A.TRAN_DATE TRAN_DATE_orn\n" +
            "        ,(case when A.TRANS_CODE in ( 'NI','NB') \n" +
            "                  then (case when A.TRAN_DATE>A.TRANS_EFFECTIVE_DATE then A.TRAN_DATE else  A.TRANS_EFFECTIVE_DATE end)\n" +
            "                    else A.TRAN_DATE end) TRAN_DATE\n" +
            "                , a.trans_effective_date\n" +
            "                , a.agent_number\n" +
            "                , a.area_code\n" +
            "                , a.policy_type\n" +
            "                , a.cvgid\n" +
            "                , a.AMNTADDTYPE  as  benefit_covered\n" +
            "                , a.riskcode\n" +
            "                , a.cvrg_indicator\n" +
            "                , a.policy_inception_date\n" +
            "                , a.currentdate\n" +
            "                , a.benefit_issue_date\n" +
            "                , a.benefit_termination_date\n" +
            "                , a.mortality\n" +
            "                , a.cession_currency\n" +
            "                , a.original_sum_assured\n" +
            "                , a.additional_mortality_ratio\n" +
            "                , a.sum_at_risk\n" +
            "                , a.effe_amnt\n" +
            "                , a.cash_value\n" +
            "                , a.bonus_sa\n" +
            "                , a.payment_mode\n" +
            "                , a.premium_pay_term\n" +
            "                , a.premium_amount\n" +
            "                , a.plan\n" +
            "                , a.deductiblemode\n" +
            "                , a.deductibled\n" +
            "                , a.payment_ratio\n" +
            "                , a.policyholder_id\n" +
            "                , a.policyholder_full_name\n" +
            "                , a.policyholder_gender\n" +
            "                , a.policyholder_date_of_birth\n" +
            "                , a.policyholder_smoker_status\n" +
            "                , a.policyholder_occupation_code\n" +
            "                , a.policyholder_socialsecsta\n" +
            "                , a.policyholder_ssecmedinssta\n" +
            "                , a.policyholder_corpsubsta\n" +
            "                , a.life1_id\n" +
            "                , a.life1_full_name\n" +
            "                , a.life1_gender\n" +
            "                , a.life1_date_of_birth\n" +
            "                , a.life1_original_age\n" +
            "                , a.life1_smoker_status\n" +
            "                , a.life1_occupation_code\n" +
            "                , a.life1_socialsecsta\n" +
            "                , a.life1_ssecmedinssta\n" +
            "                , a.life1_corpsubsta\n" +
            "                , a.joint_life_type\n" +
            "                , a.joint_life_desc\n" +
            "                , a.life2_id\n" +
            "                , a.life2_full_name\n" +
            "                , a.life2_gender\n" +
            "                , a.life2_date_of_birth\n" +
            "                , a.life2_original_age\n" +
            "                , a.life2_smoker_status\n" +
            "                , a.life2_occupation_code\n" +
            "                , a.life2_socialsecsta\n" +
            "                , a.life2_ssecmedinssta\n" +
            "                , a.life2_corpsubsta\n" +
            "                , a.underwriting_solution\n" +
            "                , a.loading_reason\n" +
            "                , a.loading_type\n" +
            "                , a.addprem\n" +
            "                , a.loading_percent_1\n" +
            "                , a.loading_percent_2\n" +
            "                , a.loading_percent\n" +
            "                , a.loading_amount\n" +
            "                , a.loading_amount_unit\n" +
            "                , a.loading_duration\n" +
            "                , a.loading_duration_unit\n" +
            "                , a.conttype\n" +
            "                , a.pushdate\n" +
            "                , a.insuredtype\n" +
            "                , a.idtype\n" +
            "                , a.idno\n" +
            "                , a.trantype\n" +
            "                , a.policyyear\n" +
            "                , a.initprem\n" +
            "                , a.constrtype\n" +
            "                , a.insurednum\n" +
            "                , a.archarea\n" +
            "                , a.archcost\n" +
            "                , a.purpose\n" +
            "                , a.constrsa\n" +
            "                , a.waitingperiod\n" +
            "                , a.rnflag\n" +
            "                , a.policy_inception_date_real\n" +
            "                , a.quotno\n" +
            "                , a.busicategory\n" +
            "                , a.disability\n" +
            "                , a.notes\n" +
            "                , a.polrcdate\n" +
            "                , a.prodpolno\n" +
            "                , a.mprpolno\n" +
            "                , b.reinsurance_date\n" +
            "                , a.GRP_EFF_DATE\n" +
            "        ,b.CVGID as CVGID_re\n" +
            "        ,b.RIPRECEPTNO\n" +
            "    , (\n" +
            "                       CASE b.RASNUM\n" +
            "                       WHEN 'CLRE01' THEN '70001'\n" +
            "                       WHEN 'QHRE01' THEN '70007'\n" +
            "                       WHEN 'HURE01' THEN '70000'\n" +
            "                       WHEN 'MURE01' THEN '70005'\n" +
            "                       WHEN 'TPRE01' THEN '70006'\n" +
            "                       WHEN 'SWRE01' THEN '70003'\n" +
            "                       WHEN 'GENE01' THEN '70004'\n" +
            "                       WHEN 'SCRE01' THEN '70002'\n" +
            "                       WHEN 'SCRE02' THEN '70009'\n" +
            "                       WHEN 'RCRE01' THEN 'RCRE01'\n" +
            "                       ELSE ''\n" +
            "                   END\n" +
            "                   ) AS rasnum\n" +
            "        , b.RASNUM as RASNUM_NAME\n" +
            "        , b.BUSSTYPE \n" +
            "        , b.AMNTADDTYPE \n" +
            "        , b.SUMATRISK \n" +
            "        , b.RA_AMOUNT\n" +
            "        , b.CUR_RA_PREM \n" +
            "        , b.CUR_RA_COMM,b.cur_ra_amount\n" +
            "              FROM \n" +
            "        (  select distinct c.*,gp.cvgid,gp.AMNTADDTYPE, gp.riskcode  from sics_data.group_cession_upload c\n" +
            "              LEFT JOIN sics_data.group_product gp\n" +
            "                ON c.dutycode = gp.dutycode where gp.cvgid is not null) a\n" +
            "                  LEFT JOIN SICS_DATA.group_cession_rein B\n" +
            "                  ON (A.Group_Schema_Number = B.Policyno\n" +
            "                  AND A.Policy_Number       = B.Contno\n" +
            "                  and A.Group_Schema_Number = B.Bussno\n" +
            "                  AND A.PolNo               = B.Polno\n" +
            "          and a.cvgid=b.cvgid\n" +
            "                  AND A.DutyCode            = B.Dutycode\n" +
            "                  AND A.Policy_Type         = B.Finriskcode\n" +
            "          AND B.BUSSTYPE = 'NB'\n" +
            "                  ) \n" +
            "                where \n" +
            "                    A.TRANS_CODE in ( 'NI','RR')\n" +
            "                and a.long_short_term = 'ST' ")
    int createCessionAllTable();

    @DS("HA_DATA_PROD1")
    @Insert("insert into sics_data.group_cession_upload_mid\n" +
            "select id_sequence.nextval as object_id,\n" +
            "t.* from (select distinct\n" +
            "A.GROUP_SCHEMA_NUMBER groschema_number,\n" +
            "A.POLICY_NUMBER,\n" +
            "A.BUSSNO,\n" +
            "A.POLNO,\n" +
            "A.DUTYCODE,\n" +
            "A.TRANS_CODE,\n" +
            "concat(A.POLICY_NUMBER,a.Cvgid) SEQUENCE_NUMBER,\n" +
            "A.PROPOSAL_NUMBER,\n" +
            "A.SALES_CHANNEL,\n" +
            "A.COMPANY_CODE,\n" +
            "A.BRANCH,\n" +
            "A.MCOB,\n" +
            "A.LONG_SHORT_TERM,\n" +
            "A.VAT,\n" +
            "substr(replace(A.BUSSNO,'_',''),12,length(replace(A.BUSSNO,'_',''))) TRANSACTION_NUMBER,\n" +
            "A.TRAN_DATE,\n" +
            "A.TRANS_EFFECTIVE_DATE,\n" +
            "A.AGENT_NUMBER,\n" +
            "A.AREA_CODE,\n" +
            "A.POLICY_TYPE,\n" +
            "a.Cvgid INSURANCE_PRODUCT_CEDENT_CODE,\n" +
            "a.benefit_covered BENEFIT_COVERED,\n" +
            "A.CVRG_INDICATOR,\n" +
            "A.POLICY_INCEPTION_DATE,\n" +
            "A.CURRENTDATE,\n" +
            "A.BENEFIT_ISSUE_DATE,\n" +
            "A.BENEFIT_TERMINATION_DATE,\n" +
            "A.MORTALITY,\n" +
            "A.CESSION_CURRENCY,\n" +
            "A.ORIGINAL_SUM_ASSURED,\n" +
            "A.ADDITIONAL_MORTALITY_RATIO,\n" +
            "A.SUMATRISK SUM_AT_RISK,\n" +
            "A.EFFE_AMNT,\n" +
            "A.CASH_VALUE,\n" +
            "A.BONUS_SA,\n" +
            "A.PAYMENT_MODE,\n" +
            "A.PREMIUM_PAY_TERM,\n" +
            "A.PREMIUM_AMOUNT,\n" +
            "A.PLAN,\n" +
            "A.DEDUCTIBLEMODE,\n" +
            "A.DEDUCTIBLED,\n" +
            "A.PAYMENT_RATIO,\n" +
            "A.POLICYHOLDER_ID,\n" +
            "A.POLICYHOLDER_FULL_NAME,\n" +
            "A.BusiCategory,\n" +
            "A.POLICYHOLDER_GENDER,\n" +
            "A.POLICYHOLDER_DATE_OF_BIRTH,\n" +
            "A.POLICYHOLDER_SMOKER_STATUS,\n" +
            "A.POLICYHOLDER_OCCUPATION_CODE,\n" +
            "A.POLICYHOLDER_SOCIALSECSTA,\n" +
            "A.POLICYHOLDER_SSECMEDINSSTA,\n" +
            "A.POLICYHOLDER_CORPSUBSTA,\n" +
            "A.LIFE1_ID,\n" +
            "A.LIFE1_FULL_NAME,\n" +
            "A.LIFE1_GENDER,\n" +
            "A.LIFE1_DATE_OF_BIRTH,\n" +
            "A.LIFE1_ORIGINAL_AGE,\n" +
            "A.LIFE1_SMOKER_STATUS,\n" +
            "case when instr(A.LIFE1_OCCUPATION_CODE, '0') <= 0 then concat('0',A.LIFE1_OCCUPATION_CODE)\n" +
            "else LIFE1_OCCUPATION_CODE end LIFE1_OCCUPATION_CODE,\n" +
            "case when A.LIFE1_SOCIALSECSTA is null then 'N'\n" +
            "when A.LIFE1_SOCIALSECSTA ='0' then 'N'\n" +
            "else 'Y' end LIFE1_SOCIALSECSTA,\n" +
            "case when A.LIFE1_SSECMEDINSSTA is null then 'N'\n" +
            "when A.LIFE1_SSECMEDINSSTA ='0' then 'N'\n" +
            "else 'Y' end LIFE1_SSECMEDINSSTA,\n" +
            "case when A.LIFE1_CORPSUBSTA is null then 'N'\n" +
            "when A.LIFE1_CORPSUBSTA ='0' then 'N'\n" +
            "else 'Y' end LIFE1_CORPSUBSTA,\n" +
            "A.JOINT_LIFE_TYPE,\n" +
            "A.JOINT_LIFE_DESC,\n" +
            "A.LIFE2_ID,\n" +
            "A.LIFE2_FULL_NAME,\n" +
            "A.LIFE2_GENDER,\n" +
            "A.LIFE2_DATE_OF_BIRTH,\n" +
            "A.LIFE2_ORIGINAL_AGE,\n" +
            "A.LIFE2_SMOKER_STATUS,\n" +
            "A.LIFE2_OCCUPATION_CODE,\n" +
            "A.LIFE2_SOCIALSECSTA,\n" +
            "A.LIFE2_SSECMEDINSSTA,\n" +
            "A.LIFE2_CORPSUBSTA,\n" +
            "(CASE  WHEN a.CVGID is null THEN 'N'\n" +
            "  when a.CVGID is not null then A.Underwriting_Solution end) UNDERWRITING_SOLUTION,\n" +
            "A.LOADING_REASON,\n" +
            "A.LOADING_TYPE,\n" +
            "A.ADDPREM,\n" +
            "A.LOADING_PERCENT_1,\n" +
            "A.LOADING_PERCENT_2,\n" +
            "A.LOADING_PERCENT,\n" +
            "A.LOADING_AMOUNT,\n" +
            "A.LOADING_AMOUNT_UNIT,\n" +
            "A.LOADING_DURATION,\n" +
            "A.LOADING_DURATION_UNIT,\n" +
            "A.CONTTYPE,\n" +
            "A.PUSHDATE,\n" +
            "A.INSUREDTYPE,\n" +
            "A.IDTYPE,\n" +
            "A.IDNO,\n" +
            "A.TRANTYPE,\n" +
            "A.POLICYYEAR,\n" +
            "A.INITPREM,\n" +
            "A.CONSTRTYPE,\n" +
            "A.INSUREDNUM,\n" +
            "A.ARCHAREA,\n" +
            "A.ARCHCOST,\n" +
            "A.PURPOSE,\n" +
            "A.CONSTRSA,\n" +
            "A.WAITINGPERIOD,\n" +
            "A.RNFLAG,\n" +
            "A.POLICY_INCEPTION_DATE_REAL,\n" +
            "a.ripreceptno RIPRECEPTNO,\n" +
            "a.reinsurance_date as reindate,\n" +
            "a.ripreceptno planno,\n" +
            "'NOTSPEC' GRCode,\n" +
            "'NOTSPEC' PCode,\n" +
            "A.Quotno  QUOTNO,\n" +
            "a.disability,\n" +
            "a.notes,\n" +
            "a.polrcdate,\n" +
            "a.prodpolno,\n" +
            "a.mprpolno,\n" +
            "a.riskcode,\n" +
            "a.GRP_EFF_DATE,'' ORGPLANNO,A.SUMATRISK SAR,a.TRAN_DATE_orn,'' TF_IND\n" +
            "from sics_data.group_cession_upload_ALL A ) t")
    int insertCessionMidTable();
    @Update("merge INTO sics_data.group_cession_upload_mid up \n" +
            "using (SELECT DISTINCT\n" +
            "  b.GROUP_SCHEMA_NUMBER,\n" +
            "  b.POLICY_NUMBER,\n" +
            "  b.POLICY_TYPE,\n" +
            "  b.INSURANCE_PRODUCT_CEDENT_CODE,\n" +
            "  b.LIFE1_ID,\n" +
            "  b.TRANSACTION_NUMBER,\n" +
            "  lpad( TO_CHAR( b.TRANSACTION_NUMBER + 1 ), 5, '0' ) AS TRANSACTION_NUMBER_new,\n" +
            "  b.TRANS_CODE,\n" +
            "  b.OBJECT_ID\n" +
            "FROM\n" +
            "  ( SELECT * FROM sics_data.group_cession_upload_mid WHERE TRANS_CODE IN ( 'NB', 'NI' ) ) A\n" +
            "  INNER JOIN ( SELECT * FROM sics_data.group_cession_upload_mid WHERE TRANS_CODE NOT IN ( 'NB', 'NI', 'LC_NI', 'RN' ) ) B ON a.GROUP_SCHEMA_NUMBER = b.GROUP_SCHEMA_NUMBER \n" +
            "  AND a.POLICY_NUMBER = b.POLICY_NUMBER \n" +
            "  AND a.POLICY_TYPE = b.POLICY_TYPE \n" +
            "  AND a.INSURANCE_PRODUCT_CEDENT_CODE = b.INSURANCE_PRODUCT_CEDENT_CODE \n" +
            "  AND a.LIFE1_ID = b.LIFE1_ID \n" +
            "WHERE\n" +
            "  a.TRANSACTION_NUMBER = b.TRANSACTION_NUMBER ) c ON ( up.OBJECT_ID = C.OBJECT_ID ) \n" +
            "WHEN matched THEN\n" +
            "UPDATE \n" +
            "  SET up.TRANSACTION_NUMBER = c.TRANSACTION_NUMBER_new")
    int changeSameTranNo();

    @DS("HA_DATA_PROD1")
    @Insert("insert into sics_data.group_cession_claim_mid\n" +
            "select id_sequence.nextval  as object_id, t.* from ( \n" +
            "SELECT  DISTINCT\n" +
            "      A.RGTNO\n" +
            "     , a.caseno\n" +
            "     , A.PolicyNo POLICY_NO\n" +
            "     , A.Contno\n" +
            "     , substr(a.caseno,9) TRANNO\n" +
            "     , A.TRANTYPE TRAN_TYPE\n" +
            "     , A.GIVETYPE GIVE_TYPE\n" +
            "     , A.ACCDATE\n" +
            "     , A.REPORTDATE\n" +
            "     , A.RGTDate\n" +
            "     , A.ENDCASEDATE\n" +
            "     , A.VALDATE\n" +
            "     , A.INSUREDNO\n" +
            "     , A.DUTYCODE\n" +
            "     , A.GETDUTYCODE\n" +
            "     , A.FINRISKCODE\n" +
            "     , a.amntaddtype CLAIM_BENEFIT_COVER\n" +
            "     , a.cvgid\n" +
            "     , A.ACCREASON\n" +
            "     , '01' LIABILITY_TYPE\n" +
            "     , A.ACCDESC ACC_DESCRIPTION\n" +
            "     , A.ACCSITE ACC_SITE\n" +
            "     , A.WAITINGPERIOD WAITING_PERIOD\n" +
            "     , A.REALPAY REAL_PAY_AMOUNT\n" +
            "     , A.STANDPAY STAND_PAY_AMOUNT\n" +
            "     , A.HOSPITALSTAY DAYS_IN_HOSPITAL\n" +
            "     , A.SPECCASEFLAG\n" +
            "     , 'LSS' LIAB_REASON\n" +
            "     , A.CLAIMOPERATOR\n" +
            "  FROM (select distinct c.*,gp.cvgid,gp.AMNTADDTYPE from sics_data.group_claim_upload c\n" +
            "      LEFT JOIN sics_data.group_product gp\n" +
            "        ON c.dutycode = gp.dutycode where gp.cvgid is not null) A\n" +
            ") t")
    int insertClaimMidTable();

    /**
     * HIS
     * */

    @Update("create table sics_data.his_grp_cession_upload_all as \n" +
            "            SELECT \n" +
            "                  a.group_schema_number\n" +
            "                , a.policy_number\n" +
            "                , a.bussno\n" +
            "                , a.polno\n" +
            "                , a.dutycode\n" +
            "                , a.trans_code\n" +
            "                , to_number(\n" +
            "                   substr(replace(a.bussno,'_',''),12,length(replace(a.bussno,'_','')))\n" +
            "                   ) transaction_number\n" +
            "                , a.sequence_number\n" +
            "                , a.proposal_number\n" +
            "                , a.sales_channel\n" +
            "                , a.company_code\n" +
            "                , a.branch\n" +
            "                , a.mcob\n" +
            "                , a.long_short_term\n" +
            "                , a.vat\n" +
            "                , A.TRAN_DATE TRAN_DATE_orn\n" +
            "        ,(case when A.TRANS_CODE in ( 'NI','NB') \n" +
            "          then (case when A.TRAN_DATE>A.TRANS_EFFECTIVE_DATE then A.TRAN_DATE else  A.TRANS_EFFECTIVE_DATE end)\n" +
            "          else A.TRAN_DATE end) TRAN_DATE\n" +
            "                , a.trans_effective_date\n" +
            "                , a.agent_number\n" +
            "                , a.area_code\n" +
            "                , a.policy_type\n" +
            "                , a.cvgid\n" +
            "                , a.AMNTADDTYPE  as  benefit_covered\n" +
            "                , a.riskcode\n" +
            "                , a.cvrg_indicator\n" +
            "                , a.policy_inception_date\n" +
            "                , a.currentdate\n" +
            "                , a.benefit_issue_date\n" +
            "                , a.benefit_termination_date\n" +
            "                , a.mortality\n" +
            "                , a.cession_currency\n" +
            "                , a.original_sum_assured\n" +
            "                , a.additional_mortality_ratio\n" +
            "                , a.sum_at_risk\n" +
            "                , a.effe_amnt\n" +
            "                , a.cash_value\n" +
            "                , a.bonus_sa\n" +
            "                , a.payment_mode\n" +
            "                , a.premium_pay_term\n" +
            "                , a.premium_amount\n" +
            "                , a.plan\n" +
            "                , a.deductiblemode\n" +
            "                , a.deductibled\n" +
            "                , a.payment_ratio\n" +
            "                , a.policyholder_id\n" +
            "                , a.policyholder_full_name\n" +
            "                , a.policyholder_gender\n" +
            "                , a.policyholder_date_of_birth\n" +
            "                , a.policyholder_smoker_status\n" +
            "                , a.policyholder_occupation_code\n" +
            "                , a.policyholder_socialsecsta\n" +
            "                , a.policyholder_ssecmedinssta\n" +
            "                , a.policyholder_corpsubsta\n" +
            "                , a.life1_id\n" +
            "                , a.life1_full_name\n" +
            "                , a.life1_gender\n" +
            "                , a.life1_date_of_birth\n" +
            "                , a.life1_original_age\n" +
            "                , a.life1_smoker_status\n" +
            "                , a.life1_occupation_code\n" +
            "                , a.life1_socialsecsta\n" +
            "                , a.life1_ssecmedinssta\n" +
            "                , a.life1_corpsubsta\n" +
            "                , a.joint_life_type\n" +
            "                , a.joint_life_desc\n" +
            "                , a.life2_id\n" +
            "                , a.life2_full_name\n" +
            "                , a.life2_gender\n" +
            "                , a.life2_date_of_birth\n" +
            "                , a.life2_original_age\n" +
            "                , a.life2_smoker_status\n" +
            "                , a.life2_occupation_code\n" +
            "                , a.life2_socialsecsta\n" +
            "                , a.life2_ssecmedinssta\n" +
            "                , a.life2_corpsubsta\n" +
            "                , a.underwriting_solution\n" +
            "                , a.loading_reason\n" +
            "                , a.loading_type\n" +
            "                , a.addprem\n" +
            "                , a.loading_percent_1\n" +
            "                , a.loading_percent_2\n" +
            "                , a.loading_percent\n" +
            "                , a.loading_amount\n" +
            "                , a.loading_amount_unit\n" +
            "                , a.loading_duration\n" +
            "                , a.loading_duration_unit\n" +
            "                , a.conttype\n" +
            "                , a.pushdate\n" +
            "                , a.insuredtype\n" +
            "                , a.idtype\n" +
            "                , a.idno\n" +
            "                , a.trantype\n" +
            "                , a.policyyear\n" +
            "                , a.initprem\n" +
            "                , a.constrtype\n" +
            "                , a.insurednum\n" +
            "                , a.archarea\n" +
            "                , a.archcost\n" +
            "                , a.purpose\n" +
            "                , a.constrsa\n" +
            "                , a.waitingperiod\n" +
            "                , a.rnflag\n" +
            "                , a.policy_inception_date_real\n" +
            "                , a.quotno\n" +
            "                , a.busicategory\n" +
            "                , a.disability\n" +
            "                , a.notes\n" +
            "                , a.polrcdate\n" +
            "                , a.prodpolno\n" +
            "                , a.mprpolno\n" +
            "                , b.reinsurance_date\n" +
            "                , a.GRP_EFF_DATE\n" +
            "        ,b.CVGID as CVGID_re\n" +
            "        ,b.RIPRECEPTNO\n" +
            "        , (\n" +
            "                       CASE b.RASNUM\n" +
            "                       WHEN 'CLRE01' THEN '70001'\n" +
            "                       WHEN 'QHRE01' THEN '70007'\n" +
            "                       WHEN 'HURE01' THEN '70000'\n" +
            "                       WHEN 'MURE01' THEN '70005'\n" +
            "                       WHEN 'TPRE01' THEN '70006'\n" +
            "                       WHEN 'SWRE01' THEN '70003'\n" +
            "                       WHEN 'GENE01' THEN '70004'\n" +
            "                       WHEN 'SCRE01' THEN '70002'\n" +
            "                       WHEN 'SCRE02' THEN '70009'\n" +
            "                       WHEN 'RCRE01' THEN 'RCRE01'\n" +
            "                       ELSE ''\n" +
            "                   END\n" +
            "                   ) AS rasnum\n" +
            "        , b.RASNUM as RASNUM_NAME\n" +
            "        , b.BUSSTYPE \n" +
            "        , b.AMNTADDTYPE \n" +
            "        , b.SUMATRISK \n" +
            "        , b.RA_AMOUNT\n" +
            "        , b.CUR_RA_PREM \n" +
            "        , b.CUR_RA_COMM,b.cur_ra_amount\n" +
            "              FROM  \n" +
            "        (  select distinct c.*,gp.cvgid,gp.AMNTADDTYPE, gp.riskcode  from sics_data.his_grp_cession_upload c\n" +
            "              LEFT JOIN sics_data.group_product gp\n" +
            "                ON c.dutycode = gp.dutycode where gp.cvgid is not null) a\n" +
            "              LEFT JOIN sics_data.his_grp_cession_rein b\n" +
            "                ON (\n" +
            "                       a.group_schema_number = b.policyno\n" +
            "                   AND a.policy_number = b.contno\n" +
            "                   AND a.polno = b.polno\n" +
            "                   AND a.trans_code = b.busstype\n" +
            "                   AND a.dutycode = b.dutycode\n" +
            "           and a.cvgid=b.cvgid\n" +
            "                   AND a.policy_type = b.finriskcode\n" +
            "                   AND a.policyyear = b.years\n" +
            "                   )\n" +
            "               WHERE a.long_short_term = 'LT'\n" +
            "            union all\n" +
            "            SELECT                       \n" +
            "                  a.group_schema_number\n" +
            "                , a.policy_number\n" +
            "                , a.bussno\n" +
            "                , a.polno\n" +
            "                , a.dutycode\n" +
            "                , a.trans_code\n" +
            "                , to_number(\n" +
            "                   substr(replace(a.bussno,'_',''),12,length(replace(a.bussno,'_','')))\n" +
            "                   ) transaction_number\n" +
            "                , a.sequence_number\n" +
            "                , a.proposal_number\n" +
            "                , a.sales_channel\n" +
            "                , a.company_code\n" +
            "                , a.branch\n" +
            "                , a.mcob\n" +
            "                , a.long_short_term\n" +
            "                , a.vat\n" +
            "                , A.TRAN_DATE TRAN_DATE_orn\n" +
            "        ,(case when A.TRANS_CODE in ( 'NI','NB') \n" +
            "          then (case when A.TRAN_DATE>A.TRANS_EFFECTIVE_DATE then A.TRAN_DATE else  A.TRANS_EFFECTIVE_DATE end)\n" +
            "          else A.TRAN_DATE end) TRAN_DATE\n" +
            "                , a.trans_effective_date\n" +
            "                , a.agent_number\n" +
            "                , a.area_code\n" +
            "                , a.policy_type\n" +
            "                , a.cvgid\n" +
            "                , a.AMNTADDTYPE  as  benefit_covered\n" +
            "                , a.riskcode\n" +
            "                , a.cvrg_indicator\n" +
            "                , a.policy_inception_date\n" +
            "                , a.currentdate\n" +
            "                , a.benefit_issue_date\n" +
            "                , a.benefit_termination_date\n" +
            "                , a.mortality\n" +
            "                , a.cession_currency\n" +
            "                , a.original_sum_assured\n" +
            "                , a.additional_mortality_ratio\n" +
            "                , a.sum_at_risk\n" +
            "                , a.effe_amnt\n" +
            "                , a.cash_value\n" +
            "                , a.bonus_sa\n" +
            "                , a.payment_mode\n" +
            "                , a.premium_pay_term\n" +
            "                , a.premium_amount\n" +
            "                , a.plan\n" +
            "                , a.deductiblemode\n" +
            "                , a.deductibled\n" +
            "                , a.payment_ratio\n" +
            "                , a.policyholder_id\n" +
            "                , a.policyholder_full_name\n" +
            "                , a.policyholder_gender\n" +
            "                , a.policyholder_date_of_birth\n" +
            "                , a.policyholder_smoker_status\n" +
            "                , a.policyholder_occupation_code\n" +
            "                , a.policyholder_socialsecsta\n" +
            "                , a.policyholder_ssecmedinssta\n" +
            "                , a.policyholder_corpsubsta\n" +
            "                , a.life1_id\n" +
            "                , a.life1_full_name\n" +
            "                , a.life1_gender\n" +
            "                , a.life1_date_of_birth\n" +
            "                , a.life1_original_age\n" +
            "                , a.life1_smoker_status\n" +
            "                , a.life1_occupation_code\n" +
            "                , a.life1_socialsecsta\n" +
            "                , a.life1_ssecmedinssta\n" +
            "                , a.life1_corpsubsta\n" +
            "                , a.joint_life_type\n" +
            "                , a.joint_life_desc\n" +
            "                , a.life2_id\n" +
            "                , a.life2_full_name\n" +
            "                , a.life2_gender\n" +
            "                , a.life2_date_of_birth\n" +
            "                , a.life2_original_age\n" +
            "                , a.life2_smoker_status\n" +
            "                , a.life2_occupation_code\n" +
            "                , a.life2_socialsecsta\n" +
            "                , a.life2_ssecmedinssta\n" +
            "                , a.life2_corpsubsta\n" +
            "                , a.underwriting_solution\n" +
            "                , a.loading_reason\n" +
            "                , a.loading_type\n" +
            "                , a.addprem\n" +
            "                , a.loading_percent_1\n" +
            "                , a.loading_percent_2\n" +
            "                , a.loading_percent\n" +
            "                , a.loading_amount\n" +
            "                , a.loading_amount_unit\n" +
            "                , a.loading_duration\n" +
            "                , a.loading_duration_unit\n" +
            "                , a.conttype\n" +
            "                , a.pushdate\n" +
            "                , a.insuredtype\n" +
            "                , a.idtype\n" +
            "                , a.idno\n" +
            "                , a.trantype\n" +
            "                , a.policyyear\n" +
            "                , a.initprem\n" +
            "                , a.constrtype\n" +
            "                , a.insurednum\n" +
            "                , a.archarea\n" +
            "                , a.archcost\n" +
            "                , a.purpose\n" +
            "                , a.constrsa\n" +
            "                , a.waitingperiod\n" +
            "                , a.rnflag\n" +
            "                , a.policy_inception_date_real\n" +
            "                , a.quotno\n" +
            "                , a.busicategory\n" +
            "                , a.disability\n" +
            "                , a.notes\n" +
            "                , a.polrcdate\n" +
            "                , a.prodpolno\n" +
            "                , a.mprpolno\n" +
            "                , b.reinsurance_date\n" +
            "                , a.GRP_EFF_DATE\n" +
            "        ,b.CVGID as CVGID_re\n" +
            "        ,b.RIPRECEPTNO\n" +
            "        , (\n" +
            "                       CASE b.RASNUM\n" +
            "                       WHEN 'CLRE01' THEN '70001'\n" +
            "                       WHEN 'QHRE01' THEN '70007'\n" +
            "                       WHEN 'HURE01' THEN '70000'\n" +
            "                       WHEN 'MURE01' THEN '70005'\n" +
            "                       WHEN 'TPRE01' THEN '70006'\n" +
            "                       WHEN 'SWRE01' THEN '70003'\n" +
            "                       WHEN 'GENE01' THEN '70004'\n" +
            "                       WHEN 'SCRE01' THEN '70002'\n" +
            "                       WHEN 'SCRE02' THEN '70009'\n" +
            "                       WHEN 'RCRE01' THEN 'RCRE01'\n" +
            "                       ELSE ''\n" +
            "                   END\n" +
            "                   ) AS rasnum\n" +
            "        , b.RASNUM as RASNUM_NAME\n" +
            "        , b.BUSSTYPE \n" +
            "        , b.AMNTADDTYPE \n" +
            "        , b.SUMATRISK \n" +
            "        , b.RA_AMOUNT\n" +
            "        , b.CUR_RA_PREM \n" +
            "        , b.CUR_RA_COMM,b.cur_ra_amount\n" +
            "              FROM \n" +
            "        (  select distinct c.*,gp.cvgid,gp.AMNTADDTYPE, gp.riskcode  from sics_data.his_grp_cession_upload c\n" +
            "              LEFT JOIN sics_data.group_product gp\n" +
            "                ON c.dutycode = gp.dutycode where gp.cvgid is not null) a\n" +
            "              LEFT JOIN SICS_DATA.his_grp_cession_rein B\n" +
            "                ON (\n" +
            "                       A.Group_Schema_Number = B.Policyno\n" +
            "                   AND A.Policy_Number = B.Contno\n" +
            "                   AND A.bussno = B.Bussno\n" +
            "                   AND A.PolNo = B.Polno\n" +
            "                   AND A.DutyCode = B.Dutycode\n" +
            "           and a.cvgid=b.cvgid\n" +
            "                   AND A.Policy_Type = B.Finriskcode\n" +
            "                   AND a.trans_code = B.busstype \n" +
            "               )\n" +
            "             where    A.TRANS_CODE not in ( 'NI','RR') and a.long_short_term = 'ST' \n" +
            "            union all\n" +
            "            SELECT \n" +
            "                  a.group_schema_number\n" +
            "                , a.policy_number\n" +
            "                , a.bussno\n" +
            "                , a.polno\n" +
            "                , a.dutycode\n" +
            "                , a.trans_code\n" +
            "                , to_number(\n" +
            "                   substr(replace(a.bussno,'_',''),12,length(replace(a.bussno,'_','')))\n" +
            "                   ) transaction_number\n" +
            "                , a.sequence_number\n" +
            "                , a.proposal_number\n" +
            "                , a.sales_channel\n" +
            "                , a.company_code\n" +
            "                , a.branch\n" +
            "                , a.mcob\n" +
            "                , a.long_short_term\n" +
            "                , a.vat\n" +
            "                , A.TRAN_DATE TRAN_DATE_orn\n" +
            "        ,(case when A.TRANS_CODE in ( 'NI','NB') \n" +
            "          then (case when A.TRAN_DATE>A.TRANS_EFFECTIVE_DATE then A.TRAN_DATE else  A.TRANS_EFFECTIVE_DATE end)\n" +
            "          else A.TRAN_DATE end) TRAN_DATE\n" +
            "                , a.trans_effective_date\n" +
            "                , a.agent_number\n" +
            "                , a.area_code\n" +
            "                , a.policy_type\n" +
            "                , a.cvgid\n" +
            "                , a.AMNTADDTYPE  as  benefit_covered\n" +
            "                , a.riskcode\n" +
            "                , a.cvrg_indicator\n" +
            "                , a.policy_inception_date\n" +
            "                , a.currentdate\n" +
            "                , a.benefit_issue_date\n" +
            "                , a.benefit_termination_date\n" +
            "                , a.mortality\n" +
            "                , a.cession_currency\n" +
            "                , a.original_sum_assured\n" +
            "                , a.additional_mortality_ratio\n" +
            "                , a.sum_at_risk\n" +
            "                , a.effe_amnt\n" +
            "                , a.cash_value\n" +
            "                , a.bonus_sa\n" +
            "                , a.payment_mode\n" +
            "                , a.premium_pay_term\n" +
            "                , a.premium_amount\n" +
            "                , a.plan\n" +
            "                , a.deductiblemode\n" +
            "                , a.deductibled\n" +
            "                , a.payment_ratio\n" +
            "                , a.policyholder_id\n" +
            "                , a.policyholder_full_name\n" +
            "                , a.policyholder_gender\n" +
            "                , a.policyholder_date_of_birth\n" +
            "                , a.policyholder_smoker_status\n" +
            "                , a.policyholder_occupation_code\n" +
            "                , a.policyholder_socialsecsta\n" +
            "                , a.policyholder_ssecmedinssta\n" +
            "                , a.policyholder_corpsubsta\n" +
            "                , a.life1_id\n" +
            "                , a.life1_full_name\n" +
            "                , a.life1_gender\n" +
            "                , a.life1_date_of_birth\n" +
            "                , a.life1_original_age\n" +
            "                , a.life1_smoker_status\n" +
            "                , a.life1_occupation_code\n" +
            "                , a.life1_socialsecsta\n" +
            "                , a.life1_ssecmedinssta\n" +
            "                , a.life1_corpsubsta\n" +
            "                , a.joint_life_type\n" +
            "                , a.joint_life_desc\n" +
            "                , a.life2_id\n" +
            "                , a.life2_full_name\n" +
            "                , a.life2_gender\n" +
            "                , a.life2_date_of_birth\n" +
            "                , a.life2_original_age\n" +
            "                , a.life2_smoker_status\n" +
            "                , a.life2_occupation_code\n" +
            "                , a.life2_socialsecsta\n" +
            "                , a.life2_ssecmedinssta\n" +
            "                , a.life2_corpsubsta\n" +
            "                , a.underwriting_solution\n" +
            "                , a.loading_reason\n" +
            "                , a.loading_type\n" +
            "                , a.addprem\n" +
            "                , a.loading_percent_1\n" +
            "                , a.loading_percent_2\n" +
            "                , a.loading_percent\n" +
            "                , a.loading_amount\n" +
            "                , a.loading_amount_unit\n" +
            "                , a.loading_duration\n" +
            "                , a.loading_duration_unit\n" +
            "                , a.conttype\n" +
            "                , a.pushdate\n" +
            "                , a.insuredtype\n" +
            "                , a.idtype\n" +
            "                , a.idno\n" +
            "                , a.trantype\n" +
            "                , a.policyyear\n" +
            "                , a.initprem\n" +
            "                , a.constrtype\n" +
            "                , a.insurednum\n" +
            "                , a.archarea\n" +
            "                , a.archcost\n" +
            "                , a.purpose\n" +
            "                , a.constrsa\n" +
            "                , a.waitingperiod\n" +
            "                , a.rnflag\n" +
            "                , a.policy_inception_date_real\n" +
            "                , a.quotno\n" +
            "                , a.busicategory\n" +
            "                , a.disability\n" +
            "                , a.notes\n" +
            "                , a.polrcdate\n" +
            "                , a.prodpolno\n" +
            "                , a.mprpolno\n" +
            "                , b.reinsurance_date\n" +
            "                , a.GRP_EFF_DATE\n" +
            "        ,b.CVGID as CVGID_re\n" +
            "        ,b.RIPRECEPTNO\n" +
            "        , (\n" +
            "                       CASE b.RASNUM\n" +
            "                       WHEN 'CLRE01' THEN '70001'\n" +
            "                       WHEN 'QHRE01' THEN '70007'\n" +
            "                       WHEN 'HURE01' THEN '70000'\n" +
            "                       WHEN 'MURE01' THEN '70005'\n" +
            "                       WHEN 'TPRE01' THEN '70006'\n" +
            "                       WHEN 'SWRE01' THEN '70003'\n" +
            "                       WHEN 'GENE01' THEN '70004'\n" +
            "                       WHEN 'SCRE01' THEN '70002'\n" +
            "                       WHEN 'SCRE02' THEN '70009'\n" +
            "                       WHEN 'RCRE01' THEN 'RCRE01'\n" +
            "                       ELSE ''\n" +
            "                   END\n" +
            "                   ) AS rasnum\n" +
            "        , b.RASNUM as RASNUM_NAME\n" +
            "        , b.BUSSTYPE \n" +
            "        , b.AMNTADDTYPE \n" +
            "        , b.SUMATRISK \n" +
            "        , b.RA_AMOUNT\n" +
            "        , b.CUR_RA_PREM \n" +
            "        , b.CUR_RA_COMM,b.cur_ra_amount\n" +
            "              FROM \n" +
            "        (  select distinct c.*,gp.cvgid,gp.AMNTADDTYPE, gp.riskcode  from sics_data.his_grp_cession_upload c\n" +
            "              LEFT JOIN sics_data.group_product gp\n" +
            "                ON c.dutycode = gp.dutycode where gp.cvgid is not null) a\n" +
            "                  LEFT JOIN SICS_DATA.his_grp_cession_rein B\n" +
            "                  ON (A.Group_Schema_Number = B.Policyno\n" +
            "                  AND A.Policy_Number       = B.Contno\n" +
            "                  and A.Group_Schema_Number = B.Bussno\n" +
            "                  AND A.PolNo               = B.Polno\n" +
            "          and a.cvgid=b.cvgid\n" +
            "                  AND A.DutyCode            = B.Dutycode\n" +
            "                  AND A.Policy_Type         = B.Finriskcode\n" +
            "          AND B.BUSSTYPE = 'NB'\n" +
            "                  ) \n" +
            "                where \n" +
            "                    A.TRANS_CODE in ( 'NI','RR')\n" +
            "                and a.long_short_term = 'ST' ")
    int createCessionAllTableHis();

    @Select("CREATE INDEX OBJID on SICS_DATA.HIS_GRP_CESSION_UPLOAD_ALL (POLICY_NUMBER, POLICY_TYPE, CVGID, LIFE1_ID, BUSSNO, TRANS_CODE, TRAN_DATE)")
    void createCessionAllTableHisKey();

    @Select("CREATE INDEX OBJECTID on sics_data.HIS_GROUP_CESSION_UPLOAD (POLICY_NUMBER,POLICY_TYPE)")
    void createsKey();
    @Select("CREATE INDEX OBJID2 on SICS_DATA.group_cession_upload_ALL (POLICY_NUMBER, POLICY_TYPE, CVGID, LIFE1_ID, BUSSNO, TRANS_CODE, TRAN_DATE)")
    void createCessionAllTableKey();

    @DS("HA_DATA_PROD1")
    @Insert("insert into sics_data.group_cession_upload_mid\n" +
            "    select id_sequence.nextval as object_id,\n" +
            "    t.* from (\n" +
            "        select distinct\n" +
            "    A.GROUP_SCHEMA_NUMBER groschema_number,\n" +
            "    A.POLICY_NUMBER,\n" +
            "    A.BUSSNO,\n" +
            "    A.POLNO,\n" +
            "    A.DUTYCODE,\n" +
            "    A.TRANS_CODE,\n" +
            "    concat(A.POLICY_NUMBER,a.Cvgid) SEQUENCE_NUMBER,\n" +
            "    A.PROPOSAL_NUMBER,\n" +
            "    A.SALES_CHANNEL,\n" +
            "    A.COMPANY_CODE,\n" +
            "    A.BRANCH,\n" +
            "    A.MCOB,\n" +
            "    A.LONG_SHORT_TERM,\n" +
            "    A.VAT,\n" +
            "    substr(replace(A.BUSSNO,'_',''),12,length(replace(A.BUSSNO,'_',''))) TRANSACTION_NUMBER,\n" +
            "    A.TRAN_DATE,\n" +
            "    A.TRANS_EFFECTIVE_DATE,\n" +
            "    A.AGENT_NUMBER,\n" +
            "    A.AREA_CODE,\n" +
            "    A.POLICY_TYPE,\n" +
            "    a.Cvgid INSURANCE_PRODUCT_CEDENT_CODE,\n" +
            "    a.benefit_covered BENEFIT_COVERED,\n" +
            "    A.CVRG_INDICATOR,\n" +
            "    A.POLICY_INCEPTION_DATE,\n" +
            "    A.CURRENTDATE,\n" +
            "    A.BENEFIT_ISSUE_DATE,\n" +
            "    A.BENEFIT_TERMINATION_DATE,\n" +
            "    A.MORTALITY,\n" +
            "    A.CESSION_CURRENCY,\n" +
            "    A.ORIGINAL_SUM_ASSURED,\n" +
            "    A.ADDITIONAL_MORTALITY_RATIO,\n" +
            "    A.SUMATRISK SUM_AT_RISK,\n" +
            "    A.EFFE_AMNT,\n" +
            "    A.CASH_VALUE,\n" +
            "    A.BONUS_SA,\n" +
            "    A.PAYMENT_MODE,\n" +
            "    A.PREMIUM_PAY_TERM,\n" +
            "    A.PREMIUM_AMOUNT,\n" +
            "    A.PLAN,\n" +
            "    A.DEDUCTIBLEMODE,\n" +
            "    A.DEDUCTIBLED,\n" +
            "    A.PAYMENT_RATIO,\n" +
            "    A.POLICYHOLDER_ID,\n" +
            "    A.POLICYHOLDER_FULL_NAME,\n" +
            "    A.BusiCategory,\n" +
            "    A.POLICYHOLDER_GENDER,\n" +
            "    A.POLICYHOLDER_DATE_OF_BIRTH,\n" +
            "    A.POLICYHOLDER_SMOKER_STATUS,\n" +
            "    A.POLICYHOLDER_OCCUPATION_CODE,\n" +
            "    A.POLICYHOLDER_SOCIALSECSTA,\n" +
            "    A.POLICYHOLDER_SSECMEDINSSTA,\n" +
            "    A.POLICYHOLDER_CORPSUBSTA,\n" +
            "    A.LIFE1_ID,\n" +
            "    A.LIFE1_FULL_NAME,\n" +
            "    A.LIFE1_GENDER,\n" +
            "    A.LIFE1_DATE_OF_BIRTH,\n" +
            "    A.LIFE1_ORIGINAL_AGE,\n" +
            "    A.LIFE1_SMOKER_STATUS,\n" +
            "    case when instr(A.LIFE1_OCCUPATION_CODE, '0') <= 0 then concat('0',A.LIFE1_OCCUPATION_CODE)\n" +
            "    else LIFE1_OCCUPATION_CODE end LIFE1_OCCUPATION_CODE,\n" +
            "    case when A.LIFE1_SOCIALSECSTA is null then 'N'\n" +
            "    when A.LIFE1_SOCIALSECSTA ='0' then 'N'\n" +
            "    else 'Y' end LIFE1_SOCIALSECSTA,\n" +
            "    case when A.LIFE1_SSECMEDINSSTA is null then 'N'\n" +
            "    when A.LIFE1_SSECMEDINSSTA ='0' then 'N'\n" +
            "    else 'Y' end LIFE1_SSECMEDINSSTA,\n" +
            "    case when A.LIFE1_CORPSUBSTA is null then 'N'\n" +
            "    when A.LIFE1_CORPSUBSTA ='0' then 'N'\n" +
            "    else 'Y' end LIFE1_CORPSUBSTA,\n" +
            "    A.JOINT_LIFE_TYPE,\n" +
            "    A.JOINT_LIFE_DESC,\n" +
            "    A.LIFE2_ID,\n" +
            "    A.LIFE2_FULL_NAME,\n" +
            "    A.LIFE2_GENDER,\n" +
            "    A.LIFE2_DATE_OF_BIRTH,\n" +
            "    A.LIFE2_ORIGINAL_AGE,\n" +
            "    A.LIFE2_SMOKER_STATUS,\n" +
            "    A.LIFE2_OCCUPATION_CODE,\n" +
            "    A.LIFE2_SOCIALSECSTA,\n" +
            "    A.LIFE2_SSECMEDINSSTA,\n" +
            "    A.LIFE2_CORPSUBSTA,\n" +
            "    (CASE  WHEN a.CVGID is null THEN 'N'\n" +
            "      when a.CVGID is not null then A.Underwriting_Solution end) UNDERWRITING_SOLUTION,\n" +
            "    A.LOADING_REASON,\n" +
            "    A.LOADING_TYPE,\n" +
            "    A.ADDPREM,\n" +
            "    A.LOADING_PERCENT_1,\n" +
            "    A.LOADING_PERCENT_2,\n" +
            "    A.LOADING_PERCENT,\n" +
            "    A.LOADING_AMOUNT,\n" +
            "    A.LOADING_AMOUNT_UNIT,\n" +
            "    A.LOADING_DURATION,\n" +
            "    A.LOADING_DURATION_UNIT,\n" +
            "    A.CONTTYPE,\n" +
            "    A.PUSHDATE,\n" +
            "    A.INSUREDTYPE,\n" +
            "    A.IDTYPE,\n" +
            "    A.IDNO,\n" +
            "    A.TRANTYPE,\n" +
            "    A.POLICYYEAR,\n" +
            "    A.INITPREM,\n" +
            "    A.CONSTRTYPE,\n" +
            "    A.INSUREDNUM,\n" +
            "    A.ARCHAREA,\n" +
            "    A.ARCHCOST,\n" +
            "    A.PURPOSE,\n" +
            "    A.CONSTRSA,\n" +
            "    A.WAITINGPERIOD,\n" +
            "    A.RNFLAG,\n" +
            "    A.POLICY_INCEPTION_DATE_REAL,\n" +
            "    a.ripreceptno RIPRECEPTNO,\n" +
            "    a.reinsurance_date as reindate,\n" +
            "    a.ripreceptno planno,\n" +
            "    'NOTSPEC' GRCode,\n" +
            "    'NOTSPEC' PCode,\n" +
            "    A.Quotno  QUOTNO,\n" +
            "    a.disability,\n" +
            "    a.notes,\n" +
            "    a.polrcdate,\n" +
            "    a.prodpolno,\n" +
            "    a.mprpolno,\n" +
            "    a.riskcode,\n" +
            "    a.GRP_EFF_DATE,'' ORGPLANNO,A.SUMATRISK SAR,a.TRAN_DATE_orn,'' TF_IND\n" +
            "    from sics_data.his_grp_cession_upload_all A\n" +
            "    where a.POLICY_NUMBER||a.POLICY_TYPE||a.Cvgid||a.LIFE1_ID||a.BUSSNO||a.TRANS_CODE\n" +
            "          in(select tmp.pol_no||tmp.cedant_ces_nber||tmp.BUSSNO||tmp.TRANS_CODE from sicslife_ceded1.dx_grp_cession_del tmp)\n" +
            "union\n" +
            "    select distinct\n" +
            "    A.GROUP_SCHEMA_NUMBER groschema_number,\n" +
            "    A.POLICY_NUMBER,\n" +
            "    A.BUSSNO,\n" +
            "    A.POLNO,\n" +
            "    A.DUTYCODE,\n" +
            "    A.TRANS_CODE,\n" +
            "    concat(A.POLICY_NUMBER,a.Cvgid) SEQUENCE_NUMBER,\n" +
            "    A.PROPOSAL_NUMBER,\n" +
            "    A.SALES_CHANNEL,\n" +
            "    A.COMPANY_CODE,\n" +
            "    A.BRANCH,\n" +
            "    A.MCOB,\n" +
            "    A.LONG_SHORT_TERM,\n" +
            "    A.VAT,\n" +
            "    substr(replace(A.BUSSNO,'_',''),12,length(replace(A.BUSSNO,'_',''))) TRANSACTION_NUMBER,\n" +
            "    A.TRAN_DATE,\n" +
            "    A.TRANS_EFFECTIVE_DATE,\n" +
            "    A.AGENT_NUMBER,\n" +
            "    A.AREA_CODE,\n" +
            "    A.POLICY_TYPE,\n" +
            "    a.Cvgid INSURANCE_PRODUCT_CEDENT_CODE,\n" +
            "    a.benefit_covered BENEFIT_COVERED,\n" +
            "    A.CVRG_INDICATOR,\n" +
            "    A.POLICY_INCEPTION_DATE,\n" +
            "    A.CURRENTDATE,\n" +
            "    A.BENEFIT_ISSUE_DATE,\n" +
            "    A.BENEFIT_TERMINATION_DATE,\n" +
            "    A.MORTALITY,\n" +
            "    A.CESSION_CURRENCY,\n" +
            "    A.ORIGINAL_SUM_ASSURED,\n" +
            "    A.ADDITIONAL_MORTALITY_RATIO,\n" +
            "    A.SUMATRISK SUM_AT_RISK,\n" +
            "    A.EFFE_AMNT,\n" +
            "    A.CASH_VALUE,\n" +
            "    A.BONUS_SA,\n" +
            "    A.PAYMENT_MODE,\n" +
            "    A.PREMIUM_PAY_TERM,\n" +
            "    A.PREMIUM_AMOUNT,\n" +
            "    A.PLAN,\n" +
            "    A.DEDUCTIBLEMODE,\n" +
            "    A.DEDUCTIBLED,\n" +
            "    A.PAYMENT_RATIO,\n" +
            "    A.POLICYHOLDER_ID,\n" +
            "    A.POLICYHOLDER_FULL_NAME,\n" +
            "    A.BusiCategory,\n" +
            "    A.POLICYHOLDER_GENDER,\n" +
            "    A.POLICYHOLDER_DATE_OF_BIRTH,\n" +
            "    A.POLICYHOLDER_SMOKER_STATUS,\n" +
            "    A.POLICYHOLDER_OCCUPATION_CODE,\n" +
            "    A.POLICYHOLDER_SOCIALSECSTA,\n" +
            "    A.POLICYHOLDER_SSECMEDINSSTA,\n" +
            "    A.POLICYHOLDER_CORPSUBSTA,\n" +
            "    A.LIFE1_ID,\n" +
            "    A.LIFE1_FULL_NAME,\n" +
            "    A.LIFE1_GENDER,\n" +
            "    A.LIFE1_DATE_OF_BIRTH,\n" +
            "    A.LIFE1_ORIGINAL_AGE,\n" +
            "    A.LIFE1_SMOKER_STATUS,\n" +
            "    case when instr(A.LIFE1_OCCUPATION_CODE, '0') <= 0 then concat('0',A.LIFE1_OCCUPATION_CODE)\n" +
            "    else LIFE1_OCCUPATION_CODE end LIFE1_OCCUPATION_CODE,\n" +
            "    case when A.LIFE1_SOCIALSECSTA is null then 'N'\n" +
            "    when A.LIFE1_SOCIALSECSTA ='0' then 'N'\n" +
            "    else 'Y' end LIFE1_SOCIALSECSTA,\n" +
            "    case when A.LIFE1_SSECMEDINSSTA is null then 'N'\n" +
            "    when A.LIFE1_SSECMEDINSSTA ='0' then 'N'\n" +
            "    else 'Y' end LIFE1_SSECMEDINSSTA,\n" +
            "    case when A.LIFE1_CORPSUBSTA is null then 'N'\n" +
            "    when A.LIFE1_CORPSUBSTA ='0' then 'N'\n" +
            "    else 'Y' end LIFE1_CORPSUBSTA,\n" +
            "    A.JOINT_LIFE_TYPE,\n" +
            "    A.JOINT_LIFE_DESC,\n" +
            "    A.LIFE2_ID,\n" +
            "    A.LIFE2_FULL_NAME,\n" +
            "    A.LIFE2_GENDER,\n" +
            "    A.LIFE2_DATE_OF_BIRTH,\n" +
            "    A.LIFE2_ORIGINAL_AGE,\n" +
            "    A.LIFE2_SMOKER_STATUS,\n" +
            "    A.LIFE2_OCCUPATION_CODE,\n" +
            "    A.LIFE2_SOCIALSECSTA,\n" +
            "    A.LIFE2_SSECMEDINSSTA,\n" +
            "    A.LIFE2_CORPSUBSTA,\n" +
            "    (CASE  WHEN a.CVGID is null THEN 'N'\n" +
            "      when a.CVGID is not null then A.Underwriting_Solution end) UNDERWRITING_SOLUTION,\n" +
            "    A.LOADING_REASON,\n" +
            "    A.LOADING_TYPE,\n" +
            "    A.ADDPREM,\n" +
            "    A.LOADING_PERCENT_1,\n" +
            "    A.LOADING_PERCENT_2,\n" +
            "    A.LOADING_PERCENT,\n" +
            "    A.LOADING_AMOUNT,\n" +
            "    A.LOADING_AMOUNT_UNIT,\n" +
            "    A.LOADING_DURATION,\n" +
            "    A.LOADING_DURATION_UNIT,\n" +
            "    A.CONTTYPE,\n" +
            "    A.PUSHDATE,\n" +
            "    A.INSUREDTYPE,\n" +
            "    A.IDTYPE,\n" +
            "    A.IDNO,\n" +
            "    A.TRANTYPE,\n" +
            "    A.POLICYYEAR,\n" +
            "    A.INITPREM,\n" +
            "    A.CONSTRTYPE,\n" +
            "    A.INSUREDNUM,\n" +
            "    A.ARCHAREA,\n" +
            "    A.ARCHCOST,\n" +
            "    A.PURPOSE,\n" +
            "    A.CONSTRSA,\n" +
            "    A.WAITINGPERIOD,\n" +
            "    A.RNFLAG,\n" +
            "    A.POLICY_INCEPTION_DATE_REAL,\n" +
            "    a.ripreceptno RIPRECEPTNO,\n" +
            "    a.reinsurance_date as reindate,\n" +
            "    a.ripreceptno planno,\n" +
            "    'NOTSPEC' GRCode,\n" +
            "    'NOTSPEC' PCode,\n" +
            "    A.Quotno  QUOTNO,\n" +
            "    a.disability,\n" +
            "    a.notes,\n" +
            "    a.polrcdate,\n" +
            "    a.prodpolno,\n" +
            "    a.mprpolno,\n" +
            "    a.riskcode,\n" +
            "    a.GRP_EFF_DATE,'' ORGPLANNO,A.SUMATRISK SAR,a.TRAN_DATE_orn,'' TF_IND\n" +
            "    from sics_data.group_cession_upload_ALL A\n" +
            "    where a.POLICY_NUMBER||a.POLICY_TYPE||a.Cvgid||a.LIFE1_ID||a.BUSSNO||a.TRANS_CODE\n" +
            "          in(select tmp.pol_no||tmp.cedant_ces_nber||tmp.BUSSNO||tmp.TRANS_CODE from sicslife_ceded1.dx_grp_cession_del tmp)\n" +
            "   ) t")
    int insertCessionMidTableHis();

    @Update("update sics_data.group_cession_upload_mid t  set t.planno=t.planno||'del' ,t.ORGPLANNO=t.planno||'del'  where t.planno in(\n" +
            "       select t.planno from sics_data.planmapping_his  t\n" +
            ") and t.planno not like '%del'")
    int updateTreatyAddData();


    @DS("HA_DATA_PROD1")
    @Insert("insert into sics_data.group_cession_claim_mid\n" +
            "select id_sequence.nextval  as object_id, t.* from ( \n" +
            "SELECT  DISTINCT\n" +
            "      A.RGTNO\n" +
            "     , a.caseno\n" +
            "     , A.PolicyNo POLICY_NO\n" +
            "     , A.Contno\n" +
            "     , substr(a.caseno,9) TRANNO\n" +
            "     , A.TRANTYPE TRAN_TYPE\n" +
            "     , A.GIVETYPE GIVE_TYPE\n" +
            "     , A.ACCDATE\n" +
            "     , A.REPORTDATE\n" +
            "     , A.RGTDate\n" +
            "     , A.ENDCASEDATE\n" +
            "     , A.VALDATE\n" +
            "     , A.INSUREDNO\n" +
            "     , A.DUTYCODE\n" +
            "     , A.GETDUTYCODE\n" +
            "     , A.FINRISKCODE\n" +
            "     , a.amntaddtype CLAIM_BENEFIT_COVER\n" +
            "     , a.cvgid CVGId\n" +
            "     , A.ACCREASON\n" +
            "     , '01' LIABILITY_TYPE\n" +
            "     , A.ACCDESC ACC_DESCRIPTION\n" +
            "     , A.ACCSITE ACC_SITE\n" +
            "     , A.WAITINGPERIOD WAITING_PERIOD\n" +
            "     , A.REALPAY REAL_PAY_AMOUNT\n" +
            "     , A.STANDPAY STAND_PAY_AMOUNT\n" +
            "     , A.HOSPITALSTAY DAYS_IN_HOSPITAL\n" +
            "     , A.SPECCASEFLAG\n" +
            "     , 'LSS' LIAB_REASON\n" +
            "     , A.CLAIMOPERATOR\n" +
            "  FROM (select distinct c.*,gp.cvgid,gp.AMNTADDTYPE from sics_data.group_claim_upload c\n" +
            "      LEFT JOIN sics_data.group_product gp\n" +
            "        ON c.dutycode = gp.dutycode where gp.cvgid is not null) A\n" +
            "       where  a.Contno||a.FINRISKCODE||a.cvgid||a.insuredno||a.RGTNO in(select tmp.pol_no||tmp.cedant_ces_nber||tmp.BUSSNO from sicslife_ceded1.dx_grp_cession_del tmp)\n" +
            "union\n" +
            "SELECT  DISTINCT\n" +
            "      A.RGTNO\n" +
            "     , a.caseno\n" +
            "     , A.PolicyNo POLICY_NO\n" +
            "     , A.Contno\n" +
            "     , substr(a.caseno,9) TRANNO\n" +
            "     , A.TRANTYPE TRAN_TYPE\n" +
            "     , A.GIVETYPE GIVE_TYPE\n" +
            "     , A.ACCDATE\n" +
            "     , A.REPORTDATE\n" +
            "     , A.RGTDate\n" +
            "     , A.ENDCASEDATE\n" +
            "     , A.VALDATE\n" +
            "     , A.INSUREDNO\n" +
            "     , A.DUTYCODE\n" +
            "     , A.GETDUTYCODE\n" +
            "     , A.FINRISKCODE\n" +
            "     , a.amntaddtype CLAIM_BENEFIT_COVER\n" +
            "     , a.cvgid CVGId\n" +
            "     , A.ACCREASON\n" +
            "     , '01' LIABILITY_TYPE\n" +
            "     , A.ACCDESC ACC_DESCRIPTION\n" +
            "     , A.ACCSITE ACC_SITE\n" +
            "     , A.WAITINGPERIOD WAITING_PERIOD\n" +
            "     , A.REALPAY REAL_PAY_AMOUNT\n" +
            "     , A.STANDPAY STAND_PAY_AMOUNT\n" +
            "     , A.HOSPITALSTAY DAYS_IN_HOSPITAL\n" +
            "     , A.SPECCASEFLAG\n" +
            "     , 'LSS' LIAB_REASON\n" +
            "     , A.CLAIMOPERATOR\n" +
            "  FROM (select distinct c.*,gp.cvgid,gp.AMNTADDTYPE from sics_data.his_grp_claim_upload c\n" +
            "      LEFT JOIN sics_data.group_product gp\n" +
            "        ON c.dutycode = gp.dutycode where gp.cvgid is not null) A  \n" +
            "         where  a.Contno||a.FINRISKCODE||a.cvgid||a.insuredno||a.RGTNO in(select tmp.pol_no||tmp.cedant_ces_nber||tmp.BUSSNO from sicslife_ceded1.dx_grp_cession_del tmp)\n" +
            ") t")
    int insertClaimMidTableHis();

    @Update("UPDATE GROUP_CESSION_UPLOAD_MID a\n" +
            "SET a.TF_IND = CASE \n" +
            "               WHEN planno = 'NOTSPEC' OR EXISTS (SELECT 1 FROM planchangmapping b WHERE b.RIPRECEPTNO = a.PLANNO) THEN 'TREATY'\n" +
            "               ELSE 'FAC'\n" +
            "             END" +
            " WHERE PLANNO IS NOT NULL")
    int changeReinsuranceByPlanNo();

    @Update("UPDATE GROUP_CESSION_UPLOAD_MID\n" +
            "SET TF_IND = CASE\n" +
            "    WHEN UNDERWRITING_SOLUTION = 'N' THEN 'RETENTION'\n" +
            "    ELSE TF_IND\n" +
            "END")
    int changeReinsuranceBySolution();



    @Delete("delete from GROUP_CESSION_UPLOAD_MID a where a.TRANS_CODE in ('IB','AC')")
    int deleteTranCodeByIBAC();

    @DS("HA_DATA_PROD1")
    void compareCession(Boolean dx,String suffix,String leftDate,String rightDate);

    @DS("HA_DATA_PROD1")
    void compareClaim(Boolean dx,String suffix,String leftDate,String rightDate);





}
