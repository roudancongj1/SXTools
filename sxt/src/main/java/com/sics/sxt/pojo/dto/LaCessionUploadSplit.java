package com.sics.sxt.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("group_all_transaction")
public class LaCessionUploadSplit implements Serializable {

    @TableField("LIFE_ID")
    private String lifeId;
    @TableField("BENEFIT_COVERED")
    private String bc;

    @TableField("POLICY_NUMBER")
    private String policyNumber;

    @TableField("bp")
    private String bp;

    @TableField("ip")
    private String ip;

    @TableField("trans_code")
    private String transCode;

    @TableField("bussno")
    private String bussno;

    @TableField("tran_date")
    private String tranDate;
    @TableField("object_id")
    private Integer objectId;

}
