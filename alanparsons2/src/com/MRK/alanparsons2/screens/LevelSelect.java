package com.MRK.alanparsons2.screens;

import java.util.ArrayList;
import java.util.List;

import com.MRK.alanparsons2.models.Level;
import com.MRK.alanparsons2.templates.Screen;
import com.MRK.alanparsons2.ui.LevelButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LevelSelect implements Screen {
	
	private final int MAX_COLUMNS = 5;
	private final float WIDTH_SPAN = 20f;
	private final float HEIGHT_SPAN = 20f;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private TextureRegion background;
	private Stage stage = new Stage();
	private Skin skin;
	private CheckBox checkBox;
	
	private List<Level> levels = new ArrayList<Level>();
	private float width, height;
	private String result = "";
	
	public LevelSelect(List<Level> levels, int width, int height) {
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		
		this.levels = levels;
		resize(width, height);
		
		Texture texture = new Texture(Gdx.files.internal("images/menu.png"));
		
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		background = new TextureRegion(texture,0,0,texture.getWidth(),texture.getHeight() * height / width);
		
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
		checkBox = new CheckBox("Read level from game files", skin);
		checkBox.setPosition(10,10);
		checkBox.setChecked(true);

		stage.addActor(checkBox);
		
		loadLevelsButtons();
		
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void update() {
		
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
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
		this.width = width;
		this.height = height;
	}
	
	private void loadLevelsButtons() {
		int rows = 1, columns = 1;
		int coordx, coordy;
		float buttonWidth = 0, buttonHeight = 0;
		
		rows = (levels.size() > MAX_COLUMNS ? MAX_COLUMNS : levels.size());
		columns = levels.size() / MAX_COLUMNS + 1;
		
		buttonWidth = (width - (MAX_COLUMNS + 1) * WIDTH_SPAN) / MAX_COLUMNS;
		buttonHeight = buttonWidth;
		
		for (int y=0 ; y<columns ; y++) {
			for (int x=0 ; x<rows ; x++) {
				if (y * x + x < levels.size()) {
//					TextButton button = new TextButton(levels.get(y * x + x).getName(), skin);
					LevelButton button = new LevelButton(levels.get(y * x + x).getName(), skin);
					button.setLevel(levels.get(y * x + x));
					coordx = (int) ((x + 1) * WIDTH_SPAN + buttonWidth * x);
					coordy = (int) (height - ((y + 1) * (HEIGHT_SPAN + buttonHeight)));					
					button.setPosition(coordx, coordy);
					button.setSize(buttonWidth, buttonHeight);
					
					button.addListener(new ClickListener() {
						@Override
						public void clicked(InputEvent event, float x, float y) {
							result = "play " + ((LevelButton)event.getListenerActor()).getLevel().getFile().nameWithoutExtension() + " " + (checkBox.isChecked() ? "internal" : "external");
						}
					});
					
					stage.addActor(button);
				}
			}
		}
	}
}
