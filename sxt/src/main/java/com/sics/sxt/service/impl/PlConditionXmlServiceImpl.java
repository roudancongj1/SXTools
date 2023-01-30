package com.sics.sxt.service.impl;

import com.sics.sxt.pojo.bo.LFBusiness;
import com.sics.sxt.service.XmlService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PlConditionXmlServiceImpl implements XmlService {
    @Override
    public String creat(Object obj) {
        if (obj instanceof LFBusiness lfBusiness) {

            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:enc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                    "        <SOAP-ENV:Header />\n" +
                    "        <SOAP-ENV:Body>\n" +
                    "                <swbep:createProportionalLimitsGeneralTermsConditionForLife xmlns:swbep=\"urn:SicsWsBusinessEntryPoint\">\n" +
                    "                        <genericInput />\n" +
                    "                        <createProportionalLimitsGeneralTermsConditionForLifeInput>\n" +
                    "                                <scopeOfCoverReference>\n" +
                    "                                           <identifier>" + lfBusiness.getId() + "</identifier>\n" +
                    //"                                        <beginDateTime>"+beginDate+"T00:00:00+00:00</beginDateTime>\n" +
                    "                                        <sequenceNumber>0</sequenceNumber>\n" +
                    "                                        <name>" + lfBusiness.getSection() + "</name>\n" +
                    "                                </scopeOfCoverReference>\n" +
                    "                                <lfProportionalLimitPremiumCondition>\n" +
                    "                                        <automaticCoverMinimumVolumeType>\n" +
                    "                                                <code>NONE</code>\n" +
                    "                                                <subclassNumber>967</subclassNumber>\n" +
                    "                                        </automaticCoverMinimumVolumeType>\n" +
                    "                                        <retentionPriorityBasis>\n" +
                    "                                                <code>BE</code>\n" +
                    "                                                <subclassNumber>825</subclassNumber>\n" +
                    "                                        </retentionPriorityBasis>\n" +
                    "                                        <limitBasis>\n" +
                    "                                                <code>PP</code>\n" +
                    "                                                <subclassNumber>926</subclassNumber>\n" +
                    "                                        </limitBasis>\n" +
                    "                                </lfProportionalLimitPremiumCondition>\n" +
                    "                        </createProportionalLimitsGeneralTermsConditionForLifeInput>\n" +
                    "                </swbep:createProportionalLimitsGeneralTermsConditionForLife>\n" +
                    "        </SOAP-ENV:Body>\n" +
                    "</SOAP-ENV:Envelope>";

        }
        return "未识别的数据类型";
    }
}
