package com.MRK.alanparsons2.resources;

import java.util.HashMap;
import java.util.Map;

/**
 * Décrit une resource et ses valeurs
 * @author malrok
 *
 */
public class Resource {

//	private String sectionName;
	private Map<String, ResourceValue> values = new HashMap<String, ResourceValue>();
	
//	public Resource(String name) {
//		sectionName = name;
//	}
//	
//	public String getSectionName() {
//		return sectionName;
//	}
	
	public Map<String, ResourceValue> getValues() {
		return values;
	}
	
	public void addValue(String entry, String value) {
		values.put(entry, new ResourceValue(value));
	}
	
	public void addValue(String entry, float value) {
		values.put(entry, new ResourceValue(value));
	}
	
	public int getValueType(String entry) {
		return values.get(entry).getType();
	}
	
	public String getStringValue(String entry) {
		return values.get(entry).getString();
	}
	
	public float getNumberValue(String entry) {
		return values.get(entry).getNumber();
	}
}
