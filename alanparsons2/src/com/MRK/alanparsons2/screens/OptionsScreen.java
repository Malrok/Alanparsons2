package com.MRK.alanparsons2.screens;

import com.MRK.alanparsons2.constants.ScreenAction;
import com.MRK.alanparsons2.resources.GamePreferences;
import com.MRK.alanparsons2.templates.MenuScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OptionsScreen extends MenuScreen {
	
	private Image back;
	private GamePreferences preferences = new GamePreferences();
	
	public OptionsScreen(int width, int height) {
		super(width, height);
	}
	
	public void generateStage(Stage stage, Skin skin) {
		final CheckBox checkBox = new CheckBox("Read level from game files", skin);
		checkBox.setPosition(100, height - checkBox.getHeight() - 30);
		checkBox.setChecked(true);
		checkBox.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				preferences.setExternalFiles(checkBox.isChecked());
			}
		});
		
		stage.addActor(checkBox);
		
		Texture backTexture = new Texture(Gdx.files.internal("buttons/back.png"));
		backTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		back = new Image(backTexture);
		back.setOrigin(back.getWidth()/2, back.getHeight()/2);
		back.setPosition(10, 10);
		back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				result = ScreenAction.BACK;
			}
		});
		
		stage.addActor(back);
		
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void update() {
		super.update();
	}

	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
	}

	@Override
	public String result() {
		return result;
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void backKeyStroke() {
		result = ScreenAction.BACK;
	}
}
