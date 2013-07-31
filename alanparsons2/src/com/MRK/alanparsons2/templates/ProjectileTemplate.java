package com.MRK.alanparsons2.templates;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ProjectileTemplate {

	private int type;
	private float speed;
	private int power = 1;
	private float width, height;
	private TextureRegion texture;
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
		
	public void setSpeed(float speed) {
		this.speed = speed;
	}
		
	public float getSpeed() {
		return speed;
	}
	
	public int getPower() {
		return power;
	}
	
	public void setPower(int shootPower) {
		this.power = shootPower;
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
	
	public TextureRegion getTexture() {
		return texture;
	}
	
	public void setTexture(TextureRegion texture) {
		this.texture = texture;
	}	
}
