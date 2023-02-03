package com.sics.sxt.service.impl;

import com.sics.sxt.common.GXml;
import com.sics.sxt.pojo.bo.LFBusiness;
import com.sics.sxt.service.XmlService;
import org.springframework.stereotype.Service;



@Service
public class SectionXmlServiceImpl implements XmlService {
    @Override
    public String creat(Object obj) {
        if (obj instanceof LFBusiness lfBusiness) {
            return "<?xml version='1.0' encoding='utf-8'?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:enc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://www.SicsNt.com/SystemTypes\" xmlns:ns2=\"http://www.SicsNt.com/CommonTypes\" xmlns:ns3=\"http://www.SicsNt.com/Business\" xmlns:ns4=\"http://www.SicsNt.com/ReferenceItems\" xmlns:ns5=\"http://www.SicsNt.com/BusinessInsurables\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                    "        <SOAP-ENV:Header/>\n" +
                    "        <SOAP-ENV:Body>\n" +
                    "                <swbep:addChildSOCWithAmendment xmlns:swbep=\"urn:SicsWsBusinessEntryPoint\">\n" +
                    "                        <genericInput xsi:type=\"ns1:SicsGenericInput\">\n" +
                    "                                <interactiveMessageResponses>\n" +
                    "                                        <answerYes>BS0782</answerYes>\n" +
                    "                                </interactiveMessageResponses>\n" +
                    "                        </genericInput>\n" +
                    "                        <addChildSOCWithAmendmentInput xsi:type=\"ns3:AddChildSOCWithAmendmentInput\">\n" +
                    "                                <parentScopeOfCover xsi:type=\"ns4:SicsScopeOfCoverReference\">\n" +
                    "                                        <identifier>"+lfBusiness.getId()+"</identifier>\n" +
                    "                                        <sequenceNumber>0</sequenceNumber>\n" +
                    "                                        <name>"+lfBusiness.getParentSection()+"</name>\n" +
                    "                                </parentScopeOfCover>\n" +
                    "                                <childScopeOfCover xsi:type=\"ns3:LfScopeOfCover\">\n" +
                    "                                        <name>"+ lfBusiness.getSection()+"</name>\n" +
                    "                                        <isActive>true</isActive>\n" +
                    "                                        <currency>\n" +
                    "                                                <isoAlpha>CNY</isoAlpha>\n" +
                    "                                        </currency>\n" +
                    "                                </childScopeOfCover>\n" +
                    "                                       <benefitType>\n" +
                    "                                                <code>CAPITAL</code>\n" +
                    "                                                <subclassNumber>823</subclassNumber>\n" +
                    "                                        </benefitType>\n" +
                    "                                        <reinsuranceLiabilityTimeSpan>\n" +
                    "                                                <start>"+lfBusiness.getBeginDateTime()+"T00:00:00+00:00</start>\n" +
                    "                                        </reinsuranceLiabilityTimeSpan>" +
                    "                                <amendmentNumber>0</amendmentNumber>\n" +
                    "                                <shallAskForAmendment>true</shallAskForAmendment>\n" +
                    "                        </addChildSOCWithAmendmentInput>\n" +
                    "                </swbep:addChildSOCWithAmendment>\n" +
                    "        </SOAP-ENV:Body>\n" +
                    "</SOAP-ENV:Envelope>";
        }
        return "未识别的数据类型";
    }
}
