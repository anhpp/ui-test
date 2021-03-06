package org.exoplatform.selenium.platform.plf.functional.navigation.topnavigation.create;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.calendar.CalendarBase;
import org.exoplatform.selenium.platform.calendar.Task;
import org.exoplatform.selenium.platform.calendar.Event;
import org.openqa.selenium.By;
import org.testng.annotations.*;

/**
 * @author chinhdtt
 * @date 24 Mar 2014
 */
public class PLF_Navigation_TopNavigation_Create_EventTask extends CalendarBase{
	ManageAccount acc;
	NavigationToolbar nav;
	Button button;
	Task task; 
	String username = "John Smith";
	Event evt; 
	ManageAlert alert;

	@BeforeMethod
	public void beforeMethods(){	
		initSeleniumTest();
		driver.get(baseUrl);
		acc = new ManageAccount(driver, this.plfVersion);
		nav = new NavigationToolbar(driver, this.plfVersion);				
		acc.signIn(DATA_USER1, DATA_PASS);	
		button = new Button(driver, this.plfVersion);
		task = new Task(driver, this.plfVersion);
		evt = new Event(driver, this.plfVersion);
		alert = new ManageAlert(driver);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Case ID:76682.
	 * Test Case Name: Add a Task from create menu.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/24 17:19:02
	 */
	@Test
	public  void test01_AddATaskFromCreateMenu() {
		info("Test 1: Add a Task from create menu");
		String calendar = "Calendar 76682";
		String task1 = "Task 76682";

		/*
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: 
		- The Top Navigation bar is displayed 		*/
		info("Check top navigation");
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);

		/*
		- Mouse over on the button "Create" (+)
		- Select the item "Event/Task"
		 *Input Data: 
		 *Expected Outcome: 
		- The drop down menu is updated to let the user create a new Event or Task
		- Buttons "Cancel" and Save are displayed		*/
		nav.goToEventTask();

		/*
		- Select "Task" from "Add new"
		- Input available data of date and times
		- Select the calendar
		- Click on the button "Save"
		 *Input Data: 
		 *Expected Outcome: 
		- A message is displayed: "The task was added to $calendar_name"		*/
		info("Select add new Task");
		task.addEventTaskFromToolbarNavigation(false,task1,getDate(1,"MM/dd/yyyy"),getDate(1,"MM/dd/yyyy"),true,true);

		/*
		- Wait 2 second
		 *Input Data: 
		 *Expected Outcome: The message disappears with a fade out effect		*/ 
		Utils.pause(2000);
		waitForElementNotPresent(ELEMENT_CREATE_TASK_MESSAGE.replace("${calendar}", calendar));

		//Delete data test
		info("Delete data test");
		goToCalendarPage();
		deleteEventTask(task1);		
	}

	/**
	 * Case ID:76683.
	 * Test Case Name: Add an event from create menu.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/24 17:19:02
	 */
	@Test
	public  void test02_AddAnEventFromCreateMenu() {
		info("Test 2: Add an event from create menu");
		String event1 = "Event 76683";

		/*
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: 
		- The Top Navigation bar is displayed 		*/
		info("Check top navigation");
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);

		/*
		- Mouse over on the button "Create" (+)
		- Select the item "Event/Task"
		 *Input Data: 
		 *Expected Outcome: 
		- The dropdown menu is updated to let the user create a new Event or Task
		- Buttons "Cancel" and next are displayed		*/
		nav.goToEventTask();

		/*
		- Select "event" from "Add new"
		- Input available data of date and times
		- Select the calendar
		- Click on the button "Next"
		 *Input Data: 
		 *Expected Outcome: 
		- A message is displayed: "The event was added to $calendar_name"		*/
		/*
		- Wait 2 second
		 *Input Data: 
		 *Expected Outcome: The message disappears with a fade out effect		*/ 
		info("Select add new Task");
		task.addEventTaskFromToolbarNavigation(true,event1,getDate(1,"MM/dd/yyyy"),getDate(1,"MM/dd/yyyy"),true,true);

