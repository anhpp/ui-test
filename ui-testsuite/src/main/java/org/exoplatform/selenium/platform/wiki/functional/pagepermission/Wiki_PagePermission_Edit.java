package org.exoplatform.selenium.platform.wiki.functional.pagepermission;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.wiki.Permalink;
import org.openqa.selenium.By;
import org.testng.annotations.*;


/**
 * @author phuongdt
 *
 */
public class Wiki_PagePermission_Edit extends Permalink{

	ManageAccount magAc;
	Dialog dialog;

	@BeforeMethod
	public void beforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		magAc.signIn(DATA_USER1, DATA_PASS);
		dialog = new Dialog(driver);
		goToWiki();
	}

	@AfterMethod
	public void afterTest(){
		//signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 *Case ID:78339.
	 *Test Case Name: Edit permissions by removing all for a user.
	 *Pre-Condition: - wiki page PageA is already created
	- User A already has permissions: View Pages, Edit Pages in Page Permissions
	- User A does not belong to any groups who have View Pages, Edit Pages in Page Permissions
	 *Post-Condition: 
	 *Done with OSs and browsers : 
	 *Generated by phuongdt at 2014/09/19 12:00:45
	 */
	@Test
	public  void test01_EditPermissionsByRemovingAllForAUser() {
		info("Test 1: Edit permissions by removing all for a user");
		String title = "title78339";
		String content = "content78339";
		String new_content = "newcontent78339";
		By element_page = By.linkText(title);
		String user = DATA_USER3;
		String[] userGroup = {user};

		info("Add a wiki page");
		goToWiki();
		addBlankWikiPage(title, content, 0);

		info("Add user has permission default");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		addPagePermission(1, userGroup);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);

		info("Add edit page permission for " + user);
		editPagePermission(user, true, true);
		goToPermalink();
		String permalink = getPermalink();
		dialog.closeMessageDialog();

		info("Check user can view/edit wiki page");
		magAc.signOut();
		magAc.signIn(user, DATA_PASS);
		checkViewEditPage(element_page, content, new_content);

		magAc.signOut();
		magAc.signIn(DATA_USER1, DATA_PASS);

		/*Step Number: 1
		 *Step Name: Step 1: Open Page Permissions form
		 *Step Description: 
			- Open wiki page PageA
			- Go to More 
			-
			-> Page Permissions
		 *Input Data: 

		 *Expected Outcome: 
			- Page Permissions form displays*/

		/*Step number: 2
		 *Step Name: Step 2: Remove all permissions for User A
		 *Step Description: 
			- Check User A permissions
			- Untick View Pages and Edit Pages checkboxes corresponding to User A
			- Click Save
		 *Input Data: 

		 *Expected Outcome: 
			- View Pages and Edit Pages are deselected
			- User A is removed from Page Permissions form*/
		goToWikiPage("Wiki Home/" + title, ManageAccount.userType.ADMIN);
		editPagePermission(user, false, false);
		magAc.signOut();

		/*Step number: 3
		 *Step Name: Step 3: Check if User A has permission
		 *Step Description: 
			- Log in as User A
			- Go to space 
			-
			-> Wiki
		 *Input Data: 

		 *Expected Outcome: 
			User A will not see PageA in the left panel. If User A use the direct link of PageA, "Page Not Found" is shown*/
		magAc.signIn(user, DATA_PASS);
		goToWiki();
		waitForElementNotPresent(By.linkText(title));
		magAc.signOut();
		goToWikiByPermalink(user, permalink, false, content);

		//Clear data
		info("Clear data");
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);

	}

	/**
	 *Case ID:78341.
	 *Test Case Name: Update permissions from Edit Pages to View Pages for a user.
	 *Pre-Condition: - wiki page PageA is already created
	- User A has already permissions: View Pages, Edit Pages in Page Permissions
	- User A does not belong to any groups who have Edit Pages permissions in Page Permissions
	 *Post-Condition: 
	 *Done with OSs and browsers : 
	 *Generated by phuongdt at 2014/09/19 12:00:45
	 */
	@Test
	public  void test02_UpdatePermissionsFromEditPagesToViewPagesForAUser() {
		info("Test 2: Update permissions from Edit Pages to View Pages for a user");
		String title = "title78341";
		String content = "content78341";
		String new_content = "newcontent78341";
		By element_page = By.linkText(title);
		String user = DATA_USER3;
		String[] userGroup = {user};

		info("Add a wiki page");
		goToWiki();
		addBlankWikiPage(title, content, 0);

		info("Add user has permission default");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		addPagePermission(1, userGroup);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);

		info("Add edit page permission for " + user);
		editPagePermission(user, true, true);

		info("Check user can view/edit wiki page");
		magAc.signOut();
		magAc.signIn(user, DATA_PASS);
		checkViewEditPage(element_page, content, new_content);

		magAc.signOut();
		magAc.signIn(DATA_USER1, DATA_PASS);

		/*Step Number: 1
		 *Step Name: Step 1: Open Page Permissions form
		 *Step Description: 
			- Open the wiki page PageA
			- Select More 
			-
			-> Page Permissions
		 *Input Data: 

		 *Expected Outcome: 
			- Page Permissions form displays*/

		/*Step number: 2
		 *Step Name: Step 2: Change permissions of User A
		 *Step Description: 
			- Check the User A permissions
			- In the Page Permissions form, untick Edit Pages checkbox corresponding to User A
			- Click Save
		 *Input Data: 

		 *Expected Outcome: 
			Edit Pages checkbox is deselected*/
		goToWikiPage("Wiki Home/" + title, ManageAccount.userType.ADMIN);
		editPagePermission(user, true, false);
		magAc.signOut();

		/*Step number: 3
		 *Step Name: Step 3: Check if User A has Edit Pages permission
		 *Step Description: 
			- Log in as User A
			- Go to space 
			-
			-> Wiki 
			-
			-> PageA
		 *Input Data: 

		 *Expected Outcome: 
			The User A can view PageA but not see Edit Page, Add Page; Move Page and Delete Page from More menu*/ 
		magAc.signIn(user, DATA_PASS);
		goToWiki();
		click(By.linkText(title));
		waitForElementNotPresent(ELEMENT_EDIT_PAGE_LINK);
		waitForElementNotPresent(ELEMENT_ADD_PAGE_LINK);
		click(ELEMENT_MORE_LINK);
		waitForElementNotPresent(ELEMENT_DELETE_LINK);
		waitForElementNotPresent(ELEMENT_MOVE_PAGE_LINK);

		//Clear data
		info("Clear data");
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}

	/**
	 *Case ID:113651.
	 *Test Case Name: Update permissions from Edit Pages to View Pages for a group.
	 *Pre-Condition: - wiki page PageA is already created
	- Group A has already permissions: View Pages, Edit Pages in Page Permissions
	- Member of Group A does not belong to any groups who have Edit Pages permissions in Page Permissions
	- Member of Group A does not have Edit Pages in Page Permissions
	 *Post-Condition: 
	 *Done with OSs and browsers : 
	 *Generated by phuongdt at 2014/09/19 12:00:45
	 */
	@Test
	public  void test03_UpdatePermissionsFromEditPagesToViewPagesForAGroup() {
		info("Test 3: Update permissions from Edit Pages to View Pages for a group");
		String title = "title113651";
		String content = "content113651";
		String new_content = "newcontent113651";
		By element_page = By.linkText(title);
		String user = DATA_USER3;
		String path = "*:/organization/employees";
		String[] userGroup = {path};

		info("Add a wiki page");
		goToWiki();
		addBlankWikiPage(title, content, 0);

		info("Add user has permission default");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		addPagePermission(0, userGroup);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);

		info("Add edit page permission for " + user);
		editPagePermission(path, true, true);

		info("Check user can view/edit wiki page");
		magAc.signOut();
		magAc.signIn(user, DATA_PASS);
		checkViewEditPage(element_page, content, new_content);

		magAc.signOut();
		magAc.signIn(DATA_USER1, DATA_PASS);
		/*Step Number: 1
		 *Step Name: Step 1: Open Page Permissions form
		 *Step Description: 
			- Open the wiki page PageA
			- Select More 
			-
			-> Page Permissions
		 *Input Data: 

		 *Expected Outcome: 
			- Page Permissions form displays*/

		/*Step number: 2
		 *Step Name: Step 2: Change permissions of Group A
		 *Step Description: 
			- Check the Group A permissions
			- In the Page Permissions form, untick Edit Pages checkbox corresponding to Group A
			- Click Save
		 *Input Data: 

		 *Expected Outcome: 
			Edit Pages checkbox is deselected*/
		goToWikiPage("Wiki Home/" + title, ManageAccount.userType.ADMIN);
		editPagePermission(path, true, false);
		magAc.signOut();

		/*Step number: 3
		 *Step Name: Step 3: Check if Group A has Edit Pages permission
		 *Step Description: 
			- Log in as user member of Group A
			- Go to space 
			-
			-> Wiki 
			-
			-> PageA
		 *Input Data: 

		 *Expected Outcome: 
			The user can view PageA but not see Edit Page, Add Page; Move Page and Delete Page from More menu*/ 
		magAc.signIn(user, DATA_PASS);
		goToWiki();
		click(By.linkText(title));
		waitForElementNotPresent(ELEMENT_EDIT_PAGE_LINK);
		waitForElementNotPresent(ELEMENT_ADD_PAGE_LINK);
		click(ELEMENT_MORE_LINK);
		waitForElementNotPresent(ELEMENT_DELETE_LINK);
		waitForElementNotPresent(ELEMENT_MOVE_PAGE_LINK);

		//Clear data
		info("Clear data");
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}

	/**
	 *Case ID:113652.
	 *Test Case Name: Edit permissions by removing all for a group.
	 *Pre-Condition: - wiki page PageA is already created
	- Group A already has permissions: View Pages, Edit Pages in Page Permissions
	- Member of Group A does not belong to any groups who have View Pages, Edit Pages in Page Permissions
	- Member of Group A does not have permissions: View Pages, Edit Pages in Page Permissions
	 *Post-Condition: 
	 *Done with OSs and browsers : 
	 *Generated by phuongdt at 2014/09/19 12:00:45
	 */
	@Test
	public  void test04_EditPermissionsByRemovingAllForAGroup() {
		info("Test 4: Edit permissions by removing all for a group");
		String title = "title113652";
		String content = "content113652";
		String new_content = "newcontent113652";
		By element_page = By.linkText(title);
		String user = DATA_USER3;
		String path = "*:/organization/employees";
		String[] userGroup = {path};

		info("Add a wiki page");
		goToWiki();
		addBlankWikiPage(title, content, 0);

		info("Add user has permission default");
		goToPermalink();
		String permalink = getPermalink();
		dialog.closeMessageDialog();
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		addPagePermission(0, userGroup);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);

		info("Add edit page permission for " + user);
		editPagePermission(path, true, true);

		info("Check user can view/edit wiki page");
		magAc.signOut();
		magAc.signIn(user, DATA_PASS);
		checkViewEditPage(element_page, content, new_content);

		magAc.signOut();
		magAc.signIn(DATA_USER1, DATA_PASS);
		/*Step Number: 1
		 *Step Name: Step 1: Open Page Permissions form
		 *Step Description: 
			- Open wiki page PageA
			- Go to More 
			-
			-> Page Permissions
		 *Input Data: 

		 *Expected Outcome: 
			- Page Permissions form displays*/

		/*Step number: 2
		 *Step Name: Step 2: Remove all permissions for Group A
		 *Step Description: 
			- Check Group A permissions
			- Untick View Pages and Edit Pages checkboxes corresponding to Group A
			- Click Save
		 *Input Data: 

		 *Expected Outcome: 
			- View Pages and Edit Pages are deselected
			- Group A is removed from Page Permissions form*/
		goToWikiPage("Wiki Home/" + title, ManageAccount.userType.ADMIN);
		editPagePermission(path, false, false);
		magAc.signOut();
		
		/*Step number: 3
		 *Step Name: Step 3: Check if Group A has permission
		 *Step Description: 
			- Log in as user member of Group A
			- Go to space 
			-
			-> Wiki
		 *Input Data: 

		 *Expected Outcome: 
			The user will not see PageA in the left panel. If the user uses the direct link of PageA, "Page Not Found" is shown*/
		magAc.signIn(user, DATA_PASS);
		goToWiki();
		waitForElementNotPresent(By.linkText(title));
		magAc.signOut();
		goToWikiByPermalink(user, permalink, false, content);

		//Clear data
		info("Clear data");
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);

	}
}