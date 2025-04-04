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

"verify cash payment feature for Visitor"
def customerdata = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('Customerdata', 'CV4')

CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.fillCreateReservationForm'(customerdata, "Visitor")
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateCreatedReservation'(customerdata, "Ongoing", "Visitor")

CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyCashPayment'(customerdata, "Ongoing", "Visitor")
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateCreatedReservation'(customerdata, "Ongoing", "Visitor")

CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.checkoutReservation'(customerdata, "Ongoing", "Visitor")
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateCreatedReservation'(customerdata, "Completed Reservations", "Visitor")
CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.verifyDayReport'(customerdata, "Visitor")
CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.verifyTransactionReport'(customerdata, "Visitor")
//CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.verifyTipReport'(customerdata)
GlobalVariable.paymentType=""
GlobalVariable.paidAmount=""
GlobalVariable.parkingFee=""
GlobalVariable.wholeMobileNumber=""

