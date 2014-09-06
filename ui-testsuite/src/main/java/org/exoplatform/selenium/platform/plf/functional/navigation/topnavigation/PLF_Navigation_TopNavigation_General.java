package org.exoplatform.selenium.platform.plf.functional.navigation.topnavigation;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.openqa.selenium.By;
import org.testng.annotations.*;
/**
 * @author chinhdtt
 * @date 20 Mar 2014
 */
public class PLF_Navigation_TopNavigation_General extends PlatformBase{
	ManageAccount acc;
	NavigationToolbar nav;
	UserGroupManagement mGroup;

	@BeforeMethod
	public void beforeMethods(){	
		initSeleniumTest();
		driver.get(baseUrl);
		acc = new ManageAccount(driver, this.plfVersion);
		nav = new NavigationToolbar(driver, this.plfVersion);	
		mGroup = new UserGroupManagement(driver, this.plfVersion);
		acc.signIn(DATA_USER1, DATA_PASS);		
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}		

	/**
	 * Case ID:76629.
	 * Test Case Name: Add menu and submenus from configuration file.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/20 08:54:44
	 * Pending this case because can't add menu and submenus from configuration file
	 */
	@Test (groups ="pending")
	public  void test01_AddMenuAndSubmenusFromConfigurationFile() {
		info("Test 1: Add menu and submenus from configuration file");
		/*
		- From the binary, Open the file Shared	-laoayout.xml
		- Add a new portlet
		- Save
		 *Input Data: 
		 *Expected Outcome: A new portlet is added to the file config		*/

		/*
		- Start the server
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: The new added portlet is displayed on the navigation bar as menu		*/ 

	}

	/**
	 * Case ID:76630.
	 * Test Case Name: Click on button from navigation bar to unfold a menu.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/20 08:54:44
	 */
	@Test
	public  void test02_ClickOnButtonFromNavigationBarToUnfoldAMenu() {
		info("Test 2: Click on button from navigation bar to unfold a menu");
		/*
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: 
		- The top navigation bar is displayed		*/
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);
		waitForAndGetElement(ELEMENT_CATEGORY_ADMINISTRATION);

		/*
		- Move the mouse over a button from the navigation bar
		 *Input Data: 
		 *Expected Outcome: The menu isn't unfolded		*/
		mouseOver(ELEMENT_MENU_ADMIN_ICON, true);
		waitForElementNotPresent(ELEMENT_MENU_ADMININISTRATION);

