package sat.test.automation.core;

import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import com.relevantcodes.extentreports.LogStatus;
import sat.test.automation.utils.ExtentReport;
import sat.test.automation.utils.Formatter;
import sat.test.automation.utils.JDBCExcelConnection;
import sat.test.automation.utils.ReadConfigFile;
import sat.test.automation.utils.SQLConnector;
import sat.test.automation.utils.WriteHTMLLog;

public class SuperScript extends GlobalVariables {
	static String DealPacingID,AgencyID, BudgetYearID, MarketPlaceID;
	public static List<List<String>> testData;
	public static Map<String, WidgetsInfo> objectRepo = new HashMap<String, WidgetsInfo>();
	public static WebDriver driver;

	public static final Logger logger = Logger.getLogger("SuperScript");

	public static ReadConfigFile configFile = new ReadConfigFile();
	public static HashMap<String, String> config;
	public ResultSet resultSet;
	public static JDBCExcelConnection connection;

	public static final int TEXTBOX = 1, LISTBOX = 2, BUTTON = 3, LINK = 4,
			RADIOBUTTON = 5, CHECKBOX = 6, TEXTBOXEXIST = 7, LISTBOX_New = 8,
			BUTTONEXIST = 9, LINKEXIST = 10, RADIOBUTTONEXIST = 11,
			CHECKBOXEXIST = 12, LINKPartialText = 13, BUTTON_IMAGE = 14;
	public static final int TEXTBOX_EXIST = 100, LISTBOX_EXIST = 101,
			BUTTON_EXIST = 102, LINK_EXIST = 103, RADIOBUTTON_EXIST = 104,
			CHECKBOX_EXIST = 105, TEXTBOX_VALUE_EXIST = 106,
			LISTBOX_VALUE_EXIST = 107, BUTTON_ENABLE = 108, LINK_ENABLE = 109,
			RADIOBUTTON_SELECTED = 110, CHECKBOX_CHECKED = 111,
			LINKPartialText_EXIST = 112, BUTTON_IMAGE_EXIST = 113,
			LABAL_EXIST = 114;

	public static final String TextBox = "TextBox", AllTextbox = "AllTextbox",
			PASSWORD_TEXTBOX = "PASSWORD_TEXTBOX", Select2 = "Select2",
			Select3 = "Select3", Select4 = "Select4",ListBoxNew = "ListBoxNew",
			DynamicTableLink = "DynamicTableLink",
			DynamicTableNTLink = "DynamicTableNTLink", EnterKey = "EnterKey",
			MouseHover = "MouseHover", SiteMouseHover = "SiteMouseHover",
			ListBox = "ListBox", Button = "Button",
			Button_Image = "BUTTON_IMAGE", RadioButton = "RADIOBUTTON",
			Link = "Link", LinkPartialText = "LINKPartialText", Tab = "Tab", Refresh="Refresh",
			ScrollUp = "ScrollUp", PageDown = "PageDown",
			JsScrollDown = "JsScrollDown", Focus = "Focus",
			Minimize = "Minimize", Alert = "Alert", sleep = "sleep",
			navigateToTad = "navigateToTad", Clear = "Clear",
			GetDealPacingID = "GetDealPacingID",GetAgencyID = "GetAgencyID", GetBudgetYearID ="GetBudgetYearID" , GetMarketPlaceID = "GetMarketPlaceID",
			DB_ExecuteQuery = "DB_ExecuteQuery", DeleteDeal = "DeleteDeal",
			DeletePacing = "DeletePacing", Checkbox = "Checkbox",Navigate="Navigate",
			CHECKBOX2 = "CHECKBOX2",ListDataverify="ListDataverify";//ReadList= "ReadList" ,Listcompare= "Listcompare";
			
 
	public static String wbName;
	//public static  int count=0;
	//public  static String[][]  ListValues=new String[2][100];

	public static String PASS_REPORT = "<font color='GREEN'> ";
	public static String FAIL_REPORT = "<font color='RED'> ";
	public static String REPORT_END = " </font> ";

	public static long scriptStartExeTime, scriptEndExeTime, scriptExeDiffTime;

	public static int sleepTime = 1000;

	static {
		config = configFile.readConfigDataToHashMap(GlobalVariables.configFilePath, "=");
	}

	public SuperScript() {
		

	}

