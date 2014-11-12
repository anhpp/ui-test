package org.exoplatform.selenium.platform;

import static org.exoplatform.selenium.TestLogger.info;

import java.util.Map;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PortalManagement extends PlatformBase {


	NavigationToolbar nav = new NavigationToolbar(driver);
	Dialog dialog = new Dialog(driver);
	ManageAlert alt = new ManageAlert(driver);
	Button button;
	public By ELEMENT_PORTAL_NAME_EXIST = By.xpath("//span[contains(text(),'This portal name already exists.')]");
	public By ELEMENT_POPUP_ADD_PORTAL = By.xpath("//div[@class='UIPopupWindow UIDragObject uiPopup']");
	public final String MESSAGE_PORTAL_NAME_REQUIRED_FIELD = "The field \"Portal Name\" is required.";

	public void configPortal(String portalName, String label, String description, String portalLocale, String portalSkin, String portalSession, 
			boolean publicMode, Map<String, String> permissions, String editGroupId, String editMembership, String...template){
		button = new Button(driver);
		if (portalName != null){
			type(ELEMENT_INPUT_NAME, portalName, true);
		}
		if (label != null){
			type(ELEMENT_PORTAL_LABEL, label, true);
		}
		if (description != null){
			type(ELEMENT_PORTAL_DESCRIPTION, description, true);
		}
		if (portalLocale != null){
			select(ELEMENT_SELECT_LOCALE, portalLocale);
		}
		if (portalSkin != null){
			select(ELEMENT_SELECT_SKIN, portalSkin);
		}
		if (portalSession != null){
			click(ELEMENT_PROPERTIES_TAB);
			select(ELEMENT_SELECT_SESSION_ALIVE, portalSession);
		}
		click(ELEMENT_PERMISSION_SETTING_TAB);
		if (publicMode) {
			check(ELEMENT_CHECKBOX_PUBLIC_MODE, 2);
			waitForElementNotPresent(ELEMENT_ADD_PERMISSION_BUTTON);
		} else {
			for (String key : permissions.keySet()) {
				setViewPermissions(key, permissions.get(key));
			}
		}
		if(editGroupId!=null && editMembership!=null){
			click(ELEMENT_EDIT_PERMISSION_SETTING);
			setEditPermissions(editGroupId, editMembership);
		}
		if (template.length > 0){
			click(ELEMENT_PORTAL_TEMPLATE_TAB);
			WebElement temp = getElementFromTextByJquery(template[0]);
			temp.click();
		}
		button.save();
		Utils.pause(2000);
		if (waitForAndGetElement(ELEMENT_POPUP_ADD_PORTAL,10000,0) == null)
			waitForElementNotPresent(ELEMENT_EDIT_PERMISSION_SETTING,120000);
	}

	//Add new portal
	public void addNewPortal(String portalName, String label, String description, String portalLocale, String portalSkin, String portalSession, 
			boolean publicMode, Map<String, String> permissions, String editGroupId, String editMembership, String...template){
		button = new Button(driver);

		info("--Create new portal--");
		click(ELEMENT_ADD_NEW_PORTAL_LINK);
		configPortal(portalName, label, description, portalLocale, portalSkin, portalSession, publicMode, permissions, editGroupId, editMembership, template);
	}

	//Edit a portal
	public void editPortal(String portalName, String label, String description, String portalLocale, String portalSkin, String portalSession, 
			boolean publicMode, Map<String, String> permissions, String editGroupId, String editMembership){
		info("--Create new portal--");

		goToEditSiteConfiguration(portalName);
		configPortal(null, label, description, portalLocale, portalSkin, portalSession, publicMode, permissions, editGroupId, editMembership);
	}

	//Delete a portal	
	public void deletePortal(String portalName){
		alt = new ManageAlert(driver);

		String portalDeleteIcon = ELEMENT_PORTAL_DELETE_ICON.replace("${portalName}", portalName);
		info("--Delete portal (" + portalName + ")--");		
		click(portalDeleteIcon);
		alt.waitForConfirmation("Are you sure you want to delete this portal?");
		//info("--Verify portal is deleted--");
		//		pause(30000);
		waitForElementNotPresent(ELEMENT_PORTAL_DELETE_ICON.replace("${portalName}", portalName), 180000);
	}

	//Verify the existence of portal
	public void verifyPortalExists(String portalName) {
		String portal = ELEMENT_PORTAL_IN_LIST.replace("${portalName}", portalName);

		info("--Verify portal (" + portalName + ") exists--");
		nav.goToPortalSites();
		waitForAndGetElement(portal);
	}

	//Go to edit layout of a portal
	public void goToPortalEditLayout(String portalName){
		info("Go to edit layout of portal" + portalName);
		click(ELEMENT_PORTAL_EDIT_LAYOUT.replace("${siteName}", portalName));
		Utils.pause(3000);
		waitForAndGetElement(ELEMENT_EDIT_INLINE_COMPOSER);
	}

	//Go to edit configuration of portal
	public void goToEditSiteConfiguration(String portalName){
		info("Go to edit configuration of portal" + portalName);
		String editIcon = ELEMENT_PORTAL_EDIT_CONFIGURATION.replace("${siteName}", portalName);		
		click(editIcon);
	}
}