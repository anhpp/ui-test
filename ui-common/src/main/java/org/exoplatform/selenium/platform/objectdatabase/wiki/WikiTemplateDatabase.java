package org.exoplatform.selenium.platform.objectdatabase.wiki;

import java.util.ArrayList;
import java.util.Random;

import org.exoplatform.selenium.platform.objectdatabase.common.DatabaseResource;

public class WikiTemplateDatabase {
	public ArrayList<String> wikiTemp;

	public WikiTemplateDatabase(ArrayList<String> wikiTemp){
		this.wikiTemp = wikiTemp;
	}

	public WikiTemplateDatabase() {
		wikiTemp  = new ArrayList<String>();
	}

	public void setWikiTemplateData(String userDataFile, String userSheet, Object... opParams) throws Exception{
		String[][] testData = DatabaseResource.getDataFromSource(userDataFile,userSheet,opParams);
		for(int i = 0; i<testData.length-1; i++)
		{	
			wikiTemp.add(testData[i][0]);
		}
	}
	
	public String getWikiTemplate(int index){
		return wikiTemp.get(index);
	}

	public String getWikiTemplateRandom(){
		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(this.wikiTemp.size());
		return this.wikiTemp.get(index);
	}
}
