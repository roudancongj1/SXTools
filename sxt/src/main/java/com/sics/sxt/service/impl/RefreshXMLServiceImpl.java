package com.sics.sxt.service.impl;

import com.sics.sxt.service.XmlService;

public class RefreshXMLServiceImpl implements XmlService {
    @Override
    public String creat(Object obj) {
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:SicsWsAdministrationEntryPoint\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <urn:fullRefresh/>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }
}
