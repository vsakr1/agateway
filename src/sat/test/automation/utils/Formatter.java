/**
 * 
 */
package sat.test.automation.utils;

/**
 * @author 206433293
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import sat.test.automation.core.SuperScript;

// Class which will Format the Age to Date Of Birth and Give the First Name and
// Last Name of the Applicants
public class Formatter extends SuperScript{
	public static final String DATE_FORMAT_NOW = "MM-dd-yyyy--HH.mm.ss";
	public static final String TIME_FORMAT_12 = "h.mm.ss a";
	public static final String TIME_FORMAT_24 = "HH.mm.ss";
	public static final String TIME_FORMAT_HH_MM = "HH.mm a";
	// Variable declaration and Initialization
	public static String DATE_FORMAT = "MM-dd-yyyy";

	public static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

//	public String dateValue[] = RSASuperScript.EFFECTIVE_DATE.split("/");
	public String dateValue[] = null;

	// The method to produce the Date of Birth of the Applicants depending on
	// the age mentioned in the Database.
	@SuppressWarnings("deprecation")
	public String getDOB(String age) {
		int currentAge = Integer.parseInt(age);

		Calendar c1 = Calendar.getInstance();
		if (currentAge == 0) {
			c1.set(2011, (Calendar.MONTH) + 1, Calendar.getInstance().getTime()
					.getDate());
		} else {
			c1.set(2011, Integer.parseInt(dateValue[0]) - 2, 1);
		}
		c1.add(Calendar.YEAR, -currentAge);
		Date date = c1.getTime();
		String dateOfBirth = sdf.format(date);
		return dateOfBirth;
	}

	// The method that produce contract date for the Application
	public String contractDate() {
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		cal1.set(cal.get(Calendar.YEAR), Integer.parseInt(dateValue[0]), 1);
		return sdf.format(cal1.getTime());
	}

	// The method that produce the First Name of the Primary Applicant depending
	// on the
	// Name mentioned in the Database
	public String getFirstName(String sName) {
		String str = sName;
		String firstName = str.substring(0, 6);
		return firstName;
	}

	// The method that produce the Last Name of the Primary Applicant depending
	// on the
	// Name mentioned in the Database
	public String getLastName(String sName) {
		String str = sName;
		String lastName = str.substring(7);
		return lastName;
	}

	// This method will produce the current system date
	public static String getDate() {
		Date currentDateTime = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern("MM-dd-yyyy");
		String date = simpleDateFormat.format(currentDateTime);
		return date;

	}

	public static String getTimeStamp() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}
	
	public static String getTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_24);
		return sdf.format(cal.getTime());
	}
	
	public static String getTime_HH_MM() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_HH_MM);
		return sdf.format(cal.getTime());
	}
	public static String getTime_12hh() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_12);
		return sdf.format(cal.getTime());
	}
	
	// This method will produce the current system date and time in
	// customized format
	public static String getSystemDateTime() {
		//DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh-mm a z");//With TimeZone
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh-mm a"); //Without TimeZone
		Date date = new Date();
		return dateFormat.format(date);
	}

	// The method that produce the First Name of the Secondary Applicant
	// depending on the
	// Name mentioned in the Database
	public String getFirstName_AI(String sName) {
		String str[] = sName.split(" ");

		return str[0];
	}

	// The method that produce the Last Name of the Secondary Applicant
	// depending on the
	// Name mentioned in the Database
	public String getLastName_AI(String sName) {
		String str[] = sName.split(" ");

		return str[1];
	}

}
