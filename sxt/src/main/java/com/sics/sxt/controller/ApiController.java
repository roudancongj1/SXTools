package com.sics.sxt.controller;


import com.sics.sxt.dao.CommonMapper;
import com.sics.sxt.dao.LogDBMapper;
import com.sics.sxt.pojo.bo.LFBusiness;
import com.sics.sxt.pojo.vo.R;
import com.sics.sxt.service.ApiService;
import com.sics.sxt.utils.GAsync;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class ApiController {

    private final ApiService apiService;
    private final LogDBMapper logDBMapper;
    private final CommonMapper commonMapper;

    @CrossOrigin
    @GetMapping("LFBusiness")
    public R query(){
        try {
            //GAsync.run(()->{
            //    logDBMapper.aa();
            //    System.out.println("up");
            //    int a=1/0;
            //    System.out.println("down");
            //    //return null;
            //});
            //List<Map<String, Object>> aa = logDBMapper.aa();

            List<Map<String, Object>> maps = commonMapper.selectByTable("ROOT","BUSINESS");
            Thread.sleep(3000);
            return R.ok("请求成功").put("bar",20).put("aaa",maps.size());
        } catch (Exception e) {
            System.out.println("程序异常");
            e.printStackTrace();
            return R.error("请求失败");
        }
    }

    @PostMapping("LFBusiness")
    public R upload(@RequestBody List<LFBusiness> lfBusinessList){
        GAsync.run(()-> apiService.uploadLFBusiness(lfBusinessList,getBatchNum()));
        return R.ok("请求成功").put(lfBusinessList);
    }

    private String getBatchNum(){
        String batchNum = UUID.randomUUID().toString().replace("-", "");
        //logDBMapper.update(new LogDB("batchNUm"));
        return batchNum;
    }

    public ApiController(ApiService apiService,
                         LogDBMapper logDBMapper,
                         CommonMapper commonMapper) {
        this.apiService = apiService;
        this.logDBMapper = logDBMapper;
        this.commonMapper = commonMapper;
    }
}
