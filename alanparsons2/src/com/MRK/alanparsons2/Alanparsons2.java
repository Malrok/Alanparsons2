package com.MRK.alanparsons2;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Alanparsons2 implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
//	private Texture texture;
	private Sprite sprite;
//	private Rectangle saucisse;
	
	@Override
	public void create() {		
//		float w = Gdx.graphics.getWidth();
//		float h = Gdx.graphics.getHeight();
		
//		camera = new OrthographicCamera(1, h/w);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		
//		texture = new Texture(Gdx.files.internal("data/saucisse.png"));
//		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
//		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
		
//		saucisse = new Rectangle();
//		saucisse.x = 800 / 2 - 64 / 2;
//		saucisse.y = 20;
//		saucisse.width = 64;
//		saucisse.height = 64;
		
		sprite = new Sprite(new Texture(Gdx.files.internal("data/saucisse.png")));
		sprite.setSize(64, 64);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(800 / 2 - 64 / 2, 20);
	}

	@Override
	public void dispose() {
		batch.dispose();
//		texture.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		sprite.draw(batch);
//		batch.draw(texture, saucisse.x, saucisse.y);

		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
