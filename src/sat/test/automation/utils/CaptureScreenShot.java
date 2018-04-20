package sat.test.automation.utils;


import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import sat.test.automation.core.SuperScript;



public class CaptureScreenShot extends SuperScript{
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd SSS");
	
	public static String captureScreen(WebDriver driver) throws IOException{
		/* Date date = new Date();
		TakesScreenshot screen = (TakesScreenshot) driver;
		File src = screen.getScreenshotAs(OutputType.FILE);
		
		String dest = "C:\\dev\\views\\PAM-Automation-master\\SATFramework//Test-ScreenShots//"+"screenName"+"-"+dateFormat.format(date)+".png";
				//System.getProperty("C:\\dev\\views\\PAM-Automation-master\\SATFramework")+"//Test-ScreenShots//"+"screenName"+"-"+dateFormat.format(date)+".png";
		
		File target = new File(dest);
		FileUtils.copyFile(src, target);
		
		return dest;*/
		
		
		String screenShotName = "";
		String filePath = config.get("LogFolder.screenShot");
		String fileName = config.get("TestData.Sheet.Coloumn.Name.TestCaseNo");
		// Take screenshot and store as a file format
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			// now copy the  screenshot to desired location using copyFile
			// method
			screenShotName = filePath + "\\" + Formatter.getDate() + "\\"
					+ fileName +"-"+ Formatter.getTime_HH_MM() + ".png";
			FileUtils.copyFile(src, new File(screenShotName));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		//System.out.println("Screen shot is stored at  : " + screenShotName);
		return screenShotName;
	}
	
	
	public static String generateFileName(ITestResult result){
		Date date = new Date();
		String fileName = result.getName()+ "_" + dateFormat.format(date);
		return fileName;
		
	}

}