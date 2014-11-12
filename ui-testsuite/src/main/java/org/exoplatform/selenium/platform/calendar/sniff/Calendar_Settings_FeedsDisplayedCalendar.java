package org.exoplatform.selenium.platform.calendar.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.calendar.CalendarBase;
import org.exoplatform.selenium.platform.calendar.Event;
import org.exoplatform.selenium.platform.calendar.Task;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author hangNTT
 * @date 18 Oct 2013
 */
public class Calendar_Settings_FeedsDisplayedCalendar extends CalendarBase{
	ManageAccount acc;
	Event evt;
	Task tsk;
	Button btn;

	@BeforeMethod
	public void setUpBeforeTest(){
		getDriverAutoSave();
		acc = new ManageAccount(driver);
		evt = new Event(driver);
		tsk = new Task(driver);
		btn = new Button(driver);
		acc.signIn(DATA_USER1, DATA_PASS);
		goToCalendarPage();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Display Calendar
	 * CaseID 74836
	 */
	@Test
	public void test01_DisplayCalendar() {

		String calendar = "ShareCalendar";
		String[] user = {DATA_USER2};
		boolean[] canEdit = {true};

		info("--Add share calendar--");
		addCalendar(calendar,calendar,"red");
		shareCalendar(calendar,user,canEdit);

		info("--Go to calendar Settings --");
		goToCalendarSettings();
		waitForAndGetElement(ELEMENT_CALENDAR_POPUP_WINDOW);
		waitForAndGetElement(ELEMENT_SETTINGS_TAB);

		info("--Verify display calendar tab--");
		click (ELEMENT_DISPLAYED_CALENDAR);
		waitForAndGetElement(ELEMENT_DISPLAYED_CALENDAR);

		info("--Verify feeds tab--");
		click(ELEMENT_FEEDS);
		waitForAndGetElement(ELEMENT_FEEDS);

		info("-- Check Displayed Calendar tab--");
		click (ELEMENT_DISPLAYED_CALENDAR);
		waitForAndGetElement(ELEMENT_CALENDAR_TAB_DEFAULT);

		info("--Verify personal and share calendar--");
		waitForAndGetElement(ELEMENT_PERSONAL_CALENDAR);
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","John Smith").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "iconCheckBox checkbox")));
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","ShareCalendar").replace("${CheckboxColor}", "red").replace("${checkicon}", "iconCheckBox checkbox")));

		info("--Verify group calendar--");
		waitForAndGetElement(ELEMENT_GROUP_CALENDAR);
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","Employees").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "iconCheckBox checkbox")));
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","Administration").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "iconCheckBox checkbox")));
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","Content Management").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "iconCheckBox checkbox")));
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","Users").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "iconCheckBox checkbox")));
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","Development").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "iconCheckBox checkbox")));
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","Executive Board").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "iconCheckBox checkbox")));

		info("--Setup to hide specific calendar--");
		if(waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","John Smith").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "iconCheckBox checkbox")),DEFAULT_TIMEOUT,0)!=null){
			click(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","John Smith").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "iconCheckBox checkbox")));
			waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","John Smith").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "checkbox iconUnCheckBox")));
		}
		click(ELEMENT_SETTINGS_FORM_SAVE_BUTTON);
		waitForElementNotPresent(By.xpath(ELEMENT_VERIFY_CALENDAR_FORM.replace("${UserName}","John Smith").replace("${CheckboxColor}", "asparagus")));

		info("--Setup to Show specific calendar--");
		goToCalendarSettings();
		click (ELEMENT_DISPLAYED_CALENDAR);
		if(waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","John Smith").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "iconUnCheckBox checkbox")),DEFAULT_TIMEOUT,0)!=null){
			click(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","John Smith").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "iconUnCheckBox checkbox")));
			waitForElementNotPresent(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","John Smith").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "checkbox iconUnCheckBox")));
		}
		click(ELEMENT_SETTINGS_FORM_SAVE_BUTTON);
		waitForAndGetElement(ELEMENT_DISPLAY_CALENDAR.replace("${calendar}", "John Smith"));
		info("--Delete Calendar--");
		deleteCalendar(calendar);
	}

	/**
	 * Feeds
	 * CaseID 74837,74838,74841
	 */
	@Test
	public void test02_Feed(){
		String eventName = "eventfeeds";
		String namefeed = "test";
		String[] userGroup = {"John Smith"};
		String newfeed = "newtest";

		info("--Add event--");
		evt.addQuickEvent(eventName,eventName,getDate(1,"MM/dd/yyyy"),getDate(1,"MM/dd/yyyy"),false);
		Utils.pause(3000);
		info("--Add new feeds --");
		addFeeds(namefeed, userGroup,2);
		Utils.pause(3000);
		info("--edit feeds--");
		editFeeds(namefeed,newfeed, userGroup, 2);
		Utils.pause(3000);
		info("--Delete feeds--");
		deleteFeeds(newfeed);
		Utils.pause(3000);
		info("--Delete event--");
		deleteEventTask(eventName, selectDayOption.ONEDAY);
	}
}