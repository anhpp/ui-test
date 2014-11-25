package org.exoplatform.selenium.platform.ecms.contentexplorer;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.admin.ManageDrive;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * 
 * @author vuna2
 *
 */
public class SitesExplorer extends EcmsBase{

	public SitesExplorer(WebDriver dr,String...plfVersion) {
		super(dr);
		this.plfVersion = plfVersion.length>0?plfVersion[0]:"4.0";
		// TODO Auto-generated constructor stub
	}

	Dialog dialog = new Dialog(driver);
	Button button = new Button(driver);
	NavigationToolbar navToolBar = new NavigationToolbar(driver);
	ActionBar actBar = new ActionBar(driver);
	ManageAlert magAlert = new ManageAlert(driver);
	ECMainFunction ecMain = new ECMainFunction(driver);
	ManageDrive magDrv = new ManageDrive(driver);
	EcmsBase ecms = new EcmsBase(driver);

	/*Text*/
	public final String ELEMENT_TEXT ="//*[text()='{$node}']";

	//Button on the top-bar menu
	//public final By ELEMENT_BUTTON_BACK_PREVIOUS_NODE = By.className("uiIconEcmsGoBack");
	public final By ELEMENT_BUTTON_REFRESH_TOPBAR_MENU = By.className("uiIconRefresh");

	//Simple Search
	public final By ELEMENT_SIMPLESEARCH_TEXTBOX = By.id("simpleSearch");
	public final By ELEMENT_SIMPLESEARCH_SUBMIT = By.id("SimpleSearch");
	public final By ELEMENT_QUICKSEARCH_ICON = By.xpath("//a[@data-original-title='Quick Search']");

	/* sidebar */
	public final String ELEMENT_SE_NODE = "//*[@title='{$node}']"; 
	public final By ELEMENT_SIDEBAR_SITES_MANAGEMENT = By.xpath("//*[@data-original-title = 'Sites Management' or @title = 'Sites Management']");
	public final By ELEMENT_SIDEBAR_COLLABORATION = By.xpath("//*[@data-original-title = 'Collaboration']");
	public final String ELEMENT_PATH_ADDRESS = "//*[@id='address' and @value='${path}']";
	public final String ELEMENT_NODE_TREE_EXPLORER_NAME=".//*[@id='UITreeExplorer']//a/span[contains(@class,'nodeName')][contains(text(),'${name_document}')]";
	//File Explorer - relation -clipboard - tag clould - saved search
	public final String ELEMENT_TAG_IN_CONTAINER_LIST = "//*[@class='tagContainer']/*[text()='${tagName}']"; 
	public final By ELEMENT_TAG_CLOUD = By.xpath("//*[@data-original-title = 'Tag Cloud']");
	public final By ELEMENT_SIDEBAR_FILE_EXPLORER = By.xpath("//*[@data-original-title = 'File Explorer']");
	public final String ELEMENT_CLIPBOARD_NODE= "//div[@data-original-title='collaboration:/sites/{$node}']";
	public final String ELEMENT_CLIPBOARD_PASTE_ICON= "//div[@data-original-title='collaboration:/sites/{$node}']/../..//a[@data-original-title='Paste']";
	public final String ELEMENT_CLIPBOARD_DELETE_ICON = "//div[@data-original-title='collaboration:/sites/{$node}']/../..//a[@data-original-title='Delete']";
	public final By ELEMENT_SITEBAR_RELATION = By.xpath("//*[@id='UISideBar']//*[@data-original-title='Relation']");
	//public final By ELEMENT_FILE_EXPLORER_MINI_ICON = By.className("uiIconEcmsExplorerMini");

	public final By ELEMENT_CLIPBOARD_ICON = By.xpath("//*[@data-original-title = 'Clipboard']");
	public final By ELEMENT_CLEAR_ALL_ICON = By.xpath("//*[contains(text(),'Clear All')]");
	public final By MESSAGE_CLEAR_ALL = By.xpath("//*[contains(text(),'There are no items in the clipboard.')]");
	public final String ELEMENT_VERIFY_ACTION = "//*[contains(@data-original-title, '${titleOfFile}')]/../..//*[contains(@class, 'uiIconEcmsPaste')]";
	public final String ELEMENT_TITLE_LEFT_PANEL = "//div[@id='UITreeExplorer']//div[contains(@onmousedown,'collaboration:/sites/${title}')]";

	public final String ELEMENT_DOCUMENT_TITLE = "//*[@class='nodeLabel']/*[text()='${title}']";

	//Check status of documents
	public final String ELEMENT_STATUS_DOCUMENT = "//*[@data-original-title='${title} ']/../..//*[@data-original-title='status']";

	//Other
	public final String ELEMENT_BUTTON_WATCH_UNWATCH = "//*[@class='actionIcon' and contains(text(), ' Watch/Unwatch')]";
	public final String ELEMENT_BUTTON_WATCH_EMAIL = "//*[text()='Email']";
	public final String ELEMENT_BUTTON_WATCH_WATCH = "//*[@class='btn' and text()='Watch']";
	
	/*================***==================*/

	//Verify if Driver is present
	public boolean isDriverPresent(String driverName){
		return getElement(By.xpath("//*[@class = 'driveLabel' and @title='" + driverName + "']")) != null;	
	}

