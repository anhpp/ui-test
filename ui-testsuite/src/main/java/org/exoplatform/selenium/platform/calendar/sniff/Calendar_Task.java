package org.exoplatform.selenium.platform.calendar.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.calendar.CalendarBase;
import org.exoplatform.selenium.platform.calendar.Event;
import org.exoplatform.selenium.platform.calendar.Task;

/**
 * 
 * @author havtt
 * @date 17 Oct 2013
 */
/**
 * @date: 25/04/2014
 * @author lientm
 * @description: update suggestion date follow https://jira.exoplatform.org/browse/FQA-1721 
 */
/**
 * @author chinhdtt
 * @date 01 Aug 2014
 * @description:Add new 8cases: 
 * 			+ Manage task in group calendar
 * 			+ Manage task in shared calendar
 * 			+ Manage task in personal calendar
 * 			+ Task/Attachment
 */
public class Calendar_Task extends CalendarBase {

	ManageAccount acc;
	Event event; 
	Task task; 

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		acc = new ManageAccount(driver, this.plfVersion);
		event = new Event(driver, this.plfVersion);
		task = new Task(driver, this.plfVersion);
		acc.signIn(DATA_USER1, DATA_PASS);		
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**Testcase to check add task from 3 ways
	 * CaseID 112466: add new task from action bar or on a calendar
	 * Case ID:111869: Edit a task in personal calendar.
	 * Case ID:111870: Delete a task in personal calendar.
	 */

	/* caseId: 112466 -> Add a task in personal calendar by click Task on action bar */
	@Test
	public void test01_AddEditDeleteTask_FromActionBar(){
		String calendar = "Calendar_112466";
		String color = "sky_blue";
		String task1 = "Task_112466";
		String newTask = "Task_112466_update";
		String note = "Update new task";

		goToCalendarPage();
		setTimezoneForCalendar("(GMT +07:00) Asia/Ho_Chi_Minh");

		addCalendar(calendar, null, color);
		task.goToAddTaskFromActionBar();
		task.checkSuggestionTaskTime(null, 30);
		task.checkSuggestionTaskTime("07:00", 30);
		task.inputDataTask(task1, null, null, null, false, calendar);
		task.editTask(task1, newTask, note, getDate(0,"MM/dd/yyyy") + " 12:00", getDate(0,"MM/dd/yyyy") + " 13:00", false, "/TestData/Winter.jpg");
		Utils.pause(3000);
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", newTask)));

		task.deleteEventTask(newTask);
		deleteCalendar(calendar);
	}

	/**
	 * Case ID:111868.
	 * Test Case Name: Add a task in personal calendar by icon settings of a calendar.
	 */
	@Test
	public void test02_AddEditDeleteTask_FromOnCalendar(){
		String calendar = "Calendar_111868";
		String color = "sky_blue";
		String task1 = "Task_111868";
		String newTask = "Task_111868_update";
		String note = "Update new task";

		goToCalendarPage();
		setTimezoneForCalendar("(GMT +07:00) Asia/Ho_Chi_Minh");

		addCalendar(calendar, null, color);
		task.goToAddTaskFromCalendar(calendar);
		task.checkSuggestionTaskTime(null, 30);
		task.checkSuggestionTaskTime("07:00", 30);
		task.inputDataTask(task1, null, null, null, false, calendar);
		task.editTask(task1, newTask, note, getDate(0,"MM/dd/yyyy") + " 12:00", getDate(0,"MM/dd/yyyy") + " 13:00", false, "/TestData/Winter.jpg");
		Utils.pause(3000);
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", newTask)));

		task.deleteEventTask(newTask);
		deleteCalendar(calendar);
	}

	/**
	 * Case ID:112467.
	 * Test Case Name: Add a task in personal calendar by right click Task on working pane.
	 */
	@Test
	public void test03_AddEditDeleteTask_FromMainPane(){
		String calendar = "Calendar_112467";
		String color = "sky_blue";
		String task1 = "Task_112467";
		String newTask = "Task_112467_update";
		String note = "Update new task";

		goToCalendarPage();
		setTimezoneForCalendar("(GMT +07:00) Asia/Ho_Chi_Minh");

		addCalendar(calendar, null, color);
		task.goToAddTaskFromMainPane("12:00");
		task.checkSuggestionTaskTime(null, 30);
		task.checkSuggestionTaskTime("07:00", 30);
		task.inputDataTask(task1, null, null, null, false, calendar);
		task.editTask(task1, newTask, note, getDate(0,"MM/dd/yyyy") + " 12:00", getDate(0,"MM/dd/yyyy") + " 13:00", false, "/TestData/Winter.jpg");
		Utils.pause(3000);
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", newTask)));

		task.deleteEventTask(newTask);
		deleteCalendar(calendar);
	}