	/**
	 * @param browsertype
	 *            this method will create the webdriver instance for the browser
	 *            specified.
	 */
	public static void createWebDriverInstance(String browsertype) {
		try {
			// Runtime.getRuntime().exec("taskkill /F -IM iexplore.exe");
			// Thread.sleep(1000);
			// Runtime.getRuntime().exec("taskkill /F -IM firefox.exe");
			// Thread.sleep(1000);
			Runtime.getRuntime().exec("taskkill /F -IM chromedriver.exe");
			Thread.sleep(1000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Reporter.log(e.toString());
			Assert.fail(e.toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Reporter.log(e.toString());
			Assert.fail(e.toString());
		}

		driver = null;
		if (driver == null) {
			if (browsertype.equalsIgnoreCase("IE")) {
				DesiredCapabilities ieCapabilities = DesiredCapabilities
						.internetExplorer();
				ieCapabilities
				.setCapability(
						InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
				driver = new InternetExplorerDriver(ieCapabilities);
				// System.setProperty("webdriver.ie.driver",
				// "C:\\rsa-runtime\\IEDriverServer.exe");
				// driver = new InternetExplorerDriver();
			} else if (browsertype.equalsIgnoreCase("FF")) {
				driver = new FirefoxDriver();
			} else if (browsertype.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						"C:\\testautomation-runtime\\chromedriver.exe");
				DesiredCapabilities chromeCapabilities = DesiredCapabilities
						.chrome();
				chromeCapabilities.setJavascriptEnabled(true);
				String[] options = { "--ignore-certificate-errors" };
				chromeCapabilities.setCapability("chrome.switches",
						Arrays.asList(options));
				// driver = new RemoteWebDriver(DesiredCapabilities.chrome());
				driver = new ChromeDriver();
			} else {
				System.out.println("No specific browser type found");
				logger.error("No specific Broser Found");
				Reporter.log("No specific Broser Found");
			}

		} else {

			System.out.println("Webdriver Instance is not null");
			logger.error("Webdriver Instance is not null");
			Reporter.log("Webdriver Instance is not null");

		}
	}

	/**
	 * @param browserURL
	 *            this method will launch the application URL specified
	 */
	public static void launchApplication(String browserURL) {

		try {

			if (browserURL == null) {
				throw new NullPointerException(
						"Broser URL is NULL. Please check the config file");
			} else {
				driver.get(browserURL);
		 		Thread.sleep(sleepTime);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				Thread.sleep(sleepTime);
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Reporter.log(e.toString());
			Assert.fail("Test case failed due to : ", e);

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Reporter.log(e.toString());

			System.out.println("killing it at the time of launch");
			// killinstance();

			Assert.fail("Failed at the time of Launch Application : ", e);
		}
	}

	/**
	 * This Method will return true if the passed value to this function is not
	 * null otherwise it will return false
	 * 
	 * @param sValue
	 * @return
	 */
	public static boolean checkNull(String sValue) {
		return (sValue != null && sValue.compareTo("") != 0 && sValue
				.compareTo("NULL") != 0);
	}

	/**
	 * This method will perform the Actions required for different Fields
	 * 
	 * @param WidgetType
	 * @param sScreenWidget
	 * @param sInputData
	 * @param sFieldName
	 * @param sScreenName
	 * @throws Exception
	 */

	public static void actionDriver(String WidgetType, String sScreenWidget,
			String sInputData, String sFieldName, String sScreenName)
					throws Exception {

		WebElement element = null;
		WebElement element2 = null;
		String element3 = null;
		List<WebElement> element4 = null;
		List<WebElement> element6 = null;
		List<WebElement> element7 = null;
		String element6val=null;
		String elementval=null;
		String elementlist=null;
		if (checkNull(sInputData)) {

			try {
				if (sScreenWidget.contains("xpath=")) {
					if (sScreenWidget.contains(";")) {
						String screenWidget[] = sScreenWidget.split(";");
						element = driver.findElement(By.xpath(screenWidget[0]
								.toString().replace("xpath=", "")));
						element6val=screenWidget[1];
						element2 = driver.findElement(By.xpath(screenWidget[1]
								.toString().replace("xpath=", "")));
						if (screenWidget.length>2)
						{
							elementlist =
									screenWidget[2].toString().replace("xpath=", "");
						// The 3 xpath will be visible only after clicking on
						// drop down. If this xpath is defined as WebElement,
						// then it will result in NoSuchElementException.
						}
						
					} else if (sScreenWidget.contains("&")) {
						
						element6val=null;
						String [] screenWidget = null;
						
						   screenWidget =sScreenWidget.split("&");
						elementval=screenWidget[0];
						element = driver.findElement(By.xpath(screenWidget[0]
								.toString().replace("xpath=", "")));
						element6val=screenWidget[1];
						element6 = driver.findElements(By.xpath(screenWidget[1]
								.toString().replace("xpath=", "")));
						screenWidget[0]=null;
						screenWidget[1]= null;
						
						
					} else if (sScreenWidget.contains("findElements=")) {

						element7 = driver
								.findElements(By.xpath(sScreenWidget
										.replace("findElements=","")));
					} else {
						element = driver.findElement(By.xpath(sScreenWidget
								.replace("xpath=", "")));
					}

				} else {
					  if (sScreenWidget.contains("findElements=")) {
							element4 = driver.findElements(By.xpath(sScreenWidget
									.replace("findElements=", "")));
						}else if (sScreenWidget.contains("id=")) {
						element = driver.findElement(By.id(sScreenWidget
								.replace("id=", "")));
					} else if (sScreenWidget.contains("className=")) {
						element = driver.findElement(By.className(sScreenWidget
								.replace("className=", "")));
					} else if (sScreenWidget.contains("tagName=")) {
						element = driver.findElement(By.tagName(sScreenWidget
								.replace("tagName=", "")));
					} else if (sScreenWidget.contains("name=")) {
						element = driver.findElement(By.name(sScreenWidget
								.replace("name=", "")));
					} else if (sScreenWidget.contains("linkText=")) {
						element = driver.findElement(By.linkText(sScreenWidget
								.replace("linkText=", "")));
					} else if (sScreenWidget.contains("partialLinkText=")) {
						element = driver.findElement(By
								.partialLinkText(sScreenWidget.replace(
										"partialLinkText=", "")));
					} else if (sScreenWidget.contains("css=")) {
						element = driver
								.findElement(By.cssSelector(sScreenWidget
										.replace("css=", "")));
					}else if (sScreenWidget.contains("alert=")) {
						// do nothing
						/**
						 * As alert we are handling with alert class not with
						 * element driver
						 */
					} else if (sScreenWidget.contains("test=")) {
						// do nothing
						/**
						 * As alert we are handling with alert class not with
						 * element driver
						 */
					} else {

						// Reporter.log("Provide valid locator. Example : id,name,linkText,partialLinkText");
						// WriteHTMLLog
						// .writeHtmlLogs("Provide valid locator. Example : id,name,linkText,partialLinkText");
						// throw new IllegalArgumentException(
						// "Provide valid locator. Example : id,name,linkText,partialLinkText "
						// + sScreenWidget
						// + " type does not contain -");
					}

				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				logger.error("Error while creating webdriver element for the OR property : "
						+ sScreenWidget + "  " + e);

				/*WriteHTMLLog
						.writeHtmlLogs(getFormatedHTMLFailError(
								"Error While creating the Webdriver Element for the OR property  ",
								sScreenWidget, sFieldName, sScreenName));*/

				WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.FAIL,"Error while creating the Webdriver Element for the OR property: "
						+sScreenWidget+" & FieldName: "+sFieldName+" & ScreenName: "+sScreenName);
				ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Error while creating the Webdriver Element for the OR property: "
						+sScreenWidget+" & FieldName: "+sFieldName+" & ScreenName: "+sScreenName);
				/*customlogger.log(LogStatus.ERROR, "Error while creating webdriver element for the OR property : "
                        + sScreenWidget + "  " + e");*/
				//				WriteHTMLLog
				//						.writeHtmlWebDeriverScreenShotDeatils(captureScreenShot());
				Reporter.log(e.toString());
				System.out.println(" At the time of Element creation");
				Assert.fail(
						"Error while creating webdriver element for the OR property : "
								+ sScreenWidget, e);

				// killinstance();

			}

			try {
				if (WidgetType.equalsIgnoreCase("TextBox")) {
					if (checkNull(sInputData)) {
						element.clear();
						Thread.sleep(3000);
						element.sendKeys(Keys.CONTROL + "a");
						element.sendKeys(Keys.CONTROL + "x");
						element.sendKeys(Keys.BACK_SPACE);
						// ((JavascriptExecutor)
						// driver).executeScript("arguments[0].scrollIntoView(true);",
						// element);
						 Thread.sleep(3000);
							element.clear();
						element.sendKeys(sInputData.toString());
						Thread.sleep(sleepTime);
						Thread.sleep(1000);

						logger.info("Successfully entered the value "
								+ sInputData + " in the TextBox " + sFieldName
								+ " on the screen " + sScreenName);
						Reporter.log("Successfully entered the value "
								+ sInputData + " in the TextBox " + sFieldName
								+ " on the screen " + sScreenName);
						/*WriteHTMLLog
								.writeHtmlLogs(getFormatedHTMLPASSTestStep(
										sFieldName, WidgetType, sInputData,
										sScreenName));*/

						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully entered the value '" 
								+ sInputData + "' in the TextBox " + sFieldName
								+ " on the screen " + sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully entered the value '" 
								+ sInputData + "' in the TextBox " + sFieldName
								+ " on the screen " + sScreenName);

						/*customlogger.log(LogStatus.PASS, "Successfully entered the value "
				                + sInputData + " in the TextBox " + sFieldName
				                + " on the screen " + sScreenName);*/

					}
				} else if (WidgetType.equalsIgnoreCase("PASSWORD_TEXTBOX")) {
					if (checkNull(sInputData)) {
						element.clear();
						element.sendKeys(Keys.CONTROL + "a");
						element.sendKeys(Keys.CONTROL + "x");
						element.sendKeys(sInputData.toString());
						Thread.sleep(sleepTime);

						logger.info("Successfully entered the value ********** "
								+ " in the TextBox " + sFieldName
								+ " on the screen " + sScreenName);
						Reporter.log("Successfully entered the value ********** "
								+ " in the TextBox " + sFieldName
								+ " on the screen " + sScreenName);
						/*WriteHTMLLog.writeHtmlLogs(getFormatedHTMLPASSTestStep(
								sFieldName, WidgetType, "**********",
								sScreenName));*/

						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully entered the value '**********' "
								+ " in the TextBox " + sFieldName
								+ " on the screen " + sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully entered the value '**********' "
								+ " in the TextBox " + sFieldName
								+ " on the screen " + sScreenName);

						/*customlogger.log(LogStatus.PASS, "Successfully entered the value "
                               + sInputData + " in the TextBox " + sFieldName
                               + " on the screen " + sScreenName); */

					}
				} else if (WidgetType.equalsIgnoreCase("AdminLogin")) {
					if (checkNull(sInputData)) {
						
						System.out.println("Admin Login Block");
						Thread.sleep(2000);
						
						String URL = "https://sharknado:password@pamadmindev.cloudeast.inbcu.com/login";
						driver.get(URL);
						
			            
			            WebElement UserListBox = driver.findElement(By.id("app_user_id"));
						Select UserSelection=new Select(UserListBox);
						UserSelection.selectByVisibleText("Anil Nagaraju");
						Thread.sleep(5000);
						driver.findElement(By.name("commit")).click();

						
						
						logger.info("Successfully entered the value "
								+ sInputData + " in the TextBox " + sFieldName
								+ " on the screen " + sScreenName);
						Reporter.log("Successfully entered the value "
								+ sInputData + " in the TextBox " + sFieldName
								+ " on the screen " + sScreenName);
						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully selected the value "
								+ sInputData 
								+ " for logging on the Admin screen ");
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully selected the value "
								+ sInputData 
								+ " for logging on the Admin screen ");
						

					}
				} else if (WidgetType.equalsIgnoreCase("Select2")) {
					if (checkNull(sInputData)) {
						element.click();
						element2.sendKeys(sInputData.toString());
						Thread.sleep(5000);
						element2.sendKeys(Keys.ENTER);
						Thread.sleep(5000);
						logger.info("Successfully entered the value "
								+ sInputData + " in the TextBox " + sFieldName
								+ " on the screen " + sScreenName);
						Reporter.log("Successfully entered the value "
								+ sInputData + " in the TextBox " + sFieldName
								+ " on the screen " + sScreenName);
						/*WriteHTMLLog
								.writeHtmlLogs(getFormatedHTMLPASSTestStep(
										sFieldName, WidgetType, sInputData,
										sScreenName));*/
						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully entered the value '"
								+ sInputData + "' in the TextBox " + sFieldName
								+ " on the screen " + sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully entered the value '"
								+ sInputData + "' in the TextBox " + sFieldName
								+ " on the screen " + sScreenName);

						/*customlogger.log(LogStatus.PASS, "Successfully selected the value "
                               + sInputData + " in the ListBox " + sFieldName
                               + " on the screen " + sScreenName); */

					}
				} else if (WidgetType.equalsIgnoreCase("List-Suggest")) {
					if (checkNull(sInputData)) {
						element.click();
						element2.sendKeys(sInputData.toString());
						Thread.sleep(5000);
						element4 = driver.findElements(By.xpath(elementlist
								.toString().replace("xpath=", "")));
							
								for (int i = 0; i < element4.size(); i++)
								{
									//System.out.println(element4.get(i).getText());
										//System.out.println("element5.get(i).getText()="+element5.get(i).getText());
										if(element4.get(i).getText().equalsIgnoreCase(sInputData.toString()))
										{
											//System.out.println(element4.get(i).getText());
											element4.get(i).click();
											break;
										}	 
									} 
													
						logger.info("Successfully entered the value "
								+ sInputData + " in the TextBox " + sFieldName
								+ " on the screen " + sScreenName);
						Reporter.log("Successfully entered the value "
								+ sInputData + " in the TextBox " + sFieldName
						
							+ " on the screen " + sScreenName);
						/*WriteHTMLLog
								.writeHtmlLogs(getFormatedHTMLPASSTestStep(
										sFieldName, WidgetType, sInputData,
										sScreenName));*/
						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully entered the value '"
								+ sInputData + "' in the TextBox " + sFieldName
								+ " on the screen " + sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully entered the value '"
								+ sInputData + "' in the TextBox " + sFieldName
								+ " on the screen " + sScreenName);
						/*customlogger.log(LogStatus.PASS, "Successfully selected the value "
                               + sInputData + " in the ListBox " + sFieldName
                               + " on the screen " + sScreenName); */
							
					}
				} else if (WidgetType.equalsIgnoreCase("Select3")) {
					if (checkNull(sInputData)) {
						//String checkvalues[]=sInputData.split(",");
						String checkvalues=sInputData;
						int test1= element6.size();
						System.out.println(test1);
						//int count=checkvalues.length;
						element.click();
						Thread.sleep(5000);
						//for (int j=0;j< count;j++)	
					//	{
						//System.out.println(element6.size());
						for (int i = 0; i < element6.size(); i++)
						{
							System.out.println(element6.get(i).getText());
								//System.out.println("element5.get(i).getText()="+element5.get(i).getText());
								if(element6.get(i).getText().equalsIgnoreCase(checkvalues.toString()))
								{
									System.out.println(element6.get(i).getText());
									element6.get(i).click();

								}	 
							} 
						
						logger.info("Successfully selected the value "
								+ sInputData + " in the ListBox" + sFieldName
								+ " on the screen " + sScreenName);
						Reporter.log("Successfully selected the value "
								+ sInputData + " in the ListBox " + sFieldName
								+ " on the screen " + sScreenName);
						
                    WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully selected the value '"
							+ sInputData + "' in the ListBox" + sFieldName
							+ " on the screen " + sScreenName);
                    ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully selected the value '"
							+ sInputData + "' in the ListBox" + sFieldName
							+ " on the screen " + sScreenName);

					/*	customlogger.log(LogStatus.PASS, "Successfully selected the value "
                               + sInputData + " in the ListBox " + sFieldName
                               + " on the screen " + sScreenName); */

					}
					
					/*else
					{
						
					}*/
				} else if (WidgetType.equalsIgnoreCase("Select4")) {
					if (checkNull(sInputData)) {
						
						/*if(sInputData.toString()=="All")
						{
							System.out.println("eNTER");
							System.out.println("Hi");
						}*/
						//element=null;
						System.out.println(elementval);
						element = driver.findElement(By.xpath(elementval
								.toString().replace("xpath=", "")));
						element.click();
						Thread.sleep(2000);
						element.sendKeys(sInputData);
						Thread.sleep(5000);
						//driver.findElement(By.xpath("//div[@id='select2-drop']/ul/li[2]/div/span")).click();
						//Thread.sleep(17000);
						element6=null;
						System.out.println(element6val);
						element6 = driver.findElements(By.xpath(element6val
								.toString().replace("xpath=", "")));
						String checkvalues=sInputData;
						System.out.println(element6);
						int test1= element6.size();
						System.out.println(test1);
						for (int i = 0; i < element6.size(); i++)
						{
							
								//System.out.println("element5.get(i).getText()="+element5.get(i).getText());
								if(element6.get(i).getText().equalsIgnoreCase(checkvalues.toString()))
								{
									System.out.println(element6.get(i).getText());
									//System.out.println("One time");
									element6 = driver.findElements(By.xpath(element6val
											.toString().replace("xpath=", "")));
									String value1=element6.get(i).getText(); 
			                          String value2=sInputData.toString();
			                                    if (value1.equals(value2))
			                                    {   
			                                    	element6.get(i).click();
			                                   // Thread.sleep(3000);
			                                    	logger.info("Successfully selected the value "
			                								+ sInputData + " in the ListBox" + sFieldName
			                								+ " on the screen " + sScreenName);
			                						Reporter.log("Successfully selected the value "
			                								+ sInputData + " in the ListBox " + sFieldName
			                								+ " on the screen " + sScreenName);
			                						
			                                    WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully selected the value '"
			            								+ sInputData + "' in the ListBox" + sFieldName
			            								+ " on the screen " + sScreenName);
			                                    ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully selected the value '"
			            								+ sInputData + "' in the ListBox" + sFieldName
			            								+ " on the screen " + sScreenName);
			                             }
			                                    else
			                                    {
			                                    	WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.FAIL,"Error while Selecting the '"+sFieldName+"' on screen '"+sScreenName);
			                                    	ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Error while Selecting the '"+sFieldName+"' on screen '"+sScreenName);
			                                    }
									
									System.out.println("Second time");
									element6 = driver.findElements(By.xpath(element6val
											.toString().replace("xpath=", "")));
								}	
								else
                                {
                                	WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.FAIL,"The Input value is not present in '"+sFieldName+"' on screen '"+sScreenName);
                                	ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"The Input value is not present in '"+sFieldName+"' on screen '"+sScreenName);
                                }
							} 
							//element6=null;
							element6val=null;
							//screenWidget[0]=null;
						
						//Thread.sleep(sleepTime);
						
					//	WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully selected the value '"
					//			+ sInputData + "' in the ListBox" + sFieldName
					//			+ " on the screen " + sScreenName);

						/*customlogger.log(LogStatus.PASS, "Successfully selected the value "
                               + sInputData + " in the ListBox " + sFieldName
                               + " on the screen " + sScreenName);*/
					}
				} else if (WidgetType.equalsIgnoreCase("ListBoxNew")) {
					if (checkNull(sInputData)) {
						element.click();
						Thread.sleep(sleepTime);
						element.sendKeys(sInputData);
						Thread.sleep(7000);
						element.sendKeys(Keys.ENTER);
						Thread.sleep(sleepTime);
						logger.info("Successfully selected the value "
								+ sInputData + " in the ListBox" + sFieldName
								+ " on the screen " + sScreenName);
						Reporter.log("Successfully selected the value "
								+ sInputData + " in the ListBox " + sFieldName
								+ " on the screen " + sScreenName);
						/*WriteHTMLLog
								.writeHtmlLogs(getFormatedHTMLPASSTestStep(
										sFieldName, WidgetType, sInputData,
										sScreenName));*/
						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully selected the value '"
								+ sInputData + "' in the ListBox" + sFieldName
								+ " on the screen " + sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully selected the value '"
								+ sInputData + "' in the ListBox" + sFieldName
								+ " on the screen " + sScreenName);
						/*customlogger.log(LogStatus.PASS, "Successfully selected the value "
                               + sInputData + " in the ListBox " + sFieldName
                               + " on the screen " + sScreenName);*/
					}
				} else if (WidgetType.equalsIgnoreCase("DynamicTableLink")) {
					if (checkNull(sInputData)) {
						driver.findElement(By.partialLinkText(sInputData)).click();
						Thread.sleep(sleepTime);
						logger.info("Successfully clicked the link "
								+ sInputData + "' on the screen '"
								+ sScreenName);
						Reporter.log("Successfully clicked the link "
								+ sInputData + " on the screen " + sScreenName);
						/*WriteHTMLLog
								.writeHtmlLogs(getFormatedHTMLPASSTestStep(
										sFieldName, WidgetType, sInputData,
										sScreenName));*/
						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully clicked the link '"
								+ sInputData + "' on the screen "
								+ sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully clicked the link '"
								+ sInputData + "' on the screen "
								+ sScreenName);
						/*customlogger.log(LogStatus.PASS, "Successfully clicked on the link "
                               + sInputData + " from " + sFieldName
                               + " on the screen " + sScreenName); */
					}

				} else if (WidgetType.equalsIgnoreCase("DynamicTableNTLink")) {
					if (checkNull(sInputData)) {
						driver.findElement(
								By.xpath("//h3[text()='" + sInputData
										+ "']/../../../../../div[2]/ul/li[2]/a"))
										.click();
						Thread.sleep(sleepTime);
						logger.info("Successfully clicked the link '"
								+ sInputData + "' on the screen '"
								+ sScreenName);
						Reporter.log("Successfully clicked the link '"
								+ sInputData + " on the screen " + sScreenName);
						/*WriteHTMLLog
								.writeHtmlLogs(getFormatedHTMLPASSTestStep(
										sFieldName, WidgetType, sInputData,
										sScreenName));*/
						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully clicked the link '"
								+ sInputData + "' on the screen '"
								+ sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully clicked the link '"
								+ sInputData + "' on the screen '"
								+ sScreenName);
						/*customlogger.log(LogStatus.PASS, "Successfully clicked on the link "
                               + sInputData + " from " + sFieldName
                               + " on the screen " + sScreenName); */
					}

				} else if (WidgetType
						.equalsIgnoreCase("DynamicTableStealthModeLink")) {
					if (checkNull(sInputData)) {
						driver.findElement(
								By.xpath("//h3[text()='" +sInputData+"']/../../../../../div[2]/ul/li[3]/a")).click();
						logger.info("Successfully clicked the link '"
								+ sInputData + "' on the screen '"
								+ sScreenName);
						Reporter.log("Successfully clicked the link '"
								+ sInputData + " on the screen " + sScreenName);
						/*WriteHTMLLog
								.writeHtmlLogs(getFormatedHTMLPASSTestStep(
										sFieldName, WidgetType, sInputData,
										sScreenName));*/
						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully clicked the link '"
								+ sInputData + "' on the screen '"
								+ sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully clicked the link '"
								+ sInputData + "' on the screen '"
								+ sScreenName);
						/*customlogger.log(LogStatus.PASS, "Successfully clicked on the link "
                               + sInputData + " from the list " + sFieldName
                               + " on the screen " + sScreenName); */
					}

				} else if (WidgetType.equalsIgnoreCase("EnterKey")) {
					if (checkNull(sInputData)) {
						element.sendKeys(Keys.ENTER);
						Thread.sleep(sleepTime);
						logger.info("Successfully clicked on textbox '"
								+ sInputData + "' on the screen '"
								+ sScreenName);
						Reporter.log("Successfully clicked on textbox "
								+ sInputData + " on the screen " + sScreenName);
						/*WriteHTMLLog
								.writeHtmlLogs(getFormatedHTMLPASSTestStep(
										sFieldName, WidgetType, sInputData,
										sScreenName));*/
						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully clicked on textbox '"
								+ sFieldName + "' on the screen '"
								+ sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully clicked on textbox '"
								+ sFieldName + "' on the screen '"
								+ sScreenName);
						/*customlogger.log(LogStatus.PASS, "Successfully performed "
                               + sInputData + " on the " + sFieldName
                               + " on the screen " + sScreenName); */
					}

				} else if (WidgetType.equalsIgnoreCase("MouseHover")) {
					if (checkNull(sInputData)) {

						// *****
						Robot robot = new Robot();
						robot.keyPress(java.awt.event.KeyEvent.VK_WINDOWS);
						robot.keyPress(java.awt.event.KeyEvent.VK_M);
						// release opposite order
						robot.keyRelease(java.awt.event.KeyEvent.VK_M);
						robot.keyRelease(java.awt.event.KeyEvent.VK_WINDOWS);
						Thread.sleep(sleepTime);
						// *******
						element = driver.findElement(By.xpath(sScreenWidget
								.replace("xpath=", "")));
						Actions action = new Actions(driver);
						action.moveToElement(element).build().perform();
						Thread.sleep(sleepTime);
						driver.findElement(By.linkText(sInputData)).click();
						Thread.sleep(sleepTime);
						logger.info("Successfully hovered over the value '"
								+ sInputData + "' within the option '"+ sFieldName +"' on the screen '"
								+ sScreenName);
						Reporter.log("Successfully hovered over the value '"
								+ sInputData + "' within the option '"+ sFieldName +"' on the screen '"
								+ sScreenName);
						/*WriteHTMLLog
								.writeHtmlLogs(getFormatedHTMLPASSTestStep(
										sFieldName, WidgetType, sInputData,
										sScreenName));*/


						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully hovered over the value '"
								+ sInputData + "' within the option '"+ sFieldName +"' on the screen '"
								+ sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully hovered over the value '"
								+ sInputData + "' within the option '"+ sFieldName +"' on the screen '"
								+ sScreenName);

						/*customlogger.log(LogStatus.PASS, "Successfully Hovered on the value "
                               + sInputData + " in the MouseHOver " + sFieldName
                               + " on the screen " + sScreenName); */
					}

				} else if (WidgetType.equalsIgnoreCase("SiteMouseHover")) {
					if (checkNull(sInputData)) {

						// *****
						Robot robot = new Robot();
						robot.keyPress(java.awt.event.KeyEvent.VK_WINDOWS);
						robot.keyPress(java.awt.event.KeyEvent.VK_M);
						// release opposite order
						robot.keyRelease(java.awt.event.KeyEvent.VK_M);
						robot.keyRelease(java.awt.event.KeyEvent.VK_WINDOWS);
						Thread.sleep(sleepTime);
						// *******

						element = driver.findElement(By.xpath(sScreenWidget
								.replace("xpath=", "")));
						Actions action = new Actions(driver);
						action.moveToElement(element).build().perform();
						Thread.sleep(sleepTime);
						driver.findElement(
								By.xpath("//a[text()='" + sInputData + "']"))
								.click();

						// driver.findElement(By.xpath("//ul[@class='Nav__list']//li/a[text()='-- "+sInputData+"']")).click();

						Thread.sleep(sleepTime);
						logger.info("Successfully hovered over the value '"
								+ sInputData + "' within the option '"+ sFieldName +"' on the screen '"
								+ sScreenName);
						Reporter.log("Successfully hovered over the value '"
								+ sInputData + "' within the option '"+ sFieldName +"' on the screen '"
								+ sScreenName);
						/*WriteHTMLLog
						.writeHtmlLogs(getFormatedHTMLPASSTestStep(
								sFieldName, WidgetType, sInputData,
								sScreenName));*/


						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully hovered over the value '"
								+ sInputData + "' within the option '"+ sFieldName +"' on the screen '"
								+ sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully hovered over the value '"
								+ sInputData + "' within the option '"+ sFieldName +"' on the screen '"
								+ sScreenName);
						/*customlogger.log(LogStatus.PASS, "Successfully hovered on the value "
                               + sInputData + " in the MouseHover " + sFieldName
                               + " on the screen " + sScreenName); */
					}

				} else if (WidgetType.equalsIgnoreCase("ContextClick")) {
					if (checkNull(sInputData)) {
						Actions action = new Actions(driver).contextClick(element);
						action.build().perform();
						Thread.sleep(3000);
						logger.info("Successfully clicked the link "
								+ sInputData + "' on the screen '"
								+ sScreenName);
						Reporter.log("Successfully clicked the link "
								+ sInputData + " on the screen " + sScreenName);
						Robot robot = new Robot();
						robot.keyPress(java.awt.event.KeyEvent.VK_ESCAPE);
						robot.keyRelease(java.awt.event.KeyEvent.VK_ESCAPE);
						/*WriteHTMLLog
								.writeHtmlLogs(getFormatedHTMLPASSTestStep(
										sFieldName, WidgetType, sInputData,
										sScreenName));*/
						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully clicked the link '"
								+ sInputData + "' on the screen "
								+ sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully clicked the link '"
								+ sInputData + "' on the screen "
								+ sScreenName);
						/*customlogger.log(LogStatus.PASS, "Successfully clicked on the link "
                               + sInputData + " from " + sFieldName
                               + " on the screen " + sScreenName); */
					}

				} else if (WidgetType.equalsIgnoreCase("ListBox")) {
					if (checkNull(sInputData)) {

						Select select = new Select(element);
						// select.selectByValue(sInputData);
						select.selectByVisibleText(sInputData);
						Thread.sleep(4000);
						logger.info("Successfully selected the value '"
								+ sInputData + "' in the ListBox '"
								+ sFieldName + "' on the screen '"
								+ sScreenName);
						Reporter.log("Successfully selected the value "
								+ sInputData + " in the ListBox " + sFieldName
								+ " on the screen " + sScreenName);
						/*WriteHTMLLog
								.writeHtmlLogs(getFormatedHTMLPASSTestStep(
										sFieldName, WidgetType, sInputData,
										sScreenName));*/


						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully selected the value '"
								+ sInputData + "' from the ListBox '"
								+ sFieldName + "' on the screen '"
								+ sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully selected the value '"
								+ sInputData + "' from the ListBox '"
								+ sFieldName + "' on the screen '"
								+ sScreenName);
						/*customlogger.log(LogStatus.PASS, "Successfully selected the value "
                               + sInputData + " in the ListBox " + sFieldName
                               + " on the screen " + sScreenName); */
					}

				} else if (WidgetType.equalsIgnoreCase("Button")) {
					if (checkNull(sInputData)) {
						element.click();
						Thread.sleep(5000);
						logger.info("Successfully clicked the '" + sFieldName
								+ "' Button on the screen '" + sScreenName);
						Reporter.log("Successfully clicked the " + sFieldName
								+ " Button on the screen " + sScreenName);
						/*WriteHTMLLog
								.writeHtmlLogs(getFormatedHTMLPASSTestStep(
										sFieldName, WidgetType, sInputData,
										sScreenName));*/

						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully clicked the '" + sFieldName
								+ "' Button on the screen '" + sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully clicked the '" + sFieldName
								+ "' Button on the screen '" + sScreenName);
						/*customlogger.log(LogStatus.PASS, "Successfully clicked on the value "
                               + sInputData + " in the Button " + sFieldName
                               + " on the screen " + sScreenName);*/
					}

				} else if (WidgetType.equalsIgnoreCase("Button-JS")) {
					if (checkNull(sInputData)) {
						WebElement elementToClick = element;
						JavascriptExecutor js = (JavascriptExecutor)driver;
						js.executeScript("arguments[0].click();", elementToClick);
						Thread.sleep(2000);
						logger.info("Successfully Click the button '"
								+ sInputData + "'  '"
								+ sFieldName + "' on the screen '"
								+ sScreenName);
						Reporter.log("Successfully Click the button "
								+ sInputData + "  " + sFieldName
								+ " on the screen " + sScreenName);
						/*WriteHTMLLog.writeHtmlLogs(getFormatedHTMLPASSTestStep(
                                                sFieldName, WidgetType, sInputData,
                                                sScreenName));*/

						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully Clicked the '" + sFieldName
								+ "' Button on the screen '" + sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully Clicked the '" + sFieldName
								+ "' Button on the screen '" + sScreenName);

						/*customlogger.log(LogStatus.PASS, "Successfully clicked on the value "
                               + sInputData + " in the Button " + sFieldName
                               + " on the screen " + sScreenName);*/

					}

				}else if (WidgetType.equalsIgnoreCase("BUTTON_IMAGE")) {
					if (checkNull(sInputData)) {
						// element.click();
						// element.submit();
						((JavascriptExecutor) driver).executeScript(
								"arguments[0].click();", element);
						logger.info("Successfully Clicked the '" + sFieldName
								+ "' Button on the screen '" + sScreenName);
						Reporter.log("Successfully Clicked the " + sFieldName
								+ " Button on the screen " + sScreenName);
						/*WriteHTMLLog
								.writeHtmlLogs(getFormatedHTMLPASSTestStep(
										sFieldName, WidgetType, sInputData,
										sScreenName));*/

						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully Clicked the '" + sFieldName
								+ "' Button on the screen '" + sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully Clicked the '" + sFieldName
								+ "' Button on the screen '" + sScreenName);

						/*customlogger.log(LogStatus.PASS, "Successfully clicked the value "
                               + sInputData + " in the Button " + sFieldName
                               + " on the screen " + sScreenName); */
					}

				} else if (WidgetType.equalsIgnoreCase("RADIOBUTTON")) {
					if (checkNull(sInputData)) {
						element.click();
						Thread.sleep(sleepTime);
						logger.info("Successfully selected the '" + sFieldName
								+ "' RadioButton on the screen '" + sScreenName);
						Reporter.log("Successfully selected the " + sFieldName
								+ " RadioButton on the screen " + sScreenName);
						/*WriteHTMLLog
								.writeHtmlLogs(getFormatedHTMLPASSTestStep(
										sFieldName, WidgetType, sInputData,
										sScreenName));*/


						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully selected the '" + sFieldName
								+ "' RadioButton on the screen '" + sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully selected the '" + sFieldName
								+ "' RadioButton on the screen '" + sScreenName);

						/*customlogger.log(LogStatus.PASS, "Successfully selected the value "
                               + sInputData + " in the RadioButton " + sFieldName
                               + " on the screen " + sScreenName);*/

					}

				} else if (WidgetType.equalsIgnoreCase("Link")) {
					if (checkNull(sInputData)) {
						element.click();
						Thread.sleep(sleepTime);
						logger.info("Successfully clicked the link '"
								+ sFieldName + "' on the screen '"
								+ sScreenName);
						Reporter.log("Successfully clicked the link "
								+ sFieldName + " on the screen " + sScreenName);
						/*WriteHTMLLog
								.writeHtmlLogs(getFormatedHTMLPASSTestStep(
										sFieldName, WidgetType, sInputData,
										sScreenName));*/

						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully clicked the link '"
								+ sFieldName + "' on the screen '"
								+ sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully clicked the link '"
								+ sFieldName + "' on the screen '"
								+ sScreenName);

						/*customlogger.log(LogStatus.PASS, "Successfully clicked on the link "
                               + sInputData + " of the Link " + sFieldName
                               + " on the screen " + sScreenName); */

					}

				} else if (WidgetType.equalsIgnoreCase("LINKPartialText")) {
					if (checkNull(sInputData)) {
						element.click();
						Thread.sleep(sleepTime);
						logger.info("Successfully clicked the link '"
								+ sFieldName + "' on the screen '"
								+ sScreenName);
						Reporter.log("Successfully clicked the link "
								+ sFieldName + " on the screen " + sScreenName);
						/*WriteHTMLLog
						.writeHtmlLogs(getFormatedHTMLPASSTestStep(
								sFieldName, WidgetType, sInputData,
								sScreenName));*/

						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully clicked the link '"
								+ sFieldName + "' on the screen '"
								+ sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully clicked the link '"
								+ sFieldName + "' on the screen '"
								+ sScreenName);
						/*customlogger.log(LogStatus.PASS, "Successfully clicked on the link "
                               + sInputData + " of the link " + sFieldName
                               + " on the screen " + sScreenName); */
					}

				} else if (WidgetType.equalsIgnoreCase("Tab")) {
					if (checkNull(sInputData)) {
						//element.clear();
						element.sendKeys(Keys.CONTROL + "a");
						//element.sendKeys(Keys.CONTROL + "x");
						element.sendKeys(Keys.TAB);
						Thread.sleep(sleepTime);
						logger.info("Successfully tabbed'" + sFieldName
								+ "' on the screen '" + sScreenName);
						Reporter.log("Successfully tabbed " + sFieldName
								+ " on the screen " + sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully tabbed'" + sFieldName
								+ "' on the screen '" + sScreenName);

					}
                 
				} else if (WidgetType.equalsIgnoreCase("KTab")) {
                    if (sInputData.equalsIgnoreCase("KTab"))
                    {
                    Robot r = new Robot();
                    r.keyPress(java.awt.event.KeyEvent.VK_TAB);
                    Thread.sleep(sleepTime);
                    r.keyRelease(java.awt.event.KeyEvent.VK_TAB);
                    
                    logger.info("Successfully tabbed'" + ""
                                  + "' on the screen '" + "");
                    Reporter.log("Successfully tabbed " + ""
                                  + " on the screen " + "");
                    ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully tabbed'" + ""
                            + "' on the screen '" + "");
                    }	
                  
             }else if (WidgetType.equalsIgnoreCase("Refresh")) {
					if (checkNull(sInputData)) {
						driver.navigate().refresh();
						Thread.sleep(sleepTime);
						logger.info("Successfully refreshed on the screen '" + sScreenName);
						Reporter.log("Successfully refreshed on the screen '" + sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully refreshed on the screen '" + sScreenName);

					}

				} else if (WidgetType.equalsIgnoreCase("ScrollUp")) {
					if (checkNull(sInputData)) {
						element.sendKeys(Keys.HOME);
						Thread.sleep(sleepTime);
						logger.info("Successfully scrolled up'" + sFieldName
								+ "' on the screen '" + sScreenName);
						Reporter.log("Successfully scrolled up" + sFieldName
								+ " on the screen " + sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully scrolled up'" + sFieldName
								+ "' on the screen '" + sScreenName);

					}

					else if (WidgetType.equalsIgnoreCase("ScrollDown")) {
						if (checkNull(sInputData)) {
							element.sendKeys(Keys.PAGE_DOWN);
							Thread.sleep(sleepTime);
							logger.info("Successfully scrolled down'"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							Reporter.log("Successfully scrolled down "
									+ sFieldName + " on the screen "
									+ sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully scrolled down "
									+ sFieldName + " on the screen "
									+ sScreenName);

						}
					}

				} else if (WidgetType.equalsIgnoreCase("PageDown")) {
					if (checkNull(sInputData)) {
						Thread.sleep(sleepTime);
						Robot r = new Robot();
						r.keyPress(java.awt.event.KeyEvent.VK_PAGE_DOWN);
						Thread.sleep(sleepTime);
						logger.info("Successfully '" + sFieldName
								+ "' on the screen '" + sScreenName);

					}
				} else if (WidgetType.equalsIgnoreCase("JsScrollDown")) {
					if (checkNull(sInputData)) {
						//WebElement element1 = driver.findElement(By.xpath("(//a[@id='populate'])[3]"));
						Thread.sleep(sleepTime);
						JavascriptExecutor js = (JavascriptExecutor) driver;
						js.executeScript("arguments[0].scrollIntoView(true);",element);
						Thread.sleep(sleepTime);
						logger.info("Successfully '" + sFieldName
								+ "' on the screen '" + sScreenName);

					}
				} else if (WidgetType.equalsIgnoreCase("Focus")) {
					if (checkNull(sInputData)) {
						element.sendKeys("");
						Thread.sleep(sleepTime);
						logger.info("Successfully focused'" + sFieldName
								+ "' on the screen '" + sScreenName);
						Reporter.log("Successfully focused " + sFieldName
								+ " on the screen " + sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully focused'" + sFieldName
								+ "' on the screen '" + sScreenName);


					}
				} else if (WidgetType.equalsIgnoreCase("Minimize")) {
					if (checkNull(sInputData)) {

						Robot robot = new Robot();
						robot.keyPress(java.awt.event.KeyEvent.VK_WINDOWS);
						robot.keyPress(java.awt.event.KeyEvent.VK_M);
						// release opposite order
						robot.keyRelease(java.awt.event.KeyEvent.VK_M);
						robot.keyRelease(java.awt.event.KeyEvent.VK_WINDOWS);
						Thread.sleep(sleepTime);
						logger.info("Successfully Minimize '" + sFieldName
								+ "' on the screen '" + sScreenName);
						Reporter.log("Successfully Minimize  " + sFieldName
								+ " on the screen " + sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully Minimize '" + sFieldName
								+ "' on the screen '" + sScreenName);

					}
				} else if (WidgetType.equalsIgnoreCase("Alert")) {
					if (checkNull(sInputData)) {
						Alert alert = driver.switchTo().alert();
						Thread.sleep(sleepTime);
						alert.getText();
						Thread.sleep(sleepTime);
						if (sInputData.equalsIgnoreCase("Accept")
								|| sInputData.equalsIgnoreCase("Yes")
								|| sInputData.equalsIgnoreCase("Ok")|| sInputData.equalsIgnoreCase("Leave") || sInputData.equalsIgnoreCase("Stay on this Page")) {
							alert.accept();
							Thread.sleep(sleepTime);
							Reporter.log("Succefully accepted the Alert Box.");
							/*WriteHTMLLog.writeHtmlLogs(getFormatedHTMLPASSTestStep("AcceptAlert",WidgetType,sInputData,sScreenName));*/

							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully clicked 'Yes' on the '" + sFieldName
									+ "' of type Alert' on the '" + sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully clicked 'Yes' on the '" + sFieldName
									+ "' of type Alert' on the '" + sScreenName);

							/*customlogger.log(LogStatus.PASS, "Successfully clicked on the value "
                               + sInputData + " Of type Alert " + sFieldName
                               + " on the screen " + sScreenName); */
						} else {
							alert.dismiss();
							Reporter.log("Succefully dismiss the Alert Box.", true);
							Thread.sleep(sleepTime);
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully clicked 'Cancel' on the '" + sFieldName
									+ "' of type Alert' on the '" + sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully clicked 'Cancel' on the '" + sFieldName
									+ "' of type Alert' on the '" + sScreenName);

						}
					}

				} else if (WidgetType.equalsIgnoreCase("sleep")) {
					if (checkNull(sInputData)) {
						int sleepValue = Integer.parseInt(sInputData);
						logger.info("Sleep Inititated for " + sleepValue + "ms");
						Reporter.log("Sleep Inititated for " + sleepValue
								+ "ms");
						Thread.sleep(sleepValue);
					}

				} else if (WidgetType.equalsIgnoreCase("navigateToTad")) {
					if (checkNull(sInputData)) {
						driver.get("http://tad-acceptance.cloudeast.inbcu.com");
						logger.info("Navigated to 'TAD' application");
						Reporter.log("Navigated to 'TAD' application");

					}

				} else if (WidgetType.equalsIgnoreCase("AllTextbox")) {
					if (checkNull(sInputData)) {
						//List<WebElement> element7 = driver.findElements(By.xpath("//*[text()='Current Estimate']/../../sub-budget/div/table/tbody/tr//td[contains(@class,'editable')]/input[1]"));
						System.out.println("No. of textfields in the table: "+element4.size());
						//System.out.println("Size: "+element3.size());
						for (int i = 0; i < element4.size(); i++) 
						{
							
						//	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element4a);
							element4.get(i).clear();
							//element4.get(i).sendKeys(Keys.CONTROL + "a");
							//element4.get(i).sendKeys(Keys.CONTROL + "x");
							element4.get(i).sendKeys(Keys.TAB);
							logger.info("Successfully cleared the value of Textbox#"
									+ i + " on the screen " + sScreenName);
							Reporter.log("Successfully cleared the value of Textbox#"
									+ i + " on the screen " + sScreenName);
							
						}
						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully cleared all the values of "+ sFieldName +" on the screen " + sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully cleared all the values of "+ sFieldName +" on the screen " + sScreenName);
											}
				} else if (WidgetType.equalsIgnoreCase("Clear")) {
					if (checkNull(sInputData)) {
						System.out.println("Clear");
						element.clear();
						element.sendKeys(Keys.CONTROL + "a");
						element.sendKeys(Keys.CONTROL + "x");
						logger.info("Successfully Cleared the '" + sFieldName
								+ "' Textbox on the screen '" + sScreenName);
						Reporter.log("Successfully Cleared the " + sFieldName
								+ " Textbox on the screen " + sScreenName);
						/*WriteHTMLLog
								.writeHtmlLogs(getFormatedHTMLPASSTestStep(
										sFieldName, WidgetType, sInputData,
										sScreenName));*/

						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully Cleared the '" + sFieldName
								+ "' Textbox on the screen '" + sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully Cleared the '" + sFieldName
								+ "' Textbox on the screen '" + sScreenName);

						/*customlogger.log(LogStatus.PASS, "Successfully cleared the value "
                                       + sInputData + " in the TextBox " + sFieldName
                                       + " on the screen " + sScreenName); */
					}

				} else if (WidgetType.equalsIgnoreCase("GetDealPacingID")) {
					if (checkNull(sInputData)) {

						String url = driver.getCurrentUrl();
						if (url.contains("pacing")) {
							DealPacingID = url.substring(
									url.lastIndexOf("/") + 1, url.indexOf("?"));

						} else if (url.contains("deals")) {
							DealPacingID = url.substring(
									url.lastIndexOf("/") + 1, url.indexOf("?"));

						} else {
							logger.info("The URL does not contain neither 'Deals' nor 'Pacing'.");
							DealPacingID = "0";
						}
					}

					/*logger.info("Successfully got the Deal/PacingID='"
							+ DealPacingID + "" + " on the screen"
							+ sScreenName);*/
					Reporter.log("The Deal/PacingID "
							+ DealPacingID + "" + "  on the screen "
							+ sScreenName,true);
					/*WriteHTMLLog.writeHtmlLogs(getFormatedHTMLPASSTestStep(
							sFieldName, WidgetType, DealPacingID, sScreenName));*/

					WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"The Deal/PacingID='"
							+ DealPacingID + "'");
					ExtentReport.ExtentLogger.log(LogStatus.PASS,"The Deal/PacingID='"
							+ DealPacingID + "'");

					/*customlogger.log(LogStatus.PASS, "Successfully captured the value "
                                   + sInputData + " in the DealPacingID " + sFieldName
                                   + " on the screen " + sScreenName);*/

				} else if (WidgetType.equalsIgnoreCase("GetNegRoundInputs")) {
                    if (checkNull(sInputData)) {

                        String url2 = driver.getCurrentUrl();
                        if( url2.contains("agencies" ) && url2.contains("budget_year_id"  ) && url2.contains("marketplace_id"  )){
                        	AgencyID = url2.substring(url2.lastIndexOf("/") + 1, url2.indexOf("?"));
                        	//System.out.println(Agency_ID);
                        	BudgetYearID = url2.substring(url2.lastIndexOf("budget_year_id=") +15, url2.indexOf("&"));
	                        //System.out.println(Budget_Year_ID);
                        	MarketPlaceID = url2.substring(url2.lastIndexOf("marketplace_id=") + 15);
                        	//System.out.println(MarketPlace_ID);
                        	Reporter.log("Successfully got the Negotiation Tracker's Round Inputs with Agency_ID="
                                    + AgencyID + "; Budget_Year_ID=" + BudgetYearID + "; MarketPlace_ID=" + MarketPlaceID + "",true);
                        	WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully got the Negotiation Tracker's Round Inputs with Agency_ID="
                                    + AgencyID + "; Budget_Year_ID=" + BudgetYearID + "; MarketPlace_ID=" + MarketPlaceID + "" );
                        	ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully got the Negotiation Tracker's Round Inputs with Agency_ID="
                                    + AgencyID + "; Budget_Year_ID=" + BudgetYearID + "; MarketPlace_ID=" + MarketPlaceID + "" );
                        
                        } else {
                                    logger.info("The URL does not contain neither Round details.");
                                    Reporter.log("The URL does not contain neither Round details.",true);
                                    WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.FAIL,"The URL does not contain neither Round details.");
                                    
                                    ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"The URL does not contain neither Round details.");
                                }
                        } 
					
				} else if (WidgetType.equalsIgnoreCase("DB_ExecuteQuery")) {
					if (checkNull(sInputData)) {
				         if (sInputData.contains (GetAgencyID) ||sInputData.contains (GetBudgetYearID) ||sInputData.contains (GetMarketPlaceID))
	                       {
	                        sInputData = sInputData.replace("GetDealPacingID",DealPacingID);
							sInputData = sInputData.replace("GetAgencyID",AgencyID);
							sInputData = sInputData.replace("GetBudgetYearID",BudgetYearID);
							sInputData = sInputData.replace("GetMarketPlaceID",MarketPlaceID);
	                       }
	                        else
	                       {
	                      // DealPacingID="12345";
	                        sInputData = sInputData.replace("GetDealPacingID",DealPacingID);
	                       }
						ArrayList<String> QueryResult = SQLConnector.DB_ExecuteQuery(sInputData);
						//logger.info("Successfully executed the SQL Query '"+ sInputData + "' in DB");
						Reporter.log("Successfully executed the SQL Query '"+ sInputData + "' in DB & Result: "+QueryResult,true);
						WriteHTMLLog.writeHtmlLogs(getFormatedDBStep(QueryResult,sInputData));
						ExtentReport.ExtentLogger.log(LogStatus.PASS,getFormatedDBStep(QueryResult,sInputData));
						/*customlogger.log(LogStatus.PASS, "Successfully executed the query "
                                       + sInputData + " in the SQL " + sFieldName
                                       + " on the screen " + sScreenName); */
					}

				} /*else if (WidgetType.equalsIgnoreCase("DeletePacingFrmDB")) {
					if (checkNull(sInputData)) {
						if (sInputData.equals("DeletePacing"))
						{
							String Query1="DELETE BUDGET_SCENARIO WHERE PACING_BUDGET_DETAIL_ID IN (SELECT PACING_BUDGET_DETAIL_ID FROM PACING_BUDGET_DETAIL WHERE PACING_BUDGET_ID IN("+DealPacingID+"))";
							String Query2="DELETE PACING_BUDGET_YEAR_STATUS WHERE PACING_BUDGET_ID IN ( "+DealPacingID+")";
							String Query3="DELETE PACING_BUDGET_DETAIL WHERE PACING_BUDGET_ID IN ( "+DealPacingID+")";
							String Query4="DELETE PACING_BUDGET WHERE PACING_BUDGET_ID IN ( "+DealPacingID+")";
							String Query5="COMMIT";
							//System.out.println(Query1);
							SQLConnector.DB_ExecuteQuery(Query1);
							SQLConnector.DB_ExecuteQuery(Query2);
							SQLConnector.DB_ExecuteQuery(Query3);
							SQLConnector.DB_ExecuteQuery(Query4);
							SQLConnector.DB_ExecuteQuery(Query5);
						}
						else if(sInputData.equals("DeleteShift"))
						{
							String Query1="DELETE SHIFT_REQUEST_BUDGET where PACING_BUDGET_ID in ( "+DealPacingID+")";
							String Query2="DELETE BUDGET_SCENARIO where PACING_BUDGET_DETAIL_ID in (SELECT PACING_BUDGET_DETAIL_ID FROM PACING_BUDGET_DETAIL where PACING_BUDGET_ID in("+DealPacingID+"))";
							String Query3="DELETE pacing_budget_year_status where pacing_budget_id in ( "+DealPacingID+")";
							String Query4="DELETE pacing_budget_detail where pacing_budget_id in ( "+DealPacingID+")";
							String Query5="DELETE PACING_BUDGET where pacing_budget_id in ( "+DealPacingID+")";
							String Query6="COMMIT";
							//System.out.println(Query1);
							SQLConnector.DB_ExecuteQuery(Query1);
							SQLConnector.DB_ExecuteQuery(Query2);
							SQLConnector.DB_ExecuteQuery(Query3);
							SQLConnector.DB_ExecuteQuery(Query4);
							SQLConnector.DB_ExecuteQuery(Query5);
							SQLConnector.DB_ExecuteQuery(Query6);
						}
						//ArrayList<String> QueryResult = SQLConnector.DB_ExecuteQuery(Query1);
						logger.info("Successfully executed the SQL Query '" + sInputData+ "' in DB");
						Reporter.log("Successfully executed the SQL Query '" + sInputData+ "' in DB");
						WriteHTMLLog.writeHtmlLogs(getFormatedDBStep(sInputData, ""));
					}

				}*/else if (WidgetType.equalsIgnoreCase("DeleteDeal")) {
                    if (checkNull(sInputData)) {
                        WebElement searchbox = driver.findElement(By
                                      .id("search_params_search_query"));
                        searchbox.clear();
                        searchbox.sendKeys(Keys.CONTROL + "a");
                        searchbox.sendKeys(Keys.CONTROL + "x");


                        searchbox.sendKeys(sInputData);// Enter the userinput
                        // data from excel to
                        // ssearch box
                        // Thread.sleep(1500);
                        driver.findElement(By.name("button")).click();// click

                        // on
                        // search
                        // button
                        Thread.sleep(5000);

                        // Assigning Del image xpath to a variable
                        String InputData = "i[class='sms-glyph-icon_trash']";
                        List<WebElement> Deal = driver.findElements(By
                                      .cssSelector(InputData)); // "i[class='sms-glyph-icon_trash']"
                        int Dealcount = Deal.size();
                        if (Dealcount > 0) {
                               for (int i = 0; i <= Dealcount - 1; i++) {
                                      List<WebElement> Deals = driver.findElements(By
                                                    .cssSelector(InputData));
                                      int Dealscount = Deals.size();
                                      if (Dealscount == 0)
                                             System.exit(0);
                                      WebElement del = (WebElement) Deals.get(0);

                                      //Get Deal ID
                                      String URL=driver.findElement(By.xpath("//li[@class='list-item']/div[2]/a")).getAttribute("href");
                                      if (URL.contains("pacing")) {
                                             DealPacingID = URL.substring(
                                                          URL.lastIndexOf("/") + 1, URL.indexOf("?"));

                                      } else if (URL.contains("deals")) {
                                             DealPacingID = URL.substring(
                                                          URL.lastIndexOf("/") + 1, URL.indexOf("?"));

                                      } else {
                                             logger.info("The URL does not contain neither 'Deals' nor 'Pacing'.");
                                      }

                                      //Delete Deal
                                      del.click();
                                      Thread.sleep(5000);
                                      Alert alert = driver.switchTo().alert(); // Handles
                                      // Alert
                                      alert.accept();
                                      Thread.sleep(5000);
                                      
                                      String url3 = driver.getCurrentUrl();
                                      try {
                                             boolean ErroPage = driver.findElement(By.xpath("//h1[text()='Looks like something went wrong']")).isDisplayed();
                                             if(ErroPage) {
                                             DealPacingID = url3.substring(url3.lastIndexOf("/") + 1);
                                             
                                             String Query1="DELETE FROM SMSDBO.ADVERTISER_NEGOTIATION WHERE DEAL_ID IN ( "+DealPacingID+")";
                                             String Query2="DELETE FROM SMSDBO.BUDGET_SNAP_SHOT WHERE DEAL_ID IN ("+DealPacingID+")";
                                             String Query3="DELETE FROM SMSDBO.DEAL_NEGOTIATION_HEADER WHERE DEAL_ID IN ( "+DealPacingID+")";
                                             String Query4="DELETE FROM RMIXDBO.MP_SCENARIO_DETAIL WHERE DEAL_ID IN ( "+DealPacingID+")";
                                             String Query5="DELETE FROM SMSDBO.STEALTH_MODE_BUDGET WHERE BUDGET_ID IN (SELECT BUDGET_ID FROM BUDGET WHERE DEAL_ID IN ( "+DealPacingID+"))";
                                             String Query6="DELETE FROM RMIXDBO.RMX_OPT_SCENARIO_DETAIL WHERE DEAL_ID IN ( "+DealPacingID+")";
                                             String Query7="DELETE FROM SMSDBO.AG_AGENCY_DEAL WHERE PRIOR_BUDGET_ID IN (SELECT BUDGET_ID FROM BUDGET WHERE DEAL_ID( "+DealPacingID+"))";
                                             String Query8="DELETE FROM SMSDBO.BUDGET WHERE DEAL_ID IN ( "+DealPacingID+")";
                                             String Query9="DELETE FROM DEAL WHERE DEAL_ID IN ( "+DealPacingID+")";
                                             String Query10="COMMIT";
                                             //System.out.println(Query1);
                                             SQLConnector.DB_ExecuteQuery(Query1);
                                             SQLConnector.DB_ExecuteQuery(Query2);
                                             SQLConnector.DB_ExecuteQuery(Query3);
                                             SQLConnector.DB_ExecuteQuery(Query4);
                                             SQLConnector.DB_ExecuteQuery(Query5);
                                             SQLConnector.DB_ExecuteQuery(Query6);
                                             SQLConnector.DB_ExecuteQuery(Query7);
                                             SQLConnector.DB_ExecuteQuery(Query8);
                                             SQLConnector.DB_ExecuteQuery(Query9);
                                             
                                             
                                             Reporter.log("Successfully deleted the Deal of combination '"
                                                          + sInputData+ "' (Deal ID: "+DealPacingID+") from DB " + sScreenName,true);
                                             
                                             WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully deleted the Deal of combination '"
                                                          + sInputData+ "' (Deal ID: "+DealPacingID+") from DB " + sScreenName);
                                             ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully deleted the Deal of combination '"
                                                     + sInputData+ "' (Deal ID: "+DealPacingID+") from DB " + sScreenName);
                                             
                                             driver.findElement(By.linkText("REGISTRATIONS")).click();
                                             Reporter.log("Clcked on REGISTRATIONS link", true);
                                             }
                                      } catch (Exception e) {
                                             System.out.println("Exception Block where deal is deleted");
                                      }
                                      
                                      logger.info("Successfully deleted the Deal of comination '"
                                                    + sInputData+ "' (Deal ID: "+DealPacingID+") on the screen " + sScreenName);
                                      Reporter.log("Successfully deleted the Deal of comination '"
                                                    + sInputData+ "' (Deal ID: "+DealPacingID+") on the screen " + sScreenName);
                                      /*WriteHTMLLog
                                                    .writeHtmlLogs(getFormatedHTMLPASSTestStep(
                                                                 sFieldName, "", "", sScreenName));*/

                                      WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully deleted the Deal of comination '"
                                                    + sInputData+ "' (Deal ID: "+DealPacingID+") on the screen " + sScreenName);
                                      ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully deleted the Deal of comination '"
                                              + sInputData+ "' (Deal ID: "+DealPacingID+") on the screen " + sScreenName);

                               }
                        } else
                               logger.info("There is no previous Deal of combination '" + sInputData
                                             + "' on the screen " + sScreenName);

                        Reporter.log("There is no previous Deal of combination '" + sInputData
                                      + "' on the screen " + sScreenName);

                        WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"There is no previous Deal of combination '" + sInputData
                                      + "' on the screen " + sScreenName);
                        ExtentReport.ExtentLogger.log(LogStatus.PASS,"There is no previous Deal of combination '" + sInputData
                                + "' on the screen " + sScreenName);

                 }

          } else if (WidgetType.equalsIgnoreCase("DeletePacing")) {
              if (checkNull(sInputData)) {
                  //driver.findElement(By.xpath("//button[text()='Clear Search']")).click();
                  WebElement searchbox = driver.findElement(By
                                .id("search_query"));
                  
                  searchbox.clear();
                  searchbox.sendKeys(Keys.CONTROL + "a");
                  searchbox.sendKeys(Keys.CONTROL + "x");
                  
                  searchbox.sendKeys(sInputData);// Enter the userinput
                  // data from excel to
                  // ssearch box
                  // Thread.sleep(1500);
                  driver.findElement(By.name("button")).click();// click
                  // on
                  // search
                  // button
                  Thread.sleep(5000);
                  // Assigning Del image xpath to a variable
                  String InputData = "i[class='sms-glyph-icon_trash']";
                  List<WebElement> Pacing = driver.findElements(By
                                .cssSelector(InputData)); // "i[class='sms-glyph-icon_trash']"
                  int Pacingcount = Pacing.size();
                  if (Pacingcount > 0) {
                         for (int i = 0; i <= Pacingcount - 1; i++) {
                                List<WebElement> Pacings = driver
                                              .findElements(By.cssSelector(InputData));
                                int Pacingscount = Pacings.size();
                                if (Pacingscount == 0)
                                       System.exit(0);
                                WebElement del = (WebElement) Pacings.get(0);

                                //Get Pacing ID
                                String URL=driver.findElement(By.xpath("//li[@class='list-item']/a")).getAttribute("href");
                                if (URL.contains("pacing")) {
                                       DealPacingID = URL.substring(
                                                    URL.lastIndexOf("/") + 1, URL.indexOf("?"));

                                } else if (URL.contains("deals")) {
                                       DealPacingID = URL.substring(
                                                    URL.lastIndexOf("/") + 1, URL.indexOf("?"));

                                } else {
                                       logger.info("The URL does not contain neither 'Deals' nor 'Pacing'.");
                                }

                                //Delete Deal

                                del.click();
                                Thread.sleep(5000);
                                Alert alert = driver.switchTo().alert(); // Handles
                                // Alert
                                alert.accept();
                                Thread.sleep(5000);
                                String url3 = driver.getCurrentUrl();
                                
                                if(url3.contains("calendar_year_id")) {
                                       DealPacingID = url3.substring(
                                                    url3.lastIndexOf("/") + 1, url3.indexOf("?"));
                                       
                                       String Query1="DELETE SHIFT_REQUEST_BUDGET WHERE PACING_BUDGET_ID IN ( "+DealPacingID+")";
                                       String Query2="DELETE BUDGET_SCENARIO WHERE PACING_BUDGET_DETAIL_ID IN (SELECT PACING_BUDGET_DETAIL_ID FROM PACING_BUDGET_DETAIL WHERE PACING_BUDGET_ID IN ("+DealPacingID+"))";
                                       String Query3="DELETE PACING_BUDGET_YEAR_STATUS WHERE PACING_BUDGET_ID IN ( "+DealPacingID+")";
                                       String Query4="DELETE PACING_BUDGET_DETAIL WHERE PACING_BUDGET_ID IN ( "+DealPacingID+")";
                                       String Query5="DELETE PACING_BUDGET WHERE PACING_BUDGET_ID IN ( "+DealPacingID+")";
                                       String Query6="COMMIT";
                                       //System.out.println(Query1);
                                       SQLConnector.DB_ExecuteQuery(Query1);
                                       SQLConnector.DB_ExecuteQuery(Query2);
                                       SQLConnector.DB_ExecuteQuery(Query3);
                                       SQLConnector.DB_ExecuteQuery(Query4);
                                       SQLConnector.DB_ExecuteQuery(Query5);
                                       SQLConnector.DB_ExecuteQuery(Query6);
                                       
                                       Reporter.log("Successfully deleted the Pacing of combination '"
                                                    + sInputData+ "' (Pacing ID: "+DealPacingID+") from DB " + sScreenName,true);
                                       
                                       WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully deleted the Pacing of combination '"
                                                    + sInputData+ "' (Pacing ID: "+DealPacingID+") from DB " + sScreenName);
                                       ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully deleted the Pacing of combination '"
                                               + sInputData+ "' (Pacing ID: "+DealPacingID+") from DB " + sScreenName);
                                       
                                       driver.findElement(By.linkText("PACING")).click();
                                       Reporter.log("Successfully clicked on 'Pacing' link.",true);
                                }
                                
                                
                                
                                logger.info("Successfully deleted the Pacing of combination '"
                                              + sInputData+ "' (Pacing ID: "+DealPacingID+") on the screen " + sScreenName);
                                Reporter.log("Successfully deleted the Pacing of combination '"
                                              + sInputData+ "' (Pacing ID: "+DealPacingID+") on the screen " + sScreenName,true);
                                /*WriteHTMLLog
                                              .writeHtmlLogs(getFormatedHTMLPASSTestStep(
                                                           sFieldName, "", "", sScreenName));*/
                                WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully deleted the Pacing of combination '"
                                              + sInputData+ "' (Pacing ID: "+DealPacingID+") on the screen " + sScreenName);
                                ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully deleted the Pacing of combination '"
                                        + sInputData+ "' (Pacing ID: "+DealPacingID+") on the screen " + sScreenName);
                         }
                  } else
                         logger.info("There is no previous Pacing of combination '" + sInputData
                                       + "' on the screen " + sScreenName);
                  Reporter.log("There is no previous Pacing of combination '" + sInputData
                                + "' on the screen " + sScreenName,true);
                  /*WriteHTMLLog.writeHtmlLogs(getFormatedHTMLPASSTestStep(
                                sFieldName, "There are no previous Pacings ",
                                "", sScreenName));*/
                  WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"There is no previous Pacing of combination '" + sInputData
                                + "' on the screen " + sScreenName);
                  ExtentReport.ExtentLogger.log(LogStatus.PASS,"There is no previous Pacing of combination '" + sInputData
                          + "' on the screen " + sScreenName);

           }

    }
 else if (WidgetType.equalsIgnoreCase("Checkbox")) {
					if (checkNull(sInputData)) {
						boolean currentChkBoxState = false;
						currentChkBoxState = element.isSelected();
						System.out
						.println("Current Checkbox State for the field '"
								+ sFieldName
								+ "' is - "
								+ currentChkBoxState);
						System.out
						.println("Expected Checkbox State for the field '"
								+ sFieldName + "' is - " + sInputData);
						Reporter.log("Current Checkbox State for the field "
								+ sFieldName + " is - " + currentChkBoxState);
						Reporter.log("Expected Checkbox State for the field "
								+ sFieldName + " is - " + sInputData);

						if (currentChkBoxState == false
								&& (sInputData.equalsIgnoreCase("Yes")
										|| sInputData.equalsIgnoreCase("-1") || sInputData
										.equalsIgnoreCase("1"))) {
							System.out.println("block1");
							Thread.sleep(sleepTime);
							element.click();
							Thread.sleep(sleepTime);
							logger.info("Successfully checked the checkBox '"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							Reporter.log("Successfully checked the checkBox "
									+ sFieldName + " on the screen "
									+ sScreenName);
							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLPASSTestStep(
											sFieldName, WidgetType, sInputData,
											sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully checked the CheckBox '"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully checked the CheckBox '"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							/*customlogger.log(LogStatus.PASS, "Successfully checked the value "
                                           + sInputData + " in the CheckBox " + sFieldName
                                           + " on the screen " + sScreenName);*/
						}
						if (currentChkBoxState == false
								&& (sInputData.equalsIgnoreCase("No"))) {
							System.out.println("block2");
							// element.click();
							logger.info("Checkbox is already unchecked "
									+ sFieldName + " on the screen "
									+ sScreenName);
							Reporter.log("Checkbox is already unchecked'"
									+ sFieldName + " on the screen "
									+ sScreenName);
							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLPASSTestStep(
											sFieldName, WidgetType, sInputData,
											sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Checkbox is already unchecked "
									+ sFieldName + " on the screen "
									+ sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Checkbox is already unchecked "
									+ sFieldName + " on the screen "
									+ sScreenName);
							/*customlogger.log(LogStatus.PASS, "Successfully checked the value "
                                           + sInputData + " in the CheckBox " + sFieldName
                                           + " on the screen " + sScreenName);  */
						}
						if (currentChkBoxState == true
								&& (sInputData.equalsIgnoreCase("No") || sInputData
										.equalsIgnoreCase("0"))) {
							System.out.println("block3");
							element.click();
							Thread.sleep(sleepTime);
							logger.info("Successfully Unchecked the CheckBox "
									+ sFieldName + " on the screen "
									+ sScreenName);
							Reporter.log("Successfully Unchecked the CheckBox "
									+ sFieldName + " on the screen "
									+ sScreenName);
							/*WriteHTMLLog
									.writeHtmlLogs(WriteHTMLLog
											.getFormatedHTMLPASSTestStep(
													sFieldName, WidgetType,
													sInputData, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully Unchecked the CheckBox "
									+ sFieldName + " on the screen "
									+ sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully Unchecked the CheckBox "
									+ sFieldName + " on the screen "
									+ sScreenName);
							/*customlogger.log(LogStatus.PASS, "Successfully checked the value "
                                           + sInputData + " in the CheckBox " + sFieldName
                                           + " on the screen " + sScreenName); */
						}
						if (currentChkBoxState == true
								&& (sInputData.equalsIgnoreCase("Yes"))) {
							System.out.println("block4");
							// element.click();
							logger.info("Checkbox is already checked'"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							Reporter.log("Checkbox is already checked "
									+ sFieldName + " on the screen "
									+ sScreenName);
							/*WriteHTMLLog
									.writeHtmlLogs(WriteHTMLLog
											.getFormatedHTMLPASSTestStep(
													sFieldName, WidgetType,
													sInputData, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Checkbox is already checked'"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Checkbox is already checked'"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							/*customlogger.log(LogStatus.PASS, "Successfully checked the value "
                                       + sInputData + " in the CheckBox " + sFieldName
                                       + " on the screen " + sScreenName); */
						}
					}
				} else if (WidgetType.equalsIgnoreCase("CHECKBOX2")) {
					if (checkNull(sInputData)) {
						boolean currentChkBoxState = false;
						String Class = element.getAttribute("class");
						if ((Class.contains("hide"))|| (!Class.contains("selected"))) {
							System.out.println("Not Checked");
							System.out
							.println("Current Checkbox State for the field '"
									+ sFieldName + "' is - false");
						} else {
							System.out.println("Checked");
							currentChkBoxState = true;
							System.out
							.println("Current Checkbox State for the field '"
									+ sFieldName + "' is - true");
						}

						Reporter.log("Current Checkbox State for the field "
								+ sFieldName + " is - " + currentChkBoxState);
						Reporter.log("Expected Checkbox State for the field "
								+ sFieldName + " is - " + sInputData);
						if (currentChkBoxState == false
								&& (sInputData.equalsIgnoreCase("Yes")
										|| sInputData.equalsIgnoreCase("-1") || sInputData
										.equalsIgnoreCase("1"))) {
							System.out.println("block1");
							element2.click();
							Thread.sleep(sleepTime);
							logger.info("Successfully checked the CheckBox '"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							Reporter.log("Successfully checked the CheckBox "
									+ sFieldName + " on the screen "
									+ sScreenName);
							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLPASSTestStep(
											sFieldName, WidgetType, sInputData,
											sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully checked the CheckBox '"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully checked the CheckBox '"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							/*customlogger.log(LogStatus.PASS, "Successfully checked the value "
                                   + sInputData + " in the CheckBox " + sFieldName
                                   + " on the screen " + sScreenName);*/
						}
						if (currentChkBoxState == false
								&& (sInputData.equalsIgnoreCase("No"))) {
							System.out.println("block2");
							// element.click();
							logger.info("Checkbox is already unchecked "
									+ sFieldName + " on the screen "
									+ sScreenName);
							Reporter.log("Checkbox is already unchecked'"
									+ sFieldName + " on the screen "
									+ sScreenName);
							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLPASSTestStep(
											sFieldName, WidgetType, sInputData,
											sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Checkbox is already unchecked'"
									+ sFieldName + " on the screen "
									+ sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Checkbox is already unchecked'"
									+ sFieldName + " on the screen "
									+ sScreenName);
							/*customlogger.log(LogStatus.PASS, "Successfully unchecked the value "
                            + sInputData + " in the CheckBox " + sFieldName
                            + " on the screen " + sScreenName);*/
						}
						if (currentChkBoxState == true
								&& (sInputData.equalsIgnoreCase("No") || sInputData
										.equalsIgnoreCase("0"))) {
							System.out.println("block3");
							element2.click();
							Thread.sleep(sleepTime);
							logger.info("Successfully Unchecked the CheckBox "
									+ sFieldName + " on the screen "
									+ sScreenName);
							Reporter.log("Successfully Unchecked the CheckBox "
									+ sFieldName + " on the screen "
									+ sScreenName);
							/*WriteHTMLLog
									.writeHtmlLogs(WriteHTMLLog
											.getFormatedHTMLPASSTestStep(
													sFieldName, WidgetType,
													sInputData, sScreenName));*/

							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully Unchecked the CheckBox "
									+ sFieldName + " on the screen "
									+ sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully Unchecked the CheckBox "
									+ sFieldName + " on the screen "
									+ sScreenName);


							/*customlogger.log(LogStatus.PASS, "Successfully unchecked the value "
                            + sInputData + " in the CheckBox " + sFieldName
                            + " on the screen " + sScreenName);*/
						}
						if (currentChkBoxState == true
								&& (sInputData.equalsIgnoreCase("Yes"))) {
							System.out.println("block4");
							// element.click();
							logger.info("Checkbox is already checked'"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							Reporter.log("Checkbox is already checked "
									+ sFieldName + " on the screen "
									+ sScreenName);
							/*WriteHTMLLog
									.writeHtmlLogs(WriteHTMLLog
											.getFormatedHTMLPASSTestStep(
													sFieldName, WidgetType,
													sInputData, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Checkbox is already checked'"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Checkbox is already checked'"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							/*customlogger.log(LogStatus.PASS, "Successfully checked the value "
                            + sInputData + " in the CheckBox " + sFieldName
                            + " on the screen " + sScreenName);*/
						}
					}
				}else if (WidgetType.equalsIgnoreCase("CHECKBOX3")) {
					if (checkNull(sInputData)) {
						boolean currentChkBoxState = false;
						currentChkBoxState = element.isSelected();

						System.out.println("Current Checkbox State for the field '"
								+ sFieldName
								+ "' is - "
								+ currentChkBoxState);
						System.out.println("Expected Checkbox State for the field '"
								+ sFieldName + "' is - " + sInputData);
						Reporter.log("Current Checkbox State for the field "
								+ sFieldName + " is - " + currentChkBoxState);
						Reporter.log("Expected Checkbox State for the field "
								+ sFieldName + " is - " + sInputData);

						if (currentChkBoxState == false
								&& (sInputData.equalsIgnoreCase("Yes")
										|| sInputData.equalsIgnoreCase("-1") || sInputData.equalsIgnoreCase("1"))) {
							//System.out.println("block1");
							Thread.sleep(sleepTime);
							element2.click();
							Thread.sleep(sleepTime);

							Reporter.log("Successfully checked the CheckBox "
									+ sFieldName + " on the screen "
									+ sScreenName,true);

							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully checked the CheckBox '"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully checked the CheckBox '"
									+ sFieldName + "' on the screen '"
									+ sScreenName);

						}
						if (currentChkBoxState == false
								&& (sInputData.equalsIgnoreCase("No"))) {
							//System.out.println("block2");
							//element2.click();
							logger.info("Checkbox is already unchecked "
									+ sFieldName + " on the screen "
									+ sScreenName);
							Reporter.log("Checkbox is already unchecked'"
									+ sFieldName + " on the screen "
									+ sScreenName);

							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Checkbox is already unchecked "
									+ sFieldName + " on the screen "
									+ sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Checkbox is already unchecked "
									+ sFieldName + " on the screen "
									+ sScreenName);

						}
						if (currentChkBoxState == true
								&& (sInputData.equalsIgnoreCase("No") || sInputData
										.equalsIgnoreCase("0"))) {
							//System.out.println("block3");
							element2.click();
							Thread.sleep(sleepTime);
							logger.info("Successfully Unchecked the CheckBox "
									+ sFieldName + " on the screen "
									+ sScreenName);
							Reporter.log("Successfully Unchecked the CheckBox "
									+ sFieldName + " on the screen "
									+ sScreenName);

							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully Unchecked the CheckBox "
									+ sFieldName + " on the screen "
									+ sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully Unchecked the CheckBox "
									+ sFieldName + " on the screen "
									+ sScreenName);

						}
						if (currentChkBoxState == true
								&& (sInputData.equalsIgnoreCase("Yes"))) {
							//System.out.println("block4");
							//element2.click();
							logger.info("Checkbox is already checked'"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							Reporter.log("Checkbox is already checked "
									+ sFieldName + " on the screen "
									+ sScreenName);

							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Checkbox is already checked'"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Checkbox is already checked'"
									+ sFieldName + "' on the screen '"
									+ sScreenName);

						}
					}
				} else if (WidgetType.equalsIgnoreCase("Frame")) {
                    if (checkNull(sInputData)) {
                        Thread.sleep(sleepTime);
                        driver.switchTo().frame(0);

                        logger.info("Successfully '"
                        + sFieldName + "' on the screen '"
                        + sScreenName);

                        }
				} else if (WidgetType.equalsIgnoreCase("FrameOut")) {
					if (checkNull(sInputData)) {
                        Thread.sleep(sleepTime);
                        driver.switchTo().defaultContent();

                        logger.info("Successfully '"
                        + sFieldName + "' on the screen '"
                        + sScreenName);

                        }
				} else if (WidgetType.equalsIgnoreCase("Logout")) {
					if (checkNull(sInputData)) {
						Thread.sleep(sleepTime);
						driver.quit();
						createWebDriverInstance(config.get("broswer"));
						launchApplication(config.get("URL")); 
						logger.info("Successfully '"
						+ sFieldName + "' on the screen '"
						+ sScreenName);
						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully '"
								+ sFieldName + "' on the screen '"
								+ sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully '"
								+ sFieldName + "' on the screen '"
								+ sScreenName);


						}
				}else if (WidgetType.equalsIgnoreCase("Navigate")) {
					if (checkNull(sInputData)) {
					
					//	System.out.println("Login url is "+sInputData);
						Thread.sleep(sleepTime);
						driver.quit();
						driver=null;
						createWebDriverInstance(config.get("broswer"));
						driver.get(sInputData);
						driver.manage().window().maximize();
						logger.info("Successfully '"
						+ sFieldName + "' on the screen '"
						+ sScreenName);
						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully '"
								+ sFieldName + "' on the screen '"
								+ sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully '"
								+ sFieldName + "' on the screen '"
								+ sScreenName);


						}
				}  else if (WidgetType.equalsIgnoreCase("NewTab")) {
					if (checkNull(sInputData)) {
						Thread.sleep(sleepTime);
						
						 ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
						 int windowcount=newTab.size();
						    driver.switchTo().window(newTab.get(windowcount-1));
						    System.out.println("control move to new tab");
						logger.info("Successfully Control swicth to '"
						+ sFieldName + "' on the screen '"	
						+ sScreenName);
						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully Control swicth to '"
								+ sFieldName + "' on the screen '"
								+ sScreenName);
						
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully Control swicth to '"
								+ sFieldName + "' on the screen '"
								+ sScreenName);

						}
				}else if (WidgetType.equalsIgnoreCase("DefaultTab")) {
					if (checkNull(sInputData)) {
						Thread.sleep(sleepTime);
						Robot robot=new Robot();
						robot.keyPress(java.awt.event.KeyEvent.VK_CONTROL);
						robot.keyPress(java.awt.event.KeyEvent.VK_W);
						
						 ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
						  driver.switchTo().window(newTab.get(0));
						 //   driver.switchTo().defaultContent();
						  robot.keyRelease(java.awt.event.KeyEvent.VK_CONTROL);
						  robot.keyRelease(java.awt.event.KeyEvent.VK_W);
						    System.out.println("control move to new tab");
						  
						logger.info("Successfully Control swicth back to '"
						+ sFieldName + "' on the screen '"
						+ sScreenName);
						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully Control swicth back to '"
								+ sFieldName + "' on the screen '"
								+ sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully Control swicth back to '"
								+ sFieldName + "' on the screen '"
								+ sScreenName);

						}
				}else if (WidgetType.equalsIgnoreCase("DeselectList")) {
					if (checkNull(sInputData)) {
						List<WebElement> element8=driver.findElements(By.xpath("//div[@id='s2id_pam_client_user_role_ids']/ul//li/a"));
						System.out.println("Size="+element8.size());
						for(int i=0;i<=element8.size()-1;i++)
						{ 
							element8.get(i).click();
								Thread.sleep(5000);
							}

							System.out.println("Deselect the List values");
							

						  
						logger.info("Successfully Deselected the list values '"
						+ sFieldName + "' on the screen '"
						+ sScreenName);
						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully Deselected the list values '"
								+ sFieldName + "' on the screen '"
								+ sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully Deselected the list values '"
								+ sFieldName + "' on the screen '"
								+ sScreenName);

						}
				}
				} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error while performating action '" + sInputData
						+ "' for the field '" + sFieldName + "' on the '"
						+ sScreenName + "' screen");
				logger.error(e.getMessage());
				Reporter.log(("Error while performating action " + sInputData
						+ " for the field " + sFieldName + " on the "
						+ sScreenName + " screen"));
				/*WriteHTMLLog.writeHtmlLogs(getFormatedHTMLFailTestStep(
						sFieldName, WidgetType, sInputData, sScreenName));*/

				WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.FAIL,"Error while performating action '" + sInputData
						+ "' for the field '" + sFieldName + "' on the '"
						+ sScreenName + "' screen");
				ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture()) +"Error while performating action" + sInputData
						+ "' for the field '" + sFieldName + "' on the '"
						+ sScreenName + "' screen");
				/*customlogger.log(LogStatus.FAIL, "Failed to perform action for the field "
                        + sFieldName
                        + " of type "
                        + WidgetType
                        + " data "
                        + sInputData
                        + " on screen "
                        + sScreenName");
				 */

				//				try {
				//					// WriteHTMLLog.takeAScreenShotOfTheApp();
				//					WriteHTMLLog
				//							.writeHtmlWebDeriverScreenShotDeatils(captureScreenShot());
				//				} catch (Exception e1) {
				//					// TODO Auto-generated catch block
				//					e1.printStackTrace();
				//					Reporter.log("Failed wile taking the screen shot");
				//					Assert.fail(e.toString());
				//				}
				// WriteHTMLLog.writeHtmlScreenShoftDeatils();
				System.out
				.println("Closing session at core actioon place in actiondriver method");
				// killinstance();
				Assert.fail("Error while performating action " + sInputData
						+ " for the field " + sFieldName + " on the "
						+ sScreenName + " screen", e);
			}

		}
	}

	public static void verifyDriver(String WidgetType, String sScreenWidget,
			String sInputData, String sFieldName, String sScreenName)
					throws InterruptedException, IOException {
		boolean elementExist = false;
		boolean elementEnable = false;
		WebElement element = null;
		WebElement element2 = null;
		
		WebElement invisiblepath=null;
		List<WebElement> element6 = null;
		List<WebElement> element7 = null;
		String TextboxValue = null;
		if (checkNull(sInputData)) {

			try {
				if (sScreenWidget.contains("xpath=")) {
					if (sScreenWidget.contains(";")) {
						String screenWidget[] = sScreenWidget.split(";");
						element = driver.findElement(By.xpath(screenWidget[0]
								.toString().replace("xpath=", "")));
						element2 = driver.findElement(By.xpath(screenWidget[1]
								.toString().replace("xpath=", "")));
					} else if (sScreenWidget.contains("&")) {
						String screenWidget[] = sScreenWidget.split("&");
						element = driver.findElement(By.xpath(screenWidget[0]
								.toString().replace("xpath=", "")));
						element6 =  driver.findElements(By.xpath(screenWidget[1]
								.toString().replace("xpath=", "")));
					} else if (sScreenWidget.contains("<")) {
						String screenWidget[] = sScreenWidget.split("<");
						element = driver.findElement(By.xpath(screenWidget[0]
								.toString().replace("xpath=", "")));
						TextboxValue =  screenWidget[1].toString().replace("ClassAttributeValue=", "");
					} /*else if (sScreenWidget.contains("findElements=")) {

						element7 = driver
								.findElements(By.xpath(sScreenWidget
										.replace("findElements=", "")));
					}*/else
						element = driver.findElement(By.xpath(sScreenWidget
								.replace("xpath=", "")));

				} else {
					if (sScreenWidget.contains("findElements=")) {
						element7 = driver.findElements(By.xpath(sScreenWidget
								.replace("findElements=", "")));
					} else if (sScreenWidget.contains("id=")) {
						element = driver.findElement(By.id(sScreenWidget
								.replace("id=", "")));
					} else if (sScreenWidget.contains("className=")) {
						element = driver.findElement(By.className(sScreenWidget
								.replace("className=", "")));
					} else if (sScreenWidget.contains("tagName=")) {
						element = driver.findElement(By.tagName(sScreenWidget
								.replace("tagName=", "")));
					} else if (sScreenWidget.contains("name=")) {
						element = driver.findElement(By.name(sScreenWidget
								.replace("name=", "")));
					} else if (sScreenWidget.contains("linkText=")) {
						element = driver.findElement(By.linkText(sScreenWidget
								.replace("linkText=", "")));
					} else if (sScreenWidget.contains("partialLinkText=")) {
						element = driver.findElement(By
								.partialLinkText(sScreenWidget.replace(
										"partialLinkText=", "")));

					} else if (sScreenWidget.contains("css=")) {
						element = driver
								.findElement(By.cssSelector(sScreenWidget
										.replace("css=", "")));
					} else {
						// Reporter.log("Provide valid locator. Example : id,name,linkText,partialLinkText");
						// WriteHTMLLog
						// .writeHtmlLogs("Provide valid locator. Example : id,name,linkText,partialLinkText");
					}

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Reporter.log(e.toString());
				WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.FAIL,"Error while verifying the '"+sFieldName+"' on screen '"+sScreenName);
				ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Error while verifying the '"+sFieldName+"' on screen '"+sScreenName);
				Reporter.log(e.toString());
				Assert.fail("Error while creating the web element instance : "
						+ e);
			}

			try {

				if (sInputData.equalsIgnoreCase("Display")) {
					if (WidgetType.equalsIgnoreCase("TextBox")
							|| WidgetType.equalsIgnoreCase("ListBox")
							|| WidgetType.equalsIgnoreCase("ListBoxNew")
							|| WidgetType.equalsIgnoreCase("Label")
							|| WidgetType.equalsIgnoreCase("Button")
							|| WidgetType.equalsIgnoreCase("RADIOBUTTON")
							|| WidgetType.equalsIgnoreCase("Checkbox")
							|| WidgetType.equalsIgnoreCase("Link")
							|| WidgetType.equalsIgnoreCase("LINKPartialText")) {
						if (checkNull(sInputData)) {
							elementExist = element.isDisplayed();
							String actual = "";
							if (elementExist) {
								logger.info("Verification Succesful element of type "
										+ WidgetType
										+ " for the field "
										+ sFieldName
										+ " is getting displayed on screen "
										+ sScreenName);
								WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"Verification Succesful element of type "
										+ WidgetType
										+ " for the field "
										+ sFieldName
										+ " is getting displayed on screen "
										+ sScreenName);
								ExtentReport.ExtentLogger.log(LogStatus.PASS,"Verification Succesful element of type "
										+ WidgetType
										+ " for the field "
										+ sFieldName
										+ " is getting displayed on screen "
										+ sScreenName);

							} else {
								logger.error("Verification Failed element of type "
										+ WidgetType
										+ " for the field "
										+ sFieldName
										+ " is not getting displayed on screen "
										+ sScreenName);
								WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"Verification Failed element of type "
										+ WidgetType
										+ " for the field "
										+ sFieldName
										+ " is not getting displayed on screen "
										+ sScreenName);
								ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Verification Failed element of type "
										+ WidgetType
										+ " for the field "
										+ sFieldName
										+ " is not getting displayed on screen "
										+ sScreenName);
							}
						}
					}

				} else if (sInputData.equalsIgnoreCase("Enable")) {
					if (WidgetType.equalsIgnoreCase("TextBox")
							|| WidgetType.equalsIgnoreCase("ListBoxNew")
							|| WidgetType.equalsIgnoreCase("ListBox")
							|| WidgetType.equalsIgnoreCase("Button")
							|| WidgetType.equalsIgnoreCase("RADIOBUTTON")
							|| WidgetType.equalsIgnoreCase("Link")
							|| WidgetType.equalsIgnoreCase("LINKPartialText")) {
						if (checkNull(sInputData)) {
							elementEnable = element.isEnabled();
							String classValue = element.getAttribute("class");
							String actual = "";
							//if (elementEnable || classValue.contains("editable")) {
							if (elementEnable) {
								logger.info("Verification Succesful element of type "
										+ WidgetType
										+ " for the field "
										+ sFieldName
										+ " is enabled on screen "
										+ sScreenName);
								/*WriteHTMLLog
										.writeHtmlLogs(getFormatedHTMLVerifyPASSTestStep(
												sFieldName, WidgetType,
												sInputData, "Enable ",
												sScreenName));*/

								WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"Successfully verified that field "
										+ sFieldName
										+ " of type "
										+ WidgetType
										+ " is enabled on screen "
										+ sScreenName);
								ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully verified that field "
										+ sFieldName
										+ " of type "
										+ WidgetType
										+ " is enabled on screen "
										+ sScreenName);

								/*customlogger.log(LogStatus.PASS, "Successfully verified the element of type "
										+ WidgetType
										+ " for the field "
										+ sFieldName
										+ " is enabled on screen "
										+ sScreenName);*/								

							} else {
								logger.error("Verification Failed element of type "
										+ WidgetType
										+ " for the field "
										+ sFieldName
										+ " is not enabled on screen "
										+ sScreenName);
								/*WriteHTMLLog
										.writeHtmlLogs(getFormatedHTMLVerifyFailTestStep(
												sFieldName, WidgetType,
												sInputData, " disabled",
												sScreenName));*/

								WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"Verification Failed element of type "
										+ WidgetType
										+ " for the field "
										+ sFieldName
										+ " is not enabled on screen "
										+ sScreenName);
								ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Verification Failed element of type "
										+ WidgetType
										+ " for the field "
										+ sFieldName
										+ " is not enabled on screen "
										+ sScreenName);


								/*customlogger.log(LogStatus.PASS, "Failed to verify the element of type "
								+ WidgetType
								+ " for the field "
								+ sFieldName
								+ " is enabled on screen "
								+ sScreenName);*/

							}

						}
					}

				} else if (sInputData.equalsIgnoreCase("Disable")) {
					if (WidgetType.equalsIgnoreCase("TextBox")
							|| WidgetType.equalsIgnoreCase("ListBoxNew")
							|| WidgetType.equalsIgnoreCase("ListBox")
							|| WidgetType.equalsIgnoreCase("Checkbox")
							|| WidgetType.equalsIgnoreCase("Button")
							|| WidgetType.equalsIgnoreCase("RADIOBUTTON")
							|| WidgetType.equalsIgnoreCase("Link")
							|| WidgetType.equalsIgnoreCase("LINKPartialText")) {
						if (checkNull(sInputData)) {
							System.out.println("Disable Block");
							elementEnable = element.isEnabled();
							String classValue = element.getAttribute("class");
							String actual = "";
							//if (!elementEnable || !classValue.contains("editable")) {
							if (!elementEnable) {
								Reporter.log("Successfully verified that field '"
										+ sFieldName
										+ "' of type '"
										+ WidgetType
										+ "' is disabled on screen "
										+ sScreenName,true);
								/*WriteHTMLLog
										.writeHtmlLogs(getFormatedHTMLVerifyPASSTestStep(
												sFieldName, WidgetType,
												sInputData, " Disable",
												sScreenName));*/

								WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"Successfully verified that field '"
										+ sFieldName
										+ "' of type '"
										+ WidgetType
										+ "' is disabled on screen "
										+ sScreenName);
								ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully verified that field '"
										+ sFieldName
										+ "' of type '"
										+ WidgetType
										+ "' is disabled on screen "
										+ sScreenName);
								/*customlogger.log(LogStatus.PASS, "Successfully verified the element of type "
								+ WidgetType
								+ " for the field "
								+ sFieldName
								+ " is disabled on screen "
								+ sScreenName);*/	
							} else {
								logger.error("Verification Failed element of type "
										+ WidgetType
										+ " for the field "
										+ sFieldName
										+ " is not disabled on screen and is not as expected"
										+ sScreenName);
								/*WriteHTMLLog
										.writeHtmlLogs(getFormatedHTMLVerifyFailTestStep(
												sFieldName, WidgetType,
												sInputData, "enabled",
												sScreenName));*/
								WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"Verification Failed for the type "
										+ WidgetType
										+ " of the field "
										+ sFieldName
										+ " is not disabled on screen "
										+ sScreenName);
								ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Verification Failed for the type "
										+ WidgetType
										+ " of the field "
										+ sFieldName
										+ " is not disabled on screen "
										+ sScreenName);

								/*customlogger.log(LogStatus.PASS, "Failed to verify the element of type "
								+ WidgetType
								+ " for the field "
								+ sFieldName
								+ " is disabled on screen "
								+ sScreenName);*/
							}

						}
					}

				} else if (sInputData.contains("ClassAttribute")) {
							if (checkNull(sInputData)) {
							String classValue = element.getAttribute("class");
							if(classValue.equals(TextboxValue)){
							Reporter.log("Successfully verified the status (Enable/Disable) of the field '"+ sFieldName+ "' on screen "+ sScreenName,true);
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"Successfully verified the status (Enable/Disable) of the field '"+ sFieldName+ "' on screen " + sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully verified the status (Enable/Disable) of the field '"+ sFieldName+ "' on screen " + sScreenName);
							} 
							else {
							logger.error("Failed to verif the status (Enable/Disable) for the field "+ sFieldName+ "' on screen "+ sScreenName);
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"Failed to verify the status (Enable/Disable) of the field "+ sFieldName+ "' on screen "+ sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Failed to verify the status (Enable/Disable) of the field "+ sFieldName+ "' on screen "+ sScreenName);
							}
						}
				} else if (WidgetType.equalsIgnoreCase("NotPresent")) {
					if (checkNull(sInputData)) {
						System.out.println("Size="+element7.size());
						Reporter.log("Entered in to loop to check absence of an element",true);
						if (element7.size()==0)
						{ 
							logger.info("The field "+ sFieldName+ " does not exist on the screen " + sScreenName);

							Reporter.log("The field "+ sFieldName+ " does not exist on the screen " + sScreenName);
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"The field "+ sFieldName+ " does not exist on the screen " + sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"The field "+ sFieldName+ " does not exist on the screen " + sScreenName);

						}
						else
						{
							//System.out.println("AKN- Error");
							logger.info("Element "+ " " + " " + sFieldName+ "  Exist on the screen " + sScreenName);

							Reporter.log("Element "+ " " + " " + sFieldName+ "  Exist on the screen " + sScreenName);
							/*WriteHTMLLog.writeHtmlLogs(getFormatedHTMLFailTestStep(sFieldName, "Commit Changes Button", "Present",sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"Element "+ " " + " " + sFieldName+ "  Exist on the screen " + sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Element "+ " " + " " + sFieldName+ "  Exist on the screen " + sScreenName);
							/*customlogger.log(LogStatus.FAIL, "Failed to verify for the field "
                                    + sFieldName
                                    + " of type "
                                    + WidgetType
                                    + " data "
                                    + sInputData
                                    + " on screen "
                                    + sScreenName");*/
						}
					}
				} else if (WidgetType.equalsIgnoreCase("IsPresent")) {

					if (checkNull(sInputData)) {
						
						Reporter.log("Entered in to loop to check presence of an element");

						if (element7.size()!=0)

						{
							logger.info("Element   "
									+ "" + "" + sFieldName
									+ " Exist on the screen " + sScreenName);

							Reporter.log("Element  "
									+ "" + " " + sFieldName
									+ "Exist on the screen " + sScreenName);
							/*WriteHTMLLog
										.writeHtmlLogs(getFormatedHTMLPASSTestStep(
												sFieldName, WidgetType, sInputData,
												sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"Element   "
									+ "" + "" + sFieldName
									+ " Exist on the screen " + sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Element   "
									+ "" + "" + sFieldName
									+ " Exist on the screen " + sScreenName);
							/*customlogger.log(LogStatus.PASS, "Successfully verified the value "
                                + sInputData + " in the TextBox " + sFieldName
                                + " on the screen " + sScreenName);*/



						}
						else
						{
							logger.info("Element "+ " " + " " + sFieldName+ "  Not Exist on the screen " + sScreenName);
							Reporter.log("Element "+ " " + " " + sFieldName+ "  Not Exist on the screen " + sScreenName);
							/*WriteHTMLLog.writeHtmlLogs(getFormatedHTMLFailTestStep(sFieldName, "Commit Changes Button", " not present ",sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"Element "+ " " + " " + sFieldName+ "  Not Exist on the screen " + sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Element "+ " " + " " + sFieldName+ "  Not Exist on the screen " + sScreenName);
							/*customlogger.log(LogStatus.FAIL, "Failed to verify for the field "
                                    + sFieldName
                                    + " of type "
                                    + WidgetType
                                    + " data "
                                    + sInputData
                                    + " on screen "
                                    + sScreenName");*/
						}

					}
				} else if (WidgetType.equalsIgnoreCase("CheckItemExistence")) {
					if (checkNull(sInputData)) {
						element.click();
						String[] items=new String[element6.size()];
						String[] temp;
						String tempItems = "";

						//Fetching the the drop down options text 
						for (int i = 0; i < element6.size(); i++)
						{
							items[i]=element6.get(i).getText().trim(); 
						} 
						if(sInputData.contains("|")){
							String delimiter = "\\|";                 
							temp = sInputData.split(delimiter);
							for(int i=0; i< temp.length; i++)  
							{
								temp[i] = temp[i].trim();
								if(!(Arrays.asList(items).contains(temp[i])))
								{
									tempItems = temp[i];
									Reporter.log("***********************************Item not found="+tempItems+"********************************************");
									System.out.println("***********************************Item not found="+tempItems+"********************************************");
								}
							}
						}
						logger.info("Options available in the dropdown of application and user expected is same "
								+ sInputData + " in the dropdown " + sFieldName
								+ " on the screen " + sScreenName);
						Reporter.log("Options available in the dropdown of application and user expected is same "
								+ sInputData + "  in the dropdown " + sFieldName
								+ " on the screen " + sScreenName);
						/*WriteHTMLLog
						.writeHtmlLogs(getFormatedHTMLPASSTestStep(
								sFieldName, WidgetType, sInputData,
								sScreenName));*/
						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully verified that following options is displayed for the field '"
								+ sFieldName + "' on the screen '"+ sScreenName+
								"<br>Actual Result : "+sInputData);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully verified that following options is displayed for the field '"
								+ sFieldName + "' on the screen '"+ sScreenName+
								"<br>Actual Result : "+sInputData);

					}
				} else if (WidgetType.equalsIgnoreCase("CheckItemNonExistence")) {
					if (checkNull(sInputData)) {
						element.click();
						String[] items=new String[element6.size()];
						String[] temp;
						String tempItems = "";
						//Fetching the the drop down options text 
						for (int i = 0; i < element6.size(); i++)
						{
							items[i]=element6.get(i).getText().trim(); 
						} 
						if(sInputData.contains("|")){
							String delimiter = "\\|";                 
							temp = sInputData.split(delimiter);
							for(int i=0; i< temp.length; i++)  
							{
								temp[i] = temp[i].trim();
								if((Arrays.asList(items).contains(temp[i])))
								{
									tempItems = tempItems+temp[i]+";"; 
									Reporter.log("***********************************Item found="+tempItems+"********************************************");
									System.out.println("***********************************Item found="+tempItems+"********************************************");
								}
							}
						}
						logger.info("Options NOT available in the dropdown of application and user expected is same "
								+ sInputData + " in the dropdown " + sFieldName
								+ " on the screen " + sScreenName);
						Reporter.log("Options NOT available in the dropdown of application and user expected is same "
								+ sInputData + "  in the dropdown " + sFieldName
								+ " on the screen " + sScreenName);
						/*WriteHTMLLog
						.writeHtmlLogs(getFormatedHTMLPASSTestStep(
								sFieldName, WidgetType, sInputData,
								sScreenName));*/
						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Successfully verified that following options is not displayed for the field '"
								+ sFieldName + "' on the screen '"+ sScreenName+
								"<br>Actual Result : "+sInputData);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully verified that following options is not displayed for the field '"
								+ sFieldName + "' on the screen '"+ sScreenName+
								"<br>Actual Result : "+sInputData);

					}
				} else if (WidgetType.equalsIgnoreCase("Label")) {
					if (checkNull(sInputData)) {
						sInputData = sInputData.replace("**","");
						String value = element.getText();
						String actual = value;
						if (value.contains(sInputData)) {
							logger.info("Label " + sInputData
									+ " is getting displayed on the screen "+sScreenName);
							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyPASSTestStep(
											sFieldName, WidgetType, sInputData,
											actual, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"Label '" + sInputData
									+ "' is getting displayed for the field '"+sFieldName+"' on the screen "+sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Label '" + sInputData
									+ "' is getting displayed for the field '"+sFieldName+"' on the screen "+sScreenName);
						} else {
							logger.error("Label '"
									+ sInputData
									+ "' is not getting displayed for the field '"+sFieldName+"' on the screen "+sScreenName+". Instead the following value is displayed: "
									+ value);
							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyFailTestStep(
											sFieldName, WidgetType, sInputData,
											actual, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"Label "
									+ sInputData
									+ " is not getting displayed. Instead the following value is displayed: "
									+ value);
							ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Label "
									+ sInputData
									+ " is not getting displayed. Instead the following value is displayed: "
									+ value);
							/*customlogger.log(LogStatus.PASS, "Successfully verified the value "
                            + sInputData + " on the label " + sFieldName
                            + " on the screen " + sScreenName);*/
						}

					}
				} else if (WidgetType.equalsIgnoreCase("Label-NA")) {
					if (checkNull(sInputData)) {
						sInputData = sInputData.replace("*","");
						String value = element.getText();
						String actual = value;
						if (value.equals(sInputData)) {
							logger.info("Label " + sInputData
									+ " is getting displayed on the screen "+sScreenName);
							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyPASSTestStep(
											sFieldName, WidgetType, sInputData,
											actual, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"Label '" + sInputData
									+ "' is getting displayed on the screen "+sScreenName);
						} else {
							logger.error("Label '"
									+ sInputData
									+ "' is not getting displayed on the screen "+sScreenName+". Instead the following value is displayed: "
									+ value);
							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyFailTestStep(
											sFieldName, WidgetType, sInputData,
											actual, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"Label "
									+ sInputData
									+ " is not getting displayed. Instead the following value is displayed: "
									+ value);
							ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Label "
									+ sInputData
									+ " is not getting displayed. Instead the following value is displayed: "
									+ value);
							/*customlogger.log(LogStatus.PASS, "Successfully verified the value "
                            + sInputData + " on the label " + sFieldName
                            + " on the screen " + sScreenName);*/
						}

					}
				}/* else if (WidgetType.equalsIgnoreCase("GetValue")) {
                    if (checkNull(sInputData)) {
                        String browserURL="http://www.google.com";
                          Selenium selenium = new WebDriverBackedSelenium(driver,browserURL );
                          String value1=selenium.getValue(TextboxValue); 
                          String value2=sInputData.toString();
                                    if (value1.equals(value2))
                                    {           
                                    Thread.sleep(3000);
                                    logger.info("Label " + sInputData
                                                            + " is getting displayed");
                                    WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"Label '" + sInputData
                                                            + "' is getting displayed");
                             }
                                    else
                                    {
                                                logger.error("Label "
                                                                        + sInputData
                                                                        + " is not getting displayed. Instead the following value is displayed: "
                                                                        + value1);
                                                
                                                WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"Label "
                                                                        + sInputData
                                                                        + " is not getting displayed. Instead the following value is displayed: "
                                                                        + value1);
                                    }
                    			}
				} */else if (WidgetType.equalsIgnoreCase("ListBoxNew")) {
					if (checkNull(sInputData)) {

						String actualText=element.getText();
//												
						//The below replace code is added, since issue was faced while fetching the value present in 
						//listboxes of PacingGridVP-Filters section
						String actual = actualText.replace("×", "");
						
						Thread.sleep(sleepTime);
						if (actual.equals(sInputData)) {
						logger.info("Actual Text in the application = '"
								+ actual
								+ "' is matching with the expected text = '"
								+ sInputData + "'");
						/*WriteHTMLLog
								.writeHtmlLogs(getFormatedHTMLVerifyPASSTestStep(
										sFieldName, WidgetType, sInputData,
										" displayed", sScreenName));*/
						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"Successfully verified the field "
								+sFieldName+" of type "
								+WidgetType+" on screen "
								+sScreenName+
								"<br>Expected Result : "+sInputData+"<br>Actual Result : "+actual);
						ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully verified the field "
								+sFieldName+" of type "
								+WidgetType+" on screen "
								+sScreenName+
								"<br>Expected Result : "+sInputData+"<br>Actual Result : "+actual);
						/*customlogger.log(LogStatus.PASS, "Successfully verified the value "
                        + sInputData + " on the ListBox " + sFieldName
                        + " on the screen " + sScreenName);*/
						} else {

							logger.info("Actual Text in the application = '"
									+ actual
									+ "'  is not matching with the expected text = '"
									+ sInputData + "'");

							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyFailTestStep(
											sFieldName, WidgetType, sInputData,
											actual, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"Failed to verifyfield "
									+sFieldName+" of type "
									+WidgetType+" on screen "
									+sScreenName+
									"<br>Expected Result : "+sInputData+"<br>Actual Result : "+actual);
							ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Failed to verifyfield "
									+sFieldName+" of type "
									+WidgetType+" on screen "
									+sScreenName+
									"<br>Expected Result : "+sInputData+"<br>Actual Result : "+actual);

							/*customlogger.log(LogStatus.FAIL, "Failed to verify the value "
	                        + sInputData + " on the ListBox " + sFieldName
	                        + " on the screen " + sScreenName);*/
						}
					}

				} else if (WidgetType.equalsIgnoreCase("ListBox")) {
					if (checkNull(sInputData)) {
						//String actualText = element.getText();


						Select select = new Select(element); 
						String actualText=select.getFirstSelectedOption().getText();

						String actual = actualText;

						if (actualText.equals(sInputData)) {
							logger.info("Actual Text in the application = '"
									+ actualText
									+ "' is matching with the expected text = '"
									+ sInputData + "'");

							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyPASSTestStep(
											sFieldName, WidgetType, sInputData,
											actual, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"Successfully verified the field "
									+sFieldName+" of type "
									+WidgetType+" on screen "
									+sScreenName+
									"<br>Expected Result : "+sInputData+"<br>Actual Result : "+actualText);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully verified the field "
									+sFieldName+" of type "
									+WidgetType+" on screen "
									+sScreenName+
									"<br>Expected Result : "+sInputData+"<br>Actual Result : "+actualText);
							/*customlogger.log(LogStatus.PASS, "Successfully verified the value "
	                        + sInputData + " on the ListBox " + sFieldName
	                        + " on the screen " + sScreenName);*/

						} else {

							logger.info("Actual Text in the application = '"
									+ actualText
									+ "'  is not matching with the expected text = '"
									+ sInputData + "'");

							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyFailTestStep(
											sFieldName, WidgetType, sInputData,
											actual, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"Failed to verifyfield "
									+sFieldName+" of type "
									+WidgetType+" on screen "
									+sScreenName+
									"<br>Expected Result : "+sInputData+"<br>Actual Result : "+actualText);
							
							ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Failed to verifyfield "
									+sFieldName+" of type "
									+WidgetType+" on screen "
									+sScreenName+
									"<br>Expected Result : "+sInputData+"<br>Actual Result : "+actualText);
							/*customlogger.log(LogStatus.FAIL, "Failed to verify the value "
	                        + sInputData + " on the ListBox " + sFieldName
	                        + " on the screen " + sScreenName);*/
						}
					}



				} else if (WidgetType.equalsIgnoreCase("TextBoxEmpty")) {
					if (checkNull(sInputData)) {
						//String actualText = element.getText();

						//sInputData=null;
						String actualText = element.getAttribute("value");
						String actual = actualText;
						//System.out.println("Client AE:"+driver.findElement(By.id("deal_cae_app_user_id")).getAttribute("value"));

											
						//if (actualText.length()<=0) {
						if (actualText.isEmpty()|| actualText.equals("-1") ) {
							logger.info("The textbox "
									+sFieldName+ " on screen "
									+sScreenName+"is empty");

							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyPASSTestStep(
											sFieldName, WidgetType, sInputData,
											actual, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"The textbox "
									+sFieldName+ " on screen "
									+sScreenName+" is empty");
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"The textbox "
									+sFieldName+ " on screen "
									+sScreenName+" is empty");
							/*customlogger.log(LogStatus.PASS, "Successfully verified the value "
	                        + sInputData + " on the ListBox " + sFieldName
	                        + " on the screen " + sScreenName);*/

						} else {

							logger.info("The textbox "
									+sFieldName+ " on screen "
									+sScreenName+"is not empty");

							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyFailTestStep(
											sFieldName, WidgetType, sInputData,
											actual, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"The textbox "
									+sFieldName+ " on screen "
									+sScreenName+" is not empty");
							ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Failed to verifyfield "
									+sFieldName+" of type "
									+WidgetType+" on screen "
									+sScreenName+
									"<br>Expected Result : "+sInputData+"<br>Actual Result : "+actualText);

							/*customlogger.log(LogStatus.FAIL, "Failed to verify the value "
	                        + sInputData + " on the ListBox " + sFieldName
	                        + " on the screen " + sScreenName);*/
						}
					}



				} else if (WidgetType.equalsIgnoreCase("Empty")) {
					if (checkNull(sInputData)) {
						//String actualText = element.getText();

						//sInputData=null;
						String actualText = element.getAttribute("value");
						String actual = actualText;
						//System.out.println("Client AE:"+driver.findElement(By.id("deal_cae_app_user_id")).getAttribute("value"));

											
						//if (actualText.length()<=0) {
						if (actualText.isEmpty()|| actualText.equals("-1") ) {
							logger.info("The listbox "
									+sFieldName+ " on screen "
									+sScreenName+"is empty");

							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyPASSTestStep(
											sFieldName, WidgetType, sInputData,
											actual, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"The listbox "
									+sFieldName+ " on screen "
									+sScreenName+" is empty");
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"The listbox "
									+sFieldName+ " on screen "
									+sScreenName+" is empty");
							/*customlogger.log(LogStatus.PASS, "Successfully verified the value "
	                        + sInputData + " on the ListBox " + sFieldName
	                        + " on the screen " + sScreenName);*/

						} else {

							logger.info("The listbox "
									+sFieldName+ " on screen "
									+sScreenName+"is not empty");

							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyFailTestStep(
											sFieldName, WidgetType, sInputData,
											actual, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"The listbox "
									+sFieldName+ " on screen "
									+sScreenName+" is not empty");
							ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"The listbox "
									+sFieldName+ " on screen "
									+sScreenName+" is not empty");

							/*customlogger.log(LogStatus.FAIL, "Failed to verify the value "
	                        + sInputData + " on the ListBox " + sFieldName
	                        + " on the screen " + sScreenName);*/
						}
					}



				}  /* else if (WidgetType.equalsIgnoreCase("Button")) {
				if (checkNull(sInputData)) {
				element.click();
				logger.info("Successfully Clicked the '" + sFieldName
						+ "' Button on the screen '" + sScreenName



						+ "'");
			}

		}*/ 
				else if (WidgetType.equalsIgnoreCase("Compare")) {
					if (checkNull(sInputData)) {
						String Val1=element.getText();
						String Val2=element2.getText();

						//System.out.println(Val1);
						//System.out.println(Val2);
						Thread.sleep(3000);
						if (Val1.equals(Val2))
						{	
							Thread.sleep(3000);
							logger.info("Values of '"+sInputData+"' are same on the screen " + sScreenName
									+"\n"+"Value1="+Val1+" & Value2="+Val2);
							Reporter.log("Values of '"+sInputData+"' are same on the screen " + sScreenName
									+"\n"+"Value1="+Val1+" & Value2="+Val2);

							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.PASS,"Values of '"+sInputData+"' are same on the screen " + sScreenName
									+"\n"+"Value1="+Val1+" & Value2="+Val2);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Values of '"+sInputData+"' are same on the screen " + sScreenName
									+"\n"+"Value1="+Val1+" & Value2="+Val2);
						}

						else
						{
							logger.info("Values of '"+sInputData+"' are not same on the screen " + sScreenName
									+"\n"+"Value1="+Val1+" & Value2="+Val2);
							Reporter.log("Values of '"+sInputData+"' are not same on the screen " + sScreenName
									+"\n"+"Value1="+Val1+" & Value2="+Val2);
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.FAIL,"Values of '"+sInputData+"' are same on the screen " + sScreenName
									+"\n"+"Value1="+Val1+" & Value2="+Val2);
							ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Values of '"+sInputData+"' are same on the screen " + sScreenName
									+"\n"+"Value1="+Val1+" & Value2="+Val2);
						}
					}
				} else if (WidgetType.equalsIgnoreCase("ListDataverify")) {
					if (checkNull(sInputData)) {
						String[] userinput=sInputData.toString().split("\\|");
						int count=element7.size();
						System.out.println(count);
						ArrayList<String> listdata = new ArrayList<String>();
						for(int i=0;i<=count-1;i++)
						{
							System.out.println(element7.get(i).getText());
							
							listdata.add(element7.get(i).getText());
						}
						

						if(listdata.size()==userinput.length)
						{
								
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"Field Size is  matching "
									+sFieldName+" of type "
									+WidgetType+" on screen "
									+sScreenName+
									"<br>Expected Result : "+listdata.size()+"<br>Actual Result : "+userinput.length);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Field Size is  matching "
									+sFieldName+" of type "
									+WidgetType+" on screen "
									+sScreenName+
									"<br>Expected Result : "+listdata.size()+"<br>Actual Result : "+userinput.length);
						}
						else
						{
							
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"Field Size is not matching "
									+sFieldName+" of type "
									+WidgetType+" on screen "
									+sScreenName+
									"<br>Expected Result : "+listdata.size()+"<br>Actual Result : "+userinput.length);
							ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Field Size is not matching "
									+sFieldName+" of type "
									+WidgetType+" on screen "
									+sScreenName+
									"<br>Expected Result : "+listdata.size()+"<br>Actual Result : "+userinput.length);
						}
						for(int i = 0; i < userinput.length; i++) {   
								    
									if(listdata.get(i).equals(userinput[i]))
									{
										System.out.println("pass");
										logger.info("Actual Text in the application = '"
												+ listdata.get(i)
												+ "'  is  matching with the expected text = '"
												+ userinput[i] + "'");
									    
										WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"Successfully verified the field "
												+sFieldName+" of type "
												+WidgetType+" on screen "
												+sScreenName+
												"<br>Expected Result : "+listdata.get(i)+"<br>Actual Result : "+userinput[i]);
										ExtentReport.ExtentLogger.log(LogStatus.PASS,"Successfully verified the field "
												+sFieldName+" of type "
												+WidgetType+" on screen "
												+sScreenName+
												"<br>Expected Result : "+listdata.get(i)+"<br>Actual Result : "+userinput[i]);
									}
									
									else
									{
										System.out.println("fail");
									
										logger.info("Actual Text in the application = '"
												+ listdata.get(i)
												+ "'  is not matching with the expected text = '"
												+ userinput[i] + "'");

										/*WriteHTMLLog
												.writeHtmlLogs(getFormatedHTMLVerifyFailTestStep(
														sFieldName, WidgetType, sInputData,
														actual, sScreenName));*/
										WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"Failed to verifyfield "
												+sFieldName+" of type "
												+WidgetType+" on screen "
												+sScreenName+
												"<br>Expected Result : "+listdata.get(i)+"<br>Actual Result : "+userinput[i]);
										ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Failed to verifyfield "
												+sFieldName+" of type "
												+WidgetType+" on screen "
												+sScreenName+
												"<br>Expected Result : "+listdata.get(i)+"<br>Actual Result : "+userinput[i]);
									}
									
						}
						
						}

					
				}else if (WidgetType.equalsIgnoreCase("Button")) {
					if (checkNull(sInputData)) {

						String actualText = element.getText();
						String actual = actualText;


						if (actualText.equals(sInputData)) {
							logger.info("Actual Text in the application '"
									+ actualText
									+ "' is matching with the expected text '"
									+ sInputData + "' on the screen '"+sScreenName);

							/*WriteHTMLLog
							.writeHtmlLogs(getFormatedHTMLVerifyPASSTestStep(
									sFieldName, WidgetType, sInputData,
									actual, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"Actual Text in the application = '"
									+ actualText
									+ "' is matching with the expected text = '"
									+ sInputData+"' on the screen "+sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Actual Text in the application = '"
									+ actualText
									+ "' is matching with the expected text = '"
									+ sInputData+"' on the screen "+sScreenName);
							/*customlogger.log(LogStatus.PASS, "Successfully verified the value "
                    + sInputData + " on the Button " + sFieldName
                    + " on the screen " + sScreenName);*/
						} else {

							logger.info("Actual Text in the application = '"
									+ actualText
									+ "'  is not matching with the expected text = '"
									+ sInputData + "'");

							/*WriteHTMLLog
							.writeHtmlLogs(getFormatedHTMLVerifyFailTestStep(
									sFieldName, WidgetType, sInputData,
									actual, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"Actual Text in the application = '"
									+ actualText
									+ "'  is not matching with the expected text = '"
									+ sInputData+"' on the screen "+sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Actual Text in the application = '"
									+ actualText
									+ "'  is not matching with the expected text = '"
									+ sInputData+"' on the screen "+sScreenName);
							/*customlogger.log(LogStatus.FAIL, "Failed to verifythe value "
                    + sInputData + " on the Button " + sFieldName
                    + " on the screen " + sScreenName);*/

						}
					}

				} else if (WidgetType.equalsIgnoreCase("BUTTON_IMAGE")) {
					if (checkNull(sInputData)) {
						element.click();
						((JavascriptExecutor) driver).executeScript(
								"arguments[0].click();", element);
						logger.info("Successfully clicked the '" + sFieldName
								+ "' Button on the screen '" + sScreenName
								+ "'");
					}

				} else if (WidgetType.equalsIgnoreCase("RADIOBUTTON")) {
					if (checkNull(sInputData)) {

						boolean currentRadioBtnState = false;

						currentRadioBtnState = element.isSelected();

						System.out
						.println("Expected Radio Button State for the field  is - "
								+ sInputData);
						System.out
						.println("Current Radio Button State for the field is - "
								+ currentRadioBtnState);

						if (currentRadioBtnState == true
								&& (sInputData.equalsIgnoreCase("Yes")
										|| sInputData.equalsIgnoreCase("-1") || sInputData
										.equalsIgnoreCase("1"))) {
							logger.info("Radio Button is already selected '"
									+ sFieldName + "' on the screen '"
									+ sScreenName + "'");
							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyPASSTestStep(
											sFieldName, WidgetType, sInputData,
											" checked ", sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"Radio Button is already selected '"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Radio Button is already selected '"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							/*customlogger.log(LogStatus.PASS, "Successfully verified the value "
		                    + sInputData + " on the RadioButton " + sFieldName
		                    + " on the screen " + sScreenName);*/
						}
						if (currentRadioBtnState == false
								&& (sInputData.equalsIgnoreCase("0") || sInputData
										.equalsIgnoreCase("No"))) {
							// element.click();
							logger.info("Radio Button is not selected '"
									+ sFieldName + "' on the screen '"
									+ sScreenName + "'");
							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyPASSTestStep(
											sFieldName, WidgetType, sInputData,
											" not selected ", sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"Radio Button is not selected '"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Radio Button is not selected '"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							/*customlogger.log(LogStatus.FAIL, "Failed to verify the value "
		                    + sInputData + " on the RadioButton " + sFieldName
		                    + " on the screen " + sScreenName);*/
						}

					}

				} else if (WidgetType.equalsIgnoreCase("Link")) {
					if (checkNull(sInputData)) {
						String value = element.getText();
						String actual = value;
						if (value.contains(sInputData)) {
							logger.info("Link " + sInputData
									+ " is getting displayed");
							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyPASSTestStep(
											sFieldName, WidgetType, sInputData,
											actual, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"Link " + sInputData
									+ " is getting displayed on the screen "+sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Link " + sInputData
									+ " is getting displayed on the screen "+sScreenName);
							/*customlogger.log(LogStatus.PASS, "Successfully verified the date "
		                    + sInputData + " of link " + sFieldName
		                    + " on the screen " + sScreenName);*/
						} else {
							logger.error("Link " + sInputData
									+ " is not getting displayed");
							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyFailTestStep(
											sFieldName, WidgetType, sInputData,
											actual, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"Link " + sInputData
									+ " is not getting displayed on the screen "+sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Link " + sInputData
									+ " is not getting displayed on the screen "+sScreenName);
							/*customlogger.log(LogStatus.FAIL, "Failed to verify the value "
		                    + sInputData + " of link " + sFieldName
		                    + " on the screen " + sScreenName);*/
						}
					}

				} else if (WidgetType.equalsIgnoreCase("DynamicTableLink")) {
					if (checkNull(sInputData)) {
						String value = driver.findElement(
								By.partialLinkText(sInputData)).getText();
						String actual = value;
						if (value.contains(sInputData)) {
							logger.info("Link " + sInputData
									+ " is getting displayed");
							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyPASSTestStep(
											sFieldName, WidgetType, sInputData,
											actual, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"Link " + sInputData
									+ " is getting displayed");
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Link " + sInputData
									+ " is getting displayed");
							/*customlogger.log(LogStatus.PASS, "Successfully verified the value "
		                    + sInputData + " on the dynamic list " + sFieldName
		                    + " on the screen " + sScreenName);*/
						} else {
							logger.error("Link " + sInputData
									+ " is not getting displayed");
							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyFailTestStep(
											sFieldName, WidgetType, sInputData,
											actual, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"Link " + sInputData
									+ " is not getting displayed");
							ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Link " + sInputData
									+ " is not getting displayed");
							/*customlogger.log(LogStatus.PASS, "Failed to verify the value "
		                    + sInputData + " on the dynamic list " + sFieldName
		                    + " on the screen " + sScreenName);*/
						}

					}
				} else if (WidgetType.equalsIgnoreCase("LINKPartialText")) {
					if (checkNull(sInputData)) {
						element.click();
						logger.info("Successfully clicked the link '"
								+ sFieldName + "' on the screen '"
								+ sScreenName + "'");
					}

				} else if (WidgetType.equalsIgnoreCase("Checkbox")) {
					if (checkNull(sInputData)) {
						
						boolean currentChkBoxState = element.isSelected();
						
						System.out
						.println("(Verify)Expected Checkbox State for the field  is - "
								+ sInputData);
						System.out
						.println("(Verify)Current/Actual Checkbox State for the field is - "
								+ currentChkBoxState);

						if (currentChkBoxState == true
								&& (sInputData.equalsIgnoreCase("Yes")
										|| sInputData.equalsIgnoreCase("-1") || sInputData
										.equalsIgnoreCase("1"))) {
							logger.info("Checkbox is checked '"
									+ sFieldName + "' on the screen '"
									+ sScreenName + "'");
							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyPASSTestStep(
											sFieldName, WidgetType, sInputData,
											" checked ", sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"Checkbox is checked for the field '"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Checkbox is checked for the field '"
									+ sFieldName + "' on the screen '"
									+ sScreenName);

						} else if (currentChkBoxState == true
								&& (sInputData.equalsIgnoreCase("No")
										|| sInputData.equalsIgnoreCase("0"))) {
							logger.info("Checkbox is not checked for the field '"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"Checkbox is checked for the field '"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Checkbox is checked for the field '"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
						} else if (currentChkBoxState == false
								&& (sInputData.equalsIgnoreCase("0") || sInputData
										.equalsIgnoreCase("No"))) {
							// element.click();
							logger.info("Checkbox is not checked '"
									+ sFieldName + "' on the screen '"
									+ sScreenName + "'");
							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyPASSTestStep(
											sFieldName, WidgetType, sInputData,
											" not checked ", sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"Checkbox is not checked '"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"Checkbox is not checked '"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							/*customlogger.log(LogStatus.PASS, "Failed to verify the value "
		                    + sInputData + " of checkbox " + sFieldName
		                    + " on the screen " + sScreenName);*/
						}
						else if (currentChkBoxState == false
								&& (sInputData.equalsIgnoreCase("Yes")
										|| sInputData.equalsIgnoreCase("-1") || sInputData
										.equalsIgnoreCase("1"))) {
							// element.click();
							logger.info("Checkbox is already checked for the field'"
									+ sFieldName + "' on the screen '"
									+ sScreenName + "'");
							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyPASSTestStep(
											sFieldName, WidgetType, sInputData,
											" not checked ", sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"Checkbox is not checked for the field '"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Checkbox is not checked for the field '"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							/*customlogger.log(LogStatus.PASS, "Failed to verify the value "
		                    + sInputData + " of checkbox " + sFieldName
		                    + " on the screen " + sScreenName);*/
						}
					}
			} 
				//Widget was designed to check the status of 'Checkbox' in Pacing Grid VP. SInce the existing checkbox widget was not able to verify
				else if (WidgetType.equalsIgnoreCase("Checkbox2")) {
					if (checkNull(sInputData)) {
					boolean currentChkBoxState=false;
					String Class = element.getAttribute("class");
					
					if (Class.contains("selected")) {
					currentChkBoxState=true;
					
					} else {
						currentChkBoxState=false;
						
					}
				if (currentChkBoxState == true
						&& (sInputData.equalsIgnoreCase("Yes")
								|| sInputData.equalsIgnoreCase("-1") || sInputData
								.equalsIgnoreCase("1"))) {
					logger.info("Checkbox is checked '"
							+ sFieldName + "' on the screen '"
							+ sScreenName + "'");
					/*WriteHTMLLog
							.writeHtmlLogs(getFormatedHTMLVerifyPASSTestStep(
									sFieldName, WidgetType, sInputData,
									" checked ", sScreenName));*/
					WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"Checkbox is checked for the field '"
							+ sFieldName + "' on the screen '"
							+ sScreenName);
					ExtentReport.ExtentLogger.log(LogStatus.PASS,"Checkbox is checked for the field '"
							+ sFieldName + "' on the screen '"
							+ sScreenName);
					/*customlogger.log(LogStatus.PASS, "Successfully verified the value "
                    + sInputData + " of checkbox " + sFieldName
                    + " on the screen " + sScreenName);*/
				} else if (currentChkBoxState == true
						&& (sInputData.equalsIgnoreCase("No")
								|| sInputData.equalsIgnoreCase("0"))) {
					logger.info("Checkbox is not checked for the field '"
							+ sFieldName + "' on the screen '"
							+ sScreenName);
					WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"Checkbox is checked for the field '"
							+ sFieldName + "' on the screen '"
							+ sScreenName);
					ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Checkbox is checked for the field '"
							+ sFieldName + "' on the screen '"
							+ sScreenName);
				} else if (currentChkBoxState == false
						&& (sInputData.equalsIgnoreCase("0") || sInputData
								.equalsIgnoreCase("No"))) {
					// element.click();
					logger.info("Checkbox is not checked '"
							+ sFieldName + "' on the screen '"
							+ sScreenName + "'");
					/*WriteHTMLLog
							.writeHtmlLogs(getFormatedHTMLVerifyPASSTestStep(
									sFieldName, WidgetType, sInputData,
									" not checked ", sScreenName));*/
					WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"Checkbox is not checked '"
							+ sFieldName + "' on the screen '"
							+ sScreenName);
					ExtentReport.ExtentLogger.log(LogStatus.PASS,"Checkbox is not checked '"
							+ sFieldName + "' on the screen '"
							+ sScreenName);
					/*customlogger.log(LogStatus.PASS, "Failed to verify the value "
                    + sInputData + " of checkbox " + sFieldName
                    + " on the screen " + sScreenName);*/
				}
				else if (currentChkBoxState == false
						&& (sInputData.equalsIgnoreCase("Yes")
								|| sInputData.equalsIgnoreCase("-1") || sInputData
								.equalsIgnoreCase("1"))) {
					// element.click();
					logger.info("Checkbox is already checked for the field'"
							+ sFieldName + "' on the screen '"
							+ sScreenName + "'");
					/*WriteHTMLLog
							.writeHtmlLogs(getFormatedHTMLVerifyPASSTestStep(
									sFieldName, WidgetType, sInputData,
									" not checked ", sScreenName));*/
					WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"Checkbox is not checked for the field '"
							+ sFieldName + "' on the screen '"
							+ sScreenName);
					ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Checkbox is not checked for the field '"
							+ sFieldName + "' on the screen '"
							+ sScreenName);
					/*customlogger.log(LogStatus.PASS, "Failed to verify the value "
                    + sInputData + " of checkbox " + sFieldName
                    + " on the screen " + sScreenName);*/
				}
				}
				
				
				
			} else if (WidgetType.equalsIgnoreCase("Textbox")) {
					if (checkNull(sInputData)) {
						String actualText = element.getText();
						String actual = actualText;
						if (actualText.equals(sInputData)) {
							logger.info("Actual Text in the application = '"
									+ actualText
									+ "' is matching with the expected text = '"
									+ sInputData + "'");

							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyPASSTestStep(
											sFieldName, WidgetType, sInputData,
											actualText, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"For the field '"+sFieldName+"' Actual Text in the application = '"
									+ actualText
									+ "' is matching with the expected text = '"
									+ sInputData);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"For the field '"+sFieldName+"' Actual Text in the application = '"
									+ actualText
									+ "' is matching with the expected text = '"
									+ sInputData);
							/*customlogger.log(LogStatus.PASS, "Successfully verified the value "
		                    + sInputData + " on textbox " + sFieldName
		                    + " on the screen " + sScreenName);*/
						} else {

							logger.info("Actual Text in the application = '"
									+ actualText
									+ "'  is not matching with the expected text = '"
									+ sInputData + "'");

							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyFailTestStep(
											sFieldName, WidgetType, sInputData,
											actualText, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"For the field '"+sFieldName+"' Actual Text in the application = '"
									+ actualText
									+ "'  is not matching with the expected text = '"
									+ sInputData);
							ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"Checkbox is not checked for the field '"
									+ sFieldName + "' on the screen '"
									+ sScreenName);
							/*customlogger.log(LogStatus.PASS, "Failed to verify the value "
		                    + sInputData + " on textbox " + sFieldName
		                    + " on the screen " + sScreenName);*/

							// WriteHTMLLog
							// .writeHtmlLogs(getFormatedHTMLVerifyFailError(
							// "", sFieldName, sInputData,
							// sScreenName, actual));
						}
					}

				} else if (WidgetType.equalsIgnoreCase("Alert")) {
					if (checkNull(sInputData)) {
						Alert alert = driver.switchTo().alert();
						Thread.sleep(sleepTime);
						String AlertMsg = alert.getText();
						Thread.sleep(sleepTime);
						if (AlertMsg.equals(sInputData)) {
							logger.info("Alert message Text in the application = '"
									+ AlertMsg
									+ "' is matching with the expected alert message = '"
									+ sInputData + "'");

							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyPASSTestStep(
											sFieldName, WidgetType, sInputData,
											actualText, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"The Alert message in the application = '"
									+ AlertMsg
									+ "' is matching with the expected Alert message = '"
									+ sInputData);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"The Alert message in the application = '"
									+ AlertMsg
									+ "' is matching with the expected Alert message = '"
									+ sInputData);
							/*customlogger.log(LogStatus.PASS, "Successfully verified the value "
		                    + sInputData + " on textbox " + sFieldName
		                    + " on the screen " + sScreenName);*/
						} else {

							logger.info("Alert message in the application = '"
									+ AlertMsg
									+ "'  is not matching with the expected Alert message = '"
									+ sInputData + "'");

							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyFailTestStep(
											sFieldName, WidgetType, sInputData,
											actualText, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"The Alert message in the application = '"
									+ AlertMsg
									+ "'  is not matching with the expected Alert message = '"
									+ sInputData);
							ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"The Alert message in the application = '"
									+ AlertMsg
									+ "'  is not matching with the expected Alert message = '"
									+ sInputData);

							/*customlogger.log(LogStatus.PASS, "Failed to verify the value "
		                    + sInputData + " on textbox " + sFieldName
		                    + " on the screen " + sScreenName);*/
						}
						
					}

				} else if (WidgetType.equalsIgnoreCase("TextboxAttribute")) {
					if (checkNull(sInputData)) {
						sInputData = sInputData.replace("*","");
						String actualText = element.getAttribute("value");
						String actual = actualText;
				
						if (actualText.equals(sInputData)) {
							logger.info("Actual Text in the application = '"
									+ actualText
									+ "' is matching with the expected text = '"
									+ sInputData + "'");

							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyPASSTestStep(
											sFieldName, WidgetType, sInputData,
											actualText, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"For the field '"+sFieldName+"' Actual Text in the application = '"
									+ actualText
									+ "' is matching with the expected text = '"
									+ sInputData);
							ExtentReport.ExtentLogger.log(LogStatus.PASS,"For the field '"+sFieldName+"' Actual Text in the application = '"
									+ actualText
									+ "' is matching with the expected text = '"
									+ sInputData);
							/*customlogger.log(LogStatus.PASS, "Successfully verified the value "
		                    + sInputData + " on textbox " + sFieldName
		                    + " on the screen " + sScreenName);*/
						} else {

							logger.info("Actual Text in the application = '"
									+ actualText
									+ "'  is not matching with the expected text = '"
									+ sInputData + "'");

							/*WriteHTMLLog
									.writeHtmlLogs(getFormatedHTMLVerifyFailTestStep(
											sFieldName, WidgetType, sInputData,
											actualText, sScreenName));*/
							WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"For the field '"+sFieldName+"' Actual Text in the application = '"
									+ actualText
									+ "'  is not matching with the expected text = '"
									+ sInputData);
							ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture())+"For the field '"+sFieldName+"' Actual Text in the application = '"
									+ actualText
									+ "'  is not matching with the expected text = '"
									+ sInputData);

							/*customlogger.log(LogStatus.PASS, "Failed to verify the value "
		                    + sInputData + " on textbox " + sFieldName
		                    + " on the screen " + sScreenName);*/
						}
					} /*else if (sInputData.contains("Invisible")) {
						

				
						if (checkNull(sInputData)) {

							//System.out.println("hai");
							String  invisiblepath1=sScreenWidget.replace("xpath=","");
								
					       
							WebDriverWait driverWait2 = new WebDriverWait(driver, 1);
							driverWait2.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(invisiblepath1) ));
							
							System.out.println("Success");

						//wait.until(ExpectedConditions.invisibilityOfElementLocated(element)));
						Reporter.log("Successfully verified the status (Enable/Disable) of the field '"+ sFieldName+ "' on screen "+ sScreenName,true);
						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYPASS,"Successfully verified the status (Enable/Disable) of the field '"+ sFieldName+ "' on screen " + sScreenName);
						
						logger.error("Failed to verif the status (Enable/Disable) for the field "+ sFieldName+ "' on screen "+ sScreenName);
						WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYFAIL,"Failed to verify the status (Enable/Disable) of the field "+ sFieldName+ "' on screen "+ sScreenName);
						ExtentReport.ExtentLogger.log(LogStatus.FAIL,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture()) +"Failed to verify the status (Enable/Disable) of the field "+ sFieldName+ "' on screen "+ sScreenName);
						}
					
			} 
*/
				}

			} catch (StaleElementReferenceException sere) {
				sere.printStackTrace();

				// WriteHTMLLog.writeHtmlLogs(getFormatedHTMLFailTestStep(
				// sFieldName, WidgetType, sInputData, sScreenName));

				/*WriteHTMLLog.writeHtmlLogs(getFormatedHTMLVerifyFailError(
						"Error while verifying ", WidgetType, sFieldName,
						sScreenName));*/
				WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYERROR,"Error while verifying"+WidgetType+sFieldName+sScreenName);
				ExtentReport.ExtentLogger.log(LogStatus.ERROR,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture()) +"Error while verifying"+WidgetType+sFieldName+sScreenName);

				/*customlogger.log(LogStatus.ERROR, "Error while creating webdriver element for the OR property : "
                        + sScreenWidget + "  " + e");*/

				// try {
				// WriteHTMLLog.takeAScreenShotOfTheApp();
				// } catch (Exception e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// Assert.fail(e1.toString());
				// }

				//Commented Screenshot code on 25-Feb-2016
				//captureScreenShot();
				//WriteHTMLLog
				//.writeHtmlWebDeriverScreenShotDeatils(captureScreenShot());
				// WriteHTMLLog.writeHtmlScreenShoftDeatils();
				// WriteHTMLLog.writeHtmlWebDeriverScreenShotDeatils(WriteHTMLLog.captureScreenShot(driver,
				// config.get("LogFolder.screenShot"),
				// config.get("TestData.SheetName")));
				System.out
				.println("Session is getting closed at the place of verification");
				// killinstance();
				Assert.fail(sere.toString());
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("Error while verifying the element presense '"
						+ sInputData + "' for the field '" + sFieldName
						+ "' on the '" + sScreenName + "' screen");
				logger.error(e.toString());

				Reporter.log("Error while verifying the element presense '"
						+ sInputData + "' for the field '" + sFieldName
						+ "' on the '" + sScreenName + "' screen");
				Reporter.log(e.toString());

				String actual = "No Data";
				// WriteHTMLLog
				// .writeHtmlLogs(getFormatedHTMLVerifyFailTestStep(
				// sFieldName, WidgetType, sInputData, actual,
				// sScreenName));

				/*WriteHTMLLog.writeHtmlLogs(getFormatedHTMLVerifyFailError(
						"Error while performaing the verification ",
						WidgetType, sFieldName, sScreenName));*/

				WriteHTMLLog.writeHtmlLogs(WriteHTMLLog.VERIFYERROR,"Error while verifying the element presense '"
						+ sInputData + "' for the field '" + sFieldName
						+ "' on the '" + sScreenName + "' screen");
				ExtentReport.ExtentLogger.log(LogStatus.ERROR,ExtentReport.ExtentLogger.addScreenCapture(ExtentReport.addScreenCapture()) +"Error while verifying the element presense '"
						+ sInputData + "' for the field '" + sFieldName
						+ "' on the '" + sScreenName + "' screen");
				// try {
				// WriteHTMLLog.takeAScreenShotOfTheApp();
				// } catch (Exception e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }
				// WriteHTMLLog.writeHtmlScreenShoftDeatils();

				//WriteHTMLLog
				//.writeHtmlWebDeriverScreenShotDeatils(captureScreenShot());
				// try {
				// throw new Exception("Widget not found");
				// } catch (Exception e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// // System.out.println("Failed*************");
				// logger.info(" Testcase = "
				// + config.get("TestData.Sheet.Coloumn.Name.TestCaseNo")
				// + " -- > FAILED");
				// throw new terminateMethod();
				// }
				// throw new terminateMethod();

				System.out
				.println("Closing after verification inside verifydriver");
				// killinstance();
				Assert.fail("Error while verifying the element : ", e);
			}
		}
	}

	/**
	 * Method to wait for the title
	 * 
	 * @param title
	 * @throws InterruptedException
	 */
	public static void waitForWindowTitle(String title)
			throws InterruptedException {
		int count = 0;
		while (!title.equals(driver.getTitle())) {
			System.out.println("Count : " + count + "  " + title);
			count++;
			Thread.sleep(sleepTime);
			if (count > 60)
				break;
		}
	}

	public void waitForPageLoad() {
		WebDriverWait driverWait = new WebDriverWait(driver, 1);
		WebDriverWait driverWait1 = new WebDriverWait(driver, 10, 1000);
	}

	public static String getWindowTitle() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		System.out.println(js.executeScript("return document.title"));
		String title = (String) js.executeScript("return document.title");
		return title;
	}

	public static void handelAlertWindow(String actionToDo) throws Exception {
		Alert alert = driver.switchTo().alert();
		alert.getText();
		Thread.sleep(sleepTime);
		if (actionToDo.equalsIgnoreCase("Accept")
				|| actionToDo.equalsIgnoreCase("Yes")
				|| actionToDo.equalsIgnoreCase("Ok")) {
			alert.accept();
			Thread.sleep(sleepTime);
		} else {
			alert.dismiss();
			Thread.sleep(sleepTime);

		}
	}

	/**
	 * close the driver instance
	 */
	public static void killinstance() {
		System.out
		.println("**************************  Selenium Instance is getting closed  *****************************");
		try {
			driver.close();
			driver.quit();
			driver = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Webdriver intance was not closed"
					+ e.getMessage() + "Cause : " + e.getCause());
			Reporter.log("Webdriver intance was not closed" + e.toString());
			Assert.fail(e.toString());
		}
	}

	/**
	 * // To start the Html log file
	 */

	public static void startHtmlLogold(String testCaseName,
			String TestCaseDescription) {
		try {
			scriptStartExeTime = System.currentTimeMillis();
		} catch (Exception ioe) {
			ioe.printStackTrace();
			logger.error(ioe);
			Assert.fail(ioe.toString());
			Reporter.log("Failed whilte starting the Framewrok Customized html log : Method Name is : startHtmlLog");
		}
		WriteHTMLLog
		.writeHtmlLogs("<html><body>"
				+ "<center><b><H3>"
				+ testCaseName
				// +
				// " Log Details</center></b></H3><table border = '1' style = 'width:100%'><th>"
				+ " Log Details</center></b></H3><table border = '1'><th>"
				+ Formatter.getSystemDateTime()
				+ "</th></table><table border = '1' style = 'width:15%'><tr><th align=left>Test Script No</th>"
				+ "</td><td><b>"
				+ testCaseName
				+ "</b>"
				+ "<tr><th align=left>Test Script Description</th>"
				+ "</td><td><b>"
				+ TestCaseDescription
				+ "</b>"
				+ "</th></table><table border = '1'><tr><th>FieldName</th>"
				+ "<th>WidgetType</th><th>Data</th><th>ScreenName</th><th>Status</th></tr>");
	}

	public static void startHtmlLog(String testCaseName,
			String TestCaseDescription) {
		try {
			scriptStartExeTime = System.currentTimeMillis();
		} catch (Exception ioe) {
			ioe.printStackTrace();
			logger.error(ioe);
			Assert.fail(ioe.toString());
			Reporter.log("Failed whilte starting the Framewrok Customized html log : Method Name is : startHtmlLog");
		}
		WriteHTMLLog
		.writeHtmlLogs("<html><body>"
				+ "<center><b><H3>"
				+ testCaseName
				+ " Log Details</center></b></H3>"
				+ "<table border = '1' style = 'width:100%'>"
				+ "<tr><th align=left width='11%'>Test Script No</th>"
				+ "</td><td><b>"
				+ testCaseName
				+ "</b>"
				+ "<tr><th align = left width='11%'>Test Script Description</th>"
				+ "</td><td><b>"
				+ TestCaseDescription
				+ "<tr><th align=left width='11%'>Execution date & Time</th>"
				+ "</td><td><b>"
				+ Formatter.getSystemDateTime()
				+ "</b>"
				+ "</th></table><table border = '1' style = 'width:100%'><tr><th align=left width='85%'>Message Details</th>"
				+ "<th align=left width='15%'>Status</th></tr>");
	}

	/**
	 * write test case discription
	 */

	public static void logTestCaseDiscription(String testCaseDiscription) {

		WriteHTMLLog
		.writeHtmlLogs("<table border = '1' style = 'width:100%'><th>"
				+ Formatter.getSystemDateTime() + "</th></table>");
	}

	/**
	 * // To stop the Html log file
	 */

	public static void endHtmllog() {
		scriptEndExeTime = System.currentTimeMillis();
		scriptExeDiffTime = scriptEndExeTime - scriptStartExeTime;
		String scriptDuration = TimeUnit.MILLISECONDS
				.toSeconds(scriptExeDiffTime) + " seconds";
		WriteHTMLLog
		.writeHtmlLogs("</th></table><i><small>Time Taken for Execution: </i></small>"
				+ "<i><small>"
				+ scriptDuration
				+ "</i></small><br><br>" + "</table></body></html>");

	}

	public static int getDBTableCount(String tableName) throws Exception {
		ResultSet resultSet;
		int j = 0;
		String sqlQuery = "Select COUNT(*) AS COUNT from [" + tableName + "$]";
		connection = new JDBCExcelConnection();
		connection.openExcelConnection("");
		resultSet = connection.getResultSet(sqlQuery);
		resultSet.next();
		String count = resultSet.getString("COUNT");
		// System.out.println(count +"   is the count at super script");
		j = Integer.parseInt(count);
		// j=j-1;
		return j;
	}

	// Code for HTML Log
	public static String getFormatedHTMLPASSTestStep(String sFieldName,
			String WidgetType, String sInputData, String sScreenName) {
		/*
		 * return
		 * "<tr><td><font face='elephant' color='green'><b>PASS</b></td><td>" +
		 * "Successfully performed action for the field " + sFieldName +
		 * " of type " + WidgetType + " data " + sInputData + " on screen " +
		 * sScreenName + "</td></tr>";
		 */

		return "<tr><td>Successfully performed action for the field "
		+ sFieldName
		+ " of type "
		+ WidgetType
		+ " data "
		+ sInputData
		+ " on screen "
		+ sScreenName
		+ "</td><td><font face='elephant' color='green'><b>PASS</b></td></tr>";

	}

	public static String getFormatedDBStep(Object queryResult,String sInputData) {
		return "<tr><td>"
				+ "Successfully executed the SQL Query: "
				+ sInputData
				+ "<br><br>SQL Query Result:<br>" + queryResult + "</td><td><font face='elephant' color='Purple'><b>SQL Query</b></td></tr>";
	}


	public static String getFormatedHTMLVerifyPASSTestStep(String sFieldName,
			String WidgetType, String sInputData, String actual,
			String sScreenName) {
		/*
		 * return
		 * "<tr><td><font face='elephant' color='green'><b>Verification Pass</b></td><td>"
		 * + "Successfully verified the field '" + sFieldName + "' of Type-" +
		 * WidgetType + " on screen " + sScreenName + "<br>Expected Result :  "
		 * + sInputData + "<br>Actual Result : " + actual + "</td></tr>";
		 */

		return "<tr><td>"
		+ "Successfully verified the field '"
		+ sFieldName
		+ "' of Type-"
		+ WidgetType
		+ " on screen "
		+ sScreenName
		+ "<br>Expected Result :  "
		+ sInputData
		+ "<br>Actual Result : "
		+ actual
		+ "</td><td><font face='elephant' color='green'><b>Verification Pass</b></td></tr>";

	}

	public static String getFormatedHTMLFailTestStep(String sFieldName,
			String WidgetType, String sInputData, String sScreenName) {
		/*
		 * return
		 * "<tr><td><font face='elephant' color='red'><b>FAIL</b></td><td><font face='elephant' color='red'><b>"
		 * + "Failed to perform action for the field " + sFieldName +
		 * " of type " + WidgetType + " data " + sInputData + " on screen " +
		 * sScreenName + "</td></tr>";
		 */

		return "<tr><td><font face='elephant' color='red'><b>"
		+ "Failed to perform action for the field "
		+ sFieldName
		+ " of type "
		+ WidgetType
		+ " data "
		+ sInputData
		+ " on screen "
		+ sScreenName
		+ "</td><td><font face='elephant' color='red'><b>FAIL</b></td></tr>";
	}

	public static String getFormatedHTMLVerifyFailTestStep(String sFieldName,
			String WidgetType, String sInputData, String actual,
			String sScreenName) {

		/*
		 * return
		 * "<tr><td><font face='elephant' color='red'><b>Verification Failed</b></td><td><font face='elephant' color='red'><b>"
		 * + "Verification failed for the field '" + sFieldName + "' of Type-" +
		 * WidgetType + " on screen " + sScreenName + "<br>Expected Result :  "
		 * + sInputData + "<br>Actual Result : " + actual + "</td></tr>";
		 */

		return "<tr><td><font face='elephant' color='red'><b>"
		+ "Verification failed for the field '"
		+ sFieldName
		+ "' of Type-"
		+ WidgetType
		+ " on screen "
		+ sScreenName
		+ "<br>Expected Result :  "
		+ sInputData
		+ "<br>Actual Result : "
		+ actual
		+ "</td><td><font face='elephant' color='red'><b>Verification Failed</b></td></tr>";

	}

	public static String getFormatedHTMLFailError(String message,
			String sScreenWidget, String sFieldName, String sScreenName) {
		/*
		 * return
		 * "<tr><td><font face='elephant' color='red'><b>Error</b></td><td><font face='elephant' color='red'><b>"
		 * + message + sScreenWidget + "' for the field '" + sFieldName +
		 * "' on the '" + sScreenName + "' screen </i></b></th></tr>" +
		 * "<tr><th colspan = 5><a href = '" +
		 * config.get("LogFolder.screenShot") + File.separator +
		 * config.get("TestData.Sheet.Coloumn.Name.TestCaseNo") + ".png" +
		 * "'> Error Screen Shot</a></th></tr>";
		 */

		return "<tr><td><font face='elephant' color='red'><b>"
		+ message
		+ sScreenWidget
		+ "' for the field '"
		+ sFieldName
		+ "' on the '"
		+ sScreenName
		+ "' screen </i></b></td><td><font face='elephant' color='red'><b>Error</b></td></th></tr>"
		+ "<tr><th colspan = 5><a href = '"
		+ config.get("LogFolder.screenShot") + "\\" + Formatter.getDate() + "\\"
		+ config.get("TestData.Sheet.Coloumn.Name.TestCaseNo") +"-"+ Formatter.getTime_HH_MM() + ".png"
		+ "'> Error Screen Shot</a></th></tr>";

	}

	// sScreenWidget, sFieldName, sScreenName));
	public static String getFormatedHTMLVerifyFailError(String message,
			String sScreenWidget, String sFieldName, String sScreenName) {
		/*return "<tr><td><font face='elephant' color='red'><b>Verification Error</b></td><td><font face='elephant' color='red'><b>"
				+ message
				+ sScreenWidget
				+ "' for the field '"
				+ sFieldName
				+ "' on the '" + sScreenName + "' screen </i></b></th></tr>";*/

		return "<tr><td><font face='elephant' color='red'><b>"
		+ message
		+ sScreenWidget
		+ "' for the field '"
		+ sFieldName
		+ "' on the '" + sScreenName + "' screen </i></b><td><font face='elephant' color='red'><b>Verification Error</b></td></th></tr>";

	}


	public static String captureScreenShot() {
		String screenShotName = "";
		String filePath = config.get("LogFolder.screenShot");
		String fileName = config.get("TestData.Sheet.Coloumn.Name.TestCaseNo");
		// Take screenshot and store as a file format
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			// now copy the screenshot to desired location using copyFile
			// method
			screenShotName = filePath + "\\" + Formatter.getDate() + "\\"
					+ fileName +"-"+ Formatter.getTime_HH_MM() + ".png";
			FileUtils.copyFile(src, new File(screenShotName));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Screen shot is stored at  : " + screenShotName);
		return screenShotName;
	}

	public static int DateSearchResult(String strDate1, String strDate2, String check)
	{
		int returnValue=0;
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			Date date1 = sdf.parse(strDate1);
			Date date2 = sdf.parse(strDate2);
			if(check=="StartDate"){
				if(date1.compareTo(date2)>0){
					returnValue=-1;    
				}
			}
			else
				if(check=="EndDate"){
					if(date1.compareTo(date2)<0){
						returnValue=-1;    
					}	
				}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return returnValue;
	}


}

