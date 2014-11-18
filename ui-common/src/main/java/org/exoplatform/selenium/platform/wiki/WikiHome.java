package org.exoplatform.selenium.platform.wiki;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WikiHome extends PlatformBase{

	public final String ELEMENT_WIKI_PAGE_LINK = "//*[@href='/portal/intranet/wiki/${pageTitle}']";
	public final By ELEMENT_WIKI_HOME_PAGE_LINK = By.xpath("//*[@href='/portal/intranet/wiki/WikiHome']");
	public final By ELEMENT_WIKI_HOME_PAGE_TEXT = By.xpath("//*[@id='titleInfo' and text()='Wiki Home']");
	public final By ELEMENT_ADD_PAGE_LINK = By.xpath("//*[@id='UIWikiPageControlArea_PageToolBar']//div[contains(text(),'Add Page')]");
	public final By ELEMENT_FROM_TEMPLATE_LINK = By.xpath ("//i[@class='uiIconAddPageFromTemplate']");
	public final By ELEMENT_BLANK_PAGE_LINK = By.xpath ("//i[@class='uiIconAddPage']");

	public final By ELEMENT_EDIT_PAGE_LINK= By.xpath("//*[@class='uiIconEditPage uiIconLightGray']");;
	
	//More menu
	public final By ELEMENT_MORE_LINK = By.xpath("//*[@id='UIWikiPageControlArea_PageToolBar']//div[contains(text(), 'More')]");
	public final By ELEMENT_DELETE_LINK = By.linkText("Delete Page");
	public final By ELEMENT_DELETE_LINK_2 = By.className("uiIconDeletePage");
	
	//Content of page
	public final String ELEMENT_MARCRO_COLOR = "//*[@style='color:${color};' and contains(text(),'${message}')]";
	public final By ELEMENT_PAGE_TITLE_INFO = By.id("titleInfo");
	public final By ELEMENT_PAGE_TITLE_EDIT_TEXTBOX = By.id("EdiableInput");
	
	/**
	 * constructor
	 * @param dr
	 */
	public WikiHome(WebDriver dr){
		this.driver=dr;
	}
	/**
	 * Go to "Add blank wiki page"
	 * 
	 */
	public void goToAddBlankPage(){
		info("--Go to add blank wiki page--");
		mouseOverAndClick(ELEMENT_ADD_PAGE_LINK);
		mouseOverAndClick(ELEMENT_BLANK_PAGE_LINK);
	}

	/**
	 * Go to "Add template wiki page"
	 * 
	 */
	public void goToAddTemplateWikiPage(){
		info("--Go to add template wiki page--");
		mouseOverAndClick(ELEMENT_ADD_PAGE_LINK);
		mouseOverAndClick(ELEMENT_FROM_TEMPLATE_LINK);
	}

	/**
	 * Go to "Add blank wiki page"
	 * 
	 */
	public void goToEditPage(){
		info("--Go to edit page--");
		click(ELEMENT_EDIT_PAGE_LINK);
		waitForElementNotPresent(ELEMENT_WIKI_HOME_PAGE_TEXT);
	}

	/**
	 * Go to Home Wiki Page
	 */
	public void goToHomeWikiPage(){
		info("-- Go to wiki home page --");
		click(ELEMENT_WIKI_HOME_PAGE_LINK);
		waitForAndGetElement(ELEMENT_WIKI_HOME_PAGE_TEXT);
	}

	/**
	 * Select any page
	 * @param title
	 */
	public void goToAPage(String title){
		info("-- Go to wiki page --");
		click(ELEMENT_WIKI_PAGE_LINK.replace("${pageTitle}", title.replace(" ", "_")));
		waitForElementNotPresent(ELEMENT_WIKI_HOME_PAGE_TEXT);
	}

	/**
	 * Select any page
	 * @param title
	 */
	public void goToDeletePage(String title){
		info("Go to delete wiki page...");
		click(ELEMENT_MORE_LINK);
		if (waitForAndGetElement(ELEMENT_DELETE_LINK_2, 5000, 0) == null){
			mouseOverAndClick(ELEMENT_DELETE_LINK);
		}else {
			click(ELEMENT_DELETE_LINK_2);
		}
	}
}
