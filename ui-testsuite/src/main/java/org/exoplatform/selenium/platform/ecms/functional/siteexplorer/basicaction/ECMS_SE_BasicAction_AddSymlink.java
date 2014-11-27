package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.basicaction;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.EcmsPermission;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @date: 17/04/2013
 * @author lientm
 * Update follow new test cases for plf4
 */

/**
 * <li>PLF4: Update by
 * @author vuna2
 * </li>
 *
 */
/*
 * @author: Lientm
 * @date: 24/09/2012
 */
public class ECMS_SE_BasicAction_AddSymlink extends PlatformBase{
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
		cTemplate = new ContentTemplate(driver,this.plfVersion);
		cMenu = new ContextMenu(driver);
		siteExp = new SitesExplorer(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);

		info("Add symlink for action bar if it does not existed");
		navToolBar.goToSiteExplorer();
		actBar.addItem2ActionBar("addSymLink", actBar.ELEMENT_ACTION_BAR_ADD_SYMLINK);
	}

	@AfterMethod
	public void afterMethods() {
		driver.quit();
	}



	/** CaseId: 102044
	 * case01: Add new Symlink has the same name with existing Symlink in document  
	 * create new document - document has parent type is nt:nt:unstructured: web content
	 * check cannot add 2 symlinks have samve name in document
	 * jira issue : https://jira.exoplatform.org/browse/ECMS-6523
	 * error : possible to create two symlinks with the same name
	 */
	@Test(groups = {"pending"})
	public void test01_Add2SymlinksHaveSameNameInDocument(){
		String WEB_CONTENT_NAME = "Webcontentname_01";
		By ELEMENT_WEB_CONTENT = By.linkText(WEB_CONTENT_NAME);
		String DATA_SYMLINK = "symlink01.lnk";
		By ELEMENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", DATA_SYMLINK));
		By ELEMENT_SYMLINK2 = By.xpath("//*[contains(@mousedown,'collaboration:/sites/"
				+ WEB_CONTENT_NAME + "/" + DATA_SYMLINK.replace(".", "") + "[2]')]");

		//create new file document
		actBar.goToAddNewContent();

		info("Create new file document with user john");
		cTemplate.createNewWebContent(WEB_CONTENT_NAME, WEB_CONTENT_NAME, "", "", "", "");

		//add 2 symlink have same name for file document
		info("Add 2 symlinks have same name in file document");
		actBar.addSymlink("collaboration", "sites/intranet/documents", DATA_SYMLINK);
		waitForAndGetElement(ELEMENT_SYMLINK);
		actBar.addSymlink("collaboration", "sites/intranet/documents", DATA_SYMLINK);
		waitForAndGetElement(ELEMENT_SYMLINK2);

		//delete data
		cMenu.deleteDocument(ELEMENT_WEB_CONTENT);
	}

	/** CaseId: 102043
	 * case02: Add new Symlink has the same name with existing Symlink in Content Folder
	 * create new content folder
	 * add 2 symlink have same name for content folder
	 * check add successfully
	 */
	@Test
	public void test02_Add2SymlinksHaveSameNameInContentFolder(){
		String DATA_CONTENT_FOLDER = "contentfolder02";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		String DATA_SYMLINK = "documents.lnk";
		By ELEMENT_SYMLINK2 = By.xpath("//*[contains(@mousedown,'collaboration:/sites/"
				+ DATA_CONTENT_FOLDER + "/" + DATA_SYMLINK.replace(".", "") + "[2]')]");

		//create new content folder
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		info("Add 2 symlinks have same name in content folder");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);

		actBar.addSymlink("collaboration", "sites/intranet/documents", DATA_SYMLINK);
		waitForAndGetElement(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", DATA_SYMLINK));

		actBar.addSymlink("collaboration", "sites/intranet/documents", DATA_SYMLINK);
		waitForAndGetElement(ELEMENT_SYMLINK2);
		info("Add 2 symlink same name in content folder successfully");

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);		  
	}


	/** CaseId: 102045
	 * case03: Add new Symlink has the same name with existing Symlink in Document Folder
	 * create new document folder
	 * add 2 symlink have same name for document folder
	 * check cannot add
	 */
	@Test
	public void test03_Add2SymlinksHaveSameNameInDocumentFolder(){
		String DATA_DOCUMENT_FOLDER = "documentfolder03";
		By ELEMENT_DOCUMENT_FOLDER = By.linkText(DATA_DOCUMENT_FOLDER);
		String DATA_SYMLINK = "documents.lnk";

		info("Create new document folder");
		cTemplate.createNewFolder(DATA_DOCUMENT_FOLDER, folderType.Document);

		info("Add 2 symlinks have same name in document folder");
		ecms.goToNode(ELEMENT_DOCUMENT_FOLDER);
		actBar.addSymlink("collaboration", "sites/intranet/documents", DATA_SYMLINK);
		waitForAndGetElement(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", DATA_SYMLINK));	
		actBar.addSymlink("collaboration", "sites/intranet/documents", DATA_SYMLINK);

		//check alert
		magAlert.verifyAlertMessage("The node name already exists.");
		button.cancel();
		info("cannot add 2 symlink same name in document folder");

		//delete data
		cMenu.deleteDocument(ELEMENT_DOCUMENT_FOLDER);		  
	}

	/**CaseID: 102032
	 * case04: Add Symlink by right click on node but do not select this node
	 * create new node (folder, file document)
	 * right click on node -> add symlink
	 * check symlink
	 */
	@Test
	public void test04_AddSymlinkByRightClick(){
		String DATA_FOLDER = "ECMS_AddSymlink_Content_folder_04";
		By ELEMENT_FOLDER = By.linkText(DATA_FOLDER);
		String FILE_NAME = "ECMS_AddSymlink_file_02";
		By ELEMENT_FILE = By.linkText(FILE_NAME);

		//create node: content folder
		cTemplate.createNewFolder(DATA_FOLDER, folderType.Content);
		info("Create new content folder successfully");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(FILE_NAME, FILE_NAME, FILE_NAME);
		ecms.goToNode(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);

		// add symlink
		info("Add symlink for node by right click");
		cMenu.contextMenuAction(ELEMENT_FOLDER, ecms.ELEMENT_CONTEXT_MENU_ADD_SYMLINK);
		cMenu.contextMenuAction(ELEMENT_FILE, ecms.ELEMENT_CONTEXT_MENU_ADD_SYMLINK);

		// check symlink
		waitForAndGetElement(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", DATA_FOLDER.replaceAll("_", "").toLowerCase() + ".lnk"));
		waitForAndGetElement(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", FILE_NAME.replaceAll("_", "").toLowerCase()  + ".lnk")); // beug 
		info("Add symlink for nodes successful");	

		//delete symlink
		cMenu.deleteData(ELEMENT_FOLDER);
		cMenu.deleteData(ELEMENT_FILE);
	}

	/** 
	 * Case Id : 102123
	 * case 05
	 * Add Symlink for a node to a node that has "check-in" status
	 * 
	 */
	@Test
	public void test05_AddSymlinkForaNodeToaNodeThatHasCheckInStatus(){
		String FILE_NAME = "ecmstest0501";
		By FILE_BY = By.linkText(FILE_NAME);

		String DATA_CONTENT_FOLDER2 = "ecmstest0502";
		By ELEMENT_CONTENT_FOLDER2 = By.linkText(DATA_CONTENT_FOLDER2);

		info("Add new content folder");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(FILE_NAME, "files test 05 02", FILE_NAME);
		cMenu.contextMenuAction(FILE_BY,cMenu.ELEMENT_MENU_CHECKIN);

		click(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);

		info("Add new content folder");
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER2, ELEMENT_CONTENT_FOLDER2);
		click(ELEMENT_CONTENT_FOLDER2);
		info("Add Symlink for node 02");
		actBar.addSymlink("collaboration", "sites/intranet/documents", FILE_NAME+".lnk");

		waitForAndGetElement(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", FILE_NAME+".lnk"));

		// delete data 
		cMenu.contextMenuAction(FILE_BY,cMenu.ELEMENT_MENU_CHECKOUT);
		cMenu.deleteData(ELEMENT_CONTENT_FOLDER2);
		cMenu.deleteData(FILE_BY);
	}


	/** CaseId: 102019
	 * case06: Add Symlink for Content Folder
	 * create new content folder
	 * add symlink for node with target node = intranet/documents
	 * check symlink
	 */
	@Test
	public void test06_AddSymlinkForContentFolder(){
		String DATA_CONTENT_FOLDER = "ECMS_AddSymlink_Content_folder_06";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		String DATA_SYMLINK = "documents.lnk";

		//create new content folder
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		//add symlink
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		info("Add symlink for content folder");
		actBar.addSymlink("collaboration", "sites/intranet/documents" , DATA_SYMLINK);
		waitForAndGetElement(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", DATA_SYMLINK));

		//delete data
		cMenu.deleteData(ELEMENT_CONTENT_FOLDER);
	}

	/** CaseId: 102020
	 * case07: Add Symlink for node is Document Folder
	 * create new document folder
	 * add symlink for node with target node = Intranet/documents
	 * check symlink
	 */
	@Test
	public void test07_AddSymlinkForDocumentFolder(){
		String DATA_DOCUMENT_FOLDER = "ECMS_AddSymlink_Document_folder_07";
		By ELEMENT_DOCUMENT_FOLDER = By.linkText(DATA_DOCUMENT_FOLDER);
		String DATA_SYMLINK = "documents.lnk";

		info("Create new document folder");
		cTemplate.createNewFolder(DATA_DOCUMENT_FOLDER, folderType.Document);
		waitForAndGetElement(ELEMENT_DOCUMENT_FOLDER);
		info("Create new document folder successfully");

		//add symlink
		ecms.goToNode(ELEMENT_DOCUMENT_FOLDER);
		info("Add symlink for document folder");
		actBar.addSymlink("collaboration", "sites/intranet/documents", DATA_SYMLINK);

		waitForAndGetElement(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", DATA_SYMLINK));

		//delete data
		cMenu.deleteData(ELEMENT_DOCUMENT_FOLDER);
	}


	/** 
	 * Case Id : 102122
	 * case 08:Add Symlink for node (not nt:file) when its parent node has "check in" status 
	 * 
	 */
	@Test
	public void test08_AddSymlinkForaNodeNotNtFileWhenItsParentNodeHAsCheckInStatus(){
		String WEB_NAME = "ecmstestwebcontent0801";
		By WEB_BY = By.linkText(WEB_NAME);

		String DATA_CONTENT_FOLDER2 = "ecmstestfolder0802";
		By ELEMENT_CONTENT_FOLDER2 = By.linkText(DATA_CONTENT_FOLDER2);

		info("Add new content folder");
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER2, ELEMENT_CONTENT_FOLDER2);
		click(ELEMENT_CONTENT_FOLDER2);
		
		info("Add new file in the folder");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(WEB_NAME, "Web test", "","","","");
		
		cMenu.contextMenuAction(ELEMENT_CONTENT_FOLDER2,cMenu.ELEMENT_MENU_CHECKIN);
		
		click(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		
		click(ELEMENT_CONTENT_FOLDER2);
		click(WEB_BY);
		info("Add Symlink for node 02");
		actBar.addSymlink("collaboration", "sites/intranet/documents", WEB_NAME+".lnk");

		waitForAndGetElement(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", WEB_NAME+".lnk"));

		// delete data 
		cMenu.contextMenuAction(ELEMENT_CONTENT_FOLDER2,cMenu.ELEMENT_MENU_CHECKOUT);
		cMenu.deleteData(ELEMENT_CONTENT_FOLDER2);

	}


	/** CaseId: 102022
	 * case09: Add Symlink for node to the node is Symlink
	 * create 1 symlink at root
	 * create new node
	 * Check can not add symlink for this node with target is a symlink
	 */
	@Test
	public void test09_AddSymlinkForNodeToSymlinkNode(){
		String DATA_CONTENT_FOLDER = "ECMS_AddSymlink_Content_folder_09";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		String DATA_SYMLINK = "documents.lnk";
		By ELEMENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", DATA_SYMLINK));

		info("Add symlink for root");
		actBar.addSymlink("collaboration", "sites/intranet/documents", DATA_SYMLINK);
		waitForAndGetElement(ELEMENT_SYMLINK);

		info("Add new content folder");
		cTemplate.createNewFolder(DATA_CONTENT_FOLDER, folderType.Content);

		//check cannot add symlink for node with target node is symlink documents.lnk
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		actBar.goToTargetNodeWhenAddSymlink("sites");
		waitForElementNotPresent(By.xpath(ecms.ELEMENT_SYMLINK_PATH_NODE_TITLE.replace("${node}", DATA_SYMLINK)));
		info("cannot select target node is a symlink node");
		click(ecms.ELEMENT_ADD_SYMLINK_CLOSE_WINDOWS);

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
		cMenu.deleteDocument(ELEMENT_SYMLINK);
	}


	/** Case10: Add Symlink for node using drag and drop
	 * Test ID : 102023
	 * create new node - content folder
	 * create new document - file
	 * drag drop file to content folder
	 * check symlink of article in content folder
	 */
	@Test
	public void test10_AddSymlinkForNodeUsingDragDrop(){
		String folderName = "ECMS_AddSymlink_Content_folder_10";
		String fileName = "ECMS_AddSymlink_file_10";
		By ELEMENT_CONTENT_FOLDER = By.linkText(folderName);
		By ELEMENT_CONTENT_FILE = By.linkText(fileName);

		cTemplate.createNewFolder(folderName, folderType.Content);
		actBar.goToAddNewContent();
		cTemplate.createNewFile(fileName, fileName, fileName);
		ecms.goToNode(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);	


		Actions actions = new Actions(driver);
		actions.keyDown(Keys.CONTROL).keyDown(Keys.SHIFT).build().perform();
		dragAndDropToObject(ELEMENT_CONTENT_FILE, ELEMENT_CONTENT_FOLDER);
		Utils.pause(2000);
		actions.release();

		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		waitForAndGetElement(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", "ecmsaddsymlinkfile10" + ".lnk"));

		//delete data
		cMenu.deleteData(ELEMENT_CONTENT_FOLDER);
	}

	/**
	 * Test ID : 102024
	 * case11: Add Symlink for node when node is check in status
	 * create new content folder
	 * check in node
	 * check cannot add symlink for node
	 */
	@Test
	public void test11_AddSymlinkForCheckInNode(){
		String DATA_FILE = "ECMS_DMS_SE_Addsymlink_FILE_11";
		By ELEMENT_FILE = By.linkText(DATA_FILE);

		//create new file
		actBar.goToAddNewContent();

		info("Create new file document with user john");
		cTemplate.createNewFile(DATA_FILE, DATA_FILE, DATA_FILE);
		waitForAndGetElement(ELEMENT_FILE);
		info("Create new file document successfully");

		//check in node
		info("Check in node");
		cMenu.contextMenuAction(ELEMENT_FILE, cMenu.ELEMENT_MENU_CHECKIN);

		//check cannot add symlink
		ecms.goToNode(ELEMENT_FILE);
		waitForElementNotPresent(ecms.ELEMENT_ACTION_BAR_ADD_SYMLINK);
		if (waitForAndGetElement(ecms.ELEMENT_MORE_LINK_WITHOUT_BLOCK, 5000, 0) != null){
			click(ecms.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			waitForElementNotPresent(ecms.ELEMENT_ACTION_BAR_ADD_SYMLINK);
		}
		rightClickOnElement(ELEMENT_FILE);
		waitForElementNotPresent(cMenu.ELEMENT_MENU_ADD_SYMLINK);
		info("Cannot add symlink for checked in node");

		//delete data
		cMenu.contextMenuAction(ELEMENT_FILE, cMenu.ELEMENT_MENU_CHECKOUT);
		cMenu.deleteDocument(ELEMENT_FILE);
	}


	/** CaseId: 102025
	 * case12: Add Symlink for node when node is locked by user is not locker
	 * create new node with user John
	 * lock node by John
	 * login with mary
	 * check user mary can not add symlink for node
	 */
	@Test
	public void test12_AddSymlinkForNodeIsLockedByUserIsNotLocker(){
		String DATA_CONTENT_FOLDER = "contentfolder12";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER); 

		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		info("Lock node");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		cMenu.contextMenuAction(ELEMENT_CONTENT_FOLDER, cMenu.ELEMENT_CONTEXT_MENU_LOCK);
		assert cMenu.isLockedNode(ELEMENT_CONTENT_FOLDER): "Lock node unsuccessfully";
		

		//login with mary
		info("Init new session and Login with Mary");
		loginWithAnotherAccOnThesameBrowser(DATA_USER2, DATA_PASS);
		magAcc = new ManageAccount(newDriver);
		navToolBar = new NavigationToolbar(newDriver);
		ecms = new EcmsBase(newDriver);
		cMenu = new ContextMenu(newDriver);
		siteExp = new SitesExplorer(newDriver);

		//check cannot add symlink for node by user mary
		navToolBar.goToSiteExplorer();
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		magAcc.waitForElementNotPresent(ecms.ELEMENT_ACTION_BAR_ADD_SYMLINK); 

		magAcc.rightClickOnElement(ELEMENT_CONTENT_FOLDER);
		magAcc.waitForElementNotPresent(cMenu.ELEMENT_MENU_ADD_SYMLINK);
		info("cannot add symlink for node by user is not locker");
		magAcc.signOut();

		//delete data
		magAcc.signIn(DATA_USER1, DATA_PASS);
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);		
		
		newDriver.close();
	}

	/** CaseId: 102026
	 * case13: Add Symlink for node when user does not have permission to add in this node
	 * create new node - content folder
	 * set for user mary does not have add node permission
	 * login with mary
	 * check cannot add symlink for node
	 */
	@Test
	public void test13_AddSymlinkForNodeWhenUserNotHasAddNodePermission(){
		String DATA_CONTENT_FOLDER = "contentfolder13";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER); 

		//create new content folder
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		info("Set for user mary does not have add node permission");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		actBar.goToNodePermissionManagement();
		ecmsPer.removeDefaultPermissionOfNode();
		ecms.selectUser(DATA_USER2);
		ecmsPer.setPermissionForNode(true, false, false);

		button.save();
		magAcc.signOut();

		info("Login with mary");
		magAcc.signIn(DATA_USER2, DATA_PASS);
		navToolBar.goToSiteExplorer();
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);

		//check cannot add symlink for node
		waitForElementNotPresent(ecms.ELEMENT_ACTION_BAR_ADD_SYMLINK);
		if (waitForAndGetElement(ecms.ELEMENT_MORE_LINK_WITHOUT_BLOCK, 5000, 0) != null){
			click(ecms.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			waitForElementNotPresent(ecms.ELEMENT_ACTION_BAR_ADD_SYMLINK);
		}
		rightClickOnElement(ELEMENT_CONTENT_FOLDER);
		waitForElementNotPresent(cMenu.ELEMENT_MENU_ADD_SYMLINK);
		info("Cannot add symlink for node because user does not have add node permission");
		magAcc.signOut();

		//delete data
		magAcc.signIn(DATA_USER1, DATA_PASS);
		navToolBar.goToSiteExplorer();
		cMenu.deleteData(ELEMENT_CONTENT_FOLDER);
	}

	/** 
	 * Case Id : 102121
	 * case 14:Add Symlink for node which is nt:file when its parent node has "check-in" status
	 * 
	 */
	@Test
	public void test14_AddSymlinkForaNodeNotNtFileWhenItsParentNodeHAsCheckInStatus(){
		String WEB_NAME = "ecmstestwebcontent1001";
		By WEB_BY = By.linkText(WEB_NAME);

		String WEB_NAME2 = "ecmstestwebcontent1002";


		info("Add new web content");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(WEB_NAME, "Web test", "","","","");
		
		info("Add web content in the 1st web content created");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(WEB_NAME2, "Web test", "","","","");
		
		cMenu.contextMenuAction(WEB_BY,cMenu.ELEMENT_MENU_CHECKIN);
		click(WEB_BY);
		info("Check add a symlink is impossible");
		waitForElementNotPresent(actBar.ELEMENT_ACTION_BAR_ADD_SYMLINK);

		// delete data 
		cMenu.contextMenuAction(WEB_BY,cMenu.ELEMENT_MENU_CHECKOUT);
		cMenu.deleteData(WEB_BY);

	}
	
	/** CaseId: 102027
	 * case15: Add Symlink for root
	 * go to site explorer
	 * add symlink at root
	 */
	@Test
	public void test15_AddSymlinkForRoot(){	
		String DATA_SYMLINK = "documents.lnk";
		By ELEMENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", DATA_SYMLINK));

		info("Add symlink for root");
		actBar.addSymlink("collaboration", "sites/intranet/documents", DATA_SYMLINK);

		waitForAndGetElement(ELEMENT_SYMLINK);
		info("Add symlink successfully");

		//delete symlink
		cMenu.deleteData(ELEMENT_SYMLINK);
	}



	/**CaseId: 102028
	 * case16: Add Symlink for some nodes at the same time
	 * select some nodes
	 * add symlink for some node
	 * -- Error of selenium -- Impossible to select two elements in the right panel
	 */
	@Test(groups={"error"})
	public void test16_AddSymlinkForSomeNodesAtTheSameTime(){
		String folderName = "ECMS_AddSymlink_Content_folder_16";
		String fileName = "ECMS_AddSymlink_file_16";
		By elementFolder = By.xpath("//div/span[text()='" + folderName + "']");
		By elementFile = By.xpath("//div/span[text()='" + fileName + "']");

		cTemplate.createNewFolder(folderName, folderType.Content);
		actBar.goToAddNewContent();
		cTemplate.createNewFile(fileName, fileName, fileName);
		ecms.goToNode(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);


		WebElement folder = waitForAndGetElement(elementFolder);
		WebElement file = waitForAndGetElement(elementFile);

		Actions actions = new Actions(driver);
		actions.keyDown(folder, Keys.CONTROL).moveToElement(file).click().contextClick(file);
		Utils.pause(3000);
		actions.keyDown(folder, Keys.CONTROL).moveToElement(folder).click().contextClick(file);
		Utils.pause(3000);
		cMenu.contextMenuAction(folder, ecms.ELEMENT_CONTEXT_MENU_ADD_SYMLINK); 
		Utils.pause(3000);
		//	moveToElement(waitForAndGetElement(ecms.ELEMENT_CONTEXT_MENU_ADD_SYMLINK)).click().build().perform();

		waitForAndGetElement(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", folderName + ".lnk"));
		waitForAndGetElement(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", fileName + ".lnk"));

		//delete data
		cMenu.deleteData(elementFolder);
		cMenu.deleteData(elementFile);
	}

	/** CaseId: 102021
	 * case17: Add Symlink for node is Symlink
	 * create new node (content folder)
	 * add symlink for this node
	 * add symlink for symlink node
	 * check add successfully
	 */
	@Test
	public void test17_AddSymlinkForSymlinkNode(){
		String DATA_CONTENT_FOLDER = "ECMS_AddSymlink_Content_folder_17";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		String ELEMENT_SYMLINK = ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", DATA_CONTENT_FOLDER.replaceAll("_", "").toLowerCase() + ".lnk");
		String DATA_SYMLINK = "documents.lnk";

		cTemplate.createNewFolder(DATA_CONTENT_FOLDER, folderType.Content);

		info("Add symlink for node by right click");
		cMenu.contextMenuAction(ELEMENT_CONTENT_FOLDER, ecms.ELEMENT_CONTEXT_MENU_ADD_SYMLINK);
		waitForAndGetElement(ELEMENT_SYMLINK);

		//add symlink for symlink node
		info("Add symlink for exiting symlink");
		ecms.goToNode(By.xpath(ELEMENT_SYMLINK));
		actBar.addSymlink("collaboration", "sites/intranet/documents", DATA_SYMLINK);
		waitForAndGetElement(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", DATA_SYMLINK));
		info("Add symlink for symlink node successfully");

		//delete data
		cMenu.deleteData(ELEMENT_CONTENT_FOLDER);
	}

	/**
	 * Test ID : 102030
	 * case18: Add Symlink when 'Symlink Name' field contains special characters like @, #,$..
	 * 
	 */
	@Test
	public void test18_AddSymlinkWithNameContainsSpecialCharacter(){
		String DATA_CONTENT_FOLDER = "ECMS_AddSymlink_Content_folder_18";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		String CharSpec = null;
		info("create new content folder");
		cTemplate.createNewFolder(DATA_CONTENT_FOLDER, folderType.Content);

		info("Add symlink with name contains special characters");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		actBar.goToAddSymlinkTab();
		click(actBar.ELEMENT_ADD_ITEM);

		actBar.selectHomePathForCategoryTree("sites/intranet/documents");
		for(int i = 0; i < cTemplate.DATA_SPECIAL_CHARACTER.length; i++){
			CharSpec=CharSpec+cTemplate.DATA_SPECIAL_CHARACTER[i];
			info("Input symlink name contains character: " + cTemplate.DATA_SPECIAL_CHARACTER[i]);
		}
		type(ecms.ELEMENT_SYMLINK_NAME,CharSpec, true);
		button.save();

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
	}

	/** CaseId: 102031
	 * case19: Add Symlink when 'Symlink Name' field is blank
	 */
	@Test
	public void test19_AddSymlinkWithBlankName(){
		info("Add symlink with blank name");
		actBar.addSymlink("collaboration", "sites/intranet/documents", "");

		//check alert
		magAlert.verifyAlertMessage("The field \"Symlink Name\" is required.");
		button.cancel();
		info("cannot add symlink with blank name");
	}

	/** CaseId: 102029
	 * case20: Add Symlink when “Path Node” field is blank
	 */
	@Test
	public void test20_AddSymlinkWithBlankPathNode(){
		info("Add symlink with blank name");
		actBar.addSymlink("collaboration", "", "symlink.lnk");

		//check alert
		magAlert.verifyAlertMessage("Please enter the path node.");
		button.cancel();
		info("cannot add symlink with blank path node");
	}

	/** CaseId: 102017
	 * case21: Check the displaying of node when user dose not permission to view it when add Symlink 
	 * login with john
	 * create new node - content folder
	 * set permission for this node: user mary does not have permission view node
	 * login with mary
	 * add symlink and choose target node
	 * check user does not see that content node
	 */
	@Test
	public void test21_CheckDisplayNodeWhenUserNotHaveViewPermission(){	  
		String DATA_CONTENT_FOLDER = "ECMS_AddSymlink_Content_folder_21";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);

		//create new content folder
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);
		//set permission for this node: user mary does not have permission view node 
		info("Set for user mary does not have view permission");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		actBar.goToNodePermissionManagement();
		ecmsPer.removeDefaultPermissionOfNode();
		button.close();
		magAcc.signOut();
		info("Login ECMS with user: mary");
		magAcc.signIn(DATA_USER2, DATA_PASS);
		navToolBar.goToSiteExplorer();

		//check user does not see that content node
		actBar.goToTargetNodeWhenAddSymlink("sites");
		waitForElementNotPresent(By.xpath(ecms.ELEMENT_SYMLINK_PATH_NODE_TITLE.replace("${node}", DATA_CONTENT_FOLDER)));
		info("cannot see node when user does not have view permission");
		click(ecms.ELEMENT_ADD_SYMLINK_CLOSE_WINDOWS);

		//delete data
		magAcc.signOut();
		magAcc.signIn(DATA_USER1, DATA_PASS);
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
	}

	/** CaseId: 102018
	 * case22: Check the displaying of workspace when user dose not have permission to view it when add Symlink 
	 * login with mary
	 * create new node - content folder
	 * check cannot add symlink for this node with system workspace
	 */
	@Test
	public void test22_CheckDisplayWorkspaceWhenUserHasNotPermission(){
		String DATA_CONTENT_FOLDER = "ECMS_AddSymlink_Content_folder_22";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);

		//login with mary
		magAcc.signOut();
		info("Login ECMS with user: mary");
		magAcc.signIn(DATA_USER2, DATA_PASS);
		navToolBar.goToSiteExplorer();

		info("create new content folder");
		cTemplate.createNewFolder(DATA_CONTENT_FOLDER, folderType.Content);

		//check cannot add symlink for this node with system workspace
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		actBar.addSymlink("dms-system", "exo:ecm", "exo:ecm.lnk");

		//check alert
		boolean checkMessage = isTextPresent("Access denied! You do not have the permission to add symlink to this node.");
		if (checkMessage){
			magAlert.verifyAlertMessage("Access denied! You do not have the permission to add symlink to this node.");
		}else{
			magAlert.verifyAlertMessage("Access denied. You do not have permission to add symlink to this node.");
		}
		info("cannot add symlink with user has not permission to view it");
		button.cancel();

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
	}


}