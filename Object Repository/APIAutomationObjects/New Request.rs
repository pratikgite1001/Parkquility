<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>New Request</name>
   <tag></tag>
   <elementGuidId>6b53dfa0-008b-4080-908f-fb72d72f82ae</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <useRalativeImagePath>false</useRalativeImagePath>
   <autoUpdateContent>true</autoUpdateContent>
   <connectionTimeout>0</connectionTimeout>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent></httpBodyContent>
   <httpBodyType></httpBodyType>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Length</name>
      <type>Main</type>
      <value>0</value>
      <webElementGuid>cb57be3b-6ca3-4d13-960f-920d13dcd470</webElementGuid>
   </httpHeaderProperties>
   <katalonVersion>8.6.6</katalonVersion>
   <maxResponseSize>0</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <restRequestMethod>POST</restRequestMethod>
   <restUrl>https://securetoken.googleapis.com/v1/token?key=AIzaSyAwuXaQy86JJJSf95NfcL5PK9d_NbA_9hc&amp;grant_type=refresh_token&amp;refresh_token=AMf-vBzo9U1fHNfmYedIZqiDQObnmmvyprCb1FHcZS9A6ognrrUll0WE4XQor_uR01SCObr_R5HZNx67gA5heJ4em67hK7QO-rWfAvCUIQt5O1N31bRLWyjPyFENpgW2XgR8ziKvtmgCjROn0HbpkEnb2JPYaJZwdfeg4VrCf1rlk7dV820dnIAOWR_4yih-ymPpIDlTqhPw-XFv8GKLexBmmcSNce1jOhaOEG2z8p7fGJUG9BB3SJ8</restUrl>
   <serviceType>RESTful</serviceType>
   <soapBody></soapBody>
   <soapHeader></soapHeader>
   <soapRequestMethod></soapRequestMethod>
   <soapServiceEndpoint></soapServiceEndpoint>
   <soapServiceFunction></soapServiceFunction>
   <socketTimeout>0</socketTimeout>
   <useServiceInfoFromWsdl>true</useServiceInfoFromWsdl>
   <verificationScript>import static org.assertj.core.api.Assertions.*

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webservice.verification.WSResponseManager

import groovy.json.JsonSlurper
import internal.GlobalVariable as GlobalVariable

RequestObject request = WSResponseManager.getInstance().getCurrentRequest()

ResponseObject response = WSResponseManager.getInstance().getCurrentResponse()</verificationScript>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>
