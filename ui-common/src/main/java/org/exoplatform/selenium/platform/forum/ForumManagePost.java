package org.exoplatform.selenium.platform.forum;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Migrate from plf3.5
 * 
 * @author lientm
 * @date 19 Aug 2013
 */

public class ForumManagePost extends ForumBase {

	ForumPermission per;
	ForumManageTopic magTopic;

	public ForumManagePost(WebDriver dr, String... plfVersion) {
		driver = dr;
		this.plfVersion = plfVersion.length > 0 ? plfVersion[0] : "4.0";
		per = new ForumPermission(driver, this.plfVersion);
		button = new Button(driver, this.plfVersion);
		alert = new ManageAlert(driver, this.plfVersion);
		magTopic = new ForumManageTopic(driver, this.plfVersion);
	}

	// --------------post home
	// screen--------------------------------------------------------

	public By ELEMENT_REPLY_LOCK_BUTTON = By
			.xpath("//*[@class='clearfix topContainer']//div[@class='uiLockIcon btn disabled' and text()='Post Reply']");

	public By ELEMENT_POST_REPLY_BUTTON = By.linkText("Post Reply");
	public String ELEMENT_POST_EDIT_BUTTON = "//*[text()='${postContent}']/../../../..//a[text()='Edit' and @class='btn']";
	public String ELEMENT_POST_EDIT_BUTTON_1 = "//*[text()='${postContent}']/../../../..//a[text()='Edit' and @class='btn']";
	public String ELEMENT_POST_CHECKBOX = "//*[contains(text(),'${postContent}')]/../../../../*//input[@type='checkbox']";
	// public By ELEMENT_MOVE_POST =
	// By.xpath("//a[@class='ItemIcon MovePostIcon' and text()='Move']");
	public By ELEMENT_MOVE_POST = By.linkText("Move");
	public String ELEMENT_GO_TO_THE_LASTS_READ_POST_FORUM = "//a[text()='${forum}']/../..//a[@title='Go to the last read post']";
	public String ELEMENT_PRIVATE_POST_BUTTON = "//*[text()='${topic}  ']/../../..//a[text()='Private']";
	public final String ELEMENT_POST_CONTENT = "//*[@class='postContent']//*[text()='${postContent}']";
	public By ELEMENT_APPROVE_FORM = By
			.xpath("//span[@class='PopupTitle popupTitle' and contains(text(),'Posts to Approve')]");
	public By ELEMENT_APPROVE_POST = By.linkText("Approve");
	// public String ELEMENT_APPROVE_POST_CHECK =
	// "//a[@data-original-title='${topic}']/../../../../..//*[@class='uiCheckbox']";
	public String ELEMENT_APPROVE_POST_CHECK = "//a[@data-original-title='${post}']/../../..//input[@type='checkbox']";

	public By ELEMENT_APPROVE_POST_BUTTON = By
			.xpath("//button[text()='Approve']");

	public By ELEMENT_CENSOR_POST = By.linkText("Censor");
	public String ELEMENT_CENSOR_POST_CHECK = "//a[@data-original-title='${post}']/../../..//input[@type='checkbox']";
	public String MSG_POST_CENSOR = "This post may contain offensive content. It will be displayed after moderation.";
	public String MSG_POST_APPROVE = "Your post is pending for moderation. It will be displayed after approval.";
	public String ELEMENT_POST_TITLE_TEXT = "//div[contains(@class,'postViewTitle') and contains(text(),'${post}')]";
	// public String ELEMENT_POST_CONTENT_TEXT =
	// "//div[@class='postContent']//p[contains(text(),'${post}')]";
	public String ELEMENT_POST_CONTENT_TEXT = "//div[@class='containerQuote']//p[contains(text(),'${post}')]";
	public String ELEMENT_POST_CONTENT_TEXT_BOLD = "//div[@class='containerQuote']//p/strong[contains(text(),'${post}')]";
	public String ELEMENT_PRIVATE_POST_MESSAGE = "//div[@class='uiForumPortlet forumBoxNotification']//div[@class='content' and contains(text(),'${post}')]";
	public By ELEMENT_PRIVATE_POST_CLOSE_NOTIFICATION = By
			.xpath("//div[@class='uiForumPortlet forumBoxNotification']//i[@class='uiIconClose']");
	public String ELEMENT_POST_QUOTE_TEXT = "//div[@class='contentQuote']/div[@class='textContent']/p[contains(text(),'${post}')]";

