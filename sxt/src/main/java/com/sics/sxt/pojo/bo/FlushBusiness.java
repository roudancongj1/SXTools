package com.sics.sxt.pojo.bo;


import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import lombok.Data;

@Data
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 9,fontName = "微软雅黑")
@HeadRowHeight(50)
public class FlushBusiness {

    /**
     * 合同ID
     * */
    @ExcelProperty("Flush ID")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 10)
    @ColumnWidth(20)
    private String id;

}
