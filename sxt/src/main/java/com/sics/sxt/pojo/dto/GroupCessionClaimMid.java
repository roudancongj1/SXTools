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
 * */
@Data
@HeadFontStyle(fontHeightInPoints = 12,fontName = "Segoe UI")
@ContentFontStyle(fontHeightInPoints = 12,fontName = "Segoe UI")
@TableName("GROUP_CESSION_CLAIM_MID")
public class GroupCessionClaimMid implements Serializable {

    @ExcelProperty("OBJECT_ID")
    @TableId("OBJECT_ID")
    private BigDecimal objectId;

    @ExcelProperty("RGTNO")
    @TableField("RGTNO")
    private String rgtNo;

    @ExcelProperty("CASENO")
    @TableField("CASENO")
    private String caseNo;

    @ExcelProperty("POLICY_NO")
    @TableField("POLICY_NO")
    private String policyNo;

    @ExcelProperty("CONTNO")
    @TableField("CONTNO")
    private String contNo;

    @ExcelProperty("TRANNO")
    @TableField("TRANNO")
    private String tranNo;

    @ExcelProperty("TRAN_TYPE")
    @TableField("TRAN_TYPE")
    private String tranType;

    @ExcelProperty("GIVE_TYPE")
    @TableField("GIVE_TYPE")
    private String giveType;

    @ExcelProperty("ACCDATE")
    @TableField("ACCDATE")
    private String accDate;

    @ExcelProperty("REPORTDATE")
    @TableField("REPORTDATE")
    private String reportDate;

    @ExcelProperty("RGTDATE")
    @TableField("RGTDATE")
    private String rgtDate;

    @ExcelProperty("ENDCASEDATE")
    @TableField("ENDCASEDATE")
    private String endcaseDate;

    @ExcelProperty("VALDATE")
    @TableField("VALDATE")
    private String valDate;

    @ExcelProperty("INSUREDNO")
    @TableField("INSUREDNO")
    private String insuredNo;

    @ExcelProperty("DUTYCODE")
    @TableField("DUTYCODE")
    private String dutyCode;

    @ExcelProperty("GETDUTYCODE")
    @TableField("GETDUTYCODE")
    private String getDutyCode;

    @ExcelProperty("FINRISKCODE")
    @TableField("FINRISKCODE")
    private String finriskCode;

    @ExcelProperty("CLAIM_BENEFIT_COVER")
    @TableField("CLAIM_BENEFIT_COVER")
    private String claimBenefitCover;

    @ExcelProperty("CVGID")
    @TableField("CVGID")
    private String cvgId;

    @ExcelProperty("ACCREASON")
    @TableField("ACCREASON")
    private String accReason;

    @ExcelProperty("LIABILITY_TYPE")
    @TableField("LIABILITY_TYPE")
    private String liabilityType;

    @ExcelProperty("ACC_DESCRIPTION")
    @TableField("ACC_DESCRIPTION")
    private String accDescription;

    @ExcelProperty("ACC_SITE")
    @TableField("ACC_SITE")
    private String accSite;

    @ExcelProperty("WAITING_PERIOD")
    @TableField("WAITING_PERIOD")
    private BigDecimal waitingPeriod;

    @ExcelProperty("REAL_PAY_AMOUNT")
    @TableField("REAL_PAY_AMOUNT")
    private BigDecimal realPayAmount;

    @ExcelProperty("STAND_PAY_AMOUNT")
    @TableField("STAND_PAY_AMOUNT")
    private BigDecimal standPayAmount;

    @ExcelProperty("DAYS_IN_HOSPITAL")
    @TableField("DAYS_IN_HOSPITAL")
    private String daysInHospital;

    @ExcelProperty("SPECCASEFLAG")
    @TableField("SPECCASEFLAG")
    private String speccaseFlag;

    @ExcelProperty("LIAB_REASON")
    @TableField("LIAB_REASON")
    private String liabReason;

    @ExcelProperty("CLAIMOPERATOR")
    @TableField("CLAIMOPERATOR")
    private String claimOperator;



}
