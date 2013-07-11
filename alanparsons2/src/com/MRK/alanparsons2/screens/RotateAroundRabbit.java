package com.MRK.alanparsons2.screens;

import com.MRK.alanparsons2.helpers.RotatingCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class RotateAroundRabbit implements Screen {

	private static float CAMERA_ANGLE_SPEED = 0.01f;
	
	private static float LAPINX = .5f; 
	private static float LAPINY = .2f;
	
	private float width, height;
	
	private RotatingCamera camera;
	private float distanceFromLapin;

	private Texture lapinTexture, saucisseTexture;
	private SpriteBatch batch;
	private Sprite saucisse1, saucisse2, lapin;

	private String result = "";
	
	public RotateAroundRabbit(int width, int height) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		
		load();
		
		camera = new RotatingCamera();
		batch = new SpriteBatch();
		
		resize(width, height);
	}
	
	@Override
	public void update() {
		boolean touched = false;
		
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			
//			float hypo = (float) Math.sqrt((touchPos.x - lapin.getX()) * (touchPos.x - lapin.getX()) + (touchPos.y - lapin.getY()) * (touchPos.y - lapin.getY()));
//			x += ((touchPos.x - lapin.getX()) / hypo) * SPACESHIP_SPEED * Gdx.graphics.getDeltaTime();
//			y += ((touchPos.y - lapin.getY()) / hypo) * SPACESHIP_SPEED * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			touched = true;
			camera.rotateCameraAround(new Vector3(saucisse1.getOriginX(), saucisse1.getOriginY(), 0), distanceFromLapin, -CAMERA_ANGLE_SPEED);
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			touched = true;
			camera.rotateCameraAround(new Vector3(saucisse1.getOriginX(), saucisse1.getOriginY(), 0), distanceFromLapin, +CAMERA_ANGLE_SPEED);
		}
		if(Gdx.input.isKeyPressed(Keys.UP)) {
//			y += SPACESHIP_SPEED * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN)) {
//			y -= SPACESHIP_SPEED * Gdx.graphics.getDeltaTime();
		}
		
		if (touched) {
			Vector3 newLapinPos = new Vector3(width * LAPINX, height * LAPINY, 0);
			camera.unproject(newLapinPos);
			lapin.setPosition(newLapinPos.x, newLapinPos.y);
		}
	}

	@Override
	public void render() {
		camera.update();
		
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		saucisse1.draw(batch);
		saucisse2.draw(batch);

		lapin.draw(batch);
		
		batch.end();
	}

	@Override
	public String result() {
		return result;
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		
		camera.setToOrtho(false, width, height);
		
		saucisse1 = new Sprite(saucisseTexture);
		saucisse1.setSize(width / 3, width / 3);
		saucisse1.setPosition(width / 2 - saucisse1.getWidth() / 2, height - saucisse1.getHeight() / 2);
		saucisse1.setOrigin(saucisse1.getX() + saucisse1.getWidth() / 2, saucisse1.getY() + saucisse1.getHeight() / 2);
		
		saucisse2 = new Sprite(saucisseTexture);
		saucisse2.setPosition(width / 3 - saucisse2.getWidth() / 2, 2 * height / 3 - saucisse2.getHeight() / 2);
		
		lapin = new Sprite(lapinTexture);
		lapin.setPosition(width * LAPINX, height * LAPINY);
		
		distanceFromLapin = Math.abs(saucisse1.getY() + saucisse1.getHeight() / 2 - camera.position.y);
		
//		System.out.println("lapin origin x/y = " + lapin1.getOriginX() + "/" + lapin1.getOriginY() + 
//				"\ncamera x/y = " + camera.position.x + "/" + camera.position.y +
//				"\ndistanceFromLapin = " + distanceFromLapin);
	}

	private void load() {
		saucisseTexture = new Texture(Gdx.files.internal("data/saucisse.png"));
		lapinTexture = new Texture(Gdx.files.internal("data/lapin.png"));
	}
}
