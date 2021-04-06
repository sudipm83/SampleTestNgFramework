package util.reader.excel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import util.exception.NoMatchingNameException;

public class ExcelUtil {
	
	private String filePath;
	
	public ExcelUtil(String relativePathStr)
	{
		if(StringUtils.isBlank(relativePathStr))
		{
			this.filePath = relativePathStr;
		}
	}
	
	private int getNumberOfUniqueIteration(List<Integer> matchingTestCases, XSSFWorkbook workbook, XSSFSheet sheet) throws NumberFormatException, IOException
	{
		int totalUniqueIteration = 0;
		
		for(int rowIndex = 0; rowIndex < matchingTestCases.size(); ++rowIndex)
		{
			int iteration = Integer.valueOf(this.getCellValue((Integer) matchingTestCases.get(rowIndex), 1,workbook,sheet));
			int subIteration = Integer.valueOf(this.getCellValue((Integer) matchingTestCases.get(rowIndex), 2, workbook, sheet));
			
			if(rowIndex >= matchingTestCases.size() -1)
			{
				++totalUniqueIteration;
			}
			else
			{
				int nextIteration = Integer.valueOf(this.getCellValue((Integer) matchingTestCases.get(rowIndex+1),1,workbook,sheet));
				int nextSubIteration = Integer.valueOf(this.getCellValue((Integer) matchingTestCases.get(rowIndex+1), 2,workbook, sheet));
				if(iteration == nextIteration && subIteration == nextSubIteration || nextIteration > iteration || nextIteration == 1 && nextSubIteration == 1)
				{
					++totalUniqueIteration;
				}
			}
		}
		return totalUniqueIteration;
	}
	
	private int getRowContains(String testCaseNameStr, int colNumInt, XSSFWorkbook workbook, XSSFSheet sheet) throws IOException
	{
		int rowNumber = 0;
		int rowCount = this.getRowUsed(sheet);
		
		for(int i = 0; i<rowCount; ++i)
		{
			if(this.getCellValue(i, colNumInt, workbook, sheet).equalsIgnoreCase(testCaseNameStr))
			{
				rowNumber = i;
				break;
			}
		}
		return rowNumber;
	}
	
	private int getRowUsed(XSSFSheet sheet)
	{
		int rowCount = 0;
		rowCount = sheet.getLastRowNum();
		return rowCount;
	}
	
	public XSSFSheet getExcelWSheetFile(String sheetNameStr) throws IOException
	{
		FileInputStream fileInputStream = new FileInputStream(this.filePath);
		XSSFWorkbook workbook = this.getXSSFWorkbook(sheetNameStr, fileInputStream);
		return workbook.getSheet(sheetNameStr);
	}
	
	
	
	
	
	
	public Object[][] getAllMatchingTestCases(String sheetNameStr, String testCaseNameStr) throws IOException, NoMatchingNameException
	{
		Object[][] tabArray = (Object[][])null;
		FileInputStream fileInputStream = new FileInputStream(this.filePath);
		XSSFWorkbook workbook = this.getXSSFWorkbook(sheetNameStr, fileInputStream);
		XSSFSheet sheet = workbook.getSheet(sheetNameStr);
		if(sheet == null)
		{
			throw new NoMatchingNameException("sheet", sheetNameStr);
		}
		else
		{
			XSSFRow row = sheet.getRow(0);
			int startRowInt = 0;
			int startColInt = 1;
			List<Integer> rowIndices = this.getMatchingRowIndices(testCaseNameStr, workbook, sheet);
			int totalColsInt = row.getLastCellNum() - 1;
			tabArray = new Object[rowIndices.size()][startRowInt + 1];
			
			for(int rowIndex = startRowInt; rowIndex < rowIndices.size(); ++rowIndex)
			{
				Map<String, String> columnData = new HashMap();
				
				for(int colIndex = startColInt; rowIndex <= totalColsInt; ++colIndex)
				{
					columnData.put(this.getCellValue(0, colIndex, workbook, sheet), this.getCellValue((Integer) rowIndices.get(colIndex),colIndex,workbook,sheet));
					columnData.put("Currentrow", String.valueOf(rowIndices.get(rowIndex)));
				}
				tabArray[rowIndex][0] = columnData;
			}
			
			fileInputStream.close();
			return tabArray;
			
		}
		
	}
	
	
	
	
	
	
	private List<Integer> getMatchingRowIndices(String testCaseNameStr, XSSFWorkbook workbook, XSSFSheet sheet) throws NoMatchingNameException
	{
		int totalRowsInt = sheet.getLastRowNum();
		List<Integer> rowIndices = new ArrayList();
		
		for(int rowindex = 1; rowindex<= totalRowsInt; ++rowindex)
		{
			if(this.getCellValue(rowindex, 0, workbook, sheet).equalsIgnoreCase(testCaseNameStr))
			{
				rowIndices.add(rowindex);
			}
		}
		
		if(rowIndices.isEmpty())
		{
			throw new NoMatchingNameException("test case", testCaseNameStr);
		}
		else
		{
			return rowIndices;
		}
	}
	
