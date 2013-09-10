package com.MRK.alanparsons2.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class WeakPoint extends Sprite implements Disposable {

	private String name;
	private int energy;
	private TextureRegion texture;
	private String hostName;
	private Sprite host;
	private int touchPoints, destroyPoints;
	
	@Override
	public void dispose() {

	}
	
	@Override
	public void draw(SpriteBatch batch) {
		if (texture != null) {
			batch.draw(texture, getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
		}
	}

	public void setTexture(TextureRegion texture) {
		this.texture = texture;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
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
