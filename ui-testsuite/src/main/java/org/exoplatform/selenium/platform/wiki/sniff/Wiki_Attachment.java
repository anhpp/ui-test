package org.exoplatform.selenium.platform.wiki.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.objectdatabase.common.AttachmentFileDatabase;
import org.exoplatform.selenium.platform.objectdatabase.common.TextBoxDatabase;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.wiki.BasicAction;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author lientm
 * @date: 1-July-2013
 */
public class Wiki_Attachment extends PlatformBase {

	ManageAccount magAc;
	AttachmentFileDatabase fData;
	TextBoxDatabase txData;
	BasicAction ba;
	@BeforeMethod
	public void setUpBeforeTest() throws Exception{
		getDriverAutoSave();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		ba = new BasicAction(driver);
		magAc.signIn(DATA_USER1, DATA_PASS); 
		ba.goToWiki();
		fData = new AttachmentFileDatabase();
		txData = new TextBoxDatabase();
		fData.setAttachFileData(attachmentFilePath,defaultSheet,isUseFile,jdbcDriver,dbUrl,user,pass,sqlAttach);
		txData.setContentData(texboxFilePath,defaultSheet,isUseFile,jdbcDriver,dbUrl,user,pass,sqlAttach);
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
		String title = txData.getContentByTypeRandom(1);
		String content =  txData.getContentByTypeRandom(1);
		String newTitle = "newtitle"+txData.getContentByTypeRandom(1);
		String newContent = "newcontent"+txData.getContentByTypeRandom(1);
		String link = fData.getAttachFileByTypeRandom(1);
		String newLink = fData.getAttachFileByTypeRandom(1);
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
