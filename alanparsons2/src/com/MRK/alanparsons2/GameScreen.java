package com.MRK.alanparsons2;

import java.util.ArrayList;

import com.MRK.alanparsons2.models.Saucisse;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class GameScreen implements Screen {

	private final int SPACESHIP_SPEED = 100;
	private final int NB_MAX_SAUCISSES = 20;
	private final double SAUCISSES_CHANCES = 0.9f;

	private OrthographicCamera camera;
	
	private SpriteBatch batch;
	private Sprite sprite;
	private ArrayList<Saucisse> saucisses = new ArrayList<Saucisse>();
	
	private boolean isDone = false;
	
	public GameScreen() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		
		sprite = new Sprite(new Texture(Gdx.files.internal("data/lapin.png")));
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(20, 600 / 2 - 64 / 2);
	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		sprite.draw(batch);		
		for (Saucisse saucisse : saucisses)
			saucisse.draw(batch);
		batch.end();
	}

	@Override
	public void dispose() {
//		batch.dispose();
	}

	@Override
	public void update() {
		float x = sprite.getX(), y = sprite.getY();
		
		// process user input
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			
			float hypo = (float) Math.sqrt((touchPos.x - sprite.getX()) * (touchPos.x - sprite.getX()) + (touchPos.y - sprite.getY()) * (touchPos.y - sprite.getY()));
			x += ((touchPos.x - sprite.getX()) / hypo) * SPACESHIP_SPEED * Gdx.graphics.getDeltaTime();
			y += ((touchPos.y - sprite.getY()) / hypo) * SPACESHIP_SPEED * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			x -= SPACESHIP_SPEED * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			x += SPACESHIP_SPEED * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Keys.UP)) {
			y += SPACESHIP_SPEED * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN)) {
			y -= SPACESHIP_SPEED * Gdx.graphics.getDeltaTime();
		}
		
		sprite.setPosition(x, y);

		// make sure the bucket stays within the screen bounds
//		if(bucket.x < 0) bucket.x = 0;
//		if(bucket.x > 800 - 64) bucket.x = 800 - 64;
		
		for (Saucisse saucisse : saucisses) {
			if (sprite.getBoundingRectangle().overlaps(saucisse.getBoundingRectangle()))
				isDone = true;
		}
	    
		if (saucisses.size() < NB_MAX_SAUCISSES) {
			if (Math.random() > SAUCISSES_CHANCES) {
				Saucisse saucisse = new Saucisse(new Texture(Gdx.files.internal("data/saucisse_small.png")));
				saucisse.setOrigin(saucisse.getWidth()/2, saucisse.getHeight()/2);
				saucisse.setPosition(800, (float) (Math.random() * 600));
	
				saucisses.add(saucisse);
			}
		}
		
		ArrayList<Sprite> toBeRemoved = new ArrayList<Sprite>();
		
		for (Saucisse saucisse : saucisses) {
			saucisse.setPosition(saucisse.getX() - saucisse.getSpeed() * Gdx.graphics.getDeltaTime(), saucisse.getY());
			if (saucisse.getX() < -saucisse.getWidth())
				toBeRemoved.add(saucisse);
		}
		
		saucisses.removeAll(toBeRemoved);

	}

	@Override
	public boolean isDone() {
		return isDone;
	}
}
