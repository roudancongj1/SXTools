package com.sics.sxt.controller;


import com.sics.sxt.pojo.bo.LFBusiness;
import com.sics.sxt.pojo.vo.ER;
import com.sics.sxt.pojo.vo.R;
import com.sics.sxt.service.ApiService;
import com.sics.sxt.utils.GAsync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {

    private final ApiService apiService;

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
        GAsync.run(()-> apiService.uploadLFBusiness(lfBusinessList));
        return R.ok("请求成功").put(lfBusinessList);
    }

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }
}
