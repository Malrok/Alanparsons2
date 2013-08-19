package com.MRK.alanparsons2.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GamePreferences {

	private final String PREFERENCES = "alanp";
	private final String PREF_EXTERNAL = "external";
	
	protected Preferences getPreferences() {
		return Gdx.app.getPreferences(PREFERENCES);
	}
	
	public boolean isExternalFiles() {
		return getPreferences().getBoolean(PREF_EXTERNAL);
	}
	
	public void setExternalFiles(boolean external) {
		getPreferences().putBoolean(PREF_EXTERNAL, external);
		getPreferences().flush();
	}
	
}
