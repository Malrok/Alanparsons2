package com.MRK.alanparsons2.screens;

import com.MRK.alanparsons2.controllers.ShipController;
import com.MRK.alanparsons2.helpers.CircleHelper;
import com.MRK.alanparsons2.helpers.RotatingCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class RotateAroundRabbit implements Screen {

	private static float CAMERA_DISTANCE_FROM_FOE;
	private static float SHIP_DISTANCE_FROM_FOE;
	private static float LAPINY = .1f;
	
//	private float width, height;
	
	private RotatingCamera camera;
	private float lastRotateValue = 0;

//	private ShapeRenderer shapeRenderer;

	private ShipController controller;
	
	private Texture lapinTexture, saucisseTexture;
	private SpriteBatch batch;
	private Sprite saucisse1, saucisse2, lapin;

	private String result = "";
	
	public RotateAroundRabbit(int width, int height) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		
		load();
		
		camera = new RotatingCamera();
		batch = new SpriteBatch();
//		shapeRenderer = new ShapeRenderer();
		
		controller = new ShipController(width);
		
		resize(width, height);
	}
	
	@Override
	public void update() {
		lastRotateValue = controller.getDirection();
		camera.rotateCameraAround(new Vector3(saucisse1.getOriginX(), saucisse1.getOriginY(), 0), CAMERA_DISTANCE_FROM_FOE, lastRotateValue);
		setLapinPosition();
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

//		drawCircle();
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
//		this.width = width;
//		this.height = height;
		
		camera.setToOrtho(false, width, height);
		
		saucisse1 = new Sprite(saucisseTexture);
		saucisse1.setSize(width / 3, width / 3);
		saucisse1.setPosition(width / 2 - saucisse1.getWidth() / 2, height - saucisse1.getHeight() / 2);
		saucisse1.setOrigin(saucisse1.getX() + saucisse1.getWidth() / 2, saucisse1.getY() + saucisse1.getHeight() / 2);
		
		saucisse2 = new Sprite(saucisseTexture);
		saucisse2.setPosition(width / 3 - saucisse2.getWidth() / 2, 2 * height / 3 - saucisse2.getHeight() / 2);
		
		lapin = new Sprite(lapinTexture);
		lapin.setOrigin(lapin.getWidth() / 2, lapin.getHeight() / 2);
		
		CAMERA_DISTANCE_FROM_FOE = Math.abs(saucisse1.getY() + saucisse1.getHeight() / 2 - camera.position.y);
		SHIP_DISTANCE_FROM_FOE = Math.abs((saucisse1.getY() + saucisse1.getHeight() / 2) - (height * LAPINY + lapin.getHeight() / 2));
		
		setLapinPosition();

//		System.out.println("lapin origin x/y = " + lapin1.getOriginX() + "/" + lapin1.getOriginY() + 
//				"\ncamera x/y = " + camera.position.x + "/" + camera.position.y +
//				"\ndistanceFromLapin = " + distanceFromLapin);
	}

	private void load() {
		saucisseTexture = new Texture(Gdx.files.internal("data/saucisse.png"));
		lapinTexture = new Texture(Gdx.files.internal("data/lapin.png"));
	}
	
	private void setLapinPosition() {
		Vector3 newLapinPos = CircleHelper.getPointOnCircle(
				new Vector3(saucisse1.getOriginX(), saucisse1.getOriginY(), 0), 
				SHIP_DISTANCE_FROM_FOE,
				CircleHelper.currentAngle);

		lapin.setPosition(newLapinPos.x - lapin.getWidth() / 2, newLapinPos.y - lapin.getHeight() / 2);
		lapin.rotate((float) Math.toDegrees(lastRotateValue));
	}
	
//	private void drawCircle() {
//		shapeRenderer.setProjectionMatrix(camera.combined);
//		 
//		shapeRenderer.begin(ShapeType.Line);
//		shapeRenderer.setColor(Color.CYAN);
//		shapeRenderer.circle(saucisse1.getX() + saucisse1.getWidth() / 2, saucisse1.getY() + saucisse1.getHeight() / 2, CAMERA_DISTANCE_FROM_FOE);
//		shapeRenderer.end();
//	}
}
