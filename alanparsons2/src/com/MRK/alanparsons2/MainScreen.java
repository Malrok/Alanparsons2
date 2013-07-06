package com.MRK.alanparsons2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class MainScreen implements Screen {
	
	private Stage stage;
	private String result = "";
	
	public MainScreen() {
		stage = new Stage(0, 0, true);
		
		Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
		
		TextButton button = new TextButton("Start", skin.get(TextButtonStyle.class));
		button.addListener(new InputListener() {
		 
		 	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		 		result = "play";
		 	}
			
		});
		stage.addActor(button);
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void render() {
		
	}

	@Override
	public String result() {
		return result;
	}

	@Override
	public void dispose() {
		
	}

}
