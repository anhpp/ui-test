package org.exoplatform.selenium.platform.ecms.admin;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * <li>This class manage access to feature pages 
 * in ECM Admin </li>
 * @author vuna2
 *
 */
public class ECMainFunction extends EcmsBase{

	public ECMainFunction(WebDriver dr,String...plfVersion) {
		super(dr);
		// TODO Auto-generated constructor stub
		this.plfVersion = plfVersion.length>0?plfVersion[0]:"4.0";
	}

	NavigationToolbar navToolbar = new NavigationToolbar(driver);

	//Template tab
	public final By ELEMENT_TEMPLATE_TAB = By.xpath("//*[text()='Templates']");
	/*
	 * @Added by: PhuongDT
	 * @date: 27/08/2013
	 */
	public final By ELEMENT_DOCUMENT_LINK_XPATH = By.xpath("//*[@class='title' and contains(text(), 'Documents')]");
	/*End Add*/
	public final By ELEMENT_DOCUMENT_LINK = By.className("uiIconEcmsTemplatesManager");
	public final By ELEMENT_MANAGE_TEMPLATE_SCREEN = By.xpath("//*[@class='title' and contains(text(),'Documents')]");
	public final By ELEMENT_LIST_LINK = By.className("uiIconEcmsCLVTemplatesManager");
	public final By ELEMENT_METADATA_LINK = By.className("uiIconEcmsMetadataManager");

	//Explorer Tab
	//public final By ELEMENT_EXPLORER_TAB = By.xpath("//*[@id='accordion']/..//a[contains(text(),'Explorer')]");
	public final By ELEMENT_EXPLORER_TAB = By.xpath("//*[text()='Explorer']");
	public final By ELEMENT_DRIVES_TAB = By.className("uiIconEcmsDriveManager");

	//Explorer Tags
	public final By ELEMENT_WCM_CATEGORY_TAG = By.xpath("//div[contains(text(),'Categories & Tags')]");

	//Repository > Node Type
	public final By ELEMENT_CONTENT_TYPES = By.xpath("//div[contains(text(),'Content Types')]");
	public final By ELEMENT_MANAGE_NODETYPE = By.className("uiIconEcmsNodeTypeManager");
	//		public final By ELEMENT_NODETYPE_TEXT = By.id("NodeTypeText");
	//		public final By ELEMENT_NODETYPE_SEARCH = By.xpath("//a[@title = 'Search']");
	public final By ELEMENT_MANAGE_NAME_SPACE_LINK = By.className("uiIconEcmsNamespaceManager");

	//Repository > Locks
	public final String ELEMENT_UNLOCK_NODE = "//*[text()='${lockedNode}']/../..//*[@data-original-title='UnLock']";

	//Advanced
	public final By ELEMENT_MANAGE_CATEGORIES_LINK = By.className("uiIconEcmsTaxonomyManagerTrees");
	public final By ELEMENT_ADD_QUERY_BUTTON = By.xpath("//*[text()='Add Query']");
	public final By ELEMENT_MANAGE_QUERIES_LINK = By.className("uiIconEcmsQueriesManager");

	public final By ELEMENT_MANAGE_SCRIPTS_LINK = By.className("uiIconEcmsScriptManager");
	public final By ELEMENT_ADD_SCRIPT_BUTTON = By.xpath("//*[text()='Add Script']");

	public final By ELEMENT_MANAGE_ACTIONS_LINK = By.className("uiIconEcmsActionManager");
	public final By ELEMENT_ADD_ACTION_TYPE_BUTTON = By.xpath("//*[text()='Add Action Type']");

	/*=============================================================*/
	//Template
	//Open Manage Template Screen
	/*
	 * @Modify by: PhuongDT
	 * @date: 27/08/2013
	 * @Content: Change condition to verify element Document
	 */
	public void goToTemplateTab(){	    
		navToolbar.goToContentAdministration();
		if (waitForAndGetElement(ELEMENT_DOCUMENT_LINK, 5000, 0) == null){
	//	if (isTextNotPresent("Documents")){
    //		if(isElementNotPresent(ELEMENT_DOCUMENT_LINK_XPATH)){
			click(ELEMENT_TEMPLATE_TAB);
		}
		click(ELEMENT_DOCUMENT_LINK);
		waitForAndGetElement(ELEMENT_MANAGE_TEMPLATE_SCREEN);	    
	}
	/*End Modify*/
	//Open [List Template] Screen
	public void goToListTemplateTab(){
		navToolbar.goToContentAdministration();
		if (waitForAndGetElement(ELEMENT_DOCUMENT_LINK, 5000, 0) == null){
			click(ELEMENT_TEMPLATE_TAB);
		}
		click(ELEMENT_LIST_LINK);
		waitForAndGetElement(By.xpath("//*[text()='Add Template']"));
	}
	
	//Go to [Metadata] tab
	public void goToMetadataTab(){
		navToolbar.goToContentAdministration();
		if (waitForAndGetElement(ELEMENT_METADATA_LINK, 5000, 0) == null){
			click(ELEMENT_TEMPLATE_TAB);
		}
		click(ELEMENT_METADATA_LINK);
	}

	/////////////////
	//Go To Content Administration / Repository / Manage Lock Tab
	public void goToLockedTab(){
		navToolbar.goToContentAdministration();
		if (waitForAndGetElement(ELEMENT_MANAGE_LOCKS, 5000, 0) == null){
			click(ELEMENT_REPOSITORY_TAB);
		}
		click(ELEMENT_MANAGE_LOCKS);
		Utils.pause(2000);
	}

