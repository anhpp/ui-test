package org.exoplatform.selenium.platform.wiki.functional.search;


import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.wiki.BasicAction;

/**
 *
 * @author HangNTT
 * @date: 13/12/2012
 */
public class Wiki_Quick_Search extends PlatformBase {

	ManageAccount magAc;
	BasicAction ba;
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		ba = new BasicAction(driver);
		magAc = new ManageAccount(driver);
		magAc.signIn(DATA_USER1, DATA_PASS);
	}
	
	//Qmetry ID: 79796
	//Search when the keyword is matched
	@Test
	public void test01_SearchWhenKeyWordIsMatched() {
		
		String PAGE_NAME1 = "Wiki_Quick_Search_01";

		By ELEMENT_PAGE1 = By.xpath(ba.ELEMENT_RESULT_SEARCH.replace("${pageName}", PAGE_NAME1));

		ba.goToWiki();

		ba.addBlankWikiPage(PAGE_NAME1, PAGE_NAME1, 0);

		ba.quickSearch(PAGE_NAME1);

		click(ELEMENT_PAGE1);

		ba.deleteCurrentWikiPage();
	}
	
	//Qmetry ID: 69798
	//Search when the keyword is not matched
	@Test
	public void test02_SearchWhenKeyWordIsNotMatched() {

		String PAGE_NAME1 = "Wiki_Quick_Search_02";

		By ELEMENT_PAGE1 = By.linkText(PAGE_NAME1);

		ba.goToWiki();

		ba.addBlankWikiPage(PAGE_NAME1, PAGE_NAME1, 0);

		ba.quickSearch("bbb");

		//waitForTextPresent(ELEMENT_VERIFY_MESSAGE);
		waitForAndGetElement(ba.ELEMENT_VERIFY_RESULT_SEARCH.replace("${pageName}", "bbb"), DEFAULT_TIMEOUT, 1, 2);

		click(ELEMENT_PAGE1);

		ba.deleteCurrentWikiPage();
	}
	
	//Qmetry ID: 69806
	//View content of search result when user does not have permission to view page
	@Test
	public void test03_ViewContentOfSearchResultWhenUserDoesNotHavePermissionToViewPage() {

		String[] user1= {DATA_USER3};

		String PAGE_NAME1 = "Wiki_Quick_Search_03";

		By ELEMENT_PAGE1 = By.linkText(PAGE_NAME1);

		ba.goToWiki();

		ba.addBlankWikiPage(PAGE_NAME1, PAGE_NAME1, 0);

		ba.deletePagePermission("any");

		ba.addPagePermission(0,user1);

		ba.editPagePermission(DATA_USER3, false,false, false, 2);

		magAc.signOut();

		magAc.signIn(DATA_USER3, DATA_PASS);

		ba.goToWiki();

		ba.quickSearch(PAGE_NAME1);

		//waitForTextPresent(ELEMENT_VERIFY_MESSAGE);
		waitForAndGetElement(ba.ELEMENT_VERIFY_RESULT_SEARCH.replace("${pageName}", PAGE_NAME1), DEFAULT_TIMEOUT, 1, 2);

		magAc.signOut();
		magAc.signIn(DATA_USER1, DATA_PASS);

		ba.goToWiki();

		click(ELEMENT_PAGE1);

		ba.deleteCurrentWikiPage();
	}

	@AfterMethod
	public void afterTest(){
		//signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}
}
