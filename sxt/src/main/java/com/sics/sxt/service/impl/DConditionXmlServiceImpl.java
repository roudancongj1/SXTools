package com.sics.sxt.service.impl;

import com.sics.sxt.pojo.bo.LFBusiness;
import com.sics.sxt.service.XmlService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class DConditionXmlServiceImpl implements XmlService {
    @Override
    public String creat(Object obj) {
        if (obj instanceof LFBusiness lfBusiness) {
            if (StringUtils.hasText(lfBusiness.getCommissionType())){
                if("Fixed".equals(lfBusiness.getCommissionType())){
                    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                            "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:enc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                            "        <SOAP-ENV:Header />\n" +
                            "        <SOAP-ENV:Body>\n" +
                            "                <swbep:createLfDeductionCondition xmlns:swbep=\"urn:SicsWsBusinessEntryPoint\">\n" +
                            "                        <genericInput />\n" +
                            "                        <createLfDeductionConditionInput>\n" +
                            "                                <scopeOfCoverReference>\n" +
                            "                                           <identifier>"+lfBusiness.getId()+"</identifier>\n" +
                            "                                        <sequenceNumber>0</sequenceNumber>\n" +
                            "                                        <name>Agreement</name>\n" +
                            "                                </scopeOfCoverReference>\n" +
                            "                                <deductionCondition>\n" +
                            "                                        <commissionBasis>\n" +
                            "                                                <initialCommissionRatioUnit>\n" +
                            "                                                        <code>PC</code>\n" +
                            "                                                        <subclassNumber>934</subclassNumber>\n" +
                            "                                                </initialCommissionRatioUnit>\n" +
                            "                                                <initialCommissionBasisType>\n" +
                            "                                                        <code>OC</code>\n" +
                            "                                                        <subclassNumber>917</subclassNumber>\n" +
                            "                                                </initialCommissionBasisType>\n" +
                            "                                                <initialDeductionCommissionRate>\n" +
                            "                                                        <code>SIN_RATE</code>\n" +
                            "                                                        <subclassNumber>882</subclassNumber>\n" +
                            "                                                </initialDeductionCommissionRate>\n" +
                            "                                                <initialCommissionPercent>0</initialCommissionPercent>\n" +
                            "                                                <renewalCommissionRatioUnit>\n" +
                            "                                                        <code>PC</code>\n" +
                            "                                                        <subclassNumber>934</subclassNumber>\n" +
                            "                                                </renewalCommissionRatioUnit>\n" +
                            "                                                <renewalCommissionBasisType>\n" +
                            "                                                        <code>OC</code>\n" +
                            "                                                        <subclassNumber>917</subclassNumber>\n" +
                            "                                                </renewalCommissionBasisType>\n" +
                            "                                                <renewalDeductionCommissionRate>\n" +
                            "                                                        <code>SIN_RATE</code>\n" +
                            "                                                        <subclassNumber>882</subclassNumber>\n" +
                            "                                                </renewalDeductionCommissionRate>\n" +
                            "                                                <renewalCommissionPercent>0</renewalCommissionPercent>\n" +
                            "                                        </commissionBasis>\n" +
                            "                                </deductionCondition>\n" +
                            "                        </createLfDeductionConditionInput>\n" +
                            "                </swbep:createLfDeductionCondition>\n" +
                            "        </SOAP-ENV:Body>\n" +
                            "</SOAP-ENV:Envelope>\n" +
                            "\n";
                }
                if("Not Required".equals(lfBusiness.getCommissionType())
                        || "Slide Scale".equals(lfBusiness.getCommissionType())){
                    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                            "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:enc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                            "        <SOAP-ENV:Header />\n" +
                            "        <SOAP-ENV:Body>\n" +
                            "                <swbep:createLfDeductionCondition xmlns:swbep=\"urn:SicsWsBusinessEntryPoint\">\n" +
                            "                        <genericInput />\n" +
                            "                        <createLfDeductionConditionInput>\n" +
                            "                                <scopeOfCoverReference>\n" +
                            "                                        <identifier>"+lfBusiness.getId()+"</identifier>\n" +
                            "                                        <sequenceNumber>0</sequenceNumber>\n" +
                            "                                        <name>"+lfBusiness.getSection()+"</name>\n" +
                            "                                </scopeOfCoverReference>\n" +
                            "                                <deductionCondition>\n" +
                            "                                        <isNotRequired>true</isNotRequired>\n" +
                            "                                </deductionCondition>\n" +
                            "                        </createLfDeductionConditionInput>\n" +
                            "                </swbep:createLfDeductionCondition>\n" +
                            "        </SOAP-ENV:Body>\n" +
                            "</SOAP-ENV:Envelope>\n";
                }

            }else
                return lfBusiness.getCommissionType()+" doesn't exist";
        }
        return "未识别的数据类型";
    }
}
