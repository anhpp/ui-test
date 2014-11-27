package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.basicaction;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.EcmsPermission;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class ECMS_SE_BasicAction_DeleteSymlink extends PlatformBase{
	//Platform
	Button button;
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;
	Dialog dialog;
	ManageAlert magAlert;

	//Ecms
	EcmsBase ecms;
	EcmsPermission ecmsPer;
	ContentTemplate cTemplate;
	ContextMenu cMenu;
	SitesExplorer siteExp;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		dialog = new Dialog(driver);
		button = new Button(driver);
		magAlert = new ManageAlert(driver);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		actBar = new ActionBar(driver);
		ecms = new EcmsBase(driver);
		ecmsPer = new EcmsPermission(driver);
		cTemplate = new ContentTemplate(driver);
		cMenu = new ContextMenu(driver);
		siteExp = new SitesExplorer(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);

		info("Add symlink for action bar if it does not existed");
		navToolBar.goToSiteExplorer();
		//actBar.addSymlinkToActionBar();
		actBar.addItem2ActionBar("addSymLink", actBar.ELEMENT_ACTION_BAR_ADD_SYMLINK);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		//		logoutEcms();
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	
	
	/** CaseId: 101913
	 * case01: Delete Symlink node
	 * create new node - content folder
	 * add symlink for node
	 * delete symlink
	 */
	@Test
	public void test01_DeleteSymlinkOfNode(){
		String DATA_CONTENT_FOLDER_1 = "contentfolder01_1";
		By ELEMENT_CONTENT_FOLDER_1 = By.linkText(DATA_CONTENT_FOLDER_1);
		String DATA_CONTENT_FOLDER_2 = "contentfolder01_2";
		String SYMLINK_NAME = "symlink01.lnk";
		By ELEMENT_CONTENT_FOLDER_2 = By.xpath(ecms.ELEMENT_SIDEBAR_NODE_TITLE.replace("${nodeName}", DATA_CONTENT_FOLDER_2));

		By ELEMENT_CONTENT_FOLDER_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", SYMLINK_NAME));

		//create new content folder 1
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_1, ELEMENT_CONTENT_FOLDER_1);

		//create new content folder 2
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_2, ELEMENT_CONTENT_FOLDER_2);

		info("Add symlink for content folder 1 with target node is content folder 2");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		actBar.addSymlink("collaboration", "sites/" + DATA_CONTENT_FOLDER_2, SYMLINK_NAME);

		//check add successfully
		waitForAndGetElement(ELEMENT_CONTENT_FOLDER_SYMLINK);
		info("Add symlink for content folder 1 successfully");

		//delete symlink
		cMenu.deleteData(ELEMENT_CONTENT_FOLDER_SYMLINK);

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_1);
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_2);
	}
	
	
	/** CaseId: 101914
	 * case02: Delete the node which is the target node
	 * create 2 content folder: 1 and 2
	 * add symlink for content folder 1 with target node = content folder 2
	 * delete folder 2
	 * check symlink is deleted too
	 */
	@Test
	public void test02_DeleteNodeWithIsTargetNodeOfSymlink(){
		String DATA_CONTENT_FOLDER_1 = "contentfolder02_1";
		By ELEMENT_CONTENT_FOLDER_1 = By.linkText(DATA_CONTENT_FOLDER_1);
		String DATA_CONTENT_FOLDER_2 = "contentfolder02_2";
		String SYMLINK_NAME = "symlink02.lnk";
		By ELEMENT_CONTENT_FOLDER_2 = By.xpath(ecms.ELEMENT_SIDEBAR_NODE_TITLE.replace("${nodeName}", DATA_CONTENT_FOLDER_2));

		By ELEMENT_CONTENT_FOLDER_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", SYMLINK_NAME));

		//create new content folder 1
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_1, ELEMENT_CONTENT_FOLDER_1);

		//create new content folder 2
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_2, ELEMENT_CONTENT_FOLDER_2);

		info("Add symlink for content folder 1 with target node is content folder 2");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		actBar.addSymlink("collaboration", "sites/" + DATA_CONTENT_FOLDER_2, SYMLINK_NAME);

		//check add successfully
		waitForAndGetElement(ELEMENT_CONTENT_FOLDER_SYMLINK);
		info("Add symlink for content folder 1 successfully");

		//delete content folder 2
		cMenu.deleteData(ELEMENT_CONTENT_FOLDER_2);

		//check symlink is deleted 
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		waitForElementNotPresent(ELEMENT_CONTENT_FOLDER_SYMLINK);
		info("Symlink is deleted too");

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_1);
	}
	
	/** CaseId: 101860
	 * case03: Remove Symlink of node
	 * create new node - content folder
	 * add symlink for node
	 * add path node -> remove path node
	 */
	@Test
	public void test03_RemoveSymlinkOfNode(){
		String DATA_CONTENT_FOLDER = "contentfolder03";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);

		//create new content folder
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		//add symlink for node
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		actBar.goToTargetNodeWhenAddSymlink("sites/intranet");
		click(ecms.ELEMENT_TARGET_NODE.replace("${node}", "documents"));
		waitForAndGetElement(ecms.ELEMENT_REMOVE_PATH_NODE);
		click(ecms.ELEMENT_REMOVE_PATH_NODE);

		//check after remove
		assert getValue(ecms.ELEMENT_SYMLINK_PATH_NODE).isEmpty(): "Path target node is not empty";
		assert getValue(ecms.ELEMENT_SYMLINK_NAME).isEmpty(): "Name symlink is not empty";
		button.cancel();

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
	}
	
}
