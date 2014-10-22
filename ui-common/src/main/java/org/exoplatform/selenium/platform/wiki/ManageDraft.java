package org.exoplatform.selenium.platform.wiki;

import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Provides all methods of managing a draft of wiki's page. 
 * 
 * 
 */
public class ManageDraft extends RichTextMode {
	public ManageDraft(WebDriver dr) {
		super(dr);
		// TODO Auto-generated constructor stub
	}

	ManageAlert magAl;
		
	//Manage Draft screen
	public String ELEMENT_DRAFT_OF_NEW_PAGE = "//*[@id='UIWikiDraftGrid']//*[contains(text(),'${title} (New Page)')]";
	public String ELEMENT_DELETE_DRAFT = "//*[contains(text(), '${title}')]/../../..//*[@data-original-title='Delete Draft']";
	public String ELEMENT_DRAFT_OF_EDIT_PAGE = "//*[@id='UIWikiDraftGrid']//*[text()='${title}']";
	public By ELEMENT_DRAFT_CONFIRM_POPUP = By.xpath("//div[@class='confirmMessage' and contains(text(), 'The draft has been created. Do you want to keep it?')]");
	public By ELEMENT_DRAFT_NO_BUTTON = By.xpath("//*[contains(text(),'No')]");
	public String ELEMENT_DRAFT_TITLE = "//*[contains(text(), '${title}')]";
	
	/**
	 * Goes to the draft management of wiki's page.
	 * 
	 */
	public void goToManageDraft(){
		mouseOverAndClick(ELEMENT_BROWSE_LINK);
		mouseOverAndClick(ELEMENT_MY_DRAFT);
		Utils.pause(1000);
	}
	
	/**
	 * Deletes a draft.
	 * 
	 * 
	 * @param title The title of a wiki's page to be deleted.
	 */
	public void deleteDraft(String title){
		magAl = new ManageAlert(driver);
		click(ELEMENT_DELETE_DRAFT.replace("${title}", title));
		magAl.acceptAlert();
		waitForElementNotPresent(ELEMENT_DELETE_DRAFT.replace("${title}", title));
	}
}
