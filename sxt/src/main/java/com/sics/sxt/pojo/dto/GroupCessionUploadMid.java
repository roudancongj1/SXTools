package com.sics.sxt.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * @Auth:GuangYun
 */
@Data
@HeadFontStyle(fontHeightInPoints = 12,fontName = "Segoe UI")
@ContentFontStyle(fontHeightInPoints = 12,fontName = "Segoe UI")
@TableName("GROUP_CESSION_UPLOAD_MID")
public class GroupCessionUploadMid implements Serializable {

    @ExcelProperty("OBJECT_ID")
    @TableId("OBJECT_ID")
    private BigDecimal objectId;

    @ExcelProperty("GROUP_SCHEMA_NUMBER")
    @TableField("GROUP_SCHEMA_NUMBER")
    private String groupSchemaNumber;

    @ExcelProperty("POLICY_NUMBER")
    @TableField("POLICY_NUMBER")
    private String policyNumber;

    @ExcelProperty("BUSSNO")
    @TableField("BUSSNO")
    private String bussNo;

    @ExcelProperty("POLNO")
    @TableField("POLNO")
    private String polNo;

    @ExcelProperty("DUTYCODE")
    @TableField("DUTYCODE")
    private String dutyCode;

    @ExcelProperty("TRANS_CODE")
    @TableField("TRANS_CODE")
    private String transCode;

    @ExcelProperty("SEQUENCE_NUMBER")
    @TableField("SEQUENCE_NUMBER")
    private String sequenceNumber;

    @ExcelProperty("PROPOSAL_NUMBER")
    @TableField("PROPOSAL_NUMBER")
    private String proposalNumber;

    @ExcelProperty("SALES_CHANNEL")
    @TableField("SALES_CHANNEL")
    private String salesChannel;

    @ExcelProperty("COMPANY_CODE")
    @TableField("COMPANY_CODE")
    private String companyCode;

    @ExcelProperty("BRANCH")
    @TableField("BRANCH")
    private String branch;

    @ExcelProperty("MCOB")
    @TableField("MCOB")
    private String mcob;

    @ExcelProperty("LONG_SHORT_TERM")
    @TableField("LONG_SHORT_TERM")
    private String longShortTerm;

    @ExcelProperty("VAT")
    @TableField("VAT")
    private String vat;

    @ExcelProperty("TRANSACTION_NUMBER")
    @TableField("TRANSACTION_NUMBER")
    private String transactionNumber;

    @ExcelProperty("TRAN_DATE")
    @TableField("TRAN_DATE")
    private String tranDate;

    @ExcelProperty("TRANS_EFFECTIVE_DATE")
    @TableField("TRANS_EFFECTIVE_DATE")
    private String transEffectiveDate;

    @ExcelProperty("AGENT_NUMBER")
    @TableField("AGENT_NUMBER")
    private String agentNumber;

    @ExcelProperty("AREA_CODE")
    @TableField("AREA_CODE")
    private String areaCode;

    @ExcelProperty("POLICY_TYPE")
    @TableField("POLICY_TYPE")
    private String policyType;

    @ExcelProperty("INSURANCE_PRODUCT_CEDENT_CODE")
    @TableField("INSURANCE_PRODUCT_CEDENT_CODE")
    private String insuranceProductCedentCode;

    @ExcelProperty("BENEFIT_COVERED")
    @TableField("BENEFIT_COVERED")
    private String benefitCovered;

    @ExcelProperty("CVRG_INDICATOR")
    @TableField("CVRG_INDICATOR")
    private String cvrgIndicator;

    @ExcelProperty("POLICY_INCEPTION_DATE")
    @TableField("POLICY_INCEPTION_DATE")
    private String policyInceptionDate;

    @ExcelProperty("CURRENTDATE")
    @TableField("CURRENTDATE")
    private String currentDate;

    @ExcelProperty("BENEFIT_ISSUE_DATE")
    @TableField("BENEFIT_ISSUE_DATE")
    private String benefitIssueDate;

    @ExcelProperty("BENEFIT_TERMINATION_DATE")
    @TableField("BENEFIT_TERMINATION_DATE")
    private String benefitTerminationDate;

