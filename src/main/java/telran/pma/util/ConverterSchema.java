package telran.pma.util;

import java.util.*;
import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Cell;
import org.json.*;

public class ConverterSchema {
	private static final String FIRST = "first";
	private static final String SECOND = "first";
	private static final String DRUG = "drug";
	private static final String ACTIVE_MOIETY = "active_moiety";
	private static final String DOSING = "dosing";
	private static final String MAX_THRESHOULD = "max_threshold";
	private static final String MIN_THRESHOULD = "min_threshold";
	private static final String AVOID = "avoid";
	private static final String AGE_ADJUSTMENT = "age_adjustment";
	private static String currentPainLevel;
	private static int currentIndex = -1;
	private static HashMap<String, JSONArray> painJSONObjects;
	private static JSONObject firstJSONObject;
	private static JSONObject secondJSONObject;
	

	@SuppressWarnings("serial")
	public static HashMap<String, Consumer<Cell>> getHandlers(HashMap<String, JSONArray> painJSONObjects) {
		ConverterSchema.painJSONObjects = painJSONObjects;
		return new HashMap<String, Consumer<Cell>>() {
			{
				put("pain_level", ConverterSchema::painLevelHandler);
				put("regimen_hierarchy", ConverterSchema::hierarchyHandler);
				put("route", ConverterSchema::routeHandler);
				put("first_drug", ConverterSchema::firstDrugHandler);
				put("1st_active_moiety", ConverterSchema::firstActiveMoietyHandler);
				put("1st_dosing_(mg)", ConverterSchema::firstDosingHandler);
				put("1st_age_adjustments_(avoid_if_years)", ConverterSchema::firstAgeAdjustmentHandler);
				put("1st_interval_(hrs)", ConverterSchema::firstIntervalHandler);
				put("1st_weight_(kg)", ConverterSchema::firstWeightHandler);
				put("1st_child_pugh_(class)", ConverterSchema::firstChildPughHandler);
				put("2nd_active_moiety", ConverterSchema::secondActiveMoietyHandler);
				put("2nd_dosing_(mg)", ConverterSchema::secondDosingHandler);
				put("2nd_age_adjustment_(avoid_if)", ConverterSchema::secondAgeAdjustmentHandler);
				put("2nd_interval_(hrs)", ConverterSchema::secondIntervalHandler);
				put("2nd_weight_(kg)", ConverterSchema::secondWeightHandler);
				put("2st_child_pugh_(class)", ConverterSchema::secondChildPughHandler);
				put("gfr_(ml/min)", ConverterSchema::gfrHandler);
				put("plt_(avoid_if_k/µl)", ConverterSchema::pltHandler);
				put("wbc_(avoid_if_10e3/microliter)", ConverterSchema::wbcHandler);
				put("sat_(avoid_if_%)", ConverterSchema::satHandler);
				put("sodium_(avoid_if_meq/l)", ConverterSchema::sodiumHandler);
				put("sensitivity_(avoid_if)", ConverterSchema::sensitivityHandler);
				put("contraindications", ConverterSchema::contraindicationsHandler);
			}
		};
	}

	static void painLevelHandler(Cell cell) {

		String level = getStringValue(cell);
		if (!currentPainLevel.equals(level)) {
			currentPainLevel = level;
			currentIndex = -1;
		}
		JSONObject rowJSONObj = new JSONObject();
		firstJSONObject = new JSONObject();

		rowJSONObj.put(FIRST, firstJSONObject);
		painJSONObjects.computeIfAbsent(currentPainLevel, k -> new JSONArray()).put(rowJSONObj);
		++currentIndex;
	}

	static void hierarchyHandler(Cell cell) {
		JSONObject jsonObj = getJSONObject();
		String value = getStringValue(cell);
		jsonObj.put("hierarchy", Integer.parseInt(value));
		
	}

