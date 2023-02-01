package com.sics.sxt.utils;


import com.sics.sxt.pojo.bo.LFBusiness;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
public class GQueue {

    private static LinkedBlockingQueue<Object> queue;

    private GQueue(){
        queue = new LinkedBlockingQueue<>(10);
    }

    public static void set(Object obj){
        try {
            //offer add
            queue.put(obj);
        } catch (InterruptedException e) {
            log.error("队列存入异常");
            e.printStackTrace();
        }
    }

    public static Object get(){
        //poll take
        return queue.remove();
    }

    /**
     * Producer/Consumer
     * */
    public void queue() throws InterruptedException {
        LinkedBlockingQueue<LFBusiness> queue = new LinkedBlockingQueue<>(1);

        List<Object> list = new ArrayList<>();//ExcelUtil.readExcelObj(file, LFBusiness.class);

        AtomicBoolean end = new AtomicBoolean(false);
        Thread thread1 = new Thread(()-> {
            for (Object t : list) {
                try {
                    LFBusiness business=(LFBusiness) t;
                    if (business.toString() != null){
                        queue.put(business);
                        log.info("当前消费以排队"+business);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            end.set(true);
            System.out.println("线程1结束");

        });
        thread1.start();
        Thread.sleep(2000);
        Thread thread2 = new Thread(()-> {
            try {
                while(!end.get() ||!queue.isEmpty()){
                    log.info("以消费");
                    LFBusiness lfBusiness = queue.take();
                }
                //boolean interrupted = Thread.interrupted();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程2结束");
        });
        thread2.start();


        try {
            thread1.join(2000);
            thread2.join(2000);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("join异常");
        }

        /*while (thread1.isAlive() || thread2.isAlive()) {
            ////只要两个线程中有任何一个线程还在活动，主线程就不会往下执行
            log.info("当前还有线程在执行");
        }*/
    }

}
