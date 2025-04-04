import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import internal.GlobalVariable as GlobalVariable

def customerdata = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('Customerdata', 'CP1')
def updatedCustomerData = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('Customerdata', 'CV11')
def paymentData = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('PaymentData', 'P1')

"fill create reservation form"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.fillCreateReservationForm'(customerdata, "Prepaid")

//validate reservation booking details
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateCreatedReservation'(customerdata, "In", "Prepaid")

"make payment"
//get hashcode from api
def response = WS.sendRequest(findTestObject('Object Repository/PaymentPage/GetHashCodeRequest',["ID": GlobalVariable.ID]))
WS.verifyResponseStatusCode(response, 200)
GlobalVariable.hashCode = WS.getElementPropertyValue(response, "data.hash")

//validate card payment scenario
CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.makeValetWebPayment'(customerdata, paymentData, "In", "Prepaid")

//validate reservation booking details
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.validateCreatedReservation'(customerdata, "Car Requested", "Prepaid")

 "verify tip Report"
 CustomKeywords.'com.Parkquility.app.Keywords.ReportPage.verifyTipReport'(customerdata)



