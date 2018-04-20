/**
 * 
 */
package sat.test.automation.utils;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import sat.test.automation.core.SuperScript;

// Class which will Generate the Error files and log files
public class WriteHTMLLog extends SuperScript {

	// Code to get module name, which is under execution
	static String moduleName = config.get("Suite.sheetName");
	static String URL = config.get("URL");
	static String Env = URL.substring(10, URL.indexOf(".")).toUpperCase();

	public static String htmlLogFilePath = config.get("LogFolder.htmlLog")
			+ Formatter.getDate() + "\\";
	public static String screenShotFilePath = config
			.get("LogFolder.screenShot") + Formatter.getDate() + "\\";

	public static String htmlLogFileName = moduleName
			+ "_TestScriptExecutionResult (" + Env + ")_"
			+ Formatter.getSystemDateTime() + ".html";

	public static String sDateTime = Formatter.getSystemDateTime();

	public static int pass_Count, fail_Count = 0;

	public static String PASS = "Pass", FAIL = "Fail", ERROR = "Error",
			VERIFYPASS = "VerifyPass", VERIFYFAIL = "VerifyFail",
			VERIFYERROR = "VerifyError";

	// Write the logs to a HTML file
	public static void writeHtmlLogs(String sToPrint) {
		String testCaseNumber = config
				.get("TestData.Sheet.Coloumn.Name.TestCaseNo");
		try {
			FileOutputStream htmlOut; // declare a file output object
			PrintStream printStream; // declare a print stream object
			File file2 = new File(htmlLogFilePath);
			File file1 = new File(file2.getParent());
			// System.out.println(file2 + " file detail ------>>>>>>");
			if (!file2.exists()) {
				file2.mkdirs();
			}

			htmlOut = new FileOutputStream(htmlLogFilePath + htmlLogFileName,
					true);
			// System.out.println(htmlOut.toString() +
			// "+++++++ html out------>>");

			// Connect print stream to the output stream
			printStream = new PrintStream(htmlOut);
			printStream.println(sToPrint);
			printStream.close();
		} catch (Exception e) {
			System.err.println("Error while writing logs to the file");
			e.printStackTrace();
		}

	}

	/*
	 * // Write the summary to a HTML file public static void writeHtmlSummary()
	 * { try { FileOutputStream htmlOut; // declare a file output object
	 * PrintStream printStream; // declare a print stream object File file2 =
	 * new File(testNGLogFilePath); File file1 = new File(file2.getParent()); //
	 * System.out.println(file2 + " file detail ------>>>>>>"); if
	 * (!file2.exists()) { file2.mkdirs(); }
	 * 
	 * // htmlOut = new FileOutputStream(testNGLogFilePath + //
	 * testNGLogFileName,true); // System.out.println(htmlOut.toString() + //
	 * "+++++++ html out------>>");
	 * 
	 * // Connect print stream to the output stream // printStream = new
	 * PrintStream(htmlOut); // printStream.println(); // printStream.close(); }
	 * catch (Exception e) {
	 * System.err.println("Error while writing summary to the file");
	 * e.printStackTrace(); } }
	 */

	// Write the Error logs to a HTML file
	public static void writeHtmlScreenShoftDeatils() {
		FileOutputStream out; // declare a file output object
		PrintStream printStream; // declare a print stream object

		try {
			// Check if the directory structure in the logFilePath exists either
			// create it

			// File file = new File(htmlLogFilePath);
			File file = new File(screenShotFilePath);
			// File fileFolder = new File(file.getParent());
			if (!file.exists()) {
				file.mkdirs();
			}
			// Creating a file output stream
			// out = new FileOutputStream(htmlLogFilePath + htmlLogFileName,
			// true);
			out = new FileOutputStream(htmlLogFilePath + htmlLogFileName, true);
			// Connect print stream to the output stream
			printStream = new PrintStream(out);
			printStream.println("<tr><th colspan = 5><a href = '"
					+ config.get("LogFolder.screenShot") + File.separator
					+ config.get("TestData.Sheet.Coloumn.Name.TestCaseNo")
					+ ".png" + "'> Error Screen Shot</a></th></tr>");
			printStream.close();
		} catch (Exception e) {
			System.err.println("Error while writing logs to the file");
		}
	}

