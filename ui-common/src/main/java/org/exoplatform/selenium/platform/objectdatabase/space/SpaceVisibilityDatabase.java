package org.exoplatform.selenium.platform.objectdatabase.space;

import java.util.ArrayList;
import java.util.Random;

import org.exoplatform.selenium.platform.objectdatabase.common.DatabaseResource;

public class SpaceVisibilityDatabase {
	public ArrayList<String> spaceVis;

	public SpaceVisibilityDatabase(ArrayList<String> spaceReg){
		this.spaceVis = spaceReg;
	}

	public SpaceVisibilityDatabase() {
		spaceVis  = new ArrayList<String>();
	}

	public void setSpaceVisibleData(String userDataFile, String userSheet, Object... opParams) throws Exception{
		String[][] testData = DatabaseResource.getDataFromSource(userDataFile,userSheet,opParams);
		for(int i = 0; i<testData.length-1; i++)
		{	
			spaceVis.add(testData[i][0]);
		}
	}
	
	public String getSpaceVisible(int index){
		return spaceVis.get(index);
	}

	public String getSpaceVisibleRandom(){
		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(this.spaceVis.size());
		return this.spaceVis.get(index);
	}
}
