package org.exoplatform.selenium.platform.plf.functional.unifiedsearch;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.HomePageGadget;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.SettingSearchPage;
import org.exoplatform.selenium.platform.calendar.CalendarBase;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.social.SpaceManagement;
import org.exoplatform.selenium.platform.wiki.BasicAction;
import org.testng.annotations.*;
import org.exoplatform.selenium.Utils;

public class PLF_UnifiedSearch_WikiSearch extends CalendarBase{
	

	ManageAccount magAcc;
	ManageMember magMember;
	SettingSearchPage qsPage;
	SpaceManagement spaceMag;
	HomePageGadget hGadget;
	BasicAction ba;
	NavigationToolbar navTool ;
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver, this.plfVersion);
		magAcc.signIn(DATA_USER1, DATA_PASS);
		hGadget = new HomePageGadget(driver);
		magMember = new ManageMember(driver,this.plfVersion);
		qsPage = new SettingSearchPage(driver);
		spaceMag = new SpaceManagement(driver);
		button = new Button(driver);
		ba  = new BasicAction(driver);
		navTool=new NavigationToolbar(driver);
	}
	
	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();		
		driver.quit();
	}
	
	
	/*
	 * ==look for wiki in spaces were user has access to==
	 * Test ID : 104371
	 * Step 1 : search for Cloud
	 * Step 2 : set view permission to the page in marketing, search for "cloud"
	 */
	@Test
	public void test01_LookForWikiInSpacesWereUserHasAccessTo(){
		String texteWiki="Qu est ce que le Cloud ? Demandons a steve job et Amazon";
		String spaceName1="Engineering104371";
		String spaceName2="Marketing104371";
		String wikiMary="Wiki page of mary";
		String wikiEngi="Wiki in engi";
		String wikiIntra="Intranet Wiki";
		String wikiJame="wiki page of james";
		String wikiMarket="wiki marketing page";
		String[] userGroupToAddPermission={"james"};
		/*
		 * pre conditions
		 */

		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName1, "");
		info("New space added : "+spaceName1);
		
		info("Invit user James in space "+spaceName1);
		click(magMember.ELEMENT_SPACE_SETTING_PORTLET);
		click(magMember.ELEMENT_MEMBER_TAB);
		magMember.inviteSingleUser("James");
		info("Add wiki page in engineering space ");
		magMember.goToSpaceMenu("Wiki");
		ba.addBlankWikiPage(wikiEngi, texteWiki, 0);
		
		
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName2, "");
		info("New space added : "+spaceName2);
		info("Add wiki page in marketing space ");
		magMember.goToSpaceMenu("Wiki");
		ba.addBlankWikiPage(wikiMarket, texteWiki, 0);
		
		info("John add a page in intranet");
		ba.goToWiki();
		ba.addBlankWikiPage(wikiIntra, texteWiki, 0);
		
		info("Disconnect user John");
		magAcc.signOut();
		Utils.pause(800);
			
		info("Connect with mary ");
		magAcc.signIn(DATA_USER2, DATA_PASS);
		
		ba.goToWiki();
		click(ba.ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ba.ELEMENT_SPACE_SWITCHER_AVATAR_MYWIKI);
		info("Add a personal page by mary williams and give permission to James");
		ba.addBlankWikiPage(wikiMary, texteWiki, 0);
		ba.addSpacePermission(1, userGroupToAddPermission);
		
//		info("Disconnect Mary");
		magAcc.signOut();
