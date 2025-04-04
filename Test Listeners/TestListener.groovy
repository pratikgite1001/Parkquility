import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

class TestListener {
	/**
	 * Executes before every test case starts.
	 * @param testCaseContext related information of the executed test case.
	 */
	@BeforeTestCase
	def sampleBeforeTestCase(TestCaseContext testCaseContext) {
		
		println testCaseContext.getTestCaseId()
		println testCaseContext.getTestCaseVariables()
		KeywordUtil.logInfo( "********************TestCase Start************************"+testCaseContext.getTestCaseId())
		"set global variables to defaults"
		GlobalVariable.UserName= ""
		GlobalVariable.ID=""
		GlobalVariable.LotName=""
		GlobalVariable.expectedParkingSpot= ""
		GlobalVariable.parkingFee=""
		GlobalVariable.bookingId=""
		GlobalVariable.startTime=""
		GlobalVariable.parkDuration = ""
		GlobalVariable.partialPayment=""
		GlobalVariable.pickUpRunner=false
		GlobalVariable.keyInCarStatus= false
		GlobalVariable.requestCarStatus = false
		GlobalVariable.carOnTheWayStatus =false
		GlobalVariable.keyInStatus=false
		GlobalVariable.isEditResident = false
		GlobalVariable.paymentType = ""
		GlobalVariable.DropOffRunner=false
		GlobalVariable.tipAmount= ""
		GlobalVariable.CheckoutStatus=false
		GlobalVariable.CheckInDate=""
		GlobalVariable.CheckInTime=""
		GlobalVariable.CheckOutDate=""
		GlobalVariable.CheckOutTime= ""
		GlobalVariable.netPayable=""
		GlobalVariable.InTime=""
		GlobalVariable.OutTime=""
		GlobalVariable.CarRequestTime=""
		GlobalVariable.OutStatus=false
		GlobalVariable.startTimeAndDate=""
		GlobalVariable.endTimeAndDate=""
		GlobalVariable.expectedCheckOutDate=""
		GlobalVariable.hashCode=""
		GlobalVariable.reservationStatus=""
		GlobalVariable.payStatus=""
		GlobalVariable.convenienceFee=""
		GlobalVariable.expiryDate=""
		GlobalVariable.keyboardSlot =""
		GlobalVariable.carLocationStatus = false
		GlobalVariable.cancelReservationStatus=false
		GlobalVariable.paidAmount=""
		GlobalVariable.carReadyStatus=false
		GlobalVariable.wholeMobileNumber=""
		GlobalVariable.exceptionalTipAmount=""
		GlobalVariable.SpecialPrice=""
		GlobalVariable.SpecialPriceDefault=""
		
		def loginData = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'("LoginData","L2")
		boolean browserOpen = GlobalVariable.loginDetails.get("browserOpen")
		boolean isLogin = GlobalVariable.loginDetails.get("isLogin")
		
		CustomKeywords.'com.Parkquility.app.Keywords.LoginPage.loginToPortal'(loginData, GlobalVariable.url)
		
		//WebUI.click(findTestObject('Object Repository/HomePage/header_username'))
		//WebUI.delay(2)
		//WebUI.click(findTestObject('Object Repository/HomePage/lot_building2'))
		//WebUI.delay(2)
		//WebUI.click(findTestObject('Object Repository/HomePage/header_username'))
		//WebUI.delay(2)
		/*
		if(browserOpen && isLogin) {
			// Navigate to My Account page
			//CustomKeywords.'com.Parkquility.app.Keywords.LoginPage.navigateToDashboard'()
			CustomKeywords.'com.Parkquility.app.Keywords.LoginPage.loginToPortal'(loginData)
		} else if(browserOpen && !isLogin) {
			// Login with respective key
			CustomKeywords.'com.Parkquility.app.Keywords.LoginPage.loginToPortal'(loginData)
		} else if(!browserOpen) {
			CustomKeywords.'com.Parkquility.app.Keywords.LoginPage.loginToPortal'(loginData)
		}
		*/
		
	}

	/**
	 * Executes after every test case ends.
	 * @param testCaseContext related information of the executed test case.
	 */
	@AfterTestCase
	def sampleAfterTestCase(TestCaseContext testCaseContext) {
		KeywordUtil.logInfo( "********************TestCase End************************"+testCaseContext.getTestCaseId())
		println testCaseContext.getTestCaseId()
		println testCaseContext.getTestCaseStatus()
		
		//WebUI.closeBrowser()
	}

	/**
	 * Executes before every test suite starts.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@BeforeTestSuite
	def sampleBeforeTestSuite(TestSuiteContext testSuiteContext) {
		println testSuiteContext.getTestSuiteId()
	}

	/**
	 * Executes after every test suite ends.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@AfterTestSuite
	def sampleAfterTestSuite(TestSuiteContext testSuiteContext) {
		println testSuiteContext.getTestSuiteId()
	}
}