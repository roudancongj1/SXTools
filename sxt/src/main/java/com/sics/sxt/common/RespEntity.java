package com.sics.sxt.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class RespEntity<T> {
    private HttpStatusCode statusCode;
    private HttpHeaders headers;
    private T body;

    private RespEntity() {
    }

    public RespEntity(ResponseEntity<T> response) {
        statusCode = response.getStatusCode();
        headers = response.getHeaders();
        body = response.getBody();
    }

    public boolean isOK() {
        return this.getStatusCode().value() == 200;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public T getBody() {
        return body;
    }


}
