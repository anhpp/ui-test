package org.exoplatform.selenium.platform.plf.functional.homepagegadgets.calendargadget;

import static org.exoplatform.selenium.TestLogger.info;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.calendar.CalendarBase;
import org.exoplatform.selenium.platform.calendar.Task;
import org.exoplatform.selenium.platform.calendar.Event;
import org.testng.annotations.*;

/**
 * @author chinhdtt
 * @date 12 Feb 2014
 */
public class PLF_HomepageGadgets_CalendarGadget_General extends CalendarBase{
	ManageAccount acc; 
	NavigationToolbar nav; 
	String user = "John Smith";
	String user1= "Jack Miller";	
	String user2= "Mary Williams";
	Event event; 
	Task task;

	@BeforeMethod
	public void beforeMethods(){	
		initSeleniumTest();
		driver.get(baseUrl);
		acc = new ManageAccount(driver, this.plfVersion);
		nav = new NavigationToolbar(driver, this.plfVersion);	
		task = new Task(driver, this.plfVersion);
		event = new Event(driver, this.plfVersion);
		acc.signIn(DATA_USER1, DATA_PASS);		
		button = new Button(driver, this.plfVersion);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Case ID:69098.
	 * Test Case Name: Display the label of Gadget Calendar.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/02/12 16:55:48
	 */
	@Test
	public  void test01_DisplayTheLabelOfGadgetCalendar() {
		info("Test 1: Display the label of Gadget Calendar");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		/*
		- Connect to Intranet
		- Change the Language to English
		 *Input Data: 
		 *Expected Outcome: In the right gadget level, the gadget calendar is displayed with a label: "TODAY: MM/DD/YY"		*/ 
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_TODAY_LABEL).getText().equalsIgnoreCase(dateFormat.format(date));
	}

	/**
	 * Case ID:69101.
	 * Test Case Name: Switch to next day in the Calendar gadget.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/02/12 16:55:48
	 */
	@Test
	public  void test02_SwitchToNextDayInTheCalendarGadget() {
		info("Test 2: Switch to next day in the Calendar gadget");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		/*
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: The day displayed is the the current day: TODAY: MM/DD/YY		*/
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_TODAY_LABEL).getText().equalsIgnoreCase(dateFormat.format(date));

