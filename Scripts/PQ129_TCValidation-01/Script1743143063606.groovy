import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.keyword.builtin.VerifyMatchKeyword as VerifyMatchKeyword
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import graphql.Assert as Assert
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.configuration.RunConfiguration as RC

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI



def customerdata = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('Customerdata', 'C4')

"click on reservation link"
WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/link_reservation'), 10)
WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

"fill create reservation form"
//CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.fillCreateReservationForm'(customerdata)

"validate created reservation"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateCreatedReservation'(customerdata)

WebUI.scrollToElement(findTestObject('Object Repository/HomePage/link_dashboard'), 10)


WebUI.click(findTestObject('Object Repository/HomePage/link_valet_self_validate'))

WebUI.click(findTestObject('Object Repository/ValidationPage/btn_validation_number'))

WebUI.verifyElementVisible(findTestObject('Object Repository/ValidationPage/input_licensePlateNo'))
WebUI.setText(findTestObject('Object Repository/ValidationPage/input_licensePlateNo'), customerdata.get("License Plate Number"))

//select country from dropdown
assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/dropdown_select_country')), "select country dropDown not visible on create reservation form"
assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/dropdown_select_country')), "select country dropDown not visible on create reservation form"
WebUI.click(findTestObject('Object Repository/ReservationPage/dropdown_select_country'))
assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/input_enter_country_name')), "enter country name inputbox not visible on create reservation form"
WebUI.setText(findTestObject('Object Repository/ReservationPage/input_enter_country_name'),customerdata.get("Country") )

//enter mobile number
assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/option_first_option')), "Enter correct country name"
assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/option_first_option')), "first option in sect country dropdown is not clickable"
WebUI.click(findTestObject('Object Repository/ReservationPage/option_first_option'))
assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/input_mobile_number')), "mobile number field not visible in create reservation form"
WebUI.setText(findTestObject('Object Repository/ReservationPage/input_mobile_number'), customerdata.get("Mobile Number"))

WebUI.click(findTestObject('Object Repository/ValidationPage/btn_get_details'))

def price = WebUI.getText(findTestObject('Object Repository/ValidationPage/price_reservation')).replaceAll('[^\\d.]', '') as double

WebUI.click(findTestObject('Object Repository/ValidationPage/select_validation_count'))

String validationCount = "1"

WebUI.verifyElementVisible(findTestObject('Object Repository/ValidationPage/select_validation_count_options', ["Count" : validationCount]))
WebUI.click(findTestObject('Object Repository/ValidationPage/select_validation_count_options', ["Count" : validationCount]))

def netPayableAmount = WebUI.getText(findTestObject('Object Repository/ValidationPage/txt_net_payable_amount')).replaceAll('[^\\d.]', '') as double

def expectedPayableAmount = price - 40.00 * validationCount.toDouble()

WebUI.verifyMatch(netPayableAmount.toString(), expectedPayableAmount.toString(), false)

WebUI.click(findTestObject('Object Repository/ValidationPage/btn_validate', ["Count" : validationCount]))

WebUI.delay(10)

//enter mobile number
assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/input_mobile_number')), "mobile number field not visible in create reservation form"
WebUI.setText(findTestObject('Object Repository/ReservationPage/input_mobile_number'), customerdata.get("Mobile Number"))

WebUI.click(findTestObject('Object Repository/ValidationPage/btn_get_details'))

String netPayableAmount1 = WebUI.getText(findTestObject('Object Repository/ValidationPage/price_reservation'))
String expectedAmount = "\$"+String.format('%.2f', netPayableAmount)

WebUI.verifyMatch(netPayableAmount1, expectedAmount, false)

"click on reservation link"
WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/link_reservation'), 10)
WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

WebUI.verifyElementPresent(findTestObject('Object Repository/ValidationPage/symbol_validation_on_card', ["LicensePlateNo" : customerdata.get("License Plate Number")]), 10)
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.clickonReservationCard'(customerdata,"Ongoing")

WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_parking_fee'), 10)
String actualParkingFee = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_parking_fee'))

WebUI.verifyMatch(actualParkingFee, expectedAmount, false)











