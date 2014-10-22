package org.exoplatform.selenium.platform.ecms.functional.siteexplorer;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.admin.ManageDrive;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu.actionType;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ECMS_SiteExplorer_OtherChecks extends PlatformBase {
	//Platform
	ManageAccount magAc;
	NavigationToolbar navToolbar;

	//Ecms
	ContentTemplate cTemplate;
	SitesExplorer siteExp;
	ContextMenu cMenu;
	ActionBar actBar;
	EcmsBase ecms;
	ManageAlert magAlert;
	ManageDrive magDrv;
	ECMainFunction ecMain;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver, this.plfVersion);
		magAlert = new ManageAlert(driver, this.plfVersion);
		navToolbar = new NavigationToolbar(driver, this.plfVersion);
		actBar = new ActionBar(driver, this.plfVersion);
		ecms = new EcmsBase(driver, this.plfVersion);
		cTemplate = new ContentTemplate(driver, this.plfVersion);
		siteExp = new SitesExplorer(driver, this.plfVersion);
		cMenu = new ContextMenu(driver, this.plfVersion);
		magDrv = new ManageDrive(driver);
		ecMain = new ECMainFunction(driver, this.plfVersion); 
		magAc.signIn(DATA_USER1, DATA_PASS);
		driver.navigate().refresh();
	}

	@AfterMethod
	public void afterTest(){
		info("ECMS_SE_Collaboration_Manage_Tag: Finish testing");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	@Test(groups = "pending")
	/**
	 * == Add thumbnail image for node ==
	 * Test case ID: 109255
	 * Step 1: Go to Site Explorer
	 * Step 2: Create node
	 * Step 3: Open form to add thumbnail image
	 * Step 4: Add image
	 * PENDING: refer issue https://jira.exoplatform.org/browse/FQA-1057: [SELENIUM] Cannot upload thumbnail on ecms
	 */
	public void test01_AddThumbnailImageForNode(){
		/*Declare variable*/
		String document = "test01_AddThumbnailImage";
		String image = "TestData/test02addthumbnailimage.jpg";
		By pdocument = By.linkText(document);
		String DATA_DRIVE_NAME = "test01_SortNodesInAdminView";
		By pdriver = By.xpath(magDrv.ELEMENT_DATA_ORIGINAL_TITLE.replace("${title}", DATA_DRIVE_NAME));

		/* Step 1: Go to Site Explorer*/
		// Go to Site Administration
		ecMain.goToManageDrive();

		//Perform to create new driver with Collaboration workspace, 
		//and Admin View, icons View, simple view, Timeline View, Cover flow view,Simple view,slide show view
		//Note: on plf4.0.x, can't create cover flow view, slide show view, timeline view
		magDrv.addNewDrive(DATA_DRIVE_NAME, "collaboration","sites", "Platform/Administration","*",
				"", "Admin/Icons/List", false, true);

		//Go to Site Explorer
		navToolbar.goToSiteExplorer();
		actBar.showDrives();
		waitForAndGetElement(pdriver);
		click(pdriver);
		info("go to the driver");
		/* Step 2: Create node*/
		//Check if create content button is shown on action bar
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		navToolbar.goToSiteExplorer();
		actBar.showDrives();
		waitForAndGetElement(pdriver);
		click(pdriver);
		info("go to the driver");
		//Create node (folder, document) or upload file
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(document, document, "", "", "", "");
		info("file created");
		/* Step 3:Add thumbnail image for node */			
		actBar.addItem2ActionBar("overloadThumbnail", actBar.ELEMENT_OVERLOAD_THUMBNAIL, "Icons", "Icons");
		actBar.showDrives();
		waitForAndGetElement(pdriver);
		click(pdriver);
		info("go to the driver");
		actBar.goToNode(pdocument);
		//upload doesn't work
		actBar.uploadThumbnail(image);
		actBar.showDrives();
		waitForAndGetElement(pdriver);
		click(pdriver);
		info("go to the driver");
		actBar.goToViewMode("Icons");
		waitForAndGetElement(By.xpath("//*[@style='width: 116px; height: 73px;']"));
		navToolbar.goToSiteExplorer();
		cMenu.deleteDocument(By.linkText(document));
		info("-- Delete drive --");
		magDrv.deleteDrive(DATA_DRIVE_NAME);
		info("Data cleaned");
		info("Test Add thumbnail image for node succeed");
	}

	@Test
	/**
	 * == Collaboration drive ==
	 * Test case ID: 101310
	 * Step 1: Access general drives
	 * Step 2: Access Collaboration Center drive
	 */
	public void test02_CollaborationDrive(){
		String DATA_DRIVE_NAME = "Collaboration";
		By pdriver = By.xpath(magDrv.ELEMENT_DATA_ORIGINAL_TITLE.replace("${title}", DATA_DRIVE_NAME));
		
		//Go to Site Explorer
		navToolbar.goToSiteExplorer();
		actBar.showDrives();
		waitForAndGetElement(pdriver);
		click(pdriver);
		info("go to the driver");
		//check for initialed folder 
		waitForAndGetElement((ELEMENT_NODE_LINK).replace("${nodeLabel}", "groups"));
		waitForAndGetElement((ELEMENT_NODE_LINK).replace("${nodeLabel}", "sites"));
		waitForAndGetElement((ELEMENT_NODE_LINK).replace("${nodeLabel}", "tags"));
		//check for actions
		waitForAndGetElement(actBar.ELEMENT_NEW_CONTENT_LINK);
		waitForAndGetElement(ELEMENT_NEW_FOLDER_LINK);
		//check the rights
		navToolbar.goToContentAdministration();
		click(By.xpath("//*[text()='Explorer']"));
		click(By.xpath("//*[@class='uiIconEcmsDriveManager uiIconEcmsLightGray']"));
		waitForAndGetElement("//*[@data-original-title='Collaboration']/../..//*[@data-original-title='*:/platform/administrators,*:/platform/web-contributors']");
		info("Test Collaboration drive succeed");
	}

	@Test
	/**
	 * == Group drive ==
	 * Test case ID: 101303
	 * Step 1: Access group drives
	 * Step 2: Access Group drive
	 */
	public void test03_GroupDrive(){
		String DATA_DRIVE_NAME = "Development";
		By pdriver = By.xpath(magDrv.ELEMENT_DATA_ORIGINAL_TITLE.replace("${title}", DATA_DRIVE_NAME));
		
		//Go to Site Explorer
		navToolbar.goToSiteExplorer();
		actBar.showDrives();
		waitForAndGetElement(pdriver);
		click(pdriver);
		info("go to the driver");
		//check there is no folder
		waitForElementNotPresent(By.xpath("//*[@class='uiIcon16x16FolderDefault uiIcon16x16exo_portalFolder']"));
		//check the user is able to create a new node
		waitForAndGetElement(ELEMENT_NEW_FOLDER_LINK);
		//check the permission
		navToolbar.goToContentAdministration();
		click(By.xpath("//*[text()='Explorer']"));
		click(By.xpath("//*[@class='uiIconEcmsDriveManager uiIconEcmsLightGray']"));
		waitForAndGetElement("//*[@data-original-title='Groups']/../..//*[@data-original-title='*:${groupId}']");
		info("Test Group drive succeed");
	}

	@Test(priority=1)
	/**
	 * == Personal document ==
	 * Test case ID: 101321
	 * Step 1: Access personal drive
	 * Step 2: Access Public drive
	 */
	public void test04_PersonalDocument(){
		String DATA_DRIVE_NAME = "Personal Documents";
		By pdriver = By.xpath(magDrv.ELEMENT_DATA_ORIGINAL_TITLE.replace("${title}", DATA_DRIVE_NAME));
		
		//Go to Site Explorer
		navToolbar.goToSiteExplorer();
		actBar.showDrives();
		waitForAndGetElement(pdriver);
		click(pdriver);
		info("go to the driver");
		//check the actions 
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK,"Admin","Admin");
		
		actBar.showDrives();
		click(pdriver);
		waitForAndGetElement(actBar.ELEMENT_NEW_CONTENT_LINK);
		waitForAndGetElement(ELEMENT_NEW_FOLDER_LINK);
		//check for initialed folder 
		waitForAndGetElement(ELEMENT_NODE_LINK.replace("${nodeLabel}", "Documents"));
		waitForAndGetElement(ELEMENT_NODE_LINK.replace("${nodeLabel}", "Music"));
		waitForAndGetElement(ELEMENT_NODE_LINK.replace("${nodeLabel}", "Public"));
		//check the permission
		navToolbar.goToContentAdministration();
		click(By.xpath("//*[text()='Explorer']"));
		click(By.xpath("//*[@class='uiIconEcmsDriveManager uiIconEcmsLightGray']"));
		waitForAndGetElement(By.xpath("//*[@data-original-title='Personal Documents']/../..//*[@data-original-title=' /Users /${userId} /Private']"));
		info("Test Personal document succeed");
	}

	@Test(dependsOnMethods = { "test04_PersonalDocument" },alwaysRun=true)
	/**
	 * == Public folder ==
	 * Test case ID: 101295
	 * Step 1: Access personal drive
	 * Step 2: Access Public folder
	 */
	public void test05_PublicFolder(){
		String DATA_DRIVE_NAME = "Personal Documents";
		By pdriver = By.xpath(magDrv.ELEMENT_DATA_ORIGINAL_TITLE.replace("${title}", DATA_DRIVE_NAME));
		
		//Go to Site Explorer
		navToolbar.goToSiteExplorer();
		actBar.showDrives();
		waitForAndGetElement(pdriver);
		click(pdriver);
		info("go to the driver");
		click(By.xpath("//*[@class='uiIcon24x24FolderDefault uiIcon24x24exo_publicFolder' and @data-original-title='Public']"));
		//check there is no folder
		waitForElementNotPresent(By.xpath("//*[@class='uiIcon16x16FolderDefault uiIcon16x16exo_portalFolder']"));
		//check the actions 
		waitForAndGetElement(actBar.ELEMENT_NEW_CONTENT_LINK);
		waitForAndGetElement(ELEMENT_NEW_FOLDER_LINK);
		//check the permission
		navToolbar.goToContentAdministration();
		click(By.xpath("//*[text()='Explorer']"));
		click(By.xpath("//*[@class='uiIconEcmsDriveManager uiIconEcmsLightGray']"));
		waitForAndGetElement("//*[@data-original-title='Personal Documents']/../..//*[@data-original-title='*:/platform/users']");
		info("Test Public folder succeed");
	}

	@Test(groups = "pending")
	/**
	 * == Remove thumbnail image ==
	 * Test case ID: 101298
	 * Step 1: Go to Site Explorer
	 * Step 2: Create node
	 * Step 3: Add thumbnail image for node
	 * Step 4: Remove thumbnail image
	 * PENDING: refer issue https://jira.exoplatform.org/browse/FQA-1057: [SELENIUM] Cannot upload thumbnail on ecms
	 */
	public void test06_RemoveThumbnailImage(){
		/*Declare variable*/
		String document = "test02_RemoveThumbnailImage";
		String image = "TestData/test02_RemoveThumbnailImage.jpg";
		By pdocument = By.linkText(document);
		String DATA_DRIVE_NAME = "test01_SortNodesInAdminView";
		By pdriver = By.xpath(magDrv.ELEMENT_DATA_ORIGINAL_TITLE.replace("${title}", DATA_DRIVE_NAME));

		/* Step 1: Go to Site Explorer*/
		// Go to Site Administration
		ecMain.goToManageDrive();

		//Perform to create new driver with Collaboration workspace, 
		//and Admin View, icons View, simple view, Timeline View, Cover flow view,Simple view,slide show view
		//Note: on plf4.0.x, can't create cover flow view, slide show view, timeline view
		magDrv.addNewDrive(DATA_DRIVE_NAME, "collaboration","sites", "Platform/Administration","*",
				"", "Admin/Icons/List", false, true);

		//Go to Site Explorer
		navToolbar.goToSiteExplorer();
		actBar.showDrives();
		waitForAndGetElement(pdriver);
		click(pdriver);
		info("go to the driver");
		/* Step 2: Create node*/
		//Check if create content button is shown on action bar
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		navToolbar.goToSiteExplorer();
		actBar.showDrives();
		waitForAndGetElement(pdriver);
		click(pdriver);
		info("go to the driver");
		//Create node (folder, document) or upload file
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(document,document,"", "", "", "");

		/* Step 3:Add thumbnail image for node */			
		actBar.addItem2ActionBar("overloadThumbnail", actBar.ELEMENT_OVERLOAD_THUMBNAIL, "Icons", "Icons");
		actBar.showDrives();
		waitForAndGetElement(pdriver);
		click(pdriver);
		info("go to the driver");
		actBar.goToNode(pdocument);
		actBar.uploadThumbnail(image);
		actBar.goToViewMode("Icons");

		/*Step 4: Remove thumbnail image*/
//		Click on 'Overload thumbnail' icon
//		Click 'Delete' icon near the image in 'Add thumbnail image' pop-up

		//The thumbnail image of node is removed. 

		/*Clear data*/
		info("-- Clear data --");
		actBar.actionsOnElement(document,actionType.DELETE,false,true);
		magDrv.deleteDrive(DATA_DRIVE_NAME);
		info("Test Remove thumbnail succeed");
	}
}
