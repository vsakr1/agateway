package sat.test.automation.utils;

import sat.test.automation.core.SuperScript;
import sat.test.automation.utils.WriteHTMLLog;
import sat.test.automation.utils.WriteHTMLSummary;

public class TestHTMLLog extends SuperScript {
	public static void main(String[] args) {
testLog();
	}

	// public static void main(String[] args) {
	// // TODO Auto-generated method stub
	// startHtmlLog(config.get("TestData.Sheet.Coloumn.Name.TestCaseNo"),
	// config.get("TestData.Sheet.Coloumn.Name.TestCaseDescription"));
	//
	// /**
	// * This method to be used at the time of writing the test case summary
	// */
	// // WriteHTMLSummary.writeHtmlSummaryData(WriteHTMLSummary
	// // .printTestCaseDetails(
	// // config.get("TestData.Sheet.Coloumn.Name.TestCaseNo"),
	// // "NA", ""));
	//
	// /**
	// * This will be used at the time of action driver pass step
	// */
	// WriteHTMLLog.writeHtmlLogs(getFormatedHTMLPASSTestStep("sFieldName",
	// "WidgetType", "sInputData", "sScreenName"));
	//
	// /**
	// * This will be used at the time of action driver fail step
	// */
	//
	// WriteHTMLLog.writeHtmlLogs(getFormatedHTMLFailTestStep("sFieldName",
	// "WidgetType", "sInputData", "sScreenName"));
	// /**
	// * This should be used at the time of Error message to be logged
	// * specially for driverAction fail cases
	// */
	// WriteHTMLLog.writeHtmlLogs(getFormatedHTMLFailError("sFieldName",
	// "WidgetType", "sInputData", "sScreenName"));
	//
	//
	//
	// try {
	// WriteHTMLLog.takeAScreenShotOfTheApp();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// /**
	// * this for the error
	// */
	// WriteHTMLLog.writeHtmlScreenShoftDeatils();
	//
	//
	// /**
	// * This will be used at the time of verify driver pass step
	// * getFormatedHTMLVerifyPASSTestStep getFormatedHTMLVerifyPASSTestStep
	// * getFormatedHTMLVerifyPASSTestStep
	// */
	// WriteHTMLLog.writeHtmlLogs(getFormatedHTMLVerifyPASSTestStep(
	// "sFieldName", "WidgetType", "sInputData", " sample",
	// "sScreenName"));
	//
	// /**
	// * This will be used at the time of verify driver fail step
	// */
	// WriteHTMLLog.writeHtmlLogs(getFormatedHTMLVerifyFailTestStep(
	// "sFieldName", "WidgetType", "sInputData", "sample2",
	// "sScreenName"));
	//
	// /**
	// * This will be used at the time of verify driver error step
	// */
	// WriteHTMLLog.writeHtmlLogs(getFormatedHTMLVerifyFailError("sFieldName",
	// "WidgetType", "sInputData", "sScreenName"));
	//
	// /**
	// * Take screen shot
	// */
	// try {
	// WriteHTMLLog.takeAScreenShotOfTheApp();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// /**
	// * this for the error
	// */
	// WriteHTMLLog.writeHtmlScreenShoftDeatils();
	//
	// /**
	// * This to be used at the time of script execution end
	// */
	//
	// endHtmllog();
	//
	// }

	public static void testLog() {
		startHtmlLog(config.get("TestData.Sheet.Coloumn.Name.TestCaseNo"),
				config.get("TestData.Sheet.Coloumn.Name.TestCaseDescription"));

		/**
		 * This will be used at the time of action driver pass step
		 */
		WriteHTMLLog
				.writeHtmlLogs(WriteHTMLLog.PASS, "Hi this is success pass");
		WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.FAIL, "Hi this is fail pass");
		WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.ERROR, "Hi this is error pass");
		WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,
				"Successfully verified the field 'GenericAdminPageTitle' of Type-Label on screen AdminTabs <br> Expected Result : APPROVAL ASSIGNMENTS </br>Actual Result : APPROVAL ASSIGNMENTS ");
		WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,
				"Hi this is  verification failiour");
		WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYERROR,
				"Hi this is  verification error");

		endHtmllog();
	}
}