	public void goToManageLockTab(){
		goToLockedTab();
		click(ELEMENT_MANAGE_LOCK_TAB);
		Utils.pause(2000);
	}

	//Go To Content Administration / Repository / Node Types Tab
	public void goToNodeTypeTab(){
		navToolbar.goToContentAdministration();
		if (isTextNotPresent("Node Types")){
			click(ELEMENT_REPOSITORY_TAB);
			click(ELEMENT_MANAGE_NODETYPE);
		}
		waitForAndGetElement(By.xpath("//*[@class='title' and contains(text(), 'Node Types')]"));
	}

	//Go To Content Administration / Repository / Namespaces
	public void goToNamespacesTab(){
		navToolbar.goToContentAdministration();
		if (waitForAndGetElement(ELEMENT_MANAGE_NAME_SPACE_LINK, 5000, 0) == null){
			click(ELEMENT_REPOSITORY_TAB);
		}
		click(ELEMENT_MANAGE_NAME_SPACE_LINK);
	}

	//Explorer: Driver/Views/Tags
	/////////

	//Function to go to Manage Driver from main screen
	public void goToManageDrive(){
		info("Go to manage driver form");
		navToolbar.goToContentAdministration();

		if ( waitForAndGetElement(ELEMENT_DRIVES_TAB, 5000, 0) == null){
			click(ELEMENT_EXPLORER_TAB);
		}
		click(ELEMENT_DRIVES_TAB);
		Utils.pause(500);
	}

	//Go to Manage View Screen
	public void goToManageViews(){
		navToolbar.goToContentAdministration();
		/*if (isTextNotPresent("Explorer Templates")){
			if (isTextNotPresent("Drives")){
				click(ELEMENT_EXPLORER_TAB);
			}
			click(ELEMENT_MANAGEMENT_VIEW);
		}*/
		if (waitForAndGetElement(ELEMENT_MANAGEMENT_VIEW, 5000, 0) != null){
			click(ELEMENT_MANAGEMENT_VIEW);
		}else {
			click(ELEMENT_EXPLORER_TAB);
			click(ELEMENT_MANAGEMENT_VIEW);
		}
		Utils.pause(500);
	}

	//View > Explorer templates
	public void goToExplorerTemplates(){
		if (isTextNotPresent("Explorer Templates")){
			goToManageViews();
		}
		click(ELEMENT_TAB_EXPLORER_TEMPLATE);
		Utils.pause(500);
	}

	//Go to Tags 
	public void goToTagsTab(){
		info("Go to Tags tab");
		navToolbar.goToContentAdministration();
		if (waitForAndGetElement(ELEMENT_MANAGE_TAGS, 5000, 0) == null){
			click(ELEMENT_EXPLORER_TAB);
		}
		click(ELEMENT_MANAGE_TAGS);
		//waitForTextPresent("Tag Manager");
		Utils.pause(1000);
	}

	//Go to Tag Permission Screen
	public void goToTagPermissionManager(){
		goToTagsTab();
		click(ELEMENT_TAG_PERMISSION);
		//waitForTextPresent("Memberships");
		Utils.pause(1000);
	}
	/////////////

	//Advanced: Categories/Queries/Scripts/Actions
	//Go to Category Tab in Content Admin
	public void goToCategoriesTabInContentAdmin(){
		navToolbar.goToContentAdministration();
		/*if (isTextNotPresent("Category Tree")){
			if (isTextNotPresent("Queries")){
				click(ELEMENT_ADVANCED_CONFIGURATION_TAB);
			}
			click(ELEMENT_MANAGE_CATEGORIES_LINK);
		}*/
		if (waitForAndGetElement(ELEMENT_MANAGE_CATEGORIES_LINK, 5000, 0) != null){
			click(ELEMENT_MANAGE_CATEGORIES_LINK);
		}else {
			click(ELEMENT_ADVANCED_CONFIGURATION_TAB);
			click(ELEMENT_MANAGE_CATEGORIES_LINK);
		}
		Utils.pause(500);
	}

	//Go to Queries Tab
	public void goToQueriesTabInContentAdmin(){
		navToolbar.goToContentAdministration();
		if (isElementNotPresent(ELEMENT_ADD_QUERY_BUTTON)){
			if (isTextNotPresent("Categories")){
				click(ELEMENT_ADVANCED_CONFIGURATION_TAB);
			}
			click(ELEMENT_MANAGE_QUERIES_LINK);
		}
		Utils.pause(500);
	}

	//Go to Scripts Tab
	public void goToScriptsTabInContentAdmin(){
		navToolbar.goToContentAdministration();
		if (isElementNotPresent(ELEMENT_ADD_SCRIPT_BUTTON)){
			if (isTextNotPresent("Categories")){
				click(ELEMENT_ADVANCED_CONFIGURATION_TAB);
			}
			click(ELEMENT_MANAGE_SCRIPTS_LINK);
		}
		Utils.pause(500);
	}

	//Go to Actions Tab
	public void goToActionsTabInContentAdmin(){
		navToolbar.goToContentAdministration();
		if (isElementNotPresent(ELEMENT_ADD_ACTION_TYPE_BUTTON)){
			if (isTextNotPresent("Categories")){
				click(ELEMENT_ADVANCED_CONFIGURATION_TAB);
			}
			click(ELEMENT_MANAGE_ACTIONS_LINK);
		}
		Utils.pause(500);
	}

	////////
}