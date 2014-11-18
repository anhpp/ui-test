package org.exoplatform.selenium.testdata;

import java.util.ArrayList;

public class UserDatabase {
	public ArrayList<Integer> type;
	public ArrayList<String> userName;
	public ArrayList<String> password;
	public ArrayList<String> email;

	public UserDatabase(ArrayList<Integer> type, ArrayList<String> userName, ArrayList<String> password, ArrayList<String> email){
		this.type = type;
		this.userName = userName;
		this.password = password;
		this.email = email;
	}

	public UserDatabase(){
		type  = new ArrayList<Integer>();
		userName  = new ArrayList<String>();
		password  = new ArrayList<String>();
		email  = new ArrayList<String>();
	}

	public void setUserData(String userDataFile, String userSheet, Object... opParams) throws Exception{
		String[][] testData = DatabaseResource.getDataFromSource(userDataFile,userSheet,opParams);
		for(int i = 0; i<testData.length-1; i++)
		{	
			type.add(Integer.valueOf(testData[i][0]));
			userName.add(testData[i][1]);
			password.add(testData[i][2]);
			email.add(testData[i][3]);
		}
	}
}
