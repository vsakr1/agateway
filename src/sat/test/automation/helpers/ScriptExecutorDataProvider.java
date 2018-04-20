package sat.test.automation.helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import org.testng.annotations.DataProvider;

import sat.test.automation.core.GlobalVariables;
import sat.test.automation.core.SuperScript;
import sat.test.automation.scripts.BaseScriptRunner;
import sat.test.automation.utils.ReadConfigFile;
import sat.test.automation.utils.ScriptExecutorReader;

public class ScriptExecutorDataProvider extends BaseScriptRunner {

	// static ScriptExecutorReader scriptExecutorReader = new
	// ScriptExecutorReader();

	// static HashMap<String, String> scriptExecutorMap = new HashMap();
	static List<List<String>> scriptExecutorDataInList = new ArrayList<List<String>>();

	

	@DataProvider(name = "ScriptExecutorInfo")
	public static Object[][] getSuiteDetails() {

		List<List<String>> suiteLists = ScriptExecutorReader
				.readScriptExecutorToArrayList();
		// System.out.println(lists.size() + "  : ----> is the list size");
		String[][] suiteArrayInfo = new String[suiteLists.size()][];
		String[] testCaseArrayInfo = new String[0];
		
		
		for (int i = 0; i < suiteLists.size(); i++) {
			suiteArrayInfo[i] = suiteLists.get(i).toArray(testCaseArrayInfo);
		}
		
//		for (int i = 0; i < suiteArrayInfo.length; i++) {
//			for (int j = 0; j < suiteArrayInfo[i].length; j++) {
//				System.out.println(suiteArrayInfo[i][j] + "This is the first test case to be executed" + i +">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//			}
//			
//		}
		
		return suiteArrayInfo;
	}
}
