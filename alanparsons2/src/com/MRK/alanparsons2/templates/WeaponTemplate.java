package com.MRK.alanparsons2.templates;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class WeaponTemplate {

	private String name;
	private String emitterName;
	private Sprite emitter;
	private int shipLevel;
	private int shootFrequency;
	private int projectileType;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
}
