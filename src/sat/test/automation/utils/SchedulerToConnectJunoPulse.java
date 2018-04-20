package sat.test.automation.utils;

import java.util.Date;

public class SchedulerToConnectJunoPulse 
{
	public static void main(String[] args) throws Exception 
	{
		//Define the time at which script should be executed inorder to connect to Juno Pulse
		int hourValue=05;
		int minValue=30;
		
		
		boolean cond=true;
		while(cond)
		{
		     @SuppressWarnings("deprecation")
		     int hour = new Date().getHours();
		     @SuppressWarnings("deprecation")
		     int minute= new Date().getMinutes();
		     if(hour==hourValue && minute==minValue)
		     {
		         System.out.println("Executing AutoIT script to connect to Pulse");
		         Runtime.getRuntime().exec("C:\\testautomation-runtime\\RSA\\rsa.exe");
		         System.exit(0);		         
		     }
		 }
	}
}
