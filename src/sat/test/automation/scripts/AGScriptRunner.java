package sat.test.automation.scripts;

import java.io.File;
import java.io.IOException;
//import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import sat.test.automation.utils.*;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import sat.test.automation.helpers.ScriptExecutorDataProvider;
import sat.test.automation.utils.ExtentReport;
import sat.test.automation.utils.WriteHTMLLog;


public class AGScriptRunner extends BaseScriptRunner {
public static int NoOfTcForExecution;
public static String browserName, version;
ExtentReports extentreport = ExtentReport.startReport();
public static ExtentTest ExtentLogger;
	@Test(dataProvider = "ScriptExecutorInfo", dataProviderClass = ScriptExecutorDataProvider.class)
	public void run(String tnamedata1, String tsheetdata1,
			String tdescriptiondata1) throws Exception {
        
		/*System.out.println("tnamedata1is "+tnamedata1);
		System.out.println("tsheetdata1"+tsheetdata1);
		System.out.println("tdescriptiondata1"+tdescriptiondata1);*/
		String tnamedata;
		String tsheetdata;
		String tdescriptiondata;
		tnamedata = tnamedata1.toString();
		tsheetdata = tsheetdata1.toString();
		tdescriptiondata = tdescriptiondata1.toString();

		config.put("TestData.Sheet.Coloumn.Name.TestCaseNo", tnamedata);
		config.put("TestData.SheetName", tsheetdata);
		config.put("TestData.Sheet.Coloumn.Name.TestCaseDescription",
				tdescriptiondata);
       System.out.println(config.get("TestData.Sheet.Coloumn.Name.TestCaseNo"));
		System.out
				.println("****************************************************************************************");
		System.out.println("Test Case Name = "
				+ config.get("TestData.Sheet.Coloumn.Name.TestCaseNo"));
		System.out.println("Test Sheet = " + config.get("TestData.SheetName"));
		System.out
				.println("Test Description = "
						+ config.get("TestData.Sheet.Coloumn.Name.TestCaseDescription"));
		System.out
				.println("****************************************************************************************");

		startHtmlLog(config.get("TestData.Sheet.Coloumn.Name.TestCaseNo"),
				config.get("TestData.Sheet.Coloumn.Name.TestCaseDescription"));
//		WriteHTMLSummary.writeHtmlSummaryData(WriteHTMLSummary
//				.printTestCaseDetails(
//						config.get("TestData.Sheet.Coloumn.Name.TestCaseNo"),
//						"NA", ""));

		// // Initialise the test data
		initialiseTestdata(); // exception for test data sheet
		 ExtentLogger = extentreport.startTest(tnamedata1);
		//ExtentLogger.log(LogStatus.PASS, "URL launched");
		createWebDriverInstance(config.get("broswer"));
		// createWebDriverInstance("chrome"); // exceptions for no chrome
		// browser

		launchApplication(config.get("URL")); // exception for no
		// instance
		loginToPAMApplication(config.get("SSO_ID"), config.get("Password"));
		
	/*
	 TestArea for extent reports */
	 
		 //String errorimage=ExtentLogger.addScreenCapture(CaptureScreenShot.captureScreen(driver));
	  
	 // ExtentLogger.log(LogStatus.FAIL,ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Google Search Button is not displaying");
	  
	  
	  
	 
	 /*                        */
		
		
		
		
		
		
		
		
		
		
		// // loginToPAMApplication("206449875", "igate123a"); // exception
		// // for not launching application
		try {
			//introduce the extent reporyt here//
			
			
			executeTestData(testData);
		} catch (Exception e) {
			extentreport.flush();
			ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"The Input value is not present in on Screen: Nullpointer Exception" +e.toString());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//rep.flush();
			extentreport.flush();
			endHtmllog();
			 //driver.quit();
			//flush the extent report
			System.out
					.println("selenium instance is getting killed at the end of the script execution");
			killinstance();
			//WriteHTMLLog.copyTestNgFile();
		}
		//WriteHTMLLog.writeHtmlSummary();
		//WriteHTMLLog.copyTestNgFile();
	
	}

}
