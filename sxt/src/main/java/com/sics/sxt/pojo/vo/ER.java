package com.sics.sxt.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@HeadFontStyle(fontHeightInPoints = 9,fontName = "微软雅黑")
@HeadRowHeight(50)
public class ER {

    @ExcelProperty("Is Successful")
    private String status;

    @ExcelProperty("Error Massage")
    private String callBackMsg;

    @ExcelProperty("Entity Body")
    private String obj;

    public ER(String status){
        this.status = status;
    }
}
