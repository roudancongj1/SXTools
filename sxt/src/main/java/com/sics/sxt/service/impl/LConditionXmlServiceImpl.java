package com.sics.sxt.service.impl;

import com.sics.sxt.pojo.bo.LFBusiness;
import com.sics.sxt.service.XmlService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class LConditionXmlServiceImpl implements XmlService {
    @Override
    public String creat(Object obj) {
        if (obj instanceof LFBusiness lfBusiness) {
            String reinsuranceProduct = lfBusiness.getReinsuranceProduct();
            if("QA".equals(reinsuranceProduct) ||"CAPPEDQA".equals(reinsuranceProduct)){
                StringBuilder sb = new StringBuilder();
                sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:enc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                        "        <SOAP-ENV:Header />\n" +
                        "        <SOAP-ENV:Body>\n" +
                        "                <swbep:createProportionalLimitsConditionForLife xmlns:swbep=\"urn:SicsWsBusinessEntryPoint\">\n" +
                        "                        <genericInput />\n" +
                        "                        <createProportionalLimitsConditionForLifeInput>\n" +
                        "                                <scopeOfCoverReference>\n" +
                        "                                        <identifier>" + lfBusiness.getId() + "</identifier>\n" +
                        // "                                        <beginDateTime>2018-05-18T00:00:00+00:00</beginDateTime>\n" +
                        "                                        <sequenceNumber>0</sequenceNumber>\n" +
                        "                                        <name>" + lfBusiness.getSection() + "</name>\n" +
                        "                                </scopeOfCoverReference>\n");
                if(StringUtils.hasText(lfBusiness.getCededSharePercentage()) && StringUtils.hasText(lfBusiness.getQsLimit())) {

                    String strPercentage = lfBusiness.getCededSharePercentage().replace(",","");
                    String strTotalRetention = lfBusiness.getQsLimit().replace(",","");

                    double percentage = Double.parseDouble(strPercentage);
                    double totalRetention = Double.parseDouble(strTotalRetention);
                    double orRetention = totalRetention * (100 - percentage) /100;
                    double ceRetention = totalRetention * percentage/100;
                    BigDecimal a = new BigDecimal(orRetention);
                    double ourRetention  = a.setScale(2,RoundingMode.HALF_UP).doubleValue();
                    BigDecimal b   =   new   BigDecimal(ceRetention);
                    double cedentsRetention  = b.setScale(2,RoundingMode.HALF_UP).doubleValue();
                    sb.append("<lfProportionalLimitsCondition>\n" +
                            "                                        <quotaShareRetentionBasis>\n" +
                            "                                                <amountType>\n" +
                            "                                                        <code>AMOUNTS</code>\n" +
                            "                                                        <subclassNumber>819</subclassNumber>\n" +
                            "                                                </amountType>\n" +
                            "                                                <percent>"+strPercentage+"</percent>\n" +
                            "                                                <details>\n" +
                            "                                                        <detail>\n" +
                            "                                                                <currency>\n" +
                            "                                                                        <isoAlpha>CNY</isoAlpha>\n" +
                            "                                                                </currency>\n" +
                            "                                                                <totalRetention>"+strTotalRetention+"</totalRetention>\n" +
                            "                                                                <ourRetention>"+ourRetention+"</ourRetention>\n" +
                            "                                                                <cedentsRetention>"+cedentsRetention+"</cedentsRetention>\n" +
                            "                                                                <availableRetention>"+cedentsRetention+"</availableRetention>\n" +
                            "                                                        </detail>\n" +
                            "                                                </details>\n" +
                            "                                        </quotaShareRetentionBasis>\n" +
                            "                                        <jumboLimit>\n" +
                            "                                                <amountType>\n" +
                            "                                                        <code>NONE</code>\n" +
                            "                                                        <subclassNumber>819</subclassNumber>\n" +
                            "                                                </amountType>\n" +
                            "                                        </jumboLimit>\n" +
                            "                                </lfProportionalLimitsCondition>");
                }
                sb.append("                        </createProportionalLimitsConditionForLifeInput>\n" +
                        "                </swbep:createProportionalLimitsConditionForLife>\n" +
                        "        </SOAP-ENV:Body>\n" +
                        "</SOAP-ENV:Envelope>");

                return sb.toString();
            }

            if("SURPLUS".equals(reinsuranceProduct)){
                String retention = null;
                if (StringUtils.hasText(lfBusiness.getCededCappedRetention())){
                    retention = lfBusiness.getCededCappedRetention().replace(",","");
                }
                String limit = null;
                if (StringUtils.hasText(lfBusiness.getQsLimit())){
                    limit = lfBusiness.getQsLimit().replace(",","");
                }
                return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:enc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                        "        <SOAP-ENV:Header />\n" +
                        "        <SOAP-ENV:Body>\n" +
                        "                <swbep:createProportionalLimitsConditionForLife xmlns:swbep=\"urn:SicsWsBusinessEntryPoint\">\n" +
                        "                        <genericInput />\n" +
                        "                        <createProportionalLimitsConditionForLifeInput>\n" +
                        "                                <scopeOfCoverReference>\n" +
                        "                                        <identifier>" + lfBusiness.getId() + "</identifier>\n" +
                        // "                                        <beginDateTime>2021-01-01T00:00:00+00:00</beginDateTime>\n" +
                        "                                        <sequenceNumber>0</sequenceNumber>\n" +
                        "                                        <name>" + lfBusiness.getSection() + "</name>\n" +
                        "                                </scopeOfCoverReference>\n" +
                        "                                <lfProportionalLimitsCondition>\n" +
                        "                                       <maximumEMFactor>200</maximumEMFactor>\n" +
                        "                                       <retentionCorridorIsAmount>true</retentionCorridorIsAmount>\n" +
                        "                                       <autoCoverBasis>\n" +
                        "                                                <amounts>\n" +
                        "                                                        <amounts>\n" +
                        "                                                                <amount>\n" +
                        "                                                                        <amount>0</amount>\n" +
                        "                                                                        <currency>\n" +
                        "                                                                                <isoAlpha>CNY</isoAlpha>\n" +
                        "                                                                        </currency>\n" +
                        "                                                                </amount>\n" +
                        "                                                        </amounts>\n" +
                        "                                                </amounts>\n" +
                        "                                                <amountType>\n" +
                        "                                                        <code>AMOUNTS</code>\n" +
                        "                                                        <subclassNumber>819</subclassNumber>\n" +
                        "                                                </amountType>\n" +
                        "                                        </autoCoverBasis>\n" +
                        "                                        <underlyingTopLimit>\n" +
                        "                                                <amountType>\n" +
                        "                                                        <code>AMOUNTS</code>\n" +
                        "                                                        <subclassNumber>819</subclassNumber>\n" +
                        "                                                </amountType>\n" +
                        "                                                       <amounts>\n" +
                        "                                                           <amounts>\n" +
                        "                                                                <amount>\n" +
                        "                                                                        <amount>" + limit + "</amount>\n" +
                        "                                                                        <currency>\n" +
                        "                                                                                <isoAlpha>CNY</isoAlpha>\n" +
                        "                                                                        </currency>\n" +
                        "                                                                </amount>\n" +
                        "                                                           </amounts>\n" +
                        "                                                       </amounts>\n" +
                        "                                        </underlyingTopLimit>\n" +
                        "                                        <surplusRetentionBasis>\n" +
                        "                                                <amounts>\n" +
                        "                                                        <amounts>\n" +
                        "                                                                <amount>\n" +
                        "                                                                        <amount>" + retention + "</amount>\n" +
                        "                                                                        <currency>\n" +
                        "                                                                                <isoAlpha>CNY</isoAlpha>\n" +
                        "                                                                        </currency>\n" +
                        "                                                                </amount>\n" +
                        "                                                        </amounts>\n" +
                        "                                                </amounts>\n" +
                        "                                                <amountType>\n" +
                        "                                                        <code>AMOUNTS</code>\n" +
                        "                                                        <subclassNumber>819</subclassNumber>\n" +
                        "                                                </amountType>\n" +
                        "                                        </surplusRetentionBasis>\n" +
                        "                                        <jumboLimit>\n" +
                        "                                                <amountType>\n" +
                        "                                                        <code>NONE</code>\n" +
                        "                                                        <subclassNumber>819</subclassNumber>\n" +
                        "                                                </amountType>\n" +
                        "                                        </jumboLimit>\n" +
                        "                                       <modificationCondition>\n" +
                        "                                                <onAnyDate>true</onAnyDate>\n" +
                        "                                                <businessAffected>\n" +
                        "                                                        <code>NE</code>\n" +
                        "                                                        <subclassNumber>933</subclassNumber>\n" +
                        "                                                </businessAffected>\n" +
                        "                                        </modificationCondition>\n" +
                        "                                </lfProportionalLimitsCondition>\n" +
                        "                        </createProportionalLimitsConditionForLifeInput>\n" +
                        "                </swbep:createProportionalLimitsConditionForLife>\n" +
                        "        </SOAP-ENV:Body>\n" +
                        "</SOAP-ENV:Envelope>";
            }
            return reinsuranceProduct+" doesn't match for condition D";
        }
        return "未识别的数据类型";
    }
}
