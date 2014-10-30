package org.exoplatform.selenium.platform.plf.functional.unifiedsearch;

import static org.exoplatform.selenium.TestLogger.info;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.EcmsPermission;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.SettingSearchPage;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;

/**
 * 
 * @date 28/08/2014
 * 
 */

public class PLF_UnifiedSearch_DocumentSearch extends PlatformBase {

	// General
	Button button;

	// Platform
	NavigationToolbar naviToolbar;
	ActionBar actBar;
	ContentTemplate conTemp;
	SitesExplorer siteExp;
	EcmsBase ecms;
	EcmsPermission ecmsPer;
	ContextMenu cMenu;
	PageManagement pageMag;
	ManageAccount magAcc;
	ManageAlert alert;

	SettingSearchPage qsPage;
	String documentNameDes = "";
	
	/**
	 * this void will call the browser and set up all classes before starting
	 * all test cases
	 * 
	 * @author quynhpt
	 * @throws Exception
	 */

	@BeforeTest
	public void setBeforeTest() throws Exception {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER1);

		magAcc = new ManageAccount(driver, this.plfVersion);
		naviToolbar = new NavigationToolbar(driver, this.plfVersion);
		qsPage = new SettingSearchPage(driver);
		pageMag = new PageManagement(driver, this.plfVersion);
		ecms = new EcmsBase(driver, this.plfVersion);
		actBar = new ActionBar(driver, this.plfVersion);
		cMenu = new ContextMenu(driver, this.plfVersion);
		conTemp = new ContentTemplate(driver, this.plfVersion);
		siteExp = new SitesExplorer(driver, this.plfVersion);
		alert = new ManageAlert(driver, this.plfVersion);
		ecmsPer = new EcmsPermission(driver);
		button = new Button(driver, this.plfVersion);
		magAcc.signIn(DATA_USER1, DATA_PASS);

	}

	/**
	 * this void will close the browser after finished all test cases
	 * 
	 * @throws Exception
	 */

	@AfterTest
	public void setAfterTest() throws Exception {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * TC_104227:Display a Document in the Floating Result
	 * Preconditions:
	 * a Document exist in the Content explorer
	 * Steps:
	 * 1. Connect to Site
	 * 2. In the Quick Search box, input a valid characters to search a Document
	 * (Test)
	 * Expected:
	 * 1. The Document "document" is displayed in the Floating Result:
	 * The Document title
	 * The document type icon such as displayed in the content explorer
	 * 
	 */

	@Test(enabled = true)
	// pass
	public void test01_DisplayDocInFloatingResult() {

		// Create data test
		String prefix_number = getRandomNumber();
		documentNameDes = "document" + " " + prefix_number;

		// Add new document(ex. webcontent)
		info("Add new webcontent");
		naviToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewWebContent(documentNameDes, documentNameDes,"","","","");

		/* Step 2: Quick search */
		// - Type some text into search box
		qsPage.quickSearchType(documentNameDes);

		// - Verify expected outcome: The Document "Test" is displayed in the
		// Floating Result:
		// The Document title, the document type icon such as displayed in the
		// content explorer
		info("-- Verify the document title --");
		assert waitForAndGetElement(
				qsPage.ELEMENT_RESULT_FLOATING_RESULTS_DOCUMENT_TITLE.replace(
						"${name_document}", documentNameDes)).getText()
				.contains(documentNameDes);

		info("-- Verify the document type icon --");
		waitForAndGetElement(ELEMENT_RESULT_FLOATING_RESULTS_DOCUMENT_ICON
				.replace("${name_document}", documentNameDes));

		info("-- Clear data --");
		// Delete file
		naviToolbar.goToSiteExplorer();
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}",
				documentNameDes));

	}

	/**
	 * TC_104228 and TC_TC_104229:Display a Doucment in the Search Result page
	 * Preconditions:
	 * a Document exist in the Content explorer
	 * Steps:
	 * 1. Connect to Site. In the Quick Search box, input a valid characters to
	 * search a Document (Test)
	 * 2. Click on "See All Search Results"
	 * Expected:
	 * 1. - The Doucment "document" is displayed in the Floating Result.The action
	 * "See All Search Results" is displayed
	 * 2.The search page is displayed. The File is displayed in the list of
	 * Results:
	 * 2.a.The document type icon
	 * 2.b.The document title.
	 * 2.c.The excerpt
	 * 2.d.The location
	 * 2.e.The last modified date
	 */
	@Test
	public void test02_DisplayDocInSearPageResult() {

		// Create data test
		documentNameDes = "prefix_number";//"document" + " " + prefix_number;

		// Add new document(ex. webcontent)
		info("Add new webcontent");
		naviToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewWebContent(documentNameDes, documentNameDes,"","","","");

		/* Step 2: Quick search */
		// - Type some text into search box
		click(By.xpath("//*[@class='uiIconPLF24x24Search']"));
		type(ELEMENT_QUICK_SEARCH_TEXTBOX, "prefix_", true);
		Utils.pause(2000);
		type(ELEMENT_QUICK_SEARCH_TEXTBOX, Keys.SPACE.toString(), false);
		Utils.pause(2000);
		type(ELEMENT_QUICK_SEARCH_TEXTBOX, Keys.BACK_SPACE.toString(), false);
		Utils.pause(2000);
		type(ELEMENT_QUICK_SEARCH_TEXTBOX, "number", false);
		Utils.pause(2000);
		// - Click on "See all search results" link
		click(ELEMENT_QUICK_SEARCH_SEE_ALL_SEARCH_RESULTS);
		// - Uncheck "All content types" checkbox
		uncheck(qsPage.ELEMENT_FILTER_SEARCH_ALL_CONTENTTYE_CHECKBOX, 2);
		// - Check "Document" checkbox
		check(qsPage.ELEMENT_FILTER_SEARCH_DOCUMENT_CHECKBOX, 2);

		// - Verify results should display: The File is displayed in the list
		// ofResults
		// The document type icon, title, excerpt,location,last modified date
		info("-- Verify the document icon --");
		waitForAndGetElement(By.xpath("//div[contains(@class,'resultBox')]/..//*[@class='uiIcon64x64TemplateFolderDefault uiIcon64x64Templateexo_webContent']"));

		info("-- Verify the document title --");
		waitForAndGetElement(By.xpath((qsPage.ELEMENT_RESULT_TITLE_41).replace("${name}", "prefix")));

		info("-- Verify the document name on excerpt and location --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_SEARCH_PAGE_EXCERPT_41.replace("${name_document}", "prefix")).getText().contains("Managed Sites");

		info("-- Verify the document date --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_SEARCH_PAGE_EXCERPT_41.replace("${name_document}", "prefix")).getText().contains(getCurrentDate("EEEEE, MMMMM d, yyyy"));

		// - Items in search result is clickable and open it when user click
		waitForAndGetElement(qsPage.ELEMENT_RESULT_SEARCH_PAGE_TITLE_41.replace("${name_document}", "prefix")).click();

		waitForAndGetElement(siteExp.ELEMENT_SE_NODE.replace("{$node}","prefix_number"));
		
		info("-- Clear data --");
		// Delete file
		naviToolbar.goToSiteExplorer();
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}",
				documentNameDes));

	}

	/**
	 * TC_104230:Not Display a Document as result when node types is not
	 * "Document Type"
	 * Preconditions:
	 * a document exist in the Content explorer
	 * add an action 'document" under document
	 * Steps:
	 * 1. Connect to a Site
	 * 2. From Administrator, Import Quick Search portlet
	 * 3. Check on "Edit" button of Quick Seach portlet
	 * 4. Uncheck "Documents" type on Edit mode tab
	 * 5. Save all changes
	 * 6. In the Quick Search box, input a valid characters to search a Document
	 * (Test)
	 * Expected:
	 * The Document"document" is not displayed in the Floating Result
	 * 
	 */
	@Test(enabled = true)
	public void test09_NotDisDocFromFloatSearResultIfNotDocTyp() {
		/* Declare variables */
		String qsGadget = "Quick Search";
		String pageName = "QuickSearch70831";
		String tempUrl = "";

		/* Create data */
		info("-- Create data --");
		// There is a page containing a Quick search portlet
		qsPage.addQuickSearchPage(pageName, qsGadget);
		tempUrl = driver.getCurrentUrl();

		/* Step 1: Configure Quick search */
		// - Login as admin, go to intranet home page
		// - Open Quick search page
		// - Click Edit this page
		// - Click Edit portlet "Quick search"
		qsPage.goToEditSearchPortlet();
		// - Quick search settings screen is shown.

		// - Set value to fields
		// - Click Save settings,
		// - value is save
		qsPage.editQuickSearchSettingEditMode("10", true, true, true,
				false, true);

		// Create data test
		String prefix_number = getRandomNumber();
		documentNameDes = "document" + " " + prefix_number;

		// Add new document(ex. webcontent)
		info("Add new webcontent");
		naviToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewWebContent(documentNameDes, documentNameDes,"","","","");
		naviToolbar.goToHomePage();
		// - Type some text into search box, Click on Search
		qsPage.quickSearchType(documentNameDes);

		info("-- Verify not display the document into floating results search --");
		waitForElementNotPresent(qsPage.ELEMENT_RESULT_FLOATING_RESULTS_DOCUMENT_TITLE
				.replace("${name_document}", documentNameDes));

		info("-- Clear data --");
		driver.get(tempUrl);
		qsPage.goToEditSearchPortlet();
		qsPage.editQuickSearchSettingEditMode("10", true, true, true,
				true, true);
		pageMag.deletePageAtManagePageAndPortalNavigation(pageName, true,
				"intranet", false, "Administration", pageName);
		naviToolbar.goToSiteExplorer();
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}",
				documentNameDes));
	}

	/**
	 * TC_104231:Display Documents in the Floating Result by pertinence
	 * Precontidions:
	 * 2 Documents containing the word: "documentS":
	 * Doc 1: (document) in Title
	 * Doc 2: (document) in Summary or description
	 * Steps:
	 * 1. Connect to Intranet
	 * 2. In the Quick Search box, input a valid characters to search a document
	 * (Test)
	 * Expected:
	 * The documents are displayed in the Floating Result in the following
	 * order: Doc 1, Doc 2
	 * 
	 */

	@Test (groups = "error")
	public void test03_DisplayDocInFloatResultByPertinence() {
		// Create Data test
		String prefix_number = getRandomNumber();
		documentNameDes = "document" + prefix_number;
		String contentDoc = "test pertinence" + prefix_number;

		// Add new first document(ex. webcontent)with "document" word in the
		// title
		info("Add new webcontent");
		naviToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewWebContent(documentNameDes, contentDoc,"","","","");
		Utils.pause(3000);
		// Add new second document (ex. webcontent) with "document" word in the
		// content of the file
		naviToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		ecms.goToNode(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		actBar.goToAddNewContent();
		conTemp.createNewWebContent(contentDoc, documentNameDes,"","","","");
		Utils.pause(3000);

		// Type some text into search box
		click(By.xpath("//*[@class='uiIconPLF24x24Search']"));
		type(ELEMENT_QUICK_SEARCH_TEXTBOX, "document", true);
		Utils.pause(2000);
		type(ELEMENT_QUICK_SEARCH_TEXTBOX, Keys.SPACE.toString(), false);
		Utils.pause(2000);
		type(ELEMENT_QUICK_SEARCH_TEXTBOX, Keys.BACK_SPACE.toString(), false);
		Utils.pause(2000);
		type(ELEMENT_QUICK_SEARCH_TEXTBOX, prefix_number, false);
		Utils.pause(2000);
		// - Click on "See all search results" link
		click(ELEMENT_QUICK_SEARCH_SEE_ALL_SEARCH_RESULTS);
		// - Uncheck "All content types" checkbox
		uncheck(qsPage.ELEMENT_FILTER_SEARCH_ALL_CONTENTTYE_CHECKBOX, 2);
		// - Check "Document" checkbox
		check(qsPage.ELEMENT_FILTER_SEARCH_DOCUMENT_CHECKBOX, 2);
		
		// - Verify expected outcome: The documents are displayed in the
		// Floating Result in the following
		// order: Doc 1, Doc 2
		info("-- Verify the order of first document --");
		waitForAndGetElement(qsPage.ELEMENT_QUICKSEARCH_RESULT.replace("${number}", "0").replace("${name}", documentNameDes));

		info("-- Verify the order of second document--");
		waitForAndGetElement(qsPage.ELEMENT_QUICKSEARCH_RESULT.replace("${number}", "1").replace("${name}", "test pertinence"));

		info("-- Clear data --");
		// Delete file
		naviToolbar.goToSiteExplorer();
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}",
				documentNameDes));
		naviToolbar.goToSiteExplorer();
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}",
				contentDoc));

	}

	/**
	 * TC_104232:Not Display a Document as result whithout read permission
	 * Preconditions:
	 * a document exist in the Content explorer
	 * the document is without read permission (defined in View Permissions
	 * screen of Content Explorer)
	 * Steps:
	 * 1. Connect to a Site
	 * 2. Input a valid characters in the quich search box to have the document
	 * 'document' as result
	 * Expected:
	 * The Document"document" is not displayed in the Floating Result
	 */
	@Test
	public void test04_NoDisDocFromFloatSearResultWithoutReadPermission() {
		// Create data test
		String prefix_number = getRandomNumber();
		documentNameDes = "document" + " " + prefix_number;

		// Add new document(ex. webcontent)
		info("Add new webcontent");
		naviToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewWebContent(documentNameDes, documentNameDes,"","","","");

		// Open Permission Management popup
		actBar.goToNodePermissionManagement();

		// Remove all members of the group webcontributors of the document
		ecmsPer.deletePermission("*:/platform/web-contributors", true);
		


		// Remove any members of the document
		ecmsPer.deletePermission("any", true);

		button.close();

		info("-- logout admin user and login a member of web-contribuldor group --");
		magAcc.signOut();
		// -sign in with a user of web-contribuldor: here is "mary" user
		magAcc.signIn(DATA_USER2, DATA_PASS);

		// - Type some text into search box
		qsPage.quickSearchType(prefix_number);

		info("-- Verify not displaying the document into floading results --");
		waitForElementNotPresent(qsPage.ELEMENT_RESULT_FLOATING_RESULTS_DOCUMENT_TITLE
				.replace("${name_document}", documentNameDes));
		waitForElementNotPresent(By.linkText(documentNameDes));

		info("--log in with admin account: here is John user");
		magAcc.signOut();
		// -sign in with admin user: here is "John" user
		magAcc.signIn(DATA_USER1, DATA_PASS);

		info("-- Clear data --");
		// Delete file
		naviToolbar.goToSiteExplorer();
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}",
				documentNameDes));

	}

	/**
	 * TC_104233:Open a Document's View mode from the Search Results
	 * Preconditions:
	 * a Document exist in the Content explorer
	 * Steps:
	 * 1. Connect to Site. In the Quick Search box, input a valid characters to
	 * search a Document (document)
	 * 2. Click on the Document's name
	 * Expected:
	 * 1.The Doucment "document" is displayed in the Floating Result
	 * 2.The Document is opened in the view mode in the matched drive in the
	 * content explorer
	 */
	@Test(enabled = true)
	public void test05_OpenDocFromFloatSearResult() throws Exception {
		// Create data test
		String prefix_number = getRandomNumber();
		documentNameDes = "document" + " " + prefix_number;

		// Add new document(ex. webcontent)
		info("Add new webcontent");
		naviToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewWebContent(documentNameDes, documentNameDes,"","","","");

		// - Type some text into search box, Click on Search
		qsPage.quickSearchType(prefix_number);
		// - Click on "See all search results" link

		click(qsPage.ELEMENT_RESULT_FLOATING_RESULTS_DOCUMENT_TITLE.replace(
				"${name_document}", documentNameDes));

		info("-- Verify the document in the view mode of the matched drive --");
		assert waitForAndGetElement(
				siteExp.ELEMENT_NODE_TREE_EXPLORER_NAME.replace(
						"${name_document}", documentNameDes)).getText()
				.contains(documentNameDes);

		info("-- Clear data --");
		// Delete file
		naviToolbar.goToSiteExplorer();
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}",
				documentNameDes));
	}

	/**
	 * TC_104234:Display a published Document Results for WCM in public mode
	 * Preconditions:
	 * a Document containing a same word with published and not published status
	 * exist in the Content explorer
	 * - The user is not a member in /platform/web-contributors
	 * Steps:
	 * 1.Connect to Site.Open the Search page
	 * 2. Input characters in the search box to display documents
	 * Expected:
	 * 1. The search page is displayed in public mode
	 * 2. Only published documents are returned as search results.
	 */

	// This test case need to confirm about displaying published document
	@Test (groups= "error")
	public void test06_DisPubDocInNotMemberWCM() {
		String prefix_number = "104234";
		documentNameDes = "document" + prefix_number;
		String documentNameDesNotPublished = "document"
				+ prefix_number + "not";

		// Add new documentthat has published(ex. webcontent)
		info("Add new webcontent");
		naviToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewWebContent(documentNameDes, documentNameDes, "", "",
				"", "");
		actBar.publishDocument();

		// Add new document that has not published(ex. webcontent)
		info("Add new webcontent");
		ecms.goToNode(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewWebContent(documentNameDesNotPublished,
				documentNameDesNotPublished, "", "", "", "");

		// Open Permission Management popup
		actBar.goToNodePermissionManagement();

		// Remove any members that is viewed the document in permission management table
		ecmsPer.deletePermission("any", true);

		button.close();

		info("-- logout admin user and login with a normal user --");
		magAcc.signOut();
		// -sign in with a user of web-contribuldor: here is "demo" user
		magAcc.signIn(DATA_USER4, DATA_PASS);

		// - Type some text into search box
		click(By.xpath("//*[@class='uiIconPLF24x24Search']"));
		type(ELEMENT_QUICK_SEARCH_TEXTBOX, documentNameDes, true);
		Utils.pause(2000);
		type(ELEMENT_QUICK_SEARCH_TEXTBOX, Keys.SPACE.toString(), false);
		Utils.pause(2000);
		type(ELEMENT_QUICK_SEARCH_TEXTBOX, Keys.BACK_SPACE.toString(), false);
		Utils.pause(2000);

		// - Click on "See all search results" link
		click(ELEMENT_QUICK_SEARCH_SEE_ALL_SEARCH_RESULTS);
		// - Uncheck "All content types" checkbox
		uncheck(qsPage.ELEMENT_FILTER_SEARCH_ALL_CONTENTTYE_CHECKBOX, 2);
		// - Check "Document" checkbox
		check(qsPage.ELEMENT_FILTER_SEARCH_DOCUMENT_CHECKBOX, 2);

		info("-- Verify the document in Search results page --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_SEARCH_PAGE_TITLE_41.replace("${name_document}", "document"+prefix_number));
		
		info("-- logout normal user and login with admin user --");
		magAcc.signOut();
		// -sign in with a user of web-contribuldor: here is "demo" user
		magAcc.signIn(DATA_USER1, DATA_PASS);

		info("-- Clear data --");
		// Delete file
		naviToolbar.goToSiteExplorer();
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}",
				documentNameDes));
		naviToolbar.goToSiteExplorer();
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}",
				documentNameDesNotPublished));

	}

	/**
	 * TC_104235:Display all Documents Results for WCM in edit mode
	 * Preconditions:
	 * a Document containing a same word with published and not published status
	 * exist in the Content explorer
	 * Steps:
	 * 1. Connect to a Site with a user member in the group
	 * "/platform/web-contributors".Open the Search page
	 * 2. From Edit menu, check the label "Content"
	 * 3. Input characters in the search box to display documents
	 * Expected:
	 * 1. The Search page is opened in the public mode
	 * 2. The search page is displayed in Edit mode
	 * 3. Documents having any publication status are returned as search results
	 */
	@Test(groups = "error")
	public void test07_DisAllDocWithMemberWCM() {
		String prefix_number = getRandomNumber();
		documentNameDes = "document" + prefix_number;
		String documentNameDesNotPublished = "document"
				+ prefix_number +"not";

		// Add new documentthat has published(ex. webcontent)
		info("Add new webcontent");
		naviToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewWebContent(documentNameDes, documentNameDes, "", "",
				"", "");
		actBar.publishDocument();

		// Add new document that has not published(ex. webcontent)
		info("Add new webcontent");
		ecms.goToNode(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewWebContent(documentNameDesNotPublished,
				documentNameDesNotPublished, "", "", "", "");

		info("-- logout admin user and login with a user of web-contribuld group --");
		magAcc.signOut();
		// -sign in with a user of web-contribuldor: here is "mary" user
		magAcc.signIn(DATA_USER2, DATA_PASS);

		// - Type some text into search box
		qsPage.quickSearchType(documentNameDes);

		// - Click on "See all search results" link
		click(ELEMENT_QUICK_SEARCH_SEE_ALL_SEARCH_RESULTS);
		// - Uncheck "All content types" checkbox
		uncheck(qsPage.ELEMENT_FILTER_SEARCH_ALL_CONTENTTYE_CHECKBOX, 2);
		// - Check "Document" checkbox
		check(qsPage.ELEMENT_FILTER_SEARCH_DOCUMENT_CHECKBOX, 2);

		info("-- Verify the document in Search results page --");
		assert waitForAndGetElement(
				qsPage.ELEMENT_RESULT_SEARCH_PAGE_TITLE.replace(
						"${name_document}", documentNameDes)).getText()
				.contains(documentNameDes);

		assert waitForAndGetElement(
				qsPage.ELEMENT_RESULT_SEARCH_PAGE_TITLE.replace(
						"${name_document}", documentNameDesNotPublished))
				.getText().contains(documentNameDesNotPublished);

		info("-- logout the user and login with admin account --");
		magAcc.signOut();
		// -sign in with a user of web-contribuldor: here is "John" user
		magAcc.signIn(DATA_USER1, DATA_PASS);

		info("-- Clear data --");
		// Delete file
		naviToolbar.goToSiteExplorer();
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}",
				documentNameDes));
		naviToolbar.goToSiteExplorer();
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}",
				documentNameDesNotPublished));

	}

	/**
	 * TC_104236:Display Document Results in public mode where user is a
	 * web-contributor
	 * Preconditions:
	 * a Document containing a same word with published and not published status
	 * exist in the Content explorer
	 * - The user is a member in /platform/web-contributors
	 * Steps:
	 * 1. Connect to Site. Open the Search page
	 * 2. Input characters in the search box to display documents
	 * Expected:
	 * 1. The search page is displayed in public mode
	 * 2. Both published and draft documents are returned in search results".
	 * 
	 */
	@Test(enabled = true)
	public void test08_DisAllDocAndDrafWithMemberWCM() {
		String prefix_number = getRandomNumber();
		documentNameDes = "document" + prefix_number;
		String documentNameDesNotPublished = "document"
				+ prefix_number+"pub";

		// Add new documentthat has published(ex. webcontent)
		info("Add new webcontent");
		naviToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewWebContent(documentNameDes, documentNameDes, "", "",
				"", "");
		actBar.publishDocument();

		// Add new document that has not published(ex. webcontent)
		info("Add new webcontent");
		ecms.goToNode(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewWebContent(documentNameDesNotPublished,
				documentNameDesNotPublished, "", "", "", "");

		info("-- logout admin user and login with a user of web-contribuld group --");
		magAcc.signOut();
		// -sign in with a user of web-contribuldor: here is "mary" user
		magAcc.signIn(DATA_USER2, DATA_PASS);

		// - Type some text into search box
		qsPage.quickSearchType(documentNameDes);

		// - Click on "See all search results" link
		click(ELEMENT_QUICK_SEARCH_SEE_ALL_SEARCH_RESULTS);
		// - Uncheck "All content types" checkbox
		uncheck(qsPage.ELEMENT_FILTER_SEARCH_ALL_CONTENTTYE_CHECKBOX, 2);
		// - Check "Document" checkbox
		check(qsPage.ELEMENT_FILTER_SEARCH_DOCUMENT_CHECKBOX, 2);

		info("-- Verify the document in Search results page --");
		assert waitForAndGetElement(
				qsPage.ELEMENT_RESULT_SEARCH_PAGE_TITLE.replace(
						"${name_document}", documentNameDes)).getText()
				.contains(documentNameDes);

		assert waitForAndGetElement(
				qsPage.ELEMENT_RESULT_SEARCH_PAGE_TITLE.replace(
						"${name_document}", documentNameDesNotPublished))
				.getText().contains(documentNameDesNotPublished);

		info("-- logout the user and login with admin account --");
		magAcc.signOut();
		// -sign in with a user of web-contribuldor: here is "John" user
		magAcc.signIn(DATA_USER1, DATA_PASS);

		info("-- Clear data --");
		// Delete file
		naviToolbar.goToSiteExplorer();
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}",
				documentNameDes));
		naviToolbar.goToSiteExplorer();
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}",
				documentNameDesNotPublished));

	}

}
