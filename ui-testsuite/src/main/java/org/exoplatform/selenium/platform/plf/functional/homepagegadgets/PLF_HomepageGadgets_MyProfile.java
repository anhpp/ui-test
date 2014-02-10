package org.exoplatform.selenium.platform.plf.functional.homepagegadgets;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.HomePageGadget;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageApplications;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.social.Activity;
import org.exoplatform.selenium.platform.social.PeopleProfile;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

/**
 * @author HangNTT
 *
 */
public class PLF_HomepageGadgets_MyProfile extends Activity{

	ManageAccount acc; 
	NavigationToolbar naviToolbar; 
	PeopleProfile peoPro;
	ManageApplications app;
	PageEditor pageEditor;
	HomePageGadget hpGadget;

	@BeforeMethod
	public void beforeMethods(){	
		initSeleniumTest();
		driver.get(baseUrl);
		naviToolbar = new NavigationToolbar(driver, this.plfVersion);
		peoPro = new PeopleProfile (driver, this.plfVersion);
		app = new ManageApplications(driver, this.plfVersion);
		acc = new ManageAccount(driver, this.plfVersion);
		pageEditor = new PageEditor(driver, this.plfVersion);
		hpGadget = new HomePageGadget(driver, this.plfVersion);
		acc.signIn(DATA_USER1, DATA_PASS);		
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Case ID:74317.
	 * Test Case Name: Check the displaying of information in My Profile gadget after updating avatar.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by phuongdt at 2014/02/11 09:32:45
	 */
	@Test
	public  void test01_CheckTheDisplayingOfInformationInMyProfileGadgetAfterUpdatingAvatar() {
		String file = "ECMS_DMS_SE_Upload_imgfile.jpg";
		String firstName="John";
		String lastName = "Smith";

		//Add my profile gadget to homepage
		naviToolbar.goToHomePage();
		//Add My Profile gadget
		naviToolbar.goToApplicationRegistry();
		// Import all application
		app.importApplication();
		naviToolbar.goToHomePage();
		naviToolbar.goToEditPageEditor();
		click(ELEMENT_CATEGORY_GADGETS);
		click(ELEMENT_SWITCH_VIEW_MODE);
		dragAndDropToObject(hpGadget.ELEMENT_APPLICATION_MY_PROFILE, hpGadget.ELEMENT_MIDDLE_CONTAINER);
		pageEditor.finishEditLayout();

		//Get old avatar
		naviToolbar.goToHomePage();
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_GADGET_WORKSPACE_FRAME));
		WebElement element = waitForAndGetElement(hpGadget.ELEMENT_PROFILE_PICTURE_IN_MY_PROFILE_GADGET);
		String oldpic = element.getAttribute("src"); 
		driver.switchTo().defaultContent();

		info("Test 1: Check the displaying of information in My Profile gadget after updating avatar");
		/*Step Number: 1
		 *Step Name: -
		 *Step Description: 
			Step 1: Update avatar
		 *Input Data: 
			- Go to intranet site
			- Click on Edit my profile link on My profile gadget
			- Click on Change Picture profile
			- Browse a file and click on Confirm button
			- Click on Save
		 *Expected Outcome: 
			New avatar is added*/
		naviToolbar.goToMyProfile();

		//Get old avatar
		element = waitForAndGetElement(By.xpath(ELEMENT_GET_URL_IMAGE.replace("${name}", firstName+" "+lastName)));
		String oldsrc = element.getAttribute("src"); 
		peoPro.changeAvatar("TestData/"+file);
		//User has new avatar
		element = waitForAndGetElement(By.xpath(ELEMENT_GET_URL_IMAGE.replace("${name}", firstName+" "+lastName)));
		String newsrc = element.getAttribute("src");
		assert (!oldsrc.contentEquals(newsrc));

