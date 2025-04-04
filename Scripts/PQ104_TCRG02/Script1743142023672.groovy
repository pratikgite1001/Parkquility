import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import internal.GlobalVariable as GlobalVariable

'Create new Reservation for vip Guest'
def customerdata = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('Customerdata', 'CG2')
def updatedCustomerData = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('Customerdata', 'CG22')
def paymentData = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('PaymentData', 'P1')

'fill new create reservation form'
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.fillCreateReservationForm'(customerdata, "Guest")

'validate created reservation in booking details and edit reservation page'
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateCreatedReservation'(customerdata, "In", "Guest")

CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateEditPageInfoWithEnteredInfo'(customerdata,"In", "Guest")

//**********************************************
'update reservation with new data'
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.updateReservation'(customerdata, updatedCustomerData, "In", "Guest")

"verify assign pick up runner feature in In Section"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyAssignPickupRunnerFeature'(updatedCustomerData, 'In', "Guest")

'validate updated reservation on booking details page and edit reservation page'
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateCreatedReservation'(updatedCustomerData, "In", "Guest")
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateEditPageInfoWithEnteredInfo'(updatedCustomerData, "In", "Guest")

//******************************************************************

"verify notes option"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyNotesFeature'(updatedCustomerData, 'In', "Guest")

"verify Damages option"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyDamagesFeature'(updatedCustomerData, "In", "Guest")

"verify Location feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyLocationFeature'(updatedCustomerData, "In", "Guest")

//******************************************************************
/*
"verify Request Car feature"
//Requesr Car
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyRequestCarFeature'(updatedCustomerData, 'In', "Guest")

"verify assign pick up runner feature in car requested section"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyAssignPickupRunnerFeature'(updatedCustomerData, 'Car Requested', "Guest")

"verify ReCheckIn feature"
//RecheckIn
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyReCheckInFeature'(updatedCustomerData, 'Car Requested', 'Guest')
*/
//************************************************************************************

//verify In and Out Report
//CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.verifyInAndOutReport'(updatedCustomerData, "Guest")

"verify Out feature, move car from IN to OUT section"
 CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyOutFeature'(updatedCustomerData, "In", "Guest")
 
//verify In and Out Report
 CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.verifyInAndOutReport'(updatedCustomerData, "Guest")
 

 "verify In feature, move reservation from OUT to IN section"
 CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyInFeature'(updatedCustomerData, "Out", "Guest")
 CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateEditPageInfoWithEnteredInfo'(updatedCustomerData,"In", "Guest")
 
 //***********************************************************************
 
 "verify assign pick up runner feature in In Section"
 CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyAssignPickupRunnerFeature'(updatedCustomerData, 'In', "Guest")

//*********************************************************8
'verify key in car feature'
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyKeyInCarFeature'(updatedCustomerData, 'In', "Guest")

//*************************************************************************************
"verify send payment link feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.clickOnSendPaymentLink'(updatedCustomerData, "In", "Guest")


//*****************************************************************************
"verify pay by qrc popup is visible"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyPayByQRCPopup'(updatedCustomerData,"In", "Guest")


//********************************************************************************
"verify sms option"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifySMSOption'(updatedCustomerData,"In", "Guest")

"verify key in  feature becomes true"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyKeyInFeaturebecomestrue'(updatedCustomerData,"In", "Guest")

//verify Day Report
CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.verifyDayReport'(updatedCustomerData, "Guest")

//*************************************************************************************
'make payment of reservation and verify'
//CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.makePayment'(paymentData, updatedCustomerData, "In", "Guest")

//get hash code from api response
def response = WS.sendRequest(findTestObject('Object Repository/PaymentPage/GetHashCodeRequest',["ID": GlobalVariable.ID]))
WS.verifyResponseStatusCode(response, 200)
GlobalVariable.hashCode = WS.getElementPropertyValue(response, "data.hash")

//verify card payment scenario
CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.makeValetWebPayment'(updatedCustomerData, paymentData, "In", "Guest")

//validate reservation booking details after card payment
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateCreatedReservation'(updatedCustomerData, "Car Requested", "Guest")

"validate used features are in active state"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyActiveStatus'(updatedCustomerData, "Car Requested", "Guest")

//*******************************************************************

"verify assign drop off runner feature in car requested section"
 CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyAssignDropOffFeature'(updatedCustomerData, 'Car Requested', "Guest")
 
 "Verify Car on the way feature"
 CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyCarOnTheWayFeature'(updatedCustomerData, 'Car Requested', "Guest")
 
 'verify car ready feature'
 CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyCarReadyFeature'(updatedCustomerData,"Car Requested", "20", "Guest")
 CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyCarReadyFeature'(updatedCustomerData,"Car Requested", "now", "Guest")

//**********************************************************************************************

"verify checkout feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.checkoutReservation'(updatedCustomerData, "Car Requested", "Guest")
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateCreatedReservation'(updatedCustomerData, "Completed Reservations", "Guest")

"verify Day Report"
CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.verifyDayReport'(updatedCustomerData, "Guest")

"verify Transaction Report"
CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.verifyTransactionReport'(updatedCustomerData, "Guest")

"verify tip Report"
CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.verifyTipReport'(updatedCustomerData)
