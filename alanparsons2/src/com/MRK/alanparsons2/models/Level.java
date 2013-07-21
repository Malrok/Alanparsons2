package com.MRK.alanparsons2.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Level {

	public static final float LEVEL_WIDTH = 100f;
	public static final float LEVEL_HEIGHT = 100f;
	
	private Texture foeTexture, projectileTexture, starsTexture, planetTexture;
	
	private Ship ship;
	private Sprite foe;

	public Level() {
		foeTexture = new Texture(Gdx.files.internal("data/saucisse.png"));
		projectileTexture = new Texture(Gdx.files.internal("data/shot.png"));
		starsTexture = new Texture(Gdx.files.internal("data/stars.png"));
		planetTexture = new Texture(Gdx.files.internal("data/planet.png"));
		
		ship = new Ship();
		ship.setWeapon(projectileTexture);
		
		foe = new Sprite(foeTexture);
	}

	public void resize(int width, int height) {
		foe.setSize(RotatingCamera.VIEWPORT_WIDTH / 3, RotatingCamera.VIEWPORT_HEIGHT / 3);
		foe.setPosition(LEVEL_WIDTH / 2 - foe.getWidth() / 2, LEVEL_HEIGHT / 2 - foe.getHeight() / 2);
		foe.setOrigin(foe.getX() + foe.getWidth() / 2, foe.getY() + foe.getHeight() / 2);
	}
	
	public Texture getFoeTexture() {
		return foeTexture;
	}

	public void setFoeTexture(Texture foeTexture) {
		this.foeTexture = foeTexture;
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

	public Sprite getFoe() {
		return foe;
	}

	public void setFoe(Sprite foe) {
		this.foe = foe;
	}

	public Texture getStarsTexture() {
		return starsTexture;
	}
	
	public Texture getPlanetTexture() {
		return planetTexture;
	}
}
