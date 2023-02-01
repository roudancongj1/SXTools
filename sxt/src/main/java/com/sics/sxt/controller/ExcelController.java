package com.sics.sxt.controller;


import com.sics.sxt.pojo.bo.FlushBusiness;
import com.sics.sxt.pojo.bo.LFBusiness;
import com.sics.sxt.pojo.vo.R;
import com.sics.sxt.service.ExcelService;
import com.sics.sxt.utils.ExcelUtil;

import com.sics.sxt.utils.GAsync;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
public class ExcelController {

    private ExcelService excelService;

    @PostMapping("/uploadExcel")
    public R singleFileUploadLfText(@RequestParam MultipartFile file, HttpServletRequest request) {
        try {
            if (file.isEmpty())
                return R.ok("上传文件为空");
            String templateType = request.getParameter("templateType");
            switch (templateType) {
                case ("LfBusinessTemplate") -> excelService.getRequestToExcel(excelService.send(ExcelUtil.readExcelObj(file, LFBusiness.class)));
                case ("FlushBusinessTemplate") -> excelService.getRequestToExcel(excelService.send(ExcelUtil.readExcelObj(file, FlushBusiness.class)));
                case ("CreatePlConditionTemplate") -> excelService.getRequestToExcel(excelService.send(ExcelUtil.readExcelObj(file, Object.class)));
                default -> log.error("未知模板 请联系管理员");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("程序异常 下载文件失败");
        }
    }

    @GetMapping("templateDownload")
    @ResponseBody
    public void templateDownload(@RequestParam String templateType) throws IOException {
        switch (templateType) {
            case ("CreateLfBusinessTemplate") -> ExcelUtil.whiteTemplate(LFBusiness.class);
            case ("FlushBusinessTemplate") -> ExcelUtil.whiteTemplate(FlushBusiness.class);
            default -> log.error("templateType is not found!");
        }
    }

    @Autowired
    public void setExcelXmlService(ExcelService excelService) {
        this.excelService = excelService;
    }
}
