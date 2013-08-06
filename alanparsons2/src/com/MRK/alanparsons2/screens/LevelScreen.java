package com.MRK.alanparsons2.screens;

import com.MRK.alanparsons2.models.Level;
import com.MRK.alanparsons2.renderers.LevelRenderer;
import com.MRK.alanparsons2.templates.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class LevelScreen implements Screen {

	private Stage stage = new Stage();
	private Skin skin;
	private Label label;
	
	private LevelRenderer renderer; 	
	private String result = "";
	
	private boolean paused = false;
	
	public LevelScreen(Level level, int width, int height) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		
		renderer = new LevelRenderer(level);		
		renderer.resize(width, height);
		
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
        label = new Label("", skin);
        label.setPosition(10, 10);
        stage.addActor(label);
	}
	
	public void pause() {
		paused = true;
	}
	
	public void resume() {
		paused = false;
	}
	
	@Override
	public void update() {
		if (!paused) {
			label.setText("FPS = " + Gdx.graphics.getFramesPerSecond());
			renderer.update();
		}
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
}
