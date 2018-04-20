package sat.test.automation.scripts;

import java.util.Iterator;
import java.util.List;

import sat.test.automation.core.ReadObjectRepository;
import sat.test.automation.helpers.ScriptHelper;

public class BaseScriptRunner extends ScriptHelper {
	String fieldName, screenName, widgetInfo, widgetType, action, tcData,
			widgetProperty;
	public ReadObjectRepository readObjectRepository = new ReadObjectRepository();

	public void executeTestData(List<List<String>> testData) {
		for (Iterator iterator = testData.iterator(); iterator.hasNext();) {
			List<String> testStepData = (List<String>) iterator.next();
			Object[] testStepsArray = testStepData.toArray();

			screenName = testStepsArray[0].toString();
			fieldName = testStepsArray[1].toString();
			widgetInfo = testStepsArray[2].toString();
			action = testStepsArray[3].toString();
			tcData = testStepsArray[4].toString();

			// System.out.println(widgetInfo + " &&&&&&&&&&");
			/**
			 * Code to get the widget property
			 */
			widgetProperty = objectRepo.get(widgetInfo).widgetProperty; // exception
																		// if
																		// there
																		// is no
																		// widget
																		// found
																		// on OR
																		// sheet
			widgetType = objectRepo.get(widgetInfo).widgetType; // exception if
																// there is no
																// widget found
																// on OR sheet

			// System.out.println(widgetProperty + " is the locator property");
			try {
				if (tcData == "N/A" || tcData.equalsIgnoreCase("N/A")) {

				} else {
					if (action.contains("Action")) {
						actionDriver(widgetType, widgetProperty, tcData,
								fieldName, screenName);
					} else if (action.contains("Verify")) {
						verifyDriver(widgetType, widgetProperty, tcData,
								fieldName, screenName);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception at base script runner : "+e.getMessage());
			}

		}

	}

}