		/*
		- Click on button from navigation bar
		 *Input Data: 
		 *Expected Outcome: The menu is unfolded		*/ 
		mouseOverAndClick(ELEMENT_MENU_ADMIN_ICON);
		waitForAndGetElement(ELEMENT_MENU_ADMININISTRATION);
	}

	/**
	 * Case ID:76631.
	 * Test Case Name: Show a visual indicator for selected item on a menu.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/20 08:54:44
	 */
	@Test
	public  void test03_ShowAVisualIndicatorForSelectedItemOnAMenu() {
		info("Test 3: Show a visual indicator for selected item on a menu");
		/*
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: The top navigation bar is displayed		*/
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);
		waitForAndGetElement(ELEMENT_CATEGORY_ADMINISTRATION);

		/*
		- Click on a button of action in the top navigation bar
		 *Input Data: 
		 *Expected Outcome: 
		- The menu is expanded
		- The list of items is displayed		*/
		mouseOverAndClick(ELEMENT_MENU_ADMIN_ICON);
		info("Administrator menu is display");
		waitForAndGetElement(By.xpath(ELEMENT_LINK_USERS));
		waitForAndGetElement(ELEMENT_APPLICATIONS_LINK);
		waitForAndGetElement(ELEMENT_MENU_CONTENT);
		waitForAndGetElement(ELEMENT_MENU_ADMININISTRATION);
		waitForAndGetElement(ELEMENT_MENU_PORTAL);

		/*
		- Move the mouse over the list in the menu
		 *Input Data: 
		 *Expected Outcome: A visual indicator is displayed to show the selected item		*/ 
		info("Mouse over on submenu");
		mouseOver(ELEMENT_MENU_ADMININISTRATION, true);
		waitForAndGetElement(ELEMENT_TOP_NAVIGATION_MANAGEMENT);
		waitForAndGetElement(ELEMENT_TOP_NAVIGATION_MONITORING);
	}

	/**
	 * Case ID:76632.
	 * Test Case Name: Show Top Navigation bar for Administrator user.
	 * Pre-Condition: The user is manager of platform/admin
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/20 08:54:44
	 */
	@Test
	public  void test04_ShowTopNavigationBarForAdministratorUser() {
		info("Test 4: Show Top Navigation bar for Administrator user");
		/*
		- Connect to Intranet with an administrator
		 *Input Data: 
		 *Expected Outcome: 
		- The top navigation bar is displayed
		- The top bar contain the Administration button, and buttons: Company logo Create button Search button Profile button Help button,edit button		*/ 
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);
		waitForAndGetElement(ELEMENT_LOGO_CONTAINER_TOOLBAR);
		waitForAndGetElement(ELEMENT_MENU_ADMIN_ICON);
		waitForAndGetElement(ELEMENT_MENU_EDIT_ICON);
		waitForAndGetElement(ELEMENT_MENU_ADD_ICON);
		waitForAndGetElement(ELEMENT_MENU_SEARCH_ICON);
		waitForAndGetElement(ELEMENT_ACCOUNT_NAME_LINK);
		waitForAndGetElement(ELEMENT_MENU_HELP_ICON);
	}

	/**
	 * Case ID:76633.
	 * Test Case Name: Show Top Navigation bar for Content editors user.
	 * Pre-Condition: user is member of /platform/administrators
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/20 08:54:44
	 */
	@Test
	public  void test05_ShowTopNavigationBarForContentEditorsUser() {
		info("Test 5: Show Top Navigation bar for Content editors user");
		String username = getRandomString();
		String password = "gtn123";
		String firstName = "BBB";
		String lastName = "AAA";
		String email = getRandomString()+"@exoplatform.com";
		String memberShip = "editor";

		//Pre-Condition
		info("Create new user");
		nav.goToNewStaff();
		acc.addNewUserAccount(username, password, password, firstName, lastName, "", email, null, null, true);
		Utils.pause(2000);
		info("Goto management group");
		nav.goToUsersAndGroupsManagement();
		Utils.pause(500);
		info("Select group");
		click(ELEMENT_USER_MANAGEMENT_GROUP);
		click(ELEMENT_USER_MANAGEMENT_GROUP_NODE.replace("${node}", "Platform"));
		click(ELEMENT_USER_MANAGEMENT_GROUP_NODE.replace("${node}", "Administration"));
		info("Add user to group Editor");
		mGroup.addUsersToGroup(username, memberShip, true, true);
		acc.signOut();

		/*
		- Connect to Intranet with a member of /platform/administrators
		 *Input Data: 
		 *Expected Outcome: 
		- The top navigation bar is displayed
		- The top bar contain the Edit button, and buttons: Company logoCreate buttonSearch buttonProfile buttonHelp button		*/ 
		acc.signIn(username, password);
		info("Editor view navigation bar");
		Utils.pause(1000);
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);
		waitForAndGetElement(ELEMENT_LOGO_CONTAINER_TOOLBAR);
		waitForAndGetElement(ELEMENT_MENU_EDIT_ICON);
		waitForAndGetElement(ELEMENT_MENU_ADD_ICON);
		waitForAndGetElement(ELEMENT_MENU_SEARCH_ICON);
		waitForAndGetElement(ELEMENT_ACCOUNT_NAME_LINK);
		waitForAndGetElement(ELEMENT_MENU_HELP_ICON);

		//Delete data test
		acc.userSignIn(userType.ADMIN);
		nav.goToUsersAndGroupsManagement();
		mGroup.deleteUser(username);
	}

	/**
	 * Case ID:76635.
	 * Test Case Name: Show Top Navigation bar in the top of the screen.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/20 08:54:44
	 */
	@Test
	public  void test06_ShowTopNavigationBarInTheTopOfTheScreen() {
		info("Test 6: Show Top Navigation bar in the top of the screen");
		/*
		- Connect to Intranet 
		 *Input Data: 
		 *Expected Outcome: 
		- The top navigation bar is displayed in the top of the screen		*/ 
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);		
	}

	/**
	 * Case ID:76628.
	 * Test Case Name: Show Top Navigation bar items for a normal user.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/20 08:54:44
	 */
	@Test
	public  void test07_ShowTopNavigationBarItemsForANormalUser() {
		info("Test 7: Show Top Navigation bar items for a normal user");
		String username = getRandomString();
		String password = "gtn123";
		String firstName = "BBB";
		String lastName = "LastName";
		String email = getRandomString()+"@exoplatform.com";

		//Pre-Condition
		nav.goToNewStaff();
		acc.addNewUserAccount(username, password, password, firstName, lastName, "", email, null, null, true);
		Utils.pause(2000);
		acc.signOut();

		/*
		- Connect to Intranet by a normal user (without privileges)
		 *Input Data: 
		 *Expected Outcome: 
		- The Top Navigation bar is displayed
		- Following items (from left to right):* Company logo* Create button* Search button* Profile button* Help button		*/ 
		acc.signIn(username, password);
		Utils.pause(1000);
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);
		waitForAndGetElement(ELEMENT_LOGO_CONTAINER_TOOLBAR);
		waitForAndGetElement(ELEMENT_MENU_ADD_ICON);
		waitForAndGetElement(ELEMENT_MENU_SEARCH_ICON);
		waitForAndGetElement(ELEMENT_ACCOUNT_NAME_LINK);
		waitForAndGetElement(ELEMENT_MENU_HELP_ICON);

		//Delete data test
		acc.userSignIn(userType.ADMIN);
		nav.goToUsersAndGroupsManagement();
		mGroup.deleteUser(username);
	}
}