package com.MRK.alanparsons2.resources;

/**
 * Décrit une valeur d'une resource par son type et sa valeur
 * @author malrok
 *
 */
public class ResourceValue {
	public static final int NUMBER = 0x0;
	public static final int STRING = 0x1;
	
	private int type;
	private float numberValue;
	private String stringValue;
	
	public ResourceValue(float value) {
		this.type = NUMBER;
		this.numberValue = value;
	}
	
	public ResourceValue(String value) {
		this.type = STRING;
		this.stringValue = value;
	}
	
	public int getType() {
		return type;
	}
	
	public float getNumber() {
		return numberValue;
	}
	
	public String getString() {
		return stringValue;
	}
}
