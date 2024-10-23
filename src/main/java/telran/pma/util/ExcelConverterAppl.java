package telran.pma.util;
import java.io.*;
import java.util.Iterator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
public class ExcelConverterAppl {
public static final String LOGGER_NAME = "converter_logger";
public static final String LOGGER_LEVEL_ENV_VARIABLE = "LOGGER_LEVEL";
	public static void main(String[] args) throws IOException {
		String levelStr = System.getenv(LOGGER_LEVEL_ENV_VARIABLE);
		if(levelStr != null) {
			Configurator.setLevel(LOGGER_NAME, Level.valueOf(levelStr));
		}
		
		FileInputStream file = new FileInputStream(new File("protocol-pma.xlsx"));
		
		//Create Workbook instance holding reference to .xlsx file
		XSSFWorkbook workbook = new XSSFWorkbook(file);

		//Get first/desired sheet from the workbook
		XSSFSheet sheet = workbook.getSheetAt(0);

		//Iterate through each rows one by one
		Iterator<Row> rowIterator = sheet.iterator();
	//	rowIterator.next();
		while (rowIterator.hasNext()) {

		  Row row = rowIterator.next();

		  //For each row, iterate through all the columns
		  Iterator<Cell> cellIterator = row.cellIterator();
		
		  while (cellIterator.hasNext()) {

		    Cell cell = cellIterator.next();
		    //Check the cell type and format accordingly
		    switch (cell.getCellType()) {
		      case  NUMERIC:
		        System.out.print(String.format("value: %s type: %s row: %s column: %c ***",cell.getNumericCellValue(),
		        		cell.getCellType(), cell.getRowIndex(), 'A' + cell.getColumnIndex()));
		        break;
		      case STRING:
		    	  System.out.print(String.format("value: %s type: %s row: %s column: %c ***",cell.getStringCellValue(),
			        		cell.getCellType(), cell.getRowIndex(), 'A' + cell.getColumnIndex()));
		        break;
			default:
				break;
		    }
		  }
		  System.out.println();
		}
		file.close();
		workbook.close();

	}

}
