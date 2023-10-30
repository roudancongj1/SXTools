package com.sics.sxt.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @Auth:GuangYun
 */
@Data
@TableName("GTPLANMAPPING")
public class GTPlanMapping implements Serializable {

    @TableField("RECEPTNO")
    private String receptNo;

    @TableField("BP")
    private String bp;

    @TableField("IP")
    private String ip;


    /**
     * 0 - SRulesMapping
     * 1 - HisMappingNotTreaty
     * 2 - NewPlanMapping
     * 3 - HisMappingTreaty
     * 4 - PlanMappingChange
     * */
    @TableField("STATUS")
    private String status;

    @TableField("OPERATOR")
    private String operator;

    @TableField("SPECIALNAME")
    private String specialName;

    @TableField("PREVALIDATE")
    private LocalDateTime prevaliDate;

    @TableField("PREINVALIDATE")
    private LocalDateTime preinvaliDate;

    @TableField("LEFTVALUE")
    private String leftValue;

    @TableField("RIGHTVALUE")
    private String rightValue;

    @TableField("ALLVALUE")
    private String allValue;


}
