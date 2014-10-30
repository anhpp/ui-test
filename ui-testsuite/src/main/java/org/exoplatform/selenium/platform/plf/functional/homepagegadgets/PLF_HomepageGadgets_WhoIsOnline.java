package org.exoplatform.selenium.platform.plf.functional.homepagegadgets;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.platform.HomePageGadget;
import org.exoplatform.selenium.platform.social.Activity;
import org.exoplatform.selenium.platform.social.PeopleConnection;
import org.exoplatform.selenium.platform.social.PeopleSearch;

/**
 * @author HaVTT
 * date: 10/02/2014
 *    
 */

public class PLF_HomepageGadgets_WhoIsOnline extends Activity {

	ManageAccount acc;
	HomePageGadget hg;
	NavigationToolbar navToolBar;
	PeopleConnection peopleC;
	PeopleSearch peopleS;
	String user1 = "Mary Williams";

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		acc = new ManageAccount(driver, this.plfVersion);
		//hg = new HomePageGadget(driver, this.plfVersion);
		peopleC = new PeopleConnection(driver, this.plfVersion);
		peopleS = new PeopleSearch(driver);
		navToolBar = new NavigationToolbar(driver, this.plfVersion);
		acc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterTest(){
//		driver.manage().deleteAllCookies();
//		driver.quit();
	}

	/**
	 * CaseID 79683
	 * Check the display of online user on Who'sOnline gadget
	 * 
	 * CaseID 79684
	 * Display pop up informations for user who requested a connection
	 * 
	 */
	@Test
	public void test01_checkDisplayOfOnlineUser(){				
		info("USER2: Switch to other window to login");
		loginWithAnotherAccOnThesameBrowser(DATA_USER2, DATA_PASS);
		hg=new HomePageGadget(newDriver);
		newDriver.navigate().refresh();

		info("USER2: 79683 - Check pop-up information of online user");
		waitForAndGetElement(hg.ELEMENT_WHOISONLINE_GADGET,5000,1,2,newDriver);
		Utils.pause(3000);
		WebElement myElement =newDriver.findElement(By.xpath(".//*[@class='avatarXSmall' and @href='/portal/intranet/profile/john']"));
		Actions builder = new Actions(newDriver);
		builder.moveToElement(myElement).build().perform();
		info("Confirm user avatar");
		waitForAndGetElement(hg.ELEMENT_ONLINE_USER_ACC_IMG.replace("${acc}","john"), 5000, 1, 2, newDriver);
		info("Confirm user name");
		waitForAndGetElement(hg.ELEMENT_ONLINE_USER_TITLE.replace("${acc}", "john"), 5000, 1, 2, newDriver);

		info("USER2: Go to My Connection");
		navToolBar = new NavigationToolbar(newDriver);
		navToolBar.goToConnectionPage();

		info("USER2: Connect with user acc 2");
		peopleC = new PeopleConnection(newDriver);
		peopleC.connectPeople("John Smith");		

		info("USER2: Go to Homepage Intranet");
		navToolBar.goToHomePage();

		info("USER2: 79684 - Check pop-up information after sending a connection request");
		hg.checkUserInfoOnWhoisOnlineGadget(DATA_USER1, false, "", true, false);


		info("--Ignore Connection--");
		navToolBar.goToConnectionPage();
		peopleC.cancelRequest("John Smith");
		info("--Close the 2nd browser window--");
		Utils.pause(500);
		newDriver.manage().deleteAllCookies();
		newDriver.quit();
		this.driver.quit();

	}

