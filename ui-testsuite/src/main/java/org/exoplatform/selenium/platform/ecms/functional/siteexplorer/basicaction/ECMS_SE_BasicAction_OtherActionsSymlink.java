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


public class ECMS_SE_BasicAction_OtherActionsSymlink extends PlatformBase{
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

	
	/** CaseId: 102008
	 * case01: Copy Symlink node to other node
	 * create 2 node: content folder 1 and 2
	 * create symlink for content folder 1
	 * copy symlink from content folder 1 to content folder 2
	 */
	@Test
	public void test01_CopySymlinkNodeToOtherNode(){
		String DATA_CONTENT_FOLDER_1 = "contentfolder01_1";
		By ELEMENT_CONTENT_FOLDER_1 = By.linkText(DATA_CONTENT_FOLDER_1);
		String DATA_CONTENT_FOLDER_2 = "contentfolder01_2";
		By ELEMENT_CONTENT_FOLDER_2 = By.linkText(DATA_CONTENT_FOLDER_2);
		String SYMLINK_NAME = "symlink01.lnk";
		By ELEMENT_DOCUMENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", SYMLINK_NAME));

		//create new content folder 1
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_1, ELEMENT_CONTENT_FOLDER_1);

		//create new content folder 2
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_2,ELEMENT_CONTENT_FOLDER_2);

		info("Add symlink for content folder 1");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		actBar.addSymlink("collaboration", "sites/intranet/documents", SYMLINK_NAME);
		info("Add symlink successfully");

		//copy symlink from content folder 1 to content folder 2
		cMenu.copyAndPasteNode(ELEMENT_DOCUMENT_SYMLINK, ELEMENT_CONTENT_FOLDER_2);

		//check copy successfully
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_2);
		waitForAndGetElement(ELEMENT_DOCUMENT_SYMLINK);
		info("Copy symlink successfully");

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_1);
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_2);		  
	}
	
	
	/** CaseId: 101954
	 * case02: Create child nodes in Symlink node
	 * create new node: content folder
	 * add symlink for node
	 * add content folder for symlink
	 * check add successfully
	 */
	@Test
	public void test02_CreateChildNodeInSymlinkNode(){
		String DATA_CONTENT_FOLDER_1 = "contentfolder02_1";
		By ELEMENT_CONTENT_FOLDER_1 = By.linkText(DATA_CONTENT_FOLDER_1);
		String DATA_CONTENT_FOLDER_2 = "contentfolder02_2";
		By ELEMENT_CONTENT_FOLDER_2 =  By.linkText(DATA_CONTENT_FOLDER_2);
		String SYMLINK_NAME = "symlink02.lnk";
		By ELEMENT_CONTENT_FOLDER_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", SYMLINK_NAME));
		String DATA_CONTENT_FOLDER_3 = "contentfolder02_3";
		By ELEMENT_CONTENT_FOLDER_3 = By.linkText(DATA_CONTENT_FOLDER_3);

		//create new content folder 1
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_1, ELEMENT_CONTENT_FOLDER_1);

		//create new content folder 2
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_2,ELEMENT_CONTENT_FOLDER_2);

		info("Add symlink for content folder 1 with target node is content folder 2");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		actBar.addSymlink("collaboration", "sites/" + DATA_CONTENT_FOLDER_2, SYMLINK_NAME);

		//check add successfully
		waitForAndGetElement(ELEMENT_CONTENT_FOLDER_SYMLINK);
		info("Add symlink for content folder 1 successfully");

		//add content folder for symlink
		click(ELEMENT_CONTENT_FOLDER_SYMLINK);
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_3, ELEMENT_CONTENT_FOLDER_3);

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_1);
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_2);
	}
	
	/** CaseId: 101971
	 * case03: Cut Symlink node to other node
	 * create 2 node: content folder 1 and 2
	 * create symlink for content folder 1
	 * cut symlink form content folder 1 to content folder 2 
	 */
	@Test
	public void test03_CutSymlinkNodeToOtherNode(){
		String DATA_CONTENT_FOLDER_1 = "contentfolder03_1";
		By ELEMENT_CONTENT_FOLDER_1 = By.linkText(DATA_CONTENT_FOLDER_1);
		String DATA_CONTENT_FOLDER_2 = "contentfolder03_2";
		By ELEMENT_CONTENT_FOLDER_2 = By.linkText(DATA_CONTENT_FOLDER_2);
		String SYMLINK_NAME = "symlink03.lnk";
		By ELEMENT_DOCUMENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", SYMLINK_NAME));

		//create new content folder 1
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_1, ELEMENT_CONTENT_FOLDER_1);

		//create new content folder 2
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_2,ELEMENT_CONTENT_FOLDER_2);

		info("Add symlink for content folder 1");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		actBar.addSymlink("collaboration", "sites/intranet/documents", SYMLINK_NAME);
		info("Add symlink successfully");

		//cut symlink from content folder 1 to content folder 2
		cMenu.cutAndPasteNode(ELEMENT_DOCUMENT_SYMLINK, ELEMENT_CONTENT_FOLDER_2);

		//check cut successfully
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_2);
		waitForAndGetElement(ELEMENT_DOCUMENT_SYMLINK);
		info("Copy symlink successfully");

		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		waitForElementNotPresent(ELEMENT_DOCUMENT_SYMLINK);

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_1);
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_2);
	}
	
	/** CaseId: 101886
	 * Case04: Edit the name of added link node in “Symlink Name”
	 * create node - content folder
	 * add symlink with input name
	 */
	@Test
	public void test04_AddSymlinkWithInputName(){
		String DATA_CONTENT_FOLDER = "contentfolder04";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		String DATA_SYMLINK = "symlink04.lnk";
		By ELEMENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", DATA_SYMLINK));

		//create new content folder
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		//add symlink
		info("Add symlink for content folder with name is node default name");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		actBar.addSymlink("collaboration", "sites/intranet/documents", DATA_SYMLINK);

		//check add successfully
		waitForAndGetElement(ELEMENT_SYMLINK);

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
	}
	
	/**CaseID: 101895
	 * case05: Open form to add Symlink for node using icon in action bar
	 * go to add symlink
	 * check form
	 */
	@Test
	public void test05_CheckFormAddSymlinkForNode(){		  
		info("Check Add Symlink Form");
		actBar.goToAddSymlinkTab();

		//check Add symlink form
		waitForAndGetElement(ecms.ELEMENT_ADD_SYMLINK_POPUP);
		waitForTextPresent("Symlink Manager");
		waitForAndGetElement(ecms.ELEMENT_SYMLINK_PATH_NODE);
		waitForAndGetElement(ecms.ELEMENT_SYMLINK_NAME);  
		info("Check add symlink popup successfully");
		dialog.closeMessageDialog();
	}

	/** CaseId: 101868
	 * case06: Rename Symlink node
	 * create new node - content folder
	 * add symlink for node
	 * rename symlink, check name of target node is not changed
	 */
	@Test
	public void test06_RenameSymlink(){
		String DATA_CONTENT_FOLDER_1 = "contentfolder06_1";
		By ELEMENT_CONTENT_FOLDER_1 = By.linkText(DATA_CONTENT_FOLDER_1);
		String DATA_CONTENT_FOLDER_2 = "contentfolder06_2";
		String SYMLINK_NAME = "symlink06.lnk";
		By ELEMENT_CONTENT_FOLDER_2 = By.xpath(ecms.ELEMENT_SIDEBAR_NODE_TITLE.replace("${nodeName}", DATA_CONTENT_FOLDER_2));

		By ELEMENT_CONTENT_FOLDER_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", SYMLINK_NAME));
		String SYMLINK_NAME_NEW = "symlink36_edited.lnk";
		By ELEMENT_CONTENT_FOLDER_SYMLINK_NAME = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", SYMLINK_NAME_NEW));

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

		//rename symlink
		cMenu.contextMenuAction(ELEMENT_CONTENT_FOLDER_SYMLINK, cMenu.ELEMENT_MENU_RENAME_NODE, SYMLINK_NAME_NEW);
		waitForAndGetElement(ELEMENT_CONTENT_FOLDER_SYMLINK_NAME); 

		//folder 2 is not renamed
		waitForAndGetElement(ELEMENT_CONTENT_FOLDER_2);

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_1);	  
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_2);	 
	}
	
	/** CaseId: 101869
	 * case07: Rename the node which is the target node
	 * create 2 new node - content folder 1, content folder 2
	 * add symlink for content folder 1 with target node is content folder 2
	 * rename content folder 2
	 * check symlink is not change name
	 */
	@Test
	public void test07_RenameNodeIsTargetNodeOfSymlink(){
		String DATA_CONTENT_FOLDER_1 = "contentfolder07_1";
		By ELEMENT_CONTENT_FOLDER_1 = By.linkText(DATA_CONTENT_FOLDER_1);
		String DATA_CONTENT_FOLDER_2 = "contentfolder07_2";
		String SYMLINK_NAME = "symlink34.lnk";
		By ELEMENT_CONTENT_FOLDER_2 = By.xpath(ecms.ELEMENT_SIDEBAR_NODE_TITLE.replace("${nodeName}", DATA_CONTENT_FOLDER_2));

		By ELEMENT_CONTENT_FOLDER_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", SYMLINK_NAME));
		String DATA_CONTENT_FOLDER_2_NEW = "contentfolder07_2_edited";
		By ELEMENT_CONTENT_FOLDER_2_NEW = By.linkText(DATA_CONTENT_FOLDER_2_NEW);

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

		//rename content folder 2
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_2);
		cMenu.contextMenuAction(ELEMENT_CONTENT_FOLDER_2, cMenu.ELEMENT_MENU_RENAME_NODE, DATA_CONTENT_FOLDER_2_NEW);

		//check name of symlink is not change
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		waitForAndGetElement(ELEMENT_CONTENT_FOLDER_SYMLINK);
		info("Symlink is kept old name");

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_1);
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_2_NEW);		  
	}
	
	/** CaseId: 102136
	 * case08: Restore deleted Symlink
	 * create new node - content folder
	 * add symlink for node 
	 * Open form to select node to add Symlink
	 * Add Symlink for node
	 * Delete target node
	 * Restore Target node
	 * Restore Symlink node
	 */
	@Test
	public void test08_RestoreDeletedSymlink(){
		String DATA_CONTENT_FOLDER = "contentfolder08";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		String DATA_SYMLINK = "symlink08.lnk";
		By ELEMENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", "symlink08.lnk"));

		//create new content folder
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);
		
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		info("Add symlink for node with target node is documents");
		actBar.addSymlink("collaboration", "sites/" + DATA_CONTENT_FOLDER, DATA_SYMLINK);
		
		waitForAndGetElement(ELEMENT_SYMLINK);
		click(ELEMENT_SYMLINK);
		
		// delete folder
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
		
		// go to restore in the trash
		actBar.chooseDrive(ecms.ELEMENT_TRASH_DRIVE);
		check(ecms.ELEMENT_DOCUMENT_NODE_LIST.replace("${node}", DATA_CONTENT_FOLDER),2);
		click(actBar.ELEMENT_RESTORE_FROM_TRASH);
		
		// check the both element are in the sites management
		actBar.chooseDrive(ecms.ELEMENT_SITES_MANAGEMENT_DRIVE);
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		
		waitForAndGetElement(ELEMENT_CONTENT_FOLDER);
		click(ELEMENT_CONTENT_FOLDER);
		waitForAndGetElement(ELEMENT_SYMLINK);
		
		// delete the folder again
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
		
		// go to the trash and check the element restore is impossible for the symlink
		actBar.chooseDrive(ecms.ELEMENT_TRASH_DRIVE);
		check(ecms.ELEMENT_DOCUMENT_NODE_LIST.replace("${node}", DATA_SYMLINK),2);
		waitForElementNotPresent(actBar.ELEMENT_RESTORE_FROM_TRASH);

	}
	
	
	
	
	/** CaseId: 101858
	 * case09: View content of Symlink
	 * create new node - content folder
	 * add symlink for node with target node is folder: acme/documents
	 * check can view all subnode of documents in document.lnk
	 */
	@Test
	public void test09_ViewContentOfSymlink(){
		String DATA_CONTENT_FOLDER = "contentfolder09";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		String DATA_SYMLINK = "documents.lnk";
		By ELEMENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", "documents.lnk"));

		//create new content folder
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		//add symlink with target node - acme/documents for this content folder
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		info("Add symlink for node with target node is documents");
		actBar.addSymlink("collaboration", "sites/acme/documents", DATA_SYMLINK);

		waitForAndGetElement(ELEMENT_SYMLINK);
		click(ELEMENT_SYMLINK);

		//check subnode of symlink
		waitForAndGetElement(By.xpath(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", "conditions.doc")));
		waitForAndGetElement(By.xpath(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", "metro.pdf")));
		waitForAndGetElement(By.xpath(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", "offices.jpg")));

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
	}
	
}