package com.Parkquility.app.common

import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable




public class CommonKeywords {
	/**
	 * get test data from the file
	 * 
	 */
	@Keyword
	def getTestDataFromFile(def dataSheetName, def rowIdentity){
		try {
			TestData data = findTestData(dataSheetName)
			Map <String, String> dataMap = new HashMap <String, String> ()
			String[] columnList = data.columnNames
			boolean matchFlag = false
			for(int rowIndex = 1; rowIndex <= data.getRowNumbers(); rowIndex++) {
				if(data.getValue("ID", rowIndex) == rowIdentity) {
					for(int cellIndex = 1; cellIndex <= data.getColumnNumbers(); cellIndex++) {
						String header = columnList[cellIndex-1]
						String cellData = data.getValue(header, rowIndex)
						dataMap.put(header, cellData)
						matchFlag = true
					}
				}
			}
			if(matchFlag == false) {
				KeywordUtil.markFailedAndStop(" There is not match found for identity " + rowIdentity + " in data sheet " + dataSheetName)
			}
			KeywordUtil.logInfo(" In Sheet " + dataSheetName + " Row details for " + rowIdentity + " is " + dataMap)
			return dataMap
		} catch(Exception e) {
			KeywordUtil.markFailedAndStop(e.getMessage())
		}
	}

	/**
	 * get Product object based on Test data
	 * 
	 */
	@Keyword
	def Map<String, Map <String, String>> getProductsMap(def productKey) {
		List<String> productList
		if(productKey instanceof String) {
			productList = productKey.split(',')
		} else {
			productList = productKey
		}
		Map<String, String> productsDetailsMap = new LinkedHashMap<String, Map <String, String>>()

		for (String key : productList) {
			Map<String, String> productMap = getTestDataFromFile('ProductData', key)
			productsDetailsMap.put(key, productMap)
		}
		return productsDetailsMap
	}


	/**
	 * Verify window url
	 * 
	 */
	@Keyword
	def verifyWindowURL(String url){
		WebUI.delay(1)
		assert WebUI.getUrl().equalsIgnoreCase(url)
	}



	/**
	 * verify checkbox is selected or not
	 * @param TestObject
	 *
	 * @return
	 *   true -> if given element is selected else false
	 */

	def boolean isElementChecked(TestObject to, int timeout) {
		try {
			WebUI.verifyElementChecked(to, timeout)
			return true
		} catch (Exception e) {
			return false
		}
	}



	@Keyword
	boolean isTimegreaterThanSpecifiedTime(Calendar cal, String timeTobeCompare) {

		boolean greaterFlag = false

		if(cal.get(Calendar.HOUR_OF_DAY) == Integer.parseInt(timeTobeCompare.split(":")[0])) {
			if(cal.get(Calendar.MINUTE) > Integer.parseInt(timeTobeCompare.split(":")[1])) {
				greaterFlag = true
			}
			else if (cal.get(Calendar.MINUTE) >= Integer.parseInt(timeTobeCompare.split(":")[1])) {
				if(cal.get(Calendar.SECOND) > Integer.parseInt(timeTobeCompare.split(":")[2])) {
					greaterFlag = true
				}
			}
		}
		else if(cal.get(Calendar.HOUR_OF_DAY) > Integer.parseInt(timeTobeCompare.split(":")[0])) {
			greaterFlag = true
		}
		KeywordUtil.logInfo(" Current Time in 24 hour format is ---> " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND))

		return greaterFlag
	}



	/**
	 * get a text from web element and converted string into float value
	 * @param to
	 * 			TestObject name from which price needs to be get
	 * 
	 */
	@Keyword
	def double getPriceFromElement (String priceTobeConverted) {

		String priceText = priceTobeConverted
		String[] splintPriceText = priceText.split(" ")
		String priceContent = splintPriceText[1].replace(",", "")
		return Double.valueOf(priceContent)
	}



	@Keyword
	def verifyElementVisible(TestObject testObject, String message) {
		if (!WebUI.verifyElementVisible(testObject)) {
			KeywordUtil.markFailed(message)
		}
	}


	@Keyword
	def verifyElementNotVisible(TestObject testObject) {
		if (WebUI.verifyElementNotVisible(testObject)) {
			return true
		} else {
			return false
		}
	}

	/**
	 * Custom verification method to check if an element is clickable.
	 * @param testObject The Test Object to be checked.
	 * @param message The custom message to be displayed on assertion failure.
	 */
	@Keyword
	def verifyElementClickable(TestObject testObject, String message) {
		if (!WebUI.verifyElementClickable(testObject)) {
			KeywordUtil.markFailed(message)
		}
	}

	/**
	 * Custom verification method to check if an element is present.
	 * @param testObject The Test Object to be checked.
	 * @param message The custom message to be displayed on assertion failure.
	 */
	@Keyword
	def verifyElementPresent(TestObject testObject, String message) {
		if (!(WebUI.verifyElementPresent(testObject,10))) {
			KeywordUtil.markFailed(message)
		}
	}

	/**
	 * Custom verification method to check if an element is not present.
	 * @param testObject The Test Object to be checked.
	 * @param message The custom message to be displayed on assertion failure.
	 */
	@Keyword
	def verifyElementNotPresent(TestObject testObject, String message) {
		if (!WebUI.verifyElementNotPresent(testObject,10)) {
			KeywordUtil.markFailed(message)
		}
	}


	@Keyword
	def logout() {
		WebUI.waitForPageLoad(10)

		WebUI.scrollToPosition(0, 0)

		WebUI.waitForElementVisible(findTestObject('Object Repository/HomePage/txt_user_name_header'), 10)
		WebUI.click(findTestObject('Object Repository/HomePage/txt_user_name_header'))

		WebUI.waitForElementVisible(findTestObject('Object Repository/HomePage/link_logout'), 10)
		WebUI.click(findTestObject('Object Repository/HomePage/link_logout'))

		WebUI.waitForPageLoad(10)

		WebUI.waitForElementVisible(findTestObject('Object Repository/LoginPage/input_L O G I N_email'), 10)
		WebUI.verifyElementVisible(findTestObject('Object Repository/LoginPage/input_L O G I N_email'))

		WebUI.verifyElementVisible(findTestObject('Object Repository/LoginPage/txt_login'))
	}

	def increaseTimeByOneMinut(String xx) {
		int aa=xx.split(':')[1].substring(0,2) as double
		int bb= aa+1
		String cc= ":"+aa.toString()
		String dd= xx.replace(cc, ":"+bb.toString())
		return dd
	}
}
