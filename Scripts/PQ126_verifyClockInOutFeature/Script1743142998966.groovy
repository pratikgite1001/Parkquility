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
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.click(findTestObject('Object Repository/HomePage/link_clock_in_out'))

"create new runner"
WebUI.click(findTestObject('Object Repository/ClockInOutPage/btn_runner'))
WebUI.setText(findTestObject('Object Repository/ClockInOutPage/input_first_name'), "Rohan1", FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Object Repository/ClockInOutPage/input_last_name'), "Deshmukh1", FailureHandling.CONTINUE_ON_FAILURE)
WebUI.click(findTestObject('Object Repository/ClockInOutPage/btn_submit'))

"verify runner info"
WebUI.verifyElementPresent(findTestObject('Object Repository/ClockInOutPage/created_runner_in_list',["RunnerName" : "Rohan1 Deshmukh1"]), 5, FailureHandling.CONTINUE_ON_FAILURE)

"verify check in icon and its color"
String actualCheckInIconText = WebUI.getAttribute(findTestObject('Object Repository/ClockInOutPage/created_runner_check_in_out_icon',["RunnerName" : "Rohan1 Deshmukh1"]), "class", FailureHandling.CONTINUE_ON_FAILURE)
String actualCheckInIconColor = WebUI.getAttribute(findTestObject('Object Repository/ClockInOutPage/created_runner_check_in_out_icon',["RunnerName" : "Rohan1 Deshmukh1"]), "style", FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyMatch(actualCheckInIconText, "fa fa-sign-in", false, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyMatch(actualCheckInIconColor, "color: green;", false, FailureHandling.CONTINUE_ON_FAILURE)

"verify edit icon is clickable"
String opacityOfEditIcon2 = WebUI.getAttribute(findTestObject('Object Repository/ClockInOutPage/edit_icon_of_runner',["RunnerName" : "Rohan1 Deshmukh1"]), "style", FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyMatch(opacityOfEditIcon2, "opacity: 1;", false, FailureHandling.CONTINUE_ON_FAILURE)

"verify delete icon is clickable"
String opacityOfDeleteIcon2 = WebUI.getAttribute(findTestObject('Object Repository/ClockInOutPage/delete_icon_of_runner',["RunnerName" : "Rohan1 Deshmukh1"]), "style", FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyMatch(opacityOfDeleteIcon2, "opacity: 1;", false, FailureHandling.CONTINUE_ON_FAILURE)

"update runner"
WebUI.click(findTestObject('Object Repository/ClockInOutPage/edit_icon_of_runner',["RunnerName" : "Rohan1 Deshmukh1"]))
WebUI.clearText(findTestObject('Object Repository/ClockInOutPage/input_first_name'))
WebUI.setText(findTestObject('Object Repository/ClockInOutPage/input_first_name'), "Rohan", FailureHandling.CONTINUE_ON_FAILURE)
WebUI.clearText(findTestObject('Object Repository/ClockInOutPage/input_last_name'))
WebUI.setText(findTestObject('Object Repository/ClockInOutPage/input_last_name'), "Deshmukh", FailureHandling.CONTINUE_ON_FAILURE)
WebUI.click(findTestObject('Object Repository/ClockInOutPage/btn_update'))

"verify update info"
WebUI.verifyElementNotPresent(findTestObject('Object Repository/ClockInOutPage/created_runner_in_list',["RunnerName" : "Rohan1 Deshmukh1"]), 5, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyElementPresent(findTestObject('Object Repository/ClockInOutPage/created_runner_in_list',["RunnerName" : "Rohan Deshmukh"]), 5, FailureHandling.CONTINUE_ON_FAILURE)

"verify check in icon and its color"
String actualCheckInIconText1 = WebUI.getAttribute(findTestObject('Object Repository/ClockInOutPage/created_runner_check_in_out_icon',["RunnerName" : "Rohan Deshmukh"]), "class", FailureHandling.CONTINUE_ON_FAILURE)
String actualCheckInIconColor1 = WebUI.getAttribute(findTestObject('Object Repository/ClockInOutPage/created_runner_check_in_out_icon',["RunnerName" : "Rohan Deshmukh"]), "style", FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyMatch(actualCheckInIconText1, "fa fa-sign-in", false, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyMatch(actualCheckInIconColor1, "color: green;", false, FailureHandling.CONTINUE_ON_FAILURE)

"verify edit icon is clickable i.e opacity is 1"
String opacityOfEditIcon = WebUI.getAttribute(findTestObject('Object Repository/ClockInOutPage/edit_icon_of_runner',["RunnerName" : "Rohan Deshmukh"]), "style", FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyMatch(opacityOfEditIcon, "opacity: 1;", false, FailureHandling.CONTINUE_ON_FAILURE)

"verify delete icon is clickable i.e opacity is 1"
String opacityOfDeleteIcon5 = WebUI.getAttribute(findTestObject('Object Repository/ClockInOutPage/delete_icon_of_runner',["RunnerName" : "Rohan Deshmukh"]), "style", FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyMatch(opacityOfDeleteIcon5, "opacity: 1;", false, FailureHandling.CONTINUE_ON_FAILURE)

"checkInRunner"
WebUI.click(findTestObject('Object Repository/ClockInOutPage/created_runner_check_in_out_icon',["RunnerName" : "Rohan Deshmukh"]))

"verify check in icon and its color"
String actualCheckInIconText12 = WebUI.getAttribute(findTestObject('Object Repository/ClockInOutPage/created_runner_check_in_out_icon',["RunnerName" : "Rohan Deshmukh"]), "class", FailureHandling.CONTINUE_ON_FAILURE)
String actualCheckInIconColor12 = WebUI.getAttribute(findTestObject('Object Repository/ClockInOutPage/created_runner_check_in_out_icon',["RunnerName" : "Rohan Deshmukh"]), "style", FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyMatch(actualCheckInIconText12, "fa fa-sign-out", false, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyMatch(actualCheckInIconColor12, "color: red;", false, FailureHandling.CONTINUE_ON_FAILURE)

"verify edit icon is not clickable i.e opacity is 0.5"
String opacityOfEditIcon6 = WebUI.getAttribute(findTestObject('Object Repository/ClockInOutPage/edit_icon_of_runner',["RunnerName" : "Rohan Deshmukh"]), "style", FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyMatch(opacityOfEditIcon6, "opacity: 0.5;", false, FailureHandling.CONTINUE_ON_FAILURE)

"verify delete icon is not clickable i.e opacity is 0.5"
String opacityOfDeleteIcon7 = WebUI.getAttribute(findTestObject('Object Repository/ClockInOutPage/delete_icon_of_runner',["RunnerName" : "Rohan Deshmukh"]), "style", FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyMatch(opacityOfDeleteIcon7, "opacity: 0.5;", false, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.delay(1)
"verify search filter"
WebUI.setText(findTestObject('Object Repository/ClockInOutPage/input_search_licenseplate'), "Rohan", FailureHandling.STOP_ON_FAILURE)
WebUI.verifyElementPresent(findTestObject('Object Repository/ClockInOutPage/created_runner_in_list',["RunnerName" : "Rohan Deshmukh"]), 5, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.clearText(findTestObject('Object Repository/ClockInOutPage/input_search_licenseplate'))

"verify checked in runner name show in valet dropdowns"
//click on reservation link
WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

//click on + icon
WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/icon_plus'))
WebUI.click(findTestObject('Object Repository/ReservationPage/icon_plus'))

WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/dropdown_assign_runner'), 10, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.click(findTestObject('Object Repository/ReservationPage/dropdown_assign_runner'))
WebUI.delay(1)
WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationSummaryPage/option_assign_runner', ["RunnerName" : "Rohan Deshmukh"]), 5, FailureHandling.CONTINUE_ON_FAILURE)


WebUI.click(findTestObject('Object Repository/HomePage/link_clock_in_out'))

"checkOutRunner"
WebUI.click(findTestObject('Object Repository/ClockInOutPage/created_runner_check_in_out_icon',["RunnerName" : "Rohan Deshmukh"]))

"verify check in icon and its color"
String actualCheckInIconText3 = WebUI.getAttribute(findTestObject('Object Repository/ClockInOutPage/created_runner_check_in_out_icon',["RunnerName" : "Rohan Deshmukh"]), "class", FailureHandling.CONTINUE_ON_FAILURE)
String actualCheckInIconColor3 = WebUI.getAttribute(findTestObject('Object Repository/ClockInOutPage/created_runner_check_in_out_icon',["RunnerName" : "Rohan Deshmukh"]), "style", FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyMatch(actualCheckInIconText3, "fa fa-sign-in", false, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyMatch(actualCheckInIconColor3, "color: green;", false, FailureHandling.CONTINUE_ON_FAILURE)

"verify edit icon is clickable i.e opacity is 1"
String opacityOfEditIcon11 = WebUI.getAttribute(findTestObject('Object Repository/ClockInOutPage/edit_icon_of_runner',["RunnerName" : "Rohan Deshmukh"]), "style", FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyMatch(opacityOfEditIcon11, "opacity: 1;", false, FailureHandling.CONTINUE_ON_FAILURE)

"verify delete icon is clickable i.e opacity is 1"
String opacityOfDeleteIcon11 = WebUI.getAttribute(findTestObject('Object Repository/ClockInOutPage/delete_icon_of_runner',["RunnerName" : "Rohan Deshmukh"]), "style", FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyMatch(opacityOfDeleteIcon11, "opacity: 1;", false, FailureHandling.CONTINUE_ON_FAILURE)


"verify checked in runner name show in valet dropdowns"
//click on reservation link
WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

//click on + icon
WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/icon_plus'))
WebUI.click(findTestObject('Object Repository/ReservationPage/icon_plus'))

WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/dropdown_assign_runner'), 10, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.click(findTestObject('Object Repository/ReservationPage/dropdown_assign_runner'))
WebUI.delay(1)
WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationSummaryPage/option_assign_runner', ["RunnerName" : "Rohan Deshmukh"]), 5, FailureHandling.CONTINUE_ON_FAILURE)

