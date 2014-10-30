package org.exoplatform.selenium.platform.plf.functional.unifiedsearch;
import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.HomePageGadget;
import org.exoplatform.selenium.platform.SettingSearchPage;
import org.exoplatform.selenium.platform.calendar.CalendarBase;
import org.exoplatform.selenium.platform.calendar.Event;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.social.SpaceManagement;
import org.openqa.selenium.By;
import org.testng.annotations.*;


public class PLF_UnifiedSearch_EventSearch extends CalendarBase {
	ManageAccount magAcc;
	ManageMember magMember;
	
	Event evt;
	SettingSearchPage qsPage;
	SpaceManagement spaceMag;
	HomePageGadget hGadget; 
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver, this.plfVersion);
		magAcc.signIn(DATA_USER1, DATA_PASS);
		hGadget = new HomePageGadget(driver);
		magMember = new ManageMember(driver,this.plfVersion);
		evt = new Event(driver, this.plfVersion);
		qsPage = new SettingSearchPage(driver);
		spaceMag = new SpaceManagement(driver);
		button = new Button(driver);
	}
	
	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();		
		driver.quit();
	}
	
	/*
	 *==Click on event result should open event details in calendar application==
	 * Test ID:104242
	 * Step 1 :Search for Diner, click on an event result
	 */
	@Test
	public void test01_ClickOnEventResultShouldOpenEventDetailsInCalendarApplication(){
		String eventName="Diner";
		info("Test01 - click on event result should open event details in calendar application");
		
		/*
		 * pre condition
		 */
		goToCalendarPage();
		evt.addQuickEvent(eventName,eventName,getDate(1,"MM/dd/yyyy"),getDate(1,"MM/dd/yyyy"),false);
		info(" Add event : diner");
		
		/*
		 * Step 1:Search for Diner, click on an event result
		 */
		qsPage.quickSearch(eventName);
		click(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", eventName));
		
		waitForAndGetElement(evt.ELEMENT_EVENT_PREVIEW_TITLE.replace("${event}", eventName));
		button.close();
		
		/*
		 * clear data
		 */
		evt.deleteEventTask(eventName);
		info("Delete Event Diner");
	}
	
	
	/*
	 * ==Display event quick result==
	 * ID :104237
	 * Step 1: search 3.5
	 */
	@Test
	public void test02_DisplayEventQuickResult(){
		String eventName="Gatein 35 party";
		String researchWord="35";
		String eventDescription="Gatein 35 has been released! It's time to make a party! Enjoy some good sounds and good sushis!";
		String eventLocation="Tatakis Suchis bar, San Francisco";
		String spaceName="Engineering104237";
		info("Test02 - Display Event quick result");
		
		/*
		 * Pre conditions
		 */
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");
		info("New space added : "+spaceName);
		click(spaceMag.ELEMENT_SPACE_CALENDAR_PORTLET);
		evt.addQuickEvent(eventName,eventDescription,getDate(1,"MM/dd/yyyy 20:00"),getDate(1,"MM/dd/yyyy 23:00"),false);
		evt.editEvent(eventName,eventName, eventDescription,eventLocation,getDate(1,"MM/dd/yyyy 20:00"), getDate(1,"MM/dd/yyyy 23:00"), false);
		info("New event "+eventName+" create");
		
		/*
		 * Step 1 : In search input 3.5
		 */
		Utils.pause(2000);
		click(ELEMENT_QUICK_SEARCH_ICON);
		Utils.pause(2000);
		type(ELEMENT_QUICK_SEARCH_TEXTBOX,researchWord,true);
		waitForAndGetElement(By.xpath(qsPage.ELEMENT_QUICK_SEARCH_BOX_RESULTS.replace("{$result}", eventName).replace("{$position}", "1")));
		
		/*
		 * delete data
		 */
		magMember.goToMySpacePage();
		magMember.deleteSpace(spaceName);
		info("Event & space delete");
		
	}
	
	/*
	 * ==Display event result==
	 * Case ID : 104238
	 * Step 1: In quick search input 3.5
	 * PLF-5212
	 */
	@Test
	public void test03_DisplayEventResult(){
		String eventName="Gatein 35";
		String researchWord="35";
		String eventDescription="Gatein 35 has been released! It's time to make a party! Enjoy some good sounds and good sushis!";
		String eventLocation="Tatakis Suchis bar, San Francisco";
		String spaceName="Engineering3";
		info("Test03 - Display Event result");
		
		/*
		 * Pre conditions
		 */
//		magMember.goToMySpacePage();
//		magMember.addNewSpace(spaceName, "");
//		info("New space added : "+spaceName);
//		click(spaceMag.ELEMENT_SPACE_CALENDAR_PORTLET);
//		setTimezoneForCalendar("(GMT +07:00) Asia/Ho_Chi_Minh");
//		evt.addQuickEvent(eventName,eventDescription,getDate(1,"MM/dd/yyyy 20:00"),getDate(1,"MM/dd/yyyy 23:00"),false);
//		evt.editEvent(eventName,eventName, eventDescription,eventLocation,getDate(1,"MM/dd/yyyy 20:00"), getDate(1,"MM/dd/yyyy 23:00"), false);
//		info("New event "+eventName+" create");
		
		/*
		 * Step 1 : In quick search input 3.5
		 */
		qsPage.quickSearch(researchWord);
		qsPage.searchInSearchPage(researchWord);
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", researchWord));
		waitForAndGetElement(ELEMENT_SEARCH_EVENT_ICON.replace("${month}", getDate(1, "MMM")).replace("${date}", getDate(1, "dd")));
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", researchWord));
		String searchTaskDate = waitForAndGetElement(qsPage.ELEMENT_RESULT_LOCATION_DATETIME.replace("${keySearch}", researchWord).replace("${item}", "Gatein ")).getText();
		String searchDate = searchTaskDate.substring(searchTaskDate.indexOf(',')+1).trim();
		assert searchDate.contains(getDate(1, "MMM"));
		assert searchDate.contains(getDate(1, "dd"));
		assert searchDate.contains("8:00 PM");
		
		String comtentEvent= waitForAndGetElement(ELEMENT_RESULT_SEARCH_PAGE).getText();
		assert comtentEvent.contains(eventDescription);
		String comtentEvent1= waitForAndGetElement(ELEMENT_RESULT_SEARCH_PAGE).getText();
		assert comtentEvent1.contains(eventLocation);
		String comtentEvent2= waitForAndGetElement(ELEMENT_RESULT_SEARCH_PAGE).getText();
		assert comtentEvent2.contains(spaceName);
		
		goToCalendarPage();
		/*
		 * Clear data
		 */
		evt.deleteEventTask(eventName);
		magMember.goToMySpacePage();
		magMember.deleteSpace(spaceName);
		info("Event & space delete");				
	}
	
	/*
	 * ==Display events in quick search by pertinence==
	 * ID : 104240
	 * step 1 : search diner in quick search
	 */
	@Test(groups="pending")
	public void test04_DisplayEventsInQuickSearchByPertinence(){

		String eventName2="eventName2";
		String eventName3="eventName3";
		String diner="diner";
		String eventDescription="Futur event";
		
		/*
		 * pre conditions
		 */
		goToCalendarPage();
		
		info("Add one past and one future event in the perso cal");
		evt.addQuickEvent(diner,eventDescription,getDate(1,"MM/dd/yyyy 15:00"),getDate(1,"MM/dd/yyyy 16:00"),false);		
		driver.navigate().refresh();
		Utils.pause(2000);
		evt.addQuickEvent(eventName2,diner,getDate(1,"MM/dd/yyyy 18:00"),getDate(1,"MM/dd/yyyy 19:00"),false);
		Utils.pause(2000);
		driver.navigate().refresh();
		evt.addQuickEvent(eventName3,eventDescription,getDate(1,"MM/dd/yyyy 21:00"),getDate(1,"MM/dd/yyyy 22:00"),false);
		driver.navigate().refresh();
		Utils.pause(2000);
		/*
		 * Step 1 : search diner
		 */
		qsPage.quickSearch(diner);
		info("Search for Diner");
		
		// Look for the order of the elements
		waitForElementNotPresent((qsPage.ELEMENT_QUICK_SEARCH_BOX_RESULTS.replace("{$result}", diner).replace("{$position}", "1")));
		waitForElementNotPresent((qsPage.ELEMENT_QUICK_SEARCH_BOX_RESULTS.replace("{$result}", eventName2).replace("{$position}", "2")));
		waitForElementNotPresent((qsPage.ELEMENT_QUICK_SEARCH_BOX_RESULTS.replace("{$result}", eventName3).replace("{$position}", "3")));
		goToCalendarPage();
		/*
		 * Clear data
		 */
		evt.deleteEventTask(diner);
		evt.deleteEventTask(eventName2);
		evt.deleteEventTask(eventName3);
	}
	
	/*
	 * ISSUE CAL-1006 ! 
	 * Id test :104241
	 * Name: Private events should only be searchable by their owner
	 * Step 1 : User James search "SEARCH_EVENT_05".
	 */
	@Test
	public void test05_PrivateEventsShouldOnlyBeSearchableByTheirOwner(){
		String eventDescription="SEARCH_EVENT_05";
		String eventName1="Private shared";
		String eventName2="Public shared";
		String eventName3="Unset shared";
		String eventName4="Private group";
		String eventName5="Public group";
		String eventName6="Unset group";
		String spaceName="Engineering5";
		String calendarNameShare="CalShared";
		String[] nomShare={"james"};
		boolean[] edit={false};
		

		goToCalendarPage();
		
		info("Create a share calendar with James");
		addCalendar(calendarNameShare,"","pink");
		shareCalendar(calendarNameShare, nomShare, edit , 1);
		
		
//		 * pre conditions
		 
		info("Add event1 with private privacy");
		evt.addQuickEvent(eventName1,eventDescription,getDate(1,"MM/dd/yyyy 02:00"),getDate(1,"MM/dd/yyyy 03:00"),false,calendarNameShare);
		evt.goToEditEventForm(eventName1);
		click(evt.ELEMENT_PARTICIPANTS_TAB);
		check(evt.ELEMENT_PARTICIPANT_PRIVATE_RADIO,2);
		click(ELEMENT_BUTTON_EVENT_SAVE_EDIT);
		
		
		info("Add event2 with public shared");
		evt.addQuickEvent(eventName2,eventDescription,getDate(1,"MM/dd/yyyy 06:00"),getDate(1,"MM/dd/yyyy 07:00"),false,calendarNameShare);
		evt.goToEditEventForm(eventName2);
		click(evt.ELEMENT_PARTICIPANTS_TAB);
		check(evt.ELEMENT_PARTICIPANT_PUBLIC_RADIO,2);
		click(ELEMENT_BUTTON_EVENT_SAVE_EDIT);
		
		
		info("add event3 with unset privacy");
		evt.addQuickEvent(eventName3,eventDescription,getDate(1,"MM/dd/yyyy 08:00"),getDate(1,"MM/dd/yyyy 09:00"),false,calendarNameShare);
		
		
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");
		info("New space added : "+spaceName);
		click(spaceMag.ELEMENT_SPACE_CALENDAR_PORTLET);
		

		info("Add event3 with private privacy");
		evt.addQuickEvent(eventName4,eventDescription,getDate(1,"MM/dd/yyyy 10:00"),getDate(1,"MM/dd/yyyy 11:00"),false);
		evt.goToEditEventForm(eventName4);
		click(evt.ELEMENT_PARTICIPANTS_TAB);
		check(evt.ELEMENT_PARTICIPANT_PRIVATE_RADIO,2);	
		click(ELEMENT_BUTTON_EVENT_SAVE_EDIT);
		
		info("Add event4 with public privacy");
		evt.addQuickEvent(eventName5,eventDescription,getDate(1,"MM/dd/yyyy 12:00"),getDate(1,"MM/dd/yyyy 13:00"),false);
		evt.goToEditEventForm(eventName5);
		click(evt.ELEMENT_PARTICIPANTS_TAB);
		check(evt.ELEMENT_PARTICIPANT_PUBLIC_RADIO,2);	
		click(ELEMENT_BUTTON_EVENT_SAVE_EDIT);
		
		driver.navigate().refresh();
		Utils.pause(2000);
		
		info("Add event5 with unset privacy");
		evt.addQuickEvent(eventName6,eventDescription,getDate(1,"MM/dd/yyyy 14:00"),getDate(1,"MM/dd/yyyy 15:00"),false);

		info("Invit user James in space engineering ");
		click(magMember.ELEMENT_SPACE_SETTING_PORTLET);
		click(magMember.ELEMENT_MEMBER_TAB);
		magMember.inviteSingleUser("James");
			
		/*
		 * step 1 search for  "SEARCH_EVENT_05 "
		 */
		info("Log with user James");
		magAcc.userSignIn(userType.AUTHOR);
		info("Accept invitation");
		hGadget.acceptSpaceInvitationGadget(spaceName);
		driver.navigate().refresh();
		info("Search event : "+eventDescription);
		qsPage.quickSearch(eventDescription);
		qsPage.searchInSearchPage(eventDescription);
		info("Check the results");
		waitForElementNotPresent(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", eventName1));
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", eventName2));
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", eventName3));
		waitForElementNotPresent(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", eventName4));
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", eventName5));
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", eventName6));
	
		info("Disconnect user");
		magAcc.signOut();
		Utils.pause(800);
		
		/*
		 * delete data
		 */
		info("Delete space ");
		magAcc.signIn(DATA_USER1, DATA_PASS);

		goToCalendarPage();
		info("Delete events ");
		deleteCalendar(calendarNameShare);
		magMember.goToMySpacePage();
		magMember.deleteSpace(spaceName);
	}
	
	/*
	 * ==Search on futur events of users calendars==
	 * Id : 104239
	 * Step 1 : Search Event
	 */
	@Test
	public void test06_SearchOnFutureEventsOfUsersCalendars(){
		String spaceName="Engineering6";
		String calendarNamePerso="Perso";
		String calendarNameShare="ShareCal";
		String calendarDefault="John Smith";
		String eventDescription=" ";
		String[] nomShare={"james"};
		boolean[] editRight={false};
		String eventName1="Future event1";
		String eventName2="Future event2";
		String eventName3="Future event3";
		String eventName4="Future event4";
		String eventName5="Past event5";
		String eventName6="Past event6";
		String eventName7="Past event7";
		String eventName8="Past event8";
		/*
		 *  pre condition 
		 */
		info("Create the engineering space");
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");
	
		goToCalendarPage();
		info("Create a personal calendar");
		addCalendar(calendarNamePerso,"","blue");
		
		info("Create a share calendar with James");
		addCalendar(calendarNameShare,"","pink");
		shareCalendar(calendarNameShare,nomShare,editRight);
		
		info("Add one past and one future event in the default cal");
		evt.addQuickEvent(eventName1,eventDescription,getDate(1,"MM/dd/yyyy 14:00"),getDate(1,"MM/dd/yyyy 15:00"),false,calendarDefault);
		evt.addQuickEvent(eventName5,eventDescription,getDate(-1,"MM/dd/yyyy 06:00"),getDate(-1,"MM/dd/yyyy 07:00"),false,calendarDefault);
		
		info("Add one past and one future event in the perso cal");
		evt.addQuickEvent(eventName2,eventDescription,getDate(1,"MM/dd/yyyy 16:00"),getDate(1,"MM/dd/yyyy 17:00"),false,calendarNamePerso);
		evt.addQuickEvent(eventName6,eventDescription,getDate(-1,"MM/dd/yyyy 05:00"),getDate(-1,"MM/dd/yyyy 06:00"),false,calendarNamePerso);
		
		info("Add one past and one future event in the shared cal");
		evt.addQuickEvent(eventName3,eventDescription,getDate(1,"MM/dd/yyyy 16:00"),getDate(1,"MM/dd/yyyy 17:00"),false,calendarNameShare);
		evt.addQuickEvent(eventName7,eventDescription,getDate(-1,"MM/dd/yyyy 10:00"),getDate(-1,"MM/dd/yyyy 11:00"),false,calendarNameShare);
		
		info("Add one past and one future event in the engineering cal");
		evt.addQuickEvent(eventName4,eventDescription,getDate(1,"MM/dd/yyyy 18:00"),getDate(1,"MM/dd/yyyy 19:00"),false,spaceName);
		evt.addQuickEvent(eventName8,eventDescription,getDate(-1,"MM/dd/yyyy 12:00"),getDate(-1,"MM/dd/yyyy 13:00"),false,spaceName);
		
		/*
		 * Step 1 : search event
		 */
		qsPage.quickSearch("event");
		qsPage.searchInSearchPage(eventName1);
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", eventName1));
		qsPage.searchInSearchPage(eventName2);
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", eventName2));
		qsPage.searchInSearchPage(eventName3);
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", eventName3));
		qsPage.searchInSearchPage(eventName4);
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", eventName4));
		qsPage.searchInSearchPage(eventName5);
		waitForElementNotPresent(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", eventName5));
		qsPage.searchInSearchPage(eventName6);
		waitForElementNotPresent(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", eventName6));
		qsPage.searchInSearchPage(eventName7);
		waitForElementNotPresent(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", eventName7));
		qsPage.searchInSearchPage(eventName8);
		waitForElementNotPresent(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", eventName8));
		
		/*
		 *  delete data
		 */
		goToCalendarPage();
		evt.deleteEventTask(eventName1);
		evt.deleteEventTask(eventName5);

		deleteCalendar(calendarNamePerso);
		deleteCalendar(calendarNameShare);
		
		
		magMember.goToMySpacePage();
		magMember.deleteSpace(spaceName);
		
	}
	
}