	private int getColumnNumber(String columnNameStr, XSSFWorkbook workbook, XSSFSheet sheet) throws IOException
	{
		int columnNumberInt = -1;
		XSSFRow row = sheet.getRow(0);
		int totalColsInt = row.getLastCellNum() -1;
		
		for(int colIndex = 0; colIndex <= totalColsInt; ++colIndex)
		{
			if(columnNameStr.equals(this.getCellValue(0, colIndex, workbook, sheet)))
			{
				columnNumberInt = colIndex;
			}
		}
		return columnNumberInt;
	}
	
	
	private List<Integer> getMatchingRowIndicesWithFilter(String testCaseNameStr, String filterNameStr, String filterValueStr, XSSFWorkbook workbook, XSSFSheet sheet) throws NoMatchingNameException, IOException
	{
		int totalRowsInt = sheet.getLastRowNum();
		List<Integer> rowIndices = new ArrayList();
		int filtercolInt = this.getColumnNumber(filterNameStr, workbook, sheet);
		if(filtercolInt == -1)
		{
			throw new NoMatchingNameException("column", filterNameStr);
		}
		else
		{
			for(int rowIndex = 1; rowIndex <= totalRowsInt; ++rowIndex)
			{
				if(this.getCellValue(rowIndex, 0, workbook, sheet).equalsIgnoreCase(testCaseNameStr) && this.getCellValue(rowIndex, filtercolInt, workbook, sheet).contentEquals(filterValueStr))
				{
					rowIndices.add(rowIndex);
				}
			}
			if(rowIndices.isEmpty())
			{
				throw new NoMatchingNameException("test case", testCaseNameStr);
			}
			else
			{
				return rowIndices;
			}
		}
	}
	
	
	private String getCellValue(int rowNumInt, int colNUmInt, XSSFWorkbook workbook, XSSFSheet sheet)
	{
		String cellValueStr = null;
		XSSFCell cell = sheet.getRow(rowNumInt).getCell(colNUmInt);
		FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
		cellValueStr = this.getCellValueAsString(cell, formulaEvaluator);
		return cellValueStr;
	}
	
	private String getCellValueAsString(XSSFCell xssfCell, FormulaEvaluator formulaEvaluator)
	{
		String cellValue = null;
		if(xssfCell != null && xssfCell.getCellType() != 3)
		{ if(formulaEvaluator.evaluate(xssfCell).getCellType() == 5)
			{
			throw new IllegalStateException("Error in formula within this cell. Error code : "+xssfCell.getErrorCellValue());
			}
		
		DataFormatter dataFormatter = new DataFormatter();
		cellValue = dataFormatter.formatCellValue(formulaEvaluator.evaluateInCell(xssfCell));
	}
	else
	{
		cellValue = "";
	}
	return cellValue;
}
	
	
	
	private XSSFWorkbook getXSSFWorkbook(String sheetNameStr, FileInputStream fileInputStream) throws IOException
	{
		XSSFWorkbook workbook;
		if(this.filePath.toUpperCase().endsWith("CSV"))
		{
			workbook = this.csvToXSSFWorkbook(sheetNameStr);
		}
		else
		{
			workbook = new XSSFWorkbook(fileInputStream);
		}
		return workbook;
	}

	private XSSFWorkbook csvToXSSFWorkbook(String sheetNameStr) throws IOException
	{
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(sheetNameStr);
		String currentLine = null;
		int rowNum = 0;
		
		BufferedReader bufferReader;
		for(bufferReader = new BufferedReader(
				new FileReader(this.filePath)); (currentLine = bufferReader.readLine()) != null; ++rowNum)
		{
			String[] str = currentLine.split(",");
			XSSFRow currentRow = sheet.createRow(rowNum);
			
			for(int i=0; i<str.length; ++i)
			{
				currentRow.createCell(i).setCellValue(str[i]);
			}
		}
		bufferReader.close();
		return workbook;
	}
	
	
	
}
