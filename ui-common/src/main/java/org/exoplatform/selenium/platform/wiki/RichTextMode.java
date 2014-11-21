package org.exoplatform.selenium.platform.wiki;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

/**
 * Provides all methods of editing a wiki page directly in the wiki markup language.
 * 
 * 
 */
public class RichTextMode extends Template {
	Button but = new Button(driver);

	//Link menu
	public By ELEMENT_LINK = By.xpath("//*[text()='Link']");
	public By ELEMENT_WIKI_PAGE_LINK = By.xpath("//*[text()='Wiki Page...']");

	//Add wiki page link popup
	public By ELEMENT_SEARCH_TAB = By.xpath("//div[text()='Search']");
	public By ELEMENT_SEARCH_TEXTBOX = By.xpath("//input[@title='Type a keyword to search for a wiki page']");
	public By ELEMENT_SEARCH_BUTTON = By.xpath("//button[text()='Search']");
	public String ELEMENT_PAGE_SELECTED = "//*[@class='xPagePreview' and @title='${page}']";
	public String ELEMENT_PAGE_SELECTED_PLF41 = "//*[@class='xPagesSelector xPagesSearch']//*[@class='xPagePreview' and @title='${page}']";
	public By ELEMENT_LABEL_LINK_TEXTBOX = By.xpath("//input[@title='Type the label of the created link.']");
	public By ELEMENT_TOOLTIP_LINK_TEXTBOX = By.xpath("//input[@title='Type the tooltip of the created link, which appears when mouse is over the link.']");
	public By ELEMENT_REMOVE_LINK = By.xpath("//div[text()='Remove Link']");
	public By ELEMENT_EDIT_LINK = By.xpath("//div[text()='Edit Link...']");
	public By ELEMENT_ADD_NEW_LINKPAGE_TEXTBOX =  By.xpath("//input[@title='Type the name of the page to be created. The final name of the page may vary since some characters are filtered.']");
	public By ELEMENT_ALL_PAGE_TAB = By.xpath("//div[contains(text(), 'All pages')]");
	public By ELEMENT_ADD_NEW_PAGE_LINK = By.xpath("//*[@class='gwt-Label xNewPagePreview']");
	public By ELEMENT_ADD_WIKI_PAGE_FRAME = By.xpath("//iframe[@class='gwt-RichTextArea']");

	//Table
	public By ELEMENT_TABLE_LINK = By.xpath("//*[text()='Table']");
	public By ELEMENT_INSERT_TABLE_LINK = By.xpath("//*[text()='Insert Table...']");
	public By ELEMENT_ROW_TEXTBOX = By.xpath("//*[@title='Row count']");
	public By ELEMENT_COLUMN_TEXTBOX = By.xpath("//*[@title='Column count']");

	//Macro
	public By ELEMENT_MACRO_LINK = By.xpath("//*[text()='Macro']");
	public By ELEMENT_INSERT_MACRO_LINK = By.xpath("//*[text()='Insert Macro...']");
	public By ELEMENT_MACRO_CATEGORY_SELECT = By.xpath("//select[@title='Select a macro category']");
	public By ELEMENT_MACRO_TYPE_FILTER = By.xpath("//input[@title='Type to filter']");
	public String ELEMENT_MACRO_LABEL = "//*[text()='${macro}']";
	public By ELEMENT_RICHTEXTMODE_FRAME = By.id("gwt-RichTextArea");
	public String ELEMENT_MACRO_BOX = "//div[@class='box']/*[contains(.,'${macro}')]";
	public String ELEMENT_MACRO_EXCERPT = "//*[@class='ExcerptClass' and contains(text(),'${macro}')]";
	public String ELEMENT_MACRO_INFO_MESSAGE = "//*[@class='box infomessage' and contains(text(),'${macro}')]";
	public String ELEMENT_MACRO_TABLE_CONTENT = "//span[@class='macro-placeholder' and contains(.,'toc')]";
	public String ELEMENT_MACRO_TIP_MESSAGE = "//*[@class='box tipmessage' and contains(text(),'${macro}')]";
	public String ELEMENT_MACRO_ERROR_MESSAGE = "//*[@class='box errormessage' and contains(text(),'${macro}')]";
	public String ELEMENT_MACRO_SUCCESS_MESSAGE = "//*[@class='box successmessage' and contains(text(),'${macro}')]";
	public String ELEMENT_MACRO_TEXT = "//*[contains(@style,'${color}') and contains(text(),'${text}')]";
	public String ELEMENT_MACRO_WARNING_MESSAGE = "//*[@class='box warningmessage' and contains(text(),'${macro}')]";
	public String ELEMENT_MACRO_CHART = "//img[@alt='${title}']";
	public String ELEMENT_MACRO_FOOTNOTE = "//li[contains(.,'${macro}')]//a[text()='^']";
	public By ELEMENT_MACRO_RSS_TITLE = By.xpath("//p[@class='rssitemtitle']");
	public By ELEMENT_MACRO_COLLAPSE_LINK = By.xpath("//div[@class='gwt-MenuItemLabel' and text()='Collapse All']");
	public By ELEMENT_MACRO_EXPAND_LINK = By.xpath("//div[@class='gwt-MenuItemLabel' and text()='Expand All']");

	//Macro: Tip message
	public By ELEMENT_CONTENT_MESSAGE_TEXTAREA = By.id("pd-content-input");
	public String ELEMENT_TIP_MESSAGE_MACRO = "//*[@class='box tipmessage' and text()='${message}']";

