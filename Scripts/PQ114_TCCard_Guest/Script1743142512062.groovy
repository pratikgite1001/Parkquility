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

'Create new Reservation for vip Guest'
def customerdata = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('Customerdata', 'CG2')
def updatedCustomerData = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('Customerdata', 'CG22')
def paymentData = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('PaymentData', 'P1')

'fill new create reservation form'
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.fillCreateReservationForm'(customerdata, "Guest")

'validate created reservation in booking details and edit reservation page'
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateCreatedReservation'(customerdata, "In", "Guest")

//get hash code from api response
def response = WS.sendRequest(findTestObject('Object Repository/PaymentPage/GetHashCodeRequest',["ID": GlobalVariable.ID]))
WS.verifyResponseStatusCode(response, 200)
GlobalVariable.hashCode = WS.getElementPropertyValue(response, "data.hash")

//verify card payment scenario
CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.makeValetWebPayment'(updatedCustomerData, paymentData, "In", "Guest")

//validate reservation booking details after card payment
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateCreatedReservation'(updatedCustomerData, "Car Requested", "Guest")

"validate used features are in active state"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyActiveStatus'(updatedCustomerData, "Car Requested", "Guest")
