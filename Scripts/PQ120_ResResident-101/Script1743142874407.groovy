import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def residentData = CustomKeywords.'com.Parkquility.app.common.CommonKeywords.getTestDataFromFile'('ResidentData (1)', 'R12')

"create new resident"
CustomKeywords.'com.Parkquility.app.Keywords.Resident1Page.createNewResident'(residentData)

"verify created resident details"
CustomKeywords.'com.Parkquility.app.Keywords.Resident1Page.verifyCreatedResidentDetails'(residentData)


"verify edit feature"
CustomKeywords.'com.Parkquility.app.Keywords.Resident1Page.verifyEditFeature'(residentData)

"verify created resident details"
CustomKeywords.'com.Parkquility.app.Keywords.Resident1Page.verifyCreatedResidentDetails'(residentData)

"verify created reservation is visible and verify its details"
//click on reservation link
WebUI.click(findTestObject('Object Repository/ReservationPage/link_reservation'))

//Click on resident button
CustomKeywords.'com.Parkquility.app.Keywords.ReservationPage.clickOnResCategoryButton'("Resident1")

//verify info on Resident as per created in residents section
CustomKeywords.'com.Parkquility.app.Keywords.Resident1Page.verifyCardDetailsInResidents'(residentData)

"verify deactivate feature and when click on deactivate feature then only reactivate and delete options are visible"
CustomKeywords.'com.Parkquility.app.Keywords.Resident1Page.verifyDeacivateFeature'(residentData)


"verify reactivate feature and when click on reactivate button then delete and reactivate button should not be visible"
CustomKeywords.'com.Parkquility.app.Keywords.Resident1Page.verifyReactivateFeature'(residentData)

"verify delete feature "
//CustomKeywords.'com.Parkquility.app.Keywords.Resident1Page.verifyDeleteFeature'(residentData)

