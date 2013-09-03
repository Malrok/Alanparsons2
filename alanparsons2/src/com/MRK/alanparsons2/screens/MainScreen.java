package com.MRK.alanparsons2.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Linear;

import com.MRK.alanparsons2.constants.ScreenAction;
import com.MRK.alanparsons2.helpers.ImageAccessor;
import com.MRK.alanparsons2.templates.MenuScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class MainScreen extends MenuScreen {

	private final float PULSE = 1.0f;
	private Image start;
	private Image options;
	private TweenManager tweenManager = new TweenManager();
	
	public MainScreen(int width, int height) {
		super(width, height);
	}
	
	public void generateStage(Stage stage, Skin skin) {
		Texture startTexture = new Texture(Gdx.files.internal("buttons/start.png"));
		startTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		start = new Image(startTexture);
		start.setSize(width / 5, width / 5);
		start.setOrigin(start.getWidth()/2, start.getHeight()/2);
		start.setPosition(width / 2 - start.getWidth() / 2, height / 2 - start.getHeight() / 2);
		start.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				result = ScreenAction.NEXT;
			}
		});
		
		stage.addActor(start);

		Texture optionsTexture = new Texture(Gdx.files.internal("buttons/options.png"));
		optionsTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		options = new Image(optionsTexture);
		options.setOrigin(options.getWidth()/2, options.getHeight()/2);
		options.setPosition(10, 10);
		options.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				result = ScreenAction.OPTIONS;
			}
		});
		
		stage.addActor(options);
		
		Tween.registerAccessor(Image.class, new ImageAccessor());
		Tween.to(start, ImageAccessor.ZOOM, PULSE).ease(Linear.INOUT).target(1.1f, 1.1f).repeatYoyo(-1, 0).start(tweenManager);
		
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void update() {
		super.update();
	}

	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		tweenManager.update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public String result() {
		return result;
	}

	@Override
	public void backKeyStroke() {
		result = "back";
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}
