package com.sics.sxt.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auth:GuangYun
 * */
@Data
@TableName("RISKRULESMAPPING")
public class RiskRulesMapping implements Serializable {

    @TableField("PLANNO")
    private String planNo;

    @TableField("RISKAMNTCALCODE")
    private String riskamntcalCode;

    @TableField("CALNAME")
    private String calName;

    @TableField("CALDESC")
    private String calDesc;

    @TableField("RULEID")
    private String ruleId;

    @TableField("CALVALUE")
    private String calValue;

}
