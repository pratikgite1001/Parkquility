import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import internal.GlobalVariable
'Create new Reservation for vip Prepaid'
def customerdata = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('Customerdata', 'CP1')
def updatedCustomerData = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('Customerdata', 'CP11')
def paymentData = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('PaymentData', 'P1')

'fill new create reservation form'
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.fillCreateReservationForm'(customerdata,  "Prepaid")

'validate created reservation'
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateCreatedReservation'(customerdata, "In", "Prepaid")

"verify active features"
//CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyActiveStatus'(customerdata, "In", "Prepaid")

'validate formation on edit reservation Page'
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateEditPageInfoWithEnteredInfo'(customerdata, "In", "Prepaid")


//**********************************************
'update reservation with new data'
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.updateReservation'(customerdata, updatedCustomerData, "In", "Prepaid")

"verify assign pick up runner feature   section"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyAssignPickupRunnerFeature'(updatedCustomerData, "In", "Prepaid")

'validate updated reservation on card fo page'
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateCreatedReservation'(updatedCustomerData, "In", "Prepaid")

'validate updated formation on edit reservation Page'
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateEditPageInfoWithEnteredInfo'(updatedCustomerData, "In", "Prepaid")


//******************************************************************

 "verify Location feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyLocationFeature'(updatedCustomerData, "In", "Prepaid")
 /*
 //******************************************************************
"verify Request Car feature"
//Requesr Car
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyRequestCarFeature'(updatedCustomerData, 'In', "Prepaid")

"verify assign pick up runner feature  car requested section"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyAssignPickupRunnerFeature'(updatedCustomerData, 'Car Requested', "Prepaid")

"verify ReCheck feature"
//Recheck
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyReCheckFeature'(updatedCustomerData, 'Car Requested', 'Prepaid')
*/

//*********************************************************

"verify pickup runner feature   Section"
//CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyAssignPickupRunnerFeature'(updatedCustomerData, "In", "Prepaid")

//*******************************************************************

'verify key  car feature'
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyKeyInCarFeature'(updatedCustomerData, "In", "Prepaid")





//*************************************************************************************
"verify send payment lk feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.clickOnSendPaymentLink'(updatedCustomerData, "In", "Prepaid")


//*****************************************************************************
"verify pay by qrc popup is visible"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyPayByQRCPopup'(updatedCustomerData,"In", "Prepaid")


//********************************************************************************
"verify sms option"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifySMSOption'(updatedCustomerData,"In", "Prepaid")

"verify key In feature becomes true"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyKeyInFeaturebecomestrue'(updatedCustomerData,"In", "Prepaid")


//*************************************************************************************
'make payment of reservation and verify'
//make payment
//CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.makePayment'(paymentData, updatedCustomerData, "In", "Prepaid")

def response = WS.sendRequest(findTestObject('Object Repository/PaymentPage/GetHashCodeRequest',["ID": GlobalVariable.ID]))
WS.verifyResponseStatusCode(response, 200)
GlobalVariable.hashCode = WS.getElementPropertyValue(response, "data.hash")

CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.makeValetWebPayment'(updatedCustomerData, paymentData, "In", "Prepaid")



//validate reservation bookg details
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateCreatedReservation'(updatedCustomerData, "Car Requested", "Prepaid")

"validate used features are  active state"
//CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyActiveStatus'(updatedCustomerData, "Car Requested", "Prepaid")

//***************************************************************
"verify assign drop off runner feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyAssignDropOffFeature'(updatedCustomerData, 'Car Requested', "Prepaid")

"Verify Car on the way feature"
 CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyCarOnTheWayFeature'(updatedCustomerData, 'Car Requested', "Prepaid")

 'verify car ready feature'
 CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyCarReadyFeature'(updatedCustomerData,"Car Requested", "45", "Prepaid")
 CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyCarReadyFeature'(updatedCustomerData,"Car Requested", "now", "Prepaid")
 
 //***********************************************************************
 
 //verify In and Out Report
 CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.verifyInAndOutReport'(updatedCustomerData, "Guest")
 
 "verify Out feature, move car from Car Request to OUT section"
 CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyOutFeature'(updatedCustomerData, "Car Requested", "Prepaid")

 //verify  In and Out Report
 CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.verifyInAndOutReport'(updatedCustomerData, "Guest")
 
 "verify Make Visitor feature"
 //CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyMakeVisitorFeature'(updatedCustomerData, "Out", "Prepaid")

 
 "verify checkout feature"
 CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.checkoutReservation'(updatedCustomerData, "Out", "Prepaid")
 CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateCreatedReservation'(updatedCustomerData, "Completed Reservations", "Prepaid")
 
 "Verify Day Report"
 CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.verifyDayReport'(updatedCustomerData, "Prepaid")
 
 "verify Transaction Report"
 CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.verifyTransactionReport'(updatedCustomerData, "Prepaid")
 
 "verify tip Report"
 CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.verifyTipReport'(updatedCustomerData)
 