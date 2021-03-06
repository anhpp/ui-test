package org.exoplatform.selenium.platform.wiki.functional.spaceswitcher;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.social.ApplicationManagement;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.social.SocialBase;
import org.exoplatform.selenium.platform.wiki.WikiBase;
import org.openqa.selenium.By;
import org.testng.annotations.*;

/**
 * @author chinhdtt
 * @date 12 May 2014
 */
public class Wiki_SpaceSwitcher extends WikiBase{
	ManageAccount acc;
	Dialog dialog;
	Button button;	
	ManageMember mMember;
	NavigationToolbar nav; 	
	ApplicationManagement mApplication; 
	SocialBase sb;

	@BeforeMethod
	public void beforeMethod(){
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		acc = new ManageAccount(driver,this.plfVersion);
		dialog = new Dialog(driver);
		alert = new ManageAlert(driver, this.plfVersion);
		button = new Button(driver, this.plfVersion);	
		mMember = new ManageMember(driver, this.plfVersion);	
		nav = new NavigationToolbar(driver, this.plfVersion);
		mApplication = new ApplicationManagement(driver);
		sb = new SocialBase();
		acc.signIn(DATA_USER1, DATA_PASS);		
	}

	@AfterMethod
	public void afterMethod(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}		

	/**
	 * Case ID:79646.
	 * Test Case Name: Hover in space switcher's list is displayed.
	 * Pre-Condition: User is member of some spaces
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/05/12 13:45:02
	 * Pending this case because can't check changing display state to hover when mouse over items of list spaces. 
	 */
	@Test (groups="pending")
	public  void test01_HoverInSpaceSwitchersListIsDisplayed() {
		info("Test 1: Hover in space switcher's list is displayed");
		String space1 = "Space79646A";
		String space2 = "Space79646B";

		//Pre-Condition
		mMember.goToAllSpaces();
		mMember.addNewSpace(space1, "");
		mMember.goToAllSpaces();
		mMember.addNewSpace(space2, "");

		/*
		- Using left side bar navigation, go into Wiki
		 *Input Data: 
		 *Expected Outcome: 
		- Wiki application is displayed
		- Breadcrumb is displaying space switcher component		*/
		goToWikiFromSpace(space2);
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_INPUT);

		/*
		- Open the Space Switcher
		- Put your mouse over an item of the list
		 *Input Data: 
		 *Expected Outcome: 
		- Item of the list is changing his display state to hover		*/ 
		mouseOver(By.xpath(ELEMENT_SPACE_SWITCHER_SELECT.replace("${spaceName}", space2)), true);
		mouseOver(By.xpath(ELEMENT_SPACE_SWITCHER_SELECT.replace("${spaceName}", space1)), true);

