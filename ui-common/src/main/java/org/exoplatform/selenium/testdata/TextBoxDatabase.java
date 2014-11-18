package org.exoplatform.selenium.testdata;

import java.util.ArrayList;
import java.util.Random;

public class TextBoxDatabase {
	public ArrayList<Integer> type;
	public ArrayList<String> content;

	public TextBoxDatabase(ArrayList<Integer> type, ArrayList<String> content){
		this.type = type;
		this.content = content;
	}

	public TextBoxDatabase(){
		type  = new ArrayList<Integer>();
		content  = new ArrayList<String>();
	}

	public void setContentData(String userDataFile, String userSheet, Object... opParams) throws Exception{
		String[][] testData = DatabaseResource.getDataFromSource(userDataFile,userSheet,opParams);
		for(int i = 0; i<testData.length-1; i++)
		{	
			type.add(Integer.valueOf(testData[i][0]));
			content.add(testData[i][1]);
		}
	}

	public String getContentByIndex(int index){
		return content.get(index);
	}

	public ArrayList<String> getContentByType(int type){
		ArrayList<String> arrayContent = new ArrayList<String>();
		for(int i = 0; i<this.type.size(); i++)
		{	
			if(this.type.get(i) == type) {
				arrayContent.add(this.content.get(i));
			}
		}
		return arrayContent;
	}

	public String getContentByTypeRandom(int type){
		ArrayList<String> arrayContent = new ArrayList<String>();
		Random randomGenerator = new Random();
		for(int i = 0; i<this.type.size(); i++)
		{	
			if(this.type.get(i) == type) {
				arrayContent.add(this.content.get(i));
			}
		}
		int index = randomGenerator.nextInt(arrayContent.size());
		String content = arrayContent.get(index);
		return content;
	}
}
