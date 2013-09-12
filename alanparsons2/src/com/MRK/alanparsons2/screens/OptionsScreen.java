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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OptionsScreen extends MenuScreen {
	
	private Image back;
	private GamePreferences preferences = new GamePreferences();
	
	private CheckBox checkBox;
	private Label musicVolumeLabel, musicVolumeText;
	private Slider musicVolume;
	
	public OptionsScreen(int width, int height) {
		super(width, height);
	}
	
	public void generateStage(Stage stage, Skin skin) {
		checkBox = new CheckBox("Read level from game files", skin);
		checkBox.setPosition(100, height - checkBox.getHeight() - 30);
		checkBox.setChecked(preferences.isExternalFiles());
		checkBox.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				preferences.setExternalFiles(checkBox.isChecked());
			}
		});
		
		stage.addActor(checkBox);
		
		musicVolumeLabel = new Label("MUSIC ", skin);
		musicVolumeLabel.setPosition(100, height - checkBox.getHeight() - 60 - musicVolumeLabel.getHeight());
		
		stage.addActor(musicVolumeLabel);
		
		musicVolume = new Slider(0, 7, 1, false, skin);
		musicVolume.setPosition(100 + musicVolumeLabel.getWidth(), height - checkBox.getHeight() - 60 - musicVolume.getHeight());
		musicVolume.setValue(preferences.getMusicVolume());
		musicVolume.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				musicVolumeText.setText(String.valueOf((int)musicVolume.getValue()));
				preferences.setMusicVolume((int) musicVolume.getValue());
			}
		});
		
		stage.addActor(musicVolume);
		
		musicVolumeText = new Label(String.valueOf((int)musicVolume.getValue()), skin);
		musicVolumeText.setPosition(100 + musicVolumeLabel.getWidth() + musicVolume.getWidth(), height - checkBox.getHeight() - 60 - musicVolumeLabel.getHeight());
		
		stage.addActor(musicVolumeText);
		
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
