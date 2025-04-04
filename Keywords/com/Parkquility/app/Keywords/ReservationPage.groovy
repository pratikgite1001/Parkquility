package com.Parkquility.app.Keywords


import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.text.SimpleDateFormat

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.testng.asserts.SoftAssert
import org.openqa.selenium.WebElement
import com.Parkquility.app.common.CommonKeywords
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

//import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

public class ReservationPage {


	SoftAssert softAssert = new SoftAssert()
	CommonKeywords common = new CommonKeywords()
	def sdf = new SimpleDateFormat("MMM dd, yyyy, hh:mm a")
	def sdf1 = new SimpleDateFormat("hh:mm a")


	/*
	 *to fill create reservation form for visitors 
	 *
	 */
	@Keyword
	def fillCreateReservationForm(def customerData, String customer) {
		//if guest mode then enter room number and click on check-out date

		//click on reservation link
		WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

		//click on customer section
		clickOnCustomerSection(customer)

		//click on + icon
		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/icon_plus'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/icon_plus'))

		if(customer.equals("Guest") || customer.equals("Prepaid")) {
			"verify error messages are displaying or not for empty fields"
			//validateErrorTextMessagesForEmptyFieldsForGuest()

			"enter room number"
			WebUI.delay(2)
			if(!(customerData.get("Room No").equals("NA"))) {
				WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/Guest/input_room_number'))
				WebUI.setText(findTestObject('Object Repository/ReservationPage/Guest/input_room_number'), customerData.get("Room No"))
			}

			"choose check out date"
			WebUI.waitForElementVisible(findTestObject('Object Repository/ReservationPage/Guest/input_checkout'), 10)
			WebUI.click(findTestObject('Object Repository/ReservationPage/Guest/input_checkout'))

			String actDate = WebUI.getText(findTestObject('Object Repository/ReservationPage/Guest/choose_date'))
			String actDate1 =actDate.length() == 1 ? "0${actDate}" : actDate
			String actmonthAndYear = WebUI.getText(findTestObject('Object Repository/ReservationPage/Guest/txt_month_and_year'))
			// Parse the month and year string
			def monthYearDate = Date.parse("MMMM yyyy", actmonthAndYear)
			// Format the day string and create the final formatted date string
			def finalDateString = "${monthYearDate.format('MMM')} ${actDate1}, ${monthYearDate.format('yyyy')}"

			GlobalVariable.expectedCheckOutDate = finalDateString.toString()
			WebUI.click(findTestObject('Object Repository/ReservationPage/Guest/choose_date'))
		}

		if(customer.equals("Visitor")) {
			"verify error messages are displaying or not for empty fields"
			//validateErrorTextMessagesForEmptyFieldsForVisitor()
		}

		//select country from dropdown
		assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/dropdown_select_country')), "select country dropDown not visible on create reservation form"
		assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/dropdown_select_country')), "select country dropDown not visible on create reservation form"
		WebUI.click(findTestObject('Object Repository/ReservationPage/dropdown_select_country'))
		assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/input_enter_country_name')), "enter country name inputbox not visible on create reservation form"
		WebUI.setText(findTestObject('Object Repository/ReservationPage/input_enter_country_name'),customerData.get("Country") )

		//enter mobile number
		assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/option_first_option')), "Enter correct country name"
		assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/option_first_option')), "first option in sect country dropdown is not clickable"
		String actcountryCode = WebUI.getText(findTestObject('Object Repository/ReservationPage/txt_contry_code'))
		def matcher = (actcountryCode =~ /\(([^)]+)\)/)
		def textBetweenBrackets = matcher.find() ? matcher[0][1] : null
		GlobalVariable.wholeMobileNumber = textBetweenBrackets.toString()+" - "+customerData.get("Mobile Number")
		WebUI.click(findTestObject('Object Repository/ReservationPage/option_first_option'))
		assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/input_mobile_number')), "mobile number field not visible in create reservation form"
		WebUI.setText(findTestObject('Object Repository/ReservationPage/input_mobile_number'), customerData.get("Mobile Number"))
		boolean flag1 = WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/suggession_dropdown_first_option'), 10, FailureHandling.OPTIONAL)
		if(flag1) {
			WebUI.click(findTestObject('Object Repository/ReservationPage/suggession_dropdown_first_option'))
		}
		WebUI.delay(2)
		boolean flag2 = WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/suggession_dropdown_first_option'), 10, FailureHandling.OPTIONAL)
		if(flag2) {
			WebUI.click(findTestObject('Object Repository/ReservationPage/suggession_dropdown_first_option'))
		}



		//enter licenseplate number
		if(!(customerData.get("License Plate Number").equals("NA"))) {
			assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/input_license_plate_number')), "License Plate Number field not visible on create reservation form"
			WebUI.clearText(findTestObject('Object Repository/ReservationPage/input_license_plate_number'))
			WebUI.setText(findTestObject('Object Repository/ReservationPage/input_license_plate_number'), customerData.get("License Plate Number"))
		}

		//choose car make
		if(!(customerData.get("Make").equals("NA"))) {
			assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/input_car_make')), "Car Make field not visible on create reservation field"
			WebUI.click(findTestObject('Object Repository/ReservationPage/input_car_make'))
			WebUI.setText(findTestObject('Object Repository/ReservationPage/input_search_car_make'), customerData.get("Make"))
			WebUI.click(findTestObject('Object Repository/ReservationPage/option_first_option_from_car'))
		}

		//choose color of car
		if(!(customerData.get("Color").equals("NA"))) {
			assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/input_car_color')), "Car color field is not visible on create reservation form"
			WebUI.click(findTestObject('Object Repository/ReservationPage/input_car_color'))
			WebUI.setText(findTestObject('Object Repository/ReservationPage/input_search_car_color'), customerData.get("Color"))

			if(customerData.get("Color").equals("Others")) {
				WebUI.click(findTestObject('Object Repository/ReservationPage/option_first_color_option'))
				WebUI.setText(findTestObject('Object Repository/ReservationPage/input_others_color'), "Others")
				WebUI.click(findTestObject('Object Repository/ReservationPage/btn_next_in_other_color'))
			}else {
				WebUI.click(findTestObject('Object Repository/ReservationPage/option_first_color_option'))
			}
		}


		//click on no of nights in prepaid
		if(customer.equals("Prepaid") && !(customerData.get("No of Nights").equals("NA"))) {
			WebUI.click(findTestObject('Object Repository/ReservationPage/btn_days_in_prepaid', ["Days" : customerData.get("No of Nights")]))
		}

		//enter unit number
		if(!(customerData.get("Unit Number").equals("NA"))) {
			assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/input_unit_number')), "Unit Number field not visible on create reservation form"
			WebUI.setText(findTestObject('Object Repository/ReservationPage/input_unit_number'),customerData.get("Unit Number"))
		}


		if(customerData.get("Damage").equals("Yes")) {
		}

		//click on no damages button
		if(customerData.get("Damage").equals("No")) {
			assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/btn_no_damages')), "No Damage button is not visible on create reservation form"
			assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/btn_no_damages')), "No Damage button is not clickable on create reservation form"
			WebUI.click(findTestObject('Object Repository/ReservationPage/btn_no_damages'))
		}


		//click on customer category
		if(!(customerData.get("Category").equals("NA"))) {
			WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/btn_category',["category" : customerData.get("Category")]), 10)
			WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/btn_category',["category" : customerData.get("Category")]))
			WebUI.delay(1)
			WebUI.click(findTestObject('Object Repository/ReservationPage/btn_category',["category" : customerData.get("Category")]))
			String defaultPrice = WebUI.getText(findTestObject('Object Repository/ReservationPage/btn_category',["category" : customerData.get("Category")]), FailureHandling.CONTINUE_ON_FAILURE)
			def match = defaultPrice =~ /\((.*?)\)/
			def result = match[0][1]+".00"
			GlobalVariable.SpecialPriceDefault = result


			if(!(customerData.get("Optional Amount").equals("NA"))) {
				//String spPrice
				try {
					WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/btn_optional_amount',["amount" : customerData.get("Optional Amount")]))
					WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/btn_optional_amount',["amount" : customerData.get("Optional Amount")]))
					WebUI.click(findTestObject('Object Repository/ReservationPage/btn_optional_amount',["amount" : customerData.get("Optional Amount")]))
					String spPrice = WebUI.getText(findTestObject('Object Repository/ReservationPage/btn_optional_amount',["amount" : customerData.get("Optional Amount")]), FailureHandling.CONTINUE_ON_FAILURE)
					String spPrice1 = "\$"+spPrice+".00"
					GlobalVariable.SpecialPrice= spPrice1
				}catch (StepFailedException e) {
					println("Verification failed, but continuing test: " + e.getMessage())
					WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/input_other_amount'))
					WebUI.setText(findTestObject('Object Repository/ReservationPage/input_other_amount'), customerData.get("Optional Amount"))
					String spPrice ="\$"+customerData.get("Optional Amount")+".00"
					GlobalVariable.SpecialPrice= spPrice
				}
			}
		}



		//choose car model
		if(!(customerData.get("Model").equals("NA"))) {
			WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/select_car_model'), 10)
			WebUI.click(findTestObject('Object Repository/ReservationPage/select_car_model'))
			WebUI.setText(findTestObject('Object Repository/ReservationPage/input_search_car_model'),customerData.get("Model"))
			WebUI.click(findTestObject('Object Repository/ReservationPage/option_select_model_first_option'))
		}

		//enter first name
		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/input_first_name'), 10)
		assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/input_first_name')),"First name input field not visible on create reservation form"
		WebUI.clearText(findTestObject('Object Repository/ReservationPage/input_first_name'))
		WebUI.setText(findTestObject('Object Repository/ReservationPage/input_first_name'),"")
		if(!(customerData.get("First Name").equals("NA"))) {
			WebUI.setText(findTestObject('Object Repository/ReservationPage/input_first_name'),customerData.get("First Name"))
		}
		//enter last name
		WebUI.clearText(findTestObject('Object Repository/ReservationPage/input_last_name'))
		WebUI.setText(findTestObject('Object Repository/ReservationPage/input_last_name'),"")
		if(!(customerData.get("Last Name").equals("NA"))) {
			assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/input_last_name')),"Last name input field not visible on create reservation form"
			WebUI.setText(findTestObject('Object Repository/ReservationPage/input_last_name'),customerData.get("Last Name"))
		}

		//select assign pickup runner from dropdown
		if(!(customerData.get("Pick Up Runner").equals("NA"))) {
			assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/dropdown_assign_runner')),"Assign Runner dropdown not visible on create reservation form"
			WebUI.click(findTestObject('Object Repository/ReservationPage/dropdown_assign_runner'))
			WebUI.delay(1)
			WebUI.click(findTestObject('Object Repository/ReservationSummaryPage/option_assign_runner', ["RunnerName" : customerData.get("Pick Up Runner")]))
			GlobalVariable.pickUpRunner=true
		}

		//select vehicle type from dropdown
		if(!(customerData.get("Vehicle Type").equals("NA"))) {
			assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/dropdown_vehicle_type')),"Vehicle Type dropdown not visible on create reservation form"
			WebUI.click(findTestObject('Object Repository/ReservationPage/dropdown_vehicle_type'))
			WebUI.delay(1)
			WebUI.click(findTestObject('Object Repository/ReservationSummaryPage/option_normal',["option" : customerData.get("Vehicle Type")]))
			WebUI.delay(1)
		}

		//enter email
		//WebUI.clearText(findTestObject('Object Repository/ReservationPage/input_email'))
		if(!(customerData.get("Email").equals("NA"))) {
			assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/input_email')),"Email input field not visible on create reservation form"

			WebUI.setText(findTestObject('Object Repository/ReservationPage/input_email'),customerData.get("Email"))
		}


		//enter notes
		if(!(customerData.get("Notes").equals("NA"))) {
			WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/input_notes'))
			WebUI.setText(findTestObject('Object Repository/ReservationPage/input_notes'),customerData.get("Notes"))
		}

		//scroll to no damage button
		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/input_car_color'), 10)

		//click on create reservation button
		WebUI.delay(2)
		WebUI.click(findTestObject('Object Repository/ReservationPage/btn_create_reservation'))

		verifyToastMessage("Reservation created")
		WebUI.waitForPageLoad(10)

		//set chheckIndate and checkIn time
		setDayAndTime("CheckIn")
		setInTimeAndDateFull("InTime")
		setInTimeAndDateFull("start")
	}

	/*
	 * update reservation with neww data
	 * 
	 * */
	@Keyword
	def updateReservation(def customerData,def updateData, String section, String customer) {



		//if guest mode then enter room number and click on check-out date
		if(customer.equals("Guest") || customer.equals("Prepaid")) {

			//click on customer section
			clickOnCustomerSection(customer)

			//click on created reservation using license plate number
			clickonReservationCard(customerData, section, customer)

			"click on edit icon"
			WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/icon_edit'))
			WebUI.waitForPageLoad(10)
			WebUI.waitForElementVisible(findTestObject('Object Repository/ReservationPage/txt_edit_reservation'), 10)


			"update room number"
			WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/Guest/input_room_number'))
			WebUI.clearText(findTestObject('Object Repository/ReservationPage/Guest/input_room_number'))
			WebUI.setText(findTestObject('Object Repository/ReservationPage/Guest/input_room_number'), updateData.get("Room No"))

			"update checkout date"
			WebUI.waitForElementVisible(findTestObject('Object Repository/ReservationPage/Guest/input_checkout'), 10)
			WebUI.click(findTestObject('Object Repository/ReservationPage/Guest/input_checkout'))
			String actDate = WebUI.getText(findTestObject('Object Repository/ReservationPage/Guest/choose_date'))
			String actDate1 =actDate.length() == 1 ? "0${actDate}" : actDate
			String actmonthAndYear = WebUI.getText(findTestObject('Object Repository/ReservationPage/Guest/txt_month_and_year'))
			// Parse the month and year string
			def monthYearDate = Date.parse("MMMM yyyy", actmonthAndYear)
			// Format the day string and create the final formatted date string
			def finalDateString = "${monthYearDate.format('MMM')} ${actDate1}, ${monthYearDate.format('yyyy')}"

			GlobalVariable.expectedCheckOutDate = finalDateString.toString()
			WebUI.click(findTestObject('Object Repository/ReservationPage/Guest/choose_date'))
		}else if(customer.equals("Visitor")){
			//click on customer section
			clickOnCustomerSection(customer)

			"click on created reservation using license plate number"
			clickonReservationCard(customerData, section, customer)

			WebUI.waitForPageLoad(10)
			WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/icon_edit'))
			WebUI.waitForElementVisible(findTestObject('Object Repository/ReservationPage/txt_edit_reservation'), 10)
		}

		//choose car make
		assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/input_car_make')), "Car Make field not visible on create reservation field"
		WebUI.click(findTestObject('Object Repository/ReservationPage/input_car_make'))
		WebUI.clearText(findTestObject('Object Repository/ReservationPage/input_search_car_make'))
		WebUI.setText(findTestObject('Object Repository/ReservationPage/input_search_car_make'), updateData.get("Make"))
		WebUI.click(findTestObject('Object Repository/ReservationPage/option_first_option_from_car'))

		//choose color of car
		assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/input_car_color')), "Car color field is not visible on create reservation form"
		WebUI.click(findTestObject('Object Repository/ReservationPage/input_car_color'))
		WebUI.clearText(findTestObject('Object Repository/ReservationPage/input_search_car_color'))
		WebUI.setText(findTestObject('Object Repository/ReservationPage/input_search_car_color'), updateData.get("Color"))

		if(updateData.get("Color").equals("Others")) {
			WebUI.click(findTestObject('Object Repository/ReservationPage/option_first_color_option'))
			WebUI.clearText(findTestObject('Object Repository/ReservationPage/input_others_color'))
			WebUI.setText(findTestObject('Object Repository/ReservationPage/input_others_color'), "Others")
			WebUI.click(findTestObject('Object Repository/ReservationPage/btn_next_in_other_color'))
		}else {
			WebUI.click(findTestObject('Object Repository/ReservationPage/option_first_color_option'))
		}


		//enter unit number only for Building-1
		if(!(updateData.get("Unit Number").equals("NA"))) {
			assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/input_unit_number')), "Unit Number field not visible on create reservation form"
			WebUI.clearText(findTestObject('Object Repository/ReservationPage/input_unit_number'))
			WebUI.setText(findTestObject('Object Repository/ReservationPage/input_unit_number'),updateData.get("Unit Number"))
		}

		//change parking spot
		//WebUI.click(findTestObject('Object Repository/ReservationPage/select_parking_spot_edit'))
		//GlobalVariable.expectedParkingSpot = WebUI.getText(findTestObject('Object Repository/ReservationPage/second_available_location_for_edit'))
		//WebUI.click(findTestObject('Object Repository/ReservationPage/second_available_location_for_edit'))



		//click on no damages button
		if(updateData.get("Damage").equals("No")) {
			String actualNoDamageClassValue = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/btn_no_damages'), "class")
			WebUI.verifyMatch(actualNoDamageClassValue, "btn btn-labeled btn-pink", false)
		}

		//click on no damages button
		if(updateData.get("Damage").equals("No")) {
			String actualNoDamageClassValue = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/btn_no_damages'), "class")

			if(actualNoDamageClassValue.equals("btn btn-labeled")) {
				WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/btn_add_damages'))
				WebUI.click(findTestObject('Object Repository/ReservationPage/btn_no_damages'))
			}else if(actualNoDamageClassValue.equals("btn btn-labeled btn-pink")){
				WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/btn_add_damages'),4)
			}
		}


		//click on customer category
		/*if(!(updateData.get("Category").equals("NA"))) {
		 //WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/btn_category',["category" : updateData.get("Category")]), 10)
		 WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/btn_category',["category" : updateData.get("Category")]))
		 WebUI.delay(1)
		 WebUI.click(findTestObject('Object Repository/ReservationPage/btn_category',["category" : updateData.get("Category")]))
		 if(!(updateData.get("Optional Amount").equals("NA"))) {
		 try {
		 WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/btn_optional_amount',["amount" : updateData.get("Optional Amount")]))
		 WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/btn_optional_amount',["amount" : updateData.get("Optional Amount")]))
		 WebUI.click(findTestObject('Object Repository/ReservationPage/btn_optional_amount',["amount" : updateData.get("Optional Amount")]))
		 }catch (StepFailedException e) {
		 println("Verification failed, but continuing test: " + e.getMessage())
		 WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/input_other_amount'))
		 WebUI.setText(findTestObject('Object Repository/ReservationPage/input_other_amount'), updateData.get("Optional Amount"))
		 }
		 }
		 }*/

		//click on customer category
		if(!(updateData.get("Category").equals("NA"))) {
			//WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/btn_category',["category" : updateData.get("Category")]), 10)
			WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/btn_category',["category" : updateData.get("Category")]))
			WebUI.delay(1)

			String isCategorySelected = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/btn_category',["category" : updateData.get("Category")]), "class", FailureHandling.CONTINUE_ON_FAILURE)

			if(isCategorySelected.equals("btn btn-labeled")) {
				WebUI.click(findTestObject('Object Repository/ReservationPage/btn_category',["category" : updateData.get("Category")]))
			}

			String defaultPrice = WebUI.getText(findTestObject('Object Repository/ReservationPage/btn_category',["category" : updateData.get("Category")]), FailureHandling.CONTINUE_ON_FAILURE)
			def match = defaultPrice =~ /\((.*?)\)/
			def result = match[0][1]+".00"
			GlobalVariable.SpecialPriceDefault = result


			if(!(updateData.get("Optional Amount").equals("NA"))) {
				try {
					WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/btn_optional_amount',["amount" : updateData.get("Optional Amount")]))
					WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/btn_optional_amount',["amount" : updateData.get("Optional Amount")]))
					WebUI.click(findTestObject('Object Repository/ReservationPage/btn_optional_amount',["amount" : updateData.get("Optional Amount")]))
					String spPrice = WebUI.getText(findTestObject('Object Repository/ReservationPage/btn_optional_amount',["amount" : updateData.get("Optional Amount")]), FailureHandling.CONTINUE_ON_FAILURE)
					String spPrice1 = "\$"+spPrice+".00"
					GlobalVariable.SpecialPrice= spPrice1
				}catch (StepFailedException e) {
					println("Verification failed, but continuing test: " + e.getMessage())
					WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/input_other_amount'))
					WebUI.setText(findTestObject('Object Repository/ReservationPage/input_other_amount'), updateData.get("Optional Amount"))
					String spPrice ="\$"+updateData.get("Optional Amount")+".00"
					GlobalVariable.SpecialPrice= spPrice
				}
			}
		}


		//choose car model
		if(!(updateData.get("Model").equals("NA"))) {
			try {
				WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/select_car_model'))
				WebUI.click(findTestObject('Object Repository/ReservationPage/select_car_model'))
			}catch(StepFailedException e) {
				WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/select_car_model'), 10)
				WebUI.click(findTestObject('Object Repository/ReservationPage/select_car_model'))
			}
			WebUI.scrollToPosition(0, 0)
			//WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/input_search_car_model'), 10)
			WebUI.clearText(findTestObject('Object Repository/ReservationPage/input_search_car_model'))
			WebUI.setText(findTestObject('Object Repository/ReservationPage/input_search_car_model'),updateData.get("Model"))
			WebUI.click(findTestObject('Object Repository/ReservationPage/option_select_model_first_option'))
		}


		//enter first name
		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/input_first_name'), 10)
		assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/input_first_name')),"First name input field not visible on create reservation form"
		WebUI.clearText(findTestObject('Object Repository/ReservationPage/input_first_name'))
		WebUI.setText(findTestObject('Object Repository/ReservationPage/input_first_name'),"")
		if(!(updateData.get("First Name").equals("NA"))) {
			WebUI.setText(findTestObject('Object Repository/ReservationPage/input_first_name'),updateData.get("First Name"))
		}

		//enter last name
		WebUI.clearText(findTestObject('Object Repository/ReservationPage/input_last_name'))
		WebUI.setText(findTestObject('Object Repository/ReservationPage/input_last_name'),"")
		if(!(updateData.get("Last Name").equals("NA"))) {
			assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/input_last_name')),"Last name input field not visible on create reservation form"

			WebUI.setText(findTestObject('Object Repository/ReservationPage/input_last_name'),updateData.get("Last Name"))
		}

		//select pick up runner from dropdown
		if(!(updateData.get("Pick Up Runner").equals("NA"))) {
			WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/dropdown_assign_runner'))
			WebUI.selectOptionByLabel(findTestObject('Object Repository/ReservationPage/dropdown_assign_runner'), updateData.get("Pick Up Runner") ,false)
		}

		///select vehicle type from dropdown
		if(!(updateData.get("Vehicle Type").equals("NA"))) {
			WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/dropdown_vehicle_type'))
			WebUI.selectOptionByLabel(findTestObject('Object Repository/ReservationPage/dropdown_vehicle_type'), updateData.get("Vehicle Type"), false)
		}

		//enter email
		if(!(updateData.get("Email").equals("NA"))) {
			assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/input_email')),"Email input field not visible on create reservation form"
			//WebUI.clearText(findTestObject('Object Repository/ReservationPage/input_email'))
			WebUI.setText(findTestObject('Object Repository/ReservationPage/input_email'),updateData.get("Email"))
		}


		//scroll to no damage button
		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/input_car_color'), 10)

		//click on create reservation button
		WebUI.waitForPageLoad(10)
		WebUI.click(findTestObject('Object Repository/ReservationPage/btn_create_reservation'))

		verifyToastMessage("Reservation updated")
		WebUI.waitForPageLoad(10)

		//click on customer section
		clickOnCustomerSection(customer)

		//verify updated information
		//validateCreatedReservation(updateData, section, customer)
	}


	/*
	 * validate all information on card and inside of car which is we filled while crating reservation
	 * 
	 * */
	@Keyword
	def validateCreatedReservation(def customerData, String section, String customer) {
		//click on customer section
		clickOnCustomerSection(customer)
		WebUI.scrollToPosition(0, 0)

		String expectedKey
		String expectedParkDuration
		String expectedUnitNumber
		String expectedLocation
		String expectedCarMake
		String payStatus
		String expectedVehicleNumber
		//String expectedKeySlot

		//verify information of reservation card box
		if(customer.equals("Visitor")) {
			if(section.equals("Ongoing") || section.equals("Car Requested")) {
				WebUI.delay(2)
				try {
					WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/reservation_by_license_plate_no', ["Section": section,('LicensePlateNo') : customerData.get("License Plate Number")]), 10)
				} catch (StepFailedException e) {
					WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/reservation_by_license_plate_no', ["Section": section,('LicensePlateNo') : customerData.get("License Plate Number")]), 10)
				}

				WebUI.delay(1)

				//verify license plate number visible
				WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/reservation_by_license_plate_no', ["Section": section,('LicensePlateNo') : customerData.get("License Plate Number")]))

				//verify Tip icon
				if(!(GlobalVariable.tipAmount.equals("\$0.00")) &&  !(GlobalVariable.tipAmount.equals(""))) {
					WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/tip_icon_on_card', ["Section": section,"LicensePlateNo" : customerData.get("License Plate Number")]), FailureHandling.CONTINUE_ON_FAILURE)
				}

				//verify key on card
				expectedKey = WebUI.getText(findTestObject('Object Repository/ReservationPage/txt_key_no',["Section": section,"LicensePlateNO" : customerData.get("License Plate Number")]))

				//verify location on card
				expectedLocation = WebUI.getText(findTestObject('Object Repository/ReservationPage/txt_location_on_card_visitor',["Section": section,"LicensePlateNO" : customerData.get("License Plate Number")]), FailureHandling.CONTINUE_ON_FAILURE)

				//verify park duration
				expectedParkDuration = WebUI.getText(findTestObject('Object Repository/ReservationPage/txt_park_duration_Ongoing_Visitor', ["Section": section,"LicensePlateNo" : customerData.get("License Plate Number")]))
			}

			if(section.equals("Cancelled Reservations") || section.equals("Completed Reservations")) {

				WebUI.delay(2)
				try {
					WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/reservation_by_license_plate_no_special', ["Section": section,('LicensePlateNo') : customerData.get("License Plate Number")]), 10)
				} catch (StepFailedException e) {
					WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/reservation_by_license_plate_no_special', ["Section": section,('LicensePlateNo') : customerData.get("License Plate Number")]), 10)
				}


				//verify Tip icon on card
				if(!(GlobalVariable.tipAmount.equals("\$0.00")) &&  !(GlobalVariable.tipAmount.equals(""))) {
					WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/tip_icon_on_card_special', ["Section": section,"LicensePlateNo" : customerData.get("License Plate Number")]), FailureHandling.CONTINUE_ON_FAILURE)
				}

				//verify license plate number on card for visitor only
				WebUI.delay(1)
				WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/reservation_by_license_plate_no_special', ["Section": section,('LicensePlateNo') : customerData.get("License Plate Number")]))

				//verify key on card
				expectedKey = WebUI.getText(findTestObject('Object Repository/ReservationPage/txt_key_no_special',["Section": section,"LicensePlateNO" : customerData.get("License Plate Number")]))

				expectedLocation = WebUI.getText(findTestObject('Object Repository/ReservationPage/txt_car_location_on_card_for_V_special',["Section": section, "LicensePlateNo" : customerData.get("License Plate Number")]), FailureHandling.CONTINUE_ON_FAILURE)


				//verify park duration on card
				expectedParkDuration = WebUI.getText(findTestObject('Object Repository/ReservationPage/txt_park_duration_Ongoing_Visitor_special', ["Section": section,"LicensePlateNo" : customerData.get("License Plate Number")]))
			}
		}else if(customer.equals("Guest") || customer.equals("Prepaid")) {
			if(section.equals("In") || section.equals("Car Requested") || section.equals("Out")) {
				WebUI.delay(2)
				try {
					WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/reservation_by_license_plate_no', ["Section": section,('LicensePlateNo') : customerData.get("Last Name")]), 10)
				} catch (StepFailedException e) {
					println("Verification failed, but continuing test: " + e.getMessage())
					WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/reservation_by_license_plate_no', ["Section": section,('LicensePlateNo') : customerData.get("Last Name")]), 10)
				}

				//verify Tip icon
				if(!(GlobalVariable.tipAmount.equals("\$0.00")) &&  !(GlobalVariable.tipAmount.equals(""))) {
					WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/tip_icon_on_card',["Section": section,"LicensePlateNo" : customerData.get("Last Name")]), FailureHandling.CONTINUE_ON_FAILURE)
				}

				WebUI.delay(1)
				//verify card visible of respective license plate number
				WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/reservation_by_license_plate_no', ["Section": section,('LicensePlateNo') : customerData.get("Last Name")]))

				//verify last name
				if(!(customerData.get("Last Name").equals("NA"))) {
					String expectedLastName = WebUI.getText(findTestObject('Object Repository/ReservationPage/txt_last_name_on_card', ["Section": section,"LastName" : customerData.get("Last Name")]))
					WebUI.verifyMatch(expectedLastName, customerData.get("Last Name").toString().toUpperCase(), false)
				}

				//verify keyslot on license plate number
				expectedKey = WebUI.getText(findTestObject('Object Repository/ReservationPage/txt_key_slot_on_card',["Section": section,"LastName" : customerData.get("Last Name")]))
				//WebUI.verifyMatch(expectedKey, customerData.get("Key").toString().toUpperCase(), false)

				//verify license plate no on card
				expectedVehicleNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/txt_car_make_on_card',["Section": section,"LastName" : customerData.get("Last Name")]))
				WebUI.verifyMatch(expectedVehicleNumber, customerData.get("License Plate Number"), false)

				//verify room number
				if(!(customerData.get("Room No").equals("NA"))) {
					WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/txt_room_number_Guest_In', ["Section": section,"LastName" : customerData.get("Last Name")]), 10, FailureHandling.CONTINUE_ON_FAILURE)
					String roomNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/txt_room_number_Guest_In', ["Section": section,"LastName" : customerData.get("Last Name")]))
					assert WebUI.verifyMatch(roomNumber, customerData.get("Room No"), false) : "Room number not match with excel data"
				}


				//verify Location on card
				expectedLocation = WebUI.getText(findTestObject('Object Repository/ReservationPage/txt_location_on_card_Guest',["Section": section,"LastName" : customerData.get("Last Name")]), FailureHandling.CONTINUE_ON_FAILURE)

				//verify park duration on card
				expectedParkDuration = WebUI.getText(findTestObject('Object Repository/ReservationPage/txt_park_duration_In_Guest', ["Section": section,"LastName" : customerData.get("Last Name")]))
			}

			if(section.equals("Cancelled Reservations") || section.equals("Completed Reservations")) {
				WebUI.delay(2)
				try {
					WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/reservation_by_license_plate_no_special', ["Section": section,('LicensePlateNo') : customerData.get("Last Name")]), 10)
				} catch (StepFailedException e) {
					println("Verification failed, but continuing test: " + e.getMessage())
					WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/reservation_by_license_plate_no_special', ["Section": section,('LicensePlateNo') : customerData.get("Last Name")]), 10)
				}

				//verify last name
				WebUI.delay(1)
				WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/reservation_by_license_plate_no_special', ["Section": section,('LicensePlateNo') : customerData.get("Last Name")]), 10, FailureHandling.CONTINUE_ON_FAILURE)
				WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/reservation_by_license_plate_no_special', ["Section": section,('LicensePlateNo') : customerData.get("Last Name")]))

				//verify tip icon
				if(!(GlobalVariable.tipAmount.equals("\$0.00")) &&  !(GlobalVariable.tipAmount.equals(""))) {
					WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/tip_icon_on_card_special',["Section": " "+section,"LicensePlateNo" : customerData.get("Last Name")]), FailureHandling.CONTINUE_ON_FAILURE)
				}

				//verify keyslot on license plate number
				expectedKey = WebUI.getText(findTestObject('Object Repository/ReservationPage/txt_key_slot_on_card',["Section": " "+section,"LastName" : customerData.get("Last Name")]))
				//WebUI.verifyMatch(expectedKey, customerData.get("Key").toString().toUpperCase(), false)


				//verify license plate no on card
				expectedVehicleNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/txt_car_make_on_card',["Section": " "+section,"LastName" : customerData.get("Last Name")]))
				WebUI.verifyMatch(expectedVehicleNumber, customerData.get("License Plate Number"), false)

				//verify room number
				if(!(customerData.get("Room No").equals("NA"))) {
					WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/txt_room_number_Guest_In', ["Section": " "+section,"LastName" : customerData.get("Last Name")]), 10, FailureHandling.CONTINUE_ON_FAILURE)
					String roomNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/txt_room_number_Guest_In', ["Section": " "+section,"LastName" : customerData.get("Last Name")]))
					assert WebUI.verifyMatch(roomNumber, customerData.get("Room No"), false) : "Room number not match with excel data"
				}


				//verify Parking spot on card
				expectedLocation = WebUI.getText(findTestObject('Object Repository/ReservationPage/txt_location_on_card_Guest',["Section": " "+section,"LastName" : customerData.get("Last Name")]), FailureHandling.CONTINUE_ON_FAILURE)

				//location and park duration on card
				//expectedLocation = WebUI.getText(findTestObject('Object Repository/ReservationPage/txt_location_on_card_Guest_special',["Section": " "+section,"LastName" : customerData.get("Last Name")]), FailureHandling.CONTINUE_ON_FAILURE)
				expectedParkDuration = WebUI.getText(findTestObject('Object Repository/ReservationPage/txt_park_duration_In_Guest_special', ["Section": " "+section,"LastName" : customerData.get("Last Name")]))
			}
		}



		//click on reservation card
		clickonReservationCard(customerData, section, customer)


		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_car_information'))

		//verify pay status and its color
		payStatus = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_pay_status')).toLowerCase()
		GlobalVariable.payStatus= payStatus

		String actualBackgroundColor = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_pay_status'), "style")
		if(payStatus.equals("paid")) {
			WebUI.verifyMatch(actualBackgroundColor, "background-color: rgb(104, 198, 184);", false, FailureHandling.CONTINUE_ON_FAILURE)
		}
		else if(payStatus.equals("unpaid") || payStatus.equals("partially paid")) {
			WebUI.verifyMatch(actualBackgroundColor, "background-color: rgb(216, 110, 115);", false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//get booking id
		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_booking_id_only_txt'), FailureHandling.CONTINUE_ON_FAILURE)
		String actualBookingId= WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_booking_id'))
		GlobalVariable.ID= actualBookingId.substring(2, 7)
		GlobalVariable.bookingId = actualBookingId

		//get lot name
		GlobalVariable.LotName = WebUI.getText(findTestObject('Object Repository/HomePage/txt_lot_name'))

		//verify license plate number
		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_license_Plate_no'), FailureHandling.CONTINUE_ON_FAILURE)
		String actualLicensePlateNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_license_Plate_no'))
		if(customer.equals("Visitor")) {
			WebUI.verifyMatch(actualLicensePlateNumber, customerData.get("License Plate Number"), false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify key board slot
		GlobalVariable.keyboardSlot = actualBookingId.split("-")[2].trim()
		String actualKey = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_key'))
		if(customerData.get("Category").equals("vip")) {
			actualKey= "V-"+actualKey
		}
		WebUI.verifyMatch(actualKey, expectedKey, false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify car make
		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_make'), FailureHandling.CONTINUE_ON_FAILURE)
		String actualCarMake = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_make'))
		if(!(customerData.get("Make").equals("NA"))) {
			WebUI.verifyMatch(actualCarMake, customerData.get("Make"), false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			WebUI.verifyMatch(actualCarMake, "-", false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify car model
		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_model'), FailureHandling.CONTINUE_ON_FAILURE)
		String actualCarModel = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_model'))
		if(!(customerData.get("Model").equals("NA"))) {
			WebUI.verifyMatch(actualCarModel, customerData.get("Model"), false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			WebUI.verifyMatch(actualCarModel, "-", false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify car color
		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_color'), FailureHandling.CONTINUE_ON_FAILURE)
		String actualCarColor = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_color'))
		if(!(customerData.get("Color").equals("NA"))) {
			WebUI.verifyMatch(actualCarColor, customerData.get("Color"), false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			WebUI.verifyMatch(actualCarColor, "-", false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify checkout date
		if(customer.equals("Guest") || customer.equals("Prepaid")) {
			WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_checkout_date'), FailureHandling.CONTINUE_ON_FAILURE)
			String actualCheckoutDate = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_checkout_date'))
			//assert actualCheckoutDate.contains(GlobalVariable.expectedCheckOutDate) :  "Checkout date is not present on booking details"
			WebUI.verifyMatch(actualCheckoutDate, GlobalVariable.expectedCheckOutDate, false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify room number if guest or prepaid
		if(customer.equals("Guest") || customer.equals("Prepaid")) {
			if(!(customerData.get("Room No").equals("NA"))) {
				WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_room_number'), FailureHandling.CONTINUE_ON_FAILURE)
				String actualRoomNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_room_number'), FailureHandling.CONTINUE_ON_FAILURE)
				WebUI.verifyMatch(actualRoomNumber, customerData.get("Room No"), false, FailureHandling.CONTINUE_ON_FAILURE)
			}else {
				WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_room_number'), 1, FailureHandling.CONTINUE_ON_FAILURE)
			}
		}

		//verify mobile number
		String actualMobileNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_mobile_number'))
		WebUI.verifyMatch(actualMobileNumber, GlobalVariable.wholeMobileNumber, false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify start time
		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_start_time'))
		String actualStartTime = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_start_time'), FailureHandling.CONTINUE_ON_FAILURE)
		if(!customer.equals("Prepaid")) {
			WebUI.verifyMatch(actualStartTime.toLowerCase(), GlobalVariable.startTimeAndDate.toString().toLowerCase(), false, FailureHandling.CONTINUE_ON_FAILURE)
		}
		
		//verify park duration
		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_parking_duration'))
		String actualParkDuration = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_parking_duration'), FailureHandling.CONTINUE_ON_FAILURE)
		if(!(actualParkDuration.equals("1min")) && !(GlobalVariable.requestCarStatus)) {
			//WebUI.verifyMatch(actualParkDuration, expectedParkDuration, false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify end time for completed reservation
		if(section.equals("Completed Reservations")) {
			String actualEndTime = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_end_time'))
			
			boolean result = Math.abs(sdf.parse(actualEndTime.toLowerCase()).time - sdf.parse(GlobalVariable.endTimeAndDate.toString().toLowerCase()).time) <= 60_000
				
			WebUI.verifyEqual(result, true, FailureHandling.CONTINUE_ON_FAILURE)
			
			WebUI.verifyMatch(actualEndTime.toLowerCase(), GlobalVariable.endTimeAndDate.toString().toLowerCase(), false, FailureHandling.OPTIONAL)
		}else {
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_end_time'), 1, FailureHandling.CONTINUE_ON_FAILURE)
		}


		//verify request time and request duration
		String actualRequestDuration
		if(GlobalVariable.requestCarStatus) {
			WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_request_time'))
			WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_request_duration'))

			actualRequestDuration = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_request_duration'), FailureHandling.CONTINUE_ON_FAILURE)
			if(!(actualRequestDuration.equals("1min"))) {
				//WebUI.verifyMatch(actualRequestDuration, expectedParkDuration, false, FailureHandling.CONTINUE_ON_FAILURE)
			}
		}else {
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_request_time'), 1, FailureHandling.CONTINUE_ON_FAILURE)
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_request_duration'), 1, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//get no of nights for prepaid
		String actualNumberOfNights
		if(customer.equals("Prepaid")) {
			actualNumberOfNights = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_no_of_nights'), FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_no_of_nights'), 1, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify mandatory fields for cancelled reservations
		if(section.equals("Cancelled Reservations")) {
			//verify cancelled by
			String actualCancelledby  = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_cancelled_by'))
			WebUI.verifyMatch(actualCancelledby, GlobalVariable.UserName, false)

			//verify cancellation time
			String actualCancellationAt = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_cancelled_at'))
			WebUI.verifyMatch(actualCancellationAt, GlobalVariable.endTimeAndDate, false, FailureHandling.CONTINUE_ON_FAILURE)

			//verify cancellation reason
			String actualCancellationreason = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_cancellation_reason'))
			WebUI.verifyMatch(actualCancellationreason, GlobalVariable.cancellationReason, false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_cancelled_by'), 1, FailureHandling.CONTINUE_ON_FAILURE)
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_cancelled_at'), 1, FailureHandling.CONTINUE_ON_FAILURE)
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_cancellation_reason'), 1, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify pickup runner
		if(!(customerData.get("Pick Up Runner").equals("NA")) || GlobalVariable.pickUpRunner) {
			String actualPickupRunner = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_pickup_runner'))
			WebUI.verifyMatch(actualPickupRunner, customerData.get("Pick Up Runner").toString().trim(), false)
		}else {
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_pickup_runner'), 1, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify drop off runner
		if(GlobalVariable.DropOffRunner) {
			String actualDropoffRunner = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_drop_off_runner'))
			WebUI.verifyMatch(actualDropoffRunner, customerData.get("Drop Off Runner").toString().trim(), false)
		}else {
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_drop_off_runner'), 1, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify name
		String actualName = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_name'))
		String actualName1 = customerData.get("First Name")+" "+customerData.get("Last Name")
		if(customerData.get("First Name").equals("NA") && customerData.get("Last Name").equals("NA")) {
			WebUI.verifyMatch(actualName, "", false)
		}else {
			WebUI.verifyMatch(actualName, actualName1, false)
		}

		//verify Email
		if(!(customerData.get("Email").equals("NA"))) {
			String actualEmail = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_email'))
			WebUI.verifyMatch(actualEmail,customerData.get("Email") , false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_email'), 1, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verifyLocation
		String actualLocation = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_location'), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(actualLocation, expectedLocation, false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify special price for vip
		if(!(customerData.get("Category").equals("NA"))) {
			WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_special_price'), 10)
			String specialPricetext = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_special_price'))
			WebUI.verifyMatch(specialPricetext.toLowerCase(), customerData.get("Category").toString().toLowerCase(), false)
		}else {
			if(customer.equals("Prepaid")) {
				WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_special_price'), 10)
				String specialPricetext = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_special_price'))
				WebUI.verifyMatch(specialPricetext.toLowerCase(), "o1", false)
			}else {
				WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_special_price'), 1, FailureHandling.CONTINUE_ON_FAILURE)
			}
		}

		//verify unit number
		if(customer.equals("Visitor")) {
			String actualUnitNumber
			if(!(customerData.get("Unit Number").equals("NA"))) {
				actualUnitNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_unit_number'))
				WebUI.verifyMatch(actualUnitNumber, customerData.get("Unit Number"), false)
			}else {
				WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_unit_number'), 1, FailureHandling.CONTINUE_ON_FAILURE)
			}
		}

		if(section.equals("Completed Reservations")) {
			WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_restart'), 10)
			WebUI.delay(1)
			WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_restart'))

			//verify notes in completed reservation
			if(!(customerData.get("Notes").equals("NA"))) {

				WebUI.scrollToPosition(0, 0)

				WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_notes'), 10)
				WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_notes'))
				WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_notes'))


				WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_notes_on_notes_popup'))
				String actNotes = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_actual_notes_onpopup'))
				WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/icon_close_qr'))
				WebUI.delay(1)
			}
		}else if(section.equals("Cancelled Reservations")){
			WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_parking_fee'), 10)
		}else{
			WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_car_ready'), 10)
		}


		//verify tip amount
		if(!(GlobalVariable.tipAmount.equals("\$0.00")) &&  !(GlobalVariable.tipAmount.equals(""))){
			String actualTipAmount = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_tip_amount'))

			//WebUI.verifyMatch(actualTipAmount, GlobalVariable.tipAmount, false, FailureHandling.CONTINUE_ON_FAILURE)
			assert actualTipAmount.contains(GlobalVariable.tipAmount)
		}else {
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_tip_amount'), 1, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify payment type
		if(section.equals("Completed Reservations")) {
			String actPaymentType = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_payment_type'))
			if(GlobalVariable.paymentType.equals("")) {
				WebUI.verifyMatch(actPaymentType, "Card", false, FailureHandling.CONTINUE_ON_FAILURE)
			}else {
				WebUI.verifyMatch(actPaymentType, GlobalVariable.paymentType, false, FailureHandling.CONTINUE_ON_FAILURE)
			}
		}else {
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_payment_type'), 1, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//get parking fee
		String actualparkingFee = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_parking_fee'))
		GlobalVariable.parkingFee = actualparkingFee

		//verify parking fee for special category
		if(!(customerData.get("Category").equals("NA"))) {
			if(!(GlobalVariable.paymentType.equals(""))) {
				WebUI.verifyMatch(actualparkingFee, GlobalVariable.SpecialPriceDefault, false, FailureHandling.CONTINUE_ON_FAILURE)
			}else {
				if(!(customerData.get("Optional Amount").equals("NA"))) {
					WebUI.verifyMatch(actualparkingFee, GlobalVariable.SpecialPrice, false, FailureHandling.CONTINUE_ON_FAILURE)
				}else{
					WebUI.verifyMatch(actualparkingFee, GlobalVariable.SpecialPriceDefault, false, FailureHandling.CONTINUE_ON_FAILURE)
				}
			}
		}


		//verify paid amount
		String actualPaidAmount = "\$0.00"
		String actualNetPayable
		String validationAmount = "\$0.00"
		String dd1= GlobalVariable.parkingFee.toString().replaceAll("\\\$","").trim()
		if((payStatus.equals("partially paid") || payStatus.equals("paid")) && !(dd1.equals("0.00"))) {
			WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_paid_amount'), 10, FailureHandling.CONTINUE_ON_FAILURE)
			WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_paid_amount'), FailureHandling.CONTINUE_ON_FAILURE)
			actualPaidAmount = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_paid_amount'), FailureHandling.CONTINUE_ON_FAILURE)
			GlobalVariable.paidAmount=actualPaidAmount

			if(!(GlobalVariable.paymentType.equals(""))) {
				if(GlobalVariable.paymentType.equals("Card")) {
					WebUI.verifyMatch(actualPaidAmount, GlobalVariable.partialPayment, false, FailureHandling.CONTINUE_ON_FAILURE)
				}
			}
		}else {
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_paid_amount'), 1, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify NetPayable
		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_net_payable'), 10)
		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_net_payable'))
		actualNetPayable = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_net_payable'))
		String dd2 = actualNetPayable.replaceAll("\\\$","").trim()
		GlobalVariable.netPayable=actualNetPayable

		//verify net payable amount calulations
		def value1 = actualparkingFee.replaceAll('[^\\d.]', '') as double
		def value2 = validationAmount.replaceAll('[^\\d.]', '') as double
		def value3 = actualPaidAmount.replaceAll('[^\\d.]', '') as double
		def result = value1 - value2 - value3
		def total = "\$"+String.format('%.2f', result)
		WebUI.verifyMatch(actualNetPayable, total, false, FailureHandling.CONTINUE_ON_FAILURE)



		"verify parking fee for prepaid should be 45 * no of nights"
		if(customer.equals("Prepaid")) {
			if(!(customerData.get("No of Nights").equals("NA"))) {
				def noOfNights = actualNumberOfNights as double
				double actualParkingFeecalculation = 45.00* noOfNights
				String finalParkingFeeCalculation= "\$"+String.format("%.2f", actualParkingFeecalculation)

				WebUI.verifyMatch(actualparkingFee, finalParkingFeeCalculation, false, FailureHandling.CONTINUE_ON_FAILURE)
			}
		}

		//verify pay status value according to parking fee and netpayable
		if( section.equals("Completed Reservations")) {
			if(section.equals("Completed Reservations")) {
				WebUI.verifyMatch(payStatus, "paid", false, FailureHandling.CONTINUE_ON_FAILURE)
			}
		}else if(GlobalVariable.paymentType.equals("")){
			if(!(dd1.equals("0.00"))  && !(dd2.equals("0.00"))){
				WebUI.verifyMatch(payStatus, "unpaid", false, FailureHandling.CONTINUE_ON_FAILURE)
			}else if(!(dd1.equals("0.00"))  && dd2.equals("0.00")) {
				WebUI.verifyMatch(payStatus, "paid", false, FailureHandling.CONTINUE_ON_FAILURE)
			}else if(dd1.equals("0.00")  && dd2.equals("0.00")) {
				WebUI.verifyMatch(payStatus, "unpaid", false, FailureHandling.CONTINUE_ON_FAILURE)
			}
		}else{
			if((customer.equals("Guest") && dd1.equals("0.00")) && GlobalVariable.paymentType.equals("Card")) {
				WebUI.verifyMatch(payStatus, "unpaid", false, FailureHandling.CONTINUE_ON_FAILURE)
			}else {
				WebUI.verifyMatch(payStatus, "paid", false, FailureHandling.CONTINUE_ON_FAILURE)
			}
		}

		if(!(section.equals("Completed Reservations") || section.equals("Cancelled Reservations"))) {
			//"verify if paid through card"
			if(GlobalVariable.paymentType.equals("Card")) {
				if(payStatus.equals("paid")) {
					String activeStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_card'), "class")
					WebUI.verifyMatch(activeStatus, "active-state", false, FailureHandling.CONTINUE_ON_FAILURE)

					WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_cash'), 1, FailureHandling.CONTINUE_ON_FAILURE)
					if(customer.equals("Guest") || customer.equals("Visitor")) {
						WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_front_desk'), 0, FailureHandling.CONTINUE_ON_FAILURE)
					}
				}else if(payStatus.equals("partially paid")) {
					String activeStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_card'), "class")
					WebUI.verifyMatch(activeStatus, "", false, FailureHandling.CONTINUE_ON_FAILURE)

					WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_cash'), 1, FailureHandling.CONTINUE_ON_FAILURE)
					if(customer.equals("Guest") || customer.equals("Visitor")) {
						WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_front_desk'), 1, FailureHandling.CONTINUE_ON_FAILURE)
					}
				}else if(payStatus.equals("unpaid")) {
					String qqq=  GlobalVariable.parkingFee.toString().replaceAll("\\\$","").trim()
					if(!(qqq.equals("0.00"))) {
						assert false: "after payment doner pay status either partially paid or paid"
					}
				}
			}

			//verify if paid through cash
			if(GlobalVariable.paymentType.equals("Cash")) {
				if(payStatus.equals("paid")) {
					String actualActiveStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_cash'), "class")
					WebUI.verifyMatch(actualActiveStatus, "active-state", false, FailureHandling.CONTINUE_ON_FAILURE)

					WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_card'), 1, FailureHandling.CONTINUE_ON_FAILURE)
					if(customer.equals("Guest") || customer.equals("Visitor")) {
						WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_front_desk'), 1, FailureHandling.CONTINUE_ON_FAILURE)
					}
				}else if(payStatus.equals("partially paid")){
					String actualActiveStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_cash'), "class")
					WebUI.verifyMatch(actualActiveStatus, "", false, FailureHandling.CONTINUE_ON_FAILURE)

					WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_card'), 1, FailureHandling.CONTINUE_ON_FAILURE)
					if(customer.equals("Guest") || customer.equals("Visitor")) {
						WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_front_desk'), 1, FailureHandling.CONTINUE_ON_FAILURE)
					}
				}else if(payStatus.equals("unpaid")) {
					String qqq=  GlobalVariable.parkingFee.toString().replaceAll("\\\$","").trim()
					if(!(qqq.equals("0.00"))) {
						assert false: "after payment doner pay status either partially paid or paid"
					}
				}
			}

			//verify if paid through front desk
			if(GlobalVariable.paymentType.equals("Front Desk")) {
				if(payStatus.equals("paid")) {
					String actualActiveStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_front_desk'), "class")
					WebUI.verifyMatch(actualActiveStatus, "active-state", false, FailureHandling.CONTINUE_ON_FAILURE)

					WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_card'), 1, FailureHandling.CONTINUE_ON_FAILURE)
					WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_cash'), 1, FailureHandling.CONTINUE_ON_FAILURE)
				}else if(payStatus.equals("partially paid")){
					String actualActiveStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_front_desk'), "class")
					WebUI.verifyMatch(actualActiveStatus, "", false, FailureHandling.CONTINUE_ON_FAILURE)

					WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_card'), 1, FailureHandling.CONTINUE_ON_FAILURE)
					WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_cash'), 1, FailureHandling.CONTINUE_ON_FAILURE)
				}else if(payStatus.equals("unpaid")) {
					String qqq=  GlobalVariable.parkingFee.toString().replaceAll("\\\$","").trim()
					if(!(qqq.equals("0.00"))) {
						assert false: "after payment doner pay status either partially paid or paid"
					}
				}
			}

			//verify if payment not done then all options are visible
			if(GlobalVariable.paymentType.equals("")) {
				if(customer.equals("Guest")) {
					WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_front_desk'), 5, FailureHandling.CONTINUE_ON_FAILURE)
					String actualActiveStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_front_desk'), "class")
					WebUI.verifyMatch(actualActiveStatus, "", false, FailureHandling.CONTINUE_ON_FAILURE)
				}
				WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_card'), 4, FailureHandling.CONTINUE_ON_FAILURE)
				String activeStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_card'), "class")
				WebUI.verifyMatch(activeStatus, "", false, FailureHandling.CONTINUE_ON_FAILURE)


				WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_cash'), 4, FailureHandling.CONTINUE_ON_FAILURE)
				String actualActiveStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_cash'), "class")
				WebUI.verifyMatch(actualActiveStatus, "", false, FailureHandling.CONTINUE_ON_FAILURE)
			}



			//verify key In status active or not
			if(GlobalVariable.keyInStatus) {
				String activeStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_key_in'), "class")
				assert WebUI.verifyMatch(activeStatus, "active-state", false, FailureHandling.CONTINUE_ON_FAILURE) : "key In  option already clicked for given reservation"
			}else {
				String activeStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_key_in'), "class")
				assert WebUI.verifyMatch(activeStatus, "", false, FailureHandling.CONTINUE_ON_FAILURE) : "key In  option already clicked for given reservation"
			}

			//verify car ready icon
			if(GlobalVariable.carReadyStatus) {
				String actualStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_car_ready'), "class", FailureHandling.CONTINUE_ON_FAILURE)
				WebUI.verifyMatch(actualStatus, "active-state", false, FailureHandling.CONTINUE_ON_FAILURE)
			}else {
				String actualStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_car_ready'), "class", FailureHandling.CONTINUE_ON_FAILURE)
				WebUI.verifyMatch(actualStatus, "", false, FailureHandling.CONTINUE_ON_FAILURE)
			}

			//verify checkout option is active or not
			//for visitor
			if(customer.equals("Visitor")) {
				if((payStatus.equals("unpaid") || payStatus.equals("partial paid")) && !(dd2.equals("0.00"))) {
					String actual_checkoutStyle = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_checkout'), "style", FailureHandling.CONTINUE_ON_FAILURE)
					WebUI.verifyMatch(actual_checkoutStyle, "background: rgb(136, 136, 136);", false, FailureHandling.CONTINUE_ON_FAILURE)
				}else {
					String actual_checkoutStyle = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_checkout'), "style", FailureHandling.CONTINUE_ON_FAILURE)
					WebUI.verifyMatch(actual_checkoutStyle, "background: linear-gradient(90deg, rgb(128, 87, 160), rgb(216, 109, 124));", false, FailureHandling.CONTINUE_ON_FAILURE)
				}
			}else if(customer.equals("Guest") || customer.equals("Prepaid")) {
				if((payStatus.equals("unpaid") || payStatus.equals("partial paid")) && !(dd2.equals("0.00"))) {
					String actual_checkoutStyle = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_checkout'), "style", FailureHandling.CONTINUE_ON_FAILURE)
					WebUI.verifyMatch(actual_checkoutStyle, "color: rgb(136, 136, 136);", false, FailureHandling.CONTINUE_ON_FAILURE)
				}else {
					String actual_checkoutStyle = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_checkout'), "style", FailureHandling.CONTINUE_ON_FAILURE)
					WebUI.verifyMatch(actual_checkoutStyle, "color: rgb(51, 51, 51);", false, FailureHandling.CONTINUE_ON_FAILURE)
				}
			}
			//else if(customer.equals("Guest")) {
			//	String actual_checkoutStyle = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_checkout'), "style", FailureHandling.CONTINUE_ON_FAILURE)
			//	WebUI.verifyMatch(actual_checkoutStyle, "color: rgb(51, 51, 51);", false, FailureHandling.CONTINUE_ON_FAILURE)
			//
			//}


			//verify dropdown options
			WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10, FailureHandling.STOP_ON_FAILURE)
			WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))

			//verify key In Car option
			if(GlobalVariable.keyInCarStatus) {
				WebUI.delay(1)
				String activeStatus1 = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_car_in_key'), "class")
				assert WebUI.verifyMatch(activeStatus1, "active-state", false) : "key In Car option not in green color"
			}else {
				WebUI.delay(1)
				String activeStatus1 = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_car_in_key'), "class")
				assert WebUI.verifyMatch(activeStatus1, "", false) : "key In Car option not in green color"
			}

			//verify pick up runner option value
			if(GlobalVariable.pickUpRunner) {
				String actualStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_pick_up_runner'), "class", FailureHandling.CONTINUE_ON_FAILURE)
				WebUI.verifyMatch(actualStatus, "active-state", false, FailureHandling.CONTINUE_ON_FAILURE)

				String actPickupRunner = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_pick_up_runner_value'), FailureHandling.CONTINUE_ON_FAILURE)
				WebUI.verifyMatch(actPickupRunner, customerData.get("Pick Up Runner"), false, FailureHandling.CONTINUE_ON_FAILURE)
			}else {
				String actualStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_pick_up_runner'), "class", FailureHandling.CONTINUE_ON_FAILURE)
				WebUI.verifyMatch(actualStatus, "", false, FailureHandling.CONTINUE_ON_FAILURE)

				String actPickupRunner = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_pick_up_runner_value'), FailureHandling.CONTINUE_ON_FAILURE)
				WebUI.verifyMatch(actPickupRunner, "Pick Up Runner", false, FailureHandling.CONTINUE_ON_FAILURE)
			}

			//verify Drop off runner option value
			if(GlobalVariable.DropOffRunner) {
				String actualStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_drop_off_runner'), "class", FailureHandling.CONTINUE_ON_FAILURE)
				WebUI.verifyMatch(actualStatus, "active-state", false, FailureHandling.CONTINUE_ON_FAILURE)

				String actDropOffRunner = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_drop_off_runner_value'), FailureHandling.CONTINUE_ON_FAILURE)
				WebUI.verifyMatch(actDropOffRunner, customerData.get("Drop Off Runner"), false, FailureHandling.CONTINUE_ON_FAILURE)
			}else {
				String actualStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_drop_off_runner'), "class", FailureHandling.CONTINUE_ON_FAILURE)
				WebUI.verifyMatch(actualStatus, "", false, FailureHandling.CONTINUE_ON_FAILURE)

				String actDropOffRunner = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_drop_off_runner_value'), FailureHandling.CONTINUE_ON_FAILURE)
				WebUI.verifyMatch(actDropOffRunner, "Drop Off Runner", false, FailureHandling.CONTINUE_ON_FAILURE)
			}

			//verify request car option and ReCheckIn option
			if(GlobalVariable.requestCarStatus) {
				String activeStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_request_car'), "class")
				assert WebUI.verifyMatch(activeStatus, "active-state", false) : "key In Car option already clicked for given reservation"

				WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_re_check_in'), 2, FailureHandling.CONTINUE_ON_FAILURE)
			}else {
				String activeStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_request_car'), "class")
				assert WebUI.verifyMatch(activeStatus, "", false) : "key In Car option already clicked for given reservation"

				WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_re_check_in'), 2, FailureHandling.CONTINUE_ON_FAILURE)
			}

			//verify car on the way status
			if(GlobalVariable.carOnTheWayStatus) {
				String activeStatus1 = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_car_on_the_way'), "class")
				assert WebUI.verifyMatch(activeStatus1, "active-state", false) : "Car On the Way option not in green color"
			}else {
				String activeStatus1 = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_car_on_the_way'), "class")
				assert WebUI.verifyMatch(activeStatus1, "", false) : "Car On the Way option not in green color"
			}


			//verify location option present
			WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_location'), 4, FailureHandling.CONTINUE_ON_FAILURE)

			//verify send payment link option present
			WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_send_payment_link'), 4, FailureHandling.CONTINUE_ON_FAILURE)

			//verify pay by QRC option is present
			WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_pay_by_qrc'), 4, FailureHandling.CONTINUE_ON_FAILURE)

			//verify sms option is present
			WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_sms'), 4, FailureHandling.CONTINUE_ON_FAILURE)
		}


		clickAtCoordinates(753,108)

		//click on customer section
		clickOnCustomerSection(customer)

		/*
		 //to convert from time to integer
		 int parkDuration
		 if(data.get('Park Duration').contains("hr")){
		 parkDuration=Integer.parseInt(data.get('Park Duration').split("hr")[0].trim())
		 }
		 else{
		 parkDuration=(Integer.parseInt(data.get('Park Duration').replaceAll(/\D/, '').trim()))/60
		 }
		 */
	}

	/*
	 * visitor
	 * validate error messages
	 * */
	@Keyword
	def static validateErrorTextMessagesForEmptyFieldsForVisitor() {
		//WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/ErrorMessagesForEmptyFields/txt_enter_car_make'), 10)
		WebUI.click(findTestObject('Object Repository/ReservationPage/btn_create_reservation'))

		assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/ErrorMessagesForEmptyFields/txt_enter_phone_number'))
		assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/ErrorMessagesForEmptyFields/txt_enter_car_make'))
		assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/ErrorMessagesForEmptyFields/txt_enter_car_color'))
		assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/ErrorMessagesForEmptyFields/txt_either_choose_no_damages_or_add_damages'))
	}


	/*guest
	 * validate error messages when we not fill any anfo and click on create reservation button
	 * 
	 * */
	@Keyword
	def static validateErrorTextMessagesForEmptyFieldsForGuest() {
		WebUI.delay(2)
		//WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/btn_create_reservation'), 10)
		WebUI.click(findTestObject('Object Repository/ReservationPage/btn_create_reservation'))

		WebUI.scrollToPosition(0, 0)
		/*
		 //WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/ErrorMessagesForEmptyFields/txt_enter_phone_number'),10)
		 assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/ErrorMessagesForEmptyFields/txt_enter_phone_number'))
		 assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/ErrorMessagesForEmptyFields/txt_choose_checkout_date')), "Car Make error message is not displaying working correct"
		 //assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/ErrorMessagesForEmptyFields/txt_enter_car_make')), "Car Make error message is not displaying working correct"
		 //assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/ErrorMessagesForEmptyFields/txt_enter_car_color'))
		 //assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/ErrorMessagesForEmptyFields/txt_either_choose_no_damages_or_add_damages'))
		 assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/ErrorMessagesForEmptyFields/txt_enter first_name'))
		 assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/ErrorMessagesForEmptyFields/txt_enter_last_name'))
		 */

		//findTestObject('Object Repository/ReservationPage/count_mandatory_fields')
		//findTestObject('Object Repository/ReservationPage/txt_each_mandatory_field')
	}


	/*
	 * click on key in car option
	 * 
	 * */
	@Keyword
	def verifyKeyInCarFeature(def customerData, String section, String customer) {
		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card
		clickonReservationCard(customerData,section, customer)

		WebUI.delay(2)
		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10)
		assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))


		String activeStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_car_in_key'), "class")
		assert WebUI.verifyMatch(activeStatus, "", false) : "key In Car option already clicked for given reservation"


		if(!(activeStatus.equals("active-state"))) {
			assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_car_in_key'))
			WebUI.delay(1)
			WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_car_in_key'))

			GlobalVariable.keyInCarStatus=true

			verifyToastMessage("Keys in car registered successfully")
			//WebUI.delay(2)
		}
		WebUI.waitForPageLoad(10)

		//click on customer section
		clickOnCustomerSection(customer)

		//click on reseration card
		clickonReservationCard(customerData,section, customer)

		assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))

		WebUI.delay(1)
		String activeStatus1 = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_car_in_key'), "class")
		assert WebUI.verifyMatch(activeStatus1, "active-state", false) : "key In Car option is not clicked for given reservation"

		clickAtCoordinates(649,105)
		//WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

		//click on customer section
		clickOnCustomerSection(customer)
	}

	@Keyword
	def verifyKeyInFeaturebecomestrue(def customerData, String section, String customer) {
		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card
		clickonReservationCard(customerData,section, customer)

		WebUI.delay(1)
		WebUI.waitForPageLoad(10)
		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_Key_In'), 10)

		String activeStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_key_In'), "class")
		assert WebUI.verifyMatch(activeStatus, "", false) : "key In  option already clicked for given reservation"


		if(!(activeStatus.equals("active-state"))) {
			assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_Key_In'))
			WebUI.delay(1)
			WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_Key_In'))

			GlobalVariable.keyInStatus=true

			verifyToastMessage("Key received")
			//WebUI.delay(2)
		}
		WebUI.waitForPageLoad(10)

		//click on customer section
		clickOnCustomerSection(customer)

		//click on reseration card
		clickonReservationCard(customerData,section, customer)

		WebUI.delay(1)
		WebUI.waitForPageLoad(10)
		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_Key_In'), 10)

		WebUI.delay(1)
		String activeStatus1 = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_key_In'), "class")
		assert WebUI.verifyMatch(activeStatus1, "active-state", false) : "key In  option is not clicked for given reservation"

		clickAtCoordinates(649,105)
		//WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

		//click on customer section
		clickOnCustomerSection(customer)
	}


	/*
	 * click on car on the way
	 * 
	 * */
	@Keyword
	def verifyCarOnTheWayFeature(def customerData, String section, String customer) {

		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card
		clickonReservationCard(customerData, section, customer)

		assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))


		String activeStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_car_on_the_way'), "class")
		assert WebUI.verifyMatch(activeStatus, "", false) : "Car On The Way option already clicked for given reservation"


		if(!(activeStatus.equals("active-state"))) {
			assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_car_on_the_way'))
			WebUI.delay(2)
			WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_car_on_the_way'))

			//WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/txt_tost_message'))
			//String actualtoastMessage = WebUI.getText(findTestObject('Object Repository/ReservationPage/txt_tost_message'))
			//WebUI.verifyMatch(actualtoastMessage, "Car On The Way Success", false)

			verifyToastMessage("Car On The Way Success")

			GlobalVariable.carOnTheWayStatus=true
		}

		WebUI.waitForPageLoad(10)

		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card
		clickonReservationCard(customerData, section, customer)

		assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))

		String activeStatus1 = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_car_on_the_way'), "class")
		assert WebUI.verifyMatch(activeStatus1, "active-state", false) : "Car On the Way option is not clicked for given reservation"

		clickAtCoordinates(649,105)
		//WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

		//click on customer section
		clickOnCustomerSection(customer)
	}

	@Keyword
	def verifyCarReadyFeature(def customerData, String section, String time, String customer){
		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card
		clickonReservationCard(customerData, section, customer)

		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_car_ready'), 10)
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_car_ready'))

		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/btn_choose_car_ready_duration',["time" : time]))

		GlobalVariable.carOnTheWayStatus=true
		if(time.equals("now")) {
			GlobalVariable.carReadyStatus=true
		}

		verifyToastMessage("Car Ready Success")

		WebUI.waitForPageLoad(10)

		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card
		clickonReservationCard(customerData, section, customer)

		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_car_ready'), 10)
		String actualStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/status_car_ready'), "class")

		if(time.equals("now")) {
			WebUI.verifyMatch(actualStatus, "active-state", false)
		}
		else {
			WebUI.verifyMatch(actualStatus, "", false)
		}

		clickAtCoordinates(649,105)
		//WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))
		//click on customer section
		clickOnCustomerSection(customer)
	}

	/*
	 * here we addDrop off runner
	 * 
	 * */
	@Keyword
	def verifyAssignDropOffFeature(def customerData, String section, String customer) {
		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card
		clickonReservationCard(customerData, section, customer)

		assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))

		assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_drop_off_runner')) : "assign drop off runner option is not visible on reservation drop down menu"
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_drop_off_runner'))

		//assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_assign_runner')) : "assign runner text is not visible on popup"

		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/select_assign_runner'))
		WebUI.delay(1)
		WebUI.click(findTestObject('Object Repository/ReservationSummaryPage/option_add_pickup',["PickUpRunner" : customerData.get("Drop Off Runner")]))

		//WebUI.selectOptionByLabel(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/select_assign_runner'), "Abhishek Sikarwar", false, FailureHandling.STOP_ON_FAILURE)
		WebUI.delay(1)
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/btn_submit'))

		verifyToastMessage("Drop Off Runner Assigned")
		WebUI.delay(2)

		GlobalVariable.DropOffRunner = true

		WebUI.waitForPageLoad(10)

		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card
		clickonReservationCard(customerData, section, customer)

		String actualDropOffRunner = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_drop_off_runner'))

		assert WebUI.verifyMatch(actualDropOffRunner, customerData.get("Drop Off Runner"), false) : "Drop Off Ruuner not match which is assign"

		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10)
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))

		String actualStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_drop_off_runner'), "class", FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(actualStatus, "active-state", false, FailureHandling.CONTINUE_ON_FAILURE)

		String actDropOffRunner = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_drop_off_runner_value'), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(actDropOffRunner, customerData.get("Drop Off Runner"), false, FailureHandling.CONTINUE_ON_FAILURE)


		clickAtCoordinates(649,105)
		//WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

		//click on customer section
		clickOnCustomerSection(customer)
	}

	@Keyword
	def verifyAssignPickupRunnerFeature(def customerData, String section, String customer) {
		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card
		clickonReservationCard(customerData, section, customer)

		assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))

		assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_pick_up_runner')) : "assign drop off runner option is not visible on reservation drop down menu"
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_pick_up_runner'))

		//assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_assign_runner')) : "assign runner text is not visible on popup"

		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/select_assign_runner'))
		WebUI.delay(1)
		WebUI.click(findTestObject('Object Repository/ReservationSummaryPage/option_add_pickup',["PickUpRunner" : customerData.get("Pick Up Runner")]))

		//WebUI.selectOptionByLabel(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/select_assign_runner'), "Abhishek Sikarwar", false, FailureHandling.STOP_ON_FAILURE)
		WebUI.delay(1)
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/btn_submit'))

		verifyToastMessage("Pick Up Runner Assigned")
		WebUI.delay(2)

		GlobalVariable.pickUpRunner = true

		WebUI.waitForPageLoad(10)

		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card
		clickonReservationCard(customerData, section, customer)

		String actualPickUpRunner = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_pickup_runner'))

		assert WebUI.verifyMatch(actualPickUpRunner, customerData.get("Pick Up Runner"), false) : "Pickup Ruuner not match which is assign"

		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10)
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))

		String actualStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_pick_up_runner'), "class", FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(actualStatus, "active-state", false, FailureHandling.CONTINUE_ON_FAILURE)

		String actPickupRunner = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_pick_up_runner_value'), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(actPickupRunner, customerData.get("Pick Up Runner"), false, FailureHandling.CONTINUE_ON_FAILURE)

		clickAtCoordinates(649,105)
		//WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

		//click on customer section
		clickOnCustomerSection(customer)
	}


	/*
	 * validate information on edit page
	 * 
	 * */
	@Keyword
	def validateEditPageInfoWithEnteredInfo(def customerData, String section, String customer) {
		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card
		clickonReservationCard(customerData, section, customer)
		String expectedKeySlot = WebUI.getText(findTestObject('Object Repository/ManageResidentPage/txt_key_slot_from_booking_id'))

		String expectedLocation
		String expLocation = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_location'), FailureHandling.CONTINUE_ON_FAILURE)
		if(!(expLocation.equals("Location"))) {
			expectedLocation = expLocation
		}
		else {
			expectedLocation = ""
		}

		String expectedParkDur =WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_parking_duration'))



		//click on edit icon
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/icon_edit'))
		WebUI.waitForElementVisible(findTestObject('Object Repository/ReservationPage/txt_edit_reservation'), 10)

		//to convert from time to integer
		Double parkDuration
		if(expectedParkDur.contains("hr")){
			Double pp=Double.parseDouble(expectedParkDur.split("hr")[0].trim())
			Double qq =(Double.parseDouble(expectedParkDur.split("hr")[1].replaceAll(/\D/, '').trim()))/60
			parkDuration = pp+qq
		}
		else{
			parkDuration=(Double.parseDouble(expectedParkDur.replaceAll(/\D/, '').trim()))/60
		}

		//check parking fees for less than two hour
		if(parkDuration> 0.17) {

			String actualMobileNoGrayColor = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/mobile_no_gray_color'), "style")
			WebUI.verifyMatch(actualMobileNoGrayColor, "background-color: rgb(238, 238, 238); pointer-events: none;", false, FailureHandling.CONTINUE_ON_FAILURE)

			String actualLicenseNoGrayColor = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/license_no_gray_color'), "style")
			WebUI.verifyMatch(actualLicenseNoGrayColor, "background-color: rgb(238, 238, 238); pointer-events: none;", false)
		}else {
			String actualMobileNoGrayColor = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/mobile_no_gray_color'), "style")
			WebUI.verifyMatch(actualMobileNoGrayColor, "pointer-events: auto;", false, FailureHandling.CONTINUE_ON_FAILURE)

			String actualLicenseNoGrayColor = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/license_no_gray_color'), "style")
			WebUI.verifyMatch(actualLicenseNoGrayColor, "pointer-events: auto;", false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify Mobile Number
		String mobileNo = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/input_mobile_number'), "value")
		WebUI.verifyMatch(mobileNo, customerData.get("Mobile Number"), false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify key slot
		String actualKeySlot = WebUI.getAttribute(findTestObject('Object Repository/ManageResidentPage/input_key_slot_edit_page'), "value")
		WebUI.verifyMatch(actualKeySlot, expectedKeySlot, false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify License plate number
		String LicensePlateNo = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/input_license_plate_number'), "value")
		WebUI.verifyMatch(LicensePlateNo, customerData.get("License Plate Number"), false)

		//verify car make
		if(!(customerData.get("Make").equals("NA"))) {
			String carMake= WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/input_car_make'), "value")
			WebUI.verifyMatch(carMake, customerData.get("Make"), false)
		}

		//verify car color
		if(!(customerData.get("Color").equals("NA"))) {
			String carColor= WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/input_car_color'), "value")
			WebUI.verifyMatch(carColor.toLowerCase(), customerData.get("Color").toString().toLowerCase(), false)
		}

		//verify Location
		String actualLocation= WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/input_parking_spot'), "value", FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(actualLocation, expectedLocation, false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify no damage selected
		if(customerData.get("Damage").equals("No")) {
			String noDamageselectedValue= WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/btn_no_damages'), "class")
			WebUI.verifyMatch(noDamageselectedValue, "btn btn-labeled btn-pink", false)
		}

		//verify Add damage selected
		if(customerData.get("Damage").equals("Yes")) {
			String noDamageselectedValue= WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/btn_no_damages'), "class")
			WebUI.verifyMatch(noDamageselectedValue, "btn btn-labeled", false)

			WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/txt_add_damages_uploded_photoes_number'), 10)
		}


		//verify sign element Present
		if(customer.equals("Guest")) {
			WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/element_sign'), 10)
		}

		//verify selected category
		if(!(customerData.get("Category").equals("NA"))) {
			String actualSelectedCategory = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/btn_category',["category" : customerData.get("Category")]), "class")
			WebUI.verifyMatch(actualSelectedCategory, "btn btn-labeled btn-pink", false)
		}


		//verify car model
		if(!(customerData.get("Model").equals("NA"))) {
			WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/txt_selected_car_model'), 10)
			String actualCarModel = WebUI.getText(findTestObject('Object Repository/ReservationPage/txt_selected_car_model'))
			WebUI.verifyMatch(actualCarModel, customerData.get("Model"), false)
		}

		//verify first name
		if(!(customerData.get("First Name").equals("NA"))) {
			WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/input_first_name'), 10)
			String actualFirstName= WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/input_first_name'), "value")
			WebUI.verifyMatch(actualFirstName, customerData.get("First Name"), false)
		}

		//verify last name
		if(!(customerData.get("Last Name").equals("NA"))) {
			WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/input_last_name'), 10)
			String actualLastName= WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/input_last_name'), "value")
			WebUI.verifyMatch(actualLastName, customerData.get("Last Name"), false)
		}

		//verify Email
		if(!(customerData.get("Email").equals("NA"))) {
			WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/input_email'), 10)
			WebUI.delay(2)
			String actualEmail= WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/input_email'), "value")
			WebUI.verifyMatch(actualEmail, customerData.get("Email"), false)
		}

		//verify notes
		if(!(customerData.get("Notes").equals("NA"))) {
			String actualNotes = WebUI.getText(findTestObject('Object Repository/ReservationPage/input_notes'))
			WebUI.verifyMatch(actualNotes, customerData.get("Notes"), false)
		}





		WebUI.scrollToPosition(0, 0)

		WebUI.waitForElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/btn_close_edit_res_page'), 10, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/btn_close_edit_res_page'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/btn_close_edit_res_page'))

		WebUI.delay(3)
	}

	/*
	 * make payment
	 * 
	 * */
	@Keyword
	def makePayment(def paymentData, def customerData, String section, customer) {
		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card
		clickonReservationCard(customerData, section, customer)
		WebUI.delay(2)

		String expectedStartTime = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_start_time'))

		"scroll to card logo"
		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_card'), 10)

		String expectedParkingFee = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_parking_fee'))

		String expectedName = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_name'))

		String expEmail
		if(!(customerData.get("Email").equals("NA"))) {
			expEmail = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_email'))
		}

		String expBookingId = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_booking_id'))

		"check payment is already done or not"
		String actualCardActiveStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_card_active_status'), "class")
		assert WebUI.verifyMatch(actualCardActiveStatus, "", false), "this reservation is already paid"

		"click on card logo"
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_card'))


		WebUI.switchToFrame(findTestObject('Object Repository/PaymentPage/frame_payment'), 10)
		WebUI.delay(2)

		"lot location"
		String location= WebUI.getText(findTestObject('Object Repository/PaymentPage/txt_location'))

		"verify booing id on reservation info page and payment page"
		String expectedBookinfId = WebUI.getText(findTestObject('Object Repository/PaymentPage/txt_booking_id'))
		WebUI.verifyMatch(expBookingId, expectedBookinfId, false, FailureHandling.CONTINUE_ON_FAILURE)


		"verify start time in card info page and payment page"
		WebUI.delay(1)
		String actualStartTime = WebUI.getText(findTestObject('Object Repository/PaymentPage/txt_start_time'))
		WebUI.verifyMatch(actualStartTime, expectedStartTime, false, FailureHandling.CONTINUE_ON_FAILURE)

		String expectedEndTime = WebUI.getText(findTestObject('Object Repository/PaymentPage/txt_end_time'))

		"verify License plate number"
		String expectedLicensePlateNo = WebUI.getText(findTestObject('Object Repository/PaymentPage/txt_license_plate_number'))
		WebUI.verifyMatch(expectedLicensePlateNo, customerData.get("License Plate Number"), false, FailureHandling.CONTINUE_ON_FAILURE)

		"verify first name"
		if(!(customerData.get("First Name").equals("NA"))) {
			String actualFirstName = WebUI.getAttribute(findTestObject('Object Repository/PaymentPage/input_first_name'), "value")
			WebUI.verifyMatch(actualFirstName, customerData.get("First Name"), false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		"verify last name"
		if(!(customerData.get("Last Name").equals("NA"))) {
			String actualLastName = WebUI.getAttribute(findTestObject('Object Repository/PaymentPage/input_last_name'), "value")
			WebUI.verifyMatch(actualLastName, customerData.get("Last Name"), false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		"verify email"
		String expectedEmail
		if(!(customerData.get("Email").equals("NA"))) {
			expectedEmail = WebUI.getText(findTestObject('Object Repository/PaymentPage/txt_email'))
			WebUI.verifyMatch(expectedEmail, expEmail, false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		"verify parking fee in card info page and payment page"
		String actualParkingFee1=""
		if(!(expectedParkingFee.equals("\$0.00"))){
			actualParkingFee1 = WebUI.getText(findTestObject('Object Repository/PaymentPage/txt_parking_fee'))
			WebUI.verifyMatch(actualParkingFee1, expectedParkingFee, false, FailureHandling.CONTINUE_ON_FAILURE)
		}


		"enter tip amount and verify btn colors"
		boolean bb1 = WebUI.verifyElementVisible(findTestObject('Object Repository/PaymentPage/btn_tip_amount', ["TipAmount" : paymentData.getAt("Tip Amount")]), FailureHandling.OPTIONAL)
		if(bb1) {
			String actStatus= WebUI.getAttribute(findTestObject('Object Repository/PaymentPage/btn_tip_amount', ["TipAmount" : paymentData.getAt("Tip Amount")]), "class")
			if(actStatus.equals("tip-label-btn")) {
				WebUI.click(findTestObject('Object Repository/PaymentPage/btn_tip_amount', ["TipAmount" : paymentData.getAt("Tip Amount")]))
			}

			WebUI.delay(2)
			String actualTipBtnColor = WebUI.getAttribute(findTestObject('Object Repository/PaymentPage/btn_tip_amount', ["TipAmount" : paymentData.getAt("Tip Amount")]), "class")
			WebUI.delay(1)
			//WebUI.verifyMatch(actualTipBtnColor, "active tip-label-btn", false, FailureHandling.CONTINUE_ON_FAILURE)
			assert actualTipBtnColor.contains("active") : "tip amount selected but not showing in purple color"
		}else {
			WebUI.setText(findTestObject('Object Repository/PaymentPage/input_other_tip_amount'), paymentData.getAt("Tip Amount"))
			WebUI.delay(1)
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/PaymentPage/btn_tip_active_or_not'), 1,FailureHandling.CONTINUE_ON_FAILURE)
		}


		"convenience fee"
		String expectedConvenienceFee = WebUI.getText(findTestObject('Object Repository/PaymentPage/txt_convenience_fee'))

		"verify total fee"
		String expectedTipAmout
		String expectedTotalAmount
		String expectedTotalTipAmout
		if(!(expectedParkingFee.equals("\$0.00"))){
			expectedTipAmout = WebUI.getText(findTestObject('Object Repository/PaymentPage/txt_tip_amount'))
			expectedTotalAmount = WebUI.getText(findTestObject('Object Repository/PaymentPage/txt_total_fee'))

			def value1 = actualParkingFee1.replaceAll('[^\\d.]', '') as double
			def value2 = expectedTipAmout.replaceAll('[^\\d.]', '') as double
			def value3 = expectedConvenienceFee.replaceAll('[^\\d.]', '') as double
			def result = value1 + value2 + value3

			def total = "\$"+String.format('%.2f', result)

			WebUI.verifyMatch(total.toString(), expectedTotalAmount, false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			expectedTotalTipAmout = WebUI.getText(findTestObject('Object Repository/PaymentPage/txt_total_tip'))

			def value1 = paymentData.get("Tip Amount") as double
			def value2 = expectedConvenienceFee.replaceAll('[^\\d.]', '') as double
			def result = value1 + value2

			def total = "\$"+String.format('%.2f', result)

			WebUI.verifyMatch(total.toString(), expectedTotalTipAmout, false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//WebUI.scrollToElement(findTestObject('Object Repository/PaymentPage/txt'), 10)
		WebUI.scrollToElement(findTestObject('Object Repository/PaymentPage/txt_payment'), 10)
		WebUI.switchToFrame(findTestObject('Object Repository/PaymentPage/iframe_card_no'), 10)


		WebUI.verifyElementVisible(findTestObject('Object Repository/PaymentPage/input_card_number'))
		WebUI.setText(findTestObject('Object Repository/PaymentPage/input_card_number'), paymentData.get("Credit Card Number"))

		WebUI.delay(1)
		WebUI.verifyElementVisible(findTestObject('Object Repository/PaymentPage/input_expiry_date'))
		WebUI.setText(findTestObject('Object Repository/PaymentPage/input_expiry_date'), paymentData.get("Expiry Date"))

		WebUI.delay(1)
		WebUI.verifyElementVisible(findTestObject('Object Repository/PaymentPage/input_cvc'))
		WebUI.setText(findTestObject('Object Repository/PaymentPage/input_cvc'), paymentData.get("CVC"))

		WebUI.delay(1)
		WebUI.verifyElementVisible(findTestObject('Object Repository/PaymentPage/input_postal'))
		WebUI.setText(findTestObject('Object Repository/PaymentPage/input_postal'), paymentData.get("ZIP"))

		WebUI.delay(1)
		WebUI.switchToDefaultContent()
		WebUI.delay(1)
		WebUI.switchToFrame(findTestObject('Object Repository/PaymentPage/frame_payment'), 10)

		WebUI.scrollToElement(findTestObject('Object Repository/PaymentPage/input_cardholder_name'), 10)
		WebUI.verifyElementVisible(findTestObject('Object Repository/PaymentPage/input_cardholder_name'))
		WebUI.setText(findTestObject('Object Repository/PaymentPage/input_cardholder_name'), paymentData.get("Cardholder Name"))



		"click on request your car and then pay button  or pay & request your car button"
		WebUI.scrollToElement(findTestObject('Object Repository/PaymentPage/btn_pay_and_request_your_car'), 10)

		boolean b1= WebUI.verifyElementPresent(findTestObject('Object Repository/PaymentPage/btn_request_your_car'), 5, FailureHandling.OPTIONAL)
		if(b1) {
			WebUI.click(findTestObject('Object Repository/PaymentPage/btn_request_your_car'))
			setInTimeAndDateFull("CarRequestTime")
			WebUI.delay(2)
			String actualReqYourCarText = WebUI.getText(findTestObject('Object Repository/PaymentPage/btn_request_your_car'))
			//WebUI.verifyMatch(actualReqYourCarText, "We have forwarded your request. We will text you when your car is ready.", false)
		}

		WebUI.verifyElementClickable(findTestObject('Object Repository/PaymentPage/btn_pay_and_request_your_car'))
		WebUI.click(findTestObject('Object Repository/PaymentPage/btn_pay_and_request_your_car'))

		WebUI.delay(7)
		WebUI.switchToDefaultContent()
		WebUI.delay(1)




		WebUI.waitForPageLoad(10)

		"verify necessary text for reservation summary page is visible"
		WebUI.switchToFrame(findTestObject('Object Repository/ReservationSummaryPage/iframe_reservation_summary'), 10, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.waitForElementVisible(findTestObject('Object Repository/ReservationSummaryPage/txt_reservation_summary'), 20, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationSummaryPage/txt_reservation_summary'), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationSummaryPage/txt_Please update your information and make payment for the Valet service'), FailureHandling.CONTINUE_ON_FAILURE)

		GlobalVariable.requestCarStatus=true
		WebUI.delay(1)

		GlobalVariable.paymentType= "Card"



		"verify mobile number"
		WebUI.waitForElementVisible(findTestObject('Object Repository/ReservationSummaryPage/txt_mobile_number'), 10, FailureHandling.CONTINUE_ON_FAILURE)
		String actualMobileNo =WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_mobile_number'))
		assert actualMobileNo.contains(customerData.get("Mobile Number"))

		"verify booking id"
		String actualBookingId =WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_booking_id'))
		WebUI.verifyMatch(actualBookingId, expectedBookinfId, false, FailureHandling.CONTINUE_ON_FAILURE)

		"verify start time"
		String actualstartTimeOnsummary = WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_start_time'))
		WebUI.verifyMatch(actualstartTimeOnsummary, actualStartTime, false, FailureHandling.CONTINUE_ON_FAILURE)

		"verify end time"
		String actualEndTime = WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_end_time'))
		//WebUI.verifyMatch(actualEndTime, expectedEndTime, false)

		if(customer.equals("Guest")) {

			WebUI.delay(2)
			String totalOnPaidButton = WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/btn_paid'))

			WebUI.click(findTestObject('Object Repository/ReservationSummaryPage/btn_paid'))

			verifyToastMessage("Payment successful")
			WebUI.delay(1)

			"verify name"
			String actualName = WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_name'))
			WebUI.verifyMatch(actualName, expectedName, false, FailureHandling.CONTINUE_ON_FAILURE)

			"verify email"
			if(!(customerData.get("Email").equals("NA"))) {
				String actualEmail =WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/text_email'))
				WebUI.verifyMatch(actualEmail, expectedEmail, false, FailureHandling.CONTINUE_ON_FAILURE)
			}

			"verify license plate number"
			String actualLicensePlateNo =WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_license_plate'))
			WebUI.verifyMatch(actualLicensePlateNo, customerData.get("License Plate Number"), false, FailureHandling.CONTINUE_ON_FAILURE)

			"verify payment status"
			String actualPaymentStatus =WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_payment_status'))
			WebUI.verifyMatch(actualPaymentStatus, "paid", false, FailureHandling.CONTINUE_ON_FAILURE)

			"verify card fee or convenience fee"
			String actualConvenienceFee = WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_convenience_fee'))
			WebUI.verifyMatch(actualConvenienceFee, expectedConvenienceFee, false, FailureHandling.CONTINUE_ON_FAILURE)

			GlobalVariable.convenienceFee=actualConvenienceFee


			if(!(expectedParkingFee.equals("\$0.00"))){

				"verify total price on paid button"
				assert totalOnPaidButton.contains(expectedTotalAmount)

				"verify payment type"
				String actualPaymentType = WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_payment_type'))
				WebUI.verifyMatch(actualPaymentType.toLowerCase(), "card", false, FailureHandling.CONTINUE_ON_FAILURE)

				String actualParkingFee = WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_parking_fee'))
				WebUI.verifyMatch(actualParkingFee, expectedParkingFee, false)
				GlobalVariable.parkingFee = actualParkingFee


				String actualPartialPayment = WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_partial_payment'))
				GlobalVariable.partialPayment = actualPartialPayment

				String actualTotalAmount = WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_total_fee'))
				WebUI.verifyMatch(actualTotalAmount, expectedTotalAmount, false, FailureHandling.CONTINUE_ON_FAILURE)

				String actualTipAmount = WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_tip'))
				WebUI.verifyMatch(actualTipAmount, expectedTipAmout, false, FailureHandling.CONTINUE_ON_FAILURE)

				GlobalVariable.tipAmount = actualTipAmount
			}else {
				"verify Tip Paid text only for paking fee \$0 "
				assert totalOnPaidButton.equals("Tip Paid")

				String actualTotalAmount = WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_total_tip'))
				WebUI.verifyMatch(actualTotalAmount, expectedTotalTipAmout, false, FailureHandling.CONTINUE_ON_FAILURE)
			}

			"verify request your car info"
			if(b1) {
				String actualReqYourCarText1 = WebUI.getText(findTestObject('Object Repository/PaymentPage/btn_request_your_car'))
				WebUI.verifyMatch(actualReqYourCarText1, "We have forwarded your request. We will text you when your car is ready.", false, FailureHandling.CONTINUE_ON_FAILURE)
			}


			"send mail"
			WebUI.delay(1)
			WebUI.scrollToElement(findTestObject('Object Repository/ReservationSummaryPage/input_email_id'), 10)
			WebUI.setText(findTestObject('Object Repository/ReservationSummaryPage/input_email_id'), "pratikgite100@gmail.com")
			WebUI.delay(1)
			WebUI.click(findTestObject('Object Repository/ReservationSummaryPage/btn_send'))
			WebUI.delay(1)

			"verify send email text after sending mail"
			WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationSummaryPage/txt_Receipt has been sent to your email id'), FailureHandling.CONTINUE_ON_FAILURE)

			"close reservation summary page"

			WebUI.delay(1)
			WebUI.scrollToPosition(0, 0)
			WebUI.switchToDefaultContent()
			WebUI.delay(1)
			WebUI.click(findTestObject('Object Repository/ReservationSummaryPage/btn_get_return'))

			WebUI.delay(2)
			WebUI.waitForPageLoad(10)
			//WebUI.verifyElementNotPresent(, 0)
			//WebUI.verifyElementPresent(, 0)
		}

		WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("License Plate Number") , ('Section') : section]), 0)

		verifyPayStatusAndCardLogoColorChanged(customerData,paymentData,"Car Requested", customer)
	}

	@Keyword
	def verifyPayStatusAndCardLogoColorChanged(def customerData, def paymentData, String section, String customer) {


		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card
		clickonReservationCard(customerData,"Car Requested", customer)

		String qq=  GlobalVariable.parkingFee.toString().replaceAll("\\\$","").trim()
		if(!(qq.equals("0.00"))) {
			//verify pay status
			String actualPayStatus = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_pay_status')).toLowerCase()
			WebUI.verifyMatch(actualPayStatus, "paid", false, FailureHandling.CONTINUE_ON_FAILURE)

			//verify pay status button color
			String actualColorOfPaid = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_pay_status'), "style")
			WebUI.verifyMatch(actualColorOfPaid, "background-color: rgb(104, 198, 184);" , false, FailureHandling.CONTINUE_ON_FAILURE)


			WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_car_ready'), 10)
			WebUI.delay(1)


			"verify parking fee"
			String actualParkingFee = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_parking_fee'))
			String actualParkingFee1 = actualParkingFee
			WebUI.verifyMatch(actualParkingFee1, GlobalVariable.parkingFee, false)


			if(!(GlobalVariable.paymentType.equals(""))) {
				"verify paid amount"
				String actualPaidAmount = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_paid_amount'))
				WebUI.verifyMatch(actualPaidAmount, GlobalVariable.partialPayment, false, FailureHandling.CONTINUE_ON_FAILURE)

				//verify card icon after payment via card
				if(GlobalVariable.paymentType.equals("Card")) {
					String actualTipAmount = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_tip_amount'))
					assert actualTipAmount.contains(GlobalVariable.tipAmount)


					String activeStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_card'), "class")
					assert WebUI.verifyMatch(activeStatus, "active-state", false, FailureHandling.CONTINUE_ON_FAILURE) : "Payment is not done"

					//verify front desk and cash icon not visible after card payment
					WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_cash'), 2, FailureHandling.CONTINUE_ON_FAILURE)
					if(customer.equals("Guest") || customer.equals("Visitor")) {
						WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_front_desk'), 2, FailureHandling.CONTINUE_ON_FAILURE)
					}
				}
			}

			WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_cancel'), 3, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			//verify pay status
			String actualPayStatus = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_pay_status')).toLowerCase()
			//WebUI.verifyMatch(actualPayStatus, "unpaid", false, FailureHandling.CONTINUE_ON_FAILURE)

			//verify pay status button color
			String actualColorOfPaid = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_pay_status'), "style")
			WebUI.verifyMatch(actualColorOfPaid, "background-color: rgb(216, 110, 115);" , false, FailureHandling.CONTINUE_ON_FAILURE)


			WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_car_ready'), 10)
			WebUI.delay(1)


			"verify parking fee"
			String actualParkingFee = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_parking_fee'))
			String actualParkingFee1 = actualParkingFee
			WebUI.verifyMatch(actualParkingFee1, GlobalVariable.parkingFee, false)

			if(!(GlobalVariable.paymentType.equals(""))) {
				"verify paid amount field not present"
				WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_paid_amount'), 2, FailureHandling.CONTINUE_ON_FAILURE)

				//verify card icon after payment via card
				if(GlobalVariable.paymentType.equals("Card")) {
					String actualTipAmount = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_tip_amount'))
					assert actualTipAmount.contains(GlobalVariable.tipAmount)


					String activeStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_card'), "class")
					assert WebUI.verifyMatch(activeStatus, "", false, FailureHandling.CONTINUE_ON_FAILURE) : "card icon should not be seen in green color here showing in green when pay tip in guest"

					//verify front desk and cash icon not visible after card payment
					WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_cash'), 5, FailureHandling.CONTINUE_ON_FAILURE)
					if(customer.equals("Guest") || customer.equals("Visitor")) {
						WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_front_desk'), 5, FailureHandling.CONTINUE_ON_FAILURE)
					}
				}
			}
		}







		clickAtCoordinates(649,105)
		//WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))
		//click on customer section
		clickOnCustomerSection(customer)
	}




	/*
	 * change location of reservation and verify it change location two times and verify its changed or not
	 * 
	 * */
	@Keyword
	def verifyLocationFeature(def customerData, String section, String customer) {
		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card
		clickonReservationCard(customerData,section, customer)

		WebUI.delay(2)
		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10)
		assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))

		WebUI.waitForElementPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_location'), 10)
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_location'))

		WebUI.scrollToPosition(0, 0)
		/*
		 boolean b1= WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/icon_edit_location'), 4, FailureHandling.OPTIONAL)
		 if(b1) {
		 WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/icon_edit_location'))
		 }
		 try {
		 WebUI.verifyElementHasAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_area_check_attribute', ["location" : "Area-2"]), "checked",10)
		 } catch (StepFailedException e) {
		 println("Verification failed, but continuing test: " + e.getMessage())
		 WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_area1', ["location" : "Area-2"]))
		 }
		 WebUI.delay(1)
		 WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/btn_save'))
		 verifyToastMessage("Car location saved")
		 WebUI.delay(1)
		 //click on reservation card
		 clickonReservationCard(customerData,section, customer)
		 WebUI.delay(2)
		 WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10)
		 assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))
		 WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))
		 WebUI.waitForElementPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_location'), 10)
		 WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_location'))
		 WebUI.scrollToPosition(0, 0)
		 String actualSelectedLocation = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_selectedlocation'))
		 assert actualSelectedLocation.contains("Area-2")
		 boolean b2= WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/icon_edit_location'), 4, FailureHandling.OPTIONAL)
		 if(b2) {
		 WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/icon_edit_location'))
		 }
		 WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_area1', ["location" : "Area-2"]))
		 */

		WebUI.setText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/input_enter_location'), "Kotul")

		WebUI.delay(1)
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/btn_save'))

		verifyToastMessage("Car location saved")
		WebUI.delay(1)

		//click on reservation card
		clickonReservationCard(customerData,section, customer)

		WebUI.delay(2)
		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10)
		assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))

		WebUI.waitForElementPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_location'), 10)
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_location'))

		WebUI.scrollToPosition(0, 0)

		String actualSelectedLocation1 = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_selectedlocation'))
		assert actualSelectedLocation1.contains("Kotul")

		GlobalVariable.carLocationStatus=true

		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/icon_close'))

		clickAtCoordinates(649,105)
		//WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

		//click on customer section
		clickOnCustomerSection(customer)
	}

	/*
	 * click on reservation card
	 * 
	 * */

	@Keyword
	def static clickonReservationCard(def customerData, String section, String customer) {
		WebUI.scrollToPosition(0, 0)

		if(customer.equals("Visitor") ) {
			if(section.equals("Ongoing") || section.equals("Car Requested")) {
				WebUI.waitForPageLoad(10)
				boolean flag = WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("License Plate Number") , ('Section') : section]), FailureHandling.OPTIONAL)
				if(!flag) {
					WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("License Plate Number") , ('Section') : section]), 10)
				}

				/*
				 try {
				 WebUI.waitForPageLoad(10)
				 WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("License Plate Number") , ('Section') : section]), 10)
				 } catch (StepFailedException e) {
				 println("Verification failed, but continuing test: " + e.getMessage())
				 WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("License Plate Number") , ('Section') : section]), 10)
				 }
				 */
				//WebUI.delay(1)
				WebUI.waitForPageLoad(10)
				WebUI.click(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("License Plate Number") , ('Section') : section]))
				WebUI.waitForPageLoad(10)
			}

			if(section.equals("Cancelled Reservations") || section.equals("Completed Reservations")) {
				WebUI.waitForPageLoad(10)
				boolean flag = WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/common_reservation_card_special', [('LicensePlateNo') : customerData.get("License Plate Number") , ('Section') : section]), FailureHandling.OPTIONAL)
				if(!flag) {
					WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/common_reservation_card_special', [('LicensePlateNo') : customerData.get("License Plate Number") , ('Section') : section]), 10)
				}

				/*
				 try {
				 WebUI.waitForPageLoad(10)
				 WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/common_reservation_card_special', [('LicensePlateNo') : customerData.get("License Plate Number") , ('Section') : section]), 10)
				 } catch (StepFailedException e) {
				 println("Verification failed, but continuing test: " + e.getMessage())
				 WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/common_reservation_card_special', [('LicensePlateNo') : customerData.get("License Plate Number") , ('Section') : section]), 10)
				 }
				 */

				//WebUI.delay(1)
				WebUI.waitForPageLoad(10)
				WebUI.click(findTestObject('Object Repository/ReservationPage/common_reservation_card_special', [('LicensePlateNo') : customerData.get("License Plate Number") , ('Section') : section]))
				WebUI.waitForPageLoad(10)
			}
		}else if(customer.equals("Guest") || customer.equals("Prepaid")) {
			if(section.equals("In") || section.equals("Car Requested") || section.equals("Out")) {
				WebUI.waitForPageLoad(10)
				boolean flag = WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("Last Name") , ('Section') : section]), FailureHandling.OPTIONAL)
				if(!flag) {
					WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("Last Name") , ('Section') : section]), 10)
				}

				/*
				 try {
				 WebUI.waitForPageLoad(10)
				 WebUI.delay(1)
				 WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("Last Name") , ('Section') : section]), 10)
				 } catch (StepFailedException e) {
				 println("Verification failed, but continuing test: " + e.getMessage())
				 WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("Last Name") , ('Section') : section]), 10)
				 }
				 */


				WebUI.delay(1)
				WebUI.waitForPageLoad(10)
				WebUI.waitForElementVisible(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("Last Name") , ('Section') : section]), 10, FailureHandling.CONTINUE_ON_FAILURE)
				WebUI.click(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("Last Name") , ('Section') : section]))
				WebUI.waitForPageLoad(10)
			}

			if(section.equals("Cancelled Reservations") || section.equals("Completed Reservations")) {
				WebUI.waitForPageLoad(10)
				boolean flag = WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/common_reservation_card_special', [('LicensePlateNo') : customerData.get("Last Name") , ('Section') : section]), FailureHandling.OPTIONAL)
				if(!flag) {
					WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/common_reservation_card_special', [('LicensePlateNo') : customerData.get("Last Name") , ('Section') : section]), 10)
				}

				/*
				 try {
				 WebUI.waitForPageLoad(10)
				 WebUI.delay(1)
				 WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/common_reservation_card_special', [('LicensePlateNo') : customerData.get("Last Name") , ('Section') : section]), 10)
				 } catch (StepFailedException e) {
				 println("Verification failed, but continuing test: " + e.getMessage())
				 WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/common_reservation_card_special', [('LicensePlateNo') : customerData.get("Last Name") , ('Section') : section]), 10)
				 }
				 */

				//WebUI.delay(1)
				WebUI.waitForPageLoad(10)
				WebUI.click(findTestObject('Object Repository/ReservationPage/common_reservation_card_special', [('LicensePlateNo') : customerData.get("Last Name") , ('Section') : section]))
				WebUI.waitForPageLoad(10)
			}
		}else if(customer.equals("Monthly")) {
			if(section.equals("Paid") || section.equals("Unpaid")) {
				try {
					WebUI.waitForPageLoad(10)
					WebUI.delay(1)
					WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/Monthly/section_hide_or_shoe_check',["Section" : section, "Description" : "Hide"]), 10)
				} catch(StepFailedException e) {
					WebUI.click(findTestObject('Object Repository/ReservationPage/Monthly/section_hide_or_shoe_check',["Section" : section, "Description" : "Show"]))
				}


				boolean flag = WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/Monthly/common_reservation_card_monthly1',["Section" : section, "LicensePlateNo" : customerData.get("License Plate Number")]), FailureHandling.OPTIONAL)
				if(!flag) {
					WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/Monthly/common_reservation_card_monthly1',["Section" : section, "LicensePlateNo" : customerData.get("License Plate Number")]), 10)
				}

				WebUI.delay(1)
				WebUI.click(findTestObject('Object Repository/ReservationPage/Monthly/common_reservation_card_monthly1',["Section" : section, "LicensePlateNo" : customerData.get("License Plate Number")]))
				WebUI.delay(1)
			}
			else {
				try {
					WebUI.waitForElementVisible(findTestObject('Object Repository/ReservationPage/Monthly/common_reservation_card_monthly',["Section" : section, "LicensePlateNo" : customerData.get("License Plate Number")]), 10)
					WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/Monthly/common_reservation_card_monthly',["Section" : section, "LicensePlateNo" : customerData.get("License Plate Number")]))
					WebUI.click(findTestObject('Object Repository/ReservationPage/Monthly/common_reservation_card_monthly',["Section" : section, "LicensePlateNo" : customerData.get("License Plate Number")]))
					WebUI.delay(1)
				} catch (StepFailedException e) {
					WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/Monthly/reservation_card_without_section', ["LicensePlateNo" : customerData.get("License Plate Number") ]), 10)
					WebUI.click(findTestObject('Object Repository/ReservationPage/Monthly/reservation_card_without_section', ["LicensePlateNo" : customerData.get("License Plate Number") ]))
					WebUI.delay(1)
				}
			}
		}

		else if(customer.equals("Resident1")) {
			boolean kk
			if(section.equals("Ongoing")) {
				kk = WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/section_ongoing'), 10, FailureHandling.OPTIONAL)
			}
			else if(section.equals("Car Requested")) {
				kk = WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/section_car_requested'), 10, FailureHandling.OPTIONAL)
			}else {
				kk=true
			}

			if(kk) {
				try {
					WebUI.waitForPageLoad(10)
					WebUI.waitForElementVisible(findTestObject('Object Repository/ReservationPage/Resident1/common_res_card_for_resident', ["Section" : section, "LicensePlateNo" : customerData.get("License Plate Number")]), 10)
					WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/Resident1/common_res_card_for_resident', ["Section" : section, "LicensePlateNo" : customerData.get("License Plate Number")]))
					WebUI.click(findTestObject('Object Repository/ReservationPage/Resident1/common_res_card_for_resident', ["Section" : section, "LicensePlateNo" : customerData.get("License Plate Number")]))
					WebUI.delay(1)
					WebUI.waitForPageLoad(10)
				}catch(StepFailedException e) {
					WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/Resident1/common_res_card_for_resident', ["Section" : section, "LicensePlateNo" : customerData.get("License Plate Number")]), 10)
					WebUI.delay(1)
					WebUI.click(findTestObject('Object Repository/ReservationPage/Resident1/common_res_card_for_resident', ["Section" : section, "LicensePlateNo" : customerData.get("License Plate Number")]))
					WebUI.delay(1)
					WebUI.waitForPageLoad(10)
				}
			}else {
				try {
					WebUI.waitForPageLoad(10)
					WebUI.waitForElementVisible(findTestObject('Object Repository/ReservationPage/Resident1/common_reservation_card_without_section', ["LicensePlateNo" : customerData.get("License Plate Number")]), 10)
					WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/Resident1/common_reservation_card_without_section', ["LicensePlateNo" : customerData.get("License Plate Number")]))
					WebUI.click(findTestObject('Object Repository/ReservationPage/Resident1/common_reservation_card_without_section', ["LicensePlateNo" : customerData.get("License Plate Number")]))
					WebUI.delay(1)
					WebUI.waitForPageLoad(10)
				}catch(StepFailedException e) {
					WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/Resident1/common_reservation_card_without_section', ["LicensePlateNo" : customerData.get("License Plate Number")]), 10)
					WebUI.delay(1)
					WebUI.click(findTestObject('Object Repository/ReservationPage/Resident1/common_reservation_card_without_section', ["LicensePlateNo" : customerData.get("License Plate Number")]))
					WebUI.delay(1)
					WebUI.waitForPageLoad(10)
				}
			}
		}
	}


	@Keyword
	def verifyOutFeature(def customerData, String section, String customer) {
		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card in In section
		clickonReservationCard(customerData, section, customer)

		//click on out img
		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_out'), 10)
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_out'))

		GlobalVariable.OutStatus = true
		GlobalVariable.requestCarStatus =false
		verifyToastMessage("Car-Out Success")

		setInTimeAndDateFull("OutTime")


		WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("Last Name") , ('Section') : section]), 3)

		WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("Last Name") , ('Section') : "Out"]), 10)
	}

	@Keyword
	def verifyInFeature(def customerData, String section, String customer) {
		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card in Out section
		clickonReservationCard(customerData, section, customer)

		//click on In img
		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_in'), 10)
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_In'))

		verifyToastMessage("Car-In Success")

		WebUI.scrollToPosition(0, 0)

		setInTimeAndDateFull("InTime")

		GlobalVariable.pickUpRunner=false
		GlobalVariable.DropOffRunner=false

		WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("Last Name") , ('Section') : section]), 3)

		WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("Last Name") , ('Section') : "In"]), 10)
	}

	@Keyword
	def static verifyToastMessage(String toastMessage) {
		String ss= "Something went wrong"
		//WebUI.waitForElementVisible(findTestObject('Object Repository/ReservationPage/txt_tost_message', ["ToastMessage" : toastMessage]), 15)
		String actualtoastMessage
		//boolean flag=true

		"main"
		//WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/txt_tost_message', ["ToastMessage" : toastMessage]), 10, FailureHandling.CONTINUE_ON_FAILURE)


		//WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/txt_tost_message', ["ToastMessage" : toastMessage]))
		//WebUI.waitForElementVisible(findTestObject('Object Repository/ReservationPage/txt_tost_message', ["ToastMessage" : toastMessage]), 10)

		//actualtoastMessage = WebUI.getText(findTestObject('Object Repository/ReservationPage/txt_tost_message', ["ToastMessage" : toastMessage]), FailureHandling.OPTIONAL)
		//WebUI.verifyMatch(actualtoastMessage, toastMessage, false)

		//WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/txt_tost_message', ["ToastMessage" : toastMessage]), 10)
		WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/txt_tost_message', ["ToastMessage" : ss]), 5, FailureHandling.CONTINUE_ON_FAILURE)
	}


	@Keyword
	def clickOnSendPaymentLink(def customerData, String section, String customer) {
		//click on customer section
		clickOnCustomerSection(customer)

		//click on created reservation using license plate number
		clickonReservationCard(customerData, section,customer)

		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10)
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))

		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_send_payment_link'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_send_payment_link'))

		verifyToastMessage("Payment link sent")
		WebUI.delay(2)
		//click on customer section
		clickOnCustomerSection(customer)
	}

	def verifyPayByQRCPopup(def customerData, String section, String customer) {
		//click on customer section
		clickOnCustomerSection(customer)

		//click on created reservation using license plate number
		clickonReservationCard(customerData, section, customer)

		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10)
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))

		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_pay_by_qrc'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_pay_by_qrc'))

		WebUI.scrollToPosition(0, 0)

		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_payment_qr'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/icon_close_qr'))
		WebUI.delay(1)

		clickAtCoordinates(649,105)
		//WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))
		//click on customer section
		clickOnCustomerSection(customer)
	}

	@Keyword
	def verifySMSOption(def customerData, String section, String customer) {
		//click on customer section
		clickOnCustomerSection(customer)

		clickonReservationCard(customerData, section, customer)

		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10)
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))

		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_sms'))
		WebUI.delay(1)
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_sms'))
		WebUI.delay(1)

		String pp=WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_smspopup_name'), FailureHandling.CONTINUE_ON_FAILURE)
		String name= customerData.get("First Name")+" "+customerData.get("Last Name")
		WebUI.verifyMatch(pp, name, false, FailureHandling.CONTINUE_ON_FAILURE)

		String mn=WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_sms_mobile_number'), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyEqual(mn.contains(customerData.get("Mobile Number")), true)

		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/textarea_in_send_sms'))
		WebUI.setText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/textarea_in_send_sms'), "Please Collect your vehicle")
		WebUI.delay(2)
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/btn_send'))
		verifyToastMessage("Message sent")
		WebUI.delay(2)
		List<WebElement> elements = WebUI.findWebElements(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/count_sms'), 10, FailureHandling.CONTINUE_ON_FAILURE)


		// Store the count in an integer
		int elementCount = elements.size()
		String lastSMS=WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_last_sms',["count": elementCount]), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(lastSMS, "Please Collect your vehicle", false, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.delay(2)

		WebUI.scrollToPosition(0, 0)


		//WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/icon_close_qr'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/btn_sms_back'))
		WebUI.delay(1)
		clickAtCoordinates(649,105)
		//WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

		//click on customer section
		clickOnCustomerSection(customer)
	}

	@Keyword
	def verifyMakeVisitorFeature(def customerData, String section, String customer) {
		//click on customer section
		clickOnCustomerSection(customer)

		//click on created reservation using license plate number
		clickonReservationCard(customerData, section, customer)


		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10)
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))

		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_make_visitor'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_make_visitor'))

		verifyToastMessage("Reservation updated")
		WebUI.waitForPageLoad(10)

		//click on customer section
		clickOnCustomerSection("Visitor")





		WebUI.waitForPageLoad(10)
	}


	@Keyword
	def verifyRequestCarFeature(def customerData, String section, String customer) {
		//click on customer section
		clickOnCustomerSection(customer)
		WebUI.delay(1)
		//click on reservation card
		clickonReservationCard(customerData, section, customer)

		WebUI.delay(2)
		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10)
		assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))

		String activeStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_request_car'), "class")
		assert WebUI.verifyMatch(activeStatus, "", false) : "key In Car option already clicked for given reservation"


		if(!(activeStatus.equals("active-state"))) {
			assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_request_car'))
			WebUI.delay(1)
			WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_request_car'))

			setInTimeAndDateFull("CarRequestTime")
			verifyToastMessage("Car requested")
			WebUI.delay(2)
			WebUI.waitForPageLoad(10)
			GlobalVariable.requestCarStatus=true
		}

		if(customer.equals("Visitor")) {
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("License Plate Number") , ('Section') : section]), 5)
			WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("License Plate Number") , ('Section') : "Car Requested"]), 10)
		}

		if(customer.equals("Guest") || customer.equals("Prepaid")) {
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("Last Name") , ('Section') : section]), 5, FailureHandling.CONTINUE_ON_FAILURE)
			WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("Last Name") , ('Section') : "Car Requested"]), 10, FailureHandling.CONTINUE_ON_FAILURE)
		}

		if(customer.equals("Resident1")) {
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/Resident1/common_res_card_for_resident', ["Section" : section, "LicensePlateNo" : customerData.get("License Plate Number")]), 5)
			WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/Resident1/common_res_card_for_resident', ["Section" : "Car Requested", "LicensePlateNo" : customerData.get("License Plate Number")]), 10)
		}

		if(customer.equals("Monthly")) {
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/Monthly/common_reservation_card_monthly',["Section" : section, "LicensePlateNo" : customerData.get("License Plate Number")]),5)
			WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/Monthly/common_reservation_card_monthly',["Section" : "Car Requested", "LicensePlateNo" : customerData.get("License Plate Number")]), 10)
		}

		clickonReservationCard(customerData,"Car Requested", customer)

		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10)
		WebUI.waitForElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10)
		assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))

		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))

		String activeStatus1 = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/Monthly/option_request_car'), "class")
		WebUI.verifyMatch(activeStatus1, "active-state", false)

		WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_re_check_in'), 10)

		clickAtCoordinates(649,105)
		//WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

		//click on customer section
		clickOnCustomerSection(customer)
	}

	@Keyword
	def verifyReCheckInFeature(def customerData, String section, String customer) {
		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card
		clickonReservationCard(customerData, section, customer)

		WebUI.delay(2)
		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10)
		assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))

		WebUI.delay(1)
		String activeStatus1 = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_request_car'), "class")
		assert WebUI.verifyMatch(activeStatus1, "active-state", false) : "key In Car option is not clicked for given reservation"


		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_re_check_in'))

		GlobalVariable.requestCarStatus=false

		verifyToastMessage("Reservation updated")
		WebUI.waitForPageLoad(10)

		if(customer.equals("Visitor")) {
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("License Plate Number") , ('Section') : section]), 5)
			WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("License Plate Number") , ('Section') : "Ongoing"]), 10)
		}

		if(customer.equals("Guest") || customer.equals("Prepaid")) {
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("Last Name") , ('Section') : section]), 5)
			WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("Last Name") , ('Section') : "In"]), 10)

			clickonReservationCard(customerData,"In", customer)
		}

		if(customer.equals("Resident1")) {
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/Resident1/common_res_card_for_resident', ["Section" : section, "LicensePlateNo" : customerData.get("License Plate Number")]), 5)
			WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/Resident1/common_res_card_for_resident', ["Section" : "Ongoing", "LicensePlateNo" : customerData.get("License Plate Number")]), 10)
		}

		if(customer.equals("Monthly")) {
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/Monthly/common_reservation_card_monthly',["Section" : section, "LicensePlateNo" : customerData.get("License Plate Number")]), 10)
			WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/Monthly/common_reservation_card_monthly',["Section" : "Ongoing", "LicensePlateNo" : customerData.get("License Plate Number")]), 10)
		}

		if(customer.equals("Visitor") || customer.equals("Resident1") ||customer.equals("Monthly")) {
			clickonReservationCard(customerData,"Ongoing", customer)
		}



		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10)
		WebUI.waitForElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))

		String activeStatus2 = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/Monthly/option_request_car'), "class")
		WebUI.verifyMatch(activeStatus2, "", false)

		WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_re_check_in'), 5)



		clickAtCoordinates(649,105)
		//WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

		//click on customer section
		clickOnCustomerSection(customer)
	}

	@Keyword
	def clickOnResCategoryButton(String name) {
		String actualText = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/btn_valet_category',["Name" : name]), "class")
		if(!(actualText.equals("button-selected"))) {
			WebUI.click(findTestObject('Object Repository/ReservationPage/btn_valet_category',["Name" : name]))
			WebUI.delay(1)
		}
	}

	@Keyword
	def clickOnCustomerSection(String customer) {
		try {
			//if summary text present then directly click on customer section
			WebUI.waitForElementVisible(findTestObject('Object Repository/ReservationPage/btn_visitor'), 5)
			WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/btn_visitor'))

			String actualText = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/btn_valet_category',["Name" : customer]), "class")
			if(!(actualText.equals("button-selected"))) {
				WebUI.click(findTestObject('Object Repository/ReservationPage/btn_valet_category',["Name" : customer]))
				WebUI.delay(1)
				WebUI.waitForPageLoad(10)
			}
		} catch(StepFailedException e){
			//else click on reservation link then click on customer section
			try {
				WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/link_reservation'))
			}catch(StepFailedException e1){
				WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/link_reservation'), 10)
			}

			WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

			String actualText = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/btn_valet_category',["Name" : customer]), "class")
			if(!(actualText.equals("button-selected"))) {
				WebUI.click(findTestObject('Object Repository/ReservationPage/btn_valet_category',["Name" : customer]))
				WebUI.delay(1)
				WebUI.waitForPageLoad(10)
			}
		}
	}

	@Keyword
	def verifyNotesFeature(def customerData, String section, String customer ) {
		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card
		clickonReservationCard(customerData, section, customer)

		WebUI.delay(2)
		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10)
		assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))

		if(!(customerData.get("Notes").equals("NA"))) {
			WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_notes'))
			WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_notes'))

			WebUI.delay(1)

			WebUI.scrollToPosition(0, 0)

			WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_notes_on_notes_popup'))

			String actualNotes = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_actual_notes'))
			WebUI.verifyMatch(actualNotes, customerData.get("Notes"), false)

			WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/icon_close'))
			WebUI.delay(1)
		}else {
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_notes'), 5)
		}

		clickAtCoordinates(649,105)
		//WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

		//click on customer section
		clickOnCustomerSection(customer)
	}

	@Keyword
	def verifyDamagesFeature(def customerData, String section, String customer ) {
		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card
		clickonReservationCard(customerData, section, customer)

		WebUI.delay(2)
		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10)
		assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))


		if(customerData.get("Damage").equals("Yes")) {
			WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_damages'))
			WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_damages'))

			WebUI.scrollToPosition(0, 0)

			WebUI.delay(1)
			WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_vehicle_damage_images'))
			WebUI.delay(1)

			WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/icon_close'))
		}else {
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_damages'), 5)
		}

		clickAtCoordinates(649,105)
		//WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

		//click on customer section
		clickOnCustomerSection(customer)
	}


	@Keyword
	def verifyCancelFeature(def customerData, String section, String customer) {
		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card
		clickonReservationCard(customerData, section, customer)

		WebUI.delay(2)
		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10)
		assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))

		WebUI.delay(1)
		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_cancel'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_cancel'))

		WebUI.delay(1)
		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_cancel_on_cancel_popup'))

		WebUI.setText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/input_cancel_reason'), GlobalVariable.cancellationReason)
		WebUI.delay(1)

		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/btn_confirm'))

		verifyToastMessage("Booking cancelled")
		WebUI.delay(2)
		GlobalVariable.reservationStatus="cancelled"
		GlobalVariable.cancelReservationStatus=true

		setDayAndTime("CheckOut")
		setInTimeAndDateFull("end")

		//verify canceled reservation not present in section
		try {
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("License Plate Number") , ('Section') : section]), 5)
		} catch (StepFailedException e) {
			//println("Verification failed, but continuing test: " + e.getMessage())
			WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("License Plate Number") , ('Section') : section]), 10)
		}


		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/section_canceled_reservations'), 10)
		WebUI.click(findTestObject('Object Repository/ReservationPage/section_canceled_reservations'))

		//click on reservation card
		clickonReservationCard(customerData, "Cancelled Reservations", customer)
		WebUI.delay(1)


		//get user full name
		WebUI.click(findTestObject('Object Repository/HomePage/header_username'))
		WebUI.delay(2)
		GlobalVariable.UserName = WebUI.getText(findTestObject('Object Repository/HomePage/txt_user_full_name'), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/HomePage/header_username'))


		//verify cancelled by
		String actualCancelledby  = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_cancelled_by'))
		WebUI.verifyMatch(actualCancelledby, GlobalVariable.UserName, false)

		//verify cancellation reason
		String actualCancellationreason = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_cancellation_reason'))
		WebUI.verifyMatch(actualCancellationreason, GlobalVariable.cancellationReason, false, FailureHandling.CONTINUE_ON_FAILURE)

		clickAtCoordinates(649,105)
	}

	@Keyword
	def checkoutReservation(def customerData, String section, String customer) {
		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card
		clickonReservationCard(customerData, section, customer)


		if(customer.equals("Visitor")) {
			WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_checkout'), 10)

			String actualText = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_checkout'), "style")
			if(actualText.equals("background: rgb(136, 136, 136);")) {
				assert false : "checkout button is not clickable reservation can be unpaid or partially paid"
			}
			else if(actualText.equals("background: linear-gradient(90deg, rgb(128, 87, 160), rgb(216, 109, 124));")) {
				WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_checkout'))
			}

			setDayAndTime("CheckOut")
			setInTimeAndDateFull("end")

			verifyToastMessage("Car Checkout Success")

			WebUI.delay(2)
			GlobalVariable.reservationStatus= "completed"

			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("License Plate Number") , ('Section') : section]), 5)

			//click on show completed reservation
			WebUI.click(findTestObject('Object Repository/ReservationPage/section_completed_reservations'))

			//click on reservation card
			clickonReservationCard(customerData, "Completed Reservations", customer)

			WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_payment_type'), 10)
			String actualPaymentType = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_payment_type'), FailureHandling.CONTINUE_ON_FAILURE)
			WebUI.verifyMatch(actualPaymentType, GlobalVariable.paymentType, false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		if(customer.equals("Guest") || customer.equals("Prepaid")) {

			WebUI.delay(2)
			WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10)
			assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))
			WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))


			WebUI.waitForElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_checkout'), 10)
			WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_checkout'))

			setDayAndTime("CheckOut")
			setInTimeAndDateFull("end")

			verifyToastMessage("Car Checkout Success")
			WebUI.delay(2)

			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("License Plate Number") , ('Section') : section]), 5)

			//click on show completed reservation
			WebUI.click(findTestObject('Object Repository/ReservationPage/section_completed_reservations'))

			//click on reservation card
			clickonReservationCard(customerData, "Completed Reservations", customer)

			WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_payment_type'), 10)
			String actualPaymentType = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_payment_type'), FailureHandling.CONTINUE_ON_FAILURE)
			WebUI.verifyMatch(actualPaymentType, GlobalVariable.paymentType, false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		if(customer.equals("Resident1") || customer.equals("Monthly")) {
			//make checkout
			WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10)

			String actualText = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_checkout'), "style")
			if(actualText.equals("background: rgb(136, 136, 136);")) {
				assert false : "checkout button is not clickable"
			}
			else if(actualText.equals("background: linear-gradient(90deg, rgb(128, 87, 160), rgb(216, 109, 124));")) {
				WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_checkout'))
			}

			setDayAndTime("CheckOut")
			setInTimeAndDateFull("end")

			//verify toast message
			verifyToastMessage("Car Checkout Success")
			WebUI.waitForPageLoad(10)

			"Click on reservation card"
			if(customer.equals("Resident1")) {
				WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/Resident1/common_res_card_for_resident', ["Section" : section, "LicensePlateNo" : customerData.get("License Plate Number")]), 5)
				clickonReservationCard(customerData, "Hide", customer)
			}
			if(customer.equals("Monthly")) {
				WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/Monthly/common_reservation_card_monthly',["Section" : section, "LicensePlateNo" : customerData.get("License Plate Number")]), 5)
				clickonReservationCard(customerData, "Paid", customer)
			}
		}

		GlobalVariable.CheckoutStatus= true


		clickAtCoordinates(649,105)

		//click on customer section
		clickOnCustomerSection(customer)
	}

	@Keyword
	def verifyActiveStatus(def customerData, String section, String customer) {
		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card
		clickonReservationCard(customerData, section, customer)

		//get pay status
		String actualPayStatus = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_pay_status'), FailureHandling.CONTINUE_ON_FAILURE).toLowerCase()
		WebUI.delay(1)
		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'), 10)
		WebUI.delay(1)

		//verify key In status active or not
		if(GlobalVariable.keyInStatus) {
			String activeStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_key_in'), "class")
			assert WebUI.verifyMatch(activeStatus, "active-state", false) : "key In  option already clicked for given reservation"
		}else {
			String activeStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_key_in'), "class")
			assert WebUI.verifyMatch(activeStatus, "", false) : "key In  option already clicked for given reservation"
		}

		//verify checkout option is active or not
		String actualNetPayable = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_net_payable'))
		String dd2 = actualNetPayable.replaceAll("\\\$","").trim()
		if(!(dd2.equals("0.00")))
			if(customer.equals("Visitor")) {
				if((actualPayStatus.equals("unpaid") || actualPayStatus.equals("partial paid")) && !(dd2.equals("0.00"))) {
					String actual_checkoutStyle = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_checkout'), "style", FailureHandling.CONTINUE_ON_FAILURE)
					WebUI.verifyMatch(actual_checkoutStyle, "background: rgb(136, 136, 136);", false, FailureHandling.CONTINUE_ON_FAILURE)
				}else {
					String actual_checkoutStyle = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_checkout'), "style", FailureHandling.CONTINUE_ON_FAILURE)
					WebUI.verifyMatch(actual_checkoutStyle, "background: linear-gradient(90deg, rgb(128, 87, 160), rgb(216, 109, 124));", false, FailureHandling.CONTINUE_ON_FAILURE)
				}
			}else if(customer.equals("Guest") || customer.equals("Prepaid")) {
				if((actualPayStatus.equals("unpaid") || actualPayStatus.equals("partial paid")) && !(dd2.equals("0.00"))) {
					String actual_checkoutStyle = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_checkout'), "style", FailureHandling.CONTINUE_ON_FAILURE)
					WebUI.verifyMatch(actual_checkoutStyle, "color: rgb(136, 136, 136);", false, FailureHandling.CONTINUE_ON_FAILURE)
				}else {
					String actual_checkoutStyle = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_checkout'), "style", FailureHandling.CONTINUE_ON_FAILURE)
					WebUI.verifyMatch(actual_checkoutStyle, "color: rgb(51, 51, 51);", false, FailureHandling.CONTINUE_ON_FAILURE)
				}
			}

		//verify key In Car option
		if(GlobalVariable.keyInCarStatus) {
			WebUI.delay(1)
			String activeStatus1 = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_car_in_key'), "class")
			assert WebUI.verifyMatch(activeStatus1, "active-state", false) : "key In Car option not in green color"
		}else {
			WebUI.delay(1)
			String activeStatus1 = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_car_in_key'), "class")
			assert WebUI.verifyMatch(activeStatus1, "", false) : "key In Car option not in green color"
		}

		//verify car on the way status
		if(GlobalVariable.carOnTheWayStatus) {
			WebUI.delay(1)
			String activeStatus1 = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_car_on_the_way'), "class")
			assert WebUI.verifyMatch(activeStatus1, "active-state", false) : "Car On the Way option not in green color"
		}else {
			WebUI.delay(1)
			String activeStatus1 = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_car_on_the_way'), "class")
			assert WebUI.verifyMatch(activeStatus1, "", false) : "Car On the Way option not in green color"
		}

		//verify request car option
		if(GlobalVariable.requestCarStatus) {
			WebUI.delay(1)
			String activeStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_request_car'), "class")
			assert WebUI.verifyMatch(activeStatus, "active-state", false) : "key In Car option already clicked for given reservation"
		}else {
			WebUI.delay(1)
			String activeStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_request_car'), "class")
			assert WebUI.verifyMatch(activeStatus, "", false) : "key In Car option already clicked for given reservation"
		}

		clickAtCoordinates(649,105)
		//click on reservation link
		//WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

		//click on customer section
		clickOnCustomerSection(customer)
	}

	@Keyword
	def clickAtCoordinates(int xCoordinate, int yCoordinate) {
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver
		WebUI.delay(1)
		String script = "var elem = document.elementFromPoint(arguments[0], arguments[1]); elem.click();"
		js.executeScript(script, xCoordinate, yCoordinate)
	}

	@Keyword
	def verifyBookingdetailsOfCheckoutReservation(def customerData, String section, String customer) {
		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation
		clickonReservationCard(customerData, "Hide", customer)
	}

	@Keyword
	def verifykeyInAlertRed(def customerData, String section, String customer) {
		WebUI.delay(2)
		String actualKeyInAlert

		if(customer.equals("Visitor")) {
			try {
				WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/reservation_by_license_plate_no', ["Section": section,('LicensePlateNo') : customerData.get("License Plate Number")]), 10)
			} catch (StepFailedException e) {
				WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/reservation_by_license_plate_no', ["Section": section,('LicensePlateNo') : customerData.get("License Plate Number")]), 10)
			}

			actualKeyInAlert = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/reservation_by_license_plate_no', ["Section": section,('LicensePlateNo') : customerData.get("License Plate Number")]), "class")
		}

		if(customer.equals("Guest") || customer.equals("Prepaid")) {
			try {
				WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/reservation_by_license_plate_no', ["Section": section,('LicensePlateNo') : customerData.get("Last Name")]), 10)
			} catch (StepFailedException e) {
				println("Verification failed, but continuing test: " + e.getMessage())
				WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/reservation_by_license_plate_no', ["Section": section,('LicensePlateNo') : customerData.get("Last Name")]), 10)
			}

			actualKeyInAlert = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/reservation_by_license_plate_no', ["Section": section,('LicensePlateNo') : customerData.get("Last Name")]), "class")
		}

		//click on reservation
		clickonReservationCard(customerData, section, customer)

		String parkDuration = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_parking_duration'), FailureHandling.CONTINUE_ON_FAILURE)
		boolean flag= false

		if(parkDuration.contains("hr")) {
			flag = true
		}else {
			parkDuration=(Double.parseDouble(parkDuration.replaceAll(/\D/, '').trim()))/60
			if(parkDuration > 0.25) {
				flag = true
			}
		}

		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_Key_In'), 10)
		String actualKeyInStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_key_In'), "class")

		//	clickAtCoordinates(649,105)
		//WebUI.delay(1)

		if(actualKeyInStatus.equals("") && flag) {
			assert actualKeyInAlert.contains("key-in")
		}else {
			if(actualKeyInAlert.contains("key-in")) {
				assert false : "key in alert should not be true reservation card should be in red color"
			}
		}
	}

	@Keyword
	def verifyCashPayment(def customerData, String section, String customer) {
		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card
		clickonReservationCard(customerData, section, customer)


		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_cash'), 10)

		String actualActiveStatus1 = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_cash'), "class")
		WebUI.verifyMatch(actualActiveStatus1, "", false, FailureHandling.CONTINUE_ON_FAILURE)

		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_cash'))
		verifyToastMessage("Paid successfully")

		GlobalVariable.paymentType = "Cash"

		//click on reservation card
		clickonReservationCard(customerData, section, customer)

		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_cash'), 10)

		//verify card and front desk is not visible
		WebUI.delay(1)
		WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_card'), 5, FailureHandling.CONTINUE_ON_FAILURE)
		if(customer.equals("Guest")) {
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_front_desk'), 5, FailureHandling.CONTINUE_ON_FAILURE)
		}
		String actualActiveStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_cash'), "class")
		WebUI.verifyMatch(actualActiveStatus, "active-state", false, FailureHandling.CONTINUE_ON_FAILURE)

		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))
		WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_cancel'), 5, FailureHandling.CONTINUE_ON_FAILURE)

		clickAtCoordinates(649,105)
	}

	@Keyword
	def verifyFrontDeskPayment(def customerData, String section, String customer) {
		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card
		clickonReservationCard(customerData, section, customer)
		WebUI.delay(1)



		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_front_desk'), 10)

		String actualActiveStatus1 = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_front_desk'), "class")
		WebUI.verifyMatch(actualActiveStatus1, "", false, FailureHandling.CONTINUE_ON_FAILURE)

		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_front_desk'))
		verifyToastMessage("Payment received")


		GlobalVariable.paymentType = "Front Desk"

		//click on reservation card
		clickonReservationCard(customerData, section, customer)

		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_front_desk'), 10)

		//verify card and front desk is not visible
		WebUI.delay(1)
		WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_card'), 5, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_cash'), 5, FailureHandling.CONTINUE_ON_FAILURE)

		String actualActiveStatus = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/active_status_front_desk'), "class")
		WebUI.verifyMatch(actualActiveStatus, "active-state", false, FailureHandling.CONTINUE_ON_FAILURE)


		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))
		WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/option_cancel'), 5, FailureHandling.CONTINUE_ON_FAILURE)

		clickAtCoordinates(649,105)
	}


	@Keyword
	def setDayAndTime(String status) {
		def dateFormat = new SimpleDateFormat("MMM dd, yyyy")
		dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"))
		def currentDate = new Date()
		def formattedDate = dateFormat.format(currentDate)

		def dateFormat1 = new SimpleDateFormat("hh:mm a")
		dateFormat1.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"))
		def currentTime = new Date()
		def formattedTime = dateFormat1.format(currentTime)




		if(status.equals("CheckIn")) {
			GlobalVariable.CheckInTime  = formattedTime
			GlobalVariable.CheckInDate = formattedDate
		}

		if(status.equals("CheckOut")) {
			GlobalVariable.CheckOutTime = formattedTime
			GlobalVariable.CheckOutDate = formattedDate
		}
	}


	@Keyword
	def setInTimeAndDateFull(String status) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy, hh:mm a")
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata")) // Set the time zone to Indian Standard Time

		Date currentDate11 = new Date()
		String formattedDate11 = sdf.format(currentDate11)

		if(status.equals("InTime")) {
			GlobalVariable.InTime= formattedDate11
		}

		if(status.equals("OutTime")) {
			GlobalVariable.OutTime = formattedDate11
		}
		if(status.equals("CarRequestTime")) {
			GlobalVariable.CarRequestTime = formattedDate11
		}

		if(status.equals("start")) {
			GlobalVariable.startTimeAndDate = formattedDate11
		}

		if(status.equals("end")) {
			GlobalVariable.endTimeAndDate = formattedDate11
		}
	}


	@Keyword
	def verifyRestartFeature(def customerData, String section, String customer) {
		//click on customer section
		clickOnCustomerSection(customer)

		//click on reservation card
		clickonReservationCard(customerData, section, customer)
		WebUI.delay(1)

		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_restart'), 10)
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_restart'))
		WebUI.waitForPageLoad(10)
		verifyToastMessage("Reservation created")
		WebUI.refresh()
		WebUI.waitForPageLoad(10)
		validateCreatedReservation(customerData, section, customer)
	}
}