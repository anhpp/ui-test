package org.exoplatform.selenium.platform;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ManageAccount.userType;

import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class ManageApplications extends PlatformBase {

	NavigationToolbar nav;
	Dialog dialog;
	Button button;
	ManageAlert alt;
	PageEditor pageE;
	ManageAccount magAc;
	NavigationToolbar navTool;

	public ManageApplications(WebDriver dr, String...plfVersion) {
		driver = dr;
		this.plfVersion = plfVersion.length>0?plfVersion[0]:"4.0";
		magAc = new ManageAccount(driver,this.plfVersion);
		navTool = new NavigationToolbar(driver, this.plfVersion);
		pageE = new PageEditor(driver,this.plfVersion);
		alt = new ManageAlert(driver, this.plfVersion);
		button = new Button(driver, this.plfVersion);
		nav = new NavigationToolbar(driver, this.plfVersion);
		dialog = new Dialog(driver);
	}

	/* Manage Application Page */
	public By ELEMENT_IMPORT_APPLICATION = By.linkText("Import Applications");
	public By ELEMENT_CATEGORIES_AREA_TITLE = By.xpath("//div[text()='Categories']");
	public By ELEMENT_SHOW_PORTLET_ICON = By.linkText("Portlet");
	public By ELEMENT_SHOW_GADGET_ICON = By.linkText("Gadget");
	public By ELEMENT_MANAGE_APPLICATION = By.linkText("Manage Applications");

	//Manage portlet
	public String ELEMENT_PORTLET_IN_CATEGORY = "//*[text()='${group}']/../following-sibling::li/a[contains(text(), '${portletName}')]";
	public String ELEMENT_PORTLET_INFO_NAME = "//*[@id='UIPortletInfo']//dt[contains(text(),'Display Name:')]/following-sibling::dd[1]/strong[contains(text(),'${portletName}')]";
	public String ELEMENT_PORTLET_INFO_DESCRIPTION = "//*[@id='UIPortletInfo']//dt[contains(text(),'Description:')]/following-sibling::dd[1][contains(text(),'${description}')]";
	public String ELEMENT_PORTLET_INFO_CATEGORY = "//*[@id='UIPortletInfo']//dt[contains(text(),'Categories:')]/following-sibling::dd[1][contains(text(),'${category}')]";

	//Manage Gadget
	public By ELEMENT_GADGET_LINK = By.xpath("//div[@id='UIApplicationRegistryPortlet']//*[@class='uiIconGadgets uiIconLightGray']");
	public By ELEMENT_ADD_REMOTE_GADGET_LINK = By.linkText("Add a remote gadget");
	public By ELEMENT_ADD_MANUAL_GADGET_LINK = By.linkText("Create a new gadget");
	public By ELEMENT_GADGET_NAME_TEXTBOX = By.id("name");
	public By ELEMENT_GADGET_SOURCE_TEXTBOX = By.id("source");
	public By ELEMENT_GADGET_URL_TEXBOX = By.id("url");
	public String ELEMENT_GADGET_INFO_NAME = "//*[@id='UIGadgetInfo']//dt[contains(text(), 'Gadget Name:')]//following-sibling::dd[1]/*[contains(text(), '${gadgetName}')]";
	public By ELEMENT_GADGET_EDIT_ICON = By.xpath("//*[@data-original-title='Edit Gadget']");
	public By ELEMENT_GADGET_REFRESH_ICON = By.xpath("//*[@data-original-title='Refresh']");
	public By ELEMENT_ADD_GADGET_TO_CATEGORY_LINK = By.linkText("Click here to add into categories");
	public String ELEMENT_GADGET_INFO_CATEGORY = "//*[@id='UIGadgetInfo']//dt[contains(text(), 'Categories:')]//following-sibling::dd[1][contains(text(), '${category}')]";
	public String ELEMENT_GADGET_DELETE_ICON = "//*[text()='${title}']/../../../..//*[@data-original-title='Delete Gadget']";
	public String ELEMENT_GADGET_CONFIRM_DELETE = "Are you sure to delete this gadget?";
	public By MESSAGE_GADGET_INFO_EXISTING_GADGET = By.xpath("//*[contains(text(),'This url is existing, please select another one!')]");

	//Application Registry portlet -> Edit Portlet
	public By ELEMENT_APPS_REG_PORTLET = By.xpath("//*[text()='Application Registry']/../../../../..");
	public By ELEMENT_SHOW_IMPORT_CHECKBOX = By.id("showImport");
	public By SHOW_IMPORT_CHECKED = By.xpath("//*[@id='showImport' and @checked='']");
	public By SHOW_IMPORT_UNCHECK = By.xpath("//input[@id='showImport' and @value='false']");
	public By ELEMENT_ACCESS_PERMISSION_TAB = By.xpath("//div[text()='Access Permission']");


	//Category
	public final By ELEMENT_ADD_NEW_CATEGORY = By.linkText("Add Category");
	public final By ELEMENT_FIELD_CATEGORY_NAME = By.id("name");
	public final By ELEMENT_FIELD_DISPLAY_NAME = By.id("displayName");
	public final By ELEMENT_FIELD_DESCRIPTION = By.id("description");
	public final By ELEMENT_PERMISSION_TAB = By.linkText("Permission Setting");
	public final By ELEMENT_CATEGORY_REMOVE_ICON = By.xpath("//*[@data-original-title='Delete category']");
	public final By ELEMENT_CATEGORY_EDIT_ICON = By.xpath("//*[@data-original-title='Edit Category']");
	public final String MESSAGE_EMPTY_CATEGORY = "This category does not have any application, click (+) button to add application.";
	public final String MESSAGE_CONFIRM_DELETE_CATEGORY = "Are you sure to delete this category and all applications on it?";
	public final String ELEMENT_CATEGORY_NAME = "//*[@id='ApplicationRegistryCategory']//*[@href='#${categoryName}']";
	public final By ELEMENT_CATEGORIES_FORM = By.xpath("//*[text()='Categories']"); 
	public final String IMPORT_APPLICATION_CONFIRMATION= "This action will automatically create categories and import all the gadgets and portlets on it.";
	public final By MESSAGE_CATEGORY_EXISTING = By.xpath("//*[contains(text(),'This category is existing, please enter another one')]");
	public final By MESSAGE_CATEGORY_DELETE_UNEXIST_CATEGORY = By.xpath("//*[contains(text(),'It's not possible to save the changes because that category doesn't exist')]");
	public final String ELEMENT_CATEGORY_GADGET = "//*[@id='ApplicationRegistryCategory']//a[contains(.,'${gadget}')]";

	//Add application to category
	public final By ELEMENT_ADD_APPS_BUTTON = By.xpath("//*[@data-original-title='Add application to category']");
	public final By ELEMENT_DISPLAY_APPS_NAME_TEXTBOX = By.id("displayName");
	public final By ELEMENT_APPS_TYPE = By.name("type");
	public final String ELEMENT_APPS_LOCATOR = "//*[@id='label' and text()='${appName}']";
	public final String ELEMENT_APPS_EXISTING = "//*[@id ='label' and text()='${appName}']/../..//input[@name='application']";
	public final By ELEMNT_APPS_PUBLIC_OPTION = By.id("publicMode");
	public final By ELEMENT_APPS_PUBLIC_CHECKED = By.xpath("//*[@id='publicMode' and @checked='']");

	// Gadget functions
	public void addRemoteGadget (String Url) {
		button = new Button(driver);
		waitForAndGetElement(ELEMENT_GADGET_LINK).click();
		for (int i =0;; i++)
		{
			if (i > DEFAULT_TIMEOUT/WAIT_INTERVAL) 
			{
				Assert.fail("Timeout");
			}
			click(ELEMENT_ADD_REMOTE_GADGET_LINK);
			if (isElementPresent(ELEMENT_GADGET_URL_TEXBOX))
			{
				type(ELEMENT_GADGET_URL_TEXBOX, Url, true);
				click(button.ELEMENT_ADD_BUTTON);
				return;
			}
		}
	}

	//Add manual gadget
	public void addManualGadget(String name, String source){
		button = new Button(driver);
		click(ELEMENT_ADD_MANUAL_GADGET_LINK);
		if (name != null){
			type(ELEMENT_GADGET_NAME_TEXTBOX, name, true);
		}
		if (source != null){
			type(ELEMENT_GADGET_SOURCE_TEXTBOX, source, true);
		}
		button.save();
		waitForAndGetElement(ELEMENT_GADGET_INFO_NAME.replace("${gadgetName}", name));
	}

	//Edit manual gadget
	public void editManualGadget(String title, String newSource){
		button = new Button(driver);
		click(ELEMENT_GADGET_EDIT_ICON);
		type(ELEMENT_GADGET_SOURCE_TEXTBOX, newSource, true);
		button.save();
		Utils.pause(1000);
	}

	//Delete gadget
	public void deleteGadget (String gadgetTitle) {
		alt = new ManageAlert(driver);
		if(isElementPresent(ELEMENT_GADGET_LINK))
			waitForAndGetElement(ELEMENT_GADGET_LINK).click();
		click(ELEMENT_GADGET_DELETE_ICON.replace("${title}", gadgetTitle));
		alt.waitForConfirmation(ELEMENT_GADGET_CONFIRM_DELETE);
		Utils.pause(1000);
		waitForElementNotPresent(ELEMENT_GADGET_DELETE_ICON.replace("${title}", gadgetTitle));
		info("Gadget " + gadgetTitle + " is deleted successfully");
	}

	//Add gadget to category
	public void addGadgetToCategory(String category){
		button = new Button(driver);
		click(ELEMENT_ADD_GADGET_TO_CATEGORY_LINK);
		String[] cat = category.split("/");
		for (int i = 0; i < cat.length; i ++){
			By elementCat = By.id("category_" + cat[i]);
			info("Add gadget to category " + cat[i]);
			if(waitForAndGetElement(elementCat, DEFAULT_TIMEOUT,0,2)==null)
				usePaginator(elementCat, "Do not find category " + cat[i]);
			check(elementCat, 2);
		}
		button.save();
		waitForAndGetElement(ELEMENT_GADGET_INFO_CATEGORY.replace("${category}", cat[0]));
	}

	//Category
	//Add a new category in Manage Applications
	public void addNewCategoryAtManageApplications(String categoryName, String displayName, String categoryDescription,
			boolean publicMode, Map<String, String> permissions, boolean verify){
		button = new Button(driver);
		info("-- Add a new category --");
		String ELEMENT_CURRENT_CATEGORY_NAME = ELEMENT_CATEGORY_NAME.replace("${categoryName}", categoryName);
		click(ELEMENT_ADD_NEW_CATEGORY);
		type(ELEMENT_FIELD_CATEGORY_NAME, categoryName, true);
		type(ELEMENT_FIELD_DISPLAY_NAME, displayName, true);
		type(ELEMENT_FIELD_DESCRIPTION, categoryDescription, true);
		click(ELEMENT_PERMISSION_TAB);
		if (publicMode){
			check(ELEMENT_CHECKBOX_PUBLIC_MODE, 2);
			waitForElementNotPresent(ELEMENT_ADD_PERMISSION_BUTTON);
			waitForElementNotPresent(ELEMENT_PERMISSION_GRID);
		} else {
			for (String key : permissions.keySet()) {
				setViewPermissions(key, permissions.get(key));
			}
		}
		button.save();	
		if (verify) {
			waitForTextPresent(MESSAGE_EMPTY_CATEGORY);
			waitForAndGetElement(ELEMENT_CURRENT_CATEGORY_NAME);
		}
	}

	/** Go to Edit category form at manage application
	 * @author phuongdt
	 * @param categoryName
	 */
	public void goToEditCategoryAtManageApplications(String categoryName){
		info("--Go to edit category (" + categoryName + ")--");
		click(ELEMENT_CATEGORY_NAME.replace("${categoryName}", categoryName));
		click(ELEMENT_CATEGORY_EDIT_ICON);
		waitForAndGetElement(ELEMENT_FIELD_CATEGORY_NAME);
		assert waitForAndGetElement(ELEMENT_FIELD_CATEGORY_NAME).getAttribute("readonly")!=null;
		assert waitForAndGetElement(ELEMENT_FIELD_DISPLAY_NAME).getAttribute("readonly")==null;
		assert waitForAndGetElement(ELEMENT_FIELD_DESCRIPTION).getAttribute("readonly")==null;
	}

	//Edit a category at Manage Applications
	public void editCategoryAtManageApplications(String categoryName, String newDisplayName, String newCategoryDescription,
			boolean publicMode, Map<String, String> permissions, boolean verify){
		button = new Button(driver);
		alt = new ManageAlert(driver);
		info("--Edit category (" + categoryName + ")--");
		click(ELEMENT_CATEGORY_NAME.replace("${categoryName}", categoryName));
		click(ELEMENT_CATEGORY_EDIT_ICON);
		if (newDisplayName != null){
			type(ELEMENT_FIELD_DISPLAY_NAME, newDisplayName, true);	
		}
		if (newCategoryDescription != null){
			type(ELEMENT_FIELD_DESCRIPTION, newCategoryDescription, true);
		}
		click(ELEMENT_PERMISSION_TAB);
		if (publicMode){
			check(ELEMENT_CHECKBOX_PUBLIC_MODE, 2);
			waitForElementNotPresent(ELEMENT_ADD_PERMISSION_BUTTON);
		} else {
			makeItPublic(false);
			if (waitForAndGetElement(ELEMENT_PERMISSION_GRID_DELETE_ICON, 5000, 0) != null){
				click(ELEMENT_PERMISSION_GRID_DELETE_ICON, 2);
				alt.acceptAlert();
				waitForElementNotPresent(ELEMENT_PERMISSION_GRID_DELETE_ICON);
			}
			for (String key : permissions.keySet()) {
				setViewPermissions(key, permissions.get(key));
			}
		}
		button.save();
		if (verify) {
			//waitForTextPresent(MESSAGE_EMPTY_CATEGORY);
			waitForAndGetElement(By.linkText(newDisplayName));
		}
	}

	//Delete a category at Manage Applications
	public void deleteCategoryAtManageApplications(String categoryName, boolean verify){
		alt = new ManageAlert(driver);

		info("--Delete category (" + categoryName + ")--");
		By ELEMENT_CURRENT_CATEGORY_NAME = By.xpath(ELEMENT_CATEGORY_NAME.replace("${categoryName}", categoryName));
		click(ELEMENT_CURRENT_CATEGORY_NAME);
		click(ELEMENT_CATEGORY_REMOVE_ICON);
		alt.waitForConfirmation(MESSAGE_CONFIRM_DELETE_CATEGORY);
		if (verify) {
			waitForElementNotPresent(ELEMENT_CURRENT_CATEGORY_NAME);
		}
		Utils.pause(500);
	}

	//Select a category
	public void selectCategoryAtManageApplications(String categoryName) {
		info("--Select category (" + categoryName + ")--");
		String ELEMENT_CURRENT_CATEGORY_NAME = ELEMENT_CATEGORY_NAME.replace("${categoryName}", categoryName);
		waitForAndGetElement(ELEMENT_CURRENT_CATEGORY_NAME);
		click(ELEMENT_CURRENT_CATEGORY_NAME);
		Utils.pause(500);
	}

	public void makeItPublic(boolean checked){
		if (checked) {
			if (waitForAndGetElement(ELEMENT_APPS_PUBLIC_CHECKED, 5000, 0, 2) == null) check(ELEMNT_APPS_PUBLIC_OPTION, 2);
		} else {
			if (waitForAndGetElement(ELEMENT_APPS_PUBLIC_CHECKED, 5000, 0, 2) != null) uncheck(ELEMNT_APPS_PUBLIC_OPTION, 2);
		}
	}

	public void addApplicationToCategory (String categoryTitle, boolean addNewApps, String newDisplayName, String applicationType, String displayName, boolean publicMode, String groupId, String membership ) {
		button = new Button(driver);
		click(ELEMENT_CATEGORY_NAME.replace("${categoryName}", categoryTitle));
		click(ELEMENT_ADD_APPS_BUTTON);

		//Add new application
		if (addNewApps) {
			type(ELEMENT_DISPLAY_APPS_NAME_TEXTBOX, newDisplayName, true);
			select(ELEMENT_APPS_TYPE, applicationType);
		}
		else
		{
			select(ELEMENT_APPS_TYPE, applicationType);
			usePaginator(ELEMENT_APPS_LOCATOR.replace("${appName}", displayName), "Application "+displayName+" is not found");
			click(ELEMENT_APPS_EXISTING.replace("${appName}", displayName), 2);
		}
		click(button.ELEMENT_ADD_BUTTON);

		//Set permission
		if (publicMode) makeItPublic(true);
		else {
			makeItPublic(false);
			setViewPermissions(groupId, membership);
		}
	}

	public void deleteApplication(String categoryTitle, String applicationName) {
		By CATEGORY_XPATH = By.xpath("//a[@title='"+categoryTitle+"']");
		By DELETE_APP_ICON = By.xpath("//a[@title='"+applicationName+"']/following::a[@title='Delete Application']");
		//By DELETE_APP_ICON= By.xpath("//span[@class='label' and text()='"+applicationName+"']/../..//input[@name='application']");
		waitForAndGetElement(CATEGORY_XPATH);
		click(CATEGORY_XPATH);
		waitForAndGetElement(DELETE_APP_ICON);
		click(DELETE_APP_ICON);
		alt.waitForConfirmation("Are you sure to delete this application?");
		Utils.pause(1000);
		waitForTextNotPresent(applicationName);
		info("'"+applicationName+"' is deleted successfully");
	}


	//Check show import
	public void showImportApplication (boolean checkShowImport) {
		nav = new NavigationToolbar(driver);
		pageE = new PageEditor(driver);
		button = new Button(driver);
		//goto Application
		nav.goToApplicationRegistry();

		//Verify Categories display as default
		waitForAndGetElement(ELEMENT_CATEGORIES_FORM);
		
		boolean importNotPresent = (waitForAndGetElement(ELEMENT_IMPORT_APPLICATION,3000,0) == null);

		if(((importNotPresent) & checkShowImport) || (!importNotPresent & !checkShowImport)){
			//goto Edit Page
			nav.goToEditPageEditor();

			//Click on Edit Portlet icon
			for (int i = 0; i < 3; i ++){
				mouseOver(ELEMENT_APPS_REG_PORTLET, true);
				Utils.pause(500);
				if (waitForAndGetElement(ELEMENT_EDIT_PORTLET_ICON, 5000, 0) != null){
					break;
				}
			}
			click(ELEMENT_EDIT_PORTLET_ICON);
			if (checkShowImport){
				if (waitForAndGetElement(SHOW_IMPORT_CHECKED, 7000, 0, 2) == null) check(ELEMENT_SHOW_IMPORT_CHECKBOX, 2);    				
			} else {
				if (waitForAndGetElement(SHOW_IMPORT_CHECKED, 7000, 0, 2) != null) uncheck(ELEMENT_SHOW_IMPORT_CHECKBOX, 2);
			}
			button.save();
			button.close();
			pageE.finishEditLayout();
		}

		//Verify after changing show import
		if (checkShowImport){
			waitForAndGetElement(ELEMENT_IMPORT_APPLICATION);
		}else {
			waitForElementNotPresent(ELEMENT_IMPORT_APPLICATION);
		}
	}

	public void importApplication () {
		alt = new ManageAlert(driver);
		showImportApplication(true);
		if(waitForAndGetElement(ELEMENT_IMPORT_APPLICATION,DEFAULT_TIMEOUT,0)==null)
			click(ELEMENT_MANAGE_APPLICATION);
		click(ELEMENT_IMPORT_APPLICATION);
		alt.waitForConfirmation(IMPORT_APPLICATION_CONFIRMATION);
		Utils.pause(1000);
	}

	/** View category at manage application
	 * @author phuongdt
	 * @param user
	 * @param categoryName
	 * @param view
	 */
	public void viewCategoryAtManageApplicationsWithOtherUser(userType user, String categoryName, boolean view,Object... opParams){
		String applicationName = (String) (opParams.length > 0 ? opParams[0]: "");		
		Boolean viewApp = (Boolean) (opParams.length > 1 ? opParams[1]: true);
		String applicationName2 = (String) (opParams.length > 2 ? opParams[2]: "");	
		Boolean viewApp2 = (Boolean) (opParams.length > 3 ? opParams[3]: true);

		magAc.userSignIn(user);
		navTool.goToSiteExplorer();
		navTool.goToEditPageEditor();
		if (view){
			waitForAndGetElement(By.xpath("//a[@title='"+categoryName+"']"));
		}else {
			waitForElementNotPresent(By.xpath("//a[@title='"+categoryName+"']"));
		}
		if(applicationName!=""){

			if(waitForAndGetElement("//a[@title='"+categoryName+"']/*[@class='uiIconArrowDown pull-right']", DEFAULT_TIMEOUT,0)==null){
				click(By.xpath("//a[@title='"+categoryName+"']"));
			}
			if(viewApp){
				waitForAndGetElement(By.xpath("//*[@class='txtLeft' and contains(text(),'"+applicationName+"')]"));
			}else{
				waitForElementNotPresent(By.xpath("//*[@class='txtLeft' and contains(text(),'"+applicationName+"')]"));
			}
		}
		if(applicationName2!=""){
			if(viewApp2){
				waitForAndGetElement(By.xpath("//*[@class='txtLeft' and contains(text(),'"+applicationName2+"')]"));
			}else{
				waitForElementNotPresent(By.xpath("//*[@class='txtLeft' and contains(text(),'"+applicationName2+"')]"));
			}
		}
		pageE.finishEditLayout();
		magAc.signOut();
	}
}