	// CKEditor
	public final String ELEMENT_CKEDITOR_DECORATE = ".//*[@title='${name}']";

	// --------------post reply
	// screen-----------------------------------------------------------
	public By ELEMENT_POST_TITLE = By.id("PostTitle");
	public By ELEMENT_POST_MESSAGE_FRAME_1 = By.id("MessageContent___Frame");
	public By ELEMENT_POST_MESSAGE_FRAME_CKEDITOR = By
			.xpath("//iframe[@class='cke_wysiwyg_frame cke_reset']");
	public By ELEMENT_POST_POPUP_NEW = By
			.xpath("//span[@class='PopupTitle popupTitle' and text()='New Post']");
	public By ELEMENT_POST_ICONS_TAB = By
			.xpath("//a[contains(text(), 'Icons and Smileys')]");
	public By ELEMENT_POST_PRIVATE_POPUP = By
			.xpath("//span[@class='PopupTitle popupTitle' and text()='Private Post']");
	public By ELEMENT_POST_PREVIEW_BUTTON = By
			.xpath("//button[text()='Preview']");
	public By ELEMENT_POST_CLOSE_BUTTON = By
			.xpath("//*[@id='UIAddPostContainer']//button[text()='Close']");
	public By ELEMENT_POST_CANCEL_BUTTON = By
			.xpath("//form[@id='UIPostForm']//button[text()='Cancel']");

	// --------------quick reply
	// form-----------------------------------------------------------
	public By ELEMENT_POST_QUICK_MESSAGE = By.id("UITopicDetail.label.Message");
	public By ELEMENT_PREVIEW_BUTTON = By.linkText("Preview");
	public By ELEMENT_POST_QUICK_BUTTON = By.linkText("Quick Reply");
	public By ELEMENT_POST_PREVIEW_POPUP = By
			.xpath("//span[@class='PopupTitle' and text()='ViewPost']");
	public By ELEEMENT_QUICK_REPLY_FORM = By.id("QuickReply");

	// --------------edit post
	// screen-----------------------------------------------------------
	public By ELEMENT_POST_POPUP_EDIT = By
			.xpath("//span[@class='PopupTitle popupTitle' and text()='Edit Post']");
	public By ELEMENT_POST_REASON = By.id("editReason");

	// --------------move post
	// screen-----------------------------------------------------------
	// public By ELEMENT_POPUP_MOVE_POST = By.id("UIForumPopupWindow");
	public By ELEMENT_POPUP_MOVE_POST = By
			.xpath("//span[@class='PopupTitle popupTitle' and text()='Move Posts']");
	public String ELEMENT_MOVE_POST_NODE = "//form[@id='UIMovePostForm']//a[contains(.,'${node}')]";

	// Delete post
	public String MSG_DELETE_POST = "Are you sure you want to delete this post ?";
	public String ELEMENT_POST_DELETE_BUTTON = "//*[text()='${postContent}']/../../../..//a[text()='Delete' and @class='btn confirm']";
	public By ELEMENT_POST_OK_DELETE = By
			.xpath("//span[text()='Are you sure you want to delete this post ?']/../../..//button[@class='btn actionOK' and text()='OK']");

	// Quote post

	public String ELEMENT_QUOTE_POST = "//*[text()='${post}']/../../../..//a[text()='Quote' and @class='btn']";

	/*-------------------------------Common function-------------------------------*/

