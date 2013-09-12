package com.MRK.alanparsons2.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GamePreferences {

	private final String PREFERENCES = "alanp";
	private final String PREF_EXTERNAL = "external";
	private final String PREF_MUSIC_VOL = "music_vol";
	
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
	
	public int getMusicVolume() {
		return getPreferences().getInteger(PREF_MUSIC_VOL);
	}
	
	public void setMusicVolume(int musicVolume) {
		getPreferences().putInteger(PREF_MUSIC_VOL, musicVolume);
		getPreferences().flush();
	}
}