    @ExcelProperty("MORTALITY")
    @TableField("MORTALITY")
    private String mortality;

    @ExcelProperty("CESSION_CURRENCY")
    @TableField("CESSION_CURRENCY")
    private String cessionCurrency;

    @ExcelProperty("ORIGINAL_SUM_ASSURED")
    @TableField("ORIGINAL_SUM_ASSURED")
    private BigDecimal originalSumAssured;

    @ExcelProperty("ADDITIONAL_MORTALITY_RATIO")
    @TableField("ADDITIONAL_MORTALITY_RATIO")
    private String additionalMortalityRatio;

    @ExcelProperty("SUM_AT_RISK")
    @TableField("SUM_AT_RISK")
    private String sumAtRisk;

    @ExcelProperty("EFFE_AMNT")
    @TableField("EFFE_AMNT")
    private BigDecimal effeAmnt;

    @ExcelProperty("CASH_VALUE")
    @TableField("CASH_VALUE")
    private BigDecimal cashValue;

    @ExcelProperty("BONUS_SA")
    @TableField("BONUS_SA")
    private BigDecimal bonusSa;

    @ExcelProperty("PAYMENT_MODE")
    @TableField("PAYMENT_MODE")
    private String paymentMode;

    @ExcelProperty("PREMIUM_PAY_TERM")
    @TableField("PREMIUM_PAY_TERM")
    private String premiumPayTerm;

    @ExcelProperty("PREMIUM_AMOUNT")
    @TableField("PREMIUM_AMOUNT")
    private BigDecimal premiumAmount;

    @ExcelProperty("PLAN")
    @TableField("PLAN")
    private String plan;

    @ExcelProperty("DEDUCTIBLEMODE")
    @TableField("DEDUCTIBLEMODE")
    private String deductibleMode;

    @ExcelProperty("DEDUCTIBLED")
    @TableField("DEDUCTIBLED")
    private BigDecimal deductibled;

    @ExcelProperty("PAYMENT_RATIO")
    @TableField("PAYMENT_RATIO")
    private BigDecimal paymentRatio;

    @ExcelProperty("POLICYHOLDER_ID")
    @TableField("POLICYHOLDER_ID")
    private String policyholderId;

    @ExcelProperty("POLICYHOLDER_FULL_NAME")
    @TableField("POLICYHOLDER_FULL_NAME")
    private String policyholderFullName;

    @ExcelProperty("BUSICATEGORY")
    @TableField("BUSICATEGORY")
    private String busicategory;

    @ExcelProperty("POLICYHOLDER_GENDER")
    @TableField("POLICYHOLDER_GENDER")
    private String policyholderGender;

    @ExcelProperty("POLICYHOLDER_DATE_OF_BIRTH")
    @TableField("POLICYHOLDER_DATE_OF_BIRTH")
    private String policyholderDateOfBirth;

    @ExcelProperty("POLICYHOLDER_SMOKER_STATUS")
    @TableField("POLICYHOLDER_SMOKER_STATUS")
    private String policyholderSmokerStatus;

    @ExcelProperty("POLICYHOLDER_OCCUPATION_CODE")
    @TableField("POLICYHOLDER_OCCUPATION_CODE")
    private String policyholderOccupationCode;

    @ExcelProperty("POLICYHOLDER_SOCIALSECSTA")
    @TableField("POLICYHOLDER_SOCIALSECSTA")
    private String policyholderSocialsecsta;

    @ExcelProperty("POLICYHOLDER_SSECMEDINSSTA")
    @TableField("POLICYHOLDER_SSECMEDINSSTA")
    private String policyholderSsecmedinssta;

    @ExcelProperty("POLICYHOLDER_CORPSUBSTA")
    @TableField("POLICYHOLDER_CORPSUBSTA")
    private String policyholderCorpsubsta;

    @ExcelProperty("LIFE1_ID")
    @TableField("LIFE1_ID")
    private String life1Id;

    @ExcelProperty("LIFE1_FULL_NAME")
    @TableField("LIFE1_FULL_NAME")
    private String life1FullName;

    @ExcelProperty("LIFE1_GENDER")
    @TableField("LIFE1_GENDER")
    private String life1Gender;

