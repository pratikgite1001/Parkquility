package com.Parkquility.app.Keywords;

import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testdata.InternalData
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

public class LoginPage {
	ReservationPage res= new ReservationPage()


	@Keyword
	def loginToPortal(def loginData, String url) {

		try{
			WebUI.openBrowser('')

			WebUI.maximizeWindow();

			WebUI.navigateToUrl(url)

			WebUI.waitForPageLoad(20)

			WebUI.click(findTestObject('Object Repository/LoginPage/input_L O G I N_email'))

			WebUI.setText(findTestObject('Object Repository/LoginPage/input_L O G I N_email'), loginData.get("Email"))

			WebUI.setText(findTestObject('Object Repository/LoginPage/input_L O G I N_password'), loginData.get("Password"))

			WebUI.click(findTestObject('Object Repository/LoginPage/button_Login'))

			//res.verifyToastMessage("Login succes")
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/txt_tost_message', ["ToastMessage" : "Incorrect username/password"]), 10)

			WebUI.waitForPageLoad(10)

			WebUI.waitForElementVisible(findTestObject('Object Repository/HomePage/link_dashboard'), 15)

			String pp=WebUI.getText(findTestObject('Object Repository/HomePage/link_dashboard'))

			WebUI.verifyMatch(pp, "Dashboard", false)

			GlobalVariable.loginDetails.put("isLogin", true)
			GlobalVariable.loginDetails.put("browserOpen", true)
		} catch(Exception e){
			KeywordUtil.markFailedAndStop(e.getMessage())
		}
	}

	@Keyword
	def navigateToDashboard() {
		//WebUI.click(findTestObject('Object Repository/HomePage/link_dashboard'))
		WebUI.delay(2)
	}
}