	/**
	 * function: post new reply
	 * 
	 * @author lientm
	 * @param title
	 *            : title of reply
	 * @param message
	 *            : content of reply
	 * @param groupName
	 *            : group of icon
	 * @param iconClass
	 *            : icon
	 * @param file
	 *            : file attach
	 */
	public void postReply(String title, String message, String groupName,
			String iconClass, Object... opParams) {
		info("Make a post");
		click(ELEMENT_POST_REPLY_BUTTON);
		waitForAndGetElement(ELEMENT_POST_POPUP_NEW);
		putDataPost(title, message, groupName, iconClass, opParams);
	}

	/**
	 * @author quynhpt
	 * @param title
	 * @param message
	 * @param groupName
	 * @param iconClass
	 * @param opParams
	 */
	public void postReplyWithDecorate(String title, String message,
			String decorate, String groupName, String iconClass,
			Object... opParams) {
		info("Make a post");
		click(ELEMENT_POST_REPLY_BUTTON);
		waitForAndGetElement(ELEMENT_POST_POPUP_NEW);
		putDataPostWithDecorate(title, message, decorate, groupName, iconClass,
				opParams);
	}

	public void openReplyPostPopUp() {
		info("open the post popup");
		click(ELEMENT_POST_REPLY_BUTTON);
		waitForAndGetElement(ELEMENT_POST_POPUP_NEW);
	}

	public void savePostContent() {
		info("open the post popup");
		click(magTopic.ELEMENT_SUBMIT_BUTTON);
		waitForElementNotPresent(ELEMENT_POST_POPUP_NEW);
	}
	

	/*
	 * @author quynhpt
	 * 
	 * @param title
	 * 
	 * @param message
	 * 
	 * @param groupName
	 * 
	 * @param iconClass
	 * 
	 * @param opParams
	 */
	public void postReplyWithDecorateFromActivityStream(String title,
			String message, String decorate, String groupName,
			String iconClass, Object... opParams) {
		info("Make a post");
		waitForAndGetElement(ELEMENT_POST_POPUP_NEW);
		putDataPostWithDecorate(title, message, decorate, groupName, iconClass,
				opParams);
	}

	/**
	 * Function make a private post
	 * 
	 * @author lientm
	 * @param topic
	 *            : topic that need add post
	 * @param title
	 *            : title of reply
	 * @param message
	 *            : content of reply
	 * @param groupName
	 *            : group of icon
	 * @param iconClass
	 *            : icon
	 * @param file
	 *            : file attach
	 */
	public void privatePost(String topic, String title, String message,
			String groupName, String iconClass, Object... opParams) {
		By private_but = By.xpath(ELEMENT_PRIVATE_POST_BUTTON.replace(
				"${topic}", topic));

		info("Make a private post");
		click(private_but);
		waitForAndGetElement(ELEMENT_POST_PRIVATE_POPUP);
		putDataPost(title, message, groupName, iconClass, opParams);
	}

	/**
	 * Function make a private post with th decoration
	 * 
	 * @author quynhpt
	 * @param topic
	 *            : topic that need add post
	 * @param title
	 *            : title of reply
	 * @param message
	 *            : content of reply
	 * @param groupName
	 *            : group of icon
	 * @param iconClass
	 *            : icon
	 * @param file
	 *            : file attach
	 */
	public void privatePostWithDecorate(String topic, String title,
			String message, String decorate, String groupName,
			String iconClass, Object... opParams) {
		By private_but = By.xpath(ELEMENT_PRIVATE_POST_BUTTON.replace(
				"${topic}", topic));

		info("Make a private post");
		click(private_but);
		waitForAndGetElement(ELEMENT_POST_PRIVATE_POPUP);
		putDataPostWithDecorate(title, message, decorate, groupName, iconClass,
				opParams);
	}

