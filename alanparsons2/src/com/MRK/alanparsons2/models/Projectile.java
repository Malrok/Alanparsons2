package com.MRK.alanparsons2.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Classe héritant de {@link Sprite}, définissant un projectile
 */
public class Projectile extends Sprite {

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
		this.speed = speed;
		this.power = power;
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
		float x, y;
		x = this.getX() + speed.x;
		y = this.getY() + speed.y;
		this.setPosition(x, y);
	}
}
