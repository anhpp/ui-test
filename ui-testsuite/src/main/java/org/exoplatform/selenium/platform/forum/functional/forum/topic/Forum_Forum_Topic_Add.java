package org.exoplatform.selenium.platform.forum.functional.forum.topic;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.forum.ForumBase;
import org.exoplatform.selenium.platform.forum.ForumManageCategory;
import org.exoplatform.selenium.platform.forum.ForumManageForum;
import org.exoplatform.selenium.platform.forum.ForumManagePost;
import org.exoplatform.selenium.platform.forum.ForumManageTopic;
import org.openqa.selenium.By;
import org.testng.annotations.*;

/**
 * @author chinhdtt
 * @date 15 Jan 2014
 */	
public class Forum_Forum_Topic_Add extends ForumBase{
	ManageAccount acc;
	ForumManageCategory cat;
	ForumManageForum forum;
	ForumManageTopic topic;
	ForumManagePost post; 
	Button button; 

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		acc = new ManageAccount(driver, this.plfVersion);
		acc.signIn(DATA_USER1, DATA_PASS);
		cat = new ForumManageCategory(driver, this.plfVersion);			
		forum = new ForumManageForum(driver, this.plfVersion); 
		topic = new ForumManageTopic(driver, this.plfVersion); 
		post = new ForumManagePost(driver, this.plfVersion);
		button = new Button(driver, this.plfVersion);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Case ID:73018.
	 * Test Case Name: Add new topic with Posts Notification option.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/01/15 17:33:31
	 */
	@Test
	public  void test01_AddNewTopicWithPostsNotificationOption() {
		info("Test 1: Add new topic with Posts Notification option");
		String catName = "Category 73018";
		String order = "1";
		int chooseRestricted = 1;
		String []restricted = {"john"};
		String description = "Description Category 73018";
		int setPermission = 1;
		String []userGroup = {"john"};
		String []addForum = {"Forum 73018", "1", "Open", "Unlocked", "Description of forum 001"};
		String title = "Topic 73018";
		String message = "Topic 73018";

		/*Step 1: Create forum
		 *Input Data: 
		- Login by the administrator to create new category & forum
		 *Expected Outcome: 
		- Category & forum are created		*/
		acc.updateUserProfile(null, null, null,EMAIL_ADDRESS1);
		goToForums();
		info("Step 1: Create forum");
		cat.goToAddCategory();
		cat.addNewCategoryInForum(catName, order, chooseRestricted, restricted, description, setPermission, userGroup, false);
		forum.goToAddForum();
		forum.inputDataInAddForumTab_addForum(catName, addForum);
		button.save();

		/*Step 2: Select forum to create new topic
		 *Input Data: 
		- Select category
		- Select forum
		 *Expected Outcome: 
		- Show a blank forum
		- 'Start topic' is enabled	*/	
		click(By.linkText(catName));
		click(By.linkText(addForum[0]));
		info("Step 2: Select forum to create new topic");
		/*Step 3: Show form to create new topic
		 *Input Data: 
		- Click [Start topic]
		 *Expected Outcome: 
		- New topic Form is shown properly		

		Step 4: Add new topic with 'Post Notification' option
		 *Input Data: 
		- Choose 'Posts notification' option in Option tab
		- Input value for other fields
		- Click [Submit]
		 *Expected Outcome: 
		- New topic is added.
		- If the added topic is the latest topic in its forum, its information is updated at Last post column.*/		
		info("Step 4: Add new topic with 'Post Notification' option");
		topic.startTopic(title, message, "", 0, userGroup, false, false, false, false, false, false, true);
		waitForAndGetElement(By.linkText(title));
		info("Start topic successfully");

		/*Step 5: Check add new post to topic with 'Posts Notification' option
		 *Input Data: 
		- Login by user having post right on new added topic above
		- Post a reply for this topic
		 *Expected Outcome: 
		- Post is added
		- A notification mail is sent to email address of topic's creator (registered email when add new account to login)		*/ 
		click(By.linkText(title));
		post.postReply(title, message, "", "", "");

		//check email
		Utils.pause(1000);
		goToMail(EMAIL_ADDRESS1, EMAIL_PASS);
		checkAndDeleteMail(By.xpath(ELEMENT_GMAIL_EMAIL.replace("${category}",catName).replace("${forum}", addForum[0]).replace("${topic}", title)), message);

		// Clean data test
		switchToParentWindow();
		click(By.linkText(catName));
		cat.deleteCategoryInForum(catName, true);	
	}

