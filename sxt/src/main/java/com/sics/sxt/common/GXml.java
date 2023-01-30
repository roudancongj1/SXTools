package com.sics.sxt.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;


@Slf4j
public class GXml {

    public static String getLevelOfBusiness(String str){
        StringBuilder sb = new StringBuilder();
        if(StringUtils.hasText(str)) {
            for (String temp : str.split(",")) {
                sb.append("<businessDirection>\n" +
                        "                                         <code>"+temp+"</code>\n" +
                        "                                         <subclassNumber>45</subclassNumber>\n" +
                        "  </businessDirection>\n");
            }
        }
        return sb.toString();
    }

    public static String getTypeOfBusiness(String str){
        StringBuffer sb = new StringBuffer();
        if(StringUtils.hasText(str)) {
            for (String temp : str.split(",")) {
                sb.append("<typeOfBusiness>\n" +
                        "                                        <code>" + temp + "</code>\n" +
                        "                                        <subclassNumber>46</subclassNumber>\n" +
                        "  </typeOfBusiness>\n");
            }
        }
        return sb.toString();
    }

    public static String getInsuredPeriodBeginDate(String str){
        StringBuffer sb = new StringBuffer();
        if(StringUtils.hasText(str)) {
            String year =str.split("-")[0];
            sb.append("<beginDateTime>"+str+"T00:00:00+00:00</beginDateTime>\n");

        }
        return sb.toString();
    }

    public static String getUnderwritingYear(String str){
        StringBuffer sb = new StringBuffer();
        if(StringUtils.hasText(str)) {
            String year =str.split("-")[0];
            sb.append("<underwritingYear>"+year+"</underwritingYear>\n");
        }
        return sb.toString();
    }

    public static String getInsuredPeriodEndDate(String str){
        StringBuffer sb = new StringBuffer();
        if(StringUtils.hasText(str) && !"9999-12-31".equals(str)) {
            sb.append("<endDateTime>"+str+"T00:00:00+00:00</endDateTime>\n");
        }
        return sb.toString();
    }

    public static String getNatureOfBusiness(String str){
        StringBuffer sb = new StringBuffer();
        if(StringUtils.hasText(str)) {
            for (String temp : str.split(",")) {
                sb.append("<includedMethodRefData>\n" +
                        "                                        <code>"+temp+"</code>\n" +
                        "                                        <subclassNumber>978</subclassNumber>\n" +
                        "  </includedMethodRefData>\n");
            }
        }
        return sb.toString();
    }

    public static String getReinsuranceProduct(String str){
        StringBuffer sb = new StringBuffer();
        if(StringUtils.hasText(str)) {
            for (String temp : str.split(",")) {
                sb.append("<includedMethodRefData>\n" +
                        "                                        <code>"+temp+"</code>\n" +
                        "                                        <subclassNumber>22</subclassNumber>\n" +
                        "  </includedMethodRefData>\n");
            }
        }
        return sb.toString();
    }

    public static String getBenefitCovered(String str){
        StringBuffer sb = new StringBuffer();
        if(StringUtils.hasText(str)) {
            for (String temp : str.split(",")) {
                sb.append("<includedRefData>\n" +
                        "                                        <code>"+temp+"</code>\n" +
                        "                                        <subclassNumber>975</subclassNumber>\n" +
                        "  </includedRefData>\n");
            }
        }
        return sb.toString();
    }

    public static String getInsuranceProduct(String str){
        StringBuffer sb = new StringBuffer();
        if(!StringUtils.hasText(str)) {
            sb.append("<includedRefData>\n" +
                    "               <code>DUMMY</code>\n" +
                    "               <subclassNumber>976</subclassNumber>\n" +
                    " </includedRefData> \n ");
        }else {
            sb.append("<includedRefData>\n" +
                    "               <code>"+str+"</code>\n" +
                    "               <subclassNumber>976</subclassNumber>\n" +
                    " </includedRefData> \n ");
        }
        return sb.toString();
    }

    public static String getLongShortTerm(String str){
        StringBuffer sb = new StringBuffer();
        if(StringUtils.hasText(str)) {
            for (String temp : str.split(",")) {
                sb.append("<includedRefData>\n" +
                        "                                        <code>"+temp+"</code>\n" +
                        "                                        <subclassNumber>00214</subclassNumber>\n" +
                        "  </includedRefData>\n");
            }
        }
        return sb.toString();
    }

    public static String getAdditionalClassification(String str){
        StringBuffer sb = new StringBuffer();
        if(StringUtils.hasText(str)) {
            for (String temp : str.split(",")) {
                sb.append("<includedRefData>\n" +
                        "                                        <code>"+temp.toUpperCase()+"</code>\n" +
                        "                                        <subclassNumber>00267</subclassNumber>\n" +
                        "  </includedRefData>\n");
            }
        }
        return sb.toString();
    }