	//Macro: Box
	public By ELEMENT_BOX_TITLE = By.id("pd-title-input");
	public By ELEMENT_BOX_CONTENT = By.id("pd-content-input");

	//Macro:Error message
	public By ELEMENT_ERROR_MESSAGE_CONTENT = By.id("pd-content-input");

	//Macro: Children
	public By ELEMENT_CHILDREN_DESCENDANT_SELECT = By.id("pd-descendant-input");

	//Macro: Code
	public By ELEMENT_CODE_LANGUAGE_INPUT = By.id("pd-language-input");
	public By ELEMENT_CODE_TITLE_INPUT = By.id("pd-title-input");
	public By ELEMENT_CODE_CONTENT_INPUT = By.id("pd-content-input");
	public String ELEMENT_MACRO_CODE = "//div[@class='box code' and contains(.,'${macro}')]";

	//Macro: Color
	public By ELEMENT_COLOR_TEXTBOX = By.id("pd-name-input");
	public By ELEMENT_COLOR_MESSAGE = By.id("pd-content-input");

	//Image
	public By ELEMENT_IMAGE_LINK = By.xpath("//*[text()='Image']");
	public By ELEMENT_IMAGE_LINK_ATTACH = By.xpath("//*[text()='Attached Image...']");
	public By ELEMENT_IMAGE_LINK_REMOVE = By.xpath("//*[text()='Remove Image']");
	public By ELEMENT_IMAGE_EDIT_LINK = By.xpath("//div[text()='Edit Image...']");
	public By ELEMENT_IMAGE_WIDTH = By.xpath("//div[contains(text(), 'Width')]/..//input[1]");
	public By ELEMENT_IMAGE_HEIGHT = By.xpath("//div[contains(text(), 'Height')]/..//input[2]");
	public By ELEMENT_IMAGE_ALTERNATIVE_TEXT = By.xpath("//div[contains(text(), 'Alternative text')]/..//input[1]");
	public final By ELEMENT_IMAGE_INSERT_BUTTON = By.xpath("//*[text()='Insert Image']");
	public By ELEMENT_IMAGE_EXTERNAL_LINK = By.xpath("//*[text()='External Image...']");
	public By ELEMENT_IMAGE_SETTING_BUTTON = By.xpath("//*[text()='Image Settings']");
	public By ELEMENT_IMAGE_LOCATION = By.xpath("//input[@title='Image location']");
	//Chart
	public By ELEMENT_CHART_HEIGHT = By.id("pd-height-input");
	public By ELEMENT_CHART_PARAMS = By.id("pd-params-input");
	public By ELEMENT_CHART_SOURCE = By.id("pd-source-input");
	public By ELEMENT_CHART_TITLE = By.id("pd-title-input");
	public By ELEMENT_CHART_TYPE = By.id("pd-type-input");
	public By ELEMENT_CHART_WIDTH = By.id("pd-width-input");
	public By ELEMENT_CHART_CONTENT = By.id("pd-content-input");

	//HTML
	public By ELEMENT_HTML_CLEAN = By.id("pd-clean-input");
	public By ELEMENT_HTML_WIKI = By.id("pd-wiki-input");
	public By ELEMENT_HTML_CONTENT = By.id("pd-content-input");
	//Fomula
	public By ELEMENT_FORMULA_FONTSIZE = By.id("pd-fontSize-input");
	public By ELEMENT_FORMULA_IMAGETYPE = By.id("pd-imageType-input");
	public By ELEMENT_FORMULA_CONTENT = By.id("pd-content-input");

	//RSS
	public By ELEMEMT_RSS_CONTENT = By.id("pd-content-input");
	public By ELEMEMT_RSS_COUNT = By.id("pd-count-input");
	public By ELEMEMT_RSS_DECORATION = By.id("pd-decoration-input");
	public By ELEMEMT_RSS_FEED = By.id("pd-feed-input");
	public By ELEMEMT_RSS_IMAGE = By.id("pd-image-input");
	public By ELEMEMT_RSS_WIDTH = By.id("pd-width-input");

	//WebPage
	public By ELEMENT_WEBPAGE_LINK = By.xpath("//*[text()='Web Page...']");
	public By ELEMENT_WEBPAGE_TEXTBOX = By.xpath("//input[@title='Web page address']");

	//WebEmail
	public By ELEMENT_EMAIL_LINK = By.xpath("//*[text()='Email Address...']");
	public By ELEMENT_EMAIL_TEXTBOX = By.xpath("//input[@title='Email address']");

	//Attach file 
	public By ELEMENT_ATTACH_FILE_LINK = By.xpath("//*[text()='Attached File...']");
	public By ELEMENT_ATTACH_FILE_PATH = By.xpath("//input[@name='filepath']");
	public String ELEMENT_ATTACH_FILE_CEL = "//*[@class='cell']//*[contains(text(), '${file}')]/../..//img[1]";