		//Delete data test
		mMember.goToAllSpaces();
		mMember.deleteSpace(space1,300000);
		Utils.pause(1000);
		mMember.deleteSpace(space2,300000);
	}

	/**
	 * Case ID:79657.
	 * Test Case Name: Input text should display a placeholder.
	 * Pre-Condition: User is member of some spaces
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/05/12 13:45:02
	 * Pending this case because can not find the locator of element placeholder
	 * Issue: https://jira.exoplatform.org/browse/FQA-1372
	 */
	@Test (groups="pending")
	public  void test02_InputTextShouldDisplayAPlaceholder() {
		info("Test 2: Input text should display a placeholder");

		/*
		- Go to Company wiki
		 *Input Data: 
		 *Expected Outcome: 
		- Company wiki is displayed
		- Breadcrumb is displaying Space Switcher		*/
		goToWiki();
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		info("input text field");
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_INPUT);

		/*
		- Open the Space Switcher
		 *Input Data: 
		 *Expected Outcome: 
		- The first item of the list is an input text field
		- Placeholder is "Filter spaces"		*/ 
		info("Filter spaces field is shown");
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_INPUT);
		info("Placeholder is displayed");
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_PLACEHOLDER);		
	}

	/**
	 * Case ID:79642.
	 * Test Case Name: List of spaces should be displayed when user is member of at least one space.
	 * Pre-Condition: User is member of the space "Mobile"Mobile Space avatar is defined
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/05/12 13:45:02
	 */
	@Test
	public  void test03_ListOfSpacesShouldBeDisplayedWhenUserIsMemberOfAtLeastOneSpace() {
		info("Test 3: List of spaces should be displayed when user is member of at least one space");
		String spaceName = "Space79642A";
		String avatar = "ECMS_DMS_SE_Article.jpg";

		//Pre-Condition
		mMember.goToAllSpaces();
		mMember.addNewSpace(spaceName, "");
		mMember.goToAllSpaces();
		info("Change avatar of space");
		mMember.editSpace(spaceName, spaceName, "", true, avatar);

		/*
		- Go to Company wiki
		 *Input Data: 
		 *Expected Outcome: 
		- Company wiki is displayed
		- Breadcrumb is displaying Space Switcher		*/
		goToWikiFromSpace(spaceName);
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);

		/*
		- Open the Space Switcher
		 *Input Data: 
		 *Expected Outcome: 
		- The list of spaces is displayed after the input textfield
		- First item is "Mobile"
		- Avatar of the space is displayed before "Mobile"		*/ 
		info("Filter spaces field is shown");
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_INPUT);
		info("Avatar and space's Name");
		waitForAndGetElement(By.xpath(ELEMENT_SPACE_SWITCHER_AVATAR_SPACE.replace("${spaceName}", spaceName)));
		waitForAndGetElement(By.xpath(ELEMENT_SPACE_SWITCHER_SELECT.replace("${spaceName}", spaceName)));

		//Delete data test
		mMember.goToAllSpaces();
		mMember.deleteSpace(spaceName,300000);	
	}

	/**
	 * Case ID:79643.
	 * Test Case Name: Space switcher component should display a title.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/05/12 13:45:02
	 * Pending this case because can not find the locator of element Select Location title
	 */
	@Test 
	public  void test04_SpaceSwitcherComponentShouldDisplayATitle() {
		info("Test 4: Space switcher component should display a title");

		/*
		- Go to Company wiki
		 *Input Data: 
		 *Expected Outcome: 
		- Company wiki is displayed
		- Breadcrumb is displaying Space Switcher		*/
		goToWiki();
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		info("Filter spaces field is shown");
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_INPUT);

		/*
		- Open the Space Switcher
		 *Input Data: 
		 *Expected Outcome: 
		- Space switcher title is displaying "Select Location"		*/ 
		info("Title is displaying Select Location");
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_LOCATION);
	}

	/**
	 * Case ID:79654.
	 * Test Case Name: Space switcher is closed if user is clicking on the close button.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/05/12 13:45:02
	 */
	@Test
	public  void test05_SpaceSwitcherIsClosedIfUserIsClickingOnTheCloseButton() {
		info("Test 5: Space switcher is closed if user is clicking on the close button");

		/*
		- Go to Wiki using left side bar navigation
		 *Input Data: 
		 *Expected Outcome: 
		- Wiki application is displayed
		- Breadcrumb is displayed with space switcher component		*/
		goToWiki();
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		info("Title of space switcher");
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_LOCATION);

		/*
		- Open the space switcher
		 *Input Data: 
		 *Expected Outcome: 
		- space switcher is displaying its list
		- space switcher is displaying on the top right, a small "x" icon to close 		*/
		info("Close element");
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_CLOSE);

		/*
		- Click on the "x" icon
		 *Input Data: 
		 *Expected Outcome: 
		- Space switcher is closed (not showing its list anymore)		*/ 
		click(ELEMENT_SPACE_SWITCHER_CLOSE);
		waitForElementNotPresent(ELEMENT_SPACE_SWITCHER_LOCATION);
	}

	/**
	 * Case ID:79653.
	 * Test Case Name: Space switcher is closed if user is clicking outside the list.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/05/12 13:45:02
	 */
	@Test
	public  void test06_SpaceSwitcherIsClosedIfUserIsClickingOutsideTheList() {
		info("Test 6: Space switcher is closed if user is clicking outside the list");
		/*
		- Go to Wiki using left sidebar navigation
		 *Input Data: 
		 *Expected Outcome: 
		- Wiki application is displayed
		- Breadcrumb is displayed with space switcher component		*/

		/*
		- Open the space switcher
		 *Input Data: 
		 *Expected Outcome: 
		- space switcher is displaying its list		*/
		goToWiki();
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		info("Title of space switcher");
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_LOCATION);

		/*
		- Click outiside the list
		 *Input Data: 
		 *Expected Outcome: 
		- Space switcher is closed (not showing its list anymore)		*/ 
		click(ELEMENT_SPACE_SWITCHER_OUTSIDE);
		waitForElementNotPresent(ELEMENT_SPACE_SWITCHER_LOCATION);
	}

	/**
	 * Case ID:79634.
	 * Test Case Name: Space switcher is displaying "Intranet" when user is browsing the company wiki.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/05/12 13:45:02
	 */
	@Test
	public  void test07_SpaceSwitcherIsDisplayingIntranetWhenUserIsBrowsingTheCompanyWiki() {
		info("Test 7: Space switcher is displaying Intranet when user is browsing the company wiki");
		/*
		- connect to Intranet
		 *Input Data: 
		 *Expected Outcome: 
		- left sidebar navigation is displayed		*/

		/*
		- using the left sidebar navigation, go into the company wiki
		 *Input Data: 
		 *Expected Outcome: 
		- wiki's breadcrumb is showing the space switcher component with the label "Intranet"		*/ 
		goToWiki();
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		info("Title of space switcher");
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_LOCATION);
		info("Intranet");
		waitForAndGetElement(By.xpath(ELEMENT_SPACE_SWITCHER_SELECT.replace("${spaceName}", "Intranet")));
	}

	/**
	 * Case ID:79636.
	 * Test Case Name: Space switcher is displaying "My Wiki" for user personal wiki.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/05/12 13:45:02
	 */
	@Test
	public  void test08_SpaceSwitcherIsDisplayingMyWikiForUserPersonalWiki() {
		info("Test 8: Space switcher is displaying My Wiki for user personal wiki");
		/*
		- connect to Intranet
		 *Input Data: 
		 *Expected Outcome: 
		- Intranet is displaying top navigation bar		*/
		info("Check top navigation");
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);

		/*
		- using the top navigation bar, go into user menu> Wiki
		 *Input Data: 
		 *Expected Outcome: 
		- Personal wiki is displayed		*/
		nav.goToWiki();

		/*
		- check display of "My Wiki" on Space Switcher
		 *Input Data: 
		 *Expected Outcome: 
		- Space Switcher component is displayed in the breadcrumb with value "My Wiki"		*/ 
		Utils.pause(500);
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		mouseOverAndClick(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		info("MyWiki");
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_MYWIKI);
	}

	/**
	 * Case ID:79637.
	 * Test Case Name: Space switcher is displaying a list when opening it.
	 * Pre-Condition: User is member of no space User is connect to the intranet
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/05/12 13:45:02
	 */
	@Test
	public  void test09_SpaceSwitcherIsDisplayingAListWhenOpeningIt() {
		info("Test 9: Space switcher is displaying a list when opening it");
		/*
		- Go to Company Wiki
		 *Input Data: 
		 *Expected Outcome: 
		- Company wiki is displayed
		- Breadcrumb is displaying the space switcher		*/

		/*
		- Click on the space switcher
		 *Input Data: 
		 *Expected Outcome: 
		- A list is opened		*/ 
		goToWiki();
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		info("Title of space switcher");
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_LOCATION);
	}

	/**
	 * Case ID:79635.
	 * Test Case Name: Space switcher is displaying the name of the space if browsing a space's wiki.
	 * Pre-Condition: User is member of a space named: Mobile
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/05/12 13:45:02
	 */
	@Test
	public  void test10_SpaceSwitcherIsDisplayingTheNameOfTheSpaceIfBrowsingASpacesWiki() {
		info("Test 10 Space switcher is displaying the name of the space if browsing a space's wiki");
		String spaceName = "Space79635";

		//Pre-Condition
		mMember.goToAllSpaces();
		mMember.addNewSpace(spaceName, "");

		/*
		- connect to Intranet
		 *Input Data: 
		 *Expected Outcome: 
		- Intranet is displaying left sidebar navigation		*/	
		/*
		- using the left sidebar navigation, go into the space "Mobile"
		 *Input Data: 
		 *Expected Outcome: 
		- Intranet is displaying space navigation		*/
		/*
		- using space navigation, go into wiki application
		 *Input Data: 
		 *Expected Outcome: 
		- Space Switcher component is displayed in the breadcrumb with value "Mobile"		*/ 
		goToWikiFromSpace(spaceName);
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		info("Space's Name");
		waitForAndGetElement(By.xpath(ELEMENT_CURRENT_SPACE_SWITCHER_SELECT.replace("${name}", spaceName)));

		//Delete data test
		mMember.goToAllSpaces();
		mMember.deleteSpace(spaceName,300000);	
	}

	/**
	 * Case ID:79656.
	 * Test Case Name: Space switcher is displaying the name of the space if browsing a space's wiki in its list.
	 * Pre-Condition: User is member of a space named: Mobile
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/05/12 13:45:02
	 */
	@Test
	public  void test11_SpaceSwitcherIsDisplayingTheNameOfTheSpaceIfBrowsingASpacesWikiInItsList() {
		info("Test 11 Space switcher is displaying the name of the space if browsing a space's wiki in its list");
		String spaceName = "Space79656";

		//Pre-Condition
		mMember.goToAllSpaces();
		mMember.addNewSpace(spaceName, "");
		/*
		- connect to Intranet
		 *Input Data: 
		 *Expected Outcome: 
		- Intranet is displaying left sidebar navigation		*/
		/*
		- using the left sidebar navigation, go into the space "Mobile"
		 *Input Data: 
		 *Expected Outcome: 
		- Intranet is displaying space navigation		*/
		/*
		- using space navigation, go into wiki application
		 *Input Data: 
		 *Expected Outcome: 
		- Space Switcher component is displayed in the breadcrumb with value "Mobile"		*/
		goToWikiFromSpace(spaceName);
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		info("Space's Name");
		waitForAndGetElement(By.xpath(ELEMENT_SPACE_SWITCHER_SELECT.replace("${spaceName}", spaceName)));

		/*
		- Open the Space switcher
		 *Input Data: 
		 *Expected Outcome: 
		- In the list of spaces, "Mobile" is the first one on the list		*/ 
		info("Space's Index");
		waitForAndGetElement(By.xpath(ELEMENT_SPACE_SWITCHER_INDEX.replace("${index}","1").replace("${spaceName}", spaceName)));

		//Delete data test
		mMember.goToAllSpaces();
		mMember.deleteSpace(spaceName,300000);	
	}

	/**
	 * Case ID:79638.
	 * Test Case Name: Space switcher list should display "Intranet".
	 * Pre-Condition: User is member of 0 spaceUser has not browsed any space
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/05/12 13:45:02
	 */
	@Test
	public  void test12_SpaceSwitcherListShouldDisplayIntranet() {
		info("Test 12 Space switcher list should display Intranet");
		/*
		- Go to Company wiki
		 *Input Data: 
		 *Expected Outcome: 
		- Company wiki is displayed
		- Breadcrumb is displaying Space Switcher		*/
		/*
		- Open the Space Switcher
		 *Input Data: 
		 *Expected Outcome: 
		- The first item of the list is "Intranet"
		- Before "Intranet", an icon is displayed (as in the test case description)		*/ 
		goToWiki();
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		info("Title of space switcher");
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_LOCATION);
		info("Intranet");
		waitForAndGetElement(By.xpath(ELEMENT_SPACE_SWITCHER_INTRANET .replace("${index}", "3").replace("${spaceName}", "Intranet")));
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_AVATAR_INTRANET);
	}

	/**
	 * Case ID:79639.
	 * Test Case Name: Space switcher list should display "My Wiki".
	 * Pre-Condition: User is member of 0 Spaces
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/05/12 13:45:02
	 */
	@Test
	public  void test13_SpaceSwitcherListShouldDisplayMyWiki() {
		info("Test 13 Space switcher list should display My Wiki");
		/*
		- Go to Company wiki
		 *Input Data: 
		 *Expected Outcome: 
		- Company wiki is displayed
		- Breadcrumb is displaying Space Switcher		*/
		/*
		- Open the Space Switcher
		 *Input Data: 
		 *Expected Outcome: 
		- The second item of the list is "My Wiki"
		- An icon is displayed before "My Wiki" (as in the description of the test case)		*/ 
		goToWiki();
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		info("Title of space switcher");
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_LOCATION);
		info("My Wiki");
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_AVATAR_MYWIKI);
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_MYWIKI.replace("${index}", "4"));		
	}

	/**
	 * Case ID:79655.
	 * Test Case Name: Space Switcher must only display spaces where wiki application exist.
	 * Pre-Condition: User is member of some spacesUser is administrator of space "Without Wiki", where wiki application has been removed
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/05/12 13:45:02
	 */
	@Test
	public  void test14_SpaceSwitcherMustOnlyDisplaySpacesWhereWikiApplicationExist() {
		info("Test 14 Space Switcher must only display spaces where wiki application exist");
		String space01 = "Space79655";
		String space02 = "Without Wiki";
		String applicationTitle = "Wiki";

		//Pre-Condition
		mMember.goToAllSpaces();
		mMember.addNewSpace(space01, "");
		mMember.goToAllSpaces();
		mMember.addNewSpace(space02, "");

		/*
		- From left sidebar navigation, go into the space "Without Wiki"
		 *Input Data: 
		 *Expected Outcome: 
		- Space "Without Wiki" is displayed
		- Wiki application is not available in the space navigation menu		*/
		mMember.goToSpaceMenu("Space Settings");
		mApplication.removeApplication(applicationTitle);
		Utils.pause(1000);

		/*
		- From left sidebar navigation, go into the wiki
		 *Input Data: 
		 *Expected Outcome: 
		- Wiki application is displayed
		- Breadcrumb is displayed with the space switcher component		*/
		goToWiki();		
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		info("Title of space switcher");
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_LOCATION);
		info("Space list");
		waitForAndGetElement(By.xpath(ELEMENT_SPACE_SWITCHER_SELECT.replace("${spaceName}", space01)));

		/*
		- Open the space switcher component
		 *Input Data: 
		 *Expected Outcome: 
		- In the list of spaces, "Without Wiki" is not displayed		*/
		info("Space is not display");
		waitForElementNotPresent(By.xpath(ELEMENT_SPACE_SWITCHER_SELECT.replace("${spaceName}", space02)));

		/*
		- in the filter, type "Without"
		 *Input Data: 
		 *Expected Outcome: 
		- In the list of spaces, "Without Wiki" is NOT displayed		*/ 
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_INPUT).sendKeys("Without");
		waitForElementNotPresent(By.xpath(ELEMENT_SPACE_SWITCHER_SELECT.replace("${spaceName}", space02)));

		//Delete data test
		mMember.goToAllSpaces();
		mMember.deleteSpace(space01,300000);
		Utils.pause(1000);
		mMember.deleteSpace(space02,300000);
	}

	/**
	 * Case ID:79644.
	 * Test Case Name: Space switcher should display last browsed spaces.
	 * Pre-Condition: User is member of spaces :
	- Mobile
	- Space 1
	- Space 2
	- Space 3
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/05/12 13:45:02
	 */
	@Test
	public  void test15_SpaceSwitcherShouldDisplayLastBrowsedSpaces() {
		info("Test 15 Space switcher should display last browsed spaces");
		String space1 = "Space79644A";
		String space2 = "Space79644B";
		String space3 = "Space79644C";
		String space4 = "Space79644D";

		//Pre-Condition
		mMember.goToAllSpaces();
		mMember.addNewSpace(space1, "");
		mMember.goToAllSpaces();
		mMember.addNewSpace(space2, "");
		mMember.goToAllSpaces();
		mMember.addNewSpace(space3, "");
		mMember.goToAllSpaces();
		mMember.addNewSpace(space4, "");

		/*
		- Connect to the intranet
		 *Input Data: 
		 *Expected Outcome: 
		- Intranet is displaying left sidebar with list of spaces		*/		
		waitForAndGetElement(By.xpath(sb.ELEMENT_SPACE_IN_MY_SPACE_LIST.replace("${space}", space4)));
		waitForAndGetElement(By.xpath(sb.ELEMENT_SPACE_IN_MY_SPACE_LIST.replace("${space}", space3)));
		waitForAndGetElement(By.xpath(sb.ELEMENT_SPACE_IN_MY_SPACE_LIST.replace("${space}", space2)));
		waitForAndGetElement(By.xpath(sb.ELEMENT_SPACE_IN_MY_SPACE_LIST.replace("${space}", space1)));

		/*
		- Using left sidebar, go into Mobile space
		- Using left sidebar, go into Space 1 
		- Using left sidebar, go into Space 2 
		- Using left sidebar, go into Space 3		 
		/*
		- Go into Space 3 wiki
		 *Input Data: 
		 *Expected Outcome: 
		- Intranet is displaying wiki of Space 3
		- Space switcher is displayed in the wiki's breadcrumb		*/
		mMember.accessSpace(space1);
		Utils.pause(500);
		mMember.accessSpace(space2);
		Utils.pause(500);
		mMember.accessSpace(space4);
		Utils.pause(500);
		mMember.accessSpace(space3);
		click(ELEMENT_WIKI_LINK_IN_SPACE);
		waitForAndGetElement(ELEMENT_TITLE_WIKI_HOME);
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		info("Title of space switcher");
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_LOCATION);

		/*
		- Open Space Switcher
		 *Input Data: 
		 *Expected Outcome: 
		- The list of spaces is displaying latest browse space :* Space 3* Space 2* Space 1* Mobile		*/ 
		info("View list space");
		waitForAndGetElement(By.xpath(ELEMENT_SPACE_SWITCHER_INDEX.replace("${index}","1").replace("${spaceName}", space3)));
		waitForAndGetElement(By.xpath(ELEMENT_SPACE_SWITCHER_INDEX.replace("${index}","2").replace("${spaceName}", space4)));
		waitForAndGetElement(By.xpath(ELEMENT_SPACE_SWITCHER_INDEX.replace("${index}","3").replace("${spaceName}", space2)));
		waitForAndGetElement(By.xpath(ELEMENT_SPACE_SWITCHER_INDEX.replace("${index}","4").replace("${spaceName}", space1)));		

		//Delete data test
		mMember.goToAllSpaces();
		mMember.deleteSpace(space1,300000);
		Utils.pause(1000);
		mMember.deleteSpace(space2,300000);
		Utils.pause(1000);
		mMember.deleteSpace(space3,300000);
		Utils.pause(1000);
		mMember.deleteSpace(space4,300000);
	}

	/**
	 * Case ID:79645.
	 * Test Case Name: space switcher should not display more than 10 lastest browsed spaces.
	 * Pre-Condition: User is member of:Mobile, Space 1, Space 2, Space 3, Space 4, Space 5, Space 6, Space 7, Space 8, Space 9, Space 10, Space 11
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/05/12 13:45:02
	 * Issue: https://jira.exoplatform.org/browse/COMMONS-324
	 */
	@Test (groups = "errors")
	public  void test16_SpaceSwitcherShouldNotDisplayMoreThan10LastestBrowsedSpaces() {
		info("Test 16 space switcher should not display more than 10 lastest browsed spaces");		
		String space1 = "Space79644A";
		String space2 = "Space79644B";
		String space3 = "Space79644C";
		String space4 = "Space79644D";
		String space5 = "Space79644E";
		String space6 = "Space79644F";
		String space7 = "Space79644G";
		String space8 = "Space79644H";
		String space9 = "Space79644I";
		String space10 = "Space79644K";
		String space11 = "Space79644L";

		//Pre-Condition
		mMember.goToAllSpaces();
		mMember.addNewSpace(space1, "");
		mMember.goToAllSpaces();
		mMember.addNewSpace(space2, "");
		mMember.goToAllSpaces();
		mMember.addNewSpace(space3, "");
		mMember.goToAllSpaces();
		mMember.addNewSpace(space4, "");
		mMember.goToAllSpaces();
		mMember.addNewSpace(space5, "");
		mMember.goToAllSpaces();
		mMember.addNewSpace(space6, "");
		mMember.goToAllSpaces();
		mMember.addNewSpace(space7, "");
		mMember.goToAllSpaces();
		mMember.addNewSpace(space8, "");
		mMember.goToAllSpaces();
		mMember.addNewSpace(space9, "");
		mMember.goToAllSpaces();
		mMember.addNewSpace(space10, "");
		mMember.goToAllSpaces();
		mMember.addNewSpace(space11, "");

		/*
		- go to intranet
		 *Input Data: 
		 *Expected Outcome: 
		- Left sidebar is displayed		*/
		/*
		- Open Space 11
		- Open Space 10
		- Open Space 9
		- Open Space 8
		- Open Space 7
		- Open Space 6
		- Open Space 5
		- Open Space 4
		- Open Space 3
		- Open Space 2
		- Open Space 1
		- Open Mobile
		 */
		mMember.accessSpace(space2);
		Utils.pause(500);
		mMember.accessSpace(space3);
		Utils.pause(500);
		mMember.accessSpace(space4);
		Utils.pause(500);
		mMember.accessSpace(space5);
		Utils.pause(500);
		mMember.accessSpace(space6);
		Utils.pause(500);
		mMember.accessSpace(space7);
		Utils.pause(500);
		mMember.accessSpace(space8);
		Utils.pause(500);
		mMember.accessSpace(space9);
		Utils.pause(500);
		mMember.accessSpace(space10);
		Utils.pause(500);
		mMember.accessSpace(space11);
		Utils.pause(500);
		mMember.accessSpace(space1);

		/*
		- Open wiki application of Mobile Space
		 *Input Data: 
		 *Expected Outcome: 
		- Wiki is opened in the space Mobile
		- Breadcrumb is displaying space switcher component		*/
		click(ELEMENT_WIKI_LINK_IN_SPACE);
		waitForAndGetElement(ELEMENT_TITLE_WIKI_HOME);
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		info("Title of space switcher");
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_LOCATION);

		/*
		- Open Space Switcher list
		 *Input Data: 
		 *Expected Outcome: The list of last browsed space is displaying :
		- Mobile
		- Space 1
		- Space 2
		- Space 3
		- Space 4
		- Space 5
		- Space 6 
		- Space 7
		- Space 8
		- Space 9		*/ 
		info("View list space");
		waitForAndGetElement(By.xpath(ELEMENT_SPACE_SWITCHER_INDEX.replace("${index}","1").replace("${spaceName}", space1)));
		waitForAndGetElement(By.xpath(ELEMENT_SPACE_SWITCHER_INDEX.replace("${index}","2").replace("${spaceName}", space11)));
		waitForAndGetElement(By.xpath(ELEMENT_SPACE_SWITCHER_INDEX.replace("${index}","3").replace("${spaceName}", space10)));
		waitForAndGetElement(By.xpath(ELEMENT_SPACE_SWITCHER_INDEX.replace("${index}","4").replace("${spaceName}", space9)));	
		waitForAndGetElement(By.xpath(ELEMENT_SPACE_SWITCHER_INDEX.replace("${index}","5").replace("${spaceName}", space8)));		
		waitForAndGetElement(By.xpath(ELEMENT_SPACE_SWITCHER_INDEX.replace("${index}","6").replace("${spaceName}", space7)));		
		waitForAndGetElement(By.xpath(ELEMENT_SPACE_SWITCHER_INDEX.replace("${index}","7").replace("${spaceName}", space6)));		
		waitForAndGetElement(By.xpath(ELEMENT_SPACE_SWITCHER_INDEX.replace("${index}","8").replace("${spaceName}", space5)));		
		waitForAndGetElement(By.xpath(ELEMENT_SPACE_SWITCHER_INDEX.replace("${index}","9").replace("${spaceName}", space4)));		
		waitForAndGetElement(By.xpath(ELEMENT_SPACE_SWITCHER_INDEX.replace("${index}","10").replace("${spaceName}", space3)));		
		waitForElementNotPresent(By.xpath(ELEMENT_SPACE_SWITCHER_INDEX.replace("${index}","4").replace("${spaceName}", space2)));		

		//Delete data test
		mMember.goToAllSpaces();
		mMember.deleteSpace(space1,300000);
		Utils.pause(1000);
		mMember.deleteSpace(space2,300000);
		Utils.pause(1000);
		mMember.deleteSpace(space3,300000);
		Utils.pause(1000);
		mMember.deleteSpace(space4,300000);
		mMember.goToAllSpaces();
		mMember.deleteSpace(space5,300000);
		Utils.pause(1000);
		mMember.deleteSpace(space6,300000);
		Utils.pause(1000);
		mMember.deleteSpace(space7,300000);
		Utils.pause(1000);
		mMember.deleteSpace(space8,300000);
		mMember.goToAllSpaces();
		mMember.deleteSpace(space9,300000);
		Utils.pause(1000);
		mMember.deleteSpace(space10,300000);
		Utils.pause(1000);
		mMember.deleteSpace(space11,300000);		
	}
}