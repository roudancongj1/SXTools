package com.sics.sxt.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.sics.sxt.dao.CommonMapper;
import com.sics.sxt.pojo.vo.R;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.driver.OracleDriver;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class SqlController {

    private final CommonMapper commonMapper;
    private final RestTemplate restTemplate;




    @CrossOrigin
    @GetMapping("LFBusiness")
    public R query(){
        try {
            //GAsync.run(()->{
            //    logDBMapper.aa();
            //    System.out.println("up");
            //    int a=1/0;
            //    System.out.println("down");
            //    //return null;
            //});
            //List<Map<String, Object>> aa = logDBMapper.aa();

            List<Map<String, Object>> maps = commonMapper.selectByTable("BUSINESS");
            Thread.sleep(3000);
            return R.ok("请求成功").put("bar",20).put("aaa",maps.size());
        } catch (Exception e) {
            System.out.println("程序异常");
            e.printStackTrace();
            return R.error("请求失败");
        }
    }

    public R send1(){
        try {
            log.info("==> 团险新交易范围匹配接口开始执行......");
            JSONObject back = JSON.parseObject(restTemplate.postForObject("http://localhost:9006/groupCessionUpload/updateGroupCessionUpload", null, String.class));
            if (back!=null){
                if (back.get("success").equals(true))
                    return R.ok(back.get("msg").toString());
                else
                    return R.error(back.get("msg").toString());
            }
            return R.error("请求团险新交易范围匹配接口失败");
        } catch (Exception e) {
            log.error("程序异常",e);
            return R.error("程序异常");
        }
    }

    @GetMapping("send5")
    public R send5(){
        try {
            log.info("==> 重置 PlanNo/SumAtRisk/ORGPlanNo 接口开始执行......");
            Thread.sleep(3000);
            return R.ok("请求成功 重置 "+ 1+ " 条");
        } catch (Exception e) {
            log.error("程序异常",e);
            return R.error("程序异常");
        }
    }
    @GetMapping("call")
    public R call (){
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://39.107.77.181:8180/vpms/v1/rest/exec")).GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            return R.ok().put(response.body());

        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }

    }

    @CrossOrigin
    @GetMapping("chat")
    public R chat (){

        try {
            DriverManager.registerDriver(new OracleDriver());
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "ROOT", "root");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from BUSINESS");

            List<Map<String,Object>> columns = new ArrayList<>();

            while (resultSet.next()){
                HashMap<String, Object> map = new HashMap<>();
                String objectId = resultSet.getString("OBJECT_ID");
                Timestamp timestamp = resultSet.getTimestamp("INCEPTION_DATE");
                map.put("OBJECT_ID",objectId);
                map.put("INCEPTION_DATE",timestamp);
                columns.add(map);
            }
            /*CallableStatement call = connection.prepareCall("select * from BUSINESS");
            boolean execute = call.execute();*/
            Thread.sleep(3000);
            return R.ok().put(20);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }

    }

    public SqlController(CommonMapper commonMapper, RestTemplate restTemplate) {
        this.commonMapper = commonMapper;
        this.restTemplate = restTemplate;
    }
}
