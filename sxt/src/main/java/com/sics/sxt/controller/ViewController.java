package com.sics.sxt.controller;

import com.sics.sxt.config.ConfUrl;
import com.sics.sxt.pojo.bo.FlushBusiness;
import com.sics.sxt.pojo.bo.LFBusiness;
import com.sics.sxt.utils.ExcelUtil;
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
aaa();
        return map;
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

    private void aaa(){
        System.out.println(1111111111);
        if (1>0){
            return;
        }
        System.out.println(222222222);
    }
}