	/**
	 * Case ID:73052.
	 * Test Case Name: Add new topic without Posts Notification option.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/01/15 17:33:31
	 */
	@Test
	public  void test02_AddNewTopicWithoutPostsNotificationOption() {
		info("Test 2: Add new topic without Posts Notification option");
		String catName = "Category 73052";
		String order = "1";
		int chooseRestricted = 1;
		String []restricted = {"john"};
		String description = "Description Category 73052";
		int setPermission = 1;
		String []userGroup = {"john"};
		String []addForum = {"Forum 73052", "1", "Open", "Unlocked", "Description of forum 002"};
		String title = "Topic 73052";
		String message = "Topic 73052";

		/*Step 1: Create forum
		 *Input Data: 
		- Login by the administrator to create new category & forum
		 *Expected Outcome: 
		- Category & forum are created		*/
		acc.updateUserProfile(null, null, null,EMAIL_ADDRESS1);
		goToForums();
		info("Step 1: Create forum");
		cat.addNewCategoryInForum(catName, order, chooseRestricted, restricted, description, setPermission, userGroup, false);
		forum.goToAddForum();
		forum.inputDataInAddForumTab_addForum(catName, addForum);
		button.save();

		/*Step 2: Select forum to create new topic
		 *Input Data: 
		- Select category
		- Select forum
		 *Expected Outcome: 
		- Show a blank forum
		- 'Start topic' is enabled		*/
		click(By.linkText(catName));
		click(By.linkText(addForum[0]));

		/*Step 3: Show form to create new topic
		 *Input Data: 
		- Click [Start topic]
		 *Expected Outcome: 
		- New topic Form is shown properly		*/

		/*Step 4: Add new topic without 'Post Notification' option
		 *Input Data: 
		- Do not check 'Posts notification' option in Option tab
		- Input value for other fields
		- Click [Submit]
		 *Expected Outcome: 
		- New topic is added.
		- If the added topic is the latest topic in its forum, its information is updated at Last post column.		*/

		topic.startTopic(title, message, "", 0, userGroup, false, false);
		waitForAndGetElement(By.linkText(title));
		info("Start topic successfully");

		/*Step 5: Check add new post to topic with 'Posts Notification' option
		 *Input Data: 
		- Login by user having post right on new added topic above
		- Post a reply for this topic
		 *Expected Outcome: 
		- Post is added
		- There is no notification sent to email address of topic's creator (registered email when add new account to login)		*/ 
		click(By.linkText(title));
		post.postReply(title, message, "", "", "");
			
		//check email
		goToMail(EMAIL_ADDRESS1, EMAIL_PASS);
		waitForElementNotPresent(By.xpath(ELEMENT_GMAIL_EMAIL.replace("${category}",catName).replace("${forum}", addForum[0]).replace("${topic}", title)));

		// Clean data test
		switchToParentWindow();
		click(By.linkText(catName));
		cat.deleteCategoryInForum(catName, true);	
	}

