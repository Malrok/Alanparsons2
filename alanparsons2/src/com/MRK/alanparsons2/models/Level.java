package com.MRK.alanparsons2.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

public class Level implements Disposable {

	public static final float LEVEL_WIDTH = 100f;
	public static final float LEVEL_HEIGHT = 100f;
	
	private Texture projectileTexture, starsTexture, planetTexture, sunTexture;
	
	private Ship ship;
	private PixmapSprite foe;

	public Level() {
		projectileTexture = new Texture(Gdx.files.internal("data/shot.png"));
		starsTexture = new Texture(Gdx.files.internal("data/stars.png"));
		planetTexture = new Texture(Gdx.files.internal("data/planet.png"));
		sunTexture = new Texture(Gdx.files.internal("data/sun.png"));
		
		ship = new Ship();
		ship.setWeapon(projectileTexture);
		
		foe = new PixmapSprite("data/enemy.png");
	}

	public void dispose() {
		ship.dispose();
		foe.dispose();
	}
	
	public void resize(int width, int height) {
		foe.setSize(RotatingCamera.VIEWPORT_WIDTH / 3, RotatingCamera.VIEWPORT_WIDTH / 3);
		foe.setPosition(LEVEL_WIDTH / 2 - foe.getWidth() / 2, LEVEL_HEIGHT / 2 - foe.getHeight() / 2);
		foe.setOrigin(foe.getX() + foe.getWidth() / 2, foe.getY() + foe.getHeight() / 2);
	}

	public Texture getProjectileTexture() {
		return projectileTexture;
	}

	public void setProjectileTexture(Texture projectileTexture) {
		this.projectileTexture = projectileTexture;
	}
	
	public Ship getShip() {
		return ship;
	}
	
	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public PixmapSprite getFoe() {
		return foe;
	}

	public Texture getStarsTexture() {
		return starsTexture;
	}
	
	public Texture getPlanetTexture() {
		return planetTexture;
	}
	
	public Texture getSunTexture() {
		return sunTexture;
	}
}
