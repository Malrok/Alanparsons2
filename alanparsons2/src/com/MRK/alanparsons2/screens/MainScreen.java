package com.MRK.alanparsons2.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Linear;

import com.MRK.alanparsons2.helpers.ImageAccessor;
import com.MRK.alanparsons2.templates.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class MainScreen implements Screen {

	private final float PULSE = 1.0f;
	
	private float width, height;
	
	private TextureRegion background;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Stage stage = new Stage();
	private Image start;
	private Image options;
	private TweenManager tweenManager = new TweenManager();
	
	private String result = "";
	
	public MainScreen(int width, int height) {
		this.width = width;
		this.height = height;
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		
		resize(width, height);
		
		Texture backgroundTexture = new Texture(Gdx.files.internal("images/menu.png"));
		
		backgroundTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		background = new TextureRegion(backgroundTexture, 0, 0, backgroundTexture.getWidth(), backgroundTexture.getHeight() * height / width);

		Texture startTexture = new Texture(Gdx.files.internal("buttons/start.png"));
		startTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		start = new Image(startTexture);
		start.setSize(width / 5, width / 5);
		start.setOrigin(start.getWidth()/2, start.getHeight()/2);
		start.setPosition(width / 2 - start.getWidth() / 2, height / 2 - start.getHeight() / 2);
		start.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				result = "next";
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
				result = "options";
			}
		});
		
		stage.addActor(options);
		
		Tween.registerAccessor(Image.class, new ImageAccessor());
		Tween.to(start, ImageAccessor.ZOOM, PULSE).ease(Linear.INOUT).target(1.1f, 1.1f).repeatYoyo(-1, 0).start(tweenManager);
		
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void update() {
		stage.act();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0, 0, width, height);		
		batch.end();
		
		stage.draw();
		
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
		stage.dispose();
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
		stage.setViewport(width, height, false);
	}
	
//	private final TweenCallback tweenCallback = new TweenCallback() {
//
//		@Override
//		public void onEvent(int type, BaseTween<?> source) {
//			if (type == TweenCallback.BEGIN) {
//				Tween.to(start, SpriteAccessor.ZOOM, PULSE).ease(Linear.INOUT).target(1.4f, 1.4f).start(tweenManager);
//			}
//			if (type == TweenCallback.END) {
//				result = "play";
//			}
//		}
//		
//	};
}
