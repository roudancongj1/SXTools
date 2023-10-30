package com.sics.sxt.controller;

import com.sics.sxt.dao.CommonMapper;
import com.sics.sxt.pojo.vo.R;
import oracle.jdbc.driver.OracleDriver;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SqlController {

    private final CommonMapper commonMapper;




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

    public SqlController(CommonMapper commonMapper) {
        this.commonMapper = commonMapper;
    }
}
