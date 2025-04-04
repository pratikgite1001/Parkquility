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

//open browser
WebUI.openBrowser("")

//navigate to url and maximize window
WebUI.navigateToUrl("https://app.powerbi.com/groups/me/apps/51d98988-eb28-452d-9357-ade2d137bb27/rdlreports/137c0283-ea69-42bd-87c6-fa12fbe56b2b?ctid=de029b37-15b1-4cfc-8b61-8a937b712443&experience=power-bi&clientSideAuth=0&noSignUpCheck=1&UPN=abhishek.anand@parkquility.com")
WebUI.maximizeWindow()
WebUI.waitForElementVisible(findTestObject('Object Repository/CSVDownload/input_password'), 15)

//verify usernammme
String actualUserName = WebUI.getText(findTestObject('Object Repository/CSVDownload/txt_username_display_name'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyMatch(actualUserName, "abhishek.anand@parkquility.com", false, FailureHandling.CONTINUE_ON_FAILURE)

//enter password
WebUI.click(findTestObject('Object Repository/CSVDownload/input_password'))
WebUI.setText(findTestObject('Object Repository/CSVDownload/input_password'), "Park@1234")

//click on sign in button
WebUI.click(findTestObject('Object Repository/CSVDownload/btn_signIn'))

//click on save password Yes button
WebUI.waitForElementVisible(findTestObject('Object Repository/CSVDownload/btn_yes_savePassword'), 10)
WebUI.click(findTestObject('Object Repository/CSVDownload/btn_yes_savePassword'))
WebUI.delay(1)
WebUI.waitForElementVisible(findTestObject('Object Repository/CSVDownload/txt_parkquility'), 15)
WebUI.switchToFrame(findTestObject('Object Repository/CSVDownload/iframe_1'), 15)

//list of month for download csv file
def months = ['August 2023', 'September 2023', 'October 2023', 'November 2023', 'December 2023']

//download each csv file
for (month in months) {
	//click on select issued month dropdown and click on  month 
	WebUI.click(findTestObject('Object Repository/CSVDownload/dropdown_select_issued_month'))
	WebUI.waitForElementVisible(findTestObject('Object Repository/CSVDownload/options_issued_month',["Month" : month]), 10)
	WebUI.click(findTestObject('Object Repository/CSVDownload/options_issued_month',["Month" : month]))
	
	//click on view report button
	WebUI.delay(1)
	WebUI.waitForElementClickable(findTestObject('Object Repository/CSVDownload/btn_view_report'), 10)
	WebUI.click(findTestObject('Object Repository/CSVDownload/btn_view_report'))
	
	WebUI.waitForElementVisible(findTestObject('Object Repository/CSVDownload/tct_column_notice_number'), 10)
	
	//click on export dropdown 
	WebUI.click(findTestObject('Object Repository/CSVDownload/dropdown_export'))
	WebUI.delay(1)
	
	//click on option comma seperated values (.csv)
	WebUI.click(findTestObject('Object Repository/CSVDownload/option_comma_seperated_value'))
	WebUI.delay(2)
	WebUI.verifyElementPresent(findTestObject('Object Repository/CSVDownload/txt_download_successfull_toast message'), 10)
	
	

}





