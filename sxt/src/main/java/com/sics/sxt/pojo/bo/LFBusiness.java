package com.sics.sxt.pojo.bo;


import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.BorderStyleEnum;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import com.alibaba.excel.util.ListUtils;
import lombok.Data;


import java.io.Serializable;
import java.util.List;


@Data
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 9,fontName = "微软雅黑")
@HeadRowHeight(50)
public class LFBusiness implements Cloneable, Serializable {



    /**
     *下标
     * */
    private Integer index;

    /**
     * 合同ID
     * */
    @ExcelProperty("ID")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 18)
    @ColumnWidth(20)
    private String id;

    /**
     * 合同标题
     * */
    @ExcelProperty("Title")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 10)
    @ColumnWidth(20)
    private String title;

    /**
     * 合同层级
     * */
    @ExcelProperty("Level of Business")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 10)
    private String level;

    /**
     * 合同类型
     * */
    @ExcelProperty("Type of Business")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 10)
    private String type;

    /**
     * Nature
     * */
    @ExcelProperty("Nature of Business")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 10)
    private String nature;

    /**
     * 币种
     * */
    @ExcelProperty("Currency")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 10)
    private String currency;

    /**
     * 合同的保险起期 -
     * */
    @ExcelProperty("Begin Date Time")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 10)
    private String beginDateTime;

    /**
     * 合同的保险终期 -
     * */
    @ExcelProperty("End Date Time")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 10)
    private String endDateTime;


    /**
     * 分出公司
     * */
    @ExcelProperty("Cedent")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 10)
    private String cedent;

    /**
     * 分出公司支付方式 ,
     * */
    @ExcelProperty("Cedent Task")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 10)
    private String taskCedent;

    /**
     * 再保公司
     * */
    @ExcelProperty("Reinsurer")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 10)
    private String reinsurer;

    /**
     * 再保险人签字日期
     * */
    //private String reinsurerSignatureDate;

    /**
     * 再保公司支付方式 ,
     * */
    //private String taskReinsurer;

    /**
     * 经纪公司
     * */
    @ExcelProperty("Broker")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 18)
    private String broker;

    /**
     * 经纪公司支付支付方式 ,
     * */
    @ExcelProperty("Broker Task")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 18)
    private String taskBroker;

    /**
     * 第三方服务公司
     * */
    @ExcelProperty("Service Company")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 18)
    private String serviceCompany;

    /**
     * 第三方服务公司支付方式 ,
     * */
    @ExcelProperty("Service Company Task")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 18)
    private String serviceCompanyTask;

    /**
     * 层级名称
     * */
    @ExcelProperty("Section")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 10)
    @ColumnWidth(50)
    private String section;

    /**
     * 层级级别码
     * */
    @ExcelProperty("Section Hierarchy")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 10)
    private Integer sectionHierarchy;

    /**
     * 产品代码 ,
     * */
    @ExcelProperty("Product")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 18)
    private String product;

    private List<String> productList = ListUtils.newArrayListWithExpectedSize(5);

    /**
     * 再保险保费方式 ,
     * */
    @ExcelProperty("Reinsurance Premium Method")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 10)
    private String reinsurancePremiumMethod;

    /**
     * 分保保费方式 ,
     * */
    @ExcelProperty("Reinsurance Product")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 10)
    private String reinsuranceProduct;

    /**
     * 险类名称 ,
     * */
    @ExcelProperty("Benefit Covered")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 10)
    private String benefitCovered;

    /**
     * 长短险 ,
     * */
    @ExcelProperty("Long Short Term")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 18)
    private String longShortTerm;

    /**
     * 重测非重测 ,
     * */
    @ExcelProperty("Additional Classification")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 18)
    private String additionalClassification;

    /**
     * 自留比例
     * */
    @ExcelProperty("Ceded Share Percentage")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 18)
    private String cededSharePercentage;

    /**
     * 自留额
     * */
    @ExcelProperty("Ceded Capped Retention")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 18)
    private String cededCappedRetention;

    /**
     * 承保限额
     * */
    @ExcelProperty("QS Limit")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 18)
    private String qsLimit;

    /**
     * 手续费类型 Not Required/Fixed/Slide Scale
     * */
    @ExcelProperty("Commission Type")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 18)
    private String commissionType;

    /**
     * 参与份额
     * */
    @ExcelProperty("Share of Order")
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 18)
    private String shareOfOrder;


    /**
     * 负责人ID ,
     * */
    //private String res_cm;


    public static String parentSection;

    @Override
    public LFBusiness clone() {
        try {
            return (LFBusiness) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
