package com.MRK.alanparsons2.ui;

import com.MRK.alanparsons2.models.Level;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class LevelButton extends TextButton {

	private Level level;
	
	public LevelButton(String text, Skin skin) {
		super(text, skin);
	}
	
	public Level getLevel() {
		return level;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}

}
