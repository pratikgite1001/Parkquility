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

"Create monthly parking and make payment of it"

def rulesData = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('PermitParkingData', 'P20')
def addressData = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('AddressData', rulesData.get("Address Data"))
def paymentData = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('PaymentData', rulesData.get("Payment Data"))


"click on rules link in permit parking"
//WebUI.scrollToElement(findTestObject('Object Repository/PermitParking/RulesPage/link_rules'), 10)
WebUI.click(findTestObject('Object Repository/PermitParking/RulesPage/link_rules'))

"create new rule"
CustomKeywords.'com.Parkquility.app.Keywords.PermitParking.createNewRule'(rulesData)

"validate created monthly parking and fill form till next button"
CustomKeywords.'com.Parkquility.app.Keywords.PermitParking.validateMonthlyParking'(rulesData)

"fill monthly parking form and pay"
CustomKeywords.'com.Parkquility.app.Keywords.PermitParking.fillMonthlyParkingPass'(rulesData, addressData, paymentData)

