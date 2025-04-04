import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import internal.GlobalVariable

def customerdata = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('Customerdata', 'CP1')
def updatedCustomerData = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('Customerdata', 'CP11')
def paymentData = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('PaymentData', 'P1')

'fill new create reservation form'
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.fillCreateReservationForm'(customerdata,  "Prepaid")

'validate created reservation'
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateCreatedReservation'(customerdata, "In", "Prepaid")

"verify active features"
//CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyActiveStatus'(customerdata, "In", "Prepaid")

'make payment of reservation and verify'
//make payment
//CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.makePayment'(paymentData, updatedCustomerData, "In", "Prepaid")

def response = WS.sendRequest(findTestObject('Object Repository/PaymentPage/GetHashCodeRequest',["ID": GlobalVariable.ID]))
WS.verifyResponseStatusCode(response, 200)
GlobalVariable.hashCode = WS.getElementPropertyValue(response, "data.hash")

CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.makeValetWebPayment'(updatedCustomerData, paymentData, "In", "Prepaid")



//validate reservation bookg details
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateCreatedReservation'(updatedCustomerData, "Car Requested", "Prepaid")

"validate used features are  active state"
//CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyActiveStatus'(updatedCustomerData, "Car Requested", "Prepaid")
