package com.sics.sxt.utils;

import com.sics.sxt.config.RespEntity;
import com.sics.sxt.config.ConfUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.*;

@Slf4j
public class RestUtil {

    public static RespEntity<String> sendLf(String xml) {
        return send(xml,ConfUrl.getCurrentLf());
    }

    public static RespEntity<String> sendPc(String xml) {
        return send(xml,ConfUrl.getCurrentPc());
    }

    private static RespEntity<String> send(String xml, String url)  {
        System.out.println(xml);
        RestTemplate tp = new RestTemplate();
        tp.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
                HttpStatusCode statusCode = clientHttpResponse.getStatusCode();
                log.error("请求访问失败 错误码::[{}]", statusCode);
            }
        });
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf8");
        headers.add("SOAPAction", "http://www.SicsWsBusinessPartnerEntryPoint.com/SicsWsBusinessPartnerEntryPoint-interface/");
        HttpEntity<Object> entity = new HttpEntity<>(xml, headers);
        return  new RespEntity<>(tp.exchange(url, HttpMethod.POST, entity, String.class));

    }


}