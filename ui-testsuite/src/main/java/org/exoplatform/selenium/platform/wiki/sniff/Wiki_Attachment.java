package org.exoplatform.selenium.platform.wiki.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.wiki.BasicAction;
import org.exoplatform.selenium.testdata.WikiDatabase;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author lientm
 * @date: 1-July-2013
 */
public class Wiki_Attachment extends BasicAction {

	ManageAccount magAc;
	String filePath = "TestData/DataDriven/" + "wiki.xls";
	String sheet = "wiki";
	WikiDatabase wData;

	@BeforeMethod
	public void setUpBeforeTest() throws Exception{
		getDriverAutoSave();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		magAc.signIn(DATA_USER1, DATA_PASS); 
		goToWiki();
		wData = new WikiDatabase();
		wData.setWikiData(wikiDataFilePath,wikiSheet,isRandom,isUseFile,jdbcDriver,dbUrl,user,pass,sqlWiki);
	}

	@AfterMethod
	public void afterTest(){
		magAc.signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**CaseId: 68842 + 70032 + 70033 -> Upload file while adding/editing wiki page
	 * + download attachment + delete attachment (attachment are .doc & an image)
	 * Need to update qmetry for caseid 70033 - Delete Attachment
	 * Base on issue: https://jira.exoplatform.org/browse/WIKI-736: Version of attachment file will not updated after deleting
	 */
	@Test
	public void test01_UploadDownloadDeleteAttachment(){
		String title = wData.wikiTitle[index];
		String content =  wData.wikiContent[index];
		String newTitle = "newtitle"+wData.wikiTitle[index];
		String newContent = "newcontent"+wData.wikiContent[index];
		String link = wData.linkAttach[0];
		String newLink = wData.linkAttach[0];
		By imgElement = By.xpath("//img[contains(@src,"+newLink+")]");

		info("Add new wiki page having attachment");
		addBlankWikiPageHasAttachment(title, content, link);

		info("Edit wiki page having attachment");
		mouseOverAndClick(ELEMENT_EDIT_PAGE_LINK);
		addWikiPageSourceEditor(newTitle, newContent);
		attachFileInWiki("TestData/" + newLink, 2);
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForAndGetElement(ELEMENT_ATTACHMENT_NUMBER.replace("${No}", "2"));

		info("Check download attachment successfully");
		click(ELEMENT_ATTACHMENT_ICON);
		click(By.linkText(newLink));
		switchToNewWindow();
		waitForAndGetElement(imgElement);
		switchToParentWindow();
		assert checkFileExisted(newLink);

		click(By.linkText(link));
		Utils.pause(3000);
		assert checkFileExisted(link);


		info("Delete attachment");
		deleteAnAttachment(link);
		deleteAnAttachment(newLink);

		deleteCurrentWikiPage();	
	}
}