	public boolean isDriverNotPresent(String driverName) {
		return !isDriverPresent(driverName);
	}

	//Create a driver in Sites Explorer
	public void createDriverInSitesExplorer(String driverName, String driverWorkspace, String driverHomePath, String groupPermission, 
			String member, String viewOption, String view){
		if (isTextNotPresent(driverName)){
			Utils.pause(1000);
			ecMain.goToManageDrive();
			magDrv.addNewDrive(driverName, driverWorkspace, driverHomePath, groupPermission, member, viewOption, view, false, true);	
			navToolBar.goToSiteExplorer();
			Utils.pause(1000);
			if (isTextNotPresent(driverName)){
				//click(ecms.ELEMENT_SHOW_DRIVES);
				actBar.showDrives();
			}
		}else{
			info("Driver " + driverName + " already exists");
		}	
		Utils.pause(1000);
	}

	//Delete a tag
	public void deleteTag(String[] tagName){
		for(int i = 0; i < tagName.length ; i++){
			info("-- Deleting the tag: " + tagName[i]);
			By ELEMENT_REMOVE_TAG = By.xpath(REMOVE_TAG.replace("${TagsName}", tagName[i]));
			if (isElementPresent(ELEMENT_REMOVE_TAG)){
				click(ELEMENT_REMOVE_TAG);
			}else if (isElementPresent(By.xpath("//*[text()='Edit Tag']/../..//*[text()='"+ tagName[i] +"']/../..//*[@data-original-title='Remove Tag']"))){
				click(By.xpath("//*[text()='Edit Tag']/../..//*[text()='"+ tagName[i] +"']/../..//*[@data-original-title='Remove Tag']"));
			}
			//magAlert.waitForConfirmation(MESSAGE_WARNING_AFTER_DELETE_TAG);
			magAlert.acceptAlert();
		}
		click(button.ELEMENT_CLOSE_WINDOW);
		waitForTextNotPresent(tagName[0]);
	}

	//Edit a public tag
	public void goToEditTag(){
		info("-- Go to Edit Tags Form --");
		if (isElementNotPresent(ELEMENT_SIDEBAR_FILE_EXPLORER)){
			navToolBar.goToSiteExplorer();     
		}
		waitForAndGetElement(ELEMENT_TAG_CLOUD);
		click(ELEMENT_TAG_CLOUD);
		if (isElementPresent(ELEMENT_EDIT_TAGS)){
			//click(ELEMENT_EDIT_TAGS);
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", ELEMENT_EDIT_TAGS);
		}else if (isElementPresent(ELEMENT_EDIT_TAGS_OTHER)){
			click(ELEMENT_EDIT_TAGS_OTHER);
			//((JavascriptExecutor)driver).executeScript("arguments[0].click();", By.xpath("//*[@data-original-title = 'Edit Tags']"));
		}
		click(By.xpath("//*[@id='UITagExplorer']//*[contains(@class, 'uiIconEdit')]"));
		waitForAndGetElement(ELEMENT_EDIT_TAGS_FORM);
	}

	//Add tag for a node
	public void addTagForNode(String[] tagName) {
		info("-- Open a Tag Form --");
		if (waitForAndGetElement(ELEMENT_TAG_LINK, 5000, 0) != null){
			click(ELEMENT_TAG_LINK);
		}else {
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			click(ELEMENT_TAG_LINK);
		}

		for(int i = 0; i < tagName.length ; i++){
			// Input information
			type(ELEMENT_TAG_NAME, tagName[i] + ",", false);

			//Verify new tag

		}
		click(button.ELEMENT_ADD_BUTTON);
		for(int i = 0; i < tagName.length ; i++){
			waitForAndGetElement(By.xpath("//*[text()='Linked Tags:']/..//*[contains(text(), '"+ tagName[i] +"')]"));
		}
		//Close
		click(button.ELEMENT_CLOSE_BUTTON);

		//Verify added new tag
		click(ELEMENT_TAG_CLOUD);
		//waitForTextPresent(tagName[0]);
		waitForAndGetElement(ELEMENT_TAG_IN_CONTAINER_LIST.replace("${tagName}", tagName[0]));
	}

	/** function edit a tag
	 * @author lientm
	 * @param oldTag
	 * @param newTag
	 */
	public void editTag(String oldTag, String newTag){
		click(By.xpath(ELEMENT_EDIT_A_TAG_ICON.replace("${tagName}", oldTag)));
		waitForAndGetElement(ELEMENT_EDIT_A_TAG_FORM);
		type(ELEMENT_EDIT_TAG_NAME, newTag, true);
		button.save();
		waitForAndGetElement(By.xpath(ELEMENT_EDIT_A_TAG_ICON.replace("${tagName}", newTag)));
	}

	//Simple search
	public boolean simpleSearch(String keyword){
		waitForAndGetElement(ELEMENT_SIMPLESEARCH_TEXTBOX);
		type(ELEMENT_SIMPLESEARCH_TEXTBOX, keyword, true);
		Utils.pause(1000);
		click(ELEMENT_SIMPLESEARCH_SUBMIT);
		if (waitForAndGetElement(By.xpath("//*[contains(text(),'"+ keyword +"')]"), 30000, 1) != null){
			return true;
		}
		return false;
	}
}
