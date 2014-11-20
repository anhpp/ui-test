package org.exoplatform.selenium.platform.objectdatabase.common;

import java.io.FileInputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import static org.exoplatform.selenium.TestLogger.info;

public class ExcelUtils {
	private static HSSFSheet ExcelWSheet;
	private static HSSFWorkbook ExcelWBook;
	private static HSSFCell Cell;
	//This method is to set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method
	public static void setExcelFile(String Path,String SheetName) throws Exception {
		try {
			// Open the Excel file
			FileInputStream ExcelFile = new FileInputStream(Path);
			// Access the required test data sheet
			ExcelWBook = new HSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
		} catch (Exception e){
			throw (e);
		}
	}
	//This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num
	public static String getCellData(int RowNum, int ColNum) throws Exception{
		try{
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			String CellData = Cell.getStringCellValue();
			return CellData;
		}catch (Exception e){
			return"";
		}
	}

	public static String[][] getData(){
		int xRows = ExcelWSheet.getLastRowNum()+1;
		info(String.valueOf(xRows));
		int xCols = ExcelWSheet.getRow(0).getLastCellNum()+1;
		info("number col: " + xCols);
		int nRow = 1;
		String[][] xData = new String[xRows][xCols];

		for (int i = 0; i < xRows-1; i++) {
			HSSFRow row = ExcelWSheet.getRow(nRow);
			for (int j = 0; j < xCols-1; j++) {
				HSSFCell cell = row.getCell(j);
				if(cell==null)
					continue;
				else{
					if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
						String value = String.valueOf((int)Math.round(cell.getNumericCellValue()));
						xData[i][j] = value;
					}
					else{//(cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
						String value =  cell.getStringCellValue();
						xData[i][j] = value;
					}
				}
				info(xData[i][j]);
			}
			nRow++;
		}
		return xData;
	}
}
