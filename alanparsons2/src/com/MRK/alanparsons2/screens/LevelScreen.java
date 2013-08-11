package com.MRK.alanparsons2.screens;

import com.MRK.alanparsons2.models.Level;
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
	
	private boolean paused = false;
	
	public LevelScreen(Level level, int width, int height) {
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
							result = "cancel";
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
		if (!paused) {
			label.setText("FPS = " + Gdx.graphics.getFramesPerSecond());
			renderer.update();
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
}
