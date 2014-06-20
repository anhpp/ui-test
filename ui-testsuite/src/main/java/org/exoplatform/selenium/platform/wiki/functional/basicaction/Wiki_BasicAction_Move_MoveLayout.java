package org.exoplatform.selenium.platform.wiki.functional.basicaction;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.social.SpaceManagement;
import org.exoplatform.selenium.platform.wiki.BasicAction;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Wiki_BasicAction_Move_MoveLayout extends BasicAction {
	ManageAccount magAcc;
	Button button;
	ManageMember magMember;

	//Space
	SpaceManagement spaceMag;
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		magAcc = new ManageAccount(driver);
		button = new Button(driver);
		magAcc.signIn(DATA_USER1,DATA_PASS);;
		spaceMag = new SpaceManagement(driver);

	}
	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	/**
	 *Case ID:79542.
	 *Test Case Name: Move Layout is displaying space switcher
	 *Case ID:79544.
	 *Test Case Name: Selecting a space with the space switcher should feed the destination container.
	 *Pre-Condition: User is member of space : "Space Move", "Space Destination"Wiki of "Space Move" contains a page "Page A"
	 *Post-Condition: 
	 *Done with OSs and browsers : 
	 *Generated by hantv at 2014/03/24 17:29:36
	 */
	@Test
	public  void test02_03_SelectingASpaceWithTheSpaceSwitcherShouldFeedTheDestinationContainer() {
		String page_name1 = "pagetomove79544";
		String spacemove = "Movespace79544";
		String spacedest = "Destspace79544";
		String page_name2 = "pagetodest";
		
		spaceMag.goToMySpacePage();
		spaceMag.addNewSpace(spacedest, "");
		goToWikiFromSpace(spacedest);
		addBlankWikiPage(page_name2, page_name2, 0);

		waitForAndGetElement(By.linkText(spacedest));
		spaceMag.goToMySpacePage();
		spaceMag.addNewSpace(spacemove, "");
		waitForAndGetElement(By.linkText(spacemove));
		goToWikiFromSpace(spacemove);
		addBlankWikiPage(page_name1, page_name1, 0);
		
		info("Test 2: Move Layout is displaying space switcher - 79542");
		click(By.linkText(page_name1));
		goToMovePage();
		waitForAndGetElement(ELEMENT_DESTINATION_TREE_ITEM.replace("${treeItem}", page_name1));
		click(ELEMENT_SELECT_SPACE_DESTINATION);
		waitForAndGetElement(ELEMENT_DESTINATION_LIST_SCROLLBAR);
		
		info("Test 3: Selecting a space with the space switcher should feed the destination container - 79544");
		click(ELEMENT_SPACE_NAME_SELECTED.replace("${space}", spacedest));
		waitForAndGetElement(ELEMENT_DESTINATION_TREE_ITEM.replace("${treeItem}", page_name2));
		
		/*Clear data*/
		spaceMag.goToAllSpaces();
		spaceMag.deleteSpace(spacemove,300000);
		spaceMag.deleteSpace(spacedest,300000);

	}

	/**
	 *Case ID: 79543
	 *Test Case Name: Move Layout is displaying "Select the desination:" label
	 *Case ID:79545.
	 *Test Case Name: Not selecting a space with the space switcher should have no impact on the destination container.
	 *Case ID:79546.
	 *Test Case Name: Space switcher must show the currently browsed space.
	 *Case ID:79574.
	 *Test Case Name: Move layout should display destination labels outside destination container.
	 *Pre-Condition: 
	 *Post-Condition: 
	 *Done with OSs and browsers : 
	 *Generated by hantv at 2014/03/24 17:29:36
	 */
	@Test
	public  void test01_04_05_06_MoveLayoutShouldDisplayDestinationLabelsOutsideDestinationContainer() {
		String pagename1 = "page79574";
		String spacemove = "Movespace79574";

		spaceMag.goToMySpacePage();
		spaceMag.addNewSpace(spacemove, "");
		waitForAndGetElement(By.linkText(spacemove));
		goToWikiFromSpace(spacemove);
		addBlankWikiPage(pagename1, pagename1, 0);
		click(By.linkText(pagename1));
		goToMovePage();
		
		info("Test 5: Space switcher must show the currently browsed space - 79546");
		waitForAndGetElement(ELEMENT_MOVE_PAGE_CURRENT_DESTINATION.replace("{$item}", spacemove));
		
		info("Test 4: Not selecting a space with the space switcher should have no impact on the destination container - 79545");
		click(ELEMENT_SELECT_SPACE_DESTINATION);
		waitForAndGetElement(ELEMENT_DESTINATION_LIST_SCROLLBAR);
		click(ELEMENT_MOVE_PAGE_POPUP_DESTINATION_LABEL);
		waitForAndGetElement(ELEMENT_MOVE_PAGE_CURRENT_DESTINATION.replace("{$item}", spacemove));
		waitForElementNotPresent(ELEMENT_DESTINATION_LIST_SCROLLBAR);
		
		info("Test 6: Move layout should display destination labels outside destination container - 79574");
		info("Test 1: Move Layout is displaying Select the desination label - 79543");
		waitForAndGetElement(ELEMENT_MOVE_PAGE_POPUP_DESTINATION_LABEL);
		waitForAndGetElement(ELEMENT_MOVE_PAGE_POPUP_UI_MOVETREE);

		/*Clear data*/
		spaceMag.goToAllSpaces();
		spaceMag.deleteSpace(spacemove,300000);
	}
}