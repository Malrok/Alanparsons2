package com.MRK.alanparsons2.screens;

import java.util.ArrayList;
import java.util.List;

import com.MRK.alanparsons2.models.Level;
import com.MRK.alanparsons2.templates.MenuScreen;
import com.MRK.alanparsons2.ui.LevelButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LevelSelect extends MenuScreen {
	
	private final int MAX_COLUMNS = 5;
	private final float WIDTH_SPAN = 20f;
	private final float HEIGHT_SPAN = 20f;
	private List<Level> levels = new ArrayList<Level>();
	
	public LevelSelect(List<Level> levels, int width, int height) {
		super(width, height);

		this.levels = levels;
		resize(width, height);
	}
	
	public void generateStage(Stage stage, Skin skin) {
		
		loadLevelsButtons(stage, skin);
		
		Texture backTexture = new Texture(Gdx.files.internal("buttons/back.png"));
		backTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		Image back = new Image(backTexture);
		back.setOrigin(back.getWidth()/2, back.getHeight()/2);
		back.setPosition(10, 10);
		back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				result = "back";
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
		result = "back";
	}
	
	private void loadLevelsButtons(Stage stage, Skin skin) {
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
					LevelButton button = new LevelButton(levels.get(y * x + x).getName(), skin);
					button.setLevel(levels.get(y * x + x));
					coordx = (int) ((x + 1) * WIDTH_SPAN + buttonWidth * x);
					coordy = (int) (height - ((y + 1) * (HEIGHT_SPAN + buttonHeight)));					
					button.setPosition(coordx, coordy);
					button.setSize(buttonWidth, buttonHeight);
					
					button.addListener(new ClickListener() {
						@Override
						public void clicked(InputEvent event, float x, float y) {
							result = "play " + ((LevelButton)event.getListenerActor()).getLevel().getFile().nameWithoutExtension();
						}
					});
					
					stage.addActor(button);
				}
			}
		}
	}

}
