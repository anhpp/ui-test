package org.exoplatform.selenium.testdata;

import org.exoplatform.selenium.DatabaseUtils;
import org.exoplatform.selenium.ExcelUtils;
import org.exoplatform.selenium.TestBase;
import org.exoplatform.selenium.Utils;
public class WikiDatabase {
	public String[] wikiTitle;
	public String[] wikiContent;
	public String[] linkAttach;
	public String[] color;
	public String[] msg;
	public String[] label;
	public String[] tooltip;
	public String[] row;
	public String[] column;
	public String[] template;

	public WikiDatabase(String[] wikiTitle, String[] wikiContent,String[] linkAttach,String[] color,String[] msg,String[] label,String[] tooltip,String[] row,String[] column,String[] template){
		this.wikiTitle = wikiTitle;
		this.wikiContent = wikiContent;
		this.linkAttach = linkAttach;
		this.color = color;
		this.msg = msg;
		this.label = label;
		this.tooltip = tooltip;
		this.row = row;
		this.column = column;
		this.template = template;
	}

	public WikiDatabase() {
		wikiTitle = new String[100];
		wikiContent = new String[100];
		linkAttach = new String[100];
		color = new String[100];
		msg = new String[100];
		label = new String[100];
		tooltip = new String[100];
		row = new String[100];
		column = new String[100];
		template = new String[100];
	}

	public void setWikiData(String userDataFile, String userSheet, Boolean isRandom, Object... opParams) throws Exception{
		String[][] testData = testData(userDataFile,userSheet,opParams);
		wikiTitle = new String[testData.length];
		wikiContent = new String[testData.length];
		linkAttach = new String[testData.length];
		if(isRandom){
			for(int i = 0; i<testData.length; i++)
			{	
				TestBase tBase = new TestBase();
				wikiTitle[i] = testData[i][0]+tBase.getRandomString();
				wikiContent[i] = testData[i][1]+tBase.getRandomString();
				linkAttach[i] = testData[i][2];
				color[i] = testData[i][3];
				msg[i] = testData[i][4];
				label[i] = testData[i][5];
				tooltip[i] = testData[i][6];
				row[i] = testData[i][7];
				column[i] = testData[i][8];
				template[i] = testData[i][9];
			}
		}
		else{
			for(int i = 0; i<testData.length; i++)
			{	
				wikiTitle[i] = testData[i][0];
				wikiContent[i] = testData[i][1];
				linkAttach[i] = testData[i][2];
				color[i] = testData[i][4];
				msg[i] = testData[i][5];
				label[i] = testData[i][6];
				tooltip[i] = testData[i][7];
				row[i] = testData[i][8];
				column[i] = testData[i][9];
				template[i] = testData[i][10];
			}
		}
	}

	public String[] getTitle(String userDataFile, String userSheet) throws Exception{
		String[][] testData = testData(userDataFile,userSheet);
		String []wTitle = new String[testData.length];
		for(int i = 0; i<testData.length; i++)
		{	
			wTitle[i] = testData[i][0];
		}
		return wTitle;
	}

	public String[] getContent(String userDataFile, String userSheet) throws Exception{
		String[][] testData = testData(userDataFile,userSheet);
		String []wContent = new String[testData.length];
		for(int i = 0; i<testData.length; i++)
		{	
			wContent[i] = testData[i][1];
		}
		return wContent;
	}

	public String[] getAttach(String userDataFile, String userSheet) throws Exception{
		String[][] testData = testData(userDataFile,userSheet);
		String []wAttach = new String[testData.length];
		for(int i = 0; i<testData.length; i++)
		{	
			wAttach[i] = testData[i][2];
		}
		return wAttach;
	}

	public String[] getSpace(String userDataFile, String userSheet) throws Exception{
		String[][] testData = testData(userDataFile,userSheet);
		String []wSpace = new String[testData.length];
		for(int i = 0; i<testData.length; i++)
		{	
			wSpace[i] = testData[i][3];
		}
		return wSpace;
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
