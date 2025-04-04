package com.Parkquility.app.Keywords


import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.sun.jna.WString
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.model.FailureHandling







class PermitParking {
	ReservationPage res = new ReservationPage()


	/*
	 * 
	 * Create new rule in permit parking
	 *
	 * */
	@Keyword
	def createNewRule(def rulesData) {

		WebUI.delay(2)
		"find count of total forms in rules"
		List<TestObject> elements = WebUI.findWebElements(findTestObject('Object Repository/PermitParking/RulesPage/count_rules_forms'),10)
		int lastFormNum = elements.size()

		"scroll to last form and set name"
		WebUI.scrollToElement(findTestObject('Object Repository/PermitParking/RulesPage/input_name', ["LastFormNumber" : lastFormNum]), 10)
		WebUI.setText(findTestObject('Object Repository/PermitParking/RulesPage/input_name', ["LastFormNumber" : lastFormNum]), rulesData.get("Name"))

		"set price"
		WebUI.setText(findTestObject('Object Repository/PermitParking/RulesPage/input_price',["LastFormNumber" : lastFormNum]), rulesData.get("Price"))

		"click on visibility checkbox"
		WebUI.click(findTestObject('Object Repository/PermitParking/RulesPage/input_visibility_checkbox',["LastFormNumber" : lastFormNum]))

		"scroll to submit button and click on submit button"
		WebUI.scrollToElement(findTestObject('Object Repository/PermitParking/RulesPage/btn_submit',["LastFormNumber" : lastFormNum]), 10)
		WebUI.click(findTestObject('Object Repository/PermitParking/RulesPage/btn_submit',["LastFormNumber" : lastFormNum]))
		WebUI.delay(2)

		"verify eye symbol visible"
		WebUI.verifyElementPresent(findTestObject('Object Repository/PermitParking/RulesPage/eye_symbol',["LastFormNumber" : lastFormNum]), 10)
		String aa=WebUI.getAttribute(findTestObject('Object Repository/PermitParking/RulesPage/eye_symbol',["LastFormNumber" : lastFormNum]), "class")
		//WebUI.verifyMatch(aa, "mp-eye fa fa-eye mp-eye-active", false)

		"verify disable button present"
		WebUI.verifyElementPresent(findTestObject('Object Repository/PermitParking/RulesPage/btn_disable',["LastFormNumber" : lastFormNum]), 10)
	}

