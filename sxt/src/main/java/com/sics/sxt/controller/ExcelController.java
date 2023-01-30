package com.sics.sxt.controller;



import com.sics.sxt.pojo.bo.FlushBusiness;
import com.sics.sxt.pojo.bo.LFBusiness;
import com.sics.sxt.pojo.vo.R;
import com.sics.sxt.service.ExcelService;
import com.sics.sxt.utils.ExcelUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class ExcelController {


    private ExcelService excelService;


    @PostMapping("/uploadExcel")
    public R singleFileUploadLfText(@RequestParam MultipartFile file, HttpServletRequest request) {
        try{
            if (file.isEmpty())
                return R.ok("上传文件为空");
            String templateType = request.getParameter("templateType");
            switch (templateType){
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

    @PostMapping("/aa")
    public String aa(){
        return "<?xml version='1.0' encoding='utf8'?>\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <soapenv:Header/>\n" +
                "    <soapenv:Body>\n" +
                "        <soapenv:Fault>\n" +
                "            <faultcode>soapenv:Server</faultcode>\n" +
                "            <faultstring>BS0010 - Validation Error</faultstring>\n" +
                "            <detail xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "                <sys:FaultDetails xmlns:sys=\"http://www.SicsNt.com/SystemTypes\" xsi:type=\"sys:SicsFaultDetails\">\n" +
                "                    <date xsi:type=\"xsd:date\">2022-12-12</date>\n" +
                "                    <time xsi:type=\"xsd:time\">08: 47: 50</time>\n" +
                "                    <xpath xsi:type=\"xsd:string\">/createBusiness</xpath>\n" +
                "                    <explanation xsi:type=\"xsd:string\">Business Identifier is not unique.</explanation>\n" +
                "                </sys:FaultDetails>\n" +
                "                <axis2:exceptionName xmlns:axis2=\"http://ws.apache.org/namespaces/axis2\">http: //www.SicsNt.com/SystemTypes.FaultDetails</axis2:exceptionName>\n" +
                "            </detail>\n" +
                "        </soapenv:Fault>\n" +
                "    </soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }

    @Autowired
    public void setExcelXmlService(ExcelService excelService) {
        this.excelService = excelService;
    }
}