	/**
	 * Case ID:73171.
	 * Test Case Name: Add new topic in case limit poster in category which contains this topic.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/01/15 17:33:31
	 */
	@Test
	public  void test03_AddNewTopicInCaseLimitPosterInCategoryWhichContainsThisTopic() {
		info("Test 3: Add new topic in case limit poster in category which contains this topic");
		String catName = "Category 73171";
		String order = "1";
		int chooseRestricted = 1;
		String []restricted = {""};
		String description = "Description Category 73171";
		int setPermission = 1;
		String []userGroup = {"john"};
		String []addForum = {"Forum 73171", "1", "Open", "Unlocked", "Description of forum 003"};
		String title = "Topic 73171";
		String message = "Topic 73171";
		String []userGroupTopic = {""};

		/*Step 1: Show form to create category
		 *Input Data: 
		- Login as root
		- Click on Manage category icon and select Add in menu
		 *Expected Outcome: Add category form is shown properly		*/

		/*Step 2:Limit poster in topic from category
		 *Input Data: 
		- Input value to required fields
		- Click on Permission tab: input/select user(s)/membership(s)/group(s) for Who can post field
		- then click on Submit button
		 *Expected Outcome: New Category is added successful with limit poster topic inside its		*/
		goToForums();
		cat.goToAddCategory();
		info("Step 1: Create forum");
		cat.addNewCategoryInForum(catName, order, chooseRestricted, restricted, description, setPermission, userGroup, false, false, true, false);

		/*Step 3: add forum
		 *Input Data: 
		- Create new forum in added category at step above
		 *Expected Outcome: New forum is added sucessful inside that cattegory		*/
		forum.goToAddForum();
		forum.inputDataInAddForumTab_addForum(catName, addForum);
		button.save();

		/*Step 4: Add new topic 
		 *Input Data: 
		- Open added forum above and click on Start topic button
		- input value to required field
		- Click on Permission tab: don't input/select any user/membership/group into Restrict who can view in this topic to field
		- Click on Submit button
		 *Expected Outcome: 
		- New topic is added successful
		- In this topic, show info message Some users may not be allowed to post this topic in permission tab
		- Only selected user/memberhsip/group in Who can post field of added category above can post in this topic		*/

		topic.startTopic(title, message, "", 0, userGroupTopic, false, false);
		waitForAndGetElement(By.linkText(title));
		info("Start topic successfully");

		/*Step 5: Check post right on created topic by legal users
		 *Input Data: 
		- Login by user who is assigned post right in category which contains this topic
		 *Expected Outcome: 
		- This user can see this topic in topic list of forum and can post in this topic
		- Forum rule text is shown validly		*/
		click(By.linkText(title));
		post.postReply(title, message, "", "", "");
		acc.signOut();

		/*Step 6: Check post right on created topic by illegal users
		 *Input Data: 
		- Login by user who is not assigned post right in category which contains this topic
		 *Expected Outcome: 
		- This user can see this topic in topic list of forum but can not post		*/ 
		acc.userSignIn(userType.PUBLISHER);
		goToForums();
		click(By.linkText(catName));
		click(By.linkText(addForum[0]));
		click(By.linkText(title));
		waitForElementNotPresent(post.ELEMENT_POST_REPLY_BUTTON);
		acc.signOut();

		// Clean data test
		acc.userSignIn(userType.ADMIN);
		goToForums();
		click(By.linkText(catName));
		cat.deleteCategoryInForum(catName, true);	
	}

