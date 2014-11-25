package org.exoplatform.selenium.platform.wiki.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.wiki.Version;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * @author lientm
 * @date: 1-July-2013
 */
public class Wiki_Information extends Version {

	ManageAccount magAc;
	Button but;
	ManageMember magMem;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		but = new Button(driver);
		magMem = new ManageMember(driver);
		magAc.signIn(DATA_USER1, DATA_PASS); 
		goToWiki();
	}

	@AfterMethod
	public void afterTest(){
		//magAc.signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**
	 * CaseId: 109192 -> View Page General information
	 */
	@Test
	public void test01_ViewPageGeneralInformation(){
		String title = "Wiki_sniff_infor_page_title_01";
		String content = "Wiki_sniff_infor_page_content_01";
		String link = "Wiki_Sniff_Attachment_01.doc";
		String newTitle = "Wiki_sniff_infor_page_title_01_update";
		String newContent = "Wiki_sniff_infor_page_content_01_update";
		info("CaseId: 109192 -View Page General information");
		addBlankWikiPageHasAttachment(title, content, link);
		editWikiPage(newTitle, newContent, 0);

		info("View Information of page");
		waitForAndGetElement(ELEMENT_CREATOR_PAGE_INFO.replace("${fullName}", "John Smith"));
		waitForAndGetElement(ELEMENT_UPDATER_PAGE_INFO.replace("${fullName}", "John Smith"));
		waitForAndGetElement(ELEMENT_VIEW_CHANGE);
		waitForAndGetElement(ELEMENT_ATTACHMENT_NUMBER.replace("${No}", "1"));
		waitForAndGetElement(ELEMENT_VERSION_LINK.replace("{$version}", "2"));

		deleteCurrentWikiPage();
	}
	
	/**
	 * CaseId: 109193 -> View Page history to compare versions
	 */
	@Test
	public void test02_ViewPageHistoryToCompareVersions(){
		String title = "Wiki_sniff_infor_page_title_02";
		String content = "Wiki_sniff_infor_page_content_02";
		String newTitle = "Wiki_sniff_infor_page_title_02_update";
		String newContent = "Wiki_sniff_infor_page_content_02_update";

		addBlankWikiPage(title, content, 0);
		editWikiPage(newTitle, newContent, 0);
		goToPageInfoFromCurrentPage();
		viewPageHistory();

		info("Compare 2 version of page");
		compareVersion("1", "2");
		if (waitForAndGetElement(ELEMENT_LINE_REMOVE.replace("${lineRemove}", content), 3000, 0) == null){
			waitForAndGetElement(ELEMENT_LINE_REMOVE_AUX.replace("${lineRemove}", content));	
		}
		waitForAndGetElement(ELEMENT_LINE_ADD.replace("${lineAdd}", content+newContent));

		click(ELEMENT_NODE_WIKI_PAGE.replace("{$node}",newTitle));
		waitForMessage(newContent);
		deleteCurrentWikiPage();
	}
	
	/**
	 * CaseId: 70337 -> Add Relation between 2 pages different space
	 */
	@Test
	public void test03_AddRelationDifferentSpace(){
		String spaceName1 = "relationspace031";
		String title1 = "Wiki_sniff_relation_title_03_1";
		String content1 = "Wiki_sniff_relation_content_03_1";

		String spaceName2 = "relationspace032";
		String title2 = "Wiki_sniff_relation_title_03_2";
		String content2 = "Wiki_sniff_relation_content_03_2";

		magMem.goToAllSpaces();
		magMem.addNewSpace(spaceName1, "", "Visible", "Validation", "", "");
		goToWikiFromSpace(spaceName1);
		addBlankWikiPage(title1, content1, 0);

		magMem.goToAllSpaces();
		magMem.addNewSpace(spaceName2, "", "Visible", "Validation", "", "");
		goToWikiFromSpace(spaceName2);
		addBlankWikiPage(title2, content2, 0);

		info("Add relation for page2 of space2 to page1 of space1");
		addRelatedPage("Wiki Home/" + title2, title1, spaceName1);

		magMem.goToAllSpaces();
		magMem.deleteSpace(spaceName1, 180000);
		magMem.deleteSpace(spaceName2, 180000);
	}
	
	/**
	 * CaseId: 70340 -> Add Relation with intranet portal
	 */
	@Test
	public void test04_AddRelationWithIntranetPortal(){
		String title1 = "Wiki_relation_title_04_1";
		String content1 = "Wiki_relation_content_04_1";

		String spaceName = "relationspace04";
		String title2 = "Wiki_relation_title_04_2";
		String content2 = "Wiki_relation_content_04_2";

		addBlankWikiPage(title1, content1, 0);

		magMem.goToAllSpaces();
		magMem.addNewSpace(spaceName, "", "Visible", "Validation", "", "");
		goToWikiFromSpace(spaceName);
		addBlankWikiPage(title2, content2, 0);

		info("Add relation for page2 of space2 to page1 of space1");
		addRelatedPage("Wiki Home/" + title2, title1, "Intranet");

		magMem.goToAllSpaces();
		magMem.deleteSpace(spaceName, 180000);
		goToWikiPage("Wiki Home/" + title1);
		deleteCurrentWikiPage();
	}

	/**
	 * CaseId: 70341 -> Add Relation same space
	 */
	@Test
	public void test05_AddRelationSameSpace(){
		String spaceName = "relationspace05";
		String title1 = "Wiki_relation_title_05_1";
		String content1 = "Wiki_relation_content_05_1";		
		String title2 = "Wiki_relation_title_05_2";
		String content2 = "Wiki_relation_content_05_2";

		magMem.goToAllSpaces();
		magMem.addNewSpace(spaceName, "", "Visible", "Validation", "", "");
		goToWikiFromSpace(spaceName);
		addBlankWikiPage(title1, content1, 0);
		goToWikiHome();
		addBlankWikiPage(title2, content2, 0);

		info("Move page2 to page1 in same space");
		addRelatedPage("Wiki Home/" + title2, title1);

		magMem.goToAllSpaces();
		magMem.deleteSpace(spaceName, 180000);
	}
	
	/**
	 * CaseId: 70342 -> Add relation in the case there is no space
	 */
	@Test
	public void test06_AddRelation_NoSpace(){
		String title = "Wiki_sniff_infor_page_title_06";
		String content = "Wiki_sniff_infor_page_content_06";

		magAc.signOut();
		magAc.signIn("fqa", "gtngtn");
		goToWiki();
		addBlankWikiPage(title, content, 0);
		goToAddRelation();
		click(ELEMENT_SELECT_SPACE);
		waitForAndGetElement(ELEMENT_NO_SPACE_OPTION);
		but.cancel();

		click(ELEMENT_NODE_WIKI_PAGE.replace("{$node}", title));
		waitForMessage(content);
		deleteCurrentWikiPage();
	}
	
	/**
	 * CaseId: 70344 -> Delete relation
	 */
	@Test
	public void test07_DeleteRelation(){
		String title1 = "Wiki_sniff_infor_page_title_07_1";
		String content1 = "Wiki_sniff_infor_page_content_07_1";
		String title2 = "Wiki_sniff_infor_page_title_07_2";
		String content2 = "Wiki_sniff_infor_page_content_07_2";

		addBlankWikiPage(title1, content1, 0);
		goToWikiHome();

		addBlankWikiPage(title2, content2, 0);
		addRelatedPage("Wiki Home/" + title1, title2);

		removeRelatedPage(true, true, "", title2);

		click(By.linkText(title1));
		waitForMessage(content1);
		deleteCurrentWikiPage();
		click(By.linkText(title2));
		waitForMessage(content2);
		deleteCurrentWikiPage();
	}
	
	/**
	 * CaseId: 109191 -> View Page info
	 */
	@Test
	public void test08_ViewPageInfo(){
		String title = "Wiki_sniff_infor_page_title_08";
		String content = "Wiki_sniff_infor_page_content_08";

		String child1Title = "Wiki_sniff_infor_page_title_08_child1";
		String child2Title = "Wiki_sniff_infor_page_title_08_child2";

		info("Add wiki page");
		addBlankWikiPage(title, content, 0);

		info("Add 2 wiki child page");
		addBlankWikiPage(child1Title, "", 0);
		goToWikiPage("Wiki Home/" + title);
		addBlankWikiPage(child2Title, "", 0);
		goToWikiPage("Wiki Home/" + title);

		info("Open Page Information");
		goToPageInfoFromCurrentPage();

		info("Verify page information");
		waitForAndGetElement(By.xpath(ELEMENT_PAGE_INFO_TITLE.replace("${infoTitle}", "Summary")));
		waitForAndGetElement(By.xpath(ELEMENT_PAGE_INFO_TITLE.replace("${infoTitle}", "Related Pages")));
		waitForAndGetElement(By.xpath(ELEMENT_PAGE_INFO_TITLE.replace("${infoTitle}", "Hierarchy")));
		waitForAndGetElement(By.xpath(ELEMENT_PAGE_INFO_TITLE.replace("${infoTitle}", "Recent Changes")));
		waitForAndGetElement(By.xpath("//*[contains(text(),'View Page History')]"));
		waitForAndGetElement(ELEMENT_ADD_MORE_RELATION_BUTTON);

		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_INFO_SUMMARY.replace("${infoSummary}", "Title").replace("${item}", title)));
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_INFO_SUMMARY.replace("${infoSummary}", "Author").replace("${item}", "John Smith")));
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_INFO_SUMMARY.replace("${infoSummary}", "Last changed by").replace("${item}", "John Smith")));

		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_HIERARCHY.replace("${page}", "Parent Page").replace("${pageTitle}", "WikiHome")));
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_HIERARCHY.replace("${page}", "Child Pages").replace("${pageTitle}", child1Title)));
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_HIERARCHY.replace("${page}", "Child Pages").replace("${pageTitle}", child2Title)));

		click(By.linkText(title));
		waitForMessage(content);
		deleteCurrentWikiPage();
	}
	
	/**Version Creation
	 * CaseId: 109771
	 * update follow issue https://jira.exoplatform.org/browse/FQA-1772
	 */
	@Test
	public void test09_VersionCreation(){
		String title1 = "Wiki_page_109771";
		String content1 = "Content page 109771";
		String title2 = "Wiki_page_109771_update";
		String content2 = "Content page 109771 update";
		String link = "Wiki_Sniff_Attachment_01.jpg";
		String title3 = "Wiki_page_109771_update_2";

		info("Create new wiki page -> it has verion 1");
		addBlankWikiPage(title1, content1, 0);
		waitForAndGetElement(ELEMENT_VERSION_LINK.replace("{$version}", "1"));
		
		info("Edit title of page by click edit -> it has version 2");
		editWikiPage(title2, null, 0);
		waitForAndGetElement(ELEMENT_VERSION_LINK.replace("{$version}", "2"));
		
		info("Edit content of page by click edit -> it has verstion 3");
		editWikiPage(null, content2, 0);
		waitForAndGetElement(ELEMENT_VERSION_LINK.replace("{$version}", "3"));
		
		info("Add new attachment -> page's version is not changed");
		click(ELEMENT_ATTACHMENT_ICON);
		attachFileInWiki("TestData" + java.io.File.separator + link, 2);
		waitForAndGetElement(ELEMENT_ATTACHMENT_NUMBER.replace("${No}", "1"));
		waitForAndGetElement(ELEMENT_VERSION_LINK.replace("{$version}", "3"));
		
		info("Delete acttachment -> page's version is not changed");
		clickByJavascript(ELEMENT_NODE_WIKI_PAGE.replace("{$node}", title2));
		deleteAnAttachment(link);		
		waitForAndGetElement(ELEMENT_ATTACHMENT_NUMBER.replace("${No}", "0"));
		waitForAndGetElement(ELEMENT_VERSION_LINK.replace("{$version}", "3"));
		
		info("Edit title by double click -> page's version is not changed");
		editWikiPageTitleByClickTitle(title3);
		waitForAndGetElement(ELEMENT_VERSION_LINK.replace("{$version}", "3"));
		
		info("Delete data");
		deleteCurrentWikiPage();
	}
}