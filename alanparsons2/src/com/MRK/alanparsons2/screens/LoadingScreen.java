package com.MRK.alanparsons2.screens;

import java.io.IOException;

import com.MRK.alanparsons2.factories.LevelBuilder;
import com.MRK.alanparsons2.templates.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoadingScreen implements Screen {

	private String result = "";
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture loadingTexture;
	private float width, height;	
	private Thread thread;
	
	public LoadingScreen(final boolean internal, final String lvlStr, final LevelBuilder levelBuilder, int width, int height) {
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		loadingTexture = new Texture(Gdx.files.internal("images/loading.png"));
		
		resize(width, height);
		
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
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		batch.draw(loadingTexture, 0, 0, width, height);
		
		batch.end();
	}

	@Override
	public String result() {
		return result;
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
		camera.setToOrtho(false, width, height);
		this.width = width;
		this.height = height;
	}

}
