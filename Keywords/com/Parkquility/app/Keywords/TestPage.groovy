package com.Parkquility.app.Keywords

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import java.text.SimpleDateFormat
import java.util.Date
import internal.GlobalVariable

public class TestPage {


	public static void main(String args) {
		TestPage t = new TestPage()
		println("Hello world")
		def dateFormat = new SimpleDateFormat("MMM dd, yyyy")
		def currentDate = new Date()
		def formattedDate = dateFormat.format(currentDate)

		def dateFormat1 = new SimpleDateFormat("hh:mm a")
		def currentTime = new Date()
		def formattedTime = dateFormat1.format(currentTime)

		print("\n"+formattedDate+"\n")
		print(formattedTime)
	}
}
