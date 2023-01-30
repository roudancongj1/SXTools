package com.sics.sxt.service;

import com.sics.sxt.pojo.vo.ER;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ExcelService {

    List<ER> send(List<Object> paramList);

    void getRequestToExcel(List<ER> list) throws IOException;
}