	private static String getStringValue(Cell cell) {
		String value = "";
		try {
			value = cell.getStringCellValue();
			if(value.equalsIgnoreCase("NA")) {
				value = null;
			}
		} catch (Exception e) {
			value = cell.getNumericCellValue() + "";
		}
		return value;
	}

	private static JSONObject getJSONObject() {
		return painJSONObjects.get(currentPainLevel).getJSONObject(currentIndex);
	}

	static void routeHandler(Cell cell) {
		JSONObject jsonObject = getJSONObject();
		String value = getStringValue(cell);
		jsonObject.put("route", value);
		
	}

	static void firstDrugHandler(Cell cell) {
		getFirstStringArray(cell, DRUG);
	}

	private static void getFirstStringArray(Cell cell, String key) {
		JSONObject firstJsonObj = getFirstJSONObject();
		String value = getStringValue(cell);
		String [] valueArray = value.split("[\n]|OR");
		JSONArray array = new JSONArray();
		Arrays.stream(valueArray).forEach(dv -> array.put(dv));
		firstJsonObj.put(key, array);
	}

	private static JSONObject getFirstJSONObject() {
		JSONObject jsonObject = getJSONObject();
		JSONObject firstJsonObj = jsonObject.getJSONObject(FIRST);
		return firstJsonObj;
	}

	static void firstActiveMoietyHandler(Cell cell) {
		getFirstStringArray(cell, ACTIVE_MOIETY);
	}

	static void firstDosingHandler(Cell cell) {
		JSONObject jsonObject = getFirstJSONObject();
		String value = getStringValue(cell);
		jsonObject.put(DOSING, value);
	}

	static void firstAgeAdjustmentHandler(Cell cell) {
		String value = getStringValue(cell);
		if(value != null) {
			JSONObject jsonObject = getFirstJSONObject();
			
			ageAdjustmentHandler(value, jsonObject);
		}
		
	}

	private static void ageAdjustmentHandler(String value, JSONObject jsonObject) {
		JSONObject nestedJSONObj = new JSONObject();
		int indexGreater = value.indexOf('>');
		int indexLess = value.indexOf('<');
		if(indexGreater > -1) {
			nestedJSONObj.put(MAX_THRESHOULD,
					Integer.parseInt(value, indexGreater + 1, indexGreater + 3, 10));
		} 
		if(indexLess > -1) {
			nestedJSONObj.put(MIN_THRESHOULD,
					Integer.parseInt(value, indexLess + 1, indexLess + 3, 10));
		} 
		if (indexGreater > -1 || indexLess > -1 ) {
			nestedJSONObj.put(AVOID, true);
		}
		if (!nestedJSONObj.isEmpty()) {
			jsonObject.put(AGE_ADJUSTMENT, nestedJSONObj);
		}
	}

	static void firstIntervalHandler(Cell cell) {
		// TODO
	}

	static void firstWeightHandler(Cell cell) {
		// TODO
	}

	static void firstChildPughHandler(Cell cell) {
		// TODO
	}

	static void secondActiveMoietyHandler(Cell cell) {
		// TODO
	}

	static void secondDosingHandler(Cell cell) {
		// TODO
	}

	static void secondAgeAdjustmentHandler(Cell cell) {
		// TODO
	}

	static void secondIntervalHandler(Cell cell) {
		// TODO
	}

	static void secondWeightHandler(Cell cell) {
		// TODO
	}

	static void secondChildPughHandler(Cell cell) {
		// TODO
	}

	static void gfrHandler(Cell cell) {
		// TODO
	}

	static void pltHandler(Cell cell) {
		// TODO
	}

	static void wbcHandler(Cell cell) {
		// TODO
	}

	static void satHandler(Cell cell) {
		// TODO
	}

	static void sodiumHandler(Cell cell) {
		// TODO
	}

	static void sensitivityHandler(Cell cell) {
		// TODO
	}

	static void contraindicationsHandler(Cell cell) {
		// TODO
	}
}
