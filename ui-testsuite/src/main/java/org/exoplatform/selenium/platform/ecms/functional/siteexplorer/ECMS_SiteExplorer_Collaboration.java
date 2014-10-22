package org.exoplatform.selenium.platform.ecms.functional.siteexplorer;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class ECMS_SiteExplorer_Collaboration extends PlatformBase {
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

		magAc.signIn(DATA_USER1, DATA_PASS);
		driver.navigate().refresh();
	}

	@AfterMethod
	public void afterTest(){
		info("ECMS_SE_Collaboration_Manage_Tag: Finish testing");
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	@Test
	/**
	 * == Add tag for document ==
	 * Test case ID: 101451
	 * Step 1: Create document
	 * Step 2: Tag document
	 */
	public void test01_Add1PublicTagForDocument(){
		String webContentTitle = "Add1PublicTagForDocument";
		String[] tagName = {"Public_Tag_1"};

		info("Go to Sites Explorer");
		navToolbar.goToSiteExplorer();

		info("Create a new document");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(webContentTitle, webContentTitle, "", "", "", "");

		info("Add a tag for this document: " + webContentTitle);
		siteExp.addTagForNode(tagName);

		info("User [mary] can see this tag");
		magAc.signOut();
		magAc.signIn(DATA_USER2, DATA_PASS);
		navToolbar.goToSiteExplorer();
		click(siteExp.ELEMENT_TAG_CLOUD);
		click(siteExp.ELEMENT_TAG_IN_CONTAINER_LIST.replace("${tagName}", tagName[0]));
		waitForAndGetElement(By.xpath("//*[text()='"+webContentTitle+"']"));
		info("Test successful");
		info("Reset data");
		magAc.signOut();

		magAc.signIn(DATA_USER1, DATA_PASS);
		navToolbar.goToSiteExplorer();
		siteExp.goToEditTag();
		siteExp.deleteTag(tagName);
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(By.linkText(webContentTitle));
		info("Test Add tag for document succeed");
	}

	@Test
	/**
	 * == Add tag for uploaded file ==
	 * Test case ID: 101452
	 * Step 1: Upload a file
	 * Step 2: Tag uploaded file
	 */
	public void test02_Add1PublicTagForUploadedFile(){
		String[] tagName = {"Public_Tag_2"};
		String fileToUpload = "ECMS_DMS_SE_Upload_docfile.doc";
		By elementFile = By.linkText(fileToUpload);

		info("Go to Sites Explorer");
		navToolbar.goToSiteExplorer();

		info("Upload a file");
		actBar.uploadFile("TestData/" + fileToUpload);
		ecms.goToNode(elementFile);

		info("Add a tag for this document: " + fileToUpload);
		siteExp.addTagForNode(tagName);
		info("Tag created");
		info("User [mary] can see this tag");
		magAc.signOut();
		magAc.signIn(DATA_USER2, DATA_PASS);
		navToolbar.goToSiteExplorer();
		click(siteExp.ELEMENT_TAG_CLOUD);
		waitForAndGetElement(siteExp.ELEMENT_TAG_IN_CONTAINER_LIST.replace("${tagName}", tagName[0]));
		info("Tag presence checked");
		info("Reset data");
		magAc.signOut();
		info("Test successful");
		magAc.signIn(DATA_USER1, DATA_PASS);
		navToolbar.goToSiteExplorer();
		siteExp.goToEditTag();
		siteExp.deleteTag(tagName);
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(elementFile);
		info("Data cleaned");
	}

	@Test 
	/**
	 * == Comment document ==
	 * Test case ID: 101427
	 * Step 1: Select node is document
	 * Step 2: Comment for selected document
	 * Step 3: Check showing comment(s)
	 * Step 4: Check hiding comments
	 */
	public void  test03_CommentDocument() {
		String fileName = "test03";
		String comment = "myComment";

		info("Go to Sites Explorer");
		//Pre-condition
		navToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		cTemplate.createNewFile(fileName, comment, comment);
		info("data created");
		actBar.addComment(comment);
		waitForAndGetElement(By.xpath("//*[@onclick='eXo.ecm.WCMUtils.showHideComponent(this)']"));
		info("Hide/show comment checked");
		info("Test successful");
		cMenu.deleteDocument(By.linkText(fileName));
		info("Data cleaned");
	}

	@Test 
	/**
	 * == Comment folder ==
	 * Test case ID: 101435
	 * Step 1: Select node is folder  
	 * Step 2: Comment for folder
	 */
	public void  test04_CommentFolder() {
		String folderName = "test04";

		info("Go to Sites Explorer");
		//Pre-condition
		navToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		cTemplate.createNewFolder(folderName, folderType.Content);
		info("data created");
		click(By.linkText(folderName));
		click(ecms.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		waitForElementNotPresent(ecms.ELEMENT_ADD_COMMENT_LINK);
		info("Impossible to comment folder checked");
		info("Test successful");
		cMenu.deleteDocument(By.linkText(folderName));
		info("Data cleaned");
	}

	@Test 
	/**
	 * == Delete tag when cancel confirm message ==
	 * Test case ID: 101423
	 * Step 1: Create a node
	 * Step 2: Add tag for document
	 * Step 3: Open Edit tag form
	 * Step 4: Delete tag
	 */
	public void  test05_DeleteTagWhenCancelConfirmMessage() {
		String fileName = "test05";
		String comment = "myComment";
		String []tag = {"myTag"};

		info("Go to Sites Explorer");
		//Pre-condition
		navToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		cTemplate.createNewFile(fileName, comment, comment);
		info("data created");
		
		siteExp.addTagForNode(tag);
		siteExp.goToEditTag();
		info("Tag created");
		for(int i = 0; i < tag.length ; i++){
			info("-- Deleting the tag: " + tag[i]);
			By ELEMENT_REMOVE_TAG = By.xpath(ecms.REMOVE_TAG.replace("${TagsName}", tag[i]));
			if (isElementPresent(ELEMENT_REMOVE_TAG)){
				click(ELEMENT_REMOVE_TAG);
			}else if (isElementPresent(By.xpath("//*[text()='Edit Tag']/../..//*[text()='"+ tag[i] +"']/../..//*[@data-original-title='Remove Tag']"))){
				click(By.xpath("//*[text()='Edit Tag']/../..//*[text()='"+ tag[i] +"']/../..//*[@data-original-title='Remove Tag']"));
			}
			//magAlert.waitForConfirmation(MESSAGE_WARNING_AFTER_DELETE_TAG);
			magAlert.cancelAlert();
			info("cancel on confirm remove window");
		}
		click(button.ELEMENT_CLOSE_WINDOW);
		waitForAndGetElement(By.xpath("//*[text()='"+tag[0]+"']"));
		click(By.xpath("//*[@data-original-title = 'File Explorer']"));
		info("Document still present checked");
		waitForAndGetElement(siteExp.ELEMENT_TAG_CLOUD);
		click(siteExp.ELEMENT_TAG_CLOUD);
		waitForAndGetElement(By.xpath("//*[@class='tagContainer']"));
		info("Test successful");
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(By.linkText(fileName));
		info("Data cleaned");
	}

	@Test 
	/**
	 * == Rename tag ==
	 * Test case ID: 101415
	 * Step 1: Create a node
	 * Step 2: Add tag for document
	 * Step 3: Open form to edit tags
	 * Step 4: Open form to edit tag
	 * Step 5: Rename tag
	 * Step 6: Check showing document after rename tag
	 */
	public void  test06_RenameTag() {
		String fileName = "test06";
		String comment = "myComment";
		String []tag = {"myTag"};
		String tag2 = "myTag2";

		info("Go to Sites Explorer");
		//Pre-condition
		navToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		cTemplate.createNewFile(fileName, comment, comment);
		info("data created");
		
		siteExp.addTagForNode(tag);
		siteExp.goToEditTag();
		siteExp.editTag(tag[0], tag2);
		click(button.ELEMENT_CLOSE_WINDOW);
		click(By.xpath("//*[text()='myTag2']"));
		waitForAndGetElement(By.xpath("//*[@class='nodeName' and text()='"+fileName+"']"));
		info("Link to the file checked");
		info("Test successful");
		click(By.xpath("//*[@data-original-title = 'File Explorer']"));
		cMenu.deleteDocument(By.linkText(fileName));
		info("Data cleaned");	
	}

	@Test 
	/**
	 * == Vote a folder ==
	 * Test case ID: 101453
	 * Step 1: Create a node
	 * Step 2: Vote folder
	 */
	public void  test07_VoteAFolder() {
		String folderName = "test07";

		info("Go to Sites Explorer");
		//Pre-condition
		navToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		cTemplate.createNewFolder(folderName, folderType.Content);
		info("data created");
		click(By.linkText("test07"));
		click(ecms.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		waitForElementNotPresent(ecms.ELEMENT_VOTE_LINK);
		info("Impossible to vote a folder checked");
		info("Test successful");
		cMenu.deleteDocument(By.linkText(folderName));
		info("Data cleaned");
	}

	@Test 
	/**
	 * == Watch document ==
	 * Test case ID: 101404
	 * Step 1: Select document
	 * Step 2: Open 'Watching document' pop-up
	 * Step 3: Do watch FNCtion
	 */
	public void  test08_WatchDocument() {
		String fileName = "test08";
		String comment = "myComment";
		
		info("Go to Sites Explorer");
		//Pre-condition
		navToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		cTemplate.createNewFile(fileName, comment, comment);
		info("data created");
		
		click(ecms.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		click(By.xpath(siteExp.ELEMENT_BUTTON_WATCH_UNWATCH));
		click(By.xpath(siteExp.ELEMENT_BUTTON_WATCH_EMAIL));
		click(By.xpath(siteExp.ELEMENT_BUTTON_WATCH_WATCH));
		waitForAndGetElement(By.xpath("//*[@id='wcm-notice' and text()='You are watching this document.']"));
		info("Watching document checked");
		info("Test successful");
		cMenu.deleteDocument(By.linkText(fileName));
		info("Data cleaned");
	}

	@Test 
	/**
	 * == Watch folder ==
	 * Test case ID: 101405
	 * Step 1: Select folder
	 * Step 2: Watch folder
	 */
	public void  test09_WatchFolder() {
		String folderName = "test09";

		info("Go to Sites Explorer");
		//Pre-condition
		navToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		cTemplate.createNewFolder(folderName, folderType.Content);
		info("data created");
		click(By.linkText("test09"));
		click(ecms.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		waitForElementNotPresent(By.xpath(siteExp.ELEMENT_BUTTON_WATCH_UNWATCH));
		info("Impossible to watch folder checked");
		info("Test successful");
		cMenu.deleteDocument(By.linkText(folderName));
		info("Data cleaned");
	}
}
