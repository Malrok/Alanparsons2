package com.MRK.alanparsons2.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


public class MainScreen implements Screen {

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Sprite start;

	private String result = "";
	
	public MainScreen() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		
		start = new Sprite(new Texture(Gdx.files.internal("buttons/start.png")));
		start.setOrigin(start.getWidth()/2, start.getHeight()/2);
		
	}
	
	@Override
	public void update() {
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			
			if (start.getBoundingRectangle().contains(new Vector2(touchPos.x, touchPos.y))) {
				result = "play";
			}
		}
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		start.draw(batch);		
		batch.end();
	}

	@Override
	public String result() {
		return result;
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void resize(int width, int height) {
		start.setPosition(width / 2 - start.getWidth() / 2, height / 2 - start.getHeight() / 2);
	}
}
