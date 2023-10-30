package com.sics.sxt.service.serv;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sics.sxt.dao.GroupCessionClaimMidMapper;
import com.sics.sxt.dao.GroupCessionUploadMidMapper;
import com.sics.sxt.pojo.dto.GroupCessionClaimMid;
import com.sics.sxt.pojo.dto.GroupCessionUploadMid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.sics.sxt.pojo.bo.DB.*;


@Slf4j
@Service
public class ExportExcelService {

    private final GroupCessionUploadMidMapper groupCessionUploadMidMapper;
    private final GroupCessionClaimMidMapper groupCessionClaimMidMapper;
    private final CommonService commonService;


    public void hook(String tableList) {
        String[] tableName = tableList.split("\n");
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        for (String table : tableName) {
            int size = commonService.queryTableCount(table);
            if (table.contains("UPLOAD")) {
                try (ExcelWriter excelWriter = EasyExcel.write(DATAPATH + table + DATASUFFIX, GroupCessionUploadMid.class).build()) {
                    for (int i = 0; i <= size / DATAPAGENUM; i++) {
                        if (i * DATAPAGENUM != size) {
                            Page<GroupCessionUploadMid> page = groupCessionUploadMidMapper.selectByTable(new Page<>(i + 1, DATAPAGENUM), table);
                            excelWriter.write(page.getRecords(), writeSheet);
                        }
                    }
                }
            } else if (table.contains("CLAIM")) {
                try (ExcelWriter excelWriter = EasyExcel.write(DATAPATH + table + DATASUFFIX, GroupCessionClaimMid.class).build()) {
                    for (int i = 0; i <= size / DATAPAGENUM; i++) {
                        if (i * DATAPAGENUM != size) {
                            Page<GroupCessionClaimMid> page = groupCessionClaimMidMapper.selectByTable(new Page<>(i + 1, DATAPAGENUM), table);
                            excelWriter.write(page.getRecords(), writeSheet);
                        }
                    }
                }

            } else {
                log.error("==> 未知表名");
            }
        }

    }
    public ExportExcelService(GroupCessionUploadMidMapper groupCessionUploadMidMapper,
                              GroupCessionClaimMidMapper groupCessionClaimMidMapper, CommonService commonService) {
        this.groupCessionUploadMidMapper = groupCessionUploadMidMapper;
        this.groupCessionClaimMidMapper = groupCessionClaimMidMapper;
        this.commonService = commonService;
    }


}
