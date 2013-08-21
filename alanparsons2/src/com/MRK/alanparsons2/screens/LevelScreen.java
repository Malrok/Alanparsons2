package com.MRK.alanparsons2.screens;

import com.MRK.alanparsons2.constants.ScreenAction;
import com.MRK.alanparsons2.models.GameLevel;
import com.MRK.alanparsons2.renderers.LevelRenderer;
import com.MRK.alanparsons2.templates.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LevelScreen implements Screen {

	private Stage stage = new Stage();
	private Skin skin;
	private Label label;
	private Button pause;
	
	private LevelRenderer renderer;
	private String result = "";
	private boolean win = false;
	
	private boolean paused = false;
	
	public LevelScreen(GameLevel level, int width, int height) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		
		renderer = new LevelRenderer(level);		
		renderer.resize(width, height);
		renderer.init();
		
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
        label = new Label("", skin);
        label.setPosition(10, 10);
        stage.addActor(label);
        
        pause = new Button(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("images/pause.png")))));
        pause.setSize(width / 10, width / 10);
        pause.setPosition(10, height - 10 - pause.getHeight());
        
        pause.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				paused = true;
				
				new Dialog("", skin) {
					protected void result (Object object) {
						paused = false;
						if (object.equals(false))
							result = ScreenAction.SELECT;
					}
				}.text("Would you like to continue").button("continue", true).button("Exit", false).show(stage);
			}
		});
        
        stage.addActor(pause);
        
        Gdx.input.setInputProcessor(stage);
	}
	
	public void pause() {
		paused = true;
	}
	
	public void resume() {
		paused = false;
	}
	
	@Override
	public void update() {
		if (!win) {
			if (!paused) {
				label.setText("FPS = " + Gdx.graphics.getFramesPerSecond());
				renderer.update();
				
				if (renderer.win()) {
					win = true;
					
					new Dialog("YOU WIN", skin) {
						protected void result (Object object) {
							paused = false;
							result = (String)object;
						}
					}.text("What do you want to do").button("Select level", ScreenAction.SELECT).button("Next level", ScreenAction.NEXT).show(stage);
				}
			}
		}
		
		stage.act();
	}

	@Override
	public void render() {
		renderer.render();
		stage.draw();
	}

	@Override
	public String result() {
		return result;
	}

	@Override
	public void dispose() {
		stage.dispose();
		renderer.dispose();
	}

	@Override
	public void resize(int width, int height) {
        stage.setViewport(width, height, true);
		renderer.resize(width, height);
	}

	@Override
	public void backKeyStroke() {
		result = ScreenAction.BACK;
	}
}
