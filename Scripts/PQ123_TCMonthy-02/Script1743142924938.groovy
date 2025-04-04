import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

"Validate all features of created monthly reservation"

def permitParkingData = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('PermitParkingData', 'P20')
def addressData = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('AddressData', permitParkingData.get("Address Data"))

"click on reservation link"
WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

"make check in monthly reservation"
CustomKeywords.'com.Parkquility.app.Keywords.PermitParking.makeCheckInReservation'(permitParkingData, "Paid")

"verify monthly reservation card information are corrrect or not"
CustomKeywords.'com.Parkquility.app.Keywords.PermitParking.verifyMonthlyCardInfo'(permitParkingData, addressData,"Ongoing")

"verify edit reservation feature"
CustomKeywords.'com.Parkquility.app.Keywords.PermitParking.verifyEditFeatureInMonthlyParkingAndResident1'(permitParkingData, "Ongoing", "Monthly")


"verify assign pick up  runner feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyAssignPickupRunnerFeature'(permitParkingData, 'Ongoing', "Monthly")

"verify Key In Car feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyKeyInCarFeature'(permitParkingData, 'Ongoing', "Monthly")

"verify key in feature becomes true"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyKeyInFeaturebecomestrue'(permitParkingData,"Ongoing", "Monthly")

"verify sms feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifySMSOption'(permitParkingData,"Ongoing", "Monthly")

"verify request car feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyRequestCarFeature'(permitParkingData, 'Ongoing', "Monthly")

"verify re check in feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyReCheckInFeature'(permitParkingData, 'Car Requested', 'Monthly')

"verify request car feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyRequestCarFeature'(permitParkingData, 'Ongoing', "Monthly")

"verify assign drop off runner feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyAssignDropOffFeature'(permitParkingData, 'Car Requested', "Monthly")

"verify Car On the way feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyCarOnTheWayFeature'(permitParkingData, 'Car Requested', "Monthly")

"verify Car ready feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyCarReadyFeature'(permitParkingData,"Car Requested", "10", "Monthly")
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyCarReadyFeature'(permitParkingData,"Car Requested", "now", "Monthly")

"verify CheckOut feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.checkoutReservation'(permitParkingData, "Car Requested", "Monthly")