		/*
		- From the gadget Calendar, click on the right arrow in the title label
		 *Input Data: 
		 *Expected Outcome: The day displayed is the next day of the current day: TOMORROW: MM/DD/YY"		*/ 
		click(ELEMENT_CALENDAR_GADGET_NEXTDAY_ARROW);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_TOMORROW_LABEL).getText().equalsIgnoreCase(dateFormat.format(date));
	}

	/**
	 * Case ID:69102.
	 * Test Case Name: Switch to previous day in the Calendar gadget.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/02/12 16:55:48
	 */
	@Test
	public  void test03_SwitchToPreviousDayInTheCalendarGadget() {
		info("Test 3: Switch to previous day in the Calendar gadget");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		/*
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: The day displayed is the the current day: TODAY: MM/DD/YY		*/
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_TODAY_LABEL).getText().equalsIgnoreCase(dateFormat.format(date));

		/*
		- From the gadget Calendar, click on the left arrow in the title label
		 *Input Data: 
		 *Expected Outcome: The day displayed is the next day of the current day: YESTERDAY: MM/DD/YY"		*/ 
		click(ELEMENT_CALENDAR_GADGET_PREVIOUSDAY_ARROW);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_YESTERDAY_LABEL).getText().equalsIgnoreCase(dateFormat.format(date));
	}

	/**
	 * Case ID:69105.
	 * Test Case Name: Display Calendar with colored labels.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/02/12 16:55:48
	 */
	@Test
	public  void test04_DisplayCalendarWithColoredLabels() {
		info("Test 4: Display Calendar with colored labels");
		String calendar1 = "Cal 69105 "+getRandomString();
		String calendar2 = "Cal 69105 "+getRandomString();
		String event1 = "Event1";
		String event2 = "Event2";

		/*
		- Connect to Intranet
		- Open the Calendar application
		- Add Calendars with differents colors
		- Add events for each calendars
		 *Input Data: 
		 *Expected Outcome: Calendars are added with colored labels		*/
		info("Create calendars");
		goToCalendarPage();
		addCalendar(calendar1, calendar1, "red colorCell");
		Utils.pause(5000);
		event.addQuickEvent(event1,"", getDate(0,"MM/dd/yyyy"), getDate(0,"MM/dd/yyyy"), false, calendar1);
		info("Add event2");
		goToCalendarPage();
		addCalendar(calendar2, calendar2, "navy_blue colorCell");
		Utils.pause(5000);
		event.addQuickEvent(event2,"", getDate(0,"MM/dd/yyyy"), getDate(0,"MM/dd/yyyy"), false, calendar2);

		/*
		- Back to Homepage
		 *Input Data: 
		 *Expected Outcome: In the Calendar gadget, calendars are displayed in a colored labelsThe color of the label is the one chosen in the calendar application		*/ 
		nav.goToHomePage();
		info("Check color");
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}",calendar1));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_CALENDAR_COLOR.replace("${color}", "calendarName red").replace("${calendar}",calendar1));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}",calendar2));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_CALENDAR_COLOR.replace("${color}", "calendarName navy_blue").replace("${calendar}",calendar2));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_EVENT_ITEM.replace("${event}", event1));

		//Delete data test
		info("Clear data");
		goToCalendarPage();
		deleteCalendar(calendar1,true);
		Utils.pause(500);
		deleteCalendar(calendar2,true);
	}

	/**
	 * Case ID:69106.
	 * Test Case Name: Display Calendar in Calendar Gadget.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/02/12 16:55:48
	 */
	@Test
	public  void test05_DisplayCalendarInCalendarGadget() {
		info("Test 5: Display Calendar in Calendar Gadget");
		String calendar = "Calendar 69106"+getRandomString();
		String eventName = "Event 69106";

		/*
		- Connect to Intranet
		- From the Calendar application add a calendar
		 *Input Data: 
		 *Expected Outcome: The calendar is added		*/
		info("Create calendars");
		goToCalendarPage();
		addCalendar(calendar, calendar,null);

		/*
		- Back to Homepage
		 *Input Data: 
		 *Expected Outcome: The calendar added doesn't appears in the gadget "Calendar"		*/
		nav.goToHomePage();
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_TODAY_LABEL);
		waitForTextNotPresent(calendar);

		/*
		- Back to the Calendar application, and re
		- open the same calendar
		- Add an event to the Calendar
		 *Input Data: 
		 *Expected Outcome: The event is added to the calendar		*/
		goToCalendarPage();
		Utils.pause(5000);
		event.addQuickEvent(eventName,"", getDate(0,"MM/dd/yyyy"), getDate(0,"MM/dd/yyyy"), false, calendar);

		/*
		- Back to the Homepage
		 *Input Data: 
		 *Expected Outcome: The calendar added is displayed in the gadget calendar with the corresponding created events		*/ 
		nav.goToHomePage();
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}",calendar));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_EVENT_ITEM.replace("${event}", eventName));

		//Delete data test
		goToCalendarPage();
		deleteCalendar(calendar,true);
	}

	/**
	 * Case ID:69107.
	 * Test Case Name: Display many calendars in Gadget Calendar.
	 * Pre-Condition: Calendar should contain a task or event
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/02/12 16:55:48
	 * 
	 * This case pending because can't verify calenders in many lines.
	 */
	@Test (groups = "Pending")
	public  void test06_DisplayManyCalendarsInGadgetCalendar() {
		info("Test 6: Display many calendars in Gadget Calendar");
		String calendar1 = "Cal 69107 "+getRandomString();
		String calendar2 = "Cal 69107 "+getRandomString();
		String event1 = "Event1";
		String event2 = "Event2";		

		/*
		- Connect to Intranet
		- From the calendar application, add many "Calendars" with events
		 *Input Data:
		 *Expected Outcome: Many calendars are added		*/
		info("Create calendars");
		goToCalendarPage();
		addCalendar(calendar1, calendar1,null);
		Utils.pause(5000);
		event.addQuickEvent(event1,"", getDate(0,"MM/dd/yyyy"), getDate(0,"MM/dd/yyyy"), false, calendar1);
		info("Add event2");
		goToCalendarPage();
		addCalendar(calendar2, calendar2,null);
		Utils.pause(5000);
		event.addQuickEvent(event2,"", getDate(0,"MM/dd/yyyy"), getDate(0,"MM/dd/yyyy"), false, calendar2);

		/*
		- Back to the Homepage
		 *Input Data: 
		 *Expected Outcome: Calendar labels are displayed on multiple lines		*/ 
		nav.goToHomePage();
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}",calendar1));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}",calendar2));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_EVENT_ITEM.replace("${event}", event1));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_EVENT_ITEM.replace("${event}", event2));

		//Delete data test
		goToCalendarPage();
		deleteCalendar(calendar1,true);
		Utils.pause(500);
		deleteCalendar(calendar2,true);
	}

	/**
	 * Case ID:69132.
	 * Test Case Name: Display the configuration button in Calendar Gadget.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/02/12 16:55:48
	 */
	@Test
	public  void test07_DisplayTheConfigurationButtonInCalendarGadget() {
		info("Test 7: Display the configuration button in Calendar Gadget");
		/*
		- Connect to Intranet
		- In the Calendar gadget, take over the mouse in the end right corner of the gadget
		 *Input Data: 
		 *Expected Outcome: A small configuration button appears		*/ 
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET);
		mouseOver(ELEMENT_CALENDAR_GADGET, true);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_ICON);
	}

	/**
	 * Case ID:69134.
	 * Test Case Name: Display the button "ADD" from the list of additional calendar.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/02/12 16:55:48
	 */
	@Test
	public  void test08_DisplayTheButtonADDFromTheListOfAdditionalCalendar() {
		info("Test 8: Display the button ADD from the list of additional calendar");
		String calendar = "Calendar 69134"+getRandomString();
		String eventName = "Event 69134";

		//Pre-Condition
		info("Create calendars");
		goToCalendarPage();
		addCalendar(calendar, calendar,null);Utils.pause(5000);
		event.addQuickEvent(eventName,"", getDate(0,"MM/dd/yyyy"), getDate(0,"MM/dd/yyyy"), false, calendar);
		nav.goToHomePage();
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}",calendar));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_EVENT_ITEM.replace("${event}", eventName));

		/*
		- Connect to Intranet
		- From the Calendar Gadget, click on the configuration button
		 *Input Data: 
		 *Expected Outcome: The "SETTINGS" view is displayed		*/
		info("Click on Setting");
		mouseOver(ELEMENT_CALENDAR_GADGET, true);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_ICON);
		click(ELEMENT_CALENDAR_GADGET_SETTING_ICON);
		click(ELEMENT_DELETE_CALENDAR_ICON.replace("${calendar}",calendar));

		/*
		- Take the mouse over a calendar in the additional calendar list
		 *Input Data: 
		 *Expected Outcome: The button "ADD" is displayed		*/

		/*
		- Move the mouse a way
		 *Input Data: 
		 *Expected Outcome: The button "ADD" is hidden		*/ 
		if(this.plfVersion =="4.0")		
		{
			mouseOver(ELEMENT_CALENDAR_IN_ADDITIONAL_LIST.replace("${calendar}",calendar), true);
			waitForAndGetElement(ELEMENT_ADD_CALENDAR_IN_ADDITION_LIST.replace("${calendar}",calendar));
			Utils.pause(1000);
			info("Move the mouse away");
			mouseOver(ELEMENT_CALENDAR_GADGET_VERIFIED_TEXT_LABEL, true);
			waitForElementNotPresent(ELEMENT_ADD_CALENDAR_IN_ADDITION_LIST.replace("${calendar}", calendar));
		}

		else if (this.plfVersion =="4.1") 
		{
			mouseOver(ELEMENT_CALENDAR_IN_ADDITIONAL_LIST.replace("${calendar}",calendar), true);
			waitForAndGetElement(ELEMENT_ADD_CALENDAR_IN_ADDITION_LIST_PLF_41.replace("${calendar}",calendar));
			Utils.pause(1000);
			info("Move the mouse away");
			mouseOver(ELEMENT_CALENDAR_GADGET_VERIFIED_TEXT_LABEL, true);
			waitForElementNotPresent(ELEMENT_ADD_CALENDAR_IN_ADDITION_LIST_PLF_41.replace("${calendar}",calendar));
		}

		//Delete data test
		goToCalendarPage();
		deleteCalendar(calendar,true);
	}

	/**
	 * Case ID:69140.
	 * Test Case Name: Display a calendar with a long name in Calendar gadget.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/02/12 16:55:48
	 * 
	 * Pending this case because can not auto check"..." at the end of calendar name. 
	 */
	@Test (groups = "pending")
	public  void test09_DisplayACalendarWithALongNameInCalendarGadget() {
		info("Test 9: Display a calendar with a long name in Calendar gadget");
		String calendar = "Add a long string for name Calendar of case 69140";
		String eventName = "Event 69140";

		/*
		- Connect to Intranet
		- In Calendar application, add a calendar with a long name
		 *Input Data: 
		 *Expected Outcome: The calendar is added		*/
		info("Create calendars");
		goToCalendarPage();
		addCalendar(calendar, calendar,null);		

		/*
		- Add an event to the calendar
		- Go to Homepage
		 *Input Data: 
		 *Expected Outcome: 
		- The calendar is displayed in the gadget
		- The name of the calendar istruncated and appended		*/ 
		Utils.pause(5000);
		event.addQuickEvent(eventName,"", getDate(0,"MM/dd/yyyy"), getDate(0,"MM/dd/yyyy"), false, calendar);
		nav.goToHomePage();
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}",calendar));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_EVENT_ITEM.replace("${event}", eventName));
		waitForTextPresent("Add a long string for name Calendar of case 69 ...");		

		//Delete data test
		goToCalendarPage();
		deleteCalendar(calendar,true);
	}

	/**
	 * Case ID:69194.
	 * Test Case Name: Change the Date format in Calendar Gadget.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/02/12 16:55:48
	 */
	@Test
	public  void test10_ChangeTheDateFormatInCalendarGadget() {
		info("Test 10 Change the Date format in Calendar Gadget");
		DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");

		/*
		- Connect to Intranet
		-the language of the session is "English"
		 *Input Data: 
		 *Expected Outcome: The Date format in the Calendar gadget is: MM/DD/YYYY		*/
		info("Check in English site");
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_TODAY_LABEL);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_TODAY_LABEL).getText().equalsIgnoreCase(dateFormat1.format(date));

		/*Change the language of the session to "French"
		 *Input Data: 
		 *Expected Outcome: The Date format in the Calendar gadget is: DD/MM/YYYY		*/ 
		info("Check in French site");
		acc.changeLanguageForUser("French");
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_TODAY_LABEL_FRENCH);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_TODAY_LABEL_FRENCH).getText().equalsIgnoreCase(dateFormat2.format(date));	

		//Delete data test
		info("Back to English site");
		acc.changeLanguageForUser("Anglais");			
	}

	/**
	 * Case ID:69195.
	 * Test Case Name: Change the Time format in Calendar gadget.
	 * Pre-Condition: events are displayed in the gadget calendar
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/02/12 16:55:48
	 */
	@SuppressWarnings("static-access")
	@Test
	public  void test11_ChangeTheTimeFormatInCalendarGadget() {
		info("Test 11 Change the Time format in Calendar gadget");
		String calendar1 = "Case 69195 "+getRandomString();
		String event1 = "Event 69195";
		DateFormat dateFormat = DateFormat.getTimeInstance();

		/*
		- Connect to Intranet
		- The language of the session is "English"
		 *Input Data: 
		 *Expected Outcome: The format of time in the event is displayed in the format "HH:MM PM"		*/

		info("Check in English site");
		goToCalendarPage();
		addCalendar(calendar1, calendar1,null);
		Utils.pause(5000);
		event.addQuickEvent(event1,"", getDate(0,"MM/dd/yyyy"), getDate(0,"MM/dd/yyyy"), false, calendar1);
		nav.goToHomePage();
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}",calendar1));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_EVENT_ITEM.replace("${event}", event1));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_EVENT_TIME.replace("${event}", event1)).getText().equalsIgnoreCase(dateFormat.LONG +"");

		/*
		- Change the language of the session to "French"
		 *Input Data: 
		 *Expected Outcome: The format of time in the event is displayed in the format "HH:MM"		*/ 
		info("Check in French site");
		acc.changeLanguageForUser("French");
		nav.goToHomePage();
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_TODAY_LABEL_FRENCH);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}",calendar1));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_EVENT_ITEM.replace("${event}", event1));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_EVENT_TIME.replace("${event}", event1)).getText().equalsIgnoreCase(dateFormat.SHORT +"");

		//Delete data test
		info("Back to English site");
		acc.changeLanguageForUser("Anglais");
		goToCalendarPage();
		deleteCalendar(calendar1,true);	
	}

	/**
	 * Case ID:69196.
	 * Test Case Name: List of calendars in Calendar Gadget survives across user sessions.
	 * Pre-Condition: calendar contain events
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/02/12 16:55:48
	 */
	@Test
	public  void test12_ListOfCalendarsInCalendarGadgetSurvivesAcrossUserSessions() {
		info("Test 12 List of calendars in Calendar Gadget survives across user sessions");
		String calendar1 = "CalendarA";
		String calendar2 = "CalendarB";
		String calendar3 = "CalendarC";
		String eventName1 = "EventA";
		String eventName2 = "EventB";
		String eventName3 = "EventC";

		//Pre-Condition
		info("Create calendars");
		goToCalendarPage();
		addCalendar(calendar1, calendar1,null);		
		addCalendar(calendar2, calendar2,null);		
		addCalendar(calendar3, calendar3,null);	
		info("Create events");
		Utils.pause(5000);
		event.addQuickEvent(eventName1,"", getDate(0,"MM/dd/yyyy"), getDate(0,"MM/dd/yyyy"), false, calendar1);
		event.addQuickEvent(eventName2,"", getDate(0,"MM/dd/yyyy"), getDate(0,"MM/dd/yyyy"), false, calendar2);
		event.addQuickEvent(eventName3,"", getDate(0,"MM/dd/yyyy"), getDate(0,"MM/dd/yyyy"), false, calendar3);

		/*
		- Connect to Intranet
		- From the Calendar Gadget, settings, choose the list of calendar to display
		 *Input Data: 
		 *Expected Outcome: The list of calendar is displayed in the "Display" view		*/
		nav.goToHomePage();
		info("Check Calendar in homepage");
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}",calendar1));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_EVENT_ITEM.replace("${event}", eventName1));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}",calendar2));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_EVENT_ITEM.replace("${event}", eventName2));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}",calendar3));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_EVENT_ITEM.replace("${event}", eventName3));

		mouseOver(ELEMENT_CALENDAR_GADGET, true);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_ICON);
		click(ELEMENT_CALENDAR_GADGET_SETTING_ICON);
		info("List of calendar");
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}",calendar1));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}",calendar2));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}",calendar3));

		info("Choose the list of calendar to display");
		click(ELEMENT_DELETE_CALENDAR_ICON.replace("${calendar}",calendar3));
		Utils.pause(500);
		button.ok();

		info("View list calendar after choose");
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}",calendar1));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}",calendar2));
		waitForElementNotPresent(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}",calendar3));

		/*
		- Logout/Login
		 *Input Data: 
		 *Expected Outcome: The list of calendars doesn't changed		*/ 
		acc.signOut();
		acc.signIn(DATA_USER1, DATA_PASS);	
		info("User signIn/signOut to check");
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}",calendar1));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}",calendar2));
		waitForElementNotPresent(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}",calendar3));

		//Delete data test
		goToCalendarPage();
		deleteCalendar(calendar1,true);
		deleteCalendar(calendar2,true);
		deleteCalendar(calendar3,true);
	}

	/**
	 * Case ID:69282.
	 * Test Case Name: Display the Calendar Gadget empty.
	 * Pre-Condition: No task and No events are performed in the calendar
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/02/12 16:55:48
	 */
	@Test
	public  void test13_DisplayTheCalendarGadgetEmpty() {
		info("Test 13 Display the Calendar Gadget empty");
		DateFormat dateFormat = new SimpleDateFormat("MM/d/yyyy");
		Date date = new Date();
		/*
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: 
		- The Gadget Calendar is displayed
		- In the Header "TODAY: DD/MM/YY"
		- A small sentence in the gadget is displayed: "Nothing planned"		*/ 

		waitForAndGetElement(ELEMENT_CALENDAR_GADGET);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_TODAY_LABEL).getText().equalsIgnoreCase(dateFormat.format(date));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_EMPTY);
	}

	/**
	 * Case ID:79375.
	 * Test Case Name: Display Calendar Gadget if no event, but tasks.
	 * Pre-Condition: There are tasks, but no events
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/02/12 16:55:48
	 */
	@Test
	public  void test14_DisplayCalendarGadgetIfNoEventButTasks() {
		info("Test 14 Display Calendar Gadget if no event, but tasks");
		String calendar = "Case 79375" +getRandomString();
		String taskName = "Task 79375" +getRandomString();

		/*
		- Connect to Intranet
		- Go to calendar application
		- Add some tasks
		- Not add events
		 *Input Data: 
		 *Expected Outcome: 
		- Tasks are created		*/
		goToCalendarPage();
		addCalendar(calendar, calendar,null);
		task.addQuickTask(taskName, taskName, getDate(0,"MM/dd/yyyy"),getDate(0,"MM/dd/yyyy"),false, calendar);

		/*Back to home page intranet
		 *Input Data: 
		 *Expected Outcome: On Calendar gadget, display a message "Nothing planned" and the list of tasks.		*/ 
		nav.goToHomePage();
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_EMPTY);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_TASK_LABEL);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_TASK_ITEM.replace("${task}",taskName));

		//Delete data test
		info("Delete data test");
		goToCalendarPage();
		deleteCalendar(calendar,true);
	}

	/**
	 * Case ID:79376.
	 * Test Case Name: Display Calendar Gadget if no tasks, but events.
	 * Pre-Condition: There are events, but no tasks
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/02/12 16:55:48
	 */
	@Test
	public  void test15_DisplayCalendarGadgetIfNoTasksButEvents() {
		info("Test 15 Display Calendar Gadget if no tasks, but events");
		String calendar = "Case 79376 "+getRandomString();
		String eventName = "Event 79376";
		/*
		- Connect to Intranet
		- Go to calendar application
		- Add some events
		- Not add tasks
		 *Input Data: 
		 *Expected Outcome: 
		- Events are created		*/
		info("Create calendar");
		goToCalendarPage();
		addCalendar(calendar, calendar,null);	
		info("Create events");
		Utils.pause(5000);
		event.addQuickEvent(eventName,"", getDate(0,"MM/dd/yyyy"), getDate(0,"MM/dd/yyyy"), false, calendar);

		/*Back to home page intranet
		 *Input Data: 
		 *Expected Outcome: On Calendar gadget, don't display the lower part of the gadget (Tasks part)		*/ 
		nav.goToHomePage();
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}",calendar));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_EVENT_ITEM.replace("${event}", eventName));
		waitForElementNotPresent(ELEMENT_CALENDAR_GADGET_TASK_LABEL);

		//Delete data test
		info("Delete data test");
		goToCalendarPage();
		deleteCalendar(calendar,true);
	}
}