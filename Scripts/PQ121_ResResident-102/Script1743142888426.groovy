import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

"Verify All features of Resident1 reservation"

"Get data from excel sheet"
def residentData = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('ResidentData (1)', 'R12')

"Click on Reservation link"
WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

"Click on resident button"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.clickOnResCategoryButton'("Resident1")

"verify info on Resident as per created in residents section"
CustomKeywords.'com.Parkquility.app.Keywords.Resident1Page.verifyCardDetailsInResidents'(residentData)

"make checkIn reservation"
CustomKeywords.'com.Parkquility.app.Keywords.Resident1Page.makeCheckInReservation'(residentData, "Hide")

"verify information on checked In reservation in Ongoing section"
CustomKeywords.'com.Parkquility.app.Keywords.Resident1Page.verifyCarInfo'(residentData, "Ongoing")


"verify details in edit page as entered"
CustomKeywords.'com.Parkquility.app.Keywords.Resident1Page.verifyEditReservationPageInResident'(residentData, 'Ongoing', "Resident1")


"verify edit reservation feature with updating data"
CustomKeywords.'com.Parkquility.app.Keywords.PermitParking.verifyEditFeatureInMonthlyParkingAndResident1'(residentData, "Ongoing", "Resident1")


"verify assign pick up  runner feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyAssignPickupRunnerFeature'(residentData, 'Ongoing', "Resident1")

"verify Key In Car feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyKeyInCarFeature'(residentData, 'Ongoing', "Resident1")

"verify key in feature becomes true"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyKeyInFeaturebecomestrue'(residentData,"Ongoing", "Resident1")

"verify sms feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifySMSOption'(residentData,"Ongoing", "Resident1")

"verify request car feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyRequestCarFeature'(residentData, 'Ongoing', "Resident1")

"verify re check in feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyReCheckInFeature'(residentData, 'Car Requested', 'Resident1')

"verify request car feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyRequestCarFeature'(residentData, 'Ongoing', "Resident1")


"verify assign drop off runner feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyAssignDropOffFeature'(residentData, 'Car Requested', "Resident1")


"verify Car On the way feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyCarOnTheWayFeature'(residentData, 'Car Requested', "Resident1")


"verify Car ready feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyCarReadyFeature'(residentData,"Car Requested", "30", "Resident1")
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyCarReadyFeature'(residentData,"Car Requested", "now", "Resident1")

"verify assign pick up runner feature in car requested section"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.verifyAssignPickupRunnerFeature'(residentData, 'Car Requested', "Resident1")


"verify CheckOut feature"
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.checkoutReservation'(residentData, "Car Requested", "Resident1")