	/**
	 * function put data into add Post
	 * 
	 * @author lientm
	 * @param title
	 *            : title of reply
	 * @param message
	 *            : content of reply
	 * @param groupName
	 *            : group of icon
	 * @param iconClass
	 *            : icon
	 * @param file
	 *            : file attach
	 */
	public void putDataPost(String title, String message, String groupName,
			String iconClass, Object... opParams) {
		// magTopic = new ForumManageTopic(driver);
		String file = (String) (opParams.length > 0 ? opParams[0] : "");
		Boolean check = (Boolean) (opParams.length > 1 ? opParams[1] : false);
		boolean verify = (Boolean) (opParams.length > 2 ? opParams[2] : true);
		if (title != null) {
			type(ELEMENT_POST_TITLE, title, true);
		}
		if (message != "" && message != null) {
			if (this.plfVersion.equalsIgnoreCase("4.1"))
				inputDataToFrame(ELEMENT_POST_MESSAGE_FRAME_CKEDITOR, message,
						true, false);
			else
				// if(this.plfVersion.equalsIgnoreCase("4.0"))
				inputDataToFrameInFrame(ELEMENT_POST_MESSAGE_FRAME_1,
						ELEMENT_POST_MESSAGE_FRAME_2, message, true, false);
			switchToParentWindow();
		}
		if (message != null) {
			if (waitForAndGetElement(ELEMENT_POST_MESSAGE_FRAME_CKEDITOR, 5000,
					0) != null)
				inputDataToFrame(ELEMENT_POST_MESSAGE_FRAME_CKEDITOR, message,
						true);
			else
				// if(this.plfVersion.equalsIgnoreCase("4.0"))
				inputDataToFrameInFrame(ELEMENT_POST_MESSAGE_FRAME_1,
						ELEMENT_POST_MESSAGE_FRAME_2, message, true);
			switchToParentWindow();
		}
		if (file != "") {
			click(ELEMENT_ATTACH_FILE);
			waitForAndGetElement(ELEMENT_POPUP_UPLOAD_FILE);
			attachFile(file);
			waitForElementNotPresent(ELEMENT_POPUP_UPLOAD_FILE);
		}
		if (groupName != "" && groupName != null && iconClass != ""
				&& iconClass != null) {
			click(ELEMENT_POST_ICONS_TAB);
			magTopic.chooseIcon(groupName, iconClass);
		}
		// magTopic = new ForumManageTopic(driver);
		if (check == true) {
			click(ELEMENT_POST_PREVIEW_BUTTON);
		} else {
			click(magTopic.ELEMENT_SUBMIT_BUTTON);
			waitForElementNotPresent(ELEMENT_POST_POPUP_NEW);
			if (verify)
				waitForAndGetElement(ELEMENT_POST_CONTENT_TEXT.replace(
						"${post}", message));
			info("Post reply successfully");
		}
	}

