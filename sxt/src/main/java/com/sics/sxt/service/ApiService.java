package com.sics.sxt.service;

import com.sics.sxt.pojo.bo.LFBusiness;
import com.sics.sxt.pojo.bo.PCBusiness;
import com.sics.sxt.pojo.vo.ER;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public interface ApiService {

    void uploadLFBusiness(List<LFBusiness> lfBusinessList);
    void uploadPCBusiness(List<PCBusiness> pcBusinessList);

}
