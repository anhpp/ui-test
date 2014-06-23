package org.exoplatform.selenium.platform.wiki;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.PlatformPermission;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Provides all methods of managing the restrictions to apply on a wiki or a page and to specific users, groups or memberships.
 *
 *
 */
public class Permission extends WikiBase{

	//ManageAccount magAc = new ManageAccount(driver);
	Dialog dialog;
	PlatformPermission per;
	//Button button = new Button(driver);

	public Permission() {
		ieFlag = super.ieFlag;
	}
	
	public final String ELEMENT_PERMISSION = "//*[@id='UIPermissionGrid']//*[contains(text(),'${user}')]";

	public Permission(WebDriver dr){
		driver = dr;
	}
	
	/////
	/*=================== Page permission ===================*/
	/** 
	 * Add permission for a Wiki page
	 * 
	 * @param option
	 *  		if the value of option determines  meaning of the userGroups parameter
	 *  <br/>
	 * 			<li> option = 0: userGroup[0]= user
	 * 			<li> option = 1: userGroup[0]= user
	 * 			<li> option = 2: userGroup[0] = path of a group
	 * 			<li> option !=0,1,2: userGroup[0] = path of a group, userGroup[1] = membership
	 * @param userGroup
	 * 			user/group of users that will be set permission
	 * @param type
	 * 			optional parameter to search user
	 * 
	 * @see WikiBase#goToPagePermission()
	 * @see PlatformPermission#selectUserPermission(String, int...)
	 * @see PlatformPermission#selectGroupPermission(String)
	 * @see PlatformPermission#selectGroupMembership(String, String)				
	 */
	public void addPagePermission(int option, String[] userGroup, int... type){
		button = new Button(driver);
		per = new PlatformPermission(driver);
		info("--Add page permission for an user--");

		goToPagePermission();

		if(userGroup[0]!=null)
		{
			switch(option){

			case 0: type(ELEMENT_SELECT_INPUT,userGroup[0],true); break;
			case 1: click(ELEMENT_SELECT_USER);
			per.selectUserPermission(userGroup[0], type); break;
			case 2: click(ELEMENT_SELECT_GROUP);
			per.selectGroupPermission(userGroup[0]); break;
			default: if (userGroup[1] !=null) {
				click(ELEMENT_SELECT_MEMBERSHIP);
				per.selectGroupMembership(userGroup[0], userGroup[1]);
			}
			break;
			}		
			Utils.pause(1000);
			clickByJavascript(button.ELEMENT_ADD_BUTTON);
			Utils.pause(1000);
			button.save();
		}
		waitForElementNotPresent(button.ELEMENT_ADD_BUTTON);
	}

	/** 
	 * Edit permission for wiki's page
	 * 
	 * @param user
	 * 			 user name of user account that will be set permission 
	 * @param edit
	 * 			 to verify if edit action will be performed or not
	 * @param viewPage
	 * 			 page of which permission will be changed
	 * @param option
	 * 			 optional parameter
	 * 
	 * @see #addEditPagePermission(org.exoplatform.selenium.platform.ManageAccount.userType, String, By)
	 * @see WikiBase#goToPagePermission()
	 */
	public void editPagePermission(String user, boolean viewPage, boolean edit, Object... option){
		button = new Button(driver);
		By EDIT_PAGE = By.xpath(ELEMENT_EDIT_PAGE_PERMISSIONS.replace("${user}", user));
		By VIEW_PAGE = By.xpath(ELEMENT_VIEW_PAGE_PERMISSIONS.replace("${user}", user));
		By DELETE_PERMISSION = By.xpath(ELEMENT_DELETE_PERMISSIONS.replace("${user}", user));
		boolean deletePermission = (Boolean) (option.length > 0 ? option[0] : false);

		if (waitForAndGetElement(EDIT_PAGE, 5000, 0) == null){
			driver.navigate().refresh();
			Utils.pause(200);
			goToPagePermission();
		}

		info("--Add page permissions--");
		if (edit){
			check(EDIT_PAGE, 2);		}
		else{
			uncheck(EDIT_PAGE, 2);
		}
		if (viewPage){
			check(VIEW_PAGE, 2);
		}
		else{
			uncheck(VIEW_PAGE, 2);
		}
		if (deletePermission){
			click(DELETE_PERMISSION);
			waitForElementNotPresent(DELETE_PERMISSION);
		}
		button.save();
		Utils.pause(1000);
	}

