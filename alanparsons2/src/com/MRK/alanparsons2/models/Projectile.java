package com.MRK.alanparsons2.models;

import com.MRK.alanparsons2.helpers.CircleHelper;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Classe héritant de {@link Sprite}, définissant un projectile
 */
public class Projectile extends Sprite {

	public static float PROJECTILE_WIDTH = 1;
	public static float PROJECTILE_HEIGHT = 0.4f;
	
	private Vector2 speed;
	private int power;

	/**
	 * Constructeur de la classe
	 * @param texture - {@link Texture}
	 * @param speed   - {@link Vector2}
	 * @param power   - int
	 */
	public Projectile(Texture texture, Vector2 speed, int power) {
		super(texture);
		super.setSize(PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
		this.speed = speed;
		this.power = power;
		super.rotate(CircleHelper.getDAngle(speed.x, speed.y));
	}
	
	public Vector2 getSpeed() {
		return speed;
	}
	
	public int getPower() {
		return power;
	}
	
	public void setPower(int power) {
		this.power = power;
	}
	
	/**
	 * Calcul de la nouvelle position
	 */
	public void update() {
		float x = getX() + speed.x,
			  y = getY() + speed.y;
		setPosition(x, y);
	}
}
