package org.exoplatform.selenium.platform;
import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
/**
 * 
 * @author phuongdt
 * @date 25/10/2013
 *
 */
public class SearchAdministration extends PlatformBase {

	//Enable or Disable button of content type
	public final String ELEMENT_CONTENT_TYPE_ENABLE_DISABLE_BUTTON = "//tr/td[text()='${contentType}']/..//input[@value='${action}']";
	public final String ELEMENT_CONTENT_TYPE_ITEM_SEARCH = "//*[@id='lstContentTypes']/li[contains(.,'${contentType}')]";

	SettingSearchPage qsPage;
	public SearchAdministration(WebDriver dr){
		driver = dr;
		qsPage = new SettingSearchPage(driver);
	}

	/** Enable a content type
	 * @author phuongdt
	 * @param contentType
	 */
	public void enableContentTypeSearch(String contentType){
		info("-- Enable a content type --");
		if(waitForAndGetElement(ELEMENT_CONTENT_TYPE_ENABLE_DISABLE_BUTTON.replace("${contentType}", contentType).replace("${action}", "Enable"),DEFAULT_TIMEOUT,0)!=null)
			clickByJavascript(By.xpath(ELEMENT_CONTENT_TYPE_ENABLE_DISABLE_BUTTON.replace("${contentType}", contentType).replace("${action}", "Enable")));
		waitForAndGetElement(ELEMENT_CONTENT_TYPE_ENABLE_DISABLE_BUTTON.replace("${contentType}", contentType).replace("${action}", "Disable"));
		Utils.pause(1000);
		qsPage.quickSearch(contentType);
		waitForAndGetElement(ELEMENT_CONTENT_TYPE_ITEM_SEARCH.replace("${contentType}", contentType));
	}

	/** Disable a content type
	 * @author phuongdt
	 * @param contentType
	 */
	public void disableContentTypeSearch(String contentType){
		info("-- Disable a content type --");
		if(waitForAndGetElement(ELEMENT_CONTENT_TYPE_ENABLE_DISABLE_BUTTON.replace("${contentType}", contentType).replace("${action}", "Disable"),DEFAULT_TIMEOUT,0)!=null)
			click(By.xpath(ELEMENT_CONTENT_TYPE_ENABLE_DISABLE_BUTTON.replace("${contentType}", contentType).replace("${action}", "Disable")));
		waitForAndGetElement(ELEMENT_CONTENT_TYPE_ENABLE_DISABLE_BUTTON.replace("${contentType}", contentType).replace("${action}", "Enable"));
		Utils.pause(1000);
		qsPage.quickSearch(contentType);
		waitForElementNotPresent(ELEMENT_CONTENT_TYPE_ITEM_SEARCH.replace("${contentType}", contentType));
	}

}