	/**
	 * @author quynhpt
	 * @param title
	 * @param message
	 * @param decorate
	 * @param groupName
	 * @param iconClass
	 * @param opParams
	 */
	public void putDataPostWithDecorate(String title, String message,
			String decorate, String groupName, String iconClass,
			Object... opParams) {
		// magTopic = new ForumManageTopic(driver);
		String file = (String) (opParams.length > 0 ? opParams[0] : "");
		Boolean check = (Boolean) (opParams.length > 1 ? opParams[1] : false);
		boolean verify = (Boolean) (opParams.length > 2 ? opParams[2] : true);
		if (title != null) {
			type(ELEMENT_POST_TITLE, title, true);
		}
		if (message != "" && message != null) {
			if (this.plfVersion.equalsIgnoreCase("4.1"))
				inputDataToFrame(ELEMENT_POST_MESSAGE_FRAME_CKEDITOR, message,
						true, false);
			else
				// if(this.plfVersion.equalsIgnoreCase("4.0"))
				inputDataToFrameInFrame(ELEMENT_POST_MESSAGE_FRAME_1,
						ELEMENT_POST_MESSAGE_FRAME_2, message, true, false);
			switchToParentWindow();
		}
		if (message != null) {
			if (waitForAndGetElement(ELEMENT_POST_MESSAGE_FRAME_CKEDITOR, 5000,
					0) != null)
				inputDataToFrame(ELEMENT_POST_MESSAGE_FRAME_CKEDITOR, message,
						true);
			else
				// if(this.plfVersion.equalsIgnoreCase("4.0"))
				inputDataToFrameInFrame(ELEMENT_POST_MESSAGE_FRAME_1,
						ELEMENT_POST_MESSAGE_FRAME_2, message, true);
			switchToParentWindow();
		}
		if (file != "") {
			click(ELEMENT_ATTACH_FILE);
			waitForAndGetElement(ELEMENT_POPUP_UPLOAD_FILE);
			attachFile(file);
			waitForElementNotPresent(ELEMENT_POPUP_UPLOAD_FILE);
		}
		if (groupName != "" && groupName != null && iconClass != ""
				&& iconClass != null) {
			click(ELEMENT_POST_ICONS_TAB);
			magTopic.chooseIcon(groupName, iconClass);
		}
		// magTopic = new ForumManageTopic(driver);
		if (check == true) {
			click(ELEMENT_POST_PREVIEW_BUTTON);
		} else {
			if (decorate != "")
				click(By.xpath(ELEMENT_CKEDITOR_DECORATE.replace("${name}",
						decorate)));
			click(magTopic.ELEMENT_SUBMIT_BUTTON);
			waitForElementNotPresent(ELEMENT_POST_POPUP_NEW);
			if (verify)
				waitForAndGetElement(ELEMENT_POST_CONTENT_TEXT.replace(
						"${post}", message));
			info("Post reply successfully");
		}
	}

	/**
	 * function: quick add new Reply
	 * 
	 * @author lientm
	 * @param message
	 *            : content of reply
	 */
	public void quickReply(String message, boolean... verify) {
		boolean check = verify.length > 0 ? verify[0] : true;
		type(ELEMENT_POST_QUICK_MESSAGE, message, true);
		click(ELEMENT_POST_QUICK_BUTTON);
		info("Quick reply successfully");
		if (check) {
			waitForTextPresent(message);
		}
	}

	/**
	 * function: Review add new Reply
	 * 
	 * @author lientm
	 * @param message
	 *            : review content of reply
	 */

	public void quickReplyAndPreview(String message, By... view) {
		type(ELEMENT_POST_QUICK_MESSAGE, message, true);
		click(ELEMENT_PREVIEW_BUTTON);
		if (view.length > 0) {
			waitForAndGetElement(ELEMENT_POST_PREVIEW_POPUP);
			for (int i = 0; i < view.length; i++) {
				waitForAndGetElement(view[i]);
			}
			button.close();
			waitForElementNotPresent(ELEMENT_POST_PREVIEW_POPUP);
		}
		click(ELEMENT_POST_QUICK_BUTTON);
		if (view.length > 0) {
			for (int j = 0; j < view.length; j++) {
				waitForAndGetElement(view[j]);
			}
			info("Quick reply successfully");
		}
	}

