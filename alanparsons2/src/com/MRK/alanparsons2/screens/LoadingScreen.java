package com.MRK.alanparsons2.screens;

import java.io.IOException;

import com.MRK.alanparsons2.factories.LevelBuilder;
import com.MRK.alanparsons2.resources.GamePreferences;
import com.MRK.alanparsons2.templates.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoadingScreen implements Screen {

	private String result = "";
	private Texture loadingTexture;
	private Thread thread;
	
	public LoadingScreen(final String lvlStr, final LevelBuilder levelBuilder) {
		GamePreferences preferences = new GamePreferences();
		final boolean internal = !preferences.isExternalFiles();
		
		loadingTexture = new Texture(Gdx.files.internal("images/loading.png"));
		
		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						try {
							levelBuilder.load(internal, lvlStr);
						} catch (IOException e) {
							e.printStackTrace();
							return;
						}
						
						levelBuilder.parse();
						levelBuilder.buildLevel();
						
						result = "loaded";
			         }
				});
			}
			
		});
		
		thread.start();
	}
	
	@Override
	public void update() {
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(loadingTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void resize(int width, int height) {

	}
}