    @ExcelProperty("LIFE1_DATE_OF_BIRTH")
    @TableField("LIFE1_DATE_OF_BIRTH")
    private String life1DateOfBirth;

    @ExcelProperty("LIFE1_ORIGINAL_AGE")
    @TableField("LIFE1_ORIGINAL_AGE")
    private BigDecimal life1OriginalAge;

    @ExcelProperty("LIFE1_SMOKER_STATUS")
    @TableField("LIFE1_SMOKER_STATUS")
    private String life1SmokerStatus;

    @ExcelProperty("LIFE1_OCCUPATION_CODE")
    @TableField("LIFE1_OCCUPATION_CODE")
    private String life1OccupationCode;

    @ExcelProperty("LIFE1_SOCIALSECSTA")
    @TableField("LIFE1_SOCIALSECSTA")
    private String life1Socialsecsta;

    @ExcelProperty("LIFE1_SSECMEDINSSTA")
    @TableField("LIFE1_SSECMEDINSSTA")
    private String life1Ssecmedinssta;

    @ExcelProperty("LIFE1_CORPSUBSTA")
    @TableField("LIFE1_CORPSUBSTA")
    private String life1Corpsubsta;

    @ExcelProperty("JOINT_LIFE_TYPE")
    @TableField("JOINT_LIFE_TYPE")
    private String jointLifeType;

    @ExcelProperty("JOINT_LIFE_DESC")
    @TableField("JOINT_LIFE_DESC")
    private String jointLifeDesc;

    @ExcelProperty("LIFE2_ID")
    @TableField("LIFE2_ID")
    private String life2Id;

    @ExcelProperty("LIFE2_FULL_NAME")
    @TableField("LIFE2_FULL_NAME")
    private String life2FullName;

    @ExcelProperty("LIFE2_GENDER")
    @TableField("LIFE2_GENDER")
    private String life2Gender;

    @ExcelProperty("LIFE2_DATE_OF_BIRTH")
    @TableField("LIFE2_DATE_OF_BIRTH")
    private String life2DateOfBirth;

    @ExcelProperty("LIFE2_ORIGINAL_AGE")
    @TableField("LIFE2_ORIGINAL_AGE")
    private BigDecimal life2OriginalAge;

    @ExcelProperty("LIFE2_SMOKER_STATUS")
    @TableField("LIFE2_SMOKER_STATUS")
    private String life2SmokerStatus;

    @ExcelProperty("LIFE2_OCCUPATION_CODE")
    @TableField("LIFE2_OCCUPATION_CODE")
    private String life2OccupationCode;

    @ExcelProperty("LIFE2_SOCIALSECSTA")
    @TableField("LIFE2_SOCIALSECSTA")
    private String life2Socialsecsta;

    @ExcelProperty("LIFE2_SSECMEDINSSTA")
    @TableField("LIFE2_SSECMEDINSSTA")
    private String life2Ssecmedinssta;

    @ExcelProperty("LIFE2_CORPSUBSTA")
    @TableField("LIFE2_CORPSUBSTA")
    private String life2Corpsubsta;

    @ExcelProperty("UNDERWRITING_SOLUTION")
    @TableField("UNDERWRITING_SOLUTION")
    private String underwritingSolution;

    @ExcelProperty("LOADING_REASON")
    @TableField("LOADING_REASON")
    private String loadingReason;

    @ExcelProperty("LOADING_TYPE")
    @TableField("LOADING_TYPE")
    private String loadingType;

    @ExcelProperty("ADDPREM")
    @TableField("ADDPREM")
    private BigDecimal addPrem;

    @ExcelProperty("LOADING_PERCENT_1")
    @TableField("LOADING_PERCENT_1")
    private BigDecimal loadingPercent1;

    @ExcelProperty("LOADING_PERCENT_2")
    @TableField("LOADING_PERCENT_2")
    private BigDecimal loadingPercent2;

    @ExcelProperty("LOADING_PERCENT")
    @TableField("LOADING_PERCENT")
    private BigDecimal loadingPercent;

    @ExcelProperty("LOADING_AMOUNT")
    @TableField("LOADING_AMOUNT")
    private BigDecimal loadingAmount;