	/*
	 * validate created rule are visible and fill some necessary req info
	 * 
	 * */
	@Keyword
	def validateMonthlyParking(def rulesData) {
		WebUI.navigateToUrl("https://stagingregister.parkquility.com/?id=pqhrwj3x5n")
		WebUI.delay(2)

		"verify name is visible as we entered"
		WebUI.scrollToElement(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/txt_name',["Name" : rulesData.get("Name")]), 10)
		WebUI.verifyElementVisible(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/txt_name',["Name" : rulesData.get("Name")]))

		"verify price is visible as we entered"
		String actualPrice = WebUI.getText(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/txt_price',["Name" : rulesData.get("Name")])).replaceAll("[\$\\s]+", "")
		WebUI.verifyMatch(actualPrice, rulesData.get("Price"), false)

		"click on select button"
		WebUI.click(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/btn_select',["Name" : rulesData.get("Name")]))

		"Verify error messages are shown"
		WebUI.click(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/input_checkbox'))
		WebUI.click(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/btn_submit'))

		WebUI.scrollToPosition(0, 0)

		WebUI.verifyElementVisible(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/error_txt_enter_the_admin_email_address'))
		WebUI.verifyElementVisible(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/error_txt_enter_your_license_plate'))
		WebUI.verifyElementVisible(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/error_txt_enter_your_vehicle_make'))
		WebUI.verifyElementVisible(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/error_txt_enter_your_vehicle_color'))

		WebUI.scrollToPosition(0, 0)

		WebUI.verifyElementVisible(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/error_txt_enter_your_first_name'))
		WebUI.verifyElementVisible(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/error_txt_enter_your_last_name'))
		WebUI.verifyElementVisible(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/error_txt_enter_your_mobile_number'))



		"fill the form "
		//enter first name
		WebUI.setText(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/input_firstName'), rulesData.get("First Name"))

		//enter last name
		WebUI.setText(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/input_lastName'), rulesData.get("Last Name"))

		//select country and enter mobile number
		WebUI.click(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/btn_select_country'))
		WebUI.click(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/option_country',["CountryName" : rulesData.get("Country Name")]))
		WebUI.setText(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/input_mobile_number'), rulesData.get("Mobile Number"))

		//enter email
		WebUI.setText(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/input_email'), rulesData.get("Email"))

		//enter unit number
		WebUI.setText(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/input_unit_number'), rulesData.get("Unit Number"))


		//enter license plate number
		WebUI.setText(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/input_license_plate_number'), rulesData.get("License Plate Number"))

		//enter vehicle make, color, model
		WebUI.setText(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/input_vehicle_make'), rulesData.get("Vehicle Make"))
		WebUI.setText(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/input_vehicle_color'), rulesData.get("Vehicle Color"))
		WebUI.setText(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/input_vehicle_model'), rulesData.get("Vehicle Model"))

		//select vehicle type
		WebUI.click(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/select_vehicle_type'))
		WebUI.click(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/option_vehicle_type',["VehicleType" : rulesData.get("Vehicle Type")]))
		WebUI.delay(1)

		//click on next button
		WebUI.click(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/btn_submit'))

		/*
		 String tostMessage = WebUI.getText(findTestObject('Object Repository/PermitParking/RulesPage/CreatedRulePage/tostmessage_validity_for_the_specified_duration_overlap_with_existing_validity'))
		 if(tostMessage.equals("Validity for the specified duration overlaps with existing validity")) {
		 assert false : "Validity for the specified duration overlaps with existing validity"
		 }
		 */
		WebUI.delay(2)
	}

	/*
	 * fill monthly parking data and click on pay button
	 * 
	 * */
	@Keyword
	def fillMonthlyParkingPass(def rulesData, def addressData, def paymentData) {
		//WebUI.navigateToUrl("https://stagingmonthly.parkquility.com/?id=fed9830ea337bbe36081")
		WebUI.delay(3)

		"verify first name, last name, mobile number, email are visible"
		String actualFirstName = WebUI.getAttribute(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/input_first_name'), "value")
		WebUI.verifyMatch(actualFirstName, rulesData.get("First Name"), false)

		String actualLastName = WebUI.getAttribute(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/input_last_name'), "value")
		WebUI.verifyMatch(actualLastName, rulesData.get("Last Name"), false)

		String actualMobileNumber = WebUI.getAttribute(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/input_mobile_number'), "value")
		WebUI.verifyMatch(actualMobileNumber, rulesData.get("Mobile Number"), false)

		String actualEmail = WebUI.getAttribute(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/input_email'), "value")
		WebUI.verifyMatch(actualEmail, rulesData.get("Email"), false)

		"fill address data"
		WebUI.setText(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/input_street_address') , addressData.get("Street Address"))
		WebUI.setText(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/input_apt_suite') , addressData.get("Unit Number"))
		WebUI.setText(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/input_city') , addressData.get("City"))
		WebUI.setText(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/input_state') , addressData.get("State"))
		WebUI.setText(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/input_zip') , addressData.get("Zip"))

		"fill vehicle data"
		String actualLicensePlateNumber = WebUI.getAttribute(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/input_license_plate_number'), "value")
		WebUI.verifyMatch(actualLicensePlateNumber, rulesData.get("License Plate Number"), false)

		String actualVehicleColor = WebUI.getAttribute(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/input_vehicle_color'), "value")
		WebUI.verifyMatch(actualVehicleColor, rulesData.get("Vehicle Color"), false)

		String actualVehicleMake = WebUI.getAttribute(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/input_vehicle_make'), "value")
		WebUI.verifyMatch(actualVehicleMake, rulesData.get("Vehicle Make"), false)

		String actualVehicleModel = WebUI.getAttribute(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/input_vehicle_model'), "value")
		WebUI.verifyMatch(actualVehicleModel, rulesData.get("Vehicle Model"), false)

		WebUI.setText(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/input_vehicle_state'), rulesData.get("Vehicle State"))
		WebUI.setText(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/input_vehicle_year') ,rulesData.get("Vehicle Year"))

		WebUI.click(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/select_vehicle_type'))
		WebUI.click(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/option_vehicle_type',["VehicleType" : rulesData.get("Vehicle Type")]))


		"fill payment data"
		WebUI.switchToFrame(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/iframe_payment'), 10)
		WebUI.scrollToElement(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/input_card_number'), 10)
		WebUI.setText(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/input_card_number') , paymentData.get("Credit Card Number"))

		WebUI.setText(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/input_expiration'), paymentData.get("Expiry Date"))
		WebUI.setText(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/input_cvc'), paymentData.get("CVC"))

		WebUI.switchToDefaultContent()

		WebUI.setText(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/input_card_holder_name'), paymentData.get("Cardholder Name"))
		WebUI.click(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/input_i_aggree_checkbox'))

		WebUI.verifyElementClickable(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/btn_pay'))
		WebUI.click(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/btn_pay'))

		WebUI.waitForElementVisible(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/txt_payment_successfull'), 10, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyElementVisible(findTestObject('Object Repository/PermitParking/RulesPage/MonthlyPassPage/txt_payment_successfull'))
	}

	@Keyword
	def static clickOnMonthlyCard(def permitParkingData, String section){

		if(section.equals("Paid") || section.equals("Unpaid")) {
			try {
				WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/Monthly/section_hide_or_shoe_check',["Section" : section, "Description" : "Hide"]), 10)
			} catch(StepFailedException e) {
				WebUI.click(findTestObject('Object Repository/ReservationPage/Monthly/section_hide_or_shoe_check',["Section" : section, "Description" : "Show"]))
			}


			WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/Monthly/common_reservation_card_monthly1',["Section" : section, "LicensePlateNo" : permitParkingData.get("License Plate Number")]))
			WebUI.delay(1)
			WebUI.click(findTestObject('Object Repository/ReservationPage/Monthly/common_reservation_card_monthly1',["Section" : section, "LicensePlateNo" : permitParkingData.get("License Plate Number")]))
		}
		else {
			try {
				WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/Monthly/reservation_card_without_section', ["LicensePlateNo" : permitParkingData.get("License Plate Number") ]), 10)
				WebUI.click(findTestObject('Object Repository/ReservationPage/Monthly/reservation_card_without_section', ["LicensePlateNo" : permitParkingData.get("License Plate Number") ]))
			} catch (StepFailedException e) {
				WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/Monthly/common_reservation_card_monthly',["Section" : section, "LicensePlateNo" : permitParkingData.get("License Plate Number")]))
				WebUI.click(findTestObject('Object Repository/ReservationPage/Monthly/common_reservation_card_monthly',["Section" : section, "LicensePlateNo" : permitParkingData.get("License Plate Number")]))
			}
		}
	}



	@Keyword
	def verifyMonthlyCardInfo(def monthlyData,def addressData, String section){
		WebUI.delay(2)
		res.clickonReservationCard(monthlyData, section, "Monthly")

		/*
		 if(section.equals("Paid")) {
		 WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/Monthly/btn_monthly_with_color',["BackgroundColor" : "background-color: rgb(104, 198, 184);"]), 10)
		 } else {
		 WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/Monthly/btn_monthly_with_color',["BackgroundColor" : "background-color: rgb(216, 110, 115);"]), 10)
		 }
		 */

		String actualMake = WebUI.getText(findTestObject('Object Repository/ReservationPage/Monthly/txt_make'))
		String actualModel = WebUI.getText(findTestObject('Object Repository/ReservationPage/Monthly/txt_model'))
		String actualColor = WebUI.getText(findTestObject('Object Repository/ReservationPage/Monthly/txt_color'))
		String actualKeyNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/Monthly/txt_key_number'))
		String actualLicensePlateNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/Monthly/txt_license_plate_number'))
		String actualUnitNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/Monthly/txt_unit_number'))

		String actualMobileNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/Monthly/txt_mobile_number'))
		assert actualMobileNumber.contains(monthlyData.get("Mobile Number"))

		String actualStartTime = WebUI.getText(findTestObject('Object Repository/ReservationPage/Monthly/txt_start_time'))
		String actualParkDuration = WebUI.getText(findTestObject('Object Repository/ReservationPage/Monthly/txt_park_duration'))
		String actualName = WebUI.getText(findTestObject('Object Repository/ReservationPage/Monthly/txt_name'))

		WebUI.verifyMatch(actualMake, monthlyData.get("Vehicle Make"), false, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(actualModel, monthlyData.get("Vehicle Model"), false, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(actualColor, monthlyData.get("Vehicle Color"), false, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(actualLicensePlateNumber, monthlyData.get("License Plate Number"), false, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(actualUnitNumber, addressData.get("Unit Number"), false, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(actualName, monthlyData.get("Name"), false, FailureHandling.CONTINUE_ON_FAILURE)

		res.clickAtCoordinates(649,105)
		//WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))
		res.clickOnCustomerSection("Monthly")
	}



	@Keyword
	def checkoutFeatureForMonthly(def permitParkingData) {
		//click on section
		res.clickOnCustomerSection(customer)

		"Click on reservation card"
		res.clickonReservationCard(permitParkingData, "Car", "Monthly")

		//make checkout
		assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_checkout'))

		//verify toast message
		res.verifyToastMessage("Car Checkout Success")

		WebUI.waitForPageLoad(10)

		"Click on reservation card"
		res.clickonReservationCard(permitParkingData, "Paid", "Monthly")
	}


	@Keyword
	def verifyEditFeatureInMonthlyParkingAndResident1(def permitParkingData, String section, String customer) {
		//click on section
		res.clickOnCustomerSection(customer)

		String expectedKey
		String expectedUnitNumber
		String expectedLocation
		String expectedParkDuration
		WebUI.waitForPageLoad(10)
		WebUI.delay(2)
		try {
			WebUI.waitForPageLoad(10)
			expectedKey = WebUI.getText(findTestObject('Object Repository/ReservationPage/Monthly/txt_key_on_card',["Section" : section, "LicensePlateNo" : permitParkingData.get("License Plate Number")]))
			//expectedUnitNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/Monthly/txt_unit_number_on_card',["Section" : section, "LicensePlateNo" : permitParkingData.get("License Plate Number")]))
			expectedLocation = WebUI.getText(findTestObject('Object Repository/ReservationPage/Monthly/txt_location_on_card',["Section" : section, "LicensePlateNo" : permitParkingData.get("License Plate Number")]))
			expectedParkDuration = WebUI.getText(findTestObject('Object Repository/ReservationPage/Monthly/txt_park_duration_on_card',["Section" : section, "LicensePlateNo" : permitParkingData.get("License Plate Number")]))
		}catch(StepFailedException e) {
			expectedKey = WebUI.getText(findTestObject('Object Repository/ReservationPage/Monthly/without_section_key_no_on_card',["LicensePlateNo" : permitParkingData.get("License Plate Number")]))
			//expectedUnitNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/Monthly/withoutSection_unit_no_on_card',["LicensePlateNo" : permitParkingData.get("License Plate Number")]))
			expectedLocation = WebUI.getText(findTestObject('Object Repository/ReservationPage/Monthly/withoutSection_txt_location_on_card',["LicensePlateNo" : permitParkingData.get("License Plate Number")]))
			expectedParkDuration = WebUI.getText(findTestObject('Object Repository/ReservationPage/Monthly/without_section_time_duration_on_card',["LicensePlateNo" : permitParkingData.get("License Plate Number")]))
		}


		//findTestObject('Object Repository/ReservationPage/Monthly/withoutSection_txt_licensePlateNo')



		"Click on reservation card"
		res.clickonReservationCard(permitParkingData, section, customer)

		"verify key between card info and booking details"
		String actualKey =WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_key'))
		WebUI.verifyMatch(actualKey, expectedKey, false)

		"verify Unit Number between card info and booking details"
		//String actualUnitNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_unit_number'))
		//WebUI.verifyMatch(actualUnitNumber, expectedUnitNumber, false)

		"verify location between card info and booking details"
		String actualLocation1 = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_location'))
		WebUI.verifyMatch(actualLocation1, expectedLocation, false)

		"verify park duration between card info and booking details"
		String actualParkDur =WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_parking_duration'))
		//WebUI.verifyMatch(actualParkDur, expectedParkDuration, false)

		"click on edit icon"
		WebUI.click(findTestObject('Object Repository/ReservationPage/Monthly/icon_edit_reservation'))
		WebUI.delay(2)


		"verify mobile number and license plate number is gray in color"
		if(customer.equals("Monthly")) {
			String actualMobileNoGrayColor = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/mobile_no_gray_color'), "style")
			WebUI.verifyMatch(actualMobileNoGrayColor, "background-color: rgb(238, 238, 238); pointer-events: none;", false)
			String actualLicenseNoGrayColor = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/license_no_gray_color'), "style")
			WebUI.verifyMatch(actualLicenseNoGrayColor, "background-color: rgb(238, 238, 238); pointer-events: none;", false)
		}
		//verify parking spot between booking deatil page and edit reservation form
		String expectedParkingSpot11= WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/select_parking_spot_edit'), "value")
		WebUI.verifyMatch(expectedParkingSpot11, actualLocation1, false, FailureHandling.CONTINUE_ON_FAILURE)

		//update parking spot
		WebUI.click(findTestObject('Object Repository/ReservationPage/select_parking_spot_edit'))
		String expectedParkingSpot = WebUI.getText(findTestObject('Object Repository/ReservationPage/second_available_location_for_edit'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/second_available_location_for_edit'))
		String expectedParkingSpot1= WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/select_parking_spot_edit'), "value")
		WebUI.verifyMatch(expectedParkingSpot, expectedParkingSpot1, false, FailureHandling.CONTINUE_ON_FAILURE)


		//verify key tag in booking details and edit reservation page
		String updatedKeySlot1=WebUI.getAttribute(findTestObject('Object Repository/ManageResidentPage/input_key_slot_edit_page'), "value")
		WebUI.verifyMatch(updatedKeySlot1, actualKey, false, FailureHandling.CONTINUE_ON_FAILURE)

		//update key slot
		WebUI.click(findTestObject('Object Repository/ManageResidentPage/input_key_slot_edit_page'))
		WebUI.delay(1)
		String actualKeySlotInEditPage = WebUI.getText(findTestObject('Object Repository/ManageResidentPage/key_slot_first_preference'))
		WebUI.click(findTestObject('Object Repository/ManageResidentPage/key_slot_first_preference'))
		WebUI.delay(1)
		String updatedKeySlot = WebUI.getAttribute(findTestObject('Object Repository/ManageResidentPage/input_key_slot_edit_page'), "value")
		WebUI.verifyMatch(actualKeySlotInEditPage, updatedKeySlot, false, FailureHandling.CONTINUE_ON_FAILURE)


		//assign pickup runner
		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/dropdown_assign_runner'), 10)
		assert WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/dropdown_assign_runner')),"Assign Runner dropdown not visible on create reservation form"
		WebUI.selectOptionByLabel(findTestObject('Object Repository/ReservationPage/dropdown_assign_runner'), "Abhishek Anand" ,false)

		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/input_car_color'), 10)

		//click on update reservation button
		WebUI.delay(2)
		WebUI.click(findTestObject('Object Repository/ReservationPage/btn_create_reservation'))
		WebUI.delay(1)

		"Click on reservation card"
		res.clickonReservationCard(permitParkingData, section, customer)
		/*
		 String actualPickUpRunner = WebUI.getText(findTestObject('Object Repository/ReservationPage/Monthly/txt_pickup_runner'))
		 WebUI.verifyMatch(actualPickUpRunner, permitParkingData.get("Pick Up Runner"), false)
		 String actualDropOffRunner = WebUI.getText(findTestObject('Object Repository/ReservationPage/Monthly/txt_drop_off_runner'))
		 WebUI.verifyMatch(actualDropOffRunner, permitParkingData.get("Drop Off Runner"), false)
		 */
		String actualLocation = WebUI.getText(findTestObject('Object Repository/ReservationPage/Monthly/txt_location'))
		WebUI.verifyMatch(actualLocation, expectedParkingSpot, false)

		res.clickAtCoordinates(649,105)
		//WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))
		res.clickOnCustomerSection(customer)
	}

	@Keyword
	def makeCheckInReservation(def permitParkingData, String section) {
		"click on customerSection"
		res.clickOnCustomerSection("Monthly")

		"click on reservation card"
		res.clickonReservationCard(permitParkingData,section, "Monthly")

		"switch reservation checkin"
		WebUI.delay(1)
		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/Monthly/img_checkin'), 10)
		WebUI.click(findTestObject('Object Repository/ReservationPage/Monthly/img_checkin'))

		//verify toast message
		//res.verifyToastMessage("Reservation created")
		WebUI.waitForPageLoad(10)
		WebUI.scrollToPosition(0, 0)

		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/select_assign_runner'))
		WebUI.delay(1)
		WebUI.click(findTestObject('Object Repository/ReservationSummaryPage/option_add_pickup',["PickUpRunner" : permitParkingData.get("Pick Up Runner")]))

		//WebUI.selectOptionByLabel(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/select_assign_runner'), "Abhishek Sikarwar", false, FailureHandling.STOP_ON_FAILURE)
		WebUI.delay(1)
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/btn_submit'))

		res.verifyToastMessage("Reservation created")
		WebUI.delay(2)
	}
}