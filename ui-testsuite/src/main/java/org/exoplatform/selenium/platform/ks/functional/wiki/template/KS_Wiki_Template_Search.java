package org.exoplatform.selenium.platform.ks.functional.wiki.template;

import org.exoplatform.selenium.platform.ks.Wiki;
import static org.exoplatform.selenium.TestLogger.info;
import static org.exoplatform.selenium.platform.ManageAccount.*;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*--
 *-- Author: HaKT
 *-- Date: 19 Dec 2012
 **/

public class KS_Wiki_Template_Search extends Wiki{

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		actions = new Actions(driver);
		driver.get(baseUrl);
		driver.manage().window().maximize();
		signIn("john", "gtn"); 
		goToWiki();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Case 01: Search template when the key is matched
	 * - Create new Template
	 * - Fill in search form new template title, press Enter
	 * - Verify new template is in list
	 * - Delete template
	 */
	@Test
	public static void test01_SearchTemplateWithMatchedKey(){

		String DATA_TEMPLATE_TITLE="Search_Template_01";
		String DATA_TEMPLATE_DESC="This is New Template 01";
		String DATA_TEMPLATE_CONTENT="Content of New Template 01";

		info("Search template when the key is matched");
		
		goToTemplateManagement();
		
		addTemplate(DATA_TEMPLATE_TITLE, DATA_TEMPLATE_DESC, DATA_TEMPLATE_CONTENT);
		
		searchTemplate(DATA_TEMPLATE_TITLE);
		
		waitForElementPresent(ELEMENT_NEW_TEMPLATE_LINK.replace("${TEMPLATE_TITLE}", DATA_TEMPLATE_TITLE));
		
		deleteTemplate(DATA_TEMPLATE_TITLE);
	}

	/**
	 * Case 02: Search template when the key is not matched
	 * - Create new Template
	 * - Fill in search form with a word not match with any template title, press Enter
	 * - Verify Empty data
	 * - Delete template
	 */
	@Test
	public static void test02_SearchTemplateWithNotMatchedKey(){

		String DATA_TEMPLATE_TITLE="Search_Template_02";
		String DATA_TEMPLATE_DESC="This is New Template 02";
		String DATA_TEMPLATE_CONTENT="Content of New Template 02";
		String DATA_SEARCH_KEY_WORD="Search_Template_0222";
		
		info("Search template when the key is matched");
		
		goToTemplateManagement();
		
		addTemplate(DATA_TEMPLATE_TITLE, DATA_TEMPLATE_DESC, DATA_TEMPLATE_CONTENT);
		
		waitForElementPresent(ELEMENT_NEW_TEMPLATE_LINK.replace("${TEMPLATE_TITLE}", DATA_TEMPLATE_TITLE));
		
		searchTemplate(DATA_SEARCH_KEY_WORD);
		
		waitForElementPresent(By.xpath(ELEMENT_EMPTY_DATA));
		
		signOut();
		
		signIn("john", "gtn"); 
		
		goToWiki();
		
		deleteTemplate(DATA_TEMPLATE_TITLE);
	}

	/**
	 * Case 03: Search template when Search field is blank
	 * - Create new Template
	 * - Leave search field blank, press Enter
	 * - Capture screen to view all template
	 * - Delete template
	 */
	@Test
	public static void test03_SearchTemplateWithBlankSearchField(){

		String DATA_TEMPLATE_TITLE="Search_Template_03";
		String DATA_TEMPLATE_DESC="This is New Template 03";
		String DATA_TEMPLATE_CONTENT="Content of New Template 03";
		String DATA_SEARCH_KEY_WORD="";
		String DATA_BEFORE_CREAT_NEW_TEMPLATE = "Before_New_Template";
		String DATA_AFTER_CREAT_NEW_TEMPLATE = "AFTER_New_Template";
		
		info("Search template with blank search field");
		
		goToTemplateManagement();
		
		captureScreen(DATA_BEFORE_CREAT_NEW_TEMPLATE);
		
		addTemplate(DATA_TEMPLATE_TITLE, DATA_TEMPLATE_DESC, DATA_TEMPLATE_CONTENT);
	
		waitForElementPresent(ELEMENT_NEW_TEMPLATE_LINK.replace("${TEMPLATE_TITLE}", DATA_TEMPLATE_TITLE));
		
		searchTemplate(DATA_SEARCH_KEY_WORD);
		
		captureScreen(DATA_AFTER_CREAT_NEW_TEMPLATE);
		
		deleteTemplate(DATA_TEMPLATE_TITLE);
	}

}