	/**
	 * CaseID 79685
	 * Check the popup information of online user who is a connection
	 * 
	 * CaseID 79686
	 * Display pop up informations for user who's invited to connect
	 */
	@Test
	public void test02_checkDisplayOfOnlineConnectedUser(){
		String activity = "HelloWorld79685";

		info("USER1: Share a status on activity stream");
		addActivity(true, activity, false,"");

		info("USER1: Go to My Connection");
		navToolBar.goToConnectionPage();

		info("USER1: Connect with user acc 2");
		peopleC.connectPeople("Mary Williams");

		info("USER2: Switch to other window to login");
		loginWithAnotherAccOnThesameBrowser(DATA_USER2, DATA_PASS);
		hg=new HomePageGadget(newDriver, this.plfVersion);

		info("USER2: 79686 - Check popup information of online user when receiving a connection request");
		hg.checkUserInfoOnWhoisOnlineGadget(DATA_USER1, true, activity, true, true);

		info("USER2: Accept invitation from user acc 1");
		hg.acceptInvitationGadget("John Smith");

		info("USER2: 79685 - Check popup information of online user 1 when having a connection");
		hg.checkUserInfoOnWhoisOnlineGadget(DATA_USER1, true, activity, true, true);

		info("Restore data");
		info("--Cancel Connection--");
		peopleC = new PeopleConnection(newDriver, this.plfVersion);
		navToolBar = new NavigationToolbar(newDriver, this.plfVersion);
		navToolBar.goToConnectionPage();
		peopleS.searchPeople(true,"John Smith");
		peopleC.removeConnection("John Smith");
		info("--Close the 2nd browser window--");
		Utils.pause(500);
		newDriver.manage().deleteAllCookies();
		newDriver.quit();
		this.driver.quit();
	}

	/**
	 * CaseID 79678
	 * Connect to User from the information pop up
	 * 
	 * CaseID 79682
	 * Display a long title of an activity in pop up for a connected user
	 * Bug: https://jira.exoplatform.org/browse/PLF-4280
	 */
	@Test(groups="error")
	public void test03_checkUpdateOfOnlineUser(){
		String longActivity = "Merry Xmas and Happy New Year 2014 to every body all around the world. We wish you a merry xmas and a happy new year with lots of joy and happiness and luck !!!";

		info("USER1: Share a status on activity stream");
		addActivity(true, longActivity, false,"");

		info("USER2: Switch to other window to login");
		loginWithAnotherAccOnThesameBrowser(DATA_USER2, DATA_PASS);
		hg=new HomePageGadget(newDriver, this.plfVersion);
		newDriver.navigate().refresh();

		info("USER2: Check updated long status of user acc 1");
		hg.checkTruncatedStatusOnWhoIsOnlineGadget(DATA_USER1);

		info("USER2: Connect to other acc from Who'sOnline");
		hg.connectPeoplefromWhoisOnlineGadget(DATA_USER1);

		info("Restore data");
		info("--Cancel Connection--");
		peopleC = new PeopleConnection(newDriver, this.plfVersion);
		navToolBar = new NavigationToolbar(newDriver, this.plfVersion);
		navToolBar.goToConnectionPage();
		peopleS.searchPeople(true,"John Smith");
		peopleC.removeConnection("John Smith");
		info("--Close the 2nd browser window--");
		Utils.pause(500);
		newDriver.manage().deleteAllCookies();
		newDriver.quit();
		this.driver.quit();
	}

	/**@date 24/4/2014
	 * @author lientm: insert
	 * CaseID 79677
	 * Automatic refresh of the Gadget "Who's online?"
	 **/
	@Test
	public void test04_AutomaticRefresh(){

		loginWithAnotherAccOnThesameBrowser(DATA_USER2, DATA_PASS);
		//user1 check user2 display on Who is online gadget after 1 minus
		Utils.pause(31000);//buffer 1s
		hg.checkUserInfoOnWhoisOnlineGadget(DATA_USER2, user1, null, false, null, 0);
		//user 2 logout
		acc = new ManageAccount(newDriver);
		acc.signOut();
		newDriver.manage().deleteAllCookies();
		newDriver.quit();
		//user1 check user2 not display on who is online gadget after 1 minus

		Utils.pause(31000);//buffer 1s

		waitForElementNotPresent(hg.ELEMENT_ONLINE_USER_AVATAR.replace("${acc}",DATA_USER1));

	}	
}