	/**
	 * function: Edit a Post
	 * 
	 * @author lientm
	 * @param postContent
	 *            : content of post that needs to edit
	 * @param title
	 *            : new title of post
	 * @param reason
	 *            : reason edit
	 * @param message
	 *            : new message
	 * @param groupName
	 *            : new group icon
	 * @param iconClass
	 *            : new icon
	 * @param file
	 *            : new file attach
	 */
	public void editPost(String postContent, String title, String reason,
			String message, String groupName, String iconClass, String... file) {
		By EDIT_POST = By.xpath(ELEMENT_POST_EDIT_BUTTON.replace(
				"${postContent}", postContent));

		info("Edit a post");
		click(EDIT_POST);
		waitForAndGetElement(ELEMENT_POST_POPUP_EDIT);
		if (title != "" && title != null) {
			type(ELEMENT_POST_TITLE, title, true);
		}
		if (reason != "" && reason != null) {
			type(ELEMENT_POST_REASON, reason, true);
		}
		if (message != "" && message != null) {
			if (this.plfVersion.equalsIgnoreCase("4.1"))
				inputDataToFrame(ELEMENT_POST_MESSAGE_FRAME_CKEDITOR, message,
						true, false);
			else
				// (this.plfVersion.equalsIgnoreCase("4.0"))
				inputDataToFrameInFrame(ELEMENT_POST_MESSAGE_FRAME_1,
						ELEMENT_POST_MESSAGE_FRAME_2, message, true, false);
			switchToParentWindow();
		}
		if (file.length > 0 && file[0] != "" && file[0] != null) {
			click(ELEMENT_ATTACH_FILE);
			waitForAndGetElement(ELEMENT_POPUP_UPLOAD_FILE);
			attachFile(file[0]);
			waitForElementNotPresent(ELEMENT_POPUP_UPLOAD_FILE);
		}
		if (groupName != "" && groupName != null && iconClass != ""
				&& iconClass != null) {
			click(ELEMENT_POST_ICONS_TAB);
			magTopic.chooseIcon(groupName, iconClass);
		}
		click(magTopic.ELEMENT_SUBMIT_BUTTON);
		waitForElementNotPresent(ELEMENT_POST_POPUP_EDIT);
		if (isElementPresent(ELEMENT_ALERT) == true) {
			click(By.linkText("OK"));
		}
		waitForAndGetElement(ELEMENT_POST_TITLE_TEXT.replace("${post}", title));
		waitForAndGetElement(ELEMENT_POST_CONTENT_TEXT.replace("${post}",
				message));
		info("Edit post successfully");
	}

	/**
	 * @author quynhpt
	 * @param postContent
	 * @param title
	 * @param reason
	 * @param message
	 * @param groupName
	 * @param iconClass
	 * @param file
	 * @throws InterruptedException
	 */
	public void editPostWithDecorate(WebDriver driver, String postContent,
			String title, String reason, String message, String decorate,
			String groupName, String iconClass, String... file) {
		By EDIT_POST = By.xpath(ELEMENT_POST_EDIT_BUTTON.replace(
				"${postContent}", postContent));

		info("Edit a post");
		click(EDIT_POST);
		waitForAndGetElement(ELEMENT_POST_POPUP_EDIT);
		if (title != "" && title != null) {
			type(ELEMENT_POST_TITLE, title, true);
		}
		if (reason != "" && reason != null) {
			type(ELEMENT_POST_REASON, reason, true);
		}
		if (message != "" && message != null) {
			if (this.plfVersion.equalsIgnoreCase("4.1")) {
				inputDataToFrame(ELEMENT_POST_MESSAGE_FRAME_CKEDITOR, message,
						true, false);
			} else {
				// (this.plfVersion.equalsIgnoreCase("4.0"))
				inputDataToFrameInFrame(ELEMENT_POST_MESSAGE_FRAME_1,
						ELEMENT_POST_MESSAGE_FRAME_2, message, true, false);
			}
			switchToParentWindow();
		}
		if (file.length > 0 && file[0] != "" && file[0] != null) {
			click(ELEMENT_ATTACH_FILE);
			waitForAndGetElement(ELEMENT_POPUP_UPLOAD_FILE);
			attachFile(file[0]);
			waitForElementNotPresent(ELEMENT_POPUP_UPLOAD_FILE);
		}
		if (groupName != "" && groupName != null && iconClass != ""
				&& iconClass != null) {
			click(ELEMENT_POST_ICONS_TAB);
			magTopic.chooseIcon(groupName, iconClass);
		}
		if (decorate != "")
			click(By.xpath(ELEMENT_CKEDITOR_DECORATE.replace("${name}",
					decorate)));
		click(magTopic.ELEMENT_SUBMIT_BUTTON);
		waitForElementNotPresent(ELEMENT_POST_POPUP_EDIT);
		if (isElementPresent(ELEMENT_ALERT) == true) {
			click(By.linkText("OK"));
		}

		waitForAndGetElement(ELEMENT_POST_TITLE_TEXT.replace("${post}", title));

		if (decorate == "Bold") {
			waitForAndGetElement(ELEMENT_POST_CONTENT_TEXT_BOLD.replace(
					"${post}", message));
		} else {
			waitForAndGetElement(ELEMENT_POST_CONTENT_TEXT.replace("${post}",
					message));
		}
		info("Edit post successfully");
	}

