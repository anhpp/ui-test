package org.exoplatform.selenium.platform.forum.functional.forum.post;
import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.forum.ForumBase;
import org.exoplatform.selenium.platform.forum.ForumManageCategory;
import org.exoplatform.selenium.platform.forum.ForumManageForum;
import org.exoplatform.selenium.platform.forum.ForumManagePost;
import org.exoplatform.selenium.platform.forum.ForumManageTopic;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
* @author phuongdt
*
*/
public class Forum_Forum_Post_PostCounter extends ForumBase{
	ManageAccount magAc;
	ForumManageCategory mngCat;
	ForumManageForum mngFru;
	ForumManageTopic mngTopic;
	ForumManagePost mngPost;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		magAc = new ManageAccount(driver);
		mngCat = new ForumManageCategory(driver, this.plfVersion);
		mngFru = new ForumManageForum(driver, this.plfVersion);
		mngTopic = new ForumManageTopic(driver, this.plfVersion);
		mngPost = new ForumManagePost(driver, this.plfVersion);
		button = new Button(driver);

		magAc.signIn(DATA_USER1, DATA_PASS);
		goToForums();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	/**
	* Case ID:106656.
	* Test Case Name: View post counter.
	* Pre-Condition: 
	* Post-Condition: 
	* Done with OSs and browsers : 
	* Generated by phuongdt at 2014/01/22 10:24:54
	*/
	@Test
	public  void test01_ViewPostCounter() {
		String titleCat = "Category 106656";
		String titleForum = "Forum 106656";
		String titleTop = "Topic 106656";
		String post1 = "Post 1066561";
		String post2 = "Post 1066562";
		String postProfile;
		Integer oldPostNum;
		Integer newPostNum;
		
		//Check data before testing
		goToUserManagement("demo");
		click(ELEMENT_VIEW_USER_PROFILE_ICON.replace("${user}", "demo"));
		postProfile = waitForAndGetElement(ELEMENT_USER_NUMBER_POST).getText();
		oldPostNum = Integer.valueOf(postProfile.substring(postProfile.indexOf(":")+1).trim());
		click(ELEMENT_CLOSE_USER_PROFILE_BUTTON);
		button.close();
		
		info("Test 01 View post counter");
		/*Step Number: 1
		*Step Name: Step 1: Create category, forum, topic
		*Step Description: 
			- Login as root to create new category, forum, topic
		*Input Data: 
			
		*Expected Outcome: 
			- Category, forum, topic are created successfully*/
		mngTopic.addCategoryForumTopic(titleCat, titleForum, titleTop,titleTop); 

		/*Step number: 2
		*Step Name: Step 2: Add posts
		*Step Description: 
			- Login as another person, eg demo then add some posts, eg 2 posts
		*Input Data: 
			
		*Expected Outcome: 
			Posts are added & displayed*/
		magAc.userSignIn(userType.DEVELOPER);
		goToForums();
		click(By.linkText(titleTop));
		mngPost.postReply(post1, post1, "", "");
		mngPost.postReply(post2, post2, "", "");

		/*Step number: 3
		*Step Name: Step 3: Check Post counter
		*Step Description: 
			Look at user info
		*Input Data: 
			
		*Expected Outcome: 
			- Post counter is shown with information "demo Has 2 Post"*/
		magAc.userSignIn(userType.ADMIN);
		goToForums();
		goToUserManagement("demo");
		click(ELEMENT_VIEW_USER_PROFILE_ICON.replace("${user}", "demo"));
		postProfile = waitForAndGetElement(ELEMENT_USER_NUMBER_POST).getText();
		newPostNum = Integer.valueOf(postProfile.substring(postProfile.indexOf(":")+1).trim());
		assert (newPostNum==(oldPostNum+2));
		click(ELEMENT_CLOSE_USER_PROFILE_BUTTON);
		button.close();

		/*Step number: 4
		*Step Name: Step 4: Add more posts
		*Step Description: 
			- Add more 2 posts
			- Look at user info
		*Input Data: 
			
		*Expected Outcome: 
			- Post counter is shown with information "demo Has 4 Post"*/ 
		magAc.userSignIn(userType.DEVELOPER);
		goToForums();
		click(By.linkText(titleTop));
		mngPost.postReply(post1, post1, "", "");
		mngPost.postReply(post2, post2, "", "");
		magAc.userSignIn(userType.ADMIN);
		goToForums();
		goToUserManagement("demo");
		click(ELEMENT_VIEW_USER_PROFILE_ICON.replace("${user}", "demo"));
		postProfile = waitForAndGetElement(ELEMENT_USER_NUMBER_POST).getText();
		newPostNum = Integer.valueOf(postProfile.substring(postProfile.indexOf(":")+1).trim());
		assert (newPostNum==(oldPostNum+4));
		click(ELEMENT_CLOSE_USER_PROFILE_BUTTON);
		button.close();
		
		//Clear data
		info("Clear data");
		click(By.linkText(titleCat));
		mngCat.deleteCategoryInForum(titleCat, true);
	}
}