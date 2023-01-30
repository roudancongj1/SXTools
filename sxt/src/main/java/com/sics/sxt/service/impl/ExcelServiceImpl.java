package com.sics.sxt.service.impl;

import com.sics.sxt.config.RespEntity;
import com.sics.sxt.pojo.bo.FlushBusiness;
import com.sics.sxt.pojo.bo.LFBusiness;
import com.sics.sxt.pojo.bo.PCBusiness;
import com.sics.sxt.pojo.vo.ER;
import com.sics.sxt.service.ExcelService;
import com.sics.sxt.service.XmlService;
import com.sics.sxt.utils.ExcelUtil;
import com.sics.sxt.utils.RestUtil;
import com.sics.sxt.utils.XmlUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ExcelServiceImpl implements ExcelService {

    private final XmlService lfBusinessXmlService;
    private final XmlService plConditionXmlService;
    private final XmlService dConditionXmlService;
    private final XmlService lConditionXmlService;
    private final XmlService sectionXmlService;
    private final XmlService classificationXmlService;
    private final XmlService flushBusinessXmlService;


    @Override
    public List<ER> send(List<Object> paramList) {
        ArrayList<ER> erList = new ArrayList<>();
        if (paramList.isEmpty()){
            log.info("upload is successful but data is null");
            erList.add(new ER("Y","upload is successful but data is null","^^"));
            return erList;
        }
        for (Object o : paramList) {
            if( o instanceof LFBusiness lfBusiness){
                if (lfBusiness.getSectionHierarchy() == 0){
                    getAllBP(lfBusiness,paramList);
                    RespEntity<String> entity = RestUtil.sendLf(lfBusinessXmlService.creat(lfBusiness));
                    //if ( entity.isOK() || XmlUtil.errorMsgIsIdExist(entity.getBody())){
                    if (entity.isOK()){
                        log.info("请求访问成功");
                        erList.add(new ER("Y"));
                        addERList(RestUtil.sendLf(plConditionXmlService.creat(o)),erList,"PlCondition create error: "+o);
                        addERList(RestUtil.sendLf(dConditionXmlService.creat(o)),erList,"DCondition create error: "+o);
                        addERList(RestUtil.sendLf(lConditionXmlService.creat(o)),erList,"LCondition create error: "+o);
                    }else {
                        erList.add(new ER("N",entity.getBody(),o.toString()));
                        System.out.println(entity.getBody());
                    }
                }else {
                    addERList(RestUtil.sendLf(sectionXmlService.creat(o)),erList,"section create error: "+o);
                    if (lfBusiness.getSectionHierarchy() == 3)
                        addERList(RestUtil.sendLf(classificationXmlService.creat(o)),erList,"classification create error: "+o);
                }
                LFBusiness.parentSection = lfBusiness.getSection();
            }else if (o instanceof PCBusiness){
                addERList(RestUtil.sendLf(plConditionXmlService.creat(o)),erList,"PCBusiness create error: "+o);
            }else if (o instanceof FlushBusiness){
                addERList(RestUtil.sendLf(flushBusinessXmlService.creat(o)),erList,"Flush Business error: "+o);
            }

        }

        return erList;
    }


    private void getAllBP(LFBusiness currentBusiness, List<Object> paramList){
        if (paramList.iterator().next() instanceof LFBusiness) {
            for (int i = currentBusiness.getIndex() + 1; i < paramList.size(); i++) {
                LFBusiness lfBusiness = (LFBusiness) paramList.get(i);
                if (lfBusiness.getSectionHierarchy() == 0)
                    break;
                if (StringUtils.hasText(lfBusiness.getProduct())) {
                    currentBusiness.getProductList().add(lfBusiness.getProduct());
                }
            }
        }else if (paramList.iterator().next() instanceof PCBusiness pcBusiness) {
            System.out.println(pcBusiness);
        }

    }

    @Override
    public void getRequestToExcel(List<ER> list) throws IOException {
        ExcelUtil.whiteERList(list);
    }

    private void addERList(RespEntity<String> entity, List<ER> erList,String errMsg){
        if (entity.isOK()){
            log.info("请求访问成功");
            erList.add(new ER("Y"));
        }
        else{
            System.out.println(entity.getBody());
            erList.add(new ER("N",entity.getBody(),errMsg));
        }
    }

    private ExcelServiceImpl(@Qualifier("lfBusinessXmlServiceImpl") XmlService lfBusinessXmlService,
                             @Qualifier("plConditionXmlServiceImpl") XmlService plConditionXmlService,
                             @Qualifier("DConditionXmlServiceImpl") XmlService DConditionXmlService,
                             @Qualifier("LConditionXmlServiceImpl") XmlService LConditionXmlService,
                             @Qualifier("sectionXmlServiceImpl") XmlService sectionXmlService,
                             @Qualifier("classificationXmlServiceImpl") XmlService classificationXmlService,
                             @Qualifier("flushBusinessXmlServiceImpl") XmlService flushBusinessXmlService) {
        this.lfBusinessXmlService = lfBusinessXmlService;
        this.plConditionXmlService = plConditionXmlService;
        this.dConditionXmlService = DConditionXmlService;
        this.lConditionXmlService = LConditionXmlService;
        this.sectionXmlService = sectionXmlService;
        this.classificationXmlService = classificationXmlService;
        this.flushBusinessXmlService = flushBusinessXmlService;
    }

}
