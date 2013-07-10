package com.MRK.alanparsons2.screens;

import java.util.ArrayList;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Linear;

import com.MRK.alanparsons2.helpers.SpriteAccessor;
import com.MRK.alanparsons2.models.Saucisse;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class RabbitvsSausages implements Screen {

	private final int SPACESHIP_SPEED = 100;
	private final int NB_MAX_SAUCISSES = 20;
	private final double SAUCISSES_CHANCES = 0.9f;
	private final int SCROLLING_SPEED = 5;

	private int width, height;
	
	private OrthographicCamera camera;

	private Texture lapinTexture;
	private Texture skyTexture;
	private Texture saucisseTexture;
	
	private SpriteBatch batch;
	private Sprite lapin;
	private Sprite sky;
	private ArrayList<Saucisse> saucisses = new ArrayList<Saucisse>();
	
    private TweenManager tweenManager = new TweenManager();
	
    private int skyScrolling = 0;
	private String result = "";
	
	public RabbitvsSausages(int width, int height) {
		load();
		
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		
		resize(width, height);
		
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
	}
	
	@Override
	public void update() {
		float x = lapin.getX(), y = lapin.getY();
		
		// process user input
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			
			float hypo = (float) Math.sqrt((touchPos.x - lapin.getX()) * (touchPos.x - lapin.getX()) + (touchPos.y - lapin.getY()) * (touchPos.y - lapin.getY()));
			x += ((touchPos.x - lapin.getX()) / hypo) * SPACESHIP_SPEED * Gdx.graphics.getDeltaTime();
			y += ((touchPos.y - lapin.getY()) / hypo) * SPACESHIP_SPEED * Gdx.graphics.getDeltaTime();
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
		
		lapin.setPosition(x, y);

		// make sure the bucket stays within the screen bounds
//		if(bucket.x < 0) bucket.x = 0;
//		if(bucket.x > 800 - 64) bucket.x = 800 - 64;
		
		for (Saucisse saucisse : saucisses) {
			if (lapin.getBoundingRectangle().overlaps(saucisse.getBoundingRectangle()))
				result = "lose";
		}
	    
		if (saucisses.size() < NB_MAX_SAUCISSES) {
			if (Math.random() > SAUCISSES_CHANCES) {
				Saucisse saucisse = new Saucisse(saucisseTexture);
				saucisse.setOrigin(saucisse.getWidth()/2, saucisse.getHeight()/2);
				saucisse.setPosition(width, (float) (Math.random() * height));
	
				saucisses.add(saucisse);
				
				Tween.to(saucisse, SpriteAccessor.POSITION_X, width / saucisse.getSpeed()).ease(Linear.INOUT).target(-saucisse.getWidth()).start(tweenManager);
			}
		}
		
		ArrayList<Sprite> toBeRemoved = new ArrayList<Sprite>();
		
		for (Saucisse saucisse : saucisses) {
			if (saucisse.getX() <= -saucisse.getWidth())
				toBeRemoved.add(saucisse);
		}
		
		saucisses.removeAll(toBeRemoved);
		
		skyScrolling -= SCROLLING_SPEED;
		if (skyScrolling < -sky.getWidth())
			skyScrolling = 0;
	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		batch.draw(sky, skyScrolling + sky.getWidth(), 0);
		batch.draw(sky, skyScrolling, 0);
		
		lapin.draw(batch);		
		for (Saucisse saucisse : saucisses)
			saucisse.draw(batch);
		batch.end();
		
		tweenManager.update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public String result() {
		return result;
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		
		camera.setToOrtho(false, width, height);
		
		lapin = new Sprite(lapinTexture);
		lapin.setOrigin(lapin.getWidth()/2, lapin.getHeight()/2);
		lapin.setPosition(20, height / 2 - lapin.getHeight() / 2);
		
		float wOnh = skyTexture.getWidth() / skyTexture.getHeight();
		
		sky = new Sprite(skyTexture, (int) (height * wOnh), height);
	}
	
	private void load() {
		lapinTexture = new Texture(Gdx.files.internal("data/lapin.png"));
		skyTexture = new Texture(Gdx.files.internal("data/sky.png"));
		saucisseTexture = new Texture(Gdx.files.internal("data/saucisse_small.png"));
	}
}
