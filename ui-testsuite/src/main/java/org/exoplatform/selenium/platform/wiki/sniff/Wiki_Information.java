package org.exoplatform.selenium.platform.wiki.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.objectdatabase.common.AttachmentFileDatabase;
import org.exoplatform.selenium.platform.objectdatabase.common.TextBoxDatabase;
import org.exoplatform.selenium.platform.objectdatabase.space.SpaceRegistrationDatabase;
import org.exoplatform.selenium.platform.objectdatabase.space.SpaceVisibilityDatabase;
import org.exoplatform.selenium.platform.objectdatabase.wiki.WikiTemplateDatabase;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.wiki.Version;
import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author lientm
 * @date: 1-July-2013
 */
public class Wiki_Information extends Version {

	ManageAccount magAc;
	Button but;
	ManageMember magMem;
	TextBoxDatabase txData;
	WikiTemplateDatabase wTempData;
	AttachmentFileDatabase fData;
	SpaceVisibilityDatabase sVisData;
	SpaceRegistrationDatabase sRegData;
	@BeforeTest
	public void setBeforeTest() throws Exception{
		initSeleniumTest();
		getDefaultUserPass(userDataFilePath,defaultSheet,false,jdbcDriver,dbUrl,user,pass,sqlUser);
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		but = new Button(driver);
		magMem = new ManageMember(driver);
		magAc.signIn(DATA_USER1, DATA_PASS);
		txData = new TextBoxDatabase();
		wTempData = new WikiTemplateDatabase();
		sVisData = new SpaceVisibilityDatabase();
		sRegData = new SpaceRegistrationDatabase();
		fData = new AttachmentFileDatabase();
		txData.setContentData(texboxFilePath,defaultSheet,isUseFile,jdbcDriver,dbUrl,user,pass,sqlAttach);
		wTempData.setWikiTemplateData(wikiTemplateFilePath,defaultSheet,isUseFile,jdbcDriver,dbUrl,user,pass,sqlAttach);	
		fData.setAttachFileData(attachmentFilePath,defaultSheet,isUseFile,jdbcDriver,dbUrl,user,pass,sqlAttach);
		sVisData.setSpaceVisibleData(spaceVisibleFilePath,defaultSheet,isUseFile,jdbcDriver,dbUrl,user,pass,sqlAttach);
		sRegData.setSpaceRegistrationData(spaceRegistrationFilePath,defaultSheet,isUseFile,jdbcDriver,dbUrl,user,pass,sqlAttach);
	}
	@AfterTest
	public void setAfterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	@BeforeMethod
	public void setBeforeMethod(){
		goToWiki();
	}

	/**
	 * CaseId: 109192 -> View Page General information
	 */
	@Test
	public void test01_ViewPageGeneralInformation(){
		String title = txData.getContentByTypeRandom(1);
		String content =  txData.getContentByTypeRandom(1);
		String newTitle = "newtitle"+txData.getContentByTypeRandom(1);
		String newContent = "newcontent"+txData.getContentByTypeRandom(1);
		String link = fData.getAttachFileByTypeRandom(1);

		info(link);
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
		String title = txData.getContentByTypeRandom(1);
		String content =  txData.getContentByTypeRandom(1);
		String newTitle = "newtitle"+txData.getContentByTypeRandom(1);
		String newContent = "newcontent"+txData.getContentByTypeRandom(1);

		addBlankWikiPage(title, content, 0);
		editWikiPage(newTitle, newContent, 0);

		goToPageInfoFromCurrentPage();
		viewPageHistory();

		info("Compare 2 version of page");
		compareVersion("1", "2");
		if (waitForAndGetElement(ELEMENT_LINE_REMOVE.replace("${lineRemove}", content), 3000, 0) == null){
			waitForAndGetElement(ELEMENT_LINE_REMOVE_AUX.replace("${lineRemove}", content));	
		}
		waitForAndGetElement(ELEMENT_LINE_ADD.replace("${lineAdd}", newContent));

		click(By.linkText(newTitle));
		deleteCurrentWikiPage();
	}

