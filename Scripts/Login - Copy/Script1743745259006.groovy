import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')

WebUI.maximizeWindow();

def loginData = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'("LoginData","L1")

WebUI.navigateToUrl('https://stagingportal.parkquility.com/')

WebUI.click(findTestObject('Object Repository/LoginPage/input_L O G I N_email'))

assert true : "this is assertion true"

WebUI.setText(findTestObject('Object Repository/LoginPage/input_L O G I N_email'), loginData.get("Email"))

WebUI.verifyElementNotPresent(findTestObject('Object Repository/HomePage/link_dashboard'), 10)

//assert CustomKeywords.'com.Parkquility.app.common.CommonKeywords.verifyElementNotVisible'(findTestObject('Object Repository/LoginPage/input_L O G I N_password')) : "Rada Rada input checkbox should not visible"
WebUI.setText(findTestObject('Object Repository/LoginPage/input_L O G I N_password'), loginData.get("Password"))

WebUI.click(findTestObject('Object Repository/LoginPage/button_Login'))




WebUI.verifyElementPresent(findTestObject('Object Repository/HomePage/div_Login success - Copy'), 10)
String gg=WebUI.getText(findTestObject('Object Repository/HomePage/div_Login success - Copy'))
WebUI.verifyMatch(gg, "Login success", false)
//WebUI.verifyElementPresent(findTestObject('Object Repository/HomePage/txt_tost_login_success'), 20)

//WebUI.delay(1)
//KeywordUtil.logInfo( WebUI.getText(findTestObject('Object Repository/HomePage/txt_tost_login_success'), FailureHandling.STOP_ON_FAILURE) )
//WebUI.verifyElementVisible(findTestObject('Object Repository/HomePage/txt_tost_login_success'))

WebUI.waitForElementVisible(findTestObject('Object Repository/HomePage/link_dashboard'), 15)

String pp=WebUI.getText(findTestObject('Object Repository/HomePage/link_dashboard'))

WebUI.verifyMatch(pp, "Dashboard", false)
//WebUI.closeBrowser()