	/** 
	 * Delete permission for an user
	 * 
	 * @param user 
	 * 			user name or group, or membership that will be deleted from Wiki page 
	 * 			<br/> eg: "*:/platform/users"
	 * 
	 * @see #deletePermissionWithUserAdmin(String, By)
	 * @see WikiBase#goToPagePermission()
	 */
	public void deletePagePermission(String user){
		button = new Button(driver);	
		By Delete = By.xpath(ELEMENT_DELETE_PERMISSION.replace("{$user}", user));

		goToPagePermission();

		info("--Delete permission--");

		if (waitForAndGetElement(Delete,10000,0) != null){
			clickByJavascript(Delete);
		}
		waitForElementNotPresent(Delete);
		button.save();
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		Utils.pause(2000);
	}

	/**
	 * Add or edit current permission of Wiki page
	 * 
	 * @param userType 
	 * 				Group Membership of user (i.e. Admin, Developer, etc...)
	 * @param user 
	 * 				user that we want to set a permission
	 * @param elementPage
	 * 				Link to a Wiki page
	 * 
	 * @see #editPagePermission(String, boolean, boolean, Object...)
	 * @see WikiBase#goToWiki()
	 * @see ManageAccount#userSignIn(org.exoplatform.selenium.platform.ManageAccount.userType)
	 * @see ManageAccount#signOut()
	 */
	public void addEditPagePermission(ManageAccount.userType userType, String user, By elementPage){
		magAcc = new ManageAccount(driver,this.plfVersion);
		info("Add edit page permission for " + user);
		//magAc.signIn(DATA_USER_ADMIN, DATA_PASS_ADMIN);
		magAcc.userSignIn(userType);
		goToWiki();
		click(elementPage);
		editPagePermission(user, true, true, false, 2);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		magAcc.signOut();	
	}

	/**
	 * Remove a Wiki page permission 
	 * 
	 * @param userType 
	 * 				Group Membership of user (i.e. Admin, Developer, etc...)
	 * @param user 
	 * 				user that we want to set a permission
	 * @param elementPage
	 * 				Link to a Wiki page
	 * 
	 * @see #editPagePermission(String, boolean, boolean, Object...)
	 * @see WikiBase#goToWiki()
	 */
	public void removePagePermission(ManageAccount.userType userType, By elementPage, String user){
		magAcc = new ManageAccount(driver,this.plfVersion);
		info("remove view/edit page permission");
		//magAc.signIn(DATA_USER_ADMIN, DATA_PASS_ADMIN);
		magAcc.userSignIn(userType);
		goToWiki();
		click(elementPage);
		editPagePermission(user, false, false, false, 2);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		magAcc.signOut();
	}

	/*================== Space Permissions ===================*/
	/**
	 * Add permission for Wiki page in Space
	 * 
	 * @param option
	 * 			 if the value of option determines  meaning of the userGroups parameter
	 * 				<li>option = 0: userGroup[0]= user
	 * 				<li>option = 1: userGroup[0]= user
	 * 				<li>option = 2: userGroup[0] = path of a group
	 * 				<li>option !=0,1,2: userGroup[0] = path of a group, userGroup[1] = membership
	 * @param groupUser
	 * 			 user or group of users that will be added into Wiki page permission list
	 * @param type 
	 * 			optional parameter
	 * 
	 * @see WikiBase#goToSpacePermission()
	 * @see PlatformPermission#selectUserPermission(String, int...)
	 * @see PlatformPermission#selectGroupPermission(String)
	 * @see PlatformPermission#selectGroupMembership(String, String)
	 */
	public void addSpacePermission(int option, String[] groupUser, Integer...type){
		button = new Button(driver);
		dialog = new Dialog(driver);
		per = new PlatformPermission(driver);
		//		if (type.length > 0){
		//			if (!(type[0] instanceof Integer)) {
		//				throw new IllegalArgumentException("-- Argument should be an Integer --");
		//			}
		//			notDisplay = (Integer)type[0];
		//		}

		goToSpacePermission();

		info("--Add space permission--");
		switch(option) {
		case 0: type(ELEMENT_SELECT_INPUT,groupUser[0],true);
		break;
		case 1:	click(ELEMENT_SELECT_USER);
		per.selectUserPermission(groupUser[0], 0);
		break;

		case 2: click(ELEMENT_SELECT_GROUP);
		per.selectGroupPermission(groupUser[0]);
		break;

		default: click(ELEMENT_SELECT_MEMBERSHIP);
		per.selectGroupMembership(groupUser[0], groupUser[1]);
		break;
		}
		click(button.ELEMENT_ADD_BUTTON);
		button.save();
		waitForMessage(MSG_PERMISSION_SAVE);

		dialog.closeMessageDialog();
		
		Utils.pause(1000);
	}

