package com.sics.sxt.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.json.XML;

import java.util.HashMap;
import java.util.Map;

public class XmlUtil {

    public static Map<String, Object> toMap(String xml) {
        JSONObject object = JSON.parseObject(XML.toJSONObject(xml).toString());
        return new HashMap<>(object);
    }

    public static boolean errorMsgIsIdExist(String xml) {
        Map<String, Object> object = toMap(xml);
        try {
            object = (JSONObject) object.get("soapenv:Envelope");
            object = (JSONObject) object.get("soapenv:Body");
            object = (JSONObject) object.get("soapenv:Fault");
            object = (JSONObject) object.get("detail");
            object = (JSONObject) object.get("sys:FaultDetails");
            object = (JSONObject) object.get("explanation");
            String error = object.get("content").toString();
            if ("Business Identifier is not unique.".equals(error))
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean errorMsgIsSectionExist(String xml) {
        //The name of the section is not unique in the section hierarchy.
        return false;
    }

}
