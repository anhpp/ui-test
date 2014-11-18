package org.exoplatform.selenium.platform.wiki.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import java.awt.event.KeyEvent;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.HomePage;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.wiki.AddEditPageManagement;
import org.exoplatform.selenium.platform.wiki.ManageDraft;
import org.exoplatform.selenium.platform.wiki.RichTextMode;
import org.exoplatform.selenium.platform.wiki.WikiHome;
import org.exoplatform.selenium.testdata.TextBoxDatabase;
import org.exoplatform.selenium.testdata.WikiRichTextDatabase;
import org.exoplatform.selenium.testdata.WikiTemplateDatabase;
import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @date 2-July-2013
 * @author lientm
 */

public class Wiki_BasicAction_ManagePage extends PlatformBase{

	AddEditPageManagement apManagement;
	WikiHome wHome;
	HomePage hp;
	ManageAccount magAc;
	Button but;
	NavigationToolbar naTool;
	HomePageActivity activity;
	WikiRichTextDatabase wData;
	RichTextMode rtMode;
	ManageDraft mDraft;
	TextBoxDatabase txData;
	WikiTemplateDatabase wTempData;
	WikiRichTextDatabase wRichTextData;
	@BeforeMethod
	public void setUpBeforeMethod() throws Exception{
		hp.goToWiki();
	}

	@BeforeTest
	public void setUpBeforeTest() throws Exception{
		initSeleniumTest();
		getDefaultUserPass();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		but = new Button(driver);
		naTool = new NavigationToolbar(driver);
		activity = new HomePageActivity(driver);
		hp = new HomePage(driver);
		wHome = new WikiHome(driver);
		button = new Button(driver, this.plfVersion);
		rtMode = new RichTextMode(driver);
		apManagement = new AddEditPageManagement(driver);
		mDraft = new ManageDraft(driver);
		magAc.signIn(DATA_USER1, DATA_PASS);
		txData = new TextBoxDatabase();
		wTempData = new WikiTemplateDatabase();
		wRichTextData = new WikiRichTextDatabase();
		txData.setContentData(texboxFilePath,defaultSheet,isUseFile,jdbcDriver,dbUrl,user,pass,sqlAttach);
		wTempData.setWikiTemplateData(wikiTemplateFilePath,defaultSheet,isUseFile,jdbcDriver,dbUrl,user,pass,sqlAttach);
		wRichTextData.setWikiData(wikiRichTextFilePath,defaultSheet,isUseFile,jdbcDriver,dbUrl,user,pass,sqlAttach);
	}

