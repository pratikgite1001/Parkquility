package com.Parkquility.app.Keywords

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import java.text.SimpleDateFormat
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI



import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement

import com.Parkquility.app.common.CommonKeywords
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.json.JsonSlurper
import internal.GlobalVariable
public class ReportPage {
	ReservationPage res = new ReservationPage()
	CommonKeywords common= new CommonKeywords()
	def sdf = new SimpleDateFormat("MMM dd, yyyy, hh:mm a")
	def sdf1 = new SimpleDateFormat("hh:mm a")

	/*
	 * Verify Transaction Report
	 *
	 * */
	@Keyword
	def verifyTransactionReport(def customerData, String customer) {
		//div[@class="rt-thead -header"]/div/div[1]/div
		//div[text()="3487"]/../div[1]

		//click on transaction report link
		try {
			WebUI.verifyElementVisible(findTestObject('Object Repository/HomePage/link_transaction_report'), FailureHandling.OPTIONAL)
		}catch(StepFailedException e){
			WebUI.scrollToElement(findTestObject('Object Repository/HomePage/link_transaction_report'), 10)
		}
		WebUI.click(findTestObject('Object Repository/HomePage/link_transaction_report'))

		//get user full name
		WebUI.click(findTestObject('Object Repository/HomePage/header_username'))
		WebUI.delay(2)
		GlobalVariable.UserName = WebUI.getText(findTestObject('Object Repository/HomePage/txt_user_full_name'), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/HomePage/header_username'))

		//get lot name
		WebUI.delay(1)
		GlobalVariable.LotName = WebUI.getText(findTestObject('Object Repository/HomePage/txt_lot_name'))

		WebUI.scrollToElement(findTestObject('Object Repository/ReportPage/value_list',["BookingId" : GlobalVariable.ID, "Index" : "1"]), 10, FailureHandling.CONTINUE_ON_FAILURE)

		//get table data
		HashMap<String, String> map= new HashMap<String, String>()
		for(int i=1;i<=51;i++){
			String header = WebUI.getText(findTestObject('Object Repository/ReportPage/header_list',["Index" : i]))
			String actualValue = WebUI.getText(findTestObject('Object Repository/ReportPage/value_list',["BookingId" : GlobalVariable.ID, "Index" : i]))
			map.put(header, actualValue)
		}

		//WebUI.verifyMatch(, , false, FailureHandling.CONTINUE_ON_FAILURE)

		// verify booking Id
		WebUI.verifyMatch( map.get("ID"),GlobalVariable.ID, false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify lot Name
		WebUI.verifyMatch(map.get("Lot Name") ,GlobalVariable.LotName, false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify License plate number
		WebUI.verifyMatch(map.get("License Plate No."),customerData.get("License Plate Number") , false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify start time
		String actStartTime= common.increaseTimeByOneMinut(GlobalVariable.startTimeAndDate)
		/*	if(map.get("Start Time").equals(GlobalVariable.startTimeAndDate) || map.get("Start Time").equals(actStartTime)) {
		 //assert true
		 KeywordUtil.logInfo("The condition is true as expected."+map.get("Start Time")+" = "+GlobalVariable.startTimeAndDate+" || "+actStartTime)
		 }else {
		 //assert false
		 KeywordUtil.logInfo("The condition should be false, but it is true."+map.get("Start Time")+" = "+GlobalVariable.startTimeAndDate+" || "+actStartTime)
		 WebUI.comment("Marking test as failed.")
		 KeywordUtil.markFailed("Condition is not met.")
		 }
		 */


		//WebUI.verifyMatch(map.get("Start Time").toLowerCase(), GlobalVariable.startTime.toString().toLowerCase(), false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify end time
		//WebUI.verifyMatch(map.get("End Time").toLowerCase(), GlobalVariable.endTimeAndDate.toString().toLowerCase(), false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify status
		if(GlobalVariable.CheckoutStatus) {
			WebUI.verifyMatch(map.get("Status"), "completed", false, FailureHandling.CONTINUE_ON_FAILURE)
		}else if(GlobalVariable.cancelReservationStatus) {
			WebUI.verifyMatch(map.get("Status"), "cancelled", false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		// verify parking/valet fee
		if(GlobalVariable.paidAmount.equals("")) {
			WebUI.verifyMatch(map.get("Valet Fee"),"\$0" , false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			String gg= GlobalVariable.parkingFee.toString().replaceAll("\\.00", "")
			WebUI.verifyMatch(map.get("Valet Fee"),gg , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify net payable
		if(GlobalVariable.paidAmount.equals("")) {
			WebUI.verifyMatch(map.get("Net Payment"),"\$0" , false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			String gg= GlobalVariable.paidAmount.toString().replaceAll("\\.00", "")
			WebUI.verifyMatch(map.get("Net Payment"),gg , false, FailureHandling.CONTINUE_ON_FAILURE)
		}


		//verify discount amount
		//pending

		//verify pay status
		WebUI.verifyMatch(map.get("Payment Status"), GlobalVariable.payStatus , false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify tip amount
		if(!(customerData.get("Optional Amount").equals("NA"))) {
			//WebUI.verifyMatch(map.get("Tip Amount"),GlobalVariable.exceptionalTipAmount, false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			if(GlobalVariable.paymentType.equals("Card") && (!(GlobalVariable.tipAmount.equals("\$0.00")) &&  !(GlobalVariable.tipAmount.equals("")))) {
				WebUI.verifyMatch(map.get("Tip Amount"),GlobalVariable.tipAmount.toString().replaceAll("\\.00", "") , false, FailureHandling.CONTINUE_ON_FAILURE)
			}else {
				WebUI.verifyMatch(map.get("Tip Amount"),"\$0" , false, FailureHandling.CONTINUE_ON_FAILURE)
			}
		}

		//verify payment type
		if(GlobalVariable.paymentType.equals("")) {
			WebUI.verifyMatch(map.get("Payment Type"), "Card", false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			WebUI.verifyMatch(map.get("Payment Type"), GlobalVariable.paymentType, false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify special price
		if(!(customerData.get("Category").equals("NA"))) {
			WebUI.verifyMatch(map.get("Special Price").toString().toLowerCase(), customerData.get("Category"), false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify mobile number
		if(!(customerData.get("Mobile Number").equals("NA"))) {
			WebUI.verifyMatch(map.get("Contact Number"), customerData.get("Mobile Number") , false, FailureHandling.CONTINUE_ON_FAILURE)
		}


		if(!(customerData.get("First Name").equals("NA"))) {
			WebUI.verifyMatch(map.get("First Name"), customerData.get("First Name"), false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		if(!(customerData.get("Last Name").equals("NA"))) {
			WebUI.verifyMatch(map.get("Last Name"), customerData.get("Last Name"), false, FailureHandling.CONTINUE_ON_FAILURE)
		}


		//verify signature
		//pending


		//verify Convenience fee
		String ddd= GlobalVariable.parkingFee.toString().replaceAll("\\\$","").trim()
		if(GlobalVariable.paymentType.equals("Card") && !(ddd.equals("0.00"))) {
			def conveniencefee11 =GlobalVariable.convenienceFee
			def numericValue = conveniencefee11.toString().replaceAll("\\\$", "").toDouble()
			def formattedPrice = "\$" + String.format("%.1f", numericValue)
			WebUI.verifyMatch(map.get("Convenience Fee"), formattedPrice, false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify color
		if(!(customerData.get("Color").equals("NA"))) {
			WebUI.verifyMatch(map.get("Vehicle Color").toString().toLowerCase(),customerData.get("Color").toString().toLowerCase() , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify make
		if(!(customerData.get("Make").equals("NA"))) {
			WebUI.verifyMatch(map.get("Vehicle Make").toString().toLowerCase(),customerData.get("Make").toString().toLowerCase() , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify model
		if(!(customerData.get("Model").equals("NA"))) {
			WebUI.verifyMatch(map.get("Vehicle Model").toString().toLowerCase(),customerData.get("Model").toString().toLowerCase() , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify keyboard slot
		WebUI.verifyMatch(map.get("Keyboard Slot"), GlobalVariable.keyboardSlot , false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify country code
		WebUI.verifyMatch(map.get("Country Code"),"+91" , false, FailureHandling.CONTINUE_ON_FAILURE)


		//verify Email
		if(!(customerData.get("Email").equals("NA"))) {
			WebUI.verifyMatch(map.get("Email"),customerData.get("Email") , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//VERIFY CAR LOCATION here hardcoded value as Kotul
		if(GlobalVariable.carLocationStatus) {
			WebUI.verifyMatch(map.get("Car Location"), "Kotul", false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			WebUI.verifyMatch(map.get("Car Location"), "", false, FailureHandling.CONTINUE_ON_FAILURE)
		}


		//verify coupon code
		//pending


		//verify Damages
		//pending

		//verify car requested status
		if(GlobalVariable.requestCarStatus) {
			WebUI.verifyMatch(map.get("Car Requested"), "Yes" , false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			WebUI.verifyMatch(map.get("Car Requested"), "No" , false, FailureHandling.CONTINUE_ON_FAILURE)
		}


		//verify free parking
		//pending

		//verify Check-In by
		WebUI.verifyMatch(map.get("Check-in By"), GlobalVariable.UserName, false, FailureHandling.CONTINUE_ON_FAILURE)


		//verify CheckOut by
		if(GlobalVariable.CheckoutStatus) {
			WebUI.verifyMatch(map.get("Checkout By"), GlobalVariable.UserName, false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			WebUI.verifyMatch(map.get("Checkout By"), "NA", false, FailureHandling.CONTINUE_ON_FAILURE)
		}



		//verify Drop Off Runner
		if(GlobalVariable.DropOffRunner) {
			WebUI.verifyMatch(map.get("Drop Off Runner"),customerData.get("Drop Off Runner") , false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			WebUI.verifyMatch(map.get("Drop Off Runner"),"" , false, FailureHandling.CONTINUE_ON_FAILURE)
		}


		//verify pickUp Runner
		if(GlobalVariable.pickUpRunner) {
			WebUI.verifyMatch(map.get("Pick Up Runner"),customerData.get("Pick Up Runner") , false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			WebUI.verifyMatch(map.get("Pick Up Runner"),"" , false, FailureHandling.CONTINUE_ON_FAILURE)
		}


		//verify cancellation reason
		if(GlobalVariable.cancelReservationStatus) {
			WebUI.verifyMatch(map.get("Cancellation Reason"), GlobalVariable.cancellationReason, false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			WebUI.verifyMatch(map.get("Cancellation Reason"), "", false, FailureHandling.CONTINUE_ON_FAILURE)
		}



		//WebUI.verifyMatch(, , false, FailureHandling.CONTINUE_ON_FAILURE)
	}

	/*
	 * Verify Day Report
	 *
	 * */
	@Keyword
	def verifyDayReport(def customerData, String customer) {
		//div[@class="rt-thead -header"]/div/div[1]/div
		//div[text()="3487"]/../div[1]

		//click on day report link
		try {
			WebUI.verifyElementVisible(findTestObject('Object Repository/HomePage/link_day_report'), FailureHandling.OPTIONAL)
		}catch(StepFailedException e){
			WebUI.scrollToElement(findTestObject('Object Repository/HomePage/link_day_report'), 10)
		}
		WebUI.click(findTestObject('Object Repository/HomePage/link_day_report'))

		//get user full name
		WebUI.click(findTestObject('Object Repository/HomePage/header_username'))
		WebUI.delay(2)
		GlobalVariable.UserName = WebUI.getText(findTestObject('Object Repository/HomePage/txt_user_full_name'), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/HomePage/header_username'))


		//click on customer section
		String actualSelectedBtnColor = WebUI.getAttribute(findTestObject('Object Repository/ReportPage/btn_customer',["Customer" : customer]), "class")
		if(actualSelectedBtnColor.equals("common-button-square")) {
			WebUI.click(findTestObject('Object Repository/ReportPage/btn_customer',["Customer" : customer]))
		}

		WebUI.scrollToElement(findTestObject('Object Repository/ReportPage/value_list',["BookingId" : GlobalVariable.ID, "Index" : "1"]), 10, FailureHandling.CONTINUE_ON_FAILURE)


		//get table data
		HashMap<String, String> map= new HashMap<String, String>()
		for(int i=1;i<=23;i++){
			String header = WebUI.getText(findTestObject('Object Repository/ReportPage/header_list',["Index" : i]))
			String actualValue = WebUI.getText(findTestObject('Object Repository/ReportPage/value_list',["BookingId" : GlobalVariable.ID, "Index" : i]))
			map.put(header, actualValue)
		}

		// verify booking Id
		WebUI.verifyMatch(GlobalVariable.ID, map.get("Booking Id"), false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify name
		if(!(customerData.get("First Name").equals("NA")) || !(customerData.get("Last Name").equals("NA"))) {
			String actualName = customerData.get("First Name")+" "+customerData.get("Last Name")
			WebUI.verifyMatch(actualName,map.get("Name") , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify Room Number
		if(!(customerData.get("Room No").equals("NA"))) {
			WebUI.verifyMatch(map.get("Room No."),customerData.get("Room No") , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify Customer Type
		if(!(customerData.get("Customer").equals("NA"))) {
			WebUI.verifyMatch(map.get("Type"),customerData.get("Customer Type") , false, FailureHandling.CONTINUE_ON_FAILURE)
		}


		if(GlobalVariable.CheckoutStatus) {
			WebUI.verifyMatch(map.get("Status"),"Completed" , false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			WebUI.verifyMatch(map.get("Status"), "Ongoing", false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify checkIn Date
		WebUI.verifyMatch(map.get("Check-In Date"), GlobalVariable.CheckInDate, false, FailureHandling.CONTINUE_ON_FAILURE)


		//verifyCheckIn time
		WebUI.verifyMatch(map.get("Check-In Time").toLowerCase(),GlobalVariable.CheckInTime.toString().toLowerCase() , false, FailureHandling.CONTINUE_ON_FAILURE)


		//verify checkOut Date
		if(GlobalVariable.CheckoutStatus) {
			boolean result = Math.abs(sdf1.parse(map.get("Check-Out Date")).time - sdf1.parse(GlobalVariable.CheckOutDate.toString()).time) <= 60_000
			WebUI.verifyEqual(result, true, FailureHandling.CONTINUE_ON_FAILURE)
			
			WebUI.verifyMatch(map.get("Check-Out Date"),GlobalVariable.CheckOutDate , false, FailureHandling.OPTIONAL)
		}else {
			WebUI.verifyMatch(map.get("Check-Out Date"),"" , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify checkOut time
		if(GlobalVariable.CheckoutStatus) {
			WebUI.verifyMatch(map.get("Check-Out Time").toLowerCase(),GlobalVariable.CheckOutTime.toString().toLowerCase(), false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			WebUI.verifyMatch(map.get("Check-Out Date"),"" , false, FailureHandling.CONTINUE_ON_FAILURE)
		}



		// verify parking/valet fee
		if(GlobalVariable.paidAmount.equals("")) {
			WebUI.verifyMatch(map.get("Valet Fee"),"\$0" , false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			String gg= GlobalVariable.parkingFee.toString().replaceAll("\\.00", "")
			WebUI.verifyMatch(map.get("Valet Fee"),gg , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify net payable
		if(GlobalVariable.paidAmount.equals("")) {
			WebUI.verifyMatch(map.get("Net Payment"),"\$0" , false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			String gg= GlobalVariable.paidAmount.toString().replaceAll("\\.00", "")
			WebUI.verifyMatch(map.get("Net Payment"),gg , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify tip amount
		if(GlobalVariable.paymentType.equals("Card") && (!(GlobalVariable.tipAmount.equals("\$0.00")) &&  !(GlobalVariable.tipAmount.equals("")))) {
			WebUI.verifyMatch(map.get("Tip Amount"),GlobalVariable.tipAmount.toString().toString().replaceAll("\\.00", "") , false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			WebUI.verifyMatch(map.get("Tip Amount"),"\$0" , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify payment type
		if(!(GlobalVariable.paymentType.equals(""))) {
			WebUI.verifyMatch(map.get("Payment Type"),GlobalVariable.paymentType , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify checked-In by
		WebUI.verifyMatch(map.get("Check-In By"),GlobalVariable.UserName , false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify check-OutBy
		if(GlobalVariable.CheckoutStatus) {
			WebUI.verifyMatch(map.get("Check-Out By"),GlobalVariable.UserName , false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			WebUI.verifyMatch(map.get("Check-Out By"),"NA" , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify pickUp Runner
		if(GlobalVariable.pickUpRunner) {
			WebUI.verifyMatch(map.get("Pick Up Runner"),customerData.get("Pick Up Runner") , false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			WebUI.verifyMatch(map.get("Pick Up Runner"),"" , false, FailureHandling.CONTINUE_ON_FAILURE)
		}
		//verify Drop Off Runner
		if(GlobalVariable.DropOffRunner) {
			WebUI.verifyMatch(map.get("Drop Off Runner"),customerData.get("Drop Off Runner") , false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			WebUI.verifyMatch(map.get("Drop Off Runner"),"" , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify make
		if(!(customerData.get("Make").equals("NA"))) {
			WebUI.verifyMatch(map.get("Make"),customerData.get("Make") , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify model
		if(!(customerData.get("Model").equals("NA"))) {
			WebUI.verifyMatch(map.get("Model"),customerData.get("Model") , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify color
		if(!(customerData.get("Make").equals("NA"))) {
			WebUI.verifyMatch(map.get("Color"),customerData.get("Color") , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//click on reservation link
		WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

		//click on customer section
		res.clickOnCustomerSection(customer)
	}

	/*
	 * Verify In and Out Report
	 * 
	 * */
	@Keyword
	def verifyInAndOutReport(def customerData, String customer) {
		//div[@class="rt-thead -header"]/div/div[1]/div
		//div[text()="3487"]/../div[1]

		//click on
		try {
			WebUI.verifyElementVisible(findTestObject('Object Repository/HomePage/link_in_and_out_report'), FailureHandling.OPTIONAL)
		}catch(StepFailedException e){
			WebUI.scrollToElement(findTestObject('Object Repository/HomePage/link_in_and_out_report'), 10)
		}
		WebUI.click(findTestObject('Object Repository/HomePage/link_in_and_out_report'))

		//get user full name
		WebUI.click(findTestObject('Object Repository/HomePage/header_username'))
		WebUI.delay(2)
		GlobalVariable.UserName = WebUI.getText(findTestObject('Object Repository/HomePage/txt_user_full_name'), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/HomePage/header_username'))


		WebUI.scrollToElement(findTestObject('Object Repository/ReportPage/value_list',["BookingId" : GlobalVariable.ID, "Index" : "1"]), 10, FailureHandling.CONTINUE_ON_FAILURE)

		//get table data
		HashMap<String, String> map= new HashMap<String, String>()
		for(int i=1;i<=12;i++){
			String header = WebUI.getText(findTestObject('Object Repository/ReportPage/header_list',["Index" : i]))
			String actualValue = WebUI.getText(findTestObject('Object Repository/ReportPage/value_list',["BookingId" : GlobalVariable.ID, "Index" : i]))
			map.put(header, actualValue)
		}

		//verify lot Name
		//WebUI.verifyMatch(, , false, FailureHandling.CONTINUE_ON_FAILURE)

		// verify booking Id
		WebUI.verifyMatch(GlobalVariable.ID, map.get("Booking Id"), false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify name
		if(!(customerData.get("First Name").equals("NA")) || !(customerData.get("Last Name").equals("NA"))) {
			String actualName = customerData.get("First Name")+" "+customerData.get("Last Name")
			WebUI.verifyMatch(actualName,map.get("Name") , false, FailureHandling.CONTINUE_ON_FAILURE)
		}


		//verify In Time
		WebUI.verifyMatch(map.get("In").toLowerCase(),GlobalVariable.InTime.toString().toLowerCase() , false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify Out Time
		if(GlobalVariable.OutStatus) {
			WebUI.verifyMatch(map.get("Out").toLowerCase(),GlobalVariable.OutTime.toString().toLowerCase() , false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			WebUI.verifyMatch(map.get("Out").toLowerCase(),"" , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify In By
		WebUI.verifyMatch(map.get("In By"),GlobalVariable.UserName , false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify Out By
		if(GlobalVariable.OutStatus) {
			WebUI.verifyMatch(map.get("Out By"),GlobalVariable.UserName , false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			WebUI.verifyMatch(map.get("Out"),"" , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify car Request time
		if(GlobalVariable.requestCarStatus) {
			WebUI.verifyMatch(map.get("Car Request Time").toLowerCase(),GlobalVariable.CarRequestTime.toString().toLowerCase()  , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify make
		if(!(customerData.get("Make").equals("NA"))) {
			WebUI.verifyMatch(map.get("Make"),customerData.get("Make") , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify model
		if(!(customerData.get("Model").equals("NA"))) {
			WebUI.verifyMatch(map.get("Model"),customerData.get("Model") , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify color
		if(!(customerData.get("Color").equals("NA"))) {
			WebUI.verifyMatch(map.get("Color"),customerData.get("Color") , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//click on reservation link
		WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

		//click on customer section
		res.clickOnCustomerSection(customer)
	}


	/*
	 * Verify Tip Report
	 * 
	 * */
	@Keyword
	def verifyTipReport(def customerData) {
		//div[@class="rt-thead -header"]/div/div[1]/div
		//div[text()="3487"]/../div[1]

		//click on day report link
		try {
			WebUI.verifyElementVisible(findTestObject('Object Repository/HomePage/link_tip_report'), FailureHandling.OPTIONAL)
		}catch(StepFailedException e){
			WebUI.scrollToElement(findTestObject('Object Repository/HomePage/link_tip_report'), 10)
		}
		WebUI.click(findTestObject('Object Repository/HomePage/link_tip_report'))


		//get table data
		HashMap<String, String> map= new HashMap<String, String>()
		for(int i=1;i<=8;i++){
			String header = WebUI.getText(findTestObject('Object Repository/ReportPage/header_list',["Index" : i]))
			String actualValue = WebUI.getText(findTestObject('Object Repository/ReportPage/value_list',["BookingId" : GlobalVariable.ID, "Index" : i]))
			map.put(header, actualValue)
		}

		//verify lot Name
		//WebUI.verifyMatch(, , false, FailureHandling.CONTINUE_ON_FAILURE)

		// verify booking Id
		WebUI.verifyMatch(GlobalVariable.ID, map.get("Valet Booking Id"), false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify tip amount
		if(GlobalVariable.paymentType.equals("Card") && (!(GlobalVariable.tipAmount.equals("\$0.00")) &&  !(GlobalVariable.tipAmount.equals("")))) {
			String aa =GlobalVariable.tipAmount.toString().replace("\$", "")
			if(!(aa.contains(".00"))) {
				aa=aa+".00"
			}
			aa="\$"+aa
			WebUI.verifyMatch(map.get("Tip Amount"),aa , false, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			WebUI.verifyMatch(map.get("Tip Amount"),"\$0" , false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		//verify Paid Date and Time
		if(GlobalVariable.requestCarStatus) {
			
			boolean result = Math.abs(sdf.parse(map.get("Paid Date & Time").toLowerCase()).time - sdf.parse(GlobalVariable.CarRequestTime.toString().toLowerCase()).time) <= 60_000

			WebUI.verifyEqual(result, true, FailureHandling.CONTINUE_ON_FAILURE)	
				
			WebUI.verifyMatch(map.get("Paid Date & Time").toLowerCase() ,GlobalVariable.CarRequestTime.toString().toLowerCase() , false, FailureHandling.OPTIONAL)
		}

		//verify customer type
		WebUI.verifyMatch(map.get("Customer Type"),customerData.get("Customer Type") , false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify Unit Number
		if(!(customerData.get("Unit Number").equals("NA"))) {
			WebUI.verifyMatch(map.get("Unit Number"),customerData.get("Unit Number") , false, FailureHandling.CONTINUE_ON_FAILURE)
		}else{
			WebUI.verifyMatch(map.get("Unit Number"),"" , false, FailureHandling.CONTINUE_ON_FAILURE)
		}
	}


	/*
	 * valet web payment for reservations
	 * */
	@Keyword
	def makeValetWebPayment(def customerData, def paymentData, String section, String customer) {
		//click on customer section
		res.clickOnCustomerSection(customer)

		//click on reservation card
		res.clickonReservationCard(customerData, section, customer)
		WebUI.delay(1)

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



		WebUI.sendKeys(null, Keys.chord(Keys.CONTROL, 't'))
		WebUI.delay(2)
		String newTabTitle = WebUI.getWindowTitle()
		WebUI.switchToWindowTitle(newTabTitle)
		WebUI.navigateToUrl("https://stagingvalet.parkquility.com/?id="+GlobalVariable.hashCode)

		WebUI.delay(1)


		String txt= WebUI.getText(findTestObject('Object Repository/PaymentPage/btn_pay_and_request_your_car_web_valet'), FailureHandling.OPTIONAL)
		boolean tipButtonPresent = WebUI.verifyElementPresent(findTestObject('Object Repository/PaymentPage/button_paid_valet_web'), 3, FailureHandling.OPTIONAL)
		if(tipButtonPresent) {
			WebUI.click(findTestObject('Object Repository/PaymentPage/btn_pay_tip_valet_web'))
		}else{
			if(customer.equals("Visitor")){
				String pp= expectedParkingFee.replaceAll("\\\$","").trim()
				if(!(pp.equals("0.00"))) {
					if(txt.equals("Pay & Request Your Car")) {
						WebUI.click(findTestObject('Object Repository/PaymentPage/btn_pay_and_request_your_car_web_valet'))
					}else {
						WebUI.click(findTestObject('Object Repository/PaymentPage/btn_checkout', ["ParkingFee" : expectedParkingFee]))
					}
				}else{
					WebUI.click(findTestObject('Object Repository/PaymentPage/btn_pay_tip'))
				}
			}else if(customer.equals("Prepaid") || customer.equals("Guest")) {
				String pp= expectedParkingFee.replaceAll("\\\$","").trim()
				if(!(pp.equals("0.00"))) {
					if(txt.equals("Pay & Request Your Car")) {
						WebUI.click(findTestObject('Object Repository/PaymentPage/btn_pay_and_request_your_car_web_valet'))
					}else {
						WebUI.click(findTestObject('Object Repository/PaymentPage/btn_checkout', ["ParkingFee" : expectedParkingFee]))
					}
				}else{
					WebUI.click(findTestObject('Object Repository/PaymentPage/btn_pay_tip'))
				}
			}
		}

		WebUI.delay(1)

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
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/PaymentPage/btn_tip_active_or_not'), 2)
		}




		"convenience fee"
		String expectedConvenienceFee = WebUI.getText(findTestObject('Object Repository/PaymentPage/txt_convenience_fee'))

		"verify total fee"
		String expectedTipAmout
		String expectedTotalAmount
		String expectedTotalTipAmout
		def total
		if(!(expectedParkingFee.equals("\$0.00"))){
			expectedTipAmout = WebUI.getText(findTestObject('Object Repository/PaymentPage/txt_tip_amount'))
			expectedTotalAmount = WebUI.getText(findTestObject('Object Repository/PaymentPage/txt_total_fee'))
			boolean partialPay = WebUI.verifyElementPresent(findTestObject('Object Repository/PaymentPage/txt_partail_payment_valet_web'), 3, FailureHandling.OPTIONAL)
			def value4 = 0
			if(partialPay) {
				String partailPayment = WebUI.getText(findTestObject('Object Repository/PaymentPage/txt_partail_payment_valet_web'))
				value4 = partailPayment.replaceAll('[^\\d.]', '') as double
			}

			def value1 = actualParkingFee1.replaceAll('[^\\d.]', '') as double
			def value2 = expectedTipAmout.replaceAll('[^\\d.]', '') as double
			def value3 = expectedConvenienceFee.replaceAll('[^\\d.]', '') as double

			def result = value1 + value2 + value3-value4

			total = "\$"+String.format('%.2f', result)

			WebUI.verifyMatch(total.toString(), expectedTotalAmount, false, FailureHandling.CONTINUE_ON_FAILURE)
			GlobalVariable.tipAmount= expectedTipAmout
		}else {
			expectedTotalTipAmout = WebUI.getText(findTestObject('Object Repository/PaymentPage/txt_total_tip'))

			def value1 = paymentData.get("Tip Amount") as double
			def value2 = expectedConvenienceFee.replaceAll('[^\\d.]', '') as double
			def result = value1 + value2

			total = "\$"+String.format('%.2f', result)

			WebUI.verifyMatch(total.toString(), expectedTotalTipAmout, false, FailureHandling.CONTINUE_ON_FAILURE)
			String dd5 = "\$"+paymentData.get("Tip Amount")+".00"
			GlobalVariable.tipAmount= dd5
		}


		//span[text()="Apply Coupon"]
		//WebUI.scrollToElement(findTestObject('Object Repository/PaymentPage/txt'), 10)
		WebUI.scrollToElement(findTestObject('Object Repository/PaymentPage/txt_payment'), 10)
		WebUI.switchToFrame(findTestObject('Object Repository/PaymentPage/iframe_card_no'), 10)

		WebUI.scrollToElement(findTestObject('Object Repository/PaymentPage/input_card_number'), 10, FailureHandling.CONTINUE_ON_FAILURE)
		//WebUI.verifyElementVisible(findTestObject('Object Repository/PaymentPage/input_card_number'))
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
		//WebUI.switchToFrame(findTestObject('Object Repository/PaymentPage/frame_payment'), 10)

		WebUI.scrollToElement(findTestObject('Object Repository/PaymentPage/input_cardholder_name'), 10)
		WebUI.verifyElementVisible(findTestObject('Object Repository/PaymentPage/input_cardholder_name'))
		WebUI.setText(findTestObject('Object Repository/PaymentPage/input_cardholder_name'), paymentData.get("Cardholder Name"))



		"click on request your car and then pay button  or pay & request your car button"
		WebUI.scrollToElement(findTestObject('Object Repository/PaymentPage/btn_pay_and_request_your_car'), 10)

		boolean b1= WebUI.verifyElementPresent(findTestObject('Object Repository/PaymentPage/btn_request_your_car'), 3, FailureHandling.OPTIONAL)
		if(b1) {
			WebUI.click(findTestObject('Object Repository/PaymentPage/btn_request_your_car'))
			res.setInTimeAndDateFull("CarRequestTime")
			GlobalVariable.requestCarStatus=true
			WebUI.delay(2)

			WebUI.scrollToElement(findTestObject('Object Repository/PaymentPage/option_select_location'), 10)
			WebUI.click(findTestObject('Object Repository/PaymentPage/option_select_location'))

			//verify option is selected or not
			String selectedLocation  =WebUI.getAttribute(findTestObject('Object Repository/PaymentPage/selcted_location_color'), "class")
			WebUI.verifyMatch(selectedLocation, "selected", false)

			WebUI.delay(1)
			WebUI.click(findTestObject('Object Repository/PaymentPage/btn_confirm'))
			WebUI.delay(1)

			String actualReqYourCarText = WebUI.getText(findTestObject('Object Repository/PaymentPage/btn_request_your_car'))
			WebUI.verifyMatch(actualReqYourCarText, "We have forwarded your request. We will text you when your car is ready.", false)
		}

		WebUI.scrollToElement(findTestObject('Object Repository/PaymentPage/input_cardholder_name'), 10)
		WebUI.delay(2)
		if(tipButtonPresent) {
			WebUI.click(findTestObject('Object Repository/PaymentPage/btn_pay_tip_valet_web'))
		}else {
			if(customer.equals("Visitor")){
				WebUI.click(findTestObject('Object Repository/PaymentPage/btn_pay_and_request_your_car_web_valet'))
				res.setInTimeAndDateFull("CarRequestTime")
				GlobalVariable.requestCarStatus=true
			}else if(customer.equals("Prepaid") || customer.equals("Guest")) {
				String pp= expectedParkingFee.replaceAll("\\\$","").trim()
				if(!(pp.equals("0.00"))) {
					WebUI.click(findTestObject('Object Repository/PaymentPage/pay_button_with_toatlFee', ["ToatlFee" : total.toString()]))
				}else{
					WebUI.click(findTestObject('Object Repository/PaymentPage/btn_pay_tip'))
				}
			}
		}

		if(txt.equals("Pay & Request Your Car")) {
			WebUI.scrollToElement(findTestObject('Object Repository/PaymentPage/option_select_location'), 10)
			WebUI.click(findTestObject('Object Repository/PaymentPage/option_select_location'))

			//verify option is selected or not
			String selectedLocation  =WebUI.getAttribute(findTestObject('Object Repository/PaymentPage/selcted_location_color'), "class")
			WebUI.verifyMatch(selectedLocation, "selected", false)

			WebUI.delay(1)
			WebUI.click(findTestObject('Object Repository/PaymentPage/btn_confirm'))
			WebUI.delay(1)

			String actualReqYourCarText = WebUI.getText(findTestObject('Object Repository/PaymentPage/btn_request_your_car'))
			WebUI.verifyMatch(actualReqYourCarText, "We have forwarded your request. We will text you when your car is ready.", false)
		}
		//	WebUI.switchToDefaultContent()
		//WebUI.switchToFrame(findTestObject('Object Repository/PaymentPage/iframe_complete'), 10)
		//WebUI.switchToFrame(findTestObject('Object Repository/PaymentPage/iframe_complete_2'), 10)
		//WebUI.scrollToElement(findTestObject('Object Repository/PaymentPage/btn_complete'), 10)
		//WebUI.click(findTestObject('Object Repository/PaymentPage/btn_complete'))

		WebUI.delay(7)
		WebUI.switchToDefaultContent()
		WebUI.delay(1)



		WebUI.waitForPageLoad(10)

		"verify necessary text for reservation summary page is visible"
		WebUI.waitForElementVisible(findTestObject('Object Repository/ReservationSummaryPage/txt_reservation_summary'), 20, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationSummaryPage/txt_Please update your information and make payment for the Valet service'), FailureHandling.CONTINUE_ON_FAILURE)

		GlobalVariable.paymentType= "Card"
		GlobalVariable.requestCarStatus=true
		WebUI.delay(1)

		"verify booking id"
		WebUI.waitForElementVisible(findTestObject('Object Repository/ReservationSummaryPage/txt_booking_id'), 10, FailureHandling.CONTINUE_ON_FAILURE)
		String actualBookingId =WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_booking_id'))
		WebUI.verifyMatch(actualBookingId, GlobalVariable.bookingId, false, FailureHandling.CONTINUE_ON_FAILURE)

		"verify mobile number"
		WebUI.waitForElementVisible(findTestObject('Object Repository/ReservationSummaryPage/txt_mobile_number'), 10, FailureHandling.CONTINUE_ON_FAILURE)
		String actualMobileNo =WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_mobile_number'))
		assert actualMobileNo.contains(customerData.get("Mobile Number"))

		"verify license plate number"
		String actualLicensePlateNo =WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_license_plate'))
		WebUI.verifyMatch(actualLicensePlateNo, customerData.get("License Plate Number"), false, FailureHandling.CONTINUE_ON_FAILURE)


		"verify start time"
		String actualstartTimeOnsummary = WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_start_time'))
		WebUI.verifyMatch(actualstartTimeOnsummary, expectedStartTime, false, FailureHandling.CONTINUE_ON_FAILURE)

		"verify end time"
		String actualEndTime = WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_end_time'))
		//WebUI.verifyMatch(actualEndTime, expectedEndTime, false)

		//String dd= expectedParkingFee.replaceAll("\\\$","").trim()
		//if(!(dd.equals("0.00"))) {

		WebUI.delay(2)
		String totalOnPaidButton = WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/btn_paid'))
		WebUI.click(findTestObject('Object Repository/ReservationSummaryPage/btn_paid'))

		res.verifyToastMessage("Payment successful")
		WebUI.delay(1)

		"verify name"
		String actualName = WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_name'))
		WebUI.verifyMatch(actualName, expectedName, false, FailureHandling.CONTINUE_ON_FAILURE)

		"verify email"
		if(!(customerData.get("Email").equals("NA"))) {
			String actualEmail =WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/text_email'))
			WebUI.verifyMatch(actualEmail, expectedEmail, false, FailureHandling.CONTINUE_ON_FAILURE)
		}

		"verify card fee or convenience fee"
		String actualConvenienceFee = WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_convenience_fee'))
		WebUI.verifyMatch(actualConvenienceFee, expectedConvenienceFee, false, FailureHandling.CONTINUE_ON_FAILURE)
		GlobalVariable.convenienceFee=actualConvenienceFee

		String actualTipAmount = WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_tip'))
		WebUI.verifyMatch(actualTipAmount, GlobalVariable.tipAmount, false, FailureHandling.CONTINUE_ON_FAILURE)

		GlobalVariable.tipAmount = actualTipAmount

		if(!(expectedParkingFee.equals("\$0.00"))){
			"verify total price on paid button"
			assert totalOnPaidButton.contains(expectedTotalAmount)

			"verify payment type"
			String actualPaymentType = WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_payment_type'))
			WebUI.verifyMatch(actualPaymentType.toLowerCase(), "card", false, FailureHandling.CONTINUE_ON_FAILURE)

			"verify parking fee"
			String actualParkingFee =WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_parking_fee'))
			WebUI.verifyMatch(actualParkingFee, expectedParkingFee, false, FailureHandling.CONTINUE_ON_FAILURE)
			GlobalVariable.parkingFee = actualParkingFee

			"verify partial payment"
			String actualPartialPayment = WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_partial_payment'))
			GlobalVariable.partialPayment = actualPartialPayment

			"verify total amount"
			String actualTotalAmount = WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_total_fee'))
			WebUI.verifyMatch(actualTotalAmount, expectedTotalAmount, false, FailureHandling.CONTINUE_ON_FAILURE)

			"verify payment status"
			String actualPaymentStatus1 = WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_payment_status'))
			WebUI.verifyMatch(actualPaymentStatus1, "paid", false, FailureHandling.CONTINUE_ON_FAILURE)

			"verify elements not present when parking fee zero"
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationSummaryPage/txt_total_tip'), 2, FailureHandling.CONTINUE_ON_FAILURE)
		}else {
			"verify Tip Paid text only for paking fee \$0 "
			assert totalOnPaidButton.equals("Tip Paid")

			"verify total tip field"
			String actualTotalAmount = WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_total_tip'))
			WebUI.verifyMatch(actualTotalAmount, expectedTotalTipAmout, false, FailureHandling.CONTINUE_ON_FAILURE)

			"verify payment status"
			String actualPaymentStatus1 = WebUI.getText(findTestObject('Object Repository/ReservationSummaryPage/txt_payment_status'))
			WebUI.verifyMatch(actualPaymentStatus1, "unpaid", false, FailureHandling.CONTINUE_ON_FAILURE)

			"verify elements not present when parking fee zero"
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationSummaryPage/txt_payment_type'), 2, FailureHandling.CONTINUE_ON_FAILURE)
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationSummaryPage/txt_parking_fee'), 2, FailureHandling.CONTINUE_ON_FAILURE)
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationSummaryPage/txt_partial_payment'), 2, FailureHandling.CONTINUE_ON_FAILURE)
			WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationSummaryPage/txt_total_fee'), 2, FailureHandling.CONTINUE_ON_FAILURE)
		}

		"verify request your car info"
		if(b1) {
			String actualReqYourCarText1 = WebUI.getText(findTestObject('Object Repository/PaymentPage/btn_request_your_car'))
			WebUI.verifyMatch(actualReqYourCarText1, "We have forwarded your request. We will text you when your car is ready.", false, FailureHandling.CONTINUE_ON_FAILURE)
		}
		/*
		 "send mail"
		 WebUI.delay(1)
		 WebUI.scrollToElement(findTestObject('Object Repository/ReservationSummaryPage/input_email_id'), 10)
		 WebUI.setText(findTestObject('Object Repository/ReservationSummaryPage/input_email_id'), "pratikgite100@gmail.com")
		 WebUI.delay(2)
		 WebUI.click(findTestObject('Object Repository/ReservationSummaryPage/btn_send'))
		 */
		WebUI.delay(3)

		"verify send email text after sending mail"
		//WebUI.verifyElementVisible(findTestObject('Object Repository/ReservationSummaryPage/txt_Receipt has been sent to your email id'), FailureHandling.CONTINUE_ON_FAILURE)
		//WebUI.delay(2)

		/*WebUI.sendKeys(null, Keys.chord(Keys.CONTROL, 'w'))
		 String originalTabTitle = "Original Tab Title" // Replace with the actual title of the original tab
		 WebUI.switchToWindowTitle(originalTabTitle)*/

		WebUI.navigateToUrl(GlobalVariable.url)
		WebUI.delay(3)
		WebUI.waitForElementVisible(findTestObject('Object Repository/HomePage/link_dashboard'), 20, FailureHandling.CONTINUE_ON_FAILURE)

		//click on reservation link
		WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))


		//click on customer section
		res.clickOnCustomerSection(customer)

		//verify after payment reservation not prsent in same section due to car requested
		WebUI.verifyElementNotPresent(findTestObject('Object Repository/ReservationPage/common_reservation_card', [('LicensePlateNo') : customerData.get("License Plate Number") , ('Section') : section]), 2)

		//verify reservation payment
		res.verifyPayStatusAndCardLogoColorChanged(customerData,paymentData,"Car Requested", customer)
	}

	/*
	 * verify total revenue, card revenue, cash revenue, PQ Tip amount from below table
	 * 
	 * */
	@Keyword
	def verifyTransactionReportColumnData() {
		WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

		WebUI.waitForPageLoad(10)
		WebUI.click(findTestObject('Object Repository/ReservationPage/Summary Dropdwn/dropdown_summary'))
		WebUI.waitForPageLoad(5)
		WebUI.delay(2)
		String  metricCheckIn= WebUI.getText(findTestObject('Object Repository/ReservationPage/Summary Dropdwn/metric_check_in'), FailureHandling.CONTINUE_ON_FAILURE)
		String  metricCheckOut= WebUI.getText(findTestObject('Object Repository/ReservationPage/Summary Dropdwn/metric_check_out'), FailureHandling.CONTINUE_ON_FAILURE)
		String  metricCarsOnBoard= WebUI.getText(findTestObject('Object Repository/ReservationPage/Summary Dropdwn/metric_cars_on_board'), FailureHandling.CONTINUE_ON_FAILURE)
		String  metricTotalRevenue= WebUI.getText(findTestObject('Object Repository/ReservationPage/Summary Dropdwn/metric_total_revenue'), FailureHandling.CONTINUE_ON_FAILURE).replaceAll(/\$|\.00/, '')
		String  metricCashRevenue= WebUI.getText(findTestObject('Object Repository/ReservationPage/Summary Dropdwn/metric_cash_revenue'), FailureHandling.CONTINUE_ON_FAILURE).replaceAll(/\$|\.00/, '')
		String metricCardRevenue = WebUI.getText(findTestObject('Object Repository/ReservationPage/Summary Dropdwn/metric_card revenue'), FailureHandling.CONTINUE_ON_FAILURE).replaceAll(/\$|\.00/, '')
		String  metricVipCars= WebUI.getText(findTestObject('Object Repository/ReservationPage/Summary Dropdwn/metric_vip_cars'), FailureHandling.CONTINUE_ON_FAILURE).replaceAll(/\$|\.00/, '')
		String  metricPQTip= WebUI.getText(findTestObject('Object Repository/ReservationPage/Summary Dropdwn/metric_pq_tip'), FailureHandling.CONTINUE_ON_FAILURE).replaceAll(/\$|\.00/, '')
		String  metricCashDeposit= WebUI.getText(findTestObject('Object Repository/ReservationPage/Summary Dropdwn/metric_cash_deposit'), FailureHandling.CONTINUE_ON_FAILURE).replaceAll(/\$|\.00/, '')
		String  metricValidation= WebUI.getText(findTestObject('Object Repository/ReservationPage/Summary Dropdwn/metric_validation'), FailureHandling.CONTINUE_ON_FAILURE).replaceAll(/\$|\.00/, '')




		//click on transaction report link
		try {
			WebUI.verifyElementVisible(findTestObject('Object Repository/HomePage/link_transaction_report'), FailureHandling.OPTIONAL)
		}catch(StepFailedException e){
			WebUI.scrollToElement(findTestObject('Object Repository/HomePage/link_transaction_report'), 10)
		}
		WebUI.click(findTestObject('Object Repository/HomePage/link_transaction_report'))

		WebUI.waitForElementVisible(findTestObject('Object Repository/ReportPage/txt_box_value',["Name" : "Cash Revenue"]), 10)

		String expectedTotalRevenue = WebUI.getText(findTestObject('Object Repository/ReportPage/txt_box_value',["Name" : "Total Revenue"]), FailureHandling.CONTINUE_ON_FAILURE)
		String expectedCardRevenue = WebUI.getText(findTestObject('Object Repository/ReportPage/txt_box_value',["Name" : "Card Revenue"]), FailureHandling.CONTINUE_ON_FAILURE)
		String expectedCashRevenue = WebUI.getText(findTestObject('Object Repository/ReportPage/txt_box_value',["Name" : "Cash Revenue"]), FailureHandling.CONTINUE_ON_FAILURE)
		String expectedPQTipAmount =WebUI.getText(findTestObject('Object Repository/ReportPage/txt_box_value',["Name" : "PQ Tip Amount"]), FailureHandling.CONTINUE_ON_FAILURE)
		String expectedCashDeposite = WebUI.getText(findTestObject('Object Repository/ReportPage/txt_box_value',["Name" : "Cash Deposit"]), FailureHandling.CONTINUE_ON_FAILURE)
		String expectedCarsOnBoard = WebUI.getText(findTestObject('Object Repository/ReportPage/txt_box_value',["Name" : "Cars on Board"]), FailureHandling.CONTINUE_ON_FAILURE)
		String expectedCheckIns = WebUI.getText(findTestObject('Object Repository/ReportPage/txt_box_value',["Name" : "Check-ins"]), FailureHandling.CONTINUE_ON_FAILURE)
		String expectedCheckOuts = WebUI.getText(findTestObject('Object Repository/ReportPage/txt_box_value',["Name" : "Check-outs"]), FailureHandling.CONTINUE_ON_FAILURE)

		"verify metric values or summary dropdown and transaction reort dropdown"
		WebUI.verifyMatch(expectedTotalRevenue, metricTotalRevenue, false, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(expectedCardRevenue, metricCardRevenue, false, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(expectedCashRevenue, metricCashRevenue, false, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(expectedPQTipAmount, metricPQTip, false, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(expectedCashDeposite, metricCashDeposit, false, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(expectedCarsOnBoard, metricCarsOnBoard, false, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(expectedCheckIns, metricCheckIn, false, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(expectedCheckOuts, metricCheckOut, false, FailureHandling.CONTINUE_ON_FAILURE)


		"to verify onboarding and checkout metric"
		def response = WS.sendRequest(findTestObject('Object Repository/APIAutomationObjects/forAccessToken'))
		WS.verifyResponseStatusCode(response, 200)
		GlobalVariable.accessToken = WS.getElementPropertyValue(response, "access_token")
		def jsonResponse = WS.sendRequest(findTestObject('Object Repository/APIAutomationObjects/forv2detailsapi'))
		WS.verifyResponseStatusCode(jsonResponse, 200)

		// Parse JSON response
		def jsonSlurper = new JsonSlurper()
		def jsonData = jsonSlurper.parseText(jsonResponse.getResponseText())

		// Count occurrences of 'ongoing' and 'completed'
		def actualOngoingCount = jsonData.data.bookings.count { it.status == 'ongoing' }
		def actualCompletedCount = jsonData.data.bookings.count { it.status == 'completed' }
		def actualVipCars = jsonData.data.bookings.count { it.special_price == 'vip' }

		//verify vips cars on summary dropdown
		WebUI.verifyMatch(metricVipCars, actualVipCars.toString(), false, FailureHandling.OPTIONAL)

		int paymentTypeIndex, netPaymentIndex, valetFeeIndex, tipAmountIndex, discountAmountIndex, bookingID
		List<WebElement> elements1 = WebUI.findWebElements(findTestObject('Object Repository/ReportPage/count_column_in_table'),10)
		int elementCount1 = elements1.size()
		HashMap<String, String> map= new HashMap<String, String>()
		for(int i=1;i<=elementCount1;i++){
			String header = WebUI.getText(findTestObject('Object Repository/ReportPage/header_list',["Index" : i]))
			//String actualValue = WebUI.getText(findTestObject('Object Repository/ReportPage/value_list',["BookingId" : GlobalVariable.ID, "Index" : i]))
			if(header.equals("Valet Fee")) {
				valetFeeIndex=i
			}
			if(header.equals("Net Payment")) {
				netPaymentIndex=i
			}
			if(header.equals("Payment Type")) {
				paymentTypeIndex=i
			}
			if(header.equals("Discount Amount")) {
				discountAmountIndex=i
			}
			if(header.equals("Tip Amount")) {
				tipAmountIndex=i
			}
			if(header.equals("ID")) {
				bookingID=i
			}
			//map.put(header, actualValue)
		}
		//getting row count
		List<WebElement> elements = WebUI.findWebElements(findTestObject('Object Repository/ReportPage/count_rows_in_table'),10)
		int elementCount = elements.size()
		int actualTotalCashRevenue =0
		int actualTotalCardRevenue=0
		int actualTotalFrontDeskRevenue=0
		int actualTotalValetFee = 0
		int actualTotalDiscountAmount
		int actualTotalTipAmount

		//iterate each row
		for (int i=1; i<=elementCount;i++) {
			//for(int j=1;j<=elementCount1;j++){
			String paymentTypeOfEachRow = WebUI.getText(findTestObject('Object Repository/ReportPage/cell_data',["I" : i, "J": paymentTypeIndex]))
			String netPaymentOfEachRow = WebUI.getText(findTestObject('Object Repository/ReportPage/cell_data',["I" : i, "J": netPaymentIndex]))
			String valetFeeOfEachRow = WebUI.getText(findTestObject('Object Repository/ReportPage/cell_data',["I" : i, "J": valetFeeIndex]))
			String discountAmountOfEachRow = WebUI.getText(findTestObject('Object Repository/ReportPage/cell_data',["I" : i, "J": discountAmountIndex]))
			String tipAmountOfEachRow = WebUI.getText(findTestObject('Object Repository/ReportPage/cell_data',["I" : i, "J": tipAmountIndex]))
			String BookingIdofEachRow = WebUI.getText(findTestObject('Object Repository/ReportPage/cell_data_for_id',["I" : i, "J": bookingID]))
			println("_______________________________________________________________________________")
			println("Payment type : "+paymentTypeOfEachRow)
			println("NetPayment : "+netPaymentOfEachRow)
			println("Valet Fee:  "+valetFeeOfEachRow)
			println("Discount Amount : "+discountAmountOfEachRow)
			println("Tip Amount: "+tipAmountOfEachRow)
			println("_______________________________________________________________________________")
			if(paymentTypeOfEachRow.equals("Cash")) {
				def pp= netPaymentOfEachRow.replace("\$", "").toInteger()
				actualTotalCashRevenue = actualTotalCashRevenue+pp
			}
			if(paymentTypeOfEachRow.equals("Card") || paymentTypeOfEachRow.equals("Apple Pay")) {
				def pp= netPaymentOfEachRow.replace("\$", "").toInteger()
				actualTotalCardRevenue = actualTotalCardRevenue+pp
			}
			if(paymentTypeOfEachRow.trim().equals("Front Desk")) {
				def pp= netPaymentOfEachRow.replace("\$", "").toInteger()
				actualTotalFrontDeskRevenue = actualTotalFrontDeskRevenue+pp
			}
			if(!valetFeeOfEachRow.trim().equals("")) {
				def pp1= valetFeeOfEachRow.replace("\$", "").toInteger()
				actualTotalValetFee = actualTotalValetFee+pp1
			}
			if(!discountAmountOfEachRow.trim().equals("")) {
				def pp2= discountAmountOfEachRow.replace("\$", "").toInteger()
				actualTotalDiscountAmount = actualTotalDiscountAmount+pp2
			}
			if(!tipAmountOfEachRow.trim().equals("")) {
				def pp3= tipAmountOfEachRow.replace("\$", "").toInteger()
				if(paymentTypeOfEachRow.equals("Card")) {
					actualTotalTipAmount = actualTotalTipAmount+pp3
				}
			}
			//bookings
		}

		//verify Total revenue
		def ss= actualTotalCashRevenue +actualTotalCardRevenue+actualTotalFrontDeskRevenue
		WebUI.verifyMatch(ss.toString(), expectedTotalRevenue, false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify card revenue
		WebUI.verifyMatch(actualTotalCardRevenue.toString(), expectedCardRevenue, false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify cash revenue
		WebUI.verifyMatch(actualTotalCashRevenue.toString(), expectedCashRevenue, false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify PQ Tip amount
		WebUI.verifyMatch(actualTotalTipAmount.toString(), expectedPQTipAmount, false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify cash deposite
		def actualCashDeposite = actualTotalCashRevenue - actualTotalTipAmount
		WebUI.verifyMatch(actualCashDeposite.toString(), expectedCashDeposite, false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify Cars on Board
		WebUI.verifyMatch(actualOngoingCount.toString(), expectedCarsOnBoard, false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify check-outs metric
		WebUI.verifyMatch(actualCompletedCount.toString(), expectedCheckOuts, false, FailureHandling.CONTINUE_ON_FAILURE)


		println("actual total revenue : "+ss)
		println("actual total Front card revenue : "+actualTotalCardRevenue)
		println("actual total Front cash revenue : "+actualTotalCashRevenue)
		println("actual total Front desk revenue : "+actualTotalFrontDeskRevenue)
		println("actual total tip amount : "+actualTotalTipAmount)
		println("actual total cash deposit : "+actualCashDeposite)
		println("actual total velet fee : "+actualTotalValetFee)
		println("actual total discount amount : "+actualTotalDiscountAmount)
		println "Ongoing Count from v2 api : ${actualOngoingCount}"
		println "Completed Count from v2 api : ${actualCompletedCount}"



		///////ALL tab

		WebUI.waitForElementVisible(findTestObject('Object Repository/ReportPage/btn_all'), 10)
		WebUI.click(findTestObject('Object Repository/ReportPage/btn_all'))
		WebUI.waitForPageLoad(10)
		String expectedCashRevenue1 = WebUI.getText(findTestObject('Object Repository/ReportPage/txt_box_value',["Name" : "Cash Revenue"]), FailureHandling.CONTINUE_ON_FAILURE)
		String expectedCardRevenue1 = WebUI.getText(findTestObject('Object Repository/ReportPage/txt_box_value',["Name" : "Card Revenue"]), FailureHandling.CONTINUE_ON_FAILURE)
		String expectedTotalRevenue1 = WebUI.getText(findTestObject('Object Repository/ReportPage/txt_box_value',["Name" : "Total Revenue"]), FailureHandling.CONTINUE_ON_FAILURE)
		String expectedPQTipAmount1 =WebUI.getText(findTestObject('Object Repository/ReportPage/txt_box_value',["Name" : "PQ Tip Amount"]), FailureHandling.CONTINUE_ON_FAILURE)
		String expectedCashDeposite1 = WebUI.getText(findTestObject('Object Repository/ReportPage/txt_box_value',["Name" : "Cash Deposit"]), FailureHandling.CONTINUE_ON_FAILURE)
		String expectedCarsOnBoard1 = WebUI.getText(findTestObject('Object Repository/ReportPage/txt_box_value',["Name" : "Cars on Board"]), FailureHandling.CONTINUE_ON_FAILURE)
		String expectedCheckIns1 = WebUI.getText(findTestObject('Object Repository/ReportPage/txt_box_value',["Name" : "Check-ins"]), FailureHandling.CONTINUE_ON_FAILURE)
		String expectedCheckOuts1 = WebUI.getText(findTestObject('Object Repository/ReportPage/txt_box_value',["Name" : "Check-outs"]), FailureHandling.CONTINUE_ON_FAILURE)


		//TestObject testObj1 =
		List<WebElement> elements11 = WebUI.findWebElements(findTestObject('Object Repository/ReportPage/count_rows_in_table'),10)
		int elementCount11 = elements11.size()
		int actualTotalCashRevenue1 =0
		int actualTotalCardRevenue1=0
		int actualTotalFrontDeskRevenue1=0
		int actualTotalValetFee1 = 0
		int actualTotalDiscountAmount1
		int actualTotalTipAmount1

		for (int i=1; i<=elementCount11;i++) {
			//for(int j=1;j<=elementCount1;j++){
			String paymentTypeOfEachRow = WebUI.getText(findTestObject('Object Repository/ReportPage/cell_data',["I" : i, "J": paymentTypeIndex]))
			String netPaymentOfEachRow = WebUI.getText(findTestObject('Object Repository/ReportPage/cell_data',["I" : i, "J": netPaymentIndex]))
			String valetFeeOfEachRow = WebUI.getText(findTestObject('Object Repository/ReportPage/cell_data',["I" : i, "J": valetFeeIndex]))
			String discountAmountOfEachRow = WebUI.getText(findTestObject('Object Repository/ReportPage/cell_data',["I" : i, "J": discountAmountIndex]))
			String tipAmountOfEachRow = WebUI.getText(findTestObject('Object Repository/ReportPage/cell_data',["I" : i, "J": tipAmountIndex]))
			String BookingIdofEachRow = WebUI.getText(findTestObject('Object Repository/ReportPage/cell_data_for_id',["I" : i, "J": bookingID]))
			println("##############################################################")
			println("Payment type : "+paymentTypeOfEachRow)
			println("NetPayment : "+netPaymentOfEachRow)
			println("Valet Fee:  "+valetFeeOfEachRow)
			println("Discount Amount : "+discountAmountOfEachRow)
			println("Tip Amount: "+tipAmountOfEachRow)
			println("##############################################################")
			if(paymentTypeOfEachRow.equals("Cash")) {
				def pp= netPaymentOfEachRow.replace("\$", "").toInteger()
				actualTotalCashRevenue1 = actualTotalCashRevenue1+pp
			}
			if(paymentTypeOfEachRow.equals("Card") || paymentTypeOfEachRow.equals("Apple Pay")) {
				def pp= netPaymentOfEachRow.replace("\$", "").toInteger()
				actualTotalCardRevenue1 = actualTotalCardRevenue1+pp
			}
			if(paymentTypeOfEachRow.trim().equals("Front Desk")) {
				def pp= netPaymentOfEachRow.replace("\$", "").toInteger()
				actualTotalFrontDeskRevenue1 = actualTotalFrontDeskRevenue1+pp
			}
			if(!valetFeeOfEachRow.trim().equals("")) {
				def pp1= valetFeeOfEachRow.replace("\$", "").toInteger()
				actualTotalValetFee1 = actualTotalValetFee1+pp1
			}
			if(!discountAmountOfEachRow.trim().equals("")) {
				def pp2= discountAmountOfEachRow.replace("\$", "").toInteger()
				actualTotalDiscountAmount1 = actualTotalDiscountAmount1+pp2
			}
			if(!tipAmountOfEachRow.trim().equals("")) {
				def pp3= tipAmountOfEachRow.replace("\$", "").toInteger()
				if(paymentTypeOfEachRow.equals("Card")) {
					actualTotalTipAmount1 = actualTotalTipAmount1+pp3
				}
			}
		}

		//verify Total revenue
		def ss1= actualTotalCashRevenue1 +actualTotalCardRevenue1+actualTotalFrontDeskRevenue1
		WebUI.verifyMatch(ss1.toString(), expectedTotalRevenue1, false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify card revenue
		WebUI.verifyMatch(actualTotalCardRevenue1.toString(), expectedCardRevenue1, false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify cash revenue
		WebUI.verifyMatch(actualTotalCashRevenue1.toString(), expectedCashRevenue1, false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify PQ Tip amount
		WebUI.verifyMatch(actualTotalTipAmount1.toString(), expectedPQTipAmount1, false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify cash deposite
		def actualCashDeposite1 = actualTotalCashRevenue1 - actualTotalTipAmount1
		WebUI.verifyMatch(actualCashDeposite1.toString(), expectedCashDeposite1, false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify Cars on Board
		WebUI.verifyMatch(actualOngoingCount.toString(), expectedCarsOnBoard1, false, FailureHandling.CONTINUE_ON_FAILURE)

		//verify check-outs metric
		WebUI.verifyMatch(actualCompletedCount.toString(), expectedCheckOuts1, false, FailureHandling.CONTINUE_ON_FAILURE)

		println("actual total revenue : "+ss1)
		println("actual total Front card revenue : "+actualTotalCardRevenue1)
		println("actual total Front cash revenue : "+actualTotalCashRevenue1)
		println("actual total Front desk revenue : "+actualTotalFrontDeskRevenue1)
		println("actual total tip amount : "+actualTotalTipAmount1)
		println("actual total cash deposit : "+actualCashDeposite1)
		//println("actual total velet fee : "+actualTotalValetFee1)
		println("actual total discount amount : "+actualTotalDiscountAmount1)
		println "Ongoing Count from v2 api : ${actualOngoingCount}"
		println "Completed Count from v2 api : ${actualCompletedCount}"
	}

	@Keyword
	def verifyMonthlyListColumnData() {
		//click on transaction report link
		/*
		 try {
		 WebUI.verifyElementVisible(findTestObject('Object Repository/HomePage/link_monthly'), FailureHandling.OPTIONAL)
		 }catch(StepFailedException e){
		 WebUI.scrollToElement(findTestObject('Object Repository/HomePage/link_monthly'), 10)
		 }
		 */
		WebUI.click(findTestObject('Object Repository/HomePage/link_monthly'))


		WebUI.waitForElementVisible(findTestObject('Object Repository/MonthlyPage/txt_monthly_parking_list'), 10)


		String expectedMonthlyPasses = WebUI.getText(findTestObject('Object Repository/ReportPage/txt_box_value',["Name" : "Monthly Passes"]), FailureHandling.CONTINUE_ON_FAILURE)
		String expectedPaidCustomers = WebUI.getText(findTestObject('Object Repository/ReportPage/txt_box_value',["Name" : "Paid"]), FailureHandling.CONTINUE_ON_FAILURE)
		String expectedRevenue = WebUI.getText(findTestObject('Object Repository/ReportPage/txt_box_value',["Name" : "Revenue"]), FailureHandling.CONTINUE_ON_FAILURE).replaceAll(",","")
		String expectedRefund =WebUI.getText(findTestObject('Object Repository/ReportPage/txt_box_value',["Name" : "Refund"]), FailureHandling.CONTINUE_ON_FAILURE)

		double startingMonthIndex, totalAmountIndex
		List<WebElement> elements1 = WebUI.findWebElements(findTestObject('Object Repository/ReportPage/count_column_in_table'),10)
		int elementCount1 = elements1.size()
		HashMap<String, String> map= new HashMap<String, String>()
		for(int i=1;i<=elementCount1;i++){
			String header = WebUI.getText(findTestObject('Object Repository/ReportPage/header_list',["Index" : i]))
			//String actualValue = WebUI.getText(findTestObject('Object Repository/ReportPage/value_list',["BookingId" : GlobalVariable.ID, "Index" : i]))

			if(header.equals("Total Amount")) {
				totalAmountIndex=i
			}


			//map.put(header, actualValue)
		}

		//TestObject testObj1 =
		List<WebElement> elements = WebUI.findWebElements(findTestObject('Object Repository/ReportPage/count_rows_in_table'),10)
		int elementCount = elements.size()
		println("_____________________________")
		double actualTotalAmount=0
		for (int i=1; i<=elementCount;i++) {
			//for(int j=1;j<=elementCount1;j++){

			String totalPaymentOfEachRow = WebUI.getText(findTestObject('Object Repository/ReportPage/cell_data',["I" : i, "J": totalAmountIndex]))


			def pp= totalPaymentOfEachRow.replace("\$", "").replaceAll(",", "").trim().toDouble()
			actualTotalAmount = actualTotalAmount+pp

			println(pp)
			//println(actualTotalAmount)
		}
		println("_____________________________")


		//verify cash revenue
		def qq= expectedRefund.replace("\$", "").replaceAll(",", "").trim().toDouble()
		def rr=actualTotalAmount-qq
		def tt=String.format("%.1f", rr)
		//def tt=rr.toInteger()
		WebUI.verifyMatch(tt.toString(), expectedRevenue, false, FailureHandling.CONTINUE_ON_FAILURE)

		println("*********metric refund************* : "+qq)
		println("********revenue by calculation total- refund************** : "+tt)
		println("**********metric revenue************ : "+expectedRevenue)
	}
}
