package com.sics.sxt.controller;

import com.sics.sxt.config.ConfUrl;
import com.sics.sxt.pojo.bo.FlushBusiness;
import com.sics.sxt.pojo.bo.LFBusiness;
import com.sics.sxt.utils.ExcelUtil;
import com.sics.sxt.utils.GAsync;
import com.sics.sxt.utils.GQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ViewController {

    private  final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public String home(){
        return "page/index.html";
    }

    @GetMapping("xml")
    public String xmlTools(){
        return "page/xmlTools.html";
    }

    @GetMapping("sql")
    public String sqlTools(){
        return "page/sqlTools.html";
    }

    @GetMapping("*")
    public String backHome(){
        return "redirect:/";
    }

    @GetMapping("contextChange")
    @ResponseBody
    public Map<String,Object> contextChange(String context){
        HashMap<String, Object> map = new HashMap<>();
        switch (context) {
            case "LIFE_URL" -> {
                ConfUrl.setCurrentLf(ConfUrl.getLf());
                map.put("currentContext", ConfUrl.getCurrentLf());
            }
            case "TEST_LIFE_URL" -> {
                ConfUrl.setCurrentLf(ConfUrl.getLfTest());
                map.put("currentContext", ConfUrl.getCurrentLf());
            }
            case "PC_URL" -> {
                ConfUrl.setCurrentPc(ConfUrl.getPc());
                map.put("currentContext", ConfUrl.getCurrentPc());
            }
            case "TEST_PC_URL" -> {
                ConfUrl.setCurrentPc(ConfUrl.getPCTest());
                map.put("currentContext", ConfUrl.getCurrentPc());
            }
            default -> map.put("currentContext", "未知环境");
        }
        return map;
    }


    @PostMapping("/aa")
    @ResponseBody
    public String aa() {

        GAsync.run(() -> {
            log.info("aa1");
            //int a =1/0;
            //Thread.sleep(5000L);
            //return "aaaaaa";
        });

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


}
