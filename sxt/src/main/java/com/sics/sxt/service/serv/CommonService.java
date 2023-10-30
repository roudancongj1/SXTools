package com.sics.sxt.service.serv;

import com.sics.sxt.dao.CommonMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auth:GuangYun
 * */
@Service
public class CommonService {
    private final CommonMapper commonMapper;

    public boolean dropTable(String tableOwner,String tableName){
        try {
            if (this.hasTable(tableOwner,tableName)){
                commonMapper.dropTable(tableOwner, tableName);
                return true;
            }else if (this.hasTable(tableOwner.toUpperCase(),tableName.toUpperCase())){
                commonMapper.dropTable(tableOwner.toUpperCase(), tableName.toUpperCase());
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean truncateTable(String tableOwner,String tableName){
        try {
            if (this.hasTable(tableOwner,tableName)){
                commonMapper.truncateTable(tableOwner, tableName);
                return true;
            }else if (this.hasTable(tableOwner.toUpperCase(),tableName.toUpperCase())){
                commonMapper.truncateTable(tableOwner.toUpperCase(), tableName.toUpperCase());
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasTable(String tableOwner,String tableName){
        return !commonMapper.tableIsExist(tableOwner, tableName).isEmpty();
    }

    public List<Map<String, Object>> queryAllMap(String tableOwner, String tableName){
            return commonMapper.selectByOwnerTable(tableOwner, tableName);
    }

 /*   public <R> List<R> queryAllObj(String tableName,Class<R> clazz) {
        List<Object> objectList = commonMapper.selectByTable(tableName);
        List<R> resultList = new ArrayList<>();
        for (Object obj : objectList) {
            if (clazz.isInstance(obj)) {
                R convertedObj = clazz.cast(obj);
                resultList.add(convertedObj);
            }
        }
        return resultList;
    }*/

    public int queryTableCount(String tableName){
        return commonMapper.selectTableCount(tableName);
    }
    public int queryOwnerTableCount(String tableOwner,String tableName){
        return commonMapper.selectOwnerTableCount(tableOwner,tableName);
    }

    public Map<String,Integer> queryTableCountList(List<String> tableList){
        LinkedHashMap<String,Integer> map = new LinkedHashMap<>();
        tableList.forEach(tableName -> map.put(tableName,this.queryTableCount(tableName)));
        return map;
    }

    public CommonService(CommonMapper commonMapper) {
        this.commonMapper = commonMapper;
    }
}