    public static String getBusinessProduct(List<String> list){
        StringBuffer sb = new StringBuffer();
        if(list != null) {
            if (!list.isEmpty()){
                for (String temp : list) {
                    sb.append("<includedRefData>\n" +
                            "                                        <code>"+temp+"</code>\n" +
                            "                                        <subclassNumber>00266</subclassNumber>\n" +
                            "  </includedRefData>\n");
                }
            }
        }
        return sb.toString();
    }

    public static String getShareOfOrder(String shareOfOrder,String reinsuranceProduct){
        StringBuffer sb = new StringBuffer();
        if("CAPPEDQA".equals(reinsuranceProduct)){
            sb.append("<shareCondition xsi:type=\"ns1:LfShareCondition\">\n" +
                    "     <cededShare>100</cededShare>\n" +
                    "  </shareCondition>");
        }else{
            sb.append("<shareCondition xsi:type=\"ns1:LfShareCondition\">\n" +
                    "     <cededShare>"+shareOfOrder.replace("%","")+"</cededShare>\n" +
                    "  </shareCondition>");
        }
        return sb.toString();
    }

    public static String getTaskList(String taskList){
        StringBuffer sb = new StringBuffer();
        if (StringUtils.hasText(taskList)) {
            for (String str : taskList.split(",")) {
                sb.append("<businessAdministratorTask>\n" +
                        "      <code>" + str + "</code>\n" +
                        "  <subclassNumber>73</subclassNumber>\n" +
                        "  </businessAdministratorTask>\n");
            }
        }
        return sb.toString();
    }

    public static String getServiceCompanyAndTask(String serviceCompany,String task){
        StringBuffer sb = new StringBuffer();
        if(StringUtils.hasText(serviceCompany)) {
            for (String temp : serviceCompany.split(",")) {
                sb.append("<businessPartnerRelationship>\n" +
                        "                                                        <businessPartner>\n" +
                        "                                                                <identifier>" + temp + "</identifier>\n" +
                        "                                                        </businessPartner>\n" +
                        "                                                        <relationshipType>\n" +
                        "                                                                <code>AGTR</code>\n" +
                        "                                                                <subclassNumber>55</subclassNumber>\n" +
                        "                                                        </relationshipType>\n" +
                        getTaskList(task) +
                        "                                                </businessPartnerRelationship>");
            }
        }
        return sb.toString();
    }

    public static String getCedentAndTask(String cedent,String task){
        StringBuffer sb = new StringBuffer();
        if(StringUtils.hasText(cedent)) {
            for (String temp : cedent.split(",")) {
                sb.append("<businessPartnerRelationship>\n" +
                        "                                                        <businessPartner>\n" +
                        "                                                                <identifier>"+temp+"</identifier>\n" +
                        "                                                        </businessPartner>\n" +
                        "                                                        <relationshipType>\n" +
                        "                                                                <code>CR</code>\n" +
                        "                                                                <subclassNumber>55</subclassNumber>\n" +
                        "                                                        </relationshipType>\n" +
                        getTaskList(task)+
                        "                                                </businessPartnerRelationship>");
            }
        }
        return sb.toString();
    }

    public static String getBrokerAndTask(String Broker,String Task){
        StringBuffer sb = new StringBuffer();
        if(StringUtils.hasText(Broker)) {
            for (String temp : Broker.split(",")) {
                sb.append("<businessPartnerRelationship>\n" +
                        "                                                        <businessPartner>\n" +
                        "                                                                <identifier>"+temp+"</identifier>\n" +
                        "                                                        </businessPartner>\n" +
                        "                                                        <relationshipType>\n" +
                        "                                                                <code>BR</code>\n" +
                        "                                                                <subclassNumber>55</subclassNumber>\n" +
                        "                                                        </relationshipType>\n" +
                        getTaskList(Task)+
                        "                                                </businessPartnerRelationship>");
            }
        }
        return sb.toString();
    }

    public static String getReinsurer(String reinsurer,String task){
        StringBuffer sb = new StringBuffer();
        if(StringUtils.hasText(reinsurer)) {
            for (String temp : reinsurer.split(",")) {
                sb.append("<businessPartnerRelationship>\n" +
                        "                                                        <businessPartner>\n" +
                        "                                                                <identifier>"+temp+"</identifier>\n" +
                        "                                                        </businessPartner>\n" +
                        "                                                        <relationshipType>\n" +
                        "                                                                <code>RR</code>\n" +
                        "                                                                <subclassNumber>55</subclassNumber>\n" +
                        "                                                        </relationshipType>\n" +
                        getTaskList(task)+
                        "                                                </businessPartnerRelationship>");
            }
        }
        return sb.toString();
    }




}
