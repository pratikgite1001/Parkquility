package com.Parkquility.app.Keywords

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class Resident1Page {
	ReservationPage res= new ReservationPage()

	@Keyword
	def verifyCardDetailsInResidents(def residentData) {
		try {
			WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/Resident1/common_res_card_for_resident', ["Section" : "Hide", "LicensePlateNo" : residentData.get("License Plate Number")]),10)
		}catch(StepFailedException e) {
			WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/Resident1/common_res_card_for_resident', ["Section" : "Hide", "LicensePlateNo" : residentData.get("License Plate Number")]), 10)
		}

		//verify key tag on reservation card
		if(!(residentData.get("Key Tag").equals("NA"))) {
			String actualKeyTag = WebUI.getText(findTestObject('Object Repository/ReservationPage/Resident1/txt_key_tag_on_card', ["Section" : "Hide", "LicensePlateNo" : residentData.get("License Plate Number")]))
			WebUI.verifyMatch(actualKeyTag.trim(), residentData.get("Key Tag"), false)
		}

		//click on reservation card
		res.clickonReservationCard(residentData,"Hide", "Resident1")


		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_member_information'))

		//verify license plate number
		String actualLicensePlateNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/license_plate_no_for_resident'))
		WebUI.verifyMatch(actualLicensePlateNumber, residentData.get("License Plate Number"), false)

		//verify car make
		String actualCarMake = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_make'))
		WebUI.verifyMatch(actualCarMake, residentData.get("Car Make"), false)
		//verify car color
		String actualCarColor = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_color'))
		WebUI.verifyMatch(actualCarColor.toLowerCase(), residentData.get("Car Color").toString().toLowerCase(), false)

		//verify mobile number
		String actualMobileNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_mobile_number'))
		assert actualMobileNumber.contains(residentData.get("Mobile Number"))

		//verify key slot
		String actualKeySlot = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_key_slot'))
		WebUI.verifyMatch(actualKeySlot, residentData.get("Key Slot"), false)

		//verify spot number
		String actualSpotNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_spot_number'))
		//WebUI.verifyMatch(actualSpotNumber, residentData.get(""), false)

		//verify name
		String actualName = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_name'))
		String expectedName = residentData.get("First Name")+" "+residentData.get("Last Name")
		WebUI.verifyMatch(actualName, expectedName, false)

		//verify unit number
		if(!(residentData.get("Unit Number").equals("NA"))) {
			String actualUnitNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_unit_number'))
			WebUI.verifyMatch(actualUnitNumber, residentData.get("Unit Number"), false)
		}

		"verify expiry date"
		if(!(GlobalVariable.expiryDate.equals(""))) {
			String actualExpiryDate = WebUI.getText(findTestObject('Object Repository/ManageResidentPage/txt_expiry_date'), FailureHandling.CONTINUE_ON_FAILURE)
			assert actualExpiryDate.contains(GlobalVariable.expiryDate)
		}

		//WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/btn_validation_history'), 10)

		//WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/btn_validation_history'))
		//WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/btn_login_history'))
		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_checkin_for_resident'))
		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_add_damage'))

		/*
		 WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/btn_validation_history'))
		 String actname1 = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_name'))
		 WebUI.verifyMatch(actualName, actname1, false)
		 WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/btn_back'))
		 WebUI.delay(1)
		 WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/btn_login_history'))
		 String actname2 = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_name'))
		 WebUI.verifyMatch(actualName, actname2, false)
		 WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/btn_back'))
		 WebUI.delay(1)
		 */

		res.clickAtCoordinates(649,105)
		//WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

		"click on customerSection"
		res.clickOnCustomerSection("Resident1")
	}

	@Keyword
	def verifyCarInfo(def residentData, String section) {
		//click on customer Section
		res.clickOnCustomerSection("Resident1")

		String expectedKeyOnCard


		boolean kk
		if(section.equals("Ongoing")) {
			kk = WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/section_ongoing'), 10, FailureHandling.OPTIONAL)
		}
		else if(section.equals("Car Requested")) {
			kk = WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/section_car_requested'), 10, FailureHandling.OPTIONAL)
		}

		if(kk) {
			//get key number for validate
			WebUI.delay(1)
			expectedKeyOnCard = WebUI.getText(findTestObject('Object Repository/ReservationPage/Resident1/txt_key_tag_on_card', ["Section" : section, "LicensePlateNo" : residentData.get("License Plate Number")]))

			//verify key tag
			if(!(residentData.get("Key Tag").equals("NA"))) {
				String actualKeyTag = WebUI.getText(findTestObject('Object Repository/ReservationPage/Resident1/txt_key_tag_in_ongoing_card', ["Section" : section, "LicensePlateNo" : residentData.get("License Plate Number")]))
				WebUI.verifyMatch(actualKeyTag.trim(), residentData.get("Key Tag"), false)
			}

			//verify unit number
			if(!(residentData.get("Unit Number").equals("NA"))) {
				//String actualUnitNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/Resident1/txt_unit_number_on_card', ["Section" : section, "LicensePlateNo" : residentData.get("License Plate Number")]))
				//WebUI.verifyMatch(actualUnitNumber.trim(), residentData.get("Unit Number"), false)
			}
		}else {
			WebUI.delay(1)
			//get key number for validate
			expectedKeyOnCard = WebUI.getText(findTestObject('Object Repository/ReservationPage/Resident1/txt_key_on_card_without_section', ["LicensePlateNo" : residentData.get("License Plate Number")]))

			//verify key tag
			if(!(residentData.get("Key Tag").equals("NA"))) {
				String actualKeyTag = WebUI.getText(findTestObject('Object Repository/ReservationPage/Resident1/txt_key_tag_on_card_without_section', ["LicensePlateNo" : residentData.get("License Plate Number")]))
				WebUI.verifyMatch(actualKeyTag.trim(), residentData.get("Key Tag"), false)
			}

			//verify unit number
			if(!(residentData.get("Unit Number").equals("NA"))) {
				//String actualUnitNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/Resident1/txt_unit_no_on_card_without_section', ["LicensePlateNo" : residentData.get("License Plate Number")]))
				//WebUI.verifyMatch(actualUnitNumber.trim(), residentData.get("Unit Number"), false)
			}
		}



		//click on reservation card
		res.clickonReservationCard(residentData,section, "Resident1")

		//verify car information text is visible
		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_car_information'))

		//verify key number
		String actualKey = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_key'))
		WebUI.verifyMatch(actualKey, expectedKeyOnCard, false)

		//verify License plate number
		String actualLicensePlateNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_license_Plate_no'))
		WebUI.verifyMatch(actualLicensePlateNumber, residentData.get("License Plate Number"), false)

		//verify car make
		String actualCarMake = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_make'))
		WebUI.verifyMatch(actualCarMake, residentData.get("Car Make"), false)

		//verify car color
		String actualCarColor = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_color'))
		WebUI.verifyMatch(actualCarColor.toLowerCase(), residentData.get("Car Color").toString().toLowerCase(), false)

		//verify mobile number
		String actualMobileNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_mobile_number'))
		assert actualMobileNumber.contains(residentData.get("Mobile Number"))

		//String actualKeySlot = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_key_slot'))
		//WebUI.verifyMatch(actualKeySlot, residentData.get("Key Slot"), false)
		//String actualSpotNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_spot_number'))
		//WebUI.verifyMatch(actualSpotNumber, residentData.get(""), false)

		//verify name
		String actualName = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_name'))
		String expectedName = residentData.get("First Name")+" "+residentData.get("Last Name")
		WebUI.verifyMatch(actualName, expectedName, false)

		//verify unit number
		if(!(residentData.get("Unit Number").equals("NA"))) {
			String actualUnitNumber = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_unit_number'))
			WebUI.verifyMatch(actualUnitNumber, residentData.get("Unit Number"), false)
		}

		//verify start time and parking duration visible
		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_start_time'))
		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_parking_duration'))

		//for car request section request time and request duration should visible
		if(section.equals("Car Requested")) {
			WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_request_time'))
			WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_request_duration'))
		}

		res.clickAtCoordinates(649,105)
		//WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))
		res.clickOnResCategoryButton("Resident1")
	}





	@Keyword
	def verifyCheckoutFeatureInResident(def residentData) {
		//click on customer section
		res.clickOnResCategoryButton("Resident1")

		//click on reservation card
		res.clickonReservationCard(residentData,"Car Requested", "Resident1")

		assert WebUI.verifyElementClickable(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_dropdown_menu'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/img_checkout'))

		res.verifyToastMessage("Car Checkout Success")
		WebUI.waitForPageLoad(10)

		//click on reservation card
		res.clickonReservationCard(residentData,"Hide", "Resident1")
	}


	@Keyword
	def makeCheckInReservation(def residentData, String section) {
		"click on customerSection"
		res.clickOnCustomerSection("Resident1")

		"click on reservation card"
		res.clickonReservationCard(residentData,section, "Resident1")

		"switch reservation checkin"
		WebUI.delay(1)
		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/Monthly/img_checkin'), 10)
		WebUI.click(findTestObject('Object Repository/ReservationPage/Monthly/img_checkin'))

		//verify toast message
		//res.verifyToastMessage("Reservation created")

		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/select_assign_runner'))
		WebUI.delay(1)
		WebUI.click(findTestObject('Object Repository/ReservationSummaryPage/option_add_pickup',["PickUpRunner" : residentData.get("Pick Up Runner")]))

		//WebUI.selectOptionByLabel(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/select_assign_runner'), "Abhishek Sikarwar", false, FailureHandling.STOP_ON_FAILURE)
		WebUI.delay(1)
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/btn_submit'))

		res.verifyToastMessage("Reservation created")
		WebUI.waitForPageLoad(10)
		WebUI.scrollToPosition(0, 0)
	}

	@Keyword
	def verifyDeleteFeature(def residentData) {
		//click on manage resident link
		WebUI.click(findTestObject('Object Repository/HomePage/link_resident'))
		WebUI.waitForPageLoad(10)

		WebUI.click(findTestObject('Object Repository/ManageResidentPage/option_deactivate',["LicensePlateNo" : residentData.get("License Plate Number")]))
		WebUI.delay(1)
		WebUI.verifyElementNotPresent(findTestObject('Object Repository/ManageResidentPage/option_deactivate',["LicensePlateNo" : residentData.get("License Plate Number")]), 10)
		WebUI.verifyElementPresent(findTestObject('Object Repository/ManageResidentPage/option_reactivate',["LicensePlateNo" : residentData.get("License Plate Number")]), 10)
		WebUI.verifyElementPresent(findTestObject('Object Repository/ManageResidentPage/option_delete',["LicensePlateNo" : residentData.get("License Plate Number")]), 10)


		//click on delete icon
		WebUI.click(findTestObject('Object Repository/ManageResidentPage/option_delete',["LicensePlateNo" : residentData.get("License Plate Number")]))
		WebUI.delay(1)
		WebUI.waitForPageLoad(10)
		WebUI.click(findTestObject('Object Repository/ManageResidentPage/btn_cancel_confirm'))
		WebUI.waitForPageLoad(10)
		WebUI.click(findTestObject('Object Repository/ManageResidentPage/option_delete',["LicensePlateNo" : residentData.get("License Plate Number")]))
		WebUI.waitForPageLoad(10)
		WebUI.click(findTestObject('Object Repository/ManageResidentPage/btn_yes_confirm'))

		res.verifyToastMessage("Successfully deleted")

		WebUI.waitForPageLoad(10)
		WebUI.verifyElementNotPresent(findTestObject('Object Repository/ManageResidentPage/txt_license_plate_number',["LicensePlateNo" : residentData.get("License Plate Number")]), 10)
	}

	@Keyword
	def verifyReactivateFeature(def residentData) {
		//click on rmanage resident link
		WebUI.click(findTestObject('Object Repository/HomePage/link_resident'))
		WebUI.waitForPageLoad(10)
		
		WebUI.delay(2)
		"enter floor and unit number"
		if(!(residentData.get("Floor Number").equals("N/A"))) {
			WebUI.scrollToPosition(0, 0)
			WebUI.click(findTestObject('Object Repository/ManageResidentPage/btn_floor', ["FloorNumber" : residentData.get("Floor Number")]))

			WebUI.click(findTestObject('Object Repository/ManageResidentPage/btn_unit_number', ["UnitNumber" : residentData.get("Unit Number")]))
		}

		WebUI.click(findTestObject('Object Repository/ManageResidentPage/option_reactivate',["LicensePlateNo" : residentData.get("License Plate Number")]))

		res.verifyToastMessage("Successfully updated")

		WebUI.verifyElementNotPresent(findTestObject('Object Repository/ManageResidentPage/option_delete',["LicensePlateNo" : residentData.get("License Plate Number")]), 10)
		WebUI.verifyElementNotPresent(findTestObject('Object Repository/ManageResidentPage/option_reactivate',["LicensePlateNo" : residentData.get("License Plate Number")]), 10)
		WebUI.verifyElementPresent(findTestObject('Object Repository/ManageResidentPage/option_deactivate',["LicensePlateNo" : residentData.get("License Plate Number")]), 10)

		"verify reactivated reservation is  seen in resident 1 page"

		//click on reservation link
		WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

		//Click on resident button
		res.clickOnResCategoryButton("Resident1")

		WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/Resident1/common_res_card_for_resident', ["Section" : "Hide", "LicensePlateNo" : residentData.get("License Plate Number")]), 10)

		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/Resident1/common_res_card_for_resident', ["Section" : "Hide", "LicensePlateNo" : residentData.get("License Plate Number")]), 10)
		WebUI.click(findTestObject('Object Repository/ReservationPage/Resident1/common_res_card_for_resident', ["Section" : "Hide", "LicensePlateNo" : residentData.get("License Plate Number")]))
	}

	@Keyword
	def verifyDeacivateFeature(def residentData) {
		//click on manage resident link
		WebUI.click(findTestObject('Object Repository/HomePage/link_resident'))
		WebUI.waitForPageLoad(10)
		WebUI.delay(2)
		"enter floor and unit number"
		if(!(residentData.get("Floor Number").equals("N/A"))) {
			WebUI.scrollToPosition(0, 0)
			WebUI.click(findTestObject('Object Repository/ManageResidentPage/btn_floor', ["FloorNumber" : residentData.get("Floor Number")]))

			WebUI.click(findTestObject('Object Repository/ManageResidentPage/btn_unit_number', ["UnitNumber" : residentData.get("Unit Number")]))
		}

		WebUI.delay(2)
		WebUI.click(findTestObject('Object Repository/ManageResidentPage/option_deactivate',["LicensePlateNo" : residentData.get("License Plate Number")]))

		res.verifyToastMessage("Successfully updated")
		WebUI.delay(1)

		WebUI.verifyElementNotPresent(findTestObject('Object Repository/ManageResidentPage/option_deactivate',["LicensePlateNo" : residentData.get("License Plate Number")]), 10)
		WebUI.verifyElementPresent(findTestObject('Object Repository/ManageResidentPage/option_reactivate',["LicensePlateNo" : residentData.get("License Plate Number")]), 10)
		WebUI.verifyElementPresent(findTestObject('Object Repository/ManageResidentPage/option_delete',["LicensePlateNo" : residentData.get("License Plate Number")]), 10)

		"verify deactivated reservation is not seen in resident 1 page"
		//click on reservation link
		WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

		//Click on resident button
		res.clickOnResCategoryButton("Resident1")

		WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/Resident1/common_res_card_for_resident', ["Section" : "Hide", "LicensePlateNo" : residentData.get("License Plate Number")]), 10)
	}

	@Keyword
	def createNewResident(def residentData) {
		"click on resident link"
		//WebUI.scrollToElement(findTestObject('Object Repository/HomePage/link_resident'), 10)
		WebUI.click(findTestObject('Object Repository/HomePage/link_resident'))

		"enter floor and unit number"
		if(!(residentData.get("Floor Number").equals("N/A"))) {
			WebUI.click(findTestObject('Object Repository/ManageResidentPage/btn_floor', ["FloorNumber" : residentData.get("Floor Number")]))

			WebUI.click(findTestObject('Object Repository/ManageResidentPage/btn_unit_number', ["UnitNumber" : residentData.get("Unit Number")]))
		}


		"enter first name"
		WebUI.setText(findTestObject('Object Repository/ManageResidentPage/input_first_name'), residentData.get('First Name'))

		"enter last name"
		WebUI.setText(findTestObject('Object Repository/ManageResidentPage/input_last_name'), residentData.get('Last Name'))

		"select country from dropdown"
		WebUI.click(findTestObject('Object Repository/ManageResidentPage/select_country_dropdown'))
		WebUI.setText(findTestObject('Object Repository/ManageResidentPage/input_country_name'), residentData.get('Country'))
		WebUI.click(findTestObject('Object Repository/ManageResidentPage/select_country_first_option'))

		"enter mobile number"
		WebUI.setText(findTestObject('Object Repository/ManageResidentPage/input_mobile_number'), residentData.get('Mobile Number'))

		"enter parking spot"
		WebUI.setText(findTestObject('Object Repository/ManageResidentPage/input_parking_spot'), residentData.get('Parking Spot'))

		"enter license plate number"
		WebUI.setText(findTestObject('Object Repository/ManageResidentPage/input_license_plate_number'), residentData.get('License Plate Number'))

		"enter car make"
		WebUI.click(findTestObject('Object Repository/ManageResidentPage/input_car_make'))
		WebUI.setText(findTestObject('Object Repository/ManageResidentPage/input_search_car_make'), residentData.get('Car Make'))
		WebUI.click(findTestObject('Object Repository/ManageResidentPage/car_make_first_option'))

		"choose car color"
		WebUI.click(findTestObject('Object Repository/ManageResidentPage/input_car_color'))
		WebUI.setText(findTestObject('Object Repository/ManageResidentPage/input_search_car_color'),residentData.get('Car Color') )
		WebUI.click(findTestObject('Object Repository/ManageResidentPage/first_option_car_color'))


		"select date"
		WebUI.click(findTestObject('Object Repository/ManageResidentPage/input_expiry_date'))
		WebUI.delay(1)
		GlobalVariable.expiryDate = WebUI.getText(findTestObject('Object Repository/ReservationPage/Guest/choose_date'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/Guest/choose_date'))


		"verify key slot and key tag not present"
		WebUI.verifyElementNotPresent(findTestObject('Object Repository/ManageResidentPage/input_key_slot'), 10)
		WebUI.verifyElementNotPresent(findTestObject('Object Repository/ManageResidentPage/input_key_tag'), 10)

		"click on vehicle sharing option"
		if (residentData.get('Vehicle Sharing').equals('Yes')) {
			WebUI.click(findTestObject('Object Repository/ManageResidentPage/checkbox_vehicle_sharing'))
		}




		"click on save button"
		WebUI.click(findTestObject('Object Repository/ManageResidentPage/btn_save'))

		res.verifyToastMessage("Successfully added")
	}

	@Keyword
	def verifyCreatedResidentDetails(def residentData) {
		"verify entered info is display on created resident list"
		WebUI.verifyElementPresent(findTestObject('Object Repository/ManageResidentPage/row_data_of_created_resident',  ["LicensePlateNo" : residentData.get('License Plate Number'), "Index" : "7"]), 10)

		ArrayList<String> arr = new ArrayList<String>(Arrays.asList("First Name", "Last Name", "Phone", "Expiry Date", "Key Slot", "Key Tag", "Parking Spot", "License Plate", "Make", "Color", "Vehicle Sharing", "Floor", "Unit"))
		HashMap<String, String> map= new HashMap<String, String>()

		for(int i=1;i<=14;i++){
			if(i==4) {
				continue
			}
			String header = WebUI.getText(findTestObject('Object Repository/ReportPage/header_list',["Index" : i]))
			String actualValue = WebUI.getText(findTestObject('Object Repository/ManageResidentPage/row_data_of_created_resident',  ["LicensePlateNo" : residentData.get('License Plate Number'), "Index" : i]))
			map.put(header, actualValue)
		}



		WebUI.verifyMatch(map.get("First Name"), residentData.get("First Name"), false, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(map.get("Last Name"), residentData.get("Last Name"), false, FailureHandling.CONTINUE_ON_FAILURE)
		assert map.get("Phone").contains(residentData.get("Mobile Number"))
		assert map.get("Expiry Date").contains(GlobalVariable.expiryDate)
		WebUI.verifyMatch(map.get("Parking Spot"), residentData.get("Parking Spot"), false, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(map.get("License Plate"), residentData.get("License Plate Number"), false, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(map.get("Make"), residentData.get("Car Make"), false, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(map.get("Color"), residentData.get("Car Color"), false, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(map.get("Vehicle Sharing"), residentData.get("Vehicle Sharing"), false, FailureHandling.CONTINUE_ON_FAILURE)


		if(GlobalVariable.isEditResident) {
			WebUI.verifyMatch(map.get("Key Slot"), residentData.get("Key Slot"), false, FailureHandling.CONTINUE_ON_FAILURE)
			WebUI.verifyMatch(map.get("Key Tag"), residentData.get("Key Tag"), false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			WebUI.verifyMatch(map.get("Key Slot"), "", false, FailureHandling.CONTINUE_ON_FAILURE)
			WebUI.verifyMatch(map.get("Key Tag"), "", false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		if(!(residentData.get("Floor Number").equals("N/A"))) {
			assert residentData.get("Floor Number").contains(map.get("Floor"))

			assert residentData.get("Unit Number").contains(map.get("Unit"))
		}

		"verify delete and reactive option not visible initially"
		WebUI.verifyElementNotPresent(findTestObject('Object Repository/ManageResidentPage/option_delete',["LicensePlateNo" : residentData.get("License Plate Number")]), 10)
		WebUI.verifyElementNotPresent(findTestObject('Object Repository/ManageResidentPage/option_reactivate',["LicensePlateNo" : residentData.get("License Plate Number")]), 10)
	}

	@Keyword
	def verifyEditFeature(def residentData) {
		WebUI.click(findTestObject('Object Repository/ManageResidentPage/option_edit',["LicensePlateNo" : residentData.get("License Plate Number")]))
		WebUI.delay(2)

		WebUI.click(findTestObject('Object Repository/ManageResidentPage/btn_cancel',["LicensePlateNo" : residentData.get("License Plate Number")]))
		WebUI.delay(2)

		WebUI.click(findTestObject('Object Repository/ManageResidentPage/option_edit',["LicensePlateNo" : residentData.get("License Plate Number")]))
		WebUI.delay(2)

		WebUI.verifyElementPresent(findTestObject('Object Repository/ManageResidentPage/input_key_slot'), 10)
		WebUI.verifyElementPresent(findTestObject('Object Repository/ManageResidentPage/input_key_tag'), 10)

		//choose key slot
		WebUI.click(findTestObject('Object Repository/ManageResidentPage/input_key_slot'))
		WebUI.click(findTestObject('Object Repository/ManageResidentPage/key_slot_option',["KeySlot" : residentData.get('Key Slot') ]))

		//enter key tag
		WebUI.setText(findTestObject('Object Repository/ManageResidentPage/input_key_tag'), residentData.get('Key Tag'))

		WebUI.delay(2)
		//click on update button
		WebUI.click(findTestObject('Object Repository/ManageResidentPage/btn_update',["LicensePlateNo" : residentData.get("License Plate Number")]))

		res.verifyToastMessage("Successfully updated")

		GlobalVariable.isEditResident=true
	}

	@Keyword
	def verifyEditReservationPageInResident(def customerData, String section, String customer) {
		//click on customer section
		res.clickOnCustomerSection(customer)

		//click on reservation card
		res.clickonReservationCard(customerData, section, customer)

		String expectedKeySlot = WebUI.getText(findTestObject('Object Repository/ManageResidentPage/txt_key_slot_from_booking_id'))

		String expectedLocation
		String expLocation = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_location'))
		if(!(expLocation.equals("Location"))) {
			expectedLocation = expLocation
		}
		else {
			expectedLocation = ""
		}

		//click on edit icon
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/icon_edit'))
		WebUI.waitForElementVisible(findTestObject('Object Repository/ReservationPage/txt_edit_reservation'), 10)

		//verify Mobile Number
		String mobileNo = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/input_mobile_number'), "value")
		WebUI.verifyMatch(mobileNo, customerData.get("Mobile Number"), false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify key slot
		String actualKeySlot = WebUI.getAttribute(findTestObject('Object Repository/ManageResidentPage/input_key_slot_edit_page'), "value")
		WebUI.verifyMatch(actualKeySlot, expectedKeySlot, false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify License plate number
		String LicensePlateNo = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/input_license_plate_number'), "value")
		WebUI.verifyMatch(LicensePlateNo, customerData.get("License Plate Number"), false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify car make
		if(!(customerData.get("Make").equals("NA"))) {
			String carMake= WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/input_car_make'), "value")
			WebUI.verifyMatch(carMake, customerData.get("Car Make"), false, FailureHandling.CONTINUE_ON_FAILURE)
		}


		//verify car color
		if(!(customerData.get("Color").equals("NA"))) {
			String carColor= WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/input_car_color'), "value")
			WebUI.verifyMatch(carColor.toLowerCase(), customerData.get("Car Color").toString().toLowerCase(), false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify Location
		String actualLocation= WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/input_parking_spot'), "value")
		WebUI.verifyMatch(actualLocation, expectedLocation, false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify no damage selected
		if(customerData.get("Damage").equals("No")) {
			String noDamageselectedValue= WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/btn_no_damages'), "class")
			WebUI.verifyMatch(noDamageselectedValue, "btn btn-labeled btn-pink", false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify Add damage selected
		if(customerData.get("Damage").equals("Yes")) {
			String noDamageselectedValue= WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/btn_no_damages'), "class")
			WebUI.verifyMatch(noDamageselectedValue, "btn btn-labeled", false, FailureHandling.CONTINUE_ON_FAILURE)

			WebUI.verifyElementPresent(findTestObject('Object Repository/ReservationPage/txt_add_damages_uploded_photoes_number'), 10)
		}

		//verify first name
		if(!(customerData.get("First Name").equals("NA"))) {
			WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/input_first_name'), 10)
			String actualFirstName= WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/input_first_name'), "value")
			WebUI.verifyMatch(actualFirstName, customerData.get("First Name"), false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify last name
		if(!(customerData.get("Last Name").equals("NA"))) {
			WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/input_last_name'), 10)
			String actualLastName= WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/input_last_name'), "value")
			WebUI.verifyMatch(actualLastName, customerData.get("Last Name"), false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		WebUI.scrollToPosition(0, 0)

		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/btn_close_edit_res_page'))
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/btn_close_edit_res_page'))

		WebUI.waitForPageLoad(10)
	}

	@Keyword
	def updateinfoFromEditPageInResidentAndMonthlyAndVerify(def customerData, String section, String customer) {
		res.clickOnCustomerSection(customer)

		//click on reservation card
		res.clickonReservationCard(customerData, section, customer)

		//click on edit icon
		WebUI.click(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/icon_edit'))
		WebUI.waitForElementVisible(findTestObject('Object Repository/ReservationPage/txt_edit_reservation'), 10)

		//update key slot
		WebUI.click(findTestObject('Object Repository/ManageResidentPage/input_key_slot_edit_page'))
		WebUI.delay(1)
		String actualKeySlotInEditPage = WebUI.getText(findTestObject('Object Repository/ManageResidentPage/key_slot_first_preference'))
		WebUI.click(findTestObject('Object Repository/ManageResidentPage/key_slot_first_preference'))
		WebUI.delay(1)
		String updatedKeySlot = WebUI.getAttribute(findTestObject('Object Repository/ManageResidentPage/input_key_slot_edit_page'), "value")
		WebUI.verifyMatch(actualKeySlotInEditPage, updatedKeySlot, false, FailureHandling.CONTINUE_ON_FAILURE)

		//update parking spot
		WebUI.click(findTestObject('Object Repository/ManageResidentPage/input_parking_spot_edit_page'))
		String actualParkingSpotInEditPage = WebUI.getText(findTestObject('Object Repository/ManageResidentPage/key_slot_first_preference'))
		WebUI.click(findTestObject('Object Repository/ManageResidentPage/key_slot_first_preference'))
		WebUI.delay(1)
		String updatedParkingSpot =  WebUI.getAttribute(findTestObject('Object Repository/ManageResidentPage/input_parking_spot_edit_page'), "value")
		WebUI.verifyMatch(actualParkingSpotInEditPage, updatedParkingSpot, false, FailureHandling.CONTINUE_ON_FAILURE)
		/*		
		 //click on no damages button
		 if(customerData.get("Damage").equals("No")) {
		 String actualNoDamageClassValue = WebUI.getAttribute(findTestObject('Object Repository/ReservationPage/btn_no_damages'), "class")
		 if(actualNoDamageClassValue.equals("btn btn-labeled")) {
		 WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/btn_add_damages'))
		 WebUI.click(findTestObject('Object Repository/ReservationPage/btn_no_damages'))
		 }else if(actualNoDamageClassValue.equals("btn btn-labeled btn-pink")){
		 WebUI.verifyElementNotVisible(findTestObject('Object Repository/ReservationPage/btn_add_damages'))
		 }
		 }
		 */		
		//select pick up runner from dropdown
		if(!(customerData.get("Pick Up Runner").equals("NA"))) {
			WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationPage/dropdown_assign_runner'))
			WebUI.selectOptionByLabel(findTestObject('Object Repository/ReservationPage/dropdown_assign_runner'), customerData.get("Pick Up Runner") ,false)
		}

		//scroll to car color element
		WebUI.scrollToElement(findTestObject('Object Repository/ReservationPage/input_car_color'), 10)

		//click on create reservation button
		WebUI.waitForPageLoad(10)
		WebUI.click(findTestObject('Object Repository/ReservationPage/btn_create_reservation'))

		//res.verifyToastMessage("Reservation updated")
		WebUI.waitForPageLoad(10)

		//click on customer section
		res.clickOnCustomerSection(customer)

		//click on reservation card
		res.clickonReservationCard(customerData, section, customer)
		WebUI.delay(1)

		String actualKeySlot = WebUI.getText(findTestObject('Object Repository/ManageResidentPage/txt_key_slot_from_booking_id'))
		WebUI.verifyMatch(actualKeySlot, actualKeySlotInEditPage, false, FailureHandling.CONTINUE_ON_FAILURE)

		String expectedLocation1
		String expLocation = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_location'))
		if(!(expLocation.equals("Location"))) {
			expectedLocation1 = expLocation
		}
		else {
			expectedLocation1 = ""
		}
		WebUI.verifyMatch(expectedLocation1, actualParkingSpotInEditPage, false, FailureHandling.CONTINUE_ON_FAILURE)


		String actualPickupRunner1 = WebUI.getText(findTestObject('Object Repository/ReservationPage/BookedCarInfoPage/txt_pickup_runner'))
		WebUI.verifyMatch(actualPickupRunner1, customerData.get("Pick Up Runner"), false, FailureHandling.STOP_ON_FAILURE)

		WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

		//click on customer section
		res.clickOnCustomerSection(customer)
	}
}
