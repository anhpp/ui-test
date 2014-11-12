package org.exoplatform.selenium.platform.calendar.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.openqa.selenium.By;
import org.exoplatform.selenium.Utils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.platform.calendar.CalendarBase;
import org.exoplatform.selenium.platform.calendar.Event;
import org.exoplatform.selenium.platform.calendar.Task;

/**
 * 
 * @author havtt
 * @date 25 Oct 2013
 */
/**
 * @date: 8/05/2014
 * @author lientm
 * @description: update suggestion date follow https://jira.exoplatform.org/browse/FQA-1721
 */

public class Calendar_Event extends CalendarBase {

	ManageAccount acc;
	Event evt;
	Task tsk;

	@BeforeMethod
	public void setUpBeforeTest(){
//		getDriverAutoSave();
		initSeleniumTest();
		driver.get(baseUrl);
		acc = new ManageAccount(driver);
		evt = new Event(driver);
		tsk = new Task(driver);
		acc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**Add, edit, delete event in personal calendar
	 * CaseId: 99373: add event
	 * CaseId: 99484: edit event
	 * CaseId: 99485: delete event
	 */
	@Test
	public void test01_AddEditDeleteEventInPersonalCal(){
		String calendar = "Calendar_99373_1";
		String color = "sky_blue";
		String event = "Event_99373_1";
		String newEvent = "Event_99373_1 update";
		String note = "Update new event";
		
		goToCalendarPage();		
		info("Create new personal calendar");
		addCalendar(calendar, null, color);
		
		info("Check suggestion time");
		evt.goToAddEventFromCalendar(calendar);
		evt.checkSuggestionEventTime(null, 60);
		evt.checkSuggestionEventTime("08:00", 60);
		evt.inputAddEventForm(event, null, null, null, null, false, calendar);
		click(evt.ELEMENT_ADD_EVENT_SAVE_QUICK_BUTTON);
		waitForElementNotPresent(evt.ELEMENT_ADD_EVENT_POPUP);

		info("Edit event");
		evt.goToEditEventForm(event);
		evt.inputAddEventForm(newEvent, note, null, getDate(0,"MM/dd/yyyy") + " 12:00", "", false);
		assert waitForAndGetElement(evt.ELEMENT_ADD_EDIT_EVENT_TO_TIME).getAttribute("value").equals("13:00");
		click(evt.ELEMENT_ADD_EVENT_SAVE_BUTTON);
		waitForElementNotPresent(evt.ELEMENT_EDIT_EVENT_POPUP);
		
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", newEvent)));

		evt.deleteEventTask(newEvent);
		deleteCalendar(calendar);
	}
	
//	/**
//	 * Add new event in Personal Calendar
//	 * CaseID 68651
//	 */
//	@Test
//	public void test01_AddEditDeleteEventInPersonalCal() {
//		String EVENT_01 = "EVENT_01";
//
//		info("Go to Intranet Calendar");
//		goToCalendarPage();
//
//		info("Add a new event in Personal calendar");
//		Utils.pause(5000);
//		evt.addQuickEvent(EVENT_01, EVENT_01,getDate(0,"MM/dd/yyyy"), getDate(0,"MM/dd/yyyy"), false);
//
//		info("Confirm added event displays in the calendar");
//		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", EVENT_01)));
//
//		info("restore data");
//		deleteEventTask(EVENT_01, selectDayOption.ONEDAY);
//	}

	/** Add, edit, delete event in shared calendar
	 * CaseID 99378: Add event
	 * CaseId 99492: edit event
	 * CaseId 99491: delete event
	 */
	@Test
	public void test02_AddEditDeleteEventInSharedCal(){
		String event = "event_99378";
		String newEvent = "Event_99378 update";
		String note = "update event";
		String calendar = "sharedCalendar_99378";
		String color = "sky_blue";
		String[] sharedUser = {"mary"};
		boolean[] edit = {true};
		goToCalendarPage();
		info("Create new calendar then share its");
		addCalendar(calendar, "calendar to share", color);
		shareCalendar(calendar, sharedUser, edit);

		info("Add event with shared calendar");
		acc.signOut();
		acc.signIn(sharedUser[0], DATA_PASS);
		goToCalendarPage();
		evt.goToAddEventFromCalendar(calendar);
		evt.checkSuggestionEventTime(null, 60);
		evt.checkSuggestionEventTime("08:00", 60);
		evt.inputAddEventForm(event, null, null, null, null, false, calendar);
		click(evt.ELEMENT_ADD_EVENT_SAVE_QUICK_BUTTON);
		waitForElementNotPresent(evt.ELEMENT_ADD_EVENT_POPUP);

		info("Edit event");
		evt.editEvent(event, newEvent, note, null, getDate(0,"MM/dd/yyyy") + " 12:00", getDate(0,"MM/dd/yyyy") + " 14:00", false);
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", newEvent)));
		
		info("Delete data");
		deleteEventTask(newEvent,selectDayOption.ONEDAY);
		acc.signOut();
		acc.signIn(DATA_USER1, DATA_PASS);
		goToCalendarPage();
		deleteCalendar(calendar,true);
	}
	
//	/**
//	 * Add new event in Shared Calendar
//	 * CaseID 68656
//	 */
//	@Test
//	public void test02_addNewEventinSharedCal() {
//		String EVENT_02 = "EVENT_02";
//		String CAL_02 = "CAL_02";
//		String EVENT_CATEGORY = "All";
//		String EVENT_SHARED_CALENDAR = CAL_02;
//		String[] USER_SHARED = {"root"};
//		String USER_SHARED_PASS = "gtngtn";
//		boolean[] EDITABLE = {true};
//
//		info("==Go to Intranet Calendar with User fqa==");
//		goToCalendarPage();
//
//		info("==Create and share a calendar with User root==");
//		Utils.pause(5000);
//		addCalendar(CAL_02, CAL_02, "pink");
//		Utils.pause(3000);
//		shareCalendar(CAL_02, USER_SHARED, EDITABLE);
//
//		info("==User fqa sign out==");
//		acc.signOut();
//
//		info("==Login by shared user - root==");
//		acc.signIn(USER_SHARED[0], USER_SHARED_PASS);
//
//		info("==Add event on shared calendar==");
//		goToCalendarPage();
//		Utils.pause(5000);
//		evt.addQuickEvent(EVENT_02,EVENT_02,getDate(0,"MM/dd/yyyy"), getDate(0,"MM/dd/yyyy"), false, EVENT_SHARED_CALENDAR, EVENT_CATEGORY);
//		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", EVENT_02)));
//
//		info("==Restore data==");
//		deleteEventTask(EVENT_02,selectDayOption.ONEDAY);
//		deleteCalendar(CAL_02,true);
//	}

	/** Add, edit, delete event in group calendar
	 * CaseID 99379: Add event
	 * CaseId 99489: edit event
	 * CaseId 99490: delete event
	 */
	@Test
	public void test03_AddEditDeleteInGroupCal(){
		String EVENT_03 = "event_99379";
		String CAL_03 = "calendar_99379";
		String EVENT_CATEGORY = "All";
		String USER_GROUP = "root";
		String CAL_GROUP = "/platform/administrators";
		String newEvent = "event_99379_update";
		String note = "Update new event";

		info("Create new group calendar");
		goToCalendarPage();
		addCalendar(CAL_03, CAL_03, "red", CAL_GROUP);
		changeEditPermissionForCalShowInGroup(CAL_03, USER_GROUP, CAL_GROUP);
		acc.signOut();

		info("Add event with user in group");
		acc.userSignIn(userType.ROOT);
		Utils.pause(2000);
		goToCalendarPage();
		evt.goToAddEventFromCalendar(CAL_03);
		evt.checkSuggestionEventTime(null, 60);
		evt.checkSuggestionEventTime("08:00", 60);
		evt.inputAddEventForm(EVENT_03, null, null, null, null, false, CAL_03, EVENT_CATEGORY);
		click(evt.ELEMENT_ADD_EVENT_SAVE_QUICK_BUTTON);
		waitForElementNotPresent(evt.ELEMENT_ADD_EVENT_POPUP);

		info("Edit event");
		evt.editEvent(EVENT_03, newEvent, note, null, getDate(0,"MM/dd/yyyy") + " 14:00", getDate(0,"MM/dd/yyyy") + " 16:00", false);
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", newEvent)));

		info("Delete data");
		deleteEventTask(newEvent, selectDayOption.ONEDAY);
		acc.signOut();
		acc.signIn(DATA_USER1, DATA_PASS);
		goToCalendarPage();
		deleteCalendar(CAL_03,true);
		
	}
//	/**
//	 * Add new event in Group Calendar
//	 * CaseID 68657
//	 */
//	@Test
//	public void test03_addNewEventinGroupCal() {
//		String EVENT_03 = "EVENT_03";
//		String CAL_03 = "CAL_03";
//		String EVENT_CATEGORY = "All";
//		String USER_GROUP = "root";
//		String USER_GROUP_PASS = "gtngtn";
//		String CAL_GROUP = "/platform/administrators";
//
//		info("==Go to Intranet Calendar==");
//		goToCalendarPage();
//
//		info("==Create a group calendar==");
//		Utils.pause(5000);
//		addCalendar(CAL_03, CAL_03, "red", CAL_GROUP);
//		Utils.pause(3000);
//		changeEditPermissionForCalShowInGroup(CAL_03, USER_GROUP, CAL_GROUP);
//
//		info("==User fqa sign out==");
//		acc.signOut();
//
//		info("==Login by shared user - root==");
//		acc.signIn(USER_GROUP, USER_GROUP_PASS);
//		Utils.pause(5000);
//
//		info("==Add event on group calendar==");
//		goToCalendarPage();
//		evt.addQuickEvent(EVENT_03,EVENT_03,getDate(0,"MM/dd/yyyy"), getDate(0,"MM/dd/yyyy"), false, CAL_03, EVENT_CATEGORY);
//		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", EVENT_03)));
//
//		info("==Restore data==");
//		deleteEventTask(EVENT_03, selectDayOption.ONEDAY);
//		deleteCalendar(CAL_03,true);
//	}
//
//	/**
//	 * Edit event in Personal Calendar
//	 * CaseID 69264
//	 */
//	@Test
//	public void test04_editEventinPersonalCal() {
//		String EVENT04 = "EVENT_04";
//		String TITLE = "EVENT_04_edited";
//		String DESCRIPTION = "EVENT_04_description_edited";
//
//		info("Go to Intranet Calendar");
//		goToCalendarPage();
//
//		info("Add a new task");
//		Utils.pause(3000);
//		evt.addQuickEvent(EVENT04,EVENT04,getDate(0,"MM/dd/yyyy"),getDate(0,"MM/dd/yyyy"),false);
//
//		info("Edit a task");
//		Utils.pause(3000);
//		evt.editEvent(EVENT04,TITLE,DESCRIPTION, null,null,null,false);
//
//		info("Restore data");
//		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", EVENT04)));
//		deleteEventTask(EVENT04, selectDayOption.ONEDAY);
//	}
//
//	/**
//	 * Delete event in Personal Calendar
//	 * CaseID 69265
//	 */
//	@Test
//	public void test05_deleteEventinPersonalCal() {
//		String EVENT05 = "EVENT_05";
//
//		info("Go to Intranet Calendar");
//		goToCalendarPage();
//
//		info("Add a new event");
//		Utils.pause(3000);
//		evt.addQuickEvent(EVENT05,EVENT05,getDate(0,"MM/dd/yyyy"),getDate(0,"MM/dd/yyyy"),false);
//
//		info("Delete an event");
//		Utils.pause(5000);
//		deleteEventTask(EVENT05, selectDayOption.ONEDAY);
//	}


	/**
	 * Drag & Drop event
	 * CaseID 69287
	 */
	@Test
	public void test06_DragDropEvent() {
		String EVENT06 = "EVENT_06";

		info("Go to Intranet Calendar");
		goToCalendarPage();

		info("Add a new task");
		Utils.pause(5000);
		evt.addQuickEvent(EVENT06,EVENT06,getDate(0,"MM/dd/yyyy"),getDate(0,"MM/dd/yyyy"),false);

		info("Drag & drop a task");
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", EVENT06)));
		dragAndDropToObject(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", EVENT06)),ELEMENT_TARGET_DATE);

		info("Restore data");
		deleteEventTask(EVENT06);
	}


	/**
	 * Edit event in Group Calendar
	 * CaseID 69288
	 */
	@Test
	public void test07_editEventinGroupCal() {
		String EVENT07 = "EVENT_07";
		String CAL_07 = "CAL_07";
		String CAL_GROUP = "/platform/web-contributors";
		String TITLE = "EVENT_07_edited";
		String DESCRIPTION = "EVENT_07_description_edited";
		String EVENT_CATEGORY = "All";
		String USER_GROUP = USER_ROOT;
		String USER_GROUP_PASS = PASS_ROOT;

		info("==Go to Intranet Calendar==");
		goToCalendarPage();

		info("==Create a group calendar==");
		driver.navigate().refresh();
		addCalendar(CAL_07, CAL_07, "green", CAL_GROUP);
		Utils.pause(3000);
		changeEditPermissionForCalShowInGroup(CAL_07, USER_GROUP, CAL_GROUP);

		info("==User fqa log out==");
		acc.signOut();

		info("==Login by shared user==");
		acc.signIn(USER_GROUP, USER_GROUP_PASS);
		Utils.pause(5000);

		info("==Add a new event on group calendar==");
		goToCalendarPage();
		Utils.pause(5000);
		evt.addQuickEvent(EVENT07,EVENT07,getDate(0,"MM/dd/yyyy"),getDate(0,"MM/dd/yyyy"),false, CAL_07, EVENT_CATEGORY);

		info("==Edit an event==");
		Utils.pause(5000);
		evt.editEvent(EVENT07,TITLE,DESCRIPTION, null,null,null,false);

		info("==Restore data==");
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", EVENT07)));
		deleteCalendar(CAL_07,true);
	}

	/**
	 * Delete event in group Calendar
	 * CaseID 69289
	 */
	@Test
	public void test08_deleteEventinGroupCal() {
		String EVENT08 = "EVENT_08";
		String CAL_08 = "CAL_08";
		String CAL_GROUP = "/platform/web-contributors";
		String EVENT_CATEGORY = "All";
		String USER_GROUP = USER_ROOT;
		String USER_GROUP_PASS = PASS_ROOT;

		info("==Go to Intranet Calendar==");
		goToCalendarPage();

		info("==Create a group calendar==");
		driver.navigate().refresh();
		addCalendar(CAL_08, CAL_08, "green", CAL_GROUP);
		Utils.pause(3000);
		changeEditPermissionForCalShowInGroup(CAL_08, USER_GROUP, CAL_GROUP);

		info("==User fqa log out==");
		acc.signOut();

		info("==Login by shared user==");
		acc.signIn(USER_GROUP, USER_GROUP_PASS);
		Utils.pause(5000);

		info("==Add a new event on group calendar==");
		goToCalendarPage();
		Utils.pause(5000);
		evt.addQuickEvent(EVENT08,EVENT08,getDate(0,"MM/dd/yyyy"),getDate(0,"MM/dd/yyyy"),false, CAL_08, EVENT_CATEGORY);

		info("==Delete an event on group calendar==");
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", EVENT08)));
		deleteEventTask(EVENT08, selectDayOption.ONEDAY);
		deleteCalendar(CAL_08,true);
	}

	/**
	 * Edit event in shared Calendar
	 * CaseID 69292
	 */
	@Test
	public void test09_editEventinSharedCal() {
		String EVENT09 = "EVENT_09";
		String CAL_09 = "CAL_09";
		String TITLE = "EVENT_09_edited";
		String DESCRIPTION = "EVENT_09_description_edited";
		String EVENT_CATEGORY = "All";
		String[] USER_SHARED = {USER_ROOT};
		String USER_SHARED_PASS = PASS_ROOT;
		boolean[] EDITABLE = {true};

		info("==Go to Intranet Calendar==");
		goToCalendarPage();

		info("==Create and share a calendar with User root==");
		driver.navigate().refresh();
		addCalendar(CAL_09, CAL_09, "pink");
		Utils.pause(3000);
		shareCalendar(CAL_09, USER_SHARED, EDITABLE);

		info("==User fqa sign out==");
		acc.signOut();

		info("==Login by shared user - root==");
		acc.signIn(USER_SHARED[0], USER_SHARED_PASS);

		info("==Add event on shared calendar==");
		goToCalendarPage();
		Utils.pause(5000);
		evt.addQuickEvent(EVENT09,EVENT09,getDate(0,"MM/dd/yyyy"), getDate(0,"MM/dd/yyyy"), false, CAL_09, EVENT_CATEGORY);
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", EVENT09)));

		info("==Edit an event==");
		evt.editEvent(EVENT09,TITLE,DESCRIPTION, null,null,null,false);

		info("==Restore data==");
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", EVENT09)));
		deleteCalendar(CAL_09,true);
	}

	/**
	 * Delete event in shared Calendar
	 * CaseID 69291
	 */
	@Test
	public void test10_deleteEventinSharedCal() {
		String EVENT10 = "EVENT_10";
		String CAL_10 = "CAL_10";
		String EVENT_CATEGORY = "All";
		String[] USER_SHARED = {USER_ROOT};
		String USER_SHARED_PASS = PASS_ROOT;
		boolean[] EDITABLE = {true};

		info("==Go to Intranet Calendar==");
		goToCalendarPage();

		info("==Create a shared calendar==");
		driver.navigate().refresh();
		addCalendar(CAL_10, CAL_10, "blue");
		Utils.pause(3000);
		shareCalendar(CAL_10, USER_SHARED, EDITABLE);

		info("==User fqa sign out==");
		acc.signOut();

		info("==Login by shared user - root==");
		acc.signIn(USER_SHARED[0], USER_SHARED_PASS);

		info("==Add a new event on shared calendar==");
		goToCalendarPage();
		Utils.pause(5000);
		evt.addQuickEvent(EVENT10,EVENT10,getDate(0,"MM/dd/yyyy"), getDate(0,"MM/dd/yyyy"), false, CAL_10, EVENT_CATEGORY);
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", EVENT10)));
		deleteEventTask(EVENT10, selectDayOption.ONEDAY);
		
		info("==Restore data==");
		deleteCalendar(CAL_10,true);
	}
//
//	/**
//	 * Edit event in Group Calendar
//	 * CaseID 69288
//	 */
//	@Test
//	public void test07_editEventinGroupCal() {
//		String EVENT07 = "EVENT_07";
//		String CAL_07 = "CAL_07";
//		String CAL_GROUP = "/platform/web-contributors";
//		String TITLE = "EVENT_07_edited";
//		String DESCRIPTION = "EVENT_07_description_edited";
//		String EVENT_CATEGORY = "All";
//		String USER_GROUP = "root";
//		String USER_GROUP_PASS = "gtngtn";
//
//		info("==Go to Intranet Calendar==");
//		goToCalendarPage();
//
//		info("==Create a group calendar==");
//		driver.navigate().refresh();
//		addCalendar(CAL_07, CAL_07, "green", CAL_GROUP);
//		Utils.pause(3000);
//		changeEditPermissionForCalShowInGroup(CAL_07, USER_GROUP, CAL_GROUP);
//
//		info("==User fqa log out==");
//		acc.signOut();
//
//		info("==Login by shared user==");
//		acc.signIn(USER_GROUP, USER_GROUP_PASS);
//		Utils.pause(5000);
//
//		info("==Add a new event on group calendar==");
//		goToCalendarPage();
//		Utils.pause(5000);
//		evt.addQuickEvent(EVENT07,EVENT07,getDate(0,"MM/dd/yyyy"),getDate(0,"MM/dd/yyyy"),false, CAL_07, EVENT_CATEGORY);
//
//		info("==Edit an event==");
//		Utils.pause(5000);
//		evt.editEvent(EVENT07,TITLE,DESCRIPTION, null,null,null,false);
//
//		info("==Restore data==");
//		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", EVENT07)));
//		deleteEventTask(EVENT07, selectDayOption.ONEDAY);
//		deleteCalendar(CAL_07,true);
//	}
//
//	/**
//	 * Delete event in group Calendar
//	 * CaseID 69289
//	 */
//	@Test
//	public void test08_deleteEventinGroupCal() {
//		String EVENT08 = "EVENT_08";
//		String CAL_08 = "CAL_08";
//		String CAL_GROUP = "/platform/web-contributors";
//		String EVENT_CATEGORY = "All";
//		String USER_GROUP = "root";
//		String USER_GROUP_PASS = "gtngtn";
//
//		info("==Go to Intranet Calendar==");
//		goToCalendarPage();
//
//		info("==Create a group calendar==");
//		driver.navigate().refresh();
//		addCalendar(CAL_08, CAL_08, "green", CAL_GROUP);
//		Utils.pause(3000);
//		changeEditPermissionForCalShowInGroup(CAL_08, USER_GROUP, CAL_GROUP);
//
//		info("==User fqa log out==");
//		acc.signOut();
//
//		info("==Login by shared user==");
//		acc.signIn(USER_GROUP, USER_GROUP_PASS);
//		Utils.pause(5000);
//
//		info("==Add a new event on group calendar==");
//		goToCalendarPage();
//		Utils.pause(5000);
//		evt.addQuickEvent(EVENT08,EVENT08,getDate(0,"MM/dd/yyyy"),getDate(0,"MM/dd/yyyy"),false, CAL_08, EVENT_CATEGORY);
//
//		info("==Delete an event on group calendar==");
//		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", EVENT08)));
//		deleteEventTask(EVENT08, selectDayOption.ONEDAY);
//		deleteCalendar(CAL_08,true);
//	}
//
//	/**
//	 * Edit event in shared Calendar
//	 * CaseID 69292
//	 */
//	@Test
//	public void test09_editEventinSharedCal() {
//		String EVENT09 = "EVENT_09";
//		String CAL_09 = "CAL_09";
//		String TITLE = "EVENT_09_edited";
//		String DESCRIPTION = "EVENT_09_description_edited";
//		String EVENT_CATEGORY = "All";
//		String[] USER_SHARED = {"root"};
//		String USER_SHARED_PASS = "gtngtn";
//		boolean[] EDITABLE = {true};
//
//		info("==Go to Intranet Calendar==");
//		goToCalendarPage();
//
//		info("==Create and share a calendar with User root==");
//		driver.navigate().refresh();
//		addCalendar(CAL_09, CAL_09, "pink");
//		Utils.pause(3000);
//		shareCalendar(CAL_09, USER_SHARED, EDITABLE);
//
//		info("==User fqa sign out==");
//		acc.signOut();
//
//		info("==Login by shared user - root==");
//		acc.signIn(USER_SHARED[0], USER_SHARED_PASS);
//
//		info("==Add event on shared calendar==");
//		goToCalendarPage();
//		Utils.pause(5000);
//		evt.addQuickEvent(EVENT09,EVENT09,getDate(0,"MM/dd/yyyy"), getDate(0,"MM/dd/yyyy"), false, CAL_09, EVENT_CATEGORY);
//		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", EVENT09)));
//
//		info("==Edit an event==");
//		evt.editEvent(EVENT09,TITLE,DESCRIPTION, null,null,null,false);
//
//		info("==Restore data==");
//		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", EVENT09)));
//		deleteEventTask(EVENT09, selectDayOption.ONEDAY);
//		deleteCalendar(CAL_09,true);
//	}
//
//	/**
//	 * Delete event in shared Calendar
//	 * CaseID 69291
//	 */
//	@Test
//	public void test10_deleteEventinSharedCal() {
//		String EVENT10 = "EVENT_10";
//		String CAL_10 = "CAL_10";
//		String EVENT_CATEGORY = "All";
//		String[] USER_SHARED = {"root"};
//		String USER_SHARED_PASS = "gtngtn";
//		boolean[] EDITABLE = {true};
//
//		info("==Go to Intranet Calendar==");
//		goToCalendarPage();
//
//		info("==Create a shared calendar==");
//		driver.navigate().refresh();
//		addCalendar(CAL_10, CAL_10, "blue");
//		Utils.pause(3000);
//		shareCalendar(CAL_10, USER_SHARED, EDITABLE);
//
//		info("==User fqa sign out==");
//		acc.signOut();
//
//		info("==Login by shared user - root==");
//		acc.signIn(USER_SHARED[0], USER_SHARED_PASS);
//
//		info("==Add a new event on shared calendar==");
//		goToCalendarPage();
//		Utils.pause(5000);
//		evt.addQuickEvent(EVENT10,EVENT10,getDate(0,"MM/dd/yyyy"),getDate(0,"MM/dd/yyyy"),false, CAL_10, EVENT_CATEGORY);
//
//		info("==Delete an event on shared calendar==");
//		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", EVENT10)));
//		deleteEventTask(EVENT10, selectDayOption.ONEDAY);
//
//		info("==Restore data==");
//		deleteCalendar(CAL_10,true);
//	}

	/** 
	 * Check pop-up reminder of an event
	 * CaseID: 75245
	 */
	//related issue: FQA-1351
	@Test(groups={"pending"})
	public void test11_CheckPopupReminderOfEvent() {

	}

	/** 
	 * Check E-mail reminder of an event
	 * CaseID: 75246
	 */
	//Related issue: FQA-1351
	@Test(groups={"pending"})
	public void test12_CheckEmailReminderOfEvent() {

	}

	/** 
	 * Resize an event
	 * CaseID: 68660
	 */
	//Related issue: FQA-1352
	@Test(groups={"pending"})
	public void test13_ResizeEvent(){

	}
	
	/**
	 * CaseId: 111483 check date suggestion
	 */
	@Test
	public void test04_CheckDateSuggestion(){
		String calendar = "Calendar_111483";
		String color = "sky_blue";
		String event1 = "Event_111483_1";
		String event2 = "Event_111483_2";
		String event3 = "Event_111483_3";
		String event4 = "Event_111483_4";
		
		goToCalendarPage();		
		info("Create new personal calendar");
		addCalendar(calendar, null, color);
		
		info("Check date suggestion when add event from click setting on calendar");
		evt.goToAddEventFromCalendar(calendar);
		evt.checkSuggestionEventTime(null, 60);
		evt.checkSuggestionEventTime("08:00", 60);
		evt.inputAddEventForm(event1, null, null, null, null, false, calendar);
		click(evt.ELEMENT_ADD_EVENT_SAVE_QUICK_BUTTON);
		waitForElementNotPresent(evt.ELEMENT_ADD_EVENT_POPUP);
		
		info("Check date suggestion when add event from click setting on action bar");
		evt.goToAddEventFromActionBar();
		evt.checkSuggestionEventTime(null, 60);
		evt.checkSuggestionEventTime("09:00", 60);
		evt.inputAddEventForm(event2, null, null, null, null, false, calendar);
		click(evt.ELEMENT_ADD_EVENT_SAVE_QUICK_BUTTON);
		waitForElementNotPresent(evt.ELEMENT_ADD_EVENT_POPUP);
		
		info("Check date suggestion when add event from click on block time in main panel");
		evt.goToAddEventByClickOnCell("10:00");
		evt.checkSuggestionEventTime(null, 30);
		evt.checkSuggestionEventTime("11:00", 60);
		evt.inputAddEventForm(event3, null, null, null, null, false, calendar);
		click(evt.ELEMENT_ADD_EVENT_SAVE_QUICK_BUTTON);
		waitForElementNotPresent(evt.ELEMENT_ADD_EVENT_POPUP);
		
		info("Check date suggestion when add event from right click on main panel");
		evt.goToAddEventFromMainPane("12:00");
		evt.checkSuggestionEventTime(null, 60);
		evt.checkSuggestionEventTime("14:00", 60);
		evt.inputAddEventForm(event4, null, null, null, null, false, calendar);
		click(evt.ELEMENT_ADD_EVENT_SAVE_QUICK_BUTTON);
		waitForElementNotPresent(evt.ELEMENT_ADD_EVENT_POPUP);
		
		deleteCalendar(calendar);
	}
}
