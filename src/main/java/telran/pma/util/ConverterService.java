package telran.pma.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONArray;
import org.json.JSONObject;

public class ConverterService {
	private HashMap<String, JSONArray> jsonObjects;
	private HashMap<Character, ColumnDefinition> columns;
	private ConverterService(HashMap<String, JSONArray> jsonObjects, 
			HashMap<Character, ColumnDefinition> columns) {
		this.jsonObjects = jsonObjects;
		this.columns = columns;
	}
	private static Logger logger = LogManager.getLogger(ExcelConverterAppl.LOGGER_NAME);
	private static ConverterService converterService = null;
	public static synchronized ConverterService getConverterService(Row titles) {
		
		if (converterService == null) {
			HashMap<String, JSONArray> jsonObjects = new HashMap<>();
			HashMap<String, Consumer<Cell>> handlers = ConverterSchema.getHandlers(jsonObjects);
			logger.debug("handlers: {}", handlers);
			String [] titlesArr = getTitlesArray();
			checkSchema(handlers, titlesArr);
			HashMap<Character,ColumnDefinition> columns = getColumns(handlers, titlesArr);
			converterService = new ConverterService(jsonObjects, columns);
			
		}
		return converterService;
	}
	private static HashMap<Character, ColumnDefinition> getColumns(HashMap<String, Consumer<Cell>> handlers,
			String[] titlesArr) {
		// TODO Auto-generated method stub
		return null;
	}
	private static void checkSchema(HashMap<String, Consumer<Cell>> handlers, String[] titlesArr) {
		// TODO Auto-generated method stub
		
	}
	private static String[] getTitlesArray() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
