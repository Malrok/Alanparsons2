package com.MRK.alanparsons2.templates;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WeaponTemplate {

	private String name;
	private TextureRegion texture;
	private float width = 0, height = 0;
	private String emitterName;
	private Sprite emitter;
	private int shipLevel;
	private int shootFrequency;
	private int projectileType;
	private int hps;
	private int touchPoints, destroyPoints;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public TextureRegion getTexture() {
		return texture;
	}

	public void setTexture(TextureRegion texture) {
		this.texture = texture;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public String getEmitterName() {
		return emitterName;
	}
	
	public void setEmitterName(String emitterName) {
		this.emitterName = emitterName;
	}
	
	public Sprite getEmitter() {
		return emitter;
	}

	public void setEmitter(Sprite emitter) {
		this.emitter = emitter;
	}

	public int getShipLevel() {
		return shipLevel;
	}

	public void setShipLevel(int shipLevel) {
		this.shipLevel = shipLevel;
	}

	public int getShootFrequency() {
		return shootFrequency;
	}
	
	public void setShootFrequency(int shootFrequency) {
		this.shootFrequency = shootFrequency;
	}
	
	public int getProjectileType() {
		return projectileType;
	}
	
	public void setProjectileType(int projectileType) {
		this.projectileType = projectileType;
	}

	public int getHps() {
		return hps;
	}

	public void setHps(int hps) {
		this.hps = hps;
	}

	public int getTouchPoints() {
		return touchPoints;
	}

	public void setTouchPoints(int touchPoints) {
		this.touchPoints = touchPoints;
	}

	public int getDestroyPoints() {
		return destroyPoints;
	}

	public void setDestroyPoints(int destroyPoints) {
		this.destroyPoints = destroyPoints;
	}
}
