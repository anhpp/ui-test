package org.exoplatform.selenium.testdata;

import org.exoplatform.selenium.DatabaseUtils;
import org.exoplatform.selenium.ExcelUtils;
import org.exoplatform.selenium.TestBase;
import org.exoplatform.selenium.Utils;
public class SpaceDatabase {
	public String[] spaceName;
	public String[] spaceDes;
	public String[] spaceVis;
	public String[] spaceReg;

	public SpaceDatabase(String[] spaceName, String[] spaceDes,String[] spaceVis,String[] spaceReg){
		this.spaceName = spaceName;
		this.spaceDes = spaceDes;
		this.spaceVis = spaceVis;
		this.spaceReg = spaceReg;
	}

	public SpaceDatabase() {
		spaceName = new String[100];
		spaceDes = new String[100];
		spaceVis = new String[100];
		spaceReg = new String[100];
	}

	public void setSpaceData(String userDataFile, String userSheet, Boolean isRandom, Object... opParams) throws Exception{
		String[][] testData = testData(userDataFile,userSheet,opParams);
		spaceName = new String[testData.length];
		spaceDes = new String[testData.length];
		spaceVis = new String[testData.length];
		spaceReg = new String[testData.length];
		if(isRandom){
			for(int i = 0; i<testData.length; i++)
			{	
				TestBase tBase = new TestBase();
				spaceName[i] = testData[i][0]+tBase.getRandomString();
				spaceDes[i] = testData[i][1]+tBase.getRandomString();
				spaceVis[i] = testData[i][2];
				spaceReg[i] = testData[i][3];
			}
		}
		else{
			for(int i = 0; i<testData.length; i++)
			{	
				spaceName[i] = testData[i][0];
				spaceDes[i] = testData[i][1];
				spaceVis[i] = testData[i][2];
				spaceReg[i] = testData[i][3];
			}
		}
	}
	
	public static String[][] testData(String userDataFile, String userSheet, Object... opParams) throws Exception{
		Boolean isUseFile = (Boolean)(opParams.length > 0 ? opParams[0]: true);
		String jdbcDriver = (String)(opParams.length > 1 ? opParams[1]: "");
		String dbUrl = (String)(opParams.length > 2 ? opParams[2]: "");
		String user = (String)(opParams.length > 3 ? opParams[3]: "");
		String pass = (String)(opParams.length > 4 ? opParams[4]: "");
		String sqlDb = (String)(opParams.length > 5 ? opParams[5]: "");
		String[][] arrayData = null;
		if(isUseFile){
			ExcelUtils.setExcelFile(Utils.getAbsoluteFilePath(userDataFile),userSheet);
			arrayData = ExcelUtils.getData();
		}
		else{
			DatabaseUtils.connectDatabase(jdbcDriver, dbUrl, user, pass);
			arrayData=DatabaseUtils.getData(sqlDb);
		}
		return arrayData;
	}
}
