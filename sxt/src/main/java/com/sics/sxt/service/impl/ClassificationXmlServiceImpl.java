package com.sics.sxt.service.impl;

import com.sics.sxt.common.GXml;
import com.sics.sxt.pojo.bo.LFBusiness;
import com.sics.sxt.service.XmlService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClassificationXmlServiceImpl implements XmlService {
    @Override
    public String creat(Object obj) {
        if (obj instanceof LFBusiness lfBusiness) {

            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:enc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://www.SicsNt.com/Business\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" +
                    "        <SOAP-ENV:Header/>\n" +
                    "        <SOAP-ENV:Body>\n" +
                    "                <swbep:editClassificationForLife xmlns:swbep=\"urn:SicsWsBusinessEntryPoint\">\n" +
                    "                        <genericInput>\n" +
                    "                                <messageId />\n" +
                    "                                <interactiveMessageResponses>\n" +
                    "                                        <answerYes>BS00138</answerYes>\n" +
                    "                                        <answerNo>BS0782</answerNo>\n" +
                    "                                </interactiveMessageResponses>\n" +
                    "                        </genericInput>\n" +
                    "                        <editClassificationForLifeInput>\n" +
                    "                                <scopeOfCoverReference>\n" +
                    "                                        <identifier>" + lfBusiness.getId() + "</identifier>\n" +
                    "                                        <beginDateTime>" + lfBusiness.getBeginDateTime() + "T00:00:00+00:00</beginDateTime>\n" +
                    "                                        <sequenceNumber>0</sequenceNumber>\n" +
                    "                                        <name>" + lfBusiness.getSection() + "</name>\n" +
                    "                                </scopeOfCoverReference>\n" +
                    "                                   <classification xsi:type=\"ns1:LfClassificationWithReplacementLists\">\n" +
                    "<includedRefDataList>\n" +
                    GXml.getBusinessProduct(lfBusiness.getProductList()) +
                    "</includedRefDataList>\n" +

                    //sb.append(getReinsuranceProductForClassification(sectionName));
                    "                                 </classification>\n" +
                    "                </editClassificationForLifeInput>\n" +
                    "                </swbep:editClassificationForLife>\n" +
                    "        </SOAP-ENV:Body>\n" +
                    "</SOAP-ENV:Envelope>";
        }
        return "未识别的数据类型";
    }
}