	/**
	 * CaseId: 70337 -> Add Relation between 2 pages different space
	 */
	@Test
	public void test03_AddRelationDifferentSpace(){
		String spaceName1 = txData.getContentByTypeRandom(1)+"1";
		String title1 = txData.getContentByTypeRandom(1)+"1";
		String content1 =  txData.getContentByTypeRandom(1)+"1";


		String spaceName2 = txData.getContentByTypeRandom(1)+"2";
		String title2 = "newtitle"+txData.getContentByTypeRandom(1)+"2";
		String content2 = "newcontent"+txData.getContentByTypeRandom(1)+"2";

		String visible = sVisData.getSpaceVisible(0);
		String validate = sRegData.getSpaceRegistration(1);

		magMem.goToAllSpaces();
		magMem.addNewSpace(spaceName1, "", visible, validate, "", "");
		goToWikiFromSpace(spaceName1);
		addBlankWikiPage(title1, content1, 0);

		magMem.goToAllSpaces();
		magMem.addNewSpace(spaceName2, "", visible, validate, "", "");
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
		String spaceName = txData.getContentByTypeRandom(1)+"1";
		String title1 = txData.getContentByTypeRandom(1)+"1";
		String content1 =  txData.getContentByTypeRandom(1)+"1";
		String title2 = "newtitle"+txData.getContentByTypeRandom(1)+"2";
		String content2 = "newcontent"+txData.getContentByTypeRandom(1)+"2";

		String visible = sVisData.getSpaceVisible(0);
		String validate = sRegData.getSpaceRegistration(1);

		addBlankWikiPage(title1, content1, 0);

		magMem.goToAllSpaces();
		magMem.addNewSpace(spaceName, "", visible, validate, "", "");
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
		String spaceName = txData.getContentByTypeRandom(1)+"1";
		String title1 = txData.getContentByTypeRandom(1)+"1";
		String content1 =  txData.getContentByTypeRandom(1)+"1";
		String title2 = "newtitle"+txData.getContentByTypeRandom(1)+"2";
		String content2 = "newcontent"+txData.getContentByTypeRandom(1)+"2";

		String visible = sVisData.getSpaceVisible(0);
		String validate = sRegData.getSpaceRegistration(1);

		magMem.goToAllSpaces();
		magMem.addNewSpace(spaceName, "", visible, validate, "", "");
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
		String title = txData.getContentByTypeRandom(1);
		String content =  txData.getContentByTypeRandom(1);

		magAc.signOut();
		magAc.signIn(DATA_USER2, DATA_PASS);
		goToWiki();
		addBlankWikiPage(title, content, 0);
		goToAddRelation();
		click(ELEMENT_SELECT_SPACE);
		waitForAndGetElement(ELEMENT_NO_SPACE_OPTION);
		but.cancel();

		click(By.linkText(title));
		deleteCurrentWikiPage();
	}

	/**
	 * CaseId: 70344 -> Delete relation
	 */
	@Test
	public void test07_DeleteRelation(){
		String title1 = txData.getContentByTypeRandom(1)+"1";
		String content1 =  txData.getContentByTypeRandom(1)+"1";
		String title2 = "newtitle"+txData.getContentByTypeRandom(1)+"2";
		String content2 = "newcontent"+txData.getContentByTypeRandom(1)+"2";

		addBlankWikiPage(title1, content1, 0);
		goToWikiHome();

		addBlankWikiPage(title2, content2, 0);
		addRelatedPage("Wiki Home/" + title1, title2);

		removeRelatedPage(true, true, "", title2);

		click(By.linkText(title1));
		deleteCurrentWikiPage();
		click(By.linkText(title2));
		deleteCurrentWikiPage();
	}

	/**
	 * CaseId: 109191 -> View Page info
	 */
	@Test
	public void test08_ViewPageInfo(){
		String title = txData.getContentByTypeRandom(1);
		String content =  txData.getContentByTypeRandom(1);

		String child1Title = "child"+title;
		String child2Title = "child"+content;

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
		deleteCurrentWikiPage();
	}

	/**Version Creation
	 * CaseId: 109771
	 * update follow issue https://jira.exoplatform.org/browse/FQA-1772
	 */
	@Test
	public void test09_VersionCreation(){
		String title1 = txData.getContentByTypeRandom(1)+"1";
		String content1 =  txData.getContentByTypeRandom(1)+"1";
		String title2 = "newtitle"+txData.getContentByTypeRandom(1)+"2";
		String content2 = "newcontent"+txData.getContentByTypeRandom(1)+"2";

		String link = fData.getAttachFileByTypeRandom(1);
		String title3 = txData.getContentByTypeRandom(1)+"3";

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
		attachFileInWiki("TestData/" + link, 2);
		waitForAndGetElement(ELEMENT_ATTACHMENT_NUMBER.replace("${No}", "1"));
		waitForAndGetElement(ELEMENT_VERSION_LINK.replace("{$version}", "3"));

		info("Delete acttachment -> page's version is not changed");
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