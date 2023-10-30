package com.sics.sxt.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Auth:GuangYun
 * */
@Data
@TableName("SRULESMAPPING")
public class SRulesMapping implements Serializable {

    @TableField("PLANNO")
    private String planNo;

    @TableField("CALCODE")
    private String calCode;

    @TableField("CALVALUE")
    private String calValue;

    @TableField("CALVALUE_TWO")
    private String calvalueTwo;

    @TableField("PREVALIDATE")
    private Date prevaliDate;

    @TableField("PREINVALIDATE")
    private Date preinvaliDate;


}
