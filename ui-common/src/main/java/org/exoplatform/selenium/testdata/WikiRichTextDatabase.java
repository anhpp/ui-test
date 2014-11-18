package org.exoplatform.selenium.testdata;

import java.util.ArrayList;
import java.util.Random;

public class WikiRichTextDatabase {
	public ArrayList<Integer> type;
	public ArrayList<String> color;
	public ArrayList<String> msg;
	public ArrayList<String> label;
	public ArrayList<String> tooltip;
	public ArrayList<Integer> row;
	public ArrayList<Integer> column;

	public WikiRichTextDatabase(ArrayList<Integer> type, ArrayList<String> color,ArrayList<String> msg,ArrayList<String> label,ArrayList<String> tooltip,ArrayList<Integer> row,ArrayList<Integer> column){
		this.type = type;
		this.color = color;
		this.msg = msg;
		this.label = label;
		this.tooltip = tooltip;
		this.row = row;
		this.column = column;
	}

	public WikiRichTextDatabase() {
		type  = new ArrayList<Integer>();
		color  = new ArrayList<String>();
		msg  = new ArrayList<String>();
		label  = new ArrayList<String>();
		tooltip  = new ArrayList<String>();
		row  = new ArrayList<Integer>();
		column  = new ArrayList<Integer>();
	}

	public void setWikiData(String userDataFile, String userSheet,Object... opParams) throws Exception{
		String[][] testData = DatabaseResource.getDataFromSource(userDataFile,userSheet,opParams);
		for(int i = 0; i<testData.length-1; i++)
		{	
			type.add(Integer.valueOf(testData[i][0]));
			color.add(testData[i][1]);
			msg.add(testData[i][2]);
			label.add(testData[i][3]);
			tooltip.add(testData[i][4]);
			row.add(Integer.valueOf(testData[i][5]));
			column.add(Integer.valueOf(testData[i][6]));
			
		}
	}
	
	public Integer getRandomIndexByType(int type){
		Random randomGenerator = new Random();
		for(int i = 0; i<this.type.size(); i++)
		{	
			if(this.type.get(i) == type) {
				color.add(this.color.get(i));
			}
		}
		Integer index = randomGenerator.nextInt(color.size());
		return index;
	}
}
