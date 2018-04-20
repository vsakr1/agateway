package sat.test.automation.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class ReadConfigFile {

	public String get(String value) {

		Properties prop = new Properties();
		String propFileName = "config.properties";

		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream(propFileName);
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (inputStream == null) {
			try {
				throw new FileNotFoundException("config.properties file '"
						+ propFileName + "' not found in the classpath");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String configValue = prop.getProperty(value);

		return configValue;
	}



	public HashMap<String, String> readConfigDataToHashMap(String filename,
			String delimiter)  {
		HashMap<String, String> configMap = null ;
		try {
			
			configMap= new HashMap();
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() == 0)
					continue;
				if (line.charAt(0) == '#')
					continue;
				int delimPosition = line.indexOf(delimiter);
				String key = line.substring(0, delimPosition - 1).trim();
				String value = line.substring(delimPosition + 1).trim();
				configMap.put(key, value);
			}
			reader.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return configMap;
	}

}