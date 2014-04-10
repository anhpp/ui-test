package org.exoplatform.selenium.platform.wiki.functional.search;
/**
 * Generated by khanhnt at 2014/01/17 08:41:09
 *
 * Copyright (C) 2009 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.wiki.BasicAction;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

/**
 * @author khanhnt
 *
 */
public class Wiki_Space_Switcher extends BasicAction{
	ManageAccount magAc;
	ManageMember magMember;

	//@Parameters({ "platform","browser","version", "url" })
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();//true,platform,browser,version,url);
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		magMember = new ManageMember(driver);
		magAc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterTest(){
		//signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Case ID:79640.
	 * Test Case Name: When user is member of 0 Space, space switcher should not display spaces list and input text.
	 * Pre-Condition: User is member of 0 space
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by khanhnt at 2014/01/17 08:41:09
	 */
	@Test
	public  void test01_WhenUserIsMemberOf0SpaceSpaceSwitcherShouldNotDisplaySpacesListAndInputText() {
		info("Test 1: When user is member of 0 Space, space switcher should not display spaces list and input text");
		/*Step Number: 1
		 *Step Name: 
		 *Step Description: 
				- Go to Company wiki
		 *Input Data: 

		 *Expected Outcome: 
				- Company wiki is displayed
				- Breadcrumb is displaying Space Switcher*/
		goToWiki();
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		/*Step number: 2
		 *Step Name: 
		 *Step Description: 
				- Open the Space Switcher
		 *Input Data: 

		 *Expected Outcome: 
				- Input text and space list are not displayed in the space switcher list*/ 
		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		waitForAndGetElement(ELEMENT_NO_SPACE_OPTION);
		waitForElementNotPresent(ELEMENT_SPACE_SWITCHER_INPUT);
	}

	/**
	 * Case ID:79641.
	 * Test Case Name: When user is member of at least one space, input text should be displayed.
	 * Pre-Condition: User is member of space "Mobile"
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by khanhnt at 2014/01/17 08:41:09
	 */
	@Test
	public  void test02_WhenUserIsMemberOfAtLeastOneSpaceInputTextShouldBeDisplayed() {
		info("Test 2: When user is member of at least one space, input text should be displayed");
		String spaceName = "Mobiles";
		magMember.goToMySpacePage();	
		magMember.addNewSpace(spaceName, "");
		/*Step Number: 1
		 *Step Name: 
		 *Step Description: 
				- Go to Company wiki
		 *Input Data: 

		 *Expected Outcome: 
				- Company wiki is displayed
				- Breadcrumb is displaying Space Switcher*/
		goToWiki();
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		/*Step number: 2
		 *Step Name: 
		 *Step Description: 
				- Open the Space Switcher
		 *Input Data: 

		 *Expected Outcome: 
				- The first item of the list is an input text field*/ 
		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_INPUT);

		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName,300000);
	}

	/**
	 * Case ID:79647, 79648.
	 * Test Case Name: Search should start on first characters inputed and When no results search result, space switcher should display "No Spaces".
	 * Pre-Condition: User is member of : Mobile, Space 1, Space 2, Space 3, Space 4, Space 5, Space 6, Space 7, Space 8, Space 9, Space 10, Space 11, "Long title for a space name 30"
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by khanhnt at 2014/01/17 08:41:09
	 */
	@Test
	public  void test03_04_SearchShouldStartOnFirstCharactersInputedAndDisplayNoSpaces() {
		info("Test 3: Search should start on first characters inputed");
		String mobileSpace = "Mobile";
		String longNameSpace = "Long title for a space name 30";
		String spacePrefix = "Space ";
		magMember.goToMySpacePage();
		magMember.addNewSpace(mobileSpace, "");
		magMember.goToMySpacePage();
		magMember.addNewSpace(longNameSpace, "");
		for (int i = 1; i < 12; i++) {
			magMember.goToMySpacePage();
			magMember.addNewSpace(spacePrefix+i, "");
		}
		/*Step Number: 1
		 *Step Name: 
		 *Step Description: 
				- Using left sidebar navigation, go into the wiki
		 *Input Data: 

		 *Expected Outcome: 
				- Wiki is displayed
				- Breadcrumb is displayed with space switcher component*/
		goToWiki();
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		/*Step number: 2
		 *Step Name: 
		 *Step Description: 
				- Open the Space switcher component
		 *Input Data: 

		 *Expected Outcome: 
				- Input text is displayed*/
		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		/*Step number: 3
		 *Step Name: 
		 *Step Description: 
				- Input "x" in the input text
		 *Input Data: 

		 *Expected Outcome: 
				- List of spaces is updated and displaying "No Spaces"*/ 
		type(ELEMENT_SPACE_SWITCHER_INPUT,"x",false);
		waitForAndGetElement(ELEMENT_NO_SPACE_OPTION);

		//resote data
		magMember.goToMySpacePage();	
		magMember.deleteSpace(mobileSpace,300000);
		magMember.deleteSpace("Long title for ...", 300000);
		for (int i = 1; i < 12; i++) {
			magMember.deleteSpace(spacePrefix+i, 300000);
		}

	}

	/**
	 * Case ID:79649.
	 * Test Case Name: Search is working as a filter in the list of spaces.
	 * Pre-Condition: User is member of : Mobile, Space 1, Space 2, Space 3, Space 4, Space 5, Space 6, Space 7, Space 8, Space 9, Space 10, Space 11, "Long title for a space name 30"
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by khanhnt at 2014/01/17 08:41:09
	 */
	@Test
	public  void test05_SearchIsWorkingAsAFilterInTheListOfSpaces() {
		info("Test 5: Search is working as a filter in the list of spaces");
		String mobileSpace = "Mobile79649";
		String longNameSpace = "Long title for a space name 31";
		String spacePrefix = "Space79649 ";
		magMember.goToMySpacePage();	
		magMember.addNewSpace(mobileSpace, "");
		magMember.goToMySpacePage();
		magMember.addNewSpace(longNameSpace, "");
		for (int i = 1; i < 12; i++) {
			magMember.goToMySpacePage();
			magMember.addNewSpace(spacePrefix+i, "");
		}
		/*Step Number: 1
		 *Step Name: 
		 *Step Description: 
				- Using left sidebar navigation, go into the wiki
		 *Input Data: 

		 *Expected Outcome: 
				- Wiki is displayed
				- Breadcrumb is displayed with space switcher component*/
		goToWiki();
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);

		/*Step number: 2
		 *Step Name: 
		 *Step Description: 
				- Open the Space switcher component
		 *Input Data: 

		 *Expected Outcome: 
				- Input text is displayed*/

		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		/*Step number: 3
		 *Step Name: 
		 *Step Description: 
				- Input "Mo" in the input text
		 *Input Data: 

		 *Expected Outcome: 
				- List of spaces is updated and displaying :* Mobile*/
		type(ELEMENT_SPACE_SWITCHER_INPUT,"Mo",false);
		waitForAndGetElement(ELEMENT_SPACE_NAME_SELECTED.replace("${space}", mobileSpace.toLowerCase()));
		/*Step number: 4
		 *Step Name: 
		 *Step Description: 
				- Delete "Mo" from input text
		 *Input Data: 

		 *Expected Outcome: 
				- List of last browsed space is displayed again*/
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_INPUT_FOCUS).clear();
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_INPUT_FOCUS).click();
		type(ELEMENT_SPACE_SWITCHER_INPUT_FOCUS," ",false);
		waitForAndGetElement(ELEMENT_SPACE_NAME_SELECTED.replace("${space}", mobileSpace.toLowerCase()));
		waitForAndGetElement(ELEMENT_SPACE_NAME_SELECTED.replace("${space}", longNameSpace.toLowerCase().replace(" ", "_")));
		for (int i = 1; i < 12; i++) {
			waitForAndGetElement(ELEMENT_SPACE_NAME_SELECTED.replace("${space}", (spacePrefix+i).toLowerCase().replace(" ", "_")));
		}
		/*Step number: 5
		 *Step Name: 
		 *Step Description: 
				- Input "bile"
		 *Input Data: 

		 *Expected Outcome: 
				- List of sapces is updated and displaying : * Mobile*/ 
		type(ELEMENT_SPACE_SWITCHER_INPUT_FOCUS,"bile",false);
		waitForAndGetElement(ELEMENT_SPACE_NAME_SELECTED.replace("${space}", mobileSpace.toLowerCase()));

		//resote data
		magMember.goToMySpacePage();	
		magMember.deleteSpace(mobileSpace,300000);
		magMember.deleteSpace("Long title for ...", 300000);
		for (int i = 1; i < 12; i++) {
			magMember.deleteSpace(spacePrefix+i, 300000);
		}
	}


	/**
	 * Case ID:79650.
	 * Test Case Name: Search is not case sensitive.
	 * Pre-Condition: User is member of : Mobile, Space 1, Space 2, Space 3, Space 4, Space 5, Space 6, Space 7, Space 8, Space 9, Space 10, Space 11, "Long title for a space name 30"
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by khanhnt at 2014/01/17 08:41:09
	 */
	@Test
	public  void test06_SearchIsNotCaseSensitive() {
		info("Test 6: Search is not case sensitive");
		String mobileSpace = "Mobile79650";
		String longNameSpace = "Long title for a space name 32";
		String spacePrefix = "Space79650 ";
		magMember.goToMySpacePage();	
		magMember.addNewSpace(mobileSpace, "");
		magMember.goToMySpacePage();
		magMember.addNewSpace(longNameSpace, "");
		for (int i = 1; i < 12; i++) {
			magMember.goToMySpacePage();
			magMember.addNewSpace(spacePrefix+i, "");
		}
		/*Step Number: 1
		 *Step Name: 
		 *Step Description: 
				- Using left sidebar navigation, go into the wiki
		 *Input Data: 

		 *Expected Outcome: 
				- Wiki is displayed
				- Breadcrumb is displayed with space switcher component*/
		goToWiki();
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);

		/*Step number: 2
		 *Step Name: 
		 *Step Description: 
				- Open the Space switcher component
		 *Input Data: 

		 *Expected Outcome: 
				- Input text is displayed*/
		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);

		/*Step number: 3
		 *Step Name: 
		 *Step Description: 
				- Input "mob" in the input text
		 *Input Data: 

		 *Expected Outcome: 
				- List of spaces is updated and displaying :* Mobile*/ 
		type(ELEMENT_SPACE_SWITCHER_INPUT,"mo",false);
		waitForAndGetElement(ELEMENT_SPACE_NAME_SELECTED.replace("${space}", mobileSpace.toLowerCase()));

		//resote data
		magMember.goToMySpacePage();	
		magMember.deleteSpace(mobileSpace,300000);
		magMember.deleteSpace("Long title for ...", 300000);
		for (int i = 1; i < 12; i++) {
			magMember.deleteSpace(spacePrefix+i, 300000);
		}
	}

	/**
	 * Case ID:79651.
	 * Test Case Name: Search doesn't take into account order of words.
	 * Pre-Condition: User is member of : Mobile, Space 1, Space 2, Space 3, Space 4, Space 5, Space 6, Space 7, Space 8, Space 9, Space 10, Space 11, "Long title for a space name 30"
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by khanhnt at 2014/01/17 08:41:09
	 */
	@Test
	public  void test07_SearchDoesntTakeIntoAccountOrderOfWords() {
		info("Test 7: Search doesn't take into account order of words");
		String mobileSpace = "Mobile79651";
		String longNameSpace = "Long title for a space name 33";
		String spacePrefix = "Space79651 ";
		magMember.goToMySpacePage();	
		magMember.addNewSpace(mobileSpace, "");
		magMember.goToMySpacePage();
		magMember.addNewSpace(longNameSpace, "");
		for (int i = 1; i < 12; i++) {
			magMember.goToMySpacePage();
			magMember.addNewSpace(spacePrefix+i, "");
		}
		/*Step Number: 1
		 *Step Name: 
		 *Step Description: 
				- Using left sidebar navigation, go into the wiki
		 *Input Data: 

		 *Expected Outcome: 
				- Wiki is displayed
				- Breadcrumb is displayed with space switcher component*/

		goToWiki();
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		/*Step number: 2
		 *Step Name: 
		 *Step Description: 
				- Open the Space switcher component
		 *Input Data: 

		 *Expected Outcome: 
				- Input text is displayed*/
		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);

		/*Step number: 3
		 *Step Name: 
		 *Step Description: 
				- Input "title" in the input text
		 *Input Data: 

		 *Expected Outcome: 
				- List of spaces is updated and displaying :* "Long title for a space name 30"*/ 
		type(ELEMENT_SPACE_SWITCHER_INPUT,"title",false);
		waitForAndGetElement(ELEMENT_SPACE_NAME_SELECTED.replace("${space}", longNameSpace.toLowerCase().replace(" ", "_")));

		//resote data
		magMember.goToMySpacePage();	
		magMember.deleteSpace(mobileSpace,300000);
		magMember.deleteSpace("Long title for ...", 300000);
		for (int i = 1; i < 12; i++) {
			magMember.deleteSpace(spacePrefix+i, 300000);
		}
	}

	/**
	 * Case ID:79652.
	 * Test Case Name: Search - Space switcher is scrollable when there are more than 10 results.
	 * Pre-Condition: User is member of : Mobile, Space 1, Space 2, Space 3, Space 4, Space 5, Space 6, Space 7, Space 8, Space 9, Space 10, Space 11, "Long title for a space name 30"
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by khanhnt at 2014/01/17 08:41:09
	 */
	@Test
	public  void test08_SearchSpaceSwitcherIsScrollableWhenThereAreMoreThan10Results() {
		info("Test 8: Search - Space switcher is scrollable when there are more than 10 results");
		String mobileSpace = "Mobile79652";
		String longNameSpace = "Long title for a space name 34";
		String spacePrefix = "Space79652 ";
		magMember.goToMySpacePage();	
		magMember.addNewSpace(mobileSpace, "");
		magMember.goToMySpacePage();
		magMember.addNewSpace(longNameSpace, "");
		for (int i = 1; i < 12; i++) {
			magMember.goToMySpacePage();
			magMember.addNewSpace(spacePrefix+i, "");
		}
		/*Step Number: 1
		 *Step Name: 
		 *Step Description: 
				- Using left sidebar navigation, go into the wiki
		 *Input Data: 

		 *Expected Outcome: 
				- Wiki is displayed
				- Breadcrumb is displayed with space switcher component*/
		goToWiki();
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);

		/*Step number: 2
		 *Step Name: 
		 *Step Description: 
				- Open the Space switcher component
		 *Input Data: 

		 *Expected Outcome: 
				- Input text is displayed*/
		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);

		/*Step number: 3
		 *Step Name: 
		 *Step Description: 
				- Input "Spac" in the input text
		 *Input Data: 

		 *Expected Outcome: 
				- List of spaces is updated and displaying :* Space 1* Space 2* Space 3* Space 4* Space 5* Space 6* Space 7* Space 8* Space 9* Space 10User can scroll down to see* Space 11* Long title for a space name 30*/ 
		type(ELEMENT_SPACE_SWITCHER_INPUT,"Spac",false);
		waitForAndGetElement(ELEMENT_SPACE_NAME_SELECTED.replace("${space}", longNameSpace.toLowerCase().replace(" ", "_")));
		for (int i = 1; i < 12; i++) {
			waitForAndGetElement(ELEMENT_SPACE_NAME_SELECTED.replace("${space}", (spacePrefix+i).toLowerCase().replace(" ", "_")));
		}

		WebElement spaceList = waitForAndGetElement(By.className("spaceList"));

		String str1 = String.valueOf(((JavascriptExecutor)driver).executeScript("return arguments[0].clientHeight;", spaceList));
		String str = String.valueOf(((JavascriptExecutor)driver).executeScript("return arguments[0].scrollHeight;", spaceList));
		int clientHeight = Integer.parseInt(str1);
		int scrollHeight = Integer.parseInt(str);
		assert clientHeight<scrollHeight;

		//resote data
		magMember.goToMySpacePage();	
		magMember.deleteSpace(mobileSpace,300000);
		magMember.deleteSpace("Long title for ...", 300000);
		for (int i = 1; i < 12; i++) {
			magMember.deleteSpace(spacePrefix+i, 300000);
		}
	}

	/**
	 * Case ID:79658.
	 * Test Case Name: Search should not take into account spaces at the end of a text inputted.
	 * Pre-Condition: User is member of : Mobile, Space 1, Space 2, Space 3, Space 4, Space 5, Space 6, Space 7, Space 8, Space 9, Space 10, Space 11, "Long title for a space name 30"
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by khanhnt at 2014/01/17 08:41:09
	 */
	@Test
	public  void test09_SearchShouldNotTakeIntoAccountSpacesAtTheEndOfATextInputted() {
		info("Test 9: Search should not take into account spaces at the end of a text inputted");
		String mobileSpace = "Mobile79658";
		String longNameSpace = "Long title for a space name 35";
		String spacePrefix = "Space79658 ";
		magMember.goToMySpacePage();	
		magMember.addNewSpace(mobileSpace, "");
		magMember.goToMySpacePage();
		magMember.addNewSpace(longNameSpace, "");
		for (int i = 1; i < 12; i++) {
			magMember.goToMySpacePage();
			magMember.addNewSpace(spacePrefix+i, "");
		}
		/*Step Number: 1
		 *Step Name: 
		 *Step Description: 
				- Using left sidebar navigation, go into the wiki
		 *Input Data: 

		 *Expected Outcome: 
				- Wiki is displayed
				- Breadcrumb is displayed with space switcher component*/
		goToWiki();
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);

		/*Step number: 2
		 *Step Name: 
		 *Step Description: 
				- Open the Space switcher component
		 *Input Data: 

		 *Expected Outcome: 
				- Input text is displayed*/
		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);

		/*Step number: 3
		 *Step Name: 
		 *Step Description: 
				- Input "Mo" (with 2 spaces at the end) in the input text
		 *Input Data: 

		 *Expected Outcome: 
				- List of spaces is updated and displaying :* Mobile*/ 
		type(ELEMENT_SPACE_SWITCHER_INPUT,"Mo  ",false);
		waitForAndGetElement(ELEMENT_SPACE_NAME_SELECTED.replace("${space}", mobileSpace.toLowerCase()));

		//resote data
		magMember.goToMySpacePage();	
		magMember.deleteSpace(mobileSpace,300000);
		magMember.deleteSpace("Long title for ...", 300000);
		for (int i = 1; i < 12; i++) {
			magMember.deleteSpace(spacePrefix+i, 300000);
		}
	}

	/**
	 * Case ID:79659.
	 * Test Case Name: Search should take into account spaces in the middle of text inputted.
	 * Pre-Condition: User is member of : Mobile, Space 1, Space 2, Space 3, Space 4, Space 5, Space 6, Space 7, Space 8, Space 9, Space 10, Space 11, "Long title for a space name 30"
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by khanhnt at 2014/01/17 08:41:09
	 */
	@Test
	public  void test10_SearchShouldTakeIntoAccountSpacesInTheMiddleOfTextInputted() {
		info("Test 10 Search should take into account spaces in the middle of text inputted");
		String mobileSpace = "Mobile79659";
		String longNameSpace = "Long title for a space name 36";
		String spacePrefix = "Space79659 ";
		magMember.goToMySpacePage();	
		magMember.addNewSpace(mobileSpace, "");
		magMember.goToMySpacePage();
		magMember.addNewSpace(longNameSpace, "");
		for (int i = 1; i < 12; i++) {
			magMember.goToMySpacePage();
			magMember.addNewSpace(spacePrefix+i, "");
		}
		/*Step Number: 1
		 *Step Name: 
		 *Step Description: 
				- Using left sidebar navigation, go into the wiki
		 *Input Data: 

		 *Expected Outcome: 
				- Wiki is displayed
				- Breadcrumb is displayed with space switcher component*/
		goToWiki();
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);

		/*Step number: 2
		 *Step Name: 
		 *Step Description: 
				- Open the Space switcher component
		 *Input Data: 

		 *Expected Outcome: 
				- Input text is displayed*/

		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		/*Step number: 3
		 *Step Name: 
		 *Step Description: 
				- Input "Long title" iin the input text
		 *Input Data: 

		 *Expected Outcome: 
				- List of spaces is updated and displaying :* Long title for a space name 30*/
		type(ELEMENT_SPACE_SWITCHER_INPUT,"Long title",false);
		waitForAndGetElement(ELEMENT_SPACE_NAME_SELECTED.replace("${space}", longNameSpace.toLowerCase().replace(" ", "_")));
		/*Step number: 4
		 *Step Name: 
		 *Step Description: 
				- Remove "Long title" from the input text
		 *Input Data: 

		 *Expected Outcome: 
				- List of last browsed spaces is displayed again*/
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_INPUT_FOCUS).clear();
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_INPUT_FOCUS).click();
		type(ELEMENT_SPACE_SWITCHER_INPUT_FOCUS," ",false);
		waitForAndGetElement(ELEMENT_SPACE_NAME_SELECTED.replace("${space}", mobileSpace.toLowerCase()));
		waitForAndGetElement(ELEMENT_SPACE_NAME_SELECTED.replace("${space}", longNameSpace.toLowerCase().replace(" ", "_")));
		for (int i = 1; i < 12; i++) {
			waitForAndGetElement(ELEMENT_SPACE_NAME_SELECTED.replace("${space}", (spacePrefix+i).toLowerCase().replace(" ", "_")));
		}
		/*Step number: 5
		 *Step Name: 
		 *Step Description: 
				- Input "Longtitle" (with 2 spaces between "Long" and "title") in the input text
		 *Input Data: 

		 *Expected Outcome: 
				Space switcher is displaying :
				- "No Spaces"*/ 
		type(ELEMENT_SPACE_SWITCHER_INPUT_FOCUS,"Longtitle ",false);
		waitForAndGetElement(ELEMENT_NO_SPACE_OPTION);
		type(ELEMENT_SPACE_SWITCHER_INPUT_FOCUS,"Long  title",false);
		waitForAndGetElement(ELEMENT_NO_SPACE_OPTION);
		//resote data
		magMember.goToMySpacePage();	
		magMember.deleteSpace(mobileSpace,300000);
		magMember.deleteSpace("Long title for ...", 300000);
		for (int i = 1; i < 12; i++) {
			magMember.deleteSpace(spacePrefix+i, 300000);
		}
	}
}