	/** 
	 * Edit a space permission for an user, or a group
	 * 
	 * @param userGroup
	 * 				username or group, or membership. eg: "*:/platform/users"
	 * @param view
	 * 				if set value as "true" , then this user/group have view permission and vice versa
	 * @param edit
	 * 				if set value as "true" , then this user/group have edit permission and vice versa
	 * @param adminPage
	 * 				if set value as "true" , then this user/group have admin page permission and vice versa
	 * @param adminSpace
	 * 				if set value as "true" , then this user/group have admin space permission and vice versa
	 * @param type
	 * 				optional parameter to verify or not display of user acc in page permission list
	 * 
	 * @see WikiBase#goToPagePermission()
	 */
	public void editSpacePermission(String userGroup,boolean view,boolean edit, boolean adminPage, 
			boolean adminSpace, Integer...type ){
		button = new Button(driver);
		dialog = new Dialog(driver);
		//By bEditPage = By.xpath(ELEMENT_EDIT_PAGE_CHECK.replace("{$user}", userGroup));
		By bEditPage = By.xpath(ELEMENT_EDIT_PAGE_PERMISSIONS.replace("${user}", userGroup));
		By bAdminPage = By.xpath(ELEMENT_ADMIN_PAGE_CHECK.replace("{$user}", userGroup));		
		By bAdminSpace = By.xpath(ELEMENT_ADMIN_SPACE_CHECK.replace("{$user}", userGroup));
		//By bViewSpace = By.xpath(ELEMENT_VIEW_SPACE_CHECK.replace("{$user}", userGroup));
		By bViewSpace = By.xpath(ELEMENT_VIEW_PAGE_PERMISSIONS.replace("${user}", userGroup));

		int notDisplay = 0;
		if (type.length > 0){
			if (!(type[0] instanceof Integer)) {
				throw new IllegalArgumentException("-- Argument should be an Integer --");
			}
			notDisplay = (Integer)type[0];
		}	

		goToSpacePermission();

		info("--Add space permission--");

		if (view){
			if(!waitForAndGetElement(bViewSpace, DEFAULT_TIMEOUT, 1, notDisplay).isSelected()){
				clickByJavascript(bViewSpace, notDisplay);
			}
		}else{
			if(waitForAndGetElement(bViewSpace, DEFAULT_TIMEOUT, 1, notDisplay).isSelected()){
				clickByJavascript(bViewSpace, notDisplay);
				Utils.pause(1000);
				assert !waitForAndGetElement(bEditPage,DEFAULT_TIMEOUT,1,notDisplay).isSelected();
				assert !waitForAndGetElement(bAdminPage,DEFAULT_TIMEOUT,1,notDisplay).isSelected();
				assert !waitForAndGetElement(bAdminSpace,DEFAULT_TIMEOUT,1,notDisplay).isSelected();
			}
		}
		if (edit){
			if(!waitForAndGetElement(bEditPage, DEFAULT_TIMEOUT, 1, notDisplay).isSelected()){
				clickByJavascript(bEditPage, notDisplay);
			}
		}else{
			if(waitForAndGetElement(bEditPage, DEFAULT_TIMEOUT, 1, notDisplay).isSelected()){
				clickByJavascript(bEditPage, notDisplay);
				Utils.pause(1000);
				assert !waitForAndGetElement(bAdminPage,DEFAULT_TIMEOUT,1,notDisplay).isSelected();
				assert !waitForAndGetElement(bAdminSpace,DEFAULT_TIMEOUT,1,notDisplay).isSelected();
			}
		}
		if (adminPage){
			if(!waitForAndGetElement(bAdminPage, DEFAULT_TIMEOUT, 1, notDisplay).isSelected()){
				clickByJavascript(bAdminPage, notDisplay);
			}
		}else{
			if(waitForAndGetElement(bAdminPage, DEFAULT_TIMEOUT, 1, notDisplay).isSelected()){
				clickByJavascript(bAdminPage, notDisplay);
				Utils.pause(1000);
				assert !waitForAndGetElement(bAdminSpace,DEFAULT_TIMEOUT,1,notDisplay).isSelected();
			}
		}

		if (adminSpace){
			if(!waitForAndGetElement(bAdminSpace, DEFAULT_TIMEOUT, 1, notDisplay).isSelected()){
				clickByJavascript(bAdminSpace, notDisplay);
			}
		}else{
			if(waitForAndGetElement(bAdminSpace, DEFAULT_TIMEOUT, 1, notDisplay).isSelected())
				clickByJavascript(bAdminSpace, notDisplay);
		}

		button.save();
		waitForMessage(MSG_PERMISSION_SAVE);
		dialog.closeMessageDialog();
		waitForTextNotPresent(MSG_PERMISSION_SAVE);
	}