	/**
	 * Add link to a Wiki page
	 * 
	 * @param search
	 * 			parameter to choose whether to search page link or not
	 * @param page
	 * 			Wiki page that will be the target link
	 * @param label
	 * 			label of link that will be added into Wiki page
	 * @param tooltip
	 * 			
	 */
	public void insertPageLink2WikiPage(boolean search, String page, String label, String tooltip,Object...opParam){
		Boolean verify =(Boolean)(opParam.length>0?opParam[0]:false);
		mouseOverAndClick(ELEMENT_LINK);
		mouseOverAndClick(ELEMENT_WIKI_PAGE_LINK);
		Utils.pause(500);
		info("Create link to the page " + page);
		if (search){
			click(ELEMENT_SEARCH_TAB);
			type(ELEMENT_SEARCH_TEXTBOX, page, true);
			click(ELEMENT_SEARCH_BUTTON);
		}
		if(waitForAndGetElement(ELEMENT_PAGE_SELECTED.replace("${page}", page), 5000,0)!=null)
			click(ELEMENT_PAGE_SELECTED.replace("${page}", page));
		else
			click(ELEMENT_PAGE_SELECTED_PLF41.replace("${page}", page));
		click(but.ELEMENT_SELECT_BUTTON);
		if(label!=null && label!="")
			type(ELEMENT_LABEL_LINK_TEXTBOX,label,true);
		if(tooltip!=null && tooltip!="")
			type(ELEMENT_TOOLTIP_LINK_TEXTBOX,tooltip,true);
		Utils.pause(500);
		click(but.ELEMENT_CREATE_LINK_BUTTON);
		waitForElementNotPresent(but.ELEMENT_CREATE_LINK_BUTTON);
		if(verify){
			WebElement e = waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME,DEFAULT_TIMEOUT,1,2);
			driver.switchTo().frame(e);
			if(label!=null && label!="")
				waitForAndGetElement(By.linkText(label));
			if(tooltip!=null && tooltip!="")
				waitForAndGetElement(By.xpath("//*[@title='"+tooltip+"']"));
			switchToParentWindow();
		}
	}

	/**
	 * Select a macro in a Wiki page editor
	 * 
	 * @param cat
	 * 			category to which a macro that will be chosen belongs
	 * @param macro
	 * 			macro name that will be chosen
	 * 
	 * @see #createTipMessageMacro(String)
	 * @see #createColorMacro(String, String)
	 */
	public void goToSelectAMacro(String cat, String macro){
		info("Select a macro: " + macro);
		mouseOverAndClick(ELEMENT_MACRO_LINK);
		mouseOverAndClick(ELEMENT_INSERT_MACRO_LINK);
		select(ELEMENT_MACRO_CATEGORY_SELECT, cat);
		mouseOverAndClick(ELEMENT_MACRO_TYPE_FILTER);
		type(ELEMENT_MACRO_TYPE_FILTER,macro,true);
		Utils.pause(1000);
		click(ELEMENT_MACRO_LABEL.replace("${macro}", macro));
		click(but.ELEMENT_SELECT_BUTTON);
		Utils.pause(3000);
	}

	/**
	 * Add macro: "Message"
	 * 
	 * @param message
	 * 			 message that will be displayed in macro
	 * @param type: type of message (Tip, Info, Warning, Success, Excerpt, Footnote, Error)
	 */

	public void createMessageMacro(String type, String message){
		if (type == "Tip") {
			goToSelectAMacro("Formatting", "Tip Message");
			type(ELEMENT_CONTENT_MESSAGE_TEXTAREA, message, true);
			click(but.ELEMENT_CREATE_MACRO_BUTTON);
		}
		if (type == "Info") {
			goToSelectAMacro("Formatting", "Info Message");
			type(ELEMENT_CONTENT_MESSAGE_TEXTAREA, message, true);
			click(but.ELEMENT_CREATE_MACRO_BUTTON);
		}
		if (type == "Warning") {
			goToSelectAMacro("Formatting", "Warning Message");
			type(ELEMENT_CONTENT_MESSAGE_TEXTAREA, message, true);
			click(but.ELEMENT_CREATE_MACRO_BUTTON);
		}
		if (type == "Success") {
			goToSelectAMacro("Formatting", "Success Message");
			type(ELEMENT_CONTENT_MESSAGE_TEXTAREA, message, true);
			click(but.ELEMENT_CREATE_MACRO_BUTTON);
		}
		if (type == "Excerpt") {
			goToSelectAMacro("Content", "Excerpt");
			type(ELEMENT_CONTENT_MESSAGE_TEXTAREA, message, true);
			click(but.ELEMENT_CREATE_MACRO_BUTTON);	
		}

		if (type == "Footnote") {
			goToSelectAMacro("Content", "Footnote");
			type(ELEMENT_CONTENT_MESSAGE_TEXTAREA, message, true);
			click(but.ELEMENT_CREATE_MACRO_BUTTON);	
		}

		if (type == "Error") {
			goToSelectAMacro("Formatting", "Error Message");
			type(ELEMENT_ERROR_MESSAGE_CONTENT, message, true);
			click(but.ELEMENT_CREATE_MACRO_BUTTON);	
		}
	}
	
	/**
	 * Create Box macro
	 * @param title
	 * @param content
	 */
	public void createBoxMacro(String title,String content){
		goToSelectAMacro("Formatting", "Box");
		type(ELEMENT_BOX_TITLE, title, true);
		type(ELEMENT_BOX_CONTENT, content, true);
		click(but.ELEMENT_CREATE_MACRO_BUTTON);
	}

	/**
	 * Create Children macro
	 * @param descendant
	 */
	public void createChildrenMacro(String descendant){
		goToSelectAMacro("Navigation", "Children"); //Bug Wiki-863
		mouseOverAndClick(ELEMENT_CHILDREN_DESCENDANT_SELECT);
		type(ELEMENT_CHILDREN_DESCENDANT_SELECT,descendant,false);
		Utils.pause(1000);
		click(but.ELEMENT_CREATE_MACRO_BUTTON);		
	}

	/**
	 * Create a table of content macro
	 */
	public void createTableOfContentsMacro(){
		goToSelectAMacro("Navigation", "Table Of Contents");
		click(but.ELEMENT_CREATE_MACRO_BUTTON);
	}

	/**
	 * Create a code macro
	 * @param language
	 * @param title
	 * @param message
	 */
	public void createCodeMacro(String language, String title, String message){
		goToSelectAMacro("Formatting", "Code");
		type(ELEMENT_CODE_LANGUAGE_INPUT,language,true);
		type(ELEMENT_CODE_TITLE_INPUT,title,true);
		type(ELEMENT_CONTENT_MESSAGE_TEXTAREA, message, true);
		click(but.ELEMENT_CREATE_MACRO_BUTTON);	
	}

	/**
	 * Add macro: "color" into a Wiki page
	 * 
	 * @param color
	 * 			color setting of macro
	 * @param message
	 * 			message setting of macro
	 */
	public void createColorMacro(String color, String message){
		goToSelectAMacro("Formatting", "Color");
		type(ELEMENT_COLOR_TEXTBOX, color, true);
		type(ELEMENT_COLOR_MESSAGE, message, true);
		click(but.ELEMENT_CREATE_MACRO_BUTTON);
	}
	/**
	 * Create a Chart macro
	 * @param height
	 * @param params
	 * @param source
	 * @param title
	 * @param type
	 * @param content
	 * @param width
	 */
	public void createChartMacro(String height,String params, String source, String title, String type, String[] content, String width ){
		goToSelectAMacro("Content", "Chart");
		if(height!=null && height!="")
			type(ELEMENT_CHART_HEIGHT, height, true);
		if(width!=null && width!="")
			type(ELEMENT_CHART_WIDTH, width, true);

		type(ELEMENT_CHART_PARAMS,params, true);
		type(ELEMENT_CHART_SOURCE,source, true);
		type(ELEMENT_CHART_TITLE,title, true);
		type(ELEMENT_CHART_TYPE,type, true);
		for(int i=0; i < content.length; i++){
			type(ELEMENT_CHART_CONTENT,content[i],false);
			waitForAndGetElement(ELEMENT_CHART_CONTENT).sendKeys(Keys.ENTER);
		}
		click(but.ELEMENT_CREATE_MACRO_BUTTON);
	}

	/**
	 * Create an HTML macro
	 * @param clean
	 * @param wiki
	 * @param content
	 */
	public void createHTMLMacro(String clean, String wiki, String content){

		goToSelectAMacro("Development", "HTML");
		if(clean!=null)
			selectOption(ELEMENT_HTML_CLEAN, clean);
		if(wiki!=null)
			selectOption(ELEMENT_HTML_WIKI, wiki);
		type(ELEMENT_HTML_CONTENT,content, true);
		click(but.ELEMENT_CREATE_MACRO_BUTTON);
	}
	
	/**
	 * Create a formula macro
	 * @param fontsize
	 * @param imagetype
	 * @param content
	 */
	public void createformulaMacro(String fontsize, String imagetype, String content){

		goToSelectAMacro("Content", "Formula");
		if(fontsize!=null)
			selectOption(ELEMENT_FORMULA_FONTSIZE, fontsize);
		if(imagetype!=null)
			selectOption(ELEMENT_FORMULA_IMAGETYPE, imagetype);
		type(ELEMENT_FORMULA_CONTENT,content, true);
		click(but.ELEMENT_CREATE_MACRO_BUTTON);
	}
	
	/**
	 * Create a RSS macro
	 * @param content
	 * @param count
	 * @param decoration
	 * @param feed
	 * @param image
	 * @param width
	 */
	public void createRssMacro(String content, String count, String decoration, String feed, String image, String width ){

		goToSelectAMacro("Content", "RSS");
		if(content!=null)
			selectOption(ELEMEMT_RSS_CONTENT, content);
		type(ELEMEMT_RSS_COUNT,count,true);
		if(decoration!=null)
			selectOption(ELEMEMT_RSS_DECORATION, decoration);
		type(ELEMEMT_RSS_FEED,feed,true);
		if(image!=null)
			selectOption(ELEMEMT_RSS_IMAGE, image);
		type(ELEMEMT_RSS_WIDTH,width, true);
		click(but.ELEMENT_CREATE_MACRO_BUTTON);
	}


	/**
	 * Add table to a Wiki page
	 * 
	 * @param rows
	 * 			Number of rows that will be added in the table
	 * @param columns
	 * 			Number of columns that will be added in the table
	 */
	public void insertTable2WikiPage(String rows, String columns){
		mouseOverAndClick(ELEMENT_TABLE_LINK);
		mouseOverAndClick(ELEMENT_INSERT_TABLE_LINK);
		type(ELEMENT_ROW_TEXTBOX, rows, true);
		type(ELEMENT_COLUMN_TEXTBOX, columns, true);
		click(but.ELEMENT_INSERT_TABLE);
		waitForAndGetElement("//table");
	}

	/**
	 * Click End then Enter in content frame in Rich text mode of Wiki page editor
	 */
	public void typeEnterInRichText(){
		try {
			WebElement inputsummary = null;
			driver.switchTo().frame(waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME));
			inputsummary = driver.switchTo().activeElement();
			inputsummary.click();
			inputsummary.sendKeys(Keys.END);
			inputsummary.sendKeys(Keys.ENTER);
			switchToParentWindow();
			Utils.pause(1000);
			driver.switchTo().defaultContent();
		}catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			driver.switchTo().defaultContent();
			typeEnterInRichText();
		} catch (ElementNotVisibleException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			driver.switchTo().defaultContent();
			typeEnterInRichText();
		}catch (WebDriverException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			driver.switchTo().defaultContent();
			typeEnterInRichText();
		}
		finally {
			loopCount = 0;
		}
	}

	/**
	 * Add webpage link to a Wiki page
	 * 
	 * @param webpage
	 * 			web page that will be the target link
	 * @param label
	 * 			label of link that will be added into Wiki page
	 * @param tooltip
	 * 			
	 * @param opParam
	 * 			 verify - true: verify result
	 * 			 veriry - false: not verify result 
	 */
	public void insertwebpageLink2WikiPage(String webpage, String label, String tooltip,Object...opParam){
		Boolean verify =(Boolean)(opParam.length>0?opParam[0]:false);
		mouseOverAndClick(ELEMENT_LINK);
		mouseOverAndClick(ELEMENT_WEBPAGE_LINK);
		Utils.pause(500);
		info("Create link to the webpage " + webpage);
		if(webpage!=null && webpage!="")
			type(ELEMENT_WEBPAGE_TEXTBOX,webpage,true);
		if(label!=null && label!="")
			type(ELEMENT_LABEL_LINK_TEXTBOX,label,true);
		if(tooltip!=null && tooltip!="")
			type(ELEMENT_TOOLTIP_LINK_TEXTBOX,tooltip,true);
		Utils.pause(500);
		click(but.ELEMENT_CREATE_LINK_BUTTON);
		waitForElementNotPresent(but.ELEMENT_CREATE_LINK_BUTTON);	
		if(verify){
			WebElement e = waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME,DEFAULT_TIMEOUT,1,2);
			driver.switchTo().frame(e);
			if(label!=null && label!="")
				waitForAndGetElement(By.linkText(label));
			if(tooltip!=null && tooltip!="")
				waitForAndGetElement(By.xpath("//*[@title='"+tooltip+"']"));
			if(webpage!=null && webpage!="")
				waitForAndGetElement(By.xpath("//*[contains(@href,'"+webpage+"')]"));
			switchToParentWindow();
		}
	}

	/**
	 * Add email link to a Wiki page
	 * 
	 * @param email
	 * 			email address that will be the target link
	 * @param label
	 * 			label of link that will be added into Wiki page
	 * @param tooltip
	 * @param opParam
	 * 			 verify - true: verify result
	 * 			 veriry - false: not verify result 
	 */
	public void insertEmailLink2WikiPage(String email, String label, String tooltip, Object...opParam){
		Boolean verify =(Boolean)(opParam.length>0?opParam[0]:false);
		mouseOverAndClick(ELEMENT_LINK);
		mouseOverAndClick(ELEMENT_EMAIL_LINK);
		Utils.pause(500);
		info("Create link to the email " + email);
		type(ELEMENT_EMAIL_TEXTBOX, email, true);
		if(label!=null && label!="")
			type(ELEMENT_LABEL_LINK_TEXTBOX,label,true);
		if(tooltip!=null && tooltip!="")
			type(ELEMENT_TOOLTIP_LINK_TEXTBOX,tooltip,true);
		Utils.pause(500);
		click(but.ELEMENT_CREATE_LINK_BUTTON);
		waitForElementNotPresent(but.ELEMENT_CREATE_LINK_BUTTON);
		if(verify){
			WebElement e = waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME,DEFAULT_TIMEOUT,1,2);
			driver.switchTo().frame(e);
			if(label!=null && label!="")
				waitForAndGetElement(By.linkText(label));
			if(tooltip!=null && tooltip!="")
				waitForAndGetElement(By.xpath("//*[@title='"+tooltip+"']"));
			switchToParentWindow();
		}
	}

	/**
	 * edit a link (attach link, web page link, email link) on wiki: go to richtext mode -> Link -> Edit link
	 * @param file
	 * @param label
	 * @param tooltip
	 * @param webpage
	 * @param email
	 * @param opParam
	 * 			 verify - true: verify result
	 * 			 veriry - false: not verify result 
	 */
	public void editLink(String file, String label, String tooltip, String webpage, String email, Object...opParam){
		Boolean verify =(Boolean)(opParam.length>0?opParam[0]:false);
		mouseOverAndClick(ELEMENT_LINK);
		mouseOverAndClick(ELEMENT_EDIT_LINK);
		if(file!=null && file!=""){
			click(By.xpath("//*[text()='"+file+"']"));
			Utils.pause(500);
			click(but.ELEMENT_SELECT_BUTTON);
			waitForElementNotPresent(but.ELEMENT_SELECT_BUTTON);
		}
		if(email!=null && email!="")
			type(ELEMENT_EMAIL_TEXTBOX,email,true);
		if(webpage!=null && webpage!="")
			type(ELEMENT_WEBPAGE_TEXTBOX,webpage,true);
		if(label!=null && label!="")
			type(ELEMENT_LABEL_LINK_TEXTBOX,label,true);
		if(tooltip!=null && tooltip!="")
			type(ELEMENT_TOOLTIP_LINK_TEXTBOX,tooltip,true);
		Utils.pause(500);
		click(but.ELEMENT_CREATE_LINK_BUTTON);
		waitForElementNotPresent(but.ELEMENT_CREATE_LINK_BUTTON);	
		if(verify){
			WebElement e = waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME,DEFAULT_TIMEOUT,1,2);
			driver.switchTo().frame(e);
			if(label!=null && label!="")
				waitForAndGetElement(By.linkText(label));
			if(tooltip!=null && tooltip!="")
				waitForAndGetElement(By.xpath("//*[@title='"+tooltip+"']"));
			if(webpage!=null && webpage!="")
				waitForAndGetElement(By.xpath("//*[contains(@href,'"+webpage+"')]"));
			if(email!=null && email!="")
				waitForAndGetElement(By.xpath("//*[contains(@href,'"+webpage+"')]"));
			switchToParentWindow();
		}
	}

	/**
	 * add an image file to wiki
	 * @param file
	 * @param opParam
	 * 			 verify - true: verify result
	 * 			 veriry - false: not verify result 
	 */
	public void insertImageFile(String file, Object...opParam){
		Boolean verify =(Boolean)(opParam.length>0?opParam[0]:false);
		String path = Utils.getAbsoluteFilePath("TestData/"+file);
		mouseOverAndClick(ELEMENT_IMAGE_LINK);
		mouseOverAndClick(ELEMENT_IMAGE_LINK_ATTACH);
		Utils.pause(500);
		click(but.ELEMENT_SELECT_BUTTON);
		waitForElementNotPresent(but.ELEMENT_SELECT_BUTTON);
		click(ELEMENT_ATTACH_FILE_PATH);
		WebElement upload = waitForAndGetElement(ELEMENT_ATTACH_FILE_PATH, 5000, 1, 2);
		((JavascriptExecutor)driver).executeScript("arguments[0].style.visibility = 'visible'; arguments[0].style.height = '1px'; " +
				"arguments[0].style.width = '1px'; arguments[0].style.opacity = 1", upload);
		((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block';", upload);
		upload.sendKeys(path);

		Utils.pause(500);
		click(ELEMENT_IMAGE_INSERT_BUTTON);
		waitForElementNotPresent(ELEMENT_IMAGE_INSERT_BUTTON);	
		if(verify){
			WebElement e = waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME,DEFAULT_TIMEOUT,1,2);
			driver.switchTo().frame(e);
			String src = waitForAndGetElement(By.xpath("//*[@id='body']/img")).getAttribute("alt");
			assert src.contains(file);
			switchToParentWindow();
		}
	}

	/**
	 * Edit page link on wiki
	 * @param search
	 * @param page
	 * @param label
	 * @param tooltip
	 * @param opParam
	 * 			 verify - true: verify result
	 * 			 veriry - false: not verify result 
	 */
	public void editPageLink2WikiPage(boolean search, String page, String label, String tooltip,Object...opParam){
		Boolean verify =(Boolean)(opParam.length>0?opParam[0]:false);
		mouseOverAndClick(ELEMENT_LINK);
		mouseOverAndClick(ELEMENT_EDIT_LINK);
		Utils.pause(500);
		info("Create link to the page " + page);
		if (search){
			click(ELEMENT_SEARCH_TAB);
			type(ELEMENT_SEARCH_TEXTBOX, page, true);
			click(ELEMENT_SEARCH_BUTTON);
		}
		if(waitForAndGetElement(ELEMENT_PAGE_SELECTED.replace("${page}", page), 5000,0)!=null)
			click(ELEMENT_PAGE_SELECTED.replace("${page}", page));
		else
			click(ELEMENT_PAGE_SELECTED_PLF41.replace("${page}", page));
		click(but.ELEMENT_SELECT_BUTTON);
		if(label!=null && label!="")
			type(ELEMENT_LABEL_LINK_TEXTBOX,label,true);
		if(tooltip!=null && tooltip!="")
			type(ELEMENT_TOOLTIP_LINK_TEXTBOX,tooltip,true);
		Utils.pause(500);
		click(but.ELEMENT_CREATE_LINK_BUTTON);
		waitForElementNotPresent(but.ELEMENT_CREATE_LINK_BUTTON);
		if(verify){
			WebElement e = waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME,DEFAULT_TIMEOUT,1,2);
			driver.switchTo().frame(e);
			if(label!=null && label!="")
				waitForAndGetElement(By.linkText(label));
			if(tooltip!=null && tooltip!="")
				waitForAndGetElement(By.xpath("//*[@title='"+tooltip+"']"));
			switchToParentWindow();
		}
	}

	/**
	 * Edit image link on wiki
	 * @param file
	 * @param width
	 * @param height
	 * @param text
	 * @param alignment
	 * @param opParam
	 * 			 verify - true: verify result
	 * 			 veriry - false: not verify result 
	 */
	public void editImage(String file, String width, String height, String text, alignmentType alignment, Object...opParam){
		Boolean verify =(Boolean)(opParam.length>0?opParam[0]:false);
		mouseOverAndClick(ELEMENT_IMAGE_LINK);
		mouseOverAndClick(ELEMENT_IMAGE_EDIT_LINK);
		click(By.xpath("//*[@title='"+file+"']"));
		Utils.pause(500);
		click(but.ELEMENT_SELECT_BUTTON);
		waitForElementNotPresent(but.ELEMENT_SELECT_BUTTON);
		if(width!=null && width!="")
			type(ELEMENT_IMAGE_WIDTH,width,true);
		if(height!=null && height!="")
			type(ELEMENT_IMAGE_HEIGHT,height,true);
		if(text!=null && text!="")
			type(ELEMENT_IMAGE_ALTERNATIVE_TEXT,text,true);
		if(alignment!=null)
			click(By.xpath("//*[@value='"+String.valueOf(alignment)+"']"));
		click(ELEMENT_IMAGE_INSERT_BUTTON);
		waitForElementNotPresent(ELEMENT_IMAGE_INSERT_BUTTON);	
		if(verify){
			WebElement e = waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME,DEFAULT_TIMEOUT,1,2);
			driver.switchTo().frame(e);
			if(width!=null && width!=""){
				String widthImage = waitForAndGetElement(By.xpath("//*[@id='body']/img")).getAttribute("width");
				assert widthImage.equalsIgnoreCase(width);
			}
			if(height!=null && height!=""){
				String heightImage = waitForAndGetElement(By.xpath("//*[@id='body']/img")).getAttribute("height");
				assert heightImage.equalsIgnoreCase(height);
			}
			if(text!=null && text!=""){
				String alt = waitForAndGetElement(By.xpath("//*[@id='body']/img")).getAttribute("alt");
				assert alt.equalsIgnoreCase(text);
			}
			if(alignment!=null){
				String alignmentImage = waitForAndGetElement(By.xpath("//*[@id='body']/img")).getAttribute("style");
				assert alignmentImage.toLowerCase().contains(String.valueOf(alignment).toLowerCase());
			}
			switchToParentWindow();
		}
	}

	/**
	 * Define a alignment of image link 
	 * LEFT
	 * CENTER
	 * RIGHT 
	 * TOP
	 * MIDDLE
	 * BOTTOM 
	 */
	public enum alignmentType {
		LEFT, CENTER, RIGHT,TOP, MIDDLE, BOTTOM;
	}

	/**
	 * Add link to a new Wiki page
	 * 
	 * @param pageName
	 * 			pageName that will be the target link
	 * @param label
	 * 			label of link that will be added into Wiki page
	 * @param tooltip
	 * 			
	 * @param opParam
	 * 			 verify - true: verify result
	 * 			 veriry - false: not verify result 
	 */
	public void insertnewPageLink2WikiPage(String pageName, String label, String tooltip,Object...opParam){
		Boolean verify =(Boolean)(opParam.length>0?opParam[0]:false);
		mouseOverAndClick(ELEMENT_LINK);
		mouseOverAndClick(ELEMENT_WIKI_PAGE_LINK);
		Utils.pause(500);
		info("Create link to the webpage " + pageName);
		if(pageName!=null && pageName!=""){
			click(ELEMENT_ADD_NEW_PAGE_LINK);
			Utils.pause(500);
			click(button.ELEMENT_SELECT_BUTTON);
			Utils.pause(500);
			type(ELEMENT_ADD_NEW_LINKPAGE_TEXTBOX,pageName,true);
		}
		else
			click(button.ELEMENT_SELECT_BUTTON);
		Utils.pause(500);
		click(button.ELEMENT_SETTING_LINK_BUTTON);
		Utils.pause(500);
		if(label!=null && label!="")
			type(ELEMENT_LABEL_LINK_TEXTBOX,label,true);
		if(tooltip!=null && tooltip!="")
			type(ELEMENT_TOOLTIP_LINK_TEXTBOX,tooltip,true);
		Utils.pause(500);
		click(but.ELEMENT_CREATE_LINK_BUTTON);
		waitForElementNotPresent(but.ELEMENT_CREATE_LINK_BUTTON);	
		if(verify){
			WebElement e = waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME,DEFAULT_TIMEOUT,1,2);
			driver.switchTo().frame(e);
			if(label!=null && label!="")
				waitForAndGetElement(By.linkText(label));
			if(tooltip!=null && tooltip!="")
				waitForAndGetElement(By.xpath("//*[@title='"+tooltip+"']"));		
			switchToParentWindow();
		}
	}

	/**
	 * attach a file to wiki page
	 * @param file
	 * @param opParam
	 * 			 verify - true: verify result
	 * 			 veriry - false: not verify result 
	 */
	public void insertAttachFileExits(String file, String fileattach, String tooltip, Object...opParam){
		Boolean verify =(Boolean)(opParam.length>0?opParam[0]:false);
		mouseOverAndClick(ELEMENT_LINK);
		mouseOverAndClick(ELEMENT_ATTACH_FILE_LINK);
		Utils.pause(500);
		click(ELEMENT_ALL_PAGE_TAB);
		Utils.pause(1000);
		mouseOverAndClick(ELEMENT_ATTACH_FILE_CEL.replace("${file}", "WikiHome"));
		Utils.pause(500);
		mouseOverAndClick(ELEMENT_ATTACH_FILE_CEL.replace("${file}", file));
		Utils.pause(500);
		mouseOverAndClick(ELEMENT_ATTACH_FILE_CEL.replace("${file}", "Attachments"));
		Utils.pause(500);
		mouseOverAndClick(ELEMENT_ATTACH_FILE_CEL.replace("${file}", fileattach));
		Utils.pause(500);
		click(but.ELEMENT_SELECT_BUTTON);
		waitForElementNotPresent(but.ELEMENT_SELECT_BUTTON);
		if(tooltip!=null && tooltip!="")
			type(ELEMENT_TOOLTIP_LINK_TEXTBOX,tooltip,true);
		Utils.pause(500);
		Utils.pause(500);
		click(but.ELEMENT_CREATE_LINK_BUTTON);
		waitForElementNotPresent(but.ELEMENT_CREATE_LINK_BUTTON);	
		if(verify){
			WebElement e = waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME,DEFAULT_TIMEOUT,1,2);
			driver.switchTo().frame(e);
			waitForAndGetElement(By.linkText(fileattach));
			if(tooltip!=null && tooltip!="")
				waitForAndGetElement(By.xpath("//*[@title='"+tooltip+"']"));	
			switchToParentWindow();
		}
	}

	/**
	 * attach a file to wiki page
	 * @param file
	 * @param label
	 * 			label of link that will be added into Wiki page
	 * @param tooltip
	 * @param opParam
	 * 			 verify - true: verify result
	 * 			 veriry - false: not verify result 
	 */
	public void insertAttachNewFile(String file, String label, String tooltip,Object...opParam){
		Boolean verify =(Boolean)(opParam.length>0?opParam[0]:false);
		String path = Utils.getAbsoluteFilePath("TestData/"+file);
		mouseOverAndClick(ELEMENT_LINK);
		mouseOverAndClick(ELEMENT_ATTACH_FILE_LINK);
		Utils.pause(500);
		click(but.ELEMENT_SELECT_BUTTON);
		waitForElementNotPresent(but.ELEMENT_SELECT_BUTTON);
		click(ELEMENT_ATTACH_FILE_PATH);
		WebElement upload = waitForAndGetElement(ELEMENT_ATTACH_FILE_PATH, 8000, 1, 2);
		((JavascriptExecutor)driver).executeScript("arguments[0].style.visibility = 'visible'; arguments[0].style.height = '1px'; " +
				"arguments[0].style.width = '1px'; arguments[0].style.opacity = 1", upload);
		((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block';", upload);
		upload.sendKeys(path);
		Utils.pause(500);
		click(button.ELEMENT_SETTING_LINK_BUTTON);
		Utils.pause(500);
		if(label!=null && label!="")
			type(ELEMENT_LABEL_LINK_TEXTBOX,label,true);
		if(tooltip!=null && tooltip!="")
			type(ELEMENT_TOOLTIP_LINK_TEXTBOX,tooltip,true);
		Utils.pause(500);
		click(but.ELEMENT_CREATE_LINK_BUTTON);
		waitForElementNotPresent(but.ELEMENT_CREATE_LINK_BUTTON);	
		if(verify){
			WebElement e = waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME,DEFAULT_TIMEOUT,1,2);
			driver.switchTo().frame(e);
			if(label!=null && label!="")
				waitForAndGetElement(By.linkText(label));
			if(tooltip!=null && tooltip!="")
				waitForAndGetElement(By.xpath("//*[@title='"+tooltip+"']"));	
			switchToParentWindow();
		}
	}

	/**
	 * Add an image file to wiki
	 * @param imageLocation
	 * @param opParam
	 * 			 verify - true: verify result
	 * 			 veriry - false: not verify result 
	 */
	public void insertExternalImage(String imageLocation, String width, String height, String text, alignmentType alignment, Object...opParam){
		Boolean verify =(Boolean)(opParam.length>0?opParam[0]:false);
		mouseOverAndClick(ELEMENT_IMAGE_LINK);
		mouseOverAndClick(ELEMENT_IMAGE_EXTERNAL_LINK);
		Utils.pause(500);
		if(imageLocation!=null && imageLocation!="")
			type(ELEMENT_IMAGE_LOCATION,imageLocation,true);
		Utils.pause(500);
		click(ELEMENT_IMAGE_SETTING_BUTTON);
		info("input width and height");
		if(width!=null && width!="")
			type(ELEMENT_IMAGE_WIDTH,width,true);
		if(height!=null && height!="")
			type(ELEMENT_IMAGE_HEIGHT,height,true);
		if(text!=null && text!="")
			type(ELEMENT_IMAGE_ALTERNATIVE_TEXT,text,true);
		if(alignment!=null)
			click(By.xpath("//*[@value='"+String.valueOf(alignment)+"']"));
		click(ELEMENT_IMAGE_INSERT_BUTTON);
		waitForElementNotPresent(ELEMENT_IMAGE_INSERT_BUTTON);	
		if(verify){
			WebElement e = waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME,DEFAULT_TIMEOUT,1,2);
			driver.switchTo().frame(e);
			if(width!=null && width!=""){
				String widthImage = waitForAndGetElement(By.xpath("//*[@id='body']/img")).getAttribute("width");
				assert widthImage.equalsIgnoreCase(width);
			}
			if(text!=null && text!=""){
				String alt = waitForAndGetElement(By.xpath("//*[@id='body']/img")).getAttribute("alt");
				assert alt.equalsIgnoreCase(text);
			}
			if(alignment!=null){
				String alignmentImage = waitForAndGetElement(By.xpath("//*[@id='body']/img")).getAttribute("style");
				assert alignmentImage.toLowerCase().contains(String.valueOf(alignment).toLowerCase());
			}
			switchToParentWindow();
		}
	}	
}
