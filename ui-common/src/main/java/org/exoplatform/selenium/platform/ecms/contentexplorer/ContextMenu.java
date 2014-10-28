package org.exoplatform.selenium.platform.ecms.contentexplorer;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * 
 * @author vuna2
 *
 */
public class ContextMenu extends EcmsBase{
	public ContextMenu(WebDriver dr, String...plfVersion) {
		super(dr);
		this.plfVersion = plfVersion.length>0?plfVersion[0]:"4.1";
		// TODO Auto-generated constructor stub
	}
	Dialog dialog = new Dialog(driver);
	Button button = new Button(driver);

	/*
	 * @Added by PhuongDT
	 * Date 04/09/2013 
	*/
	public final By ELEMENT_MENU_ADD_TO_FAVORITE = By.xpath("//*[@id='JCRContextMenu']//i[@class='uiIconEcmsAddToFavourite']");
	/*End Added*/
	
	public final By ELEMENT_CONTEXT_MENU_LOCK = By.xpath("//*[@class='uiContextMenuContainer']//i[@class='uiIconEcmsLock']"); 
	public final By ELEMENT_CONTEXT_MENU_COPY = By.xpath("//*[@class='uiContextMenuContainer']//i[@class='uiIconEcmsCopy']");
	public final By ELEMENT_CONTEXT_MENU_VIEW = By.xpath("//*[@class='uiContextMenuContainer']//i[@class='uiIconEcmsViewInfo']");
			
			//By.className("uiIconEcmsLock");
	public final By ELEMENT_MENU_UNLOCK = By.className("uiIconEcmsUnlock");
	public final By ELEMENT_MENU_CHECKIN = By.className("uiIconEcmsCheckIn");
	public final By ELEMENT_MENU_CHECKOUT = By.className("uiIconEcmsCheckOut");
	public final By ELEMENT_MENU_RENAME_NODE = By.linkText("Rename");
	public final By ELEMENT_MENU_PASTE = By.xpath("//a[contains(text(),'Paste')]");
	public final By ELEMENT_MENU_DELETE = By.className("uiIconEcmsDelete");
			//By.className("uiIconEcmsDelete");
	public final By ELEMENT_MENU_DELETE_RIGHT_CLICK_POPUP = By.xpath("//*[@id='JCRContextMenu']/div/ul/li[7]/a");
	public final By ELEMENT_MENU_EDIT = By.className("uiIconEcmsEditDocument");
	public final By ELEMENT_MENU_ADD_SYMLINK = By.className("uiIconEcmsAddSymLink");
	public final By ELEMENT_VIEW_INFORMATION = By.className("uiIconEcmsViewInfo");
	public final String ELEMENT_POPUP_VIEW_INFORMATION_NAME = "//*[@class='UIViewInfoManager']//td[text()='Name']/../td[text()='${fileName}']";
	public final String ELEMENT_POPUP_VIEW_INFORMATION_TYPE = "//*[@class='UIViewInfoManager']//td[text()='Type']/../td[text()='${folderName}']";
	public final By ELEMENT_MENU_DOWNLOAD = By.xpath("//*[@class='uiContextMenuContainer']//*[@class='uiIconDownload uiIconLightGray']");

	public final String ELEMENT_FILE_LOCKED_BY_ADMIN = "//*[@data-original-title = '${titleOfFile} (Locked by john)']";
	public final String ELEMENT_FILE_TITLE = "//*[@title = '${titleOfFile}']";
	public final String ELEMENT_FILE_TITLE_AUX = "//*[@title='${title1}']/following::*[@title='${title2}']";
	public final By ELEMENT_DOCUMENT = By.linkText("documents");
	public final By ELEMENT_UNLOCK_ARTICLE = By.className("uiIconUnLockMini");

	public final String WARNING_MESSAGE_CANNOT_PASTE = "You cannot paste the copied node type on the current node.";
	public final String ELEMENT_VERIFY = "//*[text()='${destination}']/../../../../*//*[text()='${source}']";
	/*=========================================================*/

	/**
	 * Define a type of action (on NODE) 
	 * @author vuna2
	 *
	 */
	public enum actionType{
		COPY, CUT, DELETE, EDIT, PASTE, LOCK, UNLOCK, CHECKIN, CHECKOUT, 
		RENAME, SYMLINK, VIEW_INFORMATION, DOWNLOAD;
	}

