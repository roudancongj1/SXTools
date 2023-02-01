package com.sics.sxt.pojo.po;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
//@TableName("REQ_LOG")
public class LogDB implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    //@TableField("REQ_ID")
    private String reqId;

    //@TableField("STATUS")
    private String status;

    //@TableField("INFO")
    private String info;

    //@TableField("CREATE_DATE")
    private Timestamp createDate;

    //@TableField("MESSAGE")
    private String message;

    //@TableField("BUSINESS_ID")
    private String businessId;

    //@TableField("WORKSHEET_ID")
    private String worksheetId;

}