    @ExcelProperty("LOADING_AMOUNT_UNIT")
    @TableField("LOADING_AMOUNT_UNIT")
    private String loadingAmountUnit;

    @ExcelProperty("LOADING_DURATION")
    @TableField("LOADING_DURATION")
    private String loadingDuration;

    @ExcelProperty("LOADING_DURATION_UNIT")
    @TableField("LOADING_DURATION_UNIT")
    private String loadingDurationUnit;

    @ExcelProperty("CONTTYPE")
    @TableField("CONTTYPE")
    private String contType;

    @ExcelProperty("PUSHDATE")
    @TableField("PUSHDATE")
    private String pushDate;

    @ExcelProperty("INSUREDTYPE")
    @TableField("INSUREDTYPE")
    private String insuredType;

    @ExcelProperty("IDTYPE")
    @TableField("IDTYPE")
    private String idType;

    @ExcelProperty("IDNO")
    @TableField("IDNO")
    private String idNo;

    @ExcelProperty("TRANTYPE")
    @TableField("TRANTYPE")
    private String tranType;

    @ExcelProperty("POLICYYEAR")
    @TableField("POLICYYEAR")
    private BigDecimal policyYear;

    @ExcelProperty("INITPREM")
    @TableField("INITPREM")
    private BigDecimal initPrem;

    @ExcelProperty("CONSTRTYPE")
    @TableField("CONSTRTYPE")
    private String constrType;

    @ExcelProperty("INSUREDNUM")
    @TableField("INSUREDNUM")
    private BigDecimal insuredNum;

    @ExcelProperty("ARCHAREA")
    @TableField("ARCHAREA")
    private String archArea;

    @ExcelProperty("ARCHCOST")
    @TableField("ARCHCOST")
    private String archCost;

    @ExcelProperty("PURPOSE")
    @TableField("PURPOSE")
    private String purpose;

    @ExcelProperty("CONSTRSA")
    @TableField("CONSTRSA")
    private BigDecimal constRsa;

    @ExcelProperty("WAITINGPERIOD")
    @TableField("WAITINGPERIOD")
    private BigDecimal waitingPeriod;

    @ExcelProperty("RNFLAG")
    @TableField("RNFLAG")
    private String rnFlag;

    @ExcelProperty("POLICY_INCEPTION_DATE_REAL")
    @TableField("POLICY_INCEPTION_DATE_REAL")
    private String policyInceptionDateReal;

    @ExcelProperty("RIPRECEPTNO")
    @TableField("RIPRECEPTNO")
    private String ripreceptNo;

    @ExcelProperty("REINDATE")
    @TableField("REINDATE")
    private String reinDate;

    @ExcelProperty("PLANNO")
    @TableField("PLANNO")
    private String planNo;

    @ExcelProperty("GRCODE")
    @TableField("GRCODE")
    private String grCode;

    @ExcelProperty("PCODE")
    @TableField("PCODE")
    private String pCode;

    @ExcelProperty("QUOTNO")
    @TableField("QUOTNO")
    private String quotNo;

    @ExcelProperty("DISABILITY")
    @TableField("DISABILITY")
    private String disability;

    @ExcelProperty("NOTES")
    @TableField("NOTES")
    private String notes;

    @ExcelProperty("POLRCDATE")
    @TableField("POLRCDATE")
    private String polrcDate;

    @ExcelProperty("PRODPOLNO")
    @TableField("PRODPOLNO")
    private String prodPolNo;

    @ExcelProperty("MPRPOLNO")
    @TableField("MPRPOLNO")
    private String mprPolNo;

    @ExcelProperty("RISKCODE")
    @TableField("RISKCODE")
    private String riskCode;

    @ExcelProperty("GRP_EFF_DATE")
    @TableField("GRP_EFF_DATE")
    private String grpEffDate;

    @ExcelProperty("ORGPLANNO")
    @TableField("ORGPLANNO")
    private String orgPlanNo;

    @ExcelProperty("SAR")
    @TableField("SAR")
    private String sar;

    @ExcelProperty("TRAN_DATE_ORN")
    @TableField("TRAN_DATE_ORN")
    private String tranDateOrn;

    @ExcelProperty("TF_IND")
    @TableField("TF_IND")
    private String tfInd;


}