		//Delete data test
		info("Delete data test");
		goToCalendarPage();
		deleteEventTask(event1);	
	}

	/**
	 * Case ID:76685.
	 * Test Case Name: Dismiss the "Add new Event/Task" menu.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/24 17:19:02
	 */
	@Test
	public  void test03_DismissTheAddNewEventTaskMenu() {
		info("Test 3: Dismiss the Add new Event/Task menu");
		/*
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: The Top Navigation bar is displayed		*/
		info("Check top navigation");
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);

		/*
		- Mouse over on the button "Create" (+)
		- Chosse "Event/Task"
		 *Input Data: 
		 *Expected Outcome: 
		- The Create "Event/task" menu is displayed		*/
		mouseOverAndClick(ELEMENT_MENU_ADD_ICON);
		Utils.pause(1000);
		waitForAndGetElement(ELEMENT_ADD_EVENT_TASK_ICON);
		click(ELEMENT_ADD_EVENT_TASK_ICON);
		waitForAndGetElement(ELEMENT_ADD_EVENT_TASK_FORM);

		/*
		- Input available data
		- Click on the button "Cancel"
		 *Input Data: 
		 *Expected Outcome: The menu "Create" is dismissed		*/ 
		click(button.ELEMENT_CANCEL_BUTTON);
		Utils.pause(500);
		waitForElementNotPresent(ELEMENT_ADD_EVENT_TASK_FORM);
	}

	/**
	 * Case ID:76711.
	 * Test Case Name: Display the list of calendars.
	 * Pre-Condition: user has right in calendar
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/24 17:19:02
	 */
	@Test
	public  void test04_DisplayTheListOfCalendars() {
		info("Test 4: Display the list of calendars");
		/*
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: 
		- The Top Navigation bar is displayed 		*/
		info("Check top navigation");
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);

		/*
		- Mouse over on the button "Create" (+)
		- Select the item "Event/Task"
		 *Input Data: 
		 *Expected Outcome: 
		- The dropdown menu is updated to let the user create a new Event or Task:
		- The field "Select Calendar" is displayed		*/
		nav.goToEventTask();

		/*
		- Click on the arrows next "Select calendar" field
		 *Input Data: 
		 *Expected Outcome: 
		- The list of calendars of the user is displayed		*/ 
		waitForAndGetElement(ELEMENT_TOOLBAR_SELECT_CALENDAR);
		click(ELEMENT_TOOLBAR_SELECT_CALENDAR);
		waitForAndGetElement(ELEMENT_PRIVATE_CALENDAR);
		waitForAndGetElement(ELEMENT_PUBLIC_CALENDAR);
	}

	/**
	 * Case ID:76902.
	 * Test Case Name: Inputted values are not reset when changing type between event or task.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/24 17:19:02
	 */
	@Test
	public  void test05_InputtedValuesAreNotResetWhenChangingTypeBetweenEventOrTask() {
		info("Test 5: Inputted values are not reset when changing type between event or task");
		String event2 = "Event 76902";
		/*
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: 
		- The Top Navigation bar is displayed 		*/
		info("Check top navigation");
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);

		/*
		- Mouse over on the button "Create" (+)
		- Select the item "Event/Task"
		 *Input Data: 
		 *Expected Outcome: 
		- The dropdown menu is updated to let the user create a new Event or Task
		- Buttons "Cancel" and next are displayed		*/
		nav.goToEventTask();

		/*
		- Select value from "Add new"
		- Input available data of date and times
		- Select the calendar
		 */		
		info("Input data");
		check(ELEMENT_TOOLBAR_EVENT_CHECKBOX,2);
		type(ELEMENT_TOOLBAR_INPUT_EVENT_TITLE_TEXTBOX, event2, true);
		type(ELEMENT_TOOLBAR_START_DATE, getDate(0, "MM/dd/yyyy"), true);
		select(ELEMENT_TOOLBAR_START_TIME, "All Day");
		type(ELEMENT_TOOLBAR_END_DATE, getDate(0, "MM/dd/yyyy"), true);
		select(ELEMENT_TOOLBAR_END_TIME, "All Day");

		/*Change value of "Add new" field
		 *Input Data: 
		 *Expected Outcome: The inputted value are not reset		*/ 		
		info("Change value of Add new field");
		check(ELEMENT_TOOLBAR_TASK_CHECKBOX,2);
		Utils.pause(1000);
		assert waitForAndGetElement(ELEMENT_TOOLBAR_START_DATE).getAttribute("value").equalsIgnoreCase(getCurrentDate("MM/dd/yyyy"));
		assert waitForAndGetElement(ELEMENT_TOOLBAR_END_DATE).getAttribute("value").equalsIgnoreCase(getCurrentDate("MM/dd/yyyy"));
		button.save();	
		waitForAndGetElement(ELEMENT_CREATE_TASK_MESSAGE.replace("${calendar}", waitForAndGetElement(ELEMENT_ACCOUNT_NAME_LINK).getText()));
		Utils.pause(3000);
		waitForElementNotPresent(ELEMENT_CREATE_TASK_MESSAGE.replace("${calendar}",  waitForAndGetElement(ELEMENT_ACCOUNT_NAME_LINK).getText()));

		info("Delete data test");
		goToCalendarPage();
		deleteEventTask(event2);	
	}

	/**
	 * Case ID:76760.
	 * Test Case Name: Show Date and Time in the Calendar.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/24 17:19:02
	 */
	@Test
	public  void test06_ShowDateAndTimeInTheCalendar() {
		info("Test 6: Show Date and Time in the Calendar");
		/*
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: 
		- The Top Navigation bar is displayed 		*/
		info("Check top navigation");
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);

		/*
		- Mouse over on the button "Create" (+)
		- Select the item "Event/Task"
		 *Input Data: 
		 *Expected Outcome: 
		- The dropdown menu is updated to let the user create a new Event or Task:
		- The start date and time are before the end date and time		*/ 
		info("Check show date time");
		nav.goToEventTask();
		waitForAndGetElement(ELEMENT_TOOLBAR_START_DATE);
		waitForAndGetElement(ELEMENT_TOOLBAR_END_DATE);
	}

	/**
	 * Case ID:76763.
	 * Test Case Name: Show items of menu "Add new Event/Task".
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/24 17:19:02
	 */
	@Test
	public  void test07_ShowItemsOfMenuAddNewEventTask() {
		info("Test 7: Show items of menu Add new Event/Task");
		/*
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: 
		- The Top Navigation bar is displayed 		*/
		info("Check top navigation");
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);

		/*
		- Mouse over on the button "Create" (+)
		- Select the item "Event/Task"
		 *Input Data: 
		 *Expected Outcome: 
		- The dropdown menu is updated to let the user create a new Event or Task:* select the type (Event vs Task) with a radio button, * input the name of the Event/Task, * select the start date and time* select end date and time* select the target calendar
		- Buttons "Cancel" and "Next" are displayed		*/ 
		nav.goToEventTask();
		info("Show items of menu: Add new Event/Task");
		check(ELEMENT_TOOLBAR_EVENT_CHECKBOX, 2);
		check(ELEMENT_TOOLBAR_TASK_CHECKBOX, 2);
		waitForAndGetElement(ELEMENT_TOOLBAR_INPUT_EVENT_TITLE_TEXTBOX);
		waitForAndGetElement(ELEMENT_TOOLBAR_START_DATE);
		waitForAndGetElement(ELEMENT_TOOLBAR_START_TIME);
		waitForAndGetElement(ELEMENT_TOOLBAR_END_DATE);
		waitForAndGetElement(ELEMENT_TOOLBAR_END_TIME);
		waitForAndGetElement(ELEMENT_TOOLBAR_SELECT_CALENDAR);
		waitForAndGetElement(button.ELEMENT_SAVE_BUTTON);
		waitForAndGetElement(button.ELEMENT_CANCEL_BUTTON);
	}

	/**
	 * Case ID:76765.
	 * Test Case Name: Show the default End time.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/24 17:19:02
	 * Pending this case because can't compare default time with content of element. 
	 */
	@Test (groups="pending")
	public  void test08_ShowTheDefaultEndTime() {
		info("Test 8: Show the default End time");
		int hour;
		int minutes = getMinute();
		if(minutes>=30){
			hour = getHours()+1; 
			minutes = 00; 
		}
		else{
			hour = getHours();
			minutes = 30;
		}			

		/*
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: 
		- The Top Navigation bar is displayed 		*/
		info("Check top navigation");
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);

		/*
		- Mouse over on the button "Create" (+)
		- Select the item "Event/Task"
		 *Input Data: 
		 *Expected Outcome: 
		- The dropdown menu is updated to let the user create a new Event or Task:
		- The default end time, is start time + 1 hour 		*/ 
		nav.goToEventTask();
		info("Show the default End time");
		waitForAndGetElement(ELEMENT_TOOLBAR_START_TIME);
		waitForAndGetElement(ELEMENT_TOOLBAR_END_TIME);
		String startTime = waitForAndGetElement(ELEMENT_TOOLBAR_START_TIME).getText();
		assert startTime.equalsIgnoreCase(hour+":"+minutes);
	}

	/**
	 * Case ID:76766.
	 * Test Case Name: Show the default Start date.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/24 17:19:02
	 */
	@Test
	public  void test09_ShowTheDefaultStartDate() {
		info("Test 9: Show the default Start date");
		/*
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: 
		- The Top Navigation bar is displayed 		*/
		info("Check top navigation");
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);

		/*
		- Mouse over on the button "Create" (+)
		- Select the item "Event/Task"
		 *Input Data: 
		 *Expected Outcome: 
		- The dropdown menu is updated to let the user create a new Event or Task:
		- The start date with "today" as default date		*/ 
		nav.goToEventTask();
		info("Show the default Start date");
		waitForAndGetElement(ELEMENT_TOOLBAR_START_DATE);
		String currentDate = getCurrentDate("MM/dd/yyyy");
		assert waitForAndGetElement(ELEMENT_TOOLBAR_START_DATE).getAttribute("value").equalsIgnoreCase(currentDate);
	}

	/**
	 * Case ID:76767.
	 * Test Case Name: Show the default Start time.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/24 17:19:02
	 * Pending this case because can't compare default time with content of element. 
	 */
	@Test (groups="pending")
	public  void test10_ShowTheDefaultStartTime() {
		info("Test 10 Show the default Start time");
		int hour;
		int minutes = getMinute();
		if(minutes>=30){
			hour = getHours()+1; 
			minutes = 00; 
		}
		else{
			hour = getHours();
			minutes = 30;
		}

		/*
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: 
		- The Top Navigation bar is displayed 		*/
		info("Check top navigation");
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);

		/*
		- Mouse over on the button "Create" (+)
		- Select the item "Event/Task"
		 *Input Data: 
		 *Expected Outcome: 
		- The dropdown menu is updated to let the user create a new Event or Task:
		- The default start time can be H:15, H:30, H:45 or H+1:00 where H is the current hour.		*/ 
		nav.goToEventTask();
		assert waitForAndGetElement(ELEMENT_TOOLBAR_START_TIME).getText().equalsIgnoreCase(hour+":"+minutes);
	}

	/**
	 * Case ID:76768.
	 * Test Case Name: Show the mini Calendar.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/24 17:19:02
	 */
	@Test
	public  void test11_ShowTheMiniCalendar() {
		info("Test 11 Show the mini Calendar");
		/*
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: 
		- The Top Navigation bar is displayed 		*/
		info("Check top navigation bar");
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);

		/*
		- Mouse over on the button "Create" (+)
		- Select the item "Event/Task"
		 *Input Data: 
		 *Expected Outcome: 
		- The dropdown menu is updated to let the user create a new Event or Task:
		- The start date and the end date fields are displayed		*/
		nav.goToEventTask();
		waitForAndGetElement(ELEMENT_TOOLBAR_START_DATE);
		waitForAndGetElement(ELEMENT_TOOLBAR_END_DATE);

		/*
		- Select the textfield of the field "Start date" or "end date"
		 *Input Data: 
		 *Expected Outcome: A mini calendar is displayed		*/ 
		info("Check mini calendar displayed when click on Start date");
		mouseOverAndClick(ELEMENT_ADD_EVENT_TASK_FORM);
		click(ELEMENT_TOOLBAR_START_DATE);
		waitForAndGetElement(ELEMENT_TOOLBAR_MINI_CALENDAR);
		mouseOverAndClick(ELEMENT_ADD_EVENT_TASK_FORM);
		Utils.pause(1000);
		info("Check mini calendar displayed when click on end date");
		click(ELEMENT_TOOLBAR_END_DATE);
		waitForAndGetElement(ELEMENT_TOOLBAR_MINI_CALENDAR);		
	}

	/**
	 * Case ID:76769.
	 * Test Case Name: Show the option list of time values.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/24 17:19:02
	 */
	@Test
	public  void test12_ShowTheOptionListOfTimeValues() {
		info("Test 12 Show the option list of time values");
		/*
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: 
		- The Top Navigation bar is displayed 		*/
		info("Check top navigation bar");
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);

		/*
		- Mouse over on the button "Create" (+)
		- Select the item "Event/Task"
		 *Input Data: 
		 *Expected Outcome: 
		- The dropdown menu is updated to let the user create a new Event or Task:
		- The time field is displayed for start and end dates		*/
		info("Check show date time");
		nav.goToEventTask();
		waitForAndGetElement(ELEMENT_TOOLBAR_START_TIME);
		waitForAndGetElement(ELEMENT_TOOLBAR_END_TIME);

		/*
		- Select the component of time
		 *Input Data: 
		 *Expected Outcome: 
		- The list of options are proposed time values with each half hours		*/ 
		info("Check list time option");
		click(ELEMENT_TOOLBAR_START_TIME);
		Utils.pause(5000);
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "1"))).getText().equalsIgnoreCase("All Day"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "2"))).getText().equalsIgnoreCase("12:00 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "3"))).getText().equalsIgnoreCase("12:30 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "4"))).getText().equalsIgnoreCase("01:00 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "5"))).getText().equalsIgnoreCase("01:30 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "6"))).getText().equalsIgnoreCase("02:00 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "7"))).getText().equalsIgnoreCase("02:30 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "8"))).getText().equalsIgnoreCase("03:00 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "9"))).getText().equalsIgnoreCase("03:30 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "10"))).getText().equalsIgnoreCase("04:00 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "11"))).getText().equalsIgnoreCase("04:30 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "12"))).getText().equalsIgnoreCase("05:00 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "13"))).getText().equalsIgnoreCase("05:30 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "14"))).getText().equalsIgnoreCase("06:00 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "15"))).getText().equalsIgnoreCase("06:30 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "16"))).getText().equalsIgnoreCase("07:00 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "17"))).getText().equalsIgnoreCase("07:30 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "18"))).getText().equalsIgnoreCase("08:00 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "19"))).getText().equalsIgnoreCase("08:30 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "20"))).getText().equalsIgnoreCase("09:00 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "21"))).getText().equalsIgnoreCase("09:30 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "22"))).getText().equalsIgnoreCase("10:00 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "23"))).getText().equalsIgnoreCase("10:30 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "24"))).getText().equalsIgnoreCase("11:00 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "25"))).getText().equalsIgnoreCase("11:30 AM"));
		assert (waitForAndGetElement(By.xpath(ELEMENT_TOOLBAR_TIME_CONTENT.replace("${index}", "26"))).getText().equalsIgnoreCase("12:00 PM"));
	}

	/**
	 * Case ID:76770.
	 * Test Case Name: Show Warning message if end date is before start date.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/24 17:19:02
	 */
	@Test
	public  void test13_ShowWarningMessageIfEndDateIsBeforeStartDate() {
		info("Test 13 Show Warning message if end date is before start date");
		String task3 = "Task 76770";
		/*
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: 
		- The Top Navigation bar is displayed 		*/
		info("Check top navigation bar");
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);

		/*
		- Mouse over on the button "Create" (+)
		- Select the item "Event/Task"
		- Select End date before the start date
		- Input a Title
		- Click on the button "Next"
		 *Input Data: 
		 *Expected Outcome: 
		- A warning message is displayed "Sorry, you can't create an event that ends before it starts."		*/
		nav.goToEventTask();
		info("Input data");
		check(ELEMENT_TOOLBAR_EVENT_CHECKBOX,2);
		type(ELEMENT_TOOLBAR_INPUT_EVENT_TITLE_TEXTBOX, task3, true);
		type(ELEMENT_TOOLBAR_START_DATE, getDate(1, "MM/dd/yyyy"), true);
		select(ELEMENT_TOOLBAR_START_TIME, "All Day");
		type(ELEMENT_TOOLBAR_END_DATE, getDate(0, "MM/dd/yyyy"), true);
		select(ELEMENT_TOOLBAR_END_TIME, "All Day");
		button.save();
		info("Show Warning message if end date is before start date");
		Utils.pause(500);
		waitForAndGetElement(ELEMENT_MESSAGE_WARNING_CALENDAR);		
		alert.acceptAlert();
	}

	/**
	 * Case ID:76773.
	 * Test Case Name: Update Create menu to "Add new Event/Task" menu.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/24 17:19:02
	 */
	@Test
	public  void test14_UpdateCreateMenuToAddNewEventTaskMenu() {
		info("Test 14 Update Create menu to Add new Event/Task menu");
		/*
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: 
		- The Top Navigation bar is displayed 		*/
		info("Check top navigation bar");
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);

		/*
		- Mouse over on the button "Create" (+)
		 *Input Data: 
		 *Expected Outcome: The list of items is displayed:* Event/Task* Poll* Topic* Upload Document* Wiki		*/
		nav.goToCreateMenu();
		Utils.pause(2000);
		info("List item of Create menu");
		waitForAndGetElement(ELEMENT_ADD_EVENT_TASK_ICON);
		waitForAndGetElement(ELEMENT_ADD_POLL_ICON);
		waitForAndGetElement(ELEMENT_ADD_TOPIC_ICON);
		waitForAndGetElement(ELEMENT_ADD_UPLOAD_FILE_ICON);
		waitForAndGetElement(ELEMENT_ADD_WIKI_ICON);

		/*
		- Select the item "Event/Task"
		 *Input Data: 
		 *Expected Outcome: 
		- The dropdown menu is updated to let the user create a new Event or Task
		- Button Cancel or Save are displayed		*/ 
		mouseOverAndClick(ELEMENT_ADD_EVENT_TASK_ICON);
		waitForAndGetElement(ELEMENT_ADD_EVENT_TASK_FORM);
		waitForAndGetElement(button.ELEMENT_SAVE_BUTTON);
		waitForAndGetElement(button.ELEMENT_CANCEL_BUTTON);
	}
}