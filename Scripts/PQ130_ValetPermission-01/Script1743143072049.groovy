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

def customerdata = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('Customerdata', 'CV1')
def customerdata2 = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('Customerdata', 'CV11')

//WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

"fill create reservation form"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.fillCreateReservationForm'(customerdata, "Visitor")


"enable feature"
boolean flag = CustomKeywords.'com.Parkquility.app.Keywords.ValetPermissionPage.makeFeatureEnable'("Hide Cash")
if(flag) {
	CustomKeywords.'com.Parkquility.app.Keywords.ValetPermissionPage.verifyCashOptionNotPresent'(customerdata1, "Ongoing", "Visitor")
	
	CustomKeywords.'com.Parkquility.app.Keywords.ValetPermissionPage.verifyCashOptionNotPresent'(customerdata2, "In", "Guest")
	
	"disable feature"
	CustomKeywords.'com.Parkquility.app.Keywords.ValetPermissionPage.makeFeatureDisable'("Hide Cash")
	
	CustomKeywords.'com.Parkquility.app.Keywords.ValetPermissionPage.verifyCashOptionPresent'(customerdata1, "Ongoing", "Visitor")
	
	CustomKeywords.'com.Parkquility.app.Keywords.ValetPermissionPage.verifyCashOptionPresent'(customerdata2, "In", "Guest")
		
}
else {
	CustomKeywords.'com.Parkquility.app.Keywords.ValetPermissionPage.verifyCashOptionNotPresent'(customerdata1, "Ongoing", "Visitor")
	
	CustomKeywords.'com.Parkquility.app.Keywords.ValetPermissionPage.verifyCashOptionNotPresent'(customerdata2, "In", "Guest")
	
	"enable feature"
	CustomKeywords.'com.Parkquility.app.Keywords.ValetPermissionPage.makeFeatureDisable'("Hide Cash")
	
	CustomKeywords.'com.Parkquility.app.Keywords.ValetPermissionPage.verifyCashOptionPresent'(customerdata1, "Ongoing", "Visitor")
	
	CustomKeywords.'com.Parkquility.app.Keywords.ValetPermissionPage.verifyCashOptionPresent'(customerdata2, "In", "Guest")
}


