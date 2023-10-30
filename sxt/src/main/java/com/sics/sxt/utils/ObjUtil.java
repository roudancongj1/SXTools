package com.sics.sxt.utils;

import java.util.ArrayList;
import java.util.List;

public class ObjUtil {
        public static  <R> List<R> toClass(List<Object> objectList, Class<R> clazz) {
        List<R> resultList = new ArrayList<>();
        for (Object obj : objectList) {
            if (clazz.isInstance(obj)) {
                R convertedObj = clazz.cast(obj);
                resultList.add(convertedObj);
            }
        }
        return resultList;
    }
}
