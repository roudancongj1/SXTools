package com.sics.sxt.aspect;

import com.alibaba.fastjson.JSONObject;
import com.sics.sxt.pojo.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @Auth:GuangYun
 * */
@Aspect
@Component
@Slf4j
public class GAspect {

    private final ThreadLocal<Long> startTime = new ThreadLocal<>();


    @Before("execution(* com.sics.sxt.controller.SqlController.*(..))")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());

    }

    @AfterReturning(value = "execution(* com.sics.sxt.controller.SqlController.*(..))", returning = "ret")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) {
        String retString = JSONObject.toJSONString(ret);
        //LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis() -startTime.get()), ZoneId.systemDefault());
        //DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(dateTime)
        long l = System.currentTimeMillis() - startTime.get();
        String time = String.format("%.1f", l / 1000.0);
        if (ret instanceof R) {
            R r = (R) ret;
            r.put("time",time);
        }
        log.info("==> Response: " + retString);
        log.info("==> Time: " + time + "s");
    }

}
