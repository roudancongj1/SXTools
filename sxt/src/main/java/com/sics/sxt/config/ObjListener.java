package com.sics.sxt.config;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.sics.sxt.pojo.bo.LFBusiness;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ObjListener<T> implements ReadListener<T> {

    private final List<Object> list = new ArrayList<>();

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        list.add(t);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("Data is read finished！"+ list.size() +" in total");
        System.out.println("Current server address: "+ConfUrl.getCurrentLf());
        //(LFBusiness)Class.forName("com.LFBusiness").getDeclaredConstructor().newInstance();
    }

    public List<Object> getList(){
        if (!list.isEmpty()){
            Object o = list.iterator().next();
            if (o instanceof LFBusiness){
                for (int i = 0; i < list.size(); i++) {
                    LFBusiness lfBusiness = (LFBusiness) list.get(i);
                    lfBusiness.setIndex(i);
                }
            }
        }
        return list;
    }
}
