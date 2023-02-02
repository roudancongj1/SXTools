package com.sics.sxt.service.impl;

import com.sics.sxt.common.RespEntity;
import com.sics.sxt.dao.LogDBMapper;
import com.sics.sxt.pojo.bo.LFBusiness;
import com.sics.sxt.pojo.bo.PCBusiness;
import com.sics.sxt.pojo.po.LogDB;
import com.sics.sxt.service.ApiService;
import com.sics.sxt.service.XmlService;
import com.sics.sxt.utils.BusinessUtil;
import com.sics.sxt.utils.RestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ApiServiceImpl implements ApiService {

    private final XmlService lfBusinessXmlService;
    private final XmlService plConditionXmlService;
    private final XmlService dConditionXmlService;
    private final XmlService lConditionXmlService;
    private final XmlService sectionXmlService;
    private final XmlService classificationXmlService;
    private final XmlService flushBusinessXmlService;
    private final LogDBMapper logDBMapper;



    @Override
    public void uploadLFBusiness(List<LFBusiness> lfBusinessList) {
        if (lfBusinessList.isEmpty()){
            log.warn("upload is successful but data is null");
            //logDBMapper.save(new LogDB("upload is successful but data is null"));
            //logDBMapper.aa();
        }
        lfBusinessList.forEach(lfBusiness -> {
            if (lfBusiness.getSectionHierarchy() == 0){
                BusinessUtil.getAllBP(lfBusiness,lfBusinessList);
                RespEntity<String> entity = RestUtil.sendLf(lfBusinessXmlService.creat(lfBusiness));
                if (entity.isOK()){
                    //logDBMapper.save(new LogDB("Y"));
                    addLogDB(RestUtil.sendLf(plConditionXmlService.creat(lfBusiness)), "PlCondition create error: "+lfBusiness);
                    addLogDB(RestUtil.sendLf(dConditionXmlService.creat(lfBusiness)),"DCondition create error: "+lfBusiness);
                    addLogDB(RestUtil.sendLf(lConditionXmlService.creat(lfBusiness)),"LCondition create error: "+lfBusiness);
                }else {
                    //logDBMapper.save(new LogDB("N",entity.getBody(),new LogDB("LFBusiness create error"+o")));
                }
            }else {
                addLogDB(RestUtil.sendLf(sectionXmlService.creat(lfBusiness)),"section create error: "+lfBusiness);
                if (lfBusiness.getSectionHierarchy() == 3)
                    addLogDB(RestUtil.sendLf(classificationXmlService.creat(lfBusiness)),"classification create error: "+lfBusiness);
            }
            LFBusiness.parentSection = lfBusiness.getSection();
        });
    }

    @Override
    public void uploadPCBusiness(List<PCBusiness> pcBusinessList) {

    }



    private void addLogDB(RespEntity<String> entity,String errMsg){
        LogDB logDB = new LogDB();
        if (entity.isOK()){
            logDB.setStatus("Y");
        }
        else{
            logDB.setStatus("N");
            logDB.setInfo(errMsg);

        }
        //logDBMapper.save(logDB);

    }

    private ApiServiceImpl(@Qualifier("lfBusinessXmlServiceImpl") XmlService lfBusinessXmlService,
                           @Qualifier("plConditionXmlServiceImpl") XmlService plConditionXmlService,
                           @Qualifier("DConditionXmlServiceImpl") XmlService DConditionXmlService,
                           @Qualifier("LConditionXmlServiceImpl") XmlService LConditionXmlService,
                           @Qualifier("sectionXmlServiceImpl") XmlService sectionXmlService,
                           @Qualifier("classificationXmlServiceImpl") XmlService classificationXmlService,
                           @Qualifier("flushBusinessXmlServiceImpl") XmlService flushBusinessXmlService,
                           LogDBMapper logDBMapper) {
        this.lfBusinessXmlService = lfBusinessXmlService;
        this.plConditionXmlService = plConditionXmlService;
        this.dConditionXmlService = DConditionXmlService;
        this.lConditionXmlService = LConditionXmlService;
        this.sectionXmlService = sectionXmlService;
        this.classificationXmlService = classificationXmlService;
        this.flushBusinessXmlService = flushBusinessXmlService;
        this.logDBMapper = logDBMapper;
    }

}
