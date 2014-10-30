package org.exoplatform.selenium.platform.plf.functional.unifiedsearch;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationManagement;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.SearchAdministration;
import org.exoplatform.selenium.platform.SettingSearchPage;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.forum.ForumManageCategory;
import org.exoplatform.selenium.platform.forum.ForumManageForum;
import org.exoplatform.selenium.platform.forum.ForumManagePost;
import org.exoplatform.selenium.platform.forum.ForumManageTopic;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.wiki.Template;
import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * 
 * @author cmugnier
 * @date 28/09/2014
 * 
 */

public class PLF_UnifiedSearch_SearchEngine extends Template {
	// General
	Button button;

	// Platform
	NavigationToolbar naviToolbar;
	NavigationManagement navMag;
	ManageAccount magAcc;
	ManageMember magMember;

	ContextMenu cMenu;
	ActionBar actBar;

	SearchAdministration searchAdmin;
	SettingSearchPage qsPage;

	ForumManageForum mngFru;
	ForumManageTopic mngTopic;
	ForumManagePost mngPost;
	ForumManageCategory mngCat;

	ContentTemplate conTemp;

	@BeforeTest
	public void setBeforeTest() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER1);

		magAcc = new ManageAccount(driver, this.plfVersion);
		magMember = new ManageMember(driver, this.plfVersion);
		naviToolbar = new NavigationToolbar(driver, this.plfVersion);
		button = new Button(driver, this.plfVersion);
		mngFru = new ForumManageForum(driver, this.plfVersion);
		actBar = new ActionBar(driver, this.plfVersion);
		mngTopic = new ForumManageTopic(driver, this.plfVersion);
		mngPost = new ForumManagePost(driver, this.plfVersion);
		mngCat = new ForumManageCategory(driver, this.plfVersion);
		navMag = new NavigationManagement(driver, this.plfVersion);
		qsPage = new SettingSearchPage(driver);
		cMenu = new ContextMenu(driver, this.plfVersion);
		conTemp = new ContentTemplate(driver, this.plfVersion);
		magAcc.signIn(DATA_USER1, DATA_PASS);

	}

	@AfterTest
	public void setAfterTest() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Fuzzy search by default ==
	 * Test case ID: 104311
	 * Step 1: search for "cluste"
	 * Step 2: search for "clustter"
	 */
	@Test(priority=0)
	public void test01_FuzzySearchByDefault() {
		String name = "search_engine_01";
		String content = "cluster";
		String searchText1 = "cluste";
		String searchText2 = "clustter";

		//Pre-condition
		naviToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewFile(name, content, content);
		info("data created");
		/*Step 1: search for "cluste"*/
		qsPage.quickSearch(searchText1);
		info("searching");
		//check the result
		waitForAndGetElement(By.xpath((qsPage.ELEMENT_RESULT_SEARCH_ITEM).replace("${item}", name)));
		info("check the item 'cluste'");

		/*Step 2: search for "clustter"*/
		qsPage.quickSearch(searchText2);
		info("searching");
		//check the result
		waitForAndGetElement(By.xpath((qsPage.ELEMENT_RESULT_SEARCH_ITEM).replace("${item}", name)));
		info("check the item 'clustter'");
		info("test succeed");

		//clear the data
		naviToolbar.goToSiteExplorer();
		cMenu.deleteDocument("//*[@class='nodeName' and contains(text(), '{$node}')]".replace("{$node}", name));
		info("data cleaned");
	}

	/**
	 * == All words should match ==
	 * Test case ID: 104312
	 * Step 1: search for the terms : "cluster availability"
	 */
	@Test(priority=1)
	public void test02_AllWordsShouldMatch() {
		String name = "search_engine_02_OK";
		String name1 = "search_engine_02_KO";
		String name2 = "search_engine_02_KO2";
		String content = "a cluster will enhance your service availability";
		String content1 = "cluster";
		String content2 = "availability";

		//Pre-condition
		naviToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewFile(name, content, name);
		naviToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewFile(name1, content1, name1);
		naviToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewFile(name2, content2, name2);
		info("data created");

		/*Step 1: search for the terms : "cluster availability"*/
		click(ELEMENT_QUICK_SEARCH_ICON);
		type(ELEMENT_QUICK_SEARCH_TEXTBOX, "cluster availability", true);
		Utils.pause(3000);
		click(ELEMENT_SEE_ALL_SEARCH_RESULTS);
		info("searching");
		Utils.pause(1000);
		waitForAndGetElement(By.xpath((qsPage.ELEMENT_RESULT_SEARCH_ITEM).replace("${item}", name)));
		waitForElementNotPresent(By.xpath((qsPage.ELEMENT_RESULT_SEARCH_ITEM).replace("${item}", name1)));
		waitForElementNotPresent(By.xpath((qsPage.ELEMENT_RESULT_SEARCH_ITEM).replace("${item}", name2)));


		info("test succeed");

		//clear the data
		naviToolbar.goToSiteExplorer();
		cMenu.deleteDocument("//*[@class='nodeName' and text()='{$node}']".replace("{$node}", name));
		cMenu.deleteDocument("//*[@class='nodeName' and text()= '{$node}']".replace("{$node}", name1));
		cMenu.deleteDocument("//*[@class='nodeName' and text()= '{$node}']".replace("{$node}", name2));
		info("data cleaned");
	}

	/**
	 * == Search should be case insensitive ==
	 * Test case ID: 104313
	 * Step 1: search for "cluster availability" 
	 */
	@Test(priority=2)
	public void test03_SearchShouldBeCaseInsensitive() {
		String name = "search_engine_05";
		String content = "High Availability with a Cluster";
		String search = "cluster availability";

		//Pre-condition
		naviToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewFile(name, content, content);
		info("data created");

		/*Step 1: search for "cluster availability"*/ 
		qsPage.quickSearch(search);
		info("searching 'cluster availability'");

		//check the result
		waitForAndGetElement(By.xpath((qsPage.ELEMENT_RESULT_SEARCH_ITEM).replace("${item}", name)));
		info("test succeed");
		
		//clear the data
		naviToolbar.goToSiteExplorer();
		cMenu.deleteDocument("//*[@class='nodeName' and text()='{$node}']".replace("{$node}", name));
		info("data cleaned");
	}

	/**
	 * == Search sentence ==
	 * Test case ID: 104314
	 * Step 1: Search for "High Availability" (surround the terms by the double quotes)
	 */
	@Test(priority=3)
	public void test04_SearchSentence() {
		String title = "Cluster1";
		String title1 = "Cluster2";
		String title2 = "Cluster3";
		String title3 = "Cluster4";
		String content = "High availibility";
		String content1 = "High";
		String content2 = "availibility";
		String content3 = "availibility cluster";
		String search = "\""+content+"\"";

		//Pre-condition
		//Add wiki page
		info("Add new wiki page");		
		goToWiki();
		addBlankWikiPage(title, content, 0);
		goToWiki();
		addBlankWikiPage(title1, content1, 0);
		goToWiki();
		addBlankWikiPage(title2, content2, 0);
		goToWiki();
		addBlankWikiPage(title3, content3, 0);
		info("data created");

		/*Step 1: Search for "High Availability" (surround the terms by the double quotes)*/
		qsPage.quickSearch(search);
		info("searching");

		//check the result
		waitForAndGetElement(By.xpath((qsPage.ELEMENT_RESULT_SEARCH_ITEM).replace("${item}", title)));
		waitForElementNotPresent(By.xpath((qsPage.ELEMENT_RESULT_SEARCH_ITEM).replace("${item}", title1)));
		waitForElementNotPresent(By.xpath((qsPage.ELEMENT_RESULT_SEARCH_ITEM).replace("${item}", title2)));
		waitForElementNotPresent(By.xpath((qsPage.ELEMENT_RESULT_SEARCH_ITEM).replace("${item}", title3)));
		info("test succeed");
		
		//clear the data
		//clear data 
		goToWiki();
		click(ELEMENT_NODE_WIKI_PAGE.replace("{$node}",title));
		deleteCurrentWikiPage();
		click(ELEMENT_NODE_WIKI_PAGE.replace("{$node}",title1));
		deleteCurrentWikiPage();
		click(ELEMENT_NODE_WIKI_PAGE.replace("{$node}",title2));
		deleteCurrentWikiPage();
		click(ELEMENT_NODE_WIKI_PAGE.replace("{$node}",title3));
		deleteCurrentWikiPage();
		info("data cleaned");
	}
}