package com.sics.sxt.pojo.dto;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;

/**
 * @Auth:GuangYun
 */
public class Generator {

    //DATASOURCE
    private static final String url = "jdbc:oracle:thin:@172.19.50.66:1521/test02db";
    private static final String username = "SICS_DATA";
    private static final String password = "sics2023";
    private static final String tableName = "GROUP_CESSION_CLAIM_MID";

    public static void main(String[] args) {
        //jdbc:mysql://localhost:3306/mydata
        FastAutoGenerator.create(url,username,password)
                .globalConfig(builder -> {
                    builder.author("Gao") // 设置作者
                            .outputDir("C://_____mp")// 指定输出目录
                            .fileOverride()
                            .enableSwagger(); // 开启swagger
                })
                .packageConfig(builder -> {
                    builder.parent("generator"); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tableName) // 设置需要生成的表名
                            .addTablePrefix("t_", "c_") // 设置过滤表前缀
                            .entityBuilder()
                            .enableTableFieldAnnotation() //开启字段映射
                            .enableLombok();
                })
                .execute();
    }
}