	// Method to create folders in the required Structure
	public static String createFolders(String sFilePath) {

		String sAbsolutePath = "";
		try {

			File file = new File(sFilePath);
			if (!file.exists()) {
				file.mkdirs();
			}

			sAbsolutePath = file.getAbsolutePath();

		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return sAbsolutePath;
	}

	// This method is to take the screen shot of the screen when the error
	// occured

	public static void takeAScreenShotOfTheApp() throws Exception {
		Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle screenBounds = new Rectangle(0, 0, screenDim.width,
				screenDim.height);

		/*
		 * if (selenium
		 * .isElementPresent("//*[contains(text(),'Show system details')]")) {
		 * selenium.click("//*[contains(text(),'Show system details')]"); } else
		 * if (selenium.isElementPresent("check")) { selenium.click("check"); }
		 * else {
		 * 
		 * }
		 */

		Thread.sleep(2000);

		Robot robot = new Robot();
		BufferedImage image = robot.createScreenCapture(screenBounds);

		File screenshotFile = new File(config.get("LogFolder.screenShot")
				+ File.separator
				+ config.get("TestData.Sheet.Coloumn.Name.TestCaseNo") + ".png");
		createFolders(screenshotFile.getAbsolutePath());
		System.out.println(screenshotFile.getAbsolutePath() + " is the path");
		System.out.println(screenshotFile + " is the screen file");
		ImageIO.write(image, "png", screenshotFile);

	}

	public static String captureScreenShot(WebDriver ldriver, String filePath,
			String fileName) {
		String screenShotName = "";
		// Take screenshot and store as a file format
		File src = ((TakesScreenshot) ldriver).getScreenshotAs(OutputType.FILE);
		try {
			// now copy the  screenshot to desired location using copyFile
			// method
			screenShotName = filePath + "\\" + Formatter.getDate() + "\\"
					+ fileName + Formatter.getTime_HH_MM() + ".png";
			FileUtils.copyFile(src, new File(screenShotName));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return screenShotName;
	}

	public static void deleteHtmlLogFileIfAlreadyExists() {
		File file3 = new File(config.get("LogFolder") + "\\TestCase_"
				+ config.get("TestData.Sheet.Coloumn.Name.TestCaseNo")
				+ ".html");
		if (file3.exists()) {
			boolean b = file3.delete();
			if (b) {
				System.out
						.println("The file was already existed. And Successfully Deleted");
			}
		}
	}

	public static void writeHtmlWebDeriverScreenShotDeatils(
			String captureScreenShot) {
		// TODO Auto-generated method stub

		FileOutputStream out; // declare a file output object
		PrintStream printStream; // declare a print stream object

		try {
			// Check if the directory structure in the logFilePath exists either
			// create it

			File file = new File(htmlLogFilePath);
			// File fileFolder = new File(file.getParent());
			if (!file.exists()) {
				file.mkdirs();
			}
			// Creating a file output stream
			out = new FileOutputStream(htmlLogFilePath + htmlLogFileName, true);

			// Connect print stream to the output stream
			printStream = new PrintStream(out);
			printStream.println("<tr><th colspan = 5><a href = '"
					+ captureScreenShot + "'> Error Screen Shot</a></th></tr>");
			printStream.close();
		} catch (Exception e) {
			System.err.println("Error while writing logs to the file");
		}

	}

	public static void writeHtmlLogs(String status, String message) {
		String testCaseNumber = config
				.get("TestData.Sheet.Coloumn.Name.TestCaseNo");
		try {
			FileOutputStream htmlOut; // declare a file output object
			PrintStream printStream; // declare a print stream object
			File file2 = new File(htmlLogFilePath);
			File file1 = new File(file2.getParent());
			// System.out.println(file2 + " file detail ------>>>>>>");
			if (!file2.exists()) {
				file2.mkdirs();
			}

			htmlOut = new FileOutputStream(htmlLogFilePath + htmlLogFileName,
					true);
			// System.out.println(htmlOut.toString() +
			// "+++++++ html out------>>");
			String sToPrint = null;
			if (status.equalsIgnoreCase("pass")) {

				sToPrint = "<tr><td>"
						+ message
						+ "</td><td><font face='elephant' color='green'><b>PASS</b></td></tr>";
			} else if (status.equalsIgnoreCase("fail")) {
				String hyperlink = "<a href = '"+captureScreenShot()+"'>"+message+"</a>";
				sToPrint = "<tr><td><font face='elephant' color='red'><b>"
						+ hyperlink
						+ "</td><td><font face='elephant' color='red'><b>FAIL</b></td></tr>";

			} else if (status.equalsIgnoreCase("error")) {


				String hyperlink = "<a href = '"+captureScreenShot()+"'>"+message+"</a>";
				sToPrint = "<tr><td><font face='elephant' color='red'><b>"
						+ hyperlink
						+ "</td><td><font face='elephant' color='red'><b>Error</b></td></tr>";
			} else if (status.equalsIgnoreCase("verifypass")) {

				sToPrint = "<tr><td>"
						+ message
						+ "</td><td><font face='elephant' color='green'><b>Verification Pass</b></td></tr>";
			} else if (status.equalsIgnoreCase("verifyfail")) {
				String hyperlink = "<a href = '"+captureScreenShot()+"'><font face='elephant' color='red'>"+message+"</a>";
				sToPrint = "<tr><td><font face='elephant' color='red'><b>"
						+ hyperlink
						+ "</td><td><font face='elephant' color='red'><b>Verification Failed</b></td></tr>";
			} else if (status.equalsIgnoreCase("verifyerror")) {
				String hyperlink = "<a href = '"+captureScreenShot()+"'>"+message+"</a>";
				sToPrint = "<tr><td><font face='elephant' color='red'><b>"
						+ hyperlink
						+ "</td><td><font face='elephant' color='red'><b>Verification Error</b></td></tr>";
				// sToPrint = "<tr><td><font face='elephant' color='red'><b>"
				// + message
				// +
				// "</td><td><font face='elephant' color='red'><b>Verification Error</b></td></tr>";
				;
			} else {
				sToPrint = message;
			}

			// Connect print stream to the output stream
			printStream = new PrintStream(htmlOut);
			printStream.println(sToPrint);
			printStream.close();
		} catch (Exception e) {
			System.err.println("Error while writing logs to the file");
			e.printStackTrace();
		}

	}
	
}
