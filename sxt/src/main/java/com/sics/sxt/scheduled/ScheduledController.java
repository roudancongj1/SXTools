package com.sics.sxt.scheduled;


import com.sics.sxt.pojo.vo.R;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduledController {

    @PostMapping("resetDB")
    @Scheduled(cron = "0 0 0 * * ?")
    public R flushDB(){
        return R.ok();
    }
}