	@AfterTest
	public void afterTest(){
		magAc.signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**CaseID: 68836 + 70041 + 70042 -> Add, Edit and Delete page at Source Editor mode
	 * 
	 */
	@Test
	public void test01_CreatePageSourceEditor(){
		String title = txData.getContentByTypeRandom(1);
		String content =  txData.getContentByTypeRandom(1);
		String newTitle = "newtitle"+txData.getContentByTypeRandom(1);
		String newContent = "newcontent"+txData.getContentByTypeRandom(1);

		info("Add new wiki page at Source Editor");		
		wHome.goToAddBlankPage();
		apManagement.goToSourceEditor();
		apManagement.inputDataToPageSourceEditor(title,content,true,true);
		apManagement.saveAddPage();

		info("Edit wiki page at Source Editor");
		wHome.goToHomeWikiPage();
		wHome.goToAPage(title);
		wHome.goToEditPage();
		apManagement.inputDataToPageSourceEditor(newTitle,newContent,true,true);
		apManagement.saveAddPage();
		waitForAndGetElement(wHome.ELEMENT_WIKI_PAGE_LINK.replace("${pageTitle}", newTitle));

		wHome.goToHomeWikiPage();
		wHome.goToAPage(newTitle);
		wHome.goToDeletePage(newTitle);
		button.ok();
		waitForElementNotPresent(wHome.ELEMENT_WIKI_PAGE_LINK.replace("${pageTitle}", newTitle));
	}

	/**CaseId: 68837 + 70036 + 70037 -> Add, Edit and Delete page at Rich Text mode
	 * pending: add image (product has error)
	 */
	@Test(priority = 0)
	public void test02_CreatePageRichTextEditor(){
		String title1 = txData.getContentByTypeRandom(1)+"1";
		String content1 =  txData.getContentByTypeRandom(1)+"1";
		String title2 = txData.getContentByTypeRandom(1)+"2";
		String content2 =  txData.getContentByTypeRandom(1)+"2";
		String newTitle2 = "newtitle"+txData.getContentByTypeRandom(1)+"1";
		int index = wRichTextData.getRandomIndexByType(1);
		String color = wRichTextData.color.get(index);
		String message = wRichTextData.msg.get(index);
		String label = wRichTextData.label.get(index);
		String tooltip = wRichTextData.tooltip.get(index);
		String row = String.valueOf(wRichTextData.row.get(index));
		String column = String.valueOf(wRichTextData.column.get(index));

		info("Add new wiki page at Source Editor");		
		wHome.goToAddBlankPage();
		apManagement.goToSourceEditor();
		apManagement.inputDataToPageSourceEditor(title1,content1,true,true);
		apManagement.saveAddPage();

		info("Add new wiki page at Rich Text mode");
		wHome.goToHomeWikiPage();
		wHome.goToAddBlankPage();
		apManagement.goToRichTextEditor();
		apManagement.inputDataToPageRichText(title2,content2,true,true);
		rtMode.typeEnterInRichText();
		rtMode.createColorMacro(color, message);
		rtMode.typeEnterInRichText();
		rtMode.insertPageLink2WikiPage(true, title1, label, tooltip);
		rtMode.typeEnterInRichText();
		rtMode.insertTable2WikiPage(row, column);
		apManagement.saveAddPage();
		waitForAndGetElement(By.linkText(label));
		waitForAndGetElement(By.xpath(wHome.ELEMENT_MARCRO_COLOR.replace("${color}", color).replace("${message}", message)));

		info("Edit wiki page at Rich Text Editor");
		wHome.goToHomeWikiPage();
		wHome.goToAPage(title2);
		wHome.goToEditPage();
		apManagement.goToRichTextEditor();
		apManagement.inputDataToPageRichText(newTitle2,content2,true,true);
		apManagement.saveAddPage();
		waitForAndGetElement(wHome.ELEMENT_WIKI_PAGE_LINK.replace("${pageTitle}", newTitle2));

		info("Delete wiki pages");
		wHome.goToHomeWikiPage();
		wHome.goToAPage(title1);
		wHome.goToDeletePage(title1);
		button.ok();
		waitForElementNotPresent(wHome.ELEMENT_WIKI_PAGE_LINK.replace("${pageTitle}", title1));

		wHome.goToHomeWikiPage();
		wHome.goToAPage(newTitle2);
		wHome.goToDeletePage(newTitle2);
		button.ok();
		waitForElementNotPresent(wHome.ELEMENT_WIKI_PAGE_LINK.replace("${pageTitle}", newTitle2));
	}

	/**CaseId: 70058 + 70060 -> Auto save when adding page and delete draft
	 * 
	 */
	@Test
	public void test03_AutoSaveWhenAddingPage(){
		String title = txData.getContentByTypeRandom(1);
		String content =  txData.getContentByTypeRandom(1);

		info("Add new wiki page at Source Editor");		
		wHome.goToAddBlankPage();
		apManagement.goToSourceEditor();
		apManagement.inputDataToPageSourceEditor(title,content,true,true);
		Utils.pause(30000);
		assert isElementPresent(apManagement.ELEMENT_DRAFT_NOTIFY);

		mDraft.goToManageDraft();
		waitForAndGetElement(mDraft.ELEMENT_DRAFT_OF_NEW_PAGE.replace("${title}", title));

		mDraft.deleteDraft(title);
	}

	/**CaseId: 70258 + 70260 + 70259 -> Add, Edit and Delete page from template layout
	 * 
	 */
	@Test
	public void test04_AddPageFromTemplate(){
		String title = txData.getContentByTypeRandom(1);
		String newcontent =  txData.getContentByTypeRandom(1);
		String template =  wTempData.getWikiTemplateRandom();
				
		info("Add page with select a template layout");
		wHome.goToAddTemplateWikiPage();
		apManagement.selectTemplateWikiPage(template);
		click(button.ELEMENT_SELECT_BUTTON);
		apManagement.inputDataToPageSourceEditor(title,null,true,false);
		apManagement.saveAddPage();

		info("Edit content of page");
		wHome.goToAPage(title);
		wHome.goToEditPage();
		apManagement.inputDataToPageRichText(null,newcontent,false,true);
		apManagement.saveAddPage();
		waitForAndGetElement(wHome.ELEMENT_WIKI_PAGE_LINK.replace("${pageTitle}", title));

		wHome.goToHomeWikiPage();
		wHome.goToAPage(title);
		wHome.goToDeletePage(title);
		button.ok();
		waitForElementNotPresent(wHome.ELEMENT_WIKI_PAGE_LINK.replace("${pageTitle}", title));
	}

	/**CaseId: 70261 -> Preview template when adding new page from template
	 * 
	 */
	@Test
	public void test05_PreviewTemplate(){
		String template =  wTempData.getWikiTemplateRandom();

		info("Add page with select a template layout");
		wHome.goToAddTemplateWikiPage();
		apManagement.selectTemplateWikiPage(template);
		click(button.ELEMENT_SELECT_BUTTON);

		click(apManagement.ELEMENT_PREVIEW_BUTTON);
		waitForAndGetElement(apManagement.ELEMENT_PREVIEW_SCREEN);
		waitForAndGetElement(apManagement.ELEMENT_PREVIEW_TEMPLATE_CONTENT.replace("${template}", template));
		click(apManagement.ELEMENT_CLOSE_PREVIEW_WINDOW);
	}

	/**CaseId: 70265 -> Auto Save when adding page from template
	 * 
	 */
	@Test
	public void test06_AutoSaveWhenAddingPageFormTemplate(){
		String template =  wTempData.getWikiTemplateRandom();
		String title = txData.getContentByTypeRandom(1);
		
		info("Add page with select a template layout");
		wHome.goToAddTemplateWikiPage();
		apManagement.selectTemplateWikiPage(template);
		click(button.ELEMENT_SELECT_BUTTON);
		apManagement.inputDataToPageSourceEditor(title,null,true,false);

		Utils.pause(30000);
		assert isElementPresent(apManagement.ELEMENT_DRAFT_NOTIFY);

		mDraft.goToManageDraft();
		waitForAndGetElement(mDraft.ELEMENT_DRAFT_OF_NEW_PAGE.replace("${title}", title));

		mDraft.deleteDraft(title);
	}

	/**CaseId: 70336 -> Resume a draft with save as normal
	 * pending because issue WIKI-493
	 */
	@Test
	public void test07_ResumeDraft(){
		String title = txData.getContentByTypeRandom(1);
		String content =  txData.getContentByTypeRandom(1);
		String newTitle = "newtitle"+txData.getContentByTypeRandom(1);
		String newContent = "newcontent"+txData.getContentByTypeRandom(1);
		String draftTitle = mDraft.ELEMENT_DRAFT_OF_NEW_PAGE.replace("${title}", title);

		info("Add new wiki page at Source Editor");		
		wHome.goToAddBlankPage();
		apManagement.goToSourceEditor();
		apManagement.inputDataToPageSourceEditor(title,content,true,true);
		Utils.pause(30000);
		assert isElementPresent(apManagement.ELEMENT_DRAFT_NOTIFY);

		mDraft.goToManageDraft();
		waitForAndGetElement(mDraft.ELEMENT_DRAFT_OF_NEW_PAGE.replace("${title}", title));

		click(draftTitle);
		apManagement.inputDataToPageSourceEditor(newTitle,newContent,true,true);
		apManagement.saveAddPage();

		mDraft.goToManageDraft();
		waitForElementNotPresent(draftTitle);

		wHome.goToHomeWikiPage();
		wHome.goToAPage(newTitle);
		wHome.goToDeletePage(newTitle);
		button.ok();
		waitForElementNotPresent(wHome.ELEMENT_WIKI_PAGE_LINK.replace("${pageTitle}", newTitle));
	}

	/**CaseId: 68838 -> Edit paragraph in page
	 * 
	 */
	@Test
	public void test08_EditParagraph(){
		String title = txData.getContentByTypeRandom(1);
		String content =  txData.getContentByTypeRandom(2);
		String newPara = "newtitle"+txData.getContentByTypeRandom(2);

		info("Add new wiki page at Source Editor");		
		wHome.goToAddBlankPage();
		apManagement.goToSourceEditor();
		apManagement.inputDataToPageSourceEditor(title,content,true,true);
		apManagement.saveAddPage();

		info("Edit page");
		wHome.goToHomeWikiPage();
		wHome.goToAPage(title);
		apManagement.editParagraph(content.split(" ")[1], newPara);
		apManagement.saveAddPage();
		waitForAndGetElement("//h2/*[text()='"+newPara.split(" ")[1]+"']");
		waitForElementNotPresent("//h2/*[text()='"+content.split(" ")[1]+"']");


		wHome.goToHomeWikiPage();
		wHome.goToAPage(title);
		wHome.goToDeletePage(title);
		button.ok();
		waitForElementNotPresent(wHome.ELEMENT_WIKI_PAGE_LINK.replace("${pageTitle}", title));
	}

	/**CaseId: 70059 -> Auto save when editing page
	 * 
	 */
	@Test
	public void test09_AutoSaveWhenEditingPage(){
		String title = txData.getContentByTypeRandom(1);
		String content =  txData.getContentByTypeRandom(1);
		String newTitle = "newtitle"+txData.getContentByTypeRandom(1);
		String newContent = "newcontent"+txData.getContentByTypeRandom(1);
		String newTitle2 = "newtitle"+txData.getContentByTypeRandom(1);
		String newContent2 = "newcontent"+txData.getContentByTypeRandom(1);
		String draftTitle1 = mDraft.ELEMENT_DRAFT_OF_EDIT_PAGE.replace("${title}", newTitle);
		String draftTitle2 = mDraft.ELEMENT_DRAFT_OF_EDIT_PAGE.replace("${title}", newTitle2);

		info("Add new wiki page at Source Editor");		
		wHome.goToAddBlankPage();
		apManagement.goToSourceEditor();
		apManagement.inputDataToPageSourceEditor(title,content,true,true);
		apManagement.saveAddPage();

		info("Edit wiki page at Source Editor");
		wHome.goToHomeWikiPage();
		wHome.goToAPage(title);
		wHome.goToEditPage();
		apManagement.inputDataToPageSourceEditor(newTitle,newContent,true,true);
		Utils.pause(30000);
		assert isElementPresent(apManagement.ELEMENT_DRAFT_NOTIFY);

		info("Edit draft page");
		mDraft.goToManageDraft();
		waitForAndGetElement(draftTitle1);
		click(draftTitle1);
		apManagement.inputDataToPageSourceEditor(newTitle2,newContent2,true,true);
		Utils.pause(30000);
		assert isElementPresent(apManagement.ELEMENT_DRAFT_NOTIFY);
		mDraft.goToManageDraft();
		waitForElementNotPresent(draftTitle1);
		waitForAndGetElement(draftTitle2);

		mDraft.deleteDraft(newTitle2);
		wHome.goToHomeWikiPage();
		wHome.goToAPage(title);
		wHome.goToDeletePage(title);
		button.ok();
		waitForElementNotPresent(wHome.ELEMENT_WIKI_PAGE_LINK.replace("${pageTitle}", title));
	}

	/**CaseId: 70280 -> Edit page when publish activity is checked
	 * 
	 */
	@Test
	public void test10_EditPageCheckPublicActivity(){
		String title = txData.getContentByTypeRandom(1);
		String content =  txData.getContentByTypeRandom(1);
		String newTitle = "newtitle"+txData.getContentByTypeRandom(1);
		String newContent = "newcontent"+txData.getContentByTypeRandom(1);

		info("Add new wiki page at Source Editor");		
		wHome.goToAddBlankPage();
		apManagement.goToSourceEditor();
		apManagement.inputDataToPageSourceEditor(title,content,true,true);
		apManagement.saveAddPage();

		info("Edit title of page -> check comment in activity");
		wHome.goToHomeWikiPage();
		wHome.goToAPage(title);
		wHome.goToEditPage();
		apManagement.inputDataToPageSourceEditor(newTitle,null,true,false);
		check(apManagement.ELEMENT_PUBLISH_ACTIVITY_CHECKBOX,2);
		apManagement.saveAddPage();
		waitForAndGetElement(wHome.ELEMENT_WIKI_PAGE_LINK.replace("${pageTitle}", newTitle));
		naTool.goToHomePage();
		waitForAndGetElement(activity.ELEMENT_WIKI_COMMENT_EDIT_TITLE.replace("${title}", newTitle));

		info("Edit content of page -> check comment in activity");
		hp.goToWiki();
		wHome.goToHomeWikiPage();
		wHome.goToAPage(title);
		wHome.goToEditPage();
		apManagement.inputDataToPageSourceEditor(null,newContent,false,true);
		check(apManagement.ELEMENT_PUBLISH_ACTIVITY_CHECKBOX,2);
		apManagement.saveAddPage();
		waitForAndGetElement(wHome.ELEMENT_WIKI_PAGE_LINK.replace("${pageTitle}", newTitle));
		naTool.goToHomePage();
		waitForAndGetElement(activity.ELEMENT_WIKI_COMMENT_EDIT_CONTENT.replace("${title}", newTitle));

		wHome.goToHomeWikiPage();
		wHome.goToAPage(newTitle);
		wHome.goToDeletePage(newTitle);
		button.ok();
		waitForElementNotPresent(wHome.ELEMENT_WIKI_PAGE_LINK.replace("${pageTitle}", newTitle));
	}	

	/**CaseId: 70281 -> Edit page when publish activity is not checked
	 * 
	 */
	@Test
	public void test11_EditPageUncheckPublicActivity(){
		String title = txData.getContentByTypeRandom(1);
		String content =  txData.getContentByTypeRandom(1);
		String newTitle = "newtitle"+txData.getContentByTypeRandom(1);
		String newContent = "newcontent"+txData.getContentByTypeRandom(1);

		info("Add new wiki page at Source Editor");		
		wHome.goToAddBlankPage();
		apManagement.goToSourceEditor();
		apManagement.inputDataToPageSourceEditor(title,content,true,true);
		apManagement.saveAddPage();

		info("Edit title of page -> check comment in activity");
		wHome.goToHomeWikiPage();
		wHome.goToAPage(title);
		wHome.goToEditPage();
		apManagement.inputDataToPageSourceEditor(newTitle,null,true,false);
		uncheck(apManagement.ELEMENT_PUBLISH_ACTIVITY_CHECKBOX,2);
		apManagement.saveAddPage();
		waitForAndGetElement(wHome.ELEMENT_WIKI_PAGE_LINK.replace("${pageTitle}", newTitle));
		naTool.goToHomePage();
		waitForElementNotPresent(activity.ELEMENT_WIKI_COMMENT_EDIT_TITLE.replace("${title}", newTitle));

		info("Edit content of page -> check comment in activity");
		hp.goToWiki();
		wHome.goToHomeWikiPage();
		wHome.goToAPage(title);
		wHome.goToEditPage();
		apManagement.inputDataToPageSourceEditor(null,newContent,false,true);
		uncheck(apManagement.ELEMENT_PUBLISH_ACTIVITY_CHECKBOX,2);
		apManagement.saveAddPage();
		waitForAndGetElement(wHome.ELEMENT_WIKI_PAGE_LINK.replace("${pageTitle}", newTitle));
		naTool.goToHomePage();
		waitForElementNotPresent(activity.ELEMENT_WIKI_COMMENT_EDIT_CONTENT.replace("${title}", newTitle));

		wHome.goToHomeWikiPage();
		wHome.goToAPage(newTitle);
		wHome.goToDeletePage(newTitle);
		button.ok();
		waitForElementNotPresent(wHome.ELEMENT_WIKI_PAGE_LINK.replace("${pageTitle}", newTitle));
	}

	/**CaseID: 109190 -> Rename Page
	 *
	 */
	@Test
	public void test12_01_RenamePageInLine(){
		String title = txData.getContentByTypeRandom(1);
		String content =  txData.getContentByTypeRandom(1);
		String newTitle = "newtitle"+txData.getContentByTypeRandom(1);

		info("Add new wiki page at Source Editor");		
		wHome.goToAddBlankPage();
		apManagement.goToSourceEditor();
		apManagement.inputDataToPageSourceEditor(title,content,true,true);
		apManagement.saveAddPage();

		info("Edit wiki page at Source Editor");
		wHome.goToHomeWikiPage();
		wHome.goToAPage(title);
		doubleClickOnElement(wHome.ELEMENT_PAGE_TITLE_INFO);
		type(wHome.ELEMENT_PAGE_TITLE_EDIT_TEXTBOX, newTitle, true);
		Utils.javaSimulateKeyPress(KeyEvent.VK_ENTER);
		waitForAndGetElement(wHome.ELEMENT_WIKI_PAGE_LINK.replace("${pageTitle}", newTitle));

		wHome.goToHomeWikiPage();
		wHome.goToAPage(newTitle);
		wHome.goToDeletePage(newTitle);
		button.ok();
		waitForElementNotPresent(wHome.ELEMENT_WIKI_PAGE_LINK.replace("${pageTitle}", newTitle));
	}

	@Test
	public void test12_02_RenamePageByEditingPage(){
		String title = txData.getContentByTypeRandom(1);
		String content =  txData.getContentByTypeRandom(1);
		String newTitle = "newtitle"+txData.getContentByTypeRandom(1);
		String newContent = "newcontent"+txData.getContentByTypeRandom(1);

		info("Add new wiki page at Source Editor");		
		wHome.goToAddBlankPage();
		apManagement.goToSourceEditor();
		apManagement.inputDataToPageSourceEditor(title,content,true,true);
		apManagement.saveAddPage();

		info("Edit wiki page at Source Editor");
		wHome.goToHomeWikiPage();
		wHome.goToAPage(title);
		wHome.goToEditPage();
		apManagement.inputDataToPageSourceEditor(newTitle,newContent,true,true);
		apManagement.saveAddPage();
		waitForAndGetElement(wHome.ELEMENT_WIKI_PAGE_LINK.replace("${pageTitle}", newTitle));

		wHome.goToHomeWikiPage();
		wHome.goToAPage(newTitle);
		wHome.goToDeletePage(newTitle);
		button.ok();
		waitForElementNotPresent(wHome.ELEMENT_WIKI_PAGE_LINK.replace("${pageTitle}", newTitle));
	}
}