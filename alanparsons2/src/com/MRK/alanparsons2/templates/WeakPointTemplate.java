package com.MRK.alanparsons2.templates;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WeakPointTemplate {

	private int energy;
	private TextureRegion texture;
	private float width, height;
	private String hostName;
	private Sprite host;
	
	public int getEnergy() {
		return energy;
	}
	
	public void setEnergy(int energy) {
		this.energy = energy;
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

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Sprite getHost() {
		return host;
	}

	public void setHost(Sprite host) {
		this.host = host;
	}
	
}
