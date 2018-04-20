package sat.test.automation.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import sat.test.automation.core.SuperScript;
import sat.test.automation.scripts.AGScriptRunner;


public class WriteHTMLSummary extends SuperScript{

	public  static String htmlLogFilePath = config.get("LogFolder.htmlLogSummary");
	public static String htmlLogFileName = "TestScriptExecutionSummary_"+ Formatter.getSystemDateTime() + ".html";
	public static String sDateTime = Formatter.getSystemDateTime();
	public static int pass_Count, fail_Count = 0;
	public static long scriptStartExeTime, scriptEndExeTime,scriptExeDiffTime;
	
	
	
	
	// Write the logs to a HTML file
	public static  void writeHtmlSummaryData(String sToPrint) 
	{
		try 
		{
			FileOutputStream htmlOut; // declare a file output object
			PrintStream printStream; // declare a print stream object
			File file2 = new File(htmlLogFilePath);
			File file1 = new File(file2.getParent());
			if (!file2.exists()) 
			{
				file2.mkdirs();
			}

			htmlOut = new FileOutputStream(htmlLogFilePath + htmlLogFileName,true);
			printStream = new PrintStream(htmlOut);
			printStream.println(sToPrint);
			printStream.close();
		} 
		catch (Exception e) 
		{
			System.err.println("Error while writing logs to the file");
			e.printStackTrace();
		}
	}

	// Write the Error logs to a HTML file
	public static void writeHtmlErrors() {
		FileOutputStream out; // declare a file output object
		PrintStream printStream; // declare a print stream object
		try 
		{
			// Check if the directory structure in the logFilePath exists either create it
			File file = new File(htmlLogFilePath);
			if (!file.exists()) 
			{
				file.mkdirs();
			}
			
			// Creating a file output stream
			out = new FileOutputStream(htmlLogFilePath + htmlLogFileName, true);			
		} 
		catch (Exception e) 
		{
			System.err.println("Error while writing logs to the file");
		}
	}
	
	// Method to create folders in the required Structure
	public static String createFolders(String sFilePath) 
	{
		String sAbsolutePath = "";
		try 
		{
			File file = new File(sFilePath);
			if (!file.exists()) 
			{
				file.mkdirs();
			}
			sAbsolutePath = file.getAbsolutePath();
		} 
		catch (RuntimeException e) 
		{
			e.printStackTrace();
		}
		return sAbsolutePath;
	}
	

	// Starts Writing data into HTML log file
	public static void startHtmlLog() {
		  try{
		    scriptStartExeTime = System.currentTimeMillis();
		  } catch (Exception ioe) {
		            ioe.printStackTrace();
		            
		  }
		  WriteHTMLSummary.writeHtmlSummaryData("<html><b><center><h3>Automation Execution Summary</h3></center></b>"
		  								+ "<b>Execution Details</b>"
		  								+ "<table border='1'></br><tr><td><b>Execution Date & Time</b></td><td>"
				  						+Formatter.getSystemDateTime()
				  						+"</td></tr>"
				  						+ "<tr><td><b>Environment</b></td><td>"
				  						+ config.get("ENV")
				  						+"</td></tr>"
				  						+ "<tr><td><b>URL</b></td><td>"
				  						+ config.get("URL")
				  						+ "</td></tr>"
				  						+ "<tr><td><b>Module</b></td><td>"
				  						+ config.get("Suite.sheetName")
				  						+ "</td></tr>"
				  						+ "<tr><td><b>SSO Account Used For Execution</b></td><td>"
				  						+ config.get("SSO_ID")
				  						+ "</td></tr>"
				  						+ "<tr><td><b>Total Execution Time</b></td><td>"
				  						+ "null"
				  						+ "</td></tr>"
				  						+ "<tr><td><b>Browser</b></td><td>"
				  						+ "null"
				  						+ "</td></tr>"
				  						+ "</table>"
				  						+ "<br><b>Execution Summary</b>"
				  						+ "<table border = '1'><tr><th>Total Test Cases</th>"
		                                + "<th>Pass</th>"
				  						+ "<th>Fail</th>"
		                                + "<th>Duration (sec)</th></tr>"
		                                + "<tr><th>"
		                                + AGScriptRunner.NoOfTcForExecution
		                                + "</th><th></th><th></th><th></th></tr></table>"
		                                + "<br><b>Test Case Wise Execution Details</b>"
		                                + "<table border = '1'><tr><th>Sl. No.</th>"
		                                + "<th>Test Case ID</th>"
				  						+ "<th>Result</th>"
		                                + "<th>Comments</th></tr>");		  
		}
	
	
	//Ends writing data into HTML log file
	public static void endHtmllog() 
	{
    	scriptEndExeTime = System.currentTimeMillis();
   		scriptExeDiffTime=scriptEndExeTime-scriptStartExeTime;
    	String scriptDuration=TimeUnit.MILLISECONDS.toSeconds(scriptExeDiffTime)+" seconds";
    	WriteHTMLSummary.writeHtmlSummaryData(
                 "</th></table><i><small>Time Taken for Execution: </i></small>"
                 + "<i><small>"+scriptDuration+"</i></small><br><br>"
                 + "</table></body></html>");
                 
    
}
	static int slN0=0;
	public static String printTestCaseDetails(String TestCaseID,String Result,String Comments) 
	{
		slN0++;
		return "<th>"
				+ slN0
				+ "</td><td>"
				+ TestCaseID
				+ "</td><td>"
				+ Result
				+ "</td><td>"
				+ Comments
				+ "</td></tr>";
		
	}
	
	
	//Code for adding Pass result info. in HTML log file
	public static String getFormatedHTMLPASSTestStep(String step) 
	{
		return "<tr><td>"
				+ step
				+ "</td>"
				+ "<td><font face='Arial' color='green'><b>Pass</b></td></tr>";
	}
	
	//Code for adding Fail result info. in HTML log file
	public static String getFormatedHTMLFailTestStep(String step) 
	{
		return "<tr><td><font face='Arial' color='red'><b>"
				+ step
				+ "</b></td>"
				+ "<td><font face='Tahoma' color='red'><b>Fail</b></td></tr>";
	}
	
	//Log info
	public static String getFormatedHTMLFailScreenshotLink(String imgPath) 
	{
		return "<tr><td><font face='Arial' color='red'><b><i>Screenshot Captured. Refer to: <a href="
				+imgPath
				+ ">Screenshot</a></i></b></td>";
	}

}
