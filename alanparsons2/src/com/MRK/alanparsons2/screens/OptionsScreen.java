package com.MRK.alanparsons2.screens;

import com.MRK.alanparsons2.helpers.ScreenAction;
import com.MRK.alanparsons2.resources.GamePreferences;
import com.MRK.alanparsons2.templates.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OptionsScreen implements Screen {

	private float width, height;
	private String result = "";
	
	private TextureRegion background;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Stage stage = new Stage();
	private Image back;
	
	public OptionsScreen(final GamePreferences preferences, int width, int height) {
		final CheckBox checkBox;
		Skin skin;
		
		this.width = width;
		this.height = height;
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		
		resize(width, height);
		
		Texture backgroundTexture = new Texture(Gdx.files.internal("images/menu.png"));
		backgroundTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		background = new TextureRegion(backgroundTexture, 0, 0, backgroundTexture.getWidth(), backgroundTexture.getHeight() * height / width);
		
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
		checkBox = new CheckBox("Read level from game files", skin);
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
		stage.act();
		
		if (stage.keyDown(Keys.BACK)) {
			result = "back";
		}
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
	}

	@Override
	public String result() {
		return result;
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

	@Override
	public void backKeyStroke() {
		result = ScreenAction.BACK;
	}

}
