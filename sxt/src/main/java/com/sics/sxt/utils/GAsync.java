package com.sics.sxt.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Slf4j
@Component
public class GAsync {

    private static final Integer corePoolSize = 100;

    private static final Integer maximumPoolSize = 100;

    private static final Long keepAliveTime = 1L;

    private static final Integer queueCapacity = 5;

    private static ExecutorService service;

    private GAsync() {
        service = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(queueCapacity));
    }

    //synchronized
    public static void run(Callable<Object> callable){
        service.submit(()->{
            try {
                Object call = callable.call();
            } catch (Exception e) {
                errorInfo(e);
            }
        });
    }

    public static void run(Runnable runnable){
        service.submit(()->{
            try {
                runnable.run();
            } catch (Exception e) {
                errorInfo(e);
            }
        });
    }

    private static void errorInfo(Exception e){
        log.error("线程异常");
        e.printStackTrace();
    }

}
