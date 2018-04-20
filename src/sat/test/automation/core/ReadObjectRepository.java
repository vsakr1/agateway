package sat.test.automation.core;

import java.io.File;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.PropertyConfigurator;

public class ReadObjectRepository extends SuperScript {
	int count = 0;

	/**
	 * Create a constructor and call the generateObject method to create OR map
	 * 
	 */

	public ReadObjectRepository() {
		generateObjectRepository("");
		PropertyConfigurator.configure("log4j.properties");
		// System.out.println(count + " is the read obj repo const");
		// count++;
	}

	/**
	 * Method to return the screen object
	 * 
	 * @param widgetDetails
	 * @return
	 */
	public WidgetsInfo getScreenObject(String widgetDetails) {

		return objectRepo.get(widgetDetails);
	}

	/**
	 * method to generate the Object Repository MAP from the OR excel sheet
	 * 
	 * @param OR_PATH
	 */
	public void generateObjectRepository(String OR_PATH) {
		String fieldName, propertyId, widgetType;

		File excelSheet = null;
		Workbook workbook = null;
		/**
		 * *****************************************
		 * *****Exception***************************
		 * *****************************************
		 * 
		 * exception if there is no excel OR sheet wrong path wrong template
		 * improper formating
		 */
		try {
			// System.out.println(config.get("PATH.OR") + "is the sheet");
			Workbook wb = Workbook.getWorkbook(new File(config.get("PATH.OR")));
			// System.out.println(wb.getSheets().length + " is the work book");
			for (int i = 1; i < wb.getSheets().length; i++) {
				Sheet sheet = wb.getSheet(i);
				String strSheetName = sheet.getName();
				// System.out.println(sheet.getName());
				// System.out.println();
				int columnsCount = sheet.getColumns();
				int rowsCount = sheet.getRows();

				for (int j = 0; j < rowsCount; j++) {

					fieldName = sheet.getCell(0, j).getContents();
					propertyId = sheet.getCell(1, j).getContents();
					widgetType = sheet.getCell(2, j).getContents();
					// System.out.println(fieldName + " " + propertyId + " "
					// + widgetType);
					WidgetsInfo widgetInfo = new WidgetsInfo(propertyId,
							widgetType);
					objectRepo.put(strSheetName + "." + fieldName, widgetInfo);
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		ReadObjectRepository objectRepository = new ReadObjectRepository();
		objectRepository.generateObjectRepository("");

		// System.out.println(objectRepo.size());
		// for (String key : objectRepo.keySet())
		// System.out.println(key + " - " + objectRepo.get(key));
	}

}
