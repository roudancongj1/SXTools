package com.sics.sxt.service.impl;

import com.sics.sxt.pojo.bo.FlushBusiness;
import com.sics.sxt.service.XmlService;
import org.springframework.stereotype.Service;

@Service
public class FlushBusinessXmlServiceImpl implements XmlService {
    @Override
    public String creat(Object obj) {
        FlushBusiness flushBusiness = (FlushBusiness) obj;
        return "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" +
                "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:enc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                "  <SOAP-ENV:Header />\n" +
                "  <SOAP-ENV:Body>\n" +
                "    <swbep:deleteBusiness xmlns:swbep=\"urn:SicsWsBusinessEntryPoint\">\n" +
                "      <genericInput />\n" +
                "      <deleteBusinessInput>\n" +
                "        <insuredPeriodReference>\n" +
                "          <identifier>"+flushBusiness.getId()+"</identifier>\n" +
                "        </insuredPeriodReference>\n" +
                "      </deleteBusinessInput>\n" +
                "    </swbep:deleteBusiness>\n" +
                "  </SOAP-ENV:Body>\n" +
                "</SOAP-ENV:Envelope>\n";
    }
}
