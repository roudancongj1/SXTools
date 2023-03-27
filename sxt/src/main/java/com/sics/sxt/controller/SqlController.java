package com.sics.sxt.controller;

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
}