//		Utils.pause(800);
		
		info("Connect with James ");
		magAcc.signIn(DATA_USER3, DATA_PASS);
		
		ba.goToWiki();
		click(ba.ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ba.ELEMENT_SPACE_SWITCHER_AVATAR_MYWIKI);
		info("James add a personnal page in wiki");
		ba.addBlankWikiPage(wikiJame, texteWiki, 0);
		
		navTool.goToHomePage();
		info("Accept invitation");
		hGadget.acceptSpaceInvitationGadget(spaceName1);
		driver.navigate().refresh();
		
		/*
		 * Step 1 : Search for cloud
		 */
		qsPage.quickSearch("Cloud");
		qsPage.searchInSearchPage("Cloud");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", wikiJame));
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", wikiMary));
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", wikiEngi));
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", wikiIntra));
		waitForElementNotPresent(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", wikiMarket));
		
		info("Disconnect James");
		magAcc.signOut();
		Utils.pause(800);
		
		/*
		 * set view permission to the wiki page in marketing, search for "cloud"
		 */
		info("Connect with john ");
		magAcc.signIn(DATA_USER1, DATA_PASS);
		
		magMember.goToAllSpaces();
		magMember.accessSpace(spaceName2);
		magMember.goToSpaceMenu("Wiki");
		ba.addSpacePermission(1, userGroupToAddPermission);
		
		info("Disconnect John");
		magAcc.signOut();
		Utils.pause(800);
		
		info("Connect with James ");
		magAcc.signIn(DATA_USER3, DATA_PASS);
		qsPage.quickSearch("Cloud");
		qsPage.searchInSearchPage("Cloud");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", wikiMarket));
		
		/*
		 *  delete data
		 */
		ba.goToWiki();
		click(ba.ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ba.ELEMENT_SPACE_SWITCHER_AVATAR_MYWIKI);
		String[] deleteWikiPage2 = {"Wiki Home/wiki page of james"};
		ba.deleteWikiPage(deleteWikiPage2);
		info("Wiki page of James deleted");
		
		info("Disconnect James");
		magAcc.signOut();
		Utils.pause(800);
		
		info("Connect with John ");
		magAcc.signIn(DATA_USER1, DATA_PASS);
		magMember.goToMySpacePage();
		magMember.deleteSpace(spaceName1);
		magMember.deleteSpace(spaceName2);
		info("Delete space engineering ans space marketing");
		ba.goToWiki();
		String[] deleteWikiPage = {"Wiki Home/Intranet Wiki"};
		ba.deleteWikiPage(deleteWikiPage);
		info("intranet Wiki page deleted");
		info("Disconnect John");
		magAcc.signOut();
		Utils.pause(800);
		
		info("Connect with Mary ");
		magAcc.signIn(DATA_USER2, DATA_PASS);
		ba.goToWiki();
		click(ba.ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ba.ELEMENT_SPACE_SWITCHER_AVATAR_MYWIKI);
		ba.deleteSpacePermission("james");
		ba.goToWiki();
		click(ba.ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ba.ELEMENT_SPACE_SWITCHER_AVATAR_MYWIKI);
		String[] deleteWikiPage1 = {"Wiki Home/Wiki page of mary"};
		ba.deleteWikiPage(deleteWikiPage1);
		info("Wiki page of Mary deleted");

	}
	
	/*
	 * ==user must have permission to view wiki page results==
	 * Test ID : 104373
	 * Step 1 : search for "WIKI_SEARCH_05"
	 */
	@Test
	public void test02_UserMustHavePermissionToViewWikiPageResults(){
		String searchWord="WIKI_SEARCH_05";
		String wikiPageVisible="WIKI_SEARCH_05 OK";
		String wikiPageNotVisible="WIKI_SEARCH_05 KO";
		String[] userGroupToAddPermission={"james"};
		String texteWiki="Ceci est mon premier wiki";
		
		/*
		 * Pre conditions
		 */
		ba.goToWiki();
		click(ba.ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ba.ELEMENT_SPACE_SWITCHER_AVATAR_MYWIKI);
		info("Add a personal page john : WIKI_SEARCH_05 KO");
		ba.addBlankWikiPage(wikiPageNotVisible, texteWiki, 0);
		info("Disconnect John");
		magAcc.signOut();
		Utils.pause(800);
		
		info("Connect with Mary ");
		magAcc.signIn(DATA_USER2, DATA_PASS);
		ba.goToWiki();
		click(ba.ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ba.ELEMENT_SPACE_SWITCHER_AVATAR_MYWIKI);
		info("Add a personal page by mary williams and give permission to James. page : WIKI_SEARCH_05 OK");
		ba.addBlankWikiPage(wikiPageVisible, texteWiki, 0);
		ba.addSpacePermission(1, userGroupToAddPermission);
		info("Disconnect Mary");
		magAcc.signOut();
		Utils.pause(800);
		
		/*
		 * Step 1 : Search for WIKI_SEARCH_05
		 */
		info("Connect with James ");
		magAcc.signIn(DATA_USER3, DATA_PASS);
		qsPage.quickSearch(searchWord);
		qsPage.searchInSearchPage(searchWord);
		info("Search result for : WIKI_SEARCH_05 ");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", wikiPageVisible));
		waitForElementNotPresent(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", wikiPageNotVisible));
		info("Disconnect james");
		magAcc.signOut();
		Utils.pause(800);
		
		/*
		 * Delete data
		 */
		info("Connect with mary ");
		magAcc.signIn(DATA_USER2, DATA_PASS);
		ba.goToWiki();
		click(ba.ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ba.ELEMENT_SPACE_SWITCHER_AVATAR_MYWIKI);
		ba.deleteSpacePermission("james");
		ba.goToWiki();
		click(ba.ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ba.ELEMENT_SPACE_SWITCHER_AVATAR_MYWIKI);
		String[] deleteWikiPage1 = {"Wiki Home/WIKI_SEARCH_05 OK"};
		ba.deleteWikiPage(deleteWikiPage1);
		info("Wiki page of Mary deleted");
		info("Disconnect Mary");
		magAcc.signOut();
		Utils.pause(800);
		
		info("Connect with John ");
		magAcc.signIn(DATA_USER1, DATA_PASS);
		ba.goToWiki();
		click(ba.ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ba.ELEMENT_SPACE_SWITCHER_AVATAR_MYWIKI);
		String[] deleteWikiPage2 = {"Wiki Home/WIKI_SEARCH_05 KO"};
		ba.deleteWikiPage(deleteWikiPage2);
		info("Wiki page of john deleted");
	}
	
	
	/*
	 * ==wiki quick search results display==
	 * Test ID : 104369
	 * Step 1 : in quick search field type 3.5
	 */
	@Test
	public void test03_WikiQuickSearchResultsDisplay(){
		String titleWikiPage="Gatein 3.5 Release Notes";
		String contentWikiPage="Gatein 3.5 has been released on jboss.org - New features are awesome";
		/*
		 *Pre condition 
		 */
		ba.goToWiki();
		click(ba.ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ba.ELEMENT_SPACE_SWITCHER_AVATAR_MYWIKI);
		info("Add a personal page john : Gatein 3.5 Release Notes");
		ba.addBlankWikiPage(titleWikiPage, contentWikiPage, 0);
		
		/*
		 * Step 1 : Search for 3.5
		 */
		click(ELEMENT_QUICK_SEARCH_ICON);
		Utils.pause(2000);
		type(ELEMENT_QUICK_SEARCH_TEXTBOX,"3",true);
		type(ELEMENT_QUICK_SEARCH_TEXTBOX,".5",false);
		waitForAndGetElement((qsPage.ELEMENT_QUICK_SEARCH_BOX_RESULTS.replace("{$result}", titleWikiPage).replace("{$position}", "1")));
		/*
		 * Delete data
		 */
		ba.goToWiki();
		click(ba.ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ba.ELEMENT_SPACE_SWITCHER_AVATAR_MYWIKI);
		String[] deleteWikiPage2 = {"Wiki Home/Gatein 3.5 Release Notes"};
		ba.deleteWikiPage(deleteWikiPage2);
		
	}
	
	/*
	 * ==wiki result opens with permalink==
	 * Test ID : 104374
	 * Step 1 : - search for "Gatein"
	 * 			- click on the result link
	 */
	@Test
	public void test04_WikiResutlOpensWithPermalink(){
		String spaceName="Engineering104374";
		String gatein="Gatein";
		String content="qu'est ce que le gatein ?";
		String url_result="/portal/intranet/wiki/group/spaces/engineering";

		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");
		info("New space added : "+spaceName);
		
		magMember.goToSpaceMenu("Wiki");
		ba.addBlankWikiPage(gatein, content, 0);
		info("New wiki page added in space ingineering");
		
		qsPage.quickSearch(gatein);
		qsPage.searchInSearchPage(gatein);
		info("Search result for : gatein ");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", gatein)).click();
		
		String url_wiki = driver.getCurrentUrl();
		
		assert url_wiki.contains(url_result);
		
		/*
		 * Delete data
		 */
		magMember.goToMySpacePage();
		magMember.deleteSpace(spaceName);
		info("Delete space name ");
		
	}
	
	/*
	 * ==wiki results display==
	 * Test ID : 104370
	 * Step 1 : - search for 3.5
	 */
	@Test
	public void test05_WikiResultDisplay(){
		String wikiTitle="Gatein 3.5 Release Notes";
		String wikiContent="Gatein 3.5 has been released on jboss.org - New features are awesome";
		String spaceName="Engineering104370";
		
		String dateCreation=null;
		String moisCreation=null;
		
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");
		info("New space added : "+spaceName);
		
		magMember.goToSpaceMenu("Wiki");
		ba.addBlankWikiPage(wikiTitle, wikiContent, 0);
		info("New wiki page added in space ingineering");
		moisCreation=getDate(0,"MMM");
		dateCreation=getDate(0,"d, yyyy h:");

		
		qsPage.quickSearch("3");
		qsPage.searchInSearchPage("3");
		info("Search result for : gatein ");
		// Check the title
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", wikiTitle));
		String contentDetail = waitForAndGetElement(ELEMENT_RESULT_CONTENT_DETAIL.replace("${text}", wikiTitle)).getText();
		// check space name
		assert contentDetail.contains(spaceName);
		// check date creation
		assert contentDetail.contains(moisCreation);
		assert contentDetail.contains(dateCreation);
		String contentMain= waitForAndGetElement(ELEMENT_RESULT_SEARCH_PAGE).getText();
		assert contentMain.contains(wikiContent);
		waitForAndGetElement(qsPage.ELEMENT_SEARCH_RESULT_EMPHASIZE.replace("${text}", "3"));
		
		/*
		 * Delete data
		 */
		magMember.goToMySpacePage();
		magMember.deleteSpace(spaceName);
		info("Delete space name ");
	}
	
}