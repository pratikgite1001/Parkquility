import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import internal.GlobalVariable


"Create new Reservation for normal visitor"

def customerdata = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('Customerdata', 'CV1')
def updatedCustomerData = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('Customerdata', 'CV11')
def paymentData = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('PaymentData', 'P1')

"fill create reservation form"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.fillCreateReservationForm'(customerdata, "Visitor")

"validate used features are in active state"
//CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyActiveStatus'(customerdata, "Ongoing", "Visitor")

"validate created reservation"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateCreatedReservation'(customerdata, "Ongoing", "Visitor")

"validate information on edit reservation Page"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateEditPageInfoWithEnteredInfo'(customerdata, "Ongoing", "Visitor")

//****************************************************************************8

"update reservation we new data"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.updateReservation'(customerdata,updatedCustomerData, "Ongoing", "Visitor")

"verify assign pick up runner feature in Ongoing section"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyAssignPickupRunnerFeature'(updatedCustomerData, 'Ongoing', "Visitor")

"validate updated reservation on card info page"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateCreatedReservation'(updatedCustomerData, "Ongoing", "Visitor")

"validate updated information on edit reservation Page"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateEditPageInfoWithEnteredInfo'(updatedCustomerData, "Ongoing", "Visitor")

//******************************************************************

"verify notes feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyNotesFeature'(customerdata, 'Ongoing', "Visitor")

"verify damages option"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyDamagesFeature'(customerdata, "Ongoing", "Visitor")

"verify Location feature"
//CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyLocationFeature'(updatedCustomerData, "Ongoing", "Visitor")


//******************************************************************
"verify Request Car feature and Re-CheckIn feature"
//Requesr Car
//CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyRequestCarFeature'(updatedCustomerData, 'Ongoing', "Visitor")

"verify assign pick up runner feature in car requested section"
//CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyAssignPickupRunnerFeature'(updatedCustomerData, 'Car Requested', "Visitor")

//RecheckIn
//CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyReCheckInFeature'(updatedCustomerData, 'Car Requested', 'Visitor')

//*********************************************************



//**********************************************************
'verify key in car feature'
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyKeyInCarFeature'(updatedCustomerData, 'Ongoing', "Visitor")


//*************************************************************************************
"verify send payment link feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.clickOnSendPaymentLink'(updatedCustomerData, "Ongoing", "Visitor")


//*****************************************************************************
"verify pay by qrc popup is visible"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyPayByQRCPopup'(updatedCustomerData,"Ongoing", "Visitor")


//********************************************************************************
"verify sms option"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifySMSOption'(updatedCustomerData,"Ongoing", "Visitor")

"verify key in feature becomes true"
//CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyKeyInFeaturebecomestrue'(updatedCustomerData,"Ongoing", "Visitor")


//verify Day Report
CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.verifyDayReport'(updatedCustomerData, "Visitor")

//*************************************************************************************
'make payment of reservation and verify'
//make payment
//CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.makePayment'(paymentData, updatedCustomerData, "Ongoing", "Visitor")




//get hashcode from api
def response = WS.sendRequest(findTestObject('Object Repository/PaymentPage/GetHashCodeRequest',["ID": GlobalVariable.ID]))
WS.verifyResponseStatusCode(response, 200)
GlobalVariable.hashCode = WS.getElementPropertyValue(response, "data.hash")

//validate card payment scenario
CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.makeValetWebPayment'(updatedCustomerData, paymentData, "Ongoing", "Visitor")	

//validate reservation booking details
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateCreatedReservation'(updatedCustomerData, "Car Requested", "Visitor")

"validate used features are in active state"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyActiveStatus'(updatedCustomerData, "Car Requested", "Visitor")

//************************************************************************************

"verify assign drop off runner feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyAssignDropOffFeature'(updatedCustomerData, 'Car Requested', "Visitor")

"Verify Car on the way feature"
 CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyCarOnTheWayFeature'(updatedCustomerData, 'Car Requested', "Visitor")

'verify car ready feature'
 CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyCarReadyFeature'(updatedCustomerData,"Car Requested", "10", "Visitor")
 CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyCarReadyFeature'(updatedCustomerData,"Car Requested", "now", "Visitor")
 
//*******************************************************************
 "verify checkout feature"
 CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.checkoutReservation'(updatedCustomerData, "Car Requested", "Visitor")
 CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateCreatedReservation'(updatedCustomerData, "Completed Reservations", "Visitor")
 
 "verify Day Report"
 CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.verifyDayReport'(updatedCustomerData, "Visitor")
 
 "verify Transaction Report"
 CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.verifyTransactionReport'(updatedCustomerData, "Visitor")
 
 "verify tip Report"
 CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.verifyTipReport'(updatedCustomerData)