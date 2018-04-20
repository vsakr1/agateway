package sat.test.automation.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.Reporter;

import sat.test.automation.core.SuperScript;
import sat.test.automation.scripts.BaseScriptRunner;
import jxl.Sheet;
import jxl.Workbook;

public class ScriptExecutorReader extends SuperScript {

	// static HashMap<String, String> scriptExecutorMap = new HashMap();
	static List<List<String>> scriptExecutorDataInList = new ArrayList<List<String>>();

	/*
	 * public static HashMap<String, String> readScriptExecutorToHashMap() {
	 * Workbook wb = null; Sheet ws = null; String tname = null; String runmode
	 * = null;
	 * 
	 * try { //System.out.println(config.size()+"is the size"
	 * +config.get("Suite.Path")+ "Inside script executer"); wb =
	 * Workbook.getWorkbook(new File( config.get("Suite.Path"))); ws =
	 * wb.getSheet(config.get("Suite.sheetName"));
	 * 
	 * int scriptExecutorRowsCount = ws.getRows(); int
	 * testcaseNameStartRowNumber = 1;
	 * 
	 * for (int scriptExecutorRowCounter = testcaseNameStartRowNumber;
	 * scriptExecutorRowCounter < scriptExecutorRowsCount;
	 * scriptExecutorRowCounter++) {
	 * 
	 * // checking run mode: runmode = ws.getCell(4,
	 * scriptExecutorRowCounter).getContents(); tname = ws.getCell(2,
	 * scriptExecutorRowCounter).getContents(); // initialize testDataReader
	 * sheet: // baseScriptRunnerObj.initialiseTestdata();
	 * 
	 * // Starts html log: // startHtmlLog();
	 * 
	 * if (runmode.equals("Y")) { scriptExecutorMap.put(tname, runmode); //
	 * System.out.println("tscript  ="+tname+ //
	 * "  value = "+scriptExecutorMap.get(tname)); // logger.info(
	 * "..................execute the script as the runmode for test script "+
	 * // tname+ " is "+runmode); //
	 * baseScriptRunnerObj.executeTestData(testData); } else { // logger.info(
	 * "..................Do not execute the script as the runmode for test script "
	 * + // tname+ " is "+runmode); } }
	 * 
	 * } catch (Exception ioe) { ioe.printStackTrace(); // logger.error(ioe); }
	 * 
	 * return scriptExecutorMap; }
	 */

	public static List<List<String>> readScriptExecutorToArrayList() {
		Workbook wb = null;
		Sheet ws = null;
		String tname = null;
		String runmode = null;
		String tsheet = null;
		String tdescription = null;

		try {

			wb = Workbook.getWorkbook(new File(config.get("Suite.Path")));
			// System.out.println("No. Of Sheets in ScriptExecutor workbook:"+wb.getNumberOfSheets());
			// for(int sheetNo=0; sheetNo<wb.getNumberOfSheets();sheetNo++)
			// {
			// System.out.println("Sheet No: "+sheetNo+" And Sheet Name: "+wb.getSheet(sheetNo).getName());
			// }
		//	System.out.println("Hi");
			ws = wb.getSheet(config.get("Suite.sheetName"));
			//System.out.println(config.get("Suite.sheetName").toString());
			String s = config.get("Suite.sheetName").toString();
		//	System.out.println("s value is"+s);
			// ws = wb.getSheet(sheetNo);
			int scriptExecutorRowsCount = ws.getRows();
			int testcaseNameStartRowNumber = 1;

			for (int scriptExecutorRowCounter = testcaseNameStartRowNumber; scriptExecutorRowCounter < scriptExecutorRowsCount; scriptExecutorRowCounter++) {

				List<String> oneRowStepData = new ArrayList<String>();

				runmode = ws.getCell(4, scriptExecutorRowCounter).getContents();
				tname = ws.getCell(2, scriptExecutorRowCounter).getContents();
				tsheet = ws.getCell(1, scriptExecutorRowCounter).getContents();
				tdescription = ws.getCell(3, scriptExecutorRowCounter)
						.getContents();

				// checking run mode:
				if (runmode.equals("Y")) {
					
					//System.out.println("Testcases to be excuted are "+tname);

					oneRowStepData.add(tname);
					oneRowStepData.add(tsheet);
					oneRowStepData.add(tdescription);
					// System.out.println("Store => tname = "+oneRowStepData.get(0)+
					// " & tsheet = "+oneRowStepData.get(1) +
					// " & tdescription ="+oneRowStepData.get(2));

					scriptExecutorDataInList.add(oneRowStepData);
				//	System.out.println("NO");
				}
				/*else if (runmode.isEmpty()) {
				  int count=1;
				  System.out.println("BLANK");
				  if((oneRowStepData.get(0).equals(" ")) && (oneRowStepData.get(1).equals(" ")) && (oneRowStepData.get(2).equals(" ")) && count > 1)
						  {
					  
					    System.out.println("BLANK");
					    count ++;
					    
						  }
				}*/

			}
			
			// System.out.println(".................................");
			// }
		} catch (Exception ioe) {
			ioe.printStackTrace();
			System.err
					.println("Config file path is wrong or config file is missing.");
			Reporter.log("Error : Config file missing or path is incorrect");
			// logger.error(ioe);
		}
		
		
		return scriptExecutorDataInList;
	}

}
