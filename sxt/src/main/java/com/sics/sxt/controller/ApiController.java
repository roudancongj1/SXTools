package com.sics.sxt.controller;


import com.sics.sxt.pojo.bo.LFBusiness;
import com.sics.sxt.pojo.vo.R;
import com.sics.sxt.service.ApiService;
import com.sics.sxt.utils.GAsync;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ApiController {

    private final ApiService apiService;



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

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

}
