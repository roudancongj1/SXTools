package com.sics.sxt.service.impl;

import com.sics.sxt.common.GXml;
import com.sics.sxt.pojo.bo.LFBusiness;
import com.sics.sxt.service.XmlService;
import org.springframework.stereotype.Service;


@Service
public class LfBusinessXmlServiceImpl implements XmlService {
    @Override
    public String creat(Object obj) {
        LFBusiness lfBusiness = (LFBusiness) obj;
        StringBuilder sb = new StringBuilder();

        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:enc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://www.SicsNt.com/Business\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">");
        sb.append("<SOAP-ENV:Header />");
        sb.append("<SOAP-ENV:Body>");
        sb.append("<swbep:createLfBusiness xmlns:swbep=\"urn:SicsWsBusinessEntryPoint\">");
        sb.append("     <genericInput><interactiveMessageResponses><answerYes>BS00138</answerYes><answerNo>BS0782</answerNo></interactiveMessageResponses></genericInput>\n");
        sb.append("<createBusiness>\n");
        sb.append("<identifier>" + lfBusiness.getId() + "</identifier>\n");
        sb.append("<title>" + lfBusiness.getTitle() + "</title>");

        sb.append(GXml.getLevelOfBusiness(lfBusiness.getLevel()));
        sb.append(GXml.getTypeOfBusiness(lfBusiness.getType()));

        sb.append("<insuredPeriod xsi:type=\"ns1:LfInsuredPeriod\">");

        sb.append(GXml.getInsuredPeriodBeginDate(lfBusiness.getBeginDateTime()));
        sb.append(GXml.getUnderwritingYear(lfBusiness.getBeginDateTime()));
        sb.append(GXml.getInsuredPeriodEndDate(lfBusiness.getEndDateTime()));

        sb.append("<lifeCyclePhase>");
        sb.append("<lifeCycleStatus xsi:type=\"ns1:DeclinedLifeCycleStatus\">");
        sb.append("<refAgreementLifeCycleStatus><code>OA</code><subclassNumber>90</subclassNumber> </refAgreementLifeCycleStatus>");
        sb.append("</lifeCycleStatus>");

        sb.append(" <scopeOfCover xsi:type=\"ns1:LfScopeOfCover\">");
        sb.append(" <name>Agreement</name><isActive>true</isActive><currency><isoAlpha>CNY</isoAlpha></currency>");
        sb.append(" <benefitType>\n" +
                "                                                                <code>CAPITAL</code>\n" +
                "                                                                <subclassNumber>823</subclassNumber>\n" +
                "                                                        </benefitType>");
        sb.append("<classification xsi:type=\"ns1:LfClassification\">");
        sb.append("<includedResidenceCountryGroups><code>Asia</code></includedResidenceCountryGroups>");
        sb.append("<includedCountryGroups><code>Asia</code></includedCountryGroups>");
        sb.append("                                                                <includedRefData>\n" +
                "                                                                        <code>TREATY</code>\n" +
                "                                                                        <subclassNumber>00053</subclassNumber>\n" +
                "                                                                </includedRefData> ");

        sb.append(GXml.getNatureOfBusiness(lfBusiness.getReinsurancePremiumMethod()));
        sb.append(GXml.getReinsuranceProduct(lfBusiness.getReinsuranceProduct()));
        sb.append(GXml.getBenefitCovered(lfBusiness.getBenefitCovered()));
        sb.append(GXml.getInsuranceProduct(""));
        sb.append(GXml.getLongShortTerm(lfBusiness.getLongShortTerm()));
        sb.append(GXml.getAdditionalClassification(lfBusiness.getAdditionalClassification()));
        sb.append(GXml.getBusinessProduct(lfBusiness.getProductList()));

        sb.append("</classification>\n");
        sb.append("<reportingUnitRelationList>\n" +
                "                                                                <reportingUnitRelation>\n" +
                "                                                                        <reportingUnit>\n" +
                "                                                                                <code>BID</code>\n" +
                "                                                                        </reportingUnit>\n" +
                "                                                                </reportingUnitRelation>\n" +
                "                                                        </reportingUnitRelationList>");

        sb.append(GXml.getShareOfOrder(lfBusiness.getShareOfOrder(),lfBusiness.getReinsuranceProduct()));

        sb.append("<generalBasesCondition>\n" +
                "                                                                <levelOfAdministrativeData>\n" +
                "                                                                        <code>SUMMARY</code>\n" +
                "                                                                        <subclassNumber>809</subclassNumber>\n" +
                "                                                                </levelOfAdministrativeData>\n" +
                "                                                                <administrationResponsibility>\n" +
                "                                                                        <code>CEDINGOFFICE</code>\n" +
                "                                                                        <subclassNumber>972</subclassNumber>\n" +
                "                                                                </administrationResponsibility>\n" +
                "                                                                <administrationBasis>\n" +
                "                                                                        <code>BULK</code>\n" +
                "                                                                        <subclassNumber>971</subclassNumber>\n" +
                "                                                                </administrationBasis>\n" +
                "                                                                <facReversibilityRule>\n" +
                "                                                                        <code>MUST</code>\n" +
                "                                                                        <subclassNumber>977</subclassNumber>\n" +
                "                                                                </facReversibilityRule>\n" +
                "                                                                <valuationAnalysisAdminBasis>\n" +
                "                                                                        <code>BULK</code>\n" +
                "                                                                        <subclassNumber>971</subclassNumber>\n" +
                "                                                                </valuationAnalysisAdminBasis>\n" +
                "                                                                <reinsuranceException>\n" +
                "                                                                        <code>ASSUMED</code>\n" +
                "                                                                        <subclassNumber>839</subclassNumber>\n" +
                "                                                                </reinsuranceException>\n" +
                "                                                                <businessCovered>\n" +
                "                                                                        <code>INDIV</code>\n" +
                "                                                                        <subclassNumber>973</subclassNumber>\n" +
                "                                                                </businessCovered>\n" +
                "                                                                <coversGroupPolicyType>true</coversGroupPolicyType>\n" +
                "                                                                <coversEmployeeBenefits>true</coversEmployeeBenefits>\n" +
                "                                                        </generalBasesCondition>\n" +
                "                                                </scopeOfCover>\n" +
                "                                                <isRegistrationComplete>false</isRegistrationComplete>\n" +
                "                                        </lifeCyclePhase>\n");
        sb.append("<businessPartnerRelationshipList>\n");

        sb.append(GXml.getServiceCompanyAndTask(lfBusiness.getServiceCompany(),lfBusiness.getServiceCompanyTask()));
        sb.append(GXml.getCedentAndTask(lfBusiness.getCedent(),lfBusiness.getTaskCedent()));
        sb.append(GXml.getReinsurer(lfBusiness.getReinsurer(),""));
        sb.append(GXml.getBrokerAndTask(lfBusiness.getBroker(),lfBusiness.getTaskBroker()));

        sb.append("</businessPartnerRelationshipList>");
        sb.append("</insuredPeriod>");
        sb.append("</createBusiness>");
        sb.append("</swbep:createLfBusiness>");
        sb.append("</SOAP-ENV:Body>");
        sb.append("</SOAP-ENV:Envelope>");

        return sb.toString();
    }
}

