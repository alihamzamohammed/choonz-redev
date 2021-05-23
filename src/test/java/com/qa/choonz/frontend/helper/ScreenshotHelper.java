package com.qa.choonz.frontend.helper;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.google.common.io.Files;

public class ScreenshotHelper {

	public static void screenShot(WebDriver driver, String path) throws Exception {

		// takes screenshot
		TakesScreenshot shot = ((TakesScreenshot) driver);

		// output as file
		File SrcFile = shot.getScreenshotAs(OutputType.FILE);

		// make a file class that points at the destination
		File destination = new File(path);

		Files.copy(SrcFile, destination);// store the SrcFile in the destination file
	}
}
