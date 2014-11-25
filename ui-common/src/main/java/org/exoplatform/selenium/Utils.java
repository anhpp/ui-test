package org.exoplatform.selenium;

import static org.exoplatform.selenium.TestLogger.*;
import java.awt.*;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;


/**
 * Utils.java
 * @author vuna2
 *
 */
public class Utils {
	//public WebDriver driver;
	//
	public static void pause(long timeInMillis) {
		try {
			Thread.sleep(timeInMillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	

	/*public static void clearCache(){
		Actions actionObject = new Actions(driver);
		try{
			actionObject.sendKeys(Keys.CONTROL).sendKeys(Keys.F5).build().perform();
		} catch(WebDriverException e){	
			debug("Retrying clear cache...");
			actionObject.sendKeys(Keys.CONTROL).sendKeys(Keys.F5).build().perform();
		}
	}*/

	/**
	 * Capture the screen of the current graphics device
	 * @author vuna2
	 * @param fileName: input an image name (String)
	 * @throws InterruptedException
	 */
	public static void captureScreen(String fileName){
		String path;
		String fs = File.separator;
		BufferedImage screenCapture;
		//		Thread.sleep(3000);
		pause(3000);
		try {
			Robot robot = new Robot();
			Rectangle screenSize = getScreenSize();
			screenCapture = robot.createScreenCapture(screenSize);
			// Save as PNG
			String curDir = System.getProperty("user.dir");
			path = curDir + fs + "target" + fs + "screenshoot" + fs;
			File f = new File(path);
			if (!f.exists()) f.mkdir(); 
			ImageIO.write(screenCapture, "png", new File(path + fileName));

		}catch (AWTException e) {
			error("Failed to capture screenshot");
		}catch(IOException e)
		{
			path = "Failed to capture screenshot: " + e.getMessage();
		}
	}

	/**
	 * 
	 * @return the size of the default screen
	 */
	public static Rectangle getScreenSize() {
		GraphicsEnvironment graphE = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice graphD = graphE.getDefaultScreenDevice();
		Window displayM = graphD.getFullScreenWindow();
		if(displayM != null)
			return new Rectangle(displayM.getWidth(), displayM.getHeight());
		else
			return new Rectangle(1300,1000);
	}

	//
	/**
	 * Simulating keyboard presses 
	 * @author vuna2
	 * @param firstKey: send the first key (type: KeyEvent)
	 * @param secondKey: send the second key (type: KeyEvent) 
	 * @throws InterruptedException 
	 */
	public static void javaSimulateKeyPress(int firstKey, Object... params){
		int secondKey = (Integer) (params.length > 0 ? params[0]: KeyEvent.VK_ENTER);
		int thirdKey = (Integer) (params.length > 1 ? params[1]: KeyEvent.VK_ENTER); 
		try {
			Robot robot = new Robot();
			// Simulate a key press
			robot.keyPress(firstKey);
			if(params.length > 0)
				robot.keyPress(secondKey);
			if(params.length > 1)
				robot.keyPress(thirdKey);
			pause(3000);
			if(params.length > 0)
				robot.keyRelease(secondKey);
			robot.keyRelease(firstKey);
			if(params.length > 1)
				robot.keyRelease(thirdKey);

		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/*---- Auxiliary functions ----
	public static void captureScreen(String fileName){
		String path;
		try {
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			//			relativeFilePath = "target/screenshot/" + fileName;
			String curDir = System.getProperty("user.dir");
			path = curDir + "/target/screenshoot/" + fileName;
			FileUtils.copyFile(scrFile, new File(path));
		} catch (IOException e) {
			path = "Failed to capture screenshot: " + e.getMessage();
		}catch(UnknownServerException e)
		{
			error("Failed to capture screenshot");
		}
	}
	 */

	//This function returns a absolute path from a relative path
	public static String getAbsoluteFilePath(String relativeFilePath){
		String curDir = System.getProperty("user.dir");
		String fs = File.separator;
		String absolutePath = curDir + fs + "src" + fs + "main" + fs + "resources" + fs + relativeFilePath;
		return absolutePath;
	}

	//InputStream to String
	//Get a File Content
	public static String getFileContent(String filePath){
		String path = getAbsoluteFilePath(filePath);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			error("Failed to find location of... " + filePath);
		}
		@SuppressWarnings("resource")
		String inputStreamString = new Scanner(fis,"UTF-8").useDelimiter("\\A").next();		
		return inputStreamString;	
	}

	//Get a file name from current Url
	public static String getFileNameFromCurrentUrl(WebDriver driver, Object...params){
		Boolean extension = (Boolean) (params.length > 0 ? params[0] : false);

		String currentUrl = driver.getCurrentUrl();
		File file = new File(currentUrl);
		String fileNameWithExt = file.getName();

		if (extension){
			int position = fileNameWithExt.lastIndexOf(".");
			String fileNameWithOutExt = null;
			if (position >= 0) {
				fileNameWithOutExt = fileNameWithExt.substring(0, position);
			}else{
				fileNameWithOutExt = fileNameWithExt;
			}
			return fileNameWithOutExt;
		}else {
			return fileNameWithExt;
		}
	}

	/**
	 * @author lientm
	 * @return ipV4 of local machine
	 */
	public static String getIPOfLocal(){
		info("Get IP of localhost");
		String interName = "";
		Map <String, String> inter = getInterfaces();
		for (String key: inter.keySet()){
			if (key.contains("eth")){
				interName = inter.get(key);
				break;
			}
		}
		info(interName);
		return interName;
	}

	/**
	 * @author lientm
	 * @return map of interface name and Ip of local machine
	 */
	public static Map<String, String> getInterfaces(){
		Map<String,String> inter = new HashMap <String,String>();
		String IP = "";
		try {
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();

			while(e.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) e.nextElement();
				info("Net interface: " + ni.getName()); 

				Enumeration<InetAddress> e2 = ni.getInetAddresses(); 
				while (e2.hasMoreElements()){
					InetAddress ip = (InetAddress) e2.nextElement();
					if(!ip.isLinkLocalAddress()) {
						IP = ip.getHostAddress();
					}
				}
				info("IP address: "+ IP.toString());
				inter.put(ni.getName(), IP.toString());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return inter;
	}
}