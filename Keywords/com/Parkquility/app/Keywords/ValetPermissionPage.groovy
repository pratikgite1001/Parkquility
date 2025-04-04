package com.Parkquility.app.Keywords

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class ValetPermissionPage {
	ReservationPage res = new ReservationPage()
	@Keyword
	def makeFeatureEnable(String featureName) {
		WebUI.click(findTestObject('Object Repository/HomePage/link_valet_permission'))
		try {
			WebUI.verifyElementNotHasAttribute(findTestObject('Object Repository/ValetPermissionPage/Building-1,Ecospace/text_box_of_feature',["FeatureName" : "Hide Cash"]), "checked", 0)
			WebUI.click(findTestObject('Object Repository/ValetPermissionPage/Building-1,Ecospace/text_box_of_feature',["FeatureName" : featureName]))

			WebUI.delay(1)
			WebUI.click(findTestObject('Object Repository/ValetPermissionPage/btn_submit'))


			return true
		}catch(StepFailedException e) {
			return false
		}
	}

	@Keyword
	def makeFeatureDisable(String featureName) {
		WebUI.click(findTestObject('Object Repository/HomePage/link_valet_permission'))

		WebUI.verifyElementHasAttribute(findTestObject('Object Repository/ValetPermissionPage/Building-1,Ecospace/text_box_of_feature',["FeatureName" : featureName]), "checked", 10)
		WebUI.click(findTestObject('Object Repository/ValetPermissionPage/Building-1,Ecospace/text_box_of_feature',["FeatureName" : featureName]))

		WebUI.delay(1)
		WebUI.click(findTestObject('Object Repository/ValetPermissionPage/btn_submit'))
	}

	@Keyword
	def verifyCashOptionPresent(def customerData, String section, String customer) {
		"click on reservation link"
		WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

		"click on customerSection"
		res.clickOnCustomerSection(customer)

		"click on reservation card"
		res.clickonReservationCard(customerData, section, customer)

		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10)
		assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))

		WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_cash'), 10)
		WebUI.delay(1)
	}

	@Keyword
	def verifyCashOptionNotPresent(def customerData, String section, String customer) {
		"click on reservation link"
		WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

		"click on customerSection"
		res.clickOnCustomerSection(customer)

		"click on reservation card"
		res.clickonReservationCard(customerData, section, customer)

		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10)
		assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))

		WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_cash'), 10)
		WebUI.delay(1)
	}
}