	/** 
	 * Check pop-up reminder of a task
	 * CaseID: 68654
	 * find a solution to check the pop up of the event.
	 */
	@Test(groups={"pending"})
	public void test04_CheckPopupReminderOfTask() {

		String CALENDAR02 = "CALENDAR_02";

		info("Go to Intranet Calendar");
		goToCalendarPage();
		driver.navigate().refresh();

		info("Add a new task");
		task.goToAddTaskFromActionBar();
		task.inputBasicQuickTask(CALENDAR02,CALENDAR02);
		task.inputFromToTask(getDate(0,"MM/dd/yyyy"),getDate(0,"MM/dd/yyyy"),false);
		info("Setting reminder for task");
		task.gotoSetPopupReminder();
		click(task.ELEMENT_CHECKBOX_POPUP_REMINDER);
		click(task.ELEMENT_BUTTON_TASK_SAVE_DETAILS);

		info("Check pop-up reminder appear");
		//TO-DO: need to add confirmation here after finishing setting reminder methods

		
		
		info("Restore data");
		Utils.pause(3000);
		deleteEventTask(CALENDAR02,selectDayOption.ONEDAY);

	}

	/** 
	 * Check E-mail reminder of a task
	 * CaseID: 68655
	 * 
	 */
	@Test
	public void test05_CheckEmailReminderOfTask() {

		String CALENDAR03 = "CALENDAR_03";
		//TO-DO: update later
		String ELEMENT_CAL_GMAIL_REMINDER = "";
		String ELEMENT_GMAIL_CONTENT = "";
		String FROM_TIME = getDate(0,"MM/dd/yyyy");
		String TO_TIME = getDate(0,"MM/dd/yyyy");

		info("Go to Intranet Calendar");
		goToCalendarPage();
		driver.navigate().refresh();

		info("Add a new task");
		task.goToAddTaskFromActionBar();
		task.inputBasicQuickTask(CALENDAR03,CALENDAR03);
		task.inputFromToTask(FROM_TIME,TO_TIME,false);
		info("Setting reminder for task");
		task.gotoSetEmailReminder();
		//TO-DO: update after finishing setting reminder methods
		click(task.ELEMENT_BUTTON_TASK_SAVE_DETAILS);
		Utils.pause(3000);

		info("Check if e-mail is sent");
		goToMail(EMAIL_ADDRESS1, EMAIL_PASS);
		checkAndDeleteMail(By.xpath(ELEMENT_CAL_GMAIL_REMINDER.replace("${taskName}", CALENDAR03)), ELEMENT_GMAIL_CONTENT);
		//TO-DO: update after finishing setting reminder methods
		switchToParentWindow();

		info("Restore data");
		Utils.pause(3000);
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ALL_DAY.replace("${taskTitle}", CALENDAR03)));
		deleteEventTask(CALENDAR03,selectDayOption.ONEDAY);
	}

	/** 
	 * Drag & drop a task
	 * CaseID: 75249
	 */
	@Test
	public void test06_DragDropTask(){
		String CALENDAR06 = "CALENDAR_06";

		info("Go to Intranet Calendar");
		goToCalendarPage();
		driver.navigate().refresh();

		info("Add a new task");
		task.addQuickTask(CALENDAR06,CALENDAR06,getDate(0,"MM/dd/yyyy"),getDate(0,"MM/dd/yyyy"),false);

		info("Drag & drop a task");
		Utils.pause(3000);
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", CALENDAR06)));
		dragAndDropToObject(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", CALENDAR06)),ELEMENT_TARGET_DATE);

		info("Restore data");
		deleteEventTask(CALENDAR06, selectDayOption.ONEDAY);
	}

	/** 
	 * Resize a task
	 * CaseID: 75250
	 *
	 */
	@Test(groups={"pending"})
	public void test07_ResizeTask(){
		String CALENDAR07 = "CALENDAR_07";

		info("Go to Intranet Calendar");
		goToCalendarPage();

		info("Add a new task");
		task.goToAddTaskFromActionBar();
		task.inputBasicQuickTask(CALENDAR07,CALENDAR07);
		task.inputFromToTask(getDate(0,"MM/dd/yyyy"),getDate(0,"MM/dd/yyyy"),false);
		Utils.pause(3000);
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ALL_DAY.replace("${event}", CALENDAR07))); // bug

		info("Resize a task to change date-time");
		//TO-DO: update later

		info("Restore data");
		Utils.pause(3000);
		deleteEventTask(CALENDAR07, selectDayOption.ONEDAY);
	}

	/**
	 * Case ID:111865, 111867.
	 * Test Case Name: 
	 *          + Add a task in group calendar.
	 *          + Delete a task in group calendar.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/08/01 16:54:44
	 */
	@Test
	public  void test01_02_AddDeleteATaskInGroupCalendar() {
		info("Test 1: Add a task in group calendar");
		String calendar = "Calendar 111865";
		String[] groups = {"/developers"};
		String task1 = "Task 111865";

		//Setting time zone
		goToCalendarPage();
		setTimezoneForCalendar("(GMT +07:00) Asia/Ho_Chi_Minh");

		/*
		- Login by user who has edit right on a group calendar
		- Click Setting icon of this group calendar and choose [Add Task]
		 *Expected Outcome: 
		- The pop up "Quick Add Task" is displayed
		- The Default Start date "From" is set to Today (System date)
		- The default duration for Task is 30 minutes		*/
		addCalendar(calendar, calendar, "red", groups);
		task.goToAddTaskFromCalendar(calendar);
		waitForAndGetElement(task.ELEMENT_QUICK_ADD_TASK_POPUP);
		info("Check default date");
		String dateFrom = getValue(task.ELEMENT_INPUT_TASK_FROM);
		String dateTo = getValue(task.ELEMENT_INPUT_TASK_TO);
		Utils.pause(3000);
		assert dateFrom.equals(getCurrentDate("MM/dd/yyyy"));
		assert dateTo.equals(getCurrentDate("MM/dd/yyyy"));

		info("Check default time ");
		task.checkSuggestionTaskTime(null, 30);

		/*
		- Fill values
		- Save
		 *Input Data: 
		 *Expected Outcome: 
		- New task for that group calendar is added successfully
		- The other users in shared group can view the task in the group calendar		*/ 
		info("Add task for calendar");
		task.inputDataTask(task1, null, null, null, false, calendar);
		info("User of group view calendar");
		acc.userSignIn(userType.DEVELOPER);
		goToCalendarPage();
		waitForAndGetElement(ELEMENT_CALENDAR_GET_BY_TAG_LI.replace("${calendar}", calendar));
		info("User isn't member of Group can't view");
		acc.userSignIn(userType.PUBLISHER);
		goToCalendarPage();
		waitForElementNotPresent(ELEMENT_CALENDAR_GET_BY_TAG_LI.replace("${calendar}", calendar));

		//Delete data test
		acc.userSignIn(userType.ADMIN);
		goToCalendarPage();
		deleteCalendar(calendar);
	}

	/**
	 * Case ID:111866.
	 * Test Case Name: Edit an event in group calendar.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/08/01 16:54:44
	 */
	@Test
	public  void test03_EditAnEventInGroupCalendar() {
		info("Test 2: Edit an event in group calendar");
		String calendar = "Calendar 111866";
		String[] groups = {"/developers"};
		String task1 = "Task 111866";
		String taskUpdateGroup = "Update 111866";
		String from = getDate(0,"MM/dd/yyyy") + " 12:00";

		goToCalendarPage();
		addCalendar(calendar, calendar, "orange", groups);
		task.goToAddTaskFromCalendar(calendar);
		info("Add task for calendar");
		task.inputDataTask(task1, null, null, null, false, calendar);


		/*Step 1: Edit an event to a group calendar
		 *Expected Outcome: 
		- To time is automatically set = From Time + 30min		*/
		info("Edit task");
		driver.navigate().refresh();
		task.goToEditTaskForm(task1);
		task.inputBasicQuickTask(taskUpdateGroup, taskUpdateGroup);
		info("Update time");
		String[] dateTime = from.split(" ");
		click(task.ELEMENT_ADD_EDIT_TASK_FROM_TIME_IN, 2);
		click(task.ELEMENT_ADD_EDIT_TASK_SELECT_FROM_TIME.replace("${time}", dateTime[1]));
		Utils.pause(1000);
		info("Check To time is automatically set = From Time + 30min");
		info(getValue(task.ELEMENT_ADD_EDIT_TASK_TO_TIME_IN));
		Utils.pause(3000);
		assert getValue(task.ELEMENT_ADD_EDIT_TASK_TO_TIME_IN).equals("12:30");

		/*Step 2: Save
		 *Input Data: 
		- Save
		 *Expected Outcome: 
		- Event is saved successfully
		- The other users in group can view updated event in the group calendar		*/ 
		click(task.ELEMENT_BUTTON_TASK_SAVE_EDIT);
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", taskUpdateGroup)));

		info("Member of group check");
		acc.userSignIn(userType.DEVELOPER);
		goToCalendarPage();
		waitForAndGetElement(ELEMENT_CALENDAR_GET_BY_TAG_LI.replace("${calendar}", calendar));
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", taskUpdateGroup)));

		//Delete data test
		acc.userSignIn(userType.ADMIN);
		goToCalendarPage();
		deleteCalendar(calendar);
	}	

	/**
	 * Case ID:111871, 111872.
	 * Test Case Name: 
	 * 			+ Add a task for shared calendar.
	 * 			+ Delete a task in shared calendar.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/08/01 14:50:33
	 */
	@Test
	public  void test01_02_AddDeleteATaskForSharedCalendar() {
		info("Test 1: Add a task for shared calendar");
		String calendar = "Calendar 111871";
		String task1 = "Task 111871";
		String[] groupShare = {"mary"};
		boolean[] edit = {true};

		//Setting time zone
		goToCalendarPage();
		setTimezoneForCalendar("(GMT +07:00) Asia/Ho_Chi_Minh");

		/*
		- Create personal calendar
		- Share added calendar with edit right
		 *Expected Outcome: Calendar is created and shared		*/
		addCalendar(calendar, calendar, "red");
		shareCalendar(calendar, groupShare, edit , 1);

		/*
		- Login by shared user
		- Click wheel icon of shared calendar and select Add task
		 *Input Data: 
		 *Expected Outcome: 
		- The pop up "Quick Add Task" is displayed
		- The Default Start date "From" is set to Today (System date)
		- The default duration for task is 30 minutes		*/
		acc.userSignIn(userType.PUBLISHER);
		goToCalendarPage();
		task.goToAddTaskFromCalendar(calendar);
		waitForAndGetElement(task.ELEMENT_QUICK_ADD_TASK_POPUP);
		info("Check default date");
		String dateFrom = getValue(task.ELEMENT_INPUT_TASK_FROM);
		String dateTo = getValue(task.ELEMENT_INPUT_TASK_TO);
		Utils.pause(3000);
		assert dateFrom.equals(getCurrentDate("MM/dd/yyyy"));
		assert dateTo.equals(getCurrentDate("MM/dd/yyyy"));

		info("Check default time ");
		task.checkSuggestionTaskTime(null, 30);

		/*
		- Fill values
		- Save
		 *Input Data: 
		 *Expected Outcome: Task is added successfully in shared calendar		*/ 
		info("Add task for calendar");
		task.inputDataTask(task1, null, null, null, false, calendar);

		//Delete data test
		acc.userSignIn(userType.ADMIN);
		goToCalendarPage();
		deleteCalendar(calendar);
	}

	/**
	 * Case ID:111873.
	 * Test Case Name: Edit a task in shared calendar.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/08/01 14:50:33
	 */
	@Test
	public  void test03_EditATaskInSharedCalendar() {
		info("Test 3: Edit a task in shared calendar");
		String calendar = "Calendar 111873";
		String task1 = "Task 111873";
		String[] groupShare = {"mary"};
		boolean[] edit = {true};
		String taskUpdate = "Task Update 111873";
		String from = getDate(0,"MM/dd/yyyy") + " 10:00";

		//Setting time zone
		goToCalendarPage();
		setTimezoneForCalendar("(GMT +07:00) Asia/Ho_Chi_Minh");

		/*
		- Create personal calendar
		- Share added calendar with edit right
		 *Expected Outcome: Calendar is created and shared		*/
		addCalendar(calendar, calendar, "purple");
		shareCalendar(calendar, groupShare, edit , 1);

		/*
		- Login by shared user
		- Click wheel icon of shared calendar and select Add task
		- Input task summary and click Save
		 *Input Data: 
		 *Expected Outcome: 
		- The shared user can see the shared calendar and add task into it.		*/
		acc.userSignIn(userType.PUBLISHER);
		goToCalendarPage();
		task.goToAddTaskFromCalendar(calendar);
		info("Add task for calendar");
		task.inputDataTask(task1, null, null, null, false, calendar);

		/*
		- Right click on event then choose Edit
		- Update some values
		- Change From time
		 *Input Data: 
		 *Expected Outcome: 
		- To time is automatically set = From Time + 30min		*/
		info("Edit event");
		driver.navigate().refresh();
		task.goToEditTaskForm(task1);
		task.inputBasicQuickTask(taskUpdate, taskUpdate);
		info("Update time");
		String[] dateTime = from.split(" ");
		click(task.ELEMENT_ADD_EDIT_TASK_FROM_TIME_IN, 2);
		click(task.ELEMENT_ADD_EDIT_TASK_SELECT_FROM_TIME.replace("${time}", dateTime[1]));
		Utils.pause(1000);
		info("Check To time is automatically set = From Time + 30min");
		info(getValue(task.ELEMENT_ADD_EDIT_TASK_TO_TIME_IN));
		Utils.pause(3000);
		assert getValue(task.ELEMENT_ADD_EDIT_TASK_TO_TIME_IN).equals("10:30");

		/*
		- Save
		 *Input Data: 
		 *Expected Outcome: 
		- Task in shared calendar is edited
		- Sharing user can see updated task		*/ 
		click(task.ELEMENT_BUTTON_TASK_SAVE_EDIT);
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", taskUpdate)));

		//Delete data test
		acc.userSignIn(userType.ADMIN);
		goToCalendarPage();
		deleteCalendar(calendar);
	}

	/**
	 * Case ID:111879, 111880.
	 * Test Case Name: 
	 *           + Add attachment to task.
	 *           + Remove attachment of task.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/08/01 17:55:51
	 */
	@Test
	public  void test01_AddRemoveAttachmentToTask() {
		info("Test 1: Add attachment to task");
		String calendar = "Calendar 111879";
		String task1 = "Task 111879";
		/*
		- Select a calendar, Click Setting icon of this calendar and choose [Add Task] or Click Task button on action bar
		- Input start and end time
		- Click [More Details
		 *Expected Outcome: Add/Edit Task pop-up has appears		*/
		/*
		- Click [Add Attachment]
		- Browse to file and click save
		 *Input Data: 
		 *Expected Outcome: 
		- Attachment is added to event		*/ 

		//Setting time zone
		goToCalendarPage();
		setTimezoneForCalendar("(GMT +07:00) Asia/Ho_Chi_Minh");

		addCalendar(calendar, calendar, "orange");		
		info("Add a attachment to task");
		task.goToAddTaskFromCalendar(calendar);
		click(task.ELEMENT_BUTTON_TASK_MORE_DETAILS);
		task.inputDataTabDetailTask(task1, task1, null, null, false, "/TestData/Winter.jpg");
		click(task.ELEMENT_BUTTON_TASK_SAVE_DETAILS);
		Utils.pause(500);
		driver.navigate().refresh();
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", task1)));

		//Remove attachment of task
		info("Edit task");
		task.goToEditTaskForm(task1);
		waitForAndGetElement(task.ELEMENT_TASK_ATTACHMENT.replace("${file}", "Winter.jpg"));
		info("Remove attachment");
		waitForAndGetElement(task.ELEMENT_TASK_ATTACHMENT.replace("${file}", "Remove"));
		click(task.ELEMENT_TASK_ATTACHMENT.replace("${file}", "Remove"));
		waitForElementNotPresent(task.ELEMENT_TASK_ATTACHMENT.replace("${file}", "Winter.jpg"));

		//Delete data test
		deleteCalendar(calendar);
	}

}