	/**
	 * Case ID:73185.
	 * Test Case Name: Add new topic in case limit viewer in category which contains this topic.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/01/15 17:33:31
	 */
	@Test
	public  void test04_AddNewTopicInCaseLimitViewerInCategoryWhichContainsThisTopic() {
		info("Test 4: Add new topic in case limit viewer in category which contains this topic");
		String catName = "Category 73185";
		String order = "1";
		int chooseRestricted = 1;
		String []restricted = {""};
		String description = "Description Category 73185";
		int setPermission = 1;
		String []userGroup = {"john"};
		String []addForum = {"Forum 73185", "1", "Open", "Unlocked", "Description of forum 004"};
		String title = "Topic 73185";
		String message = "Topic 73185";
		String []userGroupTopic = {""};

		/*Step 1: Show form to create category
		 *Input Data: 
		- Login as root
		- Click on Manage category icon and select Add in menu
		 *Expected Outcome: Add category form is shown properly		*/

		/*Step 2:Limit poster in topic from category
		 *Input Data: 
		- Input value to required fields
		- Click on Permission tab: input/select user(s)/membership(s)/group(s) for Who can View field
		- then click on Submit button
		 *Expected Outcome: New Category is added successful with limit viewer topics inside its		*/
		goToForums();
		cat.goToAddCategory();
		info("Step 1: Create forum");
		cat.addNewCategoryInForum(catName, order, chooseRestricted, restricted, description, setPermission, userGroup, false, false, false, true);

		/*Step 3: add forum
		 *Input Data: 
		- Create new forum in added category at step above
		 *Expected Outcome: New forum is added sucessful inside that cattegory		*/
		forum.goToAddForum();
		forum.inputDataInAddForumTab_addForum(catName, addForum);
		button.save();

		/*Step 4: Add new topic 
		 *Input Data: 
		- Open added forum above and click on Start topic button
		- input value to required field
		- Click on Permission tab: don't input/select any user/membership/group into Restrict who can post in this topic to field
		- Click on Submit button
		 *Expected Outcome: 
		- New topic is added successful 
		- In this topic, show info message Some users may not be allowed to view this topic in permission tab
		- Only selected user/memberhsip/group in Who can view field of added category above can view in this topic		*/

		topic.startTopic(title, message, "", 0, userGroupTopic, false, false);
		waitForAndGetElement(By.linkText(title));
		info("Start topic successfully");

		/*Step 5: Check view right on created topic by legal users
		 *Input Data: 
		- Login by user who is assigned view right in category which contains this topic
		 *Expected Outcome: 
		- This user can see this topic in topic list of forum
		- Forum rule text is shown validly		*/
		goToForums();
		click(By.linkText(catName));
		click(By.linkText(addForum[0]));
		click(By.linkText(title));

		/*Step 6: Check view right on created topic by illegal users
		 *Input Data: 
		- Login by user who is not assigned view right in category which contains this topic
		 *Expected Outcome: 
		- This user can not see this topic in topic list of forum		*/ 
		acc.userSignIn(userType.PUBLISHER);
		goToForums();
		click(By.linkText(catName));
		click(By.linkText(addForum[0]));
		waitForElementNotPresent(By.linkText(title));
		acc.signOut();

		// Clean data test
		acc.userSignIn(userType.ADMIN);
		goToForums();
		click(By.linkText(catName));
		cat.deleteCategoryInForum(catName, true);
	}

	/**
	 * Case ID:73196.
	 * Test Case Name: Add new topic in case limit poster in forum which contains this topic.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/01/15 17:33:31
	 */
	@Test
	public  void test05_AddNewTopicInCaseLimitPosterInForumWhichContainsThisTopic() {
		info("Test 5: Add new topic in case limit poster in forum which contains this topic");
		String catName = "Category 73196";
		String order = "1";
		int chooseRestricted = 1;
		String []restricted = {""};
		String description = "Description Category 73196";
		int setPermission = 1;
		String []userGroup = {"john"};
		String []addForum = {"Forum 73196", "1", "Open", "Unlocked", "Description of forum 005"};
		String title = "Topic 73196";
		String message = "Topic 73196";
		String []userGroupTopic = {""};

		/*Step 1: Add category
		 *Input Data: 
		- Login as root 
		- Create new category by selecting Add item from Manage category menu
		 *Expected Outcome: New category is added successful		*/
		goToForums();
		cat.goToAddCategory();
		cat.addNewCategoryInForum(catName, order, chooseRestricted, restricted, description, setPermission, userGroup, false, false, false, false);

		/*Step 2: Show form to add forum inside added category above
		 *Input Data: 
		- Open added category at step above
		- then, Select Add new forum item in Manage category
		 *Expected Outcome: Add forum form is shown properly		*/

		/*Step 3: limit posters in topic from forum
		 *Input Data: 
		- Input value to required fields 
		- Click on Permission tab: input/select user(s)/membership(s)/group(s) for Who can Post field
		- then click on Submit button
		 *Expected Outcome: New forum is added successful with limit poster topics inside its		*/
		forum.goToAddForum();
		forum.addForum(catName, addForum, false, "", "", false, 1, userGroup, false, false, false, true);

		/*Step 4: Add new topic
		 *Input Data: 
		- Open added forum above and click on Start topic button
		- input value to required field
		- Click on Permission tab: don't input/select any user/membership/group into Restrict who can post in this topic toâ€ field
		- Click on Submit button
		 *Expected Outcome: 
		- New topic is added successful 
		- In this topic, show info message Some users may not be allowed to post in this topic in permission tab
		- Only selected user/memberhsip/group in Who can post field of added forum above can post in this topic		*/

		topic.startTopic(title, message, "", 0, userGroupTopic, false, false);
		waitForAndGetElement(By.linkText(title));
		info("Start topic successfully");

		/*Step 5: Check post right on created topic by legal users
		 *Input Data: 
		- Login by user who is assigned post right in forum which contains this topic
		 *Expected Outcome: 
		- This user can post in this topic
		- Forum rule text is shown validly		*/
		click(By.linkText(title));
		post.postReply(title, message, "", "", "");
		acc.signOut();

		/*Step 6: Check post right on created topic by illegal users
		 *Input Data: 
		- Login by user who is not assigned post right in forum which contains this topic
		 *Expected Outcome: 
		- This user can not post in this topic		*/ 
		acc.userSignIn(userType.PUBLISHER);
		goToForums();
		click(By.linkText(catName));
		click(By.linkText(addForum[0]));
		click(By.linkText(title));
		waitForElementNotPresent(post.ELEMENT_POST_REPLY_BUTTON);
		acc.signOut();

		// Clean data test
		acc.userSignIn(userType.ADMIN);
		goToForums();
		click(By.linkText(catName));
		cat.deleteCategoryInForum(catName, true);	
	}

