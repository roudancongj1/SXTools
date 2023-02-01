package com.sics.sxt.utils;

import com.sics.sxt.pojo.bo.LFBusiness;
import com.sics.sxt.pojo.bo.PCBusiness;
import org.springframework.util.StringUtils;

import java.util.List;

public class BusinessUtil {

    public static void getAllBP(LFBusiness currentBusiness, List<?> paramList){
        Object now = paramList.iterator().next();
        if (now instanceof LFBusiness) {
            for (int i = currentBusiness.getIndex() + 1; i < paramList.size(); i++) {
                LFBusiness lfBusiness = (LFBusiness) paramList.get(i);
                if (lfBusiness.getSectionHierarchy() == 0)
                    break;
                if (StringUtils.hasText(lfBusiness.getProduct())) {
                    currentBusiness.getProductList().add(lfBusiness.getProduct());
                }
            }
        }else if (now instanceof PCBusiness pcBusiness) {
            System.out.println(pcBusiness);
        }

    }
}