	/**
	 * function: delete a post
	 * 
	 * @author lientm
	 * @param content
	 *            : content of post that needs to delete
	 */
	public void deletePost(String content) {
		By DELETE_POST = By.xpath(ELEMENT_POST_DELETE_BUTTON.replace(
				"${postContent}", content));

		info("Delete a post");
		click(DELETE_POST);
		waitForMessage(MSG_DELETE_POST);
		click(ELEMENT_POST_OK_DELETE);

		waitForElementNotPresent(DELETE_POST);
		waitForElementNotPresent(ELEMENT_POST_CONTENT_TEXT.replace("${post}",
				content));
		info("Delete post successfully");
	}

	/**
	 * function: mode a post to other topic
	 * 
	 * @author lientm
	 * @param content
	 *            : content of post
	 * @param destination
	 *            : path to new topic
	 */
	public void movePost(String content, String destination) {
		By element_checkbox = By.xpath(ELEMENT_POST_CHECKBOX.replace(
				"${postContent}", content));
		check(element_checkbox, 2);
		info("Move post to topic " + destination);
		click(ELEMENT_MODERATION);
		click(ELEMENT_MOVE_POST);
		waitForAndGetElement(ELEMENT_POPUP_MOVE_POST);

		// click(ELEMENT_DATA_ORIGINAL_TITLE.replace("${title}",destination));
		// waitForElementNotPresent(ELEMENT_POPUP_MOVE_POST);
		// waitForElementNotPresent(element_checkbox);

		String[] temp;
		/* Delimiter */
		String delimiter = "/";

		temp = destination.split(delimiter);
		/* Go to group */
		for (int i = 0; i < temp.length - 1; i++) {
			info("Go to " + temp[i]);
			if (waitForAndGetElement(
					ELEMENT_MOVE_POST_NODE.replace("${node}", temp[i + 1]),
					3000, 0) == null)
				click(By.xpath(ELEMENT_MOVE_POST_NODE.replace("${node}",
						temp[i])));
			Utils.pause(500);
		}
		click(By.xpath(ELEMENT_MOVE_POST_NODE.replace("${node}",
				temp[temp.length - 1])));

		waitForElementNotPresent(ELEMENT_POPUP_MOVE_POST);
		// String links[] = destination.split("/");
		// int length = links.length;
		// waitForAndGetElement(By.xpath("//a[@title='" + links[length - 2] +
		// "']/../div[@title='" + links[length - 1] + "']"));
		info("Move post successfully");
	}

	/**
	 * function approve a post
	 * 
	 * @author thuntn
	 */
	public void approvePost(String post) {
		click(ELEMENT_MODERATION);
		info("--Approve post--");
		waitForAndGetElement(ELEMENT_APPROVE_POST);
		click(ELEMENT_APPROVE_POST);
		waitForAndGetElement(ELEMENT_APPROVE_FORM);
		check(By.xpath(ELEMENT_APPROVE_POST_CHECK.replace("${post}", post)), 2);
		Utils.pause(100);
		click(ELEMENT_APPROVE_POST_BUTTON);
	}

