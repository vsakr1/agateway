package sat.test.automation.utils;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import sat.test.automation.core.GlobalVariables;
import sat.test.automation.core.SuperScript;

public class ResultSheetOP extends JDBCExcelConnection {
	private File resultFile, resultHistory;

	public void createResultLog() throws Exception {
//		// execStartTime = System.currentTimeMillis();
//		resultFile = new File(GlobalVariables.EXCEL_RESULT_PATH);
//		if (!resultFile.exists()) {
//			resultFile.getParentFile().mkdirs();
////			resultFile.mkdirs();
////			 openDatabase();
////			connection.openExcelConnection(EXCEL_RESULT_PATH);
//			try {
//				Statement stmt = connection.getGConnection().createStatement();
//				stmt.executeUpdate("CREATE TABLE AccountNumberInformation(TestCaseNo VARCHAR, CustomerName VARCHAR,AccountNumber VARCHAR,RequestID VARCHAR,TimeZoneExecuted VARCHAR,AppSubmitTime VARCHAR,CustomerInfo VARCHAR); ");
//				stmt.executeUpdate("CREATE TABLE TestCaseNo4(AccountNumber VARCHAR,UsedStatus VARCHAR,CustomerInfo VARCHAR,SubmittedTime VARCHAR); ");
//				stmt.executeUpdate("CREATE TABLE TestCaseNo6(AccountNumber VARCHAR,UsedStatus VARCHAR,CustomerInfo VARCHAR,SubmittedTime VARCHAR); ");
//				stmt.executeUpdate("CREATE TABLE TestCaseNo21(AccountNumber VARCHAR,UsedStatus VARCHAR,CustomerInfo VARCHAR,SubmittedTime VARCHAR); ");
//				stmt.executeUpdate("CREATE TABLE TestCaseNo23(AccountNumber VARCHAR,UsedStatus VARCHAR,CustomerInfo VARCHAR,SubmittedTime VARCHAR); ");
//				stmt.executeUpdate("CREATE TABLE TestCaseNo28(AccountNumber VARCHAR,UsedStatus VARCHAR,CustomerInfo VARCHAR,SubmittedTime VARCHAR); ");
//				stmt.executeUpdate("CREATE TABLE TestCaseNo30(AccountNumber VARCHAR,UsedStatus VARCHAR,CustomerInfo VARCHAR,SubmittedTime VARCHAR); ");
//				stmt.executeUpdate("CREATE TABLE TestCaseNo46(AccountNumber VARCHAR,UsedStatus VARCHAR,CustomerInfo VARCHAR,SubmittedTime VARCHAR); ");
//				stmt.executeUpdate("CREATE TABLE TestCaseNo48(AccountNumber VARCHAR,UsedStatus VARCHAR,CustomerInfo VARCHAR,SubmittedTime VARCHAR); ");
//
//			} catch (Exception e) {
//				System.err.println("Exception is " + e);
//				e.printStackTrace();
//			} finally {
//				connection.closeExcelConnection();
//			}
//		}
//
//	}
//	public void logAccountNumberInfo(String testScenarioNo, String custName,
//			String accountNo, String requestIdOfApp, String timeZone,
//			String time, String custInfo) throws Exception {
//		try {
//			createResultLog();
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//
////		openDatabase();
//		connection.openExcelConnection(EXCEL_RESULT_PATH);
//		try {
//			Statement stmt = connection.getGConnection().createStatement();
//			stmt.executeQuery("Select * from [AccountNumberInformation$]");
//			stmt.executeUpdate("INSERT INTO [AccountNumberInformation$] VALUES ('"
//					+ testScenarioNo
//					+ "','"
//					+ custName
//					+ "','"
//					+ accountNo
//					+ "','"
//					+ requestIdOfApp
//					+ "','"
//					+ timeZone
//					+ "','"
//					+ time
//					+ "','" + custInfo + "');");
//		}
//
//		catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			connection.closeExcelConnection();
//		}
	}
}
