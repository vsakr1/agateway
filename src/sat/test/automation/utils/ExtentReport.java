package sat.test.automation.utils;

import java.io.File;
import java.io.IOException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import sat.test.automation.core.GlobalVariables;
import sat.test.automation.core.SuperScript;
import sat.test.automation.scripts.AGScriptRunner;
import sat.test.automation.utils.*;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
//import com.relevantcodes.extentreports.common.ExtentTestManager;
//import com.sun.jna.platform.FileUtils;

 
public class ExtentReport extends  AGScriptRunner{
	
	//public static final ExtentReports extentreport=null;
	//public static ExtentTest ExtentLogger;
	private static ExtentReports extent;
	
	static ExtentTest logger;
	
	
	

	@Test
	public static   ExtentReports startReport()
	{
		 if (extent != null) return extent;
		 
		 //System.out.println(Extentreport);
		// System.out.println(config.get("Reportpath"));
				//extent = new ExtentReports ("C:\\dev\\views\\PAM-Automation-master\\SATFramework\\test-output\\ExtendReport.html",true);
		 extent = new ExtentReports (config.get("Reportpath")+"\\"+ Formatter.getDate() +"\\ExtentReport\\"+config.get("Suite.sheetName")+"-" +Formatter.getSystemDateTime() +".html",true);
		 //extent = new ExtentReports ("config.get(Reportpath)"+"Extendreport.html",true);
		 //extent = new ExtentReports ("C://Users//sv819597//Document//Books//extentreport.html", true);
		  
		extent
                .addSystemInfo("Host Name", "AGENCYGATEWAY")
                .addSystemInfo("Environment", "QA")
                .addSystemInfo("User Name", "NBCU USER");   
        
	  
         extent.loadConfig(new File("extent-config.xml"));
        
               return extent;
	}
		
	@Test
	public void passTest(){
		//extent.startTest("TestCaseName", "Description")
		//TestCaseName – Name of the test
		//Description – Description of the test
		//Starting test
		logger = extent.startTest("passTest");
		Assert.assertTrue(true);
		//To generate the log when the test case is passed
		logger.log(LogStatus.PASS, "Test Case Passed is passTest");
	}
	
	
	/*@Test
	public void failTest(){
		logger = extent.startTest("failTest");
		Assert.assertTrue(false);
		logger.log(LogStatus.PASS, "Test Case (failTest) Status is passed");
		
	}*/
	
	
	@Test
	public static String failTest(WebDriver driver,String screenName) throws IOException{
		logger = extent.startTest("failTest");
		Assert.assertTrue(false);
		logger.log(LogStatus.PASS, "Test Case (failTest) Status is passed");
		 String errorimage=logger.addScreenCapture(CaptureScreenShot.captureScreen(driver));
		return errorimage;
		
	}
	
	/*@Test
	public void skipTest(){
		logger = extent.startTest("skipTest");
		throw new SkipException("Skipping - This is not ready for testing ");
	}*/
	
	@AfterMethod
	public void getResult(ITestResult result){
		if(result.getStatus() == ITestResult.FAILURE){
			logger.log(LogStatus.FAIL, "Test Case Failed is "+result.getName());
			logger.log(LogStatus.FAIL, "Test Case Failed is "+result.getThrowable());
		}else if(result.getStatus() == ITestResult.SKIP){
			logger.log(LogStatus.SKIP, "Test Case Skipped is "+result.getName());
		}
		// ending test
		//endTest(logger) : It ends the current test and prepares to create HTML report
		extent.endTest(logger);
	}
	
	@Test
	public void attachscreenshots()
	{
		
		
	}
	@AfterSuite
	public void endReport(){
		// writing everything to document
		//flush() - to write or update test information to your report. 
                extent.flush();
                //Call close() at the very end of your session to clear all resources. 
                //If any of your test ended abruptly causing any side-affects (not all logs sent to ExtentReports, information missing), this method will ensure that the test is still appended to the report with a warning message.
                //You should call close() only once, at the very end (in @AfterSuite for example) as it closes the underlying stream. 
                //Once this method is called, calling any Extent method will throw an error.
                //close() - To close all the operation
                extent.close();
    }
	public static String addScreenCapture() throws IOException
	{
		
		String errorimage=CaptureScreenShot.captureScreen(driver);
		
		
		
		return errorimage;
		
	}
	
	
}