	/**
	 * Censor a post
	 * 
	 * @author thuntn
	 * @param post
	 */
	public void censorPost(String post) {
		By postCheck = By.xpath(ELEMENT_CENSOR_POST_CHECK.replace("${post}",
				post));

		info("--Approve a post that is pending by censor--");
		waitForAndGetElement(ELEMENT_MODERATION);
		click(ELEMENT_MODERATION);
		click(ELEMENT_CENSOR_POST);
		waitForAndGetElement(ELEMENT_APPROVE_POST_BUTTON);
		check(postCheck, 2);
		click(ELEMENT_APPROVE_POST_BUTTON);
		waitForElementNotPresent(ELEMENT_APPROVE_POST_BUTTON);
		info("--Approve a topic successfully--");
	}

	/**
	 * Quote a post
	 * 
	 * @author thuntn
	 * @param title
	 * @param content
	 */
	public void quotePost(String post, String title, String content) {
		info("Quote a post");

		click(ELEMENT_QUOTE_POST.replace("${post}", post));

		type(ELEMENT_POST_TITLE, title, true);
		if (this.plfVersion.equalsIgnoreCase("4.0"))
			inputDataToFrameInFrame(ELEMENT_POST_MESSAGE_FRAME_1,
					ELEMENT_POST_MESSAGE_FRAME_2, content, false);
		else
			waitForAndGetElement(ELEMENT_POST_MESSAGE_FRAME_CKEDITOR).sendKeys(content);
		switchToParentWindow();

		click(magTopic.ELEMENT_SUBMIT_BUTTON);

		waitForElementNotPresent(magTopic.ELEMENT_SUBMIT_BUTTON);
		waitForAndGetElement(ELEMENT_POST_CONTENT_TEXT.replace("${post}",
				content));
		waitForAndGetElement(ELEMENT_POST_QUOTE_TEXT.replace("${post}", post));
	}

	/**
	 * Quote a post
	 * 
	 * @author quynhpt
	 * @author thuntn
	 * @param title
	 * @param content
	 */
	public void quotePostWithDecorate(String post, String title,
			String content, String decorate) {
		info("Quote a post");

		click(ELEMENT_QUOTE_POST.replace("${post}", post));

		type(ELEMENT_POST_TITLE, title, true);
		if (this.plfVersion.equalsIgnoreCase("4.0"))
			inputDataToFrameInFrame(ELEMENT_POST_MESSAGE_FRAME_1,
					ELEMENT_POST_MESSAGE_FRAME_2, content, false);
		else
			inputDataToFrame(ELEMENT_POST_MESSAGE_FRAME_CKEDITOR, content);
		switchToParentWindow();
		click(By.xpath(ELEMENT_CKEDITOR_DECORATE.replace("${name}", decorate)));
		click(magTopic.ELEMENT_SUBMIT_BUTTON);

		waitForElementNotPresent(magTopic.ELEMENT_SUBMIT_BUTTON);
		waitForAndGetElement(ELEMENT_POST_CONTENT_TEXT.replace("${post}",
				content));
		waitForAndGetElement(ELEMENT_POST_QUOTE_TEXT.replace("${post}", post));
	}

	/**
	 * Get post counter of user
	 * 
	 * @param user
	 *            : username
	 * @return
	 */
	public Integer getPostCounter(String user) {
		String postProfile;
		Integer oldPostNum;
		goToUserManagement(user);
		click(ELEMENT_VIEW_USER_PROFILE_ICON.replace("${user}", user));
		postProfile = waitForAndGetElement(ELEMENT_USER_NUMBER_POST).getText();
		if (postProfile.contains(":"))
			oldPostNum = Integer.valueOf(postProfile.substring(
					postProfile.indexOf(":") + 1).trim());
		else
			oldPostNum = 0;
		click(ELEMENT_CLOSE_USER_PROFILE_BUTTON);
		waitForElementNotPresent(ELEMENT_CLOSE_USER_PROFILE_BUTTON);
		button.close();
		return oldPostNum;
	}
}
