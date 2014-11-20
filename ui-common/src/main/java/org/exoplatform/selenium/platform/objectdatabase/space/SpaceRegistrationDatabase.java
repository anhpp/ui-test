package org.exoplatform.selenium.platform.objectdatabase.space;

import java.util.ArrayList;
import java.util.Random;

import org.exoplatform.selenium.platform.objectdatabase.common.DatabaseResource;

public class SpaceRegistrationDatabase {
	public ArrayList<String> spaceReg;

	public SpaceRegistrationDatabase(ArrayList<String> spaceReg){
		this.spaceReg = spaceReg;
	}

	public SpaceRegistrationDatabase() {
		spaceReg  = new ArrayList<String>();
	}

	public void setSpaceRegistrationData(String userDataFile, String userSheet, Object... opParams) throws Exception{
		String[][] testData = DatabaseResource.getDataFromSource(userDataFile,userSheet,opParams);
		for(int i = 0; i<testData.length-1; i++)
		{	
			spaceReg.add(testData[i][0]);
		}
	}
	
	public String getSpaceRegistration(int index){
		return spaceReg.get(index);
	}

	public String getSpaceRegistrationRandom(){
		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(this.spaceReg.size());
		return this.spaceReg.get(index);
	}
}
