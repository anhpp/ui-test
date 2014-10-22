package org.exoplatform.selenium.platform.wiki;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddEditPageManagement extends WikiHome{
	//Source editor
	public final By ELEMENT_TITLE_WIKI_INPUT = By.id("titleInput");
	public final By ELEMENT_CONTENT_WIKI_INPUT = By.id("Markup");
	public final By ELEMENT_PUBLISH_ACTIVITY_CHECKBOX = By.id("PublishActivityUpper");
	public final By ELEMENT_PREVIEW_BUTTON = By.xpath("//*[@class='uiIconWatchPage']");
	public final By ELEMENT_PREVIEW_SCREEN = By.xpath("//div[@class='popupTitle' and text()='Preview']");

	//Richtext mode
	public final By ELEMENT_CONTENT_WIKI_FRAME = By.xpath("//div[@class='xRichTextEditor']/iframe");

	public final By ELEMENT_SAVE_BUTTON_ADD_PAGE = By.id("UISubmitToolBarUpper_SavePage_");
	public final By ELEMENT_CANCEL_BUTTON_ADD_PAGE = By.id("UISubmitToolBarBottom_Cancel_");

	public final By ELEMENT_SOURCE_EDITOR_BUTTON= By.xpath("//*[contains(text(),'Source Editor')]");
	public final By ELEMENT_RICHTEXT_BUTTON = By.xpath("//*[contains(text(),'Rich Text')]");

	public final By ELEMENT_UPLOAD_NAME = By.name("file");
	public final By ELEMENT_BODY_CONTAINER = By.xpath("//*[@class='uiRightContainerArea']");

	//Draft notification
	public By ELEMENT_DRAFT_NOTIFY = By.xpath("//*[contains(@class, 'uiWikiPageEditForm') and contains(text(), 'Draft saved at')]");

	//Add page from template
	public final String ELEMENT_SELECT_TEMPLATE_LINK = "//input[@value = '{$template}']";
	public final By ELEMENT_TEMPLATE_SELECT_FORM = By.id("UIWikiSelectTemplateForm");
	
	//Preview page
	public final String ELEMENT_PREVIEW_TEMPLATE_CONTENT = "//*[@class='uiWikiPageTitlePreview' and contains(text(), '${template}')]";
	public By ELEMENT_CLOSE_PREVIEW_WINDOW=By.xpath("//div[text()='Preview']/..//*[contains(@class,'uiIconClose')]");

	/**
	 * constructor
	 * @param dr
	 */
	public AddEditPageManagement(WebDriver dr){
		super(dr);
		this.driver=dr;
	}

	/**
	 * Select template to create page
	 */
	public void selectTemplateWikiPage(String template){
		info("--Select a template--");
		waitForAndGetElement(ELEMENT_TEMPLATE_SELECT_FORM);
		By eTemplate = By.xpath(ELEMENT_SELECT_TEMPLATE_LINK.replace("{$template}",template));
		click(eTemplate, 2);
	}

	/**
	 * Change to Source Editor mode
	 */
	public void goToSourceEditor(){
		if(waitForAndGetElement(ELEMENT_SOURCE_EDITOR_BUTTON,5000,0,2)!=null){
			info("Go to Source Editor");
			click(ELEMENT_SOURCE_EDITOR_BUTTON);	
		}
		waitForAndGetElement(ELEMENT_CONTENT_WIKI_INPUT);
	}

	/**
	 * Change to Rich Text Mode
	 */
	public void goToRichTextEditor(){
		if(waitForAndGetElement(ELEMENT_RICHTEXT_BUTTON,5000,0,2)!=null){
			info("Go to Rich Text Mode");
			click(ELEMENT_RICHTEXT_BUTTON);
		}
		waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME);
	}

	/**
	 * Modify Wiki content with Source editor
	 * 
	 * 
	 * @param title
	 * 			updated title of the wiki page. Can not be <code>null</code>
	 * @param content
	 * 			updated content of the wiki page. Can not be <code>null</code>
	 * 
	 */
	public void inputDataToPageSourceEditor(String title, String content, Boolean isClearTitle, Boolean isClearContent){
		String[] text ;
		info("Modify data with source editor");
		if(title != null){
			if(isClearTitle)
				type(ELEMENT_TITLE_WIKI_INPUT, title, true);
			else
				type(ELEMENT_TITLE_WIKI_INPUT, title, false);
		}	
		if(content != null){
			if(isClearContent){
				waitForAndGetElement(ELEMENT_CONTENT_WIKI_INPUT).clear();
				text = content.split("</br>");
				for(int i=0; i < text.length; i++){
					type(ELEMENT_CONTENT_WIKI_INPUT,text[i],false);
					waitForAndGetElement(ELEMENT_CONTENT_WIKI_INPUT).sendKeys(Keys.ENTER);
				}
			}
			else{
				text = content.split("</br>");
				for(int i=0; i < text.length; i++){
					type(ELEMENT_CONTENT_WIKI_INPUT,text[i],false);
					waitForAndGetElement(ELEMENT_CONTENT_WIKI_INPUT).sendKeys(Keys.ENTER);
				}
			}
		}	
	}

	/**
	 * Modify Wiki content with RichText editor
	 * 
	 * 
	 * @param title
	 * 			updated title of the wiki page. Can not be <code>null</code>
	 * @param content
	 * 			updated content of the wiki page. Can not be <code>null</code>
	 * 
	 */
	public void inputDataToPageRichText(String title, String content, Boolean isClearTitle, Boolean isClearContent){
		if(title != null){
			if(isClearTitle)
				type(ELEMENT_TITLE_WIKI_INPUT, title, true);
			else
				type(ELEMENT_TITLE_WIKI_INPUT, title, false);
		}
		if (content != null){
			if(isClearContent){
				inputDataToFrame(ELEMENT_CONTENT_WIKI_FRAME, content);
			}
			else{
				inputDataToFrame(ELEMENT_CONTENT_WIKI_FRAME, content);
			}
			Utils.pause(1000);
			driver.switchTo().defaultContent();
		}
	}

	/**
	 * Save add page
	 */
	public void saveAddPage(){
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);
	}

	/**
	 * Cancel add page
	 */
	public void cancelAddPage(){
		click(ELEMENT_CANCEL_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_CANCEL_BUTTON_ADD_PAGE);
	}

	/**
	 * publish page
	 */
	public void publishPage(){
		check(ELEMENT_PUBLISH_ACTIVITY_CHECKBOX, 2);
	}

	/**
	 * unpublish page
	 */
	public void unPublishPage(){
		uncheck(ELEMENT_PUBLISH_ACTIVITY_CHECKBOX, 2);
	}

	/**
	 * Go to Preview Page
	 */
	public void goToPreviewPage(){
		click(ELEMENT_PREVIEW_BUTTON);
		waitForAndGetElement(ELEMENT_PREVIEW_SCREEN);
	}

	/** 
	 * Attach a file to a Wiki page
	 * 
	 * @param link
	 * 			link of file that will be attached
	 */
	public void attachFileInWiki(String link){
		int notDisplay = 0;
		String path = Utils.getAbsoluteFilePath(link);
		info("Attach a file: " + path);
		for(int i =0; i<=4; i++){
			if(waitForAndGetElement(ELEMENT_UPLOAD_NAME, 5000, 0, notDisplay)!=null)
				break;
			else{
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[0].scrollHeight;", 
						waitForAndGetElement(ELEMENT_BODY_CONTAINER));
			}
		}
		WebElement upload = waitForAndGetElement(ELEMENT_UPLOAD_NAME, 5000, 1, notDisplay);
		((JavascriptExecutor)driver).executeScript("arguments[0].style.visibility = 'visible'; arguments[0].style.height = '1px'; " +
				"arguments[0].style.width = '1px'; arguments[0].style.opacity = 1", upload);
		((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block';", upload);
		upload.sendKeys(path);
		switchToParentWindow();
		waitForAndGetElement(By.linkText(link.replace("TestData/", "")));
	}
	
	//Wiki page
	/*================= Paragraph =================*/
	/**
	 * Edit paragraph in a Wiki page
	 * 
	 * 
	 * @param paragraphTitle
	 * 				input paragraph title without space character.  Can not be <code>null</code>.
	 * @param paragraphContent
	 * 				input paragraph content with heading followed help tips.  Can not be <code>null</code>.
	 * 
	 */
	public void editParagraph (String paragraphTitle, String paragraphContent) {
		info("-- Editing a paragraph... " + paragraphTitle);
		String ELEMENT_PARAGRAPH_ID = "H"+paragraphTitle;
		mouseOver(By.id(ELEMENT_PARAGRAPH_ID), true);
		WebElement element = waitForAndGetElement(By.xpath("//*[@data-original-title='Edit Section: " + paragraphTitle + "']"));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
		Utils.pause(500);
		driver.navigate().refresh();
		Utils.pause(2000);
		type(ELEMENT_CONTENT_WIKI_INPUT, paragraphContent, true);
		switchToParentWindow();
	}
}
