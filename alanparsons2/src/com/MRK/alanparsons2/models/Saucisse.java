package com.MRK.alanparsons2.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Saucisse extends Sprite {

	private final int SAUCISSE_MAX_SPEED = 300;
	private final int SAUCISSE_MIN_SPEED = 100;
	
	private int speed;
	
	public Saucisse(Texture texture) {
		super(texture);
		speed = (int) (SAUCISSE_MIN_SPEED + Math.random() * (SAUCISSE_MAX_SPEED - SAUCISSE_MIN_SPEED));
	}
	
	public int getSpeed() {
		return speed;
	}
}
