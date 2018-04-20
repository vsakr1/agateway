package sat.test.automation.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class UtilityHelpers {
	public void copyFile(String sourceFilePath, String destinationFilePath) {
		File source = new File(sourceFilePath);
		File desc = new File(destinationFilePath);
		try {
			FileUtils.copyFile(source, desc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