	/**
	 * 
	 * @param locator: of NODE
	 * @param action: COPY, CUT, PASTE, LOCK, UNLOCK, CHECK IN, CHECK OUT, RENAME NODE
	 * @author vuna2
	 */
	public void contextMenuAction(Object locator, By actionItem, Object... params){
		By loc = locator instanceof By ? (By)locator : By.xpath((String)locator);
		String nodeName = (String) (params.length > 0 ? params[0] : "");
		Utils.pause(1000);
		//for(int repeat=0;; repeat ++)
		//{
		//	if (repeat >= ACTION_REPEAT) {
		//		Assert.fail("Cannot perform this action after " + ACTION_REPEAT + " tries");
		//	}
		if(isElementPresent(By.xpath(ELEMENT_VIEW_MODE_LINK.replace("${viewName}", "Web")))){
			click(By.xpath(ELEMENT_VIEW_MODE_LINK.replace("${viewName}", "Web")));
			Utils.pause(1000);
			if(isElementPresent(By.xpath("//*[@data-original-title = 'File Explorer']")))
				click(By.xpath("//*[@data-original-title = 'File Explorer']"));
		}
		rightClickOnElement(loc);
		if (waitForAndGetElement(actionItem, 5000, 0) != null) {
			if(this.plfVersion.equalsIgnoreCase("4.0"))
				click(actionItem);
			else// if(this.plfVersion.equalsIgnoreCase("4.1"))
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", waitForAndGetElement(actionItem));
		}
		if (!nodeName.isEmpty()){
			//if (waitForAndGetElement(ELEMENT_MENU_RENAME_NODE, 5000, 1) != null){
			//	click(ELEMENT_MENU_RENAME_NODE);
				type(ELEMENT_INPUT_RENAME_NODE, nodeName, true);
				button.rename();
				waitForTextPresent(nodeName);
				info("Node is renamed successfully");
			//}
		}
		//Utils.pause(WAIT_INTERVAL);
		//	info("Retry...[" + repeat + "]");
		//}
		Utils.pause(1000);
	}

	//Check node is being locked
	public boolean isLockedNode(Object locator){
		boolean locked=false;
		By by = locator instanceof By ? (By)locator : By.xpath((String)locator);
		//		actions.contextClick(unlock).build().perform();
		try{
			if(isElementPresent(By.xpath(ELEMENT_VIEW_MODE_LINK.replace("${viewName}", "Web")))){
				click(By.xpath(ELEMENT_VIEW_MODE_LINK.replace("${viewName}", "Web")));
				Utils.pause(1000);
				click(By.xpath("//*[@data-original-title = 'File Explorer']"));
			}
			rightClickOnElement(by);
			Utils.pause(500);
			if (isElementPresent(ELEMENT_MENU_UNLOCK)) {
				locked = true;
			} else 
				locked =false;

			WebElement unlock = waitForAndGetElement(by);
			unlock.sendKeys(Keys.RETURN);
		} catch(StaleElementReferenceException e){
			checkCycling(e, 10);
			isLockedNode(locator);
		} finally{			
			loopCount=0;			
		}
		return locked;
	}

	/*//Rename a node
	public void renameNode(By nodePath, String newName, By nodePathNew){
		rightClickOnElement(nodePath);
		click(By.xpath("//a[contains(text(),'Rename')]"));
		type(By.id("titleField"),newName,true);
		type(By.id("nameField"),newName,true);
		click(ELEMENT_SAVE_BUTTON);
		//check rename successfully
		waitForElementPresent(nodePathNew);
		assert isElementPresent(nodePathNew):"Rename node unsuccessfully";
		info("Rename node successfully");
	}*/

	//Copy and paste a node
	public void copyAndPasteNode(Object source, Object target){
		//goToNode(source);
		copyNode(source);
		//goToNode(target);
		pasteNode(target);
		Utils.pause(1000);
	}

	//Cut and paste a node
	public void cutAndPasteNode(Object source, Object target){
		cutNode(source);
		pasteNode(target);
	}

	//Delete a node at level 1
	public void deleteDocument(Object locator, int...timeout){
		int iTimeout = timeout.length > 0 ? timeout[0] : DEFAULT_TIMEOUT;
		for(int repeat=1;; repeat ++)
		{
			if (repeat > ACTION_REPEAT) {
				Assert.fail("Cannot perform this action after " + ACTION_REPEAT + " tries");
			}
			if(isElementPresent(By.xpath(ELEMENT_VIEW_MODE_LINK.replace("${viewName}", "Web")))){
				click(By.xpath(ELEMENT_VIEW_MODE_LINK.replace("${viewName}", "Web")));
				Utils.pause(1000);
				if(isElementPresent(By.xpath("//*[@data-original-title = 'File Explorer']")))
					click(By.xpath("//*[@data-original-title = 'File Explorer']"));
			}
			rightClickOnElement(locator);
			if (waitForAndGetElement(ELEMENT_MENU_DELETE, 10000, 0)!=null) 
			{	
				click(ELEMENT_MENU_DELETE);
				//waitForTextPresent("Delete");
				//click(By.linkText("OK"));
				dialog.deleteInDialog();
				break;
			}
			info("Retry...[" + repeat + "]");

		}
		Utils.pause(1000);
		//waitForElementNotPresent(By.linkText("OK"));
		//waitForTextNotPresent("Delete");
		if (isElementPresent(locator)){
			driver.navigate().refresh();
			Utils.pause(2000);
		}
		waitForElementNotPresent(locator, iTimeout);
		info(locator.toString() + " is deleted successfully");		
		Utils.pause(1000);
	}

	//Delete data
	public void deleteData(By data){
		goToNode(data);
		deleteDocument(data);
		/*Utils.pause(1000);
		waitForElementNotPresent(data);*/
	}
}
