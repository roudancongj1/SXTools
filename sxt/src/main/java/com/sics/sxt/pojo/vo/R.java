package com.sics.sxt.pojo.vo;

import java.util.HashMap;
import java.util.Map;

public class R extends HashMap<String,Object> {

    /**
     * 默认成功
     * */
    R(){
        put("code","0");
        put("msg","success");
    }

    R(String code, Object msg){
        put("code",code);
        put("msg",msg);
    }

    public static R ok(){
        return new R();
    }

    public static R ok(String msg){
        return new R("0",msg);
    }

    public static R ok(Integer code, String msg){
        return new R(code.toString(),msg);
    }

    public static R error(){
        return new R("500","操作失败");
    }

    public static R error(String msg){
        return new R("500",msg);
    }

    public static R error(Integer code, String msg){
        return new R(code.toString(),msg);
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public R put(Object data){
        super.put("data",data);
        return this;
    }

    public R put(Map<String,Object> map){
        putAll(map);
        return this;
    }
}
