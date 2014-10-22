package org.exoplatform.selenium.platform;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.TestBase;
import org.exoplatform.selenium.platform.wiki.WikiHome;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends TestBase{
	WikiHome wHome;

	//Left panel
	public final By ELEMENT_WIKI_LINK_PLF41=By.xpath("//*[@data-original-title='Wiki']");
	/**
	 * constructor
	 * @param dr
	 */
	public HomePage(WebDriver dr){
		this.driver=dr;
		wHome = new WikiHome(dr);
	}
	/**
	 * Go to Wiki portlet
	 */
	public void goToWiki(){
		info("--Go to Wiki--");
		click(ELEMENT_WIKI_LINK_PLF41);
		waitForAndGetElement(wHome.ELEMENT_WIKI_HOME_PAGE_LINK);	
	}
}
