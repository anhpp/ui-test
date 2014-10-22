package org.exoplatform.selenium.platform.ecms.functional.siteexplorer;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.TestBase;
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
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ECMS_SiteExplorer_Publication extends PlatformBase {
	NavigationToolbar naviToolbar;
	ManageAccount magAcc;
	ActionBar actBar;
	ContentTemplate conTemp;
	ContextMenu cMenu;
	SitesExplorer siteExp;
	EcmsPermission ePerm;
	TestBase testBase;
	EcmsBase ecms;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER1);
		magAcc = new ManageAccount(driver, this.plfVersion);
		naviToolbar = new NavigationToolbar(driver, this.plfVersion);
		actBar = new ActionBar(driver, this.plfVersion);
		conTemp = new ContentTemplate(driver, this.plfVersion);
		cMenu = new ContextMenu(driver, this.plfVersion);
		siteExp = new SitesExplorer(driver, this.plfVersion);
		button = new Button(driver, this.plfVersion);
		ePerm = new EcmsPermission(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);
		testBase = new TestBase();
		ecms = new EcmsBase(driver, this.plfVersion);
	}

	@AfterMethod
	public void afterTest(){
		magAcc.signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	@Test
	/**
	 * == Add Approve status from Draft status ==
	 * Test case ID: 101276
	 * Step 1: Show site explorer by legal user
	 * Step 2: Create new node with draft status
	 * Step 3: Show Manage Publication form of a draft node
	 * Step 4: Change status
	 */
	public void test01_AddApproveStatusFromDraftStatus(){

		String doc_Name = "doc_test_67519";
		String doc_Content = "Content of File: Add Approve status from Draft status";
		By file_locator = By.linkText(doc_Name);

		info("Add Approve status from Draft status");
		// Add a content
		actBar.goToAddNewContent();
		conTemp.createNewFile(doc_Name, doc_Content, null);
		info("Data created");
		// Show Publication Form and change status
		actBar.managePublication("Approved");
		info("Test successful");
		// Remove test data
		cMenu.deleteDocument(file_locator);
		
	}

	@Test
	/**
	 * == Add pending revision from draft revision ==
	 * Test case ID: 101273
	 * Step 1: Show site explorer
	 * Step 2: Create new node with draft status
	 * Step 3: Show Manage Publication form of a draft node
	 * Step 4: Change status
	 */
	public void test02_AddPendingRevisionFromDraftRevision(){

		String doc_Name = "doc_test_67190";
		String doc_Content = "Content of File: Add Pending revision from Draft revision";
		By file_locator = By.linkText(doc_Name);

		info("Add Pending revision from Draft revision");
		// Add a content
		actBar.goToAddNewContent();
		conTemp.createNewFile(doc_Name, doc_Content, null);
		info("Data created");
		// Show Publication Form and change status
		actBar.managePublication("Pending");
		info("Status pending check");
		info("Test successful");
		// Remove test data
		cMenu.deleteDocument(file_locator);
		
	}

	@Test
	/**
	 * == Add published status from draft status ==
	 * Test case ID: 101274
	 * Step 1: Show site explorer by legal user
	 * Step 2: Create new node with draft status
	 * Step 3: Show Manage Publication form of a draft node
	 * Step 4: Change status
	 */
	public void test03_AddPublishedStatusFromDraftStatus(){

		String doc_Name = "doc_test_67191";
		String doc_Content = "Content of File: Add Published status from Draft status";
		By file_locator = By.linkText(doc_Name);

		info("Add Published status from Draft status");
		// Add a content
		actBar.goToAddNewContent();
		conTemp.createNewFile(doc_Name, doc_Content, null);
		info("Data created");
		// Show Publication Form and change status
		actBar.managePublication("Published");
		info("Status published check");
		info("Test successful");
		// Remove test data
		cMenu.deleteDocument(file_locator);
		
	}

	@Test
	/**
	 * == Add stage status from draft status ==
	 * Test case ID: 101275
	 * Step 1: Show site explorer by legal user
	 * Step 2: Create new node with draft status
	 * Step 3: Show Manage Publication form of a draft node
	 * Step 4: Change status
	 */
	public void test04_AddStageStatusFromDraftStatus(){

		String doc_Name = "doc_test_66273";
		String doc_Content = "Content of File: Add Stage status from Draft status";
		By file_locator = By.linkText(doc_Name);

		info("Add Stage status from Draft status");
		// Add a content
		actBar.goToAddNewContent();
		conTemp.createNewFile(doc_Name, doc_Content, null);
		info("Data created");
		// Show Publication Form and change status to Staged
		actBar.managePublication("Staged");
		info("Status staged check");
		info("Test successful");
		// Remove test data
		cMenu.deleteDocument(file_locator);
		
	}

	@Test
	/**
	 * == Check showing manage publication form ==
	 * Test case ID: 101271
	 * Step 1: Show site explorer by legal user
	 * Step 2: Show Manage Publication form
	 */
	public void test05_CheckShowingManagePublicationForm(){
		String doc_Name = "doc_test_67105";
		String doc_Content = "Content of File: Check showing Manage publication form";
		By file_locator = By.linkText(doc_Name);

		info("Check showing Manage publication form");
		// Add a content
		actBar.goToAddNewContent();
		conTemp.createNewFile(doc_Name, doc_Content, null);
		info("Data created");
		// Show Publication Form and change status
		actBar.managePublication("Published");

		actBar.openManagePublicationForm();
		waitForAndGetElement(actBar.ELEMENT_REVISION.replace("${state}", "Published"));
		info("Test successful");
		button.close();
		// Remove test data
		cMenu.deleteDocument(file_locator);
		
	}

	@Test
	/**
	 * == Publish child node which is not nt:file when parent has been published ==
	 * Test case ID: 101278
	 * Step 1: Create parent nodes
	 * Step 2: Create parent nodes
	 * Step 3: Publish parent
	 * Step 4: Publish child node
	 */
	public void test06_PublishChildNodeNotNTFileWhenParentPublished(){

		String parent_Name = "parent_test_94786";
		String parent_Content = "Parent node test 94786";
		String child_Name = "child_test_94786";
		String child_Sum = "Child node test 94786";
		By file_locator = By.linkText(parent_Name);

		info("Publish child node which is not nt:file when parent has been published");
		// Add parent content
		actBar.goToAddNewContent();
		conTemp.createNewWebContent(parent_Name, parent_Content, "", "", "", "");

		// Add child content
		actBar.goToAddNewContent();
		conTemp.createNewAnnouncement(child_Name, child_Sum);
		info("Data created");
		// Publish parent content
		ecms.goToNode(file_locator);
		actBar.managePublication("Published");

		// Check Publish icon for child content
		ecms.goToNode(parent_Name+"/"+child_Name);
		if ((waitForAndGetElement(actBar.ELEMENT_PUBLICATION, 10000, 0) == null )){
			click(actBar.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		}
		waitForAndGetElement(actBar.ELEMENT_PUBLISH_ICON);
		info("published check");
		info("Test successful");
		// Remove test data
		cMenu.deleteDocument(file_locator);
		
	}

	@Test
	/**
	 * == Publish child node which is nt:file when parent has been published ==
	 * Test case ID: 101277
	 * Step 1: Create parent nodes
	 * Step 2: Create parent nodes
	 * Step 3: Publish parent
	 * Step 4: Publish child node
	 */
	public void test07_PublishChildNodeNTFileWhenParentPublished(){

		String parent_Name = "parent_test_94785";
		String parent_Content = "Parent node test 94785";
		String child_Name = "child_test_94785";
		String child_Content = "Child node test 94785";
		By file_locator_parent = By.linkText(parent_Name);

		info("Publish child node which is nt:file when parent has been published");
		// Add parent content
		actBar.goToAddNewContent();
		conTemp.createNewWebContent(parent_Name, parent_Content, "", "", "", "");

		// Add child content
		actBar.goToAddNewContent();
		conTemp.createNewFile(child_Name, child_Content, null);
		info("Data created");
		// Publish parent content
		ecms.goToNode(file_locator_parent);
		actBar.managePublication("Published");

		// Check Publish icon for child content
		ecms.goToNode(child_Name);
		if ((waitForAndGetElement(actBar.ELEMENT_MORE_LINK_WITHOUT_BLOCK, 5000, 0) != null )){
			click(actBar.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		}
		waitForElementNotPresent(actBar.ELEMENT_PUBLISH_ICON);
		info("not published check");
		info("Test successful");
		// Remove test data
		cMenu.deleteDocument(file_locator_parent);
		
	}

	@Test
	/**
	 * == Publish content when only set time in From field ==
	 * Test case ID: 101262
	 * Step 1: Show site explorer by legal user
	 * Step 2: Create new node with draft status
	 * Step 3: Show Manage Publication form of a draft node
	 * Step 4: Change status to Published
	 * Step 5: Setting time on From field
	 */
	public void test08_PublishContentWhenOnlySetTimeInFromField(){

		String doc_Name = "doc_test_66269";
		String doc_Content = "Content of File: Publish content when only set time in From field";
		By file_locator = By.linkText(doc_Name);

		info("Publish content when only set time in From field");

		// Add a content
		actBar.goToAddNewContent();
		conTemp.createNewFile(doc_Name, doc_Content, null);
		info("Data created");
		// Show Publication Form, check draft status and change status to Staged
		actBar.openManagePublicationForm();
		waitForAndGetElement(actBar.ELEMENT_CURRENT_DRAFT_STATUS);
		info("Status draft check");
		button.close();

		// Set time in From Field and check status
		String oneMinuteAfterCurrentTime = addMinuteToCurrentDateTime(1);
		actBar.managePublication("Staged",oneMinuteAfterCurrentTime,"");

		// Wait for one minute plus time so that changes take effect
		Utils.pause(180000);
		actBar.openManagePublicationForm();
		waitForAndGetElement(actBar.ELEMENT_CURRENT_PUBLIC_STATUS);
		info("Status published check");
		info("Test successful");
		// Remove test data
		cMenu.deleteDocument(file_locator);
		
	}

	@Test
	/**
	 * == Publish content when only set time in To field ==
	 * Test case ID: 101263
	 * Step 1: Show site explorer by legal user
	 * Step 2: Create new node with draft status
	 * Step 3: Show Manage Publication form of a draft node
	 * Step 4: Change status to Published
	 * Step 5: Setting time on To field
	 */
	public void test09_PublishContentWhenOnlySetTimeInToField(){

		String doc_Name = "doc_test_66270";
		String doc_Content = "Content of File: Publish content when only set time in To field";
		By file_locator = By.linkText(doc_Name);

		info("Publish content when only set time in To field");
		// Add a content
		actBar.goToAddNewContent();
		conTemp.createNewFile(doc_Name, doc_Content, null);
		info("Data created");
		// Show Publication Form, change status to Staged and Set time in To Field and check status
		String oneMinuteAfterCurrentTime = addMinuteToCurrentDateTime(3);
		actBar.managePublication("Staged","",oneMinuteAfterCurrentTime);
		Utils.pause(90000);
		actBar.openManagePublicationForm();
		waitForAndGetElement(actBar.ELEMENT_CURRENT_PUBLIC_STATUS);
		info("Status published check");
		button.close();
		Utils.pause(120000);
		actBar.openManagePublicationForm();
		waitForElementNotPresent(actBar.ELEMENT_CURRENT_PUBLIC_STATUS);
		info("Status not published check");
		info("Test successful");
		button.close();

		// Remove test data
		cMenu.deleteDocument(file_locator);
		
	}

	@Test
	/**
	 * == Publish content when put invalid date format ==
	 * Test case ID: 101264
	 * Step 1: Show site explorer by legal user
	 * Step 2: Create new node with draft status
	 * Step 3: Show Manage Publication form of a draft node
	 * Step 4: Change status to Published
	 * Step 5: Setting time on From field
	 */
	public void test10_PublishContentWhenPutInvalidDateFormat(){

		String doc_Name = "doctest66271";
		String doc_Content = "Content of File: Publish content when put invalid date format";
		By file_locator = By.linkText(doc_Name);
		String invalidDate = "10/10/2013";

		info("Publish content when put invalid date format");

		// Add a content
		actBar.goToAddNewContent();
		conTemp.createNewFile(doc_Name, doc_Content, null);
		info("Data created");
		// Show Publication Form and change status to Staged
		actBar.managePublication("Staged", invalidDate,null,"0");
		info("Test successful");
		
		// Remove test data
		cMenu.deleteDocument(file_locator);
		info("Test successful");
	}

	@Test
	/**
	 * == Publish content with setting publication time in both Form and To field ==
	 * Test case ID: 101265
	 * Step 1: Show site explorer by legal user
	 * Step 2: Create new node with draft status
	 * Step 3: Show Manage Publication form of a draft node
	 * Step 4: Change status to Published
	 * Step 5: Setting time to publish content
	 */
	public void test11_PublishContentWithTimeInBothFromAndToFields(){

		String doc_Name = "doc_test_66272";
		String doc_Content = "Content of doc_66272";
		By file_locator = By.linkText(doc_Name);

		info("Publish content with setting publication time in both Form and To field");
		// Add a content
		actBar.goToAddNewContent();
		conTemp.createNewFile(doc_Name, doc_Content, null);
		info("Data created");
		// Show Publication Form, change status to Staged, Set time in From and To Fields and check status
		String oneMinuteAfterCurrentTime = addMinuteToCurrentDateTime(1);
		String fourMinuteAfterCurrentTime = addMinuteToCurrentDateTime(4);
		actBar.managePublication("Staged",oneMinuteAfterCurrentTime,fourMinuteAfterCurrentTime);
		Utils.pause(100000);

		//Check status of document
		actBar.openManagePublicationForm();
		waitForAndGetElement(actBar.ELEMENT_CURRENT_PUBLIC_STATUS);
		info("Status published check");
		button.close();
		Utils.pause(200000);
		actBar.openManagePublicationForm();
		waitForElementNotPresent(actBar.ELEMENT_CURRENT_PUBLIC_STATUS);
		info("Status not published check");
		info("Test successful");
		button.close();

		// Remove test data
		cMenu.deleteDocument(file_locator);
		
	}

	@Test
	/**
	 * == Publish content without setting publication time ==
	 * Test case ID: 101266
	 * Step 1: Show site explorer by legal user
	 * Step 2: Create new node with draft status
	 * Step 3: Show Manage Publication form of a draft node
	 * Step 4: Change status to Published
	 */
	public void Test12_PublishContentWithoutSettingPublicationTime() {
		String doc_Name = "doctest66271";
		String doc_Content = "Content of File: Publish content without setting publication time";
		By file_locator = By.linkText(doc_Name);

		info("Publish content when put invalid date format");

		// Add a content
		actBar.goToAddNewContent();
		conTemp.createNewFile(doc_Name, doc_Content, null);
		info("Data created");
		// Show Publication Form and change status to Staged
		actBar.managePublication("Staged");
		actBar.openManagePublicationForm();
		waitForAndGetElement(actBar.ELEMENT_CURRENT_STAGED_STATUS);
		button.close();
		info("Status staged check");
		info("Test successful");
		
		// Remove test data
		cMenu.deleteDocument(file_locator);
		info("Test successful");
	}
	
}
