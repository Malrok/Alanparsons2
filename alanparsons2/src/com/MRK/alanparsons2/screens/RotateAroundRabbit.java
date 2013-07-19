package com.MRK.alanparsons2.screens;

import com.MRK.alanparsons2.controllers.ProjectileController;
import com.MRK.alanparsons2.controllers.ShipController;
import com.MRK.alanparsons2.models.RotatingCamera;
import com.MRK.alanparsons2.models.Ship;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class RotateAroundRabbit implements Screen {

	private Texture saucisseTexture, projectileTexture;
	private SpriteBatch batch;
	private Sprite saucisse1, saucisse2;
	private Ship lapin;

	private String result = "";
	
	public RotateAroundRabbit(int width, int height) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		
		load();
		
		batch = new SpriteBatch();
		
		resize(width, height);
		
		ShipController.getInstance().init(width, height, new Vector2(saucisse1.getOriginX(), saucisse1.getOriginY()));
		ShipController.getInstance().setPosition();
	}
	
	@Override
	public void update() {
		ShipController.getInstance().getDirection();
		RotatingCamera.getInstance().rotateCameraAround(saucisse1.getOriginX(), saucisse1.getOriginY(), ShipController.getInstance().getLastRotateValue());
		ShipController.getInstance().setPosition();
		lapin.update(ShipController.getInstance().getLastRotateValue());
		ProjectileController.getInstance().update();
	}

	@Override
	public void render() {
		RotatingCamera.getInstance().update();
		
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(RotatingCamera.getInstance().combined);
		batch.begin();
		
		saucisse1.draw(batch);
		saucisse2.draw(batch);

//		lapin.draw(batch);
		ShipController.getInstance().drawShip(batch);
		ProjectileController.getInstance().drawProjectiles(batch);
		
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
		RotatingCamera.getInstance().setToOrtho(false, width, height);
		
		saucisse1 = new Sprite(saucisseTexture);
		saucisse1.setSize(width / 3, width / 3);
		saucisse1.setPosition(width / 2 - saucisse1.getWidth() / 2, height - saucisse1.getHeight() / 2);
		saucisse1.setOrigin(saucisse1.getX() + saucisse1.getWidth() / 2, saucisse1.getY() + saucisse1.getHeight() / 2);
		
		saucisse2 = new Sprite(saucisseTexture);
		saucisse2.setPosition(width / 3 - saucisse2.getWidth() / 2, 2 * height / 3 - saucisse2.getHeight() / 2);
		
		lapin = Ship.getInstance();
		lapin.setWeapon(projectileTexture);
		lapin.setOrigin(lapin.getWidth() / 2, lapin.getHeight() / 2);
		
		ProjectileController.getInstance().addWeapon(lapin.getWeapon());
		
		RotatingCamera.getInstance().setRadius(Math.abs(saucisse1.getY() + saucisse1.getHeight() / 2 - RotatingCamera.getInstance().position.y));
		
//		System.out.println("lapin origin x/y = " + lapin1.getOriginX() + "/" + lapin1.getOriginY() + 
//				"\ncamera x/y = " + camera.position.x + "/" + camera.position.y +
//				"\ndistanceFromLapin = " + distanceFromLapin);
	}

	private void load() {
		saucisseTexture = new Texture(Gdx.files.internal("data/saucisse.png"));
		projectileTexture = new Texture(Gdx.files.internal("data/shot.png"));
		Ship.getInstance().init();
	}
}
