package sat.test.automation.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.testng.Reporter;
import jxl.Sheet;
import jxl.Workbook;

public class TestDataReader extends SuperScript {

	public List<List<String>> readTestDataExcelSheet(
			String testDataExcelFilePath) {

		int counter = 0;
		// System.out.println(" readTestDataExcelSheet counter " + counter);
		counter++;
		File excelSheet = null;
		Workbook workbook = null;
		List<List<String>> completeTestDataInList = new ArrayList<List<String>>();

		try {
			Workbook wb = Workbook.getWorkbook(new File(config
					.get("TestData.ExcelPath")));
			
			//Sheet sheet = "test";
			System.out.println( "sheet name"+wb.getSheet(config.get("TestData.SheetName").toString()));
			Sheet sheet = wb.getSheet(config.get("TestData.SheetName"));
			// Sheet sheet = wb.getSheet(0);

			// int columnsCount = sheet.getColumns();
			int columnsCount = sheet.getColumns();
			int rowsCount = sheet.getRows();
			int testDataStartRowNumber = 2;
			//int testDataStartRowNumber = Integer.parseInt(config.get("TestData.Sheet.Row.testDataStartRowNumber")) - 1;
			// System.out.println(config
			// .get("TestData.Sheet.Coloumn.Name.TestCaseNo") +
			// " inside test data reader filesssssss----------------***********");
			// System.out.println("Sheet Name\t"+wb.getSheet(sheetNo).getName());
			for (int row = testDataStartRowNumber; row < rowsCount; row++) {
				List<String> oneRowStepData = new ArrayList<String>();
				/**
				 * code based on name
				 */

				oneRowStepData
						.add(sheet
								.getCell(
										sheet.findCell(
												config.get("TestData.Sheet.Coloumn.Name.ScreenClassName"))
												.getColumn(), row)
								.getContents());
				oneRowStepData
						.add(sheet
								.getCell(
										sheet.findCell(
												config.get("TestData.Sheet.Coloumn.Name.Field"))
												.getColumn(), row)
								.getContents());
				oneRowStepData
						.add(sheet
								.getCell(
										sheet.findCell(
												config.get("TestData.Sheet.Coloumn.Name.WidgetInfo"))
												.getColumn(), row)
								.getContents());
				oneRowStepData
						.add(sheet
								.getCell(
										sheet.findCell(
												config.get("TestData.Sheet.Coloumn.Name.Action"))
												.getColumn(), row)
								.getContents());
				oneRowStepData
						.add(sheet
								.getCell(
										sheet.findCell(
												config.get("TestData.Sheet.Coloumn.Name.TestCaseNo"))
												.getColumn(), row)
								.getContents());
				completeTestDataInList.add(oneRowStepData);
			}

		} catch (Exception ioe) {
			ioe.printStackTrace();
			logger.error(ioe);
			Reporter.log("Please verify if the test data path, Sheet name or colomn name are correct");
			Reporter.log("Verify if config file path is correct : "+ GlobalVariables.configFilePath);
			logger.info("Please verify if the test data path, Sheet name or colomn name are correct");
			logger.info("Verify if config file path is correct : "+ GlobalVariables.configFilePath);
		}
		return completeTestDataInList;

	}

	public static void main(String[] args) {
		TestDataReader dataReader = new TestDataReader();
		List<List<String>> completeTestDataInList11 = dataReader
				.readTestDataExcelSheet("");
		for (Iterator iterator = completeTestDataInList11.iterator(); iterator
				.hasNext();) {
			List<String> list = (List<String>) iterator.next();
			System.out.println(list);

		}

	}
}
