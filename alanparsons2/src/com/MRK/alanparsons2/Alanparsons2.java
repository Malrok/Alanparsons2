package com.MRK.alanparsons2;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Alanparsons2 implements ApplicationListener {
	private final int SPEED = 100;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Sprite sprite;
	
	@Override
	public void create() {		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		
		sprite = new Sprite(new Texture(Gdx.files.internal("data/saucisse.png")));
		sprite.setSize(64, 64);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(800 / 2 - 64 / 2, 20);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		sprite.draw(batch);
		batch.end();
		
		// process user input
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			
//			float x = sprite.getX() + (float) Math.sqrt(Math.abs(touchPos.x - sprite.getX())) / SPEED * Gdx.graphics.getDeltaTime();
//			float y = sprite.getY() + (float) Math.sqrt(Math.abs(touchPos.y - sprite.getY())) / SPEED * Gdx.graphics.getDeltaTime();
			float hypo = (float) Math.sqrt((touchPos.x - sprite.getX()) * (touchPos.x - sprite.getX()) + (touchPos.y - sprite.getY()) * (touchPos.y - sprite.getY()));
			float x = sprite.getX() + ((touchPos.x - sprite.getX()) / hypo) * SPEED * Gdx.graphics.getDeltaTime();
			float y = sprite.getY() + ((touchPos.y - sprite.getY()) / hypo) * SPEED * Gdx.graphics.getDeltaTime();
					
			sprite.setPosition(x, y);
		}
//		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
//			bucket.x -= 200 * Gdx.graphics.getDeltaTime();
//		}
//		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
//			bucket.x += 200 * Gdx.graphics.getDeltaTime();
//		}
		  
		// make sure the bucket stays within the screen bounds
//		if(bucket.x < 0) bucket.x = 0;
//		if(bucket.x > 800 - 64) bucket.x = 800 - 64;
	      
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
