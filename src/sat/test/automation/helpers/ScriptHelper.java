package sat.test.automation.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import sat.test.automation.core.SuperScript;
import sat.test.automation.core.TestDataReader;
import sat.test.automation.utils.WriteHTMLLog;

public class ScriptHelper extends SuperScript {

	public static String sScreenName = "Login Screen";
	public static final String SSOID = "id=username";
	public static final String PASSWORD = "id=password";
	public static final String SIGNIN = "name=Submit";

	public void initialiseTestdata() {

		
		TestDataReader testDataReader = new TestDataReader();
		testData = testDataReader.readTestDataExcelSheet(config
				.get("TestData.ExcelPath"));
		// System.out.println(testData.size() + " ***** test data size");

	}

	public void loginToPAMApplication(String sso, String password) {
		try {
			
			String URL = config.get("URL");
			String CloudEastLogin = config.get("CloudEastLogin");
			
			if(URL.contains("cloudeast"))
			{
				WebElement UserListBox = driver.findElement(By.id("app_user_id"));
				Select UserSelection=new Select(UserListBox);
				UserSelection.selectByVisibleText(CloudEastLogin);
				Thread.sleep(5000);
				driver.findElement(By.name("commit")).click();
				String sFieldName = "logging in as the user: "+CloudEastLogin;
				String WidgetType = null, sso1 = "";
				String sInputData = "";
				WriteHTMLLog
				.writeHtmlLogs(getFormatedHTMLPASSTestStep(sFieldName, WidgetType, sInputData, sso1));
			}
			
			else{
			
			actionDriver("TEXTBOX", SSOID, sso, "SSO ID", sScreenName);
			actionDriver("PASSWORD_TEXTBOX", PASSWORD, password, "Password", sScreenName);
			actionDriver("BUTTON", SIGNIN, "Click", "Login Button", sScreenName);
			Thread.sleep(25000);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
