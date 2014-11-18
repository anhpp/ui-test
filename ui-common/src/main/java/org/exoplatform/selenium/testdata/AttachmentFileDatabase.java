package org.exoplatform.selenium.testdata;

import java.util.ArrayList;
import java.util.Random;
public class AttachmentFileDatabase {
	public ArrayList<Integer> type;
	public ArrayList<String> attachName;

	public AttachmentFileDatabase(ArrayList<Integer> type, ArrayList<String> attachName){
		this.type = type;
		this.attachName = attachName;
	}

	public AttachmentFileDatabase(){
		type  = new ArrayList<Integer>();
		attachName  = new ArrayList<String>();
	}

	public void setAttachFileData(String userDataFile, String userSheet, Object... opParams) throws Exception{
		String[][] testData = DatabaseResource.getDataFromSource(userDataFile,userSheet,opParams);
		for(int i = 0; i<testData.length-1; i++)
		{	
			type.add(Integer.valueOf(testData[i][0]));
			attachName.add(testData[i][1]);
		}
	}

	public String getAttachFileByIndex(int index){
		return attachName.get(index);
	}

	public ArrayList<String> getAttachFileByType(int type){
		ArrayList<String> arrayAttachFile = new ArrayList<String>();
		for(int i = 0; i<this.type.size(); i++)
		{	
			if(this.type.get(i) == type) {
				arrayAttachFile.add(this.attachName.get(i));
			}
		}
		return arrayAttachFile;
	}

	public String getAttachFileByTypeRandom(int type){
		ArrayList<String> arrayAttachFile = new ArrayList<String>();
		Random randomGenerator = new Random();
		for(int i = 0; i<this.type.size(); i++)
		{	
			if(this.type.get(i) == type) {
				arrayAttachFile.add(this.attachName.get(i));
			}
		}
		int index = randomGenerator.nextInt(arrayAttachFile.size());
		String attachFile = arrayAttachFile.get(index);
		return attachFile;
	}
}
