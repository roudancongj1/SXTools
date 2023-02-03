package com.sics.sxt.controller;


import com.sics.sxt.dao.LogDBMapper;
import com.sics.sxt.pojo.bo.LFBusiness;
import com.sics.sxt.pojo.vo.R;
import com.sics.sxt.service.ApiService;
import com.sics.sxt.utils.GAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ApiController {

    private final ApiService apiService;
    private final LogDBMapper logDBMapper;

    @GetMapping("LFBusiness")
    public R query(){
        try {
            GAsync.run(()->{
                System.out.println("up");
                int a=1/0;
                System.out.println("down");
                //return null;
            });
        } catch (Exception e) {
            System.out.println("程序异常");
            e.printStackTrace();
            return R.error("请求失败");
        }
        return R.ok("请求成功");
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
                         LogDBMapper logDBMapper) {
        this.apiService = apiService;
        this.logDBMapper = logDBMapper;
    }
}