	/**
	 * Case ID:73206.
	 * Test Case Name: Add new topic in case limit Viewer in forum which contains this topic.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/01/15 17:33:31
	 */
	@Test
	public  void test06_AddNewTopicInCaseLimitViewerInForumWhichContainsThisTopic() {
		info("Test 6: Add new topic in case limit Viewer in forum which contains this topic");
		String catName = "Category 73206";
		String order = "1";
		int chooseRestricted = 1;
		String []restricted = {""};
		String description = "Description Category 73206";
		int setPermission = 1;
		String []userGroup = {"john"};
		String []addForum = {"Forum 73206", "1", "Open", "Unlocked", "Description of forum 006"};
		String title = "Topic 73206";
		String message = "Topic 73206";
		String []userGroupTopic = {""};

		/*Step 1: Add category
		 *Input Data: 
		- Login as root 
		- Create new category by selecting Add item from Manage category menu
		 *Expected Outcome: New category is added successful		*/
		goToForums();
		cat.goToAddCategory();
		cat.addNewCategoryInForum(catName, order, chooseRestricted, restricted, description, setPermission, userGroup, false, false, false, false);

		/*Step 2: Show form to add forum inside added category above
		 *Input Data: 
		- Open added category at step above
		- then, Select Add new forum item in Manage category
		 *Expected Outcome: Add forum form is shown properly		*/

		/*Step 3: limit posters in topic from forum
		 *Input Data: 
		- Input value to required fields 
		- Click on Permission tab: input/select user(s)/membership(s)/group(s) for â€œWho can view post field
		- then click on Submit button
		 *Expected Outcome: New forum is added successful with limit viewer topics inside its		*/

		forum.goToAddForum();
		forum.addForum(catName, addForum, false, "", "", false, 1, userGroup, false, true, false, false);

		/*Step 4: Add new topic
		 *Input Data: 
		- Open added forum above and click on Start topic button
		- input value to required field
		- Click on Permission tab: don't input/select any user/membership/group into Restrict who can view post in this topic toâ€ field
		- Click on Submit button
		 *Expected Outcome: 
		- New topic is added successful 
		- In this topic, show info message â€œSome users may not be allowed to view this topic in permission tab
		- Only selected user/memberhsip/group in â€œWho can view post field of added forum above can view in this topic		*/

		topic.startTopic(title, message, "", 0, userGroupTopic, false, false);
		waitForAndGetElement(By.linkText(title));
		info("Start topic successfully");

		/*Step 5: Check view right on created topic by legal users
		 *Input Data: 
		- Login by user who is assigned view right in forum which contains this topic
		 *Expected Outcome: 
		- This user can see this topic in topic list of forum 
		- Forum rule text is shown validly		*/
		goToForums();
		click(By.linkText(catName));
		click(By.linkText(addForum[0]));
		click(By.linkText(title));

		/*Step 6: Check view right on created topic by illegal users
		 *Input Data: 
		- Login by user who is not assigned view right in forum which contains this topic
		 *Expected Outcome: 
		- This user can not see this topic in topic list of forum 		*/ 
		acc.userSignIn(userType.PUBLISHER);
		goToForums();
		click(By.linkText(catName));
		click(By.linkText(addForum[0]));
		waitForElementNotPresent(By.linkText(title));
		acc.signOut();

		// Clean data test
		acc.userSignIn(userType.ADMIN);
		goToForums();
		click(By.linkText(catName));
		cat.deleteCategoryInForum(catName, true);
	}

