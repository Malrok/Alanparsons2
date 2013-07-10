package com.MRK.alanparsons2.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class RotateAroundRabbit implements Screen {

	private static float CAMERA_ANGLE_SPEED = 1;
	
	private int width, height;
	
	private OrthographicCamera camera;

	private Texture lapinTexture;	
	private SpriteBatch batch;
	private Sprite lapin;

	private String result = "";

	private float cameraAngle = 0.0f;
	private float cameraX, cameraY;
	
	public RotateAroundRabbit(int width, int height) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		
		load();
		
		camera = new OrthographicCamera();
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
//			x -= SPACESHIP_SPEED * Gdx.graphics.getDeltaTime();
			touched = true;
			cameraAngle -= CAMERA_ANGLE_SPEED;
//			camera.rotate(-CAMERA_ANGLE_SPEED);
			camera.rotateAround(new Vector3(lapin.getX() - lapin.getWidth() / 2, lapin.getY() - lapin.getHeight() / 2, 0), new Vector3(0,0,1), -CAMERA_ANGLE_SPEED);
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			touched = true;
//			x += SPACESHIP_SPEED * Gdx.graphics.getDeltaTime();
			cameraAngle += CAMERA_ANGLE_SPEED;
//			camera.rotate(CAMERA_ANGLE_SPEED);
			camera.rotateAround(new Vector3(lapin.getX() - lapin.getWidth() / 2, lapin.getY() - lapin.getHeight() / 2, 0), new Vector3(0,0,1), CAMERA_ANGLE_SPEED);
		}
		if(Gdx.input.isKeyPressed(Keys.UP)) {
//			y += SPACESHIP_SPEED * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN)) {
//			y -= SPACESHIP_SPEED * Gdx.graphics.getDeltaTime();
		}
		
//		if (touched) {
//			camera.update();
//		}
	}

	@Override
	public void render() {
		camera.update();
		
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
//		batch.draw(sky, skyScrolling + sky.getWidth(), 0);
//		batch.draw(sky, skyScrolling, 0);
		
		lapin.draw(batch);		
//		for (Saucisse saucisse : saucisses)
//			saucisse.draw(batch);
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
		
		lapin = new Sprite(lapinTexture);
		lapin.setOrigin(lapin.getWidth()/2, lapin.getHeight()/2);
		lapin.setPosition(width / 2 - lapin.getWidth() / 2, 2 * height / 3 - lapin.getHeight() / 2);

	}

	private void load() {
		lapinTexture = new Texture(Gdx.files.internal("data/lapin.png"));
	}
}