		/*Step number: 2
		 *Step Name: -
		 *Step Description: 
			Step 2: Check the displaying of new avatar in My Profile gadget
		 *Input Data: 
			- Back to intranet site
		 *Expected Outcome: 
			New avatar is added in My Profile gadget*/ 
		naviToolbar.goToHomePage();
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_GADGET_WORKSPACE_FRAME));
		element = waitForAndGetElement(hpGadget.ELEMENT_PROFILE_PICTURE_IN_MY_PROFILE_GADGET);
		String newpic = element.getAttribute("src"); 
		assert (!oldpic.contentEquals(newpic));
		driver.switchTo().defaultContent();

		/*Clear data*/
		info("Clear data");
		naviToolbar.goToEditPageEditor();
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForAndGetElement(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);
		pageEditor.removePortlet(hpGadget.ELEMENT_GADGET_PORLET_IN_MIDDLE_HOME_PAGE, hpGadget.ELEMENT_DELETE_ICON_GADGET_PORLET_IN_MIDDLE_HOME_PAGE);
	}

	/**
	 * Case ID:74318.
	 * Test Case Name: Check the displaying of information in My Profile gadget after updating first name.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by phuongdt at 2014/02/11 09:32:45
	 */
	@Test
	public  void test02_CheckTheDisplayingOfInformationInMyProfileGadgetAfterUpdatingFirstName() {
		String firstName="John";
		String lastName = "Smith";
		String newFirstName = "John updated";
		String fullName = firstName + " " + lastName;
		String newFullName = newFirstName + " " + lastName;

		//Add my profile gadget to homepage
		naviToolbar.goToHomePage();
		//Add My Profile gadget
		naviToolbar.goToApplicationRegistry();
		// Import all application
		app.importApplication();
		naviToolbar.goToHomePage();
		naviToolbar.goToEditPageEditor();
		click(ELEMENT_CATEGORY_GADGETS);
		click(ELEMENT_SWITCH_VIEW_MODE);
		dragAndDropToObject(hpGadget.ELEMENT_APPLICATION_MY_PROFILE,hpGadget.ELEMENT_MIDDLE_CONTAINER);
		pageEditor.finishEditLayout();

		//Get old first name
		naviToolbar.goToHomePage();
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_GADGET_WORKSPACE_FRAME));
		WebElement element = waitForAndGetElement(hpGadget.ELEMENT_PROFILE_INFO_IN_MY_PROFILE_GADGET);
		String oldName = element.getText().trim();
		assert oldName.contains(fullName);
		driver.switchTo().defaultContent();

		info("Test 2: Check the displaying of information in My Profile gadget after updating first name");
		/*Step Number: 1
		 *Step Name: -
		 *Step Description: 
			Step 1: Update first name
		 *Input Data: 
			- Go to intranet site
			- Click on Edit my profile link on My profile gadget
			- Click on Edit link of Basic Information
			- Change the first name
			- Click on Save
		 *Expected Outcome: 
			First name is updated*/
		naviToolbar.goToMyProfile();
		peoPro.editUserBasicInformation(newFirstName, "", "");

		/*Step number: 2
		 *Step Name: -
		 *Step Description: 
			Step 2: Check the changing of first name in My Profile gadget
		 *Input Data: 
			- Back to intranet site
		 *Expected Outcome: 
			First name is updated*/ 
		naviToolbar.goToHomePage();
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_GADGET_WORKSPACE_FRAME));
		element = waitForAndGetElement(hpGadget.ELEMENT_PROFILE_INFO_IN_MY_PROFILE_GADGET);
		String newName = element.getText().trim();
		assert newName.contains(newFullName);
		assert !newName.contains(fullName);
		driver.switchTo().defaultContent();

		/*Clear data*/
		info("Clear data");
		naviToolbar.goToEditPageEditor();
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForAndGetElement(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);
		pageEditor.removePortlet(hpGadget.ELEMENT_GADGET_PORLET_IN_MIDDLE_HOME_PAGE, hpGadget.ELEMENT_DELETE_ICON_GADGET_PORLET_IN_MIDDLE_HOME_PAGE);
		naviToolbar.goToMyProfile();
		peoPro.editUserBasicInformation(firstName, "", "");
	}

	/**
	 * Case ID:74319.
	 * Test Case Name: Check the displaying of information in My Profile gadget after updating last name.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by phuongdt at 2014/02/11 09:32:45
	 */
	@Test
	public  void test03_CheckTheDisplayingOfInformationInMyProfileGadgetAfterUpdatingLastName() {
		String firstName="John";
		String lastName = "Smith";
		String newLastName = "new Smith updated";
		String fullName = firstName + " " + lastName;
		String newFullName = firstName + " " + newLastName;

		//Add my profile gadget to homepage
		naviToolbar.goToHomePage();
		//Add My Profile gadget
		naviToolbar.goToApplicationRegistry();
		// Import all application
		app.importApplication();
		naviToolbar.goToHomePage();
		naviToolbar.goToEditPageEditor();
		click(ELEMENT_CATEGORY_GADGETS);
		click(ELEMENT_SWITCH_VIEW_MODE);
		dragAndDropToObject(hpGadget.ELEMENT_APPLICATION_MY_PROFILE, hpGadget.ELEMENT_MIDDLE_CONTAINER);
		pageEditor.finishEditLayout();

		//Get old first name
		naviToolbar.goToHomePage();
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_GADGET_WORKSPACE_FRAME));
		WebElement element = waitForAndGetElement(hpGadget.ELEMENT_PROFILE_INFO_IN_MY_PROFILE_GADGET);
		String oldName = element.getText().trim();
		assert oldName.contains(fullName);
		driver.switchTo().defaultContent();

		info("Test 3: Check the displaying of information in My Profile gadget after updating last name");
		/*Step Number: 1
		 *Step Name: -
		 *Step Description: 
			Step 1: Update lastname
		 *Input Data: 
			- Go to intranet site
			- Click on Edit my profile link on My profile gadget
			- Click on Edit link of Basic Information
			- Change the last name
			- Click on Save
		 *Expected Outcome: 
			Last name is updated*/
		naviToolbar.goToMyProfile();
		peoPro.editUserBasicInformation("", newLastName, "");

		/*Step number: 2
		 *Step Name: -
		 *Step Description: 
			Step 2: Check the changing of last name in My Profile gadget
		 *Input Data: 
			- Back to intranet site
		 *Expected Outcome: 
			Last name is updated*/ 
		naviToolbar.goToHomePage();
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_GADGET_WORKSPACE_FRAME));
		element = waitForAndGetElement(hpGadget.ELEMENT_PROFILE_INFO_IN_MY_PROFILE_GADGET);
		String newName = element.getText().trim();
		assert newName.contains(newFullName);
		assert !newName.contains(fullName);
		driver.switchTo().defaultContent();

		/*Clear data*/
		info("Clear data");
		naviToolbar.goToEditPageEditor();
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForAndGetElement(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);
		pageEditor.removePortlet(hpGadget.ELEMENT_GADGET_PORLET_IN_MIDDLE_HOME_PAGE, hpGadget.ELEMENT_DELETE_ICON_GADGET_PORLET_IN_MIDDLE_HOME_PAGE);
		naviToolbar.goToMyProfile();
		peoPro.editUserBasicInformation("", lastName, "");
	}
}


