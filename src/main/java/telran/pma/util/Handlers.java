package telran.pma.util;

import org.apache.poi.ss.usermodel.Cell;
import org.json.JSONObject;

public class Handlers {
public static void simpleStringHandler(Cell cell, JSONObject jsonObj, String key) {
	jsonObj.put(key, cell.getStringCellValue());
}
}