	/**
	 * Case ID:73215.
	 * Test Case Name: Add new topic in case limit poster in both category & forum which contains this topic.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/01/15 17:33:31
	 */
	@Test
	public  void test07_AddNewTopicInCaseLimitPosterInBothCategoryForumWhichContainsThisTopic() {
		info("Test 7: Add new topic in case limit poster in both category & forum which contains this topic");
		String catName = "Category 73215";
		String order = "1";
		int chooseRestricted = 1;
		String []restricted = {""};
		String description = "Description Category 73215";
		int setPermission = 1;
		String []userGroup = {"demo"};
		String []addForum = {"Forum 73215", "1", "Open", "Unlocked", "Description of forum 007"};
		String title = "Topic 73215";
		String message = "Topic 73215";

		/*Step 1: Show form to create category
		 *Input Data: 
		- Click on Manage category and select Add' in menu
		 *Expected Outcome: Add category form is shown properly		*/

		/*Step 2:Limit poster in topic from category
		 *Input Data: 
		- Input value to required fields
		- Click on Permission tab: input/select user(s)/membership(s)/group(s) for Who can Post field
		- then click on Submit button
		 *Expected Outcome: New Category is added successful with limit poster topics inside its		*/
		goToForums();
		cat.goToAddCategory();
		cat.addNewCategoryInForum(catName, order, chooseRestricted, restricted, description, setPermission, userGroup, false, false, true, false);

		/*Step 3: Show form to create forum
		 *Input Data: 
		- Open added category at step above 
		- then, Select Add new forum item in Manage category
		 *Expected Outcome: Add forum form is shown properly		*/

		/*Step 4: Limit poster in topic from forum
		 *Input Data: 
		- Input value to required fields
		- Click on Permission tab: input/select user(s)/membership(s)/group(s) for Who can Post field
		- then click on Submit button
		 *Expected Outcome: New forum is added successful with limit poster topics inside its		*/

		forum.goToAddForum();
		forum.addForum(catName, addForum, false, "", "", false, 1, userGroup, false, false, false, true);

		/*Step 5: Add new topic
		 *Input Data: 
		- Open added forum above and click on Start topic button
		- input value to required field
		- Click on Permission tab: don't input/select any user/membership/group into Restrict who can post in this topic toâ€ field
		- Click on Submit button
		 *Expected Outcome: 
		- New topic is added successful 
		- In this topic, show info message â€œSome users may not be allowed to view this topicâ€ in permission tab
		- Only selected user/memberhsip/group in â€œWho can view postâ field of added category & forum above can post in this topic		*/
		info("Add new topic");
		topic.startTopic(title, message, "", 0, userGroup, false, false);
		waitForAndGetElement(By.linkText(title));		
		acc.signOut();

		/*Step 6: Check post right on created topic by legal users
		 *Input Data: 
		- Login by user who is assigned post right incategory & forum which contains this topic
		 *Expected Outcome: 
		- This user can post in this topic
		- Forum rule text is shown validly		*/
		acc.userSignIn(userType.DEVELOPER);
		goToForums();
		click(By.linkText(catName));
		click(By.linkText(addForum[0]));
		click(By.linkText(title));
		post.postReply(title, message, "", "", "");
		acc.signOut();

		/*Step 7: Check post right on created topic by illegal users
		 *Input Data: 
		- Login by user who is not assigned post right in category & forum which contains this topic
		 *Expected Outcome: 
		- This user can not post in this topic		*/ 
		acc.userSignIn(userType.PUBLISHER);
		goToForums();
		click(By.linkText(catName));
		click(By.linkText(addForum[0]));
		click(By.linkText(title));
		waitForElementNotPresent(post.ELEMENT_POST_REPLY_BUTTON);
		acc.signOut();

		// Clean data test
		acc.userSignIn(userType.ADMIN);
		goToForums();
		click(By.linkText(catName));
		cat.deleteCategoryInForum(catName, true);	
	}