	/** 
	 * Delete a space permission for an user, or a group
	 * 
	 * @param userGroup 
	 * 				username or group, or membership which will be deleted (i.e. "*:/platform/users")
	 * 
	 * @see WikiBase#goToSpacePermission()
	 */
	public void deleteSpacePermission(String userGroup){
		button = new Button(driver);
		dialog = new Dialog(driver);
		By bDelete = By.xpath(ELEMENT_DELETE_PERMISSION.replace("{$user}", userGroup));

		goToSpacePermission();
		if (waitForAndGetElement(bDelete, 10000, 0) != null){
			info("--Delete space permission--");
			click(bDelete);
			button.save();
			waitForMessage(MSG_PERMISSION_SAVE);
			dialog.closeMessageDialog();
		}
		waitForElementNotPresent(bDelete);
		Utils.pause(1000);
	}

	//////////
	/**
	 * Verify Space Wiki page permission
	 * 
	 * @param pageOrSpace
	 * 				 boolean type[0]: gotoPage, type[1]: gotoSpace
	 * @param type
	 * 				 boolean type[0]: viewPage, type[1]: editPage, type[2]: adminPage, type[3]: adminSpace
	 * @param username
	 * 				 user account that will be verified
	 * @param optional
	 * 				 optional parameter to verfiy or not the display of user in permission list
	 * 
	 * @see WikiBase#goToPagePermission()
	 */
	public void verifyPermissions(boolean[] pageOrSpace, boolean[] type, String username, Integer... optional){	
		button = new Button(driver);
		dialog = new Dialog(driver);
		int notDisplay = 0;
		if (optional.length > 0){
			if (!(optional[0] instanceof Integer)) {
				throw new IllegalArgumentException("-- Argument should be an Integer --");
			}
			notDisplay = (Integer)optional[0];
		}

		if (pageOrSpace[0]){
			goToPagePermission();
		}else if (pageOrSpace[1]){
			goToSpacePermission();
		}

		if ( type[0]) {
			info("Message: check view page permission is checked");
			assert waitForAndGetElement(ELEMENT_VIEW_PAGE_PERMISSIONS.replace("${user}", username), 5000, 1, notDisplay).isSelected();
		}else if (!type[0]){
			info("Message: check view page permission is un-checked");
			assert !waitForAndGetElement(ELEMENT_VIEW_PAGE_PERMISSIONS.replace("${user}", username), 5000, 1, notDisplay).isSelected();	
		}

		if ( type[1]) {
			info("Message: Check edit permission is checked");
			assert waitForAndGetElement(ELEMENT_EDIT_PAGE_PERMISSIONS.replace("${user}", username), 3000, 0, notDisplay).isSelected();
		}else if (!type[1]){
			info("Message: Check edit permission is un-checked");
			assert !waitForAndGetElement(ELEMENT_EDIT_PAGE_PERMISSIONS.replace("${user}", username), 3000, 0, notDisplay).isSelected();	
		}

		if ( type.length > 2 && type[2]){
			info("Messgage: Check admin page is checked");
			assert waitForAndGetElement(ELEMENT_ADMIN_PAGE_CHECK.replace("${user}", username), 3000, 0, notDisplay).isSelected();
		}else if (type.length > 2 && !type[2]){
			info("Message: Check admin page is un-checked");
			assert waitForAndGetElement(ELEMENT_ADMIN_PAGE_CHECK.replace("${user}", username), 3000, 0, notDisplay).isSelected();
		}

		if ( type.length > 2 && type[3]){
			info("Messgage: Check admin space is checked");
			assert waitForAndGetElement(ELEMENT_ADMIN_SPACE_CHECK.replace("${user}", username), 3000, 0, notDisplay).isSelected();
		}else if (type.length > 2 && !type[3]){
			info("Message: Check admin space is un-checked");
			assert waitForAndGetElement(ELEMENT_ADMIN_SPACE_CHECK.replace("${user}", username), 3000, 0, notDisplay).isSelected();
		}
		button.save();
		Utils.pause(1000);
		if (isTextPresent(MSG_PERMISSION_SAVE)){
			//waitForMessage(MSG_PERMISSION_SAVE);
			dialog.closeMessageDialog();
			waitForTextNotPresent(MSG_PERMISSION_SAVE);
		}
	}