	/**
	 * Case ID:73223.
	 * Test Case Name: Add new topic in case limit Viewer in both category & forum which contains this topic.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/01/15 17:33:31
	 */
	@Test
	public  void test08_AddNewTopicInCaseLimitViewerInBothCategoryForumWhichContainsThisTopic() {
		info("Test 8: Add new topic in case limit Viewer in both category & forum which contains this topic");
		String catName = "Category 73223";
		String order = "1";
		int chooseRestricted = 1;
		String []restricted = {""};
		String description = "Description Category 73223";
		int setPermission = 1;
		String []userGroup = {"demo"};
		String []addForum = {"Forum 73223", "1", "Open", "Unlocked", "Description of forum 008"};
		String title = "Topic 73223";
		String message = "Topic 73223";

		/*Step 1: Show form to create category
		 *Input Data: 
		- Click on Manage category and select Add' in menu
		 *Expected Outcome: Add category form is shown properly		*/

		/*Step 2:Limit Viewer in topic from category
		 *Input Data: 
		- Input value to required fields
		- Click on Permission tab: input/select user(s)/membership(s)/group(s) for Who can view post field
		- then click on Submit button
		 *Expected Outcome: New Category is added successful with limit viewer topics inside its		*/
		goToForums();
		cat.addNewCategoryInForum(catName, order, chooseRestricted, restricted, description, setPermission, userGroup, false, false, false, true);

		/*Step 3: Show form to create forum
		 *Input Data: 
		- Open added category at step above 
		- then, Select Add new forum item in Manage category
		 *Expected Outcome: Add forum form is shown properly		*/

		/*Step 4: Limit viewer in topic from forum
		 *Input Data: 
		- Input value to required fields
		- Click on Permission tab: input/select user(s)/membership(s)/group(s) for â€œWho can view post field
		- then click on Submit button
		 *Expected Outcome: New forum is added successful with limit viewer topics inside its		*/
		forum.addForum(catName, addForum, false, "", "", false, 1, userGroup, false, true, false, false);

		/*Step 5: Add new topic
		 *Input Data: 
		- Open added forum above and click on Start topic button
		- input value to required field
		- Click on Permission tab: don't input/select any user/membership/group into Restrict who can view post in this topic toâ€ field
		- Click on Submit button
		 *Expected Outcome: 
		- New topic is added successful 
		- In this topic, show info message Some users may not be allowed to view this topic in permission tab
		- Only selected user/memberhsip/group in â€œWho can view post field of added category & forum above can post in this topic		*/
		info("Add new topic");
		topic.startTopic(title, message, "", 0, userGroup, false, false);
		waitForAndGetElement(By.linkText(title));

		/*Step 6: Check view right on created topic by legal users
		 *Input Data: 
		- Login by user who is assigned view right in category & forum which contains this topic
		 *Expected Outcome: 
		- This user can view post in this topic
		- Forum rule text is shown validly		*/
		acc.userSignIn(userType.DEVELOPER);
		goToForums();
		click(By.linkText(catName));
		click(By.linkText(addForum[0]));
		click(By.linkText(title));
		acc.signOut();
		/*Step 7: Check view right on created topic by illegal users
		 *Input Data: 
		- Login by user who is not assigned view right in category & forum which contains this topic
		 *Expected Outcome: 
		- This user can not view post in this topic		*/ 	
		acc.userSignIn(userType.PUBLISHER);
		goToForums();
		click(By.linkText(catName));
		click(By.linkText(addForum[0]));
		waitForElementNotPresent(By.linkText(title));
		acc.signOut();

		// Clean data test
		acc.userSignIn(userType.ADMIN);
		goToForums();
		click(By.linkText(catName));
		cat.deleteCategoryInForum(catName, true);
	}
}