	//////////
	/**
	 * Delete Wiki page permission with Admin acc
	 * 
	 * @param user
	 * 				User account that will be removed
	 * @param element_page
	 * 				Link to Wiki page
	 * 
	 * @see WikiBase#goToWiki()
	 * @see #deletePagePermission(String)
	 * @see ManageAccount#signOut()
	 */
	public void deletePermissionWithUserAdmin(String user, By element_page){
		magAcc = new ManageAccount(driver,this.plfVersion);
		info("Delete page permission of user/group " + user);
		//magAcc.signIn(DATA_USER_ADMIN, DATA_PASS_ADMIN);
		magAcc.userSignIn(ManageAccount.userType.ADMIN);
		goToWiki();
		click(element_page);
		deletePagePermission(user);
		magAcc.signOut();
	}

	/**
	 * Check view Wiki page
	 * 
	 * @param user
	 * 			Group membership of user defined by userType in class ManageAccount 
	 * @param element_page
	 * 			Link to Wiki page
	 * 
	 * @see WikiBase#goToWiki()
	 * @see ManageAccount#userSignIn(org.exoplatform.selenium.platform.ManageAccount.userType)
	 * @see ManageAccount#signOut()
	 */
	public void checkViewPage(ManageAccount.userType user, By element_page){
		magAcc = new ManageAccount(driver,this.plfVersion);
		info("Check user/group " + user + " does not have view/edit page permission");
		//magAcc.signIn(user, DATA_PASS_ADMIN);
		magAcc.userSignIn(user);
		goToWiki();
		waitForElementNotPresent(element_page);
		info("User/group can not view page");
		magAcc.signOut();
	}

	/**
	 * Check edit Wiki page
	 * 
	 * @param user
	 * 			 Group Membership of User (i.e. Admin, Developer, etc...)
	 * @param elementPage
	 * 			 Link to a Wiki page
	 * @param content
	 * 			 Content that will be updated to Wiki page
	 * 
	 * @see WikiBase#goToWiki()
	 * @see ManageAccount#signOut()
	 */
	public void checkEditPage(ManageAccount.userType user, By elementPage, String content){
		magAcc = new ManageAccount(driver,this.plfVersion);
		info("Check user can view page but does not have edit page");
		//magAc.signIn(user, DATA_PASS_ADMIN);
		magAcc.userSignIn(user);
		goToWiki();
		click(elementPage);
		waitForTextPresent(content);
		waitForElementNotPresent(ELEMENT_EDIT_PAGE_LINK);
		magAcc.signOut();
	}

	/**
	 * Check if user/group permission is not listed in Wiki page permission list
	 * 
	 * @param user
	 * 			user account that need to be checked
	 * 
	 * @see WikiBase#goToPagePermission()
	 * 
	 */
	public void checkUserNotInPagePermission(String user){
		button = new Button(driver);	
		By element_permission = By.xpath(ELEMENT_PERMISSION.replace("${user}", user));

		info("Check user/group permission is not listed in page permission list");
		goToPagePermission();
		waitForElementNotPresent(element_permission);
		if (isElementPresent(button.ELEMENT_CLOSE_BUTTON)){
			button.close();
		}else if (isElementPresent(By.xpath("//*[contains(@class, 'popupTitle') and text()='Page Permissions']/..//*[contains(@class, 'uiIconClose')]"))){
			click(By.xpath("//*[contains(@class, 'popupTitle') and text()='Page Permissions']/..//*[contains(@class, 'uiIconClose')]"));
		}else {
			click(button.ELEMENT_CANCEL_BUTTON);
		}
		//button.close();
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
	}
}
